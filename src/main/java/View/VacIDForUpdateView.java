package View;

import javafx.scene.control.TextField;

public class VacIDForUpdateView extends Awindow {

    public TextField vacationID;

    public void UpdateVacation(){
        if(controller.isYourVacation(vacationID.getText())){
            controller.openwindow("UpdateVacation.fxml",vacationID.getText());
        }
        else {
            showAlert("The vacation ID is invalid");
        }
        stage.close();
    }

    @Override
    public void init(Object Parameter) {
        stage.setHeight(175);
        stage.setWidth(370);
    }
}
