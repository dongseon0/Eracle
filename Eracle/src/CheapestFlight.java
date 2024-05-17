import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//출발 공항과 도착 공항을 입력받아 최저가 항공편을 찾아주는 코드
public class CheapestFlight {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/데베이름";
        String user = "유저이름";
        String password = "비번";
        
        // 입력받을 출발 공항 이름과 도착 공항 이름 설정
        String departureAirportName = "YourDepartureAirportName";
        String arrivalAirportName = "YourArrivalAirportName";

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
        //db연결
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // 사용자 입력값 설정
            pstmt.setString(1, departureAirportName);
            pstmt.setString(2, arrivalAirportName);

            // 쿼리 실행
            ResultSet rs = pstmt.executeQuery();

            // 결과
            while (rs.next()) {
                String departureAirport = rs.getString("DepartureAirport");
                String arrivalAirport = rs.getString("ArrivalAirport");
                int flightId = rs.getInt("flightId");
                double minPrice = rs.getDouble("MinPrice");

                System.out.println("Departure Airport: " + departureAirport);
                System.out.println("Arrival Airport: " + arrivalAirport);
                System.out.println("Flight ID: " + flightId);
                System.out.println("Minimum Price: " + minPrice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
