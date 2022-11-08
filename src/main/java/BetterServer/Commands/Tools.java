package BetterServer.Commands;

import BetterServer.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.google.common.collect.Lists.newArrayList;

public class Tools implements CommandExecutor, TabCompleter {
    Main plugin;

    public Tools(Main plugin) {

        this.plugin = plugin;

        Objects.requireNonNull(this.plugin.getCommand("tools")).setExecutor(this);
        Objects.requireNonNull(this.plugin.getCommand("tool")).setExecutor(this);
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
        if(sender instanceof Player player) {

            switch (label) {
                case "tools" -> {
                    if(this.plugin.getConfig().getStringList("DisabledCommands").contains("tools")) {
                        sender.sendMessage("§4§lThis command is currently disabled, if you wish to override this command you are free to do.");
                        return true;
                    }
                    sender.sendMessage("§a§lA list of available tools:");
                    sender.sendMessage(" ");
                    sender.sendMessage("§2§lAnvil");
                    sender.sendMessage("§2§lWorkbench");
                    sender.sendMessage("§2§lFurnace");
                    sender.sendMessage("§2§lCartographytable");
                    sender.sendMessage("§2§lGrindstone");
                    sender.sendMessage("§2§lLoom");
                    sender.sendMessage("§2§lSmithingtable");
                    sender.sendMessage("§2§lStonecutter");

                }
                case "tool" -> {
                    if(this.plugin.getConfig().getStringList("DisabledCommands").contains("tool")) {
                        sender.sendMessage("§4§lThis command is currently disabled, if you wish to override this command you are free to do.");
                        return true;
                    }
                    String arg;
                    if(!(args.length == 0)) {
                        arg = args[0];
                    } else {
                        sender.sendMessage("§4§lPlease enter tool to open! (Use /tools to get a list of working tools!)");
                        return true;
                    }
                    switch (arg) {
                        case "workbench", "craft", "craftingtable" -> player.openWorkbench(null, true);
                        case "anvil", "av" -> player.sendMessage("Working on this. x1");
                        case "furnace" -> player.sendMessage("Working on this. x2");
                        case "cartographytable" -> player.sendMessage("Working on this. x3");
                        case "grindstone" -> player.sendMessage("Working on this. x4");
                        case "loom" -> player.sendMessage("Working on this. x5");
                        case "smithingtable" -> player.sendMessage("Working on this. x6");
                        case "stonecutter" -> player.sendMessage("Working on this. x7");
                        default -> player.sendMessage("§4§lCannot find option " + arg + ".");
                    }
                }
                case "workbench", "craft", "craftingtable" -> {
                    if(this.plugin.getConfig().getStringList("DisabledCommands").contains("workbench")) {
                        sender.sendMessage("§4§lThis command is currently disabled, if you wish to override this command you are free to do.");
                        return true;
                    }
                    player.openWorkbench(null, true);
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

    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {

        if(args.length == 1) {
            if(label.equals("tool")) {
                List<String> toollist = newArrayList();
                toollist.add("workbench");
                toollist.add("furnace");
                toollist.add("cartographytable");
                toollist.add("grindstone");
                toollist.add("loom");
                toollist.add("smithingtable");
                toollist.add("stonecutter");

                return toollist;
            }

        }
        if(args.length >= 2) {
            return Collections.emptyList();
        }
        return null;
    }
}
