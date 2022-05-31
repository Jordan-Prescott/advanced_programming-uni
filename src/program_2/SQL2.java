package program_2;

/**
 * SQL2
 * 
 * Holds all SQL queries needed for the program to run when called an SQL query needed for a certain feature of the program is ran. For example
 * when search is clicked the SQL2.search method is called and executed to the db returning the results needed.
 * 
 * @author jordanprescott
 *
 */
public class SQL2 {
	
	public static String selectAll() {
		return "SELECT * FROM Test";
	}
	
	public static String search() {
		return ""
				+ "SELECT test_id AS 'Test ID', vehicle.make AS 'Make', vehicle.model AS 'Model', test_postcode AS 'Postcode', test_milage AS 'Miles', vehicle.first_use_date AS 'Year' "
				+ "FROM Vehicle "
				+ "JOIN Test on Test.vehicle_id = Vehicle.vehicle_id "
				+ "WHERE 1 = 1"
				+ "";
	}
	
	public static String analyse() {
		return ""
				+ "SELECT test_result, Vehicle.first_use_date "
				+ "FROM Test "
				+ "JOIN Vehicle on Test.vehicle_id = Vehicle.vehicle_id "
				+ "WHERE 1 = 1 "
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
