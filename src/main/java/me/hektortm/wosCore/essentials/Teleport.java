package me.hektortm.wosCore.essentials;

import me.hektortm.wosCore.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


@SuppressWarnings({"SpellCheckingInspection"})
public class Teleport implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if(command.getName().equalsIgnoreCase("tp")) {
            if(sender instanceof Player p) {
                if (args.length == 1) {
                    String pN = args[0];
                    Player t = Bukkit.getServer().getPlayerExact(pN);
                    if (t == null) {
                        Utils.error(p, "general", "error.online");
                    } else if (p.getName().equals(pN)) {
                        Utils.error(p, "essentials", "error.tp.self");
                    } else {
                        p.teleport(t);
                        Utils.successMsg1Value(p,"essentials", "tp", "%player%", pN);
                        p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                    }
                } else if (args.length == 0) {
                    Utils.error(p, "general", "error.noargs");
                } else if (args.length == 2) {
                    Utils.error(p, "general", "error.toomanyargs");
                }
            }
        }
        if (command.getName().equalsIgnoreCase("tphere")) {
            if (sender instanceof Player p) {
                if (args.length == 1) {
                    String pN = args[0];
                    Player t = Bukkit.getServer().getPlayerExact(pN);
                    if (t == null) {
                        Utils.error(p, "general", "error.online");
                    } else if (p.getName().contentEquals(pN)) {
                        Utils.error(p, "essentials", "error.tphere.self");
                    } else {
                        t.teleport(p);
                        Utils.successMsg1Value(p,"essentials", "tphere", "%player%", t.getName());
                    }
                } else if(args.length == 0) {
                    Utils.error(p, "general", "error.noargs");
                } else if (args.length == 2) {
                    Utils.error(p, "general", "error.toomanyargs");
                }
            }
        }
        return true;
    }
}
