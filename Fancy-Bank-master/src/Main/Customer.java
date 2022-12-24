/*
This class contains a customer's basic info and all accounts and transaction history of him.
It also contains functions like open/close accounts, deposit/withdraw/transfer monoey, and buy and sell stocks.
 */

package Main;

import Database.CustomerDatabase;
import Database.TransactionDatabase;
import Exceptions.FancyBankException;
import account.Account;
import account.CheckingAccount;
import account.SavingAccount;
import account.SecurityAccount;
import currency.*;
import loan.Collateral;
import loan.Loan;
import static Utils.Constants.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Customer extends Person{
    private List<Account<? extends Currency>> accounts;
    private List<Transaction> transactionHistory;
    private List<Loan<? extends Currency>> loans;

    private static final int minSecurityTransfer = 1000;

    public Customer(String name, Login login, String address, String email)
    {
        super(name, login, address, email);
        transactionHistory = new ArrayList<>();
        loans = new ArrayList<>();
        accounts=new ArrayList<>();
    }

    public Customer(String name, Login login, int id, String address, String email,String txnHistory,String loans,String accounts)
    {
        super(name, login, id, address, email);
        if(txnHistory!=null && (!txnHistory.equals("[]") && !txnHistory.equals(""))) {
            this.transactionHistory=parseTxnHistory(txnHistory);
        }
        else{
            this.transactionHistory = new ArrayList<>();
        }
        if(loans!=null && !loans.equals("") && !loans.equals("[]")) {
            this.loans=parseLoans(loans);
        }
        else{
           this.loans = new ArrayList<>();
        }
        if(accounts!=null && !accounts.equals("") && !accounts.equals("[]")) {
            try {
                this.accounts = parseAccounts(accounts);
            }
            catch (FancyBankException e){
                System.out.println("couldn't parse exceptions");
            }
        }
        else {
            this.accounts=new ArrayList<>();
        }
    }

    public List<Transaction> getTransactionHistory() {
        String txn = CustomerDatabase.getTransactionHistory(getLogin().getUserName());
        if(txn!=null){
            return parseTxnHistory(txn);
        }
        return new ArrayList<>();
    }

    public void setTransactionHistory(ArrayList<Transaction> transactionHistory) {
        this.transactionHistory = transactionHistory;
    }

    public List<Loan<? extends Currency>> getLoans() {
        String l=CustomerDatabase.getLoans(getLogin().getUserName());
        if(l!=null) {
            return parseLoans(l);
        }
        return new ArrayList<>();
    }

    public void setLoans(ArrayList<Loan<? extends Currency>> loans) {
        this.loans = loans;
    }

    public List<Account<? extends Currency>> getAccounts() {
        try {
                String acc = CustomerDatabase.getCustomerAccounts(getLogin().getUserName());
                if (acc != null) {
                    return parseAccounts(acc);
                }
                return new ArrayList<>();
            } catch (FancyBankException e) {
                System.out.println("couldn't parse accounts");
            }
        return new ArrayList<>();
    }

    public void setAccounts(ArrayList<Account<? extends Currency>> accounts) {
        this.accounts = accounts;
    }

    public List<Account<? extends Currency>> getNonSecurityAccounts()
    {
        List<Account<? extends Currency>> nonSecurityAccounts = new ArrayList<>();
        for (Account<? extends Currency> a : accounts) {
            if (!a.getAccountType().equals(SECURITY_ACCOUNT)) {
                nonSecurityAccounts.add(a);
            }
        }
        return nonSecurityAccounts;
    }

    public List<Account<? extends Currency>> getSavingAccounts()
    {
        List<Account<? extends Currency>> nonSecurityAccounts = new ArrayList<>();
        for (Account<? extends Currency> a : accounts) {
            if (a.getAccountType().equals(SAVINGS_ACCOUNT)) {
                nonSecurityAccounts.add(a);
            }
        }
        return nonSecurityAccounts;
    }

    public void writeTransaction(double sendingAmount, double receivingAmount, String sendingCurrency, String ReceivingCurrency, String from, String to) throws SQLException {
        Transaction transac = new Transaction(sendingAmount, receivingAmount, sendingCurrency, ReceivingCurrency, to, from);
        transactionHistory.add(transac);
        CustomerDatabase.updateTxnHistory(getLogin().getUserName(), getListAsString(transactionHistory));
        TransactionDatabase.insert(transac);
    }

    public Account getAccount(UUID accountNumber){
        return accounts.stream().filter(acc->acc.getAccountId().equals(accountNumber)).findFirst().orElse(null);
    }



    public <T extends Currency> String deposit(Account<? extends Currency> account, T currency) throws SQLException {
        System.out.println("before deposit:"+account.getDeposit().getAmount());
        double receivingAmount = account.addBalance(currency);
        updateAccounts();
        System.out.println("after deposit:"+account.getDeposit().getAmount());
        writeTransaction(currency.getAmount(), receivingAmount, currency.getClass().getSimpleName(), account.getAccountCurrency(),
                "deposit", super.getId()+"/"+account.getAccountId());
        return SUCCESS;
    }

    public <T extends Currency> String withdraw(Account<? extends Currency> account, double amount) throws SQLException {
        if (amount > account.getDeposit().getAmount()) {
            return INVALID_WITHDRAW;
        }
        account.deductBalance(amount);
        updateAccounts();
        writeTransaction(amount, amount, account.getAccountCurrency(), account.getAccountCurrency(), super.getId()+"/"+account.getAccountId(), "withdrawal");
        return SUCCESS;
    }

    public <T extends Currency> String buyStock(Account<? extends Currency> account,double amount) throws SQLException {
        Account<? extends Currency> acnt = getAccount(account);
        if (amount > acnt.getDeposit().getAmount()) {
            return INVALID_WITHDRAW;
        }
        acnt.deductBalance(amount);
        updateAccounts();
        writeTransaction(amount, new USD(amount).getAmount(), acnt.getAccountCurrency(), "USD", super.getId()+"/"+acnt.getAccountId(), "BANK - buy stocks");
        return SUCCESS;
    }
    public <T extends Currency> String sellStock(Account<? extends Currency> account,T currency) throws SQLException {
        Account<? extends Currency> acnt = getAccount(account);
        double receivingAmount = acnt.addBalance(currency);
        updateAccounts();
        writeTransaction(currency.getAmount(), receivingAmount, currency.getClass().getSimpleName(), account.getAccountCurrency(),
                "BANK - sell stock", super.getId()+"/"+account.getAccountId());
        return SUCCESS;
    }
    public String transfer(Customer receiver, Account<? extends Currency> senderAccount, Account<? extends Currency> receiverAccount, double amount) throws SQLException {
        senderAccount = getAccount(senderAccount);
        if (amount > senderAccount.getDeposit().getAmount()) {
            return INVALID_WITHDRAW;
        }
        Account<? extends Currency> receiverAcc = receiver.getAccounts().stream().filter(acc->acc.getAccountId().equals(receiverAccount.getAccountId())).findFirst().get();
        double convertedAmount = receiverAcc.addBalance(senderAccount.deductBalance(amount));
        Transaction transac = new Transaction(amount, convertedAmount, senderAccount.getAccountCurrency(), receiverAcc.getAccountCurrency(),
                receiver.getId()+"/"+receiverAcc.getAccountId(), super.getId()+"/"+senderAccount.getAccountId());
        transactionHistory.add(transac);
        receiver.getTransactionHistory().add(transac);
        CustomerDatabase.updateTxnHistory(getLogin().getUserName(),getListAsString(transactionHistory));
        CustomerDatabase.updateTxnHistory(receiver.getLogin().getUserName(),getListAsString(receiver.getTransactionHistory()));
        return SUCCESS;
    }

    public String innerTransfer(Account<? extends Currency> sender, Account<? extends Currency> receiver, double amount) throws SQLException {

        if (amount > sender.getDeposit().getAmount()) {
            return INVALID_WITHDRAW;
        }
        double convertedAmount = receiver.addBalance(sender.deductBalance(amount));
        updateAccounts();
        writeTransaction(amount, convertedAmount, sender.getAccountCurrency(), receiver.getAccountCurrency(), super.getId()+"/"+sender.getAccountType(), super.getId()+"/"+receiver.getAccountType());
        return SUCCESS;
    }

    private Account<? extends Currency> getAccount(Account<? extends Currency> account){
        return accounts.stream().filter(acc->acc.getAccountId().equals(account.getAccountId())).findFirst().get();

    }
    private void updateAccounts() throws SQLException {
        CustomerDatabase.updateAccount(getLogin().getUserName(), getListAsString(accounts));
    }
    private void updateLoans() throws SQLException {
        CustomerDatabase.updateAccount(getLogin().getUserName(), getListAsString(loans));
    }
    public <T extends Currency> String takeLoan(T principal, String dueDate, Collateral collateral, Account<? extends Currency> account) throws SQLException {
        if (principal.getAmount() > collateral.getEvaluation()*2) {
            return "Invalid Operation: maximum loan amount can only be twice as collateral evaluation.";
        }
        Loan<? extends Currency> loan = new Loan<>(principal, dueDate, collateral);
        loans.add(loan);
        CustomerDatabase.updateLoans(getLogin().getUserName(),getListAsString(loans));
        double receivingAmount = account.addBalance(principal);
        updateAccounts();
        writeTransaction(principal.getAmount(), receivingAmount, principal.getClass().getSimpleName(), account.getAccountCurrency(),
                "src/bank", super.getId()+"/"+account.getAccountId());
        return SUCCESS;
    }

    public String payLoan(Loan<? extends Currency> loan, Account<? extends Currency> account) throws SQLException {
        double loanAmount = loan.getTotalAmount();
        double convertedAmount = CurrencyExchange.convert(loan.getCurrency(), account.getAccountCurrency(), loanAmount);
        if (convertedAmount > account.getDeposit().getAmount()) {
            return INVALID_WITHDRAW;
        }
        account = getAccount(account);
        account.deductBalance(convertedAmount);

        writeTransaction(convertedAmount, loanAmount, account.getAccountCurrency(), loan.getCurrency(), super.getId()+"/"+account.getAccountId(), "src/bank");
        loans.remove(loan);
        updateAccounts();
        updateLoans();
        CustomerDatabase.updateLoans(getLogin().getUserName(),getListAsString(loans));
        return SUCCESS;
    }

    public <T extends Currency> Account<T> openAccount(String accountType, T initDeposit) throws SQLException {
        Account<T> newAccount = null;
        if(accountType.equals(SAVINGS_ACCOUNT)){
            newAccount = new SavingAccount<>(initDeposit);
        }
            else if (accountType.equals(CHECKING_ACCOUNT)) {
            newAccount = new CheckingAccount<>(initDeposit);
        }
            else if(hasSavingsAccount()!=null && hasSavingsAccount().isSecurityAllowed()) {
                    newAccount = new SecurityAccount<>(initDeposit);}
            //accounts.add(newAccount);
        String response = updateDatabaseAccount(newAccount,initDeposit);
        System.out.println(response);
        return newAccount;
        }


    private <T extends Currency> String updateDatabaseAccount(Account<? extends Currency> newAccount, T initDeposit) throws SQLException {
        if(newAccount!=null) {
            accounts.add(newAccount);
            CustomerDatabase.addAccount(getLogin().getUserName(), getListAsString(accounts));
            writeTransaction(initDeposit.getAmount(), initDeposit.getAmount(), newAccount.getAccountCurrency(), newAccount.getAccountCurrency(),
                    "open account", super.getId() + "/" + newAccount.getAccountId());
            return SUCCESS;
        }
        return FAILED;
    }

    public String closeAccount(Account<? extends Currency> account) throws SQLException {
        withdraw(account, account.getDeposit().getAmount());
        System.out.println(accounts.size());
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getAccountId().equals(account.getAccountId())) {
                accounts.remove(i);
                break;
            }
        }
        accounts.remove(account);
        System.out.println(accounts.size());
        CustomerDatabase.updateAccount(getLogin().getUserName(), getListAsString(accounts));
        return SUCCESS;
    }

    public SecurityAccount<? extends Currency> hasSecurityAccount(){
        for(Account<? extends Currency> account:accounts){
            if(account instanceof SecurityAccount){
                return (SecurityAccount<? extends Currency>) account;
            }
        }
        return null;
    }

    public SavingAccount<? extends Currency> hasSavingsAccount(){
        for(Account<? extends Currency> account:accounts){
            if(account instanceof SavingAccount){
                return (SavingAccount<? extends Currency>) account;
            }
        }
        return null;
    }

    private Currency parseCurrency(String currency,double amt){

        if(currency.equals(USD)){
            return new USD(amt);
        }
        else if(currency.equals(INR)){
            return new INR(amt);
        }
        else {
            return new CNY(amt);
        }
    }

    private List<Transaction> parseTxnHistory(String txnHistory){
        List<Transaction> transactions = new ArrayList<Transaction>();
        String[] t= txnHistory.split(",");
        for(String tsplit: t){
            String[] values = tsplit.split(";");
            transactions.add(new Transaction((Integer.parseInt(values[0])),values[1],values[2],
                    values[3],values[4],values[5],Double.parseDouble(values[6]),values[7],Double.parseDouble(values[8])));
        }
        return transactions;
    }

    private List<Loan<? extends Currency>> parseLoans(String l){
        List<Loan<? extends Currency>> loans = new ArrayList<>();
        String[] t = l.split(",");
        for(String tsplit:t){
            String[] values = tsplit.split(";");
            loans.add(new Loan(parseCurrency(values[1],Double.parseDouble(values[0])),values[1], Double.parseDouble(values[2]),values[3],new Collateral(values[4],Double.parseDouble(values[5]))));

        }
        return loans;
    }

    private List<Account<? extends Currency>> parseAccounts(String acc) throws FancyBankException {
        List<Account<? extends Currency>> parsedAccounts = new ArrayList<>();
        String[] t = acc.replace("[","").replace("]","").split(",");
        for(String tsplit:t){
            String[] values = tsplit.split(";");
            String accountType = values[2];
            Account<? extends Currency> newAccount;
            Currency curr = parseCurrency(values[1],Double.parseDouble(values[0]));
            switch (accountType) {
                case SAVINGS_ACCOUNT:
                    newAccount = new SavingAccount<>(curr,values[1],values[3]);
                    break;
                case CHECKING_ACCOUNT:
                    newAccount = new CheckingAccount<>(curr,values[1],values[3]);
                    break;
                default:
                    newAccount = new SecurityAccount<>(curr,values[1],values[3],values[4],Double.parseDouble(values[5]));
                    break;
            }
            parsedAccounts.add(newAccount);
        }
        return parsedAccounts;
    }

    private String getListAsString(List li){
        StringBuilder str = new StringBuilder();
        li.forEach(t->{
            str.append(t.toString()).append(",");
        });
        if(!str.toString().equals("")) {
            return str.substring(0, str.lastIndexOf(","));
        }
        return str.toString();
    }

    public List<Transaction> getTransactions(UUID accountId){
        List<Transaction> arr = new ArrayList<Transaction>();
        if(transactionHistory!=null && transactionHistory.size()>0){
            transactionHistory.forEach(t->{
                if(containsUUID(t.getTo(),accountId) || containsUUID(t.getFrom(),accountId)){
                    arr.add(t);
                }
            });
        }
        return arr;
    }

    private boolean containsUUID(String str,UUID accId){
        if(str == null || !str.contains("/")){
            return false;
        }
        return str.substring(str.indexOf("/")+1).equals(accId.toString());
    }

}
