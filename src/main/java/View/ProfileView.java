package View;

import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ProfileView extends AView {
    public TextField txtfld_username_P;
    public TextField txtfld_birthdate_P;
    public TextField txtfld_city_P;
    public TextField txtfld_lastName_P;
    public TextField txtfld_firstName_P;
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
        try {if(fields[6]!=null &&new File(fields[6]).exists()){
            Img_profile_P.setImage( new Image(new FileInputStream(fields[6])));
        }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void init(Object Parameter) {
        textFieldProfile((String)Parameter);
    }
}
