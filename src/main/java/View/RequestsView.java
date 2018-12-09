package View;

import javafx.scene.control.Label;

public class RequestsView extends Awindow{


    public Label VacationID;
    public String [] AllVacationid;
    int i;

    public void PushYes(){
        boolean answer=true;
        controller.SellerAnswer(answer,VacationID.getText().substring(12));
        PushNext();
    }


    public void PushNo(){
        boolean answer =false;
        controller.SellerAnswer(answer,VacationID.getText().substring(12));
        PushNext();
    }

    public void PushNext(){
        if(i==AllVacationid.length){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            showAlert("No more requests.");
            stage.close();
        }else {
            VacationID.setText("VacationId :"+AllVacationid[i]);
            i++;
            if(i==AllVacationid.length){
                showAlert("No more requests.");
            }
        }

    }

    public void SetVacationID(String[] vacationid){
        if(vacationid.length==0){
            stage.close();
            showAlert("No New Requests");
        }
        else {
            AllVacationid=vacationid;
            VacationID.setText("VacationId :"+ vacationid[i]);
        }
        i++;
    }

    @Override
    public void init(Object Parameter) {
        SetVacationID((String[])Parameter);

    }
}
