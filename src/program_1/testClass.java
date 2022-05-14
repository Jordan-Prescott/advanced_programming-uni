package program_1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class testClass {
	
	public static void main(String args[]) {
		
		// builds db and collects csv
		fileManager.buildDB();
		ArrayList files = fileManager.getCSVs();	
		
		// builds tables and indices 
		fileManager.SQLQuery(SQL.createTableVehicle());
		fileManager.SQLQuery(SQL.createTableTest());
		fileManager.SQLQuery(SQL.createIndices());
		
//		for (Object file : files) 
//		{
//		
			try(BufferedReader br = Files.newBufferedReader(Paths.get("./lib/input/testingData.csv"))) {
//				try(BufferedWriter bw = Files.newBufferedWriter(Paths.get("./lib/testsDB.db"))) {
					String line;
					while ((line = br.readLine()) != null) {
//						bw.write(line);
//						bw.newLine();
						System.out.println(line);
						
					}
//				}
			}
			catch (IOException ioe) {
				System.err.println("IO Exception: " + ioe);
			}
//		}
	}

}
