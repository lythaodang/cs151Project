import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
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
		currSelectedCost = 0;
		currCheckIn = null;
		currCheckOut = null;
		initializeRooms();
	}

	/**
	 * @param currSelectedCost the currSelectedCost to set
	 */
	public void setCurrSelectedCost(int currSelectedCost)
	{
		this.currSelectedCost = currSelectedCost;
		update();
	}
	
	/**
	 * @param currCheckIn the currCheckIn to set
	 */
	public void setCurrCheckIn(GregorianCalendar currCheckIn)
	{
		this.currCheckIn = currCheckIn;
		update();
	}

	/**
	 * @param currCheckOut the currCheckOut to set
	 */
	public void setCurrCheckOut(GregorianCalendar currCheckOut)
	{
		this.currCheckOut = currCheckOut;
		update();
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
		update();
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
		update();
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
	 * Checks to see if inputed data is valid. If it is not, this method will
	 * return an error to the make a reservation panel. If it is valid,
	 * "Available Rooms" will be displays.
	 */
	public String getValidityOfInput()
	{
		String result = "";
		
		if (currCheckIn != null && currCheckOut != null && currSelectedCost != 0)
		{
			if (!currCheckIn.before(currCheckOut))
				result = "Error: Check-out date is before check-in date.";
			else if (checkDaysBetween(currCheckIn, currCheckOut) > 60)
				result = "Error: Stay is too long.";
			else if (getAvailRooms().isEmpty())
				result = "No Available Rooms";
			else
				result = "Available Rooms";
		}
		else
			result = "Error: Missing input.";
		return result;
	}
	
	public ArrayList<Room> getAvailRooms()
	{
		ArrayList<Room> availableRooms = new ArrayList<Room>();
		
		if (currCheckIn != null && currCheckOut != null && currSelectedCost != 0
				&& currCheckIn.before(currCheckOut) && 
				checkDaysBetween(currCheckIn, currCheckOut) <= 60)
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
	
	private int checkDaysBetween(GregorianCalendar start, GregorianCalendar end)
	{
		int count = 0;
		while (!start.equals(end))
		{
			start.add(Calendar.DATE, 1);
			count++;
		}
		return count;
	}
	
	/**
	 * Adds 10 economic rooms and 10 luxury rooms to the hotel.
	 */
	private void initializeRooms()
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
	
	/**
	 * Updates the listeners that there has been a change.
	 */
	private void update()
	{
		ChangeEvent event = new ChangeEvent(this);
		for (ChangeListener listener : listeners)
			listener.stateChanged(event);
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
