import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
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
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class MakeReservationPanel extends JPanel
{
	private GridBagConstraints c;
	private final GregorianCalendar today;
	private final DatabaseModel model;
	private ViewManager manager;
	
	public MakeReservationPanel(final ViewManager manager)
	{
		this.manager = manager;
		model = manager.getModel();
		today = new GregorianCalendar();
		today.clear(Calendar.HOUR);
		today.clear(Calendar.MINUTE);
		today.clear(Calendar.SECOND);
		today.clear(Calendar.MILLISECOND);
		
		this.setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		c.insets = new Insets(10, 10, 10, 10); 
		c.fill = GridBagConstraints.BOTH;
		
		addInstructions();
		addControllers();
	}
		
		
	private void addInstructions()
	{
		JLabel instructions = new JLabel("<html>Please enter a check-in and "
				+ "check-out date. Then choose your room type.<br> "
				+ "Note: Dates must be in correct format (MM/DD/YYYY).</html>");
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
						return;
					}
					@Override
					public void insertUpdate(DocumentEvent e)
					{
						if (checkIn.getText().length() == 10)
						{
							if (isValidDate(checkIn.getText()))
							{
								GregorianCalendar date = stringToDate(checkIn.getText());
								if (date.equals(today) || date.before(today))
								{
									JOptionPane.showMessageDialog(new JFrame(), 
											"Error: Entered date is prior to today or is today.", 
											"Error", JOptionPane.ERROR_MESSAGE);
									model.setCurrCheckIn(null);
								}
								else
									model.setCurrCheckIn(date);
							}
							else
							{
								JOptionPane.showMessageDialog(new JFrame(), 
										"Error: Invalid format.", 
										"Error", JOptionPane.ERROR_MESSAGE);
								model.setCurrCheckIn(null);
							}
						}
						else
							model.setCurrCheckIn(null);
					}
					@Override
					public void removeUpdate(DocumentEvent e)
					{
						if (checkIn.getText().length() == 10)
						{
							if (isValidDate(checkIn.getText()))
							{
								GregorianCalendar date = stringToDate(checkIn.getText());
								if (date.equals(today) || date.before(today))
								{
									JOptionPane.showMessageDialog(new JFrame(), 
											"Error: Entered date is prior to today or is today.", 
											"Error", JOptionPane.ERROR_MESSAGE);
									model.setCurrCheckIn(null);
								}
								else
									model.setCurrCheckIn(date);
							}
							else
							{
								JOptionPane.showMessageDialog(new JFrame(), 
										"Error: Invalid format.", 
										"Error", JOptionPane.ERROR_MESSAGE);
								model.setCurrCheckIn(null);
							}
						}
						else
							model.setCurrCheckIn(null);
					}
			  });
		
		checkOut.getDocument().addDocumentListener(
				new DocumentListener() 
				{
					@Override
					public void changedUpdate(DocumentEvent e) 
					{
						return;
					}
					@Override
					public void insertUpdate(DocumentEvent e)
					{
						if (checkOut.getText().length() == 10)
						{
							if (isValidDate(checkOut.getText()))
							{
								GregorianCalendar date = stringToDate(checkOut.getText());
								if (date.equals(today) || date.before(today))
								{
									JOptionPane.showMessageDialog(new JFrame(), 
											"Error: Entered date is prior to today or is today.", 
											"Error", JOptionPane.ERROR_MESSAGE);
									model.setCurrCheckOut(null);
								}
								else
									model.setCurrCheckOut(date);
							}
							else
							{
								JOptionPane.showMessageDialog(new JFrame(), 
										"Error: Invalid format.", 
										"Error", JOptionPane.ERROR_MESSAGE);
								model.setCurrCheckOut(null);
							}
						}
						else
							model.setCurrCheckOut(null);
					}
					@Override
					public void removeUpdate(DocumentEvent e)
					{
						if (checkOut.getText().length() == 10)
						{
							if (isValidDate(checkOut.getText()))
							{
								GregorianCalendar date = stringToDate(checkOut.getText());
								if (date.equals(today) || date.before(today))
								{
									JOptionPane.showMessageDialog(new JFrame(), 
											"Error: Entered date is prior to today or is today.", 
											"Error", JOptionPane.ERROR_MESSAGE);
									model.setCurrCheckOut(null);
								}
								else
									model.setCurrCheckOut(date);
							}
							else
							{
								JOptionPane.showMessageDialog(new JFrame(), 
										"Error: Invalid format.", 
										"Error", JOptionPane.ERROR_MESSAGE);
								model.setCurrCheckOut(null);
							}
						}
						else
							model.setCurrCheckOut(null);
					}
			  });
		
		JPanel roomTypePanel = new JPanel(new GridLayout(1, 3, 10, 0));
		JLabel room = new JLabel("Room type:");
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 2;
		roomTypePanel.add(room);
		
		final JButton luxuryRoom = new JButton("$200");
		luxuryRoom.addActionListener(new 
				ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						model.setCurrSelectedCost(200);
						manager.switchPanel("Available Rooms");
					}
				});
		roomTypePanel.add(luxuryRoom);
		
		final JButton normalRoom = new JButton("$80");
		normalRoom.addActionListener(new 
				ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						model.setCurrSelectedCost(80);
						manager.switchPanel("Available Rooms");
					}
				});
		roomTypePanel.add(normalRoom);
		
		this.add(roomTypePanel, c);
		
		JPanel availableRooms = new JPanel(new GridLayout(2, 1));
		
		final JLabel availableLabel = new JLabel(model.getValidityOfInput());
		availableRooms.add(availableLabel);
		final JList list = new JList(model.getAvailRooms().toArray());
		list.setVisibleRowCount(20);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		JScrollPane listScroller = new JScrollPane(list);
		availableRooms.add(listScroller);
		
		ChangeListener listener = new
				ChangeListener()
				{
					@Override
					public void stateChanged(ChangeEvent event)
					{
						availableLabel.setText(model.getValidityOfInput());
						list.setListData(model.getAvailRooms().toArray());
					}
				};
				
		model.addChangeListener(listener);
		
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 2;
		c.gridheight = 2;
		this.add(availableRooms, c);
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
