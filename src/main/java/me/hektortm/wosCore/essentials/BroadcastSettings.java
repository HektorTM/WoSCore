package me.hektortm.wosCore.essentials;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BroadcastSettings {
    private static final Map<UUID, BroadcastSettings> settingsMap = new HashMap<>();

    private String title;
    private String message;
    private boolean signed;

    public static BroadcastSettings getSettings(Player p) {
        return settingsMap.computeIfAbsent(p.getUniqueId(), k -> new BroadcastSettings());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSigned() {
        return signed;
    }

    public void setSigned(boolean signed) {
        this.signed = signed;
    }

    public void clear() {
        title = null;
        message = null;
        signed = false;
    }


}
