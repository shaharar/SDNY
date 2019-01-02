package Model;

import DBManager.IDBManager;
import DBManager.DBManager;
import Controller.IController;
import javafx.util.Pair;

import java.time.LocalDateTime;
import java.util.ArrayList;


public class Model1 implements IModel {

    IController controller;
    IDBManager DBM;
  //  String currentUser;
    Profile currentUser;
    int PaymentId;
    int VacationId; //counter for next id


    public Model1(IController controller) {
        this.DBM = new DBManager(this);
        this.controller = controller;
    }

    public boolean SignUp(Profile profile) {
        if(isDataCorrect(profile)){
            DBM.InsertProfile(profile);
            currentUser=profile; //changed during string-> object
            InitID();
            return true;
        }
        return false;
    }
    //we will check the special constraints accordingly
    private boolean isDataCorrect(Profile profile) {
        //username
        if(profile.Username.length() > 8 || profile.Username.equals("")|| !(profile.Username.matches("[a-zA-Z0-9]*")) ){
            controller.showalert("Your username is illegal.\nPlease press at 'Attention' button for more details");
            return false;
        }else
        if(DBM.ReadProfile(profile.Username)&& (!profile.Username.equals(currentUser.Username))){
            controller.showalert("Your username already exists, please choose another one");
            return false;
        }

        //password
        if(profile.Password.length() !=8 || !(profile.Password.matches("[a-zA-Z0-9@./#&+-]*"))){
            controller.showalert("Your password is illegal\nPlease press at 'Attention' button for more details");
            return false;
        }
        //firstname
        if(profile.FirstName.length() >20 || !(profile.FirstName.matches("[a-zA-Z\\s]*"))){
            controller.showalert("Your first name is illegal\nPlease press at 'Attention' button for more details");
            return false;
        }
        //Lastname
        if(profile.LastName.length() >20 || !(profile.LastName.matches("[a-zA-Z\\s]*"))){
            controller.showalert("Your last name is illegal\nPlease press at 'Attention' button for more details");
            return false;
        }
        //birthdate
        if(profile.BirthDate==null){
            controller.showalert("Sorry, you have to fill your birthdate");
            return false;
        }
        if(Integer.parseInt(profile.BirthDate.substring(0,4))> 2000){
            controller.showalert("Sorry, you are too young");
            return false;
        }
        if(profile.City.length() > 20 ) {
            controller.showalert("Your city is illegal\nPlease press at 'Attention' button for more details");
            return false;
        }
        return true;
    }

    public boolean Read(String username) {
        return DBM.ReadProfile(username);
    }

    public boolean UpdateProfile(Profile Updatedprofile) {
        if(!currentUser.Username.equals(Updatedprofile.Username)){
            showAlert("You cannot update your username");
            return false;
        }
        if(isDataCorrect(Updatedprofile)) {
            DBM.UpdateProfile(currentUser.Username, Updatedprofile);
            //currentUser= profile.Username;
            currentUser=Updatedprofile;//changed during string-> object
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
       ArrayList<String> vacationsToDelete= DBM.GetUserVacation(currentUser.Username);
        for (int i = 0; i <vacationsToDelete.size() ; i++) {
            DBM.DeleteVacation(vacationsToDelete.get(i));
        }
        DBM.DeleteProfile(currentUser.Username, reasonAsString,registrationDuration);
        DBM.AddDeleteInfo(currentUser.Username, reasonAsString,registrationDuration);
        currentUser=null;//changed during string-> object
    }

    public boolean Login(String username, String password) {
        if(DBM.ReadProfile(username)){ //if found in db
            String realpass=DBM.getPassword(username);
            if( realpass.equals(password)){
                currentUser = DBM.getFields(username);
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
    public Profile getProfileFields(String username) {
        if (username == null) {
            return new Profile(currentUser);
        }
        return DBM.getFields(username);
    }

    public ArrayList<String> GetNewRequests(){

     ArrayList<String> VacationRequest= DBM.GetUserRequestForApproval(DBM.GetUserVacation(currentUser.Username));//List  reqested vacations
        return VacationRequest;

    }

    public ArrayList<Vacation> GetSearchResult(Vacation vacation){
    if(!IsVacationDetailsValid(vacation)){
        return null;
    }

        ArrayList<Vacation> searchResults=DBM.SearchResults(vacation);
      return searchResults;

    }
    public boolean ChooseVacation(String VacationID){
        if(currentUser==null){
            controller.showalert("In order to purchase a vacation you have to sign in");
            return false;
        }
        else if(DBM.isInMyRequests(currentUser.Username, VacationID)){
            showAlert("You have already chosen this vacation. Please look at your pending requests");
            return false;
        }
        else {
            DBM.UpdateVacationStatus(VacationStatus.NOT_AVAILABLE,VacationID);
            DBM.InsertNewRequest(VacationID,currentUser.Username);
            return true;
        }
    }

    @Override
    public Vacation getVacationFields(String VacationID) {
        return DBM.GetVacation(VacationID);
    }

    @Override
    public void showAlert(String text) {
        controller.showalert(text);
    }

    @Override
    public ArrayList<String> GetNewPaymentsConfirmation() {
        ArrayList<String> VacationPayment= DBM.GetNewPaymentsConfirmation(currentUser.Username);//List  requested vacations
        return VacationPayment;
    }

    @Override
    public ArrayList<ArrayList<String>> GetResultRequest() {

        ArrayList<ArrayList<String>> DBMResult=DBM.GetPendingRequestTable(currentUser.Username);
        for (int i = 0; i <DBMResult.size() ; i++) {
            if(DBMResult.get(i).get(1).equals("REQUEST_APPROVED")){
                DBMResult.get(i).add("Yes");
            }
            else {
                DBMResult.get(i).add("No");

            }

        }
        return DBMResult;
    }

    @Override
    public ArrayList<Vacation> getAllUsersVacations() {
        return DBM.getAllUsersVacations(currentUser.Username);
    }

    @Override
    public boolean isYourVacation(String vacationID) {
        return DBM.GetSeller(""+vacationID).equals(currentUser.Username);
    }

    @Override
    public boolean sellerAcceptedOrDeniedPayment(boolean accepted, String vacationID) {
        if(accepted){
            DBM.UpdateVacationStatus(VacationStatus.SOLD,vacationID);
            DBM.UpdateRequestStatus(RequestStatus.PAYMENT_APPROVED,vacationID);
        }
        else{
            DBM.UpdateVacationStatus(VacationStatus.FOR_SALE,vacationID);
            DBM.UpdateRequestStatus(RequestStatus.PAYMENT_DISAPPROVED,vacationID);
        }
        return true;
    }

    @Override
    public boolean PaymentConfirmation(String vacationID) {
        //DBM.UpdateVacationStatus(VacationStatus.SOLD,vacationID);
        DBM.UpdateRequestStatus(RequestStatus.BUYER_CONFIRMED_PAYMENT,vacationID);
        return true;
    }


    public boolean InsertVacation(Vacation vacation){
        if(vacation == null){
            return false;
        }
        if( !IsVacationDetailsValid(vacation)){
            return false;
        }
        vacation.VacationID=VacationId;
        VacationId++;
        vacation.UserName_fk=currentUser.Username;
        vacation.Status=""+VacationStatus.FOR_SALE;
        return DBM.InsertVacation(vacation);

    }
    public boolean UpdateVacation(Vacation vacation){
        if( !IsVacationDetailsValid(vacation)){
            return false;
        }
        return DBM.UpdateVacation(vacation);
    }

    public boolean DeleteVacation(String VacationID){
       if(DBM.GetSeller(VacationID).equals(currentUser.Username)){
         DBM.DeleteVacation(VacationID);
         DBM.UpdateRequestStatus(RequestStatus.REQUEST_DISAPPROVED,VacationID);
         return true;
       }
      else{
           controller.showalert("The vacation ID is invalid. Please try again");
           return false;
       }
    }

    public boolean IsVacationDetailsValid(Vacation vacation){
        if(vacation.TicketType.length()>20){
            controller.showalert("Choose Tickets Type again");
            return false;
        }
        else if(vacation.FlightCompany.length()>10){
            controller.showalert("Your Flight Company name is too long");
            return false;
        }
        else if(vacation.Origin.length()>30){
            controller.showalert("Your Origin is too long");
            return false;
        }
        else if(vacation.Destination.length()>30){
            controller.showalert("Your Destination is too long");
            return false;
        }
        int startDay=Integer.parseInt(vacation.VacationDate.substring(8,10));
        int startMonth=Integer.parseInt(vacation.VacationDate.substring(5,7));
        int startYear=Integer.parseInt(vacation.VacationDate.substring(0,4));
        int finishDay=Integer.parseInt(vacation.VacationDate.substring(18,20));
        int finishMonth=Integer.parseInt(vacation.VacationDate.substring(15,17));
        int finishYear=Integer.parseInt(vacation.VacationDate.substring(10,14));
        LocalDateTime date=LocalDateTime.now();
        if((date.getYear() > startYear) || (date.getYear() == startYear && ((date.getMonthValue() > startMonth) || (date.getMonthValue() == startMonth && date.getDayOfMonth() > startDay)))){
            controller.showalert("Start day has passed. Try changing dates");
            return false;
        }
       else if(finishYear<startYear || (finishYear==startYear && finishMonth<startMonth)||(finishYear==startYear && finishMonth==startMonth && finishDay<startDay )) {
            controller.showalert("Your dates are invalid, please choose them again");
            return false;
        }else{
            vacation.VacationDate=""+startDay+"-"+startMonth+"-"+startYear+"_"+finishDay+"-"+finishMonth+"-"+finishYear;
        }
        if(vacation.NumberOfSuitcases > 3){
            controller.showalert("Max number of suitcases per person is 3");
            return false;
        }
        else if(vacation.MaxWeight > 30){
            controller.showalert("Max weight of suitcase is 30 kg");
            return false;
        }
        return true;
    }



    /*
        public boolean ConfirmPaymentVisa(Payment payment, String RequestId){
            if(payment.Useridoc.length()!=9 || !payment.Useridoc.matches("[0-9]+") ){
                controller.showalert("Your Id number invalid. make sure you added the check digits");
                return false;
            }
           else if(payment.LastName.length()>20){
                controller.showalert("Your last name is too long");
                return false;
            }
           else if(payment.FirstName.length()>20){
                controller.showalert("Your first name is too long");
                return false;
            }
            else if(payment.CardNumber.length()!=16 || !payment.CardNumber.matches("[0-9]+")){
                controller.showalert("Your credit number is invalid. please enter 16 digits");
                return false;
            }
            int month=Integer.parseInt(payment.ExpirationDate.substring(0,2));
            int year=Integer.parseInt(payment.ExpirationDate.substring(2,4));
            if(year<18 || month>12 ||month<0){
                controller.showalert("Your expiration date is invalid. Please insert date in format mmyy");
                return false;
            }
            else if(payment.SecurityCode.length()!=3 || !payment.SecurityCode.matches("[0-9]+")){
                controller.showalert("Your security code  is invalid. Please enter 3 digits");
                return false;
            }

            payment.PaymentID=""+PaymentId;
            PaymentId++;
            payment.UserName_fk=currentUser;

           if(DBM.InsertPayment(payment)){
               DBM.DeleteRequest(RequestId);
               DBM.UpdateVacationStatus(VacationStatus.SOLD, payment.VacationID_fk);
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
*/
        @Override
        public void SellerAnswer(boolean answer, String vacationID) {
            if (answer==true){
                DBM.UpdateRequestStatus(RequestStatus.REQUEST_APPROVED,vacationID);
                DBM.UpdateVacationStatus(VacationStatus.WAITING_FOR_PAYMENT,vacationID);
                controller.showalert("Wait For Buyer's Payment");

            }
            else {
                DBM.UpdateRequestStatus(RequestStatus.REQUEST_DISAPPROVED,vacationID);
                DBM.UpdateVacationStatus(VacationStatus.FOR_SALE,vacationID);
            }
        }

    @Override
    public void ConfirmTrade(boolean answer, String vacationOffered, String vacationWanted) {
            if(answer==true){
                DBM.UpdateRequestStatus(RequestStatus.TRADE_REQUEST_APPROVED,vacationOffered);
                DBM.UpdateVacationStatus(VacationStatus.SOLD,vacationOffered);
                DBM.UpdateRequestStatus(RequestStatus.TRADE_REQUEST_APPROVED,vacationWanted);
                DBM.UpdateVacationStatus(VacationStatus.SOLD,vacationWanted);
                showAlert("The Trade In Is Succesful");
            }
            else {
                DBM.UpdateRequestStatus(RequestStatus.TRADE_REQUEST_DISAPPROVED,vacationOffered);
                DBM.UpdateVacationStatus(VacationStatus.FOR_SALE,vacationOffered);
                showAlert("The Trade In Is Cancelled");
            }


    }

    @Override
    public boolean NewTradeRequest(TradeIn trade) {
        if(currentUser==null){
            controller.showalert("In order to purchase a vacation you have to sign in");
            return false;
        }
        else if(DBM.isInMyRequests(currentUser.Username, ""+trade.vacationWanted)){
            showAlert("You have already chosen this vacation. Please look at your pending requests");
            return false;
        }
        else {
            DBM.UpdateVacationStatus(VacationStatus.NOT_AVAILABLE,""+trade.vacationWanted);
            DBM.InsertNewRequest(""+trade.vacationWanted,currentUser.Username);
            DBM.UpdateRequestStatus(RequestStatus.TRADE_REQUEST_SENT,""+trade.vacationWanted);
            DBM.InsertNewTrade(trade,DBM.GetSeller(""+trade.vacationWanted));
            showAlert("Your Request Is Sent");
            return true;
        }

    }

    @Override
    public Pair<ArrayList<Vacation>, ArrayList<String>> GetTradeRequests() {
            ArrayList<TradeIn> requestid=DBM.GetTradeRequest(currentUser.Username);
            ArrayList<Vacation> vacationsOffered=new ArrayList<>();
        ArrayList<String> vacationsWanted=new ArrayList<>();
        for (int i = 0; i <requestid.size() ; i++) {
            vacationsOffered.add(DBM.GetVacation(""+requestid.get(i).vacationOffered));
            vacationsWanted.add(""+requestid.get(i).vacationWanted);
        }

        return new Pair<ArrayList<Vacation>, ArrayList<String>>(vacationsOffered,vacationsWanted);
    }
}




