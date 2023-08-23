package bank;

import Database.CustomerDatabase;
import Main.Customer;
import account.Account;
import currency.Currency;

/*
    Used to charge several types of fees during transactions
 */
import currency.USD;

import java.sql.SQLException;
import java.util.List;

import static Utils.Constants.*;

public class ChargeFee {
    private static final double tier1feeRate = 0.01;
    private static final double tier2feeRate = 0.02;

    public static String chargeFee(String feeLevel, Customer customer, Account<? extends Currency> account, double amount, Bank bankReserved) throws SQLException {
        double fee = getFee(feeLevel, amount);
        System.out.println("amount:"+amount+" fee:"+fee+" current deposit:"+account.getDeposit().getAmount());
        if (fee > account.getDeposit().getAmount()) {
            return INVALID_WITHDRAW;
        }

        USD charged = new USD(0);
        charged.addAmount(account.deductBalance(fee));
        System.out.println("charged:"+charged.getAmount());
        CustomerDatabase.updateAccount(customer.getLogin().getUserName(),getListAsString(customer.getAccounts()));
        bankReserved.getTotalReservedMoney().addTotalReservedMoney(charged);
        customer.writeTransaction(fee, charged.getAmount(), account.getAccountCurrency(), USD,
                customer.getId()+"/"+account.getAccountId(), "Bank - processing fee");
        System.out.println(" current deposit after charge:"+account.getDeposit().getAmount());
        return SUCCESS;
    }

    public static double getFee(String feeLevel, double amount)
    {
        double fee;
        if (feeLevel.equals(feeLevel1)) {
            fee = amount * tier1feeRate;
        } else {
            fee = amount * tier2feeRate;
        }
        return fee;
    }

    private static String getListAsString(List li){
        StringBuilder str = new StringBuilder();
        li.forEach(t->{
            str.append(t.toString()).append(",");
        });
        if(!str.toString().equals("")) {
            return str.substring(0, str.lastIndexOf(","));
        }
        return str.toString();
    }
}
