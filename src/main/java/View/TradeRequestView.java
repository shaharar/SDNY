package View;

import javafx.scene.control.Label;

public class TradeRequestView extends AView {

    public Label VacationData;
    public String [] AllVacationid;
    public String [] VacationDataArr;
    int i;

    public void PushYes(){
        boolean answer=true;
        controller.ConfirmTrade(answer,AllVacationid[i-1]);
        PushNext();
    }


    public void PushNo(){
        boolean answer =false;
        controller.ConfirmTrade(answer,AllVacationid[i-1]);
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
            VacationData.setText(VacationDataArr[i]);
            i++;
            if(i==AllVacationid.length){
                showAlert("No more requests.");
            }
        }

    }

    public void SetVacationID(String[][] vacationinformation){
        if(vacationinformation[0].length==0){
            stage.close();
            showAlert("There aren't new requests");
        }
        else {
            AllVacationid=vacationinformation[0];
            VacationDataArr=vacationinformation[1];
            VacationData.setText(VacationDataArr[i]);
        }
        i++;
    }

    @Override
    public void init(Object Parameter) {
        SetVacationID(((String[][])Parameter));

    }

}
