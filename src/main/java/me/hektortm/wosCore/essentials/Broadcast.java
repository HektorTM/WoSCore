package me.hektortm.wosCore.essentials;

import me.hektortm.wosCore.LangManager;
import me.hektortm.wosCore.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

import static me.hektortm.wosCore.Utils.*;

@SuppressWarnings({"deprecation"})
public class Broadcast implements CommandExecutor {
    private final LangManager lang;
    public Broadcast(LangManager lang) {
        this.lang = lang;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("broadcast")) {
            if (sender instanceof Player p) {
                BroadcastSettings settings = BroadcastSettings.getSettings(p);
                if(args.length == 0) {
                    Utils.error(p, errorNoArgs);
                    return true;
                }

                String subCmd = args[0].toLowerCase();


                switch (subCmd) {
                    case "title":
                        if (args.length > 1) {
                            String title = String.join(" ", Arrays.copyOfRange(args, 1, args.length)).replaceAll("&", "§");
                            settings.setTitle(title);
                            Utils.successMsg1Value(p, "broadcast","broadcast.title", "%title%", title);
                        } else {
                            Utils.error(p, errorBcTitle);
                        }
                        break;
                    case "message":
                        if (args.length > 1) {
                            String msg = String.join(" ",Arrays.copyOfRange(args, 1, args.length)).replaceAll("&", "§");
                            settings.setMessage(msg);
                            Utils.successMsg1Value(p,"broadcast","broadcast.message", "%message%", msg);
                        } else {
                            Utils.error(p, errorBcMsg);
                        }
                        break;

                    case "sign":
                        settings.setSigned(!settings.isSigned());
                        Utils.successMsg1Value(p, "broadcast","broadcast.signed", "%status%", settings.isSigned() ? "§aSigned" : "§cUnsigned");
                        break;

                    case "preview":
                        if(settings.getTitle() != null && settings.getMessage() != null) {
                            String title = lang.getMessage("broadcast","broadcast.style.title").replace("%title%", settings.getTitle());
                            p.sendMessage(lang.getMessage("broadcast","broadcast.style.preview"));
                            p.sendMessage(title);
                            p.sendMessage(settings.getMessage());
                            if (settings.isSigned()) {
                                String signature = lang.getMessage("broadcast","broadcast.style.signature").replace("%signature%", p.getName());
                                p.sendMessage(signature);
                                p.sendMessage("");
                            } else {
                                p.sendMessage("");
                                p.sendMessage(lang.getMessage("broadcast","broadcast.style.preview"));
                            }
                        } else {
                            Utils.error(p, errorBcUnset);
                        }
                        break;
                    case "send":
                        if(settings.getTitle() != null && settings.getMessage() != null) {
                            String title = lang.getMessage("broadcast","broadcast.style.title").replace("%title%", settings.getTitle());
                            Bukkit.broadcastMessage(title);
                            Bukkit.broadcastMessage(settings.getMessage());
                            if (settings.isSigned()) {
                                String signature = lang.getMessage("broadcast","broadcast.style.signature").replace("%signature%", p.getName());
                                Bukkit.broadcastMessage(signature);
                                Bukkit.broadcastMessage("");
                            } else {
                                Bukkit.broadcastMessage("");
                            }
                        } else {
                            Utils.error(p, errorBcUnset);
                        }
                        break;
                    case "clear":
                        settings.clear();
                        Utils.successMsg(p,"broadcast", "broadcast.clear");
                        break;
                    default:
                        Utils.error(p, errorBcUnknown);
                        break;

                }
            }
        }
        if (command.getName().equalsIgnoreCase("shout")) {
            if (sender instanceof Player p) {
                if(args.length >= 1) {
                    String msg = String.join(" ", Arrays.copyOfRange(args, 0, args.length)).replaceAll("&", "§");
                    Bukkit.broadcastMessage("");
                    Bukkit.broadcastMessage(lang.getMessage("general","prefix.shout").replace("%player%", p.getName())+ msg);
                    Bukkit.broadcastMessage("");

                } else {
                    Utils.error(p, errorNoArgs);
                }
            }

        }

        return true;
    }
}
