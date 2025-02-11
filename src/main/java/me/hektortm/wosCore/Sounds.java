package me.hektortm.wosCore;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Sounds {

    public static void error(Player p){
        p.playSound(p, Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO, 1F, 1F);
    }

    public static void info(Player p){
        p.playSound(p, Sound.BLOCK_NOTE_BLOCK_BANJO, 1F, 1F);
    }

    public static void receiveSucess(Player p){
        p.playSound(p, Sound.BLOCK_NOTE_BLOCK_CHIME, 1F, 0.8F);
        p.playSound(p, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);
    }

    public static void completed(Player p){
        p.playSound(p, Sound.ITEM_TRIDENT_THUNDER, 1F, 2F);
        p.playSound(p, Sound.BLOCK_NOTE_BLOCK_BANJO, 1F, 0.5F);
        p.playSound(p, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);
    }
}
