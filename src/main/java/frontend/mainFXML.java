package frontend;

import data.ListUser;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import service.Service;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

public class mainFXML extends Application {

    public static Stage primaryStage ;
    public static ListUser remoteUsers ;
    public static Service serv;

    static {
        try {
            serv = new Service();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public static boolean isValid(String username) {
        String legalCharacters = "abcdefghijklmnopqrstuvwxzy0123456789éèëê-";
        boolean valid = true;
        if (username.length() < 3 || username.length() > 16) {
            valid = false;
        }
        else {
            for (int x = 0; x < username.length() ; x++) {
                boolean found = false;
                for (int z = 0; z < legalCharacters.length(); z++) {
                    char c = username.charAt(x);
                    c = java.lang.Character.toLowerCase(c);
                    if (c == legalCharacters.charAt(z)) {
                        found = true;
                    }
                }
                if (!found) {
                    valid = false;
                    break;
                }
            }
        }
        return valid;
    }

    void logout(Stage stage) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION) ;
        alert.setTitle("Log out");
        alert.setHeaderText("You're about to be disconnected from the MiaouMiaou Chat App.");
        alert.setContentText("Are you sure you want to log out ?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            //mainFXML.serv.processDeconnection();
            System.out.println("You are logged out.");
            stage.close();
        }
    }

    @Override
    public void start(Stage stage) throws IOException {

        primaryStage = stage ;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoginPage.fxml"));
        Parent parent = loader.load() ;

        Scene scene = new Scene(parent, 780, 580) ;
        scene.getStylesheets().add("/Stylesheet.css");

        stage.setTitle("Miaou Miaou");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(event -> {
            event.consume();
            try {
                logout(stage);

                Platform.exit();
                System.exit(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) throws SocketException, UnknownHostException {

        serv.lancerService();

        //avant de récupérer la liste, il faut lancer une méthode du service qui récupérer les users déjà connectés

        remoteUsers = serv.getUsers() ;

        Application.launch(args);

    }

}
