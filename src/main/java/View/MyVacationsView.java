package View;

import javafx.collections.FXCollections;
import javafx.scene.control.ListView;

import java.util.ArrayList;

public class MyVacationsView extends AView {

    public ListView<String> vacationsList;


    public void viewMyVacations () {
        ArrayList<String> myVacations = controller.getUsersVacations();
        vacationsList.setItems(FXCollections.observableArrayList(myVacations));
        vacationsList.setStyle("-fx-font-style: Calibri;-fx-font-weight:bold;");
    }

    @Override
    public void init(Object Parameter) {
        stage.setHeight(481);
        stage.setWidth(696);
        viewMyVacations();
    }
}
