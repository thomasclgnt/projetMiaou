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
    public void update(String source, String destinataire, String message, Timestamp horodatage) {
        String sql = "UPDATE Messagedb SET source = ? , "
                + "destinataire = ?, "
                + "message = ? , "
                + "horodatage = ? "
                + "WHERE source = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, source);
            pstmt.setString(2, destinataire);
            pstmt.setString(3, message);
            pstmt.setTimestamp(4, horodatage);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Update app = new Update();
        //update the warehouse with id 3
        //app.update(3, "Finished Products", 5500);
    }

}

