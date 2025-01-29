package me.hektortm.wosCore;


import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
   private static LangManager lang;
   private static final Map<String, String> colorMap = new HashMap<>();

    static {
        // Neutral
        colorMap.put("%neutral_300%", ColorCodes.NEUTRAL_300);
        colorMap.put("%neutral_500%", ColorCodes.NEUTRAL_500);
        colorMap.put("%neutral_700%", ColorCodes.NEUTRAL_700);

        // Red
        colorMap.put("%red_300%", ColorCodes.RED_300);
        colorMap.put("%red_500%", ColorCodes.RED_500);
        colorMap.put("%red_700%", ColorCodes.RED_700);

        // Orange
        colorMap.put("%orange_300%", ColorCodes.ORANGE_300);
        colorMap.put("%orange_500%", ColorCodes.ORANGE_500);
        colorMap.put("%orange_700%", ColorCodes.ORANGE_700);

        // Amber
        colorMap.put("%amber_300%", ColorCodes.AMBER_300);
        colorMap.put("%amber_500%", ColorCodes.AMBER_500);
        colorMap.put("%amber_700%", ColorCodes.AMBER_700);

        // Yellow
        colorMap.put("%yellow_300%", ColorCodes.YELLOW_300);
        colorMap.put("%yellow_500%", ColorCodes.YELLOW_500);
        colorMap.put("%yellow_700%", ColorCodes.YELLOW_700);

        // Lime
        colorMap.put("%lime_300%", ColorCodes.LIME_300);
        colorMap.put("%lime_500%", ColorCodes.LIME_500);
        colorMap.put("%lime_700%", ColorCodes.LIME_700);

        // Green
        colorMap.put("%green_300%", ColorCodes.GREEN_300);
        colorMap.put("%green_500%", ColorCodes.GREEN_500);
        colorMap.put("%green_700%", ColorCodes.GREEN_700);

        // Emerald
        colorMap.put("%emerald_300%", ColorCodes.EMERALD_300);
        colorMap.put("%emerald_500%", ColorCodes.EMERALD_500);
        colorMap.put("%emerald_700%", ColorCodes.EMERALD_700);

        // Teal
        colorMap.put("%teal_300%", ColorCodes.TEAL_300);
        colorMap.put("%teal_500%", ColorCodes.TEAL_500);
        colorMap.put("%teal_700%", ColorCodes.TEAL_700);

        // Cyan
        colorMap.put("%cyan_300%", ColorCodes.CYAN_300);
        colorMap.put("%cyan_500%", ColorCodes.CYAN_500);
        colorMap.put("%cyan_700%", ColorCodes.CYAN_700);

        // Sky
        colorMap.put("%sky_300%", ColorCodes.SKY_300);
        colorMap.put("%sky_500%", ColorCodes.SKY_500);
        colorMap.put("%sky_700%", ColorCodes.SKY_700);

        // Blue
        colorMap.put("%blue_300%", ColorCodes.BLUE_300);
        colorMap.put("%blue_500%", ColorCodes.BLUE_500);
        colorMap.put("%blue_700%", ColorCodes.BLUE_700);

        // Indigo
        colorMap.put("%indigo_300%", ColorCodes.INDIGO_300);
        colorMap.put("%indigo_500%", ColorCodes.INDIGO_500);
        colorMap.put("%indigo_700%", ColorCodes.INDIGO_700);

        // Violet
        colorMap.put("%violet_300%", ColorCodes.VIOLET_300);
        colorMap.put("%violet_500%", ColorCodes.VIOLET_500);
        colorMap.put("%violet_700%", ColorCodes.VIOLET_700);

        // Purple
        colorMap.put("%purple_300%", ColorCodes.PURPLE_300);
        colorMap.put("%purple_500%", ColorCodes.PURPLE_500);
        colorMap.put("%purple_700%", ColorCodes.PURPLE_700);

        // Fuchsia
        colorMap.put("%fuchsia_300%", ColorCodes.FUCHSIA_300);
        colorMap.put("%fuchsia_500%", ColorCodes.FUCHSIA_500);
        colorMap.put("%fuchsia_700%", ColorCodes.FUCHSIA_700);

        // Pink
        colorMap.put("%pink_300%", ColorCodes.PINK_300);
        colorMap.put("%pink_500%", ColorCodes.PINK_500);
        colorMap.put("%pink_700%", ColorCodes.PINK_700);

        // Rose
        colorMap.put("%rose_300%", ColorCodes.ROSE_300);
        colorMap.put("%rose_500%", ColorCodes.ROSE_500);
        colorMap.put("%rose_700%", ColorCodes.ROSE_700);
    }


   public static void init(LangManager langManager) {
       lang = langManager;
   }

    public static void error(CommandSender sender, String file, String msg) {
        if (sender instanceof Player p) {
            String errorMessage = lang.getMessage(file, msg);
            if (errorMessage.startsWith("Message not found:") || errorMessage.startsWith("File not found:")) {

                WoSCore.getPlugin(WoSCore.class).getLogger().warning("Error message retrieval issue: " + errorMessage);
            }
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
            p.sendMessage(lang.getMessage("general", "prefix.error") + errorMessage);
        }
    }

    public static void info(CommandSender sender, String file, String msg) {
        if (sender instanceof Player p) {
            String infoMessage = lang.getMessage(file, msg);
            if (infoMessage.startsWith("Message not found:") || infoMessage.startsWith("File not found:")) {
                WoSCore.getPlugin(WoSCore.class).getLogger().warning("Error message retrieval issue: " + infoMessage);
            }
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
            p.sendMessage(lang.getMessage("general", "prefix.info") + infoMessage);
        }
    }

    public static void success(CommandSender sender, String file, String msg, String... replacements) {
        // Fetch the base message from the language file
        String message = lang.getMessage(file, msg);

        // Apply replacements dynamically
        for (int i = 0; i < replacements.length; i += 2) {
            // Ensure there's an even number of replacement arguments (oldChar, value pairs)
            if (i + 1 < replacements.length) {
                message = message.replace(replacements[i], replacements[i + 1]);
            }
        }

        // Add the general prefix and send the final message
        String newMessage = lang.getMessage("general", "prefix.general") + message;
        sender.sendMessage(replaceColorPlaceholders(newMessage));
    }

    public static String replaceColorPlaceholders(String message) {
        // Replace placeholders like %lightblue% with the actual color code from colorMap
        for (Map.Entry<String, String> entry : colorMap.entrySet()) {
            message = message.replace(entry.getKey(), entry.getValue());
        }
        return translateHexColorCodes(message);
    }

    public static String translateHexColorCodes(String message) {
        // Regular expression to match &# followed by exactly six hex characters
        Pattern hexPattern = Pattern.compile("#([A-Fa-f0-9]{6})");
        Matcher matcher = hexPattern.matcher(message);
        StringBuffer buffer = new StringBuffer();

        // Find each match and replace it with Minecraft's hex color format
        while (matcher.find()) {
            String hex = matcher.group(1);  // Get the matched hex code
            String replacement = "§x§" + hex.charAt(0) + "§" + hex.charAt(1) + "§" + hex.charAt(2) + "§" + hex.charAt(3) + "§" + hex.charAt(4) + "§" + hex.charAt(5);
            matcher.appendReplacement(buffer, replacement);  // Replace the match
        }
        matcher.appendTail(buffer);  // Append the rest of the message

        return buffer.toString();
    }




    public static void successMsg(CommandSender sender, String file, String msg) {
        // Fetch the message from the lang file and prefix
        String message = lang.getMessage("general", "prefix.general") + lang.getMessage(file, msg);
        // Send the formatted message with color placeholders replaced and hex codes translated
        sender.sendMessage(replaceColorPlaceholders(message));
    }
    public static void successMsg1Value(CommandSender sender,String file, String msg, String oldChar, String value) {
       String message = lang.getMessage(file, msg).replace(oldChar, value);
       String newMessage = lang.getMessage("general","prefix.general")+message;
       sender.sendMessage(replaceColorPlaceholders(newMessage));
    }
    public static void successMsg2Values(CommandSender sender,String file, String msg, String oldChar1, String value1, String oldChar2, String value2) {
        String message = lang.getMessage(file, msg).replace(oldChar1, value1).replace(oldChar2, value2);
        String newMessage = lang.getMessage("general","prefix.general")+message;
        sender.sendMessage(replaceColorPlaceholders(newMessage));
    }
    public static void successMsg3Values(CommandSender sender,String file, String msg, String oldChar1, String value1, String oldChar2, String value2, String oldChar3, String value3) {
        String message = lang.getMessage(file, msg).replace(oldChar1, value1).replace(oldChar2, value2).replace(oldChar3, value3);
        String newMessage = lang.getMessage("general","prefix.general")+message;
        sender.sendMessage(replaceColorPlaceholders(newMessage));
    }

    public static void successMsg4Values(CommandSender sender,String file, String msg, String oldChar1, String value1, String oldChar2, String value2, String oldChar3, String value3, String oldChar4, String value4) {
        String message = lang.getMessage(file, msg).replace(oldChar1, value1).replace(oldChar2, value2).replace(oldChar3, value3).replace(oldChar4, value4);
        String newMessage = lang.getMessage("general","prefix.general")+message;
        sender.sendMessage(replaceColorPlaceholders(newMessage));
    }

    public static void successMsg5Values(CommandSender sender,String file, String msg, String oldChar1, String value1, String oldChar2, String value2, String oldChar3, String value3, String oldChar4, String value4,  String oldChar5, String value5) {
        String message = lang.getMessage(file, msg).replace(oldChar1, value1).replace(oldChar2, value2).replace(oldChar3, value3).replace(oldChar4, value4).replace(oldChar5, value5);
        String newMessage = lang.getMessage("general","prefix.general")+message;
        sender.sendMessage(replaceColorPlaceholders(newMessage));
    }




}
