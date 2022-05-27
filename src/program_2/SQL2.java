package program_2;

public class SQL2 {
	
	public static String selectAll() {
		return "SELECT * FROM Test";
	}
	
	public static String search() {
		return ""
				+ "SELECT test_id AS 'Test ID', vehicle.make AS 'Make', vehicle.model AS 'Model', test_postcode AS 'Postcode', test_milage AS 'Miles', vehicle.first_use_date AS 'Year' "
				+ "FROM Test "
				+ "JOIN Vehicle on Test.vehicle_id = Vehicle.vehicle_id "
				+ "WHERE 1 = 1"
				+ "";
	}
	
	public static String sidePanel() {
		return ""
				+ "SELECT * "
				+ "FROM Test "
				+ "JOIN Vehicle ON Test.vehicle_id = Vehicle.vehicle_id "
				+ "WHERE test_id == ?;"
				+ "";
	}

}
