package View;

import Model.VacationObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UpdateVacationView extends AVacationWindow{

    @Override
    public void SendToController() {
       if (controller.UpdateVacation(GetValues(),BuyAll.isSelected())){
           showAlert("Your vacation was updated successfully");
       }
    }

    public void SetValues(String [] values,boolean buyAll){
        vacationID = values[0];
        Adults.setValue(values[1]);
        Children.setValue(values[2]);
        Babies.setValue(values[3]);
        BuyAll.setSelected(buyAll);
        FlightCompany.setText(values[5]);
        Origin.setText(values[6]);
        Destination.setText(values[7]);
        String from = values[8].substring(0,values[8].indexOf("_"));
        String to = values[8].substring(values[8].indexOf("_") + 1);
        DateTimeFormatter dtf=DateTimeFormatter.ofPattern("dd-MM-yyyy"); //needed by the date picker
        dtpcker_From.setValue(LocalDate.parse(from, dtf));
        dtpcker_To.setValue(LocalDate.parse(to, dtf));
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
