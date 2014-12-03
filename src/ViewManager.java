import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

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
	private Model database;
	private JFrame frame;
	private JPanel cards;
	private CardLayout cardLayout;
	
	/**
	 * Constructs the frame and adds panels to CardLayout.
	 */
	public ViewManager(final Model model) 
	{
		this.database = model;
		frame = new JFrame("InfiniteLoops Hotel Manager");
		cards = new JPanel(cardLayout = new CardLayout());
		
		addInitialPanel();
		cards.add(new NewGuestPanel(this), "New Guest");
		cards.add(new ReturningGuestPanel(this), "Returning Guest");
		cards.add(new GuestMenuPanel(this), "Guest Menu");
		cards.add(new ManagerViewPanel(this), "Calendar");
		cards.add(new ManagerMenuPanel(this), "Manager");
		cards.add(new MakeReservationPanel(this), "Make a Reservation");
		cards.add(new ReceiptPanel(this), "Receipt");
		cards.add(new ViewCancelPanel(this), "View/Cancel a Reservation");
		
		frame.add(cards);
		frame.setSize(700, 500);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // change for load/save functionality
		frame.setVisible(true);
	}
	
	public Model getModel()
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