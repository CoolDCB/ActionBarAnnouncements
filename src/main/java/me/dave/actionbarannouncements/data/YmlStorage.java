package me.dave.actionbarannouncements.data;

import me.dave.actionbarannouncements.ActionBarAnnouncements;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.enchantedskies.EnchantedStorage.Storage;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class YmlStorage implements Storage<ActionBarUser> {
    private final File dataFolder = new File(ActionBarAnnouncements.getInstance().getDataFolder(), "data");

    @Override
    public ActionBarUser load(UUID uuid) {
        ConfigurationSection configurationSection = loadOrCreateFile(uuid);
        String name = configurationSection.getString("name");
        boolean muted = configurationSection.getBoolean("muted");
        return new ActionBarUser(uuid, name, muted);
    }

    @Override
    public void save(ActionBarUser actionBarUser) {
        YamlConfiguration yamlConfiguration = loadOrCreateFile(actionBarUser.getUUID());

        String username = actionBarUser.getUsername();
        if (username == null) username = "Error: Could not get username, will load when the player next joins";
        yamlConfiguration.set("name", username);
        yamlConfiguration.set("muted", actionBarUser.isMuted());
        File file = new File(dataFolder, actionBarUser.getUUID().toString() + ".yml");
        try {
            yamlConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public YamlConfiguration loadOrCreateFile(UUID uuid) {
        File file = new File(dataFolder, uuid.toString() + ".yml");
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
        if (yamlConfiguration.getString("name") == null) {
            Player player = Bukkit.getPlayer(uuid);

            String username;
            if (player != null) username = player.getName();
            else username = "Error: Could not get username, will load when the player next joins";
            yamlConfiguration.set("name", username);
            yamlConfiguration.set("muted", false);
            try {
                yamlConfiguration.save(file);
            } catch (IOException err) {
                err.printStackTrace();
            }
        }
        return yamlConfiguration;
    }
}
