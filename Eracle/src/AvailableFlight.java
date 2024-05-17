import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AvailableFlight {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/데베이름";
        String user = "유저이름";
        String password = "비번";

        // 사용자로부터 입력받을 출발 공항 이름, 도착 공항 이름, 항공사 이름 설정
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter the departure airport name: ");
        String departureAirportName = scanner.nextLine();
        System.out.print("Please enter the arrival airport name: ");
        String arrivalAirportName = scanner.nextLine();
        System.out.print("Please enter the airline name: ");
        String airlineName = scanner.nextLine();

        // SQL 쿼리 작성
        String query = "SELECT " +
                       "    f.flightId, " +
                       "    depAirport.airportName AS DepartureAirport, " +
                       "    arrAirport.airportName AS ArrivalAirport, " +
                       "    f.departureTime, " +
                       "    f.arrivalTime, " +
                       "    f.airline, " +
                       "    f.flightPrice, " +
                       "    s.seatNum " +
                       "FROM " +
                       "    Flight f, " +
                       "    Airport depAirport, " +
                       "    Airport arrAirport, " +
                       "    AvailableSeats s " +
                       "WHERE " +
                       "    f.departureAirportId = depAirport.airportId AND " +
                       "    f.arrivalAirportId = arrAirport.airportId AND " +
                       "    f.flightId = s.flightId AND " +
                       "    depAirport.airportName = ? AND " +
                       "    arrAirport.airportName = ? AND " +
                       "    f.airline = ?;";

        // 데이터베이스 연결 및 쿼리 실행
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // PreparedStatement에 사용자 입력값 설정
            pstmt.setString(1, departureAirportName);
            pstmt.setString(2, arrivalAirportName);
            pstmt.setString(3, airlineName);

            // 쿼리 실행
            ResultSet rs = pstmt.executeQuery();

            // 결과 처리
            while (rs.next()) {
                int flightId = rs.getInt("flightId");
                String departureAirport = rs.getString("DepartureAirport");
                String arrivalAirport = rs.getString("ArrivalAirport");
                String departureTime = rs.getString("departureTime");
                String arrivalTime = rs.getString("arrivalTime");
                String airline = rs.getString("airline");
                int flightPrice = rs.getInt("flightPrice");
                int seatNum = rs.getInt("seatNum");

                // 결과 출력
                System.out.println("Flight ID: " + flightId);
                System.out.println("Departure Airport: " + departureAirport);
                System.out.println("Arrival Airport: " + arrivalAirport);
                System.out.println("Departure Time: " + departureTime);
                System.out.println("Arrival Time: " + arrivalTime);
                System.out.println("Airline: " + airline);
                System.out.println("Flight Price: " + flightPrice);
                System.out.println("Available Seat Number: " + seatNum);
                System.out.println("-------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
