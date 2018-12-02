package Model;

public class PaymentObject {

public 	String PaymentID;
    public 	String VacationID_fk ;
    public 	String  UserName_fk ;
    public 	String Useridoc ;
    public 	String LastName;
    public 	String FirstName;
    public 	String CardNumber;
    public 	String ExpirationDate;
    public 	String SecurityCode;
    public 	String Methods;

    public PaymentObject(String paymentID, String vacationID_fk, String userName_fk, String useridoc, String lastName, String firstName, String cardNumber, String expirationDate, String securityCode, String methods) {
        PaymentID = paymentID;
        VacationID_fk = vacationID_fk;
        UserName_fk = userName_fk;
        Useridoc = useridoc;
        LastName = lastName;
        FirstName = firstName;
        CardNumber = cardNumber;
        ExpirationDate = expirationDate;
        SecurityCode = securityCode;
        Methods = methods;
    }
}
