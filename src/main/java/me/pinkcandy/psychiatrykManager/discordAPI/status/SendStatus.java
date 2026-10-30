package me.pinkcandy.psychiatrykManager.discordAPI.status;

import me.pinkcandy.psychiatrykManager.Config;
import me.pinkcandy.psychiatrykManager.PsychiatrykManager;
import me.pinkcandy.psychiatrykManager.db.repo.RegisteredStatusRepository;
import me.pinkcandy.psychiatrykManager.db.repo.StatusChannelsRepository;
import me.pinkcandy.psychiatrykManager.discordAPI.DiscordBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import org.apache.commons.lang3.ObjectUtils;
import org.bukkit.Bukkit;

import java.sql.SQLException;
import java.util.*;

public class SendStatus {

    public static Long messageId;
    private static List<UUID> oldUuids = new ArrayList<>();
    private static Map<UUID, String> userNames = new HashMap<>();

    public static void sendStatus(List<UUID> uuids) {
        if (oldUuids.equals(uuids)) {
            String desc = uuids.size() + "/67 \n \n";
            for (int i = 0; i < uuids.size(); i++) {
                try {
                    String discordNick = userNames.get(uuids.get(i));
                    if (discordNick == null) {
                        discordNick = DiscordBot.getJda().getUserById(RegisteredStatusRepository.getDiscordUserId(uuids.get(i).toString())).getName();
                        userNames.put(uuids.get(i), discordNick);
                    }
                    desc = desc + (i + 1) + ". @" + discordNick + "\n";
                } catch (SQLException | NullPointerException e) {
                    desc = desc + (i + 1) + ". " + Bukkit.getPlayer(uuids.get(i)) + "\n";

                }
            }
            oldUuids = uuids;


            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("Gracze na serverze:")
                    .setDescription(desc

                    );

            TextChannel channel = DiscordBot.getGuild()
                    .getTextChannelById(Config.getStatusChannelId());

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
        }
    }
}
