package View;

import javafx.scene.control.Label;

public class ConfirmPaymentsView extends AView {


    public Label VacationID;
    public String [] AllVacationid;
    int i;

    public void PushYes(){
        controller.sellerAcceptedOrDeniedPayment(true,VacationID.getText());
        PushNext();
    }


    public void PushNo(){
        controller.sellerAcceptedOrDeniedPayment(false,VacationID.getText());
        PushNext();
    }

    public void PushNext(){
        if(i == AllVacationid.length){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            showAlert("No more payments to confirm.");
            stage.close();
        }else {
            VacationID.setText("Vacation ID : " + AllVacationid[i]);
            i++;
            if(i == AllVacationid.length){
                showAlert("No more payments to confirm.");
            }
        }

    }

    public void SetVacationID(String[] vacationid){
        if(vacationid.length==0){
            stage.close();
            showAlert("You have no pending payments to confirm");
        }
        else {
            AllVacationid=vacationid;
            VacationID.setText("Vacation ID : "+ vacationid[i]);
        }
        i++;
    }

    @Override
    public void init(Object Parameter) {
        SetVacationID((String[])Parameter);

    }
}
