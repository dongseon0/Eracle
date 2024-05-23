import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Reservation {
	private static int getSeatNum(
			Connection conn, 
			String passportNum, 
			String flightId) {
	    int seatNum = 0;
	    Long passengerId = null;

	    // Retrieve passenger ID
	    try {
	        passengerId = Passenger.getPassengerId(conn, passportNum);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    if (passengerId == null) {
	        return seatNum; // Return 0 if passengerId is not found
	    }

	    // Retrieve seat number
	    String seatNumQuery = "SELECT seatNum FROM passengerSeat WHERE passengerId = ? AND flightId = ?";
	    try (PreparedStatement stmt = conn.prepareStatement(seatNumQuery)) {
	        stmt.setLong(1, passengerId);
	        stmt.setString(2, flightId); // Adding flightId to the query
	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                seatNum = rs.getInt("seatNum");
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return seatNum;
	}

	private static int calculateTotalPrice(
			Connection conn, 
			String flightId, 
			String classType, 
			boolean additionalBaggage) {
        int basePrice = 0;
        try {
            String query = "SELECT flightPrice FROM Flight WHERE flightId = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, flightId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                basePrice = rs.getInt("flightPrice");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int classMultiplier = "First".equalsIgnoreCase(classType) ? 3 : ("Business".equalsIgnoreCase(classType) ? 2 : 1);
        int baggageFee = additionalBaggage ? 50 : 0;

        return basePrice * classMultiplier + baggageFee;
    }
	
    public static boolean makeReservation(
    		Connection conn, 
    		String flightId,
    		long passengerId, 
    		String passportNum, 
    		Timestamp reservationDateStr, 
    		String classType, 
    		int seatNum, 
    		boolean additionalBaggage) throws SQLException {
        try {
        	int totalPrice = calculateTotalPrice(conn, flightId, classType, additionalBaggage);
            insertIntoReservationTable(conn, flightId, passengerId, passportNum, reservationDateStr, classType, seatNum, additionalBaggage, totalPrice);
            // 좌석 가용성 업데이트
            updateSeatAvailability(conn, flightId, seatNum, false);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static List<String> getReservationsByPassportNum(Connection conn, String passportNum) throws SQLException {
        List<String> myReservations = new ArrayList<>();
        String query = "SELECT flightId, reservationDate, classType, seatNum, additionalBaggage " +
                       "FROM Passenger, Reservation " +
                       "WHERE Passenger.passengerId = Reservation.passengerId " +
                       "AND Passenger.passportNum = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, passportNum);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    myReservations.add("Flight ID: " + rs.getString("flightId") + 
                                       ", Reservation Date: " + rs.getDate("reservationDate") + 
                                       ", Class Type: " + rs.getString("classType") + 
                                       ", Seat Number: " + rs.getInt("seatNum") + 
                                       ", Additional Baggage: " + rs.getBoolean("additionalBaggage"));
                }
            }
        }
        return myReservations;
    }
    
    public static int deleteReservationByFlightId(
    		Connection conn,
    		String passportNum, 
    		String flightId) {
        String query = "DELETE FROM Reservation " +
                       "WHERE passengerId = (SELECT passengerId FROM Passenger WHERE passportNum = ?) " +
                       "AND flightId = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, passportNum);
            stmt.setString(2, flightId);
            int seatNum = getSeatNum(conn, passportNum, flightId);
            if (stmt.executeUpdate() > 0)
            	updateSeatAvailability(conn, flightId, seatNum, true);
            	return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    private static void insertIntoReservationTable(
    		Connection conn, 
    		String flightId,
    		long passengerId, 
    		String passportNum, 
    		Timestamp reservationDateStr, 
    		String classType, 
    		int seatNum, 
    		boolean additionalBaggage, 
    		int totalPrice) throws SQLException {
        String sql = "INSERT INTO Reservation (flightId, passengerId, passportNum, reservationDate, classType, seatNum, additionalBaggage, totalPrice) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pStmt = conn.prepareStatement(sql)) {
            // 값 설정
            pStmt.setString(1, flightId);
            pStmt.setLong(2, passengerId);
            pStmt.setString(3, passportNum);
            pStmt.setTimestamp(4, reservationDateStr);
            pStmt.setString(5, classType);
            pStmt.setInt(6, seatNum);
            pStmt.setBoolean(7, additionalBaggage);
            pStmt.setInt(8, totalPrice);

            // 쿼리 실행
            int rowsInserted = pStmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("예약되었습니다.");
            } else {
                System.out.println("예약에 실패했습니다.");
            }
        }
    }
    
    private static void updateSeatAvailability(
    		Connection conn, 
    		String flightId, 
    		int seatNum,
    		boolean isAvailable) throws SQLException {
        // 좌석 가용성 업데이트 SQL 작성
        String sql = "UPDATE Seat SET isAvailable = ? WHERE flightId = ? AND seatNum = ?";

        // SQL 실행
        try (PreparedStatement updateStmt = conn.prepareStatement(sql)) {
        	updateStmt.setBoolean(1, isAvailable);
            updateStmt.setString(2, flightId);
            updateStmt.setInt(3, seatNum);
            int rowsUpdated = updateStmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("좌석 가용성이 업데이트되었습니다.");
            } else {
                System.out.println("좌석 가용성 업데이트에 실패했습니다.");
            }
        }
    }
}