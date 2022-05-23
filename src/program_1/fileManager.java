package program_1;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


/**
 * fileManager class is CRUDs all files and folders used in the project
 * it is used as a central hub for all integrations with the file system and the files.
 * 
 * There is one exception to the above in main where the SQL INSERTS of the data is performed for 
 * efficiency.
 * 
 * @author jordanprescott
 * 
 */
public class fileManager {

	static Path wd = Paths.get("./");
	static Path lib = Paths.get("./lib/");
	static Path input = Paths.get("./lib/input/");
	static Path output = Paths.get("./lib/output/");
	static Path[] paths = {wd,lib,input,output};

	/**
	 * buildDB 
	 * 
	 * first loops through the path list checking if there is an existing database inside the folders if 
	 * a database is found is deleted, if there is no data base found it moves on.
	 * 
	 * Once all folders have been checked the a new database called tests.db is created inside ./lib/ for future reference in the program.
	 * 
	 */
	public static void buildDB() { 


		try {
			for (Path path : paths) {

				DirectoryStream<Path> ds = Files.newDirectoryStream(path);

				for (Path file : ds) {

					if (file.getFileName().toString().contains(".db")) {

						Files.delete(file);

					}

				}
			}

			Path newFile = Paths.get("./lib/testsDB.db");
			Files.createFile(newFile);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * buildDirs
	 * 
	 * Checks if the input and output folders exist as they are used in later in the program, if not create them.
	 * t
	 */
	public static void buildDirs() {
		if (!Files.exists(input)) { 

			try {
				Files.createDirectories(input);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		if (!Files.exists(output)) { 

			try {
				Files.createDirectories(output);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * getCSVs
	 * 
	 * First loops through the Path list and checks for CSV file, if found the file is moved ./lib/input.
	 * Secondly it loops through the ./lib/input to get the new path of the file and add this path to ArrayList files.
	 * Finally the files array list is returned.
	 * 
	 * @return ArrayList file - contains paths of all CSV files
	 */
	public static ArrayList getCSVs() { // moves all csv files into input if not already and returns an array list of csv file names
		
		ArrayList<String> files = new ArrayList<String>();

		try { // moves all csv to input dir

			for (Path path : paths) {

				DirectoryStream<Path> ds = Files.newDirectoryStream(path);

				for (Path file : ds) {

					if (file.getFileName().toString().contains(".csv")) {

						String dest = "./lib/input/";
						Path filePath = Paths.get(dest + file.getFileName());
						Files.move(file, filePath);
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		try { // adds all files in input dir to files AL
			DirectoryStream<Path> ds = Files.newDirectoryStream(input);

			for (Path file : ds) {
				String dest = "./lib/input/";
				files.add(dest + file.getFileName().toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return files;
	}
	
	/**
	 * SQLQuery
	 * 
	 * Takes in an SQL query as a parameter type String and executes to database with no update. 
	 * Used for creating tables and indices. 
	 * 
	 * @param q 
	 * 
	 */
	public static void SQLQuery(String q) { // SQL queries that don't return results

		try (Connection c = DriverManager.getConnection("jdbc:sqlite:./lib/testsDB.db")) {

			Statement s = c.createStatement();
			int rs = s.executeUpdate(q);

		} catch (SQLException se) {
			se.printStackTrace();
		}
	}
	
	/**
	 * writeErrors
	 * 
	 * Takes in arrayLis of errors collected in main and writes them to a file for viewing later.
	 * 
	 * @param errors
	 */
	public static void writeErrors(ArrayList errors) { // writes the errors and adjustments caught in main
		
		ArrayList<String> err = errors;

		try (BufferedWriter bw = Files.newBufferedWriter(Paths.get("./lib/output/errors.txt"))) {
			for (String e : err) {
				bw.write(e);
				bw.newLine();

			}

		} catch (IOException ioe) {
		}

	}
	
//	public static void pSQLQuery(ArrayList validData) {
//
//		
//		try (Connection con = DriverManager.getConnection("jdbc:sqlite:./lib/testsDB.db")) {
//		Statement b = con.createStatement();
//		int begin = b.executeUpdate("BEGIN TRANSACTION;");
//
//		for (String d : validData) {
//			String[] values = d.split(",");
//			PreparedStatement vehicle = con.prepareStatement(SQL.insertVehicle());
//			vehicle.setInt(1, Integer.parseInt(values[1]));
//			vehicle.setString(2, values[8]);
//			vehicle.setString(3, values[9]);
//			vehicle.setString(4, values[10]);
//			vehicle.setString(5, values[11]);
//			vehicle.setInt(6, Integer.parseInt(values[12]));
//			vehicle.setString(7, values[13]);
//			int vResult = vehicle.executeUpdate();
//
//			PreparedStatement test = con.prepareStatement(SQL.insertTest());
//			test.setInt(1, Integer.parseInt(values[0]));
//			test.setInt(2, Integer.parseInt(values[1]));
//			test.setString(3, values[4]);
//			test.setString(4, values[3]);
//			test.setString(5, values[2]);
//			test.setInt(6, Integer.parseInt(values[6]));
//			test.setString(7, values[7]);
//			test.setString(8, values[5]);
//			int tResult = test.executeUpdate();
//		}
//		
//		Statement c = con.createStatement();
//		int commit = c.executeUpdate("COMMIT;");
//		
//		} catch (SQLException se) {
//			se.printStackTrace();
//		}
//		
//	}
}

