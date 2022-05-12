package program_1;

public class SQL { // SQL commands stored in one place for maintenance
	
	public static String createTableVehicle() { // creates vehicle table in DB
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

	public static String createTableTest() { // creates test table in DB
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
	
	public static String createIndices() { // creates tables indices
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
}
