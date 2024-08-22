package me.hektortm.wosCore.guis;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

public class GuiListener implements Listener {
    private final GuiManager guiManager;

    public GuiListener(GuiManager guiManager) {
        this.guiManager = guiManager;
    }


    // TODO: message.yml
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        guiManager.handleInventoryClick(event);

        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }



        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getInventory();

        String title = event.getView().getTitle();
        String guiId = player.getMetadata("guiId").isEmpty() ? "" : player.getMetadata("guiId").get(0).asString();
        if (guiManager.guiExists(guiId+".yml")) {
            if (title.startsWith("Editing: ")) {
                if (event.getClick().isShiftClick()) {
                    event.setCancelled(true);
                    player.closeInventory();
                    player.sendMessage("§aPlease type the command to link to the item in slot " + event.getSlot() + ".");
                    player.sendMessage("§7(Without the leading /)");

                    player.setMetadata("linkingSlot", new FixedMetadataValue(guiManager.getPlugin(), event.getSlot()));
                    return;
                } else {
                    event.setCancelled(false);
                }
            } else {
                ItemStack clickedItem = event.getCurrentItem();
                if (clickedItem != null && clickedItem.getType() != Material.AIR) {
                    ItemMeta meta = clickedItem.getItemMeta();
                    int slot = event.getSlot();
                    String action = guiManager.getItemAction(player, inventory, slot);

                    if (action != null && !action.isEmpty()) {
                        executeAction(player, action);
                    }
                    event.setCancelled(true);
                }
            }
        } else {
            event.setCancelled(false);
        }

    }


    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();

        String title = event.getView().getTitle(); // Get the title from the InventoryView

        if (title.startsWith("Editing: ")) {
            Player player = (Player) event.getPlayer();
            guiManager.saveGuiLayout(player, inventory);
        }
    }
    private void executeAction(Player player, String action) {
        // Trim the action to remove any leading or trailing whitespace
        action = action.trim();

        // Example action execution logic
        if (!action.isEmpty()) {
            // Dispatch the command as if the player typed it in
            Bukkit.dispatchCommand(player, action);
        } else {
            player.sendMessage("§cNo action defined for this item.");
        }
    }
}
