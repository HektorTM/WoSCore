package me.hektortm.wosCore;


import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class utils {

   private static String prefix;
   public static String errorOnline = "This Player is not online.";
   public static String errorNoArgs = "Please provide an argument.";
   public static String errorArgs = "Too many arguments.";

   public static void initPrefix(JavaPlugin plugin) {
       prefix = plugin.getConfig().getString("prefix", "§6World of Sorcery §8| §r");
   }
    public static String getPrefix() {
        return prefix;
    }
    public static void error(CommandSender sender, String msg) {
        if (sender instanceof Player p) {
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
            p.sendMessage("§cERROR: §7" + msg);
        }

    }

}
