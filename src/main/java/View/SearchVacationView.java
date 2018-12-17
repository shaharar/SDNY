package View;

public class SearchVacationView extends AVacationView {
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
        stage.setHeight(610);
        stage.setWidth(980);
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
