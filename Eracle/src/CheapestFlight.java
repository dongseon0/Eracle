import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

//출발 공항과 도착 공항을 입력받아 최저가 항공편을 찾아주는 코드
public class CheapestFlight {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/데베이름";
        String user = "유저이름";
        String password = "비번";
        
         // 사용자로부터 입력받을 출발 공항 이름과 도착 공항 이름 설정
         Scanner scanner = new Scanner(System.in);
         System.out.print("Please enter the departure airport name: ");
         String departureAirportName = scanner.nextLine();
         System.out.print("Please enter the arrival airport name: ");
         String arrivalAirportName = scanner.nextLine();
 
         // SQL 쿼리 작성
         String query = "SELECT " +
                        "    depAirport.airportName AS DepartureAirport, " +
                        "    arrAirport.airportName AS ArrivalAirport, " +
                        "    f.flightId, " +
                        "    MIN(f.flightPrice) AS MinPrice " +
                        "FROM " +
                        "    Flight f, " +
                        "    Airport depAirport, " +
                        "    Airport arrAirport " +
                        "WHERE " +
                        "    f.departureAirportId = depAirport.airportId AND " +
                        "    f.arrivalAirportId = arrAirport.airportId AND " +
                        "    depAirport.airportName = ? AND " +
                        "    arrAirport.airportName = ? " +
                        "GROUP BY " +
                        "    depAirport.airportName, " +
                        "    arrAirport.airportName, " +
                        "    f.flightId;";
 
         // 데이터베이스 연결 및 쿼리 실행
         try (Connection conn = DriverManager.getConnection(url, user, password);
              PreparedStatement pstmt = conn.prepareStatement(query)) {
 
             // PreparedStatement에 사용자 입력값 설정
             pstmt.setString(1, departureAirportName);
             pstmt.setString(2, arrivalAirportName);
 
             // 쿼리 실행
             ResultSet rs = pstmt.executeQuery();
 
             // 결과 처리
             while (rs.next()) {
                 String departureAirport = rs.getString("DepartureAirport");
                 String arrivalAirport = rs.getString("ArrivalAirport");
                 int flightId = rs.getInt("flightId");
                 double minPrice = rs.getDouble("MinPrice");
 
                 // 결과 출력
                 System.out.println("Departure Airport: " + departureAirport);
                 System.out.println("Arrival Airport: " + arrivalAirport);
                 System.out.println("Flight ID: " + flightId);
                 System.out.println("Minimum Price: " + minPrice);
                 System.out.println("-------------------------");
             }
         } catch (SQLException e) {
             e.printStackTrace();
         }
    }
}
