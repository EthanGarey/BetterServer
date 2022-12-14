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
        Objects.requireNonNull(this.plugin.getCommand("god")).setDescription(plugin.getMessage("godCommandDescription"));
        Objects.requireNonNull(this.plugin.getCommand("god")).setUsage(plugin.getMessage("godCommandUsage"));
    }


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        //Check if command is enabled:
        if (this.plugin.getConfig().getStringList("DisabledCommands").contains(label)) {

            sender.sendMessage(plugin.getMessage("commandDisabled"));
            return true;
        }
        //Done :D
        Player p;
        if (sender instanceof Player) {
            p = (Player) sender;
            if (args.length == 0) {
                if (! Main.godmode.contains(p)) {
                    Main.godmode.add(p);
                    sender.sendMessage(plugin.getMessage("godCommandNowInvincible"));
                } else {
                    Main.godmode.remove(p);
                    sender.sendMessage(plugin.getMessage("godCommandNoLongerInvincible"));

                }
            } else if (args.length == 1) {
                Player t = Bukkit.getPlayerExact(args[0]);
                if (t != null) {
                    if (t != p) {
                        if (! Main.godmode.contains(t)) {
                            Main.godmode.add(t);
                            sender.sendMessage(plugin.getMessage("godCommandOtherIsNowInvincible"));
                            t.sendMessage(plugin.getMessage("godCommandNowInvincible"));
                        } else {
                            Main.godmode.remove(t);
                            sender.sendMessage(plugin.getMessage("godCommandOtherIsNoLongerInvincible"));
                            t.sendMessage(plugin.getMessage("godCommandNoLongerInvincible"));
                        }
                    } else if (! Main.godmode.contains(p)) {
                        Main.godmode.add(p);
                        p.sendMessage(plugin.getMessage("godCommandNowInvincible"));
                    } else {
                        Main.godmode.remove(p);
                        p.sendMessage(plugin.getMessage("godCommandNoLongerInvincible"));
                    }
                } else {
                    sender.sendMessage(plugin.getMessage("cannotFindPlayer").replace("{0}", args[0]));
                    return true;

                }
            } else {
                return false;

            }
        } else {
            sender.sendMessage(plugin.getMessage("notAPlayer"));
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