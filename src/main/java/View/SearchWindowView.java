package View;

import java.util.ArrayList;

public class SearchWindowView extends AVacationWindow {
    @Override
    public void SendToController() {
        String[][] results=controller.SearchVacation(BuyAll.isSelected(),GetValues());
        if(results==null){
            stage.close();
            controller.openwindow("SearchResult.fxml",null);
        }


    }
}
