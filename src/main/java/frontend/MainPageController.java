package frontend;

import bdd.MessageOut;
import data.DatabaseController;
import data.IPAddress;
import data.User;
import data.UserNotFound;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.*;

public class MainPageController implements Initializable {

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
    @FXML
    private ListView<String> listUsersView;
    @FXML
    private Label remoteUsernameLabel;

    //private ObservableList<User> observableListUsers ;
    private ObservableList<String> observableListUsernames ;

    Stage stage ;
    String currentConversationUsername;

    @FXML
    void changeUsername(ActionEvent event) throws IOException {
        String new_username = changeUsernameField.getText();

        if (new_username.equals("") || !mainFXML.isValid(new_username)) {

            Text text = new Text ("Please enter a valid \n username.");
            usernameInvalid.getChildren().clear();
            usernameInvalid.getChildren().add(text);

        } else {

            boolean available = mainFXML.serv.processCheckUsername(new_username) ;

            if (!available) {

                Text text = new Text ("This username is already taken, \n please choose another one.");
                usernameInvalid.getChildren().clear();
                usernameInvalid.getChildren().add(text);
                System.out.println("Username taken");

            } else {

                mainFXML.serv.processChangeUsername(new_username);
                usernameInvalid.getChildren().clear();
                usernameLabel.setText(new_username);

            }

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
            mainFXML.serv.processDeconnection();
            System.out.println("You are logged out.");
            stage.close();
            Platform.exit();
            System.exit(0);
        }
    }

    public void displayUsername(String username) {
        usernameLabel.setText(username);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //System.out.println(connectedUsers.toString()) pour vérifier l'affichage dans ListView


        MyUpdate update = new MyUpdate();
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(update, 500, 500);

        listUsersView.setItems(this.observableListUsernames);

        listUsersView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableUsername, String s, String t1) {

                User currentConversationUser ;
                currentConversationUsername = listUsersView.getSelectionModel().getSelectedItem();
                remoteUsernameLabel.setText(currentConversationUsername);
                currentConversationUser = mainFXML.serv.getUsers().findUserWithUsername(currentConversationUsername);



                //coder une méthode externe qu'on appelle dans laquelle on :


                    //processStartSession avec ce User
                Socket socket = null;
                try {
                    socket = mainFXML.serv.processStartConversation(currentConversationUser);
                    //récupérer l'historique des messages
                    ArrayList<MessageOut> Listmsg = DatabaseController.restoreConversation(IPAddress.getLocalIP().getHostAddress(), currentConversationUser.addressIP) ;
                    for (MessageOut messageOut : Listmsg) {
                        String msg = messageOut.text;
                        String horodatage = messageOut.horodatage;
                        System.out.println("message : " + msg + "\n" + "   et heure = " + horodatage);
                    }

                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }

                //ajouter session à la liste de sessions ? sinon à quoi elle sert ?


                    //récupérer l'historique des messages

                    //afficher les messages

                //envoyer message
                //mainFXML.serv.processSendMessage("" , currentConversationUser, socket);
            }

            //TODO coder dans changed() ce qu'il le passe quand on clique sur un user
            // càd ouverture d'une session de chat et récupération de l'historique de chat

        });
    }

    public void updateListUsers() {
        this.observableListUsernames = FXCollections.observableArrayList(mainFXML.serv.getUsers().toUsernameList());
        listUsersView.setItems(this.observableListUsernames);
    }

    class MyUpdate extends TimerTask {

        @Override
        public void run() {
            Platform.runLater(() -> {
                updateListUsers() ;
            });
        }
    }

}
