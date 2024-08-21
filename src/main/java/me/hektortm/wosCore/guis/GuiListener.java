package me.hektortm.wosCore.guis;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GuiListener implements Listener {
    private final GuiManager guiManager;

    public GuiListener(GuiManager guiManager) {
        this.guiManager = guiManager;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        guiManager.handleInventoryClick(e);
    }
    public void openGui(Player p, String guiName) {
        guiManager.openGui(p, guiName);
    }

}
