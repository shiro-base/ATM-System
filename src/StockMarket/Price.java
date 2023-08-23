package StockMarket;

import Exceptions.FancyBankException;

/*
Class to maintain the buying price and current price
 */
public class Price {

    private double buyPrice;

    private double currentPrice;

    private Price() throws FancyBankException {
        new Price(0.0,0.0);
    }

    public Price(double buyPrice, double currentPrice) throws FancyBankException {
        setBuyPrice(buyPrice);
        setCurrentPrice(currentPrice);
    }

    public Price(double currentPrice) throws FancyBankException {
        setBuyPrice(0.0);
        setCurrentPrice(currentPrice);
    }

    public Price(String price) throws FancyBankException {
        Price p=stringToPrice(price);
        setBuyPrice(p.getBuyPrice());
        setCurrentPrice(p.getCurrentPrice());
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) throws FancyBankException {
        if(buyPrice<0){
            throw new FancyBankException("buying price cannot be less than 0");
        }
        this.buyPrice = buyPrice;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) throws FancyBankException {
        if(currentPrice<0){
            throw new FancyBankException("current price cannot be less than 0");
        }
        this.currentPrice = currentPrice;
    }

    @Override
    public String toString() {
        return String.join(";",Double.toString(buyPrice),Double.toString(currentPrice));
    }

    public static Price stringToPrice(String priceString) throws FancyBankException {
        String[] prices = priceString.split(";");
        return new Price(Double.parseDouble(prices[0]),Double.parseDouble(prices[1]));

    }
}
