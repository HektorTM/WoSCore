package me.hektortm.wosCore.discord;

import me.hektortm.wosCore.WoSCore;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import static me.hektortm.wosCore.WoSCore.REQUIRED_ROLE_ID;

public class DiscordListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        // Make sure the message is from a user and not a bot
        if (event.getAuthor().isBot()) {
            return;
        }

        // Check if the message starts with the command
        if (event.getMessage().getContentRaw().startsWith("!dcannounce")) {
            // Check if the user has the required role
            if (!userHasRequiredRole(event)) {
                event.getChannel().sendMessage("You do not have permission to use this command.").queue();
                return;
            }

            // Extract the message content
            String message = event.getMessage().getContentRaw().substring("!dcannounce".length()).trim();

            // Get the webhook URL from the config
            String webhookUrl = org.bukkit.plugin.java.JavaPlugin.getPlugin(WoSCore.class).getConfig().getString("webhook-url");
            if (webhookUrl == null || webhookUrl.isEmpty()) {
                event.getChannel().sendMessage("Webhook URL is not configured.").queue();
                return;
            }

            // Create the embed as a JSON payload
            String jsonPayload = "{"
                    + "\"content\": null,"
                    + "\"username\": \"" + event.getAuthor().getGlobalName() + "\","
                    + "\"avatar_url\": \"" + event.getAuthor().getEffectiveAvatarUrl() + "\","
                    + "\"embeds\": [{"
                    + "\"title\": \"New Announcement\","
                    + "\"description\": \"" + message + "\","
                    + "\"color\": 2895665,"  // Equivalent to 0x2b2d31
                    + "\"timestamp\": \"" + event.getMessage().getTimeCreated() + "\""
                    + "}]}";

            try {
                // Create and configure the webhook request
                OkHttpClient client = new OkHttpClient();
                RequestBody body = RequestBody.create(
                        jsonPayload,
                        okhttp3.MediaType.parse("application/json; charset=utf-8")
                );

                Request request = new Request.Builder()
                        .url(webhookUrl)
                        .post(body)
                        .build();

                client.newCall(request).execute();
                event.getMessage().delete().queue();
            } catch (Exception e) {
                event.getChannel().sendMessage("Failed to send message to Discord.").queue();
                e.printStackTrace();
            }
        }
    }

    private boolean userHasRequiredRole(MessageReceivedEvent event) {
        Guild guild = event.getGuild();
        Role requiredRole = guild.getRoleById(REQUIRED_ROLE_ID);
        if (requiredRole == null) {
            event.getChannel().sendMessage("Role not found in the server.").queue();
            return false;
        }
        return event.getMember() != null && event.getMember().getRoles().contains(requiredRole);
    }
}


