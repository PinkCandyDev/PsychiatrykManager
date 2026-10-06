package me.pinkcandy.psychiatrykManager.linking;

import me.pinkcandy.psychiatrykManager.db.repo.RegisteredStatusRepository;
import me.pinkcandy.psychiatrykManager.discordAPI.DiscordBot;
import me.pinkcandy.psychiatrykManager.discordAPI.GetUserId;
import me.pinkcandy.psychiatrykManager.discordAPI.dm.SendLinkRequest;
import net.dv8tion.jda.api.entities.User;
import org.apache.commons.collections4.Get;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class LinkHandler {
    public static void onLinkCommand(Player player, String username)
    {
        long id = GetUserId.fromUsername(username);

        if (id==0) {
            player.sendMessage("Na serverze discord nie ma osoby o takim niku");
        }
        else
        {
            SendLinkRequest.sendRequest(DiscordBot.getJda().getUserById(id), player.getName());
            try {
                RegisteredStatusRepository.savePending(player.getUniqueId().toString(), id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
