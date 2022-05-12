package program_1;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class testClass {
	
	public static void main(String args[]) {
		
		// builds db and collects csv
		fileManager.buildDB();
		ArrayList file = fileManager.getCSVs();	
		
		// builds tables and indices 
		fileManager.SQLQuery(SQL.createTableVehicle());
		fileManager.SQLQuery(SQL.createTableTest());
		fileManager.SQLQuery(SQL.createIndices());
		
		System.out.println(file);
		Path filePath = Paths.get("testingData.csv");
		System.out.println(filePath);
	}

}
