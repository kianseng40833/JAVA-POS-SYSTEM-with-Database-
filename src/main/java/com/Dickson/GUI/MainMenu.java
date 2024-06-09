package com.Dickson.GUI;

import com.Dickson.config.RoundButton;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame {
    private JPanel p1, p2;
    private JButton table1, table2, table3, table4, table5, table6;
    private JButton menu, booking, department, history;
    private Integer logged_status;
    public MainMenu() {
        setTitle("Main Page");
        setVisible(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initialComponent();
    }
    public void initialComponent() {
        ImageIcon img1 = new ImageIcon("");
        table1 = createButton("1", img1);
        ImageIcon img2 = new ImageIcon("g");
        table2 = createButton("2", img2);
        ImageIcon img3 = new ImageIcon("");
        table3 = createButton("3", img3);
        ImageIcon img4 = new ImageIcon("");
        table4 = createButton("4", img4);
        ImageIcon img5 = new ImageIcon("");
        table5 = createButton("5", img5);
        ImageIcon img6 = new ImageIcon("");
        table6 = createButton("6", img6);

        p1 = new JPanel();
        p1.setLayout(new GridLayout(2, 3));
        addPanelWithButton(p1, table1);
        addPanelWithButton(p1, table2);
        addPanelWithButton(p1, table3);
        addPanelWithButton(p1, table4);
        addPanelWithButton(p1, table5);
        addPanelWithButton(p1, table6);
        Font myFont = new Font("Serif",Font.BOLD + Font.ITALIC,22);
        menu = new JButton("Menu");
        menu.setPreferredSize(new Dimension(80, 50));
        menu.setFont(myFont);
        booking = new JButton("Booking");
        booking.setPreferredSize(new Dimension(80, 50));
        booking.setFont(myFont);
        department = new JButton("Department");
        department.setPreferredSize(new Dimension(80, 50));
        department.setFont(myFont);
        history = new JButton("History");
        history.setPreferredSize(new Dimension(80, 50));
        history.setFont(myFont);
        p2 = new JPanel();
        p2.setLayout(new GridLayout(1, 4));
        p2.add(menu);
        p2.add(booking);
        p2.add(department);
        p2.add(history);
        setLayout(new BorderLayout());
        add(p1);
        add(p2, BorderLayout.SOUTH);

        JPanel titleBar = new JPanel();
        titleBar.setLayout(new BorderLayout());
        titleBar.setOpaque(false);
        RoundButton close = new RoundButton("", new Color(237, 106, 94, 255), new Color(245, 189, 79, 255));
        RoundButton hide = new RoundButton("", new Color(245, 189, 79, 255), new Color(245, 189, 79, 255));
        RoundButton maximize = new RoundButton("", new Color(98, 198, 85, 255), new Color(98, 198, 85, 255));
        Icon closeIcon = new ImageIcon("");
        Icon hideIcon = new ImageIcon("");
        Icon maximizeIcon = new ImageIcon("");
        close.setIcon(closeIcon);
        hide.setIcon(hideIcon);
        maximize.setIcon(maximizeIcon);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(close);
        buttonPanel.add(hide);
        buttonPanel.add(maximize);
        titleBar.add(buttonPanel, BorderLayout.WEST);
        add(titleBar, BorderLayout.NORTH);

        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        maximize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getExtendedState() != JFrame.MAXIMIZED_BOTH) {
                    setExtendedState(JFrame.MAXIMIZED_BOTH);
                } else {
                    setExtendedState(JFrame.NORMAL);
                }
            }
        });
        hide.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setState(JFrame.ICONIFIED);
            }
        });
        menu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new AddMenu();
            }
        });
        booking.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Booking bookingPanel = new Booking();

            }
        });
        department.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Department departmentPanel = new Department();
            }
        });
        history.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                History historyPanel = new History();
            }
        });
        table1.addActionListener(new TableButtonListener(this));
        table2.addActionListener(new TableButtonListener(this));
        table3.addActionListener(new TableButtonListener(this));
        table4.addActionListener(new TableButtonListener(this));   
        table5.addActionListener(new TableButtonListener(this));
        table6.addActionListener(new TableButtonListener(this));
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
        subPanel.setLayout(new GridBagLayout());
        subPanel.add(button);
        panel.add(subPanel);
    }

    

    private class TableButtonListener implements ActionListener {
        private MainMenu mainMenuFrame;

        public TableButtonListener(MainMenu mainMenuFrame) {
            this.mainMenuFrame = mainMenuFrame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton sourceButton = (JButton) e.getSource();
            String tableNumberText = sourceButton.getText(); 
            int tableNumber = Integer.parseInt(tableNumberText);
            new Passcode(tableNumber, mainMenuFrame); // Pass reference to MainMenu frame
        }
    }
    

    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(new FlatMacDarkLaf());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                new MainMenu().setVisible(true);
            }
        });
    }
}
