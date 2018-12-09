package View;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import java.util.ArrayList;

public class ShowResultView extends Awindow {

    public Label VacationId;
    public Label Destination;
    public Label Origin;
    public Label FlightCompany;
    public Label VacationDate;
    public Label BuyAll;
    public Label AdultT;
    public Label ChildT;
    public Label BabyT;
    public Label NumOfSuitcases;
    public Label MaxWeight;
    public Label Price;
    public Button NextRes;
    public String[][] SearchResult;
    int i;


    public void SetResults(String[][] SearchResult) {
        this.SearchResult = SearchResult;
        if (this.SearchResult.length > 0) {
            ShowResult();
            NextRes.setDisable(false);
        }
        else{
            stage.close();
            showAlert("No Results were found");
            NextRes.setDisable(true);
        }


    }

    private void ShowResult() {
        String[] result=SearchResult[i];
        VacationId.setText("Vacation Id: " + result[0]);
        AdultT.setText("Adult Tickets: " + result[1]);
        ChildT.setText("Child Tickets: " + result[2]);
        BabyT.setText("Baby Tickets: " + result[3]);
        BuyAll.setText("Buy All: " + result[4]);
        FlightCompany.setText("Flight Company: " + result[5]);
        Origin.setText("Origin: " + result[6]);
        Destination.setText("Destination: " + result[7]);
        VacationDate.setText("Vacation Date: " + result[8]);
        NumOfSuitcases.setText("Number Of Suitcases: " + result[9]);
        MaxWeight.setText("Max Weight: " + result[10]);
//        Price.setText("Price: " + result[11]);
        i++;
        if(SearchResult.length==i){
            NextRes.setDisable(true);
        }

    }
    public void ChooseVacation() {
        if (controller.ChooseVacation(VacationId.getText().substring(13))) {
            showAlert("Your request was sent. Please wait for confirmation from the seller");
            stage.close();
        }
    }

    public void NextOption() {
        ShowResult();
    }

    @Override
    public void init(Object Parameter) {
        SetResults((String [][])Parameter);
    }
}