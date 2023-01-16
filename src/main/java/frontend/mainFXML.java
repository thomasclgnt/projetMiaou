package frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;

import java.io.IOException;

public class mainFXML extends Application {

    public static Stage primaryStage ;

    @Override
    public void start(Stage stage) throws IOException {

        primaryStage = stage ;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoginPage.fxml"));
        Parent parent = loader.load() ;

        Scene scene = new Scene(parent, 1200, 800) ;
        // scene.getStylesheets().add("/styles.css");

        stage.setTitle("Miaou Miaou");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
