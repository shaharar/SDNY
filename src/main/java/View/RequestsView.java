package View;

import javafx.scene.control.Label;

public class RequestsView extends Awindow{


    public Label VacationID;
    public String [] AllVacationid;
    int i;

    public void PushYes(){
        boolean answer=true;
        controller.SellerAnswer(answer,VacationID.getText());
    }


    public void PushNo(){
        boolean answer =false;
        controller.SellerAnswer(answer,VacationID.getText());

    }

    public void PushNext(){
        VacationID.setText(AllVacationid[i]);
        i++;


    }

    public void SetVacationID(String[] vacationid){
        if(vacationid.length==0){
            stage.close();
            showAlert("No New Requests");
        }
        else {
            VacationID.setText("VacationId :"+ vacationid[i]);
        }
        i++;

    }
}
