package me.hektortm.wosCore.logging.command.subcommands;

import me.hektortm.wosCore.logging.command.DebugCommand;
import me.hektortm.wosCore.logging.command.SubCommand;
import org.bukkit.command.CommandSender;

public class HelpCommand extends SubCommand {

    private final DebugCommand cmd;

    public HelpCommand(DebugCommand cmd) {
        this.cmd = cmd;
    }
    @Override
    public String getName() {
        return "help";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        cmd.debugHelp(sender);
    }
}
