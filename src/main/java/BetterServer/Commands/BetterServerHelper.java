package BetterServer.Commands;

import BetterServer.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.google.common.collect.Lists.newArrayList;

public class BetterServerHelper implements CommandExecutor, TabCompleter {
    Main plugin;

    public BetterServerHelper(Main plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(this.plugin.getCommand("betterserver")).setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        //Check if command is enabled:
        if(this.plugin.getConfig().getStringList("DisabledCommands").contains("betterserver")) {
            sender.sendMessage("§4§lThis command is currently disabled, if you wish to override this command you are free to do.");
            return true;
        }
        //Done :D
        switch (args.length) {
            case 0 -> sender.sendMessage("§4§lUsage: /BetterServer [<Help>,<Update>, <Reload>]");
            case 1 -> {
                String usage = args[0];
                switch (usage) {
                    case "help" -> sender.sendMessage("§e§lWill add at a later date.!");
                    case "update" -> {
                        sender.sendMessage("Check console!");
                        plugin.updateversion();
                    }
                    case "reload" -> {
                        this.plugin.reloadConfig();
                        sender.sendMessage("§e§lReload complete :D!");
                    }
                    default -> sender.sendMessage("§4§lUsage: /BetterServer [<Help>, <Update>, <Reload>]");
                }
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 1) {
            List<String> setList = newArrayList();
            setList.add("help");
            setList.add("update");
            setList.add("reload");
            return setList;
        } else {
            return Collections.emptyList();
        }
    }
}