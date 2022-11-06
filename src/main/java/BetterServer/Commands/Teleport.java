package BetterServer.Commands;

import BetterServer.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class Teleport implements CommandExecutor {
    Main plugin;

    public Teleport(Main plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(this.plugin.getCommand("teleport")).setExecutor(this);
        Objects.requireNonNull(this.plugin.getCommand("tpall")).setExecutor(this);
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        //Check if command is enabled:
        if(this.plugin.getConfig().getStringList("DisabledCommands").contains("teleport")) {
            sender.sendMessage("§4§lThis command is currently disabled, if you wish to override this command you are free to do.");
            return true;
        }
        //Done :D
        if(sender instanceof Player player) {
            switch (label) {
                case "teleport", "tp" -> {
                    if(args.length != 0) {
                        Player target = Bukkit.getServer().getPlayerExact(args[0]);
                        if(target != null) {
                            if(args.length < 2) {
                                if(target == player) {
                                    player.sendMessage("&e&lYou successfully teleported to yourself.".replace('&', '§'));
                                    return true;
                                }
                                player.teleport(target.getLocation());
                                player.sendMessage("&e&lYou successfully teleported to &3&l{NICK}&e&l.!".replace('&', '§').replace("{NICK}", (args[0])));
                                return true;
                            }
                            Player target2 = Bukkit.getServer().getPlayerExact(args[1]);
                            if(target2 == null) {
                                player.sendMessage("&4&lCould not find player &3&l{NICK}&4&l.".replace('&', '§').replace("{NICK}", (args[1])));
                                return true;
                            }
                            if(target2 == player && target == player) {
                                player.sendMessage("&e&lYou successfully teleported to yourself.".replace('&', '§'));
                                return true;
                            }
                            target.teleport(target2.getLocation());
                            player.sendMessage("&e&lYou successfully teleported &3&l{NICK} &e&lto &3&l{TO}&e&l.!".replace('&', '§').replace("{NICK}", (args[0])).replace("{TO}", (args[1])));
                            return true;
                        } else {
                            player.sendMessage("&4&lCloud not find player &3&l{NICK}&4&l!".replace('&', '§').replace("{NICK}", (args[0])));
                        }
                    } else {
                        player.sendMessage("&4&lPlease specify a player.".replace('&', '§'));
                    }
                    return true;
                }
                case "tpall", "bringall" -> {
                    Player cmdsender;
                    Location location = player.getLocation();
                    cmdsender = (Player) sender;
                    for (Player player2 : player.getServer().getOnlinePlayers()) {
                        if(player2 != cmdsender) player2.teleport(location);
                    }
                    sender.sendMessage("§e§lYou teleported all players to you!");

                }
            }
        } else {
            sender.sendMessage("&4&lOnly players can execute this command!".replace('&', '§'));
        }
        return true;
    }/*The End of file*/
}