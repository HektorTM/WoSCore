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

        // Ensure the click is a valid interaction
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getInventory();

        // Get the title from the InventoryView
        String title = event.getView().getTitle();

        // Check if the inventory is being edited
        if (title.startsWith("Editing: ")) {
            // If the click is a shift-click, allow it to trigger command linking
            if (event.getClick().isShiftClick()) {
                event.setCancelled(true);
                player.closeInventory();
                player.sendMessage("§aPlease type the command to link to the item in slot " + event.getSlot() + ".");
                player.sendMessage("§7(Without the leading /)");

                // Save the slot in the player's metadata for tracking
                player.setMetadata("linkingSlot", new FixedMetadataValue(guiManager.getPlugin(), event.getSlot()));
                return;
            } else {
                event.setCancelled(false);
            }

            // Prevent item taking (this handles other clicks that are not shift-clicks)


            // Check if the item in the clicked slot has an action associated with it


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
