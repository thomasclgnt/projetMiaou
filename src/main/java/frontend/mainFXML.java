package frontend;

import data.ListUser;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;

import java.io.IOException;

public class mainFXML extends Application {

    public static Stage primaryStage ;
    public static ListUser users ;

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

    public static void main(String[] args) {

        users = new ListUser();

        Application.launch(args);
    }

}
