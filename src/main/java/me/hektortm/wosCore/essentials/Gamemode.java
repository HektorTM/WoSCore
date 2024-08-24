package me.hektortm.wosCore.essentials;

import me.hektortm.wosCore.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


@SuppressWarnings({"SpellCheckingInspection"})
public class Gamemode implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("gmc")) {
            if (sender instanceof Player p) {
                if (args.length == 0) {
                    p.setGameMode(GameMode.CREATIVE);
                    Utils.successMsg(p,"essentials", "gm.c");
                } else if (args.length == 1) {
                    String playerName = args[0];
                    Player t = Bukkit.getServer().getPlayerExact(playerName);
                    if(p.getName().equals(playerName)) {
                        p.setGameMode(GameMode.CREATIVE);
                        Utils.successMsg(p, "essentials","gm.c");
                    } else {
                        if (t == null) {
                            Utils.error(p, "general", "online");
                        } else {
                            Utils.successMsg1Value(p, "essentials","gm.c-player", "%player%", playerName);
                            t.setGameMode(GameMode.CREATIVE);
                            Utils.successMsg(t, "essentials","gm.c");
                        }
                    }
                } else {
                    Utils.error(p, "general", "error.noargs");
                }
            }
        }

        if (command.getName().equalsIgnoreCase("gms")) {
            if (sender instanceof Player p) {
                if (args.length == 0) {
                    p.setGameMode(GameMode.SURVIVAL);
                    Utils.successMsg(p, "essentials","gm.s");
                } else if (args.length == 1) {
                    String playerName = args[0];
                    Player t = Bukkit.getServer().getPlayerExact(playerName);
                    if(p.getName().equals(playerName)) {
                        p.setGameMode(GameMode.SURVIVAL);
                        Utils.successMsg(p, "essentials","gm.s");
                    } else {
                        if (t == null) {
                            Utils.error(p, "general", "online");
                        } else {
                            Utils.successMsg1Value(p, "essentials","gm.s-player", "%player%", playerName);
                            t.setGameMode(GameMode.SURVIVAL);
                            Utils.successMsg(t, "essentials","gm.s");
                        }
                    }
                } else {
                    Utils.error(p, "general", "error.noargs");
                }
            }
        }

        if (command.getName().equalsIgnoreCase("gma")) {
            if (sender instanceof Player p) {
                if (args.length == 0) {
                    p.setGameMode(GameMode.ADVENTURE);
                    Utils.successMsg(p,"essentials", "gm.a");
                } else if (args.length == 1) {
                    String playerName = args[0];
                    Player t = Bukkit.getServer().getPlayerExact(playerName);
                    if(p.getName().equals(playerName)) {
                        p.setGameMode(GameMode.ADVENTURE);
                        Utils.successMsg(p, "essentials","gm.a");
                    } else {
                        if (t == null) {
                            Utils.error(p, "general", "error.online");
                        } else {
                            Utils.successMsg1Value(p, "essentials","gm.a-player", "%player%", playerName);
                            t.setGameMode(GameMode.ADVENTURE);
                            Utils.successMsg(t, "essentials","essentials.gm.a");
                        }
                    }
                } else {
                    Utils.error(p, "general", "error.noargs");
                }
            }
        }

        if (command.getName().equalsIgnoreCase("gmsp")) {
            if (sender instanceof Player p) {
                if (args.length == 0) {
                    p.setGameMode(GameMode.SPECTATOR);
                    Utils.successMsg(p,"essentials", "gm.sp");
                } else if (args.length == 1) {
                    String playerName = args[0];
                    Player t = Bukkit.getServer().getPlayerExact(playerName);
                    if(p.getName().equals(playerName)) {
                        p.setGameMode(GameMode.SPECTATOR);
                        Utils.successMsg(p,"essentials", "gm.sp");
                    } else {
                        if (t == null) {
                            Utils.error(p, "general", "error.online");
                        } else {
                            Utils.successMsg1Value(p,"essentials", "gm.sp-player", "%player%", playerName);
                            t.setGameMode(GameMode.SPECTATOR);
                            Utils.successMsg(t, "essentials","gm.sp");
                        }
                    }
                } else {
                    Utils.error(p, "general", "error.noargs");
                }
            }
        }

        if (command.getName().equalsIgnoreCase("fly")) {
            if (sender instanceof Player p) {
                if (args.length == 0) {
                    if (p.getAllowFlight()) {
                        p.setAllowFlight(false);
                        p.setFlying(false);
                        Utils.successMsg(p,"essentials", "fly.disabled");
                    } else {
                        p.setAllowFlight(true);
                        p.setFlying(true);
                        Utils.successMsg(p,"essentials", "fly.enabled");
                    }
                } else {
                    String playerName = args[0];
                    Player t = Bukkit.getServer().getPlayerExact(playerName);
                    if (p.getName().equals(playerName)) {
                        if (p.getAllowFlight()) {
                            p.setAllowFlight(false);
                            p.setFlying(false);
                            Utils.successMsg(p,"essentials", "fly.disabled");
                        } else {
                            p.setAllowFlight(true);
                            p.setFlying(true);
                            Utils.successMsg(p,"essentials", "fly.enabled");
                        }
                    } else {
                        if (t == null) {
                            Utils.error(p, "general", "error.online");
                        } else {
                            if (t.getAllowFlight()) {
                                t.setAllowFlight(false);
                                t.setFlying(false);
                                Utils.successMsg1Value(p,"essentials", "fly.disabled-player", "%player%", t.getName());
                                Utils.successMsg(t, "essentials","essentials.fly.disabled");
                            } else {
                                t.setAllowFlight(true);
                                t.setFlying(true);
                                Utils.successMsg1Value(p,"essentials", "fly.enabled-player", "%player%", t.getName());
                                Utils.successMsg(t,"essentials", "fly.enabled");
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
                    Utils.successMsg(p,"essentials", "speed.reset");
                } else if (args.length == 1) {
                    try {
                        float speed = Float.parseFloat(args[0]);
                        if (speed < 0 || speed > 10) {
                            Utils.error(p, "essentials", "error.speed.value");
                            return true;
                        }
                        setSpeed(p, speed);

                    } catch (Exception e) {
                        Utils.error(p, "essentials", "error.speed.invalid");
                    }
                } else if (args.length == 2) {
                    try {
                        float speed = Float.parseFloat(args[0]);
                        if(speed < 0 || speed > 10) {
                            Utils.error(p, "essentials", "error.speed.value");
                            return true;
                        }
                        Player t = Bukkit.getPlayerExact(args[1]);
                        if (t == null) {
                            Utils.error(p, "general", "error.online");
                            return true;
                        }
                        setSpeed(t, speed);
                        String speedVal = String.valueOf(speed);
                        if (t.isFlying()) {
                            Utils.successMsg2Values(p, "essentials","speed.set-flying-player", "%player%", t.getName(), "%speedvalue%", speedVal);
                        } else {
                            Utils.successMsg2Values(p,"essentials", "speed.set-walking-player", "%player%", t.getName(), "%speedvalue%", speedVal);
                        }
                    } catch (NumberFormatException e) {
                        Utils.error(p, "essentials", "error.speed.invalid");

                    }
                } else {
                    Utils.error(p, "general", "error.noargs");
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
            Utils.successMsg1Value(p,"essentials", "speed.set-flying", "%speedvalue%", speedVal);
        } else {
            p.setWalkSpeed(adjustSpeed);
            Utils.successMsg1Value(p, "essentials","speed.set-walking", "%speedvalue%", speedVal);
        }
    }
    private void resetSpeed(Player p) {
        p.setWalkSpeed(0.2f);
        p.setFlySpeed(0.1f);
    }
}
