package View;

import javafx.scene.control.TextField;

public class VacIDForTradeView extends AView {
    public String vacationWanted;
    public TextField vacationID;

    public void UpdateVacation(){
        if(controller.isYourVacation(vacationID.getText())){
            controller.NewTradeRequest(vacationWanted,vacationID.getText());
        }
        else {
            showAlert("The vacation ID is invalid or not yours");
        }
        stage.close();
    }

    @Override
    public void init(Object Parameter) {
        vacationWanted= (String) Parameter;
        stage.setHeight(175);
        stage.setWidth(370);
    }

}
