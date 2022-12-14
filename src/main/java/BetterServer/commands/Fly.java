package BetterServer.commands;

import BetterServer.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Fly implements CommandExecutor, TabCompleter{
    final Main plugin;

    public Fly(final Main plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(this.plugin.getCommand("fly")).setExecutor(this);
        Objects.requireNonNull(this.plugin.getCommand("fly")).setDescription(plugin.getMessage("flyCommandDescription"));
        Objects.requireNonNull(this.plugin.getCommand("fly")).setUsage(plugin.getMessage("flyCommandUsage"));
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        //Check if command is enabled:
        if (this.plugin.getConfig().getStringList("DisabledCommands").contains(label)) {

            sender.sendMessage(plugin.getMessage("commandDisabled"));
            return true;
        }
        //Done :D
        if (sender instanceof Player player) {

            if (args.length == 0) {
                if (! player.getAllowFlight()) {
                    player.setAllowFlight(true);
                    sender.sendMessage(plugin.getMessage("flightEnabledForPlayer"));
                } else {
                    player.setAllowFlight(false);
                    sender.sendMessage(plugin.getMessage("flightDisabledForPlayer"));
                }
            }
            if (args.length >= 1) {
                Player other = Bukkit.getPlayer(args[0]);
                if (other != null) {
                    if (! other.getAllowFlight()) {
                        other.setAllowFlight(true);
                        sender.sendMessage(plugin.getMessage("flightEnabledForTarget").replace("{0}", other.getName()));
                    } else {
                        other.setAllowFlight(false);
                        sender.sendMessage(plugin.getMessage("flightDisabledForTarget").replace("{0}", other.getName()));
                    }
                } else {
                    sender.sendMessage(plugin.getMessage("cannotFindPlayer").replace("{0}", args[0]));

                }

            }
        } else {
            sender.sendMessage(plugin.getMessage("notAPlayer"));
        }
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length >= 2) {
            return Collections.emptyList();
        }
        return null;
    }
}