package frontend;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;

public class MainPageController {

    @FXML
    private TextField changeUsernameField;
    @FXML
    private Label usernameLabel;
    @FXML
    private TextFlow usernameInvalid;
    @FXML
    private HBox logoutButton;
    @FXML
    private AnchorPane scenePane;

    Stage stage ;

    @FXML
    void changeUsername(ActionEvent event) throws IOException {
        String new_username = changeUsernameField.getText();

        if (new_username.equals("") || !mainFXML.isValid(new_username)) {
            Text text = new Text ("Please enter a valid \n username.");
            usernameInvalid.getChildren().clear();
            usernameInvalid.getChildren().add(text);

        } else {
            mainFXML.serv.processChangeUsername(new_username);
            usernameInvalid.getChildren().clear();
            usernameLabel.setText(new_username);
        }

    }

    @FXML
    void logout(MouseEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION) ;
        alert.setTitle("Log out");
        alert.setHeaderText("You're about to be disconnected from the MiaouMiaou Chat App.");
        alert.setContentText("Are you sure you want to log out ?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            stage = (Stage) scenePane.getScene().getWindow();
            //mainFXML.serv.processDeconnection();
            System.out.println("You are logged out.");
            stage.close();
            Platform.exit();
            System.exit(0);
        }
    }

    public void displayUsername(String username) {
        usernameLabel.setText(username);
    }


}
