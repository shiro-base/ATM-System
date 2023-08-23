/*
This class represent CNY. It inherits Currency class
 */

package currency;

public class INR extends Currency{

    public INR(double amount) {
        super(amount);
    }

    @Override
    public <T extends Currency> double addAmount(T income) {
        double amount = income.convertToCNY().getAmount();
        setAmount(getAmount()+amount);
        return amount;
    }

    @Override
    public <T extends Currency> T deductAmount(double amount) {
        setAmount(getAmount()-amount);
        return (T) new INR(amount);
    }

    public USD convertToUSD()
    {
        double amount = CurrencyExchange.convertINRtoUSD(getAmount());
        return new USD(amount);
    }

    public CNY convertToCNY()
    {
        double amount = CurrencyExchange.convertINRtoCNY(getAmount());
        return new CNY(amount);
    }

    public INR convertToINR()
    {
        return this;
    }
}
