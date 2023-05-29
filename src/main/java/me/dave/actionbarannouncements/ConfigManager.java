package me.dave.actionbarannouncements;

import org.bukkit.Bukkit;
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
    private HashMap<String, String> langMessages = new HashMap<>();


    public ConfigManager() {
        ActionBarAnnouncements plugin = ActionBarAnnouncements.getInstance();
        plugin.saveDefaultConfig();

        reloadConfig(plugin);
    }

    public void reloadConfig(ActionBarAnnouncements plugin) {
        plugin.reloadConfig();
        FileConfiguration config = plugin.getConfig();
        worldAnnouncements.clear();
        langMessages.clear();

        isEnabled = config.getBoolean("enabled", true);
        randomOrder = config.getBoolean("random-order", false);
        interval = config.getInt("interval", 20) * 20;

        for (String world : config.getConfigurationSection("announcements").getKeys(false)) {
            worldAnnouncements.put(world, config.getStringList("announcements." + world));
        }

        for (String key : config.getConfigurationSection("messages").getKeys(false)) {
            langMessages.put(key, config.getString("messages." + key));
        }

        sendOnJoin = config.getBoolean("send-on-join", false);
        onJoinMessage = config.getString("join-announcement");

        Bukkit.getScheduler().runTaskLater(plugin, () -> ActionBarAnnouncements.getAnnouncementRunner().restartAnnouncements(), 1);
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

    public String getLangMessage(String messageKey) {
        return langMessages.get(messageKey.toLowerCase());
    }
}
