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
        if (command.getName().equalsIgnoreCase("core")) {
            if (args.length == 1) {
                // If no arguments or the first argument is being typed, suggest the first set of options
                return Arrays.asList("reload");
            } else if (args.length == 2 && args[0].equalsIgnoreCase("reload")) {
                // If the first argument is "reload", suggest these options for the second argument
                return Arrays.asList("messages.yml", "config.yml", "all");
            }
        }
        return null;
    }
}
