package BetterServer;

import BetterServer.Commands.AdminEssentials.*;
import BetterServer.Commands.Msg;
import BetterServer.Commands.Reply;
import BetterServer.Events.PlayerEvents;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {


    public Msg msg;

    public SocialSpy socialSpy;
    @Override
    public void onEnable() {
        // Plugin startup logic
        new UpdateChecker (this, 105989).getVersion(version -> {
            if (this.getDescription().getVersion().equals(version)) {
                getLogger().info("There is not a new update available.");
            } else {
                getLogger().info("There is a new update available. https://www.spigotmc.org/resources/betterserver.105989/updates");
            }
        });

        this.msg = new Msg(this);
        this.socialSpy = new SocialSpy(this);
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
        Bukkit.getConsoleSender().sendMessage("§d[BetterServer] version: 1.0.0.0");

    }


    public void onDisable() {
        // Plugin shutdown logic
    }

    /*The End of file*/
}
