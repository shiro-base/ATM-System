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

//CLASS FOR DISPLAYING BANK RESERVES TO MANAGER
public class BankReservePage extends JFrame implements ActionListener{

    JTextField t1,t2,t3,t4,t;
    JLabel l1,l2,l3,l4,label,l21,l22;
    JButton b1,b2,logout;
    Container con;

    BankReservePage(){

    con = getContentPane();
    con.setLayout(null);


    label = new JLabel("Bank Reserves Info");
    label.setFont(new Font("Arial",Font.BOLD,20));
    label.setBounds(700,90,400,50);

    con.add(label);

        try {
            ATM atm = new ATM();
            String money = String.valueOf(atm.getToTalBankMoney());

            l21 = new JLabel(money);
            l21.setFont(new Font("Arial",Font.PLAIN,15));
            l21.setBounds(700,200,500,20);
            l21.setVisible(true);
            con.add(l21);

            String cust = String.valueOf(atm.getAllCustomers().size());
            System.out.println(money+" "+ cust);

            l22 = new JLabel(cust);
            l22.setFont(new Font("Arial",Font.PLAIN,15));
            l22.setBounds(700,300,500,20);
            l22.setVisible(true);
            con.add(l22);

        }catch (Exception e){
            System.out.println(e);
        }
    
    l2 = new JLabel("Total Amount in Bank : ");
    l2.setBounds(500,200,500,20);
    l2.setVisible(true);
    con.add(l2);


    //q2
    l3 = new JLabel("Total Number of Customers : ");
    l3.setBounds(500,300,500,20);
    l3.setVisible(true);
    con.add(l3);




    //button
    
    b2 = new JButton("Back");
    b2.setBounds(300,400,100,20);
    b2.setVisible(true);
    con.add(b2);
    b2.addActionListener(this);
        logout = new JButton("Logout");
        logout.setBounds(300,600,100,20);
        con.add(logout);
        logout.addActionListener(this);
   
    }

    public void actionPerformed(ActionEvent ae){

        // MODIFY THIS ACC TO ATM
        String s = ae.getActionCommand();

           if(s.equals("Back")){
                 this.dispose();
                 ManagerPage t = new ManagerPage();
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
    }
 
  } 
 

