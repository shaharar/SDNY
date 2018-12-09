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
        Scene scene = new Scene(root, 720, 404);
        newStage.setScene(scene);

        Awindow NewWindow=fxmlLoader.getController();
        NewWindow.setStage(newStage);
        NewWindow.setController(this);
        newStage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
        newStage.show();
        if(fxmlfile.equals("UpdateProfile.fxml")){
            UpdateWindowView wind=(UpdateWindowView) NewWindow;
            wind.textFieldUpdate();
        }
        else if(fxmlfile.equals("Profile.fxml")) {
           ProfileWindowView wind=(ProfileWindowView) NewWindow;
           wind.textFieldProfile((String)Parameter);
        }
        else if(fxmlfile.equals("VacationSearch.fxml")||fxmlfile.equals("CreateVacation.fxml")){
            AVacationWindow window=(AVacationWindow) NewWindow;
            window.SetLists();
        }
        else if(fxmlfile.equals("UpdateVacation.fxml")){
            UpdateVacationView window=(UpdateVacationView) NewWindow;
            window.SetLists();
            VacationObject currentVacation=Model.getVacationFields((String)Parameter);
            window.SetValues(VacToStringArr(currentVacation),currentVacation.BuyAll);
        }
        else if(fxmlfile.equals("ShowResult.fxml")){
            ShowResultView window=(ShowResultView) NewWindow;
            window.SetResults((String [][])Parameter);
        }
        else if(fxmlfile.equals("Requests.fxml")){
            RequestsView window=(RequestsView) NewWindow;
            window.SetVacationID((String[])Parameter);
        }
        else if(fxmlfile.equals("Payments.fxml")){
            PaymentsView window=(PaymentsView) NewWindow;
            window.SetVacationID((String[])Parameter);
        }
        else if(fxmlfile.equals("VacationStatus.fxml")){
            VacationStatusView window=(VacationStatusView) NewWindow;
            window.Updatetableview((String[][])Parameter);
        }

    }

    public void LogOut(){
        Model.Logout();
    }


    @Override
    public String[][] SearchVacation(boolean buyAll, String[] TextFields) {
        try{
            ArrayList<VacationObject > vacationObjects= Model.GetSearchResult(StringArrToVac(TextFields,buyAll));
            if( vacationObjects!=null){
                String[][] allResults=new String[vacationObjects.size()][];
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
    public void UpdateVacation(String[] strings, boolean selected) {
        Model.UpdateVacation(StringArrToVac(strings,selected));
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

    @Override //we transopse the array
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

    public VacationObject StringArrToVac(String [] GuiValues, boolean buyAll){
        int numOfSuitcases= -1;
        int maxWeight= -1;
        int price= -1;
        try{
           numOfSuitcases= Integer.parseInt(GuiValues[8]);
           maxWeight= Integer.parseInt(GuiValues[9]);
           if (GuiValues.length == 11){
               price= Integer.parseInt(GuiValues[10]);
           }
        }catch (Exception e){
            showalert("Please enter numbers");
            return null;
        }

        return new VacationObject(-1,null,null,false,"adu-"+GuiValues[0]+"chi-"+GuiValues[1]+"bab-"+GuiValues[2],buyAll,GuiValues[3],GuiValues[4],GuiValues[5],GuiValues[6]+GuiValues[7],numOfSuitcases,maxWeight,price);
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




