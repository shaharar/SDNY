package Model;

public class ProfileObject {
    public String Username;
    public String Password;
    public String FirstName;
    public String LastName;
    public String BirthDate;
    public String City;
    public String PhotoPath;

    public ProfileObject(String username, String password, String firstName, String lastName, String birthDate, String city, String photoPath) {
        Username = username;
        Password = password;
        FirstName = firstName;
        LastName = lastName;
        BirthDate = birthDate;
        City = city;
        PhotoPath = photoPath;
    }
    public ProfileObject(String[] fields) {
        Username = fields[0];
        Password = fields[1];
        FirstName = fields[2];
        LastName = fields[3];
        BirthDate = fields[4];
        City = fields[5];
        PhotoPath = fields[6];
    }
}
