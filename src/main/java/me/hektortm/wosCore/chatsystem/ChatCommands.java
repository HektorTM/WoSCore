package me.hektortm.wosCore.chatsystem;

import me.hektortm.wosCore.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static me.hektortm.wosCore.Utils.*;

public class ChatCommands implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(command.getName().equalsIgnoreCase("channel")) {
            if (sender instanceof Player p ) {
                String subCmd = args[0];
                String subCmd2 = args[1];
                if (args.length != 1) {
                    Utils.error(p, errorNoArgs);
                }
                switch (subCmd) {
                    case "focus":
                    case "join":
                        if (args.length != 2) {
                            Utils.error(p, errorArgs);
                        }



                    case "leave":
                        if (subCmd2 == null) {

                        }
                    case "info":
                    default:


                }

            }
        }

        return false;
    }
}
