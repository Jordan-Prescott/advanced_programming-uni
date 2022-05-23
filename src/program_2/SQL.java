package program_2;

public class SQL {
	
	public static String selectAll() {
		return "SELECT * FROM Test";
	}
	
	public static String sidePanel() {
		return ""
				+ "SELECT *"
				+ "FROM Test "
				+ "JOIN Vehicle ON Test.Vehicle_id = Vehicle.vehicle_id"
				+ "WHERE test_id == ?;";
	}

}
