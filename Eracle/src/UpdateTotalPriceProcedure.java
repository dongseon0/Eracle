// 추가수하물 여부에 따라 totalPrice 업데이트
// 예약 정보에서 수하물 추가 Y/N를 input으로 입력하면 수하물 추가가 되고, totalprice가 변경되는 메뉴
import java.sql.*;
import java.util.Scanner;

public class UpdateTotalPriceProcedure {
    public static void main(String[] args) {
        // JDBC 연결 정보
        String url = "jdbc:mysql://localhost:3306/DB이름";
        String user = "유저이름";
        String password = "비번";
        
        // 예약 정보
        long reservationId = 1; // 예약 ID 입력
        boolean additionalBaggage = true; // 추가 수하물 여부 입력

	    // 사용자로부터 추가 수하물 여부 입력 받기
        additionalBaggage = getAdditionalBaggageFromUser();
	
        // JDBC 연결
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            // totalPrice 업데이트
            double totalPrice = updateTotalPrice(conn, reservationId, additionalBaggage);
            
            System.out.println("Total price updated. New total price: " + totalPrice);
        } catch (SQLException ex) {
            System.err.println("DB 연결 오류: " + ex.getMessage());
        }
    }
    
    
    private static boolean getAdditionalBaggageFromUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("추가 수하물이 필요하십니까? (Y/N): ");
        String userInput = scanner.nextLine().toUpperCase();
        return userInput.equals("Y");
    }


    private static double updateTotalPrice(Connection conn, long reservationId, boolean additionalBaggage) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement( "UPDATE Reservation SET totalPrice = totalPrice + ? WHERE reservationId = ?")) {
            double additionalCost = additionalBaggage ? 50000 : 0; // 추가 수하물 여부에 따른 추가 비용 5만원
            stmt.setDouble(1, additionalCost);
            stmt.setLong(2, reservationId);
            stmt.executeUpdate();
        }
        
        // 업데이트된 totalPrice 반환
        return getTotalPriceFromDatabase(conn, reservationId);
    }
    
    private static double getTotalPriceFromDatabase(Connection conn, long reservationId) throws SQLException { 
        try (PreparedStatement stmt = conn.prepareStatement("SELECT totalPrice FROM Reservation WHERE reservationId = ?")) {
            stmt.setLong(1, reservationId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("totalPrice");
                } else {
                    throw new SQLException("Reservation not found for ID: " + reservationId);
                }
            }
        }
    }
}
