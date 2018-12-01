package DBManager;

import Model.ProfileObject;
import Model.VacationObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class DBManager implements IDBManager {
    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:DB/DataBase.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /*
    creating a new user with the parameters (already checked the data)
     */
    @Override
    public void Create(ProfileObject profileObject) {
        String sql = "INSERT INTO Users(USERNAME,PASSWORD,FIRSTNAME,LASTNAME,BIRTHDATE,CITY,PICTURE) VALUES(?,?,?,?,?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, profileObject.Username);
            pstmt.setString(2, profileObject.Password);
            pstmt.setString(3, profileObject.FirstName);
            pstmt.setString(4, profileObject.LastName);
            pstmt.setString(5, profileObject.BirthDate);
            pstmt.setString(6, profileObject.City);
            pstmt.setBytes(7, profileObject.PhotoPath == null ? null : convertFileContentToBlob(profileObject.PhotoPath));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     we know only one record the sql can return (username is unique).
     if there isn't even one record it will catch the exception and return false
     else there is one record with the same username.
     note:checking only non-deleted usernames
     */
    @Override
    public boolean Read(String username) {
        String sql = "SELECT username FROM Users WHERE USERNAME=\"" + username + "\"";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
        return false;
    }

    //taken from web!!
    public static byte[] convertFileContentToBlob(String filePath) throws IOException {
        byte[] fileContent = null;
        // initialize string buffer to hold contents of file
        StringBuffer fileContentStr = new StringBuffer("");
        BufferedReader reader = null;
        try {
            // initialize buffered reader
            reader = new BufferedReader(new FileReader(filePath));
            String line = null;
            // read lines of file
            while ((line = reader.readLine()) != null) {
                //append line to string buffer
                fileContentStr.append(line).append("\n");
            }
            // convert string to byte array
            fileContent = fileContentStr.toString().trim().getBytes();
        } catch (IOException e) {
            throw new IOException("Unable to convert file to byte array. " + e.getMessage());
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return fileContent;
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
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void Delete(String currentuser, String reason, String RegistrD) {
        String sql = "DELETE FROM Users WHERE USERNAME = \"" + currentuser + "\"";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        AddReason(currentuser, reason, RegistrD);
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
            System.out.println(e.getMessage());
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
                + " CITY CHAR(20) NOT NULL, \n"
                + " PICTURE BLOB \n"
                + ");");
        createTable("CREATE TABLE IF NOT EXISTS Payments (\n"
                + "	PaymentID CHAR(8) NOT NULL UNIQUE PRIMARY KEY,\n"
                + "	VacationID_fk CHAR(8) NOT NULL,\n"
                + " UserName_fk CHAR(8) NOT NULL, \n"
                + " Useridoc CHAR(9) NOT NULL, \n"
                + "	LastName CHAR(20) NOT NULL,\n"
                + " FirstName CHAR(20) NOT NULL, \n"
                + " CardNumber CHAR(16) NOT NULL, \n"
                + " ExpirationDate CHAR(4) NOT NULL, \n" //mmyy
                + " SecurityCode CHAR(3) NOT NULL, \n"
                + " Methods CHAR(5) NOT NULL \n"//visa-paypal
                + ");");
        createTable("CREATE TABLE IF NOT EXISTS Vacations (\n"
                + "	VacationID CHAR(8) NOT NULL UNIQUE PRIMARY KEY,\n"
                + "	UserName_fk CHAR(8) NOT NULL,\n"
                + " HotVacation INTEGER NOT NULL, \n"//boolean
                + " Status CHAR(8) NOT NULL, \n"
                + "	TicketType CHAR(20) NOT NULL,\n"//adult/child/baby
                + " BuyAll INTEGER NOT NULL, \n"    //boolean
                + " FlightCompany CHAR(10) NOT NULL, \n"
                + " Destination CHAR(10) NOT NULL, \n"
                + " VacationDate CHAR(16) NOT NULL, \n"//ddmmyyyy-ddmmyyy
                + " NumberOfSuitcases INTEGER NOT NULL, \n"
                + " MaxWeight INTEGER NOT NULL, \n"
                + " VacationTyape CHAR(10) , \n"
                + " RoomType CHAR(8) , \n"
                + " RoomIncluded INTEGER , \n" //boolean
                + " RoomRank INTEGER , \n"     //1-10
                + " IsConnection INTEGER \n"   //boolean
                + ");");
        createTable("CREATE TABLE IF NOT EXISTS Requests (\n"
                + "	VacationID CHAR(8) NOT NULL UNIQUE PRIMARY KEY,\n"
                + "	SellerUserName_fk CHAR(8) NOT NULL,\n"
                + " BuyerUserName_fk CHAR(8) NOT NULL, \n"
                + " RequestDate CHAR(8) NOT NULL, \n"//ddmmyyyy
                + "	RequestHour CHAR(4) NOT NULL,\n"//hhmm
                + " Status CHAR(10) NOT NULL \n"
                + ");");

    }

    private void createTable(String sql) {
        String url = "jdbc:sqlite:DB/DataBase.db";
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void AddReason(String username, String RID, String RegistrD) {
        String sql = "INSERT INTO Reasons(username,RID,RegistrationDuration) VALUES(?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, RID);
            pstmt.setString(3, RegistrD);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
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
            return "";

        }
        return null;

    }

    @Override
    public String[] getFields(String currentUser) {
        String[] fields = new String[6];
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
                return fields;
            }
        } catch (SQLException e) {
            return null;

        }
        return null;
    }

    @Override
    public byte[] getPhoto(String username) {
        String sql = "SELECT PICTURE FROM Users WHERE USERNAME=\"" + username + "\"";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            if (rs.next()) {
                return rs.getBytes("PICTURE");
            }
        } catch (SQLException e) {
            return null;

        }

        return null;
    }

    public ArrayList<String> GetUserVacatuon(String UserName) {
        String sql = "SELECT UserName_fk FROM Vacations WHERE UserName_fk=\"" + UserName + "\"";
        ArrayList<String> result = new ArrayList<>();
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            if (rs.next()) {
                result.add(rs.getString(0));
            }
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
        return result;
    }

    public ArrayList<String> GetUserRequest(ArrayList<String> vacations) {
        ArrayList<String> request = new ArrayList();
        for (int i = 0; i < vacations.size(); i++) {
            String sql = "SELECT VacationID FROM Requests WHERE VacationID=\"" + vacations.get(i) + "\"";
            try (Connection conn = this.connect();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                // loop through the result set
                if (rs.next()) {
                    request.add(rs.getString(0));
                }
            } catch (SQLException e) {
                System.out.println(e);
                return request;
            }

        }
        return request;

    }


    public ArrayList<VacationObject> SearchResults(VacationObject vacationObject) {
        ArrayList<VacationObject> searchresults = new ArrayList();
            String sql = "SELECT * FROM Vacations WHERE Destination=\"" + vacationObject.Destination + "\" AND" +
                    "FlightCompany=\""+vacationObject.FlightCompany + "\" AND NumberOfSuitcases=\""+vacationObject.NumberOfSuitcases +
                    "\" AND MaxWeight=\""+vacationObject.MaxWeight+"\" AND TicketType =\""+vacationObject.TicketType
                    + "\" AND BuyAll =\""+vacationObject.BuyAll + "\" AND VacationDate =\""+vacationObject.VacationDate;

            try (Connection conn = this.connect();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                // loop through the result set
                if (rs.next()) {
                    //boolean is integer-buyall
                    searchresults.add(new VacationObject(null,null,null,false,rs.getString("TicketType"),rs.getBoolean("BuyAll"),rs.getString("FlightCompany"),rs.getString("Destination"),rs.getString("VacationDate"),rs.getInt("NumberOfSuitcases"),rs.getInt("MaxWeight")));
                }
            } catch (SQLException e) {
                System.out.println(e);
                return searchresults;
            }

        return searchresults;
    }

    public void ChooseVacation(String VacationID ,  String BuyerUserName_fk ){







    }

    public void UpdateVacationStatus(String status) {
        String sql = "UPDATE Vacation SET USERNAME=? , PASSWORD=?, FIRSTNAME=?,LASTNAME=?, BIRTHDATE=?,CITY=? "
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
            System.out.println(e.getMessage());
        }
    }














}





