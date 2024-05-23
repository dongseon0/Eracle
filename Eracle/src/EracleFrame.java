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
        contentPane.setLayout(new GridBagLayout());

        // Title label
        JLabel titleLabel = new JLabel("Eracle Airline Reservation System");
        titleLabel.setFont(new Font("Monospaced", Font.BOLD, 30));
        titleLabel.setForeground(EWHA_COLOR_2);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 10, 10));  // 5 rows, 1 column, 10px padding
        buttonPanel.setBackground(EWHA_COLOR_1);
        
        int padding = 20;
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));

        JButton addPassengersButton = new JButton("Add Passenger");
        addPassengersButton.setFont(new Font("Monospaced", Font.BOLD, 20));
        addPassengersButton.setBackground(EWHA_COLOR_2);
        addPassengersButton.setOpaque(true);
        addPassengersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPassengerInfo();
            }
        });
        
        JButton updatePassengerButton = new JButton("Update Passenger Info");
        updatePassengerButton.setFont(new Font("Monospaced", Font.BOLD, 20));
        updatePassengerButton.setBackground(EWHA_COLOR_2);
        updatePassengerButton.setOpaque(true);
        updatePassengerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePassengerInfo();
            }
        });

        JButton cheapestFlightsButton = new JButton("Find Cheapest Flights");
        cheapestFlightsButton.setFont(new Font("Monospaced", Font.BOLD, 20));
        cheapestFlightsButton.setBackground(EWHA_COLOR_2);
        cheapestFlightsButton.setOpaque(true);
        cheapestFlightsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findCheapestFlightsByDate();
            }
        });
        
        JButton flightsButton = new JButton("Make Reservations");
        flightsButton.setFont(new Font("Monospaced", Font.BOLD, 20));
        flightsButton.setBackground(EWHA_COLOR_2);
        flightsButton.setOpaque(true);
        flightsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findFlights();
            }
        });

        JButton reservationButton = new JButton("Cancel Reservation");
        reservationButton.setFont(new Font("Monospaced", Font.BOLD, 20));
        reservationButton.setBackground(EWHA_COLOR_2);
        reservationButton.setOpaque(true);
        reservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMyReservation();
            }
        });

        buttonPanel.add(addPassengersButton);
        buttonPanel.add(updatePassengerButton);
        buttonPanel.add(cheapestFlightsButton);
        buttonPanel.add(flightsButton);
        buttonPanel.add(reservationButton);

        GridBagConstraints gbc = new GridBagConstraints();
        
        // Title constraints
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(20, 20, 20, 20); // padding around the title
        contentPane.add(titleLabel, gbc);
        
        // Button panel constraints
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(padding, padding, padding, padding);
        contentPane.add(buttonPanel, gbc);

        setSize(800, 600);
        setVisible(true);
    }
    

    // DB Connection initialization
    private void initDBConnection() {
        try {
            // Initialize database connection here
            String url = "jdbc:mysql://localhost:3306/eracle";
            String username = "Eracle";
            String password = "eracle";
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void addPassengerInfo() {
        // Implement logic to add passenger information
    	JTextField passportNumField = new JTextField(20);
        JTextField firstNameField = new JTextField(20);
        JTextField lastNameField = new JTextField(20);
        JTextField dobField = new JTextField(20);
        JTextField genderField = new JTextField(20);
        JTextField nationalityField = new JTextField(20);
        JTextField addressField = new JTextField(20);
        JTextField phoneNumField = new JTextField(20);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Passport Num:"));
        panel.add(passportNumField);
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

        int result = JOptionPane.showConfirmDialog(this, panel, "Passenger Login", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String passportNum = passportNumField.getText();
        		String firstName = firstNameField.getText();
        		String lastName = lastNameField.getText();
        		String dateOfBirth = dobField.getText();
        		String gender = genderField.getText();
        		String nationality = nationalityField.getText();
        		String address = addressField.getText();
        		String phoneNum = phoneNumField.getText();
                
                Passenger.insertPassenger(conn, passportNum, firstName, lastName, dateOfBirth, gender, nationality, address, phoneNum);
                JOptionPane.showMessageDialog(this, "Passenger added successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void findFlights() {
        JTextField arrivalAirportField = new JTextField(20);
        JTextField departureDateField = new JTextField(20);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Arrival Airport ID:"));
        panel.add(arrivalAirportField);
        panel.add(new JLabel("Departure Date (YYYY-MM-DD):"));
        panel.add(departureDateField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Find Flights", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String query = "SELECT f.flightId, f.departureTime, f.arrivalTime, s.seatNum " +
                               "FROM Flight f, Seat s " +
                               "WHERE f.flightId = s.flightId AND f.arrivalAirportId = ? AND DATE(f.departureTime) = ?" + 
                               "AND s.isAvailable = True";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, arrivalAirportField.getText());
                stmt.setDate(2, java.sql.Date.valueOf(departureDateField.getText()));
                ResultSet rs = stmt.executeQuery();

                List<String> flights = new ArrayList<>();
                String currentFlightId = null;
                StringBuilder currentFlightInfo = new StringBuilder();
                while (rs.next()) {
                    String flightId = rs.getString("flightId");
                    if (!flightId.equals(currentFlightId)) {
                        if (currentFlightId != null) {
                            flights.add(currentFlightInfo.toString());
                        }
                        currentFlightId = flightId;
                        currentFlightInfo.setLength(0);
                        currentFlightInfo.append("Flight ID: ").append(flightId)
                                         .append(", Departure: ").append(rs.getTimestamp("departureTime"))
                                         .append(", Arrival: ").append(rs.getTimestamp("arrivalTime"))
                                         .append(", Available Seats: ");
                    }
                    currentFlightInfo.append(rs.getString("seatNum")).append(" ");
                }
                if (currentFlightId != null) {
                    flights.add(currentFlightInfo.toString());
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
    
    
    private void findCheapestFlightsByDate() {
        JTextField departureDateField = new JTextField(20);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Departure Date (YYYY-MM-DD):"));
        panel.add(departureDateField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Find Cheapest Flights", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String query = "SELECT airline, arrivalAirportId, MIN(flightPrice) AS MinPrice " +
                               "FROM Flight " +
                               "WHERE DATE(departureTime) = ? " +
                               "GROUP BY airline, arrivalAirportId";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setDate(1, java.sql.Date.valueOf(departureDateField.getText()));
                ResultSet rs = stmt.executeQuery();

                List<String> flights = new ArrayList<>();
                while (rs.next()) {
                    flights.add("Airline: " + rs.getString("airline") + 
                    		    ", Airport ID: " + rs.getString("arrivalAirportId") + 
                    		    ", Minimum Price: $" + rs.getDouble("MinPrice"));
                }

                if (flights.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No flights found for the selected date.", "No Flights", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, new JList(flights.toArray()), "Cheapest Flights", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    private void makeReservation(String flightId) {
        JTextField passengerIdField = new JTextField(20);
        JTextField passportIdField = new JTextField(20);
        JTextField classTypeField = new JTextField(20);
        JTextField seatNumField = new JTextField(20);
        JCheckBox additionalBaggageField = new JCheckBox("Additional Baggage");

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Passenger ID:"));
        panel.add(passengerIdField);
        panel.add(new JLabel("Passport ID:"));
        panel.add(passportIdField);
        panel.add(new JLabel("Class Type:"));
        panel.add(classTypeField);
        panel.add(new JLabel("Seat Number:"));
        panel.add(seatNumField);
        panel.add(additionalBaggageField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Make Reservation", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String query = "INSERT INTO Reservation (flightId, passengerId, passportNum, reservationDate, classType, seatNum, additionalBaggage, totalPrice) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, flightId);
                stmt.setLong(2, Long.parseLong(passengerIdField.getText()));
                stmt.setString(3, passportIdField.getText());
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

    private void showMyReservation() {
        JPanel panel = new JPanel(new GridLayout(0, 1));
        JTextField passportNumField = new JTextField(20);
        
        panel.add(new JLabel("Your Passport Number:"));
        panel.add(passportNumField);
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Check My Reservation", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String query = "SELECT flightId, reservationDate, classType, seatNum, additionalBaggage " +
                           "FROM Passenger, Reservation " +
                           "WHERE Passenger.passengerId = Reservation.passengerId " +
                           "AND Passenger.passportNum = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, passportNumField.getText());
                try (ResultSet rs = stmt.executeQuery()) {
                    List<String> myReservations = new ArrayList<>();
                    while (rs.next()) {
                        myReservations.add("Flight ID: " + rs.getString("flightId") + 
                                           ", Reservation Date: " + rs.getDate("reservationDate") + 
                                           ", Class Type: " + rs.getString("classType") + 
                                           ", Seat Number: " + rs.getInt("seatNum") + 
                                           ", Additional Baggage: " + rs.getBoolean("additionalBaggage"));
                    }

                    if (myReservations.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "No flights found for the passenger.", "No Flights", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JPanel displayPanel = new JPanel(new BorderLayout());
                        JList<String> list = new JList<>(myReservations.toArray(new String[0]));
                        JScrollPane scrollPane = new JScrollPane(list);
                        scrollPane.setPreferredSize(new Dimension(400, 200));
                        
                        JTextField flightIdField = new JTextField(20);
                        JPanel inputPanel = new JPanel(new GridLayout(0, 1));
                        inputPanel.add(new JLabel("Enter Flight ID to delete reservation:"));
                        inputPanel.add(flightIdField);
                        
                        displayPanel.add(scrollPane, BorderLayout.CENTER);
                        displayPanel.add(inputPanel, BorderLayout.SOUTH);

                        int deleteResult = JOptionPane.showConfirmDialog(this, displayPanel, "Check My Reservation", JOptionPane.OK_CANCEL_OPTION);
                        if (deleteResult == JOptionPane.OK_OPTION) {
                            deleteReservationByFlightId(passportNumField.getText(), flightIdField.getText());
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void deleteReservationByFlightId(String passportNum, String flightId) {
        String query = "DELETE FROM Reservation " +
                       "WHERE passengerId = (SELECT passengerId FROM Passenger WHERE passportNum = ?) " +
                       "AND flightId = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, passportNum);
            stmt.setString(2, flightId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Reservation deleted successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "No reservation found for the given flight ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updatePassengerInfo() {
        JTextField passportNumField = new JTextField(20);
    
        JPanel searchPanel = new JPanel(new GridLayout(0, 1));
        searchPanel.add(new JLabel("Passport Number to Update:"));
        searchPanel.add(passportNumField);
    
        int searchResult = JOptionPane.showConfirmDialog(this, searchPanel, "Search Passenger", JOptionPane.OK_CANCEL_OPTION);
        if (searchResult == JOptionPane.OK_OPTION) {
            try {
                String passportNum = passportNumField.getText();
                String query = "SELECT * FROM Passenger WHERE passportNum = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, passportNum);
                ResultSet rs = stmt.executeQuery();
    
                if (rs.next()) {
                    JTextField firstNameField = new JTextField(rs.getString("firstName"), 20);
                    JTextField lastNameField = new JTextField(rs.getString("lastName"), 20);
                    JTextField dobField = new JTextField(rs.getString("dateOfBirth"), 20);
                    JTextField genderField = new JTextField(rs.getString("gender"), 20);
                    JTextField nationalityField = new JTextField(rs.getString("nationality"), 20);
                    JTextField addressField = new JTextField(rs.getString("address"), 20);
                    JTextField phoneNumField = new JTextField(rs.getString("phoneNum"), 20);
    
                    JPanel updatePanel = new JPanel(new GridLayout(0, 1));
                    updatePanel.add(new JLabel("First Name:"));
                    updatePanel.add(firstNameField);
                    updatePanel.add(new JLabel("Last Name:"));
                    updatePanel.add(lastNameField);
                    updatePanel.add(new JLabel("Date of Birth (YYYY-MM-DD):"));
                    updatePanel.add(dobField);
                    updatePanel.add(new JLabel("Gender:"));
                    updatePanel.add(genderField);
                    updatePanel.add(new JLabel("Nationality:"));
                    updatePanel.add(nationalityField);
                    updatePanel.add(new JLabel("Address:"));
                    updatePanel.add(addressField);
                    updatePanel.add(new JLabel("Phone Number:"));
                    updatePanel.add(phoneNumField);
    
                    int updateResult = JOptionPane.showConfirmDialog(this, updatePanel, "Update Passenger Info", JOptionPane.OK_CANCEL_OPTION);
                    if (updateResult == JOptionPane.OK_OPTION) {
                        try {
                            String updateQuery = "UPDATE Passenger SET firstName = ?, lastName = ?, dateOfBirth = ?, gender = ?, nationality = ?, address = ?, phoneNum = ? WHERE passportNum = ?";
                            PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                            updateStmt.setString(1, firstNameField.getText());
                            updateStmt.setString(2, lastNameField.getText());
                            updateStmt.setString(3, dobField.getText());
                            updateStmt.setString(4, genderField.getText());
                            updateStmt.setString(5, nationalityField.getText());
                            updateStmt.setString(6, addressField.getText());
                            updateStmt.setString(7, phoneNumField.getText());
                            updateStmt.setString(8, passportNum);
                            updateStmt.executeUpdate();
    
                            JOptionPane.showMessageDialog(this, "Passenger information updated successfully.");
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Passenger not found.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    

    public static void main(String[] args) {
        new EracleFrame();
    }
}
