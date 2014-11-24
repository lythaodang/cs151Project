import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.TreeMap;

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
	private TreeMap<Room, ArrayList<Reservation>> reservations;
	private ArrayList<ChangeListener> listeners;
	private int currSelectedCost;
	private GregorianCalendar currCheckIn;
	private GregorianCalendar currCheckOut;
	
	/**
	 * Constructs the database. Loads the 
	 */
	public DatabaseModel()
	{
		currentUser = null;
		rooms = new ArrayList<>();
		accounts = new ArrayList<>();
		listeners = new ArrayList<>();
		currCheckIn = null;
		currCheckOut = null;
	}

	/**
	 * @return the currSelectedCost
	 */
	public int getCurrSelectedCost()
	{
		return currSelectedCost;
	}

	/**
	 * @param currSelectedCost the currSelectedCost to set
	 */
	public void setCurrSelectedCost(int currSelectedCost)
	{
		this.currSelectedCost = currSelectedCost;
	}

	/**
	 * @return the currCheckIn
	 */
	public GregorianCalendar getCurrCheckIn()
	{
		return currCheckIn;
	}

	/**
	 * @param currCheckIn the currCheckIn to set
	 */
	public void setCurrCheckIn(GregorianCalendar currCheckIn)
	{
		this.currCheckIn = currCheckIn;
	}

	/**
	 * @return the currCheckOut
	 */
	public GregorianCalendar getCurrCheckOut()
	{
		return currCheckOut;
	}

	/**
	 * @param currCheckOut the currCheckOut to set
	 */
	public void setCurrCheckOut(GregorianCalendar currCheckOut)
	{
		this.currCheckOut = currCheckOut;
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
	 * Returns the available rooms with the given requirements.
	 */
	public ArrayList<Room> getAvailRooms()
	{
		ArrayList<Room> availableRooms = new ArrayList<Room>();
		for (Room room : rooms)
		{
			if (room.getCost() == currSelectedCost)
			{
				if (!reservations.get(room).isEmpty())
				{
					boolean available = true;
					for (Reservation r : reservations.get(room))
					{
						GregorianCalendar rStart = r.getStart();
						GregorianCalendar rEnd = r.getEnd();
						if (rStart.equals(currCheckIn) || rEnd.equals(currCheckOut) || 
								(rStart.before(currCheckIn) && rEnd.after(currCheckOut)))
							available = false;
					}
					
					if (available)
						availableRooms.add(room);
				}
			}
		}
		return availableRooms;
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
			
			file = new FileOutputStream("rooms.ser");
			out = new ObjectOutputStream(file);
			out.writeObject(rooms);
			
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
				
				file = new FileInputStream("rooms.ser");
				input = new ObjectInputStream(file);
				rooms = (ArrayList<Room>) input.readObject();
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
