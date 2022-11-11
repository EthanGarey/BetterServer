package betterserver.commands;

import betterserver.Main;
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
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        //Check if command is enabled:
        if (this.plugin.getConfig().getStringList("DisabledCommands").contains("trash")) {
            sender.sendMessage("§4§lThis command is currently disabled, if you wish to override this command you are free to do.");
            return true;
        }
        if (sender instanceof Player player) {
            player.sendMessage("§a§lOpening trash bin.");
            Inventory inventory = Bukkit.createInventory(player, 27, "§4§lTrash Bin");
            player.openInventory(inventory);
        } else {
            sender.sendMessage("§4§lOnly players can execute this command!");
        }
        //Done :D
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length >= 1) {
            return Collections.emptyList();
        }
        return null;
    }
}