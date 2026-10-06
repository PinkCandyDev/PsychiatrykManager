package me.pinkcandy.psychiatrykManager.db.repo;

import me.pinkcandy.psychiatrykManager.PsychiatrykManager;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class RegisteredStatusRepository {

    public static void savePending(String uuid, long discordId) throws SQLException {
        String sql = "INSERT INTO registered_status(uuid, discord_id, status) VALUES (?, ?, 'pending')" +
                "ON CONFLICT(uuid) DO UPDATE SET status = 'pending'";

        PreparedStatement ps = PsychiatrykManager.getConnection().prepareStatement(sql);

        ps.setString(1, uuid);
        ps.setLong(2, discordId);
        ps.executeUpdate();
    }

    public static void saveVerified(String uuid, long discordId) throws SQLException {
        String sql = "INSERT INTO registered_status(uuid, discord_id, status) VALUES (?, ?, 'verified')" +
                "ON CONFLICT(uuid) DO UPDATE SET status = 'verified'";

        PreparedStatement ps = PsychiatrykManager.getConnection().prepareStatement(sql);

        ps.setString(1, uuid);
        ps.setLong(2, discordId);
        ps.executeUpdate();
    }

    public static void removeStatus(String uuid) throws SQLException {
        String sql = "DELETE FROM registered_status WHERE uuid = ?";

        PreparedStatement ps = PsychiatrykManager.getConnection().prepareStatement(sql);

        ps.setString(1, uuid);
        ps.executeUpdate();
    }

    public static void removeStatus(int discordId) throws SQLException {
        String sql = "DELETE FROM registered_status WHERE discord_id = ?";

        PreparedStatement ps = PsychiatrykManager.getConnection().prepareStatement(sql);

        ps.setLong(1, discordId);
        ps.executeUpdate();
    }
}
