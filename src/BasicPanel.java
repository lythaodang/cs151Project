import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * COPYRIGHT 2014 InfiniteLoops. All Rights Reserved. 
 * Hotel Management 
 * CS151 Group Project
 * @author Mike Phe, Ly Dang, Andrew Yobs
 * @version 1.00 2014/10/30
 */

/**
 * The basic panel with the common factors between all other panels.
 */
@SuppressWarnings("serial")
public class BasicPanel extends JPanel {
	protected GridBagConstraints c;
	protected Model model;
	protected ViewManager manager;
	protected ArrayList<JTextField> tfs;
	protected ArrayList<JTextArea> tas;
	
	/** 
	 * Constructs the panel with a view manager.
	 * @param manager the view manager
	 */
	public BasicPanel(ViewManager manager) {
		this.manager = manager;
		model = manager.getModel();
		tfs = new ArrayList<JTextField>();
		tas = new ArrayList<JTextArea>();
		
		this.setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(10, 10, 10, 10);
		c.weightx = 1;
	}
	
	/**
	 * Adds a component to the panel at location x, y
	 * @param comp the component to add
	 * @param x the x location
	 * @param y the y location
	 */
	protected void addComponent(JComponent comp, int x, int y) {
		c.gridx = x;
		c.gridy = y;
		add(comp, c);
		
		if (comp instanceof JTextField)
			tfs.add((JTextField) comp);
		if (comp instanceof JTextArea)
			tas.add((JTextArea) comp);
	}
	
	/**
	 * Adds instructions to the panel.
	 * @param instructions the instructions to add
	 */
	protected void addInstructions(String instructions) {
		JLabel label = new JLabel(instructions);
		addComponent(label, 0, 0);
	}
	
	/**
	 * Adds a button for navigation.
	 * @param text the text displayed on the button
	 * @param backTo the panel to switch back to
	 * @param x the x location 
	 * @param y the y location
	 */
	protected void addNavigationButton(String text, final String backTo, int x, int y) {
		JButton button = new JButton(text);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearComponents();
				manager.switchPanel(backTo);
			}
		});
		addComponent(button, x, y);
	}
	
	/**
	 * Adds a sign out button.
	 * @param backTo the panel to switch back to
	 * @param x the x location
	 * @param y the y location
	 */
	protected void addSignOutButton(final String backTo, int x, int y) {
		JButton button = new JButton("Sign Out");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.setCurrentUser(null);
				clearComponents();
				manager.switchPanel(backTo);
			}
		});
		addComponent(button, x, y);
	}
	
	/**
	 * Adds a label to the panel.
	 * @param text the text to display
	 * @param x the x location
	 * @param y the y location
	 */
	protected void addLabel(String text, int x, int y) {
		JLabel label = new JLabel(text);
		addComponent(label, x, y);
	}
	
	/**
	 * Clears all textfields and textareas on the panel.
	 */
	protected void clearComponents() {
		for (JTextField tf : tfs)
			tf.setText("");
		for (JTextArea ta : tas)
			ta.setText("");
	}
}
