package View;

public class CreateVacation extends AVacationWindow {





public void SendToController(){
    if(controller.CreateVacation(GetValues(),BuyAll.isSelected())){
        showAlert("The vacation was created succesfully");
    }



}

}
