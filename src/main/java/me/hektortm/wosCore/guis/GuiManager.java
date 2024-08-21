package me.hektortm.wosCore.guis;

import com.google.gson.Gson;
import me.hektortm.wosCore.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.metadata.FixedMetadataValue;

import java.io.*;
import java.util.*;

import static me.hektortm.wosCore.Utils.*;

public class GuiManager {
    private final JavaPlugin plugin;

    public GuiManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void createGui(Player p, String guiName, int rows) {
        File file = new File(plugin.getDataFolder(), "guis/" + guiName + ".json");
        if (file.exists()) {
            Utils.error(p, errorGuiExists);
            return;
        }

        Map<String, Object> guiData = new HashMap<>();
        guiData.put("title", guiName);
        guiData.put("rows", rows);
        guiData.put("items", new ArrayList<>());

        saveGuiData(file, guiData);
        Utils.successMsg1Value(p, "gui.created", "%GUIname%", guiName);
    }

    public void openGui(Player p, String guiName) {
        File file = new File(plugin.getDataFolder(), "guis/" + guiName + ".json");
        if (!file.exists()) {
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
                if (itemData.containsKey("name")) {
                    meta.setDisplayName((String) itemData.get("name"));
                }
                if (itemData.containsKey("lore")) {
                    meta.setLore((List<String>) itemData.get("lore"));
                }
                item.setItemMeta(meta);

                gui.setItem(slot, item);
            }
            p.openInventory(gui);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openGuiEditor(Player p, String guiName) {
        File file = new File(plugin.getDataFolder(), "guis/" + guiName + ".json");
        if (!file.exists()) {
            Utils.error(p, errorGuiNotFound);
            return;
        }

        try (FileReader reader = new FileReader(file)) {
            Map<String, Object> guiData = new Gson().fromJson(reader, Map.class);
            int rows = ((Double) guiData.get("rows")).intValue();

            Inventory editor = Bukkit.createInventory(p, rows * 9, "Editing: " + guiName);

            List<Map<String, Object>> items = (List<Map<String, Object>>) guiData.get("items");
            for (Map<String, Object> itemData : items) {
                int slot = ((Double) itemData.get("slot")).intValue();
                Material material = Material.getMaterial((String) itemData.get("material"));
                ItemStack item = new ItemStack(material);
                ItemMeta meta = item.getItemMeta();
                if (itemData.containsKey("name")) {
                    meta.setDisplayName((String) itemData.get("name"));
                }
                if (itemData.containsKey("lore")) {
                    meta.setLore((List<String>) itemData.get("lore"));
                }
                item.setItemMeta(meta);

                editor.setItem(slot, item);
            }

            p.openInventory(editor);
            // Store title in metadata for later use
            p.setMetadata("guiTitle", new FixedMetadataValue(plugin, "Editing: " + guiName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteGui(Player p, String guiName) {
        File file = new File(plugin.getDataFolder(), "guis/" + guiName + ".json");
        if (!file.exists()) {
            Utils.error(p, errorGuiNotFound);
            return;
        }

        if (file.delete()) {
            Utils.successMsg1Value(p, "gui.delted", "%GUIname%", guiName);
        } else {
            Utils.error(p, errorGuiDelete);
        }
    }
    @EventHandler
    public void handleInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getInventory();

        String title = player.getMetadata("guiTitle").isEmpty() ? "" : player.getMetadata("guiTitle").get(0).asString();

        if (title.startsWith("Editing: ")) {
            // Allow modifications in editor
            event.setCancelled(false);
        } else {
            // Prevent item movement in regular GUIs
            event.setCancelled(true);
        }
    }

    public void saveGuiData(File file, Map<String, Object> guiData) {
        try (FileWriter writer = new FileWriter(file)) {
            new Gson().toJson(guiData, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveGuiLayout(Player p, Inventory inventory) {
        String title = p.getMetadata("guiTitle").isEmpty() ? "" : p.getMetadata("guiTitle").get(0).asString();
        String guiName = title.replace("Editing: ", "");

        Map<String, Object> guiData = new HashMap<>();
        guiData.put("title", guiName);
        guiData.put("rows", inventory.getSize() / 9);

        List<Map<String, Object>> items = new ArrayList<>();
        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack item = inventory.getItem(i);
            if (item != null) {
                Map<String, Object> itemData = new HashMap<>();
                itemData.put("slot", i);
                itemData.put("material", item.getType().toString());
                ItemMeta meta = item.getItemMeta();
                if (meta.hasDisplayName()) {
                    itemData.put("name", meta.getDisplayName());
                }
                if (meta.hasLore()) {
                    itemData.put("lore", meta.getLore());
                }
                items.add(itemData);
            }
        }
        guiData.put("items", items);

        File file = new File(plugin.getDataFolder(), "guis/" + guiName + ".json");
        saveGuiData(file, guiData);
        Utils.successMsg1Value(p, "gui.saved", "%GUIname%", guiName);
    }

    public void linkCommandToItem(Player p, Inventory inventory, int slot, String command) {
        String title = p.getMetadata("guiTitle").isEmpty() ? "" : p.getMetadata("guiTitle").get(0).asString();
        String guiName = title.replace("Editing: ", "");
        File file = new File(plugin.getDataFolder(), "guis/" + guiName + ".json");

        try (FileReader reader = new FileReader(file)) {
            Map<String, Object> guiData = new Gson().fromJson(reader, Map.class);
            List<Map<String, Object>> items = (List<Map<String, Object>>) guiData.get("items");

            for (Map<String, Object> itemData : items) {
                int itemSlot = ((Double) itemData.get("slot")).intValue();
                if (itemSlot == slot) {
                    itemData.put("action", command);
                    break;
                }
            }

            saveGuiData(file, guiData);
            String slotVal = String.valueOf(slot);
            Utils.successMsg2Values(p, "gui.linked", "%cmd%", command, "%slot%", slotVal);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }
    public String getItemAction(Player player, Inventory inventory, int slot) {
        String title = player.getMetadata("guiTitle").isEmpty() ? "" : player.getMetadata("guiTitle").get(0).asString();
        String guiName = title.replace("Editing: ", "");
        File file = new File(plugin.getDataFolder(), "guis/" + guiName + ".json");

        try (FileReader reader = new FileReader(file)) {
            Map<String, Object> guiData = new Gson().fromJson(reader, Map.class);
            List<Map<String, Object>> items = (List<Map<String, Object>>) guiData.get("items");

            for (Map<String, Object> itemData : items) {
                int itemSlot = ((Double) itemData.get("slot")).intValue();
                if (itemSlot == slot) {
                    // Return the action associated with the item
                    return (String) itemData.get("action");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    public List<String> getAllGuiFilenames() {
        File guiFolder = new File(plugin.getDataFolder(), "guis");
        List<String> guiFiles = new ArrayList<>();

        if (guiFolder.exists() && guiFolder.isDirectory()) {
            File[] files = guiFolder.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.endsWith(".json");
                }
            });

            if (files != null) {
                for (File file : files) {
                    guiFiles.add(file.getName().replace(".json", ""));
                }
            }
        }

        return guiFiles;
    }



}
