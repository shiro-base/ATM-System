package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JFrame;

import bank.ATM;


//CLASS FOR DISPLAYING THE STARTING PAGE OF THE PROJECT : WELCOME MESSAGE AND LOGIN FUNCTIONS OF CUSTOMER AND MANAGER
public class LoginPage extends JFrame implements ActionListener{
	private Container c;
	private JLabel title;
	private JLabel name,l1,lt1,h1,h2,h3,name2;
	private JLabel pass,l2,lt2,ls,lt,la,li, pass2;
	private JTextField tname, tname1, tname2, datetext;
	private TextField tpass, tpass1, tpass2;
	private JLabel branch;
	private String bvalue = "";
	private JLabel descrip,d1,d2,d3,d4,d5,l5;
	private ImageIcon logo;
	private JButton b,s,t,a, login,signup,fp, date;
	static ATM atm;

	LoginPage(){

		c = getContentPane();
		c.setLayout(null);

		descrip = new JLabel("Our Fancy Bank application is the one-stop destination for all your banking needs!");
		descrip.setFont(new Font("Arial",Font.ITALIC,14));
		//descrip.setSize(500,400);
		descrip.setBounds(20,20,2000,18);

		//descrip.setEditable(false);
		c.add(descrip);

		d1 = new JLabel("Customers can choose to check their balance, make transactions, take loans & even play the stock market!");
		d1.setFont(new Font("Arial",Font.ITALIC,14));
		d1.setBounds(20,38,2000,36);
		c.add(d1);


		d2 = new JLabel("Do contact us for complaints or queries & we will be happy to help.");
		d2.setFont(new Font("Arial",Font.ITALIC,14));
		d2.setBounds(20,56,2000,54);
		c.add(d2);

		d3 = new JLabel("Always at your service!");
		d3.setFont(new Font("Arial",Font.ITALIC,14));
		d3.setBounds(20,74,2000,72);
		c.add(d3);



		logo = new ImageIcon(getClass().getResource("Logo.jpg"));
		li = new JLabel(logo);
		//logo.setSize(200,100);
		li.setBounds(1250,20,100,100);


		c.add(li);

		title = new JLabel("LOGIN");
		title.setFont(new Font("Arial",Font.BOLD,20));
		title.setBounds(650,200,200,30);

		c.add(title);

		// JXDatePicker picker = new JXDatePicker();
        // picker.setDate(Calendar.getInstance().getTime());
        // picker.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
		// picker.setBounds(150,200);
        // c.add(picker);

		// UtilDateModel model = new UtilDateModel();
		// JDatePanelImpl datePanel = new JDatePanelImpl(model);
		// JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);

		// c.add(datePicker);

		s = new JButton("Customer");
		s.setBounds(500,280,100,50);
		s.addActionListener(this);
		c.add(s);

		a = new JButton("Manager");
		a.setBounds(800,280,100,50);
		a.addActionListener(this);
		c.add(a);

        login = new JButton("Login");
        signup = new JButton("Sign Up");

		name = new JLabel("Username");
		name.setFont(new Font("Arial",Font.PLAIN,15));
		name.setBounds(600,400,100,20);
		name.setVisible(false);
		//name.setLocation(50,200);
		c.add(name);

		tname = new JTextField();
		tname.setFont(new Font("Arial",Font.PLAIN,15));
		tname.setBounds(720,400,100,20);
		tname.setVisible(false);
		//tname.setLocation(150,200);
		c.add(tname);

		pass = new JLabel("Password");
		pass.setFont(new Font("Arial",Font.PLAIN,15));
		pass.setBounds(600,450,100,20);
		pass.setVisible(false);
		//pass.setLocation(50,220);
		c.add(pass);

		tpass = new TextField(7);
		tpass.setFont(new Font("Arial",Font.PLAIN,15));
		tpass.setBounds(720,450,100,20);
		tpass.setEchoChar('*');
		tpass.setVisible(false);
		//tpass.setLocation(150,220);
		c.add(tpass);

		l1 = new JLabel("Username");
		l1.setFont(new Font("Arial",Font.PLAIN,15));
		l1.setBounds(600,400,100,20);
		l1.setVisible(false);
		c.add(l1);

		tname1 = new JTextField();
		tname1.setBounds(720,400,100,20);
		tname1.setVisible(false);
		c.add(tname1);

		l2 = new JLabel("Password");
		l2.setFont(new Font("Arial",Font.PLAIN,15));
		l2.setBounds(600,450,100,20);
		l2.setVisible(false);
		c.add(l2);

		tpass1 = new TextField(7);
		tpass1.setBounds(720,450,100,20);
		tpass1.setEchoChar('*');
		tpass1.setVisible(false);
		c.add(tpass1);

		lt1 = new JLabel("Username");
		lt1.setFont(new Font("Arial",Font.PLAIN,15));
		lt1.setBounds(600,400,100,20);
		lt1.setVisible(false);
		c.add(lt1);

		tname2 = new JTextField();
		tname2.setBounds(720,400,100,20);
		tname2.setVisible(false);
		c.add(tname2);

		lt2 = new JLabel("Password");
		lt2.setFont(new Font("Arial",Font.PLAIN,15));
		lt2.setBounds(600,450,100,20);
		lt2.setVisible(false);
		c.add(lt2);

		tpass2 = new TextField(7);
		tpass2.setBounds(720,450,100,20);
		tpass2.setEchoChar('*');
		tpass2.setVisible(false);
		c.add(tpass2);

		datetext = new JTextField();
		datetext.setFont(new Font("Arial",Font.PLAIN,15));
		datetext.setBounds(200,600,100,20);
		datetext.setVisible(true);
		datetext.addActionListener(this);
		//tname.setLocation(150,200);
		c.add(datetext);

		date = new JButton("increment days");
		date.setBounds(320,600,150,20);
		date.setVisible(true);
		//date.setLocation(50,200);
		c.add(date);

		login.setBounds(600,600,100,20);
		login.addActionListener(this);
		login.setVisible(false);
		c.add(login);

		signup.setBounds(700,600,100,20);
		signup.addActionListener(this);
		signup.setVisible(false);
		c.add(signup);

        fp = new JButton("Forgot Password");
        fp.setBounds(840,450,200,20);
        fp.addActionListener(this);
        fp.setVisible(false);
        c.add(fp);

		l5 = new JLabel();
		l5.setFont(new Font("Arial", Font.PLAIN, 15));
		l5.setSize(500, 20);
		l5.setLocation(600, 700);
		l5.setVisible(false);
		c.add(l5);

		tname.addActionListener(this);
		tpass.addActionListener(this);
		tname1.addActionListener(this);
		tpass1.addActionListener(this);
		tname2.addActionListener(this);
		tpass2.addActionListener(this);
		// picker.addActionListener(this);

	}

	public void actionPerformed(ActionEvent ae){
		String s = ae.getActionCommand();
		String s1 = ae.getActionCommand();

		if(s1.equals("Customer")){
			if(bvalue.equals(""))
				bvalue = s1;
			h1 = new JLabel("CUSTOMER LOGIN");
			h1.setFont(new Font("Arial",Font.PLAIN,15));
			h1.setBounds(600,350,200,20);

			name.setVisible(true);
			tname.setVisible(true);
			pass.setVisible(true);
			tpass.setVisible(true);
			login.setVisible(true);
			signup.setVisible(true);
            fp.setVisible(true);

      		l1.setVisible(false);
			tname1.setVisible(false);
			l2.setVisible(false);
			tpass1.setVisible(false);

			lt1.setVisible(false);
			tname2.setVisible(false);
			lt2.setVisible(false);
			tpass2.setVisible(false);

		}


		else if(s1.equals("Manager")){
			if(bvalue.equals(""))
				bvalue = s1;
			h2 = new JLabel("MANAGER LOGIN");
			h2.setFont(new Font("Arial",Font.PLAIN,15));
			h2.setBounds(600,350,200,20);

			l1.setVisible(true);
			tname1.setVisible(true);
			l2.setVisible(true);
			tpass1.setVisible(true);
			login.setVisible(true);
            fp.setVisible(true);

			name.setVisible(false);
			tname.setVisible(false);
			pass.setVisible(false);
			tpass.setVisible(false);

			lt1.setVisible(false);
			tname2.setVisible(false);
			lt2.setVisible(false);
			tpass2.setVisible(false);

		} else if (s1.equals("increment days")) {
			try {
				int days = Integer.parseInt(datetext.getText());
				System.out.println(days);
				try {
					String str = atm.incrementDate(days);
					System.out.println("increment days msg : " + str);
					l5.setText(str);
					l5.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
					l5.setText("could not increment");
					l5.setVisible(true);
				}
			}catch (Exception e){
				e.printStackTrace();
			}
		}

		String n = tname1.getText();
		String p = tpass1.getText();
		String lname = tname.getText();
		String lpass = tpass.getText();
		String lname1 = tname2.getText();
		String lpass1 = tpass2.getText();

		// CHECK IF BUTTON PRESSED IS CUSTOMER OR MANAGER
	if(s.equals("Login") && bvalue.equals("Customer")) {

		// ADD CODE USING ATM VALIDATION
		try {

			String checkedResult = atm.login(bvalue, lname, lpass);
			System.out.println(checkedResult);
			if(checkedResult.equals("Successfully logged in")){
				//l3.setVisible(true);
				this.dispose();
				ExistingCustomerPage c = new ExistingCustomerPage();
				c.setSize(4096,7016);
				c.setLayout(null);
				c.getContentPane().setBackground(new java.awt.Color(207, 158, 255));
				c.setVisible(true);
			}
			else{
				l5.setText(checkedResult);
				l5.setVisible(true);

				tname.setText("");
				tpass.setText("");
			}
		}
		catch (Exception e){
			System.out.println(e);
		}

		}

		else if(s.equals("Login") && bvalue.equals("Manager")){
		try {

		String checkedResultAd = atm.login(bvalue, n, p);
		
		if(checkedResultAd.equals("Successfully logged in")){

			this.dispose();
			ManagerPage f2 = new ManagerPage();
			f2.setSize(4096,7016);
			f2.getContentPane().setBackground(new java.awt.Color(207, 158, 255));
			f2.setLayout(null);
			f2.setVisible(true);

		}

		else{

			l5.setText(checkedResultAd);
			l5.setVisible(true);

			tname.setText("");
			tpass.setText("");
		}}catch (Exception e){
			System.out.println(e);
		}

	        }

        else if(s.equals("Sign Up")){

			this.dispose();

			NewCustomerPage c = new NewCustomerPage();
			c.setSize(4096,7016);
			c.getContentPane().setBackground(new java.awt.Color(207, 158, 255));
			c.setLayout(null);
			c.setVisible(true);

            }

        else if(s.equals("Forgot Password")){
            	ForgotPass f = new ForgotPass();
                f.setTitle("Forgot Password");
                f.setSize(400,400);
                f.getContentPane().setBackground(new java.awt.Color(207, 158, 255));
                f.setVisible(true);
        }
	}

	public static void main(String []args) throws Exception {

		LoginPage In = new LoginPage();
		atm = new ATM(true);
		In.setTitle("FANCY BANK - INDEX");
		In.setSize(4960,7016);
		In.getContentPane().setBackground(new java.awt.Color(174, 234, 255));
		In.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		In.setVisible(true);
	}

}
