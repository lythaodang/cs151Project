import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * COPYRIGHT (C) 2014 InfiniteLoops. All Rights Reserved. 
 * Solves CS151 Project
 * @author Mike Phe, Ly Dang, Andrew Yobs
 * @version 1.00 2014/11/03
 */

/**
 * This is the database of the hotel. It holds rooms, accounts, and current
 * user.
 */
public class Model {
	public static final GregorianCalendar TODAY = new GregorianCalendar();
	private final double TAX = .0875;
	
	private ArrayList<Room> rooms;
	private ArrayList<Account> accounts;
	private ArrayList<Reservation> reservations;
	private ArrayList<ChangeListener> listeners;
	
	// variables used for the transaction
	private Account currentUser;
	private double cost;
	private GregorianCalendar checkIn;
	private GregorianCalendar checkOut;
	private Room selectedRoom;
	private ArrayList<Reservation> transaction;

	// variables used for manager
	private GregorianCalendar selectedDate;

	/**
	 * Constructs the database. Loads the serialized accounts and reservations.
	 */
	public Model() {
		TODAY.clear(Calendar.HOUR);
		TODAY.clear(Calendar.MINUTE);
		TODAY.clear(Calendar.SECOND);
		TODAY.clear(Calendar.MILLISECOND);

		rooms = new ArrayList<>();
		accounts = new ArrayList<>();
		reservations = new ArrayList<>();
		listeners = new ArrayList<>();
		
		currentUser = null;
		cost = 0;
		checkIn = null;
		checkOut = null;
		selectedRoom = null;
		transaction = new ArrayList<>();

		selectedDate = null;

		initializeRooms();
		deserialize();
	}

	/**
	 * Adds the changelisteners
	 * @param accounts the accounts to set
	 */
	public void addChangeListener(ChangeListener listener) {
		listeners.add(listener);
	}

	/**
	 * Add an account to the database.
	 * @param account the account to add
	 */
	public void addAccount(Account account) {
		accounts.add(account);
		update();
	}
	
	/**
	 * Gets the current transaction of reservations
	 * @return the reservations
	 */
	public ArrayList<Reservation> getTransaction() {
		return transaction;
	}
	
	/**
	 * Gets the selected date
	 * @return the selected date
	 */
	public GregorianCalendar getSelectedDate() {
		return selectedDate;
	}

	/**
	 * Sets the selected date
	 * @param date the date
	 */
	public void setSelectedDate(GregorianCalendar date) {
		selectedDate = (GregorianCalendar) date.clone();
		update();
	}

	/**
	 * Gets the selected room
	 * @return the selected Room
	 */
	public Room getSelectedRoom() {
		return selectedRoom;
	}
	
	/**
	 * Sets the selected room
	 * @param room the Room
	 */
	public void setSelectedRoom(Room room) {
		selectedRoom = room;
	}

	/**
	 * Sets the cost
	 * @param currSelectedCost the cost to set
	 */
	public void setCost(int cost) {
		this.cost = cost;
		update();
	}

	/**
	 * Sets the check-in
	 * @param checkIn the checkIn to set
	 */
	public void setCheckIn(GregorianCalendar checkIn) {
		this.checkIn = checkIn;
		update();
	}

	/**
	 * Sets the check-out
	 * @param checkOut the checkOut to set
	 */
	public void setCheckOut(GregorianCalendar checkOut) {
		this.checkOut = checkOut;
		update();
	}

	/**
	 * Adds reservations
	 */
	public void addReservation() {
		Reservation newReservation = new Reservation(selectedRoom, checkIn,
				checkOut, currentUser);
		currentUser.addReservation(newReservation);
		reservations.add(newReservation);
		transaction.add(newReservation);
	}

	/**
	 * Cancels the reservation
	 * @param r the reservation
	 */
	public void cancelReservation(Reservation r) {
		reservations.remove(r);
		currentUser.cancelReservation(r);
		update();
	}

	/**
	 * The current user. It will be null if the manager is the current user.
	 * @return the currentUser
	 */
	public Account getCurrentUser() {
		return currentUser;
	}

	/**
	 * Sets the current user.
	 * @param currentUser the currentUser to set
	 */
	public void setCurrentUser(Account currentUser) {
		this.currentUser = currentUser;
		update();
	}

	/**
	 * Gets all room information for a selected day for the manager view panel.
	 * @return availability of all rooms for a selected day
	 */
	public String getRoomInformation() {
		String result = String.format("Room Information for %s\n\n",
				new SimpleDateFormat("MM/dd/yyyy").format(selectedDate.getTime()));
				
		boolean available;
		// iterate through rooms
		for (Room room : rooms) {
			result += room.toString() + "\n";
			available = true; // available until proven unavailable
			// iterate through all reservations
			for (Reservation res : reservations) {
				// if room matches reservation, then check availability
				if (res.getRoom().equals(room)) {
					if (compareDates(res.getStart(), selectedDate)
							|| compareDates(res.getEnd(), selectedDate)
							|| (res.getStart().before(selectedDate) && res.getEnd()
									.after(selectedDate))) {
						result += String.format("Unavailable\nReserved from: %s to %s"
								+ "\nReserved by: %s (ID: %s)\nSubtotal: $%.2f\nTax: $%.2f"
								+ "\nTotal: $%.2f\n\n", 
								new SimpleDateFormat("MM/dd/yyyy").format(res.getStart().getTime()),
								new SimpleDateFormat("MM/dd/yyyy").format(res.getEnd().getTime()),
								res.getUserID().getName(), res.getUserID().getUserID(), res.getCost(),
								res.getCost() * TAX, res.getCost() + (res.getCost() * TAX));
						available = false;
						break;
					}
				}
			}

			if (available) result += "Available\n\n";
		}
		return result;
	}

	/**
	 * Compares the dates
	 * @param date1 the first date
	 * @param date2 the second date
	 * @return true if the dates are equal else false
	 */
	public boolean compareDates(GregorianCalendar date1, GregorianCalendar date2) {
		return new SimpleDateFormat("MM/dd/yyyy").format(date1.getTime()).equals(
				new SimpleDateFormat("MM/dd/yyyy").format(date2.getTime()));
	}

	/**
	 * Looks for a user ID in the system.
	 * @return account of user if found, otherwise null
	 */
	public Account findUser(String userID) {
		for (Account a : accounts) {
			if (a.getUserID().equalsIgnoreCase(userID)) return a;
		}
		return null;
	}

	/**
	 * Checks to see if inputed data is valid. If it is not, this method will
	 * return an error to the make a reservation panel. If it is valid,
	 * "Available Rooms" will be displays.
	 * @return error messages during user input if any
	 */
	public String getValidityOfInput() {
		String result = "<html> Results for: <br>Check-in: ";

		// check if checkIn is inputed
		if (checkIn != null) 
			result += new SimpleDateFormat("MM/dd/yyyy").format(checkIn.getTime());
		else result += "not inputed";

		result += "<br>Check-out: ";

		// check if checkOut is inputed
		if (checkOut != null) 
			result += new SimpleDateFormat("MM/dd/yyyy").format(checkOut.getTime());
		else result += "not inputed";

		// check if cost is inputed
		result += "<br>Cost: ";
		if (cost != 0) result += String.format("$%.2f<br>", cost);
		else result += "not inputed<br>";

		// checkIn must be before checkOut and the stay must be 60 days or shorter
		if (checkIn != null && checkOut != null) {
			if (checkIn.before(checkOut)) {
				if (checkDaysBetween() > 60) result += "Error: Stay is too long.<br>";
				else {
					if (cost != 0) {
						if (getAvailRooms().isEmpty()) result += "No Available Rooms";
						else result += "Available Rooms";
					} else result += "Error: Missing input.";
				}
			} else result += "Error: Check-out date is before check-in date.<br>";
		} else result += "Error: Missing input.";

		return result + "</html>";
	}

	/**
	 * Gets the available rooms
	 * @return the list of rooms
	 */
	public ArrayList<Room> getAvailRooms() {
		ArrayList<Room> availRooms = new ArrayList<Room>();

		// checkIn, checkOut, and cost must be inputed
		// stay must be 60 or less nights && checkIn must be before checkOut
		if (checkIn != null && checkOut != null && cost != 0
				&& checkIn.before(checkOut) && checkDaysBetween() <= 60) {
			for (Room room : rooms)
				// add all rooms temporarily
				if (room.getCost() == cost) availRooms.add(room); 

			// take out the rooms that are not available
			for (Reservation res : reservations) {
				if (res.getRoom().getCost() == cost) {
					GregorianCalendar rStart = res.getStart();
					GregorianCalendar rEnd = res.getEnd();
					if (rStart.equals(checkIn) || rStart.equals(checkOut)
							|| rEnd.equals(checkIn) || rEnd.equals(checkOut)
							|| (checkIn.before(rStart) && checkOut.after(rEnd))
							|| (rStart.before(checkIn) && rEnd.after(checkIn))
							|| (rStart.before(checkOut) && rEnd.after(checkOut)))
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
	private int checkDaysBetween() {
		GregorianCalendar temp = (GregorianCalendar) checkIn.clone();
		int count = 0;
		while (!temp.equals(checkOut)) {
			temp.add(Calendar.DATE, 1);
			count++;
		}
		return count;
	}

	/**
	 * Adds 10 economic rooms and 10 luxury rooms to the hotel.
	 */
	private void initializeRooms() {
		int i;
		for (i = 1; i <= 10; i++)
			rooms.add(new LuxuryRoom(i));
		for (i = 1; i <= 10; i++)
			rooms.add(new NormalRoom(i));
	}

	/**
	 * Notifies the observers that there has been a change.
	 */
	private void update() {
		ChangeEvent event = new ChangeEvent(this);
		for (ChangeListener listener : listeners)
			listener.stateChanged(event);
	}

	/**
	 * Serializes the accounts and reservations.
	 */
	public void serialize() {
		try {
			FileOutputStream file = new FileOutputStream("accounts.ser");
			ObjectOutputStream out = new ObjectOutputStream(file);
			out.writeObject(accounts);
			out.close();
			file.close();

			file = new FileOutputStream("reservations.ser");
			out = new ObjectOutputStream(file);
			out.writeObject(reservations);
			out.close();
			file.close();
		} catch (IOException io) {
			io.printStackTrace();
			return;
		}
	}

	/**
	 * Deserializes the accounts and reservations. Only deserializes when the
	 * files exist.
	 */
	@SuppressWarnings("unchecked")
	public void deserialize() {
		File a = new File("accounts.ser");
		File r = new File("reservations.ser");
		
		if(a.exists() && !a.isDirectory()) {
			try {
			   FileInputStream f1 = new FileInputStream(a);
			   ObjectInputStream i1 = new ObjectInputStream(f1);
				accounts = (ArrayList<Account>) i1.readObject();
				i1.close();
				f1.close();
			} catch (IOException io) {
				io.printStackTrace();
				return;
			} catch (ClassNotFoundException c) {
				c.printStackTrace();
				return;
			}
		}

		if (r.exists() && !r.isDirectory()) {
			try {
				FileInputStream f2 = new FileInputStream(r);
				ObjectInputStream i2 = new ObjectInputStream(f2);
				reservations = (ArrayList<Reservation>) i2.readObject();
				i2.close();
				f2.close();
			} catch (IOException io) {
				io.printStackTrace();
				return;
			} catch (ClassNotFoundException c) {
				c.printStackTrace();
				return;
			}
		}
	}
}
