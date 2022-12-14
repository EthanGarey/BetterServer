package BetterServer.commands;

import BetterServer.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;


public class Rules implements CommandExecutor, TabCompleter{
    final Main plugin;


    public Rules(Main plugin) {
        this.plugin = plugin;
        Path rulesfile = plugin.getDataFolder().toPath().resolve("rules.txt");

        if (Files.notExists(rulesfile)) {
            Bukkit.getConsoleSender().sendMessage("§4§lCould not find your rules.txt file, creating one for you!");
            plugin.saveResource("rules.txt", false);

        }
        Objects.requireNonNull(this.plugin.getCommand("rules")).setExecutor(this);
        Objects.requireNonNull(this.plugin.getCommand("rules")).setDescription(plugin.getMessage("rulesCommandDescription"));
        Objects.requireNonNull(this.plugin.getCommand("rules")).setUsage(plugin.getMessage("rulesCommandUsage"));
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        //Check if command is enabled:
        if (this.plugin.getConfig().getStringList("DisabledCommands").contains(label)) {

            sender.sendMessage(plugin.getMessage("commandDisabled"));
            return true;
        }
        Path rulesfile = plugin.getDataFolder().toPath().resolve("rules.txt");

        if (Files.notExists(rulesfile)) {
            sender.sendMessage(plugin.getMessage("rulesCommandCouldNotFindRules"));
            plugin.saveResource("rules.txt", false);

        }
        sender.sendMessage(plugin.getMessage("rulesCommandPresentRules"));
        sender.sendMessage("");
        Scanner sc;
        try {
            sc = new Scanner(rulesfile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        while (sc.hasNextLine())
            sender.sendMessage((sc.nextLine().replace('&', '§')));


        return true;

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return Collections.emptyList();
        }
        return null;
    }
}