package com.Dickson.GUI;

import com.Dickson.GUI.MainMenu;
import com.Dickson.Mapperdao.FunctionMapper;
import com.Dickson.entity.Product;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Arrays;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class AddMenu extends JFrame {
    @Getter
    @Setter
    private JPanel p1, p2, p3, display, panel2, buttonPanel2;
    private JButton btn_home, btn_set, btn_breakfast, btn_noodle, btn_rice, btn_dessert, btn_drink, btn_insert, btn_update, btn_delete;
    private JLabel lbl_productdetails, lbl_selectedImage, lbl_price, lbl_product_category, lbl_name, lbl_productID;
    private JTextField tf_productID, tf_name, tf_price;
    private JComboBox<String> cb_category;
    private JButton[] buttons = null;
    private JScrollPane scrollPane;
    private int[] buttonLabelIndex, buttonClickCounts;
    private String[] buttonChoiceNames;
    private List<JLabel> nameLabelList;
    @Setter
    @Getter
    private String imagePath, productID;

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

    public AddMenu() {
        setTitle("Add Menu");
        setExtendedState(JFrame.MAXIMIZED_BOTH);        
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initialComponent();
    }

    public void initialComponent() {
        Font myFont = new Font("Serif",Font.BOLD,16);
        btn_home = new JButton();
        btn_home.setFont(myFont);
        ImageIcon homeImage = new ImageIcon("C://Users//USER//OneDrive - S Seri Kledang//SkuLLLL//Semester 5//Java 2//Assignment//POS_System_Project (Function Done)//POS_System_Project//version 2.3//POS_System_Project//src//main//java//com//Dickson//GUI//Image//homeButton1.png");
        Image scaledHome = homeImage.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH); 
        btn_home.setIcon(new ImageIcon(scaledHome));
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
        p1.setLayout(new GridLayout(7, 1));
        p1.add(btn_home);
        p1.add(btn_set);
        p1.add(btn_breakfast);
        p1.add(btn_noodle);
        p1.add(btn_rice);
        p1.add(btn_dessert);
        p1.add(btn_drink);

        lbl_productdetails = new JLabel("Product Details: ");
        lbl_productdetails.setFont(myFont);

        lbl_productID = new JLabel("Product ID: ");
        lbl_productID.setFont(myFont);
        lbl_name = new JLabel("Product name: ");
        lbl_name.setFont(myFont);
        lbl_product_category = new JLabel("Category: ");
        lbl_product_category.setFont(myFont);
        lbl_price = new JLabel("Price: ");
        lbl_price.setFont(myFont);

        tf_productID = new JTextField(20);
        tf_name = new JTextField(20);
        cb_category = new JComboBox<>(new String[]{"", "Set", "Breakfast", "Noodle", "Rice", "Dessert", "Drink"});
        tf_price = new JTextField(20);

        btn_insert = new JButton("Add");
        btn_insert.setFont(myFont);
        btn_update = new JButton("Update");
        btn_update.setFont(myFont);
        btn_delete = new JButton("Delete");
        btn_delete.setFont(myFont);

        btn_home.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                MainMenu mainmenuPanel = new MainMenu();
            }
        });

        btn_set.addActionListener(e -> {
            buttonPanel2.removeAll();
            try (SqlSession session = sqlSessionFactory.openSession(true)) {
                FunctionMapper mapper = session.getMapper(FunctionMapper.class);
                List<Product> images = mapper.selectProductImagesByPrefix1("s");

                int totalImages = images.size();
                buttons = new JButton[totalImages];
                buttonChoiceNames = new String[totalImages];
                buttonClickCounts = new int[totalImages];
                buttonLabelIndex = new int[totalImages];
                Arrays.fill(buttonLabelIndex, -1);

                for (int i = 0; i < totalImages; i++) {
                    Product product = images.get(i);
                    String id = product.getProduct_id();

                    List<String> productImages = mapper.selectProductImagesById1(id);

                    JButton newButton = new JButton();
                    newButton.setPreferredSize(new Dimension(200, 200));
                    newButton.setActionCommand(id);

                    if (productImages != null && !productImages.isEmpty()) {
                        String firstImage = productImages.getFirst();
                        ImageIcon imageIcon = new ImageIcon(firstImage);
                        Image image = imageIcon.getImage();
                        Image newimg = image.getScaledInstance(300, 300, java.awt.Image.SCALE_SMOOTH);
                        imageIcon = new ImageIcon(newimg);
                        newButton.setIcon(imageIcon);
                    }

                    newButton.addActionListener(e1 -> {
                        reset();
                        setProductID(id);
                        try (SqlSession session1 = sqlSessionFactory.openSession(true)) {
                            FunctionMapper mapper2 = session1.getMapper(FunctionMapper.class);
                            Product var = mapper2.selectProductId1(id);
                            tf_productID.setText(var.getProduct_id());
                            tf_name.setText(var.getProduct_name());
                            cb_category.setSelectedItem(var.getProduct_category());
                            tf_price.setText(String.valueOf(var.getPrice()));
                            ImageIcon originalIcon = new ImageIcon(productImages.getFirst());
                            Image originalImage = originalIcon.getImage();
                            Image resizedImage = originalImage.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);
                            ImageIcon resizedIcon = new ImageIcon(resizedImage);
                            lbl_selectedImage.setIcon(resizedIcon);
                        }
                    });

                    buttons[i] = newButton;
                    buttonPanel2.add(newButton);
                }

                buttonPanel2.revalidate();
                buttonPanel2.repaint();
            }
            buttonPanel2.revalidate();
            buttonPanel2.repaint();
            reset();
        });


        btn_breakfast.addActionListener(e -> {
            buttonPanel2.removeAll();
            try (SqlSession session = sqlSessionFactory.openSession(true)) {
                FunctionMapper mapper = session.getMapper(FunctionMapper.class);
                List<Product> images = mapper.selectProductImagesByPrefix1("b");

                int totalImages = images.size();
                buttons = new JButton[totalImages];
                buttonChoiceNames = new String[totalImages];
                buttonClickCounts = new int[totalImages];
                buttonLabelIndex = new int[totalImages];
                Arrays.fill(buttonLabelIndex, -1);

                for (int i = 0; i < totalImages; i++) {
                    Product product = images.get(i);
                    String id = product.getProduct_id();


                    List<String> productImages = mapper.selectProductImagesById1(id);

                    JButton newButton = new JButton();
                    newButton.setPreferredSize(new Dimension(200, 200));
                    newButton.setActionCommand(id);

                    if (productImages != null && !productImages.isEmpty()) {
                        String firstImage = productImages.getFirst();
                        ImageIcon imageIcon = new ImageIcon(firstImage);
                        Image image = imageIcon.getImage();
                        Image newimg = image.getScaledInstance(300, 300, java.awt.Image.SCALE_SMOOTH);
                        imageIcon = new ImageIcon(newimg);
                        newButton.setIcon(imageIcon);
                    }

                    newButton.addActionListener(e1 -> {
                        JButton button = (JButton) e1.getSource();
                        setProductID(id);
                        try (SqlSession session1 = sqlSessionFactory.openSession(true)) {
                            FunctionMapper mapper2 = session1.getMapper(FunctionMapper.class);
                            Product var = mapper2.selectProductId1(id);
                            tf_productID.setText(var.getProduct_id());
                            tf_name.setText(var.getProduct_name());
                            cb_category.setSelectedItem(var.getProduct_category());
                            tf_price.setText(String.valueOf(var.getPrice()));
                            ImageIcon originalIcon = new ImageIcon(productImages.getFirst());
                            Image originalImage = originalIcon.getImage();
                            Image resizedImage = originalImage.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);
                            ImageIcon resizedIcon = new ImageIcon(resizedImage);
                            lbl_selectedImage.setIcon(resizedIcon);
                        }
                    });

                    buttons[i] = newButton;
                    buttonPanel2.add(newButton);
                }

                buttonPanel2.revalidate();
                buttonPanel2.repaint();
            }
            buttonPanel2.revalidate();
            buttonPanel2.repaint();
            reset();
        });

        btn_noodle.addActionListener(e -> {
            buttonPanel2.removeAll();
            try (SqlSession session = sqlSessionFactory.openSession(true)) {
                FunctionMapper mapper = session.getMapper(FunctionMapper.class);
                List<Product> images = mapper.selectProductImagesByPrefix1("n");

                int totalImages = images.size();
                buttons = new JButton[totalImages];
                buttonChoiceNames = new String[totalImages];
                buttonClickCounts = new int[totalImages];
                buttonLabelIndex = new int[totalImages];
                Arrays.fill(buttonLabelIndex, -1);

                for (int i = 0; i < totalImages; i++) {
                    Product product = images.get(i);
                    String id = product.getProduct_id();


                    List<String> productImages = mapper.selectProductImagesById1(id);

                    JButton newButton = new JButton();
                    newButton.setPreferredSize(new Dimension(200, 200));
                    newButton.setActionCommand(id);

                    if (productImages != null && !productImages.isEmpty()) {
                        String firstImage = productImages.getFirst();
                        ImageIcon imageIcon = new ImageIcon(firstImage);
                        Image image = imageIcon.getImage();
                        Image newimg = image.getScaledInstance(300, 300, java.awt.Image.SCALE_SMOOTH);
                        imageIcon = new ImageIcon(newimg);
                        newButton.setIcon(imageIcon);
                    }

                    newButton.addActionListener(e1 -> {
                        JButton button = (JButton) e1.getSource();
                        setProductID(id);
                        try (SqlSession session1 = sqlSessionFactory.openSession(true)) {
                            FunctionMapper mapper2 = session1.getMapper(FunctionMapper.class);
                            Product var = mapper2.selectProductId1(id);
                            tf_productID.setText(var.getProduct_id());
                            tf_name.setText(var.getProduct_name());
                            cb_category.setSelectedItem(var.getProduct_category());
                            tf_price.setText(String.valueOf(var.getPrice()));
                            ImageIcon originalIcon = new ImageIcon(productImages.getFirst());
                            Image originalImage = originalIcon.getImage();
                            Image resizedImage = originalImage.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);
                            ImageIcon resizedIcon = new ImageIcon(resizedImage);
                            lbl_selectedImage.setIcon(resizedIcon);
                        }
                    });

                    buttons[i] = newButton;
                    buttonPanel2.add(newButton);
                }

                buttonPanel2.revalidate();
                buttonPanel2.repaint();
            }
            buttonPanel2.revalidate();
            buttonPanel2.repaint();
            reset();
        });

        btn_rice.addActionListener(e -> {
            buttonPanel2.removeAll();
            try (SqlSession session = sqlSessionFactory.openSession(true)) {
                FunctionMapper mapper = session.getMapper(FunctionMapper.class);
                List<Product> images = mapper.selectProductImagesByPrefix1("r");

                int totalImages = images.size();
                buttons = new JButton[totalImages];
                buttonChoiceNames = new String[totalImages];
                buttonClickCounts = new int[totalImages];
                buttonLabelIndex = new int[totalImages];
                Arrays.fill(buttonLabelIndex, -1);

                for (int i = 0; i < totalImages; i++) {
                    Product product = images.get(i);
                    String id = product.getProduct_id();


                    List<String> productImages = mapper.selectProductImagesById1(id);

                    JButton newButton = new JButton();
                    newButton.setPreferredSize(new Dimension(200, 200));
                    newButton.setActionCommand(id);

                    if (productImages != null && !productImages.isEmpty()) {
                        String firstImage = productImages.getFirst();
                        ImageIcon imageIcon = new ImageIcon(firstImage);
                        Image image = imageIcon.getImage();
                        Image newimg = image.getScaledInstance(300, 300, java.awt.Image.SCALE_SMOOTH);
                        imageIcon = new ImageIcon(newimg);
                        newButton.setIcon(imageIcon);
                    }

                    newButton.addActionListener(e1 -> {
                        JButton button = (JButton) e1.getSource();
                        setProductID(id);
                        try (SqlSession session1 = sqlSessionFactory.openSession(true)) {
                            FunctionMapper mapper2 = session1.getMapper(FunctionMapper.class);
                            Product var = mapper2.selectProductId1(id);
                            tf_productID.setText(var.getProduct_id());
                            tf_name.setText(var.getProduct_name());
                            cb_category.setSelectedItem(var.getProduct_category());
                            tf_price.setText(String.valueOf(var.getPrice()));
                            ImageIcon originalIcon = new ImageIcon(productImages.getFirst());
                            Image originalImage = originalIcon.getImage();
                            Image resizedImage = originalImage.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);
                            ImageIcon resizedIcon = new ImageIcon(resizedImage);
                            lbl_selectedImage.setIcon(resizedIcon);
                        }
                    });

                    buttons[i] = newButton;
                    buttonPanel2.add(newButton);
                }

                buttonPanel2.revalidate();
                buttonPanel2.repaint();
            }
            buttonPanel2.revalidate();
            buttonPanel2.repaint();
            reset();
        });

        btn_dessert.addActionListener(e -> {
            buttonPanel2.removeAll();
            try (SqlSession session = sqlSessionFactory.openSession(true)) {
                FunctionMapper mapper = session.getMapper(FunctionMapper.class);
                List<Product> images = mapper.selectProductImagesByPrefix1("d1");

                int totalImages = images.size();
                buttons = new JButton[totalImages];
                buttonChoiceNames = new String[totalImages];
                buttonClickCounts = new int[totalImages];
                buttonLabelIndex = new int[totalImages];
                Arrays.fill(buttonLabelIndex, -1);

                for (int i = 0; i < totalImages; i++) {
                    Product product = images.get(i);
                    String id = product.getProduct_id();


                    List<String> productImages = mapper.selectProductImagesById1(id);

                    JButton newButton = new JButton();
                    newButton.setPreferredSize(new Dimension(200, 200));
                    newButton.setActionCommand(id);

                    if (productImages != null && !productImages.isEmpty()) {
                        String firstImage = productImages.getFirst();
                        ImageIcon imageIcon = new ImageIcon(firstImage);
                        Image image = imageIcon.getImage();
                        Image newimg = image.getScaledInstance(300, 300, java.awt.Image.SCALE_SMOOTH);
                        imageIcon = new ImageIcon(newimg);
                        newButton.setIcon(imageIcon);
                    }

                    newButton.addActionListener(e1 -> {
                        JButton button = (JButton) e1.getSource();
                        setProductID(id);
                        try (SqlSession session1 = sqlSessionFactory.openSession(true)) {
                            FunctionMapper mapper2 = session1.getMapper(FunctionMapper.class);
                            Product var = mapper2.selectProductId1(id);
                            tf_productID.setText(var.getProduct_id());
                            tf_name.setText(var.getProduct_name());
                            cb_category.setSelectedItem(var.getProduct_category());
                            tf_price.setText(String.valueOf(var.getPrice()));
                            ImageIcon originalIcon = new ImageIcon(productImages.getFirst());
                            Image originalImage = originalIcon.getImage();
                            Image resizedImage = originalImage.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);
                            ImageIcon resizedIcon = new ImageIcon(resizedImage);
                            lbl_selectedImage.setIcon(resizedIcon);
                        }
                    });

                    buttons[i] = newButton;
                    buttonPanel2.add(newButton);
                }

                buttonPanel2.revalidate();
                buttonPanel2.repaint();
            }
            buttonPanel2.revalidate();
            buttonPanel2.repaint();
            reset();
        });

        btn_drink.addActionListener(e -> {
            buttonPanel2.removeAll();
            try (SqlSession session = sqlSessionFactory.openSession(true)) {
                FunctionMapper mapper = session.getMapper(FunctionMapper.class);
                List<Product> images = mapper.selectProductImagesByPrefix1("d2");

                int totalImages = images.size();
                buttons = new JButton[totalImages];
                buttonChoiceNames = new String[totalImages];
                buttonClickCounts = new int[totalImages];
                buttonLabelIndex = new int[totalImages];
                Arrays.fill(buttonLabelIndex, -1);

                for (int i = 0; i < totalImages; i++) {
                    Product product = images.get(i);
                    String id = product.getProduct_id();


                    List<String> productImages = mapper.selectProductImagesById1(id);

                    JButton newButton = new JButton();
                    newButton.setPreferredSize(new Dimension(200, 200));
                    newButton.setActionCommand(id);

                    if (productImages != null && !productImages.isEmpty()) {
                        String firstImage = productImages.getFirst();
                        ImageIcon imageIcon = new ImageIcon(firstImage);
                        Image image = imageIcon.getImage();
                        Image newimg = image.getScaledInstance(300, 300, java.awt.Image.SCALE_SMOOTH);
                        imageIcon = new ImageIcon(newimg);
                        newButton.setIcon(imageIcon);
                    }

                    newButton.addActionListener(e1 -> {
                        JButton button = (JButton) e1.getSource();
                        setProductID(id);
                        try (SqlSession session1 = sqlSessionFactory.openSession(true)) {
                            FunctionMapper mapper2 = session1.getMapper(FunctionMapper.class);
                            Product var = mapper2.selectProductId1(id);
                            tf_productID.setText(var.getProduct_id());
                            tf_name.setText(var.getProduct_name());
                            cb_category.setSelectedItem(var.getProduct_category());
                            tf_price.setText(String.valueOf(var.getPrice()));
                            ImageIcon originalIcon = new ImageIcon(productImages.getFirst());
                            Image originalImage = originalIcon.getImage();
                            Image resizedImage = originalImage.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);
                            ImageIcon resizedIcon = new ImageIcon(resizedImage);
                            lbl_selectedImage.setIcon(resizedIcon);
                        }
                    });

                    buttons[i] = newButton;
                    buttonPanel2.add(newButton);
                }

                buttonPanel2.revalidate();
                buttonPanel2.repaint();
            }
            buttonPanel2.revalidate();
            buttonPanel2.repaint();
            reset();
        });

        JScrollPane scrollPane2 = new JScrollPane();
        scrollPane2.setLayout(new ScrollPaneLayout());
        buttonPanel2 = new JPanel();
        GridLayout gridLayout = new GridLayout(0, 3, 10, 10);
        buttonPanel2.setLayout(gridLayout);
        scrollPane2.setViewportView(buttonPanel2);

        p2 = new JPanel();
        p2.setLayout(new BorderLayout());
        p2.add(scrollPane2, BorderLayout.CENTER);

        btn_insert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String productId = tf_productID.getText();
                String productName = tf_name.getText();
                String category = (String) cb_category.getSelectedItem();
                double price = Double.parseDouble(tf_price.getText());

                try {
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pos_system", "root", "Kian_Seng8");
                    String query = "INSERT INTO Product (product_id, product_name, product_category, price, product_image) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement statement = conn.prepareStatement(query);
                    statement.setString(1, productId);
                    statement.setString(2, productName);
                    statement.setString(3, category);
                    statement.setDouble(4, price);
                    if (imagePath != null) {
                        statement.setString(5, imagePath);
                    } else {
                        statement.setNull(5, Types.VARCHAR);
                    }
                    statement.executeUpdate();
                    reset();
                    JOptionPane.showMessageDialog(null, "Record inserted successfully!");
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error inserting record: " + ex.getMessage());
                }
            }
        });

        btn_update.addActionListener(e -> {

            if (imagePath == null) {
                JOptionPane.showMessageDialog(null, "Please upload picture");
                return;
            }
            if (tf_productID.getText().equals(getProductID())) {
                JOptionPane.showMessageDialog(null, "ID exits!");
                return;
            }
            if (tf_name.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "name cannot be blank!");
                return;
            }
            if (cb_category.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(null, "Please choose category!");
            }
            try (SqlSession session = sqlSessionFactory.openSession(true)) {
                FunctionMapper mapper = session.getMapper(FunctionMapper.class);
                System.out.println(getProductID());
                mapper.updateProduct(tf_productID.getText(), tf_name.getText(), (String) cb_category.getSelectedItem(), Double.parseDouble(tf_price.getText()), imagePath, getProductID());
                JOptionPane.showMessageDialog(null, "Update successfully!");
                reset();
            }
        });


        btn_delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String productId = tf_productID.getText();
                try {
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pos_system", "root", "Kian_Seng8");
                    String query = "DELETE FROM Product WHERE product_id = ?";
                    PreparedStatement statement = conn.prepareStatement(query);
                    statement.setString(1, productId);
                    statement.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record deleted successfully!");
                    reset();
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error deleting record: " + ex.getMessage());
                }
            }
        });

        JPanel p_detailname = new JPanel();
        p_detailname.add(lbl_productdetails);

        JPanel p_add = new JPanel(new GridLayout(4, 2));
        p_add.add(lbl_productID);
        p_add.add(tf_productID);
        p_add.add(lbl_name);
        p_add.add(tf_name);
        p_add.add(lbl_product_category);
        p_add.add(cb_category);
        p_add.add(lbl_price);
        p_add.add(tf_price);

        JPanel p_text = new JPanel(new GridLayout(1, 1));
        p_text.setPreferredSize(new Dimension(200, 100));
        p_text.add(p_add);

        JPanel p_chooseImage = new JPanel(new BorderLayout());
        p_chooseImage.setPreferredSize(new Dimension(200, 100));

        JButton btn_ChooseImage = new JButton("Choose Image");
        btn_ChooseImage.setPreferredSize(new Dimension(200, 30));
        btn_ChooseImage.setFont(myFont);

        lbl_selectedImage = new JLabel();
        lbl_selectedImage.setHorizontalAlignment(SwingConstants.CENTER);

        btn_ChooseImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpg", "jpeg", "png", "gif");
                fileChooser.setFileFilter(filter);
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    imagePath = selectedFile.getAbsolutePath();
                    lbl_selectedImage.setIcon(new ImageIcon(imagePath));
                }
            }
        });

        p_chooseImage.add(btn_ChooseImage, BorderLayout.NORTH);
        p_chooseImage.add(lbl_selectedImage, BorderLayout.CENTER);

        JPanel p_button = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        p_button.add(btn_insert);
        p_button.add(btn_update);
        p_button.add(btn_delete);

        JPanel p_textAndImage = new JPanel(new GridLayout(2, 1));

        p_textAndImage.add(p_text);
        p_textAndImage.add(p_chooseImage);

        p3 = new JPanel();
        p3.setLayout(new BorderLayout());
        p3.add(p_detailname, BorderLayout.NORTH);
        p3.add(p_textAndImage, BorderLayout.CENTER);
        p3.add(p_button, BorderLayout.SOUTH);

        getContentPane().add(p1, BorderLayout.WEST);
        getContentPane().add(p2);
        getContentPane().add(p3, BorderLayout.EAST);
    }

    private JButton createButton() {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(200, 200));
        return button;
    }

    private void addPanelWithButton(JPanel panel, JButton button) {
        JPanel subPanel = new JPanel();
        subPanel.setLayout(new GridBagLayout());
        subPanel.add(button);
        panel.add(subPanel);
    }

    public void reset() {
        lbl_selectedImage.setIcon(null);
        tf_name.setText(null);
        tf_productID.setText(null);
        tf_price.setText(null);
        cb_category.setSelectedItem(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(new FlatMacDarkLaf());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                new AddMenu().setVisible(true);
            }
        });
    }
}