package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import javax.swing.JTable;
import javax.swing.JFrame;
import java.util.List;

import bank.*;
import account.*;
import currency.*;
import Database.*;
import Exceptions.*;
import Main.*;
import StockMarket.*;
import javax.swing.table.*;

//CLASS FOR DISPLAYING DISPLAYING TRANSACTION HISTORY OF EACH CUSTOMER
public class CustomerListPage extends JFrame{


    CustomerListPage() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Customer List");

        //Create and set up the ContentPane

        frame.setContentPane(createContentPane());

        frame.pack();
        frame.setVisible(true);

    }

    public Container createContentPane()
    {
        List<Customer> trans = null;
        try {

            ATM atm = new ATM();

            trans = atm.getAllCustomers();

        }catch (Exception e) {
            e.printStackTrace();
        }

        String col[] = {"Name", "All Accounts", "Email", "Transactions", "Loans"};

        Object[][] data_arr = new Object[trans.size()][col.length];

        for (int i = 0; i < trans.size(); i++) {

            String position = trans.get(i).getName();
            String name = String.valueOf(trans.get(i).getAccounts());
            String points = trans.get(i).getEmail();
            String wins = String.valueOf(trans.get(i).getTransactionHistory());
            String defeats = String.valueOf(trans.get(i).getLoans());

            Object[] data = {position, name, points, wins, defeats};
//                System.out.println("row:" + position + name + points + wins + defeats + draws + totalMatches + goalF);

            data_arr[i] = data;

        }
        // Table instantiated using the two sets of data.
        JTable table = new JTable(data_arr, col);

        // The table displayed in a Scrollpane.
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(1500, 1000));

        JPanel totalGUI = new JPanel();
        totalGUI.add(scrollPane);
        totalGUI.setOpaque(true);
        return totalGUI;
    }


}
