import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GuestMenuPanel extends JPanel
{
	private GridBagConstraints c;
	private final Model model;
	private ViewManager manager;
	
	public GuestMenuPanel(final ViewManager manager)
	{
		this.manager = manager;
		model = manager.getModel();
		this.setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(25, 10, 25, 10);
	
		final JLabel name = new JLabel();
		ChangeListener listener = new
				ChangeListener()
				{
					@Override
					public void stateChanged(ChangeEvent event)
					{
						name.setText("<html>User:<br>" + 
								model.getCurrentUserName() + "</html>");
					}
				};
		model.addChangeListener(listener);
		c.weightx = 1;
		c.weighty = 1;
		c.insets = new Insets(10, 10, 10, 10); 
		this.add(name, c);
		
		JButton backButton = new JButton("Sign out");
		backButton.addActionListener(new 
				ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						model.setCurrentUser(null);
						manager.switchPanel("Initial");
					}
				});
		c.gridx = 1;
		this.add(backButton, c);
		
		JButton make = new JButton("Make a Reservation");
		make.addActionListener(new 
				ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						manager.switchPanel("Make a Reservation");
					}
				});
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 1;
		this.add(make, c);
		
		JButton viewCancel = new JButton("View/Cancel a Reservation");
		viewCancel.addActionListener(new 
				ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						manager.switchPanel("View/Cancel a Reservation");
					}
				});
		c.gridy = 2;
		this.add(viewCancel, c);
	}
}
