package View;

import Model.VacationObject;

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
        Origin.setText(values[4]);
        Destination.setText(values[5]);
        dtpcker_From.setPromptText(values[6]);
        dtpcker_To.setPromptText(values[7]);
        NumberOfSuitCases.setText(values[8]);
        MaxWeight.setText(values[9]);
        Price.setText(values[10]);



    }

    @Override
    public void init(Object Parameter) {
        //it was in the controller first
        SetLists();
        VacationObject currvac=controller.getVacationFields(Parameter);
        SetValues(controller.VacToStringArr(currvac),currvac.BuyAll);
    }
}
