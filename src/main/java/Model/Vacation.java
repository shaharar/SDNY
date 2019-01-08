package Model;

public class Vacation {

    public int VacationID;
    public String UserName_fk;
    public String Status;
    public boolean HotVacation;
    public String TicketType;
    public boolean BuyAll;
    public String FlightCompany;
    public String Origin;
    public String Destination;
    public String VacationDate;
    public int NumberOfSuitcases;
    public int MaxWeight;
    public int Price;

    public Vacation(int vacationID, String userName_fk, String status, boolean hotVacation, String ticketType, boolean buyAll, String flightCompany, String origin, String destination, String vacationDate, int numberOfSuitcases, int maxWeight, int price) {
        VacationID = vacationID;
        UserName_fk = userName_fk;
        Status = status;
        HotVacation = hotVacation;
        TicketType = ticketType;
        BuyAll = buyAll;
        FlightCompany = flightCompany;
        Origin = origin;
        Destination = destination;
        VacationDate = vacationDate;
        NumberOfSuitcases = numberOfSuitcases;
        MaxWeight = maxWeight;
        Price = price;
    }
}
