package View;

import Model.Reason;
import Model.UserFields;
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
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import Controller.IController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class View implements IView {

    @FXML
    public static IController controller;
    public TextField txtfld_username_W;
    public TextField txtfld_username_login_L;
    public TextField txtfld_username_reg_L;
    public TextField txtfld_username_U;
    public TextField txtfld_username_P;
    public PasswordField pswfld_password_U;
    public PasswordField pswfld_password_login_L;
    public PasswordField pswfld_password_reg_L;
    public DatePicker DP_birthdate_L;
    public DatePicker DP_birthdate_U;
    public TextField txtfld_birthdate_P;
    public TextField txtfld_city_U;
    public TextField txtfld_city_L;
    public TextField txtfld_city_P;
    public TextField txtfld_firstName_U;
    public TextField txtfld_firstName_L;
    public TextField txtfld_firstName_P;
    public TextField txtfld_lastName_U;
    public TextField txtfld_lastName_L;
    public TextField txtfld_lastName_P;
    public TextField txtfld_regDuration;
    public Button btn_Login;
    public Button btn_search_W;
    public Button btn_saveChanges_U;
    public javafx.scene.image.ImageView Img_profile_L;
    public javafx.scene.image.ImageView Img_profile_P;
    public ChoiceBox chobx_reason;
    public static Stage stage;
    public File fileselected = null;
    private ObservableList<String> quitReasons = FXCollections.observableArrayList("I found what i was looking for", "Disappointed from the service", "I found a better service", "other");
    private static Stage Mainstage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /*
    experimental function (may help debug problems with 2 windows.
     */
    public void setMainStage(Stage stage) {
        this.Mainstage = stage;
    }

    public void setController(IController controller) {
        this.controller = controller;
    }


    /*
    1. controller gets data from fields and send it to the model
    2. website is opened
     */
    public void signUp() {
        String[] fieldsArr = new String[]{txtfld_username_reg_L.getText(), pswfld_password_reg_L.getText(), txtfld_firstName_L.getText(), txtfld_lastName_L.getText(), DP_birthdate_L.getValue().toString(), txtfld_city_L.getText(), fileselected == null ? null : fileselected.getPath()};
        if (controller.SignUp(fieldsArr)) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                Parent root = fxmlLoader.load(getClass().getResource("../main/resources/website.fxml").openStream());
                Scene scene = new Scene(root, 1024, 600);
                Mainstage.setScene(scene);
                scene.getStylesheets().add(getClass().getResource("../main/resources/Background.css").toExternalForm());
                Mainstage.show();
//                btn_search_W.requestFocus();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    public void viewMyProfile() {
        controller.openwindow("Profile.fxml", null);
    }


    public void showProfile(String username) {
        controller.openwindow("Profile.fxml", username);
    }



    public void Search() {
        if (controller.Search(txtfld_username_W.getText())) {
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
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                Parent root = fxmlLoader.load(getClass().getResource("../main/resources/website.fxml").openStream());
                Scene scene = new Scene(root, 1024, 600);
                stage.setScene(scene);
                //  stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
                scene.getStylesheets().add(getClass().getResource("../main/resources/Background.css").toExternalForm());
                stage.show();
//                btn_search_W.requestFocus();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            showAlert("Incorrect Username/Password. Please try Again");
        }
    }


    public void Update() {
        controller.openwindow("Update.fxml", null);
    }

    public void saveChanges() {
        String [] fields= { txtfld_username_U.getText(), pswfld_password_U.getText(), txtfld_firstName_U.getText(), txtfld_lastName_U.getText(), DP_birthdate_U.getValue().toString(),txtfld_city_U.getText(),null};
        if(controller.Update(fields))
            this.stage.close();
    }
    /*
     the window changed to the delete user screen
     */

    public void Delete() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("../main/resources/Delete.fxml").openStream());
            Scene scene = new Scene(root, 1024, 600);
            Mainstage.setScene(scene);
            Mainstage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete the profile of the user and check if he answered the questions
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

    /**
     * Set the options for quit reasons in the choice box
     */
    public void setChoiceBoxItems() {
        chobx_reason.setItems(quitReasons);
    }

    public void showAlert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(alertMessage);
        alert.show();

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


    public void goToWebsite() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("../main/resources/website.fxml").openStream());
            Scene scene = new Scene(root, 1024, 600);
            Mainstage.setScene(scene);
            scene.getStylesheets().add(getClass().getResource("../main/resources/Background.css").toExternalForm());
            Mainstage.show();
            btn_search_W.requestFocus();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setDateDisable() {
        DP_birthdate_L.getEditor().setDisable(true);
        DP_birthdate_U.getEditor().setDisable(true);
    }

    public void uploadImage() {
        FileChooser fc = new FileChooser();
        fileselected = fc.showOpenDialog(stage);
        try {
            Image img = new Image(new FileInputStream(fileselected.getPath()));
            Img_profile_L.setImage(img);
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
        }
    }


    public void textFielProfile(String usernametosearch) {
        String[] fields = controller.getFields(usernametosearch);
        txtfld_username_P.setPromptText(fields[0]);
        //txtfld_Password_P.setPromptText(fields[1]);
        txtfld_firstName_P.setPromptText(fields[2]);
        txtfld_lastName_P.setPromptText(fields[3]);
        txtfld_birthdate_P.setPromptText(fields[4]);
        txtfld_city_P.setPromptText(fields[5]);
        ViewPhoto();
    }

    public void Logout() {
        controller.LogOut();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("../main/resources/Login.fxml").openStream());
            Scene scene = new Scene(root, 1024, 600);
            Mainstage.setScene(scene);
            scene.getStylesheets().add(getClass().getResource("../main/resources/ViewStyle.css").toExternalForm());
            Mainstage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ViewPhoto(){
        byte[]baytearr = controller.getphoto(null);
        if(baytearr!=null){
            ByteArrayInputStream in= new ByteArrayInputStream(baytearr);
            Img_profile_P.setImage(new Image(in));
        }
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
}
