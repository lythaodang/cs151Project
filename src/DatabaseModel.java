import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
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
	
	// variables used for the transaction
	private int cost;
	private GregorianCalendar checkIn;
	private GregorianCalendar checkOut;
	private Room selectedRoom;
	private ArrayList<Reservation> transaction;

	/**
	 * Constructs the database. Loads the 
	 */
	public DatabaseModel()
	{
		currentUser = null;
		rooms = new ArrayList<>();
		accounts = new ArrayList<>();
		reservations = new TreeMap<Room, ArrayList<Reservation>>(
				new Comparator<Room>()
				{
					@Override
					public int compare(Room room1, Room room2)
					{
						if (room1.getCost() > room2.getCost())
							return -1;
						else if (room1.getCost() < room2.getCost())
							return 1;
						else
							return room1.getRoomNumber() - room2.getRoomNumber();
					}
				});
		listeners = new ArrayList<>();
		cost = 0;
		checkIn = null;
		checkOut = null;
		selectedRoom = null;
		transaction = new ArrayList<Reservation>();
		initializeRooms();
	}
	
	public void setSelectedRoom(Room room)
	{
		selectedRoom = room;
	}

	public Room getSelectedRoom()
	{
		return selectedRoom;
	}
	
	/**
	 * @param currSelectedCost the cost to set
	 */
	public void setCost(int cost)
	{
		this.cost = cost;
		update();
	}

	/**
	 * @param checkIn the checkIn to set
	 */
	public void setCheckIn(GregorianCalendar checkIn)
	{
		this.checkIn = checkIn;
		update();
	}

	/**
	 * @param checkOut the checkOut to set
	 */
	public void setCheckOut(GregorianCalendar checkOut)
	{
		this.checkOut = checkOut;
		update();
	}

	public void addReservation()
	{
		Reservation newReservation = new Reservation(checkIn, checkOut, currentUser);
		reservations.get(selectedRoom).add(newReservation);
		transaction.add(newReservation);
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
		String result = "<html> Results for: <br>Check-in: ";

		if (checkIn != null)
			result = result + new SimpleDateFormat("MM/dd/yyyy").
			format(checkIn.getTime());
		else
			result = result + "not inputed";

		result = result + "<br>Check-out: "; 

		if (checkOut != null)
			result = result + new SimpleDateFormat("MM/dd/yyyy").
			format(checkOut.getTime());
		else
			result = result + "not inputed";

		result = result + "<br>Cost: ";
		if (cost != 0)
			result = result + cost + "<br>";
		else
			result = result + "not inputed<br>";

		if (checkIn != null && checkOut != null)
		{
			if (checkIn.before(checkOut))
			{
				if (checkDaysBetween() > 60)
					result = result + "Error: Stay is too long.<br>";
				else
				{
					if (cost != 0)
					{
						if (getAvailRooms().isEmpty())
							result = result + "No Available Rooms";
						else
							result = result + "Available Rooms";
					}
					else
						result = result + "Error: Missing input.";
				}
			}
			else
				result = result + "Error: Check-out date is before check-in date.<br>";
		}
		else
			result = result + "Error: Missing input.";

		return result + "</html>";
	}

	public ArrayList<Room> getAvailRooms()
	{
		ArrayList<Room> availableRooms = new ArrayList<Room>();

		if (checkIn != null && checkOut != null && cost != 0 
				&& checkIn.before(checkOut) && checkDaysBetween() <= 60)
		{
			for (Room room : reservations.keySet())
			{
				if (room.getCost() == cost)
				{
					if (reservations.get(room).isEmpty())
					{
						availableRooms.add(room);
					}
					else
					{
						boolean available = true;
						for (Reservation r : reservations.get(room))
						{
							GregorianCalendar rStart = r.getStart();
							GregorianCalendar rEnd = r.getEnd();
							if (rStart.equals(checkIn) || rEnd.equals(checkOut) || 
									(rStart.before(checkIn) && rEnd.after(checkOut)))
								available = false;
						}

						if (available)
							availableRooms.add(room);
					}
				}
			}
		}

		return availableRooms;
	}

	/**
	 * Checks how many days are between the inputed check in and check out dates.
	 * @return the number of days
	 */
	private int checkDaysBetween()
	{
		GregorianCalendar temp = (GregorianCalendar)checkIn.clone();
		int count = 0;
		while (!temp.equals(checkOut))
		{
			temp.add(Calendar.DATE, 1);
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
		{
			rooms.add(new LuxuryRoom(i));
			rooms.add(new NormalRoom(i));
		}

		for (Room r : rooms)
			reservations.put(r, new ArrayList<Reservation>());
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

	/**
	 * Serializes the accounts and rooms.
	 */
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

	/**
	 * Deserializes the accounts and rooms.
	 */
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
