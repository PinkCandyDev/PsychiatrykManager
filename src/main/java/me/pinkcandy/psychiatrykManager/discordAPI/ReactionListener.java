package me.pinkcandy.psychiatrykManager.discordAPI;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class ReactionListener extends ListenerAdapter {

    private final long messageId;
    private final long userId;
    private final String emoji;
    private final Consumer<MessageReactionAddEvent> onReaction;
    private final ScheduledFuture<?> timeoutTask;

    public ReactionListener(long messageId, long userId, String emoji, int timeoutSeconds,
                             Consumer<MessageReactionAddEvent> onTimeout,
                             Consumer<MessageReactionAddEvent> onReaction) {
        this.messageId = messageId;
        this.userId = userId;
        this.emoji = emoji;
        this.onReaction = onReaction;

        DiscordBot.getJda().addEventListener(this);
        this.timeoutTask = DiscordBot.getJda().getGatewayPool().schedule(() -> {
            DiscordBot.getJda().removeEventListener(this);
            onTimeout.accept(null);
        }, timeoutSeconds, TimeUnit.SECONDS);
    }

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        if (event.getMessageIdLong() != messageId) return;
        if (event.getUserIdLong() != userId) return;
        if (!event.getEmoji().getAsReactionCode().equals(emoji) && 
            !event.getEmoji().getName().equals(emoji)) return;

        timeoutTask.cancel(false);
        DiscordBot.getJda().removeEventListener(this);
        onReaction.accept(event);
    }
}