package me.hektortm.wosCore.listeners;

import me.hektortm.wosCore.database.PlayerdataDAO;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Objects;

public class JoinListener implements Listener {

    private final PlayerdataDAO dao;

    public JoinListener(PlayerdataDAO dao) {
        this.dao = dao;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if(!player.hasPlayedBefore()) {
            dao.addPlayer(player);
        }
        if (!dao.isInDatabase(player)) {
            dao.addPlayer(player);
        }
        if (!Objects.equals(dao.getLastKnownName(player), player.getName())) {
            dao.updateUsername(player);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        dao.updateLastOnline(player);
    }

}
