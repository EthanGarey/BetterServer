package BetterServer.Commands.AdminEssentials;

import BetterServer.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class BetterServerHelper extends JavaPlugin implements CommandExecutor {
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
                switch (usage) {
                    case "help" -> sender.sendMessage("§e§lHello World!");
                    case "update" -> new UpdateChecker(this, 105989).getVersion(version -> {
                        if (this.getDescription().getVersion().equals(version)) {
                            getLogger().info("There is not a new update available.");
                        } else {
                            getLogger().info("There is a new update available. https://www.spigotmc.org/resources/betterserver.105989/updates");
                        }
                    });
                    default -> sender.sendMessage("§4§lUsage: /BetterServer [<Help>]");
                }
            }
        }
        return true;
    }
}