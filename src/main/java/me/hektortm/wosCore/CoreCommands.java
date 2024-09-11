package me.hektortm.wosCore;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;


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
                if (args.length == 0) {
                    Utils.error(sender, "general", "error.noargs");
                    return false;
                }

                String subCmd = args[0];

                if (subCmd.equalsIgnoreCase("list")) {
                    player.sendMessage(lang.getMessage("general", "prefix.general")+"WoS Plugins:");
                    checkPlugins(player);
                }



            }
        }
        return true;
    }

    public void checkPlugins(Player p) {
        String[] pluginNames =
                {"WoSFriends",
                "WoSEconomy",
                "WoSTime",
                "WoSChestRun",
                "WoSBroomRace",
                "WoSCitems",
                "WoSEssentials",
                "WoSFamilies",
                "WoSHousePoints",
                "WoSMailBox",
                "WoSProfile",
                "WoSPvP"};
        PluginManager pluginManager = Bukkit.getPluginManager();

        for (String pluginName : pluginNames) {
            Plugin plugin = pluginManager.getPlugin(pluginName);
            if (plugin != null && plugin.isEnabled()) {
                p.sendMessage("§7- §a" + pluginName);
            } else {
                p.sendMessage("§7- §c"+ pluginName);
            }
        }
    }



}
