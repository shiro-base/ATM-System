package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import bank.*;
import account.*;
import currency.*;
import Database.*;
import Exceptions.*;
import loan.*;
import Main.*;
import StockMarket.*;
//CLASS FOR DISPLAYING ACCOUNT FUNCTIONS OF THE MANAGER
public class ManagerPage extends JFrame implements ActionListener{
JButton ad,rem,cf,ca,submit,logout,back;
Container c;
JLabel label,limg,ladn,ldes,lorg,lima;
ImageIcon im,ima;

public ManagerPage(){

 c = this.getContentPane();
 c.setBackground(new java.awt.Color(255, 249, 170));
 //c.setBackground(Color.gray);
 c.setLayout(null);

 ima = new ImageIcon(getClass().getResource("adm.png"));
 lima = new JLabel(ima);
 lima.setBounds(30,10,100,100);
 c.add(lima);
 ladn = new JLabel("Manager",JLabel.LEFT);
 ladn.setBounds(30,110,100,20);
 ldes = new JLabel("Administration",JLabel.LEFT);
 ldes.setBounds(30,130,100,20);
 lorg = new JLabel("Fancy Bank",JLabel.LEFT);
 lorg.setBounds(30,150,300,20);
 c.add(ladn);
 c.add(ldes);
 c.add(lorg);
 label = new JLabel("MANAGER FUNCTIONS");
 label.setBounds(600,90,200,50);
 im = new ImageIcon(getClass().getResource("Logo.jpg"));
 limg = new JLabel(im);
 limg.setBounds(1020,10,300,100);
 c.add(limg);
 ad = new JButton("Get Daily Report");
 ad.setBounds(570,150,200,50);
 rem = new JButton("View Customer List");
 rem.setBounds(570,250,200,50);
 cf = new JButton("Manage Stocks");
 cf.setBounds(570,350,200,50);
 ca = new JButton("View Bank Reserves");
 ca.setBounds(570,450,200,50);
 back = new JButton("Back");
 back.setBounds(400,470,100,20);
 c.add(back);
 logout = new JButton("Logout");
 logout.setBounds(400,800,100,20);
 c.add(logout);
 logout.addActionListener(this);
 c.add(label);
 c.add(ad);
 c.add(rem);
 c.add(cf);
 c.add(ca);
 ad.addActionListener(this);
 rem.addActionListener(this);
 cf.addActionListener(this);
 ca.addActionListener(this);
 back.addActionListener(this);
}

public void actionPerformed(ActionEvent ae){
  String s = ae.getActionCommand();

   if(s.equals("View Customer List")){
      CustomerListPage cad = new CustomerListPage();

  }

   if(s.equals("Get Daily Report")){
      DailyReportPage att = new DailyReportPage();

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
     ManagerStockPage t = new ManagerStockPage();
     t.setSize(4096,7016);
     t.setVisible(true);
     t.setTitle("Manage Stocks");
 }

   if(s.equals("View Bank Reserves")){
  this.dispose();
     BankReservePage t = new BankReservePage();
     t.setSize(4096,7016);
     t.setVisible(true);
     t.setTitle("View Bank Reserves");

 }

   if(s.equals("Logout")){
  LoginPage t = new LoginPage();
  t.setSize(4096,7016);
  t.setVisible(true);
  t.setTitle("FANCY BANK - INDEX");
 }

}

}
