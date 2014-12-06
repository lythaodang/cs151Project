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


public class ManagerMenuPanel extends JPanel
{
	GridBagConstraints c;
	private final Model model;
	private ViewManager manager;
	
	/**
	 * Constructor
	 * @param manager the view
	 */
	public ManagerMenuPanel(final ViewManager manager)
	{
		this.manager = manager;
		model = manager.getModel();
		this.setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(10, 10, 10, 10);
		
		JLabel name = new JLabel();
		name.setText("<html>User:<br>" + "Manager" + "</html>");
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
						manager.switchPanel("Initial");
					}
				});
		c.gridx = 1;
		this.add(backButton, c);
		
		JButton view = new JButton("View");
		view.addActionListener(new 
				ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						manager.switchPanel("Calendar");
					}
				});
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 1;
		this.add(view, c);
		
		JButton save = new JButton("Save/Quit");
		save.addActionListener(new 
				ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						model.serialize();
						System.exit(0);
					}
				});
		c.gridy = 2;
		this.add(save, c);
	}
}
