import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * COPYRIGHT 2014 InfiniteLoops. All Rights Reserved. 
 * Hotel Management 
 * CS151 Group Project
 * @author Mike Phe, Ly Dang, Andrew Yobs
 * @version 1.00 2014/10/30
 */

/**
 * A panel in which the user can create an account.
 */
@SuppressWarnings("serial")
public class NewGuestPanel extends BasicPanel {

	/**
	 * Constructs the panel in which the user can create an account.
	 * @param m the view manager
	 */
	public NewGuestPanel(ViewManager m) {
		super(m);

		c.gridwidth = 2;
		addInstructions("<html>Fill out the following information."
				+ "<br>Your user ID should be at least 6 characters and at most 12 "
				+ "characters. Your first and last name should not exceed 15 "
				+ "characters.</html>");
		
		c.weightx = 1;
		c.weighty = 1;
		c.gridwidth = 1;
		addLabel("First name: ", 0, 1);
		addLabel("Last name: ", 0, 2);
		addLabel("User ID: ", 0, 3);
		
		final JTextField firstName = new JTextField();
		addComponent(firstName, 1, 1);

		final JTextField lastName = new JTextField();
		addComponent(lastName, 1, 2);

		final JTextField userID = new JTextField();
		addComponent(userID, 1, 3);

		addNavigationButton("Back", "Initial", 0, 4);

		JButton submit = new JButton("Submit");
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String first = firstName.getText();
				String last = lastName.getText();
				String id = userID.getText();
				if (validEntry(first, last, id)) {
					Account newAccount = new Account(first + " " + last, id);
					clearComponents();
					model.addAccount(newAccount);
					model.setCurrentUser(newAccount);
					manager.switchPanel("GMenu");
				}
			}
		});
		addComponent(submit, 1, 4);
	}

	/**
	 * Checks if entry is valid.
	 * @param first the first name
	 * @param last the last name
	 * @param id the user id
	 * @return true if input is valid else false
	 */
	private boolean validEntry(String first, String last, String id) {
		if (first.length() < 1 || last.length() < 1 || id.length() < 1) {
			JOptionPane.showMessageDialog(new JFrame(), 
					"Error: One or more fields not entered.", "Error",
					JOptionPane.ERROR_MESSAGE);
		} else if (first.length() > 15 || last.length() > 15) {
			JOptionPane.showMessageDialog(new JFrame(), 
					"Error: First and/or last name(s) too long.", "Error",
					JOptionPane.ERROR_MESSAGE);
		} else if (id.length() < 6 || id.length() > 12) {
			JOptionPane.showMessageDialog(new JFrame(), 
					"Error: ID is less than 6 or more than 12 characters.", "Error",
					JOptionPane.ERROR_MESSAGE);
		} else if (model.findUser(id) != null) {
			JOptionPane.showMessageDialog(new JFrame(), 
					"Error: ID taken.", "Error",
					JOptionPane.ERROR_MESSAGE);
		} else return true;
		return false;
	}
}
