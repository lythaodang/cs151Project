import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


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
		addView();
	}
		
	private void addInstructions()
	{
		JLabel instructions = new JLabel("<html>Please enter a check-in and "
				+ "check-out date. Then choose your room type.<br> A list of "
				+ "available rooms will be displayed when all input is valid. <br>"
				+ "Note: Dates must be in correct format (MM/DD/YYYY).</html>");
		c.gridwidth = 2;
		c.weightx = 1;
		this.add(instructions, c);
		
		JLabel checkIn = new JLabel("Check-in:");
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
		final JTextField checkIn = new JTextField();
		c.gridx = 0;
		c.gridy = 2;
		this.add(checkIn, c);
		
		final JTextField checkOut = new JTextField();
		c.gridx = 1;
		c.gridy = 2;
		this.add(checkOut, c);
		
		checkIn.getDocument().addDocumentListener(new TextFieldListener(checkIn, "in"));
		
		checkOut.getDocument().addDocumentListener(new TextFieldListener(checkOut, "out"));
		
		JPanel roomTypePanel = new JPanel(new GridLayout(1, 3, 10, 0));
		JLabel room = new JLabel("Room type:");
		roomTypePanel.add(room);
		
		final JButton luxuryRoom = new JButton("$200");
		luxuryRoom.addActionListener(new 
				ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						model.setCost(200);
					}
				});
		roomTypePanel.add(luxuryRoom);
		
		final JButton normalRoom = new JButton("$80");
		normalRoom.addActionListener(new 
				ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						model.setCost(80);
					}
				});
		roomTypePanel.add(normalRoom);
		
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 2;
		this.add(roomTypePanel, c);
		
		JButton confirmed = new JButton("Confirmed");
		confirmed.addActionListener(new 
				ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						if (model.getSelectedRoom() != null)
						{
							model.addReservation();
							int response = JOptionPane.showConfirmDialog(new JFrame(), 
									"<html>Your reservation has been saved.<br>"
											+ "Would you like to make more transactions?</html>", 
											"Confirmation", JOptionPane.YES_NO_OPTION, 
											JOptionPane.QUESTION_MESSAGE);
							if (response == JOptionPane.NO_OPTION) 
								manager.switchPanel("Transaction Done");
							if (response == JOptionPane.YES_OPTION) 
								;
							checkIn.setText("");
							checkOut.setText("");
							model.setCheckIn(null);
							model.setCheckOut(null);
							model.setCost(0);
						}
					}
				});
		c.gridy = 6;
		c.gridwidth = 1;
		c.weighty = 0;
		this.add(confirmed, c);
		
		JButton trans = new JButton("Transaction Done");
		trans.addActionListener(new 
				ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						checkIn.setText("");
						checkOut.setText("");
						model.setCheckIn(null);
						model.setCheckOut(null);
						model.setCost(0);
						
						manager.switchPanel("Transaction Done");
					}
				});
		c.gridx = 1;
		this.add(trans, c);
	}
	
	class TextFieldListener implements DocumentListener
	{
		private JTextField tf;
		private String inOrOut;
		
		public TextFieldListener(JTextField tf, String inOrOut)
		{
			this.tf = tf;
			this.inOrOut = inOrOut;
		}
		
		@Override
		public void changedUpdate(DocumentEvent e)
		{
			return;
		}
		
		@Override
		public void insertUpdate(DocumentEvent e)
		{
			if (tf.getText().length() == 10)
			{
				if (isValidDate(tf.getText()))
				{
					GregorianCalendar date = stringToDate(tf.getText());
					if (date.equals(today) || date.before(today))
					{
						JOptionPane.showMessageDialog(new JFrame(), 
								"Error: Entered date is prior to today or is today.", 
								"Error", JOptionPane.ERROR_MESSAGE);
						if (inOrOut.equals("in"))
							model.setCheckIn(null);
						else
							model.setCheckOut(null);
					}
					else 
					{
						if (inOrOut.equals("in"))
							model.setCheckIn(date);
						else
							model.setCheckOut(date);
					}
				}
				else
				{
					JOptionPane.showMessageDialog(new JFrame(), 
							"Error: Invalid format.", 
							"Error", JOptionPane.ERROR_MESSAGE);
					if (inOrOut.equals("in"))
						model.setCheckIn(null);
					else
						model.setCheckOut(null);
				}
			}
			else
			{
				if (inOrOut.equals("in"))
					model.setCheckIn(null);
				else
					model.setCheckOut(null);
			}
		}
		
		@Override
		public void removeUpdate(DocumentEvent e)
		{
			insertUpdate(e);
		}
	}
	
	private void addView()
	{
		final JLabel availableLabel = new JLabel(model.getValidityOfInput());
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 2;
		this.add(availableLabel, c);
		
		final JList list = new JList(model.getAvailRooms().toArray());
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		JScrollPane listScroller = new JScrollPane(list);
		c.weighty = 1;
		c.gridy = 5;
		this.add(listScroller, c);
		
		list.addListSelectionListener(new 
				ListSelectionListener()
				{
					@Override
					public void valueChanged(ListSelectionEvent event)
					{
						model.setSelectedRoom((Room)list.getSelectedValue());
					}
				});
		
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
	}
	
	private boolean isValidDate(String input) 
	{
      try 
      {
          SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
          format.setLenient(false);
          format.parse(input);
      } 
      catch (Exception e) 
      {
          return false;
      }
      return true;
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
