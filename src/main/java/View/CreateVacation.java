package View;

public class CreateVacation extends AVacationWindow {

public void SendToController(){
    if(controller.CreateVacation(GetValues(),BuyAll.isSelected())){
        showAlert("The vacation was created successfully.\n" + "Your vacation ID is: " + (GetValues())[0]);
        stage.close();
    }
}

    @Override
    public void init(Object Parameter) {
        stage.setHeight(610);
        stage.setWidth(980);
        SetLists();
    }
}
