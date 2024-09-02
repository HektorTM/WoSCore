package me.hektortm.wosCore.pvpsystem;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import me.hektortm.wosCore.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class PvPManager {
    private final File dataFile;
    private final Gson gson;
    private final Set<UUID> pvpEnabledPlayers;

    public PvPManager(File dataFolder) {
        this.dataFile = new File(dataFolder, "pvp_status.json");
        this.pvpEnabledPlayers = new HashSet<>();
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        loadStatus();
    }

    // Toggle PvP status and update player tab name accordingly
    public boolean togglePvP(Player player) {
        UUID playerId = player.getUniqueId();
        boolean pvpEnabled = pvpEnabledPlayers.contains(playerId);

        if (pvpEnabled) {
            pvpEnabledPlayers.remove(playerId);
        } else {
            pvpEnabledPlayers.add(playerId);
            Utils.playSound(player, Sound.ENTITY_WITHER_SPAWN);
        }

        updatePlayerTabName(player, !pvpEnabled);
        saveStatus(); // Save status after toggling
        return !pvpEnabled;
    }

    // Check if PvP is enabled for a player
    public boolean pvpEnabled(Player player) {
        return pvpEnabledPlayers.contains(player.getUniqueId());
    }

    // Load PvP status from JSON file
    public void loadStatus() {
        if (dataFile.exists()) {
            try (FileReader reader = new FileReader(dataFile)) {
                Type type = new TypeToken<Set<UUID>>() {}.getType();
                Set<UUID> loadedData = gson.fromJson(reader, type);
                if (loadedData != null) {
                    pvpEnabledPlayers.addAll(loadedData);
                }
            } catch (IOException e) {
                //noinspection CallToPrintStackTrace
                e.printStackTrace();
            }
        }
    }

    // Save PvP status to JSON file
    public void saveStatus() {
        try (FileWriter writer = new FileWriter(dataFile)) {
            gson.toJson(pvpEnabledPlayers, writer);
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    // Update the player's name in the tab list based on PvP status
    @SuppressWarnings("deprecation")
    public void updatePlayerTabName(Player player, boolean pvpEnabled) {
        String newName = pvpEnabled ? ChatColor.RED + "⚔ " + player.getName() : ChatColor.GRAY + player.getName();
        player.setPlayerListName(newName);
    }
}
