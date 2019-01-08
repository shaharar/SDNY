package View;

import javafx.scene.control.Label;

public class TradeRequestView extends AView {

    public Label VacationData;
    public String [] AllVacationidOffered;
    public String [] OfferedVacationDataArr;
    public String []  WantedVacationID;
    int i;
    public Label offerText;

    public void PushYes(){
        boolean answer=true;
        controller.ConfirmTrade(answer, AllVacationidOffered[i-1],WantedVacationID[i-1]);
        PushNext();
    }


    public void PushNo(){
        boolean answer =false;
        controller.ConfirmTrade(answer, AllVacationidOffered[i-1], WantedVacationID[i - 1]);
        PushNext();
    }

    public void PushNext(){
        if(i== AllVacationidOffered.length){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            showAlert("No more requests.");
            stage.close();
        }else {
            offerText.setText(offerText.getText().replace(WantedVacationID[i-1],WantedVacationID[i]));
            VacationData.setText(OfferedVacationDataArr[i]);
            i++;

        }

    }

    public void SetVacationID(String[][] vacationinformation){
        if(vacationinformation[0].length==0){
            stage.close();
            showAlert("There aren't new requests");
        }
        else {
            AllVacationidOffered =vacationinformation[0];
            OfferedVacationDataArr =vacationinformation[1];
            WantedVacationID=vacationinformation[2];
            VacationData.setText(OfferedVacationDataArr[i]);
            offerText.setText(offerText.getText().replace("#",WantedVacationID[i]));
        }
        i++;
    }

    @Override
    public void init(Object Parameter) {
        SetVacationID(((String[][])Parameter));
    }

}
