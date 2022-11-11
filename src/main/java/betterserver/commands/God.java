package betterserver.commands;

import betterserver.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import java.util.Objects;

public class God implements CommandExecutor, Listener {
    final Main plugin;

    public God(Main plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(this.plugin.getCommand("god")).setExecutor(this);
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);

    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        //Check if command is enabled:
        if(this.plugin.getConfig().getStringList("DisabledCommands").contains("god")) {
            sender.sendMessage("§4§lThis command is currently disabled, if you wish to override this command you are free to do.");
            return true;
        }
        //Done :D
        if(args.length == 0) {
            if(!(sender instanceof Player player)) {
                sender.sendMessage("§4§lYou must be a player to execute this command.");
                return true;
            } else {
                if(player.isInvulnerable()) {
                    player.setInvulnerable(false);
                    sender.sendMessage("§d§lYou are no longer invincible.");
                } else {
                    player.setInvulnerable(true);
                    player.setFoodLevel(20);
                    player.setHealth(20);
                    sender.sendMessage("§d§lYou have made yourself invincible.");
                }
            }

        } else {
            Player target = Bukkit.getServer().getPlayer(args[0]);
            if(target == null) {
                sender.sendMessage("§4§lCan't find player by the name of " + args[1]);
                return true;
            }
            if((target).isInvulnerable()) {
                target.setInvulnerable(false);
                sender.sendMessage("§d§l{NICK} is no longer invincible.".replace("{NICK}", target.getName()));
                target.sendMessage("§d§lYou are no longer invincible.");
            } else {
                target.setInvulnerable(true);
                target.setFoodLevel(20);
                target.setHealth(20);
                sender.sendMessage("§d§l{NICK} is now invincible.".replace("{NICK}", target.getName()));
                target.sendMessage("§d§lYou are now invincible.");
            }

        }
        return true;
    }

    @EventHandler

    public void onPlayerLoseHungerEvent(FoodLevelChangeEvent event) {

        if(!(event.getEntity() instanceof Player player)) return;
        if(event.getFoodLevel() > player.getFoodLevel()) return;
        if(!player.isInvulnerable()) return;
        event.setCancelled(true);
    }
}