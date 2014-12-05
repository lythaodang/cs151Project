import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ViewCancelPanel extends JPanel
{	
	public ViewCancelPanel(final ViewManager manager)
	{
		final Model model = manager.getModel();
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(10, 10, 10, 10);
		c.weightx = 1;
		
		JLabel label = new JLabel("<html>Below are all your reservations.<br>To cancel a "
				+ "reservation: Select the one you wish to cancel. Press cancel.<br>"
				+ "If the list is empty, then you have not made any reservations.</html>");
		c.gridwidth = 2;
		this.add(label, c);
		
		final JList list = new JList();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		JScrollPane listScroller = new JScrollPane(list);
		c.weighty = 1;
		c.gridy = 1;
		this.add(listScroller, c);
		
		model.addChangeListener(new
				ChangeListener()
				{
					@Override
					public void stateChanged(ChangeEvent e)
					{
						if (model.getCurrentUser() != null)
							list.setListData(model.getCurrentUser().getReservations().toArray());
						else
							list.setListData(new Reservation[0]);
					}
				});
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new
				ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0)
					{
						if (!list.isSelectionEmpty())
						{
							int response = JOptionPane.showConfirmDialog(new JFrame(), 
									"Are you sure you want to cancel this reservation?", 
									"Confirmation", JOptionPane.YES_NO_OPTION, 
									JOptionPane.QUESTION_MESSAGE);
							if (response == JOptionPane.NO_OPTION) 
								;
							if (response == JOptionPane.YES_OPTION) 
							{
								model.cancelReservation((Reservation)list.getSelectedValue());
							}
						}
					}
				});
		c.weighty = 0.5;
		c.gridy = 2;
		this.add(cancelButton, c);
		
		JButton backButton = new JButton("Back to Guest Menu");
		backButton.addActionListener(new 
				ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						list.clearSelection();
						manager.switchPanel("Guest Menu");
					}
				});
		c.gridy = 3;
		c.gridwidth = 1;
		this.add(backButton, c);
		
		JButton signOutButton = new JButton("Sign out");
		signOutButton.addActionListener(new 
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
		this.add(signOutButton, c);
	}
}
