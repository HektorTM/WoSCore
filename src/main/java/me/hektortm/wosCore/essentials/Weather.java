package me.hektortm.wosCore.essentials;

import me.hektortm.wosCore.Utils;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Weather implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("sun")) {
            if (sender instanceof Player p) {
                World w = p.getWorld();
                if (args.length == 0) {
                    w.setStorm(false);
                    w.setThundering(false);
                    Utils.successMsg(p,"essentials", "weather.sun");
                } else {
                    Utils.error(p, "general", "error.toomanyargs");
                }
            }
        }

        if (command.getName().equalsIgnoreCase("rain")) {
            if (sender instanceof Player p) {
                World w = p.getWorld();
                if (args.length == 0) {
                    w.setStorm(true);
                    w.setThundering(false);
                    Utils.successMsg(p,"essentials", "weather.rain");
                } else {
                    Utils.error(p, "general", "error.toomanyargs");
                }
            }
        }

        if (command.getName().equalsIgnoreCase("storm")) {
            if (sender instanceof Player p) {
                World w = p.getWorld();
                if (args.length == 0) {
                    w.setStorm(true);
                    w.setThundering(true);
                    Utils.successMsg(p,"essentials", "weather.storm");
                } else {
                    Utils.error(p, "general", "error.toomanyargs");
                }
            }
        }

        return true;
    }
}
