package bdd;

import data.User;

import java.sql.*;
import java.time.LocalDateTime ;

public class Insert {

    /**
     * Connect to the test.db database
     *
     * @return the Connection object
     */
    private Connection connect() {
        // SQLite connection string
        /** Chemin relatif vers BDD" */
        String url = "./database/miaou.db" ;
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Insert a new row into the Messagedb table
     *
     * @param source
     * @param destinataire
     * @param message
     * @param horodatage
     */

    /** Source et destinataire sont des string, on prend le pseudo du source de type User pour le mettre en argument
     * A FAIRE PLUS TARD */

    public void insert(String source, String destinataire, String message, Timestamp horodatage) {
        String sql = "INSERT INTO Messagedb(source, destinataire, message, horodatage) VALUES(?,?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, source);
            pstmt.setString(2, destinataire);
            pstmt.setString(3, message);
            pstmt.setTimestamp(4, horodatage);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Insert data = new Insert();
        // insert three new rows
        //A TESTER
        Timestamp time1 =  new Timestamp(2023, 01, 03, 20, 45, 0, 0);
        data.insert("Thomas", "Marie", "je crois que la bdd fonctionne", time1);
        data.insert("Thomas", "Marie", "je crois que la bdd fonctionne", time1);
        data.insert("Thomas", "Marie", "je crois que la bdd fonctionne", time1);
    }

}
