package BetterServer.commands;

import BetterServer.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

import static com.google.common.collect.Lists.newArrayList;


public class Homes implements CommandExecutor, TabCompleter{

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
        Objects.requireNonNull(this.plugin.getCommand("home")).setDescription(plugin.getMessage("homeCommandDescription"));
        Objects.requireNonNull(this.plugin.getCommand("home")).setUsage(plugin.getMessage("homeCommandUsage"));
        plugin.getCommand("sethome").setExecutor(this);
        Objects.requireNonNull(this.plugin.getCommand("sethome")).setDescription(plugin.getMessage("setHomeCommandDescription"));
        Objects.requireNonNull(this.plugin.getCommand("sethome")).setUsage(plugin.getMessage("setHomeCommandUsage"));
        plugin.getCommand("delhome").setExecutor(this);
        Objects.requireNonNull(this.plugin.getCommand("delhome")).setDescription(plugin.getMessage("delHomeCommandDescription"));
        Objects.requireNonNull(this.plugin.getCommand("delHome")).setUsage(plugin.getMessage("delHomeCommandUsage"));

    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (this.plugin.getConfig().getStringList("DisabledCommands").contains(label)) {

            sender.sendMessage(plugin.getMessage("commandDisabled"));
            return true;
        }
        createHomesFile();

        load();

        if (sender instanceof Player player) {

            if (! homeConfig.getConfigurationSection("").contains("Homes." + "Players." + player.getUniqueId() + "")) {

                homeConfig.createSection("Homes.Players." + player.getUniqueId());
                load();
            }
            switch (label) {
                case "homes" -> {

                    if (homeConfig.getConfigurationSection("Homes.Players." + player.getUniqueId()).getKeys(false).size() != 0) {
                        sender.sendMessage(plugin.getMessage("homeCommandHereIsListOfHomes"));

                        listhomes(player);
                    } else {
                        sender.sendMessage(plugin.getMessage("homeCommandNoHomes"));
                    }
                }

                case "home" -> {

                    if (args.length >= 1) {
                        String homename = args[0];
                        checkifplayerhashome(player, homename);
                    } else {
                        if (homeConfig.getConfigurationSection("Homes.Players." + player.getUniqueId()).getKeys(false).size() != 0) {
                            sender.sendMessage(plugin.getMessage("homeCommandHereIsListOfHomes"));

                            listhomes(player);
                        } else {
                            sender.sendMessage(plugin.getMessage("homeCommandNoHomes"));
                        }
                    }

                }
                case "sethome", "createhome" -> {

                    if (args.length >= 1) {
                        String homename = args[0];
                        if (! homeConfig.getStringList("Homes.Players." + player.getUniqueId()).contains(homename)) {
                            if (homeConfig.getStringList("Homes.Players." + player.getUniqueId()).isEmpty())
                                if (homeConfig.getConfigurationSection("Homes.Players." + player.getUniqueId()).getKeys(false).size() <= (plugin.getConfig().getInt("MaxPlayerHomes")) - 1) {
                                    addPlayerHome(player, homename);
                                } else {
                                    sender.sendMessage(plugin.getMessage("homeCommandMaxHomesError"));
                                }
                        }
                    } else {
                        sender.sendMessage(plugin.getMessage("homeCommandEnterNameError"));
                    }

                }
                case "delhome", "remhome", "removehome" -> {

                    if (args.length >= 1) {
                        String homename = args[0];
                        removePlayerHome(player, homename);
                    } else {
                        sender.sendMessage(plugin.getMessage("homeCommandHereIsListOfHomes"));
                        listhomes(player);
                    }
                }

            }
        } else {
            sender.sendMessage(plugin.getMessage("notAPlayer"));
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

    public void addPlayerHome(Player p, String name) {


        if (homeConfig.contains("Homes.Players." + p.getUniqueId() + "." + name)) {
            p.sendMessage(plugin.getMessage("homeCommandHomeAlreadyExists").replace("{0}", name));
        } else {
            homeConfig.createSection("Homes.Players." + p.getUniqueId() + "." + name);
            homeConfig.createSection("Homes.Players." + p.getUniqueId() + "." + name + ".Enabled");


            homeConfig.createSection("Homes.Players." + p.getUniqueId() + "." + name + ".X");
            homeConfig.createSection("Homes.Players." + p.getUniqueId() + "." + name + ".Y");
            homeConfig.createSection("Homes.Players." + p.getUniqueId() + "." + name + ".Z");


            int x = (int) p.getLocation().getX();
            int y = (int) p.getLocation().getY();
            int z = (int) p.getLocation().getZ();
            homeConfig.set("Homes.Players." + p.getUniqueId() + "." + name + ".Enabled", true);
            homeConfig.set("Homes.Players." + p.getUniqueId() + "." + name + ".X", x);
            homeConfig.set("Homes.Players." + p.getUniqueId() + "." + name + ".Y", y);
            homeConfig.set("Homes.Players." + p.getUniqueId() + "." + name + ".Z", z);
            p.sendMessage(plugin.getMessage("homeCommandHomeCreated").replace("{0}", name).replace("{1}", x + "").replace("{2}", y + "").replace("{3}", z + ""));
            load();
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
            p.sendMessage(plugin.getMessage("homeCommandCouldNotFindHome").replace("0", name));
        } else {
            homeConfig.set(("Homes.Players." + p.getUniqueId() + "." + name), null);
            p.sendMessage(plugin.getMessage("homeCommandRemovedHome").replace("{0}", name));
        }

        load();
    }


//    }

    public void listhomes(Player p) {
        StringJoiner message = new StringJoiner("§2§l, ");
        for (String key : homeConfig.getConfigurationSection("Homes.Players." + p.getUniqueId()).getKeys(false)) {
            message.add("§a§l" + key);
        }
        p.sendMessage(message + "");
    }

    public void checkifplayerhashome(Player p, String name) {
        if (homeConfig.getBoolean("Homes.Players." + p.getUniqueId() + "." + name + ".Enabled")) {
            teleportplayertolocation(p, name);

        } else {
            p.sendMessage(plugin.getMessage("homeCommandCouldNotFindHome").replace("{0}", name));
        }
    }

    public void load( ) {

        File homeConfigFile = new File(plugin.getDataFolder(), "homes.yml");
        if (! homeConfigFile.exists()) {
            homeConfigFile.getParentFile().mkdirs();
            plugin.saveResource("homes.yml", false);
            Bukkit.getConsoleSender().sendMessage("§4§lCould not save the file, is it being used by something?");
        }
        try {
            homeConfig.save(homeConfigFile);
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage("§4§lAn error occurred while reading your home.yml file, if you need help just join our discord with /betterserver help!");
        }
        try {
            homeConfig.load(homeConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
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
        p.sendMessage(plugin.getMessage("homeCommandTeleportSuccess").replace("{0}", name));


    }

    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 1) {
            if (! label.equals("homes")) {
                if (sender instanceof Player player) {
                    if (homeConfig.getStringList("Homes.Players").contains(player.getUniqueId() + "")) {
                        List<String> gethomes = newArrayList();

                        gethomes.addAll(homeConfig.getConfigurationSection("Homes.Players." + player.getUniqueId()).getKeys(false));

                        return gethomes;
                    } else {
                        return Collections.emptyList();
                    }
                }
            } else {
                return Collections.emptyList();
            }
        }
        if (args.length >= 2) {
            return Collections.emptyList();
        }
        return null;
    }
}
