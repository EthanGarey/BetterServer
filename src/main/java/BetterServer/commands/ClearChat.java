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
import java.util.stream.IntStream;

public class ClearChat implements CommandExecutor, TabCompleter{
    final Main plugin;

    public ClearChat(Main plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(this.plugin.getCommand("clearchat")).setExecutor(this);
        Objects.requireNonNull(this.plugin.getCommand("clearchat")).setDescription(plugin.getMessage("clearChatCommandDescription"));
        Objects.requireNonNull(this.plugin.getCommand("clearchat")).setUsage(plugin.getMessage("clearChatCommandUsage"));

    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        //Check if command is enabled:
        if (this.plugin.getConfig().getStringList("DisabledCommands").contains(label)) {
            sender.sendMessage(plugin.getMessage("commandDisabled"));
            return true;
        }
        //Done :D
        Bukkit.getOnlinePlayers().forEach(p -> {
            if (! (p.hasPermission("permission.Clearchat.exempt"))) {
                IntStream.range(0, 100).forEach(i -> p.sendMessage(""));
                p.sendMessage(plugin.getMessage("clearChatCommandMessageForNonPermission").replace("{0}", sender.getName()));
            } else {
                p.sendMessage(plugin.getMessage("clearChatCommandMessageForPermission").replace("{0}", sender.getName()));

            }
        });
        if (sender instanceof Player) {
            Bukkit.getConsoleSender().sendMessage(plugin.getMessage("clearChatCommandMessageToSendToConsole").replace("{0}", sender.getName()));
        } else {
            Bukkit.getConsoleSender().sendMessage(plugin.getMessage("clearChatCommandMessageToSendToConsole").replace("{0}", sender.getName()));
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
