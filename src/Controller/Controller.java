package Controller;

import Model.*;
import View.IView;
import View.View;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller implements IController{

    public IModel Model;
    public IView View;

    public Controller(View view){
        this.Model=new Model1(this);
        this.View= view;
    }
    @Override
    public boolean Login(String username, String password) {
        return  (Model.Login(username, password));
    }
    public boolean Search(String username) {
        return  (Model.Read(username));
    }
    public String[] getFields(String username){
        return Model.getFields(username);
    }
    @Override
    public boolean Update(String [] fields) {
        return Model.Update(new ProfileObject(fields));
    }

    @Override
    public boolean SignUp(String[] fields) {
        ProfileObject po=new ProfileObject(fields);
        if(Model.Create(po)){
            Model.Login(po.Username,po.Password);
            return true;
        }
        return false;
    }

    @Override
    public void Delete(String registrationDuration, Reason reason) {
        Model.Delete(registrationDuration, reason);
    }


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

        View NewView=fxmlLoader.getController();
        NewView.setStage(newStage);
        newStage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
        newStage.show();
        switch (fxmlfile){

            case "Update.fxml":

                NewView.textfieldupdate();

                break;

            case "Profile.fxml":
                NewView.textfielprofile(usernametosearch);
                break;
        }
    }


    public void LogOut(){
        Model.Logout();
    }



    public byte[] getphoto( String username){

        return Model.getPhoto(username);
    }

    @Override
    public void showalert(String alert) {
        View.showAlert(alert);
    }
}




