package Controller;

import Model.*;
import View.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

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
            root = fxmlLoader.load(getClass().getClassLoader().getResource(fxmlfile).openStream());
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
        if(fxmlfile.equals("UpdateProfile.fxml")){
            UpdateWindowView wind=(UpdateWindowView) NewWindow;
            wind.textFieldUpdate();
        }
        else if(fxmlfile.equals("Profile.fxml")) {
           ProfileWindowView wind=(ProfileWindowView) NewWindow;
            wind.textFieldProfile(usernametosearch);
        }
        else if(fxmlfile.equals("VacationSearch.fxml")||fxmlfile.equals("CreateVacation.fxml")){
            View.AVacationWindow window=(View.AVacationWindow) NewWindow;
            window.SetLists();
        }
    }


    public void LogOut(){
        Model.Logout();
    }



    public byte[] getphoto( String username){

        return Model.getPhoto(username);
    }

    @Override
    public String[][] SearchVacation(boolean buyAll, String[] TextFields) {
        try{
            int numOfSuitcases= Integer.parseInt(TextFields[7]);
            int maxWeight= Integer.parseInt(TextFields[8]);
            ArrayList<VacationObject > vacationObjects= Model.GetSearchResult(new VacationObject(null,null,null,false,"adu-"+TextFields[0]+"chi-"+TextFields[1]+"bab-"+TextFields[2],buyAll,TextFields[3],TextFields[4],TextFields[5]+TextFields[6],numOfSuitcases,maxWeight));
            String[][] allResults=new String[vacationObjects.size()][];
            if( vacationObjects!=null){
                for (int i = 0; i <vacationObjects.size() ; i++) {
                    String[] values=new String[10];
                    values[0]=vacationObjects.get(i).VacationID;
                    String[] adultTicketsArr=vacationObjects.get(i).VacationID.split("adu");
                    String[] childrenArr=adultTicketsArr[1].split("chi");
                    String[] babyArr=childrenArr[1].split("bab");
                    values[1]=childrenArr[1] ;
                    values[2]=babyArr[0] ;
                    values[3]= babyArr[1];
                    values[4]=""+vacationObjects.get(i).BuyAll;
                    values[5]= vacationObjects.get(i).FlightCompany;
                    values[6]= vacationObjects.get(i).Destination;
                    values[7]= vacationObjects.get(i).VacationDate;
                    values[8]= ""+vacationObjects.get(i).NumberOfSuitcases;
                    values[9]=""+vacationObjects.get(i).MaxWeight ;
                    allResults[i]=values;

                }
                return allResults;
            }
            return null;
        }catch (Exception e){
            showalert("Try entering again");
            return null;
        }

    }


    public void showalert(String alert) {
        MyView.showAlert(alert);
    }

    @Override
    public ArrayList<String> GetNewRequests() {
        return Model.GetNewRequests();
    }

    @Override
    public boolean CreateVacation(String[] strings, boolean selected) {
   return Model.InsertVacation(ConvertToVacation(strings,selected));
    }

    @Override
    public void DeleteVacation(String vacationId) {
        Model.DeleteVacation(vacationId);
    }

    public VacationObject ConvertToVacation(String [] GuiValues,boolean buyAll){
        int numOfSuitcases= Integer.parseInt(GuiValues[7]);
        int maxWeight= Integer.parseInt(GuiValues[8]);
        return new VacationObject(null,null,null,false,"adu-"+GuiValues[0]+"chi-"+GuiValues[1]+"bab-"+GuiValues[2],buyAll,GuiValues[3],GuiValues[4],GuiValues[5]+GuiValues[6],numOfSuitcases,maxWeight);




    }
}




