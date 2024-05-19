import java.sql.*;
import java.util.Scanner;

public class Passenger {
	private static void insertIntoPassengerTable(Connection conn, long passengerId, String passengerNum, String firstName, String lastName, String dateOfBirth, String gender, String nationality, String address, String phoneNum) throws SQLException{
        String sql = "INSERT INTO Passenger (passengerId, passengerNum, firstName, lastName, dateOfBirth, gender, nationality, address, phoneNum) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pStmt = conn.prepareStatement(sql)){
            pStmt.setLong(1, passengerId);
            pStmt.setString(2, passengerNum);
            pStmt.setString(3, firstName);
            pStmt.setString(4, lastName);
            pStmt.setDate(5, java.sql.Date.valueOf(dateOfBirth));
            pStmt.setString(6, gender);
            pStmt.setString(7, nationality);
            pStmt.setString(8, address);
            pStmt.setString(9, phoneNum);

            int rowsInserted = pStmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("등록되었습니다.");
            } else {
                System.out.println("등록에 실패했습니다.");
            }
        }
    }
	
	// Passenger Table에 승객 정보 추가
    public static void insertPassenger(
    		Connection conn, 
    		long passengerId, 
    		String passengerNum, 
    		String firstName, 
    		String lastName, 
    		String dateOfBirth, 
    		String gender, 
    		String nationality, 
    		String address, 
    		String phoneNum) throws SQLException {
        try {
        	insertIntoPassengerTable(conn, passengerId, passengerNum, firstName, lastName, dateOfBirth, gender, nationality, address, phoneNum);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
