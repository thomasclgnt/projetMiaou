package bdd;

import java.sql.*;

public class Select {

    /**
     * Connect to the miaoudb.db database
     * @return the Connection object
     */
    private Connection connect() {
        // SQLite connection string
        /** Chemin relatif vers BDD" */
        String url = "jdbc:sqlite:./database/miaoudb" ;

        /** lien pour la session Marie : */
         //"jdbc:sqlite:/home/mecaliff/Bureau/4A/Projet_Miaou_local/projetMiaou/database/miaoudb" ;
        /** lien pour la session Thomas : */
        //String url = "jdbc:sqlite:/home/caylagin/Bureau/4IR/Projet/projetMiaou/database/miaoudb";

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     *
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

    /** ce sera notre restoreConversation : le but est de retrouver tous les messages de la bdd entre 2 utilisateurs :
     * notre user local en source et celui avec qui il veut converser en destination.  */
    public void select_conversation(String source, String destinataire) {
        String sql = "SELECT message, horodatage "
                + "FROM Messagedb WHERE source = ? AND destinataire = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){

            // set the value
            pstmt.setString(1,source);
            pstmt.setString(2, destinataire);
            //
            ResultSet rs  = pstmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getString("message") + "\t" +
                        rs.getDate("horodatage"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Select app = new Select();
        app.selectAll();
    }

}
