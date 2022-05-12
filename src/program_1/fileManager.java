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

	static Path dir = Paths.get("./lib/");

	public static void buildDB() { // first deletes any DB that already exists and then creates a new one

		try {
			DirectoryStream<Path> ds = Files.newDirectoryStream(dir);

			for (Path file : ds) {

				if (file.getFileName().toString().contains(".db")) {

					Files.delete(file);

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
	
	public static ArrayList getCSVs() { // moves all csv files into input if not already and returns an array list of csv files

		ArrayList<String> files = new ArrayList<String>();

		Path path = Paths.get("./lib/input/");
		
		if (!Files.exists(path)) {
			
			try {
				Files.createDirectories(path);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		try {
			DirectoryStream<Path> ds = Files.newDirectoryStream(dir);
			
			for (Path file : ds) {
				
				if (file.getFileName().toString().contains(".csv")) {
					
					files.add(file.getFileName().toString());
					String dest = "./lib/input/";
					Path filePath = Paths.get(dest+ file.getFileName());
					Files.move(file, filePath);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return files;
	}
}
