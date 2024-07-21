package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

public class DatabaseQueryTest {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/my_java_database_assigment";
    private static final String USER = "postgres";
    private static final String PASS = "Doinaspopricas1";

    @Test
    void testQueryPrices() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {

            String query = "SELECT a.type, a.bed_type, rf.value, rf.season " +
                    "FROM accommodation a " +
                    "JOIN accommodation_room_fair_relation arr ON a.id = arr.accommodation_id " +
                    "JOIN room_fair rf ON rf.id = arr.room_fair_id";

            try (PreparedStatement pstmt = conn.prepareStatement(query);
                 ResultSet rs = pstmt.executeQuery()) {


                while (rs.next()) {
                    String type = rs.getString("type");
                    String bedType = rs.getString("bed_type");
                    double value = rs.getDouble("value");
                    String season = rs.getString("season");
                    System.out.println("Type: " + type + ", Bed Type: " + bedType +
                            ", Price: " + value + ", Season: " + season);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

