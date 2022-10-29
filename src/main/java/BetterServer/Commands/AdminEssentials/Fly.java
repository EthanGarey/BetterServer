package BetterServer.Commands.AdminEssentials;

import BetterServer.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class Fly implements CommandExecutor {
    Main plugin;

    public Fly(final Main plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(this.plugin.getCommand("fly")).setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof final Player player) {
            if (player.hasPermission("permissions.commands.flight.toggle")) {
                switch (args.length) {
                    case 0:
                        if (!player.getAllowFlight()) {
                            player.setAllowFlight(true);
                            player.sendMessage("§e§lFlight is now §a§lEnabled.");
                        } else {
                            player.setAllowFlight(false);
                            player.sendMessage("§e§lFlight is now §4§lDisabled.");
                        }
                        break;
                    case 1:
                        if (player.hasPermission("permissions.commands.flight.toggleothers")) {
                            final Player other = Bukkit.getPlayer(args[0]);
                            if (other != null) {
                                if (!other.getAllowFlight()) {
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
                        break;
                }
            } else {
                player.sendMessage("§4§lYou do not have access to that command!");
            }
        } else {
            sender.sendMessage("§4§lOnly players can execute this command!");
        }
        return true;
    }/*The End of file*/
}