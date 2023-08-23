package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import javax.swing.JFrame;

import bank.*;
import account.*;
import currency.*;
import Database.*;
import Exceptions.*;
import Main.*;
import StockMarket.*;

//CLASS FOR CREATING A NEW ACCOUNT OF CUSTOMER
public class NewCustomerPage extends JFrame implements ActionListener{

    private JLabel title,mail,addr, name,cname,pass,sec,sec1,sec2,l1,l2, msg;
    Button proceed, back;
    private JTextField tname,taddr,tmail,tcname, l3;
    Container c;
	private TextField tpass;
    public static ButtonGroup t;
    ImageIcon im, im2;

    NewCustomerPage(){

    c = this.getContentPane();
    c.setBackground(new java.awt.Color(255, 249, 170));
    //c.setBackground(Color.gray);
    c.setLayout(null);

    title = new JLabel("Registration Form");
    title.setFont(new Font("Arial", Font.BOLD, 20));
    title.setSize(700, 30);
    title.setLocation(600, 30);
    c.add(title);

    cname = new JLabel("Name");
    cname.setFont(new Font("Arial",Font.PLAIN,15));
    cname.setBounds(600,200,100,20);
    cname.setVisible(true);
    //name.setLocation(50,200);
    c.add(cname);

    tcname = new JTextField();
    tcname.setFont(new Font("Arial",Font.PLAIN,15));
    tcname.setBounds(720,200,100,20);
    tcname.setVisible(true);
    //tname.setLocation(150,200);
    c.add(tcname);

    name = new JLabel("Username");
    name.setFont(new Font("Arial",Font.PLAIN,15));
    name.setBounds(600,250,100,20);
    name.setVisible(true);
    //name.setLocation(50,200);
    c.add(name);

    tname = new JTextField();
    tname.setFont(new Font("Arial",Font.PLAIN,15));
    tname.setBounds(720,250,100,20);
    tname.setVisible(true);
    //tname.setLocation(150,200);
    c.add(tname);

    pass = new JLabel("Password");
    pass.setFont(new Font("Arial",Font.PLAIN,15));
    pass.setBounds(600,300,100,20);
    pass.setVisible(true);
    //pass.setLocation(50,220);
    c.add(pass);

    tpass = new TextField(7);
    tpass.setFont(new Font("Arial",Font.PLAIN,15));
    tpass.setBounds(720,300,100,20);
    tpass.setEchoChar('*');
    tpass.setVisible(true);
    c.add(tpass);

    mail = new JLabel("Email");
    mail.setFont(new Font("Arial",Font.PLAIN,15));
    mail.setBounds(600,350,100,20);
    mail.setVisible(true);
    //name.setLocation(50,200);
    c.add(mail);

    tmail = new JTextField();
    tmail.setFont(new Font("Arial",Font.PLAIN,15));
    tmail.setBounds(720,350,100,20);
    tmail.setVisible(true);
    //tname.setLocation(150,200);
    c.add(tmail);

    addr = new JLabel("Address");
    addr.setFont(new Font("Arial",Font.PLAIN,15));
    addr.setBounds(600,400,100,52);
    addr.setVisible(true);
    //name.setLocation(50,200);
    c.add(addr);

    taddr = new JTextField();
    taddr.setFont(new Font("Arial",Font.PLAIN,15));
    taddr.setBounds(720,400,500,100);
    taddr.setVisible(true);
    //tname.setLocation(150,200);
    c.add(taddr);

    sec1 = new JLabel("In case you forget your password\n, your Security Question is the following:");
    sec1.setFont(new Font("Arial",Font.PLAIN,15));
    sec1.setBounds(600,550,600,20);
    sec1.setVisible(true);
    //pass.setLocation(50,220);
    c.add(sec1);

    sec2 = new JLabel("What was the name of your first school?");
    sec2.setFont(new Font("Arial",Font.PLAIN,15));
    sec2.setBounds(600,600,600,20);
    sec2.setVisible(true);
    //pass.setLocation(50,220);
    c.add(sec2);

    sec = new JLabel("Your answer");
    sec.setFont(new Font("Arial",Font.PLAIN,15));
    sec.setBounds(600,650,100,20);
    sec.setVisible(true);
    //pass.setLocation(50,220);
    c.add(sec);

    l3 = new JTextField(7);
    l3.setFont(new Font("Arial",Font.PLAIN,15));
    l3.setBounds(720,650,100,20);
    l3.setVisible(true);
    //tpass.setLocation(150,220);
    c.add(l3);
    

    proceed = new Button("Proceed");
    c.add(proceed);
    proceed.setBounds(1200,700,75,20);
    proceed.addActionListener(this);

    back = new Button("Back");
    c.add(back);
    back.setBounds(1100,700,75,20);
    back.addActionListener(this);

    msg = new JLabel();
    msg.setFont(new Font("Arial",Font.PLAIN,15));
    msg.setSize(300, 20);
    msg.setLocation(600, 800);
    c.add(msg);

    im = new ImageIcon(getClass().getResource("Logo.jpg"));
    l1 = new JLabel(im);
    l1.setBounds(1100,20,300,100);
    //  l1.setLayout(new BorderLayout.EAST);
    c.add(l1);

    im2 = new ImageIcon(getClass().getResource("dp.png"));
    l2 = new JLabel(im2);
    l2.setBounds(40,30,150,150);
    //  l1.setLayout(new BorderLayout.EAST);
    c.add(l2);

    }

    public void actionPerformed(ActionEvent e){

        String name = tname.getText();
		String pass = tpass.getText();
        String mail = tmail.getText();
        String addr = taddr.getText();
        String acname = tcname.getText();
		String secu = l3.getText();

    if(e.getSource() == proceed){

        try {
            ATM atm = new ATM();
            System.out.println("created new ATM obj");
            String res = atm.registerNewCustomer(pass,name,acname,addr,mail,secu);

            System.out.println(res);
            msg.setText(res);
            msg.setVisible(true);
            //pass.setLocation(50,220);

        } catch (Exception excep) {
            System.err.println ("Exception " + excep);
         }
    }

   if(e.getSource() == back){

        this.dispose();
        LoginPage f = new LoginPage();
        f.setSize(4100,7020);
        f.setLayout(null);
        f.setVisible(true);

    }if(e.equals("Logout")){
            LoginPage t = new LoginPage();
            t.setSize(4096,7016);
            t.setVisible(true);
            t.setTitle("FANCY BANK - INDEX");
        }

    }

}
