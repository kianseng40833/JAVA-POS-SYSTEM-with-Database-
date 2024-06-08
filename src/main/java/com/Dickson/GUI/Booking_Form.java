package com.Dickson.GUI;
import  javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class Booking_Form extends JFrame {
    private JLabel booking_form;
    private JLabel lbl_name, lbl_pax, lbl_tel, lbl_date, lbl_time, lbl_comment;
    private JTextField tf_name, tf_pax, tf_tel, tf_comment;
    private JButton btn_submit, btn_cancel;
    private int tableNo;
    private Font myFont = new Font("Serif", Font.BOLD, 16);
    public Booking_Form(int tableNo) {
        this.tableNo = tableNo;
        setTitle("Booking Form");
        setSize(450, 300);
        setVisible(true);
        setLocationRelativeTo(null);
        initialComponent();
        setCurrentDateTime();
    }
    public void initialComponent() {
        booking_form = new JLabel("Booking Form (Table " + tableNo + ")");
        booking_form.setHorizontalAlignment(SwingConstants.CENTER);

        lbl_name = new JLabel("Name:");
        lbl_pax = new JLabel("Pax:");
        lbl_tel = new JLabel("No. Tel:");
        lbl_date = new JLabel("Date:");
        lbl_time = new JLabel("Time:");
        lbl_comment = new JLabel("Comment:");

        tf_name = new JTextField(20);
        tf_pax = new JTextField(5);
        tf_tel = new JTextField(10);
        tf_comment = new JTextField(30);

        btn_submit = new JButton("Submit");
        btn_cancel = new JButton("Cancel");

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5));

        formPanel.add(lbl_name);
        formPanel.add(tf_name);
        formPanel.add(lbl_pax);
        formPanel.add(tf_pax);
        formPanel.add(lbl_tel);
        formPanel.add(tf_tel);
        formPanel.add(lbl_date);
        formPanel.add(createDatePicker());
        formPanel.add(lbl_time);
        formPanel.add(createTimeComboBox());
        formPanel.add(lbl_comment);
        formPanel.add(tf_comment);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(btn_submit);
        buttonPanel.add(btn_cancel);

        setLayout(new BorderLayout());
        add(booking_form, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        btn_submit.addActionListener(e -> {
            String name = tf_name.getText();
            int pax = Integer.parseInt(tf_pax.getText());
            int tel = Integer.parseInt(tf_tel.getText());
            String date = getDateFromDatePicker();
            String time = (String) getTimeComboBox().getSelectedItem();
            String comment = tf_comment.getText();

            if (isBookingConflict(tableNo, date, time)) {
                JOptionPane.showMessageDialog(null, "The table is already booked at this date and time. Please choose a different table, date, or time.");
            } else {
                insertBooking(name, pax, tel, date, time, comment);
                JOptionPane.showMessageDialog(null, "Booking submitted successfully!");
                dispose();
            }
        });
        btn_cancel.addActionListener(e -> {
            dispose();
        });
    }
    private JPanel createDatePicker() {
        UtilDateModel model = new UtilDateModel();
        Properties properties = new Properties();
        properties.put("text.today", "Today");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, properties);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());
        JPanel panel = new JPanel();
        panel.add(datePicker);
        return panel;
    }
    private JComboBox<String> createTimeComboBox() {
        String[] times = {"09:00 AM", "10:00 AM", "11:00 AM", "12:00 PM", "01:00 PM", "02:00 PM", "03:00 PM", "04:00 PM", "05:00 PM", "06:00 PM", "07:00 PM", "08:00 PM", "09:00 PM", "10:00 PM", "11:00 PM"};
        JComboBox<String> timeComboBox = new JComboBox<>(times);
        timeComboBox.setSelectedIndex(0);
        return timeComboBox;
    }
    private String getDateFromDatePicker() {
        JDatePickerImpl datePicker = (JDatePickerImpl) ((JPanel) ((JPanel) getContentPane().getComponent(1)).getComponent(7)).getComponent(0);
        Date selectedDate = (Date) datePicker.getModel().getValue();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(selectedDate);
    }
    private JComboBox<String> getTimeComboBox() {
        return (JComboBox<String>) ((JPanel) getContentPane().getComponent(1)).getComponent(9);
    }
    private void setCurrentDateTime() {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    }
    private boolean isBookingConflict(int tableNo, String date, String time) {
        boolean conflict = false;
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/POS_System", "root", "Kian_Seng8");
            String sql = "SELECT * FROM Booking WHERE table_no = ? AND date = ? AND TIME_FORMAT(time, '%h:%i %p') = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, tableNo);
            statement.setString(2, date);
            statement.setString(3, time);
            ResultSet resultSet = statement.executeQuery();
            conflict = resultSet.next();

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return conflict;
    }
    private void insertBooking(String name, int pax, int tel, String date, String time, String comment) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/POS_System", "root", "Kian_Seng8");

            SimpleDateFormat inputFormat = new SimpleDateFormat("hh:mm a");
            SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm:ss");
            Date parsedTime = inputFormat.parse(time);
            time = outputFormat.format(parsedTime);

            String sql = "INSERT INTO Booking (date, time, table_no, cus_name, no_tel, comment) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, date);
            statement.setString(2, time);
            statement.setInt(3, tableNo);
            statement.setString(4, name);
            statement.setInt(5, tel);
            statement.setString(6, comment);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Booking inserted successfully.");
                writeToFile(name, pax, tel, date, time, comment);
            } else {
                System.out.println("Failed to insert booking.");
            }
            statement.close();
            connection.close();
        } catch (SQLException | ParseException ex) {
            ex.printStackTrace();
        }
    }
    private void writeToFile(String name, int pax, int tel, String date, String time, String comment) {
        String filePath = "C://Users//USER//OneDrive - S Seri Kledang//SkuLLLL//Semester 5//Java 2//Assignment//POS_System_Project (Function Done)//POS_System_Project//version 2.3//POS_System_Project//src//main//java//com//Dickson//GUI//booking//booking.txt";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
            writer.write("Table No: " + tableNo +", Name: " + name + ", Pax: " + pax + ", Tel: " + tel + ", Date: " + date + ", Time: " + time + ", Comment: " + comment);
            writer.newLine();
            writer.close();
            System.out.println("Booking information has been successfully written to the file.");
        } catch (IOException e) {
            System.err.println("Error occurred while writing to the file: " + e.getMessage());
        }
    }
    public static void main(String[] args) {
        new Booking_Form(1);
    }
}