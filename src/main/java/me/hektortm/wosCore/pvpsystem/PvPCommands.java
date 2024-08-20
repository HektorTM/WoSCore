package me.hektortm.wosCore.pvpsystem;

import me.hektortm.wosCore.LangManager;
import me.hektortm.wosCore.utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static me.hektortm.wosCore.utils.errorUnknownPvP;

@SuppressWarnings({"SpellCheckingInspection"})
public class PvPCommands implements CommandExecutor {
    private final PvPManager pvpManager;
    private final LangManager lang;

    public PvPCommands(PvPManager pvpManager, LangManager lang) {
        this.pvpManager = pvpManager;
        this.lang = lang;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("pvp")) {
            if (sender instanceof Player p) {
                if (args.length == 0) {
                    boolean newStatus = pvpManager.togglePvP(p);
                    utils.successMsg1Value(p, "pvp.combat-toggle", "%status%", newStatus ? "§aenabled" : "§cdisabled");
                    return true;
                }

                String subCommand = args[0].toLowerCase();

                switch (subCommand) {
                    case "toggle":
                        boolean newStatus = pvpManager.togglePvP(p);
                        utils.successMsg1Value(p, "pvp.combat-toggle", "%status%", newStatus ? "§aenabled" : "§cdisabled");
                        break;

                    case "status":
                        boolean isPvPEnabled = pvpManager.pvpEnabled(p);
                        utils.successMsg1Value(p, "pvp.status", "%status%", isPvPEnabled ? "§aenabled" : "§cdisabled");
                        break;

                    case "help":
                        utils.successMsg(p, "pvp.help-header");
                        p.sendMessage(lang.getMessage("pvp.help-toggle"));
                        p.sendMessage(lang.getMessage("pvp.help-status"));
                        p.sendMessage(lang.getMessage("pvp.help-list"));
                        break;

                    default:
                        utils.error(p, errorUnknownPvP);
                        break;
                }
            }
        }
        return true;
    }
}
