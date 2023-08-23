package StockMarket;

import Database.BankReservesDatabase;
import Database.StocksDatabase;
import Exceptions.FancyBankException;
import Main.Customer;
import account.SavingAccount;
import account.SecurityAccount;
import bank.Bank;
import bank.ChargeFee;
import currency.CNY;
import currency.INR;
import currency.USD;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static Utils.Constants.SUCCESS;
import static Utils.Constants.feeLevel1;

/*
Class to maintain stock market - acts like a stock market controller
 */
public class StockMarket implements StockMarketInterface{
    private static List<Stock> stockInventory;
    private Bank bank;

    private final double minRequiredSavingsBalance = 2500;

    private StockMarket(){
        stockInventory= new ArrayList<>();
    }
    public StockMarket(Bank bank) throws SQLException, FancyBankException {
        stockInventory = StocksDatabase.getAllStocks();
        this.bank = bank;
        //read database to initialize all stocks
    }

    public Stock view(Stock stock){
        if(stockInventory.contains(stock)){
            return stock;
        }
        return null;
    }

    public String updateStockPrice(Stock stock,double price) throws FancyBankException, SQLException {  //update the price of a stock
        if(stockInventory.stream().anyMatch(s->s.getId()==stock.getId())){
            Stock oldStock = stockInventory.stream().filter(s->s.getId()==stock.getId()).findFirst().get();
            Stock newStock = new Stock(oldStock.getId(), oldStock.getName(),oldStock.getPrice(),oldStock.getQuantity(),oldStock.getPriceHistory());
            newStock.getPrice().setCurrentPrice(price);
            StocksDatabase.updateStockPrice(oldStock,newStock); //update database
            return SUCCESS;
        }
        return "Stock not found";
    }

    public List<Stock> view(){
        return stockInventory;
    }

    public void addStock(Stock stock) throws FancyBankException, SQLException { //add a stock to the stock market
        if(stockInventory.stream().anyMatch(st->st.getId()==(stock.getId()))){
            Stock oldStock = stockInventory.stream().filter(st->st.getName().equals(stock.getName())).findFirst().get();
            Stock newStock = new Stock(oldStock.getId(), oldStock.getName(),new Price(oldStock.getPrice().getCurrentPrice()),oldStock.getQuantity(),oldStock.getPriceHistory());
            newStock.addQuantity(stock.getQuantity());
            StocksDatabase.modifyStockQuantity(oldStock.getId(),oldStock.getQuantity()+stock.getQuantity());//update database
        }
        else {
            stock.setPrice(new Price(getUsersAmountFromUSD(stock.getPrice().getCurrentPrice(),"USD")));
            stockInventory.add(stock);
            StocksDatabase.insert(stock);//update database
        }

    }

    public boolean buyStock(Stock stock, int quantity, Customer c) throws FancyBankException, SQLException {
        if(checkStockInventory(stock,quantity) && checkUserEligibility(stock,quantity, c)){
            //transact amount from user's security account
            transactStock(stock,quantity,true,c);
            //reduce quantity of stock
            updateStock(stock,quantity,true,c);
            return true;
        }
        return false;
    }

    public String removeStock(Stock stock,int qty) throws FancyBankException, SQLException { //remove stock from market
        if(stockInventory.stream().anyMatch(s->s.getId()==stock.getId())){
            Stock oldStock = stockInventory.stream().filter(s->s.getId()==stock.getId()).findFirst().get();
            Stock newStock = new Stock(oldStock.getId(), oldStock.getName(),oldStock.getPrice(),oldStock.getQuantity(),oldStock.getPriceHistory());
            newStock.setQuantity(Math.max(0, oldStock.getQuantity()-qty));  //don't remove entry from stock market - just set qty to 0
            StocksDatabase.modifyStock(oldStock,newStock);  //we don't remove entry from stock market since some other customers might still have this stock
            return SUCCESS;
        }
        return "Stock not found";
    }

    public boolean sellStock(Stock stock, int quantity,Customer c) throws FancyBankException, SQLException {
        if(checkUserHasStock(stock,quantity,c)){
            //transact amount from user's security account
            transactStock(stock,quantity,false,c);
            //reduce quantity of stock
            updateStock(stock,quantity,false,c);
            return true;
        }
        return false;
    }

    private boolean checkUserHasStock(Stock stock, int quantity,Customer c){
        //get user stock lists and check if user has enough stock.
        SecurityAccount securityAccount = c.hasSecurityAccount();
        if(securityAccount!=null){
            //securityAccount.get
            return securityAccount.containsStock(stock,quantity);
        }
        return false;
    }

    private void updateStock(Stock stock, int quantity, boolean buy,Customer c) throws FancyBankException, SQLException {
        if (buy) {
            stockInventory.stream().filter(s->s.getId()==stock.getId()).findFirst().get().setQuantity(stock.getQuantity() - quantity);
            //add stock to user's stock list - security account
            //if user already has stock, add quanity else add new stock to list
            double stockPrice = getUsersAmountFromUSD(stock.getPrice().getCurrentPrice(),c.hasSecurityAccount().getAccountCurrency());
            Stock newS=new Stock(stock.getId(),stock.getName(),new Price(stockPrice,stockPrice),quantity);
            c.hasSecurityAccount().addStock(newS);
            StocksDatabase.modifyStockQuantity(stock.getId(),stock.getQuantity()-quantity);
        }
        else{
            stockInventory.stream().filter(s->s.getId()==stock.getId()).findFirst().get().setQuantity(stock.getQuantity() + quantity);
                Stock newS=new Stock(stock.getId(),stock.getName(),stock.getPrice(),quantity);
                c.hasSecurityAccount().removeStock(newS,quantity);
                addStock(newS);
            }

            //remove stock from security account - (check if he sells all quantity or partial)
            //if partial - reduce the quantity

    }

    private void transactStock(Stock stock, int quantity, boolean fromUser,Customer c) throws  SQLException {
        double amount = stockInventory.stream().filter(s->s.getId()==stock.getId()).findFirst().get().getPrice().getCurrentPrice()*quantity;
        //do transaction on user's security account
        SecurityAccount securityAccount = c.hasSecurityAccount();
        if(fromUser){
            //user is buying
            c.buyStock(securityAccount,getUsersAmountFromUSD(amount,c.hasSecurityAccount().getAccountCurrency()));
            bank.getTotalReservedMoney().addTotalReservedMoney(new USD(amount));
            ChargeFee.chargeFee(feeLevel1,c,securityAccount,amount,bank);
        }
        else{
            //user is selling
            c.sellStock(securityAccount,new USD(amount));
            bank.getTotalReservedMoney().removeTotalReservedMoney(amount);
            c.hasSecurityAccount().addRealizedProfit(amount);
            double stockprice = getUsersAmountFromUSD(amount,c.hasSecurityAccount().getAccountCurrency());
            securityAccount.addRealizedProfit(stockprice-stock.getPrice().getBuyPrice());
        }
        BankReservesDatabase.changeBankReserve(bank.getTotalReservedMoney().getTotalReservedMoney());
    }

    public boolean checkUserEligibility(Stock stock, int quantity,Customer customer){
        SavingAccount savingAccount = customer.hasSavingsAccount();
        SecurityAccount securityAccount = customer.hasSecurityAccount();
        double amount = stockInventory.stream().filter(s->s.getId()==stock.getId()).findFirst().get().getPrice().getCurrentPrice()*quantity;
        return savingAccount != null && securityAccount != null &&
                savingAccount.getDeposit().convertToUSD().getAmount() > minRequiredSavingsBalance
                && securityAccount.getDeposit().convertToUSD().getAmount() >= amount;
        //check if user has sufficient funds - check security account
    }

    public boolean checkStockInventory(Stock stock, int quantity){
        return stockInventory.stream().anyMatch(s->s.getId()==stock.getId()) &&
                stockInventory.stream().filter(s->s.getId()==stock.getId()).findFirst().get().getQuantity()>=quantity;
    }

    public static List<Stock> getStockInventory() {
        return stockInventory;
    }

    public static void setStockInventory(List<Stock> stockInventory) {
        StockMarket.stockInventory = stockInventory;
    }

    @Override
    public String toString() {
        return "StockMarket{ stock inventory = }" + stockInventory;
    }

    private double getUsersAmountFromUSD(double amount, String currency){
        if(currency.equals("USD")){
            return amount;
        } else if (currency.equals("INR")) {
            INR inr = new INR(amount);
            return inr.convertToUSD().getAmount();
        }
        else{
            CNY cny = new CNY(amount);
            return cny.convertToUSD().getAmount();
        }
    }


}
