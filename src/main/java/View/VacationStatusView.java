package View;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableView;
public class VacationStatusView extends Awindow{

    public String [] vacationID;
    public String [] RequestStatus;
    public String [] Paymentstatus;
    public Label id1;
    public Label status1;
    public Label id2;
    public Label status2;
    public Label id3;
    public Label status3;
    public Label id4;
    public Label status4;
    public Label id5;
    public Label status5;
    public Label id6;
    public Label status6;
    public Label id7;
    public Label status7;
    public Button B1;
    public Button B2;
    public Button B3;
    public Button B4;
    public Button B5;
    public Button B6;
    public Button B7;



    public void Updatetableview(String [][] values ){
       this.vacationID=values[0];
       this.RequestStatus=values[1];
       this.Paymentstatus =values[2];
        for (int i = 0; i <vacationID.length ; i++) {
            try {
                Label currid=(Label)getClass().getDeclaredField("id"+(i+1)).get(this);
                Label currstatus=(Label)getClass().getDeclaredField("status"+(i+1)).get(this);
                Button currbtn=(Button) getClass().getDeclaredField("B"+(i+1)).get(this);
                currid.setText(vacationID[i]);
                currstatus.setText(RequestStatus[i]);
                if(Paymentstatus[i].equals("Yes")){
                    currbtn.setVisible(true);
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }



    }

    public void ClickPayments(ActionEvent actionEvent) {
        ((Button)actionEvent.getSource()).setDisable(true);
            controller.openwindow("Payments.fxml",controller.GetnewPayments());



    }
}
