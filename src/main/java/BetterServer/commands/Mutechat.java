package BetterServer.commands;

import BetterServer.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Mutechat implements CommandExecutor, Listener, TabCompleter{
    final Main plugin;
    public boolean chatmuted;

    public Mutechat(Main plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(this.plugin.getCommand("mutechat")).setExecutor(this);
        Objects.requireNonNull(this.plugin.getCommand("mutechat")).setDescription(plugin.getMessage("mutechatCommandDescription"));
        Objects.requireNonNull(this.plugin.getCommand("mutechat")).setUsage(plugin.getMessage("mutechatCommandUsage"));
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        //Check if command is enabled:
        if (this.plugin.getConfig().getStringList("DisabledCommands").contains(label)) {

            sender.sendMessage(plugin.getMessage("commandDisabled"));
            return true;
        }
        //Done :D
        chatmuted = ! chatmuted;
        if (sender instanceof ConsoleCommandSender) {
            if (chatmuted) {
                sender.sendMessage(plugin.getMessage("mutechatCommandInstanceOfConsoleMutedChat"));
            } else {
                sender.sendMessage(plugin.getMessage("mutechatCommandInstanceOfConsoleUnmutedChat"));
            }
        }
        for (Player p : Bukkit.getOnlinePlayers()) {

            if (chatmuted) {
                if (p.hasPermission("permissions.chatmuted.bypass")) {
                    p.sendMessage(plugin.getMessage("mutechatCommandChatMutedHasPermission").replace("{0}", sender.getName()));
                } else {
                    p.sendMessage(plugin.getMessage("mutechatCommandChatMuted").replace("{0}", sender.getName()));
                }
            } else {
                p.sendMessage(plugin.getMessage("mutechatCommandChatUnmuted").replace("{0}", sender.getName()));
            }
        }
        return true;
    }

    @EventHandler
    @Deprecated
    public void onPlayerChat(PlayerChatEvent event) {
        Player player = event.getPlayer();
        if (chatmuted) {
            if (! (player.hasPermission("permission.mutechat.bypass"))) {
                player.sendMessage("§4§lYou cannot speak now, chat is currently muted.");
                event.setCancelled(true);
            }
        }
    }

    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length >= 1) {
            return Collections.emptyList();
        }
        return null;
    }

}