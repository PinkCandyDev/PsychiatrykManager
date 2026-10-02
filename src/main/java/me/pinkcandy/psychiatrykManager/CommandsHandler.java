package me.pinkcandy.psychiatrykManager;

import me.pinkcandy.psychiatrykManager.discordAPI.GetUser;
import me.pinkcandy.psychiatrykManager.discordAPI.dm.SendLinkRequest;
import net.dv8tion.jda.api.entities.User;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandsHandler implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("link")) {
            if (args.length != 1) {
                sender.sendMessage("§cUżycie: /link <nick z discord>");
                return false;
            }

            String discordNick = args[0];

            User user = GetUser.getUser(discordNick);
            if (user!=null) {
                SendLinkRequest.sendRequest(user, sender.getName());
            }
        }
        return true;
    }
}
