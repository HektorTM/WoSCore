package me.hektortm.wosCore;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static me.hektortm.wosCore.Utils.*;

public class CoreCommands implements CommandExecutor {
    private final LangManager lang;
    private final WoSCore plugin;

    public CoreCommands(LangManager lang, WoSCore plugin) {
        this.lang = lang;
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("core")) {
            if(sender instanceof Player p) {
                String subCmd = args[0];
                if (subCmd.equals("reload")) {
                    String subCmd2 = args[1];
                    switch (subCmd2) {
                        case "config.yml":
                            Utils.successMsg(p, "core.config.reloading");
                            plugin.reloadConfig();
                            Utils.successMsg(p, "core.config.reloaded");
                            break;
                        case "messages.yml":
                            Utils.successMsg(p, "core.messages.reloading");
                            lang.reload();
                            Utils.successMsg(p, "core.messages.reloaded");
                            break;
                        case "all":
                            Utils.successMsg(p, "core.config.reloading");
                            Utils.successMsg(p, "core.messages.reloading");
                            plugin.reloadConfig();
                            lang.reload();
                            Utils.successMsg(p, "core.config.reloaded");
                            Utils.successMsg(p, "core.messages.reloaded");
                            break;
                        default:
                            Utils.error(p, errorCoreUnknown);
                    }

                } else {
                    Utils.error(p, errorCoreUnknown);
                }
            }
        }


        return true;
    }
}
