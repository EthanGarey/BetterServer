package BetterServer.commands;

import BetterServer.Main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.google.common.collect.Lists.newArrayList;

public class Gamemode implements CommandExecutor, TabCompleter{
    final Main plugin;

    public Gamemode(Main plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(this.plugin.getCommand("gamemode")).setExecutor(this);
        Objects.requireNonNull(this.plugin.getCommand("gamemode")).setDescription(plugin.getMessage("gamemodeCommandDescription"));
        Objects.requireNonNull(this.plugin.getCommand("gamemode")).setUsage(plugin.getMessage("gamemodeCommandUsage"));
        Objects.requireNonNull(this.plugin.getCommand("gmc")).setExecutor(this);
        Objects.requireNonNull(this.plugin.getCommand("gmc")).setDescription(plugin.getMessage("gmcCommandDescription"));
        Objects.requireNonNull(this.plugin.getCommand("gmc")).setUsage(plugin.getMessage("gmcCommandUsage"));
        Objects.requireNonNull(this.plugin.getCommand("gma")).setExecutor(this);
        Objects.requireNonNull(this.plugin.getCommand("gma")).setDescription(plugin.getMessage("gmaCommandDescription"));
        Objects.requireNonNull(this.plugin.getCommand("gma")).setUsage(plugin.getMessage("gmaCommandUsage"));
        Objects.requireNonNull(this.plugin.getCommand("gms")).setExecutor(this);
        Objects.requireNonNull(this.plugin.getCommand("gms")).setDescription(plugin.getMessage("gmsCommandDescription"));
        Objects.requireNonNull(this.plugin.getCommand("gms")).setUsage(plugin.getMessage("gmsCommandUsage"));
        Objects.requireNonNull(this.plugin.getCommand("gmsp")).setExecutor(this);
        Objects.requireNonNull(this.plugin.getCommand("gmsp")).setDescription(plugin.getMessage("gmspCommandDescription"));
        Objects.requireNonNull(this.plugin.getCommand("gmsp")).setUsage(plugin.getMessage("gmspCommandUsage"));
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        //Check if command is enabled:
        if (this.plugin.getConfig().getStringList("DisabledCommands").contains(label)) {

            sender.sendMessage(plugin.getMessage("commandDisabled"));
            return true;

        }

        //Done :D
        switch (label) {
            case "gmc", "gma", "gmsp", "gms" -> {
                Player target;
                if (args.length == 0) {
                    if (sender instanceof Player player) {
                        target = player;
                    } else {
                        sender.sendMessage(plugin.getMessage("gamemodeCommandEnterUser"));
                        return true;
                    }
                } else {
                    target = Bukkit.getPlayer(args[0]);
                }
                String gamemode = "";
                if (target == null) {
                    sender.sendMessage(plugin.getMessage("cannotFindPlayer").replace("{0}", args[0]));

                    return true;
                }
                switch (label) {
                    case "gmc" -> {
                        target.setGameMode(GameMode.CREATIVE);
                        gamemode = "CREATIVE";
                    }
                    case "gms" -> {
                        target.setGameMode(GameMode.SURVIVAL);
                        gamemode = "SURVIVAL";
                    }
                    case "gmsp" -> {
                        target.setGameMode(GameMode.SPECTATOR);
                        gamemode = "SPECTATOR";
                    }
                    case "gma" -> {
                        target.setGameMode(GameMode.ADVENTURE);
                        gamemode = "ADVENTURE";
                    }
                }
                if (! (target == sender)) {
                    sender.sendMessage(plugin.getMessage("gamemodeCommandSetGamemodeTarget").replace("{0}", target.getName()).replace("{1}", gamemode));
                }
                target.sendMessage(plugin.getMessage("gamemodeCommandSetGamemodeUser").replace("{0}", gamemode));
                return true;
            }
        }
        if (args.length == 0) {
            return false;
        }
        String usage = args[0];
        Player target;

        if (args.length == 1) {
            if (sender instanceof Player player) {
                target = player;
            } else {
                sender.sendMessage(plugin.getMessage("notAPlayer"));
                return true;
            }
        } else {
            target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage(plugin.getMessage("cannotFindPlayer").replace("{0}", args[0]));

                return true;
            }
        }


        String gamemode = "";
        switch (cmd.getName()) {
            case "gamemode", "gm" -> {
                switch (usage) {
                    case "spectator", "sp", "4" -> {
                        target.setGameMode(GameMode.SPECTATOR);
                        gamemode = "SPECTATOR";
                    }
                    case "adventure", "av", "a", "3" -> {
                        target.setGameMode(GameMode.ADVENTURE);
                        gamemode = "ADVENTURE";
                    }
                    case "survival", "s", "0" -> {
                        target.setGameMode(GameMode.SURVIVAL);
                        gamemode = "SURVIVAL";
                    }
                    case "creative", "c", "1" -> {
                        target.setGameMode(GameMode.CREATIVE);
                        gamemode = "CREATIVE";
                    }
                    default -> {
                        sender.sendMessage(plugin.getMessage("gamemodeCommandCannotFindGamemode").replace("{0}", usage));
                        return true;
                    }
                }
            }
        }
        if (! (args.length == 1)) {
            if (! (target == sender)) {
                sender.sendMessage(plugin.getMessage("gamemodeCommandSetGamemodeTarget").replace("{0}", target.getName()).replace("{1}", gamemode));
            }
        }
        target.sendMessage(plugin.getMessage("gamemodeCommandSetGamemodeUser").replace("{0}", gamemode));
        return true;

    }

    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {

        if (args.length == 1) {
            switch (label) {
                case "gm", "gamemode" -> {
                    List<String> getgamemode = newArrayList();
                    getgamemode.add("creative");
                    getgamemode.add("survival");
                    getgamemode.add("spectator");
                    getgamemode.add("adventure");

                    return getgamemode;

                }
            }
        }
        if (args.length >= 2) {
            switch (label) {
                case "gmc", "gms", "gmsp", "gma" -> {
                    return Collections.emptyList();

                }
            }
        }
        if (args.length >= 3) {
            return Collections.emptyList();
        }
        return null;
    }
}