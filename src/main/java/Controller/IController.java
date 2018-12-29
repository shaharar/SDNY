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

    String[] GetnewPaymentsConfirmation();

    boolean sellerAcceptedOrDeniedPayment(boolean accepted, String vacationID);

    boolean BuyerConfirmsPayment(String vacationID);

    String [][] GetVacationStatusvalues();

    Vacation getVacationFields(Object vacationId);
    String [] VacToStringArr(Vacation vacation);

    boolean isYourVacation (String vacationID);

    ArrayList<String> getUsersVacations();

    /*
        @Override
        public boolean GetPayVisa(String[] Visa) {
            return Model.ConfirmPaymentVisa(StringArrVisaToPay(Visa),Visa[6]);
        }

        public Payment StringArrVisaToPay(String [] VisaValues){
            return new Payment(null,VisaValues[6],null,VisaValues[0],VisaValues[2],VisaValues[1],VisaValues[3],VisaValues[4],VisaValues[5]);
        }

        @Override
        public boolean GetPayPaypal(String[] paypal, String VacationId) {
        return Model.ConfirmPaypal(paypal,VacationId);
        }
    */
    void SellerAnswer(boolean answer, String vacationID);

    void ConfirmTrade(boolean answer, String s);

    void NewTradeRequest(String substring);

    String [][] GetTradeRequests();
    /*boolean GetPayVisa(String [] Visa);
    void SellerAnswer(boolean answer, String vacationID);
    boolean GetPayPaypal(String[] paypal, String VacationId);
    */
}
