package Model;

import javafx.util.Pair;

import java.util.ArrayList;

public interface IModel {



    boolean SignUp(Profile profile);
    boolean Read (String username);
    boolean UpdateProfile(Profile profile);
    void DeleteProfile(String registrationDuration, Reason reason);
    boolean Login(String username, String password);
    void Logout();
    void CreateDB();
    ArrayList<Vacation> GetSearchResult(Vacation vacation);
    ArrayList<String> GetNewRequests();
    boolean InsertVacation(Vacation vacation);
    boolean DeleteVacation(String VacationID);
    Profile getProfileFields(String username);
    boolean UpdateVacation(Vacation vacation);
    boolean ChooseVacation(String VacationID);
    Vacation getVacationFields(String parameter);
    void showAlert(String text);
    ArrayList<String> GetNewPaymentsConfirmation();

    ArrayList<ArrayList<String>> GetResultRequest();
    ArrayList<Vacation> getAllUsersVacations();
    boolean isYourVacation(String vacationID);

    boolean sellerAcceptedOrDeniedPayment(boolean accepted, String vacationID);

    boolean PaymentConfirmation(String vacationID);

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
    void SellerAnswer(boolean answer, String vacationID);

    void ConfirmTrade(boolean answer, String id, String vacationWanted);

    boolean NewTradeRequest(TradeIn vacationid);

    Pair<ArrayList<Vacation>, ArrayList<String>> GetTradeRequests();
/*
    boolean PaymentConfirmation(String vacationID);
    boolean ConfirmPaymentVisa(Payment payment, String requestid);
    boolean ConfirmPaypal(String[] paypal, String requestid);*/
}
