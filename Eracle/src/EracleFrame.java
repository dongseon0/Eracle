import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EracleFrame extends JFrame {
    public static final Color EWHA_COLOR_1 = new Color(80, 149, 98);
    public static final Color EWHA_COLOR_2 = new Color(255, 253, 241);
    private Connection conn;

    public EracleFrame() {
        setTitle("Eracle Airline Reservation System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Initialize database connection
        initDBConnection();

        Container contentPane = getContentPane();
        contentPane.setBackground(EWHA_COLOR_1);
        contentPane.setLayout(new FlowLayout());

        JButton passengerInfoButton = new JButton("Passenger Info.");
        passengerInfoButton.setFont(new Font("Monospaced", Font.BOLD, 15));
        passengerInfoButton.setBackground(EWHA_COLOR_2);
        passengerInfoButton.setOpaque(true);
        
        passengerInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPassengerInfo();
            }
        });
        
        JButton flightsButton = new JButton("Flights");
        flightsButton.setFont(new Font("Monospaced", Font.BOLD, 15));
        flightsButton.setBackground(EWHA_COLOR_2);
        flightsButton.setOpaque(true);
        
        flightsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findFlights();
            }
        });
        
        JButton reservationButton = new JButton("Reservation");
        reservationButton.setFont(new Font("Monospaced", Font.BOLD, 15));
        reservationButton.setBackground(EWHA_COLOR_2);
        reservationButton.setOpaque(true);
        
        reservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkReservation();
            }
        });

        contentPane.add(passengerInfoButton);
        contentPane.add(flightsButton);
        contentPane.add(reservationButton);
        
        setSize(800, 600);
        setVisible(true);
    }

    // DB Connection 초기화
    private void initDBConnection() {
        try {
            // Initialize database connection here
            String url = "jdbc:mysql://localhost:3306/eracledb";  // EB: Fill with your own database name.
            String username = "root";          // EB: Fill with your own username.
            String password = "password";     // EB: Fill with your own password.
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addPassengerInfo() {
        // Implement logic to add passenger information
    	JTextField passengerIdField = new JTextField(20);
        JTextField firstNameField = new JTextField(20);
        JTextField lastNameField = new JTextField(20);
        JTextField dobField = new JTextField(20);
        JTextField genderField = new JTextField(20);
        JTextField nationalityField = new JTextField(20);
        JTextField addressField = new JTextField(20);
        JTextField phoneNumField = new JTextField(20);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Passenger ID:"));
        panel.add(passengerIdField);
        panel.add(new JLabel("First Name:"));
        panel.add(firstNameField);
        panel.add(new JLabel("Last Name:"));
        panel.add(lastNameField);
        panel.add(new JLabel("Date of Birth (YYYY-MM-DD):"));
        panel.add(dobField);
        panel.add(new JLabel("Gender:"));
        panel.add(genderField);
        panel.add(new JLabel("Nationality:"));
        panel.add(nationalityField);
        panel.add(new JLabel("Address:"));
        panel.add(addressField);
        panel.add(new JLabel("Phone Number:"));
        panel.add(phoneNumField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add Passenger Information", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                Long passengerId = Long.parseLong(passengerIdField.getText());
                String passengerNum = generatePassengerNumber();
        		String firstName = firstNameField.getText();
        		String lastName = lastNameField.getText();
        		String dateOfBirth = dobField.getText();
        		String gender = genderField.getText();
        		String nationality = nationalityField.getText();
        		String address = addressField.getText();
        		String phoneNum = phoneNumField.getText();
                
                Passenger.insertPassenger(conn, passengerId, passengerNum, firstName, lastName, dateOfBirth, gender, nationality, address, phoneNum);
                JOptionPane.showMessageDialog(this, "Passenger added successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private String generatePassengerNumber() {
        // Generate a random passenger number (can be improved to meet specific requirements)
        return "100";
    	//return "P" + System.currentTimeMillis();
    }

    private void findFlights() {
        JTextField departureAirportField = new JTextField(20);
        JTextField arrivalAirportField = new JTextField(20);
        JTextField departureDateField = new JTextField(20);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Departure Airport ID:"));
        panel.add(departureAirportField);
        panel.add(new JLabel("Arrival Airport ID:"));
        panel.add(arrivalAirportField);
        panel.add(new JLabel("Departure Date (YYYY-MM-DD):"));
        panel.add(departureDateField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Find Flights", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String query = "SELECT * FROM Flight WHERE departureAirportId = ? AND arrivalAirportId = ? AND DATE(departureTime) = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setLong(1, Long.parseLong(departureAirportField.getText()));
                stmt.setLong(2, Long.parseLong(arrivalAirportField.getText()));
                stmt.setDate(3, java.sql.Date.valueOf(departureDateField.getText()));
                ResultSet rs = stmt.executeQuery();

                List<String> flights = new ArrayList<>();
                while (rs.next()) {
                    flights.add("Flight ID: " + rs.getString("flightId") + ", Departure: " + rs.getTimestamp("departureTime") + ", Arrival: " + rs.getTimestamp("arrivalTime"));
                }

                if (flights.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No flights found.");
                } else {
                    String selectedFlight = (String) JOptionPane.showInputDialog(this, "Available Flights:", "Select Flight", JOptionPane.QUESTION_MESSAGE, null, flights.toArray(), flights.get(0));
                    if (selectedFlight != null) {
                        makeReservation(selectedFlight.split(",")[0].split(":")[1].trim());
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void makeReservation(String flightId) {
        JTextField passengerIdField = new JTextField(20);
        JTextField classTypeField = new JTextField(20);
        JTextField seatNumField = new JTextField(20);
        JCheckBox additionalBaggageField = new JCheckBox("Additional Baggage");

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Passenger ID:"));
        panel.add(passengerIdField);
        panel.add(new JLabel("Class Type:"));
        panel.add(classTypeField);
        panel.add(new JLabel("Seat Number:"));
        panel.add(seatNumField);
        panel.add(additionalBaggageField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Make Reservation", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String query = "INSERT INTO Reservation (flightId, passengerId, passengerNum, reservationDate, classType, seatNum, additionalBaggage, totalPrice) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, flightId);
                stmt.setLong(2, Long.parseLong(passengerIdField.getText()));
                stmt.setString(3, generatePassengerNumber());
                stmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
                stmt.setString(5, classTypeField.getText());
                stmt.setInt(6, Integer.parseInt(seatNumField.getText()));
                stmt.setBoolean(7, additionalBaggageField.isSelected());
                stmt.setInt(8, calculateTotalPrice(flightId, classTypeField.getText(), additionalBaggageField.isSelected()));
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Reservation made successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private int calculateTotalPrice(String flightId, String classType, boolean additionalBaggage) {
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

    private void checkReservation() {
        JTextField reservationIdField = new JTextField(20);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Reservation ID:"));
        panel.add(reservationIdField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Check Reservation", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String query = "SELECT * FROM Reservation WHERE reservationId = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setLong(1, Long.parseLong(reservationIdField.getText()));
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    String reservationDetails = "Flight ID: " + rs.getString("flightId") + ", Passenger ID: " + rs.getLong("passengerId") + ", Seat Number: " + rs.getInt("seatNum");
                    int response = JOptionPane.showConfirmDialog(this, reservationDetails, "Delete Reservation?", JOptionPane.YES_NO_OPTION);
                    if (response == JOptionPane.YES_OPTION) {
                        deleteReservation(rs.getLong("reservationId"));
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Reservation not found.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void deleteReservation(long reservationId) {
        try {
            String query = "DELETE FROM Reservation WHERE reservationId = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setLong(1, reservationId);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Reservation deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new EracleFrame();
    }
}
