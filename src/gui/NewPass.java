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
//CLASS FOR RESETTING PASSWORD
public class NewPass extends JFrame implements ActionListener{

    JTextField t1,t2,t3,t4,t;
    JLabel l1,l2,l3,l4,l5;
    JButton b1,b2;
    Container con;
    String username;

    NewPass(String uname){
    username = uname;
    con = getContentPane();
    con.setLayout(null);
    
    l2 = new JLabel("Enter a New Password");
    l2.setBounds(30,120,500,20);
    l2.setVisible(true);
    con.add(l2);
    t2 = new JTextField("");
    t2.setBounds(40,140,100,20);
    t2.setVisible(true);
    con.add(t2);
    //q2
    l3 = new JLabel("Re-enter new password");
    l3.setBounds(30,170,500,20);
    l3.setVisible(true);
    con.add(l3);
    t3 = new JTextField("");
    t3.setBounds(40,190,100,20);
    t3.setVisible(true);
    con.add(t3);
    //button
    b1 = new JButton("Submit");
    b1.setBounds(90,230,100,20);
    con.add(b1);
    b1.setVisible(true);
    b1.addActionListener(this);

    l5 = new JLabel("Password Reset Successfully!");
    l5.setFont(new Font("Arial", Font.PLAIN, 15));
    l5.setSize(500, 20);
    l5.setLocation(600, 700);
    l5.setVisible(false);
    con.add(l5);

    b2 = new JButton("Back");
    b2.setBounds(30,290,100,20);
    b2.setVisible(true);
    con.add(b2);
    b2.addActionListener(this);
    t2.addActionListener(this);
	t3.addActionListener(this);
   
        
    }

    public String getName(){
        return username;
    }

    public void actionPerformed(ActionEvent ae){
        String s = ae.getActionCommand();
        String newpass = t2.getText();
        String renewpass = t3.getText();
 
            if(s.equals("Submit")){
             
             if(newpass.equals(renewpass)){
                 try {
                     ATM atm = new ATM();
                     String usernamee = getName();
                     atm.setNewPassword(usernamee, renewpass);
                     l5.setVisible(true);
                     // System.out.println("Password Reset Successfully!");
                 }catch (Exception e){
                     System.out.println(e);
                 }
             }
              else{

                l5.setText("Passwords don't match");
                l5.setVisible(true);
                con.add(l5);
             }
             
                 }
       
         
           if(s.equals("Back")){
                 this.dispose();
                 LoginPage t = new LoginPage();
                 t.setSize(4960,7016);
                   t.setVisible(true);
                   t.setLayout(null);
             }
        
    }
 
  } 
 
