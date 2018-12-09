package View;

import javafx.scene.control.TextField;



public class DeleteVacationView extends Awindow{


    public TextField InsertVacID;


    public void deletevacation(){
        if(controller.DeleteVacation(InsertVacID.getText())){
            stage.close();
            showAlert("Delete Successful");
        }
    }





}
