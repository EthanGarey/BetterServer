package BetterServer.Commands.AdminEssentials;

import BetterServer.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SocialSpy implements CommandExecutor {
    public List<Player> SocialSpyUsers = new ArrayList<>();
    Main plugin;

    public SocialSpy(Main plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(this.plugin.getCommand("socialspy")).setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        //Check if command is enabled:
        if(this.plugin.getConfig().getStringList("DisabledCommands").contains("socialspy")) {
            sender.sendMessage("§4§lThis command is currently disabled, if you wish to override this command you are free to do.");
            return true;
        }
        //Done :D
        if(sender instanceof Player player) {
            if(player.hasPermission("permissions.socialspy")) {
                if(!SocialSpyUsers.contains(player)) {
                    SocialSpyUsers.add(player);
                    player.sendMessage("&d&l[SocialSpy]&e&l is now &a&lEnabled ".replace('&', '§'));
                } else {
                    SocialSpyUsers.remove(player);
                    player.sendMessage("&d&l[SocialSpy]&e&l is now &4&lDisabled ".replace('&', '§'));
                }

            } else {
                player.sendMessage("&4&lYou do not have access to that command!".replace('&', '§'));
            }
        } else {
            sender.sendMessage("&4&lOnly players can execute this command!".replace('&', '§'));
        }
        return false;
    }/*The End of file*/
}