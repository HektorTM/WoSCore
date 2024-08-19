package me.hektortm.woSClasses.essentials;

import me.hektortm.woSClasses.utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static me.hektortm.woSClasses.utils.errorOnline;

public class gamemode implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("gmc")) {
            if (sender instanceof Player p) {
                if (args.length == 0) {
                    p.setGameMode(GameMode.CREATIVE);
                    p.sendMessage(utils.getPrefix() + "§7Gamemode updated to §eCreative§7.");
                } else {
                    String playerName = args[0];
                    Player t = Bukkit.getServer().getPlayerExact(playerName);
                    if(p.getName().equals(playerName)) {
                        p.setGameMode(GameMode.CREATIVE);
                        p.sendMessage(utils.getPrefix() + "§7Gamemode updated to §eCreative§7.");
                    } else {
                        if (t == null) {
                            utils.error(p, errorOnline);
                        } else {
                            p.sendMessage(utils.getPrefix() + "$a" + playerName + "'s §7Gamemode updated to §eCreative§7.");
                            t.setGameMode(GameMode.CREATIVE);
                            t.sendMessage(utils.getPrefix() + "§7Gamemode updated to §eCreative§7.");
                        }
                    }
                }
            }
        }

        if (command.getName().equalsIgnoreCase("gms")) {
            if (sender instanceof Player p) {
                if (args.length == 0) {
                    p.setGameMode(GameMode.SURVIVAL);
                    p.sendMessage(utils.getPrefix() + "§7Gamemode updated to §eSurvival§7.");
                } else {
                    String playerName = args[0];
                    Player t = Bukkit.getServer().getPlayerExact(playerName);
                    if(p.getName().equals(playerName)) {
                        p.setGameMode(GameMode.SURVIVAL);
                        p.sendMessage(utils.getPrefix() + "§7Gamemode updated to §eSurvival§7.");
                    } else {
                        if (t == null) {
                            utils.error(p, errorOnline);
                        } else {
                            p.sendMessage(utils.getPrefix() + "$a" + playerName + "'s §7Gamemode updated to §eSurvival§7.");
                            t.setGameMode(GameMode.SURVIVAL);
                            t.sendMessage(utils.getPrefix() + "§7Gamemode updated to §eSurvival§7.");
                        }
                    }
                }
            }
        }

        if (command.getName().equalsIgnoreCase("gma")) {
            if (sender instanceof Player p) {
                if (args.length == 0) {
                    p.setGameMode(GameMode.ADVENTURE);
                    p.sendMessage(utils.getPrefix() + "§7Gamemode updated to §eAdventure§7.");
                } else {
                    String playerName = args[0];
                    Player t = Bukkit.getServer().getPlayerExact(playerName);
                    if(p.getName().equals(playerName)) {
                        p.setGameMode(GameMode.ADVENTURE);
                        p.sendMessage(utils.getPrefix() + "§7Gamemode updated to §eAdventure§7.");
                    } else {
                        if (t == null) {
                            utils.error(p, errorOnline);
                        } else {
                            p.sendMessage(utils.getPrefix() + "$a" + playerName + "'s §7Gamemode updated to §eAdventure§7.");
                            t.setGameMode(GameMode.ADVENTURE);
                            t.sendMessage(utils.getPrefix() + "§7Gamemode updated to §eAdventure§7.");
                        }
                    }
                }
            }
        }

        if (command.getName().equalsIgnoreCase("gmsp")) {
            if (sender instanceof Player p) {
                if (args.length == 0) {
                    p.setGameMode(GameMode.SPECTATOR);
                    p.sendMessage(utils.getPrefix() + "§7Gamemode updated to §eSpectator§7.");
                } else {
                    String playerName = args[0];
                    Player t = Bukkit.getServer().getPlayerExact(playerName);
                    if(p.getName().equals(playerName)) {
                        p.setGameMode(GameMode.SPECTATOR);
                        p.sendMessage(utils.getPrefix() + "§7Gamemode updated to §eSpectator§7.");
                    } else {
                        if (t == null) {
                            utils.error(p, errorOnline);
                        } else {
                            p.sendMessage(utils.getPrefix() + "$a" + playerName + "'s §7Gamemode updated to §eSpectator§7.");
                            t.setGameMode(GameMode.SPECTATOR);
                            t.sendMessage(utils.getPrefix() + "§7Gamemode updated to §eSpectator§7.");
                        }
                    }
                }
            }
        }


        return true;
    }
}
