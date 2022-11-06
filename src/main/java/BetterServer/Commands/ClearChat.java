package BetterServer.Commands;

import BetterServer.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.stream.IntStream;

public class ClearChat implements CommandExecutor {
    Main plugin;

    public ClearChat(Main plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(this.plugin.getCommand("clearchat")).setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        //Check if command is enabled:
        if(this.plugin.getConfig().getStringList("DisabledCommands").contains("clearchat")) {
            sender.sendMessage("§4§lThis command is currently disabled, if you wish to override this command you are free to do.");
            return true;
        }
        //Done :D
        String message = "§e§lChat cleared by " + (sender instanceof Player player ? player.getName() : "Console");
        Bukkit.getOnlinePlayers().forEach(p -> {
            if(!(p.hasPermission("permission.Clearchat.exempt"))) {
                p.sendMessage("If you see this message, report this as error code 0.");
                IntStream.range(0, 100).forEach(i -> p.sendMessage(""));
                p.sendMessage(message);
            } else {
                p.sendMessage("");
                p.sendMessage(message + " §e§l(You did not get your chat cleared as you have permissions)");
                p.sendMessage("");
            }
        });
        Bukkit.getConsoleSender().sendMessage(message);
        return true;
    }
}
