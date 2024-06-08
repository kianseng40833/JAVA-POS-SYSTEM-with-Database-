package com.Dickson.GUI.Menu;

import com.Dickson.GUI.MainMenu;
import com.Dickson.GUI.Passcode;
import com.Dickson.GUI.Payment;
import com.Dickson.Mapperdao.FunctionMapper;
import com.Dickson.config.RoundButton;
import com.Dickson.entity.Status;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class Set extends JFrame {

    @Getter
    @Setter
    private String employename, choice_name;
    private JPanel p1, p2, p3, display, panel2, buttonPanel2;
    private JButton btn_home, btn_set, btn_breakfast, btn_noodle, btn_rice, btn_dessert, btn_drink, btn_submit;
    private JLabel lbl_order;
    private JButton[] buttons = null;
    private JScrollPane scrollPane;
    private int[] buttonLabelIndex, buttonClickCounts;
    private String[] buttonChoiceNames;
    private List<JLabel> nameLabelList;
    private List<String> selectedProducts;
    static SqlSessionFactory sqlSessionFactory = null;


    static { //必须要写
        String resource = "mybatis-config.xml";
        InputStream inputStream;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    public Set() {

        setTitle("Menu");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initialComponent();

        selectedProducts = new ArrayList<>();

    }
    // Define a constructor that accepts a List<String> parameter
    public Set(List<String> selectedProducts) {
        // Call the default constructor using super()
        super();

        // Initialize any fields or perform any setup as needed
        // For example, you can initialize the selectedProducts list
        this.selectedProducts = selectedProducts;

        // Call any initialization methods
        initialComponent();
    }

    public void initialComponent() {
        Font myFont = new Font("Serif",Font.BOLD,16);
        btn_home = new JButton("Home");

        ImageIcon homeImage = new ImageIcon("C://Users//Asus//Documents//Diploma in Information System//Jan 2024//Java 2//Assignment//POS_System_Project (Function Done)//POS_System_Project//version 2.3//POS_System_Project//src//main//java//com//Dickson//GUI//Image//homeButton1.png");
        Image scaledHome = homeImage.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);

        btn_home = new JButton("");
        btn_home.setIcon(new ImageIcon(scaledHome));

        btn_home.setFont(myFont);
        ImageIcon home = new ImageIcon("C://Users//USER//OneDrive - S Seri Kledang//SkuLLLL//Semester 5//Java 2//Assignment//POS_System_Project (Function Done)//POS_System_Project//version 2.3//POS_System_Project//src//main//java//com//Dickson//GUI//Image//homeButton1.png");
        btn_home = createButton("",home);
        btn_set = new JButton("Set");
        btn_set.setFont(myFont);
        btn_breakfast = new JButton("Breakfast");
        btn_breakfast.setFont(myFont);
        btn_noodle = new JButton("Noodle");
        btn_noodle.setFont(myFont);
        btn_rice = new JButton("Rice");
        btn_rice.setFont(myFont);
        btn_dessert = new JButton("Dessert");
        btn_dessert.setFont(myFont);
        btn_drink = new JButton("Drink");
        btn_drink.setFont(myFont);

        p1 = new JPanel();
        p1.setLayout(new GridLayout(7, 1, 0, 5));
        p1.add(btn_home);
        p1.add(btn_set);
        p1.add(btn_breakfast);
        p1.add(btn_noodle);
        p1.add(btn_rice);
        p1.add(btn_dessert);
        p1.add(btn_drink);

        JScrollPane scrollPane2 = new JScrollPane();
        scrollPane2.setLayout(new ScrollPaneLayout());
        buttonPanel2 = new JPanel();
        GridLayout gridLayout = new GridLayout(0, 3, 10, 10);
        buttonPanel2.setLayout(gridLayout);
        scrollPane2.setViewportView(buttonPanel2);

        p2 = new JPanel();
        p2.setLayout(new BorderLayout());
        p2.add(scrollPane2, BorderLayout.CENTER);

        JLabel em_name = new JLabel("Employee:" + returnname());
        em_name.setFont(myFont);
        JLabel table_no = new JLabel("Table:" + Passcode.getTableNumber());
        table_no.setFont(myFont);

        display = new JPanel();
        display.setLayout(new GridLayout(2, 1));
        display.add(em_name);
        display.add(table_no);

        lbl_order = new JLabel("Order");
        lbl_order.setFont(myFont);

        buttonClickCounts = new int[0];
        buttonLabelIndex = new int[0];
        buttonChoiceNames = new String[0];
        Arrays.fill(buttonLabelIndex, -1);

        btn_submit = new JButton("Submit");
        btn_submit.setPreferredSize(new Dimension(150, 150));
        btn_submit.setFont(myFont);

        btn_submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SwingUtilities.invokeLater(() -> {
                    Payment paymentFrame = new Payment(selectedProducts);
                    paymentFrame.setVisible(true);
                });
            }
        });

        p3 = new JPanel();
        p3.setLayout(new GridLayout(4, 1));
        p3.add(display);
        p3.add(lbl_order);

        panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
        nameLabelList = new ArrayList<>();

        scrollPane = new JScrollPane(panel2);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVisible(false);
        p3.add(scrollPane);
        p3.add(btn_submit);

        JPanel titleBar = new JPanel();
        titleBar.setLayout(new BorderLayout());
        titleBar.setOpaque(false);
        RoundButton close = new RoundButton("", new Color(237, 106, 94, 255), new Color(245, 189, 79, 255));
        RoundButton hide = new RoundButton("", new Color(245, 189, 79, 255), new Color(245, 189, 79, 255));
        RoundButton maximize = new RoundButton("", new Color(98, 198, 85, 255), new Color(98, 198, 85, 255));
        Icon closeIcon = new ImageIcon("C://Users//ASUS//Desktop//POS_System_Project//src//main//java//com//Dickson//GUI//Image//remove-svgrepo-com.png");
        Icon hideIcon = new ImageIcon("C://Users//ASUS//Desktop//POS_System_Project//src//main//java//com//Dickson//GUI//Image//minus-svgrepo-com.png");
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
        add(titleBar, BorderLayout.NORTH);

        getContentPane().add(titleBar, BorderLayout.NORTH);
        getContentPane().add(p1, BorderLayout.WEST);
        getContentPane().add(p2, BorderLayout.CENTER);
        getContentPane().add(p3, BorderLayout.EAST);

        btn_home.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                MainMenu mainmenuPanel = new MainMenu();
            }
        });

        btn_set.addActionListener(e -> {
            buttonPanel2.removeAll();
            List<String> images;
            try (SqlSession session = sqlSessionFactory.openSession(true)) {
                FunctionMapper mapper = session.getMapper(FunctionMapper.class);
                images = mapper.selectProductImagesByPrefix("s");
            }

            int totalImages = images.size();
            buttons = new JButton[totalImages];
            buttonChoiceNames = new String[totalImages];
            buttonClickCounts = new int[totalImages];
            buttonLabelIndex = new int[totalImages];
            Arrays.fill(buttonLabelIndex, -1);

            for (int i = 0; i < totalImages; i++) {
                JButton newButton = new JButton();
                newButton.setPreferredSize(new Dimension(200, 200));

                int finalI = i;
                newButton.addComponentListener(new ComponentAdapter() {
                    @Override
                    public void componentResized(ComponentEvent e) {
                        JButton button = (JButton) e.getComponent();
                        ImageIcon icon = new ImageIcon(images.get(finalI));
                        Image img = icon.getImage();
                        Image newImg = img.getScaledInstance(button.getWidth(), button.getHeight(), Image.SCALE_SMOOTH);
                        icon = new ImageIcon(newImg);
                        button.setIcon(icon);
                    }
                });

                buttons[i] = newButton;
                buttonPanel2.add(newButton);
            }

            buttonPanel2.revalidate();
            buttonPanel2.repaint();

            setupButtonActionListeners("s100");
        });

        btn_breakfast.addActionListener(e -> {
            buttonPanel2.removeAll();
            List<String> images;
            try (SqlSession session = sqlSessionFactory.openSession(true)) {
                FunctionMapper mapper = session.getMapper(FunctionMapper.class);
                images = mapper.selectProductImagesByPrefix("b");
            }
            int totalImages = images.size();
            buttons = new JButton[totalImages];
            buttonChoiceNames = new String[totalImages];
            buttonClickCounts = new int[totalImages];
            buttonLabelIndex = new int[totalImages];
            Arrays.fill(buttonLabelIndex, -1);

            for (int i = 0; i < totalImages; i++) {
                JButton newButton = new JButton();
                newButton.setPreferredSize(new Dimension(200, 200));
                ImageIcon icon = new ImageIcon(images.get(i));
                Image scaledImage = icon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH); // Scale the image
                ImageIcon scaledIcon = new ImageIcon(scaledImage);
                newButton.setIcon(scaledIcon);



                buttons[i] = newButton;
                buttonPanel2.add(newButton);
            }

            buttonPanel2.revalidate();
            buttonPanel2.repaint();

            setupButtonActionListeners("b100");
        });

        btn_noodle.addActionListener(e -> {
            buttonPanel2.removeAll();
            List<String> images;
            try (SqlSession session = sqlSessionFactory.openSession(true)) {
                FunctionMapper mapper = session.getMapper(FunctionMapper.class);
                images = mapper.selectProductImagesByPrefix("n");
            }
            int totalImages = images.size();
            buttons = new JButton[totalImages];
            buttonChoiceNames = new String[totalImages];
            buttonClickCounts = new int[totalImages];
            buttonLabelIndex = new int[totalImages];
            Arrays.fill(buttonLabelIndex, -1);

            for (int i = 0; i < totalImages; i++) {
                JButton newButton = new JButton();
                newButton.setPreferredSize(new Dimension(200, 200));
                ImageIcon icon = new ImageIcon(images.get(i));
                Image scaledImage = icon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH); // Scale the image
                ImageIcon scaledIcon = new ImageIcon(scaledImage);
                newButton.setIcon(scaledIcon);
                buttons[i] = newButton;
                buttonPanel2.add(newButton);
            }

            buttonPanel2.revalidate();
            buttonPanel2.repaint();

            setupButtonActionListeners("n100");
        });

        btn_rice.addActionListener(e -> {
            buttonPanel2.removeAll();
            List<String> images;
            try (SqlSession session = sqlSessionFactory.openSession(true)) {
                FunctionMapper mapper = session.getMapper(FunctionMapper.class);
                images = mapper.selectProductImagesByPrefix("r");
            }
            int totalImages = images.size();
            buttons = new JButton[totalImages];
            buttonChoiceNames = new String[totalImages];
            buttonClickCounts = new int[totalImages];
            buttonLabelIndex = new int[totalImages];
            Arrays.fill(buttonLabelIndex, -1);

            for (int i = 0; i < totalImages; i++) {
                JButton newButton = new JButton();
                newButton.setPreferredSize(new Dimension(200, 200));
                ImageIcon icon = new ImageIcon(images.get(i));
                Image scaledImage = icon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH); // Scale the image
                ImageIcon scaledIcon = new ImageIcon(scaledImage);
                newButton.setIcon(scaledIcon);
                buttons[i] = newButton;
                buttonPanel2.add(newButton);
            }

            buttonPanel2.revalidate();
            buttonPanel2.repaint();

            setupButtonActionListeners("r100");
        });

        btn_dessert.addActionListener(e -> {
            buttonPanel2.removeAll();
            List<String> images;
            try (SqlSession session = sqlSessionFactory.openSession(true)) {
                FunctionMapper mapper = session.getMapper(FunctionMapper.class);
                images = mapper.selectProductImagesByPrefix("d1");
            }
            int totalImages = images.size();
            buttons = new JButton[totalImages];
            buttonChoiceNames = new String[totalImages];
            buttonClickCounts = new int[totalImages];
            buttonLabelIndex = new int[totalImages];
            Arrays.fill(buttonLabelIndex, -1);

            for (int i = 0; i < totalImages; i++) {
                JButton newButton = new JButton();
                newButton.setPreferredSize(new Dimension(200, 200));
                ImageIcon icon = new ImageIcon(images.get(i));
                Image scaledImage = icon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH); // Scale the image
                ImageIcon scaledIcon = new ImageIcon(scaledImage);
                newButton.setIcon(scaledIcon);
                buttons[i] = newButton;
                buttonPanel2.add(newButton);
            }

            buttonPanel2.revalidate();
            buttonPanel2.repaint();

            setupButtonActionListeners("d100");
        });

        btn_drink.addActionListener(e -> {
            buttonPanel2.removeAll();
            List<String> images;
            try (SqlSession session = sqlSessionFactory.openSession(true)) {
                FunctionMapper mapper = session.getMapper(FunctionMapper.class);
                images = mapper.selectProductImagesByPrefix("d2");
            }
            int totalImages = images.size();
            buttons = new JButton[totalImages];
            buttonChoiceNames = new String[totalImages];
            buttonClickCounts = new int[totalImages];
            buttonLabelIndex = new int[totalImages];
            Arrays.fill(buttonLabelIndex, -1);

            for (int i = 0; i < totalImages; i++) {
                JButton newButton = new JButton();
                newButton.setPreferredSize(new Dimension(200, 200));
                ImageIcon icon = new ImageIcon(images.get(i));
                Image scaledImage = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH); // Scale the image
                ImageIcon scaledIcon = new ImageIcon(scaledImage);
                newButton.setIcon(scaledIcon);
                buttons[i] = newButton;
                buttonPanel2.add(newButton);
            }

            buttonPanel2.revalidate();
            buttonPanel2.repaint();

            setupButtonActionListeners("d200");
        });

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
    }

    private JButton createButton(String text, ImageIcon img) {
        // Get the original image
        Image originalImage = img.getImage();

        // Scale the original image to fit the button size smoothly
        Image scaledImage = originalImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);

        // Create a new ImageIcon with the scaled image
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        // Create the button with the scaled ImageIcon
        JButton button = new JButton(text, scaledIcon);
        button.setPreferredSize(new Dimension(100, 200));
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(new FlatMacLightLaf());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                new Set().setVisible(true);
            }
        });
    }

    private String returnname() {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            FunctionMapper mapper = session.getMapper(FunctionMapper.class);
            employename = mapper.show_name(Status.getLogged_Status());
            return employename;
        }
    }

    public List<String> getSelectedProducts() {
        return selectedProducts;
    }

    private void setupButtonActionListeners(String menuId) {
        for (int i = 0; i < buttons.length; i++) {
            final int buttonIndex = i;
            buttons[buttonIndex].removeActionListener(null);
            buttons[buttonIndex].addActionListener(e -> {
                scrollPane.setVisible(true);
                String image_id_special = menuId + (buttonIndex + 1);
                try (SqlSession session = sqlSessionFactory.openSession(true)) {
                    FunctionMapper mapper = session.getMapper(FunctionMapper.class);
                    choice_name = mapper.displayselectioname(image_id_special);
                    System.out.println(image_id_special);
                }
                buttonChoiceNames[buttonIndex] = choice_name;
                String labelId = menuId + "_" + buttonIndex;
                boolean labelFound = false;
                int labelIndex = -1;
                for (int j = 0; j < nameLabelList.size(); j++) {
                    if (nameLabelList.get(j).getName().equals(labelId)) {
                        labelFound = true;
                        labelIndex = j;
                        break;
                    }
                }
                if (labelFound) {
                    buttonClickCounts[labelIndex]++;
                    JLabel nameLabelToUpdate = nameLabelList.get(labelIndex);
                    nameLabelToUpdate.setText(buttonChoiceNames[buttonIndex] + " " + buttonClickCounts[labelIndex]);
                } else {
                    buttonClickCounts[buttonIndex]++;
                    JLabel nameLabel = new JLabel(choice_name + buttonClickCounts[buttonIndex]);
                    nameLabel.setName(labelId);
                    nameLabelList.add(nameLabel);
                    panel2.add(nameLabel);
                    JButton cancelButton = new JButton("X");
                    cancelButton.addActionListener(cancelEvent -> {
                        JButton sourceButton = (JButton) cancelEvent.getSource();
                        int buttonIndexToRemove = buttonIndex;
                        panel2.remove(nameLabel);
                        panel2.remove(sourceButton);
                        nameLabelList.remove(nameLabel);
                        buttonClickCounts[buttonIndexToRemove] = 0;
                        panel2.revalidate();
                        panel2.repaint();

                        // Remove the corresponding product from the selectedProducts list
                        selectedProducts.remove(buttonChoiceNames[buttonIndexToRemove]);
                    });

                    panel2.add(cancelButton);
                }
                if (panel2.getComponentCount() > 3) {
                    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                }
                panel2.revalidate();
                panel2.repaint();
                selectedProducts.add(choice_name);
            });
        }
    }

}