package me.dave.actionbarannouncements.runnables;

import me.dave.actionbarannouncements.ActionBarAnnouncements;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Random;

public class IntervalTask implements Runnable {
    private int index = 0;

    @Override
    public void run() {
        index++;
        boolean randomize = ActionBarAnnouncements.getConfigManager().isRandomOrder();

        for (World world : Bukkit.getWorlds()) {
            List<String> messages = ActionBarAnnouncements.getConfigManager().getWorldAnnouncements(world.getName());
            if (messages == null) {
                messages = ActionBarAnnouncements.getConfigManager().getGlobalAnnouncements();
                if (messages.isEmpty()) return;
            }
            String message = randomize ? getRandomFromList(messages) : messages.get(index);

            List<Player> players = world.getPlayers();
            if (players.isEmpty()) return;

            Bukkit.getScheduler().runTask(ActionBarAnnouncements.getInstance(), new AnnouncementTask(players, message));
        }
    }

    public <T> T getRandomFromList(List<T> list) {
        return list.get(new Random().nextInt(list.size()));
    }
}