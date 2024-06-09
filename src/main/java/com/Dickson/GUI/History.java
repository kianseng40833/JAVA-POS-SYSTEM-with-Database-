package com.Dickson.GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class History extends JFrame implements ItemListener{
    private JComboBox<String> dateDropdown;
    private JComboBox<String> paymentTypeDropdown;
    private JTable paymentTable;
    private DefaultTableModel tableModel;
    private Font myFont = new Font("Serif",Font.BOLD,16);

    public History(){
        setTitle("History Page");
        setSize(1200,600);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
        setLocationRelativeTo(null);
        initialComponent();
        startUpdate();
    }
    
    public void initialComponent(){
        // Create components
        JButton homeButton = new JButton("");

        ImageIcon homeImage = new ImageIcon("");
        Image scaledHome = homeImage.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH); 

        homeButton = new JButton("");
        homeButton.setIcon(new ImageIcon(scaledHome));

        dateDropdown = new JComboBox<>(new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"});
        dateDropdown.addItemListener(this);
        paymentTypeDropdown = new JComboBox<>(new String[]{"All","Bank", "Cash", "E-Wallet"});
        paymentTypeDropdown.addItemListener(this);
        tableModel = new DefaultTableModel(new Object[]{"History ID","Receipt ID", "Date and Time", "Amount", "Table No", "Payment Type"}, 0);
        paymentTable = new JTable(tableModel);

        // Create a scroll pane for the table
        JScrollPane tableScrollPane = new JScrollPane(paymentTable);

        setLayout(new BorderLayout());

        add(createHeaderPanel(homeButton), BorderLayout.NORTH);
        add(createCenterPanel(dateDropdown, paymentTypeDropdown, tableScrollPane), BorderLayout.CENTER);

        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MainMenu();
            }
        });
    }

    private JPanel createHeaderPanel(JButton homeButton) {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        headerPanel.add(homeButton);
        return headerPanel;
    }   

    private JPanel createCenterPanel(JComboBox<String> dateDropdown, JComboBox<String> paymentTypeDropdown, JScrollPane tableScrollPane) {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        
        // Create panel for dropdowns
        JPanel dropdownPanel = new JPanel();
        dropdownPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        dropdownPanel.add(new JLabel("Select Month:"));
        dropdownPanel.add(dateDropdown);
        dropdownPanel.add(new JLabel("Payment Type:"));
        dropdownPanel.add(paymentTypeDropdown);

        centerPanel.add(dropdownPanel, BorderLayout.NORTH);
        centerPanel.add(tableScrollPane, BorderLayout.CENTER);
        return centerPanel;
    }

    private void startUpdate(){
    // Establish connection to the database
    String url = "jdbc:mysql://localhost:3306/POS_System";
    String user = "root";
    String password = "Kian_Seng8";

    try (Connection connection = DriverManager.getConnection(url, user, password)) {
        String query;
        query = "SELECT h.history_id,h.receipt_id,r.dateANDtime,r.totalPrice,r.table_no,r.paymentType from History h, Receipt r WHERE h.receipt_id = r.receipt_id;";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        // Add fetched employee data to the table
        while (resultSet.next()) {
            tableModel.addRow(new Object[]{
                    resultSet.getInt("history_id"),
                    resultSet.getInt("receipt_id"),
                    resultSet.getTimestamp("dateANDtime"),
                    resultSet.getDouble("totalPrice"),
                    resultSet.getInt("table_no"),
                    resultSet.getString("PaymentType"),

            });
        }
        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Failed to fetch History data from the database.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
    
public void itemStateChanged(ItemEvent e) {
    // Check which JComboBox triggered the event
    JComboBox<?> comboBox = (JComboBox<?>) e.getSource();
    
    // Update table data based on the selected index of the triggering JComboBox
    if (comboBox == dateDropdown) {
        updateTableDataByDate();
    } else if (comboBox == paymentTypeDropdown) {
        updateTableDataByPaymentType();
    }
}
        

            
    private void updateTableDataByDate() {
        tableModel.setRowCount(0);
        // Establish connection to the database
        String url = "jdbc:mysql://localhost:3306/POS_System";
        String user = "root";
        String password = "Kian_Seng8";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query;

            if(dateDropdown.getSelectedIndex() == 0){
                query = "SELECT h.history_id,h.receipt_id,r.dateANDtime,r.totalPrice,r.table_no,r.paymentType from History h, Receipt r WHERE h.receipt_id = r.receipt_id AND MONTH(r.dateANDtime) = 1 ;";
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery();
        
                // Add fetched employee data to the table
                while (resultSet.next()) {
                    tableModel.addRow(new Object[]{
                            resultSet.getInt("history_id"),
                            resultSet.getInt("receipt_id"),
                            resultSet.getTimestamp("dateANDtime"),
                            resultSet.getDouble("totalPrice"),
                            resultSet.getInt("table_no"),
                            resultSet.getString("PaymentType"),
        
                    });
                }
                connection.close();

            }else if(dateDropdown.getSelectedIndex() == 1){
                query = "SELECT h.history_id,h.receipt_id,r.dateANDtime,r.totalPrice,r.table_no,r.paymentType from History h, Receipt r WHERE h.receipt_id = r.receipt_id AND MONTH(r.dateANDtime) = 2 ;";
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery();
        
                // Add fetched employee data to the table
                while (resultSet.next()) {
                    tableModel.addRow(new Object[]{
                            resultSet.getInt("history_id"),
                            resultSet.getInt("receipt_id"),
                            resultSet.getTimestamp("dateANDtime"),
                            resultSet.getDouble("totalPrice"),
                            resultSet.getInt("table_no"),
                            resultSet.getString("PaymentType"),
        
                    });
                }
                connection.close();
            }else if(dateDropdown.getSelectedIndex() == 2){
                query = "SELECT h.history_id,h.receipt_id,r.dateANDtime,r.totalPrice,r.table_no,r.paymentType from History h, Receipt r WHERE h.receipt_id = r.receipt_id AND MONTH(r.dateANDtime) = 3 ;";
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery();
        
                // Add fetched employee data to the table
                while (resultSet.next()) {
                    tableModel.addRow(new Object[]{
                            resultSet.getInt("history_id"),
                            resultSet.getInt("receipt_id"),
                            resultSet.getTimestamp("dateANDtime"),
                            resultSet.getDouble("totalPrice"),
                            resultSet.getInt("table_no"),
                            resultSet.getString("PaymentType"),
        
                    });
                }
                connection.close();
            }else if(dateDropdown.getSelectedIndex() == 3){
                query = "SELECT h.history_id,h.receipt_id,r.dateANDtime,r.totalPrice,r.table_no,r.paymentType from History h, Receipt r WHERE h.receipt_id = r.receipt_id AND MONTH(r.dateANDtime) = 4 ;";
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery();
        
                // Add fetched employee data to the table
                while (resultSet.next()) {
                    tableModel.addRow(new Object[]{
                            resultSet.getInt("history_id"),
                            resultSet.getInt("receipt_id"),
                            resultSet.getTimestamp("dateANDtime"),
                            resultSet.getDouble("totalPrice"),
                            resultSet.getInt("table_no"),
                            resultSet.getString("PaymentType"),
        
                    });
                }
                connection.close();
            }else if(dateDropdown.getSelectedIndex() == 4){
                query = "SELECT h.history_id,h.receipt_id,r.dateANDtime,r.totalPrice,r.table_no,r.paymentType from History h, Receipt r WHERE h.receipt_id = r.receipt_id AND MONTH(r.dateANDtime) = 5 ;";
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery();
        
                // Add fetched employee data to the table
                while (resultSet.next()) {
                    tableModel.addRow(new Object[]{
                            resultSet.getInt("history_id"),
                            resultSet.getInt("receipt_id"),
                            resultSet.getTimestamp("dateANDtime"),
                            resultSet.getDouble("totalPrice"),
                            resultSet.getInt("table_no"),
                            resultSet.getString("PaymentType"),
        
                    });
                }
                connection.close();
            }else if(dateDropdown.getSelectedIndex() == 5){
                query = "SELECT h.history_id,h.receipt_id,r.dateANDtime,r.totalPrice,r.table_no,r.paymentType from History h, Receipt r WHERE h.receipt_id = r.receipt_id AND MONTH(r.dateANDtime) = 6 ;";
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery();
        
                // Add fetched employee data to the table
                while (resultSet.next()) {
                    tableModel.addRow(new Object[]{
                            resultSet.getInt("history_id"),
                            resultSet.getInt("receipt_id"),
                            resultSet.getTimestamp("dateANDtime"),
                            resultSet.getDouble("totalPrice"),
                            resultSet.getInt("table_no"),
                            resultSet.getString("PaymentType"),
        
                    });
                }
                connection.close();
            }else if(dateDropdown.getSelectedIndex() == 6){
                query = "SELECT h.history_id,h.receipt_id,r.dateANDtime,r.totalPrice,r.table_no,r.paymentType from History h, Receipt r WHERE h.receipt_id = r.receipt_id AND MONTH(r.dateANDtime) = 7 ;";
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery();
        
                // Add fetched employee data to the table
                while (resultSet.next()) {
                    tableModel.addRow(new Object[]{
                            resultSet.getInt("history_id"),
                            resultSet.getInt("receipt_id"),
                            resultSet.getTimestamp("dateANDtime"),
                            resultSet.getDouble("totalPrice"),
                            resultSet.getInt("table_no"),
                            resultSet.getString("PaymentType"),
        
                    });
                }
                connection.close();
            }else if(dateDropdown.getSelectedIndex() == 7){
                query = "SELECT h.history_id,h.receipt_id,r.dateANDtime,r.totalPrice,r.table_no,r.paymentType from History h, Receipt r WHERE h.receipt_id = r.receipt_id AND MONTH(r.dateANDtime) = 8 ;";
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery();
        
                // Add fetched employee data to the table
                while (resultSet.next()) {
                    tableModel.addRow(new Object[]{
                            resultSet.getInt("history_id"),
                            resultSet.getInt("receipt_id"),
                            resultSet.getTimestamp("dateANDtime"),
                            resultSet.getDouble("totalPrice"),
                            resultSet.getInt("table_no"),
                            resultSet.getString("PaymentType"),
        
                    });
                }
                connection.close();
            }else if(dateDropdown.getSelectedIndex() == 8){
                query = "SELECT h.history_id,h.receipt_id,r.dateANDtime,r.totalPrice,r.table_no,r.paymentType from History h, Receipt r WHERE h.receipt_id = r.receipt_id AND MONTH(r.dateANDtime) = 9 ;";
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery();
        
                // Add fetched employee data to the table
                while (resultSet.next()) {
                    tableModel.addRow(new Object[]{
                            resultSet.getInt("history_id"),
                            resultSet.getInt("receipt_id"),
                            resultSet.getTimestamp("dateANDtime"),
                            resultSet.getDouble("totalPrice"),
                            resultSet.getInt("table_no"),
                            resultSet.getString("PaymentType"),
        
                    });
                }
                connection.close();
            }else if(dateDropdown.getSelectedIndex() == 9){
                query = "SELECT h.history_id,h.receipt_id,r.dateANDtime,r.totalPrice,r.table_no,r.paymentType from History h, Receipt r WHERE h.receipt_id = r.receipt_id AND MONTH(r.dateANDtime) = 10 ;";
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery();
        
                // Add fetched employee data to the table
                while (resultSet.next()) {
                    tableModel.addRow(new Object[]{
                            resultSet.getInt("history_id"),
                            resultSet.getInt("receipt_id"),
                            resultSet.getTimestamp("dateANDtime"),
                            resultSet.getDouble("totalPrice"),
                            resultSet.getInt("table_no"),
                            resultSet.getString("PaymentType"),
        
                    });
                }
                connection.close();
            }else if(dateDropdown.getSelectedIndex() == 10){
                query = "SELECT h.history_id,h.receipt_id,r.dateANDtime,r.totalPrice,r.table_no,r.paymentType from History h, Receipt r WHERE h.receipt_id = r.receipt_id AND MONTH(r.dateANDtime) = 11 ;";
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery();
        
                // Add fetched employee data to the table
                while (resultSet.next()) {
                    tableModel.addRow(new Object[]{
                            resultSet.getInt("history_id"),
                            resultSet.getInt("receipt_id"),
                            resultSet.getTimestamp("dateANDtime"),
                            resultSet.getDouble("totalPrice"),
                            resultSet.getInt("table_no"),
                            resultSet.getString("PaymentType"),
        
                    });
                }
                connection.close();
            }else if(dateDropdown.getSelectedIndex() == 11){
                query = "SELECT h.history_id,h.receipt_id,r.dateANDtime,r.totalPrice,r.table_no,r.paymentType from History h, Receipt r WHERE h.receipt_id = r.receipt_id AND MONTH(r.dateANDtime) = 12 ;";
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery();
        
                // Add fetched employee data to the table
                while (resultSet.next()) {
                    tableModel.addRow(new Object[]{
                            resultSet.getInt("history_id"),
                            resultSet.getInt("receipt_id"),
                            resultSet.getTimestamp("dateANDtime"),
                            resultSet.getDouble("totalPrice"),
                            resultSet.getInt("table_no"),
                            resultSet.getString("PaymentType"),
        
                    });
                }
                connection.close();
            }
        }catch(Exception ex){
                JOptionPane.showMessageDialog(this,"Unable retrieve from database","Error",JOptionPane.ERROR_MESSAGE);
                }
            }
    
    private void updateTableDataByPaymentType() {
        tableModel.setRowCount(0);
        // Establish connection to the database
        String url = "jdbc:mysql://localhost:3306/POS_System";
        String user = "root";
        String password = "Kian_Seng8";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query;
        if(paymentTypeDropdown.getSelectedIndex() == 0){
            query = "SELECT h.history_id,h.receipt_id,r.dateANDtime,r.totalPrice,r.table_no,r.paymentType from History h, Receipt r WHERE h.receipt_id = r.receipt_id;";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
    
            // Add fetched employee data to the table
            while (resultSet.next()) {
                tableModel.addRow(new Object[]{
                        resultSet.getInt("history_id"),
                        resultSet.getInt("receipt_id"),
                        resultSet.getTimestamp("dateANDtime"),
                        resultSet.getDouble("totalPrice"),
                        resultSet.getInt("table_no"),
                        resultSet.getString("PaymentType"),
    
                });
            }
            connection.close();
        }else if(paymentTypeDropdown.getSelectedIndex() == 1){
            query = "SELECT h.history_id,h.receipt_id,r.dateANDtime,r.totalPrice,r.table_no,r.paymentType from History h, Receipt r WHERE h.receipt_id = r.receipt_id AND r.paymentType = 'VISA CARD' OR r.paymentType = 'MASTER CARD';";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
    
            // Add fetched employee data to the table
            while (resultSet.next()) {
                tableModel.addRow(new Object[]{
                        resultSet.getInt("history_id"),
                        resultSet.getInt("receipt_id"),
                        resultSet.getTimestamp("dateANDtime"),
                        resultSet.getDouble("totalPrice"),
                        resultSet.getInt("table_no"),
                        resultSet.getString("PaymentType"),
    
                });
            }
            connection.close();
        }else if(paymentTypeDropdown.getSelectedIndex() == 2){
            query = "SELECT h.history_id,h.receipt_id,r.dateANDtime,r.totalPrice,r.table_no,r.paymentType from History h, Receipt r WHERE h.receipt_id = r.receipt_id AND r.paymentType = 'CASH';";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
    
            // Add fetched employee data to the table
            while (resultSet.next()) {
                tableModel.addRow(new Object[]{
                        resultSet.getInt("history_id"),
                        resultSet.getInt("receipt_id"),
                        resultSet.getTimestamp("dateANDtime"),
                        resultSet.getDouble("totalPrice"),
                        resultSet.getInt("table_no"),
                        resultSet.getString("PaymentType"),
    
                });
            }
            connection.close();
        }else if(paymentTypeDropdown.getSelectedIndex() == 3){
            query = "SELECT h.history_id,h.receipt_id,r.dateANDtime,r.totalPrice,r.table_no,r.paymentType from History h, Receipt r WHERE h.receipt_id = r.receipt_id AND r.paymentType = 'TnG' OR r.paymentType = 'Grab Pay' OR r.paymentType = 'Shopee Pay' ;";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
    
            // Add fetched employee data to the table
            while (resultSet.next()) {
                tableModel.addRow(new Object[]{
                        resultSet.getInt("history_id"),
                        resultSet.getInt("receipt_id"),
                        resultSet.getTimestamp("dateANDtime"),
                        resultSet.getDouble("totalPrice"),
                        resultSet.getInt("table_no"),
                        resultSet.getString("PaymentType"),
    
                });
            }
            connection.close();
        }

}catch(Exception ex){
    JOptionPane.showMessageDialog(this,"Unable retrieve from database","Error",JOptionPane.ERROR_MESSAGE);
    }
}

    public static void main(String[] args) {
        new History().setVisible(true);
    }
}
