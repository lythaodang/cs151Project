import java.io.Serializable;
import java.util.ArrayList;

/**
 * COPYRIGHT 2014 InfiniteLoops. All Rights Reserved. 
 * Hotel Management
 * CS151 Group Project
 * @author Mike Phe, Ly Dang, Andrew Yobs
 * @version 1.00 2014/10/30
 */

/**
 * A user's account.
 */
@SuppressWarnings("serial")
public class Account implements Serializable {
	final private String userID; // cannot be changed once account is created
	private String name;
	private ArrayList<Reservation> reservations;

	/**
	 * Create an account with a userID and name.
	 * @param userID the user's ID
	 * @param name the user's name
	 */
	public Account(String name, String userID) {
		this.userID = userID;
		this.name = name;
		reservations = new ArrayList<Reservation>();
	}

	/**
	 * Returns the user's ID.
	 * @return the userID
	 */
	public String getUserID() {
		return userID;
	}

	/**
	 * Gets the user's name.
	 * @return the name
	 */
	public String getName() {
		return name.toUpperCase();
	}

	/**
	 * Sets the user's name.
	 * @param name the name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the reservations.
	 * @return the reservations
	 */
	public ArrayList<Reservation> getReservations() {
		return reservations;
	}

	/**
	 * Adds the reservation.
	 * @param r the reservation
	 */
	public void addReservation(Reservation r) {
		reservations.add(r);
	}

	/**
	 * Cancels the reservation.
	 * @param r the reservation
	 */
	public void cancelReservation(Reservation r) {
		reservations.remove(r);
	}

	@Override
	public int hashCode() {
		return userID.hashCode(); // user ID is the hash code
	}

	@Override
	public boolean equals(Object obj) {
		if (this.hashCode() == obj.hashCode()) return true; 
		else return false;
	}
}
