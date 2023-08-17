package dev.jayms.evergreen.storage;

import dev.jayms.evergreen.Evergreen;
import dev.jayms.evergreen.arena.Arena;
import dev.jayms.evergreen.arena.ArenaFlag;
import dev.jayms.evergreen.arena.ArenaType;
import dev.jayms.evergreen.region.Region;
import vg.civcraft.mc.civmodcore.dao.ManagedDatasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ArenaDAO {

    private ManagedDatasource managedDatasource;

    public ArenaDAO(ManagedDatasource managedDatasource) {
        this.managedDatasource = managedDatasource;

        init();
    }

    private static final String CREATE_ARENA = "CREATE TABLE IF NOT EXISTS arena(" +
            "arena_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "arena_name TEXT NOT NULL UNIQUE," +
            "arena_type TEXT NOT NULL," +
            "arena_region_x1 INTEGER NOT NULL," +
            "arena_region_y1 INTEGER NOT NULL," +
            "arena_region_z1 INTEGER NOT NULL," +
            "arena_region_x2 INTEGER NOT NULL," +
            "arena_region_y2 INTEGER NOT NULL," +
            "arena_region_z2 INTEGER NOT NULL" +
            ");";

    private static final String CREATE_ARENA_FLAG = "CREATE TABLE IF NOT EXISTS arena_flag(" +
            "arena_flag_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "arena_flag_name TEXT NOT NULL," +
            "arena_id INTEGER," +
            "FOREIGN KEY (arena_id) REFERENCES arena(arena_id)" +
            ");";

    private static final String SELECT_ARENAS = "SELECT arena_id, arena_name, arena_type, " +
            "arena_region_x1, arena_region_y1, arena_region_z1, " +
            "arena_region_x2, arena_region_y2, arena_region_z2 " +
            "FROM arena;";

    private static final String INSERT_ARENA = "INSERT OR REPLACE INTO arena (" +
            "arena_name," +
            "arena_type," +
            "arena_region_x1," +
            "arena_region_y1," +
            "arena_region_z1," +
            "arena_region_x2," +
            "arena_region_y2," +
            "arena_region_z2" +
            ") VALUES (" +
            "?, ?, ?, ?, ?, ?, ?, ?" +
            ")";

    private static final String INSERT_ARENA_FLAG = "INSERT INTO arena_flag (" +
            "arena_flag_name," +
            "arena_id" +
            ") VALUES (" +
            "?, ?" +
            ")";

    private static final String DELETE_ARENA_FLAG = "DELETE FROM arena_flag " +
            "WHERE arena_id = ?";

    private static final String SELECT_ARENA_FLAG = "SELECT arena_flag_name FROM arena_flag " +
            "WHERE arena_id = ?";

    public void init() {
        managedDatasource.registerMigration(1, false, CREATE_ARENA, CREATE_ARENA_FLAG);
    }

    public Map<String, Arena> loadArenas() {
        Map<String, Arena> arenas = new HashMap<>();

        try (Connection connection = managedDatasource.getConnection();
            PreparedStatement selectArenaStmt = connection.prepareStatement(SELECT_ARENAS)) {
            ResultSet resultSet = selectArenaStmt.executeQuery();
            while (resultSet.next()) {
                int arenaId = resultSet.getInt("arena_id");
                String arenaName = resultSet.getString("arena_name");
                String arenaTypeStr = resultSet.getString("arena_type");
                ArenaType arenaType = ArenaType.valueOf(arenaTypeStr);
                int regionX1 = resultSet.getInt("arena_region_x1");
                int regionY1 = resultSet.getInt("arena_region_y1");
                int regionZ1 = resultSet.getInt("arena_region_z1");
                int regionX2 = resultSet.getInt("arena_region_x2");
                int regionY2 = resultSet.getInt("arena_region_y2");
                int regionZ2 = resultSet.getInt("arena_region_z2");

                Set<ArenaFlag> flags = new HashSet<>();
                PreparedStatement selectFlagsStmt = connection.prepareStatement(SELECT_ARENA_FLAG);
                selectFlagsStmt.setInt(1, arenaId);
                ResultSet flagsResultSet = selectFlagsStmt.executeQuery();
                while (flagsResultSet.next()) {
                    flags.add(ArenaFlag.valueOf(flagsResultSet.getString("arena_flag_name")));
                }

                Region region = new Region();
                region.setOne(regionX1, regionY1, regionZ1);
                region.setTwo(regionX2, regionY2, regionZ2);
                Arena arena = new Arena(arenaId, arenaName, region);
                arena.setType(arenaType);

                arenas.put(arenaName, arena);
            }
        } catch (SQLException e) {
            Evergreen.getInstance().getLogger().severe("Failed to load arenas.");
            e.printStackTrace();
        }

        return arenas;
    }

    public void save(Collection<Arena> arenas) {
        try (Connection connection = managedDatasource.getConnection();
            PreparedStatement insertArenaStmt = connection.prepareStatement(INSERT_ARENA, PreparedStatement.RETURN_GENERATED_KEYS);
             PreparedStatement deleteArenaFlagStmt = connection.prepareStatement(DELETE_ARENA_FLAG);
            PreparedStatement insertArenaFlagStmt = connection.prepareStatement(INSERT_ARENA_FLAG)) {
            for (Arena arena : arenas) {
                insertArenaStmt.setString(1, arena.getName());
                insertArenaStmt.setString(2, arena.getType().toString());
                insertArenaStmt.setInt(3, arena.getRegion().getX1());
                insertArenaStmt.setInt(4, arena.getRegion().getY1());
                insertArenaStmt.setInt(5, arena.getRegion().getZ1());
                insertArenaStmt.setInt(6, arena.getRegion().getX2());
                insertArenaStmt.setInt(7, arena.getRegion().getY2());
                insertArenaStmt.setInt(8, arena.getRegion().getZ2());
                insertArenaStmt.execute();

                deleteArenaFlagStmt.setInt(1, arena.getId());
                deleteArenaFlagStmt.execute();

                for (ArenaFlag flag : arena.getFlags()) {
                    insertArenaFlagStmt.setString(1, flag.toString());
                    insertArenaFlagStmt.setInt(2, arena.getId());
                    insertArenaFlagStmt.execute();
                }
            }
        } catch (SQLException e) {
            Evergreen.getInstance().getLogger().severe("Failed to save arenas.");
            e.printStackTrace();
        }
    }

}
