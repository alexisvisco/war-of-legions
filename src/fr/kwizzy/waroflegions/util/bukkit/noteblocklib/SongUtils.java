package fr.kwizzy.waroflegions.util.bukkit.noteblocklib;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Par Alexis le 04/10/2016.
 */

public class SongUtils {

    static HashMap<String, ArrayList<SongPlayer>> playingSongs = new HashMap<String, ArrayList<SongPlayer>>();
    private static HashMap<String, Byte> playerVolume = new HashMap<String, Byte>();

    public static boolean isReceivingSong(Player p) {
        return (playingSongs.get(p.getName()) != null) && (!playingSongs.get(p.getName()).isEmpty());
    }

    public static void stopPlaying(Player p) {
        if (playingSongs.get(p.getName()) == null) {
            return;
        }
        for (SongPlayer s : playingSongs.get(p.getName())) {
            s.removePlayer(p);
        }
    }

    public static void setPlayerVolume(Player p, byte volume) {
        playerVolume.put(p.getName(), volume);
    }

    public static byte getPlayerVolume(Player p) {
        Byte b = playerVolume.get(p.getName());
        if (b == null) {
            b = 100;
            playerVolume.put(p.getName(), b);
        }
        return b;
    }

    public static void clearSongs(){
        Bukkit.getOnlinePlayers().forEach(SongUtils::stopPlaying);
    }

}
