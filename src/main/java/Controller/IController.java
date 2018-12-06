package Controller;

import Model.Reason;
import javafx.scene.control.Label;

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
    byte[] getphoto( String username);
    String[][] SearchVacation(boolean buyAll, String[] TextFields);

    void showalert(String alert);

    ArrayList<String> GetNewRequests();

    boolean CreateVacation(String[] strings, boolean selected);

    void DeleteVacation(String vacationId);

    void UpdateVacation(String[] strings, boolean selected);

    boolean ChooseVacation(String vacationId);
}
