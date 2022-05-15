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
		
		ArrayList<String> errors = new ArrayList<String>();
		
		// TODO: Print out progress every time there is a new and for every 1000 lines
		for (Object file : files) {
			try (BufferedReader br = Files.newBufferedReader(Paths.get(file.toString()))) {

//				br.readLine(); // read first line to skip headers
				String line;
				while ((line = br.readLine()) != null) {
					
					

					if (line.contains("\\\"")) {
						
						line = line.replace("\\\"", "");

					}
					
					if (line.contains("\"")) {
						String[] values = line.split("\"");
						
						if (values[1].equals(",")) {
							line = line.replace(",", ""); // TODO: look at the value and adjust that NOT the line 

						}
						else {
							
						}
						line = String.join("", values);
						
					}
										
					System.out.println(line);
					
					String[] values = line.split(",");	
					if (values.length > 14) { 
						line += ",ERROR - Higehr than 14";
						errors.add(line);
					}
					
					if (values.length < 14) { // data incomplete
						line += ",ERROR - Less than 14";
						errors.add(line); 
					}
					
				}
			} catch (IOException ioe) {
				System.err.println("IO Exception: " + ioe);
			}

		}
		
		
//		try (BufferedWriter bw = Files.newBufferedWriter(Paths.get("./lib/erros.csv"))) {
//			
//			for (String e : errors) {
//				bw.write(e);
//				bw.newLine();
//			}
//			
//		} catch (IOException ioe) {
//			System.err.println("IO Exception: " + ioe);
//		}
		System.out.println("DONE");
	}
	

}
