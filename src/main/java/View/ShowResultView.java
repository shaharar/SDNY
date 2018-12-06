package View;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import java.util.ArrayList;

public class ShowResultView extends Awindow {

    public Label VacationId;
    public Label Destination;
    public Label FlightCompany;
    public Label VacationDate;
    public Label BuyAll;
    public Label AdultT;
    public Label ChildT;
    public Label BabyT;
    public Label NumOfSuitcases;
    public Label MaxWeight;
    public Button NextRes;
    public String[][] SearchResult;


    public void SetResults(String[][] SearchResult) {
        this.SearchResult = SearchResult;
        if (this.SearchResult.length > 0) {
            ShowResult(SearchResult[0]);
        }
        NextRes.setDisable(false);


    }

    private void ShowResult(String[] result) {

        VacationId.setText("Vacation Id: " + result[0]);
        AdultT.setText("Adult Tickets: " + result[1]);
        ChildT.setText("Child Tickets: " + result[2]);
        BabyT.setText("Baby Tickets: " + result[3]);
        BuyAll.setText("Buy All: " + result[4]);
        FlightCompany.setText("Flight Company: " + result[5]);
        Destination.setText("Destination: " + result[6]);
        VacationDate.setText("Vacation Date: " + result[7]);
        NumOfSuitcases.setText("Number Of Suitcases: " + result[8]);
        MaxWeight.setText("Max Weight: " + result[9]);


    }
    public void ChooseVacation() {
        if (controller.ChooseVacation(VacationId.getText().substring(13))) {
            showAlert("Your request sand.please wait for  the seller to confirm");
            stage.close();
        }
    }
}