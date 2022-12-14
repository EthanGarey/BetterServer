package BetterServer.commands;

import BetterServer.Main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class InventorySee implements CommandExecutor, TabCompleter{

    final Main plugin;


    public InventorySee(Main plugin) {

        this.plugin = plugin;
        Objects.requireNonNull(this.plugin.getCommand("invsee")).setExecutor(this);
        Objects.requireNonNull(this.plugin.getCommand("invsee")).setDescription(plugin.getMessage("inventorySeeCommandDescription"));
        Objects.requireNonNull(this.plugin.getCommand("invsee")).setUsage(plugin.getMessage("inventorySeeCommandUsage"));
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        //Check if command is enabled:
        if (this.plugin.getConfig().getStringList("DisabledCommands").contains(label)) {

            sender.sendMessage(plugin.getMessage("commandDisabled"));
            return true;
        }
        //Done :D
        if (sender instanceof Player player) {
            if (! (args.length == 0)) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    sender.sendMessage(plugin.getMessage("cannotFindPlayer"));
                    return true;
                }
                if (target == player) {
                    sender.sendMessage(plugin.getMessage("targetCannotBeYou"));
                    return true;
                }
                if (target.getGameMode() == GameMode.CREATIVE) {
                    sender.sendMessage(plugin.getMessage("inventorySeeCommandBuggyWarning"));
                    player.openInventory(target.getInventory());
                    return true;
                }
                sender.sendMessage(plugin.getMessage("inventorySeeYouOpenedTargetsInventory").replace("{0}", target.getName()));
            } else {
                return false;
            }
            return true;

        }
        sender.sendMessage(plugin.getMessage("notAPlayer"));
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length >= 2) {
            return Collections.emptyList();
        }
        return null;
    }
}