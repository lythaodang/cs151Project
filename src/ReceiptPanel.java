import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultCaret;


public class ReceiptPanel extends JPanel
{
	public ReceiptPanel(final ViewManager manager)
	{
		final Model model = manager.getModel();
		final SimpleReceipt simple = new SimpleReceipt(model.getTransaction());
		final ComprehensiveReceipt comprehensive = new ComprehensiveReceipt(model.getCurrentUser());

		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10, 10, 10, 10); 
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		
		JLabel instructions = new JLabel("<html>Choose the type of receipt you wish to view.<br>"
				+ "Simple: Shows only reservations in this transaction.<br>"
				+ "Comprehensive: Shows all reservations made with this account.<br>"
				+ "Sign out when you are done.");
		c.gridwidth = 2;
		this.add(instructions, c);
		
		final JTextArea receipt = new JTextArea();
		receipt.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(receipt,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		DefaultCaret caret = (DefaultCaret) receipt.getCaret();
		caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
		c.gridy = 2;
		c.weighty = 1;
		this.add(scrollPane, c);
		
		JButton simpleButton = new JButton("Simple");
		simpleButton.addActionListener(new
				ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0)
					{
						receipt.setText(simple.toString());
					}
				});
		c.weighty = 0.5;
		c.gridy = 1;
		c.gridwidth = 1;
		this.add(simpleButton, c);
		
		JButton comprehensiveButton = new JButton("Comprehensive");
		comprehensiveButton.addActionListener(new
				ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						receipt.setText(comprehensive.toString());
					}
				});
		c.gridx = 1;
		this.add(comprehensiveButton, c);
		
		JButton backButton = new JButton("Back to Guest Menu");
		backButton.addActionListener(new 
				ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						model.getTransaction().clear();
						receipt.setText("");
						manager.switchPanel("Guest Menu");
					}
				});
		c.gridx = 0;
		c.gridy = 3;
		this.add(backButton, c);
		
		JButton signOutButton = new JButton("Sign out");
		signOutButton.addActionListener(new 
				ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						model.setCurrentUser(null);
						model.getTransaction().clear();
						receipt.setText("");
						manager.switchPanel("Initial");
					}
				});
		c.gridx = 1;
		this.add(signOutButton, c);
		
		model.addChangeListener(new
				ChangeListener()
				{
					@Override
					public void stateChanged(ChangeEvent e)
					{
						comprehensive.setUser(model.getCurrentUser());
					}
				});
	}
}