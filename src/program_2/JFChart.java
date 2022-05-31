package program_2;

import java.awt.Dimension;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * JFChart
 * 
 * This class is used to create an object of JFreeCharts requested in Class.GUI
 * when buttons 'Pass Rate Year' and 'Pass Rate Milage' are selected.
 * 
 * @author jordanprescott
 *
 */
public class JFChart {

	List<String> years = Arrays.asList("2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019",
			"2020");
	List<String> miles = Arrays.asList("10000", "20000", "30000", "40000", "50000", "60000", "70000", "80000", "90000",
			"100000");

	private String query;
	private String title;
	private String measurement;

	/**
	 * JFChart
	 * 
	 * @param query       - SQL query base requested in GUI and passed into
	 *                    constructor to gather correct data.
	 * @param title       - Sets correct title depending on which button for either
	 *                    age or miles.
	 * @param measurement - Takes in the measurement of either miles or age.
	 */
	public JFChart(String query, String title, String measurement) {

		this.query = query;
		this.title = title;
		this.measurement = measurement;
		setUpJFChart();

	}

	/**
	 * setUpChart
	 * 
	 * This generates the chart and acts as anchor calling other methods to gather
	 * data and work out passrate to be then shown in this table
	 */
	public void setUpJFChart() {

		try {

			DefaultCategoryDataset dataSet = getData();
			JFreeChart lineChart = ChartFactory.createLineChart(title, measurement, "Pass Rate(%)", dataSet,
					PlotOrientation.VERTICAL, false, true, true);
			BarRenderer renderer = null;
			CategoryPlot plot = null;
			renderer = new BarRenderer();
			ChartFrame frame = new ChartFrame("Pass Rate", lineChart);
			frame.setVisible(true);
			frame.setSize(new Dimension(650, 400));

		} catch (Exception e) {

		}
	}

	/**
	 * getData
	 * 
	 * This looks if the chart is for miles or age depending on that selects a class
	 * variable list to loop through. Each entry in the list if a colunm within the
	 * chart and a set of data that is then added to the dataSet to be returned and
	 * displayed in the chart
	 * 
	 * @return - dataSet for setUpJFChart
	 */
	public DefaultCategoryDataset getData() {

		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		String passRate = "Pass Rate";

		try {
			Connection con = DriverManager.getConnection("jdbc:sqlite:./lib/testsDB.db");
			Statement st = con.createStatement();

			for (String y : years) {
				int eoYear = Integer.parseInt(y) + 1;

				ResultSet rs = st.executeQuery(this.query + " AND Vehicle.first_use_date BETWEEN " + "'" + y + "'"
						+ " AND " + "'" + Integer.toString(eoYear) + "';");
				System.out.println(this.query + " AND Vehicle.first_use_date BETWEEN " + "'" + y + "'" + " AND " + "'"
						+ Integer.toString(eoYear) + "';");

				int total = 0;
				int passed = 0;

				while (rs.next()) {
					total++;
					if (rs.getString(1).equals("P") || rs.getString(1).equals("P")) {
						passed++;
					}
				}
				System.out.println(Integer.toString(total) + " " + Integer.toString(passed) + " " + y);
				dataSet.addValue(passRate(total, passed), passRate, y);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dataSet;
	}

	/**
	 * passRate
	 * 
	 * Calculates the pass rate of a dataset
	 * 
	 * @param total  - total number of vehicles
	 * @param passed - number of vehicles that passed
	 * @return - pass rate of the passed divded by total times by 100
	 */
	public double passRate(Integer total, Integer passed) {
		
		int prInteger = 0;
		
		if (total > 0 && passed > 0) {
			double pr = passed * 100 / total;
			prInteger = (int) pr;			
		}
		
		return prInteger;
	}
}
