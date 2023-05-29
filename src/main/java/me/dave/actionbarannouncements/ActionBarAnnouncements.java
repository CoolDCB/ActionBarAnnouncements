package me.dave.actionbarannouncements;

import me.dave.actionbarannouncements.runnables.AnnouncementRunner;
import org.bukkit.plugin.java.JavaPlugin;

public final class ActionBarAnnouncements extends JavaPlugin {
    private static ActionBarAnnouncements plugin;
    private static ConfigManager configManager;
    private static AnnouncementRunner announcementRunner;

    @Override
    public void onEnable() {
        plugin = this;
        configManager = new ConfigManager();
        announcementRunner = new AnnouncementRunner();
    }

    public static ActionBarAnnouncements getInstance() {
        return plugin;
    }

    public static ConfigManager getConfigManager() {
        return configManager;
    }

    public static AnnouncementRunner getAnnouncementRunner() {
        return announcementRunner;
    }
}
