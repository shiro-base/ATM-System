/*
This class represent CNY. It inherits Currency class
 */

package currency;

public class CNY extends Currency{

    public CNY(double amount) {
        super(amount);
    }

    @Override
    public <T extends Currency> double addAmount(T income) {
        double amount = income.convertToCNY().getAmount();
        setAmount(getAmount()+amount);
        return getAmount()+amount;
    }

    @Override
    public <T extends Currency> T deductAmount(double amount) {
         setAmount(getAmount()-amount);
        return (T) new CNY(amount);
    }

    public USD convertToUSD()
    {
        double amount = CurrencyExchange.convertCNYtoUSD(getAmount());
        return new USD(amount);
    }

    public INR convertToINR()
    {
        double amount = CurrencyExchange.convertCNYtoINR(getAmount());
        return new INR(amount);
    }

    public CNY convertToCNY()
    {
        return this;
    }



}
