package DBManager;

import Model.PaymentObject;
import Model.VacationObject;
import Model.VacationStatus;

import java.util.ArrayList;

public class Tester {
    public static void main(String[] args) {
        //dont forget to change in the db the payment method to 6 chars
        DBManager dBmanager=new DBManager();
        dBmanager.CreateDB();
       // test1(dBmanager);//adding Vacation
        // test2(dBmanager);//updating Vacation
       // test2point1(dBmanager);//updating id in Vacation (we might need to fix this)
        //test2point2(dBmanager);//changing back for rest of the tests
        // test3(dBmanager);//(look prints) adding 2 more vacations and returning user Vacations;
        // test4(dBmanager);//choosing a vacation
        // test5(dBmanager);//(look prints)getting the request message;
        // test6(dBmanager);//(look prints)getting search results
        //test7(dBmanager);// inserting payment
        // test8(dBmanager);//(look prints)get seller check
        test9(dBmanager);//deleting Vacation
    }
    private static void test9(DBManager dBmanager) {
        dBmanager.DeleteVacation("id");
        dBmanager.DeleteVacation("id2");
        dBmanager.DeleteVacation("id3");
    }
    private static void test8(DBManager dBmanager) {
        System.out.println(dBmanager.GetSeller("id3"));
        System.out.println("expecting a");
    }
    private static void test7(DBManager dBmanager) {
        PaymentObject paymentObject=new PaymentObject("1","id","b","300948394","cohen","Avi","1234567812345678","1219","192");
        dBmanager.InsertPayment(paymentObject);
    }
    private static void test6(DBManager dBmanager) {
        VacationObject vacationObject=new VacationObject(-1,null,null,false,"ADULT7BABY1",true,"EL-AL","Eilat","1112201813122018",3,1);
        ArrayList<VacationObject> searchResults=dBmanager.SearchResults(vacationObject);
        for (int i = 0; i <searchResults.size() ; i++) {
            System.out.print(" "+searchResults.get(i).VacationID);
        }
        System.out.println(" expecting id3");
    }
    private static void test5(DBManager dBmanager) {
        ArrayList<String> VacationRequest= dBmanager.GetUserRequest(dBmanager.GetUserVacation("a"));//List  reqested vacations
        System.out.println("");
        for (int i = 0; i < VacationRequest.size(); i++) {
            System.out.print(" "+VacationRequest.get(i));

        }
        System.out.println("expecting id");
    }
    private static void test4(DBManager dBmanager) {
        dBmanager.ChooseVacation("id","b");
        dBmanager.UpdateVacationStatus(VacationStatus.WAITTING_FOR_APPROVAL,"id");

    }

    private static void test3(DBManager dBmanager) {
        VacationObject vacationObject=new VacationObject(2,"a","new",true,"ADULT7BABY1",true,"EL-AL","Tel-Aviv","1112201813122018",3,27);
        dBmanager.InsertVacation(vacationObject); //changed to hot vacation
        VacationObject vacationObject2=new VacationObject(3,"a","new",true,"ADULT7BABY1",true,"EL-AL","Eilat","1112201813122018",3,1);
        dBmanager.InsertVacation(vacationObject2); //changed max weight and destination
        ArrayList<String> results= dBmanager.GetUserVacation("a");
        System.out.println("");
        for (int i = 0; i < results.size(); i++) {
            System.out.print(" "+results.get(i));

        }
        System.out.println("expecting: id id2 id3");
    }
    private static void test2point2(DBManager dBmanager) {
        VacationObject vacationObject=new VacationObject(1,"a","new",true,"ADULT7BABY1",true,"EL-AL","Tel-Aviv","1112201813122018",3,27);
        dBmanager.UpdateVacation(vacationObject); //changed nothing

    }
    private static void test2point1(DBManager dBmanager) {
        VacationObject vacationObject=new VacationObject(2,"a","new",true,"ADULT7BABY1",true,"EL-AL","Tel-Aviv","1112201813122018",3,27);
        dBmanager.UpdateVacation(vacationObject); //changed id

    }

    private static void test2(DBManager dBmanager) {
        VacationObject vacationObject=new VacationObject(1,"a","new",true,"ADULT7BABY1",true,"EL-AL","Tel-Aviv","1112201813122018",3,27);
        dBmanager.UpdateVacation(vacationObject); //changed to hot vacation
        VacationObject vacationObject2=new VacationObject(1,"a","new",true,"ADULT7BABY1",true,"EL-AL","Eilat","1112201813122018",3,1);
        dBmanager.UpdateVacation(vacationObject2); //changed max weight and destination

    }

    private static void test1(DBManager dBmanager) {
        VacationObject vacationObject=new VacationObject(1,"a","new",false,"ADULT7BABY1",true,"EL-AL","Tel-Aviv","1112201813122018",3,27);
        dBmanager.InsertVacation(vacationObject);
    }
}
