package frontend;

import data.ListUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class LoginController {

    @FXML
    private Button buttonLogin;
    @FXML
    private TextField usernameChoice;
    @FXML
    private TextFlow usernameVide;

    @FXML
    void sendLogin(ActionEvent event) {
        // on récupère l'username tapé sur l'interface
        String username = usernameChoice.getText();

        System.out.println("Login button pressed");
        System.out.println("Username chosen = " + username);

        //on appelle process NewConnection, selon ce qu'il renvoie true ou false

        // on teste si l'username est déjà pris ou non
        if (username.equals("")) {
            Text text = new Text ("Please enter a username.   ");
            //usernameVide.setStyle("-fx-background-color: red");
            usernameVide.getChildren().clear();
            usernameVide.getChildren().add(text);

        } else if (mainFXML.remoteUsers.checkUsernameAvailable(username)) {
            //gérer l'ajout d'une nouvelle connexion
        } else {
            //TextFlow => ce nom d'utilisateur est déjà pris, veuillez en choisir un nouveau
        }

    }

}
