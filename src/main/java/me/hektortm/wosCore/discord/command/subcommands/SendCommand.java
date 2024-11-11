package me.hektortm.wosCore.discord.command.subcommands;

import me.hektortm.wosCore.util.SubCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

import static me.hektortm.wosCore.WoSCore.jda;

public class SendCommand extends SubCommand {
    @Override
    public String getName() {
        return "send";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage("Usage: /senddc <channelID> <message>"); //TODO lang
            return;
        }

        String channelId = args[0];
        String message = String.join(" ", Arrays.copyOfRange(args, 1, args.length));

        try {
            TextChannel channel = jda.getTextChannelById(channelId);
            if (channel == null) {
                sender.sendMessage("Invalid channel ID."); //TODO lang
                return;
            }

            EmbedBuilder embed = new EmbedBuilder();
            embed.setDescription(message);
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
    }
}
