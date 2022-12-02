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

    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        //Check if command is enabled:
        if (this.plugin.getConfig().getStringList("DisabledCommands").contains("more")) {
            sender.sendMessage("§4§lThis command is currently disabled, if you wish to override this command you are free to do.");
            return true;
        }
        if (sender instanceof Player player) {
            ItemStack iteminhand = player.getItemInHand();
            if (! (iteminhand.getType() == Material.AIR)) {
                int max = plugin.getConfig().getInt("MoreCommandMaxStack");
                if (max < 128) {
                    iteminhand.setAmount(max);
                    sender.sendMessage("§e§lThe item in your hand has been set to a stack of " + max + "!");
                } else {
                    sender.sendMessage("§4§lError, max amount of items allowed is 127, please fix your configuration!");
                }
            } else {
                sender.sendMessage("§4§lError, cannot set air to a stack set in the configuration.");
            }
        } else {
            sender.sendMessage("§4§lYou must be a player to execute this command!");
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