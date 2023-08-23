package gui;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import bank.*;

//CLASS FOR ADDING STOCK BY MANAGER
public class AddStockPage extends JFrame implements ActionListener{
        private Container c;
        private JLabel name,amt,lt1,h1,h2,h3,name2, lima,label;
        private JLabel pass,msg,lt2,ls,lt,la,li, pass2,curr,acc,msg1;
        private JTextField tacc,tcurr,tamt,tmsg,tmsg1,cb;
        private JLabel err;
        private ImageIcon logo, ima;
        private JButton submit,back,logout;
    
        AddStockPage(){
    
            c = getContentPane();
            c.setLayout(null);
    
            ima = new ImageIcon(getClass().getResource("dp.png"));
            lima = new JLabel(ima);
            lima.setBounds(30,10,100,100);
            c.add(lima);
    
            label = new JLabel("Enter Stock details");
            label.setFont(new Font("Arial",Font.BOLD,20));
            label.setBounds(600,90,400,50);
    
            c.add(label);
    
            logo = new ImageIcon(getClass().getResource("Logo.jpg"));
            li = new JLabel(logo);
            li.setBounds(1250,20,100,100);
    
            c.add(li);
        
            lt1 = new JLabel("Enter Stock Name");
            lt1.setFont(new Font("Arial",Font.PLAIN,15));
            lt1.setBounds(600,150,200,20);
            lt1.setVisible(true);
            c.add(lt1);
    
            cb = new JTextField();
            cb.setFont(new Font("Arial",Font.PLAIN,15));
            cb.setBounds(900,150,200,20);
            cb.setVisible(true);
            c.add(cb);
    
            acc = new JLabel("Enter Stock Quantity");
            acc.setFont(new Font("Arial",Font.PLAIN,15));
            acc.setBounds(600,200,200,20);
            acc.setVisible(true);
            c.add(acc);
    
            tacc = new JTextField();
            tacc.setFont(new Font("Arial",Font.PLAIN,15));
            tacc.setBounds(900,200,200,20);
            tacc.setVisible(true);
            c.add(tacc);
    
            curr = new JLabel("Enter Stock Price (in USD)");
            curr.setFont(new Font("Arial",Font.PLAIN,15));
            curr.setBounds(600,250,200,20);
            curr.setVisible(true);
            
            c.add(curr);
    
            tcurr = new JTextField(7);
            tcurr.setFont(new Font("Arial",Font.PLAIN,15));
            tcurr.setBounds(900,250,200,20);
            tcurr.setVisible(true);
            
            c.add(tcurr);
    
            back = new JButton("Back");
            back.setBounds(600,550,100,20);
            back.setVisible(true);
            c.add(back);
            logout = new JButton("Logout");
            logout.setBounds(600,800,100,20);
            c.add(logout);
            logout.addActionListener(this);
    
            submit = new JButton("Add Stock");
            submit.setBounds(800,550,100,20);
            submit.setVisible(true);
            c.add(submit);
    
            err = new JLabel("Error Occurred!");
            err.setFont(new Font("Arial", Font.PLAIN, 15));
            err.setSize(300, 20);
            err.setLocation(800, 600);
            err.setVisible(false);
            c.add(err);
    
            tacc.addActionListener(this);
            tcurr.addActionListener(this);
            
            cb.addActionListener(this);
            back.addActionListener(this);
            submit.addActionListener(this);
    
        }
    
        
        public void actionPerformed(ActionEvent ae){

            String s = ae.getActionCommand();
            if(s.equals("Back")){

                this.dispose();

                ManagerStockPage t = new ManagerStockPage();
                t.setSize(4960,7016);
                t.setVisible(true);
                t.setLayout(null);

            }

            String myacc = cb.getText();
    

            double price = Double.parseDouble(tcurr.getText());
            int tacc1 = Integer.parseInt(tacc.getText());

        if(s.equals("Add Stock")){
    
            // ADD CODE USING ATM VALIDATION
            try {
                ATM atm = new ATM();

                atm.addStock(myacc, price,tacc1);
                err.setText("Stock added Successfully!");
                err.setVisible(true);
                tacc.setText("");
                tcurr.setText("");
                tamt.setText("");
                tmsg.setText("");

            }catch (Exception e){
                System.out.println(e);
            }
    
                } 
    


            if(s.equals("Logout")){
                LoginPage t = new LoginPage();
                t.setSize(4096,7016);
                t.setVisible(true);
                t.setTitle("FANCY BANK - INDEX");
            }
        }
    }
    
    