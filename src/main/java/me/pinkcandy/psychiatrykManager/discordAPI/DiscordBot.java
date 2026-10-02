package me.pinkcandy.psychiatrykManager.discordAPI;

import me.pinkcandy.psychiatrykManager.Config;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class DiscordBot extends ListenerAdapter {

    private static Guild guild;

    @Override
    public void onReady(ReadyEvent event) {
        guild = event.getJDA().getGuildById(Config.getGuildId());
        GetUser.guild = event.getJDA().getGuildById(Config.getGuildId());
    }

    public static Guild getGuild(){return guild;}
}
