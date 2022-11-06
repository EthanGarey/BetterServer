package BetterServer.Commands;

import BetterServer.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Objects;


public class Msg implements CommandExecutor {

    public HashMap<Player, Player> lastMessageSender = new HashMap<>();
    Main plugin;

    public Msg(Main plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(this.plugin.getCommand("msg")).setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        //Check if command is enabled:
        if(this.plugin.getConfig().getStringList("DisabledCommands").contains("message")) {
            sender.sendMessage("§4§lThis command is currently disabled, if you wish to override this command you are free to do.");
            return true;
        }
        //Done :D
        if(sender instanceof Player player) {
            if(args.length > 0) {
                Player other = Bukkit.getPlayer(args[0]);
                if(other != null) {
                    if(other != player) {
                        if(args.length > 1) {
                            lastMessageSender.put(other, player);
                            lastMessageSender.put(player, other);
                            StringBuilder sb = new StringBuilder();
                            for (int i = 1; i < args.length; i++)
                                sb.append(args[i]).append(" ");
                            String message = sb.toString();
                            if(!this.plugin.socialSpy.SocialSpyUsers.isEmpty()) {
                                for (Player socialSpyUser : this.plugin.socialSpy.SocialSpyUsers) {
                                    socialSpyUser.sendMessage("§d§l[SocialSpy]{NICK} whispers to {NICKTO}:{MESSAGE}".replace("{NICK}", player.getName()).replace("{NICKTO}", other.getName()).replace("{MESSAGE}", message));
                                }
                            }
                            other.sendMessage("§d{NICK} whispers:{MESSAGE}".replace("{NICK}", player.getName()).replace("{MESSAGE}", message));
                            player.sendMessage("§dTo {NICK}:{MESSAGE}".replace("{NICK}", other.getName()).replace("{MESSAGE}", message));
                        } else {
                            player.sendMessage("§e§lYou are now in a message chat with §b§l{NICK} ,§e§l,Type /r <message> to continue your chat.".replace("{NICK}", other.getName()));
                            lastMessageSender.put(other, player);
                            lastMessageSender.put(player, other);
                            return false;
                        }
                    } else {
                        player.sendMessage("§4§lYou can not message yourself.");
                        return false;
                    }
                } else {
                    player.sendMessage("§4§lCan't find player by the name of {NICK}".replace("{NICK}", (args[0])));
                    return false;
                }
            } else {
                player.sendMessage("§4§lPlease specify a player.");
                return false;
            }
        } else {
            sender.sendMessage("§4§lOnly players can execute this command!");
        }
        return false;
    }/*The End of file*/
}