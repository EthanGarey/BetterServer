package betterserver.commands;

import betterserver.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import java.util.Objects;

public class Mutechat implements CommandExecutor, Listener {
    final Main plugin;
    public boolean chatmuted;

    public Mutechat(Main plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(this.plugin.getCommand("mutechat")).setExecutor(this);
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        //Check if command is enabled:
        if(this.plugin.getConfig().getStringList("DisabledCommands").contains("mutechat")) {
            sender.sendMessage("§4§lThis command is currently disabled, if you wish to override this command you are free to do.");
            return true;
        }
        //Done :D
        if(chatmuted) {
            chatmuted = false;
            Bukkit.getConsoleSender().sendMessage("Chat unmuted by " + (sender instanceof Player player ? player.getName() : "Console"));
        } else {
            chatmuted = true;
            Bukkit.getConsoleSender().sendMessage("Chat muted by " + (sender instanceof Player player ? player.getName() : "Console"));


        }
        for (Player p : Bukkit.getOnlinePlayers()) {

            if(chatmuted) {
                if(p.hasPermission("permissions.mutechat.bypass")) {
                    p.sendMessage("§4§lChat has been muted by " + (sender instanceof Player player ? player.getName() : "Console") + (" (You can still chat as you have permission)"));

                } else {
                    p.sendMessage("§4§lChat has been muted by " + (sender instanceof Player player ? player.getName() : "Console"));
                }
            } else {
                p.sendMessage("§e§lChat has been unmuted by " + (sender instanceof Player player ? player.getName() : "Console"));

            }
        }
        return true;
    }

    @EventHandler
    @Deprecated
    public void onPlayerChat(PlayerChatEvent event) {
        Player player = event.getPlayer();
        if(chatmuted) {
            if(!(player.hasPermission("permission.mutechat.bypass"))) {
                player.sendMessage("§4§lYou cannot speak now, chat is currently muted.");
                event.setCancelled(true);
            }
        }
    }

}