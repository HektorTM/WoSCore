package me.hektortm.wosCore.essentials;

import me.hektortm.wosCore.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static me.hektortm.wosCore.Utils.*;

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
                        Utils.error(p, errorOnline);
                    } else if (p.getName().equals(pN)) {
                        Utils.error(p, errorTpSelf);
                    } else {
                        p.teleport(t);
                        Utils.successMsg1Value(p,"essentials", "essentials.tp", "%player%", pN);
                        p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                    }
                } else if (args.length == 0) {
                    Utils.error(p, errorNoArgs);
                } else if (args.length == 2) {
                    Utils.error(p, errorArgs);
                }
            }
        }
        if (command.getName().equalsIgnoreCase("tphere")) {
            if (sender instanceof Player p) {
                if (args.length == 1) {
                    String pN = args[0];
                    Player t = Bukkit.getServer().getPlayerExact(pN);
                    if (t == null) {
                        Utils.error(p, errorOnline);
                    } else if (p.getName().contentEquals(pN)) {
                        Utils.error(p, errorTphereSelf);
                    } else {
                        t.teleport(p);
                        Utils.successMsg1Value(p,"essentials", "essentials.tphere", "%player%", t.getName());
                    }
                } else if(args.length == 0) {
                    Utils.error(p, errorNoArgs);
                } else if (args.length == 2) {
                    Utils.error(p, errorArgs);
                }
            }
        }
        return true;
    }
}
