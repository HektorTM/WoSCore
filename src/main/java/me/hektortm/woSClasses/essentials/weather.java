package me.hektortm.woSClasses.essentials;

import me.hektortm.woSClasses.utils;
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

        if (command.getName().equalsIgnoreCase("pweather")) {
            if (sender instanceof Player p) {
                if (args.length == 1) {
                    String weatherArg = args[0].toLowerCase();

                    switch (weatherArg) {
                        case "clear":
                            setPlayerWeather(p, 1.0f);
                            p.sendMessage(utils.getPrefix()+"§7You have set your personal weather to §eSunny§7.");
                    }
                }
            }
        }
        return true;
    }
    private void setPlayerWeather(Player p, float type) {
        try {
            Object packet = Class.forName("net.minecraft.server." + getServerVersion() + "PacketPlayOutGameStateChange")
                .getConstructor(int.class, float.class)
                .newInstance(7, type);
            Object nmsPlayer = p.getClass().getMethod("getHandle").invoke(p);
            Object playerConnection = nmsPlayer.getClass().getField("playerConnection").get(nmsPlayer);
            playerConnection.getClass().getMethod("sendPacket",Class.forName("net.minecraft.server." + getServerVersion() + ".Packet"))
                    .invoke(playerConnection, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getServerVersion() {
        return org.bukkit.Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    }
}
