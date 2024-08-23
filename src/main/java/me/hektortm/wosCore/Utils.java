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
   public static String errorCoreUnknownFile;
   public static String errorGuiNotFound;
   public static String errorGuiExists;
   public static String errorGuiDelete;
   public static String errorGuiUnknown;


   public static void init(LangManager langManager) {
       lang = langManager;
       //initPrefix(plugin);

       errorOnline = lang.getMessage("errors", "error.online");
       errorNoArgs = lang.getMessage("errors","error.noargs");
       errorArgs = lang.getMessage("errors","error.toomanyargs");
       errorUnknownPvP = lang.getMessage("errors","error.pvp.unknown");
       errorSpeedValue = lang.getMessage("errors","error.speed.value");
       errorSpeedInvalid = lang.getMessage("errors","error.speed.invalid");
       errorTpSelf = lang.getMessage("errors", "errors.tp.self");
       errorTphereSelf = lang.getMessage("errors","error.tphere.self");
       errorTimeInvalid = lang.getMessage("errors","error.time.invalid");
       errorBcTitle = lang.getMessage("errors","error.broadcast.title");
       errorBcMsg = lang.getMessage("errors","error.broadcast.message");
       errorBcUnset = lang.getMessage("errors","error.broadcast.unset");
       errorBcUnknown = lang.getMessage("errors","error.broadcast.unknown");
       errorCoreUnknown = lang.getMessage("errors","error.core.unknown");
       errorCoreUnknownFile = lang.getMessage("errors", "error.core.unknown-file");
       errorGuiNotFound = lang.getMessage("errors","error.gui.not-found");
       errorGuiExists = lang.getMessage("errors","error.gui.exists");
       errorGuiDelete = lang.getMessage("errors","error.gui.delete");
       errorGuiUnknown = lang.getMessage("errors","error.gui.unknown");
   }

   //public static void initPrefix(JavaPlugin plugin) {
   //    prefix = plugin.getConfig().getString("prefix", "§6World of Sorcery §8| §r");
   //}
    public static String getPrefix() {
        return lang.getMessage("general","prefix.general");
    }
    public static void error(CommandSender sender, String msg) {
        if (sender instanceof Player p) {
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
            p.sendMessage(lang.getMessage("general","prefix.error") + msg);
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
