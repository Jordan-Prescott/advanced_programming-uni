package program_1;

/**
 * SQL
 * 
 * This class is a collection of SQL queries used in this program stored in a central place.
 * Whenever the program executes an SQL query to the database the query is pulled from one of this classes methods.
 * 
 * @author jordanprescott
 */
public class SQL { // SQL commands stored in one place for maintenance
	
	/**
	 * createTableVehicle
	 * 
	 * Creates Vehicle table.
	 * @return SQL query to create the vehicle table
	 */
	public static String createTableVehicle() { 
		return ""
				+ "CREATE TABLE Vehicle ("
				+ "vehicle_id int(20) NOT NULL,"
				+ "make varchar(30) NOT NULL,"
				+ "model varchar(30) NOT NULL,"
				+ "colour varchar(10) NOT NULL,"
				+ "fuel_type varchar(30) NOT NULL,"
				+ "cylinder_capacity int(4) NOT NULL,"
				+ "first_use_date varchar(10) NOT NULL,"
				+ "PRIMARY KEY (vehicle_id));"
				+ "";
	}

	/**
	 * createTableTest
	 * 
	 * Creates Test table.
	 * @return SQL query to create the test table
	 */
	public static String createTableTest() { 
		return ""
				+ "CREATE TABLE Test ("
				+ "test_id int(20) NOT NULL,"
				+ "vehicle_id int(20) NOT NULL,"
				+ "test_type varchar(3) NOT NULL,"
				+ "test_class varchar(2) NOT NULL,"
				+ "test_date varchar(10) NOT NULL,"
				+ "test_milage int(5) NOT NULL,"
				+ "test_postcode varchar(5) NOT NULL,"
				+ "test_result varchar(3) NOT NULL,"
				+ "PRIMARY KEY (test_id),"
				+ "CONSTRAINT FKVehicle_Test FOREIGN KEY (vehicle_id) REFERENCES Vehicle (vehicle_id));"
				+ "";
	}
	
	/**
	 * createIndeces
	 * 
	 * Creates indices for tables Test and Vehicle.
	 * @return SQL query to create db indexes 
	 */
	public static String createIndices() {
		return ""
				+ "CREATE INDEX idx_Vehicle_vehicle_id ON Vehicle (vehicle_id);"
				+ "CREATE INDEX idx_Vehicle_model ON Vehicle (model);"
				+ "CREATE INDEX idx_Vehicle_first_use_date ON Vehicle (first_use_date);"
				+ "CREATE INDEX idx_Test_test_milage ON Test (test_milage);"
				+ "CREATE INDEX idx_Test_test_postcode ON Test (test_postcode);"
				+ "CREATE INDEX idx_Test_test_result ON Test (test_result);"
				+ "CREATE INDEX idx_Test_resuclt_milage ON Test (test_result, test_milage);"
				+ "";
				
	}

	/**
	 * insertVehicle
	 * 
	 * Parameterised query used to insert a row of data into Vehicle table.
	 * @return returns vehicle table parameterised query used in main 
	 */
	public static String insertVehicle() {
		return ""
				+ "INSERT OR IGNORE INTO Vehicle"
				+ "(vehicle_id, make, model, colour, fuel_type, cylinder_capacity, first_use_date)"
				+ "VALUES"
				+ "(?, ?, ?, ?, ?, ?, ?);";
				
	}
	
	/**
	 * insertTest
	 * 
	 * Parameterised query used to insert a row of data into Test table.
	 * @return returns test table parameterised query used in main 
	 */
	public static String insertTest() {
		return ""
				+ "INSERT OR IGNORE INTO Test"
				+ "(test_id, vehicle_id, test_type, test_class, test_date, test_milage, test_postcode, test_result)"
				+ "VALUES "
				+ "(?, ?, ?, ?, ?, ?, ?, ?);";
				
	}

}
