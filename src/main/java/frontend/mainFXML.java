package frontend;

import data.ListUser;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;
import service.Service;

import java.io.IOException;
import java.net.SocketException;

public class mainFXML extends Application {

    public static Stage primaryStage ;
    public static ListUser remoteUsers ;


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
    }

    public static void main(String[] args) throws SocketException {

        Service serv = new Service() ;
        serv.lancerService();

        //avant de récupérer la liste, il faut lancer une méthode du service qui récupérer les users déjà connectés

        remoteUsers = serv.getUsers() ;

        Application.launch(args);

    }

}
