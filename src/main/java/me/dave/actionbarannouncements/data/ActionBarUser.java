package me.dave.actionbarannouncements.data;

import me.dave.actionbarannouncements.ActionBarAnnouncements;

import java.util.UUID;

public class ActionBarUser {
    private final UUID uuid;
    private String username;
    private boolean muted;

    public ActionBarUser(UUID uuid, String username, boolean muted) {
        this.uuid = uuid;
        this.username = username;
        this.muted = muted;
    }

    public void setUsername(String username) {
        this.username = username;
        ActionBarAnnouncements.getDataManager().saveActionBarUser(this);
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
        if (muted) ActionBarAnnouncements.getDataManager().addMutedPlayer(uuid);
        else ActionBarAnnouncements.getDataManager().removeMutedPlayer(uuid);
        ActionBarAnnouncements.getDataManager().saveActionBarUser(this);
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public String getUsername() {
        return this.username;
    }

    public boolean isMuted() {
        return this.muted;
    }
}
