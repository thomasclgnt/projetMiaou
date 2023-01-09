package bdd;

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
         *
         * @param source
         * @param destinataire
         * @param message
         * @param horodatage
         */
        public void deleteData(String source, String destinataire, String message, Timestamp horodatage) {
            String sql = "DELETE FROM Messagedb WHERE source = ? AND destinataire = ? AND message = ? AND horodatage = ?";

            try (Connection conn = this.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                // set the corresponding param
                pstmt.setString(1, source);
                pstmt.setString(2, destinataire);
                pstmt.setString(3, message);
                pstmt.setTimestamp(4, horodatage);
                pstmt.executeUpdate();
                // execute the delete statement
                pstmt.executeUpdate();

                System.out.println("L'entrée a été supprimée de la base de donnée");

            } catch (SQLException e) {
                System.out.println(e.getMessage()); //TODO l'entrée n'a pas pu être supprimée => pas dans la bdd
            }
        }

        /**
         * @param args the command line arguments
         */
        public static void main(String[] args) {
            Timestamp time1 =  new Timestamp(2023, 11, 03, 20, 45, 0, 0);
            Timestamp time2 =  new Timestamp(2020, 01, 03, 20, 45, 0, 0);
            Timestamp time0 =  new Timestamp(1885, 01, 02, 02, 05, 10, 50);

            Delete app = new Delete();
            // delete the row with id 3
            app.deleteData("Henry", "Matisse", "je peins", time0);
            //app.deleteData("Marie", "Thomas", "c'est vrai ??? waaaaa", time2);
        }

    }

