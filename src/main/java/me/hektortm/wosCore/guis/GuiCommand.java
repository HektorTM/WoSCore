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
                if (args.length != 4) {
                    Utils.error(sender, errorNoArgs);
                    return true;
                }
                Player player = (Player) sender;
                String guiId = args[1];
                String guiTitle = args[2];
                int rows = Integer.parseInt(args[3]);
                guiManager.createGui(player, guiId, guiTitle, rows);
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
                String editId = args[1];
                guiManager.openGuiEditor(editingPlayer, editId);
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
                String deleteId = args[1];
                guiManager.deleteGui(deletingPlayer, deleteId);
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
                String id = args[0];
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

                guiManager.openGui(targetPlayer, id);
                break;

        }

        return true;
    }
}