package me.pinkcandy.psychiatrykManager;

import me.pinkcandy.psychiatrykManager.db.repo.RegisteredStatusRepository;
import me.pinkcandy.psychiatrykManager.db.repo.VeryficationMessageIdRepository;
import me.pinkcandy.psychiatrykManager.discordAPI.dm.AuthorizationMessage;
import me.pinkcandy.psychiatrykManager.linking.TitleSender;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.eclipse.aether.repository.Authentication;

import java.sql.SQLException;
import java.util.UUID;

public class JoinVerification implements Listener {
    @EventHandler
    public void onPlayerJoin(org.bukkit.event.player.PlayerJoinEvent event) {
        UUID playerid = event.getPlayer().getUniqueId();
        boolean registered = isPlayerRegistered(playerid.toString());

        if (!checkAuthorization(playerid) && registered) {
            event.getPlayer().kickPlayer(
                    "§cAby dołczyć do serwera, musisz zaznaczyć klucz na discordzie lub włączyć weryfikacje hasłem." +
                            " \n §4 ---------------------- \n" +
                            " §cTo join the server, you must mark the key on discord or enable password verification.");
        }
        else if (!registered) {
            TitleSender.SendTitle(event.getPlayer());
        }
    }

    private boolean isPlayerRegistered(String playerUUID) {
        try {
            if (RegisteredStatusRepository.getStatus(playerUUID).equals("registered"))
            {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    private boolean checkAuthorization(UUID playerUUID) {
        try {
            long discordId = RegisteredStatusRepository.getDiscordUserId(playerUUID.toString());
            long messageId = VeryficationMessageIdRepository.getMessageId(playerUUID.toString());
            return AuthorizationMessage.isAuth(discordId, messageId).join();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
