package frontend;

import data.ListUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private Button buttonLogin;
    @FXML
    private TextField usernameChoice;



    @FXML
    void sendLogin(ActionEvent event) {
        // on récupère l'username tapé sur l'interface
        String username = usernameChoice.getText();
        System.out.println("Login button pressed");
        System.out.println("Username chosen = " + username);

        // on teste si l'username est déjà pris ou non
        if (mainFXML.users.checkUsernameAvailable(username)) {
            //gérer l'ajout d'une nouvelle connexion
        } else {
            //TextFlow => ce nom d'utilisateur est déjà pris, veuillez en choisir un nouveau
        }

    }

}
