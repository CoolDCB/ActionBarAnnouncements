package me.dave.actionbarannouncements.data;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.enchantedskies.EnchantedStorage.IOHandler;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class DataManager {
    private final IOHandler<ActionBarUser> ioHandler = new IOHandler<>(new YmlStorage());
    private final HashMap<UUID, ActionBarUser> uuidToActionBarUser = new HashMap<>();
    private final HashSet<UUID> mutedPlayers = new HashSet<>();

    public ActionBarUser getActionBarUser(@NotNull UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        if (player == null) return null;

        ActionBarUser actionBarUser = uuidToActionBarUser.get(uuid);
        if (actionBarUser == null) {
            actionBarUser = new ActionBarUser(uuid, player.getName(), false);
            uuidToActionBarUser.put(uuid, actionBarUser);
        }
        return actionBarUser;
    }

    public CompletableFuture<ActionBarUser> loadActionBarUser(UUID uuid) {
        return ioHandler.loadPlayer(uuid).thenApply(actionBarUser -> {
            uuidToActionBarUser.put(uuid, actionBarUser);
            if (actionBarUser.isMuted()) mutedPlayers.add(actionBarUser.getUUID());
            return actionBarUser;
        });
    }

    public void unloadActionBarUser(UUID uuid) {
        uuidToActionBarUser.remove(uuid);
        mutedPlayers.remove(uuid);
    }

    public void saveActionBarUser(ActionBarUser user) {
        ioHandler.savePlayer(user);
    }

    public HashSet<UUID> getMutedPlayers() {
        return mutedPlayers;
    }

    public void addMutedPlayer(UUID uuid) {
        mutedPlayers.add(uuid);
    }

    public void removeMutedPlayer(UUID uuid) {
        mutedPlayers.remove(uuid);
    }

    public IOHandler<ActionBarUser> getIoHandler() {
        return ioHandler;
    }
}
