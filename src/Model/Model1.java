package Model;

import DBManager.IDBManager;
import DBManager.DBManager;
import Controller.IController;

import java.util.ArrayList;


public class Model1 implements IModel {

    IController controller;
    IDBManager DBM;
    String currentUser;

    public Model1(IController controller) {
        this.DBM = new DBManager();
        this.controller = controller;
    }

    public boolean Create (ProfileObject profileObject) {
        if(isDataCorrect(profileObject)){
            DBM.InsertProfile(profileObject);
            return true;
        }
        return false;
    }
    //we will check the special constraints accordingly
    private boolean isDataCorrect(ProfileObject profileObject) {
        //username
        if(profileObject.Username.length() > 8 || profileObject.Username.equals("")|| !(profileObject.Username.matches("[a-zA-Z0-9]*")) ){
            controller.showalert("Your username is illegal");
            return false;
        }else
        if(DBM.ReadProfile(profileObject.Username)&& (!profileObject.Username.equals(currentUser))){
            controller.showalert("Your username already exists, choose another one");
            return false;
        }

        //password
        if(profileObject.Password.length() !=8 || !(profileObject.Password.matches("[a-zA-Z0-9@./#&+-]*"))){
            controller.showalert("Your password is illegal");
            return false;
        }
        //firstname
        if(profileObject.FirstName.length() >20 || !(profileObject.FirstName.matches("[a-zA-Z\\s]*"))){
            controller.showalert("Your first name is illegal");
            return false;
        }
        //Lastname
        if(profileObject.LastName.length() >20 || !(profileObject.LastName.matches("[a-zA-Z\\s]*"))){
            controller.showalert("Your last name is illegal");
            return false;
        }
        //birthdate
        if(profileObject.BirthDate==null){
            controller.showalert("Sorry, you have to fill your birthdate");
            return false;
        }
        if(Integer.parseInt(profileObject.BirthDate.substring(0,4))> 2000){
            controller.showalert("Sorry, you are too young");
            return false;
        }
        if(profileObject.City.length() >20 ) {
            controller.showalert("Your city is illegal");
            return false;
        }
        return true;

    }

    public boolean Read(String username) {
        return DBM.ReadProfile(username);
    }

    public boolean Update(ProfileObject profileObject) {
        if(isDataCorrect(profileObject)) {
            DBM.UpdateProfile(currentUser,profileObject);
            currentUser=profileObject.Username;
            return true;
        }
        return false;
    }

    public void Delete(String registrationDuration, Reason reason) {
        String reasonAsString="";
        switch (reason) {
            case I_FOUND_WHAT_I_WAS_LOOKING_FOR:
               reasonAsString="1";
                break;
            case DISAPPOINTED_FROM_THE_SERVICE:
                reasonAsString="2";
                break;
            case I_FOUND_A_BETTER_SERVICE:
                reasonAsString="3";
                break;
            case OTHER:
                reasonAsString="4";
                break;
            case NO_ANSWER:
                reasonAsString="5";
                break;
        }
        DBM.DeleteProfile(currentUser, reasonAsString,registrationDuration);
        DBM.AddReason(currentUser, reasonAsString,registrationDuration);
    }

    public boolean Login(String username, String password) {
        if(DBM.ReadProfile(username)){ //if found in db
            String realpass=DBM.getPassword(username);
            if( realpass.equals(password)){
                currentUser =  username;
                return true;
            }
        }
        return false;
    }


    public void Logout() {
        currentUser = null;
    }

    public void CreateDB() {
        DBM.CreateDB();
    }
    /*
    gets user record devided to fields
     */
    public String[] getFields(String username) {
        if (username == null) {
            return DBM.getFields(currentUser);
        }
        return DBM.getFields(username);
    }

/*
get user's photo - did not use this yet
 */
    public byte[] getPhoto(String user) {
        if (user==null){
            return DBM.getPhoto(currentUser);
        }
        return new byte[0];
    }

    public ArrayList<String> GetNewRequest(){

     ArrayList<String> VacationRequest= DBM.GetUserRequest(DBM.GetUserVacatuon(currentUser));//List  reqested vacations
        return VacationRequest;

    }

    public ArrayList<VacationObject> GetSearchResult(VacationObject vacationObject){
      ArrayList<VacationObject> searchResults=DBM.SearchResults(vacationObject);
      return searchResults;

    }
    public void ChooseVacation(String VacationID){
       DBM.UpdateVacationStatus(VacationStatus.WAITTING_FOR_APPROVAL,VacationID);
       DBM.ChooseVacation(VacationID,currentUser);

    }
    public boolean ConfirmPayment(PaymentObject paymentObject){
       return DBM.InsertPayment(paymentObject);

    }

    public boolean InsertVacation(VacationObject vacationObject){
        return DBM.InsertVacation(vacationObject);

    }
    public boolean UpdateVacation(VacationObject vacationObject){
        return DBM.UpdateVacation(vacationObject);
    }

    public void DeleteVacation(String VacationID){
         DBM.DeleteVacation(VacationID);
    }

}




