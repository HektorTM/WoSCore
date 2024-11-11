package me.hektortm.wosCore.logging.command.subcommands;

import me.hektortm.wosCore.LangManager;
import me.hektortm.wosCore.Utils;
import me.hektortm.wosCore.logging.LogManager;
import me.hektortm.wosCore.util.SubCommand;
import me.hektortm.wosCore.util.PermUtil;
import me.hektortm.wosCore.util.Permissions;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.ClickEvent;
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
        if (!PermUtil.hasPermission(sender, Permissions.LOG_VIEW)) return;
        if (args.length == 0) {
            Utils.error(sender, "debug", "error.usage.view");
            return;
        }

        String playerName = args[0];
        int page = 1;
        if (args.length > 1) {
            try {
                page = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                Utils.error(sender, "debug", "error.invalid-page");
                return;
            }
        }

        List<String> logs = logManager.getLogsForPlayer(playerName);
        if (logs.isEmpty()) {
            String msg = lang.getMessage("general", "prefix.error") + lang.getMessage("debug", "error.no-logs").replace("%player%", playerName);
            sender.sendMessage(msg);
            return;
        }

        int logsPerPage = 10;
        int startIndex = (page - 1) * logsPerPage;
        int endIndex = Math.min(startIndex + logsPerPage, logs.size());


        if (startIndex >= logs.size()) {
            Utils.error(sender, "debug", "error.no-more-logs");
            return;
        }

        sender.sendMessage(lang.getMessage("debug", "log.header")
                .replace("%log%", String.valueOf(endIndex - startIndex))
                .replace("%log_total%", String.valueOf(logs.size())).replace("%player%", playerName));
        for (int i = startIndex; i < endIndex; i++) {
            // TODO: Maybe make this soft coded?
            sender.sendMessage(ChatColor.GRAY + logs.get(i));
        }

        int totalPages = (logs.size() + logsPerPage - 1) / logsPerPage;

        if (page < totalPages) {
            sendPageNavigation(sender, playerName, page, totalPages);
        }
    }

    public void sendPageNavigation(CommandSender sender, String playerName, int page, int totalPages) {
        // Erzeuge die Hauptnachricht für die aktuelle Seite
        TextComponent mainText = new TextComponent();

        // Erzeuge "<<" Button, falls auf Seite 2 oder höher
        if (page > 1) {
            TextComponent previousPage = new TextComponent(lang.getMessage("debug", "log.page_down") + " ");
            previousPage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/debug log view " + playerName + " " + (page - 1)));


            mainText.addExtra(previousPage);

        }

        TextComponent main = new TextComponent(lang.getMessage("debug", "log.footer").replace("%page%", String.valueOf(page)).replace("%page_total%", String.valueOf(totalPages)));
        mainText.addExtra(main);

        // Füge die Hauptnachricht (z.B. "Page X/Y") hinzu
//        mainText.addExtra(ChatColor.YELLOW + " Page " + page + "/" + totalPages);

        // Erzeuge ">>" Button, falls weitere Seiten verfügbar sind
        if (page < totalPages) {
            TextComponent nextPage = new TextComponent(" "+lang.getMessage("debug", "log.page_up"));
            nextPage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/debug log view " + playerName + " " + (page + 1)));
            mainText.addExtra(nextPage);
        }

        // Sende die gesamte zusammengesetzte Nachricht an den Spieler
        sender.spigot().sendMessage(mainText);
    }


}
