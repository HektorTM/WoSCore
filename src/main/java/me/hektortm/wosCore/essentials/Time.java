package me.hektortm.wosCore.essentials;

import me.hektortm.wosCore.Utils;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static me.hektortm.wosCore.Utils.errorArgs;
import static me.hektortm.wosCore.Utils.errorTimeInvalid;

@SuppressWarnings({"SpellCheckingInspection"})
public class Time implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("day")) {
            if (sender instanceof Player p) {
                World w = p.getWorld();
                if (args.length == 0) {
                    w.setTime(1000);
                    Utils.successMsg(p,"essentials", "essentials.time.day");
                } else {
                    Utils.error(p, errorArgs);
                }
            }
        }

        if (command.getName().equalsIgnoreCase("night")) {
            if (sender instanceof Player p) {
                World w = p.getWorld();
                if (args.length == 0) {
                    w.setTime(13000);
                    Utils.successMsg(p, "essentials","essentials.time.night");
                } else {
                    Utils.error(p, errorArgs);
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
                            Utils.successMsg(p,"essentials", "essentials.ptime.day");
                            break;
                        case "night":
                            p.setPlayerTime(13000, false);
                            Utils.successMsg(p,"essentials", "essentials.ptime.night");
                            break;
                        case "reset":
                            p.resetPlayerTime();
                            Utils.successMsg(p,"essentials", "essentials.ptime.reset");
                            break;
                        default:
                            try {
                                long time = Long.parseLong(timeArg);
                                String timeVal = String.valueOf(time);
                                p.setPlayerTime(time, false);
                                Utils.successMsg1Value(p,"essentials", "essentials.ptime.custom", "%time%", timeVal);
                            } catch (NumberFormatException e) {
                                Utils.error(p, errorTimeInvalid);
                            }
                            break;
                    }
                }
            }
        }
        return true;
    }
}
