package dev.jayms.evergreen.elytra;

import dev.jayms.evergreen.Evergreen;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import vg.civcraft.mc.civmodcore.players.scoreboard.side.CivScoreBoard;
import vg.civcraft.mc.civmodcore.players.scoreboard.side.ScoreBoardAPI;
import vg.civcraft.mc.civmodcore.utilities.cooldowns.ICoolDownHandler;
import vg.civcraft.mc.civmodcore.utilities.cooldowns.MilliSecCoolDownHandler;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.function.BiFunction;

public class ElytraListener implements Listener {

    public static final Random random = new Random(System.nanoTime());

    public static Vector getRandomVector() {
        double x, y, z;
        x = random.nextDouble() * 2 - 1;
        y = random.nextDouble() * 2 - 1;
        z = random.nextDouble() * 2 - 1;
        return new Vector(x, y, z).normalize();
    }

    private static Map<UUID, BoostData> boostDataMap = new HashMap<>();
    private static Map<UUID, BukkitRunnable> beenHit = new HashMap<>();

    private BoostData getBoostData(Player player) {
        BoostData boostData = boostDataMap.get(player.getUniqueId());
        if (boostData == null) {
            boostData = new BoostData(100, 5, 5, 10, null);
            boostDataMap.put(player.getUniqueId(), boostData);
        }
        return boostData;
    }

    private CivScoreBoard staminaBoard;

    public CivScoreBoard getStaminaBoard() {
        if (staminaBoard == null) {
            staminaBoard = ScoreBoardAPI.createBoard("pearlCooldown");
            staminaBoard.updatePeriodically(getStaminaBiFunction(), 1L);
        }
        return staminaBoard;
    }

    public BiFunction<Player, String, String> getStaminaBiFunction() {
        return (player, oldText) -> {
            if (!player.isGliding()) {
                return null;
            }

            return getStaminaText(player);
        };
    }

    public String getStaminaText(Player player) {
        BoostData boostData = boostDataMap.get(player.getUniqueId());

        return ChatColor.GOLD + "" + ChatColor.BOLD +
                "Stamina: " + ChatColor.YELLOW + ((int) boostData.getStamina()) +
                ChatColor.GOLD  + "/" + ChatColor.YELLOW + ((int) boostData.getMaxStamina());
    }

    public ElytraListener() {
        Bukkit.getScheduler().runTaskTimer(Evergreen.getInstance(), () -> {
            for (Map.Entry<UUID, BoostData> boostDataEntry : boostDataMap.entrySet()) {
                BoostData boostData = boostDataEntry.getValue();
                if (!boostData.isMaxStamina()) {
                    boostData.regainStamina();

                    Player player = Bukkit.getPlayer(boostDataEntry.getKey());
                    if (player != null && player.isOnline()) {
                        boostData.printStamina(player);
                    }
                }
            }
        }, 0L, 20L);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        boostDataMap.remove(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onPlayerSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        if (player.isGliding() && !beenHit.containsKey(player.getUniqueId())) {
            if (event.isSneaking()) {
                BoostData boostData = getBoostData(player);
                BukkitRunnable boostTask = new BukkitRunnable() {

                    @Override
                    public void run() {
                        if (!player.isSneaking() || !boostData.hasStamina()) {
                            cancel();
                            return;
                        }
                        Location loc = player.getLocation();
                        player.setVelocity(player.getEyeLocation().getDirection().multiply(1.07));
                        for(int i = 0; i < 3; i++)
                        {
                            loc.getWorld().spawnParticle(Particle.CLOUD, loc.clone().add(getRandomVector()),
                                    1, 0, 0, 0, 0.1f, null, true);
                        }
                        boostData.consumeStamina();
                        boostData.printStamina(player);
                    }

                };
                boostTask.runTaskTimer(Evergreen.getInstance(), 0L, 1L);
                boostData.setBoostTask(boostTask);
                boostDataMap.put(player.getUniqueId(), boostData);
            }
        }
    }

    public static void hitDown(LivingEntity victim) {
        BukkitRunnable downTask = getDownTask(victim);

        if (victim instanceof Player) {
            Player player = (Player) victim;
            player.sendMessage("May day, may day! You've been hit by an anti-air missile, and we're going down!");
            if (boostDataMap.containsKey(player.getUniqueId())) {
                BukkitRunnable boostTask = boostDataMap.remove(player.getUniqueId()).getBoostTask();
                if (boostTask != null) boostTask.cancel();
            }
            beenHit.put(player.getUniqueId(), downTask);
        }

        downTask.runTaskTimer(Evergreen.getInstance(), 0L, 1L);
    }

//    @EventHandler
//    public void onPlayerInteract(PlayerInteractEvent event) {
//        Player player = event.getPlayer();
//        if (player.isGliding()) {
//            if (event.getAction() == Action.LEFT_CLICK_AIR) {
//                player.sendMessage("May day, may day! You've been hit by an anti-air missile!");
//                if (boostDataMap.containsKey(player.getUniqueId())) {
//                    BukkitRunnable boostTask = boostDataMap.remove(player.getUniqueId()).getBoostTask();
//                    if (boostTask != null) boostTask.cancel();
//                }
//
//                BukkitRunnable downTask = getDownTask(player);
//                downTask.runTaskTimer(Evergreen.getInstance(), 0L, 1L);
//                beenHit.put(player.getUniqueId(), downTask);
//            }
//        }
//    }

    @NotNull
    private static BukkitRunnable getDownTask(LivingEntity player) {
        Vector down = getRandomVector();
        down.setY(-1);
        BukkitRunnable downTask = new BukkitRunnable() {
            long start = System.currentTimeMillis();
            @Override
            public void run() {
                if ((player.getLocation().clone().subtract(0, 1, 0).getBlock().isCollidable()) ||
                        (player.getVelocity().clone().setY(0).length() < 0.1) ||
                        (System.currentTimeMillis() - start > 5000)) {
                    beenHit.remove(player.getUniqueId());
                    cancel();
                }

                player.setVelocity(down);
            }
        };
        return downTask;
    }

}
