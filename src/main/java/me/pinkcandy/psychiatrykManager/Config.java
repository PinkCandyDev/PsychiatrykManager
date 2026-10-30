package me.pinkcandy.psychiatrykManager;

public class Config {
    //discord integration
    private static String token;
    private static String guildId;
    private static String statusChannelId;
    private static int statusUpdateFrequency;

    public static void reloadConfig() {
        PsychiatrykManager.getInstance().reloadConfig();
        token = PsychiatrykManager.getInstance().getConfig().getString("token");
        guildId = PsychiatrykManager.getInstance().getConfig().getString("guildId");
        statusChannelId = PsychiatrykManager.getInstance().getConfig().getString("statusChannelId");
        statusUpdateFrequency = PsychiatrykManager.getInstance().getConfig().getInt("statusUpdateFrequency");
    }

    public static String getToken() {
        return token;
    }

    public static String getGuildId() {
        return guildId;
    }
    public static String getStatusChannelId() {
        return statusChannelId;
    }
    public static int getStatusUpdateFrequency(){return statusUpdateFrequency;}
}
