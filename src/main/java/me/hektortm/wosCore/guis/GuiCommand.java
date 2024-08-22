package me.hektortm.wosCore.guis;

import me.hektortm.wosCore.LangManager;
import me.hektortm.wosCore.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static me.hektortm.wosCore.Utils.*;

public class GuiCommand implements CommandExecutor {
    private final GuiManager guiManager;
    private final LangManager lang;

    public GuiCommand(GuiManager guiManager, LangManager lang) {
        this.guiManager = guiManager;
        this.lang = lang;
    }

    // TODO: message.yml
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length == 0) {
            Utils.error(sender, errorGuiUnknown);
            return true;
        }

        String subCommand = args[0].toLowerCase();

        switch (subCommand) {
            case "create":
                if (!(sender instanceof Player)) {
                    sender.sendMessage("This command can only be used by players.");
                    return true;
                }
                if (args.length != 3) {
                    Utils.error(sender, errorNoArgs);
                    return true;
                }
                Player player = (Player) sender;
                String createName = args[1];
                int rows = Integer.parseInt(args[2]);
                guiManager.createGui(player, createName, rows);
                break;

            case "edit":
                if (!(sender instanceof Player)) {
                    sender.sendMessage("This command can only be used by players.");
                    return true;
                }
                if (args.length != 2) {
                    Utils.error(sender, errorNoArgs);
                    return true;
                }
                Player editingPlayer = (Player) sender;
                String editName = args[1];
                guiManager.openGuiEditor(editingPlayer, editName);
                break;

            case "delete":
                if (!(sender instanceof Player)) {
                    sender.sendMessage("This command can only be used by players.");
                    return true;
                }
                if (args.length != 2) {
                    Utils.error(sender, errorNoArgs);
                    return true;
                }
                Player deletingPlayer = (Player) sender;
                String deleteName = args[1];
                guiManager.deleteGui(deletingPlayer, deleteName);
                break;
            case "help":
                Utils.successMsg(sender, "guis", "gui.help-header");
                sender.sendMessage(lang.getMessage("guis","gui.help-default"));
                sender.sendMessage(lang.getMessage("guis","gui.help-create"));
                sender.sendMessage(lang.getMessage("guis","gui.help-edit"));
                sender.sendMessage(lang.getMessage("guis","gui.help-delete"));
                sender.sendMessage(lang.getMessage("guis","gui.help-list"));
                break;
            default:
                // Handle the /gui <name> [PLAYER] command
                String guiName = args[0];
                Player targetPlayer;

                if (args.length == 2) {
                    targetPlayer = Bukkit.getPlayer(args[1]);
                    if (targetPlayer == null) {
                        Utils.error(sender, errorOnline);
                        return true;
                    }
                } else {
                    if (!(sender instanceof Player)) {
                        sender.sendMessage("§cConsole must specify a player to open the GUI for.");
                        return true;
                    }
                    targetPlayer = (Player) sender;
                }

                guiManager.openGui(targetPlayer, guiName);
                break;
        }

        return true;
    }
}