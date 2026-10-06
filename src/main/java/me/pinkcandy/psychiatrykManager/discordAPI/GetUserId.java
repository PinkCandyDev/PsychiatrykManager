package me.pinkcandy.psychiatrykManager.discordAPI;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;

public class GetUserId {

    public static Guild guild;

    public static long fromUsername(String username){
        long id = guild.getMembers().stream()
                .map(Member::getUser)
                .filter(u -> u.getId().equalsIgnoreCase(username))
                .map(User::getIdLong)
                .findFirst()
                .orElse(0L);
        return id;

    }
}