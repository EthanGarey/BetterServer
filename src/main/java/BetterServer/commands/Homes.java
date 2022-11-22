package BetterServer.commands;

import BetterServer.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class Homes implements CommandExecutor{

    Main plugin;
    YamlConfiguration homes;
    File file;

    public Homes(Main plugin) {
        file = plugin.getDataFolder();
        if (! file.exists()) {
            createHomesFile();
        }
        plugin.getCommand("home").setExecutor(this);
        plugin.getCommand("sethome").setExecutor(this);
        plugin.getCommand("delhome").setExecutor(this);
    }

    public void setHome(Player player) {
        homes.set("Homes." + player.getUniqueId().toString() + ".X", player.getLocation().getX());
        homes.set("Homes." + player.getUniqueId().toString() + ".Y", player.getLocation().getY());
        homes.set("Homes." + player.getUniqueId().toString() + ".Z", player.getLocation().getZ());
        homes.set("Homes." + player.getUniqueId().toString() + ".Yaw", player.getLocation().getYaw());
        homes.set("Homes." + player.getUniqueId().toString() + ".Pitch", player.getLocation().getPitch());
        homes.set("Homes." + player.getUniqueId().toString() + ".World", player.getLocation().getWorld().getName());
        saveHomesFile();
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player player) {
            switch (label) {
                case "home", "homes" -> {
                    if (this.plugin.getConfig().getStringList("DisabledCommands").contains("home")) {
                        player.sendMessage("§4§lThis command is currently disabled, if you wish to override this command you are free to do.");
                        return true;
                    }
                    if (homeIsNull(player)) {
                        player.sendMessage(ChatColor.DARK_RED + "[" + ChatColor.RED + "*" + ChatColor.DARK_RED + "] " + ChatColor.GRAY + "You must first use /sethome");

                    } else {
                        sendPlayerHome(player);
                    }
                }
                case "sethome", "createhome" -> {
                    if (this.plugin.getConfig().getStringList("DisabledCommands").contains("sethome")) {
                        player.sendMessage("§4§lThis command is currently disabled, if you wish to override this command you are free to do.");
                        return true;
                    }
                    setPlayerHome(player);


                }
                case "delhome", "remhome", "removehome" -> {
                    if (this.plugin.getConfig().getStringList("DisabledCommands").contains("delhome")) {
                        player.sendMessage("§4§lThis command is currently disabled, if you wish to override this command you are free to do.");
                        return true;
                    }
                }

            }
        } else {
            sender.sendMessage("Only players can use this command.");
        }
        return true;
    }

    public void createHomesFile( ) {
        try {
            file.createNewFile();
        } catch (IOException var2) {
            Bukkit.getConsoleSender().sendMessage("Could not save homes file.\nHere is the stack trace:");
            var2.printStackTrace();
        }

    }

    public void saveHomesFile( ) {
        try {
            this.homes.save(file);
        } catch (IOException var2) {
            Bukkit.getConsoleSender().sendMessage("Could not save homes file.\nHere is the stack trace:");
            var2.printStackTrace();
        }

    }

    void sendPlayerHome(Player player) {
        sendHome(player);
    }

    void setPlayerHome(Player player) {
        setHome(player);

    }

    public void sendHome(Player player) {
        player.teleport(this.getHomeLocation(player));
    }

    public Location getHomeLocation(Player player) {
        return new Location(Bukkit.getWorld(homes.getString("Homes." + player.getUniqueId().toString() + ".World")), homes.getDouble("Homes." + player.getUniqueId().toString() + ".X"), homes.getDouble("Homes." + player.getUniqueId().toString() + ".Y"), homes.getDouble("Homes." + player.getUniqueId().toString() + ".Z"), (float) homes.getLong("Homes." + player.getUniqueId().toString() + ".Yaw"), (float) homes.getLong("Homes." + player.getUniqueId().toString() + ".Pitch"));
    }

    public boolean homeIsNull(Player player) {
        return homes.getString("Homes." + player.getUniqueId()) == null;
    }


}
