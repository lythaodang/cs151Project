import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSpinner.NumberEditor;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

public class ManagerViewPanel extends JPanel
{
	private final static String[] monthList = {"January", "February", "March",
		"April", "May", "June", "July", "August", "September", "October", 
		"November", "December"};
	private final static String[] daysList = {"S", "M", "T", "W", "T", "F", "S"};
	private final static int DAYS_IN_A_WEEK = 7;
	private final static int MAX_WEEKS_IN_A_MONTH = 6;

	private GregorianCalendar current;
	private GridBagConstraints c;

	public ManagerViewPanel()
	{
		current = new GregorianCalendar();
		
		this.setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(10, 10, 10, 10);
		
		addDropDown();
		addCalendar();
		addRoomInfo();
		addBackButton();
	}

	public void addDropDown()
	{
		JPanel dropDownPanel = new JPanel(new GridLayout(1, 2, 10, 10));
		JComboBox<Object> months = new JComboBox<Object>(monthList);
		months.setSelectedItem(monthList[current.get(Calendar.MONTH)]);
		months.setBackground(Color.white);
		((JLabel)months.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		
		SpinnerModel spinnerModel = new SpinnerNumberModel(
				current.get(Calendar.YEAR), 
				current.getMinimum(GregorianCalendar.YEAR), 
				current.getMaximum(GregorianCalendar.YEAR), 1);
		JSpinner spinner = new JSpinner(spinnerModel);
		NumberEditor editor = new NumberEditor(spinner, "#");
		editor.getTextField().setHorizontalAlignment(JTextField.CENTER);
		editor.getTextField().setEditable(false);
		editor.getComponent(0).setBackground(Color.white);
		spinner.setEditor(editor);
		
		dropDownPanel.add(months);
		dropDownPanel.add(spinner);

		c.weighty = 0.25;
		add(dropDownPanel, c);
	}
	
	public void addCalendar()
	{
		int weeksInMonth = current.getActualMaximum(Calendar.WEEK_OF_MONTH);
		int daysInMonth = current.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		GregorianCalendar temp = (GregorianCalendar) current.clone();
		temp.set(Calendar.DAY_OF_MONTH, 1);
		int weekdayStart = temp.get(GregorianCalendar.DAY_OF_WEEK) - 1;
		
		JPanel calendarPanel = new JPanel(new GridLayout(
				MAX_WEEKS_IN_A_MONTH + 1, DAYS_IN_A_WEEK));
		
		for (int i = 0; i < DAYS_IN_A_WEEK; i++)
		{
			JLabel label = new JLabel(daysList[i], JLabel.CENTER);
			label.setBackground(Color.blue);
			label.setBorder(null);
			label.setOpaque(true);
			label.setForeground(Color.white);
			calendarPanel.add(label);
		}
		
		JButton[][] days = new JButton[weeksInMonth][DAYS_IN_A_WEEK];
		
		for (int i = 0; i < weeksInMonth; i++)
			for (int j = 0; j < DAYS_IN_A_WEEK; j++)
			{
				JButton button = new JButton();
				button.setBackground(Color.white);
				button.setOpaque(true);
				days[i][j] = button;
				calendarPanel.add(days[i][j]);
			}
		
		int currDay = 1;
		for (int i = 0; i < weeksInMonth; i++)
			for (int j = 0; j < DAYS_IN_A_WEEK; j++)
			{
				if (currDay <= daysInMonth) 
				{
					if ((i == 0 && j >= weekdayStart) || (i != 0))
					{
						days[i][j].setText(Integer.toString(currDay));
						currDay++;
					}
				}
				else
					break;
			}
		
		c.weightx = 0;
		c.weighty = 1;
		c.gridy = 1;
		c.weightx = 0.25;
		add(calendarPanel, c);
	}
	
	public void addRoomInfo()
	{
		JTextArea roomInfo = new JTextArea("Room Information");
		roomInfo.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(roomInfo,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 1;
		c.gridheight = 2;
		add(scrollPane, c);
	}
	
	public void addBackButton()
	{
		JButton back = new JButton("Back");
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weighty = 0.5;
		add(back, c);
	}
}