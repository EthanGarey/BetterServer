package betterserver.commands;

import betterserver.Main;
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
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        //Check if command is enabled:
        if (this.plugin.getConfig().getStringList("DisabledCommands").contains("fly")) {
            sender.sendMessage("§4§lThis command is currently disabled, if you wish to override this command you are free to do.");
            return true;
        }
        //Done :D
        if (sender instanceof final Player player) {

            if (args.length == 0) {
                if (! player.getAllowFlight()) {
                    player.setAllowFlight(true);
                    player.sendMessage("§e§lFlight is now §a§lEnabled.");
                } else {
                    player.setAllowFlight(false);
                    player.sendMessage("§e§lFlight is now §4§lDisabled.");
                }
            }
            if (args.length >= 1) {
                if (player.hasPermission("permissions.commands.flight.toggleothers")) {
                    final Player other = Bukkit.getPlayer(args[0]);
                    if (other != null) {
                        if (! other.getAllowFlight()) {
                            other.setAllowFlight(true);
                            player.sendMessage("§e§lFlight is now §a§lEnabled §e§lFor §3§l{NICK}§e§l.".replace("{NICK}", other.getName()));
                        } else {
                            other.setAllowFlight(false);
                            player.sendMessage("§e§lFlight is now §4§lDisabled §e§lFor §3§l{NICK}§e§l.".replace("{NICK}", other.getName()));
                        }
                    } else {
                        player.sendMessage("§4§lCloud not find player §3§l{NICK}§4§l!".replace("{NICK}", (args[0])));
                    }
                } else {
                    player.sendMessage("§4§lYou do not have access to that command!");
                }
            }
        } else {
            sender.sendMessage("§4§lOnly players can execute this command!");
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