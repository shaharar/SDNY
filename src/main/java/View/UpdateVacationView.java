package View;

import Model.VacationObject;

public class UpdateVacationView extends AVacationWindow{

    @Override
    public void SendToController() {
       controller.UpdateVacation(GetValues(),BuyAll.isSelected());
    }

    public void SetValues(String [] values,boolean buyAll){
        Adults.setValue(values[1]);
        Children.setValue(values[2]);
        Babies.setValue(values[3]);
        BuyAll.setSelected(buyAll);
        FlightCompany.setText(values[5]);
        Origin.setText(values[6]);
        Destination.setText(values[7]);
        String s1 = values[8].substring(0,values[8].indexOf("|"));
        String s2 = values[8].substring(values[8].indexOf("|") + 1);
        dtpcker_From.setPromptText(s1);
        dtpcker_To.setPromptText(s2);
        NumberOfSuitCases.setText(values[9]);
        MaxWeight.setText(values[10]);
        Price.setText(values[11]);
    }


    @Override
    public void init(Object Parameter) {
        //it was in the controller first
        stage.setHeight(610);
        stage.setWidth(980);
        SetLists();
        VacationObject currvac=controller.getVacationFields(Parameter);
        SetValues(controller.VacToStringArr(currvac),currvac.BuyAll);
    }
}
