package me.hektortm.wosEssentials.essentials;

import me.hektortm.wosEssentials.utils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static me.hektortm.wosEssentials.utils.*;

public class teleport implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if(command.getName().equalsIgnoreCase("tp")) {
            if(sender instanceof Player p) {
                if (args.length == 1) {
                    String pN = args[0];
                    Player t = Bukkit.getServer().getPlayerExact(pN);
                    if (t == null) {
                        utils.error(p, errorOnline);
                    } else if (p.getName().equals(pN)) {
                        utils.error(p, "You can't teleport to yourself.");
                    } else {
                        p.teleport(t);
                        p.sendMessage(utils.getPrefix() + "§7Teleporting to §a" + pN + " §7...");
                        p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                    }
                } else if (args.length == 0) {
                    utils.error(p, "Please provide an argument");
                } else if (args.length > 1) {
                    utils.error(p, "Too many arguments");
                }
            }
        }
        if (command.getName().equalsIgnoreCase("tphere")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (args.length == 1) {
                    String pN = args[0];
                    Player t = Bukkit.getServer().getPlayerExact(pN);
                    if (t == null) {
                        utils.error(p, errorOnline);
                    } else if (p.getName().contentEquals(pN)) {
                        utils.error(p, "You can't teleport yourself to you.");
                    } else {
                        t.teleport(p);
                        p.sendMessage(utils.getPrefix() + "§7Teleporting §a" + t.getName() + " §7to you...");
                    }
                } else if(args.length == 0) {
                    utils.error(p, errorNoArgs);
                } else if (args.length > 1) {
                    utils.error(p, errorArgs);
                }
            }
        }
        return true;
    }
}
