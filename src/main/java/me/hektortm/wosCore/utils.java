package me.hektortm.wosCore;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
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
    //public static void sendActionBar(Player player, String message) {
    //    ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
    //
    //    PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.CHAT);
    //
    //    WrappedChatComponent chatComponent = WrappedChatComponent.fromText(message);
    //    packet.getChatComponents().write(0, chatComponent);
    //
    //    packet.getBytes().write(0, (byte) 2); // ActionBar ID is 2
    //
    //    try {
    //        protocolManager.sendServerPacket(player, packet);
    //    } catch (Exception e) {
    //        e.printStackTrace();
    //    }
    //}


}
