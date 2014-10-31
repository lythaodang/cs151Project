/**
   COPYRIGHT (C) 2014 Infinite Loops. All Rights Reserved.
   A room for reservation.
   Solves CS151 Project
   @author Mike Phe, Ly Dang, Andrew Yobs
   @version 1.00 2014/11/03
*/

public interface Room
{
	/**
	 	Gets the cost
	 	@return the cost
	 */
	double getCost();
	
	/**
	 	Checks if room is reserved
	 	@return boolean if room is reserved or not
	 */
	boolean isReserved();
	
	/**
	 	Gets who is reserving the room
	 	@param account the account
	 	@return the account
	 */
	Account reservedBy(Account account);
}
