/*
    concrete bank class, specific functions that our specific bank is able to realize
 */
package bank;

import java.sql.SQLException;
import java.util.List;

import Database.BankReservesDatabase;
import Database.CustomerDatabase;
import Database.StocksDatabase;
import Exceptions.FancyBankException;
import Main.Customer;
import Main.Person;
import StockMarket.StockMarket;
import currency.USD;

public class Bank extends BankAbstract{
    private String bankName;
    private BankReserved totalReservedMoney;
    private List<Customer> customerList;
    private StockMarket stockMarket;
    private boolean isBankrupt;



    public Bank(String name) throws SQLException {
        bankName = name;
        totalReservedMoney = new BankReserved(BankReservesDatabase.getBankMoney());
        isBankrupt = false;
        customerList = CustomerDatabase.getAllCustomers();
    }

    public BankReserved getTotalReservedMoney() throws SQLException {
        return new BankReserved(BankReservesDatabase.getBankMoney());
    }


    public String getBankName() {
        return bankName;
    }

     public StockMarket getStockMarket() {
         return stockMarket;
     }

    public void setBankrupt(boolean bankrupt){
        isBankrupt = bankrupt;
    }

    public List<Customer> getCustomerList() {
        return CustomerDatabase.getAllCustomers();
    }

    public boolean isBankrupt() {
        return isBankrupt;
    }
}
