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

//CLASS FOR DISPLAYING STOCK FUNCTIONS TO THE MANAGER
public class ManagerStockPage extends JFrame implements ActionListener{

        private Container c;
        private JLabel name,amt,lt1,h1,h2,h3,name2, lima,label;
        private JLabel pass,msg,lt2,ls,lt,la,li, pass2,curr,acc,msg1;
        private JTextField tacc,tcurr,tamt,tmsg,tmsg1;
        JLabel limg,ladn,ldes,lorg;
        private JComboBox<String> cb;
        private JLabel err;
        private ImageIcon logo, ima,im;
        private JButton submit,logout,back,na,rem,ad,mkt;
    
        ManagerStockPage(){
    
            c = this.getContentPane();
            c.setBackground(new java.awt.Color(255, 249, 170));
            //c.setBackground(Color.gray);
            c.setLayout(null);
           
            ima = new ImageIcon(getClass().getResource("dp.png"));
            lima = new JLabel(ima);
            lima.setBounds(30,10,100,100);
            c.add(lima);
            ladn = new JLabel("admin1",JLabel.LEFT);
            ladn.setBounds(30,110,100,20);
            ldes = new JLabel("Manager",JLabel.LEFT);
            ldes.setBounds(30,130,100,20);
            lorg = new JLabel("Fancy Bank",JLabel.LEFT);
            lorg.setBounds(30,150,300,20);
            c.add(ladn);
            c.add(ldes);
            c.add(lorg);
            label = new JLabel("MANAGE STOCKS");
            label.setBounds(600,90,200,50);
            im = new ImageIcon(getClass().getResource("Logo.jpg"));
            limg = new JLabel(im);
            limg.setBounds(1020,10,300,100);
            c.add(limg);
            na = new JButton("Add Stocks");
            na.setBounds(570,150,200,50);
            ad = new JButton("Delete Stocks");
            ad.setBounds(570,250,200,50);
            rem = new JButton("Modify Stocks");
            rem.setBounds(570,350,200,50);
            mkt = new JButton("Check Stock Market");
            mkt.setBounds(570,450,200,50);
            back = new JButton("Back");
            back.setBounds(400,600,100,20);
            c.add(back);
            c.add(label);
            c.add(ad);
            c.add(rem);
            c.add(na);
            c.add(mkt);
            logout = new JButton("Logout");
            logout.setBounds(400,800,100,20);
            c.add(logout);
            logout.addActionListener(this);
            ad.addActionListener(this);
            rem.addActionListener(this);
            na.addActionListener(this);
            back.addActionListener(this);
            mkt.addActionListener(this);
           }
           
           public void actionPerformed(ActionEvent ae){
             String s = ae.getActionCommand();
             
             if(s.equals("Add Stocks")){
         
               AddStockPage cad = new AddStockPage();
               cad.setSize(4096,7016);
               cad.setVisible(true);
               cad.setTitle("Add Stocks");
            }
         
             if(s.equals("Delete Stocks")){
         
               //  chooseAcc();
               this.dispose();
                DeleteStockPage cad = new DeleteStockPage();
                cad.setSize(4096,7016);
                cad.setVisible(true);
                cad.setTitle("Delete Stocks");
            }
           
             if(s.equals("Modify Stocks")){
              this.dispose();
                ModifyStockPage att = new ModifyStockPage();
                att.setSize(4096,7016);
                att.setVisible(true);
                att.setTitle("Modify Stocks");
            }

            if(s.equals("Check Stock Market")){
              StockMarketPage att = new StockMarketPage();

          }
           
             if(s.equals("Back")){
                 this.dispose();
                 ManagerPage t = new ManagerPage();
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
    
