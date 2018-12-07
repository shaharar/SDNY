package Controller;

import Model.Reason;

public interface IController {

    boolean Login(String username, String password);
    boolean SignUp(String [] fields);
    boolean SearchProfile(String username);
    String[] getFields(String username);
    boolean Update(String [] fields);
    void Delete(String registrationDuration, Reason reason);
    void openwindow(String fxmlfile, Object parameter);
    void LogOut();
    String[][] SearchVacation(boolean buyAll, String[] TextFields);

    void showalert(String alert);

    String[] GetNewRequests();

    boolean CreateVacation(String[] strings, boolean selected);

    void DeleteVacation(String vacationId);

    void UpdateVacation(String[] strings, boolean selected);

    boolean ChooseVacation(String vacationId);

    void GetPayVisa(String [] Visa);

    void GetPayPaypal(String[] paypal);

    void SellerAnswer(boolean answer, String vacationID);

    String[] GetnewPayments();
}
