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
        // boolean additionalBaggage = getAdditionalBaggageFromUser();
	
        // JDBC 연결
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            // 프로시저 호출
            double totalPrice = getTotalPrice(conn, reservationId, additionalBaggage);
            
            System.out.println("Total price updated. New total price: " + totalPrice);
        } catch (SQLException ex) {
            System.err.println("Database Connection Error: " + ex.getMessage());
        }
    }
    
    private static boolean getAdditionalBaggageFromUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("추가 수하물이 필요하십니까? (Y/N): ");
        String userInput = scanner.nextLine().toUpperCase();
        return userInput.equals("Y");
    }
    
    private static double getTotalPrice(Connection conn, long reservationId, boolean additionalBaggage) throws SQLException {
        try (CallableStatement stmt = conn.prepareCall("{call AddBaggage(?, ?, ?)}")) {
            stmt.setLong(1, reservationId);
            stmt.setBoolean(2, additionalBaggage);
            stmt.registerOutParameter(3, Types.DOUBLE);
            stmt.execute();
            return stmt.getDouble(3);
        }
    }
}
