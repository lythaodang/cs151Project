import java.io.Serializable;
import java.util.ArrayList;

/**
 * COPYRIGHT 2014 InfiniteLoops. All Rights Reserved.
 * Comment goes here
 * Solves CS151 Group Project
 * @author Mike Phe, Ly Dang, Andrew Yobs
 * @version 1.00 2014/10/30
 */ 
	
/**
 * A user's account. 
 */
public class Account implements Serializable
{
	private String userID;
	private String firstName;
	private String lastName;
	
	/**
	 * Create an account with a userID and name.
	 * @param userID the user's ID
	 * @param name the user's name
	 */
	public Account(String firstName, String lastName, String userID)
	{
		this.userID = userID;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	/**
	 * Returns the user's ID.
	 * @return the userID
	 */
	public String getUserID()
	{
		return userID;
	}

	/**
	 * Sets the user's ID.
	 * @param userID the userID to set
	 */
	public void setUserID(String userID)
	{
		this.userID = userID;
	}

	/**
	 * Gets the user's first name.
	 * @return the name
	 */
	public String getFirstName()
	{
		return firstName;
	}

	/**
	 * Sets the user's first name.
	 * @param name the name to set
	 */
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	
	/**
	 * Gets the user's last name.
	 * @return the name
	 */
	public String getLastName()
	{
		return lastName;
	}

	/**
	 * Sets the user's last name.
	 * @param name the name to set
	 */
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
}
