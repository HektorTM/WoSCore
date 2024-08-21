package me.hektortm.wosCore.guis;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GuiCommand implements CommandExecutor {
    private final GuiManager guiManager;

    public GuiCommand(GuiManager guiManager) {
        this.guiManager = guiManager;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length > 0) {
                String guiName = args[0];
                guiManager.openGui(player, guiName);
            } else {
                player.sendMessage("§cPlease specify a GUI name.");
            }
        }
        return true;
    }
}
