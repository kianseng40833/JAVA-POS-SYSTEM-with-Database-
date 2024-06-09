package com.Dickson.GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Booking extends JFrame {
    private JButton table1, table2, table3, table4, table5, table6;
    private JButton homeButton;

    public Booking() {
        setTitle("Booking");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        initialComponent();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void initialComponent() {
        Font myFont = new Font("Serif",Font.BOLD,16);
        ImageIcon img1 = new ImageIcon("C://Users//USER//OneDrive - S Seri Kledang//SkuLLLL//Semester 5//Java 2//Assignment//POS_System_Project (Function Done)//POS_System_Project//version 2.3//POS_System_Project//src//main//java//com//Dickson//GUI//Image//Table 1.jpg");
        table1 = createButton("1", img1);
        ImageIcon img2 = new ImageIcon("C://Users//USER//OneDrive - S Seri Kledang//SkuLLLL//Semester 5//Java 2//Assignment//POS_System_Project (Function Done)//POS_System_Project//version 2.3//POS_System_Project//src//main//java//com//Dickson//GUI//Image//Table 2.jpg");
        table2 = createButton("2", img2);
        ImageIcon img3 = new ImageIcon("C://Users//USER//OneDrive - S Seri Kledang//SkuLLLL//Semester 5//Java 2//Assignment//POS_System_Project (Function Done)//POS_System_Project//version 2.3//POS_System_Project//src//main//java//com//Dickson//GUI//Image//Table 3.jpg");
        table3 = createButton("3", img3);
        ImageIcon img4 = new ImageIcon("C://Users//USER//OneDrive - S Seri Kledang//SkuLLLL//Semester 5//Java 2//Assignment//POS_System_Project (Function Done)//POS_System_Project//version 2.3//POS_System_Project//src//main//java//com//Dickson//GUI//Image//Table 4.jpg");
        table4 = createButton("4", img4);
        ImageIcon img5 = new ImageIcon("C://Users//USER//OneDrive - S Seri Kledang//SkuLLLL//Semester 5//Java 2//Assignment//POS_System_Project (Function Done)//POS_System_Project//version 2.3//POS_System_Project//src//main//java//com//Dickson//GUI//Image//Table 5.jpg");
        table5 = createButton("5", img5);
        ImageIcon img6 = new ImageIcon("C://Users//USER//OneDrive - S Seri Kledang//SkuLLLL//Semester 5//Java 2//Assignment//POS_System_Project (Function Done)//POS_System_Project//version 2.3//POS_System_Project//src//main//java//com//Dickson//GUI//Image//Table 6.jpg");
        table6 = createButton("6", img6);

        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(2, 3));
        addPanelWithButton(p1, table1);
        addPanelWithButton(p1, table2);
        addPanelWithButton(p1, table3);
        addPanelWithButton(p1, table4);
        addPanelWithButton(p1, table5);
        addPanelWithButton(p1, table6);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setPreferredSize(new Dimension(1200, 100)); // Set preferred size for top panel

        ImageIcon homeImage = new ImageIcon("C://Users//USER//OneDrive - S Seri Kledang//SkuLLLL//Semester 5//Java 2//Assignment//POS_System_Project (Function Done)//POS_System_Project//version 2.3//POS_System_Project//src//main//java//com//Dickson//GUI//Image//homeButton1.png");
        Image scaledHome = homeImage.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);

        homeButton = new JButton();
        homeButton.setIcon(new ImageIcon(scaledHome));
        homeButton.setFont(myFont);
        topPanel.setBorder(new EmptyBorder(0, 10, 0, 0)); // Add margin to the left of the topPanel
        topPanel.add(homeButton, BorderLayout.WEST); // Add homeButton to the left side

        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH); // Add top panel above buttons
        add(p1, BorderLayout.CENTER); // Add buttons in the center

        // Add action listeners to table buttons
        table1.addActionListener(new TableButtonListener(this));
        table2.addActionListener(new TableButtonListener(this));
        table3.addActionListener(new TableButtonListener(this));
        table4.addActionListener(new TableButtonListener(this));
        table5.addActionListener(new TableButtonListener(this));
        table6.addActionListener(new TableButtonListener(this));

        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                MainMenu HomePanel = new MainMenu();
            }
        });
    }

    private JButton createButton(String text, ImageIcon img) {
        // Get the original image
        Image originalImage = img.getImage();

        // Scale the original image to fit the button size smoothly
        Image scaledImage = originalImage.getScaledInstance(200, 200, Image.SCALE_SMOOTH);

        // Create a new ImageIcon with the scaled image
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        // Create the button with the scaled ImageIcon
        JButton button = new JButton(text, scaledIcon);
        button.setPreferredSize(new Dimension(200, 200));
        return button;
    }

    private void addPanelWithButton(JPanel panel, JButton button) {
        JPanel subPanel = new JPanel();
        subPanel.setLayout(new GridBagLayout()); // Use GridBagLayout to center the button
        subPanel.add(button);
        panel.add(subPanel);
    }

    // ActionListener for table buttons
    private class TableButtonListener implements ActionListener {
        private Booking bookingFrame;

        public TableButtonListener(Booking bookingFrame) {
            this.bookingFrame = bookingFrame;
        }

        public void actionPerformed(ActionEvent e) {
            JButton sourceButton = (JButton) e.getSource();
            String tableNumberText = sourceButton.getText();
            int tableNumber = Integer.parseInt(tableNumberText);
            new PasscodeBooking(tableNumber, bookingFrame);
        }
    }

    public JButton getHomeButton() {
        return homeButton;
    }

    public static void main(String[] args) {
        new Booking().setVisible(true);
    }
}