package org.example;

import org.junit.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.fail;

public class DatabaseInsertTest {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/my_java_database_assigment";
    private static final String USER = "postgres";
    private static final String PASS = "Doinaspopricas1";

    @Test
    public void testInsertData() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            conn.setAutoCommit(false); // pornim tranzactia manual

            // inseram date in tabelul accommodation
            String insertAccommodationSQL = "INSERT INTO accommodation (type, bed_type, max_guests, description) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmtAccommodation = conn.prepareStatement(insertAccommodationSQL)) {
                pstmtAccommodation.setString(1, "Hotel");
                pstmtAccommodation.setString(2, "Double");
                pstmtAccommodation.setInt(3, 2);
                pstmtAccommodation.setString(4, "A double room in a hotel");
                pstmtAccommodation.executeUpdate();
            }

            // inseram date in tabelul room_fair
            String insertRoomFairSQL = "INSERT INTO room_fair (value, season) VALUES (?, ?)";
            try (PreparedStatement pstmtRoomFair = conn.prepareStatement(insertRoomFairSQL)) {
                pstmtRoomFair.setDouble(1, 100.0);
                pstmtRoomFair.setString(2, "Summer");
                pstmtRoomFair.executeUpdate();
            }

            // ibținem id urile generate pentru a le folosi in tabela de relatii
            int accommodationId = getLastInsertedId(conn, "accommodation");
            int roomFairId = getLastInsertedId(conn, "room_fair");

            // inseram date in tabela de relatie accommodation_room_fair_relation
            String insertRelationSQL = "INSERT INTO accommodation_room_fair_relation (accommodation_id, room_fair_id) VALUES (?, ?)";
            try (PreparedStatement pstmtRelation = conn.prepareStatement(insertRelationSQL)) {
                pstmtRelation.setInt(1, accommodationId);
                pstmtRelation.setInt(2, roomFairId);
                pstmtRelation.executeUpdate();
            }

            conn.commit(); // confirmam

            // verificam daca datele au fost inserate corect

        } catch (SQLException e) {
            e.printStackTrace();
            fail("SQL Exception: " + e.getMessage());
        }
    }

    // metoda pentru a obtine ultimul ID inserat intr un tabel
    private int getLastInsertedId(Connection conn, String tableName) throws SQLException {
        int id = 0;
        String sql = "SELECT last_value FROM " + tableName + "_id_seq";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                id = rs.getInt("last_value");
            }
        }
        return id;
    }
}





