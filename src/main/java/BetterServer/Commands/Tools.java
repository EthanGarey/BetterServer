package BetterServer.Commands;

import BetterServer.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class Tools implements CommandExecutor {
    Main plugin;

    public Tools(Main plugin) {
        this.plugin = plugin;

        Objects.requireNonNull(this.plugin.getCommand("workbench")).setExecutor(this);
        Objects.requireNonNull(this.plugin.getCommand("anvil")).setExecutor(this);
        Objects.requireNonNull(this.plugin.getCommand("cartographytable")).setExecutor(this);
        Objects.requireNonNull(this.plugin.getCommand("grindstone")).setExecutor(this);
        Objects.requireNonNull(this.plugin.getCommand("loom")).setExecutor(this);
        Objects.requireNonNull(this.plugin.getCommand("smithingtable")).setExecutor(this);
        Objects.requireNonNull(this.plugin.getCommand("stonecutter")).setExecutor(this);
        Objects.requireNonNull(this.plugin.getCommand("furnace")).setExecutor(this);

    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        //Check if command is enabled:

        //Done :D
        if(sender instanceof Player player) {

            switch (label) {
                case "tool" -> {
                    switch (args[0]) {
                        case "workbench", "craft", "craftingtable" -> {
                            player.openWorkbench(null, true);
                            if(this.plugin.getConfig().getStringList("DisabledCommands").contains("workbench")) {
                                sender.sendMessage("§4§lThis command is currently disabled, if you wish to override this command you are free to do.");
                                return true;
                            }
                        }
                        case "anvil", "av" -> player.sendMessage("Working on this. x1");
                        case "furnace" -> player.sendMessage("Working on this. x2");
                        case "cartographytable" -> player.sendMessage("Working on this. x3");
                        case "grindstone" -> player.sendMessage("Working on this. x4");
                        case "loom" -> player.sendMessage("Working on this. x5");
                        case "smithingtable" -> player.sendMessage("Working on this. x6");
                        case "stonecutter" -> player.sendMessage("Working on this. x7");
                    }
                }
                case "workbench", "craft", "craftingtable" -> {
                    player.openWorkbench(null, true);
                    if(this.plugin.getConfig().getStringList("DisabledCommands").contains("workbench")) {
                        sender.sendMessage("§4§lThis command is currently disabled, if you wish to override this command you are free to do.");
                        return true;
                    }
                }
                case "anvil", "av" -> player.sendMessage("Working on this. x1");
                case "furnace" -> player.sendMessage("Working on this. x2");
                case "cartographytable" -> player.sendMessage("Working on this. x3");
                case "grindstone" -> player.sendMessage("Working on this. x4");
                case "loom" -> player.sendMessage("Working on this. x5");
                case "smithingtable" -> player.sendMessage("Working on this. x6");
                case "stonecutter" -> player.sendMessage("Working on this. x7");
            }

        } else {
            sender.sendMessage("§4§lYou must be a player to execute this command");
        }
        return true;
    }
}
