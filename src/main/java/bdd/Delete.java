package bdd;

import data.User;

import java.sql.*;

public class Delete {

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


            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    public void deleteUserLine(String ip) {
        String sql = "DELETE FROM ListUsers WHERE ip = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, ip);
            pstmt.executeUpdate();
            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteUser(String ip) {
            Delete app = new Delete() ;
            app.deleteUserLine(ip);
    }

        public static void main(String[] args) {
            //TODO vider main
            Delete app = new Delete();
            // delete the row with id 3
            //app.deleteData("marie", "ipmarie", "thomas", "ipthomas", "ok");
            //app.deleteData("Thomas", "ip10000", "Marie", "IP101", "on est vendredi");
            //app.deleteUserLine("bucy","001");
            app.deleteUser ("100") ;
        }

    }

