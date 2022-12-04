package BetterServer.commands;

import BetterServer.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.google.common.collect.Lists.newArrayList;

public class BetterServerHelper implements CommandExecutor, TabCompleter, Listener{

    final Main plugin;

    public BetterServerHelper(Main plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(this.plugin.getCommand("BetterServer")).setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        //Check if command is enabled:
        if (this.plugin.getConfig().getStringList("DisabledCommands").contains("BetterServer")) {
            sender.sendMessage("§4§lThis command is currently disabled, if you wish to override this command you are free to do.");
            return true;
        }
        //Done :D
        switch (args.length) {
            case 0 -> sender.sendMessage("§4§lUsage: /BetterServer [<help>,<update>, <reload>]");
            case 1 -> {
                String usage = args[0];
                switch (usage) {
                    case "help" ->
                            sender.sendMessage("§e§lMake sure to join the discord for help!: https://discord.gg/eKCQUpuUXM!");
                    case "update" -> {
                        sender.sendMessage("Check console!");
                        plugin.updateversion();
                    }
                    case "reload" -> {
                        this.plugin.reloadConfig();
                        File homeConfigFile = new File(plugin.getDataFolder(), "homes.yml");
                        if (! homeConfigFile.exists()) {
                            homeConfigFile.getParentFile().mkdirs();
                            plugin.saveResource("homes.yml", false);
                        }
                        File spawnConfigFile = new File(plugin.getDataFolder(), "spawn.yml");
                        if (! spawnConfigFile.exists()) {
                            spawnConfigFile.getParentFile().mkdirs();
                            plugin.saveResource("spawn.yml", false);
                        }
                        sender.sendMessage("§e§lReload complete :D!");
                    }
                    default -> //GUI LOGIC
                            sender.sendMessage("§4§lError: Cannot find option, " + args[0] + ".");
                }
            }
        }
        return true;
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
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