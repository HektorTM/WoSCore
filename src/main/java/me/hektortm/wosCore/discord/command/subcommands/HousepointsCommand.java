package me.hektortm.wosCore.discord.command.subcommands;

import me.hektortm.wosCore.WoSCore;
import me.hektortm.wosCore.util.SubCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static me.hektortm.wosCore.WoSCore.jda;

public class HousepointsCommand extends SubCommand {

    private final WoSCore core;

    public HousepointsCommand(WoSCore core) {
        this.core = core;
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ConsoleCommandSender c) {

            // dchousepoints Slytherin 15 ColaCinema approver reason

            String house = args[0];
            int points = Integer.parseInt(args[1]);
            OfflinePlayer p = Bukkit.getOfflinePlayer(args[2]);
            Player approver = Bukkit.getPlayer(args[3]);
            String id = org.bukkit.plugin.java.JavaPlugin.getPlugin(WoSCore.class).getConfig().getString("housepoint-channel");

            StringBuilder builder = new StringBuilder();
            for (int i = 4; i < args.length; i++) {
                if (i > 4) {
                    builder.append(" ");
                }
                builder.append(args[i]);
            }

            String reason = builder.toString();

            try {
                TextChannel channel = jda.getTextChannelById(id);
                if (channel == null) {
                    sender.sendMessage("Invalid channel ID."); //TODO lang
                    return;
                }
                String message = "Oh, yes, yes! Dolly is overjoyed to announce that **" + p.getName() + "** has earned house points!" +
                        " Such a splendid effort, it is! **" + approver.getName() + "** has awarded **" + points + "** points to **" + p.getName() + "** of **" + house +
                        "** for **" + reason + "**! So proud, Dolly is!";

                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                String formattedTime = now.format(formatter);

                EmbedBuilder embed = new EmbedBuilder();
                embed.setAuthor("Dolly", null, "https://i.imgur.com/sqZGV5O.jpeg");
                embed.setDescription(message);
                embed.addField("Username", p.getName(), true);
                embed.addField("House", house, true);
                embed.addField("Amount", String.valueOf(points), true);
                embed.setImage("https://minotar.net/helm/" + p.getName() + "/125.png");
                embed.setFooter("Hourglass • " + formattedTime);
                embed.setColor(0x2b2d31);

                channel.sendMessageEmbeds(embed.build()).queue(
                        success -> sender.sendMessage("Message sent to Discord: " + message), //TODO lang
                        error -> {
                            sender.sendMessage("Failed to send message to Discord."); //TODO lang
                            error.printStackTrace();
                        }
                );
            } catch (Exception e) {
                sender.sendMessage("An error occurred while sending the message."); //TODO lang
                e.printStackTrace();
            }
        } else {
            sender.sendMessage("This command cannot be executed by a player."); //TODO lang
        }
    }
}
