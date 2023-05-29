package me.dave.actionbarannouncements;

import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConfigManager {
    private boolean isEnabled;
    private boolean randomOrder;
    private int interval;
    private final HashMap<String, List<String>> worldAnnouncements= new HashMap<>();
    private boolean sendOnJoin;
    private String onJoinMessage;


    public ConfigManager() {
        ActionBarAnnouncements plugin = ActionBarAnnouncements.getInstance();
        plugin.saveDefaultConfig();

        reloadConfig(plugin);
    }

    public void reloadConfig(ActionBarAnnouncements plugin) {
        FileConfiguration config = plugin.getConfig();
        worldAnnouncements.clear();

        isEnabled = config.getBoolean("enabled", true);
        randomOrder = config.getBoolean("randomOrder", false);
        interval = config.getInt("interval", 20);

        for (String world : config.getConfigurationSection("announcements").getKeys(false)) {
            worldAnnouncements.put(world, config.getStringList("announcements." + world));
        }

        sendOnJoin = config.getBoolean("send-on-join", false);
        onJoinMessage = config.getString("join-announcement");

        ActionBarAnnouncements.getAnnouncementRunner().restartAnnouncements();
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public boolean isRandomOrder() {
        return randomOrder;
    }

    public int getInterval() {
        return interval;
    }

    @NotNull
    public List<String> getGlobalAnnouncements() {
        List<String> messages = worldAnnouncements.get("global");
        if (messages == null) return new ArrayList<>();
        return messages;
    }

    @Nullable
    public List<String> getWorldAnnouncements(String world) {
        return worldAnnouncements.get(world);
    }

    public boolean sendOnJoin() {
        return sendOnJoin;
    }

    public String getJoinAnnouncement() {
        return onJoinMessage;
    }
}
