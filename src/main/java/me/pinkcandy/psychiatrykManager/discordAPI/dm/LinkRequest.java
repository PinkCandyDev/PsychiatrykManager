package me.pinkcandy.psychiatrykManager.discordAPI.dm;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.emoji.Emoji;

import java.util.concurrent.CompletableFuture;


public class LinkRequest {
    public static CompletableFuture<Long> sendRequest(User user, String playerName) {
        CompletableFuture<Long> future = new CompletableFuture<>();

        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Verification request")
                .setDescription(
                        "Prośba połączenia konta Discord z nickiem \"" + playerName
                                + "\". Aby potwierdzić, kliknij ✅. Jeżeli to nie ty wysłałeś/aś tą proźbe, poczekaj 5 minut na wygaśnięcie zapytania"
                );

        user.openPrivateChannel().queue(channel ->
                channel.sendMessageEmbeds(embed.build()).queue(message -> {
                    message.addReaction(Emoji.fromFormatted("✅")).queue();

                    future.complete(Long.parseLong(message.getId()));
                })
        );

        return future;
    }

    public static void sendSuccess(User user, String playerName) {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Verification Succed")
                .setDescription(
                        "Twoje knto Discord zostało poprawnie przypisane do niku " + playerName
                );
        user.openPrivateChannel().queue(channel ->
                channel.sendMessageEmbeds(embed.build()).queue());
    }

    public static void sendTimeout(User user) {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Verification Timeout")
                .setDescription(
                        "Prośba połączenia konta Discord wygasła, urzyj komędy link aby wysłać ponownie"
                );
        user.openPrivateChannel().queue(channel ->
                channel.sendMessageEmbeds(embed.build()).queue());
    }
}
