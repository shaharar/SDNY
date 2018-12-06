package Model;

import java.util.ArrayList;

public interface IModel {



    boolean Create (ProfileObject profileObject);
    boolean Read (String username);
    boolean Update (ProfileObject profileObject);
    void  Delete(String registrationDuration, Reason reason);
    boolean Login(String username, String password);
    void Logout();
    void CreateDB();
    ArrayList<VacationObject> GetSearchResult(VacationObject vacationObject);
    ArrayList<String> GetNewRequests();
    boolean InsertVacation(VacationObject vacationObject);
    void DeleteVacation(String VacationID);
    String[] getFields(String username);
    byte[] getPhoto(String user);
}
