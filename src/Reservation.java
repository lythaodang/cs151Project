/**
 * COPYRIGHT 2014 InfiniteLoops. All Rights Reserved.
 * Comment goes here
 * Solves CS151 Group Project
 * @author Mike Phe, Ly Dang, Andrew Yobs
 * @version 1.00 2014/10/30
 */
import java.util.GregorianCalendar;

/**
 * Reservation for a room, detailing the time it starts and ends, along with the user who reserved it.
 */
public class Reservation
{
    private  GregorianCalendar start;
    private  GregorianCalendar end;
    private  Account userID;

    /**
     * Constructor for the reservation, detailing the start, end, and userid.
     * @param start Start date of the reservation.
     * @param end End date of the reservation.
     * @param userID ID number of the user who made the reservation.
     */
    public Reservation(GregorianCalendar start, GregorianCalendar end, Account userID)
    {
        this.start = start;
        this.end = end;
        this.userID = userID;
    }

    /**
     * Return the date that the reservation starts.
     * @return GregorianCalendar of the start date
     */
    public GregorianCalendar getStart()
    {
        return start;
    }

    /**
     * Return the date that the reservation ends.
     * @return GregorianCalendar of the end date.
     */
    public GregorianCalendar getEnd()
    {
        return end;
    }

    /**
     * Return the user who reserved the room.
     * @return ID of the user who made the reservation.
     */
    public Account getUserID()
    {
        return userID;
    }
}
