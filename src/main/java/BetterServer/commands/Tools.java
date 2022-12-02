package BetterServer.commands;


import BetterServer.Main;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.google.common.collect.Lists.newArrayList;

public class Tools implements CommandExecutor, TabCompleter{
    final Main plugin;

    public Tools(Main plugin) {

        this.plugin = plugin;

        Objects.requireNonNull(this.plugin.getCommand("tools")).setExecutor(this);
        Objects.requireNonNull(this.plugin.getCommand("tool")).setExecutor(this);
        Objects.requireNonNull(this.plugin.getCommand("workbench")).setExecutor(this);
        Objects.requireNonNull(this.plugin.getCommand("anvil")).setExecutor(this);
        Objects.requireNonNull(this.plugin.getCommand("cartographytable")).setExecutor(this);
        Objects.requireNonNull(this.plugin.getCommand("grindstone")).setExecutor(this);
        Objects.requireNonNull(this.plugin.getCommand("loom")).setExecutor(this);
        Objects.requireNonNull(this.plugin.getCommand("smithingtable")).setExecutor(this);
        Objects.requireNonNull(this.plugin.getCommand("stonecutter")).setExecutor(this);
        Objects.requireNonNull(this.plugin.getCommand("furnace")).setExecutor(this);

    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player player) {
            switch (label) {
                case "tools" -> {
                    if (this.plugin.getConfig().getStringList("DisabledCommands").contains("tools")) {
                        sender.sendMessage("§4§lThis command is currently disabled, if you wish to override this command you are free to do.");
                        return true;
                    }

                    TextComponent anvil = new TextComponent("§2§lAnvil §4§l<-- does not work");
                    anvil.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click this to put /tools anvil in the chat!").create()));
                    anvil.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tool anvil"));
                    TextComponent workbench = new TextComponent("§2§lWorkbench");
                    workbench.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click this to put /tools workbench in the chat!").create()));
                    workbench.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tool workbench"));
                    TextComponent furnace = new TextComponent("§2§lFurnace §4§l<-- does not work");
                    furnace.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click this to put /tools furnace in the chat!").create()));
                    furnace.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tool furnace"));
                    TextComponent cartographytable = new TextComponent("§2§lCartographytable §4§l<-- does not work");
                    cartographytable.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click this to put /cartographytable anvil in the chat!").create()));
                    cartographytable.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tool cartographytable"));
                    TextComponent grindstone = new TextComponent("§2§lGrindstone §4§l<-- does not work");
                    grindstone.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click this to put /grindstone workbench in the chat!").create()));
                    grindstone.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tool grindstone"));
                    TextComponent loom = new TextComponent("§2§lFurnace §4§l<-- does not work");
                    loom.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click this to put /tools furnace in the chat!").create()));
                    loom.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tool furnace"));
                    TextComponent smithingtable = new TextComponent("§2§lSmithingtable §4§l<-- does not work");
                    smithingtable.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click this to put /grindstone workbench in the chat!").create()));
                    smithingtable.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tool grindstone"));
                    TextComponent stonecutter = new TextComponent("§2§lStonecutter §4§l<-- does not work");
                    stonecutter.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click this to put /tools stonecutter in the chat!").create()));
                    stonecutter.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tool stonecutter"));
                    sender.sendMessage("§a§lA list of available tools:");
                    sender.sendMessage(" ");
                    player.spigot().sendMessage(anvil);
                    player.spigot().sendMessage(workbench);
                    player.spigot().sendMessage(furnace);
                    player.spigot().sendMessage(cartographytable);
                    player.spigot().sendMessage(grindstone);
                    player.spigot().sendMessage(loom);
                    player.spigot().sendMessage(smithingtable);
                    player.spigot().sendMessage(stonecutter);
                }

                case "tool" -> {
                    if (this.plugin.getConfig().getStringList("DisabledCommands").contains("tool")) {
                        sender.sendMessage("§4§lThis command is currently disabled, if you wish to override this command you are free to do.");
                        return true;
                    }
                    String arg;
                    if (! (args.length == 0)) {
                        arg = args[0];
                    } else {
                        sender.sendMessage("§4§lPlease enter tool to open! (Use /tools to get a list of working tools!)");
                        return true;
                    }
                    switch (arg) {
                        case "workbench", "craft", "craftingtable" -> player.openWorkbench(null, true);
                        case "anvil", "av" -> player.sendMessage("Working on this. x1");
                        case "furnace" -> player.sendMessage("Working on this. x2");
                        case "cartographytable" -> player.sendMessage("Working on this. x3");
                        case "grindstone" -> player.sendMessage("Working on this. x4");
                        case "loom" -> player.sendMessage("Working on this. x5");
                        case "smithingtable" -> player.sendMessage("Working on this. x6");
                        case "stonecutter" -> player.sendMessage("Working on this. x7");
                        default -> player.sendMessage("§4§lCannot find option " + arg + ".");
                    }
                }
                case "workbench", "craft", "craftingtable" -> {
                    if (this.plugin.getConfig().getStringList("DisabledCommands").contains("workbench")) {
                        sender.sendMessage("§4§lThis command is currently disabled, if you wish to override this command you are free to do.");
                        return true;
                    }
                    player.openWorkbench(null, true);
                }
                case "anvil", "av" -> player.sendMessage("Working on this. x1");
                case "furnace" -> player.sendMessage("Working on this. x2");
                case "cartographytable" -> player.sendMessage("Working on this. x3");
                case "grindstone" -> player.sendMessage("Working on this. x4");
                case "loom" -> player.sendMessage("Working on this. x5");
                case "smithingtable" -> player.sendMessage("Working on this. x6");
                case "stonecutter" -> player.sendMessage("Working on this. x7");
            }

        } else {
            sender.sendMessage("§4§lYou must be a player to execute this command");
        }
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {

        if (args.length == 1) {
            if (label.equals("tool")) {
                List<String> toollist = newArrayList();
                toollist.add("workbench");
                toollist.add("furnace");
                toollist.add("cartographytable");
                toollist.add("grindstone");
                toollist.add("loom");
                toollist.add("smithingtable");
                toollist.add("stonecutter");

                return toollist;

            }


        }
        if (args.length >= 1) {
            return Collections.emptyList();
        }
        return null;

    }
}
