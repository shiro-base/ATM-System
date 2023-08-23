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
//CLASS FOR DISPLAYING FORGOT PASSWORD SCREEN OF EACH CUSTOMER
public class ForgotPass extends JFrame implements ActionListener {
JTextField t1,t2,t3,t4,t;
JLabel l1,l2,l3,l4,err;
JButton b1,b2;
Container con;

public ForgotPass(){

  con = getContentPane();
	con.setLayout(null);
   
   l2 = new JLabel("Enter username");
   l2.setBounds(30,120,500,20);
   l2.setVisible(true);
   con.add(l2);
   t2 = new JTextField("");
   t2.setBounds(40,140,100,20);
   t2.setVisible(true);
   con.add(t2);
   //q2
   l3 = new JLabel("Security Question : What is the name of your first school?");
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

    err = new JLabel("Error Occurred! Account Balance insufficient.");
    err.setFont(new Font("Arial", Font.PLAIN, 15));
    err.setSize(300, 20);
    err.setLocation(30, 350);
    err.setVisible(false);
    con.add(err);
   
   b2 = new JButton("Back");
   b2.setBounds(30,290,100,20);
   b2.setVisible(true);
   con.add(b2);
   b2.addActionListener(this);
   t2.addActionListener(this);
	 t3.addActionListener(this);


   
 }
   public void actionPerformed(ActionEvent ae){
       String s = ae.getActionCommand();
       String uname = t2.getText();
       String sec = t3.getText();

           if(s.equals("Submit")) {
               try {
                   ATM atm = new ATM();

                   Boolean checkedResult = atm.forgotPassword(uname, sec);

                   if (checkedResult == false) {
                       System.out.println("No entry exists");
                       err.setText("Incorrect answer");
                       err.setVisible(true);
                   } else {
                       NewPass reset = new NewPass(uname);
                       reset.setSize(400, 400);
                       reset.setVisible(true);
                       reset.setLayout(null);
                   }
               }catch (Exception e){
                   System.out.println(e);
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
