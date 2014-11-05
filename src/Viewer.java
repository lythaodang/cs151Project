import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * COPYRIGHT (C) 2014 InfiniteLoops. All Rights Reserved.
 * A room for reservation.
 * Solves CS151 Project
 * @author Mike Phe, Ly Dang, Andrew Yobs
 * @version 1.00 2014/11/03
*/

public class Viewer
{
	private JFrame frame;
	private JPanel cards;
	
	public Viewer()
	{
		frame = new JFrame("InfiniteLoops Hotel Manager");
		cards = new JPanel(new CardLayout());
		
		cards.add(new InitialPanel(cards), "Initial");
		cards.add(new ReturningGuestPanel(cards), "RGuest");
		cards.add(new NewGuestPanel(cards), "NGuest");
		
		frame.add(cards);
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // change for load/save functionality
		frame.setVisible(true);
	}
	
	public class SwitchListener implements ActionListener
	{
		JPanel cards;
		String name;
		
		public SwitchListener(JPanel cards, String name)
		{
			this.cards = cards;
			this.name = name;
		}
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			CardLayout cl = (CardLayout)(cards.getLayout());
			cl.show(cards, name);
		}
	}
	
	public class InitialPanel extends JPanel
	{
		public InitialPanel(final JPanel cards)
		{
			setLayout(new GridLayout(3, 1));
			
			JButton returningGuest = new JButton("Returning Guest");
			returningGuest.setFont(returningGuest.getFont().deriveFont(36f));
			
			JButton newGuest = new JButton("New Guest");
			newGuest.setFont(newGuest.getFont().deriveFont(36f));
			
			JButton manager = new JButton("Manager");
			manager.setFont(manager.getFont().deriveFont(36f));
			
			add(returningGuest);
			add(newGuest);
			add(manager);
			
			returningGuest.addActionListener(new SwitchListener(cards, "RGuest"));
			newGuest.addActionListener(new SwitchListener(cards, "NGuest"));
			manager.addActionListener(new SwitchListener(cards, "Manager"));
		}
	}
	
	public class NewGuestPanel extends JPanel
	{
		public NewGuestPanel(JPanel cards)
		{
			setLayout(new BorderLayout(0, 25));
			
			JLabel instructions = new JLabel("<html>Fill out the following information."
					+ "<br>The user ID should be at least 6 characters.</html");
			instructions.setFont(instructions.getFont().deriveFont(20f));
			
			JPanel info = new JPanel(new GridLayout(3, 2, 0, 25));
			
			JLabel firstLabel = new JLabel("First name:");
			firstLabel.setFont(firstLabel.getFont().deriveFont(32f));
			JTextField firstTextField = new JTextField();
			firstTextField.setFont(firstTextField.getFont().deriveFont(32f));
			
			JLabel lastLabel = new JLabel("Last name:");
			lastLabel.setFont(lastLabel.getFont().deriveFont(32f));
			JTextField lastTextField = new JTextField();
			lastTextField.setFont(lastTextField.getFont().deriveFont(32f));
			
			JLabel userIDLabel = new JLabel("User ID:");
			userIDLabel.setFont(userIDLabel.getFont().deriveFont(32f));
			JTextField userIDTextField = new JTextField();
			userIDTextField.setFont(userIDTextField.getFont().deriveFont(32f));
			
			info.add(firstLabel);
			info.add(firstTextField);
			info.add(lastLabel);
			info.add(lastTextField);
			info.add(userIDLabel);
			info.add(userIDTextField);
			
			JButton submitButton = new JButton("Submit");
			submitButton.setFont(submitButton.getFont().deriveFont(32f));
			
			JButton backButton = new JButton("Back");
			backButton.setFont(backButton.getFont().deriveFont(32f));
			backButton.addActionListener(new SwitchListener(cards, "Initial"));
			
			JPanel buttonsPanel = new JPanel(new GridLayout(1, 2));
			buttonsPanel.add(backButton);
			buttonsPanel.add(submitButton);
			
			add(instructions, BorderLayout.NORTH);
			add(info, BorderLayout.CENTER);
			add(buttonsPanel, BorderLayout.SOUTH);
		}
	}
	
	public class ReturningGuestPanel extends JPanel
	{
		public ReturningGuestPanel(JPanel cards)
		{
			setLayout(new GridLayout(2, 2, 0, 100));
			
			JLabel userIDLabel = new JLabel("Enter user ID: ");
			userIDLabel.setFont(userIDLabel.getFont().deriveFont(32f));
			JTextField userIDTextField = new JTextField();
			userIDTextField.setFont(userIDTextField.getFont().deriveFont(32f));
			userIDTextField.setPreferredSize(new Dimension(200, 40));
			
			JButton submitButton = new JButton("Submit");
			submitButton.setFont(submitButton.getFont().deriveFont(32f));
			
			JButton backButton = new JButton("Back");
			backButton.setFont(backButton.getFont().deriveFont(32f));
			backButton.addActionListener(new SwitchListener(cards, "Initial"));
			
			add(userIDLabel);
			add(userIDTextField);
			add(backButton);
			add(submitButton);
		}
	}
}
