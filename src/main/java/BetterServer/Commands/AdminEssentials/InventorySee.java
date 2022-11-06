package BetterServer.Commands.AdminEssentials;

import BetterServer.Main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class InventorySee implements CommandExecutor {

    Main plugin;


    public InventorySee(Main plugin) {

        this.plugin = plugin;
        Objects.requireNonNull(this.plugin.getCommand("invsee")).setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        //Check if command is enabled:
        if(this.plugin.getConfig().getStringList("DisabledCommands").contains("invsee")) {
            sender.sendMessage("§4§lThis command is currently disabled, if you wish to override this command you are free to do.");
            return true;
        }
        //Done :D
        if(sender instanceof Player player) {
            if(!(args.length == 0)) {
                Player target = Bukkit.getPlayer(args[0]);
                if(target == null) {
                    sender.sendMessage("§4§lCannot find player " + args[0]);
                    return true;
                }
                if(target == player) {
                    sender.sendMessage("§4§lPlease enter a player that is not you to run this command");
                    return true;
                }
                if(target.getGameMode() == GameMode.CREATIVE) {
                    sender.sendMessage("§4§lViewing inventory's of users in creative mode might be buggy. Beware.");
                    player.openInventory(target.getInventory());
                    return true;
                }
                sender.sendMessage("§e§lYou opened " + target.getName() + "'s inventory.");
                player.openInventory(target.getInventory());
            } else {
                sender.sendMessage("§4§lPlease enter a player to open a inventory.");
            }
            return true;

        }
        sender.sendMessage("§4§lOnly players can execute this command.");
        return true;
    }

}