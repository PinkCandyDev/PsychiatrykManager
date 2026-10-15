package me.pinkcandy.psychiatrykManager.discordAPI.status;

import me.pinkcandy.psychiatrykManager.Config;
import me.pinkcandy.psychiatrykManager.PsychiatrykManager;
import me.pinkcandy.psychiatrykManager.db.repo.StatusChannelsRepository;
import me.pinkcandy.psychiatrykManager.discordAPI.DiscordBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;

import java.sql.SQLException;
import java.util.List;

public class SendStatus {

    public static Long messageId;
    public static EmbedBuilder oldEmbed;

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

        if (oldEmbed != embed) {
            if (messageId != null) {
                channel.retrieveMessageById(messageId).queue((message) -> {
                    message.editMessageEmbeds(embed.build()).queue();
                });
            } else {
                channel.sendMessageEmbeds(embed.build()).queue(message -> {
                    messageId = message.getIdLong();
                    try {
                        StatusChannelsRepository.saveMessageId(channel.getIdLong(), messageId);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            oldEmbed = embed;
        }
    }
}
