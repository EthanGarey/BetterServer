package BetterServer.Commands.AdminEssentials;

import BetterServer.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Objects;

public class BetterServerHelper implements CommandExecutor {
    Main plugin;

    public BetterServerHelper(Main plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(this.plugin.getCommand("betterserver")).setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        switch (args.length) {
            case 0 -> sender.sendMessage("§4§lUsage: /BetterCommands [<Help>,Reload]");
            case 1 -> {
                String usage = args[0];
                if ("help".equals(usage)) {
                    sender.sendMessage("§e§lHello World!");
                } else {
                    sender.sendMessage("§4§lUsage: /BetterServer [<Help>]");
                }
            }
        }
        return true;
    }
}