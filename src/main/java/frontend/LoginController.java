package frontend;

import data.ListUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

        if (username.equals("") || !mainFXML.isValid(username)) {

            Text text = new Text ("Please enter a valid username.");
            usernameInvalid.getChildren().clear();
            usernameInvalid.getChildren().add(text);
            System.out.println("Username invalid");

            Alert alert = new Alert(Alert.AlertType.ERROR) ;
            alert.setTitle("Username invalid");
            alert.setHeaderText("This username is invalid, please choose another one.");
            alert.setContentText("Your username must be between 2 and 16 characters \nand not contain an unsupported character (, . ? ! : / % * )");
            alert.showAndWait() ;

        } else {

            mainFXML.serv.processGetRemoteUsers();
            Thread.sleep(1000);
            mainFXML.serv.getListUsersFromDB();
            boolean available = mainFXML.serv.processCheckUsername(username) ;

            if (!available) {

                  Text text = new Text ("This username is already taken, please choose another one.");
                  usernameInvalid.getChildren().clear();
                  usernameInvalid.getChildren().add(text);
                  System.out.println("Username taken");

                Alert alertb = new Alert(Alert.AlertType.ERROR) ;
                alertb.setTitle("Username taken");
                alertb.setHeaderText("This username is already taken, please choose another one.");
                alertb.showAndWait() ;


            } else {

                mainFXML.serv.processConnection(username);

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

}
