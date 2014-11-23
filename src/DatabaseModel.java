import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.event.ChangeEvent;
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
		
		ChangeEvent event = new ChangeEvent(this);
		for (ChangeListener listener : listeners)
			listener.stateChanged(event);
	}
	
	/**
	 * Gets the current user's first and last name.
	 * @return the current user's first and last name.
	 */
	public String getCurrentUserName()
	{
		if (currentUser != null)
			return currentUser.getFirstName().toUpperCase() + " " 
			+ currentUser.getLastName().toUpperCase();
		else
			return "";
	}
	
	/**
	 * Looks for a user ID in the system.
	 * @return account of user if found, otherwise null
	 */
	public Account findUser(String userID)
	{
		for (Account a : accounts)
		{
			if (a.getUserID().equals(userID))
				return a;
		}
		return null;
	}

	/**
	 * Add an account to the database.
	 * @param account the account to add
	 */
	public void addAccount(Account account)
	{
		accounts.add(account);
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
	
	public void serialize() 
	{
		try
		{
			FileOutputStream file = new FileOutputStream("accounts.ser");
			ObjectOutputStream out = new ObjectOutputStream(file);
			out.writeObject(accounts);
			out.close();
			file.close();
		}
		catch(IOException io)
	    {
	        io.printStackTrace();
	        return;
	    }
	}
	
	@SuppressWarnings("unchecked")
	public void deserialize()
	{
		try
	        {
				FileInputStream file = new FileInputStream("accounts.ser");
				ObjectInputStream input = new ObjectInputStream(file);
				accounts = (ArrayList<Account>) input.readObject();
				input.close();
				file.close();
			}
			catch(IOException io)
			{
				io.printStackTrace();
				return;
	        }
			catch(ClassNotFoundException c)
			{
	            c.printStackTrace();
	            return;
	        }
	}
}
