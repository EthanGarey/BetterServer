package BetterServer;

import BetterServer.commands.*;
import BetterServer.events.PlayerEvents;
import BetterServer.util.MetricsLite;
import BetterServer.util.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Properties;

public class Main extends JavaPlugin{
    public static ArrayList<Player> godmode = new ArrayList<>();
    public static HashMap<Player, Location> backlistlocation = new HashMap<>();
    public Msg msg;

    public void updateversion( ) {

        File config = new File(getDataFolder(), "config.yml");
        if (! (config.exists())) {
            Bukkit.getConsoleSender().sendMessage("Config file not found, Creating one for you!");

        }

        saveDefaultConfig();

        getConfig().set("version", Objects.requireNonNull(getConfig().getDefaults()).get("version"));

        saveConfig();
        new UpdateChecker(this, 105989).getVersion(version -> {
            if (Objects.equals(getConfig().getString("version"), version)) {
                getLogger().info("There is not a new update available.");
            } else {
                getLogger().info("There is a new update available. {NICK} https://www.spigotmc.org/resources/betterserver.105989/updates".replace("{NICK}", version));
            }
        });

    }

    @Override
    public void onEnable( ) {
        // Plugin startup logic

        int pluginId = 16752;
        new MetricsLite(this, pluginId);
        updateversion();
        LanguageUtil();
        this.msg = new Msg(this);
        
        new Motd(this);
        new Homes(this);
        new Rules(this);
        new More(this);
        new Trash(this);
        new Mutechat(this);
        new Give(this);
        new Back(this);
        new Tools(this);
        new Spawn(this);
        new InventorySee(this);
        new ClearInventory(this);
        new ClearChat(this);
        new PlayerEvents(this);
        new God(this);
        new BetterServerHelper(this);
        new Gamemode(this);
        new Fly(this);
        new Teleport(this);
        new Speed(this);
        Bukkit.getConsoleSender().sendMessage("&d[BetterServer] is running".replace('&', '§'));
        Bukkit.getConsoleSender().sendMessage("&d[BetterServer] made by &b&lEthan Garey".replace('&', '§'));


    }

    public void onDisable( ) {
        // Plugin shutdown logic
    }

    public void LanguageUtil( ) {

        switch (this.getConfig().getString("Language")) {
            case "en" -> {
                Bukkit.getConsoleSender().sendMessage("§e§lThe plugin language is set to §a§lEnglish§e§l.");
                saveResource("messages.properties", false);

            }
            case "es" -> {
                Bukkit.getConsoleSender().sendMessage("§e§lEl idioma del plugin esta configurado en §a§lEspanol§e§l.");
                saveResource("messages_es.properties", false);

            }
            default -> {
                Bukkit.getConsoleSender().sendMessage("§4§lAn error occurred, the config.yml did not have a language set.");
                Bukkit.getConsoleSender().sendMessage("§4§lIt will be defaulted as english.");

            }
        }

    }

    public String getMessage(String e) {
        switch (this.getConfig().getString("Language")) {
            case "en" -> {
                InputStreamReader langfile;
                try {
                    langfile = new FileReader(getDataFolder() + "\\messages.properties");
                } catch (FileNotFoundException v) {
                    saveResource("messages.properties", true);
                    return "&c4&lAn error occurred! Please try that command again later!";
                }
                Properties lang = new Properties();
                try {
                    lang.load(langfile);
                } catch (IOException ignored) {
                }
                String translate = lang.get(e) + "";
                if (translate.equals("null")) {
                    InputStream langfilenull;
                    langfilenull = getResource("messages.properties");
                    Properties langnull = new Properties();
                    try {
                        langnull.load(langfilenull);
                    } catch (IOException ignored) {
                    }
                    translate = langnull.getProperty(e);
                }
                return translate.replace('&', '§');
            }

            case "es" -> {
                InputStreamReader langfile;
                try {
                    langfile = new FileReader(getDataFolder() + "\\messages_es.properties");
                } catch (FileNotFoundException v) {
                    saveResource("messages_es.properties", true);
                    return "&4&lAn error occurred! Please try that command again later!";
                }
                Properties lang = new Properties();
                try {
                    lang.load(langfile);
                } catch (IOException ignored) {
                }
                String translate = lang.get(e) + "";
                if (translate.equals("null")) {
                    InputStream langfilenull;
                    langfilenull = getResource("messages_es.properties");
                    Properties langnull = new Properties();
                    try {
                        langnull.load(langfilenull);
                    } catch (IOException ignored) {
                    }
                    translate = langnull.getProperty(e);
                }
                return translate.replace('&', '§');
            }
            default -> {
                return null;
            }
        }

    }
}

