package View;

import Controller.IController;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.zip.GZIPInputStream;

public class UpdateWindowView implements Iwindow {
    public TextField txtfld_username_U;
    public PasswordField pswfld_password_U;
    public DatePicker DP_birthdate_U;
    public TextField txtfld_city_U;
    public TextField txtfld_firstName_U;
    public TextField txtfld_lastName_U;
    public Button btn_saveChanges_U;
    private IController controller;
    private Stage stage;


    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public void uploadImage() {
        FileChooser fc = new FileChooser();
       File fileselected = fc.showOpenDialog(stage);
        try {
            Image img = new Image(new FileInputStream(fileselected.getPath()));
           // Img_profile_L.setImage(img); //change!!!
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }
    @Override
    public void setController(IController controller) {
        this.controller=controller;
    }

    public void textFieldUpdate() {
        DateTimeFormatter dtf=DateTimeFormatter.ofPattern("yyyy-MM-dd"); //needed by the date picker
        String[] fields = controller.getFields(null);
        txtfld_username_U.setText(fields[0]);
        pswfld_password_U.setText(fields[1]);
        txtfld_firstName_U.setText(fields[2]);
        txtfld_lastName_U.setText(fields[3]);
        DP_birthdate_U.setValue(LocalDate.parse(fields[4],dtf));
        txtfld_city_U.setText(fields[5]);
        btn_saveChanges_U.requestFocus();

    }
/*
after filling fields in te update window the info is sent to be checked and updated
 */

    public void saveChanges() {
        String [] fields= { txtfld_username_U.getText(), pswfld_password_U.getText(), txtfld_firstName_U.getText(), txtfld_lastName_U.getText(), DP_birthdate_U.getValue().toString(),txtfld_city_U.getText(),null};
        if(controller.Update(fields))
            stage.close(); //auto closin window if the data is not problematic
    }
}