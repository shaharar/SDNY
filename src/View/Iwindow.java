package View;

import Controller.IController;
import javafx.stage.Stage;

public interface Iwindow {
    void setStage(Stage stage);
    void setController(IController controller);
}
