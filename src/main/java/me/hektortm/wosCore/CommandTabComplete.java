package me.hektortm.wosCore;

import me.hektortm.wosCore.guis.GuiManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandTabComplete implements TabCompleter {
    private final GuiManager guiManager;
    private final LangManager lang;

    public CommandTabComplete(GuiManager guiManager, LangManager lang) {
        this.guiManager = guiManager;
        this.lang = lang;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();

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
                completions.addAll(Arrays.asList("reload"));
            } else if (args.length == 2 && args[0].equalsIgnoreCase("reload")) {
                // If the first argument is "reload", suggest these options for the second argument
                completions.addAll(lang.getAllLangFilenames());
                completions.addAll(Arrays.asList("lang", "config.yml", "all"));
            }
        }
        if (command.getName().equalsIgnoreCase("gui")) {
            if (args.length == 1) {
                // Provide options for the first argument
                if ("gui".equalsIgnoreCase(args[0])) {
                    completions.addAll(Arrays.asList("create", "edit", "delete"));
                } else {
                    // Complete the GUI names if no subcommand or unrecognized command
                    completions.addAll(guiManager.getAllGuiFilenames());
                }
            } else if (args.length == 3) {
                // Handle the second argument based on the first argument
                String subCommand = args[0].toLowerCase();
                if ("edit".equalsIgnoreCase(subCommand) || "delete".equalsIgnoreCase(subCommand)) {
                    completions.addAll(guiManager.getAllGuiFilenames());
                } else if ("create".equalsIgnoreCase(subCommand)) {
                    // You might add additional suggestions or constraints for "create"
                }
            } else if (args.length == 4 && "create".equalsIgnoreCase(args[0])) {
                // Handle row numbers for the "create" subcommand
                completions.addAll(Arrays.asList("1", "2", "3", "4", "5", "6"));
            }
        }

        // Filter and return matching completions
        return completions;
    }
}
