package View;

import Controller.IController;
import javafx.stage.Stage;

public abstract class Awindow {

 static IController controller;
 Stage stage;

    void setStage(Stage stage){
        this.stage=stage;

    };

   public static void setController(IController controller){
        Awindow.controller=controller;

    };

}





