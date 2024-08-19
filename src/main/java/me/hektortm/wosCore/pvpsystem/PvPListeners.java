package me.hektortm.wosCore.pvpsystem;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PvPListeners implements Listener {
    private final PvPManager pvpManager;

    public PvPListeners(PvPManager pvpManager) {
        this.pvpManager = pvpManager;
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player victim && event.getDamager() instanceof Player attacker) {
            boolean victimPvPEnabled = pvpManager.pvpEnabled(victim);
            boolean attackerPvPEnabled = pvpManager.pvpEnabled(attacker);

            if (!victimPvPEnabled && !attackerPvPEnabled) {
                event.setCancelled(true);
               // WIP utils.sendActionBar(attacker, "§c"+victim +"does not have combat enabled.");
            } else if (!attackerPvPEnabled) {
                event.setCancelled(true);
                // WIP utils.sendActionBar(attacker, "§cYou do not have combat enabled.");
            } else if (!victimPvPEnabled) {
                event.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        boolean pvpenabled = pvpManager.pvpEnabled(p);
        pvpManager.updatePlayerTabName(p, pvpenabled);
    }
}
