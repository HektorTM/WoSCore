package me.hektortm.wosCore.guis;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GuiCommand implements CommandExecutor {
    private final GuiManager guiManager;

    public GuiCommand(GuiManager guiManager) {
        this.guiManager = guiManager;
    }

    // TODO: message.yml
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        if (args.length == 0) {
            player.sendMessage("§cPlease specify a subcommand: <name>, create, edit, delete");
            return true;
        }

        String subCommand = args[0].toLowerCase();

        switch (subCommand) {
            case "create":
                if (args.length != 3) {
                    player.sendMessage("§cUsage: /gui create <name> <rows>");
                    return true;
                }
                String createName = args[1];
                int rows = Integer.parseInt(args[2]);
                guiManager.createGui(player, createName, rows);
                break;

            case "edit":
                if (args.length != 2) {
                    player.sendMessage("§cUsage: /gui edit <name>");
                    return true;
                }
                String editName = args[1];
                guiManager.openGuiEditor(player, editName);
                break;

            case "delete":
                if (args.length != 2) {
                    player.sendMessage("§cUsage: /gui delete <name>");
                    return true;
                }
                String deleteName = args[1];
                guiManager.deleteGui(player, deleteName);
                break;

            default:
                String guiName = args[0];
                guiManager.openGui(player, guiName);
                break;
        }

        return true;
    }
}