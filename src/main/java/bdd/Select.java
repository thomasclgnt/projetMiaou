package bdd;

import data.Message;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        String sql = "SELECT rowid, * FROM Messagedb ";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getString("rowid") + "\t" +
                        rs.getString("source") +  "\t" +
                        rs.getString("IPsource") + "\t" +
                        rs.getString("destinataire") + "\t" +
                        rs.getString("IPdest") + "\t" +
                        rs.getString("message") + "\t" +
                        rs.getString("horodatage"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /** ce sera notre restoreConversation : le but est de retrouver tous les messages de la bdd entre 2 utilisateurs :
     * notre user local en source et celui avec qui il veut converser en destination. retourne une liste de MessageBDD  */
    public ArrayList select_conversation(String IPsource, String IPdest) {
        String ligne ;
        ArrayList<MessageBDD> listLigne = new ArrayList<MessageBDD>() ;
        ArrayList<MessageBDD> messagesRecus = new ArrayList<MessageBDD>() ;

        String sql = "SELECT rowid, message, horodatage "
                + "FROM Messagedb WHERE IPsource = ? AND IPdest = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){

            // set the value
            pstmt.setString(1,IPsource);
            pstmt.setString(2, IPdest);
            //
            ResultSet rs  = pstmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getString("message") + "\t" +
                        rs.getString("horodatage") +
                        rs.getString("rowid") + "\t");

                ligne = rs.getString("message") + ' ' + rs.getString("horodatage") ;

                MessageBDD data_ligne = new MessageBDD(rs.getString("rowid"), rs.getString("message"),rs.getString("horodatage")) ;

                System.out.println("ligne : " + data_ligne.toString()) ; //
                messagesRecus.add(data_ligne) ;

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messagesRecus ;
    }

    public static ArrayList restore(String IPsource, String IPdest) {
        ArrayList<MessageBDD> res = new ArrayList<MessageBDD>() ;
        Select app = new Select();
        res = app.select_conversation(IPsource, IPdest);
        return res ;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Select app = new Select();
        app.selectAll();
        //app.select_conversation("100","200");
    }

}
