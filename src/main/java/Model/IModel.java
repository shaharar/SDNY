package Model;

import java.util.ArrayList;

public interface IModel {



    boolean SignUp(Profile profile);
    boolean Read (String username);
    boolean UpdateProfile(Profile profile);
    void  Delete(String registrationDuration, Reason reason);
    boolean Login(String username, String password);
    void Logout();
    void CreateDB();
    ArrayList<Vacation> GetSearchResult(Vacation vacation);
    ArrayList<String> GetNewRequests();
    boolean InsertVacation(Vacation vacation);
    boolean DeleteVacation(String VacationID);
    String[] getProfileFields(String username);
    boolean UpdateVacation(Vacation vacation);
    boolean ChooseVacation(String VacationID);
    Vacation getVacationFields(String parameter);
    boolean ConfirmPaymentVisa(Payment payment, String requestid);
    boolean ConfirmPaypal(String[] paypal, String requestid);
    void SellerAnswer(boolean answer, String vacationID);
    void showAlert(String text);
    ArrayList<String> GetNewPayments();

    ArrayList<ArrayList<String>> GetResultRequest();
    ArrayList<Vacation> getAllUsersVacations();
    boolean isYourVacation(String vacationID);
}
