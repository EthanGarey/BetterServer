package BetterServer.commands;

import BetterServer.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class Motd implements CommandExecutor, Listener, TabCompleter{

    final Main plugin;
    final FileConfiguration motdConfig = new YamlConfiguration();

    public Motd(Main plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(this.plugin.getCommand("motd")).setExecutor(this);
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
        createMotdFile();
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        ifItExists();
        load();
        sendPlayerMotdToPlayer(sender);
        return true;
    }


    private void createMotdFile( ) {
        ifItExists();
        File motdConfigFile = new File(plugin.getDataFolder(), "motd.yml");
        try {
            motdConfig.load(motdConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void ifItExists( ) {
        File motdConfigFile = new File(plugin.getDataFolder(), "motd.yml");
        if (! motdConfigFile.exists()) {
            motdConfigFile.getParentFile().mkdirs();
            plugin.saveResource("motd.yml", false);
        }
    }

    public void sendPlayerMotdToPlayer(CommandSender p) {
        int size = motdConfig.getStringList("JoinGame-MOTD.Messages").size();
        int get = 0;
        if (! (p instanceof Player)) {
            p.sendMessage("§e§lInfo: If you §4this§e message it means that you are typing motd in console. We will send you the original message in the motd configuration with variables.");
        }
        while (size > 0) {
            if (p instanceof Player player) {
                p.sendMessage((motdConfig.getStringList("JoinGame-MOTD.Messages").get(get).replace('&', '§').replace("{Player}", player.getName()).replace("{WorldTime}", player.getWorld().getTime() + "")));
            }
            if (! (p instanceof Player)) {

                p.sendMessage((motdConfig.getStringList("JoinGame-MOTD.Messages").get(get).replace('&', '§')));
            }
            get++;
            size--;
        }
    }

    public void load( ) {

        File motdConfigFile = new File(plugin.getDataFolder(), "motd.yml");
        if (! motdConfigFile.exists()) {
            motdConfigFile.getParentFile().mkdirs();
            plugin.saveResource("motd.yml", false);
        }
        try {
            motdConfig.load(motdConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage("§4§lAn error occurred while reading your motd.yml file, if you need help just join our discord with /betterserver help!");
        }
    }

    @EventHandler
    public void serverListPingListener(ServerListPingEvent event) {

        if (motdConfig.getBoolean("Server-MOTD.Enabled")) {
            String e = null;
            if (! motdConfig.getString("Server-MOTD.Line-1").isEmpty()) {
                e = motdConfig.getString("Server-MOTD.Line-1").replace('&', '§').replace("{OnlinePlayers}", plugin.getServer().getOnlinePlayers() + "").replace("{MaxPlayers}", plugin.getServer().getMaxPlayers() + "");
            }
            if (! motdConfig.getString("Server-MOTD.Line-2").isEmpty()) {
                e = e + "\n" + (motdConfig.getString("Server-MOTD.Line-2").replace('&', '§'));
            }
            event.setMotd(e + "");


        }
        if (motdConfig.getBoolean("Custom-Server-Icon.Enabled")) {
            File motdConfigImage = new File(plugin.getDataFolder(), "server-icon.png");
            if (! motdConfigImage.exists()) {
                motdConfigImage.getParentFile().mkdirs();
                plugin.saveResource("server-icon.png", false);
            }
            try {
                event.setServerIcon(Bukkit.loadServerIcon(motdConfigImage));
            } catch (Exception e) {
                Bukkit.getConsoleSender().sendMessage("And error occurred while loading the server image file!");
            }


        }
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.runTaskLater(plugin, ( ) -> {

            load();
            if (motdConfig.getBoolean("JoinGame-MOTD.Enabled")) {
                event.setJoinMessage("");

                int size = motdConfig.getStringList("JoinGame-MOTD.Messages").size();
                int get = 0;
                while (size > 0) {
                    event.getPlayer().sendMessage((motdConfig.getStringList("JoinGame-MOTD.Messages").get(get).replace('&', '§').replace("{Player}", event.getPlayer().getName()).replace("{WorldTime}", event.getPlayer().getWorld().getTime() + "")));
                    get++;
                    size--;
                }
            }
        }, 10L);
    }

    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length >= 1) {
            return Collections.emptyList();
        }
        return null;
    }
}


//Bukkit.getServer().getOnlinePlayers().size()
//Bukkit.getServer().getMaxPlayers()