package BetterServer.commands;

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


public class Give implements CommandExecutor, TabCompleter{
    final Main plugin;


    public Give(Main plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(this.plugin.getCommand("give")).setExecutor(this);
        Objects.requireNonNull(this.plugin.getCommand("give")).setDescription(plugin.getMessage("giveCommandDescription"));
        Objects.requireNonNull(this.plugin.getCommand("give")).setUsage(plugin.getMessage("giveCommandUsage"));
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        //Check if command is enabled:
        if (this.plugin.getConfig().getStringList("DisabledCommands").contains(label)) {

            sender.sendMessage(plugin.getMessage("commandDisabled"));
            return true;
        }
        if (args.length == 0) {
            return false;
        }
        //Done :D
        if (args.length == 1) {
            sender.sendMessage(plugin.getMessage("giveCommandEnterItemToGive"));
            return true;
        }
        Player target = plugin.getServer().getPlayerExact(args[0]);
        if (target == null) {
            sender.sendMessage(plugin.getMessage("cannotFindPlayer").replace("{0}", args[0]));
            return true;
        }
        Material itemType = Material.matchMaterial(args[1]);
        if (itemType == null) { //check whether the material exists
            sender.sendMessage(plugin.getMessage("giveCommandUnknownMaterial").replace("{0}", args[1]));
            return true;
        }
        Player player;
        if (sender instanceof Player) {
            player = (Player) sender;
        } else {
            player = null;
        }
        if (args.length == 2) {
            target.getInventory().addItem(new ItemStack(itemType, 1));
            if (target == player) {
                sender.sendMessage(plugin.getMessage("giveCommandGaveYourselfItem").replace("{0}", "1").replace("{1}", itemType.toString()));
            } else {
                sender.sendMessage(plugin.getMessage("giveCommandGaveTargetItem").replace("{0}", target.getName()).replace("{1}", "1").replace("{2}", itemType.toString()));
            }
        } else if (args.length == 3) {
            try {
                int number = Integer.parseInt(args[2]);
                target.getInventory().addItem(new ItemStack(itemType, number));
                if (target == player) {
                    sender.sendMessage(plugin.getMessage("giveCommandGaveYourselfItem").replace("{0}", number + "").replace("{1}", itemType.toString()));
                } else {
                    sender.sendMessage(plugin.getMessage("giveCommandGaveTargetItem").replace("{0}", target.getName() + "").replace("{1}", number + "").replace("{2}", itemType.toString()));
                }
            } catch (NumberFormatException ex) {
                sender.sendMessage(plugin.getMessage("giveCommandCannotFindNumber"));
            }
        }
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        if (args.length == 2)
            return Arrays.stream(Material.values()).map(Enum::name).filter(name -> name.toLowerCase().startsWith(args[1].toLowerCase())).toList();
        if (args.length >= 3) return Collections.emptyList();
        return null;
    }

}