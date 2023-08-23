/*
This class represent USD. It inherits Currency class
 */

package currency;

public class USD extends Currency{

    public USD(double amount) {
        super(amount);
    }

    @Override
    public <T extends Currency> double addAmount(T income) {
        double amount = income.convertToUSD().getAmount();
        setAmount(getAmount()+amount);
        return amount;
    }

    @Override
    public <T extends Currency> T deductAmount(double amount) {
        setAmount(getAmount()-amount);
        return (T) new USD(amount);
    }

    public CNY convertToCNY()
    {
        double amount = CurrencyExchange.convertUSDtoCNY(getAmount());
        return new CNY(amount);
    }

    public INR convertToINR()
    {
        double amount = CurrencyExchange.convertUSDtoINR(getAmount());
        return new INR(amount);
    }

    public USD convertToUSD()
    {
        return this;
    }
}
