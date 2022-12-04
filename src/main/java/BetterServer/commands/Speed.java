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
        Objects.requireNonNull(this.plugin.getCommand("walkspeed")).setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        //Check if command is enabled:

        //Done :D
        if (! (sender instanceof Player player)) {
            sender.sendMessage("&4&lOnly players can execute this command!".replace('&', '§'));
            return true;
        } else {
            switch (label) {
                case "flyspeed" -> {
                    //Check if command is enabled:
                    if (this.plugin.getConfig().getStringList("DisabledCommands").contains("flyspeed")) {
                        sender.sendMessage("§4§lThis command is currently disabled, if you wish to override this command you are free to do.");
                        return true;
                    }
                    //Done :D
                    if (args.length == 0) {
                        sender.sendMessage("§4§lPlease set a number 1-10 to make your flyspeed.");
                        return true;
                    }
                    if (args.length == 1) {
                        if ((args[0]).equals("reset")) {
                            sender.sendMessage("§e§lYou reset your flyspeed.");
                            player.setFlySpeed((float) .1);
                            return true;
                        }
                        try {
                            int test = Integer.parseInt(args[0]);
                            if (test < 1 || test > 10) {
                                sender.sendMessage("&4&l1-10 is the allowed speed".replace('&', '§'));
                                return true;
                            }
                            final float speed = (float) test / 10;
                            player.setFlySpeed(speed);
                            player.sendMessage("&e&lFly speed is set now to &a&l{SPEEDFLOAT}".replace('&', '§').replace("{SPEEDFLOAT}", ("" + speed).replace(".0", "")));
                        } catch (NumberFormatException ex) {
                            sender.sendMessage("&4You are meant to use only numbers here.".replace('&', '§'));
                        }
                        return true;
                    }
                }
                case "walkspeed" -> {
                    //Check if command is enabled:
                    if (this.plugin.getConfig().getStringList("DisabledCommands").contains("walkspeed")) {
                        sender.sendMessage("§4§lThis command is currently disabled, if you wish to override this command you are free to do.");
                        return true;
                    }
                    //Done :D
                    if (args.length == 0) {
                        sender.sendMessage("§4§lPlease set a number 1-10 to make your walkspeed.");
                        return true;
                    }

                    if (args.length == 1) {
                        if ((args[0]).equals("reset")) {
                            sender.sendMessage("§e§lYou reset your walkspeed.");
                            player.setWalkSpeed((float) .2);
                            return true;
                        }
                        try {
                            int test = Integer.parseInt(args[0]);
                            if (test < 1 || test > 10) {
                                sender.sendMessage("&4&l1-10 is the allowed speed".replace('&', '§'));
                                return true;
                            }
                            final float speed = (float) test / 10;
                            player.setWalkSpeed(speed);
                            player.sendMessage("&e&lWalk speed is set now to &a&l{SPEEDFLOAT}".replace('&', '§').replace("{SPEEDFLOAT}", ("" + speed).replace(".0", "")));
                        } catch (NumberFormatException ex) {
                            sender.sendMessage("&4You are meant to use only numbers here.".replace('&', '§'));
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