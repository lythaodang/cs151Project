import java.io.Serializable;
import java.util.GregorianCalendar;

/**
 * COPYRIGHT 2014 InfiniteLoops. All Rights Reserved.
 * Comment goes here
 * Solves CS151 Group Project
 * @author Mike Phe, Ly Dang, Andrew Yobs
 * @version 1.00 2014/10/30
 */ 

public class NormalRoom implements Room, Serializable
{
	private final double COST = 80;
	private int roomNumber;
	
	public NormalRoom(int roomNumber)
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
	public String toString()
	{
		return this.getClass().getSimpleName() + " #" + roomNumber;
	}
	
	@Override
	public int hashCode()
	{
		return this.toString().hashCode();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this.hashCode() == obj.hashCode())
			return true;
		
		return false;
	}
}
