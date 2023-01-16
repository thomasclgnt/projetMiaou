package bdd;

import java.sql.*;

public class Update {

    // TODO pas sûr de l'utilité pour le moment, on y reviendra

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
     * Update data of a warehouse specified by the id
     *
     *  @param source
     *  @param destinataire
     *  @param message
     *  @param horodatage
     */
    public void updateMessage(String message_new, String source, String IPsource, String destinataire, String IPdest, String message_old) {
        String sql = "UPDATE Messagedb SET message = ?"
                + "WHERE source = ? AND IPsource = ? AND destinataire = ? AND IPdest = ? AND message = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, message_new);
            pstmt.setString(2, source);
            pstmt.setString(3, IPsource);
            pstmt.setString(4, destinataire);
            pstmt.setString(5, IPdest);
            pstmt.setString(6, message_old);
            // update
            pstmt.executeUpdate();
            System.out.println("le message '" + message_old +"' a été modifié en '" + message_new + "'");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Update app = new Update();
        app.updateMessage("on est lundi soir", "Marie", "200","Thomas","100","on est lundi");
    }

}

