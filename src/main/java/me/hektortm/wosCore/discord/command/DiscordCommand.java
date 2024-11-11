package me.hektortm.wosCore.discord.command;

import me.hektortm.wosCore.Utils;
import me.hektortm.wosCore.WoSCore;
import me.hektortm.wosCore.discord.command.subcommands.HousepointsCommand;
import me.hektortm.wosCore.discord.command.subcommands.SendCommand;
import me.hektortm.wosCore.util.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class DiscordCommand implements CommandExecutor {

    private final Map<String, SubCommand> subCommands = new HashMap<>();
    private final WoSCore core;

    public DiscordCommand(WoSCore core) {
        this.core = core;

        subCommands.put("send", new SendCommand());
        subCommands.put("housepoints", new HousepointsCommand(core));
        //subCommands.put("link");


    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage("invite link here"); //TODO lang
            return true;
        }

        String primaryCommand = args[0].toLowerCase();

        SubCommand subCommand = subCommands.get(primaryCommand);
        if (subCommand != null) {
            subCommand.execute(sender, java.util.Arrays.copyOfRange(args, 1, args.length));
        } else {
            discordHelp(sender);
        }

        return true;
    }

    public void discordHelp(CommandSender s) {

    }

}
