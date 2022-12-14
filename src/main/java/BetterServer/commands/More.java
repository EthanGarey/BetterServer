package BetterServer.commands;

import BetterServer.Main;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class More implements CommandExecutor, TabCompleter{

    final Main plugin;


    public More(Main plugin) {

        this.plugin = plugin;
        Objects.requireNonNull(this.plugin.getCommand("more")).setExecutor(this);
        Objects.requireNonNull(this.plugin.getCommand("more")).setDescription(plugin.getMessage("moreCommandDescription"));
        Objects.requireNonNull(this.plugin.getCommand("more")).setUsage(plugin.getMessage("moreCommandUsage"));
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        //Check if command is enabled:
        if (this.plugin.getConfig().getStringList("DisabledCommands").contains(label)) {

            sender.sendMessage(plugin.getMessage("commandDisabled"));
            return true;
        }
        if (sender instanceof Player player) {
            ItemStack iteminhand = player.getItemInHand();
            if (! (iteminhand.getType() == Material.AIR)) {
                int max = plugin.getConfig().getInt("MoreCommandMaxStack");
                if (max < 128) {
                    iteminhand.setAmount(max);
                    sender.sendMessage(plugin.getMessage("moreCommandItemSuccess").replace("{0}", max + ""));
                } else {
                    sender.sendMessage(plugin.getMessage("moreCommandItemErrorMaxItem"));
                }
            } else {
                sender.sendMessage(plugin.getMessage("moreCommandItemErrorAir"));
            }
        } else {
            sender.sendMessage(plugin.getMessage("notAPlayer"));
        }
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length >= 1) {
            return Collections.emptyList();
        }
        return null;
    }
}