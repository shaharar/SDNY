package View;

public class CreateVacationView extends SearchWindowView{





public void SendToController(){
    if(controller.CreateVacation(GetValues(),BuyAll.isSelected())){
        showAlert("The vacation was created succesfully");
    }



}

}
