package me.pinkcandy.psychiatrykManager.linking;

import me.pinkcandy.psychiatrykManager.PsychiatrykManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TitleSender {

    public static void SendTitle(Player player)
    {
        player.sendTitle("§x§C§4§A§2§C§BP§x§B§9§A§3§C§8ₛ§x§A§8§9§5§B§Ey§x§9§6§8§8§B§3c§x§8§4§7§B§A§8ₕ§x§7§2§6§E§9§Dᵢ§x§6§0§6§1§9§2ₐ§x§4§E§5§4§8§7ₜ§x§3§C§4§7§7§Cᵣ§x§2§F§3§F§7§2y§x§B§B§B§0§B§Ak"
                , "§aWitamy"
                , 10, 120, 20);

        Bukkit.getServer().getScheduler().runTaskLater(PsychiatrykManager.getInstance(), () -> {
            player.sendTitle("§aDołącz do §9Discord"
                    , "§anastępnie użyj komędy §6/link <nik z discord>"
                    , 10, 9999, 20);

            player.sendMessage("§aAby dołączyć do serwera, wejdź na §9Discord: §1https://discord.gg/ZXBrvGjkg");
            player.sendMessage("§aNastępnie użyj komendy §6/link <nick z discord>§a i dokończ konfigurację na Discordzie.");
        }, 155L);
    }
}
