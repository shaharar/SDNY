package View;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ShowResultView extends AView {

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
    public Button PrevRes;
    public String[][] SearchResult;
    int i;


    public void SetResults(String[][] SearchResult) {
        this.SearchResult = SearchResult;
        if (this.SearchResult.length > 0) {
            NextRes.setDisable(false);
            ShowResult();
            PrevRes.setDisable(true);


        }
        else{
            stage.close();
            showAlert("No results were found");
        }


    }

    private void ShowResult() {
        String[] result=SearchResult[i];
        VacationId.setText("Vacation ID : " + result[0]);
        AdultT.setText("Adult Tickets : " + result[1]);
        ChildT.setText("Child Tickets : " + result[2]);
        BabyT.setText("Baby Tickets : " + result[3]);
        BuyAll.setText("Enable Partial Purchase Of Tickets : " + result[4]);
        FlightCompany.setText("Flight Company : " + result[5]);
        Origin.setText("Origin : " + result[6]);
        Destination.setText("Destination : " + result[7]);
        VacationDate.setText("Vacation Date : " + result[8]);
        NumOfSuitcases.setText("Number Of Suitcases Per Person : " + result[9]);
        MaxWeight.setText("Max Suitcase Weight : " + result[10]);
        Price.setText("Price($) : " + result[11]);
        i++;
        if(SearchResult.length <= i){
            NextRes.setDisable(true);
        }
        else{
            NextRes.setDisable(false);
        }
        if(i > 1){
            PrevRes.setDisable(false);
        }
        else{
            PrevRes.setDisable(true);
        }

    }
    public void ChooseVacation() {
        if (controller.ChooseVacation(VacationId.getText().substring(14))) {
            showAlert("Your request was sent. Please wait for confirmation from the seller");
            stage.close();
        }
    }

    public void NextOption() {
        ShowResult();
    }
    public void prevOption() {
        i=i-2;
        ShowResult();
    }
    public void TradeThisVacation(){
        controller.openwindow("VacationIDForTrade.fxml",VacationId.getText().substring(14));
    }

    @Override
    public void init(Object Parameter) {
        SetResults((String [][])Parameter);
    }
}