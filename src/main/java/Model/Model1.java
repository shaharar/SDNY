package Model;

import DBManager.IDBManager;
import DBManager.DBManager;
import Controller.IController;

import java.time.LocalDateTime;
import java.util.ArrayList;


public class Model1 implements IModel {

    IController controller;
    IDBManager DBM;
    String currentUser;
    int PaymentId;
    int VacationId; //counter for next id


    public Model1(IController controller) {
        this.DBM = new DBManager(this);
        this.controller = controller;
    }

    public boolean SignUp(ProfileObject profileObject) {
        if(isDataCorrect(profileObject)){
            DBM.InsertProfile(profileObject);
            currentUser=profileObject.Username;
            InitID();
            return true;
        }
        return false;
    }
    //we will check the special constraints accordingly
    private boolean isDataCorrect(ProfileObject profileObject) {
        //username
        if(profileObject.Username.length() > 8 || profileObject.Username.equals("")|| !(profileObject.Username.matches("[a-zA-Z0-9]*")) ){
            controller.showalert("Your username is illegal.\nPlease press at 'Attention' button for details");
            return false;
        }else
        if(DBM.ReadProfile(profileObject.Username)&& (!profileObject.Username.equals(currentUser))){
            controller.showalert("Your username already exists, please choose another one");
            return false;
        }

        //password
        if(profileObject.Password.length() !=8 || !(profileObject.Password.matches("[a-zA-Z0-9@./#&+-]*"))){
            controller.showalert("Your password is illegal\nPlease press at 'Attention' button for details");
            return false;
        }
        //firstname
        if(profileObject.FirstName.length() >20 || !(profileObject.FirstName.matches("[a-zA-Z\\s]*"))){
            controller.showalert("Your first name is illegal\nPlease press at 'Attention' button for details");
            return false;
        }
        //Lastname
        if(profileObject.LastName.length() >20 || !(profileObject.LastName.matches("[a-zA-Z\\s]*"))){
            controller.showalert("Your last name is illegal\nPlease press at 'Attention' button for details");
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
        if(profileObject.City.length() > 20 ) {
            controller.showalert("Your city is illegal");
            return false;
        }
        return true;

    }

    public boolean Read(String username) {
        return DBM.ReadProfile(username);
    }

    public boolean UpdateProfile(ProfileObject profileObject) {
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
                InitID();
                return true;
            }
        }
        return false;
    }

    private void InitID() {
        int [] result=DBM.GetMaxId();
        VacationId=result[0]+1;
        PaymentId=result[1]+1;

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
    public String[] getProfileFields(String username) {
        if (username == null) {
            return DBM.getFields(currentUser);
        }
        return DBM.getFields(username);
    }

    public ArrayList<String> GetNewRequests(){

     ArrayList<String> VacationRequest= DBM.GetUserRequest(DBM.GetUserVacation(currentUser));//List  reqested vacations
        return VacationRequest;

    }

    public ArrayList<VacationObject> GetSearchResult(VacationObject vacationObject){
    if(!IsVacationDetailsValid(vacationObject)){
        return null;
    }

        ArrayList<VacationObject> searchResults=DBM.SearchResults(vacationObject);
      return searchResults;

    }
    public boolean ChooseVacation(String VacationID){
        if(currentUser==null){
            controller.showalert("You need to sign in");
            return false;
        }
        else {
            DBM.UpdateVacationStatus(VacationStatus.NOT_AVAILABLE,VacationID);
            DBM.InsertNewRequest(VacationID,currentUser);
            return true;
        }


    }

    @Override
    public VacationObject getVacationFields(String VacationID) {
        return DBM.GetVacation(VacationID);
    }

    public boolean ConfirmPaymentVisa(PaymentObject paymentObject, String RequestId){
        if(paymentObject.Useridoc.length()!=9 || !paymentObject.Useridoc.matches("[0-9]+") ){
            controller.showalert("Your Id number invalid. make sure you added the check digits");
            return false;
        }
       else if(paymentObject.LastName.length()>20){
            controller.showalert("Your last name is too long");
            return false;
        }
       else if(paymentObject.FirstName.length()>20){
            controller.showalert("Your first name is too long");
            return false;
        }
        else if(paymentObject.CardNumber.length()!=16 || !paymentObject.CardNumber.matches("[0-9]+")){
            controller.showalert("Your credit number is invalid. please enter 16 digits");
            return false;
        }
        int month=Integer.parseInt(paymentObject.ExpirationDate.substring(0,2));
        int year=Integer.parseInt(paymentObject.ExpirationDate.substring(2,4));
        if(year<18 || month>12 ||month<0){
            controller.showalert("Your Expiration Date invalid. enter in format mmyy");
            return false;
        }
        else if(paymentObject.SecurityCode.length()!=3 || !paymentObject.SecurityCode.matches("[0-9]+")){
            controller.showalert("Your security code  is invalid. please enter 3 digits");
            return false;
        }

        paymentObject.PaymentID=""+PaymentId;
        PaymentId++;
        paymentObject.UserName_fk=currentUser;

       if(DBM.InsertPayment(paymentObject)){
           DBM.DeleteRequest(RequestId);
           return true;
       }
       return false;

    }

    @Override
    public boolean ConfirmPaypal(String[] paypal, String RequestId) {
        DBM.InsertPaymentPaypal(paypal);
        DBM.DeleteRequest(RequestId);
        return true;
    }

    @Override
    public void SellerAnswer(boolean answer, String vacationID) {
        if (answer==true){
            DBM.UpdateRequestStatus(RequestStatus.APPROVED,vacationID);
            DBM.UpdateVacationStatus(VacationStatus.WAITING_FOR_PAYMENT,vacationID);
            controller.showalert("Wait For Buyer's Payment");

        }
        else {
            DBM.UpdateRequestStatus(RequestStatus.DISAPPROVED,vacationID);
            DBM.UpdateVacationStatus(VacationStatus.FOR_SALE,vacationID);
        }
    }

    @Override
    public void showAlert(String text) {
        controller.showalert(text);
    }

    @Override
    public ArrayList<String> GetNewPayments() {
        ArrayList<String> VacationPayment= DBM.GetNewPayments(currentUser);//List  requested vacations
        return VacationPayment;
    }

    @Override
    public ArrayList<ArrayList<String>> GetResultRequest() {

        ArrayList<ArrayList<String>> DBMResult=DBM.GetRequestTable(currentUser);
        for (int i = 0; i <DBMResult.size() ; i++) {
            if(DBMResult.get(i).get(1).equals("APPROVED")){
                DBMResult.get(i).add("Yes");
            }
            else {
                DBMResult.get(i).add("No");

            }

        }
        return DBMResult;
    }

    @Override
    public ArrayList<VacationObject> getAllUsersVacations() {
        return DBM.getAllUsersVacations(currentUser);
    }

    @Override
    public boolean isYourVacation(String vacationID) {
        return DBM.GetSeller(""+vacationID).equals(currentUser);
    }

    public boolean InsertVacation(VacationObject vacationObject){
        if(vacationObject == null){
            return false;
        }
        if( !IsVacationDetailsValid(vacationObject)){
            return false;
        }
        vacationObject.VacationID=VacationId;
        VacationId++;
        vacationObject.UserName_fk=currentUser;
        vacationObject.Status=""+VacationStatus.FOR_SALE;
        return DBM.InsertVacation(vacationObject);

    }
    public boolean UpdateVacation(VacationObject vacationObject){
        if( !IsVacationDetailsValid(vacationObject)){
            return false;
        }
        return DBM.UpdateVacation(vacationObject);
    }

    public boolean DeleteVacation(String VacationID){
       if(DBM.GetSeller(VacationID).equals(currentUser)){
         DBM.DeleteVacation(VacationID);
         DBM.UpdateRequestStatus(RequestStatus.DISAPPROVED,VacationID);
         return true;
       }
      else{
           controller.showalert("The vacation ID is invalid. Please try again");
           return false;
       }
    }

    public boolean IsVacationDetailsValid(VacationObject vacationObject){
        if(vacationObject.TicketType.length()>20){
            controller.showalert("Choose Tickets Type again");
            return false;
        }
        else if(vacationObject.FlightCompany.length()>10){
            controller.showalert("Your Flight Company name is too long");
            return false;
        }
        else if(vacationObject.Origin.length()>30){
            controller.showalert("Your Origin is too long");
            return false;
        }
        else if(vacationObject.Destination.length()>30){
            controller.showalert("Your Destination is too long");
            return false;
        }
        int startDay=Integer.parseInt(vacationObject.VacationDate.substring(8,10));
        int startMonth=Integer.parseInt(vacationObject.VacationDate.substring(5,7));
        int startYear=Integer.parseInt(vacationObject.VacationDate.substring(0,4));
        int finishDay=Integer.parseInt(vacationObject.VacationDate.substring(18,20));
        int finishMonth=Integer.parseInt(vacationObject.VacationDate.substring(15,17));
        int finishYear=Integer.parseInt(vacationObject.VacationDate.substring(10,14));
        LocalDateTime date=LocalDateTime.now();
        if(date.getYear()<2018||(date.getYear()==2018&& date.getMonthValue()>startMonth)||(date.getMonthValue()==startMonth && date.getDayOfMonth()>startDay)){
            controller.showalert("Start day has passed. Try changing dates");
            return false;
        }
       else if(finishYear<startYear || (finishYear==startYear && finishMonth<startMonth)||(finishYear==startYear && finishMonth==startMonth && finishDay<startDay )) {
            controller.showalert("Your dates are invalid, please choose them again");
            return false;
        }else{
            vacationObject.VacationDate=""+startDay+"-"+startMonth+"-"+startYear+"_"+finishDay+"-"+finishMonth+"-"+finishYear;
        }
        if(vacationObject.NumberOfSuitcases > 3){
            controller.showalert("Max number of suitcases per person is 3");
            return false;
        }
        else if(vacationObject.MaxWeight > 30){
            controller.showalert("Max weight of suitcase is 30 kg");
            return false;
        }
        return true;
    }

}




