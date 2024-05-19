import java.sql.*;

public class Passenger {
    public static void insertPassenger(
    		Connection conn, 
    		long passengerId, 
    		String passportNum, 
    		String firstName, 
    		String lastName, 
    		String dateOfBirth, 
    		String gender, 
    		String nationality, 
    		String address, 
    		String phoneNum) throws SQLException {
        try {
        	insertIntoPassengerTable(conn, passengerId, passportNum, firstName, lastName, dateOfBirth, gender, nationality, address, phoneNum);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
	private static void insertIntoPassengerTable(
			Connection conn, 
			long passengerId, 
			String passportNum, 
			String firstName, 
			String lastName, 
			String dateOfBirth, 
			String gender, 
			String nationality, 
			String address, 
			String phoneNum) throws SQLException{
        String query = "INSERT INTO Passenger (passengerId, passportNum, firstName, lastName, dateOfBirth, gender, nationality, address, phoneNum) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pStmt = conn.prepareStatement(query)){
            pStmt.setLong(1, passengerId);
            pStmt.setString(2, passportNum);
            pStmt.setString(3, firstName);
            pStmt.setString(4, lastName);
            pStmt.setDate(5, java.sql.Date.valueOf(dateOfBirth));
            pStmt.setString(6, gender);
            pStmt.setString(7, nationality);
            pStmt.setString(8, address);
            pStmt.setString(9, phoneNum);

            int rowsInserted = pStmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("[SUCCESS] Insert into passenger table.");
            } else {
                System.out.println("[FAIL] Insert into passenger table.");
            }
        }
    }
}