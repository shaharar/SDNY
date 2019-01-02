package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Controller.Controller;

public class Main extends Application {

    private MainView mainView;
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getClassLoader().getResource("Login.fxml").openStream());
        primaryStage.setTitle("Welcome to Vacation4U");
        Scene scene = new Scene(root, 1024, 600);
        primaryStage.setScene(scene);
        mainView = fxmlLoader.getController();
        mainView.setMainStage(primaryStage);
        mainView.setStage(primaryStage);
        mainView.setMainStage(primaryStage);
        Controller con = new Controller(mainView);
        AView.setController(con);
      //  scene.getStylesheets().add(getClass().getClassLoader().getResource("ViewStyle.css").toExternalForm());
        primaryStage.show();
        mainView.btn_Login.requestFocus();
       // con.Model.CreateDB();
           }


    public static void main(String[] args) {
        launch(args);
    }
}
