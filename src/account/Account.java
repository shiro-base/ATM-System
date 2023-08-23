/*
This class is an abstract account. It contains basic attributes for all types of accounts.
 */

package account;

import currency.Currency;

import java.util.UUID;

abstract public class Account<T extends Currency> {
    private T deposit;
    private String accountCurrency;
    private String accountType;
    private UUID accountId;


    protected Account(){

    }
    public Account(T deposit, String type) {
        this.deposit = deposit;
        accountType = type;
        accountCurrency = deposit.getClass().getSimpleName();
        accountId = UUID.randomUUID();
    }

    public Account(T deposit, String accountCurrency, String accountType, String id) {
        this.deposit = deposit;
        this.accountType = accountType;
        this.accountCurrency = accountCurrency;
        accountId = UUID.fromString(id);
    }

    public T getDeposit() {
        return deposit;
    }

    public void setDeposit(T deposit) {
        this.deposit = deposit;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public String getAccountCurrency() {
        return accountCurrency;
    }

    public <E extends Currency> double addBalance(E amount)
    {
        return deposit.addAmount(amount);
    }

    public <C extends Currency> C deductBalance(double amount)
    {
        return deposit.deductAmount(amount);

    }

    @Override
    public String toString(){
        return String.join(";", deposit.toString(),accountType,accountId.toString());
    }
}
