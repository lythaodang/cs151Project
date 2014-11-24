import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.NumberEditor;
import javax.swing.JTextArea;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;


public class ManagerCalendar extends JPanel
{
	private final static String[] monthList = {"January", "February", "March"};
	
	private GregorianCalendar calendar;
	private JPanel dropDownPanel;
	private JPanel calendarPanel;
	
	public ManagerCalendar()
	{
		this.dropDownPanel = new JPanel(new FlowLayout());
		calendarPanel = new JPanel(new GridLayout(1, 2));
		addDropDownPanel();
		addCalendar();
		addRoomPanel();
	}
	
	public void addDropDownPanel()
	{
		GregorianCalendar c = new GregorianCalendar();
		JComboBox<Object> months = new JComboBox<Object>(monthList);
		SpinnerModel spinnerModel = new SpinnerNumberModel(2014, c.getMinimum(GregorianCalendar.YEAR), 
													c.getMaximum(GregorianCalendar.YEAR), 1);
		JSpinner spinner = new JSpinner(spinnerModel);
		NumberEditor editor = new NumberEditor(spinner, "#");
		spinner.setEditor(editor);
		dropDownPanel.add(months);
		dropDownPanel.add(spinner);
		add(dropDownPanel);
	}
	
	public void addCalendar()
	{
		JPanel buttonPanel = new JPanel();
		calendar = new GregorianCalendar();
        int weekNumber = calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
        int firstDay = calendar.getActualMinimum(Calendar.DAY_OF_WEEK);
        int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_WEEK);
        JButton[][] days = new JButton[weekNumber+1][7];
        days[0][0] = new JButton("Su");
        days[0][1] = new JButton("Mo");
        days[0][2] = new JButton("Tu");
        days[0][3] = new JButton("We");
        days[0][4] = new JButton("Th");
        days[0][5] = new JButton("Fr");
        days[0][6] = new JButton("Sa");
        for (int i = 0; i < 7; i++)
        {
            days[0][i].setBackground(Color.CYAN);
            days[0][i].setOpaque(true);
        }
        int day = 0;
        for (int i = 1; i < weekNumber+1; i++)
        {
            for (int k = 0; k < 7; k++)
            {
                day++;
                if (day >= firstDay && day <= lastDay)
                {
                    Integer date = day - firstDay + 1;
                    days[i][k] = new JButton(String.valueOf(date));
                }
                else
                {
                    days[i][k] = new JButton("");
                }
            }
        }
		buttonPanel.setLayout(new GridLayout(7, 7));
        for (int i = 0; i < weekNumber+1; i++)
        {
            for (int k = 0; k < 7; k++)
            {
                days[i][k].setPreferredSize(new Dimension(25,25));
                days[i][k].addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                    }
                });
                buttonPanel.add(days[i][k]);
            }
        }
        calendarPanel.add(buttonPanel);
	}
	
	public void addRoomPanel()
	{
		JTextArea area = new JTextArea("Room Information", 10, 10);
		area.setEditable(false);
		calendarPanel.add(area);
		add(calendarPanel);
	}
}