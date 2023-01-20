package bdd;

import java.sql.*;

/**
 *
 * @author sqlitetutorial.net
 */
public class CreateDatabase {

    /**
     * Connect to a sample database
     * */
    public static void createNewDatabase() {

        /** Chemin relatif vers BDD" */
        String url = "jdbc:sqlite:./miaoudb" ;

        /** lien pour la session Marie : */
        //String url = "jdbc:sqlite:/home/mecaliff/Bureau/4A/Projet_Miaou_local/projetMiaou/database/" + fileName;
        /** lien pour la session Thomas : */
        //String url = "jdbc:sqlite:/home/caylagin/Bureau/4IR/Projet/projetMiaou/database/" + fileName;



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
