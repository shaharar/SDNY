package View;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class PaymentsView extends AView {

    public TextField Id;
    public TextField FirstName;
    public TextField LastName;
    public TextField CardNumber;
    public TextField ExperationDate;
    public TextField SecurityNum;
    public TextField UserName;
    public PasswordField Password;
    public String[] VacationID;
    public int i;



    public void GetValuesVisa() {
        String[] Visa = new String[7];
        Visa[0] = Id.getText();
        Visa[1] = FirstName.getText();
        Visa[2] = LastName.getText();
        Visa[3] = CardNumber.getText();
        Visa[4] = ExperationDate.getText();
        Visa[5] = SecurityNum.getText();
        Visa[6]=VacationID[i];
        /*if(controller.GetPayVisa(Visa)){
            PushConfirm();
        }*/

    }

    public void GetValuesPaypal() {
        String[] Paypal = new String[2];
        Paypal[0] = UserName.getText();
        Paypal[1] = Password.getText();
       /* if(controller.GetPayPaypal(Paypal,VacationID[i])){
            PushConfirm();
        }*/
    }

    public void SetVacationID(String[] payments) {
        if(payments.length==0){
            stage.close();
            showAlert("No New Payments");
        }
        else{
            VacationID=payments;

        }
    }

    public void PushNext(){
        if(i==VacationID.length){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            showAlert("No More Payments.");
            stage.close();
        }else {
            i++;
            if(i==VacationID.length){
                showAlert("No More Payments.");
            }
        }

    }
    public void PushConfirm(){
        showAlert("Your Details Are Sent Securely");
        stage.close();
    }

    @Override
    public void init(Object Parameter) {
        SetVacationID((String[])Parameter);
    }
}