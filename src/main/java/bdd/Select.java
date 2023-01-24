package bdd;

import data.ListUser;

import java.sql.*;
import java.util.ArrayList;

public class Select {

    /**
     * Connect to the miaoudb.db database
     * @return the Connection object
     */
    private Connection connect() {
        // SQLite connection string
        // Chemin relatif vers BDD" */
        String url = "jdbc:sqlite:./miaoudb.db" ;


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
     * notre user local en source et celui avec qui il veut converser en destination. retourne une liste de MessageOut  */
    public ArrayList<MessageOut> select_conversation(String IPsource, String IPdest) {
        ArrayList<MessageOut> messagesRecus = new ArrayList<MessageOut>() ;

        String sql = "SELECT rowid, source, IPsource, destinataire, IPdest, message, horodatage "
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

                MessageOut data_ligne = new MessageOut(rs.getString("source"), rs.getString("IPsource"), rs.getString("destinataire"), rs.getString("IPdest"), rs.getString("message"),rs.getString("horodatage"), rs.getString("rowid")) ;
                messagesRecus.add(data_ligne);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messagesRecus ;
    }

    public static ArrayList<MessageOut> restore(String IPsource, String IPdest) {
        ArrayList<MessageOut> res = new ArrayList<MessageOut>() ;
        Select app = new Select();
        res = app.select_conversation(IPsource, IPdest);
        return res ;
    }

    public ListUser selectAllListUsers(){
        String sql = "SELECT rowid, * FROM ListUsers ";
        String name ;
        String address ;
        ListUser res = new ListUser() ;

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                name = rs.getString("username") ;
                address = rs.getString("ip") ;
                res.addUser(name, address, 1234); ;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return res ;
    }

    public static ListUser restoreUsers() {
        ListUser res = new ListUser() ;
        Select app = new Select();
        res = app.selectAllListUsers() ;
        return res ;
    }

    public String selectAllMyself(){
        String sql = "SELECT * FROM Myself ";
        String name = "" ;


        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                name = rs.getString("username") ;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return name ;
    }

    public static String restoreMyself() {
        String res;
        Select app = new Select();
        res = app.selectAllMyself();
        return res;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Select app = new Select();
        app.selectAll();
        //System.out.println(app.selectAllListUsers().listToString());
        //app.select_conversation("100","200");
        //System.out.println(restoreMyself()) ;
    }

}
