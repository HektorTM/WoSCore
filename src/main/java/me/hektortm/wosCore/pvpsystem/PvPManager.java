package me.hektortm.wosCore.pvpsystem;


import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class PvPManager {
    private final HashMap<UUID, Boolean> pvpStatus = new HashMap<>();
    private final File dataFile;
    private final Gson gson;
    private final Set<UUID> pvpEnabledPlayers;

    public PvPManager(File dataFolder) {
        this.dataFile = new File(dataFolder, "pvp_status.json");
        this.pvpEnabledPlayers = new HashSet<>();
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        loadStatus();
    }

    public boolean togglePvP(Player player) {
        UUID playerId = player.getUniqueId();
        if (pvpEnabledPlayers.contains(playerId)) {
            pvpEnabledPlayers.remove(playerId);
            updatePlayerTabName(player, false);
            return false;
        } else {
            pvpEnabledPlayers.add(playerId);
            updatePlayerTabName(player, true);
            return true;
        }
    }

    public boolean pvpEnabled(Player player) {
        return pvpEnabledPlayers.contains(player.getUniqueId());
    }



    public void loadStatus() {
        if (dataFile.exists()) {
            try (FileReader reader = new FileReader(dataFile)) {
                Type type = new TypeToken<Set<UUID>>() {}.getType();
                Set<UUID> loadedData = gson.fromJson(reader, type);
                if (loadedData != null) {
                    pvpEnabledPlayers.addAll(loadedData);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveStatus() {
        try (FileWriter writer = new FileWriter(dataFile)) {
            gson.toJson(pvpEnabledPlayers, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void updatePlayerTabName(Player player, boolean pvpEnabled) {
        String newName = pvpEnabled ? ChatColor.RED + "⚔ " + player.getName() : ChatColor.GRAY + player.getName();
        player.setPlayerListName(newName);
    }
}
