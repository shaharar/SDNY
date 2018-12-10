package View;

import javafx.scene.control.TextField;

public class VacIDForUpdateView extends Awindow {

    public TextField vacationID;

    public void UpdateVacation(){
        if(!vacationID.getText().equals("")){
            controller.openwindow("UpdateVacation.fxml",vacationID.getText());
        }
        else {
            showAlert("Please enter Vacation ID for update" );
        }
        stage.close();
    }

    @Override
    public void init(Object Parameter) {
        stage.setHeight(175);
        stage.setWidth(370);
    }
}
