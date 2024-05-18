import java.sql.*;
import java.util.Scanner;

public class Passenger {
    public static void main(String[] args) {
        // MySQL 데이터베이스 연결 정보
        String url = "jdbc:mysql://localhost:3306/데베이름";
        String user = "유저이름";
        String password = "비번";

        Scanner scanner = new Scanner(System.in);

        System.out.print("승객 일렬번호: ");
        long passengerId = scanner.nextLong();

        System.out.print("여권 번호: ");
        String passengerNum = scanner.nextLine();

        System.out.print("이름: ");
        String firstName = scanner.nextLine();

        System.out.print("성: ");
        String lastName = scanner.nextLine();

        System.out.print("생년월일 (YYYY-MM-DD): ");
        String dateOfBirth = scanner.nextLine();

        System.out.print("성별: ");
        String gender = scanner.nextLine();

        System.out.print("국적: ");
        String nationality = scanner.nextLine();

        System.out.print("주소: ");
        String address = scanner.nextLine();

        System.out.print("전화번호: ");
        String phoneNum = scanner.nextLine();

        // 연결 객체 초기화
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            insertIntoPassengerTable(conn, passengerId, passengerNum, firstName, lastName, dateOfBirth, gender, nationality, address, phoneNum);
        } catch (SQLException e) {
            System.out.println("데이터베이스 연결 오류!");
            e.printStackTrace();
        }
    }

    private static void insertIntoPassengerTable(Connection conn, long passengerId, String passengerNum, String firstName, String lastName, String dateOfBirth, String gender, String nationality, String address, String phoneNum) throws SQLException{
        String sql = "INSERT INTO Passenger (passengerId, passengerNum, firstName, lastName, dateOfBirth, gender, nationality, address, phoneNum) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pStmt = conn.prepareStatement(sql)){
            //값 설정
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
}
