package library;

import library.config.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DbSmokeTest {

    public static void main(String[] args) {
        try (Connection conn = Database.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT student_id, name, role FROM users")) {

            System.out.println("DB connection OK. Users:");
            while (rs.next()) {
                System.out.println(
                        rs.getString("student_id") + " | " +
                                rs.getString("name") + " | " +
                                rs.getString("role")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

