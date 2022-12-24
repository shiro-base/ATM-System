/*
    An interface which will be implemented by the bank manager to change stocks
 */

package bank;

import Exceptions.FancyBankException;
import StockMarket.Price;
import StockMarket.Stock;

public interface StockManagement {

    void addStock(String name, Double price, int quantity) throws FancyBankException;

    void removeStock(Stock stock, int quantity) throws FancyBankException;

    void changeStockPrice(Stock stock, double price) throws FancyBankException;
}
