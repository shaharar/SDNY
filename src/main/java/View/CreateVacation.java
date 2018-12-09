package View;

public class CreateVacation extends AVacationWindow {





public void SendToController(){
    if(controller.CreateVacation(GetValues(),BuyAll.isSelected())){
        showAlert("The vacation was created succesfully");
    }



}

    @Override
    public void init(Object Parameter) {
        SetLists();
    }
}
