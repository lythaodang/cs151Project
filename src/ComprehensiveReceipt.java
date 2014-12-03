/**
 * COPYRIGHT 2014 InfiniteLoops. All Rights Reserved.
 * Comment goes here
 * Solves CS151 Group Project
 * @author Mike Phe, Ly Dang, Andrew Yobs
 * @version 1.00 2014/10/30
 */

/**
 * Implements the Receipt interface, generating a comprehensive receipt that details the
 * user's stay and amount to pay in length.
 */
public class ComprehensiveReceipt implements Receipt
{
	private Account user;
	private static double TAX = .0875;

	/**
	 * Takes in an account. 
	 * This receipt will display all reservations made by the user.
	 * @param roomCost Cost of the room per night.
	 * @param days Number of nights stayed.
	 */
	public ComprehensiveReceipt(Account user)
	{
		this.user = user;
	}

	public void setUser(Account user)
	{
		this.user = user;
	}
	
	/**
	 * Generates a formatted receipt in comprehensive form.
	 * @return Comprehensive receipt.
	 */
	public String toString()
	{
		String receipt = "Name: " + user.getFirstName() + " " + user.getLastName()
				+ "\nUser ID: " + user.getUserID();
		
		double cost = 0;
		int i = 1;
		for (Reservation r : user.getReservations())
		{
			receipt += "\n\nReservation #" + i + "\n" + r.toString();
			cost += r.getCost();
			i++;
		}
		
		receipt += "\n\nSubtotal: $" + cost + "\nTax: $" + String.format("%.2f", cost * TAX)
				+ "\nTotal: $" + String.format("%.2f", cost + (cost * TAX));
		
		return receipt;
	}
}
