package me.dave.actionbarannouncements.commands;

import me.dave.actionbarannouncements.ActionBarAnnouncements;
import me.dave.chatcolorhandler.ChatColorHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActionBarAnnouncerCmd implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("announce")) {
                if (!sender.hasPermission("actionbarannouncer.admin.announce")) {
                    ChatColorHandler.sendMessage(sender, ActionBarAnnouncements.getConfigManager().getLangMessage("no-permissions"));
                    return true;
                }

                ActionBarAnnouncements.getAnnouncementRunner().sendAnnouncement(new ArrayList<>(Bukkit.getOnlinePlayers()), String.join(", ", Arrays.copyOfRange(args, 1, args.length)));
            }
            else if (args[0].equalsIgnoreCase("mute")) {
                if (!sender.hasPermission("actionbarannouncer.admin.mute")) {
                    ChatColorHandler.sendMessage(sender, ActionBarAnnouncements.getConfigManager().getLangMessage("no-permissions"));
                    return true;
                }
                if (!(sender instanceof Player player)) {
                    sender.sendMessage("Console cannot run this command!");
                    return true;
                }
                ActionBarAnnouncements.getDataManager().getActionBarUser(player.getUniqueId()).setMuted(true);
                sender.sendMessage(ChatColorHandler.translateAlternateColorCodes(ActionBarAnnouncements.getConfigManager().getLangMessage("muted")));
                return true;
            }
            else if (args[0].equalsIgnoreCase("reload")) {
                if (!sender.hasPermission("actionbarannouncer.admin.reload")) {
                    ChatColorHandler.sendMessage(sender, ActionBarAnnouncements.getConfigManager().getLangMessage("no-permissions"));
                    return true;
                }
                ActionBarAnnouncements.getConfigManager().reloadConfig(ActionBarAnnouncements.getInstance());
                sender.sendMessage(ChatColorHandler.translateAlternateColorCodes(ActionBarAnnouncements.getConfigManager().getLangMessage("reloaded")));
                return true;
            }
            else if (args[0].equalsIgnoreCase("unmute")) {
                if (!sender.hasPermission("actionbarannouncer.admin.mute")) {
                    ChatColorHandler.sendMessage(sender, ActionBarAnnouncements.getConfigManager().getLangMessage("no-permissions"));
                    return true;
                }
                if (!(sender instanceof Player player)) {
                    sender.sendMessage("Console cannot run this command!");
                    return true;
                }
                ActionBarAnnouncements.getDataManager().getActionBarUser(player.getUniqueId()).setMuted(false);
                sender.sendMessage(ChatColorHandler.translateAlternateColorCodes(ActionBarAnnouncements.getConfigManager().getLangMessage("unmuted")));
                return true;
            }
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> tabComplete = new ArrayList<>();
        List<String> wordCompletion = new ArrayList<>();
        boolean wordCompletionSuccess = false;

        if (args.length == 1) {
            if (sender.hasPermission("actionbarannouncer.admin.announce")) tabComplete.add("announce");
            if (sender.hasPermission("actionbarannouncer.mute")) {
                tabComplete.add("mute");
                tabComplete.add("unmute");
            }
            if (sender.hasPermission("actionbarannouncer.admin.reload")) tabComplete.add("reload");
//            if (sender.hasPermission("actionbarannouncer.admin.send")) tabComplete.add("send");
        }

        for (String currTab : tabComplete) {
            int currArg = args.length - 1;
            if (currTab.startsWith(args[currArg])) {
                wordCompletion.add(currTab);
                wordCompletionSuccess = true;
            }
        }
        if (wordCompletionSuccess) return wordCompletion;
        return tabComplete;
    }
}
