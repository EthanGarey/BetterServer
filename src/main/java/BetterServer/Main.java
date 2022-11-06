package BetterServer;

import BetterServer.Commands.*;
import BetterServer.Events.PlayerEvents;
import BetterServer.util.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;

public class Main extends JavaPlugin {


    public Msg msg;
    public SocialSpy socialSpy;


    public void updateversion() {
        new UpdateChecker(this, 105989).getVersion(version -> {
            if(Objects.requireNonNull(getConfig().getString("version")).equals(version)) {
                getLogger().info("There is not a new update available.");
            } else {
                getLogger().info("There is a new update available. {NICK} https://www.spigotmc.org/resources/betterserver.105989/updates (NOTE WHEN YOU INSTALL IT TO YOUR PLUGINS LIST MAKE SURE TO DELETE THE CONFIG.YML FILE!)".replace("{NICK}", version));
            }

        });

    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        File config = new File(getDataFolder(), "config.yml");
        if(!(config.exists())) {
            Bukkit.getConsoleSender().sendMessage("Config file not found, Creating one for you!");

        }
        saveDefaultConfig();
        updateversion();

        this.msg = new Msg(this);
        this.socialSpy = new SocialSpy(this);
        new Trash(this);
        new Mutechat(this);
        new Give(this);
        new Back(this);
        new Tools(this);
        new InventorySee(this);
        new ClearInventory(this);
        new ClearChat(this);
        new PlayerEvents(this);
        new God(this);
        new BetterServerHelper(this);
        new Gamemode(this);
        new Fly(this);
        new Reply(this);
        new Teleport(this);
        new Speed(this);
        Bukkit.getConsoleSender().sendMessage("&d[BetterServer] is running".replace('&', '§'));
        Bukkit.getConsoleSender().sendMessage("&d[BetterServer] made by &b&lEthan Garey".replace('&', '§'));
        saveDefaultConfig();

    }


    public void onDisable() {
        // Plugin shutdown logic
    }

    /*The End of file*/
}
