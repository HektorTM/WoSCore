package me.hektortm.wosCore.essentials;

import me.hektortm.wosCore.utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class playtime implements CommandExecutor {
    private final me.hektortm.wosCore.essentials.playtimeManager playtimeManager;

    public playtime(me.hektortm.wosCore.essentials.playtimeManager playtimeManager) {
        this.playtimeManager = playtimeManager;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("playtime")) {
            if (sender instanceof Player p) {
                String playtime = playtimeManager.getPlaytime(p);
                p.sendMessage(utils.getPrefix()+ "§7Your playtime: "+playtime);
                return true;
            }
        }


        return false;
    }
}
