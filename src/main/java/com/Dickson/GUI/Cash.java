package com.Dickson.GUI;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.event.*;

public class Cash extends JFrame {
	private JPanel p1_CashSen,p2_ConfirmBtn;
	private JPanel p1_Cash,p2_Sen;
	private JPanel p1_RM1,p2_RM5,p3_RM10,p4_RM20,p5_RM50,p6_RM100;
	private JPanel p1RM_btn,p1RM_lbl,p2RM_btn,p2RM_lbl,p3RM_btn,p3RM_lbl,p4RM_btn,p4RM_lbl,p5RM_btn,p5RM_lbl,p6RM_btn,p6RM_lbl;
	
	private JPanel p1_5sen,p2_10sen,p3_20sen,p4_50sen;
	private JPanel p1sen_btn,p1sen_lbl,p2sen_btn,p2sen_lbl,p3sen_btn,p3sen_lbl,p4sen_btn,p4sen_lbl;

	private JButton btnP1RM_minus,btnP1RM_add,btnP2RM_minus,btnP2RM_add,btnP3RM_minus,btnP3RM_add,btnP4RM_minus,btnP4RM_add,btnP5RM_minus,btnP5RM_add,btnP6RM_minus,btnP6RM_add;
	private JTextField tf_P1RM,tf_P2RM,tf_P3RM,tf_P4RM,tf_P5RM,tf_P6RM;
	
	private JButton btnP1Sen_minus,btnP1Sen_add,btnP2Sen_minus,btnP2Sen_add,btnP3Sen_minus,btnP3Sen_add,btnP4Sen_minus,btnP4Sen_add;
	private JTextField tf_P1Sen,tf_P2Sen,tf_P3Sen,tf_P4Sen;
	private JLabel lbl_RM1,lbl_RM5,lbl_RM10,lbl_RM20,lbl_RM50,lbl_RM100;
	private JLabel lbl_5sen,lbl_10sen,lbl_20sen,lbl_50sen;

	private JButton btn_Confirm;
	private Payment paymentInstance; // Reference to the Payment instance



	public Cash(Payment paymentInstance){
        this.paymentInstance = paymentInstance;
        Font myFont = new Font("Serif",Font.BOLD,16);
		LineBorder lBorder = new LineBorder(Color.black,2);
		TitledBorder tCashBorder = new TitledBorder("Cash");
		//RM1
		p1RM_btn = new JPanel();
		p1RM_btn.setLayout(new FlowLayout(FlowLayout.LEFT));
		btnP1RM_minus = new JButton("-");
		tf_P1RM = new JTextField("0",5);
		btnP1RM_add = new JButton("+");
		btnP1RM_add.setFont(myFont);
		p1RM_btn.add(btnP1RM_minus);
		p1RM_btn.add(tf_P1RM);
		p1RM_btn.add(btnP1RM_add);

		p1RM_lbl = new JPanel();
		p1RM_lbl.setLayout(new FlowLayout(FlowLayout.CENTER));
		lbl_RM1 = new JLabel("RM1");
		p1RM_lbl.add(lbl_RM1);
		
		p1_RM1 = new JPanel();		
		p1_RM1.setLayout(new GridLayout(2,1));
		p1_RM1.add(p1RM_btn);
		p1_RM1.add(p1RM_lbl);
		p1_RM1.setBorder(lBorder);

		// Adding action listeners to plus and minus buttons for RM1 panel
		btnP1RM_add.addActionListener(e -> {
			int value = Integer.parseInt(tf_P1RM.getText());
			tf_P1RM.setText(String.valueOf(value + 1));
		});

		btnP1RM_minus.addActionListener(e -> {
			int value = Integer.parseInt(tf_P1RM.getText());
			if (value > 0) {
				tf_P1RM.setText(String.valueOf(value - 1));
			}
		});

		//RM2
		p2RM_btn = new JPanel();
		p2RM_btn.setLayout(new FlowLayout(FlowLayout.LEFT));
		btnP2RM_minus = new JButton("-");
		tf_P2RM = new JTextField("0",5);
		btnP2RM_add = new JButton("+");
		p2RM_btn.add(btnP2RM_minus);
		p2RM_btn.add(tf_P2RM);
		p2RM_btn.add(btnP2RM_add);

		p2RM_lbl = new JPanel();
		p2RM_lbl.setLayout(new FlowLayout(FlowLayout.CENTER));
		lbl_RM5 = new JLabel("RM5");
		p2RM_lbl.add(lbl_RM5);
		
		p2_RM5 = new JPanel();		
		p2_RM5.setLayout(new GridLayout(2,1));
		p2_RM5.add(p2RM_btn);
		p2_RM5.add(p2RM_lbl);
		p2_RM5.setBorder(lBorder);


		btnP2RM_add.addActionListener(e -> {
			int value = Integer.parseInt(tf_P2RM.getText());
			tf_P2RM.setText(String.valueOf(value + 1));
		});

		btnP2RM_minus.addActionListener(e -> {
			int value = Integer.parseInt(tf_P2RM.getText());
			if (value > 0) {
				tf_P2RM.setText(String.valueOf(value - 1));
			}
		});

		
		//RM10
		p3RM_btn = new JPanel();
		p3RM_btn.setLayout(new FlowLayout(FlowLayout.LEFT));
		btnP3RM_minus = new JButton("-");
		tf_P3RM = new JTextField("0",5);
		btnP3RM_add = new JButton("+");
		p3RM_btn.add(btnP3RM_minus);
		p3RM_btn.add(tf_P3RM);
		p3RM_btn.add(btnP3RM_add);

		p3RM_lbl = new JPanel();
		p3RM_lbl.setLayout(new FlowLayout(FlowLayout.CENTER));
		lbl_RM10 = new JLabel("RM10");
		p3RM_lbl.add(lbl_RM10);
		
		p3_RM10 = new JPanel();		
		p3_RM10.setLayout(new GridLayout(2,1));
		p3_RM10.add(p3RM_btn);
		p3_RM10.add(p3RM_lbl);
		p3_RM10.setBorder(lBorder);

		btnP3RM_add.addActionListener(e -> {
			int value = Integer.parseInt(tf_P3RM.getText());
			tf_P3RM.setText(String.valueOf(value + 1));
		});

		btnP3RM_minus.addActionListener(e -> {
			int value = Integer.parseInt(tf_P3RM.getText());
			if (value > 0) {
				tf_P3RM.setText(String.valueOf(value - 1));
			}
		});

		//RM20
		p4RM_btn = new JPanel();
		p4RM_btn.setLayout(new FlowLayout(FlowLayout.LEFT));
		btnP4RM_minus = new JButton("-");
		tf_P4RM = new JTextField("0",5);
		btnP4RM_add = new JButton("+");
		p4RM_btn.add(btnP4RM_minus);
		p4RM_btn.add(tf_P4RM);
		p4RM_btn.add(btnP4RM_add);

		p4RM_lbl = new JPanel();
		p4RM_lbl.setLayout(new FlowLayout(FlowLayout.CENTER));
		lbl_RM20 = new JLabel("RM20");
		p4RM_lbl.add(lbl_RM20);
		
		p4_RM20 = new JPanel();		
		p4_RM20.setLayout(new GridLayout(2,1));
		p4_RM20.add(p4RM_btn);
		p4_RM20.add(p4RM_lbl);
		p4_RM20.setBorder(lBorder);

		btnP4RM_add.addActionListener(e -> {
			int value = Integer.parseInt(tf_P4RM.getText());
			tf_P4RM.setText(String.valueOf(value + 1));
		});

		btnP4RM_minus.addActionListener(e -> {
			int value = Integer.parseInt(tf_P4RM.getText());
			if (value > 0) {
				tf_P4RM.setText(String.valueOf(value - 1));
			}
		});

		//RM50
		p5RM_btn = new JPanel();
		p5RM_btn.setLayout(new FlowLayout(FlowLayout.LEFT));
		btnP5RM_minus = new JButton("-");
		tf_P5RM = new JTextField("0",5);
		btnP5RM_add = new JButton("+");
		p5RM_btn.add(btnP5RM_minus);
		p5RM_btn.add(tf_P5RM);
		p5RM_btn.add(btnP5RM_add);

		p5RM_lbl = new JPanel();
		p5RM_lbl.setLayout(new FlowLayout(FlowLayout.CENTER));
		lbl_RM50 = new JLabel("RM50");
		p5RM_lbl.add(lbl_RM50);
		
		p5_RM50 = new JPanel();		
		p5_RM50.setLayout(new GridLayout(2,1));
		p5_RM50.add(p5RM_btn);
		p5_RM50.add(p5RM_lbl);
		p5_RM50.setBorder(lBorder);

		btnP5RM_add.addActionListener(e -> {
			int value = Integer.parseInt(tf_P5RM.getText());
			tf_P5RM.setText(String.valueOf(value + 1));
		});

		btnP5RM_minus.addActionListener(e -> {
			int value = Integer.parseInt(tf_P5RM.getText());
			if (value > 0) {
				tf_P5RM.setText(String.valueOf(value - 1));
			}
		});

		//RM100
		p6RM_btn = new JPanel();
		p6RM_btn.setLayout(new FlowLayout(FlowLayout.LEFT));
		btnP6RM_minus = new JButton("-");
		tf_P6RM = new JTextField("0",5);
		btnP6RM_add = new JButton("+");
		p6RM_btn.add(btnP6RM_minus);
		p6RM_btn.add(tf_P6RM);
		p6RM_btn.add(btnP6RM_add);

		p6RM_lbl = new JPanel();
		p6RM_lbl.setLayout(new FlowLayout(FlowLayout.CENTER));
		lbl_RM100 = new JLabel("RM100");
		p6RM_lbl.add(lbl_RM100);
		
		p6_RM100 = new JPanel();		
		p6_RM100.setLayout(new GridLayout(2,1));
		p6_RM100.add(p6RM_btn);
		p6_RM100.add(p6RM_lbl);
		p6_RM100.setBorder(lBorder);

		btnP6RM_add.addActionListener(e -> {
			int value = Integer.parseInt(tf_P6RM.getText());
			tf_P6RM.setText(String.valueOf(value + 1));
		});

		btnP6RM_minus.addActionListener(e -> {
			int value = Integer.parseInt(tf_P6RM.getText());
			if (value > 0) {
				tf_P6RM.setText(String.valueOf(value - 1));
			}
		});

		//Cash Panel
		p1_Cash = new JPanel();
		p1_Cash.setLayout(new GridLayout(2,3,10,10));
		p1_Cash.add(p1_RM1);
		p1_Cash.add(p2_RM5);
		p1_Cash.add(p3_RM10);
		p1_Cash.add(p4_RM20);
		p1_Cash.add(p5_RM50);
		p1_Cash.add(p6_RM100);
		p1_Cash.setBorder(tCashBorder);
		
		// Adjust the values as needed to create space between the panels
		Border paddingBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		p1_Cash.setBorder(BorderFactory.createCompoundBorder(tCashBorder, paddingBorder));

		TitledBorder tSenBorder = new TitledBorder("Sen");

		//5sen
		p1sen_btn = new JPanel();
		p1sen_btn.setLayout(new FlowLayout(FlowLayout.LEFT));
		btnP1Sen_minus = new JButton("-");
		tf_P1Sen = new JTextField("0",5);
		btnP1Sen_add = new JButton("+");
		p1sen_btn.add(btnP1Sen_minus);
		p1sen_btn.add(tf_P1Sen);
		p1sen_btn.add(btnP1Sen_add);

		p1sen_lbl = new JPanel();
		p1sen_lbl.setLayout(new FlowLayout(FlowLayout.CENTER));
		lbl_5sen = new JLabel("5 sen");
		p1sen_lbl.add(lbl_5sen);
		
		p1_5sen = new JPanel();		
		p1_5sen.setLayout(new GridLayout(2,1));
		p1_5sen.add(p1sen_btn);
		p1_5sen.add(p1sen_lbl);
		p1_5sen.setBorder(lBorder);

		btnP1Sen_add.addActionListener(e -> {
			int value = Integer.parseInt(tf_P1Sen.getText());
			tf_P1Sen.setText(String.valueOf(value + 1));
		});

		btnP1Sen_minus.addActionListener(e -> {
			int value = Integer.parseInt(tf_P1Sen.getText());
			if (value > 0) {
				tf_P1Sen.setText(String.valueOf(value - 1));
			}
		});

		//10sen
		p2sen_btn = new JPanel();
		p2sen_btn.setLayout(new FlowLayout(FlowLayout.LEFT));
		btnP2Sen_minus = new JButton("-");
		tf_P2Sen = new JTextField("0",5);
		btnP2Sen_add = new JButton("+");
		p2sen_btn.add(btnP2Sen_minus);
		p2sen_btn.add(tf_P2Sen);
		p2sen_btn.add(btnP2Sen_add);

		p2sen_lbl = new JPanel();
		p2sen_lbl.setLayout(new FlowLayout(FlowLayout.CENTER));
		lbl_10sen = new JLabel("10 sen");
		p2sen_lbl.add(lbl_10sen);
		
		p2_10sen = new JPanel();		
		p2_10sen.setLayout(new GridLayout(2,1));
		p2_10sen.add(p2sen_btn);
		p2_10sen.add(p2sen_lbl);
		p2_10sen.setBorder(lBorder);

		btnP2Sen_add.addActionListener(e -> {
			int value = Integer.parseInt(tf_P2Sen.getText());
			tf_P2Sen.setText(String.valueOf(value + 1));
		});

		btnP2Sen_minus.addActionListener(e -> {
			int value = Integer.parseInt(tf_P2Sen.getText());
			if (value > 0) {
				tf_P2Sen.setText(String.valueOf(value - 1));
			}
		});

		//20sen
		p3sen_btn = new JPanel();
		p3sen_btn.setLayout(new FlowLayout(FlowLayout.LEFT));
		btnP3Sen_minus = new JButton("-");
		tf_P3Sen = new JTextField("0",5);
		btnP3Sen_add = new JButton("+");
		p3sen_btn.add(btnP3Sen_minus);
		p3sen_btn.add(tf_P3Sen);
		p3sen_btn.add(btnP3Sen_add);

		p3sen_lbl = new JPanel();
		p3sen_lbl.setLayout(new FlowLayout(FlowLayout.CENTER));
		lbl_20sen = new JLabel("20 sen");
		p3sen_lbl.add(lbl_20sen);
		
		p3_20sen = new JPanel();		
		p3_20sen.setLayout(new GridLayout(2,1));
		p3_20sen.add(p3sen_btn);
		p3_20sen.add(p3sen_lbl);
		p3_20sen.setBorder(lBorder);

		btnP3Sen_add.addActionListener(e -> {
			int value = Integer.parseInt(tf_P3Sen.getText());
			tf_P3Sen.setText(String.valueOf(value + 1));
		});

		btnP3Sen_minus.addActionListener(e -> {
			int value = Integer.parseInt(tf_P3Sen.getText());
			if (value > 0) {
				tf_P3Sen.setText(String.valueOf(value - 1));
			}
		});

		//50sen
		p4sen_btn = new JPanel();
		p4sen_btn.setLayout(new FlowLayout(FlowLayout.LEFT));
		btnP4Sen_minus = new JButton("-");
		tf_P4Sen = new JTextField("0",5);
		btnP4Sen_add = new JButton("+");
		p4sen_btn.add(btnP4Sen_minus);
		p4sen_btn.add(tf_P4Sen);
		p4sen_btn.add(btnP4Sen_add);

		p4sen_lbl = new JPanel();
		p4sen_lbl.setLayout(new FlowLayout(FlowLayout.CENTER));
		lbl_50sen = new JLabel("50 sen");
		p4sen_lbl.add(lbl_50sen);
		
		p4_50sen = new JPanel();		
		p4_50sen.setLayout(new GridLayout(2,1));
		p4_50sen.add(p4sen_btn);
		p4_50sen.add(p4sen_lbl);
		p4_50sen.setBorder(lBorder);

		btnP4Sen_add.addActionListener(e -> {
			int value = Integer.parseInt(tf_P4Sen.getText());
			tf_P4Sen.setText(String.valueOf(value + 1));
		});

		btnP4Sen_minus.addActionListener(e -> {
			int value = Integer.parseInt(tf_P4Sen.getText());
			if (value > 0) {
				tf_P4Sen.setText(String.valueOf(value - 1));
			}
		});

		//Sen Panel
		p2_Sen = new JPanel();
		p2_Sen.setLayout(new GridLayout(2,2,10,10));
		p2_Sen.add(p1_5sen);
		p2_Sen.add(p2_10sen);
		p2_Sen.add(p3_20sen);
		p2_Sen.add(p4_50sen);
		p2_Sen.setBorder(tSenBorder);

		// Adjust the values as needed to create space between the panels
		p2_Sen.setBorder(BorderFactory.createCompoundBorder(tSenBorder, paddingBorder));


		//Cash Sen 
		p1_CashSen = new JPanel();
		p1_CashSen.add(p1_Cash);
		p1_CashSen.add(p2_Sen);
		p1_CashSen.setLayout(new FlowLayout(FlowLayout.LEFT));


		//Confirm Button
		p2_ConfirmBtn = new JPanel();
		p2_ConfirmBtn.setLayout(new FlowLayout(FlowLayout.CENTER));
		btn_Confirm = new JButton("Confirm");
		btn_Confirm.setPreferredSize(new Dimension(400, 50)); // Set preferred size here
		p2_ConfirmBtn.add(btn_Confirm);

		setLayout(new BorderLayout());
		add(p1_CashSen,BorderLayout.CENTER);
		add(p2_ConfirmBtn,BorderLayout.SOUTH);

		btn_Confirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Get the total value of all notes and coins
				double totalValue = getTotalValue();
				System.out.printf("Total Received: RM%.2f%n", totalValue);
				
				// Call the method to send the total value to the Payment class
				sendCashValueToPayment(totalValue);
				dispose();
			}
		});

	}

	// Method to calculate the total value of all notes
    private double getTotalValue() {
        int rm1 = Integer.parseInt(tf_P1RM.getText());
        int rm5 = Integer.parseInt(tf_P2RM.getText()) * 5;
        int rm10 = Integer.parseInt(tf_P3RM.getText()) * 10;
        int rm20 = Integer.parseInt(tf_P4RM.getText()) * 20;
        int rm50 = Integer.parseInt(tf_P5RM.getText()) * 50;
        int rm100 = Integer.parseInt(tf_P6RM.getText()) * 100;

        double totalSen = (Double.parseDouble(tf_P1Sen.getText()) * 0.5) +
                        (Double.parseDouble(tf_P2Sen.getText()) * 0.10 )+
                        (Double.parseDouble(tf_P3Sen.getText()) * 0.20 )+
                        (Double.parseDouble(tf_P4Sen.getText()) * 0.50);

        return rm1 + rm5 + rm10 + rm20 + rm50 + rm100 + totalSen;
    }

	// Method to update the total received value in the Payment class
    private void updateTotalReceivedValue(double totalReceived) {
        paymentInstance.updateTotalReceived(totalReceived);
    }

	private void sendCashValueToPayment(double cashValue) {
		if (paymentInstance != null) {
			// Update the total received value in the Payment class
			updateTotalReceivedValue(cashValue);
		} else {
			System.out.println("Payment instance is null. Cannot update total received value.");
		}
	}
	

	public void main(String[] args) {
		Cash frame = new Cash(paymentInstance);
		frame.setTitle("Cash");
		frame.setLocationRelativeTo(null);
		frame.setSize(400,300);
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		frame.pack();
		frame.setResizable(false);
	}
}