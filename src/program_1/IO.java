package program_1;

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

public class IO {

	static Path dir = Paths.get("./lib/");
	static Connection c = null;
	
	public static void buildDB() {


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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void buildTable(String table) {
		
		try {
			c = DriverManager.getConnection("jdbc:sqlite:testsDB.db");
			
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(table);
			
		} 
		catch (SQLException se) {
			se.printStackTrace();
		}
		finally {
			if (c != null) {
				try {
					c.close();
				}
				catch (SQLException sqe) {
					
				}
				
			}
		}
		
		
	}
}
