package BetterServer.Commands.AdminEssentials;

import BetterServer.Main;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class Give implements CommandExecutor, TabCompleter {
    Main plugin;


    public Give(Main plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(this.plugin.getCommand("give")).setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 1) {
            sender.sendMessage("§4§lPlease enter an item to give.");
            return true;
        }
        if (args.length >= 2) {
            Player target = plugin.getServer().getPlayerExact(args[0]);
            if (target == null) {
                sender.sendMessage("§4§lCannot find player " + args[0]);
                return true;
            }
            Material itemType = Material.matchMaterial(args[1]);
            if (itemType == null) { //check whether the material exists
                sender.sendMessage("§4§lUnknown material: " + args[1] + ".");
                return true;
            }
            if (args.length == 2) target.getInventory().addItem(new ItemStack(itemType, 1));
            else if (args.length == 3) {
                try {
                    int number = Integer.parseInt(args[2]);
                    target.getInventory().addItem(new ItemStack(itemType, number));
                } catch (NumberFormatException ex) {
                    sender.sendMessage("§4§lYou must specify a number");
                }
            } else sender.sendMessage("§4§lUsage: /give <playername> <material> [<amount>]");
        }
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        if (args.length == 2)
            return Arrays.stream(Material.values()).map(Enum::name).filter(name -> name.toLowerCase().startsWith(args[1].toLowerCase())).toList();
        if (args.length >= 4) return Collections.emptyList();
        return null;
    }

}