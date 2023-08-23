package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
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
//CLASS FOR DISPLAYING STOCK MARKET
public class StockMarketPage extends JFrame {

    StockMarketPage() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Stock Market");

        //Create and set up the ContentPane

        frame.setContentPane(createContentPane());

        frame.pack();
        frame.setVisible(true);
    }

    public Container createContentPane() {
        List<Stock> trans = null;
        try {

            ATM atm = new ATM();

            trans = atm.getAllStocks();

        } catch (Exception e) {
            e.printStackTrace();
        }

        String col[] = {"Stock Name", "Stock Quantity", "Stock Price"};
        Object[][] data_arr = new Object[trans.size()][col.length];

        for (int i = 0; i < trans.size(); i++) {
            String position = trans.get(i).getName();
            String name = String.valueOf(trans.get(i).getQuantity());
            String points = String.valueOf(trans.get(i).getPrice().getCurrentPrice());

            Object[] data = {position, name, points};

            data_arr[i] = data;
        }
        System.out.println(data_arr[0][0]);
        // Table instantiated using the two sets of data.
        JTable table = new JTable(data_arr, col);

        // The table displayed in a Scrollpane.
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(500, 500));

        JPanel totalGUI = new JPanel();
        totalGUI.add(scrollPane);
        totalGUI.setOpaque(true);
        return totalGUI;
    }

}
