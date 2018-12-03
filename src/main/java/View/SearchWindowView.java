package View;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import java.util.ArrayDeque;

public class SearchWindowView extends Awindow {
    public ChoiceBox Adults;
    public ChoiceBox Children;
    public ChoiceBox Babies;
    public CheckBox BuyAll;
    public TextField FlightCompany;
    public TextField Destination;
    public DatePicker dtpcker_From;
    public DatePicker dtpcker_To;
    public TextField NumberOfSuitCases;
    public TextField MaxWeight;
    public Button SendSearch;
    private ObservableList<String> adultsList = FXCollections.observableArrayList("1", "2", "3", "4","5","6","7","8","9","10","11","12","13","14","15","16");
    private ObservableList<String> childrenList = FXCollections.observableArrayList("1", "2", "3", "4","5","6","7","8","9","10","11","12","13","14","15","16");
    private ObservableList<String> babiesList =  FXCollections.observableArrayList("1", "2", "3", "4","5","6","7","8","9","10","11","12","13","14","15","16");
    public void SearchVacation() {
        String[] values={(String)Adults.getValue(),(String)Children.getValue(),(String)Babies.getValue(),FlightCompany.getText(),Destination.getText(),dtpcker_From.getValue().toString(),dtpcker_To.getValue().toString(),NumberOfSuitCases.getText(),MaxWeight.getText()};
        if(controller.SearchVacation(BuyAll.isSelected(),values)){
         //stage.close();
        }
    }

    public void SetLists() {
        Adults.setItems(adultsList);
        Children.setItems(childrenList);
        Babies.setItems(babiesList);
    }
}
