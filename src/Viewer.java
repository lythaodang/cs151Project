import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * COPYRIGHT (C) 2014 InfiniteLoops. All Rights Reserved.
 * A room for reservation.
 * Solves CS151 Project
 * @author Mike Phe, Ly Dang, Andrew Yobs
 * @version 1.00 2014/11/03
*/

/**
 * The view. Contains the frame and all panels. 
 * Viewer and controller of MVC pattern.
 */
public class Viewer 
{
	private DatabaseModel database;
	private Controller controller;
	private JFrame frame;
	private final JPanel cards;
	private CardLayout cardLayout;
	
	/**
	 * Constructs the frame and adds panels to CardLayout.
	 */
	public Viewer(final DatabaseModel database) 
	{
		this.database = database;
		database.deserialize();
		frame = new JFrame("InfiniteLoops Hotel Manager");
		cards = new JPanel(cardLayout = new CardLayout());
		
		addInitialPanel();
		addNewGuestPanel();
		addReturningGuestPanel();
		addGuestMenu();
		addManagerPanel();
		addReservationPanel();
		
		frame.add(cards);
		frame.setSize(400, 400);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // change for load/save functionality
		frame.setVisible(true);
		controller = new Controller(this.database, this);
	}
	
	/**
	 * This ActionListener simply switches from one panel to another.
	 */
	public class SwitchListener implements ActionListener
	{
		private String name;
		
		public SwitchListener(String name)
		{
			this.name = name;
		}
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			cardLayout.show(cards, name);
		}
	}
	
	/**
	 * Adds the initial panel to the CardLayout.
	 */
	private void addInitialPanel()
	{
		JPanel panel = new JPanel(new GridLayout(3, 1));

		JButton guestButton = new JButton("Returning Guest");

		JButton newGuestButton = new JButton("New Guest");

		JButton managerButton = new JButton("Manager");

		guestButton.addActionListener(new SwitchListener("Returning Guest"));
		newGuestButton.addActionListener(new SwitchListener("New Guest"));
		managerButton.addActionListener(new SwitchListener("Manager"));
		
		panel.add(guestButton);
		panel.add(newGuestButton);
		panel.add(managerButton);
		
		cards.add(panel, "Initial");
	}
	
	/**
	 * Add the new guest panel to the CardLayout.
	 */
	private void addNewGuestPanel()
	{
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;

		JLabel instructions = new JLabel("<html>Fill out the following information."
				+ "<br>Your user ID should be at least 6 characters and at most 12 "
				+ "characters. Your first and last name should not exceed 15 "
				+ "characters.</html>");
		c.insets = new Insets(10, 10, 10, 10); 
		c.gridwidth = 2;
		panel.add(instructions, c);

		JLabel firstLabel = new JLabel("First name:");
		c.weightx = 1;
		c.weighty = 1;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 1;
		panel.add(firstLabel, c);
		
		final JTextField firstTextField = new JTextField();
		c.gridx = 1;
		c.gridy = 1;
		panel.add(firstTextField, c);
		
		JLabel lastLabel = new JLabel("Last name:");
		c.gridx = 0;
		c.gridy = 2;
		panel.add(lastLabel, c);
		
		final JTextField lastTextField = new JTextField();
		c.gridx = 1;
		c.gridy = 2;
		panel.add(lastTextField, c);

		JLabel userIDLabel = new JLabel("User ID:");
		c.gridx = 0;
		c.gridy = 3;
		panel.add(userIDLabel, c);
		
		final JTextField userIDTextField = new JTextField();
		c.gridx = 1;
		c.gridy = 3;
		panel.add(userIDTextField, c);
		
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new 
				ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						firstTextField.setText("");
						lastTextField.setText("");
						userIDTextField.setText("");
						cardLayout.show(cards, "Initial");
					}
				});
		c.gridx = 0;
		c.gridy = 4;
		c.anchor = GridBagConstraints.PAGE_END;
		panel.add(backButton, c);
		
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new 
				ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0)
					{
						String message = "";
						String userID = userIDTextField.getText();
						String firstName = firstTextField.getText();
						String lastName = lastTextField.getText();
						
						if (firstName.length() < 1 || lastName.length() < 1
								|| userID.length() < 1)
						{
							message = "One or more fields not entered. "
									+ "Please try again.";
							JOptionPane.showMessageDialog(new JFrame(), message, 
									"Error", JOptionPane.ERROR_MESSAGE);
						}
						else if (firstName.length() > 15 || lastName.length() > 15)
						{
							message = "Error: First or/and last name(s) are too long.";
							JOptionPane.showMessageDialog(new JFrame(), message, 
									"Error", JOptionPane.ERROR_MESSAGE);
						}
						else if (userID.length() < 6)
						{
							message = "Error: User ID is less than 6 "
									+ "characters. Please try again.";
							JOptionPane.showMessageDialog(new JFrame(), message, 
									"Error", JOptionPane.ERROR_MESSAGE);
						}
						else if (userID.length() > 12)
						{
							message = "Error: User ID is more than 12 "
									+ "characters. Please try again.";
							JOptionPane.showMessageDialog(new JFrame(), message, 
									"Error", JOptionPane.ERROR_MESSAGE);
						}
						else if (database.findUser(userID) != null)
						{
							message = "Error: User ID taken. Please try again.";
							JOptionPane.showMessageDialog(new JFrame(), message, 
									"Error", JOptionPane.ERROR_MESSAGE);
						}
						else
						{
							Account newAccount = new Account(userIDTextField.getText(), 
									firstTextField.getText(), lastTextField.getText());
							firstTextField.setText("");
							lastTextField.setText("");
							userIDTextField.setText("");
							database.addAccount(newAccount);
							database.setCurrentUser(newAccount);
							cardLayout.show(cards, "Guest Menu");
						}
					}
				});
		c.gridx = 1;
		c.gridy = 4;
		panel.add(submitButton, c);

		cards.add(panel, "New Guest");
	}
	
	public void addReturningGuestPanel()
	{
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;

		JLabel instructions = new JLabel("<html>Please enter your user ID to "
				+ "make, cancel, or view your reservations.<br><br>If you do not "
				+ "have an account please go back and create one.</html>");
		c.insets = new Insets(25, 10, 25, 10); 
		c.gridwidth = 2;
		panel.add(instructions, c);
		
		JLabel userIDLabel = new JLabel("Enter user ID:");
		c.weightx = 1;
		c.weighty = 1;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 1;
		panel.add(userIDLabel, c);
		
		final JTextField userIDTextField = new JTextField();
		c.gridx = 1;
		c.gridy = 1;
		panel.add(userIDTextField, c);

		JButton backButton = new JButton("Back");
		backButton.addActionListener(new 
				ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						userIDTextField.setText("");
						cardLayout.show(cards, "Initial");
					}
				});
		c.gridx = 0;
		c.gridy = 2;
		panel.add(backButton, c);
		
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new 
				ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						String userID = userIDTextField.getText();
						String message = "";
						
						if (userID.length() < 6 || userID.length() > 12)
						{
							message = "Error: Entered user ID is invalid.";
							JOptionPane.showMessageDialog(new JFrame(), message, 
									"Error", JOptionPane.ERROR_MESSAGE);
						}
						else if (database.findUser(userID) == null)
						{
							message = "Error: User ID does not exist in the system.";
							JOptionPane.showMessageDialog(new JFrame(), message, 
									"Error", JOptionPane.ERROR_MESSAGE);
						}
						else
						{
							userIDTextField.setText("");
							database.setCurrentUser(database.findUser(userID));
							cardLayout.show(cards, "Guest Menu");
						}
					}
				});
		c.gridx = 1;
		c.gridy = 2;
		panel.add(submitButton, c);
		
		cards.add(panel, "Returning Guest");
	}
	
	public void addGuestMenu()
	{
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		
		final JLabel name = new JLabel();
		ChangeListener listener = new
				ChangeListener()
				{
					@Override
					public void stateChanged(ChangeEvent event)
					{
						name.setText("<html>User:<br>" + 
								database.getCurrentUserName() + "</html>");
					}
				};
		database.addChangeListener(listener);
		c.weightx = 1;
		c.weighty = 1;
		c.insets = new Insets(10, 10, 10, 10); 
		panel.add(name, c);
		
		JButton backButton = new JButton("Sign out");
		backButton.addActionListener(new 
				ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						database.setCurrentUser(null);
						cardLayout.show(cards, "Initial");
					}
				});
		c.gridx = 1;
		panel.add(backButton, c);
		
		JButton make = new JButton("Make a Reservation");
		make.addActionListener(new SwitchListener("Make Reservation"));
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 1;
		panel.add(make, c);
		
		JButton viewCancel = new JButton("View/Cancel a Reservation");
		viewCancel.addActionListener(new SwitchListener("View Cancel"));
		c.gridy = 2;
		panel.add(viewCancel, c);
		
		cards.add(panel, "Guest Menu");
	}
	
	public void addManagerPanel()
	{
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		
		JLabel name = new JLabel();
		name.setText("<html>User:<br>" + "Manager" + "</html>");
		c.weightx = 1;
		c.weighty = 1;
		c.insets = new Insets(10, 10, 10, 10); 
		panel.add(name, c);
		
		// two drop down one for month one for year
		// a calendar (probably buttons for each day? grid layout?)
		// room info
		
		JButton backButton = new JButton("Sign out");
		backButton.addActionListener(new 
				ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						cardLayout.show(cards, "Initial");
					}
				});
		c.gridx = 1;
		panel.add(backButton, c);
		
		JButton view = new JButton("View");
		view.addActionListener(new SwitchListener("View"));
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 1;
		panel.add(view, c);
		
		// not sure if this is still needed since it loads up, but I left for debate
		JButton load = new JButton("Load");
		load.addActionListener(new 
				ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						String loaded = "Accounts and reservations have been loaded.";
						database.deserialize();
						JOptionPane.showMessageDialog(new JFrame(), loaded);
					}
				});
		c.gridy = 2;
		panel.add(load, c);
		
		JButton save = new JButton("Save/Quit");
		save.addActionListener(new 
				ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						database.serialize();
						System.exit(0);
					}
				});
		c.gridy = 3;
		panel.add(save, c);
		
		cards.add(panel, "Manager");
	}
	
	public void addReservationPanel()
	{
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		
		JLabel instructions = new JLabel("<html>Please enter a check-in and "
				+ "check-out date. Then choose your room type.<br> "
				+ "Note: Dates must be in correct format (MM/DD/YYYY).</html>");
		c.insets = new Insets(10, 10, 10, 10); 
		c.gridwidth = 2;
		panel.add(instructions, c);
		
		JLabel checkInButton = new JLabel("Check-in:");
		c.weightx = 1;
		c.weighty = 1;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 1;
		panel.add(checkInButton, c);
		
		final JTextField checkInTextField = new JTextField();
		checkInTextField.setText("MM/DD/YYYY");
		checkInTextField.getDocument().addDocumentListener(
				new DocumentListener() 
				{
					@Override
					public void changedUpdate(DocumentEvent e) 
					{
						database.setCurrCheckIn(stringToDate(checkInTextField.getText()));
						if (database.getCurrCheckOut() != null)
						{
							if (database.getCurrCheckOut().before(database.getCurrCheckIn()))
								JOptionPane.showMessageDialog(new JFrame(), 
										"Check-out is before check-in", 
										"Error", JOptionPane.ERROR_MESSAGE);
							// check number of days between checkin and checkout
						}
					}
					@Override
					public void insertUpdate(DocumentEvent e)
					{
						return;
					}
					@Override
					public void removeUpdate(DocumentEvent e)
					{
						return;
					}
			  });
			  
		c.gridx = 0;
		c.gridy = 2;
		panel.add(checkInTextField, c);
		
		JLabel checkOutButton = new JLabel("Check-out:");
		c.gridx = 1;
		c.gridy = 1;
		panel.add(checkOutButton, c);
		
		final JTextField checkOutTextField = new JTextField();
		checkOutTextField.setText("MM/DD/YYYY");
		c.gridx = 1;
		c.gridy = 2;
		panel.add(checkOutTextField, c);
		checkOutTextField.getDocument().addDocumentListener(
				new DocumentListener() 
				{
					@Override
					public void changedUpdate(DocumentEvent e) 
					{
						database.setCurrCheckOut(stringToDate(checkOutTextField.getText()));
						if (database.getCurrCheckIn() != null)
						{
							if (database.getCurrCheckOut().before(database.getCurrCheckIn()))
								JOptionPane.showMessageDialog(new JFrame(), 
										"Check-out is before check-in", 
										"Error", JOptionPane.ERROR_MESSAGE);
							// check number of days between checkin and checkout
						}
					}
					@Override
					public void insertUpdate(DocumentEvent e)
					{
						return;
					}
					@Override
					public void removeUpdate(DocumentEvent e)
					{
						return;
					}
			  });
		
		JPanel roomTypePanel = new JPanel(new GridLayout(1, 3, 10, 0));
		JLabel room = new JLabel("Room type:");
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 2;
		roomTypePanel.add(room);
		
		JButton luxuryRoom = new JButton("$200");
		luxuryRoom.addActionListener(new 
				ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						
					}
				});
		roomTypePanel.add(luxuryRoom);
		
		JButton normalRoom = new JButton("$80");
		normalRoom.addActionListener(new 
				ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						
					}
				});
		roomTypePanel.add(normalRoom);
		
		panel.add(roomTypePanel, c);
		
		JPanel availableRooms = new JPanel();
		
		JLabel availableLabel = new JLabel("Available Rooms");
		// add label and buttons to panel
		
		// get rooms from database
		// if not valid show "Fix the error"
		ChangeListener listener = new
				ChangeListener()
				{
					@Override
					public void stateChanged(ChangeEvent event)
					{
						for (Room r : database.getAvailRooms())
						{
							// add a button of each room avail to panel
						}
					}
				};
				
		database.addChangeListener(listener);
		
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 2;
		c.gridheight = 2;
		panel.add(availableRooms, c);
		
		cards.add(panel, "Make Reservation");
	}
	
	/**
	 * Converts the user's input into a GregorianCalendar for use in printing
	 * the month and events.
	 * @param input the user's string input in the form of MM/DD/YYYY
	 * @return a GregorianCalendar with the user's inputed date
	 */
	public final GregorianCalendar stringToDate(String input)
	{
		// check validity
		
		GregorianCalendar temp = new GregorianCalendar
				(Integer.parseInt(input.substring(6, 10)),
						Integer.parseInt(input.substring(0, 2)) - 1, 
						Integer.parseInt(input.substring(3, 5)));

		return temp;
	}
}