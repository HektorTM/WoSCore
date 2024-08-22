package me.hektortm.wosCore;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import static me.hektortm.wosCore.Utils.*;

public class CoreCommands implements CommandExecutor {
    private final LangManager lang;
    private final WoSCore plugin;

    public CoreCommands(LangManager lang, WoSCore plugin) {
        this.lang = lang;
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("core")) {
            if (sender instanceof Player player) {
                if (args.length < 2) {
                    Utils.error(player, errorNoArgs);
                    return false;
                }

                String subCmd = args[0];
                String subCmd2 = args[1];

                if (subCmd.equalsIgnoreCase("reload")) {
                    List<String> files = lang.getAllLangFilenames();

                    if (subCmd2.equalsIgnoreCase("config.yml")) {
                        Utils.successMsg(player, "general", "core.config.reloading");
                        plugin.reloadConfig();
                        Utils.successMsg(player, "general", "core.config.reloaded");

                    } else if (subCmd2.equalsIgnoreCase("lang")) {
                        Utils.successMsg(player, "general", "core.messages.reloading");
                        lang.reload();
                        Utils.successMsg(player, "general", "core.messages.reloaded");

                    } else if (subCmd2.equalsIgnoreCase("all")) {
                        Utils.successMsg(player, "general", "core.config.reloading");
                        plugin.reloadConfig();
                        Utils.successMsg(player, "general", "core.config.reloaded");
                        Utils.successMsg(player, "general", "core.messages.reloading");
                        lang.reload();
                        Utils.successMsg(player, "general", "core.messages.reloaded");

                    } else if (files.contains(subCmd2)) {
                        Utils.successMsg1Value(player, "general", "core.messages.reloading_specific", "%file%", subCmd2);
                        lang.reloadFile(subCmd2);
                        Utils.successMsg1Value(player, "general", "core.messages.reloaded_specific", "%file%", subCmd2);
                    } else {
                        Utils.error(player, errorCoreUnknownFile);
                    }
                } else {
                    Utils.error(player, errorCoreUnknown);
                }
            }
        }
        return true;
    }




}
