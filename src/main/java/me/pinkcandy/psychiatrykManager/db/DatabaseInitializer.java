package me.pinkcandy.psychiatrykManager.db;

import me.pinkcandy.psychiatrykManager.PsychiatrykManager;

import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
    public static void initializeDatabase() {
        try {
            Statement statement = PsychiatrykManager.getConnection().createStatement();

            statement.execute(
                    "CREATE TABLE IF NOT EXISTS registered_status (" +
                    "uuid TEXT PRIMARY KEY," +
                    "discordId LONG NOT NULL," +
                    "status TEXT NOT NULL)");
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
