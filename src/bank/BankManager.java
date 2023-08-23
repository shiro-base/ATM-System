/*
    concrete subclass of BankEmployeeAbstract, enable perform manager actions mentioned in the file
 */
package bank;

import Database.CustomerDatabase;
import Exceptions.FancyBankException;
import Main.Customer;
import Main.DateSystem;
import Main.Login;
import Main.Transaction;
import StockMarket.Price;
import StockMarket.Stock;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class BankManager extends BankEmployeeAbstract implements StockManagement {

    public BankManager(String name, Login login,Bank bank){
        super(name, login);
        setBankInCharge(bank);
        setBankEmployeeType(BankEmployeeType.MANAGER);
    }


    public void addStock(String name, Double price, int quantity) throws FancyBankException {
        for(Stock s : getBankInCharge().getStockMarket().view()) {
            if (s.getName().equals(name)){
                System.out.println("Stock already exists");
            }
            return;
        }
        Stock stock = new Stock(name, new Price(price), quantity);
        try {
            getBankInCharge().getStockMarket().addStock(stock);
        }
        catch (SQLException e){
            System.out.println("SQL execption encountered");
        }
    }



    public void removeStock(Stock stock, int quantity) throws FancyBankException {
        try {
            getBankInCharge().getStockMarket().removeStock(stock, quantity);
        }
        catch (SQLException e){
            System.out.println("SQL exception encountered");
        }
    }



    public void changeStockPrice(Stock stock, double price) throws FancyBankException {
        try {
            getBankInCharge().getStockMarket().updateStockPrice(stock, price);
        }
        catch (SQLException e){
            System.out.println("SQL exception encountered");
        }
    }

    // need modification
    public Customer viewCustomer(String customerUserName) throws SQLException {
        Customer customer = CustomerDatabase.getCustomer(customerUserName);
        return customer;
    }

    public List<Transaction> getDailyReport(){
        // transaction list of the day
        ArrayList<Transaction> transactionsOfTheDay = new ArrayList<Transaction>();
        for (Customer c : getBankInCharge().getCustomerList()){
            for(Transaction t : c.getTransactionHistory()) {
                if(DateSystem.dateDifference(t.getDate()) == 0) {
                    transactionsOfTheDay.add(t);
                }
            }
        }
        return transactionsOfTheDay;
    }

    public void increaseDate(int dayToIncrement) throws SQLException {
        DateSystem.incrementDays(dayToIncrement);
    }
}
