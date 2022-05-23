package program_2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.AbstractBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class testClass {
	
	private JFrame frame;
	private JTextField make;
	private JTextField model;
	private JTextField postcode;
	private JTextField miles; 
	private JTextField year;
	private JButton search;
	private JButton analyse;
	
	private JPanel panel;
	private JPanel westPanel;
	private JPanel buttonPanel;
	
	private JPanel center;
	private JPanel north;
	private JPanel east;
	private JPanel south;
	private JPanel west;
	
	
	private int width;
	private int height;

	
	public testClass() {
		
		frame = new JFrame("MOT Test Data Application");
		panel = new JPanel();
		
		make = new JTextField("Make");
		model = new JTextField("Model");
		postcode = new JTextField("Postcode");
		miles = new JTextField("Miles");
		year = new JTextField("Year");
		search = new JButton("Search");
		analyse = new JButton("Analyse");
		
		center = new JPanel();
		north = new JPanel();
		east = new JPanel();
		south = new JPanel();
		west = new JPanel();
		
		buttonPanel = new JPanel();
		
		width = 1100;
		height = 700;

	}
	
	public void setUpGUI() {
		Container cp = frame.getContentPane();
		frame.setSize(width, height);
		
		setUpPanels();
		setUpButtons();
		
		cp.setLayout(new BorderLayout(10, 10));
		cp.add(center, BorderLayout.CENTER);
		cp.add(north, BorderLayout.NORTH);
		cp.add(east, BorderLayout.EAST);
		cp.add(south, BorderLayout.SOUTH);
//		cp.add(west, BorderLayout.WEST);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBackground(Color.LIGHT_GRAY);
		cp.setBackground(Color.decode("#EAE6E5")); 
		frame.setVisible(true);
	
	}
	
	public void setUpPanels() {

		buttonPanel.add(make);
		buttonPanel.add(model);
		buttonPanel.add(postcode);
		buttonPanel.add(miles);
		buttonPanel.add(year);
		buttonPanel.add(search);
		buttonPanel.setBackground(Color.LIGHT_GRAY);
		buttonPanel.setLayout(new FlowLayout());
		
		center.setPreferredSize(new Dimension (0, 150));
		center.setBackground(Color.LIGHT_GRAY);
		center.setBorder(BorderFactory.createMatteBorder(0, 10, 0, 0, Color.decode("#EAE6E5")));
		
		north.setPreferredSize(new Dimension (0, 75));
		north.setBackground(Color.LIGHT_GRAY);
		north.setBorder(BorderFactory.createMatteBorder(10, 10, 0, 10, Color.decode("#EAE6E5")));
		north.setLayout(new BorderLayout());
		north.add(buttonPanel, BorderLayout.CENTER);
		
		
		east.setPreferredSize(new Dimension(250, 500));
		east.setBackground(Color.LIGHT_GRAY);
		east.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 10, Color.decode("#EAE6E5")));
		
		south.setPreferredSize(new Dimension (0, 50));
		south.setBackground(Color.LIGHT_GRAY);
		south.setLayout(new BorderLayout());
		south.add(analyse, BorderLayout.CENTER);
		south.setBorder(BorderFactory.createMatteBorder(0, 10, 10, 10, Color.decode("#EAE6E5")));

		west.setPreferredSize(new Dimension(250, 500));
		west.setBackground(Color.LIGHT_GRAY);
		
	}
	
	public void setUpButtons() {
		
		make.setPreferredSize(new Dimension(100, 30));
		make.setBackground(Color.decode("#EAE6E5"));
		make.setForeground(Color.decode("#A3938F"));
		make.setFont(new Font("SansSerif",Font.ITALIC,13));
		
		model.setPreferredSize(new Dimension(100, 30));
		model.setBackground(Color.decode("#EAE6E5"));
		model.setForeground(Color.decode("#A3938F"));
		model.setFont(new Font("SansSerif",Font.ITALIC,13));
		
		postcode.setPreferredSize(new Dimension(100, 30));
		postcode.setBackground(Color.decode("#EAE6E5"));
		postcode.setForeground(Color.decode("#A3938F"));
		postcode.setFont(new Font("SansSerif",Font.ITALIC,13));
		
		miles.setPreferredSize(new Dimension(100, 30));
		miles.setBackground(Color.decode("#EAE6E5"));
		miles.setForeground(Color.decode("#A3938F"));
		miles.setFont(new Font("SansSerif",Font.ITALIC,13));
		
		year.setPreferredSize(new Dimension(100, 30));
		year.setBackground(Color.decode("#EAE6E5"));
		year.setForeground(Color.decode("#A3938F"));
		year.setFont(new Font("SansSerif",Font.ITALIC,13));
		
		search.setPreferredSize(new Dimension(100, 22));
		search.setBackground(Color.decode("#5B9279"));
		search.setOpaque(true);
		search.setBorderPainted(false);
		search.setForeground(Color.decode("#EAE6E5"));
		
		analyse.setPreferredSize(new Dimension(100, 40));
		analyse.setBackground(Color.decode("#5B9279"));
		analyse.setOpaque(true);
		analyse.setBorderPainted(false);
		analyse.setForeground(Color.decode("#EAE6E5"));
		
	}
	
	
	public void textFieldLabel() {
		ActionListener buttonListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent a) {
				System.out.println("test");
			}
		};
		
		//TODO: add a button group
	}
}

