package me.hektortm.wosCore.logging.command;

import me.hektortm.wosCore.LangManager;
import me.hektortm.wosCore.Utils;
import me.hektortm.wosCore.logging.LogManager;
import me.hektortm.wosCore.logging.command.subcommands.ViewCommand;
import me.hektortm.wosCore.logging.command.subcommands.WriteCommand;
import me.hektortm.wosCore.util.PermUtil;
import me.hektortm.wosCore.util.Permissions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class DebugCommand implements CommandExecutor {

    private final Map<String, SubCommand> subCommands = new HashMap<>();
    private final LogManager logManager;
    private final LangManager lang;


    public DebugCommand(LogManager logManager, LangManager lang) {
        this.logManager = logManager;
        this.lang = lang;

        subCommands.put("view", new ViewCommand(logManager, lang));
        subCommands.put("write", new WriteCommand(logManager));
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (args.length == 0) {
            sender.sendMessage("nope");
            return true;
        }

        String subCommandName = args[0].toLowerCase();
        SubCommand subCommand = subCommands.get(subCommandName);

        if (subCommand != null) {
            subCommand.execute(sender, java.util.Arrays.copyOfRange(args, 1, args.length));
        } else {
            logHelp(sender);
        }
        return true;
    }

    public void logHelp(CommandSender s) {
        if (PermUtil.hasAnyPermission(s, Permissions.LOG_VIEW, Permissions.LOG_WRITE)) {

        } else {
            Utils.error(s, "general", "error.perms");
        }

    }
}
