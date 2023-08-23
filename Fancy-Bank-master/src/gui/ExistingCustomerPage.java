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

import java.util.ArrayList;
import java.util.UUID;
//CLASS FOR DISPLAYING ACCOUNT FUNCTIONS TO EACH CUSTOMER
public class ExistingCustomerPage extends JFrame implements ActionListener{

  JButton ad,rem,cf,tr,vi,ca,na,loan,logout,submit,back,close;
  Container c;
  JLabel label,limg,ladn,ldes,lorg,lima;
  ImageIcon im,ima;
  
  public ExistingCustomerPage(){
  
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
   label = new JLabel("CUSTOMER FUNCTIONS : ");
   label.setBounds(600,90,200,50);
   im = new ImageIcon(getClass().getResource("Logo.jpg"));
   limg = new JLabel(im);
   limg.setBounds(1020,10,300,100);
   c.add(limg);
   na = new JButton("Create Account");
   na.setBounds(570,150,200,50);
   ad = new JButton("Make Deposit");
   ad.setBounds(570,200,200,50);
   rem = new JButton("Make Withdrawal");
   rem.setBounds(570,250,200,50);
   tr = new JButton("Make Transaction");
   tr.setBounds(570,300,200,50);
   cf = new JButton("Manage Stocks");
   cf.setBounds(570,350,200,50);
   ca = new JButton("View Transaction History");
   ca.setBounds(570,400,200,50);
   loan = new JButton("Loans");
   loan.setBounds(570,450,200,50);
   close = new JButton("Close Account");
   close.setBounds(570,500,200,50);
   vi = new JButton("View Account Info");
   vi.setBounds(570,550,200,50);
   back = new JButton("Back");
   back.setBounds(400,600,100,20);
   c.add(back);
   logout = new JButton("Logout");
   logout.setBounds(400,800,100,20);
   c.add(logout);
   c.add(label);
   c.add(vi);
   c.add(close);
   c.add(ad);
   c.add(rem);
   c.add(cf);
   c.add(tr);
   c.add(ca);
   c.add(na);
   c.add(loan);
   ad.addActionListener(this);
   rem.addActionListener(this);
   cf.addActionListener(this);
   tr.addActionListener(this);
   close.addActionListener(this);
   vi.addActionListener(this);
   ca.addActionListener(this);
   na.addActionListener(this);
   loan.addActionListener(this);
   logout.addActionListener(this);
   back.addActionListener(this);
  }
  
  public void actionPerformed(ActionEvent ae) {
    String s = ae.getActionCommand();
    
    if(s.equals("Create Account")){

      this.dispose();
      CreateAccountPage cad = new CreateAccountPage();
      cad.setSize(4096,7016);
      cad.setVisible(true);
      cad.setTitle("New Account");
   }

    if(s.equals("Make Deposit")){

      //  chooseAcc();
      this.dispose();
       DepositPage cad = new DepositPage();
       cad.setSize(4096,7016);
       cad.setVisible(true);
       cad.setTitle("Make Deposit");
   }
  
    if(s.equals("Make Withdrawal")){
      this.dispose();
       WithdrawPage att = new WithdrawPage();
       att.setSize(4096,7016);
       att.setVisible(true);
       att.setTitle("Make Withdrawal");
   }

    if(s.equals("Loans")){
      this.dispose();
      LoanPage loan = new LoanPage();
      loan.setSize(4096,7016);
      loan.setVisible(true);
      loan.setTitle("Loan Page");
  }

    if(s.equals("Make Transaction")){
      this.dispose();
      TransactionsPage ttt = new TransactionsPage();
      ttt.setSize(4096,7016);
      ttt.setVisible(true);
      ttt.setTitle("Make Transaction");
    }
  
    if(s.equals("Back")){
      this.dispose();
        LoginPage t = new LoginPage();
         t.setSize(4960,7016);
          t.setVisible(true);
          t.setLayout(null);
  
   }
  
    if(s.equals("Manage Stocks")){
      this.dispose();
       CustomerStockPage t = new CustomerStockPage();
       t.setSize(4096,7016);
       t.setVisible(true);
       t.setTitle("Manage Stocks");
   }

   if(s.equals("Close Account")){
    this.dispose();
     CloseAccountPage t = new CloseAccountPage();
     t.setSize(4096,7016);
     t.setVisible(true);
     t.setTitle("Close Account");
 }
  
      if(s.equals("View Transaction History")){
        TransacHistoryPage t = new TransacHistoryPage();

    }
      if(s.equals("View Account Info")){
          AccountInfo t = new AccountInfo();
          t.setSize(4096,7016);
          t.setVisible(true);
          t.setTitle("Account Info");
      }

      if(s.equals("Logout")){
          LoginPage t = new LoginPage();
          t.setSize(4096,7016);
          t.setVisible(true);
          t.setTitle("FANCY BANK - INDEX");
      }
  
  }

}
