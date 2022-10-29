package BetterServer.Commands.AdminEssentials;

import BetterServer.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.util.Objects;

public class BetterServerHelper implements CommandExecutor {
    Main plugin;

    public BetterServerHelper(Main plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(this.plugin.getCommand("bettercommands")).setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        switch (args.length) {
            case 0 -> sender.sendMessage("§4§lUsage: /BetterCommands [<Help>,Reload]");
            case 1 -> {
                String usage = args[0];
                switch (usage) {
                    case "help" -> sender.sendMessage("§e§lHello World!");
                    case "reload" -> {
                        sender.sendMessage("§4§lok reloading.");
                        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                        String command = "plug reload betterevents --noconfirm";
                        String command2 = "plug reload bettercommands --noconfirm";
                        String command3 = "plug reload betternpcs --noconfirm";
                        String command4 = "plug reload betterprefixes --noconfirm";

                        Bukkit.dispatchCommand(console, command);
                        Bukkit.dispatchCommand(console, command2);
                        Bukkit.dispatchCommand(console, command3);
                        Bukkit.dispatchCommand(console, command4);
                    }
                    default -> sender.sendMessage("§4§lUsage: /BetterCommands [<Help>]");
                }
            }
        }
        return true;
    }
}