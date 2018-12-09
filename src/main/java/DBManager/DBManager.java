package DBManager;

import Model.*;

import java.io.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;

public class DBManager implements IDBManager {
    public DBManager(IModel model) {
        this.model = model;
    }

    IModel model;
    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:DB/DataBase.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            model.showAlert(e.getMessage());
        }
        return conn;
    }

    /*
    creating a new user with the parameters (already checked the data)
     */
    @Override
    public void InsertProfile(ProfileObject profileObject) {
        String sql = "INSERT INTO Users(USERNAME,PASSWORD,FIRSTNAME,LASTNAME,BIRTHDATE,CITY) VALUES(?,?,?,?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, profileObject.Username);
            pstmt.setString(2, profileObject.Password);
            pstmt.setString(3, profileObject.FirstName);
            pstmt.setString(4, profileObject.LastName);
            pstmt.setString(5, profileObject.BirthDate);
            pstmt.setString(6, profileObject.City);
            pstmt.executeUpdate();
                 } catch (SQLException e) {
            model.showAlert(e.getMessage());
           }
           SavePhoto(profileObject.PhotoPath,profileObject.Username);
    }

    /*
     we know only one record the sql can return (username is unique).
     if there isn't even one record it will catch the exception and return false
     else there is one record with the same username.
     note:checking only non-deleted usernames
     */
    @Override
    public boolean ReadProfile(String username) {
        String sql = "SELECT username FROM Users WHERE USERNAME=\"" + username + "\"";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            model.showAlert(e.getMessage());
            return false;
        }
        return false;
    }



    @Override
    public void UpdateProfile(String user, ProfileObject profileObject) {
        String sql = "UPDATE Users SET USERNAME=? , PASSWORD=?, FIRSTNAME=?,LASTNAME=?, BIRTHDATE=?,CITY=? "
                + "WHERE USERNAME = \"" + user + "\"";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // set the corresponding param
            pstmt.setString(1, profileObject.Username);
            pstmt.setString(2, profileObject.Password);
            pstmt.setString(3, profileObject.FirstName);
            pstmt.setString(4, profileObject.LastName);
            pstmt.setString(5, profileObject.BirthDate);
            pstmt.setString(6, profileObject.City);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            model.showAlert(e.getMessage());
        }
    }

    private void SavePhoto(String photoPath,String Username) {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(new File(photoPath));
            os = new FileOutputStream(new File("../DB/pictures/"+Username));
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    public void DeleteProfile(String currentuser, String reason, String RegistrD) {
        String sql = "DELETE FROM Users WHERE USERNAME = \"" + currentuser + "\"";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            model.showAlert(e.getMessage());
        }
    }


    /**
     * should only be called one time by the programmer to create the database,
     * we keep the function so we can change it and create new databases according to
     * the tables needed
     */
    @Override
    public void CreateDB() {
        String url = "jdbc:sqlite:DB/DataBase.db";
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
            }

        } catch (SQLException e) {
            model.showAlert(e.getMessage());
        }
        createTable("CREATE TABLE IF NOT EXISTS Reasons (\n"
                + "Username CHAR(8) NOT NULL, \n"
                + "RID CHAR(2) NOT NULL,\n"
                + "RegistrationDuration CHAR(20) NOT NULL\n"
                + ");");
        createTable("CREATE TABLE IF NOT EXISTS Users (\n"
                + "	USERNAME CHAR(8) NOT NULL UNIQUE PRIMARY KEY,\n"
                + "	PASSWORD CHAR(8) NOT NULL,\n"
                + " FIRSTNAME CHAR(20) NOT NULL, \n"
                + " LASTNAME CHAR(20) NOT NULL, \n"
                + "	BIRTHDATE CHAR(8) NOT NULL,\n"   //ddmmyyyy
                + " CITY CHAR(20) NOT NULL \n"
                + ");");
        createTable("CREATE TABLE IF NOT EXISTS Payments (\n"
                + "	PaymentID INTEGER NOT NULL UNIQUE PRIMARY KEY,\n"
                + "	VacationID_fk INTEGER NOT NULL,\n"
                + " UserName_fk CHAR(8) NOT NULL, \n"
                + " Useridoc CHAR(9) NOT NULL, \n" //user real id (teodat zeout)
                + "	LastName CHAR(20) NOT NULL,\n"
                + " FirstName CHAR(20) NOT NULL, \n"
                + " CardNumber CHAR(16) NOT NULL, \n"
                + " ExpirationDate CHAR(4) NOT NULL, \n" //mmyy
                + " SecurityCode CHAR(3) NOT NULL \n"
                + ");");
        createTable("CREATE TABLE IF NOT EXISTS Vacations (\n"
                + "	VacationID INTEGER NOT NULL UNIQUE PRIMARY KEY,\n"
                + "	UserName_fk CHAR(8) NOT NULL,\n"
                + " HotVacation INTEGER NOT NULL, \n"//boolean
                + " Status CHAR(8) NOT NULL, \n"
                + "	TicketType CHAR(20) NOT NULL,\n"//adult/child/baby
                + " BuyAll INTEGER NOT NULL, \n"    //boolean
                + " FlightCompany CHAR(10) NOT NULL, \n"
                + " Origin CHAR(30) NOT NULL, \n"
                + " Destination CHAR(30) NOT NULL, \n"
                + " VacationDate CHAR(16) NOT NULL, \n"//ddmmyyyy-ddmmyyy
                + " NumberOfSuitcases INTEGER NOT NULL, \n"
                + " MaxWeight INTEGER NOT NULL, \n"
                + " Price INTEGER NOT NULL, \n"
                + " VacationType CHAR(10) , \n"
                + " RoomType CHAR(8) , \n"
                + " RoomIncluded INTEGER , \n" //boolean
                + " RoomRank INTEGER , \n"     //1-10
                + " IsConnection INTEGER \n"   //boolean
                + ");");
        createTable("CREATE TABLE IF NOT EXISTS Requests (\n"
                + "	VacationID INTEGER NOT NULL UNIQUE PRIMARY KEY,\n"
                + "	SellerUserName_fk CHAR(8) NOT NULL,\n"
                + " BuyerUserName_fk CHAR(8) NOT NULL, \n"
                + " RequestDate CHAR(8) NOT NULL, \n"//ddmmyyyy
                + "	RequestHour CHAR(4) NOT NULL,\n"//hhmm
                + " Status CHAR(30) NOT NULL \n"
                + ");");
        createTable("CREATE TABLE IF NOT EXISTS PaymentsPaypal (\n"
                + "	UserName CHAR(8) NOT NULL,\n"
                + "	Password CHAR(8) NOT NULL \n"
                + ");");
    }

    public void createTable(String sql) {
        String url = "jdbc:sqlite:DB/DataBase.db";
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
            }

        } catch (SQLException e) {
            model.showAlert(e.getMessage());
        }
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            model.showAlert(e.getMessage());
        }
    }

   public void AddReason(String username, String RID, String RegistrD) {
        String sql = "INSERT INTO Reasons(username,RID,RegistrationDuration) VALUES(?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, RID);
            pstmt.setString(3, RegistrD);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            model.showAlert(e.getMessage());
        }


    }

    /*
     if the user does not exist the dbmanager wont get here so we know
     the user exists, therefore the password can be found and returned.
     If by some reason there is an expection we will return "". it should not
     get to return null in no case
     */
    public String getPassword(String username) {
        String sql = "SELECT PASSWORD FROM Users WHERE USERNAME=\"" + username + "\"";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            if (rs.next()) {
                return rs.getString("PASSWORD");
            }
        } catch (SQLException e) {
            model.showAlert(e.getMessage());
            return "";

        }
        return null;

    }

    @Override
    public String[] getFields(String currentUser) {
        String[] fields = new String[7];
        String sql = "SELECT * FROM Users WHERE USERNAME=\"" + currentUser + "\"";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            int i = 0;
            // loop through the result set
            if (rs.next()) {
                for (int j = 0; j < 6; j++) {
                    fields[j] = rs.getString(j + 1); // j or j+1 ??
                }
                fields[6]="DB/pictures/"+fields[0];
                return fields;
            }
        } catch (SQLException e) {
            model.showAlert(e.getMessage());
            return null;

        }
        return null;
    }


    public ArrayList<String> GetUserVacation(String UserName) {
        String sql = "SELECT VacationID FROM Vacations WHERE UserName_fk=\"" + UserName + "\"";
        ArrayList<String> result = new ArrayList<>();
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {
                result.add(rs.getString(1));
            }
        } catch (SQLException e) {
            model.showAlert(e.getMessage());
            return null;
        }
        return result;
    }

    public ArrayList<String> GetUserRequest(ArrayList<String> vacations) {
        ArrayList<String> request = new ArrayList();
        for (int i = 0; i < vacations.size(); i++) {
            String sql = "SELECT VacationID FROM Requests WHERE VacationID=\"" + vacations.get(i) + "\" AND Status=\"WAITING_FOR_SELLER_RESPONSE\"";
            try (Connection conn = this.connect();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                // loop through the result set
                while (rs.next()) {
                    request.add(rs.getString(1));
                }
            } catch (SQLException e) {
                model.showAlert(e.getMessage());
                return request;
            }

        }
        return request;

    }


    public ArrayList<VacationObject> SearchResults(VacationObject vacationObject) {
        ArrayList<VacationObject> searchresults = new ArrayList();
            String sql = "SELECT * FROM Vacations WHERE Destination=\"" + vacationObject.Destination + "\" AND " +
                    " VacationDate =\""+vacationObject.VacationDate
                    + "\" AND Origin =\""+vacationObject.Origin + "\" AND Status=\"FOR_SALE\"";

            try (Connection conn = this.connect();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                // loop through the result set
                while (rs.next()) {
                    //boolean is integer-buyall
                    searchresults.add(new VacationObject(rs.getInt("VacationID"),rs.getString("UserName_fk"),rs.getString("Status"),rs.getBoolean("HotVacation"),rs.getString("TicketType"),rs.getBoolean("BuyAll"),rs.getString("FlightCompany"),rs.getString("Origin"),rs.getString("Destination"),rs.getString("VacationDate"),rs.getInt("NumberOfSuitcases"),rs.getInt("MaxWeight"),rs.getInt("Price")));
                }
            } catch (SQLException e) {
                model.showAlert(e.getMessage());
                return searchresults;
            }

        return searchresults;
    }


    public void InsertNewRequest(String VacationID , String BuyerUserName ){
        String sql = "INSERT INTO Requests(VacationID,SellerUserName_fk,BuyerUserName_fk,RequestDate,RequestHour,Status) VALUES(?,?,?,?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1,VacationID);
            pstmt.setString(2, GetSeller(VacationID));
            pstmt.setString(3, BuyerUserName);
            pstmt.setString(4, LocalDateTime.now().toString());
            pstmt.setString(5, ZonedDateTime.now().toString());
            pstmt.setString(6, RequestStatus.WAITING_FOR_SELLER_RESPONSE.toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            model.showAlert(e.getMessage());
        }
    }

    public String GetSeller(String vacationID) {
        String sql = "SELECT UserName_fk FROM Vacations WHERE VacationID=\"" + vacationID + "\"";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            model.showAlert(e.getMessage());
            return "";
        }
        return "";
    }

    public void UpdateVacationStatus(VacationStatus status, String VacationID) {
        String sql = "UPDATE Vacations SET Status=? "
                + "WHERE VacationID = \"" + VacationID + "\"";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // set the corresponding param
            pstmt.setString(1, status.toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            model.showAlert(e.getMessage());
        }

    }

    public boolean InsertPayment(PaymentObject paymentObject){
        String sql = "INSERT INTO Payments(PaymentID,VacationID_fk,UserName_fk,Useridoc,LastName,FirstName,CardNumber,ExpirationDate,SecurityCode) VALUES(?,?,?,?,?,?,?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1,paymentObject.PaymentID);
            pstmt.setString(2, paymentObject.VacationID_fk);
            pstmt.setString(3, paymentObject.UserName_fk);
            pstmt.setString(4, paymentObject.Useridoc);
            pstmt.setString(5, paymentObject.LastName);
            pstmt.setString(6, paymentObject.FirstName);
            pstmt.setString(7, paymentObject.CardNumber);
            pstmt.setString(8, paymentObject.ExpirationDate);
            pstmt.setString(9, paymentObject.SecurityCode);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            model.showAlert(e.getMessage());
            return false;
        }
        return true;
    }


    public boolean InsertVacation(VacationObject vacationObject) {
        String sql = "INSERT INTO Vacations(VacationID,UserName_fk,HotVacation,Status,TicketType,BuyAll,FlightCompany,Origin,Destination,VacationDate,NumberOfSuitcases,MaxWeight,Price,VacationType,RoomType,RoomIncluded,RoomRank,IsConnection) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, vacationObject.VacationID);
            pstmt.setString(2, vacationObject.UserName_fk);
            pstmt.setBoolean(3,vacationObject.HotVacation);
            pstmt.setString(4, vacationObject.Status);
            pstmt.setString(5, vacationObject.TicketType);
            pstmt.setBoolean(6, vacationObject.BuyAll);
            pstmt.setString(7, vacationObject.FlightCompany);
            pstmt.setString(8, vacationObject.Origin);
            pstmt.setString(9, vacationObject.Destination);
            pstmt.setString(10, vacationObject.VacationDate);
            pstmt.setInt(11, vacationObject.NumberOfSuitcases);
            pstmt.setInt(12, vacationObject.MaxWeight);
            pstmt.setInt(13, vacationObject.Price);
            pstmt.setString(14, null);
            pstmt.setString(15, null);
            pstmt.setString(16,null);
            pstmt.setString(17, null);
            pstmt.setString(18, null);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            model.showAlert(e.getMessage());
            return false;
        }
    return true;
    }

    public boolean UpdateVacation(VacationObject vacationObject) {
        String sql = "UPDATE Vacations SET VacationID=? , UserName_fk=?, HotVacation=?,Status=?, TicketType=?,BuyAll=?,FlightCompany=?,Origin=?,Destination=?,VacationDate=?,NumberOfSuitcases=?,MaxWeight=?,Price=? "
                + "WHERE VacationID = \"" + vacationObject.VacationID + "\"";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // set the corresponding param
            pstmt.setInt(1, vacationObject.VacationID);
            pstmt.setString(2, vacationObject.UserName_fk);
            pstmt.setBoolean(3,vacationObject.HotVacation);
            pstmt.setString(4, vacationObject.Status);
            pstmt.setString(5, vacationObject.TicketType);
            pstmt.setBoolean(6, vacationObject.BuyAll);
            pstmt.setString(7, vacationObject.FlightCompany);
            pstmt.setString(8, vacationObject.Origin);
            pstmt.setString(9, vacationObject.Destination);
            pstmt.setString(10, vacationObject.VacationDate);
            pstmt.setInt(11, vacationObject.NumberOfSuitcases);
            pstmt.setInt(12, vacationObject.MaxWeight);
            pstmt.setInt(13, vacationObject.Price);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            model.showAlert(e.getMessage());
            return false;
        }
        return true;
    }

    public void DeleteVacation(String VacationID) {
        String sql = "DELETE FROM Vacations WHERE VacationID = \"" + VacationID + "\"";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            model.showAlert(e.getMessage());
        }

    }

    @Override
    public VacationObject GetVacation(String vacationID) {
        String sql = "SELECT * FROM Vacations WHERE VacationID=\"" + vacationID + "\"";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            if (rs.next()) {
              return(new VacationObject(rs.getInt("VacationID"),rs.getString("UserName_fk"),rs.getString("Status"),rs.getBoolean("HotVacation"),rs.getString("TicketType"),rs.getBoolean("BuyAll"),rs.getString("FlightCompany"),rs.getString("Origin"),rs.getString("Destination"),rs.getString("VacationDate"),rs.getInt("NumberOfSuitcases"),rs.getInt("MaxWeight"),rs.getInt("Price")));
            }
        } catch (SQLException e) {
            model.showAlert(e.getMessage());
            return null;
        }
        return null;
    }

    @Override
    public void InsertPaymentPaypal(String[] paypal) {
        String sql = "INSERT INTO PaymentsPaypal(UserName,Password) VALUES(?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1,paypal[0]);
            pstmt.setString(2, paypal[1]);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            model.showAlert(e.getMessage());
        }

    }

    @Override
    public void UpdateRequestStatus(RequestStatus requestStatus, String vacationID) {
        String sql = "UPDATE Requests SET Status=? "
                + "WHERE VacationID = \"" + vacationID + "\"";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // set the corresponding param
            pstmt.setString(1, requestStatus.toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            model.showAlert(e.getMessage());

        }
    }

    @Override
    public ArrayList<String> GetNewPayments(String currUser) {
        ArrayList<String> payments = new ArrayList();
            String sql = "SELECT VacationID FROM Requests WHERE BuyerUserName_fk=\"" + currUser + "\" AND Status=\"APPROVED\"";
            try (Connection conn = this.connect();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                // loop through the result set
                while (rs.next()) {
                    payments.add(rs.getString(1));
                }
            } catch (SQLException e) {
                model.showAlert(e.getMessage());
                return payments;
            }


        return payments;
    }

    @Override
    public int[] GetMaxId() {
        int [] maxid=new int[2];
        String sql = "SELECT MAX(DISTINCT VacationID) FROM Vacations";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            // loop through the result set
            if(rs.next()) {
                maxid[0]=(rs.getInt(1));
            }
        } catch (SQLException e) {
            model.showAlert(e.getMessage());

        }
         sql = "SELECT MAX(DISTINCT PaymentID) FROM Payments";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            // loop through the result set
            if(rs.next()) {
                maxid[1]=(rs.getInt(1));
            }
        } catch (SQLException e) {
            model.showAlert(e.getMessage());

        }
  return maxid;
    }

    @Override
    public ArrayList<ArrayList<String>> GetRequestTable(String currentUser) {
        ArrayList<ArrayList<String>> request = new ArrayList();
        String sql = "SELECT VacationID,Status FROM Requests WHERE BuyerUserName_fk=\"" + currentUser + "\"";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            // loop through the result set
            while (rs.next()) {
                ArrayList<String> currres=new ArrayList<>();
                currres.add(rs.getString(1));
                currres.add(rs.getString(2));
                request.add(currres);
            }

        } catch (SQLException e) {
            model.showAlert(e.getMessage());
            return request;
        }


        return request;
    }

    @Override
    public void DeleteRequest(String requestId) {
        String sql = "DELETE FROM Requests WHERE VacationID = \"" + requestId + "\"";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            model.showAlert(e.getMessage());
        }
    }


}





