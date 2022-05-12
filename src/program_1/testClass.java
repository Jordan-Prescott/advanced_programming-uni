package program_1;

import java.util.ArrayList;

public class testClass {
	
	public static void main(String args[]) {
		
		// builds db and tables
		fileManager.buildDB();
		fileManager.SQLQuery(SQL.createTableVehicle());
		fileManager.SQLQuery(SQL.createTableTest());
		
		// builds indexes for search functions
//		fileManager.SQLQuery(SQL.creatIndexFirst_used_date());
//		fileManager.SQLQuery(SQL.creatIndexModel());
//		fileManager.SQLQuery(SQL.creatIndexTest_milage());
//		fileManager.SQLQuery(SQL.creatIndexVehicle_id());
		
		fileManager.SQLQuery(SQL.createIndices());
		
		fileManager.getCSVs();
	}

}
