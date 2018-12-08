package DBManager;


import Model.*;

import java.util.ArrayList;

public interface IDBManager {
    void InsertProfile(ProfileObject profileObject);
    boolean ReadProfile(String username);
    void UpdateProfile(String username, ProfileObject profileObject);
    void DeleteProfile(String currentuser, String reason, String RegistrD);
    void CreateDB();
    String getPassword(String username);
    String[] getFields(String currentUser);
    void AddReason(String currentuser, String reason, String RegistrD);
    boolean InsertPayment(PaymentObject paymentObject);
    void UpdateVacationStatus(VacationStatus status, String VacationID);
    String GetSeller(String vacationID);
     void ChooseVacation(String VacationID ,  String BuyerUserName );
     ArrayList<VacationObject> SearchResults(VacationObject vacationObject);
     ArrayList<String> GetUserRequest(ArrayList<String> vacations);
     ArrayList<String> GetUserVacation(String UserName);
     boolean InsertVacation(VacationObject vacationObject);
     boolean UpdateVacation(VacationObject vacationObject);
     void DeleteVacation(String VacationID);

    VacationObject GetVacation(String vacationID);

    void InsertPaymentPaypal(String[] paypal);

    void UpdateRequestStatus(VacationStatus waitingForPayment, String vacationID);

    ArrayList<String> GetNewPayments(String strings);

    int[] GetMaxId();
}
