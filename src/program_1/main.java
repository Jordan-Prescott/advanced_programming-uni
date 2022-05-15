package program_1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class main {

	public static void main(String[] args) {

		System.out.println("** MOT Test Data Application - Program 1 **");
		System.out.println("");

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

				br.readLine(); // read first line to skip headers
				String line;
				while ((line = br.readLine()) != null) {

					if (line.contains("\\\"")) { // takes out breaks example "88/"" is now "88"
						line.replace("\\\"", "");
						errors.add(line);
					}
					
					String[] values = line.split(",");


				}
			} catch (IOException ioe) {
				System.err.println("IO Exception: " + ioe);
			}

		}

		try (BufferedWriter bw = Files.newBufferedWriter(Paths.get("./lib/erros.csv"))) {

			for (String e : errors) {
				bw.write(e);
				bw.newLine();
			}

		} catch (IOException ioe) {
			System.err.println("IO Exception: " + ioe);
		}
		System.out.println("DONE");
	}

}
