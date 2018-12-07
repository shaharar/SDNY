package View;

import Controller.IController;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ProfileWindowView extends Awindow{
    public TextField txtfld_username_P;
    public TextField txtfld_birthdate_P;
    public TextField txtfld_city_P;
    public TextField txtfld_lastName_P;
    public TextField txtfld_firstName_P;
    public TextField updatevacation;
    public javafx.scene.image.ImageView Img_profile_P;
    @Override
    public void setStage(Stage stage) {
        this.stage=stage;
    }


    /*
we would like the profile to get the most updated info on the profile from the database
 */

    public void textFieldProfile(String usernametosearch) {
        String[] fields = controller.getFields(usernametosearch);
        txtfld_username_P.setPromptText(fields[0]);
        //txtfld_Password_P.setPromptText(fields[1]);
        txtfld_firstName_P.setPromptText(fields[2]);
        txtfld_lastName_P.setPromptText(fields[3]);
        txtfld_birthdate_P.setPromptText(fields[4]);
        txtfld_city_P.setPromptText(fields[5]);
  /*      try {
           Img_profile_P.setImage( new Image(new FileInputStream(fields[6]))); //FIX LATER
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/

    }

    public void DeleteVacation(){
        controller.openwindow("DeleteVacation.fxml",null);
    }


    public void UpdateVacation(){
        controller.openwindow("UpdateVacation.fxml",updatevacation.getText());
    }
}
