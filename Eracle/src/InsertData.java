import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class InsertData {
    public static void main(String[] args) {
        // MySQL 데이터베이스 연결 정보
        String url = "jdbc:mysql://localhost:3306/데베이름";
        String user = "유저이름";
        String password = "비번";

        // 연결 객체 초기화
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            // Flight 테이블에 데이터 삽입
            insertIntoFlightTable(conn);

            // Reservation 테이블에 데이터 삽입
            insertIntoReservationTable(conn);

        } catch (SQLException e) {
            System.out.println("MySQL 데이터베이스 연결 오류!");
            e.printStackTrace();
        }
    }

    private static void insertIntoFlightTable(Connection conn) throws SQLException {
        String sql = "INSERT INTO Flight (flightId, departureAirportId, arrivalAirportId, aircraftId, departureTime, arrivalTime, airline, flightPrice) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            // 값 설정
            statement.setLong(1, 1); // flightId
            statement.setLong(2, 101); // departureAirportId
            statement.setLong(3, 201); // arrivalAirportId
            statement.setLong(4, 1); // aircraftId
            statement.setTimestamp(5, Timestamp.valueOf("2024-05-14 10:00:00")); // departureTime
            statement.setTimestamp(6, Timestamp.valueOf("2024-05-14 12:00:00")); // arrivalTime
            statement.setString(7, "Airline Name"); // airline
            statement.setInt(8, 500); // flightPrice

            // 쿼리 실행
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Flight 테이블에 데이터가 성공적으로 삽입되었습니다.");
            } else {
                System.out.println("Flight 테이블에 데이터 삽입에 실패하였습니다.");
            }
        }
    }

    private static void insertIntoReservationTable(Connection conn) throws SQLException {
        String sql = "INSERT INTO Reservation (reservationId, flightId, passengerId, passengerNum, reservationDate, classType, seatNum, additionalBaggage, totalPrice) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            // 값 설정
            statement.setLong(1, 1); // reservationId
            statement.setLong(2, 1); // flightId
            statement.setLong(3, 1); // passengerId
            statement.setString(4, "Passenger123"); // passengerNum
            statement.setTimestamp(5, Timestamp.valueOf("2024-05-14 09:00:00")); // reservationDate
            statement.setString(6, "Economy"); // classType
            statement.setInt(7, 25); // seatNum
            statement.setBoolean(8, true); // additionalBaggage
            statement.setInt(9, 550); // totalPrice

            // 쿼리 실행
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Reservation 테이블에 데이터가 성공적으로 삽입되었습니다.");
            } else {
                System.out.println("Reservation 테이블에 데이터 삽입에 실패하였습니다.");
            }
        }
    }
}
