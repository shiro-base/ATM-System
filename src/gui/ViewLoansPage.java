package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import javax.swing.JTable;
import javax.swing.JFrame;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.*;

import bank.*;
import account.*;
import currency.*;
import Database.*;
import Exceptions.*;
import loan.*;
import Main.*;
import StockMarket.*;
//CLASS FOR DISPLAYING ALL LOANS OF THE CUSTOMER
public class ViewLoansPage extends JFrame{

    ViewLoansPage() {

        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("My Loans");

        //Create and set up the ContentPane

        frame.setContentPane(createContentPane());

        frame.pack();

        frame.setVisible(true);

    }

    public Container createContentPane()
    {
        List<Loan<? extends Currency>> trans=null;
        try {

            ATM atm = new ATM();

             trans=atm.getLoansByCustomer();

            } catch (Exception e) {

                 e.printStackTrace();
        }

        String col[] = {"Total Loan Amount", "Loan Interest", "Loan Due Date"};
        Object[][] data_arr = new Object[trans.size()][col.length];
        if(!(trans==null)) {

            for (int i = 0; i < trans.size(); i++) {

                String position = String.valueOf(trans.get(i).getTotalAmount());
                String name = String.valueOf(trans.get(i).getInterest());

                String points = trans.get(i).getDueDate();

                Object[] data = {position, name, points};

                data_arr[i] = data;

            }
        }else {
            JPanel pa = new JPanel();
            pa.setSize(500, 500);
            pa.setOpaque(true);
            return pa;
        }
            // Table instantiated using the two sets of data.
            JTable table = new JTable(data_arr, col);

            JScrollPane scrollPane = new JScrollPane(table);

            scrollPane.setPreferredSize(new Dimension(500, 100));


            JPanel totalGUI = new JPanel();

            totalGUI.add(scrollPane);

            totalGUI.setVisible(true);

            return totalGUI;

    }

}

