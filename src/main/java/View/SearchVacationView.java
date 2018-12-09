package View;

public class SearchVacationView extends AVacationWindow {
    @Override
    public void SendToController() {
        String[][] results=controller.SearchVacation(BuyAll.isSelected(),GetValues());
        if(results!=null){
            stage.close();
            controller.openwindow("ShowResult.fxml",results);
        }



    }

    @Override
    public void init(Object Parameter) {
        SetLists();
        Adults.setDisable(true);
         Children.setDisable(true);
        Babies.setDisable(true);
        BuyAll.setDisable(true);
        FlightCompany.setDisable(true);
        NumberOfSuitCases.setDisable(true);
         MaxWeight.setDisable(true);
         Price.setDisable(true);
    }
}
