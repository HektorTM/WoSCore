package me.hektortm.wosCore.pvpsystem;

import me.hektortm.wosCore.utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings({"SpellCheckingInspection"})
public class PvPCommands implements CommandExecutor {
    private final PvPManager pvpManager;

    public PvPCommands(PvPManager pvpManager) {
        this.pvpManager = pvpManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("pvp")) {
            if (sender instanceof Player p) {
                if (args.length == 0) {
                    boolean newStatus = pvpManager.togglePvP(p);
                    p.sendMessage(utils.getPrefix()+"§7You have " + (newStatus ? "§aenabled" : "§cdisabled") + "§7 your combat mode.");
                    return true;
                }

                String subCommand = args[0].toLowerCase();

                switch (subCommand) {
                    case "toggle":
                        boolean newStatus = pvpManager.togglePvP(p);
                        p.sendMessage(utils.getPrefix()+"§7You have " + (newStatus ? "§aenabled" : "§cdisabled") + "§7 your combat mode.");
                        break;

                    case "status":
                        boolean isPvPEnabled = pvpManager.pvpEnabled(p);
                        p.sendMessage(utils.getPrefix()+"§7You currently have combat " + (isPvPEnabled ? "§aenabled" : "§cdisabled") + "§7.");
                        break;

                    case "help":
                        p.sendMessage(utils.getPrefix()+ "§7Combat Commands:");
                        p.sendMessage("§e/pvp toggle - Toggle your combat status.");
                        p.sendMessage("§e/pvp status - Check your current combat status.");
                        p.sendMessage("§e/pvp help - Display this help message.");
                        break;

                    default:
                        utils.error(p, "Unknown command. Use /pvp help for a list of commands.");
                        break;
                }
            }
        }
        return true;
    }
}
