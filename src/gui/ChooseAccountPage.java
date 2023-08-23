package gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.Image.*;

//import javax.lang.model.element.ModuleElement.ExportsDirective;
import javax.swing.*;
import javax.swing.ImageIcon.*;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JFrame;
import java.io.*; 
import java.util.stream.Stream;
import java.util.stream.Collectors;
import javax.swing.JPanel;
import java.util.Scanner;
import bank.*;
import account.*;
import currency.*;
import Database.*;
import Exceptions.*;
import loan.*;
import Main.*;
import StockMarket.*;

//CLASS FOR CHOOSING ACCOUNT INFO OF EACH CUSTOMER
public class ChooseAccountPage extends JFrame implements ActionListener{
    JButton ad,rem,cf,ca,logout,proceed,back;
    Container c;
    JLabel label,limg,ladn,ldes,lorg,lima;
    ImageIcon im,ima;
    private JComboBox<String> cb;
    private List<Account<? extends Currency>> choices;

    ChooseAccountPage(){

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
        label = new JLabel("Choose account to view details : ");
        label.setBounds(600,90,200,50);
        im = new ImageIcon(getClass().getResource("Logo.jpg"));
        limg = new JLabel(im);
        limg.setBounds(1020,10,300,100);
        c.add(limg);

        try {
            ATM obj = new ATM();
            choices = obj.getCustomerAccount();
            List<String> str = new ArrayList<String>();


            for ( Account<? extends Currency> e: choices) {
                String type = e.getAccountType();
                String id = (e.getAccountId()).toString();
                str.add(type + id);

            }

            System.out.println("accs are :" + str);
            String[] str_new = str.toArray(new String[str.size()]);
            cb = new JComboBox<String>(str_new);
            cb.setBounds(900, 150, 200, 20);
            cb.setVisible(true);
            c.add(cb);
        }
        catch (Exception e){
            System.out.println(e);
        }
        back = new JButton("Back");
        back.setBounds(400,470,100,20);
        logout = new JButton("Logout");
        logout.setBounds(400,600,100,20);
        c.add(logout);
        logout.addActionListener(this);
        proceed = new JButton("Proceed");
        proceed.setBounds(600,470,100,20);

        c.add(back);
        c.add(proceed);
        c.add(label);


        back.addActionListener(this);
        proceed.addActionListener(this);
    }

    public void actionPerformed(ActionEvent ae){
        String s = ae.getActionCommand();
      
//         if(s.equals("Proceed")){
//            AccountInfo cad = new AccountInfo();
//            cad.setSize(400,400);
//            cad.setVisible(true);
//            cad.setTitle("Customer List");
//        }
      

        if(s.equals("Back")){
      
            ExistingCustomerPage t = new ExistingCustomerPage();
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
