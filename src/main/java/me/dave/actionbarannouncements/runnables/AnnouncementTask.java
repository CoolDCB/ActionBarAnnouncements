package me.dave.actionbarannouncements.runnables;

import me.dave.actionbarannouncements.ActionBarAnnouncements;
import org.bukkit.entity.Player;

import java.util.List;

public class AnnouncementTask implements Runnable {
    private final List<Player> players;
    private final String message;


    public AnnouncementTask(List<Player> players, String message) {
        this.players = players;
        this.message = message;
    }

    @Override
    public void run() {
        players.forEach(player -> ActionBarAnnouncements.getAnnouncementRunner().sendAnnouncement(player, message));
    }
}
