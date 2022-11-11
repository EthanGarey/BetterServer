package betterserver.commands;

import betterserver.Main;
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
        Objects.requireNonNull(this.plugin.getCommand("gmc")).setExecutor(this);
        Objects.requireNonNull(this.plugin.getCommand("gma")).setExecutor(this);
        Objects.requireNonNull(this.plugin.getCommand("gms")).setExecutor(this);
        Objects.requireNonNull(this.plugin.getCommand("gmsp")).setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        //Check if command is enabled:
        if (this.plugin.getConfig().getStringList("DisabledCommands").contains("gmc")) {
            if (label.equals("gmc")) {
                sender.sendMessage("§4§lThis command is currently disabled, if you wish to override this command you are free to do.");
                return true;
            }
        }
        if (this.plugin.getConfig().getStringList("DisabledCommands").contains("gms")) {
            if (label.equals("gms")) {
                sender.sendMessage("§4§lThis command is currently disabled, if you wish to override this command you are free to do.");
                return true;
            }
        }
        if (this.plugin.getConfig().getStringList("DisabledCommands").contains("gma")) {
            if (label.equals("gma")) {
                sender.sendMessage("§4§lThis command is currently disabled, if you wish to override this command you are free to do.");
                return true;
            }
        }
        if (this.plugin.getConfig().getStringList("DisabledCommands").contains("gmsp")) {
            if (label.equals("gmsp")) {
                sender.sendMessage("§4§lThis command is currently disabled, if you wish to override this command you are free to do.");
                return true;
            }
        }
        if (this.plugin.getConfig().getStringList("DisabledCommands").contains("gamemode")) {
            if (label.equals("gamemode")) {
                sender.sendMessage("§4§lThis command is currently disabled, if you wish to override this command you are free to do.");
                return true;
            }
        }
        //Done :D
        switch (label) {
            case "gmc", "gma", "gmsp", "gms" -> {
                Player target;
                if (args.length == 0) {
                    if (sender instanceof Player player) {
                        target = player;
                    } else {
                        sender.sendMessage("§4§lPlease enter a user to set the gamemode to.");
                        return true;
                    }
                } else {
                    target = Bukkit.getPlayer(args[0]);
                }
                String gamemode = "";
                if (target == null) {
                    sender.sendMessage("§4§lCan't find player by the name of " + args[0]);
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
                    sender.sendMessage("§e§lYou set " + target.getName() + "§e§l's gamemode to §a§l" + gamemode);
                }
                target.sendMessage("§e§lYour gamemode has been set to §a§l" + gamemode);
                return true;
            }
        }
        if (args.length == 0) {
            sender.sendMessage("§4§lUsage: /gamemode <creative,survival,spectator,adventure> [player]");
            return true;
        }
        String usage = args[0];
        Player target;

        if (args.length == 1) {
            if (sender instanceof Player player) {
                target = player;
            } else {
                sender.sendMessage("§4§lA player must execute this command!");
                return true;
            }
        } else {
            target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage("§4§lCan't find player by the name of " + args[1]);
                return true;
            }
        }


        String gamemode = "";
        switch (cmd.getName()) {
            case "gamemode":
            case "gm":
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
                        sender.sendMessage("§4§lCannot find the gamemode " + usage);
                        return true;
                    }
                }
        }
        if (! (args.length == 1)) {
            if (! (target == sender)) {
                sender.sendMessage("§e§lYou set " + target.getName() + "§e§l's gamemode to §a§l" + gamemode);
            }
        }
        target.sendMessage("§e§lYour gamemode has been set to §a§l" + gamemode);
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