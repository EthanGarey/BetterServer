package BetterServer.Events;

import BetterServer.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerEvents implements Listener {
    final Main plugin;

    public PlayerEvents(Main plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);

    }

    @EventHandler
    public void onPlayerChat(PlayerChatEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("betterserver.permissions.chat.chatcolor")) {
            event.setMessage(event.getMessage().replace('&', '§'));
        }
    }
    @EventHandler

    public void onPlayerLoseHungerEvent(FoodLevelChangeEvent event) {

        if (!(event.getEntity() instanceof Player player)) return;
        if (event.getFoodLevel() > player.getFoodLevel()) return;
        if (!player.isInvulnerable()) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerLeaveEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        this.plugin.socialSpy.SocialSpyUsers.remove(player);
        this.plugin.msg.lastMessageSender.remove(player);
    }

    @EventHandler
    public void onPlayerLeaveEvent(PlayerKickEvent event) {
        Player player = event.getPlayer();
        this.plugin.socialSpy.SocialSpyUsers.remove(player);
        this.plugin.msg.lastMessageSender.remove(player);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("permissions.back")) {
            player.sendMessage("§e§lYou just died! Type /back to go to your last death location!");
        }
    }


}