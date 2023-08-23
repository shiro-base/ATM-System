package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JFrame;

import bank.*;
import account.*;
import currency.*;
import Database.*;
import Exceptions.*;
import Main.*;
import StockMarket.*;

//CLASS FOR CLOSING ACCOUNT OF EACH CUSTOMER
public class CloseAccountPage  extends JFrame implements ActionListener{

        private Container c;
        private JLabel name,amt,lt1,h1,h2,h3,name2, lima,label;
        private JLabel pass,msg,lt2,ls,lt,la,li, pass2,curr,acc;
        private JTextField tacc,tcurr,tamt,tmsg;
        private JComboBox<String> cb;
        private JLabel err;
        private ImageIcon logo, ima;
        private JButton submit,back,logout;
        private List<Account<? extends Currency>> choices;
        
        CloseAccountPage(){
    
            c = getContentPane();
            c.setLayout(null);
    
            ima = new ImageIcon(getClass().getResource("dp.png"));
            lima = new JLabel(ima);
            lima.setBounds(30,10,100,100);
            c.add(lima);
    
            label = new JLabel("Closing Account");
            label.setFont(new Font("Arial",Font.BOLD,20));
            label.setBounds(600,90,400,50);
    
            c.add(label);
    
            logo = new ImageIcon(getClass().getResource("Logo.jpg"));
            li = new JLabel(logo);
            li.setBounds(1250,20,100,100);
    
            c.add(li);

            err = new JLabel();
            err.setFont(new Font("Arial", Font.PLAIN, 15));
            err.setSize(100, 20);
            err.setLocation(800, 600);
            err.setVisible(false);
            c.add(err);
        
            lt1 = new JLabel("Choose Account");
            lt1.setFont(new Font("Arial",Font.PLAIN,15));
            lt1.setBounds(600,150,200,20);
            lt1.setVisible(true);
            c.add(lt1);

            try {
                ATM obj = new ATM();
                choices = obj.getCustomerAccount();
                if(choices.isEmpty()){
                    err.setText("No Account available");
                }else {
                    List<String> str = new ArrayList<String>();

                    for (Account<? extends Currency> e : choices) {
                        String type = e.getAccountType();
                        String id = (e.getAccountId()).toString();
                        str.add(type + "-" + id);

                    }

                    System.out.println(str);
                    String[] str_new = str.toArray(new String[str.size()]);
                    cb = new JComboBox<String>(str_new);
                    cb.setBounds(900, 150, 200, 20);
                    cb.setVisible(true);
                    c.add(cb);
                    cb.addActionListener(this);
                }

            }catch(Exception e){
                System.out.println(e);
            }
    
    
            back = new JButton("Back");
            back.setBounds(600,550,100,20);
            back.setVisible(true);
            c.add(back);

            logout = new JButton("Logout");
            logout.setBounds(600,600,100,20);
            c.add(logout);
            logout.addActionListener(this);
    
            submit = new JButton("Close Account");
            submit.setBounds(800,550,200,20);
            submit.setVisible(true);
            c.add(submit);


            back.addActionListener(this);
            submit.addActionListener(this);
    
        }
    
        
        public void actionPerformed(ActionEvent ae){
    

            String s = ae.getActionCommand();
            if(s.equals("Back")){

                this.dispose();

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
            String myacc = cb.getSelectedItem().toString();


            if(s.equals("Close Account")){
    
            // ADD CODE USING ATM VALIDATION
            try {
                ATM atm = new ATM();
                int dashInd = myacc.indexOf('-');
                String accSel = myacc.substring(dashInd+1);

                for ( Account<? extends Currency> e: choices) {
                    if(String.valueOf(e.getAccountId()).equals(accSel)){
                        String checkedResult = atm.closeAccount(e);
                        err.setText(checkedResult);
                        err.setVisible(true);
                        tacc.setText("");
                        tcurr.setText("");
                        tamt.setText("");
                        tmsg.setText("");
                    }
                }

            }catch (Exception e){
                System.out.println(e);
            }
            }
    

        }


    
    
    }
    
