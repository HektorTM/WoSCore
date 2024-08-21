package me.hektortm.wosCore.guis;

import com.google.gson.Gson;
import me.hektortm.wosCore.Utils;
import me.hektortm.wosCore.WoSCore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Map;

import static me.hektortm.wosCore.Utils.*;

public class GuiManager {
    private final JavaPlugin plugin;

    public GuiManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void openGui(Player p, String guiName) {
        File file = new File(plugin.getDataFolder(), guiName + ".json");
        if(!file.exists()) {
            Utils.error(p, errorGuiNotFound);
            return;
        }

        try (FileReader reader = new FileReader(file)) {
            Map<String, Object> guiData = new Gson().fromJson(reader, Map.class);
            String title = (String) guiData.get("title");
            int rows = ((Double) guiData.get("rows")).intValue();

            Inventory gui = Bukkit.createInventory(null, rows * 9, title);

            List<Map<String, Object>> items = (List<Map<String, Object>>) guiData.get("items");
            for (Map<String, Object> itemData : items) {
                int slot = ((Double) itemData.get("slot")).intValue();
                Material material = Material.getMaterial((String) itemData.get("material"));
                ItemStack item = new ItemStack(material);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName((String) itemData.get("name"));
                meta.setLore((List<String>) itemData.get("lore"));
                item.setItemMeta(meta);

                gui.setItem(slot, item);
            }
            p.openInventory(gui);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void handleInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

        String guiName = event.getView().getTitle();

        File file = new File(plugin.getDataFolder(), guiName + ".json");
        if (!file.exists()) return;

        try (FileReader reader = new FileReader(file)) {
            Map<String, Object> guiData = new Gson().fromJson(reader, Map.class);
            List<Map<String, Object>> items = (List<Map<String, Object>>) guiData.get("items");

            for (Map<String, Object> itemData : items) {
                int slot = ((Double) itemData.get("slot")).intValue();
                if (slot == event.getSlot()) {
                    String action = (String) itemData.get("action");
                    performAction(player, action);
                    event.setCancelled(true);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void performAction(Player player, String action) {
        String[] parts = action.split(" ");
        switch (parts[0]) {
            case "give_item":
                Material material = Material.getMaterial(parts[1]);
                int amount = Integer.parseInt(parts[2]);
                player.getInventory().addItem(new ItemStack(material, amount));
                player.sendMessage("§aYou received " + amount + " " + parts[1].toLowerCase() + "(s)!");
                break;

            case "give_money":
                int money = Integer.parseInt(parts[1]);
                // Assume you have a method to give money to the player
                // Economy.addMoney(player, money);
                player.sendMessage("§aYou received " + money + " coins!");
                break;

            // Add more actions as needed

            default:
                player.sendMessage("§cUnknown action: " + action);
                break;
        }
    }
}
