package program_1;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class fileManager {

	static Path wd = Paths.get("./");
	static Path lib = Paths.get("./lib/");
	static Path input = Paths.get("./lib/input/");
	static Path output = Paths.get("./lib/output/");
	static Path[] paths = {Paths.get("./"),Paths.get("./lib/"),Paths.get("./lib/input"), Paths.get("./lib/output/")};

	public static void buildDB() { // first deletes any DB that already exists and then creates a new one


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

	public static void SQLQuery(String q) { // SQL queries that don't return results

		try (Connection c = DriverManager.getConnection("jdbc:sqlite:./lib/testsDB.db")) {

			Statement s = c.createStatement();
			int rs = s.executeUpdate(q);

		} catch (SQLException se) {
			se.printStackTrace();
		}
	}
	
	public static void buildDirs() { // builds input and output directories 
		if (!Files.exists(input)) { // builds input dir

			try {
				Files.createDirectories(input);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		if (!Files.exists(output)) { // builds output dir

			try {
				Files.createDirectories(output);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

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
}
