/*
This class is a security account. It inherits the general Account class.
It contains all methods needed to trade stocks.
 */

package account;

import Exceptions.FancyBankException;
import StockMarket.Price;
import StockMarket.Stock;
import currency.Currency;
import static Utils.Constants.SECURITY_ACCOUNT;

import java.util.ArrayList;
import java.util.List;

public class SecurityAccount<T extends Currency> extends Account<T>
{
    List<Stock> stocks;
    double realizedProfit;
    double unrealizedProfit;


    private SecurityAccount(){
        super();
        stocks=new ArrayList<>();

    }
    public SecurityAccount(T deposit) {
        super(deposit, SECURITY_ACCOUNT);
        stocks=new ArrayList<>();
    }

    public SecurityAccount(T deposit,String curr,String id,String stocks,double realizedProfit) throws FancyBankException {
        super(deposit,curr,SECURITY_ACCOUNT,id);
        this.stocks = parsestocks(stocks);
        this.realizedProfit=realizedProfit;
    }

    private List<Stock> parsestocks(String sts) throws FancyBankException {
        List<Stock> stocksList = new ArrayList<Stock>();
        if(sts==null || sts.equals("")){
            return stocksList;
        }
        String[] t= sts.split("!");
        for(String tsplit: t){
            String[] values = tsplit.split("@");
            if(values.length>=6) {
                stocksList.add(new Stock(Integer.parseInt(values[0]), values[1], new Price(Double.parseDouble(values[2]), Double.parseDouble(values[3])), Integer.parseInt(values[4]), values[5]));
            }
            else{
                stocksList.add(new Stock(Integer.parseInt(values[0]), values[1], new Price(Double.parseDouble(values[2]), Double.parseDouble(values[3])), Integer.parseInt(values[4]), ""));

            }
        }
        return stocksList;
    }



    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }

    public void addStock(Stock s){
        stocks.add(s);
    }

    public void removeStock(Stock s,int quantity){
        Stock st = stocks.stream().filter(stock->stock.getId()==s.getId()).findFirst().get();
        st.setQuantity(st.getQuantity()-quantity);
        if(st.getQuantity()==0) {
            stocks.remove(s);
        }
    }

    public void modifyPrice(Stock s,double price) throws FancyBankException {
        if(stocks.contains(s)){
            Stock stock = stocks.stream().filter(st->st.getId()==s.getId()).findFirst().get();
            if(stock.getPrice().getCurrentPrice()!=price) {
                stock.getPriceHistory().add(stock.getPrice().getCurrentPrice());
                stock.getPrice().setCurrentPrice(price);
            }
        }
    }

    public boolean containsStock(Stock s,int q){
        return stocks.stream().anyMatch(st->st.getId()==(s.getId())) && stocks.stream().filter(st->st.getId()==s.getId()).findFirst().get().getQuantity()>=q;
    }

    @Override
    public String toString(){
        return String.join(";",super.toString(),getListAsString(stocks),Double.toString(realizedProfit));
    }

    private String getListAsString(List li){
        StringBuilder str = new StringBuilder();
        li.forEach(t->{
            str.append(t.toString()).append("!");
        });
        if(!str.toString().equals("")) {
            return str.substring(0, str.lastIndexOf("!"));
        }
        return str.toString();
    }

    public double getRealizedProfit() {
        return realizedProfit;
    }

    public void setRealizedProfit(double realizedProfit) {
        this.realizedProfit = realizedProfit;
    }

    public void addRealizedProfit(double realizedProfit) {
        this.realizedProfit = this.realizedProfit +realizedProfit;
    }

    private void setUnrealizedProfit(double unrealizedProfit) {
        this.unrealizedProfit = unrealizedProfit;
    }

    public double getUnRealizedProfit(){
        double amt = 0;
        for(Stock s:stocks){
            amt = amt + getUnRealizedProfit(s);
        };
        setUnrealizedProfit(amt);
        return amt;
    }

    public double getUnRealizedProfit(Stock s){
        return s.getPrice().getCurrentPrice()-s.getPrice().getBuyPrice();
    }


}
