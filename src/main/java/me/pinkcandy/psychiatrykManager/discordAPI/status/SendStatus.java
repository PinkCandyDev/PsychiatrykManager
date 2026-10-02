package me.pinkcandy.psychiatrykManager.discordAPI.status;

import me.pinkcandy.psychiatrykManager.Config;
import me.pinkcandy.psychiatrykManager.discordAPI.DiscordBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;

import java.util.List;

public class SendStatus {

    private static Message persistantMessage;

    public static void sendStatus(List<String> nicknames) {
        String desc = nicknames.size() + "/67 \n \n";
        for (int i = 0; i < nicknames.size(); i++) {
            desc = desc + (i + 1) + ". " + nicknames.get(i) + "\n";
        }

        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Gracze na serverze:")
                .setDescription(desc

                );

        TextChannel channel = DiscordBot.getGuild()
                .getTextChannelById(Config.getStatusChannelId());

        if (persistantMessage != null) {
            persistantMessage.editMessageEmbeds(embed.build()).queue();
        } else {
            channel.sendMessageEmbeds(embed.build()).queue(message -> {
                persistantMessage = message;
            });
        }
    }
}
