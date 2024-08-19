package me.hektortm.wosEssentials.essentials;

import me.hektortm.wosEssentials.utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static me.hektortm.wosEssentials.utils.*;

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

        if (command.getName().equalsIgnoreCase("fly")) {
            if (sender instanceof Player p) {
                if (args.length == 0) {
                    if (p.getAllowFlight()) {
                        p.setAllowFlight(false);
                        p.setFlying(false);
                        p.sendMessage(utils.getPrefix() + "§7You have §cdisabled§7 fly.");
                    } else {
                        p.setAllowFlight(true);
                        p.setFlying(true);
                        p.sendMessage(utils.getPrefix() + "§7You have §aenabled§7 fly.");
                    }
                } else {
                    String playerName = args[0];
                    Player t = Bukkit.getServer().getPlayerExact(playerName);
                    if (p.getName().equals(playerName)) {
                        if (p.getAllowFlight()) {
                            p.setAllowFlight(false);
                            p.setFlying(false);
                            p.sendMessage(utils.getPrefix() + "§7You have §cdisabled§7 fly.");
                        } else {
                            p.setAllowFlight(true);
                            p.setFlying(true);
                            p.sendMessage(utils.getPrefix() + "§7You have §aenabled§7 fly.");
                        }
                    } else {
                        if (t == null) {
                            utils.error(p, "This player is not online.");
                        } else {
                            if (t.getAllowFlight()) {
                                t.setAllowFlight(false);
                                t.setFlying(false);
                                p.sendMessage(utils.getPrefix() + "§7You have §cdisabled§7 fly for §e" + t.getName() + "§7.");
                                t.sendMessage(utils.getPrefix() + "§7Your fly has been §cdisabled§7.");
                            } else {
                                t.setAllowFlight(true);
                                t.setFlying(true);
                                p.sendMessage(utils.getPrefix() + "§7You have §aenabled§7 fly for §e" + t.getName() + "§7.");
                                t.sendMessage(utils.getPrefix() + "§7Your fly has been §aenabled§7.");
                            }
                        }
                    }
                }
            }
        }

        if (command.getName().equalsIgnoreCase("speed")) {
            if (sender instanceof Player p) {
                if (args.length == 0) {
                    resetSpeed(p);
                    p.sendMessage(utils.getPrefix()+"§7You have reset your speed");
                } else if (args.length == 1) {
                    try {
                        float speed = Float.parseFloat(args[0]);
                        if (speed < 0 || speed > 10) {
                            utils.error(p, "Provide a value between 0 and 10");
                            return true;
                        }
                        setSpeed(p, speed);

                    } catch (Exception e) {
                        utils.error(p, "Invalid value. Provide a number.");
                    }
                } else if (args.length == 2) {
                    try {
                        float speed = Float.parseFloat(args[0]);
                        if(speed < 0 || speed > 10) {
                            utils.error(p, "Provide a value between 0 and 10");
                            return true;
                        }
                        Player t = Bukkit.getPlayerExact(args[1]);
                        if (t == null) {
                            utils.error(p, errorOnline);
                            return true;
                        }
                        setSpeed(t, speed);
                        if (t.isFlying()) {
                            p.sendMessage(utils.getPrefix()+"§7You have set the flying speed for §e"+t.getName()+"§7 to §a"+speed+"§7.");
                        } else {
                            p.sendMessage(utils.getPrefix()+"§7You have set the walking speed for §e"+t.getName()+"§7 to §a"+speed+"§7.");
                        }
                    } catch (NumberFormatException e) {
                        utils.error(p, "Invalid value. Provide a number.");

                    }
                } else {
                    utils.error(p, errorArgs);
                }
            }
        }



        return true;
    }
    private void setSpeed(Player p, float speed) {
        float adjustSpeed = speed / 10.0f;
        if(p.isFlying()) {
            p.setFlySpeed(adjustSpeed);
            p.sendMessage(utils.getPrefix()+"§7Your flying speed was set to §a"+speed+"§7.");
        } else {
            p.setWalkSpeed(adjustSpeed);
            p.sendMessage(utils.getPrefix()+"§7Your walking speed was set to §a"+speed+"§7.");
        }
    }
    private void resetSpeed(Player p) {
        p.setWalkSpeed(0.2f);
        p.setFlySpeed(0.1f);
    }
}
