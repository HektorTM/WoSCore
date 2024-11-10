package me.hektortm.wosCore.logging.command.subcommands;

import me.hektortm.woSSystems.systems.unlockables.UnlockableManager;
import me.hektortm.woSSystems.systems.unlockables.utils.Action;
import me.hektortm.wosCore.LangManager;
import me.hektortm.wosCore.Utils;
import me.hektortm.wosCore.WoSCore;
import me.hektortm.wosCore.logging.command.SubCommand;
import me.hektortm.wosCore.util.PermUtil;
import me.hektortm.wosCore.util.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarningsCommand extends SubCommand {

    private final WoSCore plugin;
    private final LangManager lang;

    public WarningsCommand(WoSCore plugin, LangManager lang) {
        this.plugin = plugin;
        this.lang = lang;

    }

    @Override
    public String getName() {
        return "warnings";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!PermUtil.hasPermission(sender, Permissions.WARNING_TOGGLE)) return;
        Player p = (Player) sender;

        if (args.length == 0) {
            if (!plugin.checkUnlockable(p.getUniqueId(), "core_warnings")) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"unlockable give "+p.getName()+" core_warnings");
                Utils.successMsg1Value(p, "debug", "warnings.status-change", "%status%", lang.getMessage("debug", "var.enabled"));
            } else {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"unlockable take "+p.getName()+" core_warnings");
                Utils.successMsg1Value(p, "debug", "warnings.status-change", "%status%", lang.getMessage("debug", "var.disabled"));
            }
        }

        String subCmd = args[0].toLowerCase();



        // TODO change this to not use console command
        switch(subCmd) {
            case "status":
                if (plugin.checkUnlockable(p.getUniqueId(), "core_warnings")) {
                    Utils.successMsg1Value(p, "debug", "warnings.status", "%status%", lang.getMessage("debug", "var.enabled"));
                } else {
                    Utils.successMsg1Value(p, "debug", "warnings.status", "%status%", lang.getMessage("debug", "var.disabled"));
                }
                break;
            case "on":
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "unlockable give "+p.getName()+" core_warnings");
                Utils.successMsg(p, "debug", "warnings.active");
                break;
            case "off":
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "unlockable take "+p.getName()+" core_warnings");
                Utils.successMsg(p, "debug", "warnings.not-active");
                break;
            default:
                Utils.error(p, "debug", "error.usage.warnings");
        }

    }
}
