package me.hektortm.wosCore;


import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Utils {
   private static LangManager lang;

   public static String errorOnline;
   public static String errorNoArgs;
   public static String errorArgs;
   public static String errorUnknownPvP;
   public static String errorSpeedValue;
   public static String errorSpeedInvalid;
   public static String errorTpSelf;
   public static String errorTphereSelf;
   public static String errorTimeInvalid;
   public static String errorBcTitle;
   public static String errorBcMsg;
   public static String errorBcUnset;
   public static String errorBcUnknown;
   public static String errorCoreUnknown;
   public static String errorGuiNotFound;


   public static void init(LangManager langManager) {
       lang = langManager;
       //initPrefix(plugin);

       errorOnline = lang.getMessage("error.online");
       errorNoArgs = lang.getMessage("error.noargs");
       errorArgs = lang.getMessage("error.toomanyargs");
       errorUnknownPvP = lang.getMessage("error.pvp.unknown");
       errorSpeedValue = lang.getMessage("error.speed.value");
       errorSpeedInvalid = lang.getMessage("error.speed.invalid");
       errorTpSelf = lang.getMessage("error.tp.self");
       errorTphereSelf = lang.getMessage("error.tphere.self");
       errorTimeInvalid = lang.getMessage("error.time.invalid");
       errorBcTitle = lang.getMessage("error.broadcast.title");
       errorBcMsg = lang.getMessage("error.broadcast.message");
       errorBcUnset = lang.getMessage("error.broadcast.unset");
       errorBcUnknown = lang.getMessage("error.broadcast.unknown");
       errorCoreUnknown = lang.getMessage("error.core.unknown");
       errorGuiNotFound = lang.getMessage("error.gui.not-found");
   }

   //public static void initPrefix(JavaPlugin plugin) {
   //    prefix = plugin.getConfig().getString("prefix", "§6World of Sorcery §8| §r");
   //}
    public static String getPrefix() {
        return lang.getMessage("prefix.general");
    }
    public static void error(CommandSender sender, String msg) {
        if (sender instanceof Player p) {
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
            p.sendMessage(lang.getMessage("prefix.error") + msg);
        }

    }
    public static void successMsg(CommandSender sender, String msg) {
       sender.sendMessage(lang.getMessage("prefix.general")+lang.getMessage(msg));
    }
    public static void successMsg1Value(CommandSender sender, String msg, String oldChar, String value) {
       String message = lang.getMessage(msg).replace(oldChar, value);
       sender.sendMessage(lang.getMessage("prefix.general")+message);
    }
    public static void successMsg2Values(CommandSender sender, String msg, String oldChar1, String value1, String oldChar2, String value2) {
        String message = lang.getMessage(msg).replace(oldChar1, value1).replace(oldChar2, value2);
        sender.sendMessage(lang.getMessage("prefix.general")+message);
    }

}
