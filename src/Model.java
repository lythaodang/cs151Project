import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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
public class Model 
{
	private Account currentUser;
	private ArrayList<Room> rooms;
	private ArrayList<Account> accounts;
	private ArrayList<Reservation> reservations;
	private ArrayList<ChangeListener> listeners;
	public static final GregorianCalendar TODAY = new GregorianCalendar();
	
	// variables used for the transaction
	private double cost;
	private GregorianCalendar checkIn;
	private GregorianCalendar checkOut;
	private Room selectedRoom;
	private ArrayList<Reservation> transaction;
	
	// variables used for manager
	private GregorianCalendar selectedDate;
	
	/**
	 * Constructs the database. Loads the 
	 */
	public Model()
	{
		currentUser = null;
		rooms = new ArrayList<>();
		accounts = new ArrayList<>();
		reservations = new ArrayList<>();
		listeners = new ArrayList<>();
		TODAY.clear(Calendar.HOUR);
		TODAY.clear(Calendar.MINUTE);
		TODAY.clear(Calendar.SECOND);
		TODAY.clear(Calendar.MILLISECOND);
		
		cost = 0;
		checkIn = null;
		checkOut = null;
		selectedRoom = null;
		transaction = new ArrayList<>();
		
		selectedDate = null;
		
		initializeRooms();
	}
	
	public ArrayList<Reservation> getTransaction()
	{
		return transaction;
	}
	
	/**
	 * Gets all room information for a selected day for the manager view panel.
	 * @return availability of all rooms for a selected day
	 */
	public String getRoomInformation()
	{
		String result = "Room Information for " + 
				new SimpleDateFormat("MM/dd/yyyy").format(selectedDate.getTime())
				+ "\n\n";
		boolean available;
		
		for (Room room : rooms)
		{
			result += room.toString() + "\n";
			available = true;
			for (Reservation res : reservations)
			{
				if (res.getRoom().equals(room))
				{
					if (res.getStart().equals(selectedDate) ||
							res.getEnd().equals(selectedDate) ||
							(res.getStart().before(selectedDate) && 
							res.getEnd().after(selectedDate)))
					{
						result += "Unavailable\nReserved from: " + 
								new SimpleDateFormat("MM/dd/yyyy").format(res.getStart().getTime()) + 
								" to " + new SimpleDateFormat("MM/dd/yyyy").format(res.getEnd().getTime()) 
								+ "\n" + "Reserved by: " + res.getUserID().getFirstName().toUpperCase() +
								" " + res.getUserID().getLastName().toUpperCase() + " (ID: " +
								res.getUserID().getUserID() + ")\n\n";
						available = false;
						break;
					}
				}
			}
			
			if (available)
				result += "Available\n\n";
		}
		
		return result;
	}
	
	public GregorianCalendar getSelectedDate()
	{
		return selectedDate;
	}
	
	public void setSelectedDate(GregorianCalendar date)
	{
		selectedDate = (GregorianCalendar) date.clone();
		update();
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
		Reservation newReservation = new Reservation(selectedRoom, checkIn, checkOut, currentUser);
		currentUser.addReservation(newReservation);
		reservations.add(newReservation);
		transaction.add(newReservation);
	}
	
	public void cancelReservation(Reservation r)
	{
		reservations.remove(r);
		currentUser.cancelReservation(r);
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
		ArrayList<Room> availRooms = new ArrayList<Room>();
		
		if (checkIn != null && checkOut != null && cost != 0 
				&& checkIn.before(checkOut) && checkDaysBetween() <= 60)
		{
			for (Room room : rooms)
				if (room.getCost() == cost)
					availRooms.add(room);
			
			for (Reservation res : reservations)
			{
				if (res.getRoom().getCost() == cost)
				{
					GregorianCalendar rStart = res.getStart();
					GregorianCalendar rEnd = res.getEnd();
					if (rStart.equals(checkIn) || rStart.equals(checkOut) ||
							rEnd.equals(checkIn) || rEnd.equals(checkOut) || 
							(rStart.before(checkIn) && rEnd.after(checkIn)) ||
							(rStart.before(checkOut) && rEnd.after(checkOut)))
						availRooms.remove(res.getRoom());
				}
			}
		}

		return availRooms;
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
			file.close();
			
			file = new FileOutputStream("reservations.ser");
			out = new ObjectOutputStream(file);
			out.writeObject(reservations);
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
			
			file = new FileInputStream("reservations.ser");
			input = new ObjectInputStream(file);
			reservations = (ArrayList<Reservation>) input.readObject();
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
