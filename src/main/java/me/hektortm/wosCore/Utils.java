package me.hektortm.wosCore;


import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Utils {
   private static LangManager lang;



   public static void init(LangManager langManager) {
       lang = langManager;
       //initPrefix(plugin);

   }

   //public static void initPrefix(JavaPlugin plugin) {
   //    prefix = plugin.getConfig().getString("prefix", "§6World of Sorcery §8| §r");
   //}
    public static String getPrefix() {
        return lang.getMessage("general","prefix.general");
    }


    public static void error(CommandSender sender, String file, String msg) {
        if (sender instanceof Player p) {
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
            p.sendMessage(lang.getMessage("general","prefix.error") + lang.getMessage(file, msg));
        }

    }



    public static void successMsg(CommandSender sender,String file, String msg) {
       sender.sendMessage(lang.getMessage("general","prefix.general")+lang.getMessage(file, msg));
    }
    public static void successMsg1Value(CommandSender sender,String file, String msg, String oldChar, String value) {
       String message = lang.getMessage(file, msg).replace(oldChar, value);
       sender.sendMessage(lang.getMessage("general","prefix.general")+message);
    }
    public static void successMsg2Values(CommandSender sender,String file, String msg, String oldChar1, String value1, String oldChar2, String value2) {
        String message = lang.getMessage(file, msg).replace(oldChar1, value1).replace(oldChar2, value2);
        sender.sendMessage(lang.getMessage("general","prefix.general")+message);
    }




}
