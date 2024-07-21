package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookingAppQuery {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/my_java_database_assigment";
    private static final String USER = "postgres";
    private static final String PASS = "Doinaspopricas1";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {

            String querySQL = "SELECT a.type, a.bed_type, rf.value, rf.season " +
                    "FROM accommodation a " +
                    "JOIN accommodation_room_fair_relation arfr ON a.id = arfr.accommodation_id " +
                    "JOIN room_fair rf ON rf.id = arfr.room_fair_id";

            try (PreparedStatement pstmt = conn.prepareStatement(querySQL);
                 ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    String type = rs.getString("type");
                    String bedType = rs.getString("bed_type");
                    double value = rs.getDouble("value");
                    String season = rs.getString("season");

                    System.out.println("Type: " + type + ", Bed Type: " + bedType + ", Price: " + value + ", Season: " + season);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

