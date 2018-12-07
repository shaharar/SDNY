package View;

import DBManager.DBManager;
import Model.ProfileObject;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import Controller.Controller;

public class Main extends Application {

    private  View view;
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getClassLoader().getResource("Login.fxml").openStream());
        primaryStage.setTitle("Welcome to Vacation4U");
        Scene scene = new Scene(root, 1024, 600);
        primaryStage.setScene(scene);
        view = fxmlLoader.getController();
        view.setMainStage(primaryStage);
        view.setStage(primaryStage);
        view.setMainStage(primaryStage);
        Controller con = new Controller(view);
        Awindow.setController(con);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("ViewStyle.css").toExternalForm());
        primaryStage.show();
        view.btn_Login.requestFocus();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
