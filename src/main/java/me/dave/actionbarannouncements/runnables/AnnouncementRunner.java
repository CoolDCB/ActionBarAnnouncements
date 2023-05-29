package me.dave.actionbarannouncements.runnables;

import me.dave.actionbarannouncements.ActionBarAnnouncements;
import me.dave.actionbarannouncements.runnables.IntervalTask;
import me.dave.chatcolorhandler.ChatColorHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

public class AnnouncementRunner {
    private BukkitTask aaTask;

    public void restartAnnouncements() {
        stopAnnouncements();
        aaTask = Bukkit.getScheduler().runTaskTimerAsynchronously(ActionBarAnnouncements.getInstance(), new IntervalTask(), 1L, 20L * ActionBarAnnouncements.getConfigManager().getInterval());
    }

    public void stopAnnouncements() {
        if (aaTask != null && !aaTask.isCancelled()) {
            aaTask.cancel();
            aaTask = null;
        }
    }

    public void sendAnnouncement(Player player, String message) {
        ChatColorHandler.sendActionBarMessage(player, parsePlaceholders(player, message));
    }

    public void sendAnnouncement(List<Player> players, String message) {
        players.forEach(player -> sendAnnouncement(player, message));
    }

    public String parsePlaceholders(Player player, String string) {
        string = string
            .replace("%player%", player.getName())
            .replace("%online%", String.valueOf(Bukkit.getServer().getOnlinePlayers().size()));

        return string;
    }
}
