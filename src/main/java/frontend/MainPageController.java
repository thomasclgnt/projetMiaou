package frontend;

import bdd.MessageOut;
import data.DatabaseController;
import data.IPAddress;
import data.User;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    @FXML
    private VBox vboxMessages;
    @FXML
    private ScrollPane spConv;
    @FXML
    private TextField messageToSend;

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

    @FXML
    void sendMessage(ActionEvent event) {

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
            public void changed(ObservableValue<? extends String> observableUsername, String oldValue, String newValue) {

                User currentConversationUser ;

                if(newValue != null) {
                    remoteUsernameLabel.setText(newValue);
                    currentConversationUser = mainFXML.serv.getUsers().findUserWithUsername(newValue);
                    try {
                        ArrayList<MessageOut> conversation = openConversation(currentConversationUser);
                        displayConversation(conversation);
                    } catch (IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        });

        vboxMessages.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                spConv.setVvalue((Double) newValue);
            }
        });
    }

    //récupérer l'historique des messages
    public ArrayList<MessageOut> openConversation(User remoteUser) throws IOException, InterruptedException {
        Socket socket = mainFXML.serv.processStartConversation(remoteUser);
        ArrayList<MessageOut> Listmsg = DatabaseController.restoreConversation(IPAddress.getLocalIP().getHostAddress(), remoteUser.addressIP) ;
        return Listmsg ;
    }

    public void displayConversation (ArrayList<MessageOut> conversation){
        for (MessageOut messageOut : conversation) {
            String msg = messageOut.text;
            String horodatage = messageOut.horodatage;
            String IPsource = messageOut.IPsource ;
            String myLocalIP = IPAddress.getLocalIP().getHostAddress();
            if (myLocalIP.equals(IPsource)){
                addMessageSent(msg, horodatage, vboxMessages);
            } else {
                addMessageReceived(msg, horodatage, vboxMessages);
            }
            System.out.println("message : " + msg + "\n" + "   et heure = " + horodatage);
        }
    }

    public void addMessageReceived(String message, String horodatage, VBox vBox){
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5,5,5,10));
        Text textMessage = new Text(message) ;
        TextFlow textFlowMessage = new TextFlow(textMessage);
        textFlowMessage.getStyleClass().clear();
        textFlowMessage.getStyleClass().add("txt-fld");
        textFlowMessage.setPadding(new Insets(5,10,5,10));
        hBox.getChildren().add(textFlowMessage);

        Platform.runLater(() -> vBox.getChildren().add(hBox));

    }

    //String message = messageToSend.getText(); à faire dans sendMessage
    //et utiliser ce message dans l'appel de addMesageSent
    public void addMessageSent(String message, String horodatage, VBox vBox){
        if (!message.isEmpty()){
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_RIGHT);
            hBox.setPadding(new Insets(5,5,5,10));

            Text textMessage = new Text(message) ;
            TextFlow textFlowMessage = new TextFlow(textMessage);
            textFlowMessage.getStyleClass().clear();
            textFlowMessage.getStyleClass().add("txtfld");
            textFlowMessage.setPadding(new Insets(5,10,5,10));

            hBox.getChildren().add(textFlowMessage);
            vBox.getChildren().add(hBox);
            messageToSend.clear();
        }

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
