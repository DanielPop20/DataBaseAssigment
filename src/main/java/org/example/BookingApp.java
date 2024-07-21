package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BookingApp {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/my_java_database_assigment";
    private static final String USER = "postgres";
    private static final String PASS = "Doinaspopricas1";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {

            // inserare date in tabelul accommodation
            String insertAccommodationSQL = "INSERT INTO accommodation (type, bed_type, max_guests, description) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertAccommodationSQL)) {
                pstmt.setString(1, "Hotel");
                pstmt.setString(2, "Double");
                pstmt.setInt(3, 2);
                pstmt.setString(4, "A double room in a hotel");
                pstmt.executeUpdate();
            }

            // inserare date in tabelul room_fair
            String insertRoomFairSQL = "INSERT INTO room_fair (value, season) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertRoomFairSQL)) {
                pstmt.setDouble(1, 100.0);
                pstmt.setString(2, "Summer");
                pstmt.executeUpdate();
            }

            // inserare date in tabelul accommodation_room_fair_relation
            String insertRelationSQL = "INSERT INTO accommodation_room_fair_relation (accommodation_id, room_fair_id) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertRelationSQL)) {
                pstmt.setInt(1, 1); // id ul accommodation
                pstmt.setInt(2, 1); // id ul room_fair
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
