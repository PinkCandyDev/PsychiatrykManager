package me.pinkcandy.psychiatrykManager.db.repo;

import me.pinkcandy.psychiatrykManager.PsychiatrykManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StatusChannelsRepository {

    public static void saveMessageId(long channelId, long messageId) throws SQLException {
        String sql = "INSERT INTO status_channels (channel_id, message_id) VALUES (?, ?) " +
                "ON CONFLICT(channel_id) DO UPDATE SET message_id = excluded.message_id";

        try (PreparedStatement ps = PsychiatrykManager.getConnection().prepareStatement(sql)) {
            ps.setLong(1, channelId);
            ps.setLong(2, messageId);
            ps.executeUpdate();
        }
    }

    public static Long getMessageId(long channelId) throws SQLException {
        String sql = "SELECT message_id FROM status_channels WHERE channel_id = ? LIMIT 1";

        try (PreparedStatement ps = PsychiatrykManager.getConnection().prepareStatement(sql)) {
            ps.setLong(1, channelId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getLong("message_id") : null;
            }
        }
    }
}
