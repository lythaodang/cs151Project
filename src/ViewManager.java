import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class ViewManager
{
	private DatabaseModel database;
	private Controller controller;
	private JFrame frame;
	private JPanel cards;
	private CardLayout cardLayout;
	
	/**
	 * Constructs the frame and adds panels to CardLayout.
	 */
	public ViewManager(final DatabaseModel database) 
	{
		this.database = database;
		database.deserialize();
		frame = new JFrame("InfiniteLoops Hotel Manager");
		cards = new JPanel(cardLayout = new CardLayout());
		
		addInitialPanel();
		cards.add(new NewGuestPanel(this), "New Guest");
		cards.add(new ReturningGuestPanel(this), "Returning Guest");
		cards.add(new GuestMenuPanel(this), "Guest Menu");
		cards.add(new ManagerViewPanel(this), "Calendar");
		cards.add(new ManagerMenuPanel(this), "Manager");
		cards.add(new MakeReservationPanel(this), "Make a Reservation");
		
		frame.add(cards);
		frame.setSize(550, 500);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // change for load/save functionality
		frame.setVisible(true);
		controller = new Controller(this.database, this);
	}
	
	public DatabaseModel getModel()
	{
		return database;
	}
	
	public void switchPanel(String panelName)
	{
		cardLayout.show(cards, panelName);
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

		guestButton.addActionListener(new 
				ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						switchPanel("Returning Guest");
					}
				});
		
		newGuestButton.addActionListener(new 
				ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						switchPanel("New Guest");
					}
				});
		
		managerButton.addActionListener(new 
				ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						switchPanel("Manager");
					}
				});
		
		panel.add(guestButton);
		panel.add(newGuestButton);
		panel.add(managerButton);
		
		cards.add(panel, "Initial");
	}
}