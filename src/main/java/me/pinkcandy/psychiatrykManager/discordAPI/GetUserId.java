package me.pinkcandy.psychiatrykManager.discordAPI;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;

public class GetUserId {

    public static Guild guild;

    public static long fromUsername(String username) {
        return guild.getMembers().stream()
                .map(Member::getUser)
                .filter(u -> u.getName().equalsIgnoreCase(username))
                .mapToLong(User::getIdLong)
                .findFirst()
                .orElse(0L);
    }
}