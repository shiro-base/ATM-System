package gui;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import bank.*;
import account.*;
import currency.*;
import java.util.List;
import java.util.ArrayList;
import Database.*;
import Exceptions.*;
import loan.*;
import Main.*;
import StockMarket.*;
//CLASS FOR DISPLAYING TAKE LOAN FUNCTIONS TO EACH CUSTOMER
public class TakeLoanPage extends JFrame implements ActionListener{
    private Container c;
	List<Account<? extends Currency>> choices;
	private JLabel name,amt,lt1,h1,h2,h3,name2, lima,label;
	private JLabel pass,msg,lt2,ls,lt,la,li, pass2,curr,acc,msg1;
	private JTextField tacc,tcurr,tamt,tmsg,tmsg1;
	private JComboBox<String> cb;
	private JLabel err;
	private ImageIcon logo, ima;
	private JButton logout,submit,back;

    TakeLoanPage(){

		c = getContentPane();
		c.setLayout(null);

        ima = new ImageIcon(getClass().getResource("dp.png"));
        lima = new JLabel(ima);
        lima.setBounds(30,10,100,100);
        c.add(lima);

		label = new JLabel("Enter Loan details : ");
        label.setFont(new Font("Arial",Font.BOLD,20));
        label.setBounds(600,90,400,50);

        c.add(label);

		logo = new ImageIcon(getClass().getResource("Logo.jpg"));
		li = new JLabel(logo);
		li.setBounds(1250,20,100,100);

		c.add(li);
    
        lt1 = new JLabel("Choose your account");
		lt1.setFont(new Font("Arial",Font.PLAIN,15));
		lt1.setBounds(600,150,200,20);
		lt1.setVisible(true);
		c.add(lt1);
		try {
			ATM obj = new ATM();
			choices = obj.getCustomerAccount();
			List<String> str = new ArrayList<String>();


			for (Account<? extends Currency> e : choices) {
				String type = e.getAccountType();
				String id = (e.getAccountId()).toString();
				str.add(type + "-" + id);

			}

			System.out.println(str);
			String[] str_new = str.toArray(new String[str.size()]);
			cb = new JComboBox<String>(str_new);
			cb.setBounds(900, 150, 200, 20);
			cb.setVisible(true);
			c.add(cb);
			cb.addActionListener(this);
		}catch(Exception e){
			System.out.println(e);
		}

		acc = new JLabel("Loan Amount");
		acc.setFont(new Font("Arial",Font.PLAIN,15));
		acc.setBounds(600,200,200,20);
		acc.setVisible(true);
		c.add(acc);

		tacc = new JTextField();
		tacc.setFont(new Font("Arial",Font.PLAIN,15));
		tacc.setBounds(900,200,200,20);
		tacc.setVisible(true);
		c.add(tacc);


		amt = new JLabel("Due Date of Repayment (YYYY-MM-DD)");
		amt.setFont(new Font("Arial",Font.PLAIN,15));
		amt.setBounds(600,300,300,20);
		amt.setVisible(true);
		c.add(amt);

		tamt = new JTextField();
		tamt.setBounds(900,300,200,20);
		tamt.setVisible(true);
		c.add(tamt);

		msg = new JLabel("Collateral Name");
		msg.setFont(new Font("Arial",Font.PLAIN,15));
		msg.setBounds(600,350,200,20);
		msg.setVisible(true);
		c.add(msg);

		tmsg = new JTextField(7);
		tmsg.setBounds(900,350,200,20);
		tmsg.setVisible(true);
		c.add(tmsg);

        msg1 = new JLabel("Collateral Value");
		msg1.setFont(new Font("Arial",Font.PLAIN,15));
		msg1.setBounds(600,400,200,20);
		msg1.setVisible(true);
		c.add(msg1);

		tmsg1 = new JTextField(7);
		tmsg1.setBounds(900,400,200,20);
		tmsg1.setVisible(true);
		c.add(tmsg1);

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

        err = new JLabel("Error Occured! Collateral amount insufficient.");
        err.setFont(new Font("Arial", Font.PLAIN, 15));
        err.setSize(300, 20);
        err.setLocation(800, 600);
        err.setVisible(false);
        c.add(err);

		tacc.addActionListener(this);
		tamt.addActionListener(this);
		tmsg.addActionListener(this);
        tmsg1.addActionListener(this);

		back.addActionListener(this);
        submit.addActionListener(this);

	}

	
	public void actionPerformed(ActionEvent ae){


		String s = ae.getActionCommand();
		if(s.equals("Back")){

			this.dispose();

			LoanPage t = new LoanPage();
			t.setSize(4960,7016);
			t.setVisible(true);
			t.setLayout(null);

		}if(s.equals("Logout")){
			LoginPage t = new LoginPage();
			t.setSize(4096,7016);
			t.setVisible(true);
			t.setTitle("FANCY BANK - INDEX");
		}


	if(s.equals("Submit")){

		String myacc = cb.getSelectedItem().toString();

		String tamt1 = tamt.getText();
		double tacc1 = Double.parseDouble(tacc.getText());
		String tcname = tmsg.getText();
		double tcval = Double.parseDouble(tmsg1.getText());

		try{
		// ADD CODE USING ATM VALIDATION
		ATM atm = new ATM();
		Collateral col = new Collateral(tcname, tcval);
		int dashInd = myacc.indexOf('-');
		String accSel = myacc.substring(dashInd+1);

		for (Account<? extends Currency> e : choices) {
			if(String.valueOf(e.getAccountId()).equals(accSel)){
				String checkedResult = atm.takeLoan(col, tacc1, e, tamt1 );
				err.setText(checkedResult);
				err.setVisible(true);
				tacc.setText("");
				tamt.setText("");
				tmsg.setText("");
			}
		}

		 }catch (Exception e){
			System.out.println(e);
		}

	        } 


	}
}
