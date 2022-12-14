package BetterServer.commands;

import BetterServer.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.google.common.collect.Lists.newArrayList;

public class Speed implements CommandExecutor, TabCompleter{
    final Main plugin;

    public Speed(final Main plugin) {

        this.plugin = plugin;
        Objects.requireNonNull(this.plugin.getCommand("flyspeed")).setExecutor(this);
        Objects.requireNonNull(this.plugin.getCommand("flyspeed")).setDescription(plugin.getMessage("flyspeedCommandDescription"));
        Objects.requireNonNull(this.plugin.getCommand("flyspeed")).setUsage(plugin.getMessage("flyspeedCommandUsage"));
        Objects.requireNonNull(this.plugin.getCommand("walkspeed")).setExecutor(this);
        Objects.requireNonNull(this.plugin.getCommand("walkspeed")).setDescription(plugin.getMessage("walkspeedCommandDescription"));
        Objects.requireNonNull(this.plugin.getCommand("walkspeed")).setUsage(plugin.getMessage("walkspeedCommandUsage"));
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        //Check if command is enabled:
        if (this.plugin.getConfig().getStringList("DisabledCommands").contains(label)) {

            sender.sendMessage(plugin.getMessage("commandDisabled"));
            return true;
        }
        //Done :D
        if (! (sender instanceof Player player)) {
            sender.sendMessage(plugin.getMessage("notAPlayer"));
            return true;
        } else {
            switch (label) {
                case "flyspeed" -> {
                    //Check if command is enabled:

                    //Done :D
                    if (args.length == 0) {
                        sender.sendMessage(plugin.getMessage("flyspeedCommandWrongUsage"));
                        return true;
                    }
                    if (args.length == 1) {
                        if ((args[0]).equals("reset")) {
                            sender.sendMessage(plugin.getMessage("flyspeedCommandReset"));
                            player.setFlySpeed((float) .1);
                            return true;
                        }
                        try {
                            int test = Integer.parseInt(args[0]);
                            if (test < 1 || test > 10) {
                                sender.sendMessage(plugin.getMessage("flyspeedCommandReturnError"));
                                return true;
                            }
                            final float speed = (float) test / 10;
                            player.setFlySpeed(speed);
                            sender.sendMessage(plugin.getMessage("flyspeedSuccessMessage").replace("{0}", speed + ""));
                        } catch (NumberFormatException ex) {
                            sender.sendMessage(plugin.getMessage("speedCommandsNumberError"));
                        }
                        return true;
                    }
                }
                case "walkspeed" -> {
                    //Check if command is enabled:

                    //Done :D
                    if (args.length == 0) {
                        sender.sendMessage(plugin.getMessage("walkspeedCommandWrongUsage"));
                        return true;
                    }

                    if (args.length == 1) {
                        if ((args[0]).equals("reset")) {
                            sender.sendMessage(plugin.getMessage("walkspeedCommandReset"));
                            player.setWalkSpeed((float) .2);
                            return true;
                        }
                        try {
                            int test = Integer.parseInt(args[0]);
                            if (test < 1 || test > 10) {
                                sender.sendMessage(plugin.getMessage("walkspeedCommandReturnError"));
                                return true;
                            }
                            final float speed = (float) test / 10;
                            player.setWalkSpeed(speed);
                            sender.sendMessage(plugin.getMessage("walkspeedSuccessMessage").replace("{0}", speed + ""));
                        } catch (NumberFormatException ex) {
                            sender.sendMessage(plugin.getMessage("speedCommandsNumberError"));
                        }
                    }
                }
            }
        }


        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 1) {

                List<String> list = newArrayList();


                list.add("9");
                list.add("8");
                list.add("7");
                list.add("6");
                list.add("5");
                list.add("4");
                list.add("3");
                list.add("2");
                list.add("1");

                list.add("reset");
                return list;
            }
            if (args.length >= 2) {
                return Collections.emptyList();
            }

        }
        return null;
    }
}