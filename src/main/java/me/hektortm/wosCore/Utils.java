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
        colorMap.put("%red_200%", ColorCodes.RED_200);
        colorMap.put("%red_300%", ColorCodes.RED_300);
        colorMap.put("%red_400%", ColorCodes.RED_400);
        colorMap.put("%red_500%", ColorCodes.RED_500);
        colorMap.put("%red_600%", ColorCodes.RED_600);
        colorMap.put("%red_700%", ColorCodes.RED_700);
        colorMap.put("%red_800%", ColorCodes.RED_800);
        colorMap.put("%red_900%", ColorCodes.RED_900);
        colorMap.put("%red_950%", ColorCodes.RED_950);

        // Orange
        colorMap.put("%orange_200%", ColorCodes.ORANGE_200);
        colorMap.put("%orange_300%", ColorCodes.ORANGE_300);
        colorMap.put("%orange_400%", ColorCodes.ORANGE_400);
        colorMap.put("%orange_500%", ColorCodes.ORANGE_500);
        colorMap.put("%orange_600%", ColorCodes.ORANGE_600);
        colorMap.put("%orange_700%", ColorCodes.ORANGE_700);
        colorMap.put("%orange_800%", ColorCodes.ORANGE_800);
        colorMap.put("%orange_900%", ColorCodes.ORANGE_900);
        colorMap.put("%orange_950%", ColorCodes.ORANGE_950);

        // Amber
        colorMap.put("%amber_200%", ColorCodes.AMBER_200);
        colorMap.put("%amber_300%", ColorCodes.AMBER_300);
        colorMap.put("%amber_400%", ColorCodes.AMBER_400);
        colorMap.put("%amber_500%", ColorCodes.AMBER_500);
        colorMap.put("%amber_600%", ColorCodes.AMBER_600);
        colorMap.put("%amber_700%", ColorCodes.AMBER_700);
        colorMap.put("%amber_800%", ColorCodes.AMBER_800);
        colorMap.put("%amber_900%", ColorCodes.AMBER_900);
        colorMap.put("%amber_950%", ColorCodes.AMBER_950);

        // Yellow
        colorMap.put("%yellow_200%", ColorCodes.YELLOW_200);
        colorMap.put("%yellow_300%", ColorCodes.YELLOW_300);
        colorMap.put("%yellow_400%", ColorCodes.YELLOW_400);
        colorMap.put("%yellow_500%", ColorCodes.YELLOW_500);
        colorMap.put("%yellow_600%", ColorCodes.YELLOW_600);
        colorMap.put("%yellow_700%", ColorCodes.YELLOW_700);
        colorMap.put("%yellow_800%", ColorCodes.YELLOW_800);
        colorMap.put("%yellow_900%", ColorCodes.YELLOW_900);
        colorMap.put("%yellow_950%", ColorCodes.YELLOW_950);

        colorMap.put("%lime_200%", ColorCodes.LIME_200);
        colorMap.put("%lime_300%", ColorCodes.LIME_300);
        colorMap.put("%lime_400%", ColorCodes.LIME_400);
        colorMap.put("%lime_500%", ColorCodes.LIME_500);
        colorMap.put("%lime_600%", ColorCodes.LIME_600);
        colorMap.put("%lime_700%", ColorCodes.LIME_700);
        colorMap.put("%lime_800%", ColorCodes.LIME_800);
        colorMap.put("%lime_900%", ColorCodes.LIME_900);
        colorMap.put("%lime_950%", ColorCodes.LIME_950);

        colorMap.put("%green_200%", ColorCodes.GREEN_200);
        colorMap.put("%green_300%", ColorCodes.GREEN_300);
        colorMap.put("%green_400%", ColorCodes.GREEN_400);
        colorMap.put("%green_500%", ColorCodes.GREEN_500);
        colorMap.put("%green_600%", ColorCodes.GREEN_600);
        colorMap.put("%green_700%", ColorCodes.GREEN_700);
        colorMap.put("%green_800%", ColorCodes.GREEN_800);
        colorMap.put("%green_900%", ColorCodes.GREEN_900);
        colorMap.put("%green_950%", ColorCodes.GREEN_950);

        colorMap.put("%emerald_200%", ColorCodes.EMERALD_200);
        colorMap.put("%emerald_300%", ColorCodes.EMERALD_300);
        colorMap.put("%emerald_400%", ColorCodes.EMERALD_400);
        colorMap.put("%emerald_500%", ColorCodes.EMERALD_500);
        colorMap.put("%emerald_600%", ColorCodes.EMERALD_600);
        colorMap.put("%emerald_700%", ColorCodes.EMERALD_700);
        colorMap.put("%emerald_800%", ColorCodes.EMERALD_800);
        colorMap.put("%emerald_900%", ColorCodes.EMERALD_900);
        colorMap.put("%emerald_950%", ColorCodes.EMERALD_950);

        colorMap.put("%teal_200%", ColorCodes.TEAL_200);
        colorMap.put("%teal_300%", ColorCodes.TEAL_300);
        colorMap.put("%teal_400%", ColorCodes.TEAL_400);
        colorMap.put("%teal_500%", ColorCodes.TEAL_500);
        colorMap.put("%teal_600%", ColorCodes.TEAL_600);
        colorMap.put("%teal_700%", ColorCodes.TEAL_700);
        colorMap.put("%teal_800%", ColorCodes.TEAL_800);
        colorMap.put("%teal_900%", ColorCodes.TEAL_900);
        colorMap.put("%teal_950%", ColorCodes.TEAL_950);

        colorMap.put("%cyan_200%", ColorCodes.CYAN_200);
        colorMap.put("%cyan_300%", ColorCodes.CYAN_300);
        colorMap.put("%cyan_400%", ColorCodes.CYAN_400);
        colorMap.put("%cyan_500%", ColorCodes.CYAN_500);
        colorMap.put("%cyan_600%", ColorCodes.CYAN_600);
        colorMap.put("%cyan_700%", ColorCodes.CYAN_700);
        colorMap.put("%cyan_800%", ColorCodes.CYAN_800);
        colorMap.put("%cyan_900%", ColorCodes.CYAN_900);
        colorMap.put("%cyan_950%", ColorCodes.CYAN_950);

        colorMap.put("%sky_200%", ColorCodes.SKY_200);
        colorMap.put("%sky_300%", ColorCodes.SKY_300);
        colorMap.put("%sky_400%", ColorCodes.SKY_400);
        colorMap.put("%sky_500%", ColorCodes.SKY_500);
        colorMap.put("%sky_600%", ColorCodes.SKY_600);
        colorMap.put("%sky_700%", ColorCodes.SKY_700);
        colorMap.put("%sky_800%", ColorCodes.SKY_800);
        colorMap.put("%sky_900%", ColorCodes.SKY_900);
        colorMap.put("%sky_950%", ColorCodes.SKY_950);

        colorMap.put("%blue_200%", ColorCodes.BLUE_200);
        colorMap.put("%blue_300%", ColorCodes.BLUE_300);
        colorMap.put("%blue_400%", ColorCodes.BLUE_400);
        colorMap.put("%blue_500%", ColorCodes.BLUE_500);
        colorMap.put("%blue_600%", ColorCodes.BLUE_600);
        colorMap.put("%blue_700%", ColorCodes.BLUE_700);
        colorMap.put("%blue_800%", ColorCodes.BLUE_800);
        colorMap.put("%blue_900%", ColorCodes.BLUE_900);
        colorMap.put("%blue_950%", ColorCodes.BLUE_950);

        colorMap.put("%indigo_200%", ColorCodes.INDIGO_200);
        colorMap.put("%indigo_300%", ColorCodes.INDIGO_300);
        colorMap.put("%indigo_400%", ColorCodes.INDIGO_400);
        colorMap.put("%indigo_500%", ColorCodes.INDIGO_500);
        colorMap.put("%indigo_600%", ColorCodes.INDIGO_600);
        colorMap.put("%indigo_700%", ColorCodes.INDIGO_700);
        colorMap.put("%indigo_800%", ColorCodes.INDIGO_800);
        colorMap.put("%indigo_900%", ColorCodes.INDIGO_900);
        colorMap.put("%indigo_950%", ColorCodes.INDIGO_950);

        colorMap.put("%violet_200%", ColorCodes.VIOLET_200);
        colorMap.put("%violet_300%", ColorCodes.VIOLET_300);
        colorMap.put("%violet_400%", ColorCodes.VIOLET_400);
        colorMap.put("%violet_500%", ColorCodes.VIOLET_500);
        colorMap.put("%violet_600%", ColorCodes.VIOLET_600);
        colorMap.put("%violet_700%", ColorCodes.VIOLET_700);
        colorMap.put("%violet_800%", ColorCodes.VIOLET_800);
        colorMap.put("%violet_900%", ColorCodes.VIOLET_900);
        colorMap.put("%violet_950%", ColorCodes.VIOLET_950);

        colorMap.put("%purple_200%", ColorCodes.PURPLE_200);
        colorMap.put("%purple_300%", ColorCodes.PURPLE_300);
        colorMap.put("%purple_400%", ColorCodes.PURPLE_400);
        colorMap.put("%purple_500%", ColorCodes.PURPLE_500);
        colorMap.put("%purple_600%", ColorCodes.PURPLE_600);
        colorMap.put("%purple_700%", ColorCodes.PURPLE_700);
        colorMap.put("%purple_800%", ColorCodes.PURPLE_800);
        colorMap.put("%purple_900%", ColorCodes.PURPLE_900);
        colorMap.put("%purple_950%", ColorCodes.PURPLE_950);

        colorMap.put("%fuchsia_200%", ColorCodes.FUCHSIA_200);
        colorMap.put("%fuchsia_300%", ColorCodes.FUCHSIA_300);
        colorMap.put("%fuchsia_400%", ColorCodes.FUCHSIA_400);
        colorMap.put("%fuchsia_500%", ColorCodes.FUCHSIA_500);
        colorMap.put("%fuchsia_600%", ColorCodes.FUCHSIA_600);
        colorMap.put("%fuchsia_700%", ColorCodes.FUCHSIA_700);
        colorMap.put("%fuchsia_800%", ColorCodes.FUCHSIA_800);
        colorMap.put("%fuchsia_900%", ColorCodes.FUCHSIA_900);
        colorMap.put("%fuchsia_950%", ColorCodes.FUCHSIA_950);

        colorMap.put("%pink_200%", ColorCodes.PINK_200);
        colorMap.put("%pink_300%", ColorCodes.PINK_300);
        colorMap.put("%pink_400%", ColorCodes.PINK_400);
        colorMap.put("%pink_500%", ColorCodes.PINK_500);
        colorMap.put("%pink_600%", ColorCodes.PINK_600);
        colorMap.put("%pink_700%", ColorCodes.PINK_700);
        colorMap.put("%pink_800%", ColorCodes.PINK_800);
        colorMap.put("%pink_900%", ColorCodes.PINK_900);
        colorMap.put("%pink_950%", ColorCodes.PINK_950);

        colorMap.put("%rose_200%", ColorCodes.ROSE_200);
        colorMap.put("%rose_300%", ColorCodes.ROSE_300);
        colorMap.put("%rose_400%", ColorCodes.ROSE_400);
        colorMap.put("%rose_500%", ColorCodes.ROSE_500);
        colorMap.put("%rose_600%", ColorCodes.ROSE_600);
        colorMap.put("%rose_700%", ColorCodes.ROSE_700);
        colorMap.put("%rose_800%", ColorCodes.ROSE_800);
        colorMap.put("%rose_900%", ColorCodes.ROSE_900);
        colorMap.put("%rose_950%", ColorCodes.ROSE_950);
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
