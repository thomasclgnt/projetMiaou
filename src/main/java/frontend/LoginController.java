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
    void sendLogin(ActionEvent event) throws IOException, InterruptedException {
        // on récupère l'username tapé sur l'interface
        String username = usernameChoice.getText();

        System.out.println("Login button pressed");
        System.out.println("Username chosen = " + username);

        mainFXML.serv.processGetRemoteUsers();
        mainFXML.serv.getListUsersFromDB();
        boolean available = mainFXML.serv.processCheckUsername(username) ;

        if (username.equals("") || !mainFXML.isValid(username)) {

            Text text = new Text ("Please enter a valid username.");
            usernameInvalid.getChildren().clear();
            usernameInvalid.getChildren().add(text);

        } else if (!available) {

            Text text = new Text ("This username is already taken, please choose another one.");
            usernameInvalid.getChildren().clear();
            usernameInvalid.getChildren().add(text);

        } else {

            //passage à la page suivante
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("MainPage.fxml"));
            Parent parent = loader.load();

            MainPageController mainpagecontroller = loader.getController();
            mainpagecontroller.displayUsername(username);

            Scene scene = new Scene(parent, 780, 580);
            scene.getStylesheets().add("/Stylesheet.css");
            // client.setScene(scene);
            mainFXML.primaryStage.setTitle("Miaou Miaou - Connected");
            mainFXML.primaryStage.setScene(scene);

        }

    }



}
