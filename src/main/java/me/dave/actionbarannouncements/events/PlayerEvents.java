package me.dave.actionbarannouncements.events;

import me.dave.actionbarannouncements.ActionBarAnnouncements;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class PlayerEvents implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        ActionBarAnnouncements.getDataManager().loadActionBarUser(player.getUniqueId()).thenAccept((actionBarUser) -> new BukkitRunnable() {
            @Override
            public void run() {
                actionBarUser.setUsername(player.getName());
            }
        }.runTask(ActionBarAnnouncements.getInstance()));
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        UUID playerUUID = event.getPlayer().getUniqueId();
        ActionBarAnnouncements.getDataManager().unloadActionBarUser(playerUUID);
    }
}
