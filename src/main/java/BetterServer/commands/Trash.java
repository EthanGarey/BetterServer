package BetterServer.commands;

import BetterServer.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Trash implements CommandExecutor, TabCompleter{
    final Main plugin;

    public Trash(Main plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(this.plugin.getCommand("trash")).setExecutor(this);
        Objects.requireNonNull(this.plugin.getCommand("trash")).setDescription(plugin.getMessage("trashCommandDescription"));
        Objects.requireNonNull(this.plugin.getCommand("trash")).setUsage(plugin.getMessage("trashCommandUsage"));
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        //Check if command is enabled:
        if (this.plugin.getConfig().getStringList("DisabledCommands").contains(label)) {

            sender.sendMessage(plugin.getMessage("commandDisabled"));
            return true;
        }
        if (sender instanceof Player player) {
            sender.sendMessage(plugin.getMessage("trashCommandOpen"));
            Inventory inventory = Bukkit.createInventory(player, 27, "§4§lTrash Bin");
            player.openInventory(inventory);
        } else {
            sender.sendMessage(plugin.getMessage("notAPlayer"));
        }
        //Done :D
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length >= 2) {
            return Collections.emptyList();
        }
        return null;
    }
}