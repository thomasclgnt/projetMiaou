package bdd;

import java.sql.*;

/** A nettoyer, enlever les méthodes qu'on a déplacé dans les classes CreateTable et Select

/**
 *
 * @author sqlitetutorial.net
 */
public class Database {


        /**
         * Connect to a sample database
         *
         * @param fileName the database file name
         */
        public static void createNewDatabase(String fileName) {

            String url = "jdbc:sqlite:/home/mecaliff/Bureau/4A/Projet_Miaou_local/projetMiaou/database/" + fileName;

            try (Connection conn = DriverManager.getConnection(url)) {
                if (conn != null) {
                    DatabaseMetaData meta = conn.getMetaData();
                    System.out.println("The driver name is " + meta.getDriverName());
                    System.out.println("A new database has been created!");
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        /**
         * @param args the command line arguments
         */



        /**
         *
         * @author sqlitetutorial.net
         */


            /**
             * Create a new table in the test database
             *
             */
        public static void createNewTable() {
            // SQLite connection string
            String url = "jdbc:sqlite:/home/mecaliff/Bureau/4A/Projet_Miaou_local/projetMiaou/database/miaoudb";

            // SQL statement for creating a new table
            String sql = "CREATE TABLE IF NOT EXISTS Messagedb (\n"
                    + "     source varchar(40) NOT NULL,\n"
                    + "     destinataire varchar(40) NOT NULL,\n"
                    + "     message Text NOT NULL,\n"
                    + "     horodatage Datetime NOT NULL,\n"
                    + "PRIMARY KEY(source, destinataire, message, horodatage)"
                    + ");";

            try (Connection conn = DriverManager.getConnection(url);
                 Statement stmt = conn.createStatement()) {
                // create a new table
                stmt.execute(sql);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    /**
     * Connect to the miaoudb.db database
     * @return the Connection object
     */
    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:/home/mecaliff/Bureau/4A/Projet_Miaou_local/projetMiaou/database/miaoudb";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * select all rows in the warehouses table
     */
    public void selectAll(){
        String sql = "SELECT source, destinataire, message, horodatage FROM Messagedb";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getString("source") +  "\t" +
                        rs.getString("destinataire") + "\t" +
                        rs.getString("message") + "\t" +
                        rs.getDate("horodatage"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void main(String[] args) {
            //createNewDatabase("miaoudb.db");
            //createNewTable();
        }
}
