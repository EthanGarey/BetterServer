package BetterServer.commands;

import BetterServer.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

@SuppressWarnings("ALL")
public class Homes implements CommandExecutor{

    final Main plugin;
    final FileConfiguration homeConfig = new YamlConfiguration();


    public Homes(Main plugin) {
        this.plugin = plugin;
        if (plugin.getConfig().getInt("MaxPlayerHomes") == 0) {
            Bukkit.getConsoleSender().sendMessage("§4§lError, Configuration \"MaxPlayerHome\" must be greater than 0");
        }
/*        load();
        createHomesFile();*/
        plugin.getCommand("home").setExecutor(this);
        plugin.getCommand("sethome").setExecutor(this);
        plugin.getCommand("delhome").setExecutor(this);
    }


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        sender.sendMessage("This command is being worked on!");
        return true;
        /*
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

                        player.sendMessage("Here is a list of your homes!");
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
                        int maxhomes = plugin.getConfig().getInt("MaxPlayerHomes");
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
                }

            }
        } else {
            sender.sendMessage("Only players can use this command.");
        }
        return true;
    }

    private void createHomesFile( ) {
        ifItExists();
        File homeConfigFile = new File(plugin.getDataFolder(), "homes.yml");
        try {
            homeConfig.load(homeConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void ifItExists( ) {
        File homeConfigFile = new File(plugin.getDataFolder(), "homes.yml");
        if (! homeConfigFile.exists()) {
            homeConfigFile.getParentFile().mkdirs();
            plugin.saveResource("homes.yml", false);
        }
    }

    public void addPlayerHome(Player p, String name) {

        File homeConfigFile = new File(plugin.getDataFolder(), "homes.yml");
        if (homeConfig.contains("Homes.Players." + p.getUniqueId() + "." + name)) {
            p.sendMessage("§4§lYour home, " + name + "already exists! Use /delhome to remove it!");
        } else {
            homeConfig.createSection("Homes.Players." + p.getUniqueId() + "." + name);
            homeConfig.createSection("Homes.Players." + p.getUniqueId() + "." + name + ".X");
            homeConfig.createSection("Homes.Players." + p.getUniqueId() + "." + name + ".Y");
            homeConfig.createSection("Homes.Players." + p.getUniqueId() + "." + name + ".Z");
            homeConfig.createSection("Homes.Players." + p.getUniqueId() + "." + name + ".Enabled");

            double x = p.getLocation().getX();
            double y = p.getLocation().getY();
            double z = p.getLocation().getZ();

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
        Set<String> e = homeConfig.getConfigurationSection("Homes.Players." + p.getUniqueId()).getKeys(false);
        p.sendMessage(e + "");
    }

    public void checkifplayerhashome(Player p, String name) {
        if (homeConfig.getBoolean("Homes.Players." + p.getUniqueId() + "." + name + ".Enabled")) {
            teleportplayertolocation(p, name);

        }
    }

    public void load( ) {
        File homeConfigFile = new File(plugin.getDataFolder(), "homes.yml");
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
        File homeConfigFile = new File(plugin.getDataFolder(), "homes.yml");

        World world = p.getWorld();
        Integer x = (int) homeConfig.get("Homes.Players." + p.getUniqueId() + "." + name + ".X");
        Integer y = (int) homeConfig.get("Homes.Players." + p.getUniqueId() + "." + name + ".Y");
        Integer z = (int) homeConfig.get("Homes.Players." + p.getUniqueId() + "." + name + ".Z");
        p.teleport(new Location(world, x, y, z));
        p.sendMessage("§e§lSuccesfully teleported you to your home \"" + name.toString() + "\".");

   */
    }
}
