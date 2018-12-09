package View;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import java.util.ArrayDeque;
import java.util.ArrayList;

public abstract class AVacationWindow extends Awindow {
    public ChoiceBox Adults;
    public ChoiceBox Children;
    public ChoiceBox Babies;
    public CheckBox BuyAll;
    public TextField FlightCompany;
    public TextField Origin;
    public TextField Destination;
    public DatePicker dtpcker_From;
    public DatePicker dtpcker_To;
    public TextField NumberOfSuitCases;
    public TextField MaxWeight;
    public TextField Price;
    public Button SendSearch;
    private ObservableList<String> adultsList = FXCollections.observableArrayList("0","1", "2", "3", "4","5","6","7","8","9","10","11","12","13","14","15","16");
    private ObservableList<String> childrenList = FXCollections.observableArrayList("0","1", "2", "3", "4","5","6","7","8","9","10","11","12","13","14","15","16");
    private ObservableList<String> babiesList =  FXCollections.observableArrayList("0","1", "2", "3", "4","5","6","7","8","9","10","11","12","13","14","15","16");


    public void SendToController() {

    }
    public String[] GetValues (){
        String[] values=null;
        if(dtpcker_From.getValue()==null ||dtpcker_To.getValue()==null){
            showAlert("insert dates!");
        }
        else if(Price == null){
            values = new String[]{(String) Adults.getValue(), (String) Children.getValue(), (String) Babies.getValue(), FlightCompany.getText(), Origin.getText(), Destination.getText(), dtpcker_From.getValue().toString(), dtpcker_To.getValue().toString(), NumberOfSuitCases.getText(), MaxWeight.getText()};
        }
        else{
            values = new String[]{(String) Adults.getValue(), (String) Children.getValue(), (String) Babies.getValue(), FlightCompany.getText(), Origin.getText(), Destination.getText(), dtpcker_From.getValue().toString(), dtpcker_To.getValue().toString(), NumberOfSuitCases.getText(), MaxWeight.getText(), Price.getText()};
        }

    return values;
    }
    public void SetLists() {
        Adults.setItems(adultsList);
        Children.setItems(childrenList);
        Babies.setItems(babiesList);
    }
}
