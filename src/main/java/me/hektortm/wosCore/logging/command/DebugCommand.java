package me.hektortm.wosCore.logging.command;

import me.hektortm.wosCore.LangManager;
import me.hektortm.wosCore.Utils;
import me.hektortm.wosCore.WoSCore;
import me.hektortm.wosCore.logging.LogManager;
import me.hektortm.wosCore.util.PermUtil;
import me.hektortm.wosCore.util.Permissions;
import me.hektortm.wosCore.util.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class DebugCommand implements CommandExecutor {

    private final WoSCore plugin;
    private final LogManager logManager;
    private final LangManager lang;

    public DebugCommand(LogManager logManager, LangManager lang, WoSCore plugin) {
        this.logManager = logManager;
        this.lang = lang;
        this.plugin = plugin;
    }

    // TODO: help cmd, lang file
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!PermUtil.hasPermission(sender, Permissions.LOG_WRITE)) return true;

        if (args.length == 0) {
            Utils.error(sender, "debug", "error.usage.write");
            return true;
        }
        StringBuilder b = new StringBuilder();
        for(int l = 0; l < args.length; l++) {
            b.append(args[l]+" ");
        }
        String log = b.toString();
        Player p = (Player) sender;

        logManager.writeLog(p, log);
        Utils.successMsg(sender, "debug", "log.sent");
        return true;
    }
}
