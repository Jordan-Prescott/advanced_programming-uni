package program_1;

public class SQL {
	
	public static String createVehicleTable() {
		return ""
				+ "CREATE TABLE Vehicle ("
				+ "vehicle_id int(20) NOT NULL,"
				+ "make varchar(30) NOT NULL,"
				+ "model varchar(30) NOT NULL,"
				+ "colour varchar(10) NOT NULL,"
				+ "fuel_type varchar(30) NOT NULL,"
				+ "cylinder_capacity int(4) NOT NULL,"
				+ "first_use_date varchar(10) NOT NULL,"
				+ "PRIMARY KEY (vehicle_id));";
	}

	public static String createTestTable() {
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
				+ "CONSTRAINT FKVehicle_Test FOREIGN KEY (vehicle_id) REFERENCES Vehicle (vehicle_id));";
	}
	
	
	//TODO
	public static String creatIndex() {
		return "";
	}
	
	
}
