package com.Dickson.GUI;

import com.Dickson.GUI.Menu.Set;
import com.Dickson.Mapperdao.FunctionMapper;
import com.Dickson.config.RoundButton;
import com.Dickson.entity.Employee;
import com.Dickson.entity.Status;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class PasscodeBooking extends JFrame {
    private JLabel table_number;
    private JPasswordField pass_code;
    private JButton btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9, btn_0;
    private JButton yes, no;
    private StringBuilder passcodeStringBuilder;
    private static Booking bookingFrame;
    @Getter
    private static int tableNumber;
    static SqlSessionFactory sqlSessionFactory = null;

    static {
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }
    public PasscodeBooking(int tableNumber,Booking bookingFrame) {
        setTitle("Main Page");
        setSize(200, 250);
        setVisible(true);
        setLocationRelativeTo(null);
        initialComponent(tableNumber);
        this.bookingFrame = bookingFrame;
    }
    public void initialComponent(int tableNumber) {
        Font myFont = new Font("Serif",Font.BOLD + Font.ITALIC,16);
        this.tableNumber = tableNumber;
        table_number = new JLabel("Table " + tableNumber);
        btn_0 = new JButton("0");
        btn_0.setFont(myFont);
        btn_1 = new JButton("1");
        btn_1.setFont(myFont);
        btn_2 = new JButton("2");
        btn_2.setFont(myFont);
        btn_3 = new JButton("3");
        btn_3.setFont(myFont);
        btn_4 = new JButton("4");
        btn_4.setFont(myFont);
        btn_5 = new JButton("5");
        btn_5.setFont(myFont);
        btn_6 = new JButton("6");
        btn_6.setFont(myFont);
        btn_7 = new JButton("7");
        btn_7.setFont(myFont);
        btn_8 = new JButton("8");
        btn_8.setFont(myFont);
        btn_9 = new JButton("9");
        btn_9.setFont(myFont);

        pass_code = new JPasswordField(4);
        pass_code.setEditable(false);

        yes = new JButton("✓");
        yes.setForeground(Color.green);
        yes.setFont(myFont);
        no = new JButton("✗");
        no.setForeground(Color.red);
        yes.setFont(myFont);

        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(4, 3));
        p1.add(btn_1);
        p1.add(btn_2);
        p1.add(btn_3);
        p1.add(btn_4);
        p1.add(btn_5);
        p1.add(btn_6);
        p1.add(btn_7);
        p1.add(btn_8);
        p1.add(btn_9);
        p1.add(yes);
        p1.add(btn_0);
        p1.add(no);

        JPanel p2 = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        p2.add(table_number, gbc);
        gbc.gridy = 1;
        p2.add(pass_code, gbc);

        setLayout(new BorderLayout());
        JPanel titleBar = new JPanel();
        titleBar.setLayout(new BorderLayout());
        titleBar.setOpaque(false);
        RoundButton close = new RoundButton("", new Color(237, 106, 94, 255), new Color(245, 189, 79, 255));
        RoundButton hide = new RoundButton("", new Color(245, 189, 79, 255), new Color(245, 189, 79, 255));
        RoundButton maximize = new RoundButton("", new Color(98, 198, 85, 255), new Color(98, 198, 85, 255));
        Icon closeIcon = new ImageIcon("C:\\Users\\ASUS\\Desktop\\POS_System_Project\\src\\main\\java\\com\\Dickson\\GUI\\Image\\remove-svgrepo-com.png");
        Icon hideIcon = new ImageIcon("C:\\Users\\ASUS\\Desktop\\POS_System_Project\\src\\main\\java\\com\\Dickson\\GUI\\Image\\minus-svgrepo-com.png");
        Icon maximizeIcon = new ImageIcon("C:/Users/ASUS/Desktop/POS_System_Project/src/main/java/com/Dickson/GUI/Image/expand-svgrepo-com.png");
        close.setIcon(closeIcon);
        hide.setIcon(hideIcon);
        maximize.setIcon(maximizeIcon);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(close);
        buttonPanel.add(hide);
        buttonPanel.add(maximize);
        titleBar.add(buttonPanel, BorderLayout.WEST);
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(p1, BorderLayout.CENTER);
        mainPanel.add(p2, BorderLayout.NORTH);
        setLayout(new BorderLayout());

        add(mainPanel, BorderLayout.CENTER);
        add(titleBar, BorderLayout.NORTH);

        passcodeStringBuilder = new StringBuilder();
        btn_0.addActionListener(new NumberButtonListener());
        btn_1.addActionListener(new NumberButtonListener());
        btn_2.addActionListener(new NumberButtonListener());
        btn_3.addActionListener(new NumberButtonListener());
        btn_4.addActionListener(new NumberButtonListener());
        btn_5.addActionListener(new NumberButtonListener());
        btn_6.addActionListener(new NumberButtonListener());
        btn_7.addActionListener(new NumberButtonListener());
        btn_8.addActionListener(new NumberButtonListener());
        btn_9.addActionListener(new NumberButtonListener());

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
        pass_code.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (Character.isDigit(c) && passcodeStringBuilder.length() < 4) {
                    passcodeStringBuilder.append(c);
                } else if (c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE) {
                    if (passcodeStringBuilder.length() > 0) {
                        passcodeStringBuilder.deleteCharAt(passcodeStringBuilder.length() - 1);
                    }
                }
                pass_code.setText(passcodeStringBuilder.toString());
            }
        });
        no.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (passcodeStringBuilder.length() > 0) {
                    passcodeStringBuilder.deleteCharAt(passcodeStringBuilder.length() - 1);
                    pass_code.setText(passcodeStringBuilder.toString());
                }
            }
        });
        yes.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            try {
                FunctionMapper mapper = session.getMapper(FunctionMapper.class);
                Employee employee = mapper.login(Integer.valueOf(pass_code.getText()));
                if (employee != null) {
                    Status.setLogged_Status(Integer.valueOf(pass_code.getText()));
                    dispose();
                    new Booking_Form(tableNumber);
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect Passcode", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                session.rollback();
            }
        }
    }
});
    }
    private class NumberButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton sourceButton = (JButton) e.getSource();
            String buttonText = sourceButton.getText();
            if (passcodeStringBuilder.length() < 4) {
                passcodeStringBuilder.append(buttonText);
                pass_code.setText(passcodeStringBuilder.toString());
            }
        }
    }
    public static void main(String[] args) {
        new PasscodeBooking(tableNumber,bookingFrame).setVisible(true);
    }
}
