package bdd;

import java.sql.*;


public class CreateTable {

    /**
     * Create a new table in the test database
     * Deja fait, normalement pas d'autres tables à créer.
     */
    public static void createTableMessageDB() {
        // SQLite connection string
        /** Chemin relatif vers BDD" */
        String url = "jdbc:sqlite:./database/miaoudb" ;

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS Messagedb (\n"
                + "     source varchar(40) NOT NULL,\n"
                + "     IPsource varchar(40) NOT NULL,\n"
                + "     destinataire varchar(40) NOT NULL,\n"
                + "     IPdest varchar(40) NOT NULL,\n"
                + "     message Text NOT NULL,\n"
                + "     horodatage Datetime NOT NULL,\n"
                + "PRIMARY KEY(IPsource, IPdest, message, horodatage)"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createTableListUsers() {
        // SQLite connection string
        /** Chemin relatif vers BDD" */
        String url = "jdbc:sqlite:./database/miaoudb" ;

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS ListUsers (\n"
                + "     username varchar(40) NOT NULL,\n"
                + "     ip varchar(40) NOT NULL,\n"
                + "PRIMARY KEY(username, ip)"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createTableMyself() {
        // SQLite connection string
        /** Chemin relatif vers BDD" */
        String url = "jdbc:sqlite:./database/miaoudb" ;

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS Myself (\n"
                + "     username varchar(40) NOT NULL,\n"
                + "PRIMARY KEY(username)"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        createTableMessageDB();
        createTableListUsers();
        createTableMyself();

    }
}
