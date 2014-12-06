import java.awt.CardLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * COPYRIGHT 2014 InfiniteLoops. All Rights Reserved. 
 * Hotel Management 
 * CS151 Group Project
 * @author Mike Phe, Ly Dang, Andrew Yobs
 * @version 1.00 2014/10/30
 */

/**
 * The view manager. Contains the frame and all different panels.
 */
public class ViewManager {
	private Model model;
	private JPanel cards;
	private CardLayout cardLayout;

	/**
	 * Constructs the frame and adds panels to CardLayout.
	 */
	public ViewManager(Model model) {
		this.model = model;
		JFrame frame = new JFrame("InfiniteLoops Hotel Manager");
		cards = new JPanel(cardLayout = new CardLayout());

		// add panels to the card layout
		cards.add(new InitialPanel(this), "Initial");
		cards.add(new NewGuestPanel(this), "NGuest");
		cards.add(new ReturningGuestPanel(this), "RGuest");
		cards.add(new GuestMenuPanel(this), "GMenu");
		cards.add(new ManagerViewPanel(this), "Calendar");
		cards.add(new ManagerMenuPanel(this), "MMenu");
		cards.add(new MakeReservationPanel(this), "Make Reservation");
		cards.add(new ReceiptPanel(this), "Receipt");
		cards.add(new ViewCancelPanel(this), "View/Cancel");

		frame.add(cards); // add the panel with card layout to the frame

		// below are the frame's characteristics
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	/**
	 * Gets the model of the view manager.
	 * @return the model
	 */
	public Model getModel() {
		return model;
	}

	/**
	 * Switches the current panel.
	 * @param panelName
	 */
	public void switchPanel(String panelName) {
		cardLayout.show(cards, panelName);
	}

	/**
	 * The main menu panel.
	 */
	@SuppressWarnings("serial")
	private class InitialPanel extends BasicPanel {
		/**
		 * Constructs the panel with a view manager.
		 * @param m the view manager
		 */
		public InitialPanel(ViewManager manager) {
			super(manager);

			addInstructions("<html>Welcome to InfiniteLoops Hotel Manager!<br>"
					+ "Please select a type of user.");
			c.weightx = 1;
			c.weighty = 1;
			addNavigationButton("Returning Guest", "RGuest", 0, 1);
			addNavigationButton("New Guest", "NGuest", 0, 2);
			addNavigationButton("Manager", "MMenu", 0, 3);
		}
	}
	
	/**
	 * The manager's menu panel.
	 */
	@SuppressWarnings("serial")
	private class ManagerMenuPanel extends BasicPanel {
		/**
		 * Constructs the panel with a view manager.
		 * @param m the view manager
		 */
		public ManagerMenuPanel(ViewManager m) {
			super(m);

			c.weighty = 1;
			addLabel("<html>User:<br>" + "Manager" + "</html>", 0, 0);
			addSignOutButton("Initial", 1, 0);
			c.gridwidth = 2;
			addNavigationButton("View", "Calendar", 0, 1);

			JButton save = new JButton("Save/Quit");
			save.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					model.serialize();
					System.exit(0);
				}
			});
			addComponent(save, 0, 2);
		}
	}
	
	/**
	 * A panel in which a returning user can log in.
	 */
	@SuppressWarnings("serial")
	private class ReturningGuestPanel extends BasicPanel {
		/**
		 * Constructs the panel with a view manager
		 * @param m the view
		 */
		public ReturningGuestPanel(ViewManager m) {
			super(m);
			
			c.insets = new Insets(20, 20, 20, 20);
			c.ipady = 25;
			c.gridwidth = 2;
			addInstructions("<html>Please enter your user ID to "
					+ "make, cancel, or view your reservations.<br><br>If you do not "
					+ "have an account please go back and create one.</html>");

			c.weighty = 1;
			c.gridwidth = 1;
			addLabel("Enter user ID:", 0, 1);

			final JTextField userIDTextField = new JTextField();
			addComponent(userIDTextField, 1, 1);

			addNavigationButton("Back", "Initial", 0, 2);

			JButton submitButton = new JButton("Sign in");
			submitButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String userID = userIDTextField.getText();

					if (userID.length() < 6 || userID.length() > 12) {
						JOptionPane.showMessageDialog(new JFrame(),
								"Error: Entered user ID is invalid.", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else if (model.findUser(userID) == null) {
						JOptionPane.showMessageDialog(new JFrame(), 
								"Error: User ID does not exist in the system.", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else {
						clearComponents();
						model.setCurrentUser(model.findUser(userID));
						manager.switchPanel("GMenu");
					}
				}
			});
			addComponent(submitButton, 1, 2);
		}
	}
}