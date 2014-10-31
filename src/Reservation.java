import java.util.GregorianCalendar;

/**
 * Created by Amped on 10/30/2014.
 */
public class Reservation
{
    private  GregorianCalendar start;
    private  GregorianCalendar end;
    private  int userID;

    public Reservation(GregorianCalendar start, GregorianCalendar end, int userID)
    {
        this.start = start;
        this.end = end;
        this.userID = userID;
    }

    public GregorianCalendar getStart()
    {
        return start;
    }

    public GregorianCalendar getEnd()
    {
        return end;
    }

    public int getUserID()
    {
        return userID;
    }
}
