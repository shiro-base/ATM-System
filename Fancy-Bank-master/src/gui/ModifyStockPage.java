package gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import bank.*;
import account.*;
import currency.*;
import Database.*;
import Exceptions.*;
import Main.*;
import StockMarket.*;
//CLASS FOR MANAGER TO MODIFY STOCKS
public class ModifyStockPage extends JFrame implements ActionListener{

    private Container c;
    private JLabel name,amt,lt1,h1,h2,h3,name2, lima,label;
    private JLabel pass,msg,lt2,ls,lt,la,li,lt4, pass2,curr,acc,msg1;
    private JTextField tacc,tcurr,tamt,tmsg,tmsg1;
    private JComboBox<String> cb;
    private JLabel err;
    private ImageIcon logo, ima;
    private JButton submit,logout,back;
    private List<Stock> choices;

    ModifyStockPage(){

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

        lt1 = new JLabel("Stock Market");
        lt1.setFont(new Font("Arial",Font.PLAIN,15));
        lt1.setBounds(600,150,200,20);
        lt1.setVisible(true);
        c.add(lt1);

        String col[] = {"Stock Name","Stock Price","Stock Quantity"};
        DefaultTableModel tableModel = new DefaultTableModel(col, 0);

        JTable table = new JTable(tableModel);
        try {
            ATM atm = new ATM();
            List<Stock> trans = atm.getAllStocks();

            for (int i = 0; i < trans.size(); i++) {

                String name = trans.get(i).getName();
                double price = trans.get(i).getPrice().getCurrentPrice();
                int qtt = trans.get(i).getQuantity();

                Object[] data = {name, price, qtt};

                tableModel.addRow(data);

            }

            c.add(table);
        }catch (Exception e){
            System.out.println(e);
        }

        lt4 = new JLabel("Choose Stock to Modify");
        lt4.setFont(new Font("Arial",Font.PLAIN,15));
        lt4.setBounds(600,150,200,20);
        lt4.setVisible(true);
        c.add(lt4);

        try {
            ATM obj = new ATM();
            choices = obj.getAllStocks();
            List<String> str = new ArrayList<String>();

            for (Stock e : choices) {
                String sname = e.getName();
                str.add(sname);
            }

            System.out.println(str);
            String[] str_new = str.toArray(new String[str.size()]);
            cb = new JComboBox<String>(str_new);
            cb.setBounds(900, 150, 200, 20);
            cb.setVisible(true);
            c.add(cb);

        }catch(Exception e){
            System.out.println(e);
        }
        
        acc = new JLabel("Modified Stock Quantity");
        acc.setFont(new Font("Arial",Font.PLAIN,15));
        acc.setBounds(600,200,200,20);
        acc.setVisible(true);
        c.add(acc);

        tacc = new JTextField();
        tacc.setFont(new Font("Arial",Font.PLAIN,15));
        tacc.setBounds(900,200,200,20);
        tacc.setVisible(true);
        c.add(tacc);

        curr = new JLabel("Modified Stock Price");
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

        submit = new JButton("Update Stock");
        submit.setBounds(800,550,150,20);
        submit.setVisible(true);
        c.add(submit);

        err = new JLabel("Error Occured!");
        err.setFont(new Font("Arial", Font.PLAIN, 15));
        err.setSize(300, 20);
        err.setLocation(800, 600);
        err.setVisible(false);
        c.add(err);
        logout = new JButton("Logout");
        logout.setBounds(400,800,100,20);
        c.add(logout);
        logout.addActionListener(this);

        tacc.addActionListener(this);
        tcurr.addActionListener(this);
        cb.addActionListener(this);
        back.addActionListener(this);
        submit.addActionListener(this);

    }

    
    public void actionPerformed(ActionEvent ae){

        String stkSel = cb.getSelectedItem().toString();

        String s = ae.getActionCommand();
        
        int qty = Integer.parseInt(tacc.getText());
        double price = Double.parseDouble(tcurr.getText());

        if(s.equals("Update Stock")){
            try {
                // ADD CODE USING ATM VALIDATION
                ATM atm = new ATM();

                for (Stock e : choices) {
                    if(String.valueOf(e.getName()).equals(stkSel)){
                        atm.setStockPrice(e,price);
                        atm.removeStock(e,qty);
                        err.setText("Stock updated Successfully!");
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

       if(s.equals("Back")){
            
            this.dispose();

            ManagerStockPage t = new ManagerStockPage();
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



    
