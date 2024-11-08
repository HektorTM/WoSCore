package me.hektortm.wosCore.logging.command.subcommands;

import me.hektortm.wosCore.Utils;
import me.hektortm.wosCore.logging.LogManager;
import me.hektortm.wosCore.logging.command.SubCommand;
import me.hektortm.wosCore.util.PermUtil;
import me.hektortm.wosCore.util.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WriteCommand extends SubCommand {

    private final LogManager logManager;

    public WriteCommand(LogManager logManager) {
        this.logManager = logManager;
    }

    @Override
    public String getName() {
        return "write";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (PermUtil.hasPermission(sender, Permissions.LOG_WRITE)) return;

        if (args.length == 0) {
            Utils.error(sender, "logs", "error.usage.write");
            return;
        }
        StringBuilder b = new StringBuilder();
        for(int l = 0; l < args.length; l++) {
            b.append(args[l]);
        }
        String log = b.toString();
        Player p = (Player) sender;

        logManager.writeLog(p, log);
        Utils.successMsg(sender, "logs", "log-sent");
    }
}
