package me.hektortm.wosCore.logging.command.subcommands;

import me.hektortm.wosCore.LangManager;
import me.hektortm.wosCore.Utils;
import me.hektortm.wosCore.logging.LogManager;
import me.hektortm.wosCore.logging.command.SubCommand;
import me.hektortm.wosCore.util.PermUtil;
import me.hektortm.wosCore.util.Permissions;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ViewCommand extends SubCommand {

    private final LogManager logManager;
    private final LangManager lang;

    public ViewCommand(LogManager logManager, LangManager lang) {
        this.logManager = logManager;
        this.lang = lang;
    }

    @Override
    public String getName() {
        return "view";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (PermUtil.hasPermission(sender, Permissions.LOG_VIEW)) return;

        if (args.length == 0) {
            Utils.error(sender, "logs", "error.usage.view");
            return;
        }

        String playerName = args[0];
        int page = 1;
        if (args.length > 1) {
            try {
                page = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                Utils.error(sender, "logs", "error.invalid-page");
                return;
            }
        }

        List<String> logs = logManager.getLogsForPlayer(playerName);
        if (logs.isEmpty()) {
            String msg =lang.getMessage("general", "prefix.error") + lang.getMessage("logs", "error.no-logs").replace("%player%", playerName);
            sender.sendMessage(msg);
            return;
        }

        int logsPerPage = 50;
        int startIndex = (page - 1) * logsPerPage;
        int endIndex = Math.min(startIndex + logsPerPage, logs.size());

        if (startIndex >= logs.size()) {
            sender.sendMessage(ChatColor.RED + "No more logs to display.");
            return;
        }

        sender.sendMessage(ChatColor.GOLD + "Showing logs for player: " + playerName + " (Page " + page + ")");
        for (int i = startIndex; i < endIndex; i++) {
            sender.sendMessage(ChatColor.GRAY + logs.get(i));
        }

        int totalPages = (logs.size() + logsPerPage - 1) / logsPerPage;
        sender.sendMessage(ChatColor.GOLD + String.format("Showing %d/%d logs. Page %d/%d.",
                endIndex - startIndex, logs.size(), page, totalPages));

        if (page < totalPages) {
            sender.sendMessage(ChatColor.YELLOW + "Type /logs view " + playerName + " " + (page + 1) + " for next page.");
        }

        return;
    }
}
