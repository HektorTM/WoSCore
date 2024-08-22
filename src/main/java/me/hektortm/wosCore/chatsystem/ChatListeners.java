package me.hektortm.wosCore.chatsystem;


import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListeners implements Listener {
    // TODO: WIP
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        // Create the player name component with hover text
        Component playerComponent = Component.text(player.getName(), NamedTextColor.GOLD)
                .hoverEvent(HoverEvent.showText(Component.text("This is " + player.getName() + "'s hover text")));

        // Create the message component
        Component messageComponent = Component.text(": " + message, NamedTextColor.WHITE);

        // Combine components
        Component finalMessage = playerComponent.append(messageComponent);

        // Cancel the default message and send the custom message
        event.setCancelled(true);
        event.getRecipients().forEach(recipient -> recipient.sendMessage(finalMessage));

    }

}
