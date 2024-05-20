import java.sql.*;

public class Passenger {
    public static void insertPassenger(
    		Connection conn, 
    		String passportNum,
    		String firstName, 
    		String lastName, 
    		String dateOfBirth, 
    		String gender, 
    		String nationality, 
    		String address, 
    		String phoneNum) throws SQLException {
        try {
        	insertIntoPassengerTable(conn, passportNum, firstName, lastName, dateOfBirth, gender, nationality, address, phoneNum);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
	private static void insertIntoPassengerTable(
			Connection conn, 
			String passportNum,
			String firstName, 
			String lastName, 
			String dateOfBirth, 
			String gender, 
			String nationality, 
			String address, 
			String phoneNum) throws SQLException{
        String query = "INSERT INTO Passenger (passportNum, firstName, lastName, dateOfBirth, gender, nationality, address, phoneNum) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pStmt = conn.prepareStatement(query)){
            pStmt.setString(1, passportNum);
            pStmt.setString(2, firstName);
            pStmt.setString(3, lastName);
            pStmt.setDate(4, java.sql.Date.valueOf(dateOfBirth));
            pStmt.setString(5, gender);
            pStmt.setString(6, nationality);
            pStmt.setString(7, address);
            pStmt.setString(8, phoneNum);

            int rowsInserted = pStmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("[SUCCESS] Insert into passenger table.");
            } else {
                System.out.println("[FAIL] Insert into passenger table.");
            }
        }
    }
}