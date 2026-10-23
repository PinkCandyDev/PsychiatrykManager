package me.pinkcandy.psychiatrykManager.linking;

import me.pinkcandy.psychiatrykManager.db.repo.RegisteredStatusRepository;
import me.pinkcandy.psychiatrykManager.db.repo.VeryficationMessageIdRepository;
import me.pinkcandy.psychiatrykManager.discordAPI.DiscordBot;
import me.pinkcandy.psychiatrykManager.discordAPI.GetUserId;
import me.pinkcandy.psychiatrykManager.discordAPI.ReactionListener;
import me.pinkcandy.psychiatrykManager.discordAPI.dm.AuthorizationMessage;
import me.pinkcandy.psychiatrykManager.discordAPI.dm.LinkRequest;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

public class LinkHandler {

    public static void onLinkCommand(Player player, String username) {
        long id = GetUserId.fromUsername(username);

        if (id==0) {
            player.sendMessage("Na serverze discord nie ma osoby o takim niku");
        }
        else
        {
            LinkRequest.sendRequest(DiscordBot.getJda().getUserById(id),
                    player.getName()).thenAccept(messageId -> {
                ReactionListener reactionListener = new ReactionListener(messageId, id, "✅", 300,
                        event ->  onTimeout(id),
                        event ->onLinkAccept(id, player));
            });
            TitleSender.SendAwaitVerification(player);
        }
    }

    public static void onLinkAccept(long id, Player player) {
        try{
            RegisteredStatusRepository.saveRegistered(player.getUniqueId().toString(), id);
            LinkRequest.sendSuccess(DiscordBot.getJda().getUserById(id), player.getName());
            CompletableFuture<Long> messageId = AuthorizationMessage.sendAuth(DiscordBot.getJda().getUserById(id));
            VeryficationMessageIdRepository.saveMessageId(player.getUniqueId().toString(), messageId.join());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void onTimeout(long id){
        LinkRequest.sendTimeout(DiscordBot.getJda().getUserById(id));
    }
}
