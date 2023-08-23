package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JFrame;
import java.util.List;
import java.util.ArrayList;
import bank.*;
import account.*;
import currency.*;
import static Utils.Constants.*;

//CLASS FOR CREATING ACC OF EACH CUSTOMER
public class CreateAccountPage extends JFrame implements ActionListener {
    JTextField t4;
    JLabel l2,l3,l4,msg,label,sav;
    JButton back,submit;
    private JComboBox<String> cb, cb1,cc;
    Container con;

    List<Account<? extends Currency>> ch;

    public CreateAccountPage(){

        con = getContentPane();
        con.setLayout(null);
        label = new JLabel("Create New Account");
        label.setFont(new Font("Arial",Font.BOLD,20));
        label.setBounds(600,90,400,50);

        con.add(label);

        l2 = new JLabel("Enter Account Type:");
        l2.setBounds(500,150,200,20);
        l2.setVisible(true);
        con.add(l2);

        String[] choices = { SAVINGS_ACCOUNT,CHECKING_ACCOUNT,SECURITY_ACCOUNT};

        cb = new JComboBox<String>(choices);
        cb.setBounds(800,150,200,20);
        cb.setVisible(true);
        con.add(cb);

        //q2
        l3 = new JLabel("Choose Base Currency:");
        l3.setBounds(500,170,200,20);
        l3.setVisible(true);
        con.add(l3);

        String[] currs = { "USD","INR","CNY"};

        cb1 = new JComboBox<String>(currs);
        cb1.setBounds(800,170,200,20);
        cb1.setVisible(true);
        con.add(cb1);

        l4 = new JLabel("Initial Deposit Amount:");
        l4.setBounds(500,190,200,20);
        l4.setVisible(true);
        con.add(l4);

        t4 = new JTextField(10);
        t4.setBounds(800,190,200,20);
        t4.setVisible(true);
        con.add(t4);

        sav = new JLabel("Choose saving account");
        sav.setBounds(500,250,200,20);
        sav.setVisible(false);
        con.add(sav);


        cc = new JComboBox();
        cc.setBounds(800, 250, 200, 20);
        cc.setVisible(false);
        cc.addActionListener(this);
        con.add(cc);
        //button
        back = new JButton("Back");
        back.setBounds(600,350,100,20);
        back.setVisible(true);
        con.add(back);

        submit = new JButton("Create");
        submit.setBounds(800,350,100,20);
        submit.setVisible(true);
        con.add(submit);

        msg = new JLabel();
        msg.setBounds(700,450,500,20);
        msg.setVisible(false);
        con.add(msg);

        back.addActionListener(this);
        submit.addActionListener(this);
        // t4.addActionListener(this);
        cb.addActionListener(this);
        // msg.addActionListener(this);
        cb1.addActionListener(this);
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
        String acctype = cb.getSelectedItem().toString();
        String curr = cb1.getSelectedItem().toString();
       if(acctype.equals(SECURITY_ACCOUNT)) {
           sav.setVisible(true);
           cc.setVisible(true);
           try {
               ATM atm = new ATM();
               String[] str_new = {};
               ch = atm.getCustomerAccount();
               List<String> str = new ArrayList<String>();

               for (Account<? extends Currency> e : ch) {
                   String type = e.getAccountType();
                   if (type.equals(SAVINGS_ACCOUNT)) {
                       String id = (e.getAccountId()).toString();
                       str.add(id);
                   }
               }

               System.out.println("saving accs are :" + str);
               str_new = str.toArray(new String[str.size()]);

               DefaultComboBoxModel model = (DefaultComboBoxModel) cc.getModel();
               // removing old data
               model.removeAllElements();

               for (String item : str_new) {
                   model.addElement(item);
               }

               // setting model with new data
               cc.setModel(model);

           } catch (Exception e) {
               System.out.println(e);
           }}

           String val = t4.getText();
           System.out.println(acctype + "," + curr + "," + s + "," + val);


        float dep =0;
        if(val.equals(null))
            System.out.println("Empty");
        else{
            dep = Float.parseFloat(val);
        }

        if(s.equals("Create") && !acctype.equals(SECURITY_ACCOUNT)){

            try {

                ATM atm = new ATM();
                System.out.println("opening acc");
                String res = atm.openAccount(acctype, dep, curr);
                System.out.println("Res is" +res);
                msg.setText(res);
                msg.setVisible(true);

            }catch (Exception e){
                System.out.println(e);
            }
        } else if (s.equals("Create") && acctype.equals(SECURITY_ACCOUNT)) {

            try {
                ATM atm = new ATM();

                String savs = cc.getSelectedItem().toString();
                for ( Account<? extends Currency> e: ch) {
                    String type = e.getAccountType();
                    String accid = (e.getAccountId()).toString();
                    if(type.equals(SAVINGS_ACCOUNT) && savs.equals(accid)) {
                        String res = atm.openSecurityAccount((SavingAccount<? extends Currency>) e,dep);
                        System.out.println("Res : "+res);
                        msg.setText(res);
                        msg.setVisible(true);
                    }
                }
//
            }catch (Exception e){
                System.out.println(e);
            }
        }



    }

}