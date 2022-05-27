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
	 */
	public static void buildDB() { 
		try {
			for (Path path : paths) { 

				DirectoryStream<Path> ds = Files.newDirectoryStream(path);

				for (Path file : ds) { // runs through dires and removes any existing db

					if (file.getFileName().toString().contains(".db")) {

						Files.delete(file);

					}

				}
			}

			Path newFile = Paths.get("./lib/testsDB.db"); // creates a new db
			Files.createFile(newFile);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * buildDirs
	 * 
	 * Checks if the input and output folders exist as they are used in later in the program, if not create them.
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
	 * @return ArrayList file contains paths of all CSV files
	 */
	public static ArrayList getCSVs() { 
		
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
	 * @param q - query to executed to the db
	 */
	public static void SQLQuery(String q) { 

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
	 * @param errors - ArrayList of errors to be written errors.txt
	 */
	public static void writeErrors(ArrayList errors) { 
		
		ArrayList<String> err = errors;

		try (BufferedWriter bw = Files.newBufferedWriter(Paths.get("./lib/output/errors.txt"))) {
			for (String e : err) {
				bw.write(e);
				bw.newLine();

			}

		} catch (IOException ioe) {
		}

	}
	
}

