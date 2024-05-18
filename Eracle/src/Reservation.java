import java.sql.*;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Reservation {
    public static void main(String[] args) {
        // MySQL 데이터베이스 연결 정보
        String url = "jdbc:mysql://localhost:3306/데베이름";
        String user = "유저이름";
        String password = "비번";

        Scanner scanner = new Scanner(System.in);

        // 사용자로부터 정보 입력받기
        System.out.print("예약 일렬번호: "); // 나중에 제출시 자동으로 부여되는것으로 하기
        long reservationId = scanner.nextLong();

        System.out.print("운항 일렬번호: "); //select로 비행기 출발, 목적지, 시간 등 받아와서 사용자가 선택하면 입력되는것으로 바꾸기
        String flightId = scanner.next();

        System.out.print("승객 일렬번호: ");//승객이 직접 입력..? 아니면 승객에서 받아오기?
        long passengerId = scanner.nextLong();

        System.out.print("여권 번호: ");//승객이 직접 입력..? 아니면 승객에서 받아오기?
        String passengerNum = scanner.next();

        LocalDateTime now = LocalDateTime.now(); //현재 날짜 시간 가져옴
        String reservationDateStr = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        System.out.print("클래스 타입: ");//선택해서 입력하는것으로 변경?
        String classType = scanner.next();

        System.out.print("좌석 번호: ");
        int seatNum = scanner.nextInt();

        System.out.print("추가 수하물 여부 (true/false): ");
        boolean additionalBaggage = scanner.nextBoolean();

        System.out.print("총 가격: ");//자동으로 설정되는것으로 수정해야 함
        int totalPrice = scanner.nextInt();

        // 연결 객체 초기화
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            insertIntoReservationTable(conn, reservationId, flightId, passengerId, passengerNum, reservationDateStr, classType, seatNum, additionalBaggage, totalPrice);
        } catch (SQLException e) {
            System.out.println("데이터베이스 연결 오류!");
            e.printStackTrace();
        }
    }

    private static void insertIntoReservationTable(Connection conn, long reservationId, String flightId, long passengerId, String passengerNum, String reservationDateStr, String classType, int seatNum, boolean additionalBaggage, int totalPrice) throws SQLException {
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
}
