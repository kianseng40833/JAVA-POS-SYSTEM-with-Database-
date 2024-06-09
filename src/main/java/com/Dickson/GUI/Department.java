package com.Dickson.GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Department extends JFrame {
    private JComboBox<String> departmentDropdown;
    private JTable departmentTable;
    private DefaultTableModel tableModel;
    private Font myFont = new Font("Serif",Font.BOLD,16);

    public Department(){
        setTitle("Department Page");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
        initialComponent();
        setLocationRelativeTo(null);
    }

    public void initialComponent(){
        JButton homeButton = new JButton("Home");

        ImageIcon homeImage = new ImageIcon("homeButton1.png");
        Image scaledHome = homeImage.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH); 

        homeButton = new JButton("");
        homeButton.setIcon(new ImageIcon(scaledHome));

        departmentDropdown = new JComboBox<>(new String[]{"All Department","Front-of-house (FOH)", "Back-of-House (BOH)", "Human Resource"});
        tableModel = new DefaultTableModel(new Object[]{"Employee ID", "Name", "Gender", "Telephone No.", "Role"}, 0);
        departmentTable = new JTable(tableModel);

        JScrollPane tableScrollPane = new JScrollPane(departmentTable);

        startUpdate();
        setLayout(new BorderLayout());

        add(createHeaderPanel(homeButton), BorderLayout.NORTH);
        add(createCenterPanel(departmentDropdown, tableScrollPane), BorderLayout.CENTER);

        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MainMenu();
            }
        });

        departmentDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedDepartment = (String) departmentDropdown.getSelectedItem();
                updateTableData(selectedDepartment);
            }
        });
    }

    private JPanel createHeaderPanel(JButton homeButton) {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        headerPanel.add(homeButton);
        return headerPanel;
    }

    private JPanel createCenterPanel(JComboBox<String> departmentDropdown, JScrollPane tableScrollPane) {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(departmentDropdown, BorderLayout.NORTH);
        centerPanel.add(tableScrollPane, BorderLayout.CENTER);
        return centerPanel;
    }

    private void startUpdate(){
        // Establish connection to the database
        String url = "jdbc:mysql://localhost:3306/POS_System";
        String user = "";
        String password = "";
    
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query;
            query = "SELECT em_id, em_name, sex, phone_no, roles FROM Employee";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
    
            // Add fetched employee data to the table
            while (resultSet.next()) {
                tableModel.addRow(new Object[]{
                        resultSet.getInt("em_id"),
                        resultSet.getString("em_name"),
                        resultSet.getString("sex"),
                        resultSet.getInt("phone_no"),
                        resultSet.getString("roles")
                });
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to fetch History data from the database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTableData(String department) {
        tableModel.setRowCount(0); // Clear existing rows

        // Establish connection to the database
        String url = "jdbc:mysql://localhost:3306/POS_System";
        String user = "";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query;  
            if ("All Department".equals(department)) {
                // If "Choose Department" is selected, fetch all employees
                query = "SELECT em_id, em_name, sex, phone_no, roles FROM Employee";
            } else {
                // Fetch employees based on the selected department
                query = "SELECT em_id, em_name, sex, phone_no, roles FROM Employee WHERE department = ?";
            }
            PreparedStatement statement = connection.prepareStatement(query);
            if (!"All Department".equals(department)) {
                // Set the department parameter if a specific department is selected
                statement.setString(1, department);
            }

            ResultSet resultSet = statement.executeQuery();

            // Add fetched employee data to the table
            while (resultSet.next()) {
                tableModel.addRow(new Object[]{
                        resultSet.getInt("em_id"),
                        resultSet.getString("em_name"),
                        resultSet.getString("sex"),
                        resultSet.getInt("phone_no"),
                        resultSet.getString("roles")
                });
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to fetch employee data from the database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new Department().setVisible(true);
    }
}