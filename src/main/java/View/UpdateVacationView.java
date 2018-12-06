package View;

public class UpdateVacationView extends AVacationWindow{

    @Override
    public void SendToController() {
       controller.UpdateVacation(GetValues(),BuyAll.isSelected());
    }

    public void SetValues(String [] values,boolean buyAll){
        Adults.setValue(values[0]);
        Children.setValue(values[1]);
        Babies.setValue(values[2]);
        BuyAll.setSelected(buyAll);
        FlightCompany.setText(values[3]);
        Destination.setText(values[4]);
        dtpcker_From.setPromptText(values[5]);
        dtpcker_To.setPromptText(values[6]);
        NumberOfSuitCases.setText(values[7]);
        MaxWeight.setText(values[8]);



    }
}
