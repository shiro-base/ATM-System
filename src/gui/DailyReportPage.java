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
public class DailyReportPage extends JFrame{


    DailyReportPage() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Daily Report");

        //Create and set up the ContentPane

        frame.setContentPane(createContentPane());

        frame.pack();
        frame.setVisible(true);

    }

    public Container createContentPane()
    {
        List<Transaction> trans = null;
        try {

            ATM atm = new ATM();

            trans = atm.showDailyReport();

        }catch (Exception e) {
            e.printStackTrace();
        }

        String col[] = {"Date", "TimeStamp", "From", "To", "Amount Sent", "Amount Received", "Currency Sent", "Currency Received"};

        Object[][] data_arr = new Object[trans.size()][col.length];

        for (int i = 0; i < trans.size(); i++) {

            String position = trans.get(i).getDate();
            String name = trans.get(i).getTimeStamp();
            String points = trans.get(i).getFrom();
            String wins = trans.get(i).getTo();
            double defeats = trans.get(i).getSendingAmount();
            double draws = trans.get(i).getReceivingAmount();
            String totalMatches = trans.get(i).getSendingCurrency();
            String goalF = trans.get(i).getReceivingCurrency();

            Object[] data = {position, name, points, wins, defeats, draws, totalMatches, goalF};
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
