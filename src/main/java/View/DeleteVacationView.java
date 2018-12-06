package View;

import javafx.scene.control.TextField;



public class DeleteVacationView extends Awindow{


    public TextField InsertVacID;


    public void deletevacation(){
        controller.DeleteVacation(InsertVacID.getText());

    }





}
