package Controller;

import Model.Reason;
import Model.UserFields;
import javafx.stage.Stage;

import java.util.ArrayList;

public interface IController {

    boolean Login(String username, String password);
    boolean SignUp(String [] fields);
    boolean Search(String username);
    String[] getFields(String username);
    boolean Update(String [] fields);
    void Delete(String registrationDuration, Reason reason);
    void openwindow(String fxmlfile, String usernametosearch);
    void LogOut();
    byte[] getphoto( String username);
    boolean SearchVacation(boolean buyAll,String[] TextFields);

    void showalert(String alert);

    ArrayList<String> GetNewRequests();
}
