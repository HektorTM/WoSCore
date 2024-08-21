package me.hektortm.wosCore.guis.editor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class GuiEditorCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (sender instanceof Player p) {

            if (args.length >= 2 && args[0].equalsIgnoreCase("create")) {
                String guiName = args[1];
                int rows = args.length > 2 ? Integer.parseInt(args[2]) : 3; // Default to 3 rows
                createNewGui(p, guiName, rows);
            } else if (args.length >= 1 && args[0].equalsIgnoreCase("edit")) {
                String guiName = args[1];
                openGuiEditor(p, guiName);
            } else {
                p.sendMessage("§cUsage: /editgui create <name> <rows>");
                p.sendMessage("§cUsage: /editgui edit <name>");
            }
        }
        return true;
    }

    public void createNewGui(Player player, String guiName, int rows) {
        Inventory editor = org.bukkit.Bukkit.createInventory(player, rows * 9, "Editing: " + guiName);
        player.openInventory(editor);
        player.sendMessage("§aCreated a new GUI: " + guiName + " with " + rows + " rows.");
    }

    public void openGuiEditor(Player player, String guiName) {
        Inventory editor = org.bukkit.Bukkit.createInventory(player, 27, "Editing: " + guiName);
        player.openInventory(editor);
    }
}
