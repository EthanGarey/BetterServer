package BetterServer.Commands.AdminEssentials;

import BetterServer.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class ClearInventory implements CommandExecutor {
    Main plugin;

    public ClearInventory(Main plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(this.plugin.getCommand("clear")).setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player player) {
                player.getInventory().clear();
                player.sendMessage("§e§lYour inventory has been cleared!");
            } else {
                sender.sendMessage("§4§lConsole does not have a inventory!");
            }
        } else {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage("§4§lCloud not find player §3§l{NICK}§4§l!".replace("{NICK}", (args[0])));
                return true;
            }
            target.getInventory().clear();
            sender.sendMessage("§e§lYou cleared {NICK}'s inventory!".replace("{NICK}", (args[0])));
        }
        return true;
    }
}