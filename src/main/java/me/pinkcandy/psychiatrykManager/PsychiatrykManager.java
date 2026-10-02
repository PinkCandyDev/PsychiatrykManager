package me.pinkcandy.psychiatrykManager;

import me.pinkcandy.psychiatrykManager.discordAPI.DiscordBot;
import me.pinkcandy.psychiatrykManager.discordAPI.status.SendStatus;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class PsychiatrykManager extends JavaPlugin {

    private static PsychiatrykManager instance;
    private Connection connection;

    @Override
    public void onEnable() {
        instance = this;
        instance.saveDefaultConfig();
        Config.reloadConfig();

        try{
            connection = DriverManager.getConnection(
                    "jdbc:sqlite:plugins/PsychiatrykManager/database.db"
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        JDA jda = JDABuilder.createDefault(Config.getToken())
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .setChunkingFilter(ChunkingFilter.ALL)
                .addEventListeners(new DiscordBot())
                .build();

        getServer().getPluginManager().registerEvents(new JoinVerification(), this);

        getServer().getPluginCommand("link").setExecutor(new CommandsHandler());

        Bukkit.getScheduler().runTaskTimer(instance, () -> {
            List<String> nicknames = new ArrayList<>();
            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                nicknames.add(p.getName());
            }
            int online = Bukkit.getOnlinePlayers().size();
            SendStatus.sendStatus(nicknames);
            jda.getPresence().setActivity(
                    Activity.playing("(" + online + "/67) na Psychiatryku"));
        }, 0L, 20L * 15);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static PsychiatrykManager getInstance() {
        return instance;
    }
}
