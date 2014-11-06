/**
 * COPYRIGHT 2014 InfiniteLoops. All Rights Reserved.
 * Comment goes here
 * Solves CS151 Group Project
 * @author Mike Phe, Ly Dang, Andrew Yobs
 * @version 1.00 2014/10/30
 */ 

public class LuxuryRoom implements Room
{
	private final double COST = 200;
	private int roomNumber;
	
	public LuxuryRoom(int roomNumber)
	{
		this.roomNumber = roomNumber;
	}
	
	@Override
	public int getRoomNumber()
	{
		return roomNumber;
	}
	
	@Override
	public double getCost() 
	{
		return COST;
	}
	
	@Override
	public boolean isReserved() 
	{
		return false;
	}
	
	@Override
	public Account reservedBy(Account account) 
	{
		return null;
	}

}
