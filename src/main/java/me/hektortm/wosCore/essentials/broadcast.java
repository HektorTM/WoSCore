package me.hektortm.wosCore.essentials;

import me.hektortm.wosCore.LangManager;
import me.hektortm.wosCore.utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

import static me.hektortm.wosCore.utils.*;

@SuppressWarnings({"deprecation"})
public class broadcast implements CommandExecutor {
    private final LangManager lang;
    public broadcast(LangManager lang) {
        this.lang = lang;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("broadcast")) {
            if (sender instanceof Player p) {
                broadcastSettings settings = broadcastSettings.getSettings(p);
                if(args.length == 0) {
                    utils.error(p, errorNoArgs);
                    return true;
                }

                String subCmd = args[0].toLowerCase();


                switch (subCmd) {
                    case "title":
                        if (args.length > 1) {
                            String title = String.join(" ", Arrays.copyOfRange(args, 1, args.length)).replaceAll("&", "§");
                            settings.setTitle(title);
                            utils.successMsg1Value(p,"broadcast.title", "%title%", title);
                        } else {
                            utils.error(p, errorBcTitle);
                        }
                        break;
                    case "message":
                        if (args.length > 1) {
                            String msg = String.join(" ",Arrays.copyOfRange(args, 1, args.length)).replaceAll("&", "§");
                            settings.setMessage(msg);
                            utils.successMsg1Value(p,"broadcast.message", "%message%", msg);
                        } else {
                            utils.error(p, errorBcMsg);
                        }
                        break;

                    case "sign":
                        settings.setSigned(!settings.isSigned());
                        utils.successMsg1Value(p, "broadcast.signed", "%status%", settings.isSigned() ? "§aSigned" : "§cUnsigned");
                        break;

                    case "preview":
                        if(settings.getTitle() != null && settings.getMessage() != null) {
                            String title = lang.getMessage("broadcast.style.title").replace("%title%", settings.getTitle());
                            p.sendMessage(title);
                            p.sendMessage(settings.getMessage());
                            if (settings.isSigned()) {
                                String signature = lang.getMessage("broadcast.style.signature").replace("%signature%", p.getName());
                                p.sendMessage(signature);
                                p.sendMessage("");
                            } else {
                                p.sendMessage("");
                            }
                        } else {
                            utils.error(p, errorBcUnset);
                        }
                        break;
                    case "send":
                        if(settings.getTitle() != null && settings.getMessage() != null) {
                            String title = lang.getMessage("broadcast.style.title").replace("%title%", settings.getTitle());
                            Bukkit.broadcastMessage(title);
                            Bukkit.broadcastMessage(settings.getMessage());
                            if (settings.isSigned()) {
                                String signature = lang.getMessage("broadcast.style.signature").replace("%signature%", p.getName());
                                Bukkit.broadcastMessage(signature);
                                Bukkit.broadcastMessage("");
                            } else {
                                Bukkit.broadcastMessage("");
                            }
                        } else {
                            utils.error(p, errorBcUnset);
                        }
                        break;
                    case "clear":
                        settings.clear();
                        utils.successMsg(p, "broadcast.clear");
                        break;
                    default:
                        utils.error(p, errorBcUnknown);
                        break;

                }
            }
        }
        if (command.getName().equalsIgnoreCase("shout")) {
            if (sender instanceof Player p) {
                if(args.length >= 1) {
                    String msg = String.join(" ", Arrays.copyOfRange(args, 0, args.length)).replaceAll("&", "§");
                    Bukkit.broadcastMessage("");
                    Bukkit.broadcastMessage(lang.getMessage("prefix.shout").replace("%player%", p.getName())+ msg);
                    Bukkit.broadcastMessage("");

                } else {
                    utils.error(p, errorNoArgs);
                }
            }

        }

        return true;
    }
}
