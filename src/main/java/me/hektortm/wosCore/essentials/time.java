package me.hektortm.wosCore.essentials;

import me.hektortm.wosCore.utils;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static me.hektortm.wosCore.utils.errorArgs;

@SuppressWarnings({"SpellCheckingInspection"})
public class time implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("day")) {
            if (sender instanceof Player p) {
                World w = p.getWorld();
                if (args.length == 0) {
                    w.setTime(1000);
                    p.sendMessage(utils.getPrefix()+"§7You have set the time to §eDay§7.");
                } else {
                    utils.error(p, errorArgs);
                }
            }
        }

        if (command.getName().equalsIgnoreCase("night")) {
            if (sender instanceof Player p) {
                World w = p.getWorld();
                if (args.length == 0) {
                    w.setTime(13000);
                    p.sendMessage(utils.getPrefix()+"§7You have set the time to §9Night§7.");
                } else {
                    utils.error(p, errorArgs);
                }
            }
        }

        if (command.getName().equalsIgnoreCase("ptime")) {
            if (sender instanceof Player p) {
                if (args.length == 1) {
                    String timeArg = args[0];
                    switch (timeArg.toLowerCase()) {
                        case "day":
                            p.setPlayerTime(1000, false);
                            p.sendMessage(utils.getPrefix()+"§7You have set your personal time to §eDay§7.");
                            break;
                        case "night":
                            p.setPlayerTime(13000, false);
                            p.sendMessage(utils.getPrefix()+"§7You have set your personal time to §9Night§7.");
                            break;
                        case "reset":
                            p.resetPlayerTime();
                            p.sendMessage(utils.getPrefix()+"§7You have §creset§7 your personal time.");
                            break;
                        default:
                            try {
                                long time = Long.parseLong(timeArg);
                                p.setPlayerTime(time, false);
                                p.sendMessage(utils.getPrefix()+"§7You have set your personal time to §a"+time+"§7.");
                            } catch (NumberFormatException e) {
                                utils.error(p, "Invalid time value. Provide 'day', 'night', 'reset' or a number");
                            }
                            break;
                    }
                }
            }
        }
        return true;
    }
}
