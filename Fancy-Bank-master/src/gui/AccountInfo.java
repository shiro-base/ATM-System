package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import javax.swing.JFrame;
import javax.swing.table.*;
import bank.*;
import account.*;
import currency.*;
import Database.*;
import Exceptions.*;
import Main.*;
import StockMarket.*;
import static Utils.Constants.*;

//CLASS FOR DISPLAYING ACCOUNT INFO OF EACH CUSTOMER
public class AccountInfo extends JFrame implements ActionListener {
        private Container c;
        private JLabel name,ltt1,amt,lt1,h1,h2,h3,name2, lima,label;
        private JLabel pass,msg1,msg,lt2,ls,lt,la,li, pass2,curr,acc;
        private JTextField tacc,tcurr,tamt,tmsg;
        private JComboBox<String> cb;
        private JLabel err,cc;
        private ImageIcon logo, ima;
        private JButton logout,submit,back,trn;
        private java.util.List<Account<? extends Currency>> choices;

        AccountInfo(){

            c = getContentPane();
            c.setLayout(null);

            ima = new ImageIcon(getClass().getResource("dp.png"));
            lima = new JLabel(ima);
            lima.setBounds(30,10,100,100);
            c.add(lima);

            label = new JLabel("Account Information :");
            label.setFont(new Font("Arial",Font.BOLD,20));
            label.setBounds(600,90,400,50);

            c.add(label);

            logo = new ImageIcon(getClass().getResource("Logo.jpg"));
            li = new JLabel(logo);
            li.setBounds(1250,20,100,100);

            c.add(li);

            err = new JLabel();
            err.setFont(new Font("Arial", Font.PLAIN, 15));
            err.setSize(300, 20);
            err.setLocation(800, 600);
            err.setVisible(false);
            c.add(err);

            lt1 = new JLabel("Choose your Account");
            lt1.setFont(new Font("Arial",Font.PLAIN,15));
            lt1.setBounds(600,150,200,20);
            lt1.setVisible(true);
            c.add(lt1);
            try{
                ATM obj = new ATM();
                choices = obj.getCustomerAccount();
                if(choices.isEmpty()){
                    err.setText("No Account available");
                }else {
                    List<String> str = new ArrayList<String>();

                    for (Account<? extends Currency> e : choices) {
                        String type = e.getAccountType();
                        String id = String.valueOf(e.getAccountId());
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

            }catch (Exception e){
                System.out.println(e);
            }


            ltt1 = new JLabel();
            ltt1.setFont(new Font("Arial",Font.BOLD,15));
            ltt1.setBounds(600,250,200,20);
            ltt1.setVisible(false);
            c.add(ltt1);

            cc = new JLabel();
            cc.setFont(new Font("Arial",Font.BOLD,15));
            cc.setBounds(600,170,700,20);
            cc.setVisible(false);
            c.add(cc);


            amt = new JLabel();
            amt.setFont(new Font("Arial",Font.PLAIN,15));
            amt.setBounds(600,300,200,20);
            amt.setVisible(false);
            c.add(amt);


            msg = new JLabel();
            msg.setFont(new Font("Arial",Font.PLAIN,15));
            msg.setBounds(600,350,900,900);
            msg.setVisible(false);
            c.add(msg);

            msg1 = new JLabel();
            msg1.setFont(new Font("Arial",Font.PLAIN,15));
            msg1.setBounds(600,380,900,900);
            msg1.setVisible(false);
            c.add(msg1);

            trn = new JButton("View Transactions");
            trn.setBounds(600,450,200,20);
            trn.setVisible(true);
            c.add(trn);
            trn.addActionListener(this);

            back = new JButton("Back");
            back.setBounds(600,550,100,20);
            back.setVisible(true);
            c.add(back);

            logout = new JButton("Logout");
            logout.setBounds(400,800,100,20);
            c.add(logout);
            logout.addActionListener(this);


            back.addActionListener(this);

        }


        public void actionPerformed(ActionEvent ae){
            String[] accs = { SAVINGS_ACCOUNT,CHECKING_ACCOUNT,SECURITY_ACCOUNT};
            String s = ae.getActionCommand();
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

            String myacc = cb.getSelectedItem().toString();
            String acc_curr="";
            String balance="";
            List<Transaction> trans;
            int dashInde = myacc.indexOf('-');
            String accSele = myacc.substring(dashInde+1);
            UUID acc = UUID.fromString(accSele);

            for ( Account<? extends Currency> e: choices) {
                if (accSele.equals(String.valueOf(e.getAccountId()))) {
                    acc_curr = e.getAccountCurrency();
                    ltt1.setText("Account currency is: " + acc_curr);
                    ltt1.setVisible(true);
                    balance = String.valueOf(e.getDeposit().getAmount());
                    amt.setText("Current balance is: " + balance);
                    amt.setVisible(true);
                    String idd = String.valueOf(e.getAccountId());
                    cc.setText("Account ID is:"+idd);
                    cc.setVisible(true);

                    if(e.getAccountType().equals(SECURITY_ACCOUNT)){
                        try{
                            ATM atm = new ATM();
                            msg1.setText("Realized profit is :"+String.valueOf(atm.getTotalRealizedProfit()));
                        }catch (Exception exc){
                            err.setText("No account found");
                            err.setVisible(true);
                        }

                    }

                }
            }

            if(s.equals("View Transactions")){

                AccountTransactions ac = new AccountTransactions(acc);

            }


        }


    }

