package program_1;


public class testClass {
	
	public static void main(String args[]) {
		
		// builds db and tables
		fileManager.buildDB();
		fileManager.buildTable(SQL.createTestTable());
		fileManager.buildTable(SQL.createVehicleTable());
		// TODO: create index 
		
		
	}

}
