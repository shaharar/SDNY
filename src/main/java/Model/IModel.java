package Model;

public interface IModel {



    boolean Create (ProfileObject profileObject);
    boolean Read (String username);
    boolean Update (ProfileObject profileObject);
    void  Delete(String registrationDuration, Reason reason);
    boolean Login(String username, String password);
    void Logout();
    void CreateDB();

    String[] getFields(String username);

    byte[] getPhoto(String user);
}
