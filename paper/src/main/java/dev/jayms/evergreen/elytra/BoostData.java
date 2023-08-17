package dev.jayms.evergreen.elytra;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class BoostData {

    private double maxStamina;
    private double stamina;
    private double regainStamina;
    private double consumeStamina;
    private BukkitRunnable boostTask;

    public BoostData(double maxStamina, double stamina, double regainStamina, double consumeStamina, BukkitRunnable boostTask) {
        this.maxStamina = maxStamina;
        this.stamina = stamina;
        this.regainStamina = regainStamina;
        this.consumeStamina = consumeStamina;
        this.boostTask = boostTask;
    }

    public double getStamina() {
        return stamina;
    }

    public double getMaxStamina() {
        return maxStamina;
    }

    public boolean isMaxStamina() {
        return stamina >= maxStamina;
    }

    public boolean hasStamina() {
        return stamina > 0;
    }

    public void regainStamina() {
        stamina += regainStamina;
        if (stamina > maxStamina) {
            stamina = maxStamina;
        }
    }

    public void consumeStamina() {
        stamina -= consumeStamina;
        if (stamina < 0) {
            stamina = 0;
        }
    }

    public void printStamina(Player player) {
        player.sendMessage("Stamina: " + ((int) stamina) + "/" + ((int) maxStamina));
    }

    public BukkitRunnable getBoostTask() {
        return boostTask;
    }

    public void setBoostTask(BukkitRunnable boostTask) {
        this.boostTask = boostTask;
    }
}
