package frontend;

import data.ListUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;

public class LoginController {

    @FXML
    private Button buttonLogin;
    @FXML
    private TextField usernameChoice;
    @FXML
    private TextFlow usernameInvalid;

    @FXML
    void sendLogin(ActionEvent event) throws IOException {
        // on récupère l'username tapé sur l'interface
        String username = usernameChoice.getText();

        System.out.println("Login button pressed");
        System.out.println("Username chosen = " + username);


        // on teste si l'username est déjà pris ou non et s'il est valide
        //on appelle process NewConnection, selon ce qu'il renvoie true ou false

        if (username.equals("") || !mainFXML.isValid(username)) {
            Text text = new Text ("Please enter a valid username.");
            //usernameVide.setStyle("-fx-background-color: red");
            usernameInvalid.getChildren().clear();
            usernameInvalid.getChildren().add(text);

                // mainFXML.remoteUsers.checkUsernameAvailable(username)
        } else if (true) {
            //gérer l'ajout d'une nouvelle connexion
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("MainPage.fxml"));
            Parent parent = loader.load();

            MainPageController mainpagecontroller = loader.getController();
            mainpagecontroller.displayUsername(username);

            Scene scene = new Scene(parent, 780, 580);
            scene.getStylesheets().add("/Stylesheet.css");
            // client.setScene(scene);
            mainFXML.primaryStage.setTitle("Miaou Miaou - Connected");
            mainFXML.primaryStage.setScene(scene);
        } else {
            //TextFlow => ce nom d'utilisateur est déjà pris, veuillez en choisir un nouveau
        }

    }



}
