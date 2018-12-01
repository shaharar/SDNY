package Controller;

import Model.*;
import View.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller implements IController{

    public IModel Model;
    public IView MyView;

    public Controller(View myView){
        this.Model=new Model1(this);
        this.MyView = myView;
    }

    public boolean Login(String username, String password) {
        return  (Model.Login(username, password));
    }
    public boolean Search(String username) {
        return  (Model.Read(username));
    }
    public String[] getFields(String username){
        return Model.getFields(username);
    }
    public boolean Update(String [] fields) {
        return Model.Update(new ProfileObject(fields));
    }
    public boolean SignUp(String[] fields) {
        ProfileObject po=new ProfileObject(fields);
        if(Model.Create(po)){ //if managed to create the profileObject
            Model.Login(po.Username,po.Password);
            return true;
        }
        return false;
    }

    public void Delete(String registrationDuration, Reason reason) {
        Model.Delete(registrationDuration, reason);
    }
    /*
    get fxml file and opens a new window on top of the main window(which stays untouchable)
     */
    public void openwindow(String fxmlfile,String usernametosearch){
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = null;
        try {
            root = fxmlLoader.load(getClass().getResource("../main/resources/"+fxmlfile).openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage newStage = new Stage();
        Scene scene = new Scene(root, 720, 404);
        newStage.setScene(scene);

        Awindow NewWindow=fxmlLoader.getController();
        NewWindow.setStage(newStage);
        NewWindow.setController(this);
        newStage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
        newStage.show();
        if(fxmlfile.equals("Update.fxml")){
            UpdateWindowView wind=(UpdateWindowView) NewWindow;
            wind.textFieldUpdate();
        }
        else if(fxmlfile.equals("Profile.fxml")) {
           ProfileWindowView wind=(ProfileWindowView) NewWindow;
            wind.textFieldProfile(usernametosearch);
        }
    }


    public void LogOut(){
        Model.Logout();
    }



    public byte[] getphoto( String username){

        return Model.getPhoto(username);
    }


    public void showalert(String alert) {
        MyView.showAlert(alert);
    }
}




