package me.pinkcandy.psychiatrykManager;

import me.pinkcandy.psychiatrykManager.linking.TitleSender;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class JoinVerification implements Listener {
    @EventHandler
    public void onPlayerJoin(org.bukkit.event.player.PlayerJoinEvent event) {
        UUID playerid = event.getPlayer().getUniqueId();
        boolean registered = isPlayerRegistered(playerid);

        if (!checkAuthorization(playerid) && registered) {
            event.getPlayer().kickPlayer(
                    "§cAby dołczyć do serwera, musisz zaznaczyć klucz na discordzie lub włączyć weryfikacje hasłem." +
                            " \n §4 ---------------------- \n" +
                            " §cTo join the server, you must mark the key on discord or enable password verification.");
        }
        else if (registered) {
            TitleSender.SendTitle(event.getPlayer());
        }
    }

    private boolean isPlayerRegistered(UUID playerUUID) {
        return false;
    }
    private boolean checkAuthorization(UUID playerUUID) {
        return true;
    }
}
