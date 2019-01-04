package View;

import javafx.scene.control.TextField;



public class DeleteVacationView extends AView {


    public TextField InsertVacID;


    public void deletevacation(){
        if(controller.DeleteVacation(InsertVacID.getText())){
            stage.close();
            showAlert("Delete vacation Successful");
        }
    }





}
