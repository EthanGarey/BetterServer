package BetterServer.commands;

import BetterServer.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.StringJoiner;

public class Homes implements CommandExecutor{

    final FileConfiguration homeConfig = new YamlConfiguration();
    final Main plugin;


    public Homes(Main plugin) {
        this.plugin = plugin;
        if (plugin.getConfig().getInt("MaxPlayerHomes") == 0) {
            Bukkit.getConsoleSender().sendMessage("§4§lError, Configuration \"MaxPlayerHome\" must be greater than 0");
        }
        createHomesFile();
        load();

        plugin.getCommand("home").setExecutor(this);
        plugin.getCommand("sethome").setExecutor(this);
        plugin.getCommand("delhome").setExecutor(this);

    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {


        ifItExists();
        load();
        if (sender instanceof Player player) {
            switch (label) {
                case "home", "homes" -> {
                    if (plugin.getConfig().getStringList("DisabledCommands").contains("home")) {
                        player.sendMessage("§4§lThis command is currently disabled, if you wish to override this command you are free to do.");
                        return true;
                    }
                    if (args.length >= 1) {
                        String homename = args[0];

                        checkifplayerhashome(player, homename);
                    } else {

                        player.sendMessage("§a§lHere is a list of your homes.");

                        listhomes(player);

                    }

                }
                case "sethome", "createhome" -> {
                    if (this.plugin.getConfig().getStringList("DisabledCommands").contains("sethome")) {
                        player.sendMessage("§4§lThis command is currently disabled, if you wish to override this command you are free to do.");
                        return true;
                    }
                    if (args.length >= 1) {
                        String homename = args[0];
                        if (! homeConfig.getStringList("Homes.Players." + player.getUniqueId()).contains(homename)) {
                            addPlayerHome(player, homename);
                        }
                    }

                }
                case "delhome", "remhome", "removehome" -> {
                    if (this.plugin.getConfig().getStringList("DisabledCommands").contains("delhome")) {
                        player.sendMessage("§4§lThis command is currently disabled, if you wish to override this command you are free to do.");
                        return true;
                    }
                    if (args.length >= 1) {
                        String homename = args[0];
                        removePlayerHome(player, homename);
                    } else {
                        player.sendMessage("§a§lHere is a list of your homes.");
                        listhomes(player);
                    }
                }

            }
        } else {
            sender.sendMessage("§4§lOnly players can use this command.");
        }
        return true;
    }

    private void createHomesFile( ) {
        File homeConfigFile = new File(this.plugin.getDataFolder(), "homes.yml");
        ifItExists();

        try {
            homeConfig.load(homeConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void ifItExists( ) {
        File homeConfigFile = new File(this.plugin.getDataFolder(), "homes.yml");
        if (! homeConfigFile.exists()) {
            plugin.saveResource("homes.yml", true);
        }
    }

    public void removePlayerHome(Player p, String name) {

        if (! homeConfig.contains("Homes.Players." + p.getUniqueId() + "." + name)) {
            p.sendMessage("§4§lCould not find your home, " + name + " use /homes to show you a list of your homes!");
        } else {
            homeConfig.set(("Homes.Players." + p.getUniqueId() + "." + name), null);
            p.sendMessage("§4§lYour home, " + name + " has been removed!");
        }

        load();
    }

    public void addPlayerHome(Player p, String name) {


        if (homeConfig.contains("Homes.Players." + p.getUniqueId() + "." + name)) {
            p.sendMessage("§4§lYour home, " + name + "already exists! Use /delhome to remove it!");
        } else {
            homeConfig.createSection("Homes.Players." + p.getUniqueId() + "." + name);
            homeConfig.createSection("Homes.Players." + p.getUniqueId() + "." + name + ".X");
            homeConfig.createSection("Homes.Players." + p.getUniqueId() + "." + name + ".Y");
            homeConfig.createSection("Homes.Players." + p.getUniqueId() + "." + name + ".Z");
            homeConfig.createSection("Homes.Players." + p.getUniqueId() + "." + name + ".Enabled");

            int x = (int) p.getLocation().getX();
            int y = (int) p.getLocation().getY();
            int z = (int) p.getLocation().getZ();

            homeConfig.set("Homes.Players." + p.getUniqueId() + "." + name + ".X", x);
            homeConfig.set("Homes.Players." + p.getUniqueId() + "." + name + ".Y", y);
            homeConfig.set("Homes.Players." + p.getUniqueId() + "." + name + ".Z", z);
            homeConfig.set("Homes.Players." + p.getUniqueId() + "." + name + ".Enabled", true);

            p.sendMessage("§a§lYour home " + name + " has been set to coordnates, §e§l" + x + " " + y + " " + z + "§a§l.");

            load();
        }
    }
//    }

    public void listhomes(Player p) {
        StringJoiner message = new StringJoiner(", ");
        for (String key : homeConfig.getConfigurationSection("Homes.Players." + p.getUniqueId()).getKeys(false)) {
            message.add(key);
        }
        p.sendMessage(message + "");
    }

    public void checkifplayerhashome(Player p, String name) {
        if (homeConfig.getBoolean("Homes.Players." + p.getUniqueId() + "." + name + ".Enabled")) {
            teleportplayertolocation(p, name);

        }
    }

    public void load( ) {
        File homeConfigFile = new File(this.plugin.getDataFolder(), "homes.yml");

        try {
            homeConfig.save(homeConfigFile);
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage("§4§lCould not save the file, is it being used by something?");
        }
        try {
            homeConfig.load(homeConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            Bukkit.getConsoleSender().sendMessage("§4§lAn error occurred while reading your home.yml file, if you need help just join our discord with /betterserver help!");
        }


    }

    public void teleportplayertolocation(Player p, String name) {
        load();
        World world = p.getWorld();
        int x = (int) homeConfig.get("Homes.Players." + p.getUniqueId() + "." + name + ".X");
        int y = (int) homeConfig.get("Homes.Players." + p.getUniqueId() + "." + name + ".Y");
        int z = (int) homeConfig.get("Homes.Players." + p.getUniqueId() + "." + name + ".Z");
        p.teleport(new Location(world, x, y, z));
        p.sendMessage("§e§lSuccesfully teleported you to your home, §a§l" + name + "§e§l.");


    }
}
