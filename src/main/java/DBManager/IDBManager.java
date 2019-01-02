package DBManager;


import Model.*;

import java.util.ArrayList;

public interface IDBManager {
    void InsertProfile(Profile profile);
    boolean ReadProfile(String username);
    void UpdateProfile(String username, Profile profile);
    void DeleteProfile(String currentuser, String reason, String RegistrD);
    void CreateDB();
    String getPassword(String username);
    Profile getFields(String currentUser);
    void AddDeleteInfo(String currentuser, String reason, String RegistrD);
    boolean InsertPayment(Payment payment);
    void UpdateVacationStatus(VacationStatus status, String VacationID);
    String GetSeller(String vacationID);
     void InsertNewRequest(String VacationID , String BuyerUserName );
     ArrayList<Vacation> SearchResults(Vacation vacation);
     ArrayList<String> GetUserRequestForApproval(ArrayList<String> vacations);
     ArrayList<String> GetUserVacation(String UserName);
     boolean InsertVacation(Vacation vacation);
     boolean UpdateVacation(Vacation vacation);
     void DeleteVacation(String VacationID);
    Vacation GetVacation(String vacationID);
    void InsertPaymentPaypal(String[] paypal);
    void UpdateRequestStatus(RequestStatus requestStatus, String vacationID);
    ArrayList<String> GetNewPaymentsConfirmation(String strings);
    int[] GetMaxId();
    ArrayList<ArrayList<String>> GetPendingRequestTable(String currentUser);
    void DeleteRequest(String requestId);
    ArrayList<Vacation> getAllUsersVacations(String UserName);

    boolean isInMyRequests(String currentUser, String vacationID);
    void InsertNewTrade(TradeIn tradeIn,String userForOffer);
    ArrayList<TradeIn> GetTradeRequest(String currentUser);
}
