
package program_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * main
 * 
 * Main is the core engine of this program, it calls the fileManager to first sort the working directories and then starts to read in the CSV files one by one, line by line.
 * As it reads each line it first washed removing any data incorrect characters not such as " / and highlights where there is missing data. Once washed it finally determines if the 
 * data is valid to to be applied the DB, if the data is valid it them calls SQL and pushes queries to the DB.
 * 
 * @author jordanprescott
 *
 */
public class main {

	static ArrayList<String> errors = new ArrayList<String>();
	static String fileName;

	/**
	 * wash 
	 * 
	 * This method reads every line in the CSV file and runs it through a series of checks, in each check it looks for certain characters and positioning of these characters. 
	 * If they are found the line is adjusted either removing characters that should not be there such as \ or replacing a character with what should be there like 3,8 should be 3.8
	 * Finally, when the data is washed it is returned back to the main method where it then goes through data validation checks.
	 * 
	 * @param line
	 * @param row
	 * @return line
	 */
	public static String wash(String line, int row) {

		if (line.contains("\\\"") || line.contains(".") || line.contains(",,") || line.contains(",,,")
				|| line.contains("\"") || line.contains("\",\"")) {

			errors.add("Altered data on File: " + fileName + " Row: " + row);

		}

		if (line.contains("\\\"")) { // takes out breaks example "88\"" is now "88"
			line = line.replace("\\\"", "");
		}
		if (line.contains(".")) { // checks for points that are not used in engine size

			int i = line.indexOf(".") + 1;
			Boolean flag = Character.isDigit(line.charAt(i));

			if (!flag) {

				line = line.replace(".", "");

			}

		}
		if (line.contains("\",\"")) {
			line = line.replace("\",\"", "");
		}
		if (line.contains(",,")) { // checks for missing data and adds a wildcard to identify later
			line = line.replaceAll(",,", ",#,");
		}
		if (line.contains("\"")) { // checks for "
			String[] values = line.split("\"");

			int i = values[1].indexOf(",");

			if (i != -1) {

				int k = values[1].indexOf(",") + 1;
				Boolean flag = Character.isDigit(values[1].charAt(k));

				String s = values[1];

				if (i == 0) { // if the comma is at the start remove it
					values[1] = values[1].replaceAll(",", "");
				} else if (flag) { // if the comma is between numeric values change for point
					values[1] = values[1].replaceAll(",", ".");
				} else if (!Character.isWhitespace(s.charAt(k))) { // if the value to the right is not a
																	// space replace comma with one
					values[1] = values[1].replaceAll(",", " ");
				}
				values[1] = values[1].replaceAll(",", "");
			}

			line = String.join("", values);

		}
		if (line.contains(",,,")) { // checks for missing data and adds a wildcard to identify later
			line = line.replaceAll(",,,", ",#,#,");
		}

		return line;
	}

	/**
	 * tableFeild
	 * 
	 * This method is used to determine where data is missing from a line in the CSV. The parameter c is passed to this method which is a array index, depending on the number passed this will indicate 
	 * which column in the line is missing data and the column name is then returned.
	 * @param c
	 * @return column
	 */
	public static String tableFeild(int c) { // returns the SQL table field in relation to order of the column in CSV

		String column = null;

		if (c == 0) {
			column = "test_id. ";
		} else if (c == 1) {
			column = "vehicle_id. ";
		} else if (c == 2) {
			column = "test_date. ";
		} else if (c == 3) {
			column = "test_class_id. ";
		} else if (c == 4) {
			column = "test_type. ";
		} else if (c == 5) {
			column = "test_result. ";
		} else if (c == 6) {
			column = "test_milage. ";
		} else if (c == 7) {
			column = "postcode_area. ";
		} else if (c == 8) {
			column = "make. ";
		} else if (c == 9) {
			column = "model. ";
		} else if (c == 10) {
			column = "colour. ";
		} else if (c == 11) {
			column = "fuel_type. ";
		} else if (c == 12) {
			column = "cylinder_capacity. ";
		} else if (c == 13) {
			column = "first_used_date. ";
		}

		return column;
	}

	public static void main(String[] args) {

		// builds db and collects csv
		fileManager.buildDB();
		fileManager.buildDirs();
		ArrayList files = fileManager.getCSVs(); // list of paths to csv files

		// builds tables and indices
		fileManager.SQLQuery(SQL.createTableVehicle());
		fileManager.SQLQuery(SQL.createTableTest());
		fileManager.SQLQuery(SQL.createIndices());

		try {
			TimeUnit.SECONDS.sleep(1);
			System.out.println("** MOT Test Data Application - Program 1 **" + "\n" + "\nProgress Key: "
					+ "\n    Every 1000 lines = ." + "\n    Every new file = X\n");

			TimeUnit.SECONDS.sleep(2);
			System.out.println("Program Started.\n" + "Progress:");
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
		}

		try (Connection con = DriverManager.getConnection("jdbc:sqlite:./lib/testsDB.db")) {
			Statement b = con.createStatement();
			int begin = b.executeUpdate("BEGIN TRANSACTION;");
			PreparedStatement vehicle = con.prepareStatement(SQL.insertVehicle());
			PreparedStatement test = con.prepareStatement(SQL.insertTest());
			
			for (Object file : files) { // loops through files

				fileName = file.toString();
				System.out.println("\nX");
				int r = 0; // row count

				try (BufferedReader br = Files.newBufferedReader(Paths.get(file.toString()))) { // loops through lines
																								// in
																								// files
					br.readLine(); // read first line to skip headers
					String line;

					// counts
					int l = 0;
					int t = 1;
					int ct = 0;

					while ((line = br.readLine()) != null) {

						Boolean discard = false;
						r++;
						l++;
						ct++;

						if (l == 1000) { // indicates every 1000 lines
							System.out.print(".");
							t++;
							if (t == 150) {
								System.out.println("\n");
								t = 0;
							}
							l = 0;
						}

						String washed = wash(line, r); // removes excess characters and formats data

						String[] values = washed.split(",");
						long count = washed.chars().filter(ch -> ch == ',').count();

						if (count > 13) { // should not happen as data is cleaned
							errors.add("More than 14 colunms on File: " + file.toString() + " Row: " + r);
							discard = true;
						} else if (count < 13) { // indicates data is incomplete
							errors.add("Less than 14 colunms on File " + file.toString() + "Row:" + r);
							discard = true;
						} else if (washed.contains("#")) { // indicates what data is missing
							discard = true;
							ArrayList<Integer> colunm = new ArrayList<Integer>();

							int i = 0;
							for (String v : values) { // collect index value of missing data

								if (v.equals("#")) {
									colunm.add(i);
								}
								i++;
							}

							String errorMessage = "Missing data on File: " + file.toString() + " Row: " + r
									+ " Colunms: ";

							for (int c : colunm) {
								errorMessage += tableFeild(c);
							}
							
							errors.add(errorMessage);

						}
						
						if (!discard) {
							
							vehicle.setInt(1, Integer.parseInt(values[1]));
							vehicle.setString(2, values[8]);
							vehicle.setString(3, values[9]);
							vehicle.setString(4, values[10]);
							vehicle.setString(5, values[11]);
							vehicle.setInt(6, Integer.parseInt(values[12]));
							vehicle.setString(7, values[13]);
							int vResult = vehicle.executeUpdate();
							
							test.setInt(1, Integer.parseInt(values[0]));
							test.setInt(2, Integer.parseInt(values[1]));
							test.setString(3, values[4]);
							test.setString(4, values[3]);
							test.setString(5, values[2]);
							test.setInt(6, Integer.parseInt(values[6]));
							test.setString(7, values[7]);
							test.setString(8, values[5]);
							int tResult = test.executeUpdate();
							
						}

					}

				} catch (IOException | StringIndexOutOfBoundsException | SQLException e) {
					Statement c = con.createStatement();
					int commit = c.executeUpdate("COMMIT;");
					Statement ba = con.createStatement();
					int beginAgain = ba.executeUpdate("BEGIN TRANSACTION;");
				}

			}
		
			Statement c = con.createStatement();
			int commit = c.executeUpdate("COMMIT;");
		
		} catch (SQLException se) {
			se.printStackTrace();
		}

		
		fileManager.writeErrors(errors);
		System.out.print(" Data Input Complete\n");

		Scanner sc = new Scanner(System.in);
		System.out.println("Press enter key to see errors.");
		String enter = sc.nextLine();
		sc.close();
		for (String e : errors) {
			System.out.println(e);
		}

		System.out.println("\nComplete list found at: ./lib/output/errors.txt");

	}

}
