package StockMarket;

import Exceptions.FancyBankException;

import java.sql.SQLException;

/*
Interface to maintain functions a stock market must implement
 */
public interface StockMarketInterface {

    public void addStock(Stock stock) throws FancyBankException, SQLException;

    public String removeStock(Stock stock,int qty) throws FancyBankException, SQLException;

    public boolean checkStockInventory(Stock stock, int quantity);
}
