package BetterServer.commands;

import BetterServer.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Back implements CommandExecutor, Listener, TabCompleter{

    Main plugin;

    public Back(Main plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(this.plugin.getCommand("back")).setExecutor(this);
        Objects.requireNonNull(this.plugin.getCommand("back")).setDescription(plugin.getMessage("backCommandDescription"));
        Objects.requireNonNull(this.plugin.getCommand("back")).setUsage(plugin.getMessage("backCommandUsage"));
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);

    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        //Check if command is enabled:
        if (this.plugin.getConfig().getStringList("DisabledCommands").contains(label)) {
            sender.sendMessage(plugin.getMessage("commandDisabled"));
            return true;
        }
        //Done :D
        if (sender instanceof Player player) {
            if (Main.backlistlocation.containsKey(player)) {
                player.teleport(Main.backlistlocation.get(player));
                sender.sendMessage(plugin.getMessage("backCommandLastTeleportMessage"));
            } else {
                sender.sendMessage(plugin.getMessage("backCommandCouldNotFindTPMessage"));
            }
        } else {
            sender.sendMessage(plugin.getMessage("notAPlayer"));
        }

        return true;

    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity().getPlayer();
        if (player.hasPermission("betterserver.permissions.back")) {
            Main.backlistlocation.put(player, player.getLocation());
        }
    }

    @EventHandler
    public void onPLayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("betterserver.permissions.back")) {
            player.sendMessage("§e§lLooks like you just died! Type /back to go to your last death location!");
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length >= 2) {
            return Collections.emptyList();
        }
        return null;
    }


}