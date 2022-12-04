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
import java.util.Objects;

public class Spawn implements CommandExecutor{
    final Main plugin;
    final FileConfiguration spawnConfig = new YamlConfiguration();

    public Spawn(Main plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(this.plugin.getCommand("spawn")).setExecutor(this);
        Objects.requireNonNull(this.plugin.getCommand("setspawn")).setExecutor(this);
    }


    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        File spawnConfigFile = new File(this.plugin.getDataFolder(), "spawn.yml");
        createSpawnFile();

        switch (label) {
            case "spawn" -> {
                if (this.plugin.getConfig().getStringList("DisabledCommands").contains("spawn")) {
                    sender.sendMessage("§4§lThis command is currently disabled, if you wish to override this command you are free to do.");
                    break;
                }
                if ((sender instanceof Player player)) {
                    if (! spawnConfig.getBoolean("spawn.IsEnabled")) {
                        sender.sendMessage("§4§lThis server has no spawnpoint set!");
                        return true;
                    }
                    World world = Bukkit.getWorld(Objects.requireNonNull(spawnConfig.getString("spawn.World")));
                    double x = spawnConfig.getDouble("spawn.X");
                    double y = spawnConfig.getDouble("spawn.Y");
                    double z = spawnConfig.getDouble("spawn.Z");
                    float yaw = (float) spawnConfig.getDouble("spawn.Yaw");
                    float pitch = (float) spawnConfig.getDouble("spawn.Pitch");
                    if (plugin.getConfig().getBoolean("SpawnCommandWaitThing")) {
                        player.sendMessage("§e§lTeleportation commencing...");
                        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, ( ) -> player.sendMessage("§e§lYou will be teleported in 3 seconds"), 20);

                        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, ( ) -> player.sendMessage("§e§lYou will be teleported in 2 seconds"), 40);
                        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, ( ) -> player.sendMessage("§e§lYou will be teleported in 1 second"), 60);

                    }
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, ( ) -> player.teleport(new Location(world, x, y, z, yaw, pitch)), 70);
                } else {
                    sender.sendMessage("&4&lYou must be a player to execute this command.!".replace('&', '§'));
                }
            }
            case "setspawn" -> {

                if (this.plugin.getConfig().getStringList("DisabledCommands").contains("setspawn")) {
                    sender.sendMessage("§4§lThis command is currently disabled, if you wish to override this command you are free to do.");
                    break;
                }

                if ((sender instanceof Player player)) {

                    spawnConfig.set("spawn.IsEnabled", true);
                    String world = player.getLocation().getWorld().getName();
                    double x = player.getLocation().getX();
                    double y = player.getLocation().getY();
                    double z = player.getLocation().getZ();
                    double yaw = player.getLocation().getYaw();
                    double pitch = player.getLocation().getPitch();

                    spawnConfig.set("spawn" + ".World", world);
                    spawnConfig.set("spawn" + ".X", x);
                    spawnConfig.set("spawn" + ".Y", y);
                    spawnConfig.set("spawn" + ".Z", z);
                    spawnConfig.set("spawn" + ".Yaw", yaw);
                    spawnConfig.set("spawn" + ".Pitch", pitch);

                    player.sendMessage("§a§lThe server spawn has been set!");

                    try {
                        spawnConfig.save(spawnConfigFile);
                    } catch (IOException e) {
                        player.sendMessage("&4&lError saving the spawn configuration file.");
                    }
                } else {
                    sender.sendMessage("&4&lYou must be a player to execute this command.!".replace('&', '§'));
                }
            }
        }


        return true;
    }

    public void ifItExists( ) {
        File spawnConfigFile = new File(this.plugin.getDataFolder(), "homes.yml");
        if (! spawnConfigFile.exists()) {
            plugin.saveResource("homes.yml", true);
        }
    }

    private void createSpawnFile( ) {
        File spawnConfigFile = new File(this.plugin.getDataFolder(), "spawn.yml");
        ifItExists();

        try {
            spawnConfig.load(spawnConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        try {
            spawnConfig.save(spawnConfigFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}