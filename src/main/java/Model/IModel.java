package Model;

import java.util.ArrayList;

public interface IModel {



    boolean SingUp(ProfileObject profileObject);
    boolean Read (String username);
    boolean UpdateProfile(ProfileObject profileObject);
    void  Delete(String registrationDuration, Reason reason);
    boolean Login(String username, String password);
    void Logout();
    void CreateDB();
    ArrayList<VacationObject> GetSearchResult(VacationObject vacationObject);
    ArrayList<String> GetNewRequests();
    boolean InsertVacation(VacationObject vacationObject);
    void DeleteVacation(String VacationID);
    String[] getProfileFields(String username);
    boolean UpdateVacation(VacationObject vacationObject);
    boolean ChooseVacation(String VacationID);
    VacationObject getVacationFields(String parameter);
    boolean ConfirmPaymentVisa(PaymentObject paymentObject);
    void ConfirmPaypal(String[] paypal);
    void SellerAnswer(boolean answer, String vacationID);
    ArrayList<String> GetNewPayments();
}
