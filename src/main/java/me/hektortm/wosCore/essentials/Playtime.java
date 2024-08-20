package me.hektortm.wosCore.essentials;

import me.hektortm.wosCore.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Playtime implements CommandExecutor {
    private final PlaytimeManager playtimeManager;

    public Playtime(PlaytimeManager playtimeManager) {
        this.playtimeManager = playtimeManager;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("playtime")) {
            if (sender instanceof Player p) {
                String playtime = playtimeManager.getPlaytime(p);
                p.sendMessage(Utils.getPrefix()+ "§7Your playtime: "+playtime);
                return true;
            }
        }


        return false;
    }
}
