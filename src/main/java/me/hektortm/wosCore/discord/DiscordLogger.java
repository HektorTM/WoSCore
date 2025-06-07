package me.hektortm.wosCore.discord;

import me.hektortm.wosCore.WoSCore;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

public class DiscordLogger {

    private static final String ERROR_WEBHOOK_URL = WoSCore.getPlugin(WoSCore.class).getConfig().getString("discord.webhook.error");
    private static final String WARNING_WEBHOOK_URL = WoSCore.getPlugin(WoSCore.class).getConfig().getString("discord.webhook.warning");


    public static void log(String pluginName, Level level, String message, Throwable throwable) {
        String webhookUrl;
        StringBuilder content = new StringBuilder();

        if (level == Level.SEVERE) {
            webhookUrl = ERROR_WEBHOOK_URL;
            content.append("**[ERROR]** ");
        } else if (level == Level.WARNING) {
            webhookUrl = WARNING_WEBHOOK_URL;
            content.append("**[WARNING]** ");
        } else {
            return;
        }

        content.append("**[").append(pluginName).append("]** ");
        content.append(message);

        if (throwable != null) {
            content.append("\n```").append(getStackTraceAsString(throwable)).append("```");
        }

        sendToDiscord(webhookUrl, content.toString());
    }

    private static void sendToDiscord(String webhookUrl, String content) {
        try {
            URL url = new URL(webhookUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            String jsonPayload = "{\"content\":\"" + content.replace("\"", "\\\"") + "\"}";
            byte[] postData = jsonPayload.getBytes(StandardCharsets.UTF_8);

            try (OutputStream outputStream = connection.getOutputStream()) {
                outputStream.write(postData);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode != 204) {
                System.err.println("Failed to send log to Discord: HTTP " + responseCode);
            }

        } catch (Exception e) {
            System.err.println("Error sending log to Discord: " + e.getMessage());
        }
    }

    private static String getStackTraceAsString(Throwable throwable) {
        StringBuilder sb = new StringBuilder();
        sb.append(throwable.toString()).append("\n");
        for (StackTraceElement element : throwable.getStackTrace()) {
            sb.append("  at ").append(element.toString()).append("\n");
        }
        return sb.toString();
    }
}