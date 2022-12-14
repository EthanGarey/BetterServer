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

public class ClearInventory implements CommandExecutor, TabCompleter{
    final Main plugin;

    public ClearInventory(Main plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(this.plugin.getCommand("clear")).setExecutor(this);
        Objects.requireNonNull(this.plugin.getCommand("clear")).setDescription(plugin.getMessage("clearInventoryCommandDescription"));
        Objects.requireNonNull(this.plugin.getCommand("clear")).setUsage(plugin.getMessage("clearInventoryCommandUsage"));

    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        //Check if command is enabled:
        if (this.plugin.getConfig().getStringList("DisabledCommands").contains(label)) {
            sender.sendMessage(plugin.getMessage("commandDisabled"));
            return true;
        }
        //Done :D
        if (args.length == 0) {
            if (sender instanceof Player player) {
                player.getInventory().clear();
                sender.sendMessage(plugin.getMessage("clearInventoryCommandInvCleared"));
            } else {
                sender.sendMessage(plugin.getMessage("notAPlayer"));

            }
        } else {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(plugin.getMessage("cannotFindPlayer").replace("{0}", args[0]));
                return true;
            }
            target.getInventory().clear();
            sender.sendMessage(plugin.getMessage("clearedTargetsInventory"));
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