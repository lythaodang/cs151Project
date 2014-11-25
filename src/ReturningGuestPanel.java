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


public class ReturningGuestPanel extends JPanel
{
	GridBagConstraints c;
	private final DatabaseModel model;
	private ViewManager manager;
	
	public ReturningGuestPanel(final ViewManager manager)
	{
		this.manager = manager;
		model = manager.getModel();
		this.setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(25, 10, 25, 10);
		this.addInstructions();
		this.addDataEntry();
	}
	
	private void addInstructions()
	{	
		JLabel instructions = new JLabel("<html>Please enter your user ID to "
				+ "make, cancel, or view your reservations.<br><br>If you do not "
				+ "have an account please go back and create one.</html>");
		c.gridwidth = 2;
		this.add(instructions, c);
	}
	
	private void addDataEntry()
	{
		JLabel userIDLabel = new JLabel("Enter user ID:");
		c.weightx = 1;
		c.weighty = 1;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 1;
		this.add(userIDLabel, c);
		
		final JTextField userIDTextField = new JTextField();
		c.gridx = 1;
		c.gridy = 1;
		this.add(userIDTextField, c);

		JButton backButton = new JButton("Back");
		backButton.addActionListener(new 
				ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						userIDTextField.setText("");
						manager.switchPanel("Initial");
					}
				});
		c.gridx = 0;
		c.gridy = 2;
		this.add(backButton, c);
		
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
						else if (model.findUser(userID) == null)
						{
							message = "Error: User ID does not exist in the system.";
							JOptionPane.showMessageDialog(new JFrame(), message, 
									"Error", JOptionPane.ERROR_MESSAGE);
						}
						else
						{
							userIDTextField.setText("");
							model.setCurrentUser(model.findUser(userID));
							manager.switchPanel("Guest Menu");
						}
					}
				});
		c.gridx = 1;
		c.gridy = 2;
		this.add(submitButton, c);
	}
}
