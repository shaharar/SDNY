package Model;

import DBManager.IDBManager;
import DBManager.DBManager;
import Controller.IController;


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
            DBM.Create(profileObject);
            return true;
        }
        return false;
    }
    //we will check the special constraints accordingly
    private boolean isDataCorrect(ProfileObject profileObject) {
        //username
        if(profileObject.Username.length() > 8 || !(profileObject.Username.matches("[a-zA-Z0-9]*")) ){
            controller.showalert("Your username is illegal");
            return false;
        }else
        if(DBM.Read(profileObject.Username)&& (!profileObject.Username.equals(currentUser))){
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
        //Lastname
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
        return DBM.Read(username);

    }


    public boolean Update(ProfileObject profileObject) {
        if(isDataCorrect(profileObject)) {
            DBM.Update(currentUser,profileObject);
            currentUser=profileObject.Username;
            return true;
        }
        return false;
    }


    public void Delete(String registrationDuration, Reason reason) {
        switch (reason) {
            case I_FOUND_WHAT_I_WAS_LOOKING_FOR:
                DBM.Delete(currentUser, "1",registrationDuration);
                break;
            case DISAPPOINTED_FROM_THE_SERVICE:
                DBM.Delete(currentUser, "2",registrationDuration);
                break;
            case I_FOUND_A_BETTER_SERVICE:
                DBM.Delete(currentUser, "3",registrationDuration);
                break;
            case OTHER:
                DBM.Delete(currentUser, "4",registrationDuration);
                break;
            case NO_ANSWER:
                DBM.Delete(currentUser, "5",registrationDuration);
                break;
        }

    }


    public boolean Login(String username, String password) {
        if(DBM.Read(username)){
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


    public String[] getFields(String username) {
        if (username == null) {
            return DBM.getFields(currentUser);
        }
        return DBM.getFields(username);
    }


    public byte[] getPhoto(String user) {
        if (user==null){
            return DBM.getPhoto(currentUser);
        }

        return new byte[0];
    }




}




