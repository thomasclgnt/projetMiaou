package bdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Update {

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
     * @param id
     * @param name name of the warehouse
     * @param capacity capacity of the warehouse
     */
    public void update(int id, String name, double capacity) {
        String sql = "UPDATE Messagedb SET name = ? , "
                + "capacity = ? "
                + "WHERE id = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, name);
            pstmt.setDouble(2, capacity);
            pstmt.setInt(3, id);
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

        //UpdateApp app = new UpdateApp();
        // update the warehouse with id 3
        //app.update(3, "Finished Products", 5500);
    }

}

