package program_2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.jdbc.JDBCCategoryDataset;

import program_1.SQL;

/**
 * GUI
 * 
 * All grapical user interface related code is hosted in this class, when called an object of GUI is created generating a GUI.
 * This contains all SWING related functionality and the complete design of the GUI, it also includes interaction with the DB 
 * for data to be displayed within the JTable.
 * 
 * @author jordanprescott
 *
 */
public class GUI {

	private JFrame frame;

	private JPanel buttonPanel;
	private JPanel searchedDataPanel;
	private JPanel singleRowPanel;
	private JPanel analysePanel;
	private JPanel center;
	private JPanel north;
	private JPanel east;
	private JPanel south;
	private JPanel west;
	
	private JTextField make;
	private JTextField model;
	private JTextField postcode;
	private JTextField miles;
	private JTextField year;
	private JButton search;
	private JButton clearSearch;
	private JButton analyse;
	private JButton help;

	private JLabel lMake;
	private JLabel lModel;
	private JLabel lPostcode;
	private JLabel lMiles;
	private JLabel lYear;
	
	private JLabel srlMake;
	private JLabel srlModel;
	private JLabel srlPostcode;
	private JLabel srlMiles;
	private JLabel srlYear;
	private JLabel srlTestID;
	private JLabel srlTestType;
	private JLabel srlTestClass;
	private JLabel srlTestDate;
	private JLabel srlTestResult;
	private JLabel srlVehicleID;
	private JLabel srlColour;
	private JLabel srlFuelType;
	private JLabel srlCylinderCapacity;
	
	private JTable searchedData;
	private JScrollPane adScrollPane;

	private int width;
	private int height;

	public GUI() {

		frame = new JFrame("MOT Test Data Application");

		// panels
		north = new JPanel();
		east = new JPanel();
		south = new JPanel();
		west = new JPanel();
		center = new JPanel();
		buttonPanel = new JPanel();
		searchedDataPanel = new JPanel();
		singleRowPanel = new JPanel();

		// textFields
		make = new JTextField();
		model = new JTextField();
		postcode = new JTextField();
		miles = new JTextField();
		year = new JTextField();
		search = new JButton("Search");
		clearSearch = new JButton("Clear");
		analyse = new JButton("Analyse");
		help = new JButton("Help?");

		// labels
		lMake = new JLabel("Make:");
		lModel = new JLabel("Model:");
		lPostcode = new JLabel("Postcode:");
		lMiles = new JLabel("Miles:");
		lYear = new JLabel("Year:");
		
		// single row labels
		srlMake = new JLabel("");
		srlModel = new JLabel("Model:");
		srlPostcode = new JLabel("Postcode:");
		srlMiles = new JLabel("Miles:");
		srlYear = new JLabel("Year:");
		srlTestID = new JLabel("TestID:");
		srlTestType = new JLabel("Test Type:");
		srlTestClass= new JLabel("Test Class:");
		srlTestDate= new JLabel("Test Date:");
		srlTestResult= new JLabel("Test Result:");
		srlVehicleID= new JLabel("Vehicle ID:");
		srlColour= new JLabel("Colour:");
		srlFuelType= new JLabel("Fuel Type:");
		srlCylinderCapacity= new JLabel("Cylinder Capacity:");

		// jtable
		searchedData = new JTable();
		adScrollPane = new JScrollPane(searchedData);

		// dimensions of frame
		width = 1025;
		height = 700;

	}

	/**
	 * setUpGUI
	 * 
	 * Once the constructor is built this method is called and this pulls in all Swing related elements that have been separated for maintainability such as panels, labels, and buttons. 
	 * This method creates the JFrame and adds all elements needed to the contentPane of of the frame.
	 */
	public void setUpGUI() {
		Container cp = frame.getContentPane();
		frame.setSize(width, height);

		setUpPanels();
		setUpLabels();
		setUpButtons();
		setUpJTables();
		textFieldLabel();
		searchData();
		clearSearch();
		analyseData();
		helpRequest();
		sidePanel();

		cp.setLayout(new BorderLayout(10, 10));
		cp.add(center, BorderLayout.CENTER);
		cp.add(north, BorderLayout.NORTH);
		cp.add(east, BorderLayout.EAST);
		cp.add(south, BorderLayout.SOUTH);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBackground(Color.LIGHT_GRAY);
		cp.setBackground(Color.decode("#EAE6E5"));
		frame.setVisible(true);

	}

	/**
	 * setUpPanels
	 * 
	 * Creates and adds all elements needed to panels for the layout of the GUI. 
	 */
	public void setUpPanels() {

		// contains all buttons added in the north panel
		buttonPanel.add(lMake);
		buttonPanel.add(make);
		buttonPanel.add(lModel);
		buttonPanel.add(model);
		buttonPanel.add(lPostcode);
		buttonPanel.add(postcode);
		buttonPanel.add(lMiles);
		buttonPanel.add(miles);
		buttonPanel.add(lYear);
		buttonPanel.add(year);
		buttonPanel.add(search);
		buttonPanel.add(clearSearch);
		buttonPanel.setBackground(Color.LIGHT_GRAY);
		buttonPanel.setLayout(new FlowLayout());

		// contains to the JTable and is where the data is displayed
		searchedDataPanel.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.lightGray));
		searchedDataPanel.setLayout(new BorderLayout());
		searchedDataPanel.add(adScrollPane, BorderLayout.CENTER);
		
		
		// Green box to the right on the GUI displaying all elements of the row selected. This is a series of labels that get updated once clicked.
		singleRowPanel.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.decode("#5B9279")));
		singleRowPanel.setBackground(Color.decode("#5B9279"));
		singleRowPanel.add(srlMake);
		singleRowPanel.add(srlModel);
		singleRowPanel.add(srlPostcode);
		singleRowPanel.add(srlMiles);
		singleRowPanel.add(srlYear);
		singleRowPanel.add(srlTestID);
		singleRowPanel.add(srlTestType);
		singleRowPanel.add(srlTestClass);
		singleRowPanel.add(srlTestDate);
		singleRowPanel.add(srlTestResult);
		singleRowPanel.add(srlVehicleID);
		singleRowPanel.add(srlColour);
		singleRowPanel.add(srlFuelType);
		singleRowPanel.add(srlCylinderCapacity);
		singleRowPanel.setLayout(new FlowLayout());

		// hosts the searchedDataPanel with the JTable in it and creates border
		center.setPreferredSize(new Dimension(0, 150));
		center.setLayout(new BorderLayout());
		center.add(searchedDataPanel, BorderLayout.CENTER);
		center.setBackground(Color.LIGHT_GRAY);
		center.setBorder(BorderFactory.createMatteBorder(0, 10, 0, 0, Color.decode("#EAE6E5")));

		// hosts the buttonPanel where all the search criteria is entered
		north.setPreferredSize(new Dimension(0, 75));
		north.setBackground(Color.LIGHT_GRAY);
		north.setBorder(BorderFactory.createMatteBorder(10, 10, 0, 10, Color.decode("#EAE6E5")));
		north.setLayout(new BorderLayout());
		north.add(buttonPanel, BorderLayout.CENTER);

		// hosts the singleRowPanel
		east.setPreferredSize(new Dimension(250, 500));
		east.setLayout(new BorderLayout());
		east.add(singleRowPanel, BorderLayout.CENTER);
		east.setBackground(Color.decode("#5B9279"));
		east.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 10, Color.decode("#EAE6E5")));

		// hosts the function buttons at the botton of the GUI
		south.setPreferredSize(new Dimension(0, 50));
		south.setBackground(Color.decode("#EAE6E5"));
		south.setLayout(new BorderLayout(10, 0));
		south.add(help, BorderLayout.WEST);
		south.add(analyse, BorderLayout.CENTER);
		south.setBorder(BorderFactory.createMatteBorder(0, 10, 10, 10, Color.decode("#EAE6E5")));

		// used to set border between center and the edge of GUI
		west.setPreferredSize(new Dimension(250, 500));
		west.setBackground(Color.WHITE);

	}

	/**
	 * setUpButtons
	 * 
	 * Creates buttons and sets design like color and font
	 */
	public void setUpButtons() {

		make.setPreferredSize(new Dimension(100, 30));
		make.setText("BMW");
		make.setBackground(Color.decode("#EAE6E5"));
		make.setForeground(Color.decode("#A3938F"));
		make.setFont(new Font("SansSerif", Font.ITALIC, 13));

		model.setPreferredSize(new Dimension(100, 30));
		model.setText("3 Series");
		model.setBackground(Color.decode("#EAE6E5"));
		model.setForeground(Color.decode("#A3938F"));
		model.setFont(new Font("SansSerif", Font.ITALIC, 13));

		postcode.setPreferredSize(new Dimension(100, 30));
		postcode.setText("GL");
		postcode.setBackground(Color.decode("#EAE6E5"));
		postcode.setForeground(Color.decode("#A3938F"));
		postcode.setFont(new Font("SansSerif", Font.ITALIC, 13));

		miles.setPreferredSize(new Dimension(100, 30));
		miles.setText("1-999999");
		miles.setBackground(Color.decode("#EAE6E5"));
		miles.setForeground(Color.decode("#A3938F"));
		miles.setFont(new Font("SansSerif", Font.ITALIC, 13));

		year.setPreferredSize(new Dimension(100, 30));
		year.setText("2008");
		year.setBackground(Color.decode("#EAE6E5"));
		year.setForeground(Color.decode("#A3938F"));
		year.setFont(new Font("SansSerif", Font.ITALIC, 13));

		search.setPreferredSize(new Dimension(100, 22));
		search.setBackground(Color.decode("#5B9279"));
		search.setOpaque(true);
		search.setBorderPainted(false);
		search.setForeground(Color.decode("#EAE6E5"));

		analyse.setBackground(Color.decode("#5B9279"));
		analyse.setOpaque(true);
		analyse.setBorderPainted(false);
		analyse.setForeground(Color.decode("#EAE6E5"));
		
		help.setBackground(Color.decode("#B74F6F"));
		help.setOpaque(true);
		help.setBorderPainted(false);
		help.setForeground(Color.decode("#EAE6E5"));

		clearSearch.setPreferredSize(new Dimension(100, 22));
		clearSearch.setBackground(Color.decode("#5B9279"));
		clearSearch.setOpaque(true);
		clearSearch.setBorderPainted(false);
		clearSearch.setForeground(Color.decode("#EAE6E5"));

	}

	/**
	 * setUpLabels
	 * 
	 * Creates labels for in text field as placeholder and in the side panel.
	 */
	public void setUpLabels() {
		lMake.setForeground(Color.decode("#20221B"));
		lMake.setFont(new Font("SansSerif", Font.BOLD, 13));
		lModel.setForeground(Color.decode("#20221B"));
		lModel.setFont(new Font("SansSerif", Font.BOLD, 13));
		lPostcode.setForeground(Color.decode("#20221B"));
		lPostcode.setFont(new Font("SansSerif", Font.BOLD, 13));
		lMiles.setForeground(Color.decode("#20221B"));
		lMiles.setFont(new Font("SansSerif", Font.BOLD, 13));
		lYear.setForeground(Color.decode("#20221B"));
		lYear.setFont(new Font("SansSerif", Font.BOLD, 13));
		
		
		srlMake.setPreferredSize(new Dimension(210, 31));	
		srlMake.setBackground(Color.decode("#5B9279"));
		srlMake.setOpaque(true);
		srlMake.setText("Make: ");
		srlMake.setForeground(Color.WHITE);
		srlMake.setFont(new Font("SansSerif", Font.BOLD, 13));
		
		srlModel.setPreferredSize(new Dimension(210, 31));	
		srlModel.setBackground(Color.decode("#5B9279"));
		srlModel.setOpaque(true);
		srlModel.setText("Model: ");
		srlModel.setForeground(Color.WHITE);
		srlModel.setFont(new Font("SansSerif", Font.BOLD, 13));
		
		srlPostcode.setPreferredSize(new Dimension(210, 31));	
		srlPostcode.setBackground(Color.decode("#5B9279"));
		srlPostcode.setOpaque(true);
		srlPostcode.setText("Postcode: ");
		srlPostcode.setForeground(Color.WHITE);
		srlPostcode.setFont(new Font("SansSerif", Font.BOLD, 13));
		
		srlMiles.setPreferredSize(new Dimension(210, 31));	
		srlMiles.setBackground(Color.decode("#5B9279"));
		srlMiles.setOpaque(true);
		srlMiles.setText("Miles: ");
		srlMiles.setForeground(Color.WHITE);
		srlMiles.setFont(new Font("SansSerif", Font.BOLD, 13));
		
		srlYear.setPreferredSize(new Dimension(210, 31));	
		srlYear.setBackground(Color.decode("#5B9279"));
		srlYear.setOpaque(true);
		srlYear.setText("Year: ");
		srlYear.setForeground(Color.WHITE);
		srlYear.setFont(new Font("SansSerif", Font.BOLD, 13));
		
		srlTestID.setPreferredSize(new Dimension(210, 31));	
		srlTestID.setBackground(Color.decode("#5B9279"));
		srlTestID.setOpaque(true);
		srlTestID.setText("Test ID: ");
		srlTestID.setForeground(Color.WHITE);
		srlTestID.setFont(new Font("SansSerif", Font.BOLD, 13));
		
		srlTestType.setPreferredSize(new Dimension(210, 31));	
		srlTestType.setBackground(Color.decode("#5B9279"));
		srlTestType.setOpaque(true);
		srlTestType.setText("Test Type: ");
		srlTestType.setForeground(Color.WHITE);
		srlTestType.setFont(new Font("SansSerif", Font.BOLD, 13));
		
		srlTestClass.setPreferredSize(new Dimension(210, 31));	
		srlTestClass.setBackground(Color.decode("#5B9279"));
		srlTestClass.setOpaque(true);
		srlTestClass.setText("Test Class: ");
		srlTestClass.setForeground(Color.WHITE);
		srlTestClass.setFont(new Font("SansSerif", Font.BOLD, 13));
		
		srlTestDate.setPreferredSize(new Dimension(210, 31));	
		srlTestDate.setBackground(Color.decode("#5B9279"));
		srlTestDate.setOpaque(true);
		srlTestDate.setText("Test Date: ");
		srlTestDate.setForeground(Color.WHITE);
		srlTestDate.setFont(new Font("SansSerif", Font.BOLD, 13));
		
		srlTestResult.setPreferredSize(new Dimension(210, 31));	
		srlTestResult.setBackground(Color.decode("#5B9279"));
		srlTestResult.setOpaque(true);
		srlTestResult.setText("Test Result: ");
		srlTestResult.setForeground(Color.WHITE);
		srlTestResult.setFont(new Font("SansSerif", Font.BOLD, 13));
		
		srlVehicleID.setPreferredSize(new Dimension(210, 31));	
		srlVehicleID.setBackground(Color.decode("#5B9279"));
		srlVehicleID.setOpaque(true);
		srlVehicleID.setText("Vehicle ID: ");
		srlVehicleID.setForeground(Color.WHITE);
		srlVehicleID.setFont(new Font("SansSerif", Font.BOLD, 13));
		
		srlColour.setPreferredSize(new Dimension(210, 31));	
		srlColour.setBackground(Color.decode("#5B9279"));
		srlColour.setOpaque(true);
		srlColour.setText("Colour: ");
		srlColour.setForeground(Color.WHITE);
		srlColour.setFont(new Font("SansSerif", Font.BOLD, 13));
		
		srlFuelType.setPreferredSize(new Dimension(210, 31));	
		srlFuelType.setBackground(Color.decode("#5B9279"));
		srlFuelType.setOpaque(true);
		srlFuelType.setText("Fuel Type: ");
		srlFuelType.setForeground(Color.WHITE);
		srlFuelType.setFont(new Font("SansSerif", Font.BOLD, 13));
		
		srlCylinderCapacity.setPreferredSize(new Dimension(210, 31));	
		srlCylinderCapacity.setBackground(Color.decode("#5B9279"));
		srlCylinderCapacity.setOpaque(true);
		srlCylinderCapacity.setText("Cylinder Capacity: ");
		srlCylinderCapacity.setForeground(Color.WHITE);
		srlCylinderCapacity.setFont(new Font("SansSerif", Font.BOLD, 13));
		
	
	}

	/**
	 * setUpJTables
	 * 
	 * Adds JTable functionality 
	 */
	public void setUpJTables() {
		searchedData.setAutoCreateRowSorter(true);
	}

	/**
	 * textFieldLabel
	 * 
	 * This method listens for when user click into the text fields on the search panel, once clicked the text should be removed and set to black so its clear 
	 * the user is now entering criteria that will be used in search.
	 */
	public void textFieldLabel() {
		MouseListener textFieldListener = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent ae) {
				Object o = ae.getSource();

				if (o == make) {
					make.setForeground(Color.BLACK);
					make.setText("");
				} else if (o == model) {
					model.setForeground(Color.BLACK);
					model.setText("");
				} else if (o == postcode) {
					postcode.setForeground(Color.BLACK);
					postcode.setText("");
				} else if (o == miles) {
					miles.setForeground(Color.BLACK);
					miles.setText("");
				} else if (o == year) {
					year.setForeground(Color.BLACK);
					year.setText("");
				}
			}
		};

		make.addMouseListener(textFieldListener);
		model.addMouseListener(textFieldListener);
		postcode.addMouseListener(textFieldListener);
		miles.addMouseListener(textFieldListener);
		year.addMouseListener(textFieldListener);

	}
	
	/**
	 * searchData
	 * 
	 * Listens for when the search button is clicked as this indicates the user has filled in criteria and is looking to get results. 
	 * Once clicked the method pulls the data entered in the text fields to construct an SQL query based on the data, if a text field is blank it 
	 * is ignored in the construction of the query. Once constructed the query is executed and the method then checks if the result set is empty, if empty a pop up 
	 * window will be displayed stating no results found. If data is returned that data is iterated over and displayed in the JTable.
	 */
	public void searchData() {
		ActionListener searchButtonListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				System.out.println("Search");
				searchedData.setModel(new DefaultTableModel());

				try (Connection con = DriverManager.getConnection("jdbc:sqlite:./lib/testsDB.db")) {

					// gets the data entered
					String base = SQL2.search();
					String tfMake = make.getText().toUpperCase();
					String tfModel = model.getText().toUpperCase();
					String tfPostcode = postcode.getText().toUpperCase();
					String tfMiles = miles.getText();
					String tfYear = year.getText();

					// constructs query
					if (!tfMake.equals("")) {
						base += " AND Vehicle.make == " + "'" + tfMake + "'";
					}
					if (!tfModel.equals("")) {
						base += " AND Vehicle.model == " + "'" + tfModel + "'";
					}
					if (!tfPostcode.equals("")) {
						base += " AND test_postcode == " + "'" + tfPostcode + "'";
					}
					if (!tfMiles.equals("")) {

						if (tfMiles.contains("-")) {
							String[] range = tfMiles.split("-");
							base += " AND test_milage BETWEEN " + "'" + range[0] + "'" + " AND " + "'" + range[1] + "'";
						} else {
							base += " AND test_milage == " + "'" + tfMiles + "'";
						}
					}
					if (!tfYear.equals("")) {
						int eoYear = Integer.parseInt(tfYear) + 1;
						base += " AND Vehicle.first_use_date BETWEEN " + "'" + tfYear + "'" + " AND " + "'" + Integer.toString(eoYear) + "'";
					}
					base += ";";

					System.out.println(base);
					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery(base);
					ResultSetMetaData rsmd = rs.getMetaData();
					DefaultTableModel model = (DefaultTableModel) searchedData.getModel();

					// checks if result set is empty to display pop up window
					if (rs.next() == false) {
						System.out.println("ResultSet in empty in Java");
					    Popup p;
				        JFrame f = new JFrame("No Results.");
				        JLabel l = new JLabel("No Results Found.");
				        f.setSize(300, 100);				 
				        PopupFactory pf = new PopupFactory();				 
				        JPanel p2 = new JPanel();				 
				        p2.setBackground(Color.WHITE);				 
				        p2.add(l);				 
				        p = pf.getPopup(f, p2, 180, 100);			 
				 
				        f.add(p2);
				        f.show();
					    
						
					} else { // adds data to the JTable

						int cols = rsmd.getColumnCount();
						String[] cName = new String[cols];
						for (int c = 0; c < cols; c++) {
							cName[c] = rsmd.getColumnName(c + 1);
							model.setColumnIdentifiers(cName);
						}

						while (rs.next()) {
							Vector<String> vector = new Vector<String>();
							for (int columnIndex = 1; columnIndex <= cols; columnIndex++) {
								vector.add(rs.getString(columnIndex));
							}

							model.addRow(vector);
						}

					}
				} catch (SQLException e) {
				}

			}
		};
		
		search.addActionListener(searchButtonListener);
	}
	
	/**
	 * clearSearch
	 * 
	 * This cancels/ clears the searched data, when the Jtable is populated and a row is selected filling in the sidepanel the user 
	 * can click the clear button and this will give the table a new model and set the labels back to original text.
	 * 
	 */
	public void clearSearch() {
		ActionListener searchButtonListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				System.out.println("Clear");
				searchedData.setModel(new DefaultTableModel());
				
				srlMake.setText("Make: ");
				srlModel.setText("Model: ");
				srlPostcode.setText("Postcode: ");
				srlMiles.setText("Miles: ");
				srlYear.setText("Year: ");
				srlTestID.setText("Test ID: "); 
				srlTestType.setText("Test Type: "); 
				srlTestClass.setText("Test Class : ");
				srlTestDate.setText("Test Date: " );
				srlTestResult.setText("Test Result: ");
				srlVehicleID.setText("Vehicle ID: ");
				srlColour.setText("Colour: " );
				srlFuelType.setText("Fuel Type: ");
				srlCylinderCapacity.setText("Cylinder Capacity: ");
				

			}
		};

		clearSearch.addActionListener(searchButtonListener);
	}

	/**
	 * analyseData
	 * 
	 * listens for button Pass Rate Year on GUI to be pressed and generates a JFreeChart based on criteria entered.
	 */
	public void analyseData() {
		ActionListener analyseButtonListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				System.out.println("Analyse");
				
				String base = SQL2.analyse();
				String tfMake = make.getText().toUpperCase();
				String tfModel = model.getText().toUpperCase();
				
				base += " AND Vehicle.make == " + "'" + tfMake + "'" + " AND Vehicle.model == " + "'" + tfModel + "'";
				System.out.println(base);
				JFChart age = new JFChart(base, "MOT Test Pass Rate " + tfMake + " " + tfModel + " By Age(Years)", "Age(Years)");
				JFChart miles = new JFChart(base, "MOT Test Pass Rate " + tfMake + " " + tfModel + " By Miles", "Miles in K");
				
			}
		};

		analyse.addActionListener(analyseButtonListener);
	}
	

	/**
	 *helpRequest
	 *
	 * Creates a new frame with help instructions displayed to user. The details displayed are pulled in from the ./lib/help.txt file.
	 */
	public void helpRequest() {
		ActionListener helpButtonListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				System.out.println("Help");
				JFrame helpPage = new JFrame();
				JTextArea textArea = new JTextArea();
				
				try {
					FileReader reader = new FileReader("./lib/help.txt");
					BufferedReader br = new BufferedReader(reader);
					textArea.read(br, null);
					br.close();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				helpPage.add(textArea);
				helpPage.getContentPane();
				helpPage.pack();
				helpPage.setVisible(true);
				

			}
		};

		help.addActionListener(helpButtonListener);
	}
	
	/**
	 * sidePanel
	 * 
	 * Listens for when the JTable is clicked and takes in the values of where the user has clicked. It then updates the labels
	 * in the sidePanel label with the results of an SQL query to get all 13 elements of the test.
	 */
	public void sidePanel() {
		ListSelectionListener JTableListener = new ListSelectionListener() {
		
		@Override
		public void valueChanged(ListSelectionEvent lse) {
			
			if(!lse.getValueIsAdjusting()) {
				
				System.out.println("Table");
				int column = 0;
				int row = searchedData.getSelectedRow();
				String value = searchedData.getModel().getValueAt(row, column).toString();
				
				try (Connection con = DriverManager.getConnection("jdbc:sqlite:./lib/testsDB.db")) {
					
					PreparedStatement ps = con.prepareStatement(SQL2.sidePanel());
					
					ps.setInt(1, Integer.parseInt(value));
					ResultSet rs = ps.executeQuery();
					
					
					srlMake.setText("Make: " + rs.getString("make"));
					srlModel.setText("Model: " + rs.getString("model"));
					srlPostcode.setText("Postcode: " + rs.getString("test_postcode"));
					srlMiles.setText("Miles: " + rs.getString("test_milage"));
					srlYear.setText("Year: " + rs.getString("first_use_date"));
					srlTestID.setText("Test ID: " + rs.getString("test_id")); 
					srlTestType.setText("Test Type: " + rs.getString("test_type")); 
					srlTestClass.setText("Test Class : " + rs.getString("test_class"));
					srlTestDate.setText("Test Date: " + rs.getString("test_date"));
					srlTestResult.setText("Test Result: " + rs.getString("test_result"));
					srlVehicleID.setText("Vehicle ID: " + rs.getString("vehicle_id"));
					srlColour.setText("Colour: " + rs.getString("colour"));
					srlFuelType.setText("Fuel Type " + rs.getString("fuel_type"));
					srlCylinderCapacity.setText("Cylinder Capacity: " + rs.getString("cylinder_capacity"));
				}
				
				catch (SQLException e) {
					System.out.println("Connection Failed");
					e.printStackTrace();
				}
			}
			

		}
	};

	searchedData.getSelectionModel().addListSelectionListener(JTableListener);
	}
}

	