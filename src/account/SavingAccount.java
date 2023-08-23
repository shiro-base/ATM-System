/*
This class is a saving account. It inherits the general Account class.
It manages the monetary thresholds for transfer to and open a security account.
 */

package account;

import currency.Currency;
import currency.CurrencyExchange;
import static Utils.Constants.*;

import java.util.ArrayList;

public class SavingAccount<T extends Currency> extends Account<T>{
    private double stockThreshold;
    private double minimumThreshold;
    private double transferToSecurityThreshold;
    private double interestThreshold;
    private double interestRate;
    private double totalInterestMoney;
    public SavingAccount(T deposit) {
        super(deposit, SAVINGS_ACCOUNT);
        initializeCurrency();
    }

    public SavingAccount(T deposit,String currency,String id) {
        super(deposit, currency,SAVINGS_ACCOUNT,id);
        initializeCurrency();
    }

    private void initializeCurrency(){
        if (getAccountCurrency().equals(USD)) {
            stockThreshold = 5000;
            minimumThreshold = 2500;
            transferToSecurityThreshold = 1000;
            interestThreshold = 10000;
        }
        else if (getAccountCurrency().equals(INR)) {
            stockThreshold = CurrencyExchange.convertUSDtoINR(5000);
            minimumThreshold = CurrencyExchange.convertUSDtoINR(2500);
            transferToSecurityThreshold = CurrencyExchange.convertUSDtoINR(1000);
            interestThreshold = CurrencyExchange.convertUSDtoINR(10000);
        }
        else {
            stockThreshold = CurrencyExchange.convertUSDtoCNY(5000);
            minimumThreshold = CurrencyExchange.convertUSDtoCNY(2500);
            transferToSecurityThreshold = CurrencyExchange.convertUSDtoCNY(1000);
            interestThreshold = CurrencyExchange.convertUSDtoCNY(10000);
        }
        interestRate = 0.0002;
        totalInterestMoney = 0;
    }

    public double getTotalInterestMoney() {
        return totalInterestMoney;
    }

    public void setTotalInterestMoney(double totalInterestMoney) {
        this.totalInterestMoney = totalInterestMoney;
    }

    public double getStockThreshold() {
        return stockThreshold;
    }

    public double getMinimumThreshold() {
        return minimumThreshold;
    }

    public double getTransferToSecurityThreshold() {
        return transferToSecurityThreshold;
    }

    public void setTransferToSecurityThreshold(double transferToSecurityThreshold) {
        this.transferToSecurityThreshold = transferToSecurityThreshold;
    }

    public double getInterestThreshold() {
        return interestThreshold;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public boolean isSecurityAllowed()
    {
        return super.getDeposit().getAmount() >= stockThreshold;
    }

    public void incrementInterestMoney(int days)
    {
        if (getDeposit().getAmount() >= interestThreshold) {
            totalInterestMoney += getDeposit().getAmount() * Math.pow(1 + interestRate, days) - getDeposit().getAmount();
        }
    }
}
