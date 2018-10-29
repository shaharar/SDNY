package DBManager;


import Model.ProfileObject;
import Model.UserFields;

public interface IDBManager {
    void Create (ProfileObject profileObject);
    boolean Read (String username);
    void Update (String username, ProfileObject profileObject);
    void  Delete(String currentuser, String reason, String RegistrD);
    void CreateDB();
    String getPassword(String username);
    String[] getFields(String currentUser);

    byte[] getPhoto(String currentUser);
}
