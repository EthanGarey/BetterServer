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
        Objects.requireNonNull(this.plugin.getCommand("tools")).setDescription(plugin.getMessage("toolsCommandDescription"));
        Objects.requireNonNull(this.plugin.getCommand("tools")).setUsage(plugin.getMessage("toolsCommandUsage"));
        Objects.requireNonNull(this.plugin.getCommand("tool")).setExecutor(this);
        Objects.requireNonNull(this.plugin.getCommand("tool")).setDescription(plugin.getMessage("toolCommandDescription"));
        Objects.requireNonNull(this.plugin.getCommand("tool")).setUsage(plugin.getMessage("toolCommandUsage"));
        Objects.requireNonNull(this.plugin.getCommand("workbench")).setExecutor(this);


    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (this.plugin.getConfig().getStringList("DisabledCommands").contains(label)) {

            sender.sendMessage(plugin.getMessage("commandDisabled"));
            return true;
        }
        if (sender instanceof Player player) {
            switch (label) {
                case "tools" -> {

                    TextComponent workbench = new TextComponent("§2§lWorkbench");
                    workbench.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(plugin.getMessage("toolsCommandClickThisToPutWorkbench")).create()));
                    workbench.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tool workbench"));
                    sender.sendMessage(plugin.getMessage("toolsCommandAListOfAvailableTools"));
                    sender.sendMessage(" ");

                    player.spigot().sendMessage(workbench);

                }

                case "tool" -> {
                    String arg;
                    if (! (args.length == 0)) {
                        arg = args[0];
                    } else {
                        sender.sendMessage(plugin.getMessage("toolCommandWrongUsage"));
                        return true;
                    }
                    switch (arg) {
                        case "workbench", "craft", "craftingtable" -> player.openWorkbench(null, true);
                        default -> plugin.getMessage("toolCommandCannotFindOption").replace("{0}", args[0]);
                    }
                }
                case "workbench", "craft", "craftingtable" -> player.openWorkbench(null, true);
            }

        } else {
            sender.sendMessage(plugin.getMessage("notAPlayer"));
        }
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {

        if (args.length == 1) {
            if (label.equals("tool")) {
                List<String> toollist = newArrayList();
                toollist.add("workbench");

                return toollist;

            }


        }
        if (args.length >= 1) {
            return Collections.emptyList();
        }
        return null;

    }
}
