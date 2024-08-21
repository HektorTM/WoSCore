package me.hektortm.wosCore.guis;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.metadata.FixedMetadataValue;

public class ChatListener implements Listener {
    private final GuiManager guiManager;

    public ChatListener(GuiManager guiManager) {
        this.guiManager = guiManager;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (player.hasMetadata("linkingSlot")) {
            int slot = player.getMetadata("linkingSlot").get(0).asInt();
            String command = event.getMessage();
            event.setCancelled(true);

            Inventory inventory = player.getOpenInventory().getTopInventory();
            guiManager.linkCommandToItem(player, inventory, slot, command);

            player.removeMetadata("linkingSlot", guiManager.getPlugin());
        }
    }

}
