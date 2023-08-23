package StockMarket;

import Exceptions.FancyBankException;
import currency.Currency;
import loan.Collateral;
import loan.Loan;

import java.util.ArrayList;
import java.util.List;

/*
Class to represent a stock in the stock market
 */
public class Stock implements Buyable,Sellable{

    private int id;
    private String name;
    private Price price;
    private int quantity;

    private List<Double> priceHistory;  //maintain a history of the prices the stock has

    public Stock() throws FancyBankException {
        new Stock("",new Price(0.0),0);
    }

    public Stock(String name,Price price, int quantity) throws FancyBankException {
        setName(name);
        setPrice(price);
        setQuantity(quantity);
        priceHistory = new ArrayList<>();
    }
    public Stock(int id, String name,Price price, int quantity) throws FancyBankException {
        setId(id);
        setName(name);
        setPrice(price);
        setQuantity(quantity);
        priceHistory = new ArrayList<>();
    }

    public Stock(int id, String name,Price price, int quantity,List<Double> priceHistory) throws FancyBankException {
        setId(id);
        setName(name);
        setPrice(price);
        setQuantity(quantity);
        setPriceHistory(priceHistory);
    }

    public Stock(int id, String name,Price p, int quantity,String priceHistory) throws FancyBankException {
        setId(id);
        setName(name);
        setPrice(p);
        setQuantity(quantity);
        if(priceHistory!=null && !priceHistory.equals("[]"))
        setPriceHistory(parsePriceHistory(priceHistory));
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
       this.quantity = quantity;
    }

    public void addQuantity(int quantity) throws FancyBankException {
        if(this.quantity + quantity<0){
            throw new FancyBankException("Quantity cannot be less than 0");
        }
        this.quantity = this.quantity + quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Double> getPriceHistory() {
        if(priceHistory==null){
            return new ArrayList<>();
        }
        return priceHistory;
    }

    public void setPriceHistory(List<Double> priceHistory) {
        this.priceHistory = priceHistory;
    }

    @Override
    public String toString() {
        return String.join("@",Integer.toString(id),name,Double.toString(price.getBuyPrice()),Double.toString(price.getCurrentPrice()),Integer.toString(quantity),getListAsString(priceHistory));
    }

    private String getListAsString(List li){
        StringBuilder str = new StringBuilder();
        li.forEach(t->{
            str.append(t.toString()).append("^");
        });
        if(!str.toString().equals("")) {
            return str.substring(0, str.lastIndexOf("^"));
        }
        return str.toString();
    }

    private List<Double> parsePriceHistory(String prices){  //parse string to price history list
        List<Double> st = new ArrayList<>();
        if(prices==null || prices.equals("")){
            return st;
        }
        for(String tsplit:prices.split("^")){
            st.add(Double.parseDouble(tsplit));
        }
        return st;
    }

    public void buy(){
    }
    public void sell(){

    }
}
