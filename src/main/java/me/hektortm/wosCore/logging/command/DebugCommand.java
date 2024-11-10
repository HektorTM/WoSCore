package me.hektortm.wosCore.logging.command;

import me.hektortm.woSSystems.systems.unlockables.UnlockableManager;
import me.hektortm.wosCore.LangManager;
import me.hektortm.wosCore.Utils;
import me.hektortm.wosCore.WoSCore;
import me.hektortm.wosCore.logging.LogManager;
import me.hektortm.wosCore.logging.command.subcommands.HelpCommand;
import me.hektortm.wosCore.logging.command.subcommands.ViewCommand;
import me.hektortm.wosCore.logging.command.subcommands.WarningsCommand;
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

    private final Map<String, SubCommand> logSubCommands = new HashMap<>(); // Secondary subcommands for 'log'
    private final Map<String, SubCommand> subCommands = new HashMap<>(); // Primary subcommands
    private final WoSCore plugin;
    private final LogManager logManager;
    private final LangManager lang;

    public DebugCommand(LogManager logManager, LangManager lang, WoSCore plugin) {
        this.logManager = logManager;
        this.lang = lang;
        this.plugin = plugin;

        // Add subcommands directly
        subCommands.put("warnings", new WarningsCommand(plugin, lang));
        subCommands.put("help", new HelpCommand(this));

        // Add log-specific subcommands directly in the logSubCommands map
        logSubCommands.put("view", new ViewCommand(logManager, lang));
        logSubCommands.put("write", new WriteCommand(logManager));
    }

    // TODO: help cmd, lang file
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 0) {
            debugHelp(sender);
            return true;
        }

        String primaryCommand = args[0].toLowerCase();

        // Check if it's the "log" primary command
        if ("log".equals(primaryCommand)) {
            if (args.length < 2) {
                Utils.error(sender, "debug", "error.usage.log");
                return true;
            }

            // Handle "log" subcommands (view, write)
            String logSubCommandName = args[1].toLowerCase();
            SubCommand logSubCommand = logSubCommands.get(logSubCommandName);
            if (logSubCommand != null) {
                logSubCommand.execute(sender, java.util.Arrays.copyOfRange(args, 2, args.length));
            } else {
                Utils.error(sender, "debug", "error.usage.log");
            }
            return true;
        }

        // Handle other primary subcommands (e.g., "warnings")
        SubCommand subCommand = subCommands.get(primaryCommand);
        if (subCommand != null) {
            subCommand.execute(sender, java.util.Arrays.copyOfRange(args, 1, args.length));
        } else {
            debugHelp(sender);
        }

        return true;
    }


    public void debugHelp(CommandSender sender) {
        if (PermUtil.hasAnyPermission(sender, Permissions.LOG_VIEW, Permissions.LOG_WRITE, Permissions.WARNING_TOGGLE)) {
            Utils.successMsg(sender, "debug", "help.header");
            if (PermUtil.hasPermissionNoMsg(sender, Permissions.LOG_VIEW)) sender.sendMessage(lang.getMessage("debug", "help.view"));
            if (PermUtil.hasPermissionNoMsg(sender, Permissions.LOG_WRITE)) sender.sendMessage(lang.getMessage("debug", "help.write"));
            if(PermUtil.hasPermissionNoMsg(sender, Permissions.WARNING_TOGGLE)) sender.sendMessage(lang.getMessage("debug", "help.warnings"));
            sender.sendMessage(lang.getMessage("debug", "help.help"));
        } else {
            Utils.error(sender, "general", "error.perms");
        }
    }
}
