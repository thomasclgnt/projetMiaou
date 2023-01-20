package bdd;

import data.User;

import java.sql.*;

public class Delete {
        //TODO cette classe servirait si on implémente un truc pour que les utilisateurs puissent supprimer des messages, sinon ne sert à rien dans l'état
        /**
         * Connect to the test.db database
         *
         * @return the Connection object
         */
        private Connection connect() {
            // SQLite connection string
            /** Chemin relatif vers BDD" */
            String url = "jdbc:sqlite:./database/miaoudb" ; ;
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
         */

        public void deleteData(String source, String IPsource, String destinataire, String IPdest, String message) {
            String sql = "DELETE FROM Messagedb WHERE source = ? AND IPsource = ? AND destinataire = ? AND IPdest = ? AND message = ?";

            try (Connection conn = this.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                // set the corresponding param
                pstmt.setString(1, source);
                pstmt.setString(2, IPsource);
                pstmt.setString(3, destinataire);
                pstmt.setString(4, IPdest);
                pstmt.setString(5, message);
                //pstmt.setTimestamp(6, horodatage);
                pstmt.executeUpdate();
                // execute the delete statement
                pstmt.executeUpdate();

                System.out.println("L'entrée a été supprimée de la base de donnée");

            } catch (SQLException e) {
                System.out.println(e.getMessage()); //TODO l'entrée n'a pas pu être supprimée => pas dans la bdd
            }
        }

    public void deleteUserLine(String username, String ip) {
        String sql = "DELETE FROM ListUsers WHERE username = ? AND ip = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, username);
            pstmt.setString(2, ip);
            pstmt.executeUpdate();
            // execute the delete statement
            pstmt.executeUpdate();
            System.out.println("L'entrée a été supprimée de la base de donnée");

        } catch (SQLException e) {
            System.out.println(e.getMessage()); //TODO l'entrée n'a pas pu être supprimée => pas dans la bdd
        }
    }

    public static void deleteUser(User u) {
            Delete app = new Delete() ;
            app.deleteUserLine(u.username, u.addressIP);
    }

        /**
         * @param args the command line arguments
         */
        public static void main(String[] args) {

            Delete app = new Delete();
            // delete the row with id 3
            //app.deleteData("marie", "ipmarie", "thomas", "ipthomas", "ok");
            //app.deleteData("Thomas", "ip10000", "Marie", "IP101", "on est vendredi");
            User Leonie = new User("léonie", "105", 1234) ;
            //app.deleteUserLine("bucy","001");
            //app.deleteUser (Leonie) ;
        }

    }

