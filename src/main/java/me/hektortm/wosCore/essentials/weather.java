package me.hektortm.wosCore.essentials;

import me.hektortm.wosCore.utils;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class weather implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("sun")) {
            if (sender instanceof Player p) {
                World w = p.getWorld();
                if (args.length == 0) {
                    w.setStorm(false);
                    w.setThundering(false);
                    p.sendMessage(utils.getPrefix()+"§7You have set the weather to §eSunny§7.");
                } else {
                    utils.error(p, utils.errorArgs);
                }
            }
        }

        if (command.getName().equalsIgnoreCase("rain")) {
            if (sender instanceof Player p) {
                World w = p.getWorld();
                if (args.length == 0) {
                    w.setStorm(true);
                    w.setThundering(false);
                    p.sendMessage(utils.getPrefix()+"§7You have set the weather to §bRainy§7.");
                } else {
                    utils.error(p, utils.errorArgs);
                }
            }
        }

        if (command.getName().equalsIgnoreCase("storm")) {
            if (sender instanceof Player p) {
                World w = p.getWorld();
                if (args.length == 0) {
                    w.setStorm(true);
                    w.setThundering(true);
                    p.sendMessage(utils.getPrefix()+"§7You have set the weather to §9Stormy§7.");
                } else {
                    utils.error(p, utils.errorArgs);
                }
            }
        }

        return true;
    }
}
