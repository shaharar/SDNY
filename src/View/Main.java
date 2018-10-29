package View;

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
        Parent root = fxmlLoader.load(getClass().getResource("../main/resources/Login.fxml").openStream());
        primaryStage.setTitle("Welcome to Vacation4U");
        Scene scene = new Scene(root, 1024, 600);
        primaryStage.setScene(scene);
        view = fxmlLoader.getController();
        view.setStage(primaryStage);
        view.setMainStage(primaryStage);
        Controller con = new Controller(view);
        view.setController(con);
        scene.getStylesheets().add(getClass().getResource("ViewStyle.css").toExternalForm());
        String[] firstuser=new String[7];
        firstuser[0]="abcdefgh";
        firstuser[1]="12121212";
        firstuser[4]="1999-08-08";
        firstuser[3]="a";
        firstuser[2]="b";
        firstuser[5]="beersheva";
        firstuser[6]=null;
        String[] seconduser=new String[7];
        seconduser[0]="username";
        seconduser[1]="password";
        seconduser[4]="1998-07-07";
        seconduser[3]="a";
        seconduser[2]="b";
        seconduser[5]="beersheva";
        seconduser[6]=null;
        con.Model.CreateDB();
        con.Model.Create(new ProfileObject(firstuser));
        con.Model.Create(new ProfileObject(seconduser));
        //view.setDateDisable();
        primaryStage.show();
        view.btn_Login.requestFocus();
        //view.Update();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
