import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class AvailablePanel extends JPanel
{
	private GridBagConstraints c;
	private ViewManager manager;
	
	public AvailablePanel(ViewManager m)
	{
		this.manager = m;
		this.setLayout(new GridBagLayout());
		this.c = new GridBagConstraints();
		this.c.fill = GridBagConstraints.BOTH;
		this.c.insets = new Insets(10, 25, 10, 25);
		addAvailableRoomsInfo();
		addButtons();
		addBackButton();
	}
	
	public void addAvailableRoomsInfo()
	{
		JTextArea roomInfo = new JTextArea("Available Rooms");
		roomInfo.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(roomInfo,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 1;
		c.gridwidth = 2;
		add(scrollPane, c);
	}
	
	public void addButtons()
	{
		JButton confirmed = new JButton("Confirmed");
		confirmed.addActionListener(new 
				ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						
					}
				});
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.weighty = 0.1;
		c.weightx = 1;
		add(confirmed, c);
		
		JButton trans = new JButton("Transaction Done");
		trans.addActionListener(new 
				ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						
					}
				});
		c.gridx = 1;
		add(trans, c);
	}
	
	public void addBackButton()
	{
		JButton back = new JButton("Back");
		back.addActionListener(new 
				ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						manager.switchPanel("Make a Reservation");
					}
				});
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weighty = 0.1;
		add(back, c);
	}
}
