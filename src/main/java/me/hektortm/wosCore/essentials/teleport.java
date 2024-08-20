package me.hektortm.wosCore.essentials;

import me.hektortm.wosCore.utils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static me.hektortm.wosCore.utils.*;

@SuppressWarnings({"SpellCheckingInspection"})
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
                        utils.error(p, errorTpSelf);
                    } else {
                        p.teleport(t);
                        utils.successMsg1Value(p, "essentials.tp", "%player%", pN);
                        p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                    }
                } else if (args.length == 0) {
                    utils.error(p, errorNoArgs);
                } else if (args.length == 2) {
                    utils.error(p, errorArgs);
                }
            }
        }
        if (command.getName().equalsIgnoreCase("tphere")) {
            if (sender instanceof Player p) {
                if (args.length == 1) {
                    String pN = args[0];
                    Player t = Bukkit.getServer().getPlayerExact(pN);
                    if (t == null) {
                        utils.error(p, errorOnline);
                    } else if (p.getName().contentEquals(pN)) {
                        utils.error(p, errorTphereSelf);
                    } else {
                        t.teleport(p);
                        utils.successMsg1Value(p, "essentials.tphere", "%player%", t.getName());
                    }
                } else if(args.length == 0) {
                    utils.error(p, errorNoArgs);
                } else if (args.length == 2) {
                    utils.error(p, errorArgs);
                }
            }
        }
        return true;
    }
}
