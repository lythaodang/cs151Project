import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
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
		cards.add(new GuestPanel(cards), "Guest");
		
		frame.add(cards);
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // change for load/save functionality
		frame.setVisible(true);
	}
	
	public class InitialPanel extends JPanel
	{
		public InitialPanel(final JPanel cards)
		{
			JButton guest = new JButton("Guest");
			JButton manager = new JButton("Manager");
			this.add(guest);
			this.add(manager);
			
			guest.addActionListener(new 
					ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent e)
						{
							CardLayout cl = (CardLayout)(cards.getLayout());
							cl.show(cards, "Guest");
						}
					}
			);
			
			manager.addActionListener(new 
					ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent e)
						{
							CardLayout cl = (CardLayout)(cards.getLayout());
							cl.show(cards, "Manager");
						}
					}
			);
		}
	}
	
	public class GuestPanel extends JPanel
	{
		public GuestPanel(JPanel cards)
		{
			setLayout(new BorderLayout());
			
			JPanel userInput = new JPanel();
			
			JLabel requestID = new JLabel("Enter user ID: ");
			requestID.setFont(new Font("Serif", Font.PLAIN, 16));
			JTextField enterID = new JTextField();
			enterID.setPreferredSize(new Dimension(200, 30));
			
			userInput.add(requestID);
			userInput.add(enterID);
			
			JButton create = new JButton("Create an account");
			
			add(userInput, BorderLayout.NORTH);
			add(create, BorderLayout.SOUTH);
		}
	}
}
