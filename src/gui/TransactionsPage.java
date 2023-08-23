package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JFrame;

import bank.*;
import account.*;
import currency.*;
import Database.*;
import Exceptions.*;
import Main.*;
import StockMarket.*;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//CLASS FOR DISPLAYING TRANSACTIONS BETWEEN CUSTOMER ACCOUNTS AND CUSTOMER-CUSTOMER TRANSFERS
public class TransactionsPage extends JFrame implements ActionListener{
    private Container c;
	private JLabel name,amt,lt1,h1,h2,h3,name2, lima,label;
	private JLabel pass,msg,lt2,ls,lt,la,li, pass2,curr,acc;
	private JTextField tacc,tamt,tmsg;
	private JComboBox<String> cb,tcurr;
	private JLabel err;
	private ImageIcon logo, ima;
	private JButton logout,submit,back;
	List<Account<? extends Currency>> choices;
	TransactionsPage(){

		c = getContentPane();
		c.setLayout(null);

        ima = new ImageIcon(getClass().getResource("dp.png"));
        lima = new JLabel(ima);
        lima.setBounds(30,10,100,100);
        c.add(lima);

		label = new JLabel("Enter Transaction details : ");
        label.setFont(new Font("Arial",Font.BOLD,20));
        label.setBounds(600,90,400,50);

        c.add(label);

		logo = new ImageIcon(getClass().getResource("Logo.jpg"));
		li = new JLabel(logo);
		li.setBounds(1250,20,100,100);

		c.add(li);

		err = new JLabel();
		err.setFont(new Font("Arial", Font.PLAIN, 15));
		err.setSize(700, 20);
		err.setLocation(800, 600);
		err.setVisible(false);
		c.add(err);
    
        lt1 = new JLabel("Choose your account");
		lt1.setFont(new Font("Arial",Font.PLAIN,15));
		lt1.setBounds(600,150,200,20);
		lt1.setVisible(true);
		c.add(lt1);
		try {
			ATM obj = new ATM();
			choices = obj.getCustomerAccount();
			if(choices.isEmpty()){
				err.setText("No Account available");
			}else {
				List<String> str = new ArrayList<String>();


				for (Account<? extends Currency> e : choices) {
					String type = e.getAccountType();
					String id = e.getAccountId().toString();
					str.add(type + "-" + id);

				}

				System.out.println(str);
				String[] str_new = str.toArray(new String[str.size()]);
				cb = new JComboBox<String>(str_new);
				cb.setBounds(900, 150, 200, 20);
				cb.setVisible(true);
				c.add(cb);
				cb.addActionListener(this);

			}

		}catch(Exception e){
			System.out.println(e);
		}

		acc = new JLabel("Account No. of Recipient");
		acc.setFont(new Font("Arial",Font.PLAIN,15));
		acc.setBounds(600,200,200,20);
		acc.setVisible(true);
		c.add(acc);

		tacc = new JTextField();
		tacc.setFont(new Font("Arial",Font.PLAIN,15));
		tacc.setBounds(900,200,200,20);
		tacc.setVisible(true);
		c.add(tacc);

		curr = new JLabel("Currency chosen");
		curr.setFont(new Font("Arial",Font.PLAIN,15));
		curr.setBounds(600,250,200,20);
		curr.setVisible(true);
		
		c.add(curr);

		String[] currs = { "USD","INR","CNY"};

		tcurr = new JComboBox<String>(currs);
		tcurr.setBounds(900,250,200,20);
		tcurr.setVisible(true);
		c.add(tcurr);

		amt = new JLabel("Enter Amount");
		amt.setFont(new Font("Arial",Font.PLAIN,15));
		amt.setBounds(600,300,200,20);
		amt.setVisible(true);
		c.add(amt);

		tamt = new JTextField();
		tamt.setBounds(900,300,200,20);
		tamt.setVisible(true);
		c.add(tamt);

		msg = new JLabel("Enter Message (Optional)");
		msg.setFont(new Font("Arial",Font.PLAIN,15));
		msg.setBounds(600,350,200,20);
		msg.setVisible(true);
		c.add(msg);

		tmsg = new JTextField(7);
		tmsg.setBounds(900,350,200,20);
		tmsg.setVisible(true);
		c.add(tmsg);

        back = new JButton("Back");
        back.setBounds(600,550,100,20);
        back.setVisible(true);
        c.add(back);
		logout = new JButton("Logout");
		logout.setBounds(400,800,100,20);
		c.add(logout);
		logout.addActionListener(this);

        submit = new JButton("Submit");
        submit.setBounds(800,550,100,20);
        submit.setVisible(true);
        c.add(submit);



		tacc.addActionListener(this);
		tcurr.addActionListener(this);
		tamt.addActionListener(this);
		tmsg.addActionListener(this);

		back.addActionListener(this);
        submit.addActionListener(this);

	}

	private final static Pattern UUID_REGEX_PATTERN =
			Pattern.compile("^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$");

	public static boolean isValidUUID(String str) {
		if (str == null) {
			return false;
		}
		return UUID_REGEX_PATTERN.matcher(str).matches();
	}

	public boolean is_valid_uuid_ok(String str){
		if(isValidUUID(str)){
			return true;
		}
		return false;
	}

	public void actionPerformed(ActionEvent ae){


		String s = ae.getActionCommand();

		if(s.equals("Back")){

			this.dispose();

			ExistingCustomerPage t = new ExistingCustomerPage();
			t.setSize(4960,7016);
			t.setVisible(true);
			t.setLayout(null);

		}if(s.equals("Logout")){
			LoginPage t = new LoginPage();
			t.setSize(4096,7016);
			t.setVisible(true);
			t.setTitle("FANCY BANK - INDEX");
		}
		String myacc = cb.getSelectedItem().toString();

		String tacc1 = tacc.getText();
		String tcurr1 = tcurr.getSelectedItem().toString();
		String tamt1 = tamt.getText();
		String tmsg1 = tmsg.getText();
		UUID accID;
		boolean chk = is_valid_uuid_ok(tacc1);
		if(chk){
			accID = UUID.fromString(tacc1);
			if(s.equals("Submit")){
				try {
					ATM atm = new ATM();
					// ADD CODE USING ATM VALIDATION
					//WHERE TO GET UUID FROM??
					int dashInd = myacc.indexOf('-');
					String accSel = myacc.substring(dashInd+1);

					for ( Account<? extends Currency> e: choices) {
						if(String.valueOf(e.getAccountId()).equals(accSel)){

							try{
							double am = Double.parseDouble(tamt1);
							String checkedResult = atm.Transaction(e, accID, am);
							err.setText(checkedResult);
							err.setVisible(true);
							tacc.setText("");
							tamt.setText("");
							tmsg.setText("");
							}catch (Exception ex){
								err.setText("Enter a valid double value");
								err.setVisible(true);
							}
						}
					}

				}catch (Exception e){
					System.out.println(e);
				}
			}

		}else{
			err.setText("Enter a valid Account ID");
			err.setVisible(true);
		}




	}


}
