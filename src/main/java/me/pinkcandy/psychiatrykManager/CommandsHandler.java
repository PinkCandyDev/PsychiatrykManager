package me.pinkcandy.psychiatrykManager;

import me.pinkcandy.psychiatrykManager.linking.LinkHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandsHandler implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("link")) {
            if (sender instanceof Player)
            {
                sender.sendMessage("This command can only be used by a player");
                return false;
            }
            if (args.length != 1) {
                sender.sendMessage("§cUżycie: /link <nick z discord>");
                return false;
            }
            String username = args[0];
            LinkHandler.onLinkCommand((Player) sender, username);

        }
        return true;
    }
}
