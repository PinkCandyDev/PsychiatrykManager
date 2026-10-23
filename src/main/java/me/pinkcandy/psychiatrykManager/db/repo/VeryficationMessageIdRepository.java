package me.pinkcandy.psychiatrykManager.db.repo;

import me.pinkcandy.psychiatrykManager.PsychiatrykManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VeryficationMessageIdRepository {

    public static void saveMessageId(String uuid, long messageId) throws SQLException {
        String sql = "INSERT INTO status_channels (channel_id, message_id) VALUES (?, ?) " +
                "ON CONFLICT(uuid) DO UPDATE SET message_id = excluded.message_id";

        try (PreparedStatement ps = PsychiatrykManager.getConnection().prepareStatement(sql)) {
            ps.setString(1, uuid);
            ps.setLong(2, messageId);
            ps.executeUpdate();
        }
    }

    public static Long getMessageId(String uuid) throws SQLException {
        String sql = "SELECT message_id FROM status_channels WHERE uuid = ? LIMIT 1";

        try (PreparedStatement ps = PsychiatrykManager.getConnection().prepareStatement(sql)) {
            ps.setString(1, uuid);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getLong("message_id") : null;
            }
        }
    }
}
