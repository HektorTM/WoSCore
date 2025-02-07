package me.hektortm.wosCore.listeners;

import io.papermc.paper.event.server.WhitelistStateUpdateEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.logging.Level;

import static me.hektortm.wosCore.WoSCore.jda;

public class WhitelistLogin implements Listener {

    @EventHandler
    public void onPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
        UUID uuid = event.getUniqueId();
        OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
        if (!Bukkit.hasWhitelist()) return;
        if (!player.isWhitelisted()) {
            String playerName = event.getName();
            String ipAddress = event.getAddress().getHostAddress();

            try {
                TextChannel channel = jda.getTextChannelById("1336853450897621094");
                if (channel == null) {
                    //sender.sendMessage("Invalid channel ID."); //TODO lang
                    return;
                }
                String message = "New login attempt:";

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                String formattedTime = LocalDateTime.now().format(formatter);

                EmbedBuilder embed = new EmbedBuilder();
                embed.setAuthor("Dolly", null, "https://i.imgur.com/sqZGV5O.jpeg");
                embed.setDescription(message);
                embed.addField("UUID", uuid.toString(), true);
                embed.addField("Username", playerName, true);
                embed.addField("IP", ipAddress, true);
                embed.setFooter("Dev Debug • " + formattedTime);
                embed.setColor(0x2b2d31);

                channel.sendMessageEmbeds(embed.build()).queue(
                        success -> Bukkit.getLogger().log(Level.INFO, "Message sent to Discord: " + message), //TODO lang
                        error -> {
                            Bukkit.getLogger().log(Level.SEVERE, "Error while sending message to Discord: " + error.getMessage());
                        }
                );
            } catch (Exception e) {
                Bukkit.getLogger().log(Level.SEVERE, "An error occurred while sending the message: " + e.getMessage());
                e.printStackTrace();
            }

            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST,
                    "§x§c§f§4§2§4§2UNDER CONSTRUCTION"+ "\n" +
                            "§x§f§3§7§3§3§5ᴡ§x§f§4§7§8§3§5ᴏ§x§f§4§7§e§3§4ʀ§x§f§5§8§3§3§4ʟ§x§f§6§8§8§3§4ᴅ§x§f§6§8§e§3§3 §x§f§7§9§3§3§3ᴏ§x§f§7§9§8§3§3ꜰ§x§f§8§9§e§3§3 §x§f§9§a§3§3§2ꜱ§x§f§9§a§8§3§2ᴏ§x§f§a§a§d§3§2ʀ§x§f§b§b§3§3§1ᴄ§x§f§b§b§8§3§1ᴇ§x§f§c§b§d§3§1ʀ§x§f§c§c§3§3§0ʏ§r §7is currently under maintenance." + "\n" +
                    "§7Please try again later... Stay tuned!");


        }

    }

}
