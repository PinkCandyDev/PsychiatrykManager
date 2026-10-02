package me.pinkcandy.psychiatrykManager.discordAPI;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;

public class GetUser {

    public static Guild guild;

    public static User getUser(String username) {
        User user = guild.getMembers().stream()
                .map(Member::getUser)
                .filter(u -> u.getName().equalsIgnoreCase(username))
                .findFirst()
                .orElse(null);
        return user;
    }
}