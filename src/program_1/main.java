package program_1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class main {

	public static String colunm(int c) {

		String colunm = null;

		if (c == 0) {
			colunm = "test_id. ";
		} else if (c == 1) {
			colunm = "vehicle_id. ";
		} else if (c == 2) {
			colunm = "test_date. ";
		} else if (c == 3) {
			colunm = "test_class_id. ";
		} else if (c == 4) {
			colunm = "test_type. ";
		} else if (c == 5) {
			colunm = "test_result. ";
		} else if (c == 6) {
			colunm = "test_milage. ";
		} else if (c == 7) {
			colunm = "postcode_area. ";
		} else if (c == 8) {
			colunm = "make. ";
		} else if (c == 9) {
			colunm = "model. ";
		} else if (c == 10) {
			colunm = "colour. ";
		} else if (c == 11) {
			colunm = "fuel_type. ";
		} else if (c == 12) {
			colunm = "cylinder_capacity. ";
		} else if (c == 13) {
			colunm = "first_used_date. ";
		}

		return colunm;
	}

	public static void main(String[] args) {
		try {
			TimeUnit.SECONDS.sleep(1);
			System.out.println("** MOT Test Data Application - Program 1 **" + "\n" + "\nProgress Key: "
					+ "\n    Every 1000 lines = ." + "\n    Every new File = X\n");

			TimeUnit.SECONDS.sleep(2);

			System.out.println("Program Started.\n" + "Progress:");

			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		// builds db and collects csv
//		fileManager.buildDB();
//		fileManager.buildDirs();
		ArrayList files = fileManager.getCSVs();
//
//		// builds tables and indices
//		fileManager.SQLQuery(SQL.createTableVehicle());
//		fileManager.SQLQuery(SQL.createTableTest());
//		fileManager.SQLQuery(SQL.createIndices());

		ArrayList<String> errors = new ArrayList<String>(); // temp and holds all errors

		for (Object file : files) {

			System.out.println("X");

			int r = 0; // row count

			try (BufferedReader br = Files.newBufferedReader(Paths.get(file.toString()))) {
				br.readLine(); // read first line to skip headers
				String line;
				int l = 0;
				int tr = 1;

				while ((line = br.readLine()) != null) {

					r++;
					l++;

					if (l == 1000) {
						System.out.print(".");
						tr++;

						if (tr == 150) {
							System.out.println("\n");
							tr = 0;
						}
						l = 0;
					}

					// Washing data
					// STEP 1. Remove excess characters
					if(line.contains("\\\"") || line.contains(".") || line.contains(",,") || line.contains(",,,") || line.contains("\"")) {
						
						errors.add("Altered data on File: " + file.toString() + " Row: " + r );
						
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
					if (line.contains(",,")) { // checks for missing data and adds a wildcard to identify later
						line = line.replaceAll(",,", ",#,");
					}

					// STEP 2. Depending on position, values to the right and if the comma is
					// present at all
					if (line.contains("\"")) { // checks for "
						String[] values = line.split("\"");

						if (!values[1].equals(",")) {

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
																					// space replace with one
									values[1] = values[1].replaceAll(",", " ");
								}

								values[1] = values[1].replaceAll(",", "");
							}
						}

						line = String.join("", values);

					}
					if (line.contains(",,,")) { // checks for missing data and adds a wildcard to identify later
						line = line.replaceAll(",,,", ",#,#,");
					}

					// STEP 3. Now that data has been cleaned check if there is too much or too
					// little this will be invalid data
					String[] values = line.split(",");
					long count = line.chars().filter(ch -> ch == ',').count();

					if (count > 13) { // should not happen as data is cleaned
						
						errors.add("More than 14 colunms on File: " + file.toString() + " Row: " + r);
					} else if (count < 13) { // indicates data is incomplete
					
						errors.add("Less than 14 colunms on File " + file.toString() + "Row:" + r);
					} else if (line.contains("#")) { // indicates what data is missing

						ArrayList<Integer> colunm = new ArrayList<Integer>();

						int i = 0;
						for (String v : values) { // collect index value of missing data

							if (v.equals("#")) {
								colunm.add(i);
							}

							i++;

						}

						String errorMessage = "Missing data on File: " + file.toString() + " Row: " + r + " Colunms: ";

						for (int c : colunm) {

							errorMessage += colunm(c);

						}

						errors.add(errorMessage);

					}

				}

			} catch (IOException | StringIndexOutOfBoundsException e) {
			}

		}
		System.out.print(" Data Input Complete\n");
		
		for (String e : errors) {
			
			try (BufferedWriter bw = Files.newBufferedWriter(Paths.get("./lib/output/errors.txt"))) {
				bw.write(e);
				bw.newLine();
				
			} catch (IOException ioe) {
			}
			
		}
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Press any key to see errors.");
		String enter = sc.nextLine();
		sc.close();
		
		for (String e : errors) {
			System.out.println(e);
		}
		
		System.out.println("\nComplete list found at: ./lib/output/errors.txt");

	}

}
