package betterserver.events;

import betterserver.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerEvents implements Listener {
    final Main plugin;

    public PlayerEvents(Main plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }


    @EventHandler

    public void onPlayerChat(AsyncPlayerChatEvent event) {
        //Check if event is enabled:
        if(!(this.plugin.getConfig().getBoolean("ChatcolorSupport"))) {
        }
        //Done :D
        else {
            Player player = event.getPlayer();
            if(player.hasPermission("betterserver.permissions.chat.chatcolor")) {
                event.setMessage(event.getMessage().replace('&', '§'));

            }
        }
    }


}