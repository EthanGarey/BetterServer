package BetterServer.Commands.AdminEssentials;

import BetterServer.Main;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class Back implements CommandExecutor {
    Main plugin;

    public Back(Main plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(this.plugin.getCommand("back")).setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player player) {
            Location loc = player.getLastDeathLocation();
            if (loc == null) {
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
}