/**
 * COPYRIGHT 2014 InfiniteLoops. All Rights Reserved.
 * Comment goes here
 * Solves CS151 Group Project
 * @author Mike Phe, Ly Dang, Andrew Yobs
 * @version 1.00 2014/10/30
 */ 

public class NormalRoom implements Room
{
	private final double COST = 80;
	
	/**
	 * Gets the cost
	 * returns the cost
	 */
	@Override
	public double getCost() 
	{
		return COST;
	}
	
	/**
	 * Checks if room is reserved
	 * @return boolean if room is reserved or not
	 */
	@Override
	public boolean isReserved() 
	{
		return false;
	}
	
	/**
	 * Gets who is reserving the room
	 * @param account the account
	 * @return the account
	 */
	@Override
	public Account reservedBy(Account account) 
	{
		return null;
	}

}
