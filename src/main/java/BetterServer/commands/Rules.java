package BetterServer.commands;

import BetterServer.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Scanner;


public class Rules implements CommandExecutor{
    final Main plugin;


    public Rules(Main plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(this.plugin.getCommand("rules")).setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        //Check if command is enabled:
        if (this.plugin.getConfig().getStringList("DisabledCommands").contains("rules")) {
            sender.sendMessage("§4§lThis command is currently disabled, if you wish to override this command you are free to do.");
            return true;


        }
        Path rulesfile = plugin.getDataFolder().toPath().resolve("rules.txt");

        if (Files.notExists(rulesfile)) {
            sender.sendMessage("§4§lCould not find your rules.txt file, creating one for you!");
            plugin.saveResource("rules.txt", false);

        }
        sender.sendMessage("§a§lHere are the server rules:");
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

}