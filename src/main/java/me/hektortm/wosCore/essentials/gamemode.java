package me.hektortm.wosCore.essentials;

import me.hektortm.wosCore.LangManager;
import me.hektortm.wosCore.utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static me.hektortm.wosCore.utils.*;

@SuppressWarnings({"SpellCheckingInspection"})
public class gamemode implements CommandExecutor {
    private final LangManager lang;

    public gamemode(LangManager lang) {
        this.lang = lang;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("gmc")) {
            if (sender instanceof Player p) {
                if (args.length == 0) {
                    p.setGameMode(GameMode.CREATIVE);
                    utils.successMsg(p, "essentials.gm.c");
                } else if (args.length == 1) {
                    String playerName = args[0];
                    Player t = Bukkit.getServer().getPlayerExact(playerName);
                    if(p.getName().equals(playerName)) {
                        p.setGameMode(GameMode.CREATIVE);
                        utils.successMsg(p, "essentials.gm.c");
                    } else {
                        if (t == null) {
                            utils.error(p, errorOnline);
                        } else {
                            utils.successMsg1Value(p, "essentials.gm.c-player", "%player%", playerName);
                            t.setGameMode(GameMode.CREATIVE);
                            utils.successMsg(t, "essentials.gm.c");
                        }
                    }
                } else {
                    utils.error(p, errorArgs);
                }
            }
        }

        if (command.getName().equalsIgnoreCase("gms")) {
            if (sender instanceof Player p) {
                if (args.length == 0) {
                    p.setGameMode(GameMode.SURVIVAL);
                    utils.successMsg(p, "essentials.gm.s");
                } else if (args.length == 1) {
                    String playerName = args[0];
                    Player t = Bukkit.getServer().getPlayerExact(playerName);
                    if(p.getName().equals(playerName)) {
                        p.setGameMode(GameMode.SURVIVAL);
                        utils.successMsg(p, "essentials.gm.s");
                    } else {
                        if (t == null) {
                            utils.error(p, errorOnline);
                        } else {
                            utils.successMsg1Value(p, "essentials.gm.s-player", "%player%", playerName);
                            t.setGameMode(GameMode.SURVIVAL);
                            utils.successMsg(t, "essentials.gm.s");
                        }
                    }
                } else {
                    utils.error(p, errorArgs);
                }
            }
        }

        if (command.getName().equalsIgnoreCase("gma")) {
            if (sender instanceof Player p) {
                if (args.length == 0) {
                    p.setGameMode(GameMode.ADVENTURE);
                    utils.successMsg(p, "essentials.gm.a");
                } else if (args.length == 1) {
                    String playerName = args[0];
                    Player t = Bukkit.getServer().getPlayerExact(playerName);
                    if(p.getName().equals(playerName)) {
                        p.setGameMode(GameMode.ADVENTURE);
                        utils.successMsg(p, "essentials.gm.a");
                    } else {
                        if (t == null) {
                            utils.error(p, errorOnline);
                        } else {
                            utils.successMsg1Value(p, "essentials.gm.a-player", "%player%", playerName);
                            t.setGameMode(GameMode.ADVENTURE);
                            utils.successMsg(t, "essentials.gm.a");
                        }
                    }
                } else {
                    utils.error(p, errorArgs);
                }
            }
        }

        if (command.getName().equalsIgnoreCase("gmsp")) {
            if (sender instanceof Player p) {
                if (args.length == 0) {
                    p.setGameMode(GameMode.SPECTATOR);
                    utils.successMsg(p, "essentials.gm.sp");
                } else if (args.length == 1) {
                    String playerName = args[0];
                    Player t = Bukkit.getServer().getPlayerExact(playerName);
                    if(p.getName().equals(playerName)) {
                        p.setGameMode(GameMode.SPECTATOR);
                        utils.successMsg(p, "essentials.gm.sp");
                    } else {
                        if (t == null) {
                            utils.error(p, errorOnline);
                        } else {
                            utils.successMsg1Value(p, "essentials.gm.sp-player", "%player%", playerName);
                            t.setGameMode(GameMode.SPECTATOR);
                            utils.successMsg(t, "essentials.gm.sp");
                        }
                    }
                } else {
                    utils.error(p, errorArgs);
                }
            }
        }

        if (command.getName().equalsIgnoreCase("fly")) {
            if (sender instanceof Player p) {
                if (args.length == 0) {
                    if (p.getAllowFlight()) {
                        p.setAllowFlight(false);
                        p.setFlying(false);
                        utils.successMsg(p, "essentials.fly.disabled");
                    } else {
                        p.setAllowFlight(true);
                        p.setFlying(true);
                        utils.successMsg(p, "essentials.fly.enabled");
                    }
                } else {
                    String playerName = args[0];
                    Player t = Bukkit.getServer().getPlayerExact(playerName);
                    if (p.getName().equals(playerName)) {
                        if (p.getAllowFlight()) {
                            p.setAllowFlight(false);
                            p.setFlying(false);
                            utils.successMsg(p, "essentials.fly.disabled");
                        } else {
                            p.setAllowFlight(true);
                            p.setFlying(true);
                            utils.successMsg(p, "essentials.fly.enabled");
                        }
                    } else {
                        if (t == null) {
                            utils.error(p, errorOnline);
                        } else {
                            if (t.getAllowFlight()) {
                                t.setAllowFlight(false);
                                t.setFlying(false);
                                utils.successMsg1Value(p, "essentials.fly.disabled-player", "%player%", t.getName());
                                utils.successMsg(t, "essentials.fly.disabled");
                            } else {
                                t.setAllowFlight(true);
                                t.setFlying(true);
                                utils.successMsg1Value(p, "essentials.fly.enabled-player", "%player%", t.getName());
                                utils.successMsg(t, "essentials.fly.enabled");
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
                    utils.successMsg(p, "essentials.speed.reset");
                } else if (args.length == 1) {
                    try {
                        float speed = Float.parseFloat(args[0]);
                        if (speed < 0 || speed > 10) {
                            utils.error(p, errorSpeedValue);
                            return true;
                        }
                        setSpeed(p, speed);

                    } catch (Exception e) {
                        utils.error(p, errorSpeedInvalid);
                    }
                } else if (args.length == 2) {
                    try {
                        float speed = Float.parseFloat(args[0]);
                        if(speed < 0 || speed > 10) {
                            utils.error(p, errorSpeedValue);
                            return true;
                        }
                        Player t = Bukkit.getPlayerExact(args[1]);
                        if (t == null) {
                            utils.error(p, errorOnline);
                            return true;
                        }
                        setSpeed(t, speed);
                        String speedVal = String.valueOf(speed);
                        if (t.isFlying()) {
                            utils.successMsg2Values(p, "essentials.speed.set-flying-player", "%player%", t.getName(), "%speedvalue%", speedVal);
                        } else {
                            utils.successMsg2Values(p, "essentials.speed.set-walking-player", "%player%", t.getName(), "%speedvalue%", speedVal);
                        }
                    } catch (NumberFormatException e) {
                        utils.error(p, errorSpeedInvalid);

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
        String speedVal = String.valueOf(speed);
        if(p.isFlying()) {
            p.setFlySpeed(adjustSpeed);
            utils.successMsg1Value(p, "essentials.speed.set-flying", "%speedvalue%", speedVal);
        } else {
            p.setWalkSpeed(adjustSpeed);
            utils.successMsg1Value(p, "essentials.speed.set-walking", "%speedvalue%", speedVal);
        }
    }
    private void resetSpeed(Player p) {
        p.setWalkSpeed(0.2f);
        p.setFlySpeed(0.1f);
    }
}
