package me.hektortm.wosCore.guis;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;

public class ChatListener implements Listener {
    private final GuiManager guiManager;

    public ChatListener(GuiManager guiManager) {
        this.guiManager = guiManager;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();

        if (p.hasMetadata("linkingSlot")) {
            int slot = p.getMetadata("linkingSlot").get(0).asInt();
            String command = e.getMessage();
            e.setCancelled(true);

            Inventory inventory = p.getOpenInventory().getTopInventory();
            guiManager.linkCommandToItem(p, inventory, slot, command);

            p.removeMetadata("linkingSlot", guiManager.getPlugin());
        }
    }

}
