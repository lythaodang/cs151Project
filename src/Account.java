import java.util.ArrayList;

/**
 * COPYRIGHT 2014 InfiniteLoops. All Rights Reserved.
 * Comment goes here
 * Solves CS151 Group Project
 * @author Mike Phe, Ly Dang, Andrew Yobs
 * @version 1.00 2014/10/30
 */ 
	
/**
 * A user's account. 
 */
public class Account
{
	private int userID;
	private String name;
	ArrayList<Reservation> reservations;
	
	/**
	 * Create an account with a userID and name.
	 * @param userID the user's ID
	 * @param name the user's name
	 */
	public Account(int userID, String name)
	{
		this.userID = userID;
		this.name = name;
		reservations = new ArrayList<Reservation>();
	}
	
	/**
	 * Returns the reservations held by this account.
	 * @return the reservations
	 */
	public ArrayList<Reservation> getReservations()
	{
		return reservations;
	}
	
	/**
	 * Adds a reservation.
	 * @param reservation the reservation to add
	 */
	public void addReservation(Reservation reservation)
	{
		reservations.add(reservation);
	}
	
	/**
	 * Removes a reservation.
	 * @param reservation the reservation to remove
	 */
	public void removeReservation(Reservation reservation)
	{
		reservations.remove(reservation);
	}

	/**
	 * Returns the user's ID.
	 * @return the userID
	 */
	public int getUserID()
	{
		return userID;
	}

	/**
	 * Sets the user's ID.
	 * @param userID the userID to set
	 */
	public void setUserID(int userID)
	{
		this.userID = userID;
	}

	/**
	 * Gets the user's name.
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the user's name.
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}
}
