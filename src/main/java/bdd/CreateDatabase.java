package bdd;

import java.sql.*;

public class CreateDatabase {


    public static void createNewDatabase() {

        /** Chemin relatif vers BDD" */
        String url = "jdbc:sqlite:./miaoudb.db" ;


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

    public static void main(String[] args) {
            createNewDatabase();
        }
}
