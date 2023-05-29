package me.dave.actionbarannouncements;

import me.dave.actionbarannouncements.commands.ActionBarAnnouncerCmd;
import me.dave.actionbarannouncements.data.DataManager;
import me.dave.actionbarannouncements.events.PlayerEvents;
import me.dave.actionbarannouncements.runnables.AnnouncementRunner;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class ActionBarAnnouncements extends JavaPlugin {
    private static ActionBarAnnouncements plugin;
    private static ConfigManager configManager;
    private static DataManager dataManager;
    private static AnnouncementRunner announcementRunner;

    @Override
    public void onEnable() {
        plugin = this;
        configManager = new ConfigManager();
        dataManager = new DataManager();
        announcementRunner = new AnnouncementRunner();

        Listener[] listeners = new Listener[] {
            new PlayerEvents()
        };
        registerEvents(listeners);

        getCommand("actionbarannouncer").setExecutor(new ActionBarAnnouncerCmd());
    }

    @Override
    public void onDisable() {
        dataManager.getIoHandler().disableIOHandler();
    }

    private void registerEvents(Listener[] listeners) {
        for (Listener listener : listeners) {
            getServer().getPluginManager().registerEvents(listener, this);
        }
    }

    public static ActionBarAnnouncements getInstance() {
        return plugin;
    }

    public static ConfigManager getConfigManager() {
        return configManager;
    }

    public static DataManager getDataManager() {
        return dataManager;
    }

    public static AnnouncementRunner getAnnouncementRunner() {
        return announcementRunner;
    }
}
