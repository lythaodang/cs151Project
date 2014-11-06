import java.util.ArrayList;
import javax.swing.event.ChangeListener;

/**
 * COPYRIGHT (C) 2014 InfiniteLoops. All Rights Reserved.
 * A room for reservation.
 * Solves CS151 Project
 * @author Mike Phe, Ly Dang, Andrew Yobs
 * @version 1.00 2014/11/03
*/

/**
 * This is the database of the hotel. It holds rooms, accounts, and
 * current user.
 */
public class DatabaseModel
{
	private Account currentUser;
	private ArrayList<Room> rooms;
	private ArrayList<Account> accounts;
	private ArrayList<ChangeListener> listeners;
	
	/**
	 * Constructs the database. Loads the 
	 */
	public DatabaseModel()
	{
		currentUser = null;
		rooms = new ArrayList<>();
		accounts = new ArrayList<>();
		listeners = new ArrayList<>();
	}

	/**
	 * The current user. It will be null if the manager is the current user.
	 * @return the currentUser
	 */
	public Account getCurrentUser()
	{
		return currentUser;
	}

	/**
	 * Sets the current user. 
	 * @param currentUser the currentUser to set
	 */
	public void setCurrentUser(Account currentUser)
	{
		this.currentUser = currentUser;
	}
	
	/**
	 * Gets the current user's first and last name.
	 * @return the current user's first and last name.
	 */
	public String getCurrentUserName()
	{
		return currentUser.getFirstName() + " " + currentUser.getLastName();
	}

	/**
	 * Returns the ArrayList of rooms available in the hotel.
	 * @return the rooms
	 */
	public ArrayList<Room> getRooms()
	{
		return rooms;
	}

	/**
	 * Adds 10 economic rooms and 10 luxury rooms to the hotel.
	 */
	public void initializeRooms()
	{
		int i;
		for (i = 1; i <= 10; i++)
			rooms.add(new LuxuryRoom(i));
		
		for (i = 1; i <= 10; i++)
			rooms.add(new NormalRoom(i));
	}
	
	/**
	 * @param accounts the accounts to set
	 */
	public void addChangeListener(ChangeListener listener)
	{
		listeners.add(listener);
	}
}
