package betterserver.commands;

import betterserver.Main;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.Objects;

@SuppressWarnings("ALL")
public class Back implements CommandExecutor, Listener {
    final Main plugin;

    public Back(Main plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(this.plugin.getCommand("back")).setExecutor(this);
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);

    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        //Check if command is enabled:
        if(this.plugin.getConfig().getStringList("DisabledCommands").contains("back")) {
            sender.sendMessage("§4§lThis command is currently disabled, if you wish to override this command you are free to do.");
            return true;
        }
        //Done :D
        if(sender instanceof Player player) {
            Location loc = player.getLastDeathLocation();
            if(loc == null) {
                sender.sendMessage("§4§lCannot find your last death location.");
            } else {
                player.teleport(loc);
                sender.sendMessage("§e§lYou teleported to your last death location.");
            }

        } else {
            sender.sendMessage("§4§lYou must be a player to execute this command.");
        }
        return true;
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        if(player.hasPermission("permissions.back")) {
            player.sendMessage("§e§lYou just died! Type /back to go to your last death location!");
        }
    }
}