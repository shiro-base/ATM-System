/*
    store and change the money in the bank
 */
package bank;

import Database.BankReservesDatabase;
import currency.USD;

import java.sql.SQLException;

public class BankReserved {
    private USD totalReservedMoney;

    public BankReserved() throws SQLException {
        this(0);
    }

    public BankReserved(double amount) throws SQLException {
        setTotalReservedMoney(new USD(amount));
    }
    public void addTotalReservedMoney(USD addAmount) throws SQLException {
        totalReservedMoney.addAmount(addAmount);
        updateBankReserveDatabase();
    }

    public void removeTotalReservedMoney(double deductAmount) throws SQLException {
        totalReservedMoney.deductAmount(deductAmount);
        updateBankReserveDatabase();
    }

    public double getTotalReservedMoney() {
        return totalReservedMoney.getAmount();
    }

    public void setTotalReservedMoney(USD initMoney) throws SQLException {
        totalReservedMoney = initMoney;
        updateBankReserveDatabase();
    }

    private void updateBankReserveDatabase() throws SQLException {
        BankReservesDatabase.changeBankReserve(totalReservedMoney.getAmount());
    }
}
