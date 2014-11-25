import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class MakeReservationPanel extends JPanel
{
	GridBagConstraints c;
	private final DatabaseModel model;
	private ViewManager manager;
	private JButton selectedCost;
	
	public MakeReservationPanel(final ViewManager manager)
	{
		this.manager = manager;
		model = manager.getModel();
		selectedCost = null;
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
	}
		
		
	public void addInstructions()
	{
		JLabel instructions = new JLabel("<html>Please enter a check-in and "
				+ "check-out date. Then choose your room type.<br> "
				+ "Note: Dates must be in correct format (MM/DD/YYYY).</html>");
		c.insets = new Insets(10, 10, 10, 10); 
		c.gridwidth = 2;
		this.add(instructions, c);
		
		JLabel checkIn = new JLabel("Check-in:");
		c.weightx = 1;
		c.weighty = 1;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 1;
		this.add(checkIn, c);
		
		JLabel checkOut = new JLabel("Check-out:");
		c.gridx = 1;
		c.gridy = 1;
		this.add(checkOut, c);
	}
	
	public void addControllers()
	{	
		final JTextField checkIn = new JTextField("MM/DD/YYYY");
		c.gridx = 0;
		c.gridy = 2;
		this.add(checkIn, c);
		
		final JTextField checkOut = new JTextField("MM/DD/YYYY");
		c.gridx = 1;
		c.gridy = 2;
		this.add(checkOut, c);
		
		checkIn.getDocument().addDocumentListener(
				new DocumentListener() 
				{
					@Override
					public void changedUpdate(DocumentEvent e) 
					{
						if(checkIn.getText().length() == 10 && checkIn.getText()checkIn.getText().length() == 10
								&& selectedCost != null)
						{
							if (isValidDate(checkIn.getText()) && isValidDate(checkOut.getText()))
							{
								GregorianCalendar start = stringToDate(checkIn.getText());
								GregorianCalendar end = stringToDate(checkOut.getText());
								if (!start.before(end))
									JOptionPane.showMessageDialog(new JFrame(), 
											"Error: Check-out is before check-in", 
											"Error", JOptionPane.ERROR_MESSAGE);
								else if (checkDaysBetween(start, end) > 60)
									JOptionPane.showMessageDialog(new JFrame(), 
											"Error: Stay is too long.", 
											"Error", JOptionPane.ERROR_MESSAGE);
								else if ()
									
							}
					}
					@Override
					public void insertUpdate(DocumentEvent e)
					{
						return;
					}
					@Override
					public void removeUpdate(DocumentEvent e)
					{
						return;
					}
			  });
		
		checkOut.getDocument().addDocumentListener(
				new DocumentListener() 
				{
					@Override
					public void changedUpdate(DocumentEvent e) 
					{
						if (isValidDate(checkOutTextField.getText()))
						database.setCurrCheckOut(stringToDate(checkOutTextField.getText()));
						if (database.getCurrCheckIn() != null)
						{
							if (database.getCurrCheckOut().before(database.getCurrCheckIn()))
								JOptionPane.showMessageDialog(new JFrame(), 
										"Check-out is before check-in", 
										"Error", JOptionPane.ERROR_MESSAGE);
							// check number of days between checkin and checkout
						}
					}
					@Override
					public void insertUpdate(DocumentEvent e)
					{
						return;
					}
					@Override
					public void removeUpdate(DocumentEvent e)
					{
						return;
					}
			  });
		
		JPanel roomTypePanel = new JPanel(new GridLayout(1, 3, 10, 0));
		JLabel room = new JLabel("Room type:");
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 2;
		roomTypePanel.add(room);
		
		JButton luxuryRoom = new JButton("$200");
		luxuryRoom.addActionListener(new 
				ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						
					}
				});
		roomTypePanel.add(luxuryRoom);
		
		JButton normalRoom = new JButton("$80");
		normalRoom.addActionListener(new 
				ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						
					}
				});
		roomTypePanel.add(normalRoom);
		
		panel.add(roomTypePanel, c);
		
		JPanel availableRooms = new JPanel();
		
		JLabel availableLabel = new JLabel("Available Rooms");
		// add label and buttons to panel
		
		// get rooms from database
		// if not valid show "Fix the error"
		ChangeListener listener = new
				ChangeListener()
				{
					@Override
					public void stateChanged(ChangeEvent event)
					{
						for (Room r : database.getAvailRooms())
						{
							// add a button of each room avail to panel
						}
					}
				};
				
		database.addChangeListener(listener);
		
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 2;
		c.gridheight = 2;
		panel.add(availableRooms, c);
	}
	
	private boolean isValidDate(String input) 
	{
      Date date = null;
      try 
      {
          date = new SimpleDateFormat("MM/DD/YYYY").parse(input);
      } 
      catch (Exception e) 
      {
          return false;
      }
      return true;
	}
	
	private int checkDaysBetween(GregorianCalendar start, GregorianCalendar end)
	{
		int count = 0;
		while (!start.equals(end))
		{
			start.add(Calendar.DATE, 1);
			count++;
		}
		return count;
	}
	
	/**
	 * Converts the user's input into a GregorianCalendar for use in printing
	 * the month and events.
	 * @param input the user's string input in the form of MM/DD/YYYY
	 * @return a GregorianCalendar with the user's inputed date
	 */
	public GregorianCalendar stringToDate(String input)
	{
		GregorianCalendar temp = new GregorianCalendar
				(Integer.parseInt(input.substring(6, 10)),
						Integer.parseInt(input.substring(0, 2)) - 1, 
						Integer.parseInt(input.substring(3, 5)));

		return temp;
	}
}
