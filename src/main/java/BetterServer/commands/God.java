package BetterServer.commands;

import BetterServer.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class God implements CommandExecutor, Listener, TabCompleter{
    final Main plugin;


    public God(Main plugin) {
        this.plugin = plugin;

        Objects.requireNonNull(this.plugin.getCommand("god")).setExecutor(this);
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);

    }


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        //Check if command is enabled:
        if (this.plugin.getConfig().getStringList("DisabledCommands").contains("god")) {
            sender.sendMessage("§4§lThis command is currently disabled, if you wish to override this command you are free to do.");
            return true;
        }
        //Done :D
        Player p;
        if (sender instanceof Player) {
            p = (Player) sender;
            if (args.length == 0) {
                if (! Main.godmode.contains(p)) {
                    Main.godmode.add(p);
                    p.sendMessage("§a§lYou are now invincible!");
                } else {
                    Main.godmode.remove(p);
                    p.sendMessage("§a§lYou are no longer invincible!");
                }
            } else if (args.length == 1) {
                Player t = Bukkit.getPlayerExact(args[0]);
                if (t != null) {
                    if (t != p) {
                        if (! Main.godmode.contains(t)) {
                            Main.godmode.add(t);
                            p.sendMessage("§a§l" + t + "§e§lis now invincible!");
                            t.sendMessage("§a§lYou are now invincible!");
                        } else {
                            Main.godmode.remove(t);
                            p.sendMessage("§a§l" + t + "§e§lis no longer invincible");
                            t.sendMessage("§a§lYou are no longer invincible!");
                        }
                    } else if (! Main.godmode.contains(p)) {
                        Main.godmode.add(p);
                        p.sendMessage("§a§lYou are now invincible!");
                    } else {
                        Main.godmode.remove(p);
                        p.sendMessage("§a§lYou are no longer invincible!");
                    }
                } else {
                    p.sendMessage("§4§lCould not find player §3§l" + args[0] + "&4&l!");
                }
            } else {
                p.sendMessage("§4§lUsage: /god [<player>]");
            }

        } else if (args.length == 1) {
            p = Bukkit.getPlayerExact(args[0]);
            if (p != null) {
                if (! Main.godmode.contains(p)) {
                    Main.godmode.add(p);
                    sender.sendMessage("§a§l" + p.getName() + "§e§lis now invincible");
                    p.sendMessage("§a§lYou are now invincibly!");
                } else {
                    Main.godmode.remove(p);
                    sender.sendMessage("§a§l" + p.getName() + "§e§lis no longer invincible");
                    p.sendMessage("§a§lYou are no longer invincibly!");
                }
            }
        } else {
            sender.sendMessage("§4§lUsage: /god [<player>]");
        }

        return true;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player p) {
            if (Main.godmode.contains(p)) {
                e.setDamage(0.0);
            }
        }

    }

    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length >= 2) {
            return Collections.emptyList();
        }
        return null;
    }
}