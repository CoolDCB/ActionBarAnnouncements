package me.dave.actionbarannouncements.runnables;

import me.dave.actionbarannouncements.ActionBarAnnouncements;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class AnnouncementTask extends BukkitRunnable {
    private final List<Player> players;
    private final String message;
    private int duration;

    public AnnouncementTask(List<Player> players, String message, int duration) {
        this.players = players;
        this.message = message;
        this.duration = duration;
    }

    @Override
    public void run() {
        if (duration > 0) {
            duration--;
            ActionBarAnnouncements.getAnnouncementRunner().sendAnnouncement(players, message);
        } else {
            cancel();
        }
    }
}
