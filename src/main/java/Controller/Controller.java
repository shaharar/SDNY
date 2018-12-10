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

public class Controller implements IController{

    public IModel Model;
    public IView MyView;

    public Controller(View myView){
        this.Model=new Model1(this);
        this.Model.CreateDB();
        this.MyView = myView;
    }

    public boolean Login(String username, String password) {
        return  (Model.Login(username, password));
    }
    public boolean SearchProfile(String username) {
        return  (Model.Read(username));
    }
    public String[] getFields(String username){
        return Model.getProfileFields(username);
    }
    public boolean Update(String [] fields) {
        return Model.UpdateProfile(new ProfileObject(fields));
    }
    public boolean SignUp(String[] fields) {
        ProfileObject po=new ProfileObject(fields);
        if(Model.SingUp(po)){ //if managed to create the profileObject
            Model.Login(po.Username,po.Password);
            return true;
        }
        return false;
    }

    public void Delete(String registrationDuration, Reason reason) {
        Model.Delete(registrationDuration, reason);
    }

   /* get fxml file and opens a new window on top of the main window(which stays untouchable)*/
     public void openwindow(String fxmlfile,Object Parameter){
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

        Awindow NewWindow=fxmlLoader.getController();
        NewWindow.setStage(newStage);
        NewWindow.setController(this);
        newStage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
        newStage.show();
        NewWindow.init(Parameter);

    }

    public void LogOut(){
        Model.Logout();
    }


    @Override
    public String[][] SearchVacation(boolean buyAll, String[] TextFields) {
         if(TextFields==null){
             return null;
         }
        try{
            //default values so the logic wont be ruined in the isVacationValid in model
            TextFields[0]="1";
            TextFields[1]="1";
            TextFields[2]="1";
            TextFields[3]="1";
            TextFields[4]="1";
            TextFields[9]="1";
            TextFields[10]="1";
            TextFields[11]="1";
            ArrayList<VacationObject > vacationObjects= Model.GetSearchResult(StringArrToVac(TextFields,buyAll));
            if( vacationObjects != null){
                String[][] allResults = new String[vacationObjects.size()][];
                for (int i = 0; i <vacationObjects.size() ; i++) {
                    allResults[i]= VacToStringArr(vacationObjects.get(i));

                }
                return allResults;
            }
            return null;
        }catch (Exception e){
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


       ArrayList<String> Request= Model.GetNewRequests();
       String [] requestarr=new String[Request.size()];
        for (int i = 0; i <Request.size() ; i++) {
            requestarr[i]=Request.get(i);


        }
        return requestarr;
    }

    @Override
    public boolean CreateVacation(String[] strings, boolean selected) {
   return Model.InsertVacation(StringArrToVac(strings,selected));
    }

    @Override
    public boolean DeleteVacation(String vacationId) {

       return Model.DeleteVacation(vacationId);
    }

    @Override
    public boolean UpdateVacation(String[] strings, boolean selected) {
        return Model.UpdateVacation(StringArrToVac(strings,selected));
    }


    public boolean ChooseVacation(String vacationId){
       return Model.ChooseVacation(vacationId);

    }

    @Override
    public boolean GetPayVisa(String[] Visa) {
        return Model.ConfirmPaymentVisa(StringArrVisaToPay(Visa),Visa[6]);
    }

    @Override
    public boolean GetPayPaypal(String[] paypal, String VacationId) {
    return Model.ConfirmPaypal(paypal,VacationId);
    }

    @Override
    public void SellerAnswer(boolean answer, String vacationID) {
        Model.SellerAnswer(answer,vacationID);
    }

    @Override
    public String[] GetnewPayments() {
        ArrayList<String> payments= Model.GetNewPayments();
        String [] PaymentsArr=new String[payments.size()];
        for (int i = 0; i <payments.size() ; i++) {
            PaymentsArr[i]=payments.get(i);
        }
        return PaymentsArr;
    }

    @Override //we transpose the array
    public String[][] GetVacationStatusvalues() {
        ArrayList<ArrayList<String>> resultrequest=Model.GetResultRequest();
        String [][] result=new String [3][];
        String[] ids=new String[resultrequest.size()];
        String[] status=new String[resultrequest.size()];
        String[] payments=new String[resultrequest.size()];
        for (int i = 0; i <resultrequest.size() ; i++) {
            ids[i]=resultrequest.get(i).get(0);
            status[i]=resultrequest.get(i).get(1);
            payments[i]=resultrequest.get(i).get(2);
        }
        result[0]=ids;
        result[1]=status;
        result[2]=payments;
        return result;
    }

    @Override
    public VacationObject getVacationFields(Object parameter) {
        return Model.getVacationFields((String)parameter);
       }

    public VacationObject StringArrToVac(String [] GuiValues, boolean buyAll){
        for (int i = 0; i <GuiValues.length ; i++) {
            if(GuiValues[i].equals("")){
                showalert("Please enter all fields");
                return null;
            }
        }
        int vacationID = -1;
        int numOfSuitcases= -1;
        int maxWeight= -1;
        int price= -1;
        try{
           vacationID = Integer.parseInt(GuiValues[0]);
           numOfSuitcases= Integer.parseInt(GuiValues[9]);
           maxWeight= Integer.parseInt(GuiValues[10]);
           price= Integer.parseInt(GuiValues[11]);
        }catch (Exception e){
            showalert("Please enter numbers");
            return null;
        }

        return new VacationObject(vacationID,null,null,false,"adu-"+GuiValues[1]+"chi-"+GuiValues[2]+"bab-"+GuiValues[3],buyAll,GuiValues[4],GuiValues[5],GuiValues[6],GuiValues[7]+GuiValues[8],numOfSuitcases,maxWeight,price);
    }
    public String [] VacToStringArr(VacationObject vacationObject){
        String[] values=new String[12];
        values[0]=""+vacationObject.VacationID;
        String[] adultTicketsArr=vacationObject.TicketType.split("adu-");
        String[] childrenArr=adultTicketsArr[1].split("chi-");
        String[] babyArr=childrenArr[1].split("bab-");
        values[1]=childrenArr[0] ;
        values[2]=babyArr[0] ;
        values[3]= babyArr[1];
        values[4]=""+vacationObject.BuyAll;
        values[5]= vacationObject.FlightCompany;
        values[6]= vacationObject.Origin;
        values[7]= vacationObject.Destination;
        values[8]= vacationObject.VacationDate;
        values[9]= ""+vacationObject.NumberOfSuitcases;
        values[10]=""+vacationObject.MaxWeight ;
        values[11]=""+vacationObject.Price ;
        return values;
    }
    public PaymentObject StringArrVisaToPay(String [] VisaValues){

        return new PaymentObject(null,VisaValues[6],null,VisaValues[0],VisaValues[2],VisaValues[1],VisaValues[3],VisaValues[4],VisaValues[5]);

    }


}




