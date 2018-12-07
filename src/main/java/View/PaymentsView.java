package View;

import javafx.scene.control.TextField;

public class PaymentsView extends Awindow {

    public TextField Id;
    public TextField FirstName;
    public TextField LastName;
    public TextField CardNumber;
    public TextField ExperationDate;
    public TextField SecurityNum;
    public TextField UserName;
    public TextField Password;
    public String VacationID;



    public void GetValuesVisa() {
        String[] Visa = new String[7];
        Visa[0] = Id.getText();
        Visa[1] = FirstName.getText();
        Visa[2] = LastName.getText();
        Visa[3] = CardNumber.getText();
        Visa[4] = ExperationDate.getText();
        Visa[5] = SecurityNum.getText();
        Visa[6]=VacationID;
        controller.GetPayVisa(Visa);

    }

    public void GetValuesPaypal() {
        String[] Paypal = new String[2];
        Paypal[0] = UserName.getText();
        Paypal[1] = Password.getText();
        controller.GetPayPaypal(Paypal);


    }

    public void SetVacationID(String[] payments) {
        if(payments.length==0){
            stage.close();
            showAlert("No New Payments");
        }
    }
}