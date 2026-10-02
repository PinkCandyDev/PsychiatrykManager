package me.pinkcandy.psychiatrykManager.discordAPI.dm;

import me.pinkcandy.psychiatrykManager.PsychiatrykManager;
import me.pinkcandy.psychiatrykManager.discordAPI.DiscordBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.emoji.Emoji;


public class SendLinkRequest {
    public static void sendRequest(User user, String playerName) {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Veryfication request")
                .setDescription(
                        "Proźba połączenia konta discord z nikiem \"" + playerName
                                + "\". Jeżeli nie ty wysłałeś/aś te zapytanie, kliknij ❌, aby potwierdzić, kliknij ✅"
                );

        user.openPrivateChannel().queue(channel ->
                channel.sendMessageEmbeds(embed.build()).queue(message -> {
                    message.addReaction(Emoji.fromFormatted("✅")).queue();
                    message.addReaction(Emoji.fromFormatted("❌")).queue();
                })
        );

    }
}
