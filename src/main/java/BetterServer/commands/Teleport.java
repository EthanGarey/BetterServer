package BetterServer.commands;

import BetterServer.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Teleport implements CommandExecutor, TabCompleter{
    final Main plugin;

    public Teleport(Main plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(this.plugin.getCommand("teleport")).setExecutor(this);
        Objects.requireNonNull(this.plugin.getCommand("teleport")).setDescription(plugin.getMessage("teleportCommandDescription"));
        Objects.requireNonNull(this.plugin.getCommand("teleport")).setUsage(plugin.getMessage("teleportCommandUsage"));
        Objects.requireNonNull(this.plugin.getCommand("tpall")).setExecutor(this);
        Objects.requireNonNull(this.plugin.getCommand("tpall")).setDescription(plugin.getMessage("tpallCommandDescription"));
        Objects.requireNonNull(this.plugin.getCommand("tpall")).setUsage(plugin.getMessage("tpallCommandUsage"));
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        //Check if command is enabled:
        if (this.plugin.getConfig().getStringList("DisabledCommands").contains(label)) {

            sender.sendMessage(plugin.getMessage("commandDisabled"));
            return true;
        }
        //Done :D
        if (sender instanceof Player player) {
            switch (label) {
                case "teleport", "tp" -> {
                    if (args.length != 0) {
                        Player target = Bukkit.getServer().getPlayerExact(args[0]);
                        if (target != null) {
                            if (args.length < 2) {
                                if (target == player) {
                                    sender.sendMessage(plugin.getMessage("teleportCommandTeleportSelf"));
                                    return true;
                                }
                                player.teleport(target.getLocation());
                                sender.sendMessage(plugin.getMessage("teleportCommandTeleportOther"));
                                return true;
                            }
                            Player target2 = Bukkit.getServer().getPlayerExact(args[1]);
                            if (target2 == null) {
                                sender.sendMessage(plugin.getMessage("cannotFindPlayer").replace("{0}", args[0]));
                                return true;
                            }
                            if (target2 == player && target == player) {
                                sender.sendMessage(plugin.getMessage("teleportCommandTeleportSelf"));
                                return true;
                            }
                            target.teleport(target2.getLocation());
                            sender.sendMessage(plugin.getMessage("teleportCommandTeleportOtherToOther").replace("{0}", args[0]).replace("{1}", args[1]));
                            return true;
                        } else {
                            sender.sendMessage(plugin.getMessage("cannotFindPlayer").replace("{0}", args[0]));
                        }
                    } else {
                        sender.sendMessage(plugin.getMessage("pleaseSpecifyPlayer"));
                    }
                    return true;
                }
                case "tpall", "bringall" -> {
                    Player cmdsender;
                    Location location = player.getLocation();
                    cmdsender = (Player) sender;
                    for (Player player2 : player.getServer().getOnlinePlayers()) {
                        if (player2 != cmdsender) player2.teleport(location);
                    }
                    sender.sendMessage(plugin.getMessage("tpallCommandTeleportedAll"));

                }
            }
        } else {
            sender.sendMessage(plugin.getMessage("notAPlayer"));
        }
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length >= 3) {
            return Collections.emptyList();
        }
        return null;
    }
}