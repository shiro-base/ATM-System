package gui;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

import bank.*;
import account.*;
import currency.*;
import Database.*;
import Exceptions.*;
import loan.*;
import Main.*;
import StockMarket.*;
import java.util.List;
//CLASS FOR DISPLAYING FUNCTION TO REPAY LOAN BY CUSTOMER
public class RepayLoanPage extends JFrame implements ActionListener{
    private Container c;
	private JLabel name,amt,lt0,lt1,h1,h2,h3,name2, lima,label;
	private JLabel pass,msg,lt2,ls,lt,la,li, pass2,curr,acc,msg1;
	private JTextField tacc,tcurr,tamt,tmsg,tmsg1;
	private JComboBox<String> cb, cb1;
	private JLabel err;
	private ImageIcon logo, ima;
	private JButton submit,logout,back;

	private List<Loan<? extends Currency>> choices;
	private List<Account<? extends Currency>> choices1;

    RepayLoanPage(){

		c = getContentPane();
		c.setLayout(null);

        ima = new ImageIcon(getClass().getResource("dp.png"));
        lima = new JLabel(ima);
        lima.setBounds(30,10,100,100);
        c.add(lima);

		label = new JLabel("Enter Loan Repayment details");
        label.setFont(new Font("Arial",Font.BOLD,20));
        label.setBounds(600,90,400,50);

        c.add(label);

		logo = new ImageIcon(getClass().getResource("Logo.jpg"));
		li = new JLabel(logo);
		li.setBounds(1250,20,100,100);

		c.add(li);

		lt0 = new JLabel("Choose your account");
		lt0.setFont(new Font("Arial",Font.PLAIN,15));
		lt0.setBounds(600,150,200,20);
		lt0.setVisible(true);
		c.add(lt0);
		try {
			ATM obj = new ATM();
			choices1 = obj.getCustomerAccount();
			List<String> str = new ArrayList<String>();


			for (Account<? extends Currency> e : choices1) {
				String type = e.getAccountType();
				String id = (e.getAccountId()).toString();
				str.add(type + "-" + id);

			}

			System.out.println(str);
			String[] str_new = str.toArray(new String[str.size()]);
			cb1 = new JComboBox<String>(str_new);
			cb1.setBounds(900, 150, 200, 20);
			cb1.setVisible(true);
			cb1.addActionListener(this);
			c.add(cb1);

		}catch(Exception e){
			System.out.println(e);
		}



		////////////////////
    
        lt1 = new JLabel("Choose Loan to Repay");
		lt1.setFont(new Font("Arial",Font.PLAIN,15));
		lt1.setBounds(600,250,200,20);
		lt1.setVisible(true);
		c.add(lt1);

		try {
			ATM obj = new ATM();
			choices = obj.getLoansByCustomer();
			List<String> str = new ArrayList<String>();

			for (Loan e : choices) {
				String type = String.valueOf(e.getPrincipal().getAmount());
				String id = (e.getDueDate()).toString();
				str.add(type + "-" + id);

			}

			System.out.println(str);
			String[] str_new = str.toArray(new String[str.size()]);
			cb = new JComboBox<String>(str_new);
			cb.setBounds(900, 250, 200, 20);
			cb.setVisible(true);
			cb.addActionListener(this);
			c.add(cb);

		}
		catch(Exception e){
			System.out.println(e);
		}

        back = new JButton("Back");
        back.setBounds(600,550,100,20);
        back.setVisible(true);
        c.add(back);

        submit = new JButton("Repay Loan");
        submit.setBounds(800,550,100,20);
        submit.setVisible(true);
        c.add(submit);

        err = new JLabel("Error Occurred!");
        err.setFont(new Font("Arial", Font.PLAIN, 15));
        err.setSize(300, 20);
        err.setLocation(800, 600);
        err.setVisible(false);
        c.add(err);
		logout = new JButton("Logout");
		logout.setBounds(400,800,100,20);
		c.add(logout);
		logout.addActionListener(this);


		back.addActionListener(this);
        submit.addActionListener(this);

	}

	
	public void actionPerformed(ActionEvent ae){

		String s = ae.getActionCommand();
		if(s.equals("Back")){

			this.dispose();

			LoanPage t = new LoanPage();
			t.setSize(4096,7016);
			t.setVisible(true);
			t.setLayout(null);

		}if(s.equals("Logout")){
			LoginPage t = new LoginPage();
			t.setSize(4096,7016);
			t.setVisible(true);
			t.setTitle("FANCY BANK - INDEX");
		}
        int loanind = cb.getSelectedIndex();
		String ac = cb1.getSelectedItem().toString();


		if(s.equals("Repay Loan")){

			try{
				// ADD CODE USING ATM VALIDATION
				ATM atm = new ATM();
				int dashInd = ac.indexOf('-');
				String accSel = ac.substring(dashInd+1);


				for ( Account<? extends Currency> e: choices1) {
					if(String.valueOf(e.getAccountId()).equals(accSel)){
						Loan selectedLoan = choices.get(loanind);
						String checkedResult = atm.payLoan(selectedLoan, e);
						err.setText(checkedResult);
						err.setVisible(true);

					}
				}

			}catch (Exception e){
				System.out.println(e);
			}

		}


	}
}

