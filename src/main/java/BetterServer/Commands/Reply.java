package BetterServer.Commands;

import BetterServer.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class Reply implements CommandExecutor {
    Main plugin;


    public Reply(Main plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(this.plugin.getCommand("reply")).setExecutor(this);

    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        //Check if command is enabled:
        if(this.plugin.getConfig().getStringList("DisabledCommands").contains("reply")) {
            sender.sendMessage("§4§lThis command is currently disabled, if you wish to override this command you are free to do.");
            return true;
        }
        //Done :D
        if(sender instanceof Player player) {
            if(args.length > 0) {
                if(this.plugin.msg.lastMessageSender.get(player) != null) {
                    Player other = Bukkit.getPlayer(this.plugin.msg.lastMessageSender.get(player).getName());
                    if(other != null) {
                        this.plugin.msg.lastMessageSender.put(other, player);
                        StringBuilder sb = new StringBuilder();
                        for (String arg : args) sb.append(arg).append(" ");
                        String message = sb.toString();
                        if(!this.plugin.socialSpy.SocialSpyUsers.isEmpty()) {
                            for (Player socialSpyUser : this.plugin.socialSpy.SocialSpyUsers) {
                                socialSpyUser.sendMessage("§d§l[SocialSpy]{NICK} whispers to {NICKTO}:{MESSAGE}".replace("{NICK}", player.getName()).replace("{NICKTO}", other.getName()).replace("{MESSAGE}", message));
                            }
                        }
                        other.sendMessage("§d{NICK} whispers:{MESSAGE}".replace("{NICK}", player.getName()).replace("{MESSAGE}", message));
                        player.sendMessage("§dTo {NICK}:{MESSAGE}".replace("{NICK}", other.getName()).replace("{MESSAGE}", message));
                        return false;
                    }
                    player.sendMessage("§7You have no one to reply to.");
                } else {
                    player.sendMessage("§7You have no one to reply to.");
                }
            } else {
                player.sendMessage("§4§lPlease specify a message.");
            }
        } else {
            sender.sendMessage("§4§lOnly players can execute this command!");
        }
        return false;
    }/*The End of file*/
}