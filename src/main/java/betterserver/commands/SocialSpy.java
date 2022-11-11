package betterserver.commands;

import betterserver.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class SocialSpy implements CommandExecutor, Listener, TabCompleter{
    public final List<Player> SocialSpyUsers = new ArrayList<>();
    final Main plugin;

    public SocialSpy(Main plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(this.plugin.getCommand("socialspy")).setExecutor(this);
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);

    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        //Check if command is enabled:
        if (this.plugin.getConfig().getStringList("DisabledCommands").contains("socialspy")) {
            sender.sendMessage("§4§lThis command is currently disabled, if you wish to override this command you are free to do.");
            return true;
        }
        //Done :D
        if (sender instanceof Player player) {
            if (! SocialSpyUsers.contains(player)) {
                SocialSpyUsers.add(player);
                player.sendMessage("&d&l[SocialSpy]&e&l is now &a&lEnabled ".replace('&', '§'));
            } else {
                SocialSpyUsers.remove(player);
                player.sendMessage("&d&l[SocialSpy]&e&l is now &4&lDisabled ".replace('&', '§'));
            }


        } else {
            sender.sendMessage("&4&lOnly players can execute this command!".replace('&', '§'));
        }
        return false;
    }

    @EventHandler
    public void onPlayerLeaveEvent(PlayerKickEvent event) {
        Player player = event.getPlayer();
        this.plugin.socialSpy.SocialSpyUsers.remove(player);
        this.plugin.msg.lastMessageSender.remove(player);
    }

    @EventHandler
    public void onPlayerLeaveEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        this.plugin.socialSpy.SocialSpyUsers.remove(player);
        this.plugin.msg.lastMessageSender.remove(player);
    }

    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length >= 1) {
            return Collections.emptyList();
        }
        return null;
    }
}