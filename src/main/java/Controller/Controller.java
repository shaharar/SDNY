package Controller;

import Model.*;
import View.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Controller implements IController {

    public IModel Model;
    public IMainView MyView;

    public Controller(MainView myMainView) {
        this.Model = new Model1(this);
        this.Model.CreateDB();
        this.MyView = myMainView;
    }

    public boolean Login(String username, String password) {
        return (Model.Login(username, password));
    }

    public boolean SearchProfile(String username) {
        return (Model.Read(username));
    }

    public String[] getFields(String username) {
        Profile currProfile = Model.getProfileFields(username);
        String[] fields = new String[7];
        fields[0] = currProfile.Username;
        fields[1] = currProfile.Password;
        fields[2] = currProfile.FirstName;
        fields[3] = currProfile.LastName;
        fields[4] = currProfile.BirthDate;
        fields[5] = currProfile.City;
        fields[6] = currProfile.PhotoPath;

        return fields;
    }

    public boolean Update(String[] fields) {
        return Model.UpdateProfile(new Profile(fields));
    }

    public boolean SignUp(String[] fields) {
        Profile po = new Profile(fields);
        if (Model.SignUp(po)) { //if managed to create the profileObject
            Model.Login(po.Username, po.Password);
            return true;
        }
        return false;
    }

    public void Delete(String registrationDuration, Reason reason) {
        Model.Delete(registrationDuration, reason);
    }

    /* get fxml file and opens a new window on top of the main window(which stays untouchable)*/
    public void openwindow(String fxmlfile, Object Parameter) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = null;
        try {
            root = fxmlLoader.load(getClass().getClassLoader().getResource(fxmlfile).openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage newStage = new Stage();
        Scene scene = new Scene(root, 600, 457);
        newStage.setScene(scene);

        AView NewWindow = fxmlLoader.getController();
        NewWindow.setStage(newStage);
        NewWindow.setController(this);
        newStage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
        newStage.show();
        NewWindow.init(Parameter);

    }

    public void LogOut() {
        Model.Logout();
    }


    @Override
    public String[][] SearchVacation(boolean buyAll, String[] TextFields) {
        if (TextFields == null) {
            return null;
        }
        try {
            //default values so the logic wont be ruined in the isVacationValid in model
            TextFields[0] = "1";
            TextFields[1] = "1";
            TextFields[2] = "1";
            TextFields[3] = "1";
            TextFields[4] = "1";
            TextFields[9] = "1";
            TextFields[10] = "1";
            TextFields[11] = "1";
            ArrayList<Vacation> vacations = Model.GetSearchResult(StringArrToVac(TextFields, buyAll));
            if (vacations != null) {
                String[][] allResults = new String[vacations.size()][];
                for (int i = 0; i < vacations.size(); i++) {
                    allResults[i] = VacToStringArr(vacations.get(i));

                }
                return allResults;
            }
            return null;
        } catch (Exception e) {
            System.out.println(e);
            showalert("Try entering again");
            return null;
        }
    }

    public void showalert(String alert) {
        MyView.showAlert(alert);
    }

    @Override
    public String[] GetNewRequests() {


        ArrayList<String> Request = Model.GetNewRequests();
        String[] requestarr = new String[Request.size()];
        for (int i = 0; i < Request.size(); i++) {
            requestarr[i] = Request.get(i);


        }
        return requestarr;
    }

    @Override
    public boolean CreateVacation(String[] strings, boolean selected) {
        strings[0] = "1";
        return Model.InsertVacation(StringArrToVac(strings, selected));
    }

    @Override
    public boolean DeleteVacation(String vacationId) {

        return Model.DeleteVacation(vacationId);
    }

    @Override
    public boolean UpdateVacation(String[] strings, boolean selected) {
        return Model.UpdateVacation(StringArrToVac(strings, selected));
    }


    public boolean ChooseVacation(String vacationId) {
        return Model.ChooseVacation(vacationId);

    }


    @Override
    public String[] GetnewPaymentsConfirmation() {
        ArrayList<String> payments = Model.GetNewPaymentsConfirmation();
        String[] PaymentsArr = new String[payments.size()];
        for (int i = 0; i < payments.size(); i++) {
            PaymentsArr[i] = payments.get(i);
        }
        return PaymentsArr;
    }

    @Override
    public boolean sellerAcceptedOrDeniedPayment(boolean accepted, String vacationID) {
        //should we delete request? in what cases?
        return Model.sellerAcceptedOrDeniedPayment(accepted, vacationID);
    }

    @Override
    public boolean BuyerConfirmsPayment(String vacationID) {
        return Model.PaymentConfirmation(vacationID);
    }


    @Override //we transpose the array
    public String[][] GetVacationStatusvalues() {
        ArrayList<ArrayList<String>> resultrequest = Model.GetResultRequest();
        String[][] result = new String[3][];
        String[] ids = new String[resultrequest.size()];
        String[] status = new String[resultrequest.size()];
        String[] payments = new String[resultrequest.size()];
        for (int i = 0; i < resultrequest.size(); i++) {
            ids[i] = resultrequest.get(i).get(0);
            status[i] = resultrequest.get(i).get(1);
            payments[i] = resultrequest.get(i).get(2);
        }
        result[0] = ids;
        result[1] = status;
        result[2] = payments;
        return result;
    }

    @Override
    public Vacation getVacationFields(Object parameter) {
        return Model.getVacationFields((String) parameter);
    }

    public Vacation StringArrToVac(String[] GuiValues, boolean buyAll) {
        for (int i = 0; i < GuiValues.length; i++) {
            if (GuiValues[i] == null) {
                showalert("Please fill all fields");
                return null;
            } else if (GuiValues[i].equals("")) {
                showalert("Please enter all fields");
                return null;
            }
        }
        int vacationID = -1;
        int numOfSuitcases = -1;
        int maxWeight = -1;
        int price = -1;
        try {
            vacationID = Integer.parseInt(GuiValues[0]);
            numOfSuitcases = Integer.parseInt(GuiValues[9]);
            maxWeight = Integer.parseInt(GuiValues[10]);
            price = Integer.parseInt(GuiValues[11]);
        } catch (Exception e) {
            showalert("Please enter numbers");
            return null;
        }

        return new Vacation(vacationID, null, null, false, "adu-" + GuiValues[1] + "chi-" + GuiValues[2] + "bab-" + GuiValues[3], buyAll, GuiValues[4], GuiValues[5], GuiValues[6], GuiValues[7] + GuiValues[8], numOfSuitcases, maxWeight, price);
    }

    public String[] VacToStringArr(Vacation vacation) {
        String[] values = new String[12];
        values[0] = "" + vacation.VacationID;
        String[] adultTicketsArr = vacation.TicketType.split("adu-");
        String[] childrenArr = adultTicketsArr[1].split("chi-");
        String[] babyArr = childrenArr[1].split("bab-");
        values[1] = childrenArr[0];
        values[2] = babyArr[0];
        values[3] = babyArr[1];
        values[4] = "" + vacation.BuyAll;
        values[5] = vacation.FlightCompany;
        values[6] = vacation.Origin;
        values[7] = vacation.Destination;
        values[8] = vacation.VacationDate;
        values[9] = "" + vacation.NumberOfSuitcases;
        values[10] = "" + vacation.MaxWeight;
        values[11] = "" + vacation.Price;
        return values;
    }


    public boolean isYourVacation(String vacationID) {
        return Model.isYourVacation(vacationID);
    }

    public ArrayList<String> getUsersVacations() {
        ArrayList<Vacation> dataFromModel = Model.getAllUsersVacations();//user's
        ArrayList<String> results = new ArrayList<>();
        for (int i = 0; i < dataFromModel.size(); i++) {
            Vacation vacObj = dataFromModel.get(i);
            String[] vacArr = VacToStringArr(vacObj);
            results.add("Vacation ID : " + vacObj.VacationID + "\n" + "Origin : " + vacObj.Origin + ",  Destination : " + vacObj.Destination + ",    Dates : " + vacObj.VacationDate + "\n" + "Adults : " + vacArr[1] + ",  Children : " + vacArr[2] + ",   Babies : " + vacArr[3] + ",    Flight Company : " + vacObj.FlightCompany + "\nNumber of suitcases : " + vacObj.NumberOfSuitcases + ",   Max weight of suitcase : " + vacObj.MaxWeight + " kg\nPrice: " + vacObj.Price + "$,  Enable partial purchase : " + vacObj.BuyAll);
        }
        return results;
    }

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
    @Override
    public void SellerAnswer(boolean answer, String vacationID) {
        Model.SellerAnswer(answer, vacationID);
    }

    @Override
    public void ConfirmTrade(boolean answer, String id) {
        Model.ConfirmTrade(answer, id);


    }

    @Override
    public void NewTradeRequest(String Vacationid) {
        Model.NewTradeRequest(Vacationid);
    }

    @Override
    public String[][] GetTradeRequests() {
        ArrayList<Vacation> vacationdata = Model.GetTradeRequests();
        String[][] ans = new String[2][];
        String[] idvacation = new String[vacationdata.size()];
        String[] Vacationdata = new String[vacationdata.size()];
        for (int i = 0; i <vacationdata.size() ; i++) {
            idvacation[i]=""+vacationdata.get(i).VacationID;
            Vacation vacObj=vacationdata.get(i);
            String[] vacArr = VacToStringArr(vacObj);
            Vacationdata[i]=("Vacation ID : " + vacObj.VacationID + "\n" + "Origin : " + vacObj.Origin + ",  Destination : " + vacObj.Destination + "\nDates : " + vacObj.VacationDate + "\n" + "Adults : " + vacArr[1] + ",  Children : " + vacArr[2] + ",   Babies : " + vacArr[3] + "\nFlight Company : " + vacObj.FlightCompany + "  Number of suitcases : " + vacObj.NumberOfSuitcases + ",   Max weight of suitcase : " + vacObj.MaxWeight + " kg\nPrice: " + vacObj.Price + "$,  Enable partial purchase : " + vacObj.BuyAll);
        }
        ans[0]=idvacation;
        ans[1]=Vacationdata;
        return ans;
    }


}




