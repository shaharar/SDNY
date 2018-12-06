package View;

public class SearchWindowView extends AVacationWindow {
    @Override
    public void SendToController() {
        String[][] results=controller.SearchVacation(BuyAll.isSelected(),GetValues());
        if(results!=null){
            stage.close();
            controller.openwindow("ShowResult.fxml",results);
        }



    }
}
