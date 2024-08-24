package me.hektortm.wosCore.pvpsystem;

import me.hektortm.wosCore.LangManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;

@SuppressWarnings("SpellCheckingInspection")
public class PvPListeners implements Listener {
    private final PvPManager pvpManager;
    private final LangManager lang;

    public PvPListeners(PvPManager pvpManager, LangManager lang) {
        this.pvpManager = pvpManager;
        this.lang = lang;
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player victim && event.getDamager() instanceof Player attacker) {
            boolean victimPvPEnabled = pvpManager.pvpEnabled(victim);
            boolean attackerPvPEnabled = pvpManager.pvpEnabled(attacker);

            if (!victimPvPEnabled && !attackerPvPEnabled) {
                event.setCancelled(true);
                attacker.sendActionBar(lang.getMessage("pvp", "actionbar.victim-notenabled").replace("%victim%", victim.toString()));
            } else if (!attackerPvPEnabled) {
                event.setCancelled(true);
                attacker.sendActionBar(lang.getMessage("pvp", "actionbar.notenabled"));
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
