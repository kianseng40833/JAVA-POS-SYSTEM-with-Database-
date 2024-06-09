package com.Dickson.GUI;

import com.Dickson.GUI.Menu.Set;
import com.Dickson.Mapperdao.FunctionMapper;
import com.Dickson.entity.Status;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.io.InputStream;
import java.awt.print.PageFormat;

public class Payment extends JFrame {
    private JPanel leftPanel, middlePanel, bottomPanel;
    private JPanel secondPanel;
    private JPanel voucherPanel, receiptPanel;
    private JPanel eWalletPanel, eWalletPanel2, eWalletPanel3, cashPanel, bankPanel, bankPanel2;

    private JButton homeButton, addButton, printButton, payButton, eWButton1, eWButton2, eWButton3,cashButton, bankButton1, bankButton2;
    private JLabel lblOrder, lblVoucher, lblEWallet, lblCash, lblBank;
    private JTextField tfVoucher;
    private JLabel[] lblOrderedProducts;
    private JScrollPane scrollPane;
    private List <String> selectedProducts;
    private JTextArea receiptTextArea;
    private double totalReceived;
    private double totalChange;
    private double discount;
    private double totalPrice;
    private double afterDiscount;
    private String paymentType;
    private String acct;
    private int table_no;
    private String employee;
    private int receipt_id;
    private Font myFont = new Font("Serif",Font.BOLD,16);

    static final String DRIVER ="com.mysql.cj.jdbc.Driver";
    static final String DB_URL ="jdbc:mysql://localhost/pos_system?serverTimezone=UTC";
    static SqlSessionFactory sqlSessionFactory = null;

    static {
        String resource = "mybatis-config.xml";
        InputStream inputStream;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    public Payment(List <String> selectedProducts) {
        this.selectedProducts = selectedProducts;
        setTitle("Payment");
        setSize(1200, 600);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initialComponent();

        displaySelectedProducts(selectedProducts);

        receiptTextArea = new JTextArea();
        receiptTextArea.setEditable(false);
        JScrollPane receiptScrollPane = new JScrollPane(receiptTextArea);
        receiptPanel.add(receiptScrollPane);

        generateAndDisplayReceipt(selectedProducts);
    }
    private String returnName() {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            FunctionMapper mapper = session.getMapper(FunctionMapper.class);
            employee = mapper.show_name(Status.getLogged_Status());
            return employee;
        }
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

    public void initialComponent() {
        leftPanel = new JPanel(new GridLayout(4, 1));
        leftPanel.setPreferredSize(new Dimension(250, getHeight()));

        homeButton = new JButton("Home");
        ImageIcon home = new ImageIcon("");
        homeButton = createButton("",home);
        homeButton.setPreferredSize(new Dimension(10, 50));
        homeButton.setBorder(new EmptyBorder(0, 0, 0, 0));
        leftPanel.add(homeButton);

        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                MainMenu HomePanel = new MainMenu();
            }
        });

        lblOrder = new JLabel("Ordered Product:");
        lblOrder.setFont(myFont);
        leftPanel.add(lblOrder);

        lblOrderedProducts = new JLabel[20];
        JPanel productPanel = new JPanel(new GridLayout(0, 1));
        for (int i = 0; i < lblOrderedProducts.length; i++) {
            lblOrderedProducts[i] = new JLabel((i + 1) + ". ");
            productPanel.add(lblOrderedProducts[i]);
        }
        scrollPane = new JScrollPane(productPanel);
        leftPanel.add(scrollPane);

        addButton = new JButton(" ADD ");
        addButton.setFont(myFont);
        leftPanel.add(addButton);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();

                Set setPanel = new Set(selectedProducts);
                setPanel.setSize(1200, 600);
                setPanel.setVisible(true);
            }
        });

        middlePanel = new JPanel();
        middlePanel.setLayout(new BorderLayout());
        receiptPanel = new JPanel();
        receiptPanel.setLayout(new FlowLayout(FlowLayout.CENTER));


        receiptTextArea = new JTextArea();
        receiptTextArea.setEditable(false);
        receiptPanel.add(receiptTextArea);

        JScrollPane receiptScrollPane = new JScrollPane(receiptPanel);

        JPanel payPrintPanel = new JPanel();
        payButton = new JButton("Pay");
        payButton.setFont(myFont);
        printButton = new JButton(" Print ");
        printButton.setFont(myFont);
        payButton.setPreferredSize(new Dimension(80, 400));

        payButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                insertReceiptDatabase(table_no, totalPrice,totalReceived, totalChange,discount,afterDiscount, paymentType, selectedProducts, acct, employee);
                insertHistoryDatabase(receipt_id);
            }
        });

        printButton.setPreferredSize(new Dimension(80, 400));
        payPrintPanel.add(payButton);
        payPrintPanel.add(printButton);
        voucherPanel = new JPanel(new FlowLayout());
        lblVoucher = new JLabel("Voucher Code: ");
        lblVoucher.setFont(myFont);
        tfVoucher = new JTextField(8);
        JButton applyButton = new JButton("Apply");

        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String voucherId = tfVoucher.getText();
                applyVoucher(voucherId);
            }
        });
        voucherPanel.add(lblVoucher);
        voucherPanel.add(tfVoucher);
        voucherPanel.add(applyButton);
        middlePanel.add(receiptScrollPane, BorderLayout.CENTER);
        middlePanel.add(payPrintPanel, BorderLayout.EAST);
        middlePanel.add(voucherPanel, BorderLayout.SOUTH);

        bottomPanel = new JPanel(new GridBagLayout());
        bottomPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        eWalletPanel = new JPanel(new GridLayout(3, 1));
        eWalletPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        lblEWallet = new JLabel(" E-Wallet ");

        eWalletPanel2 = new JPanel();
        eWalletPanel2.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        eWButton1 = new JButton("");
        eWButton1.setPreferredSize(new Dimension(100,100));

        ImageIcon eWButton1Image = new ImageIcon("");
        Image scaledTnG = eWButton1Image.getImage().getScaledInstance(200, 100, Image.SCALE_SMOOTH);
        eWButton1.setIcon(new ImageIcon(scaledTnG));

        ImageIcon eWButton2Image = new ImageIcon("");
        Image scaledGrab = eWButton2Image.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);

        ImageIcon eWButton3Image = new ImageIcon("");
        Image scaledShopee = eWButton3Image.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);

        eWButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setPaymentType("TnG");
                StringBuilder newText = new StringBuilder(receiptTextArea.getText());
                int startIndex = newText.indexOf("Payment Type: ") + "Payment Type: ".length();
                int endIndex = newText.indexOf("\n", startIndex);

                String newPaymentType = String.format("%-42s\t\t", paymentType);

                newText.replace(startIndex, endIndex, newPaymentType);

                receiptTextArea.setText(newText.toString());

                updateTotalReceivedEWallet();
            }
        });
        eWButton2 = new JButton("");
        eWButton2.setIcon(new ImageIcon(scaledGrab));
        eWButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setPaymentType("Grab Pay");
                StringBuilder newText = new StringBuilder(receiptTextArea.getText());
                int startIndex = newText.indexOf("Payment Type: ") + "Payment Type: ".length();
                int endIndex = newText.indexOf("\n", startIndex);

                String newPaymentType = String.format("%-42s\t\t", paymentType);

                newText.replace(startIndex, endIndex, newPaymentType);

                receiptTextArea.setText(newText.toString());

                updateTotalReceivedEWallet();
            }
        });

        eWalletPanel2.add(eWButton1);
        eWalletPanel2.add(eWButton2);

        eWalletPanel3 = new JPanel();
        eWButton3 = new JButton("");
        eWButton3.setIcon(new ImageIcon(scaledShopee));
        eWButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setPaymentType("Shopee Pay");
                StringBuilder newText = new StringBuilder(receiptTextArea.getText());
                int startIndex = newText.indexOf("Payment Type: ") + "Payment Type: ".length();
                int endIndex = newText.indexOf("\n", startIndex);

                String newPaymentType = String.format("%-42s\t\t", paymentType);

                newText.replace(startIndex, endIndex, newPaymentType);

                receiptTextArea.setText(newText.toString());

                updateTotalReceivedEWallet();
            }
        });
        eWalletPanel3.add(eWButton3);
        eWalletPanel.add(lblEWallet);
        eWalletPanel.add(eWalletPanel2);
        eWalletPanel.add(eWalletPanel3);

        cashPanel = new JPanel(new GridLayout(3, 1));
        cashPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        lblCash = new JLabel(" Cash ");

        ImageIcon cashButton1Image = new ImageIcon("cash.jpg");
        Image scaledCash = cashButton1Image.getImage().getScaledInstance(150, 100, Image.SCALE_SMOOTH);

        JPanel cashPanel2 = new JPanel();
        cashPanel2.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        cashButton = new JButton("");
        cashButton.setIcon(new ImageIcon(scaledCash));
        cashPanel2.add(cashButton);

        cashButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setPaymentType("Cash");
                StringBuilder newText = new StringBuilder(receiptTextArea.getText());
                int startIndex = newText.indexOf("Payment Type: ") + "Payment Type: ".length();
                int endIndex = newText.indexOf("\n", startIndex);

                String newPaymentType = String.format("%-42s\t\t", paymentType);

                newText.replace(startIndex, endIndex, newPaymentType);

                receiptTextArea.setText(newText.toString());

                Cash cashPanel = new Cash(Payment.this);
                cashPanel.setVisible(true);
                cashPanel.setLocationRelativeTo(null);
                cashPanel.pack();
            }
        });

        cashPanel.add(lblCash);
        cashPanel.add(cashPanel2);

        bankPanel = new JPanel(new GridLayout(3, 1));
        bankPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        lblBank = new JLabel(" Bank ");

        bankPanel2 = new JPanel();
        bankPanel2.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        bankButton1 = new JButton("");

        ImageIcon bankButton1Image = new ImageIcon("visa.jpg");
        Image scaledVisa = bankButton1Image.getImage().getScaledInstance(150, 100, Image.SCALE_SMOOTH);
        bankButton1.setIcon(new ImageIcon(scaledVisa));

        bankButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setPaymentType("VISA CARD");
                String userInputACCT = JOptionPane.showInputDialog(null, "Enter last 4-digits of ACCT Number:");
                acct = "**** **** **** " + userInputACCT;

                StringBuilder newText = new StringBuilder(receiptTextArea.getText());
                int startIndex = newText.indexOf("Payment Type: ") + "Payment Type: ".length();
                int endIndex = newText.indexOf("\n", startIndex);

                String newPaymentType = String.format("%-42s\t\t", paymentType);

                newText.replace(startIndex, endIndex, newPaymentType);

                startIndex = newText.indexOf("ACCT: ") + "ACCT: ".length();
                endIndex = newText.indexOf("\n", startIndex);

                String newACCT = String.format("%-42s\t\t", acct);

                newText.replace(startIndex, endIndex, newACCT);

                receiptTextArea.setText(newText.toString());

                updateTotalReceivedBank();
            }
        });

        ImageIcon bankButton2Image = new ImageIcon("master.jpg");
        Image scaledMaster = bankButton2Image.getImage().getScaledInstance(150, 100, Image.SCALE_SMOOTH);

        bankButton2 = new JButton("");
        bankButton2.setIcon(new ImageIcon(scaledMaster));
        bankButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setPaymentType("MASTER CARD");
                String userInputACCT = JOptionPane.showInputDialog(null, "Enter last 4-digits of ACCT Number:");
                acct = "" + userInputACCT;

                StringBuilder newText = new StringBuilder(receiptTextArea.getText());
                int startIndex = newText.indexOf("Payment Type: ") + "Payment Type: ".length();
                int endIndex = newText.indexOf("\n", startIndex);

                String newPaymentType = String.format("%-42s\t\t", paymentType);

                newText.replace(startIndex, endIndex, newPaymentType);

                startIndex = newText.indexOf("ACCT: ") + "ACCT: ".length();
                endIndex = newText.indexOf("\n", startIndex);

                String newACCT = String.format("%-42s\t\t", acct);

                newText.replace(startIndex, endIndex, newACCT);

                receiptTextArea.setText(newText.toString());

                updateTotalReceivedBank();
            }
        });
        bankPanel2.add(bankButton1);
        bankPanel2.add(bankButton2);

        bankPanel.add(lblBank);
        bankPanel.add(bankPanel2);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(10, 10, 10, 10);

        bottomPanel.add(eWalletPanel, gbc);
        bottomPanel.add(cashPanel, gbc);
        bottomPanel.add(bankPanel, gbc);


        secondPanel = new JPanel(new GridLayout(2, 1));
        secondPanel.add(middlePanel);
        secondPanel.add(bottomPanel);

        setLayout(new BorderLayout());
        add(leftPanel, BorderLayout.WEST);
        add(secondPanel, BorderLayout.CENTER);

        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PrinterJob printerJob = PrinterJob.getPrinterJob();

                printerJob.setPrintable(new Printable() {
                    @Override
                    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                        if (pageIndex > 0) {
                            return Printable.NO_SUCH_PAGE;
                        }
                        Graphics2D g2d = (Graphics2D) graphics;
                        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

                        receiptTextArea.printAll(graphics);

                        return Printable.PAGE_EXISTS;
                    }
                });
                if (printerJob.printDialog()) {
                    try {
                        printerJob.print();
                    } catch (PrinterException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }
    private void applyVoucher(String voucherId) {
        if (voucherId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a voucher code.");
            return;
        }
        double discount = validateVoucher(voucherId);
        this.discount = discount;
        if (discount > 0) {
            updateReceipt(selectedProducts,discount);
            JOptionPane.showMessageDialog(this, "Voucher applied successfully. Discount: $" + discount);
        } else if (discount == 0) {
            JOptionPane.showMessageDialog(this, "Voucher applied successfully. No discount applied.");
        } else {
            JOptionPane.showMessageDialog(this, "Invalid voucher or voucher expired. No discount applied.");
        }
    }



    private void generateAndDisplayReceipt(List<String> selectedProducts) {
        StringBuilder receiptText = new StringBuilder();
        table_no = Passcode.getTableNumber();
        receiptText.append("\t\tRECEIPT\n");
        receiptText.append("\t                       Hot Stove Cafe\n");
        receiptText.append("\t              69, JALAN BUKIT BINTANG 9,\n");
        receiptText.append("\t               TAMAN BUKIT SEMBILAN,\n");
        receiptText.append("\t               56180, KUALA LUMPUR.\n");
        receiptText.append("\t                       +03-9133 8683\n\n");

        receiptText.append("Date: ").append(getCurrentDate()).append("\n");
        receiptText.append("Table No: "+ table_no +"\n");
        receiptText.append("Employee: "+ returnName()+"\n");
        receiptText.append("----------------------------------------------------------------------------\n");
        receiptText.append(String.format("%-30s\t%-4s\t%-9s\t%s\n", "Product Name", "Qty", "Price", "SUM"));
        receiptText.append("----------------------------------------------------------------------------\n");
        Map<String, Integer> productCounts = new HashMap<>();
        Map<String, Double> productPrices = new HashMap<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, "", "")) {
            String query = "SELECT product_name, price FROM Product WHERE product_name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            for (String productName : selectedProducts) {
                preparedStatement.setString(1, productName);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    String name = resultSet.getString("product_name");
                    double price = resultSet.getDouble("price");
                    productPrices.put(name,price);
                }
            }
            for (String productName : selectedProducts) {
                int quantity = productCounts.getOrDefault(productName, 0) + 1;
                productCounts.put(productName, quantity);
            }
            for (Map.Entry<String, Integer> entry : productCounts.entrySet()) {
                String productName = entry.getKey();
                int quantity = entry.getValue();
                double price = productPrices.get(productName);
                double subtotal = price * quantity;

                receiptText.append(String.format("%-30s\tx%-3d\t%8.2f\t%8.2f\n", productName, quantity, price, subtotal));
                totalPrice += subtotal;
            }
            receiptText.append("----------------------------------------------------------------------------\n");
            receiptText.append(String.format("%-42s\t\t%8.2f\n", "Discount (RM):", discount));
            receiptText.append(String.format("%-42s\t\t%8.2f\n", "Total Price (RM):", totalPrice));
            receiptText.append(String.format("%-42s\t\t%8.2f\n", "Total Receive (RM):", totalReceived));
            receiptText.append(String.format("%-42s\t\t%8.2f\n", "Total Change (RM):", totalChange));
            receiptText.append("Payment Type: " + paymentType+ "\n");
            receiptText.append("ACCT: "+acct+"\n");

            receiptText.append("----------------------------------------------------------------------------\n");
            receiptText.append(String.format("%61s\n", "\t        Thank You! Please come again :)"));

            receiptTextArea.setText(receiptText.toString());
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateTotalReceivedEWallet(){
        if (discount > 0){
            afterDiscount = totalPrice - discount;
            totalReceived = afterDiscount;


            int startIndex = receiptTextArea.getText().indexOf("Total Receive (RM):") + "Total Receive (RM):".length();
            int endIndex = receiptTextArea.getText().indexOf("\n", startIndex);

            String newTotalReceived = String.format("%-42s\t\t%8.2f", "", totalReceived);

            StringBuilder newText = new StringBuilder(receiptTextArea.getText());
            newText.replace(startIndex, endIndex, newTotalReceived);

            totalChange = 0;

            startIndex = newText.indexOf("Total Change (RM):") + "Total Change (RM):".length();
            endIndex = newText.indexOf("\n", startIndex);

            String newTotalChange = String.format("%-42s\t\t%8.2f", "", 0.00);

            newText.replace(startIndex, endIndex, newTotalChange);

            startIndex = newText.indexOf("ACCT: ") + "ACCT: ".length();
            endIndex = newText.indexOf("\n", startIndex);

            String newACCT = String.format("%-42s\t\t", " ");

            newText.replace(startIndex, endIndex, newACCT);

            receiptTextArea.setText(newText.toString());

        }else if(discount == 0){
            totalChange = totalReceived - totalPrice;

            int startIndex = receiptTextArea.getText().indexOf("Total Receive (RM):") + "Total Receive (RM):".length();
            int endIndex = receiptTextArea.getText().indexOf("\n", startIndex);

            totalReceived = totalPrice;
            String newTotalReceived = String.format("%-42s\t\t%8.2f", "", totalReceived);

            StringBuilder newText = new StringBuilder(receiptTextArea.getText());
            newText.replace(startIndex, endIndex, newTotalReceived);

            startIndex = newText.indexOf("Total Change (RM):") + "Total Change (RM):".length();
            endIndex = newText.indexOf("\n", startIndex);

            String newTotalChange = String.format("%-42s\t\t%8.2f", "", 0.00);

            newText.replace(startIndex, endIndex, newTotalChange);

            startIndex = newText.indexOf("ACCT: ") + "ACCT: ".length();
            endIndex = newText.indexOf("\n", startIndex);

            String newACCT = String.format("%-42s\t\t", " ");

            newText.replace(startIndex, endIndex, newACCT);

            receiptTextArea.setText(newText.toString());
        }
    }
    public void updateTotalReceivedBank(){
        if (discount > 0){
            afterDiscount = totalPrice - discount;
            totalReceived = afterDiscount;
            System.out.println(totalReceived);
            int startIndex = receiptTextArea.getText().indexOf("Total Receive (RM):") + "Total Receive (RM):".length();
            int endIndex = receiptTextArea.getText().indexOf("\n", startIndex);
            
            String newTotalReceived = String.format("%-42s\t\t%8.2f", "", totalReceived);
            
            StringBuilder newText = new StringBuilder(receiptTextArea.getText());
            newText.replace(startIndex, endIndex, newTotalReceived);
            
            totalChange = 0;
            
            startIndex = newText.indexOf("Total Change (RM):") + "Total Change (RM):".length();
            endIndex = newText.indexOf("\n", startIndex);
            
            String newTotalChange = String.format("%-42s\t\t%8.2f", "", 0.00);
            
            newText.replace(startIndex, endIndex, newTotalChange);
            
            receiptTextArea.setText(newText.toString());

        }else if(discount == 0){
            totalChange = totalReceived - totalPrice;

            int startIndex = receiptTextArea.getText().indexOf("Total Receive (RM):") + "Total Receive (RM):".length();
            int endIndex = receiptTextArea.getText().indexOf("\n", startIndex);

            totalReceived = totalPrice;
            String newTotalReceived = String.format("%-42s\t\t%8.2f", "", totalReceived);

            StringBuilder newText = new StringBuilder(receiptTextArea.getText());
            newText.replace(startIndex, endIndex, newTotalReceived);

            startIndex = newText.indexOf("Total Change (RM):") + "Total Change (RM):".length();
            endIndex = newText.indexOf("\n", startIndex);

            String newTotalChange = String.format("%-42s\t\t%8.2f", "", 0.00);

            newText.replace(startIndex, endIndex, newTotalChange);

            receiptTextArea.setText(newText.toString());
        }
    }
    public void updateTotalReceived(double totalReceived) {
        this.totalReceived = totalReceived;

        if (discount > 0){
            totalPrice = totalPrice - discount;

            totalChange = totalReceived - totalPrice;

            int startIndex = receiptTextArea.getText().indexOf("Total Receive (RM):") + "Total Receive (RM):".length();
            int endIndex = receiptTextArea.getText().indexOf("\n", startIndex);

            String newTotalReceived = String.format("%-42s\t\t%8.2f", "", totalReceived);

            StringBuilder newText = new StringBuilder(receiptTextArea.getText());
            newText.replace(startIndex, endIndex, newTotalReceived);

            startIndex = newText.indexOf("Total Change (RM):") + "Total Change (RM):".length();
            endIndex = newText.indexOf("\n", startIndex);

            String newTotalChange = String.format("%-42s\t\t%8.2f", "", totalChange);

            newText.replace(startIndex, endIndex, newTotalChange);

            startIndex = newText.indexOf("ACCT: ") + "ACCT: ".length();
            endIndex = newText.indexOf("\n", startIndex);

            String newACCTChange = String.format("%-42s\t\t", "");

            newText.replace(startIndex, endIndex, newACCTChange);

            receiptTextArea.setText(newText.toString());
        }else if(discount == 0){
            totalChange = totalReceived - totalPrice;

            int startIndex = receiptTextArea.getText().indexOf("Total Receive (RM):") + "Total Receive (RM):".length();
            int endIndex = receiptTextArea.getText().indexOf("\n", startIndex);

            String newTotalReceived = String.format("%-42s\t\t%8.2f", "", totalReceived);

            StringBuilder newText = new StringBuilder(receiptTextArea.getText());
            newText.replace(startIndex, endIndex, newTotalReceived);

            startIndex = newText.indexOf("Total Change (RM):") + "Total Change (RM):".length();
            endIndex = newText.indexOf("\n", startIndex);

            String newTotalChange = String.format("%-42s\t\t%8.2f", "", totalChange);

            newText.replace(startIndex, endIndex, newTotalChange);

            receiptTextArea.setText(newText.toString());
        }
    }
    private void updateReceipt(List<String> selectedProducts, double discount) {
        if (paymentType == "Cash"){
            StringBuilder receiptText = new StringBuilder();
            receiptText.append("\t\tRECEIPT\n");
            receiptText.append("\t                       Hot Stove Cafe\n");
            receiptText.append("\t              69, JALAN BUKIT BINTANG 9,\n");
            receiptText.append("\t               TAMAN BUKIT SEMBILAN,\n");
            receiptText.append("\t               56180, KUALA LUMPUR.\n");
            receiptText.append("\t                       +03-9133 8683\n\n");
            receiptText.append("Date: ").append(getCurrentDate()).append("\n");
            receiptText.append("Table No: "+ Passcode.getTableNumber() +"\n");
            receiptText.append("Employee: "+ returnName()+"\n");
            receiptText.append("----------------------------------------------------------------------------\n");
            receiptText.append(String.format("%-30s\t%-4s\t%-9s\t%s\n", "Product Name", "Qty", "Price", "SUM"));
            receiptText.append("----------------------------------------------------------------------------\n");
            Map<String, Integer> productCounts = new HashMap<>();
            Map<String, Double> productPrices = new HashMap<>();
            double totalPrice = 0.0;

            try (Connection connection = DriverManager.getConnection(DB_URL, "", "")) {
                String query = "SELECT product_name, price FROM Product WHERE product_name = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);

                for (String productName : selectedProducts) {
                    preparedStatement.setString(1, productName);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()) {
                        String name = resultSet.getString("product_name");
                        double price = resultSet.getDouble("price");
                        productPrices.put(name, price);
                    }
                }
                for (String productName : selectedProducts) {
                    int quantity = productCounts.getOrDefault(productName, 0) + 1;
                    productCounts.put(productName, quantity);
                }
                for (Map.Entry<String, Integer> entry : productCounts.entrySet()) {
                    String productName = entry.getKey();
                    int quantity = entry.getValue();
                    double price = productPrices.get(productName);
                    double subtotal = price * quantity;

                    receiptText.append(String.format("%-30s\tx%-3d\t%8.2f\t%8.2f\n", productName, quantity, price, subtotal));
                    totalPrice += subtotal;
                }
                totalPrice = totalPrice - discount;

                receiptText.append("----------------------------------------------------------------------------\n");
                receiptText.append(String.format("%-42s\t\t%8.2f\n", "Discount:", discount));
                receiptText.append(String.format("%-42s\t\t%8.2f\n", "Total Price:", totalPrice));
                receiptText.append(String.format("%-42s\t%8.2f\n", "Total Receive (RM):", totalReceived));
                receiptText.append(String.format("%-42s\t%8.2f\n", "Total Change (RM):", totalChange = totalReceived - totalPrice));
                receiptText.append("Payment Type: " + paymentType+ "\n");
                receiptText.append("ACCT: "+acct+"\n");
                receiptText.append("----------------------------------------------------------------------------\n");
                receiptText.append(String.format("%61s\n", "\t        Thank You! Please come again :)"));

                receiptTextArea.setText(receiptText.toString());
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            StringBuilder receiptText = new StringBuilder();
            receiptText.append("\t\tRECEIPT\n");
            receiptText.append("\t                       Hot Stove Cafe\n");
            receiptText.append("\t              69, JALAN BUKIT BINTANG 9,\n");
            receiptText.append("\t               TAMAN BUKIT SEMBILAN,\n");
            receiptText.append("\t               56180, KUALA LUMPUR.\n");
            receiptText.append("\t                       +03-9133 8683\n\n");

            receiptText.append("Date: ").append(getCurrentDate()).append("\n");
            receiptText.append("Table No: "+ Passcode.getTableNumber() +"\n");
            receiptText.append("Employee: "+ returnName()+"\n");
            receiptText.append("----------------------------------------------------------------------------\n");
            receiptText.append(String.format("%-30s\t%-4s\t%-9s\t%s\n", "Product Name", "Qty", "Price", "SUM"));
            receiptText.append("----------------------------------------------------------------------------\n");
            Map<String, Integer> productCounts = new HashMap<>();
            Map<String, Double> productPrices = new HashMap<>();
            double totalPrice = 0.0;

            try (Connection connection = DriverManager.getConnection(DB_URL, "", "")) {
                String query = "SELECT product_name, price FROM Product WHERE product_name = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);

                for (String productName : selectedProducts) {
                    preparedStatement.setString(1, productName);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()) {
                        String name = resultSet.getString("product_name");
                        double price = resultSet.getDouble("price");
                        productPrices.put(name, price);
                    }
                }
                for (String productName : selectedProducts) {
                    int quantity = productCounts.getOrDefault(productName, 0) + 1;
                    productCounts.put(productName, quantity);
                }
                for (Map.Entry<String, Integer> entry : productCounts.entrySet()) {
                    String productName = entry.getKey();
                    int quantity = entry.getValue();
                    double price = productPrices.get(productName);
                    double subtotal = price * quantity;

                    receiptText.append(String.format("%-30s\tx%-3d\t%8.2f\t%8.2f\n", productName, quantity, price, subtotal));
                    totalPrice += subtotal;
                }
                totalPrice = totalPrice - discount;
                receiptText.append("----------------------------------------------------------------------------\n");
                receiptText.append(String.format("%-42s\t\t\t%8.2f\n", "Discount:", discount));
                receiptText.append(String.format("%-42s\t\t\t%8.2f\n", "Total Price:", totalPrice));

                totalReceived = totalPrice;
                receiptText.append(String.format("%-42s\t\t\t%8.2f\n", "Total Receive (RM):", totalReceived));
                receiptText.append(String.format("%-42s\t\t\t%8.2f\n", "Total Change (RM):", totalChange = totalReceived - totalPrice));
                receiptText.append("Payment Type: " + paymentType+ "\n");
                receiptText.append("ACCT: "+acct+"\n");
                receiptText.append("----------------------------------------------------------------------------\n");
                receiptText.append(String.format("%61s\n", "\t        Thank You! Please come again :)"));

                receiptTextArea.setText(receiptText.toString());
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    private void displaySelectedProducts(List<String> selectedProducts) {
        Map<String, Integer> productCounts = new HashMap<>();

        for (String product : selectedProducts) {
            productCounts.put(product, productCounts.getOrDefault(product, 0) + 1);
        }
        int index = 1;
        for (Map.Entry<String, Integer> entry : productCounts.entrySet()) {
            String productName = entry.getKey();
            int count = entry.getValue();
            lblOrderedProducts[index - 1].setText(index + ". " + productName + " x" + count);
            index++;
        }
    }
    private double validateVoucher(String voucherId) {
        double discount = 0.0;

        try (Connection connection = DriverManager.getConnection(DB_URL, "", "")) {
            String query = "SELECT value FROM Voucher WHERE voucher_id = ? AND exp_date >= CURDATE()";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, voucherId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                discount = resultSet.getDouble("value");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid voucher or voucher expired.");
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return discount;
    }
    public String getPaymentType() {
        return paymentType;
    }
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }
    private void insertReceiptDatabase(int table_no, double totalPrice, double totalReceived,double afterDisount, double totalChange, double discount, String paymentType, List<String> selectedProducts, String acct, String employeeName) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/POS_System", "", "");

            String sql = "INSERT INTO Receipt (table_no, totalPrice, totalReceived, totalChange, discount, paymentType, orderedProduct, acct, dateANDtime, employeeName) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, table_no);
            statement.setDouble(2, totalPrice);

            statement.setDouble(3, totalReceived);
            statement.setDouble(3, afterDisount);

            statement.setDouble(4, totalChange);
            statement.setDouble(5, discount);
            statement.setString(6, paymentType);
            String productList = String.join(",", selectedProducts);
            statement.setString(7, productList);
            statement.setString(8, acct);
            LocalDateTime localDateTime = LocalDateTime.now();
            Timestamp timestamp = Timestamp.valueOf(localDateTime);
            statement.setTimestamp(9, timestamp);
            statement.setString(10, employeeName);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    receipt_id = generatedKeys.getInt(1);
                }
                JOptionPane.showMessageDialog(this, "Receipt Stored Successfully");

                generatedKeys.close();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to Store Receipt into database.");
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void insertHistoryDatabase(int receipt_id) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/POS_System", "", "");

            String sql = "INSERT INTO History (receipt_id) VALUES (?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, receipt_id);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "History Record Stored Successfully");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to Store History Record into database.");
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void main(String[] args) {
        List<String> selectedProducts = new ArrayList<>();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(new FlatMacDarkLaf());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                new Payment(selectedProducts).setVisible(true);
            }
        });
    }
}