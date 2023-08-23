package gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import static Utils.Constants.*;

import bank.*;
import account.*;
import currency.*;
import Database.*;
import Exceptions.*;
import Main.*;
import StockMarket.*;

//CLASS FOR BUYING STOCK FOR EACH CUSTOMER
public class BuyStockPage extends JFrame implements ActionListener{
    private Container c;
	private JLabel name,amt,h1,h2,h3,name2,lt4, lima,label;
	private JLabel pass,msg,lt2,ls,lt,la,li, pass2,curr,acc,msg1;
	private JTextField tacc,tcurr,tamt,tmsg,tmsg1;
	private JComboBox<String> cb;
	private JLabel err;
	private ImageIcon logo, ima;
	private JButton submit,lt1,back,logout;
	private List<Stock> choices;


    BuyStockPage(){

		c = getContentPane();
		c.setLayout(null);

        ima = new ImageIcon(getClass().getResource("dp.png"));
        lima = new JLabel(ima);
        lima.setBounds(30,10,100,100);
        c.add(lima);

		label = new JLabel("Enter Stock details");
        label.setFont(new Font("Arial",Font.BOLD,20));
        label.setBounds(600,90,400,50);

        c.add(label);

		logo = new ImageIcon(getClass().getResource("Logo.jpg"));
		li = new JLabel(logo);
		li.setBounds(1250,20,100,100);

		c.add(li);
    
        lt1 = new JButton("Click to view Stock Market");
		lt1.setBounds(600,150,500,20);
		lt1.setVisible(true);
		c.add(lt1);

		lt4 = new JLabel("Choose Stock");
		lt4.setFont(new Font("Arial",Font.PLAIN,15));
		lt4.setBounds(600,550,200,20);
		lt4.setVisible(true);
		c.add(lt4);

		try {
			ATM obj = new ATM();
			choices = obj.getAllStocks();
			List<String> str = new ArrayList<String>();

			for (Stock e : choices) {
				String sname = e.getName();
				str.add(sname);
			}

			System.out.println(str);
			String[] str_new = str.toArray(new String[str.size()]);
			cb = new JComboBox<String>(str_new);
			cb.setBounds(900, 550, 200, 20);
			cb.setVisible(true);
			c.add(cb);

		}catch(Exception e){
			System.out.println(e);
		}

		acc = new JLabel("Enter Quantity");
		acc.setFont(new Font("Arial",Font.PLAIN,15));
		acc.setBounds(600,600,200,20);
		acc.setVisible(true);
		c.add(acc);

		tacc = new JTextField();
		tacc.setFont(new Font("Arial",Font.PLAIN,15));
		tacc.setBounds(900,600,200,20);
		tacc.setVisible(true);
		c.add(tacc);

        back = new JButton("Back");
        back.setBounds(600,650,100,20);
        back.setVisible(true);
        c.add(back);

		logout = new JButton("Logout");
		logout.setBounds(600,700,100,20);
		c.add(logout);
		logout.addActionListener(this);
        submit = new JButton("Buy Stock");
        submit.setBounds(800,650,100,20);
        submit.setVisible(true);
        c.add(submit);

        err = new JLabel("Error Occurred! Account Balance insufficient.");
        err.setFont(new Font("Arial", Font.PLAIN, 15));
        err.setSize(600, 20);
        err.setLocation(800, 400);
        err.setVisible(false);
        c.add(err);

		tacc.addActionListener(this);

        cb.addActionListener(this);
		back.addActionListener(this);
        submit.addActionListener(this);
		lt1.addActionListener(this);

	}

	
	public void actionPerformed(ActionEvent ae){

        String stkSel = cb.getSelectedItem().toString();

		String s = ae.getActionCommand();

		if(s.equals("Click to view Stock Market")){
			StockMarketPage obj = new StockMarketPage();
		}

		if(s.equals("Back")){

			this.dispose();

			CustomerStockPage t = new CustomerStockPage();
			t.setSize(4960,7016);
			t.setVisible(true);
			t.setLayout(null);

		}

		if(s.equals("Logout")){
			LoginPage t = new LoginPage();
			t.setSize(4096,7016);
			t.setVisible(true);
			t.setTitle("FANCY BANK - INDEX");
		}
        


	if(s.equals("Buy Stock")){
		if(!tacc.getText().equals("")) {
			int tacc1 = Integer.parseInt(tacc.getText());

			try {
				// ADD CODE USING ATM VALIDATION
				ATM atm = new ATM();

				for (Stock e : choices) {
					if (String.valueOf(e.getName()).equals(stkSel)) {
						String checkedResult = atm.buyStock(e, tacc1);
						if (checkedResult.equals(SUCCESS)) {
							err.setText("Stock added Successfully!");
							err.setVisible(true);
							tacc.setText("");
							tamt.setText("");
							tmsg.setText("");
						}else{
							err.setText("Stock could not be bought");
							err.setVisible(true);
						}
					}
				}

			} catch (Exception e) {
				System.out.println(e);
			}
		}else{
			err.setText("Enter a valid quantity");
			err.setVisible(true);
		}
	        } 


	}
}

