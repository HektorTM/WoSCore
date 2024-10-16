package me.hektortm.wosCore;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandTabComplete implements TabCompleter {
    private final LangManager lang;

    public CommandTabComplete(LangManager lang) {
        this.lang = lang;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();

        if (command.getName().equalsIgnoreCase("core")) {
            if (args.length == 1) {
                // If no arguments or the first argument is being typed, suggest the first set of options
                completions.addAll(Arrays.asList("reload"));
            } else if (args.length == 2 && args[0].equalsIgnoreCase("reload")) {
                // If the first argument is "reload", suggest these options for the second argument
                completions.addAll(lang.getAllLangFilenames());
                completions.addAll(Arrays.asList("lang", "config.yml", "all"));
            }
        }
        // Filter and return matching completions
        return completions;
    }
}
