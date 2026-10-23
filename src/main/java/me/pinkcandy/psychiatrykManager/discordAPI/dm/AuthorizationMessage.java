package me.pinkcandy.psychiatrykManager.discordAPI.dm;

import me.pinkcandy.psychiatrykManager.db.repo.VeryficationMessageIdRepository;
import me.pinkcandy.psychiatrykManager.discordAPI.DiscordBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.emoji.Emoji;

import java.util.concurrent.CompletableFuture;

public class AuthorizationMessage {
    public static CompletableFuture<Long> sendAuth(User user) {
        CompletableFuture<Long> future = new CompletableFuture<>();

        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Authorization")
                .setDescription("Aby moc dolonczyc do serwera, nalezy zaznaczyc ✅");

        user.openPrivateChannel().queue(channel ->
                channel.sendMessageEmbeds(embed.build()).queue(message -> {
                    message.addReaction(Emoji.fromFormatted("✅")).queue();
                    future.complete(Long.parseLong(message.getId()));
                })
        );

        return future;
    }

    public static CompletableFuture<Boolean> isAuth(long userId, long messageID) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        User user = DiscordBot.getJda().getUserById(userId);

        if (user == null) {
            future.complete(false);
            return future;
        }

        user.openPrivateChannel().queue(channel ->
                        channel.retrieveMessageById(messageID).queue(message ->
                                        message.retrieveReactionUsers(Emoji.fromUnicode("✅")).queue(users -> {
                                            boolean clicked = users.stream()
                                                    .anyMatch(u -> u.getIdLong() == userId);

                                            future.complete(clicked);
                                        }, error -> future.completeExceptionally(error)),
                                error -> future.completeExceptionally(error)
                        ),
                error -> future.completeExceptionally(error)
        );

        return future;
    }
}
