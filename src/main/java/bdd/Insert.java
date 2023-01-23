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
        String url = "jdbc:sqlite:./miaoudb.db" ;
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

    public void insert(String source, String IPsource, String destinataire, String IPdest, String message, String horodatage) {
        String sql = "INSERT INTO Messagedb(source, IPsource, destinataire, IPdest, message, horodatage) VALUES(?,?,?,?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, source);
            pstmt.setString(2, IPsource);
            pstmt.setString(3, destinataire);
            pstmt.setString(4, IPdest);
            pstmt.setString(5, message);
            pstmt.setString(6, horodatage);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertUsers(String username, String ip) {
        String sql = "INSERT INTO ListUsers(username, ip) VALUES(?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, ip);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void add_data(String source, String IPsource, String destinataire, String IPdest, String message, String horodatage) {
        Insert data = new Insert() ;
        data.insert(source, IPsource, destinataire, IPdest, message, horodatage) ;
    }

    public static void add_user(String username, String ip) {
        Insert data = new Insert() ;
        data.insertUsers(username, ip) ;
    }

    public void insertMyself(String username) {
        String sql = "INSERT INTO Myself(username) VALUES(?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addMyself(String username) {
        Insert data = new Insert() ;
        data.insertMyself(username); ;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Insert data = new Insert();
        // insert three new rows
        //A TESTER
        //Date time1 =  new Date(System.currentTimeMillis());//TODO marche, reste à comprendre le format
        //data.insert("Marie", "200", "Thomas", "100", "on est lundi", "10:00");
        //data.insertUsers("thomas","100");
        data.insertMyself("thomas");
    }

}
