package me.hektortm.wosCore;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class CommandTabComplete implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(command.getName().equalsIgnoreCase("pvp")) {
            if (args.length == 1) {
                return Arrays.asList("toggle","status","help");
            }
        }
        if(command.getName().equalsIgnoreCase("ptime")) {
            if (args.length == 1) {
                return Arrays.asList("day", "night");
            }
        }
        if(command.getName().equalsIgnoreCase("broadcast")) {
            if (args.length == 1) {
                return Arrays.asList("title", "message", "sign", "preview", "send", "clear");
            }
        }

        return null;
    }
}
