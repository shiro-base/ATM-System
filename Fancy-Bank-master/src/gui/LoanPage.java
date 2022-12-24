package gui;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import bank.*;
import account.*;
import currency.*;
import Database.*;
import Exceptions.*;
import loan.*;
import Main.*;
import StockMarket.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;
//CLASS FOR DISPLAYING LOAN PAGE OF EACH CUSTOMER
public class LoanPage extends JFrame implements ActionListener{

  JButton ad,rem,cf,tr,ca,na,loan,submit,logout,back;
  Container c;
  JLabel label,limg,ladn,ldes,lorg,lima;
  ImageIcon im,ima;
  
  public LoanPage(){
  
   c = this.getContentPane();
   c.setBackground(new java.awt.Color(255, 249, 170));
   //c.setBackground(Color.gray);
   c.setLayout(null);
  
   ima = new ImageIcon(getClass().getResource("dp.png"));
   lima = new JLabel(ima);
   lima.setBounds(30,10,100,100);
   c.add(lima);
   ladn = new JLabel("Customer_name",JLabel.LEFT);
   ladn.setBounds(30,110,100,20);
   ldes = new JLabel("Customer",JLabel.LEFT);
   ldes.setBounds(30,130,100,20);
   lorg = new JLabel("Fancy Bank",JLabel.LEFT);
   lorg.setBounds(30,150,300,20);
   c.add(ladn);
   c.add(ldes);
   c.add(lorg);
   label = new JLabel("LOAN OPTIONS");
   label.setBounds(600,90,200,50);
   im = new ImageIcon(getClass().getResource("Logo.jpg"));
   limg = new JLabel(im);
   limg.setBounds(1020,10,300,100);
   c.add(limg);
   na = new JButton("Take Loan");
   na.setBounds(570,150,200,50);
   ad = new JButton("Repay Loan");
   ad.setBounds(570,250,200,50);
   rem = new JButton("View Current Loans");
   rem.setBounds(570,350,200,50);
   
   back = new JButton("Back");
   back.setBounds(400,600,100,20);
   c.add(back);
      logout = new JButton("Logout");
      logout.setBounds(400,800,100,20);
      c.add(logout);
      logout.addActionListener(this);
   c.add(label);
   c.add(ad);
   c.add(rem);
   c.add(na);

   ad.addActionListener(this);
   rem.addActionListener(this);
   na.addActionListener(this);
   back.addActionListener(this);
  }
  
  public void actionPerformed(ActionEvent ae){
    String s = ae.getActionCommand();
    
    if(s.equals("Take Loan")){

      this.dispose();
      TakeLoanPage cad = new TakeLoanPage();
      cad.setSize(4096,7016);
      cad.setVisible(true);
      cad.setTitle("Take Loan");
   }

    if(s.equals("Repay Loan")){

      //  chooseAcc();
      this.dispose();
       RepayLoanPage cd = new RepayLoanPage();
       cd.setSize(4096,7016);
       cd.setVisible(true);
       cd.setTitle("Repay Loan");
   }
  
     if(s.equals("View Current Loans")){
       ViewLoansPage att = new ViewLoansPage();

     }
  
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
  
  }

}
