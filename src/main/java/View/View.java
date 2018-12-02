package View;

import Model.Reason;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

public class View extends Awindow implements IView {

    @FXML
    public TextField txtfld_username_W;
    public TextField txtfld_username_login_L;
    public TextField txtfld_username_reg_L;
    public PasswordField pswfld_password_login_L;
    public PasswordField pswfld_password_reg_L;
    public DatePicker DP_birthdate_L;
    public TextField txtfld_city_L;
    public TextField txtfld_firstName_L;
    public TextField txtfld_lastName_L;
    public TextField txtfld_regDuration;
    public Button btn_Login;
    public AnchorPane Anchorpane;
    public Button btn_search_W;
    public javafx.scene.image.ImageView Img_profile_L;
    public ChoiceBox chobx_reason;
    public Button btnSearch_Vacation_L;
    public Button btnSearch_Vacation_W;
    public File fileselected = null;
    private ObservableList<String> quitReasons = FXCollections.observableArrayList("I found what i was looking for", "Disappointed from the service", "I found a better service", "other");
/*
A stage for secondary windows
 */
    public void setStage(Stage stage) {
        this.stage = stage;
    }


    /*
    1. controller gets data from fields and send it to the model
    2. website is opened
     */
    public void signUp() {
        String[] fieldsArr = new String[]{txtfld_username_reg_L.getText(), pswfld_password_reg_L.getText(), txtfld_firstName_L.getText(), txtfld_lastName_L.getText(),DP_birthdate_L.getValue()==null?null: DP_birthdate_L.getValue().toString(), txtfld_city_L.getText(), fileselected == null ? null : fileselected.getPath()};
        if (controller.SignUp(fieldsArr)) {
           ChangeScene("website.fxml");
        }
    }

    public void viewMyProfile() {
        controller.openwindow("Profile.fxml", null);
    }

    public void showProfile(String username) {
        controller.openwindow("Profile.fxml", username);
    }

    public void SearchProfile() {
        if (controller.Search(txtfld_username_W.getText())) { //if found in db
            showProfile(txtfld_username_W.getText());
        } else {
            showAlert("Username doesn't exist. Please try Again");
        }
    }

    /*
    if the username matches the passeord the website will open
    else an alert pops up
     */
    public void Login() {
        if (controller.Login(txtfld_username_login_L.getText(), pswfld_password_login_L.getText())) {
            ChangeScene("website.fxml");
        } else {
            showAlert("Incorrect Username/Password. Please try Again");
        }
       /* showAlert("no new messages");
        ArrayList<String> requests=controller.GetNewRequests();
        for (int i = 0; i <requests.size() ; i++) {
            showAlert(requests.get(i));
        }*/
    }

    public void Update() {
        controller.openwindow("Update.fxml", null);
    }


    /*
     the window changed to the delete user screen
     */
    public void Delete() {
        ChangeScene("Delete.fxml");
    }

    /*
     * DeleteProfile the profile of the user and check if he answered the questions
     */
    public void Quit() {
        Reason reason = Reason.NO_ANSWER;
        if(chobx_reason.getValue() == null) //the user didn't answer the second question
            chobx_reason.setValue(Reason.NO_ANSWER);
        switch (chobx_reason.getValue().toString()) {
            case "I found what i was looking for":
                reason = Reason.I_FOUND_WHAT_I_WAS_LOOKING_FOR;
                break;
            case "I found a better service":
                reason = Reason.I_FOUND_A_BETTER_SERVICE;
                break;
            case "Disappointed from the service":
                reason = Reason.DISAPPOINTED_FROM_THE_SERVICE;
                break;
            case "Other":
                reason = Reason.OTHER;
                break;
            case "":
                reason = Reason.NO_ANSWER;
                break;
        }
        if (!reason.equals(Reason.NO_ANSWER)) {
            //the user answered only one question
            if(txtfld_regDuration.getText().equals("")){
                showAlert("Please answer the first question, or none of them.");
            }
            //the user answered both of the questions
            else {
                controller.Delete(txtfld_regDuration.getText(), reason);
                stage.close();
                Logout(); //disconnect and go back to Login window
            }
        }
        else {
            //the user answered only one question
            if(!(txtfld_regDuration.getText().equals(""))){
                showAlert("Please answer the second question, or none of them.");
            }
            //the user didn't answer the questions
            else {
                controller.Delete("No Answer", Reason.NO_ANSWER);
                stage.close();
                Logout(); //disconnect and go back to Login window
            }
        }

    }

    /*
     * Set the options for quit reasons in the choice box
     */
    public void setChoiceBoxItems() {
        chobx_reason.setItems(quitReasons);
    }
/*
when opening update window the fields should have their current state for the user to see
 */


    public void goToWebsite() {
       ChangeScene("website.fxml");
    }
/*
this function is unnecessery because it is defaulted on true but we might need this in the future
 */

    public void uploadImage() {
        FileChooser fc = new FileChooser();
        fileselected = fc.showOpenDialog(stage);
        try {
            Image img = new Image(new FileInputStream(fileselected.getPath()));
            Img_profile_L.setImage(img);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    public void Logout() {
        controller.LogOut();
       ChangeScene("Login.fxml");
    }

    public void lookForNewRequests(){
        controller.openwindow("Requests.fxml",null);
    }
    public void attention(){
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = null;
        try {
            root = fxmlLoader.load(getClass().getResource("../main/resources/Attention.fxml").openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage newStage = new Stage();
        Scene scene = new Scene(root, 290, 300);
        newStage.setScene(scene);

        View NewView = fxmlLoader.getController();
        NewView.setStage(newStage);
        newStage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
        newStage.show();
    }
        public void OpenSearchScreen(){
        controller.openwindow("VacationSearch.fxml",null);

        }
}
