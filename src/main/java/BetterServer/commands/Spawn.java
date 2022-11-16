package BetterServer.commands;

import BetterServer.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static java.lang.Thread.sleep;

public class Spawn implements CommandExecutor{
    final Main plugin;

    public Spawn(Main plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(this.plugin.getCommand("spawn")).setExecutor(this);
        Objects.requireNonNull(this.plugin.getCommand("setspawn")).setExecutor(this);
    }

    private void wait1second( ) {
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        //Check if command is enabled:

        switch (label) {
            case "spawn" -> {
                if (this.plugin.getConfig().getStringList("DisabledCommands").contains("spawn")) {
                    sender.sendMessage("§4§lThis command is currently disabled, if you wish to override this command you are free to do.");
                    break;
                }
                if ((sender instanceof Player player)) {
                    plugin.saveDefaultConfig();
                    File file = new File("plugins/BetterServer", "config.yml");
                    FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
                    if ((plugin.getConfig().getString("spawn.World")) == null) {
                        player.sendMessage("§4§lThere is no server spawn point set up, please try again later!");
                        break;
                    }
                    World world = Bukkit.getWorld(Objects.requireNonNull(cfg.getString("spawn.World")));
                    double x = cfg.getDouble("spawn.X");
                    double y = cfg.getDouble("spawn.Y");
                    double z = cfg.getDouble("spawn.Z");
                    float yaw = (float) cfg.getDouble("spawn.Yaw");
                    float pitch = (float) cfg.getDouble("spawn.Pitch");
                    if (plugin.getConfig().getBoolean("SpawnCommandWaitThing")) {
                        player.sendMessage("§e§lTeleportation commencing...");
                        wait1second();
                        player.sendMessage("§e§lYou will be teleported in 3");
                        wait1second();
                        player.sendMessage("§e§lYou will be teleported in 2");
                        wait1second();
                        player.sendMessage("§e§lYou will be teleported in 1");
                        wait1second();
                    }
                    player.teleport(new Location(world, x, y, z, yaw, pitch));
                } else {
                    sender.sendMessage("&4&You must be a player to execute this command.!".replace('&', '§'));
                }
            }
            case "setspawn" -> {

                if (this.plugin.getConfig().getStringList("DisabledCommands").contains("setspawn")) {
                    sender.sendMessage("§4§lThis command is currently disabled, if you wish to override this command you are free to do.");
                    break;
                }

                if ((sender instanceof Player player)) {
                    plugin.saveDefaultConfig();
                    File file = new File("plugins/BetterServer", "config.yml");
                    FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
                    String world = Objects.requireNonNull(player.getLocation().getWorld()).getName();
                    double x = player.getLocation().getX();
                    double y = player.getLocation().getY();
                    double z = player.getLocation().getZ();
                    double yaw = player.getLocation().getYaw();
                    double pitch = player.getLocation().getPitch();

                    cfg.set("spawn" + ".World", world);
                    cfg.set("spawn" + ".X", x);
                    cfg.set("spawn" + ".Y", y);
                    cfg.set("spawn" + ".Z", z);
                    cfg.set("spawn" + ".Yaw", yaw);
                    cfg.set("spawn" + ".Pitch", pitch);

                    player.sendMessage("§a§lThe server spawn has been set!");

                    try {
                        cfg.save(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    sender.sendMessage("&4&You must be a player to execute this command.!".replace('&', '§'));
                }
            }
        }


        return true;
    }
}