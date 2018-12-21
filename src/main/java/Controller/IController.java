package Controller;

import Model.Reason;
import Model.Vacation;

import java.util.ArrayList;

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

    boolean DeleteVacation(String vacationId);

    boolean UpdateVacation(String[] strings, boolean selected);

    boolean ChooseVacation(String vacationId);



    void SellerAnswer(boolean answer, String vacationID);

    String[] GetnewPayments();

    boolean sellerAcceptedOrDeniedPayment(boolean accepted, String vacationID);

    boolean BuyerConfirmsPayment(String vacationID);

    String [][] GetVacationStatusvalues();

    Vacation getVacationFields(Object vacationId);
    String [] VacToStringArr(Vacation vacation);

    boolean isYourVacation (String vacationID);

    ArrayList<String> getUsersVacations();
    /*boolean GetPayVisa(String [] Visa);

    boolean GetPayPaypal(String[] paypal, String VacationId);
    */
}
