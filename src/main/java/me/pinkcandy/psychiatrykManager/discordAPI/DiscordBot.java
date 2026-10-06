package me.pinkcandy.psychiatrykManager.discordAPI;

import me.pinkcandy.psychiatrykManager.Config;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class DiscordBot extends ListenerAdapter {

    private static Guild guild;
    private static JDA jda;

    @Override
    public void onReady(ReadyEvent event) {
        this.jda = event.getJDA();
        guild = event.getJDA().getGuildById(Config.getGuildId());
        GetUserId.guild = event.getJDA().getGuildById(Config.getGuildId());
    }

    public static Guild getGuild(){return guild;}
    public static JDA getJda(){return jda;}
}
