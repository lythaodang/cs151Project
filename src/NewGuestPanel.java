import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NewGuestPanel extends JPanel
{
	private GridBagConstraints c;
	private final Model model;
	private ViewManager manager;

	public NewGuestPanel(final ViewManager manager)
	{
		this.manager = manager;
		model = manager.getModel();
		this.setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(10, 10, 10, 10);
		this.addInstructions();
		this.addLabels();
		this.addDataEntry();
	}

	private void addInstructions()
	{
		JLabel instructions = new JLabel("<html>Fill out the following information."
				+ "<br>Your user ID should be at least 6 characters and at most 12 "
				+ "characters. Your first and last name should not exceed 15 "
				+ "characters.</html>");
		c.gridwidth = 2;
		this.add(instructions, c);
	}

	private void addLabels()
	{
		JLabel firstName = new JLabel("First name:");
		c.weightx = 1;
		c.weighty = 1;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 1;
		this.add(firstName, c);

		JLabel lastName = new JLabel("Last name:");
		c.gridx = 0;
		c.gridy = 2;
		this.add(lastName, c);

		JLabel userID = new JLabel("User ID:");
		c.gridx = 0;
		c.gridy = 3;
		this.add(userID, c);
	}

	private void addDataEntry()
	{
		final JTextField firstName = new JTextField();
		c.gridx = 1;
		c.gridy = 1;
		this.add(firstName, c);

		final JTextField lastName = new JTextField();
		c.gridx = 1;
		c.gridy = 2;
		this.add(lastName, c);

		final JTextField userID = new JTextField();
		c.gridx = 1;
		c.gridy = 3;
		this.add(userID, c);

		JButton backButton = new JButton("Back");
		backButton.addActionListener(new 
				ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						firstName.setText("");
						lastName.setText("");
						userID.setText("");
						manager.switchPanel("Initial");
					}
				});
		c.gridx = 0;
		c.gridy = 4;
		this.add(backButton, c);
		
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new 
				ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0)
					{
						if (validEntry(firstName.getText(), lastName.getText(), userID.getText()))
						{
							Account newAccount = new Account(firstName.getText(), 
									lastName.getText(), userID.getText());
							firstName.setText("");
							lastName.setText("");
							userID.setText("");
							model.addAccount(newAccount);
							model.setCurrentUser(newAccount);
							manager.switchPanel("Guest Menu");
						}
					}
				});
		c.gridx = 1;
		c.gridy = 4;
		this.add(submitButton, c);
	}
	
	private boolean validEntry(String firstName, String lastName, String userID)
	{
		String message = "";
		if (firstName.length() < 1 || lastName.length() < 1
				|| userID.length() < 1)
		{
			message = "Error: One or more fields not entered.";
			JOptionPane.showMessageDialog(new JFrame(), message, 
					"Error", JOptionPane.ERROR_MESSAGE);
		}
		else if (firstName.length() > 15 || lastName.length() > 15)
		{
			message = "Error: First and/or last name(s) too long.";
			JOptionPane.showMessageDialog(new JFrame(), message, 
					"Error", JOptionPane.ERROR_MESSAGE);
		}
		else if (userID.length() < 6 || userID.length() > 12)
		{
			message = "Error: User ID is less than 6 characters or greater than"
					+ "12 characters.";
			JOptionPane.showMessageDialog(new JFrame(), message, 
					"Error", JOptionPane.ERROR_MESSAGE);
		}
		else if (model.findUser(userID) != null)
		{
			message = "Error: User ID taken.";
			JOptionPane.showMessageDialog(new JFrame(), message, 
					"Error", JOptionPane.ERROR_MESSAGE);
		}
		else
			return true;
		return false;
	}
}
