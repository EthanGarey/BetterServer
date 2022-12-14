package BetterServer.commands;

import BetterServer.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.*;


public class Msg implements CommandExecutor, TabCompleter, Listener{

    public final HashMap<Player, Player> lastMessageSender = new HashMap<>();
    final Main plugin;
    public List<Player> SocialSpyUsers = new ArrayList<>();

    public Msg(Main plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(this.plugin.getCommand("msg")).setExecutor(this);
        Objects.requireNonNull(this.plugin.getCommand("msg")).setDescription(plugin.getMessage("messageCommandDescription"));
        Objects.requireNonNull(this.plugin.getCommand("msg")).setUsage(plugin.getMessage("messageCommandUsage"));

        Objects.requireNonNull(this.plugin.getCommand("reply")).setExecutor(this);
        Objects.requireNonNull(this.plugin.getCommand("reply")).setDescription(plugin.getMessage("replyCommandDescription"));
        Objects.requireNonNull(this.plugin.getCommand("reply")).setUsage(plugin.getMessage("replyCommandUsage"));

        Objects.requireNonNull(this.plugin.getCommand("socialspy")).setExecutor(this);
        Objects.requireNonNull(this.plugin.getCommand("socialspy")).setDescription(plugin.getMessage("socialSpyCommandDescription"));
        Objects.requireNonNull(this.plugin.getCommand("socialspy")).setUsage(plugin.getMessage("SocialSpyCommandUsage"));
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        //Check if command is enabled:
        if (this.plugin.getConfig().getStringList("DisabledCommands").contains(label)) {

            sender.sendMessage(plugin.getMessage("commandDisabled"));
            return true;
        }
        //Done :D
        if (sender instanceof Player player) {

            switch (label) {
                case "message", "msg", "w", "tell" -> {
                    if (args.length > 0) {
                        Player other = Bukkit.getPlayer(args[0]);
                        if (other != null) {
                            if (other != player) {
                                if (args.length > 1) {
                                    lastMessageSender.put(other, player);
                                    lastMessageSender.put(player, other);
                                    StringBuilder sb = new StringBuilder();
                                    for (int i = 1; i < args.length; i++)
                                        sb.append(args[i]).append(" ");
                                    String message = sb.toString();
                                    if (! SocialSpyUsers.isEmpty()) {
                                        for (Player socialSpyUser : this.SocialSpyUsers) {
                                            socialSpyUser.sendMessage("§d§l[SocialSpy]{NICK} whispers to {NICKTO}:{MESSAGE}".replace("{NICK}", player.getName()).replace("{NICKTO}", other.getName()).replace("{MESSAGE}", message));
                                        }
                                    }
                                    other.sendMessage("§d{NICK} whispers:{MESSAGE}".replace("{NICK}", player.getName()).replace("{MESSAGE}", message));
                                    player.sendMessage("§dTo {NICK}:{MESSAGE}".replace("{NICK}", other.getName()).replace("{MESSAGE}", message));
                                } else {
                                    sender.sendMessage(plugin.getMessage("messageCommandNowInMessageChat").replace("{0}", other.getName()));
                                    lastMessageSender.put(other, player);
                                    lastMessageSender.put(player, other);
                                    return true;
                                }
                            } else {
                                sender.sendMessage(plugin.getMessage("messageCommandCantMessageSelf"));
                                return true;
                            }
                        } else {
                            sender.sendMessage(plugin.getMessage("cannotFindPlayer").replace("{0}", args[0]));
                            return true;
                        }
                    } else {
                        sender.sendMessage(plugin.getMessage("pleaseSpecifyPlayer"));
                        return true;
                    }
                }
                case "reply", "r" -> {
                    if (args.length > 0) {
                        if (this.plugin.msg.lastMessageSender.get(player) != null) {
                            Player other = Bukkit.getPlayer(this.plugin.msg.lastMessageSender.get(player).getName());
                            if (other != null) {
                                this.plugin.msg.lastMessageSender.put(other, player);
                                StringBuilder sb = new StringBuilder();
                                for (String arg : args) sb.append(arg).append(" ");
                                String message = sb.toString();
                                if (! SocialSpyUsers.isEmpty()) {
                                    for (Player socialSpyUser : SocialSpyUsers) {
                                        socialSpyUser.sendMessage("§d§l[SocialSpy]{NICK} whispers to {NICKTO}:{MESSAGE}".replace("{NICK}", player.getName()).replace("{NICKTO}", other.getName()).replace("{MESSAGE}", message));
                                    }
                                }
                                other.sendMessage("§d{NICK} whispers:{MESSAGE}".replace("{NICK}", player.getName()).replace("{MESSAGE}", message));
                                player.sendMessage("§dTo {NICK}:{MESSAGE}".replace("{NICK}", other.getName()).replace("{MESSAGE}", message));
                                return true;
                            }
                            sender.sendMessage(plugin.getMessage("replyCommandNoOneToReplyTo"));
                        } else {
                            sender.sendMessage(plugin.getMessage("replyCommandNoOneToReplyTo"));
                        }
                    } else {
                        sender.sendMessage(plugin.getMessage("replyCommandSpecifyMessage"));
                    }
                }
                case "socialspy" -> {
                    if (! SocialSpyUsers.contains(player)) {
                        SocialSpyUsers.add(player);
                        sender.sendMessage(plugin.getMessage("socialSpyCommandEnabled"));
                    } else {
                        SocialSpyUsers.remove(player);
                        sender.sendMessage(plugin.getMessage("socialSpyCommandDisabled"));
                    }
                }

            }

        } else {
            sender.sendMessage(plugin.getMessage("notAPlayer"));
        }
        return false;
    }

    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equals("socialspy")) {
            if (args.length == 1) {
                return Collections.emptyList();
            }
        }
        if (args.length >= 2) {
            return Collections.emptyList();
        }
        return null;
    }
}