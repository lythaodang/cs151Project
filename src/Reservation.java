/**
 * COPYRIGHT 2014 InfiniteLoops. All Rights Reserved.
 * Comment goes here
 * Solves CS151 Group Project
 * @author Mike Phe, Ly Dang, Andrew Yobs
 * @version 1.00 2014/10/30
 */
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Reservation for a room, detailing the time it starts and ends, along with the user who reserved it.
 */
public class Reservation implements Serializable
{
	private Room room;
	private GregorianCalendar start;
	private GregorianCalendar end;
	private Account userID;
	private int days;

	/**
	 * Constructor for the reservation, detailing the start, end, and userid.
	 * @param start Start date of the reservation.
	 * @param end End date of the reservation.
	 * @param userID ID number of the user who made the reservation.
	 */
	public Reservation(Room room, GregorianCalendar start, GregorianCalendar end, Account userID)
	{
		this.room = room;
		this.start = start;
		this.end = end;
		this.userID = userID;
		days = calculateDays();
	}

	private int calculateDays()
	{
		GregorianCalendar temp = (GregorianCalendar)start.clone();
		int count = 0;
		while (!temp.equals(end))
		{
			temp.add(Calendar.DATE, 1);
			count++;
		}
		return count;
	}
	
	public Room getRoom()
	{
		return room;
	}
	
	public double getCost()
	{
		return days * room.getCost();
	}

	public int getDays()
	{
		return days;
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

	public String toString()
	{
		return room.toString() + " " + " \n" + 
				new SimpleDateFormat("MM/dd/yyyy").format(start.getTime()) + " to " +
				new SimpleDateFormat("MM/dd/yyyy").format(end.getTime()) + " \nCost: "
				+ days + " day(s) X $" + room.getCost() + " a night = $" + days * room.getCost();
	}
	
	@Override
	public int hashCode()
	{
		return this.toString().hashCode();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this.hashCode() == obj.hashCode())
			return true;
		
		return false;
	}
}
