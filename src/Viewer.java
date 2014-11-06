import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
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
 * The view. Contains the frame and all panels. 
 * Viewer and controller of MVC pattern.
 */
public class Viewer
{
	private DatabaseModel database;
	private JFrame frame;
	private final JPanel cards;
	private CardLayout cardLayout;
	
	/**
	 * Constructs the frame and adds panels to CardLayout.
	 */
	public Viewer(final DatabaseModel database)
	{
		this.database = database;
		frame = new JFrame("InfiniteLoops Hotel Manager");
		cards = new JPanel(cardLayout = new CardLayout());
		
		addInitialPanel();
		addNewGuestPanel();
		addReturningGuestPanel();
		addManagerPanel();
		
		frame.add(cards);
		frame.setSize(400, 400);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // change for load/save functionality
		frame.setVisible(true);
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
				+ "<br>The user ID should be at least 6 characters and at most 12 "
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
						
						if (firstTextField.getText().length() < 1 
								|| lastTextField.getText().length() < 1
								|| userIDTextField.getText().length() < 1)
						{
							message = "One or more fields not entered. "
									+ "Please try again.";
							JOptionPane.showMessageDialog(new JFrame(), message, 
									"Error", JOptionPane.ERROR_MESSAGE);
						}
						else if (userIDTextField.getText().length() < 6)
						{
							message = "Error: User ID is less than 6 "
									+ "characters. Please try again.";
							JOptionPane.showMessageDialog(new JFrame(), message, 
									"Error", JOptionPane.ERROR_MESSAGE);
						}
						else if (userIDTextField.getText().length() > 12)
						{
							message = "Error: User ID is more than 12 "
									+ "characters. Please try again.";
							JOptionPane.showMessageDialog(new JFrame(), message, 
									"Error", JOptionPane.ERROR_MESSAGE);
						}
						else
						{
							Account newAccount = new Account(userIDTextField.getText(), 
									firstTextField.getText(), lastTextField.getText());
							// add account to Model
							// set account as current user in Model
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
					public void actionPerformed(ActionEvent arg0)
					{
						String message = "";
						if (userIDTextField.getText().length() < 6 ||
								userIDTextField.getText().length() > 12)
						{
							message = "Error: Entered user ID is invalid.";
							JOptionPane.showMessageDialog(new JFrame(), message, 
									"Error", JOptionPane.ERROR_MESSAGE);
						}
						else if (message != "")
						{
							message = "Error: User ID does not exist in the system.";
							JOptionPane.showMessageDialog(new JFrame(), message, 
									"Error", JOptionPane.ERROR_MESSAGE);
						}
						else
						{
							// set current user
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
		JPanel panel = new JPanel(new GridLayout(3, 1));
		
		final JLabel name = new JLabel();
		
		ChangeListener listener = new
				ChangeListener()
				{
					@Override
					public void stateChanged(ChangeEvent event)
					{
						name.setText("User: " + database.getCurrentUserName());
					}
				};
				
		database.addChangeListener(listener);
		
		JButton make = new JButton("Make a Reservation");
		make.addActionListener(new SwitchListener("Make Reservation"));
		
		JButton viewCancel = new JButton("View/Cancel a Reservation");
		viewCancel.addActionListener(new SwitchListener("View Cancel"));
		
		panel.add(name);
		panel.add(make);
		panel.add(viewCancel);
		
		cards.add(cards, "Guest Menu");
	}
	
	public void addManagerPanel()
	{
		JPanel panel = new JPanel();
		
		// two drop down one for month one for year
		// a calendar (probably buttons for each day? grid layout?)
		// room info
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new SwitchListener("Initial"));
		panel.add(backButton);
		cards.add(panel, "Manager");
	}
}
