package frontend;

import data.MessageOut;
import data.MessageIn;
import service.DatabaseController;
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

    private ObservableList<String> observableListUsernames ;
    private ObservableList<MessageIn> observableListMessages ;

    Stage stage ;
    User currentRemoteUser ;
    Socket currentSocket ;

    int indexPrint ;
    int sizeHistory = 0 ;

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

        changeUsernameField.clear();
    }

    @FXML
    void logout(MouseEvent event) throws IOException {

        System.out.println("I am trying to log out.");
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
    void sendMessage(ActionEvent event) throws IOException, InterruptedException {
        String message = messageToSend.getText();
        if (!message.isEmpty()) {
            String horodatage = mainFXML.serv.processSendMessage(message, currentRemoteUser, currentSocket);
            addMessageSent(message, horodatage, vboxMessages);
        }
    }

    public void displayUsername(String username) {
        usernameLabel.setText(username);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        MyUpdate update = new MyUpdate();
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(update, 500, 500);

        listUsersView.setItems(this.observableListUsernames);

        listUsersView.setOnMouseClicked(event -> {
            String currentConversationUsername = listUsersView.getSelectionModel().getSelectedItem() ;
            if(currentConversationUsername != null) {
                remoteUsernameLabel.setText(currentConversationUsername);
                currentRemoteUser = mainFXML.serv.getUsers().findUserWithUsername(currentConversationUsername);
                try {
                    openConversation(currentRemoteUser);
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
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
    public void openConversation(User remoteUser) throws IOException, InterruptedException {
        currentSocket = mainFXML.serv.processStartConversation(remoteUser);
        ArrayList<MessageOut> conversation = DatabaseController.restoreConversation(IPAddress.getLocalIP().getHostAddress(), remoteUser.addressIP) ;
        displayConversation(conversation);
        sizeHistory = conversation.size();
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
        }
        indexPrint = 0 ;
    }

    public void displayConversationIn(ArrayList<MessageIn> conversation){
        for (MessageIn messageIn : conversation) {
            String msg = messageIn.text;
            String horodatage = messageIn.horodatage;
            String IPsource = messageIn.IPsource ;
            String myLocalIP = IPAddress.getLocalIP().getHostAddress();

            if (myLocalIP.equals(IPsource)){
                addMessageSent(msg, horodatage, vboxMessages);
            } else {
                addMessageReceived(msg, horodatage, vboxMessages);
            }
        }
        indexPrint = 0 ;
    }

    public void addMessageReceived(String message, String horodatage, VBox vBox){
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5,5,5,10));
        Text textMessage = new Text(message) ;
        TextFlow textFlowMessage = new TextFlow(textMessage);
        textFlowMessage.getStyleClass().clear();
        textFlowMessage.getStyleClass().add("txtfld");
        textFlowMessage.setPadding(new Insets(5,10,5,10));
        hBox.getChildren().add(textFlowMessage);
        vBox.getChildren().add(hBox);
        indexPrint ++ ;
    }

    public void addMessageSent(String message, String horodatage, VBox vBox){
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setPadding(new Insets(5,5,5,10));

        Text textMessage = new Text(message) ;
        TextFlow textFlowMessage = new TextFlow(textMessage);
        textFlowMessage.getStyleClass().clear();
        textFlowMessage.getStyleClass().add("txt-fld");
        textFlowMessage.setPadding(new Insets(5,10,5,10));

        hBox.getChildren().add(textFlowMessage);
        vBox.getChildren().add(hBox);
        messageToSend.clear();
    }

    public void updateListUsers() {
        this.observableListUsernames = FXCollections.observableArrayList(mainFXML.serv.getUsers().toUsernameList());
        listUsersView.setItems(this.observableListUsernames);
    }

    public void updateMessages(){
        this.observableListMessages = FXCollections.observableArrayList(mainFXML.serv.getListMessage().convertToArrayList()) ;
        if (!observableListMessages.isEmpty()){
            int lastIndex = observableListMessages.size() ;
            System.out.println("ici : "+ lastIndex);
            //System.out.println("ici : "+ sizeHistory);
            System.out.println("ici : "+ indexPrint);
            if (lastIndex > (indexPrint)) {
                ArrayList<MessageIn> subList = (ArrayList<MessageIn>) observableListMessages.subList(indexPrint, lastIndex);
                System.out.println(subList);
                displayConversationIn(subList);
            }
        }
    }


    class MyUpdate extends TimerTask {

        @Override
        public void run() {
            Platform.runLater(() -> {
                updateListUsers() ;
                updateMessages() ;
            });
        }
    }

}
