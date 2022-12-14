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
        Objects.requireNonNull(this.plugin.getCommand("BetterServer")).setDescription(plugin.getMessage("betterServerCommandDescription"));
        Objects.requireNonNull(this.plugin.getCommand("BetterServer")).setUsage(plugin.getMessage("betterServerCommandUsage"));

    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        //Check if command is enabled:
        if (this.plugin.getConfig().getStringList("DisabledCommands").contains(label)) {

            sender.sendMessage(plugin.getMessage("commandDisabled"));
            return true;
        }
        //Done :D
        if (args.length >= 1) {
            String usage = args[0];
            switch (usage) {
                case "help" -> sender.sendMessage(plugin.getMessage("betterServerCommandHelp"));
                case "update" -> {
                    sender.sendMessage(plugin.getMessage("betterServerCommandUpdate"));
                    plugin.updateversion();
                }
                case "reload" -> {
                    this.plugin.reloadConfig();
                    plugin.updateversion();
                    plugin.LanguageUtil();
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
                    sender.sendMessage(plugin.getMessage("betterServerCommandReloadComplete"));
                }
                case "language" -> {
                    if (args.length >= 2) {
                        switch (args[1]) {
                            case "en", "english" -> {
                                plugin.getConfig().getConfigurationSection("").set("Language", "en");
                                plugin.saveConfig();
                                sender.sendMessage(plugin.getMessage("betterServerlanguageSetToEN"));
                            }
                            case "es" -> {
                                plugin.getConfig().getConfigurationSection("").set("Language", "es");
                                plugin.saveConfig();
                                sender.sendMessage(plugin.getMessage("betterServerlanguageSetToES"));

                            }
                            default -> {
                                sender.sendMessage(plugin.getMessage("languageCommandCannotFindLanguage").replace("{0}", args[0]));
                            }
                        }
                    } else {
                        sender.sendMessage(plugin.getMessage("betterServerLanguageNoArgs").replace("{0}", plugin.getConfig().getString("Language")));
                    }
                }
                default -> sender.sendMessage(plugin.getMessage("betterServerCommandCannotFindOption"));
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
            setList.add("language");
            return setList;
        }
        if (args.length == 2) {
            if (args[0].equals("language")) {
                List<String> list = newArrayList();
                list.add("en");
                list.add("es");
                return list;
            } else {
                return Collections.emptyList();
            }
        }
        if (args.length >= 3) {
            return Collections.emptyList();
        }
        return null;
    }
}