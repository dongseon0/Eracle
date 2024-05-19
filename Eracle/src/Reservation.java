import java.sql.*;

public class Reservation {
    public static void makeReservation(
    		Connection conn, 
    		long reservationId, 
    		String flightId, 
    		long passengerId, 
    		String passengerNum, 
    		String reservationDateStr, 
    		String classType, 
    		int seatNum, 
    		boolean additionalBaggage, 
    		int totalPrice) throws SQLException {
        try {
            insertIntoReservationTable(conn, reservationId, flightId, passengerId, passengerNum, reservationDateStr, classType, seatNum, additionalBaggage, totalPrice);
            // 좌석 가용성 업데이트
            updateSeatAvailability(conn, flightId, seatNum, true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void deleteReservation(
    		Connection conn, 
    		long reservationId) throws SQLException {
    	try {
    		// EB: To update the seat availability, 
    		// get flightId and seatNum attributes before deletion.
    		String query = "SELECT * FROM Reservation WHERE reservationId = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setLong(1, reservationId);
            ResultSet rs = stmt.executeQuery();
         
            if (rs.next()) {
            	String flightId = rs.getString("flightId");
            	int seatNum = rs.getInt("seatNum");
         
            	// EB: Delete the corresponding tuples from reservation table.
        		deleteFromReservationTable(conn, reservationId);
        		// EB: Update the seat availability as false.
        		updateSeatAvailability(conn, flightId, seatNum, false);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private static void deleteFromReservationTable(
    		Connection conn, 
    		long reservationId) throws SQLException {
    	String query = "DELETE FROM Reservation WHERE reservationId = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
        	stmt.setLong(1, reservationId);
        	stmt.executeUpdate();
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    }
    
    private static void insertIntoReservationTable(
    		Connection conn, 
    		long reservationId, 
    		String flightId, 
    		long passengerId, 
    		String passengerNum, 
    		String reservationDateStr, 
    		String classType, 
    		int seatNum, 
    		boolean additionalBaggage, 
    		int totalPrice) throws SQLException {
        String sql = "INSERT INTO Reservation (reservationId, flightId, passengerId, passengerNum, reservationDate, classType, seatNum, additionalBaggage, totalPrice) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pStmt = conn.prepareStatement(sql)) {
            // 값 설정
            pStmt.setLong(1, reservationId);
            pStmt.setString(2, flightId);
            pStmt.setLong(3, passengerId);
            pStmt.setString(4, passengerNum);
            pStmt.setTimestamp(5, Timestamp.valueOf(reservationDateStr));
            pStmt.setString(6, classType);
            pStmt.setInt(7, seatNum);
            pStmt.setBoolean(8, additionalBaggage);
            pStmt.setInt(9, totalPrice);

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