/*
    concrete ATM class, functions can be used to perform customer and manager actions
 */
package bank;

import Database.*;
import Exceptions.FancyBankException;
import StockMarket.Stock;
import StockMarket.StockMarket;
import account.Account;
import static Utils.Constants.*;
import account.SavingAccount;
import account.SecurityAccount;
import currency.*;
import Main.*;
import loan.Collateral;
import loan.Loan;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ATM extends ATMAbstract {
    private static Bank bank;
    private static Customer loggedInCustomer;
    private static BankManager loggedInManager;

    private static StockMarket stockMarket;

    public ATM() throws SQLException, FancyBankException {
    }

    public ATM(boolean initialize) throws SQLException, FancyBankException {
        ExecuteQuery.createDatabase();
        bank = new Bank("local");
        stockMarket = new StockMarket(bank);
    }
    public String login(String type, String userName, String password ) throws SQLException {
        if (type.equals("Customer")) {
            System.out.println("Verifying login");
            if(CustomerLoginDatabase.verifyPassword(userName,password)) {
                loggedInCustomer = CustomerDatabase.getCustomer(userName);
                loggedInManager = null;
                return SUCCESSFULL_LOGIN;
            } else {
                return UNSUCCESSFULL_LOGIN;
            }
        } else if (type.equals("Manager")) {
            if(EmployeeLoginDatabase.verifyBankManager(userName,password)){
                loggedInManager = new BankManager("Manager",new Login(userName,password),bank);
                loggedInCustomer = null;
                return SUCCESSFULL_LOGIN;
            } else {
                return UNSUCCESSFULL_LOGIN;
            }
        } else {
            return INVALID_LOGIN;
        }
    }


    public boolean logout(){
        loggedInCustomer = null;
        loggedInManager = null;
        return true;
    }

    public String registerNewCustomer(String password, String username, String name, String address, String email, String security){
        if(isValidEmailAddress(email)){
        Login login = new Login(username, password,security);
        Customer customer = new Customer(name, login, address, email);
        if(CustomerDatabase.registerCustomer(login,customer)) {
            return CUSTOMER_REGISTERED;
        }
        return USERNAME_TAKEN;
        }
        return INVALID_EMAIL_ADDRESS;
    }

    public static boolean isValidEmailAddress(String email) {
        return email.matches(EMAIL_REGEX);
    }

    public boolean forgotPassword(String username, String securityAnswer){
        return CustomerLoginDatabase.forgotPassword(username, securityAnswer);
    }

    public void setNewPassword(String username, String newPassword) throws SQLException {
        CustomerLoginDatabase.setNewPassword(username,newPassword);
    }

    public boolean isValidAccountType(String accountType){
        if(accountType.equals(SECURITY_ACCOUNT)||accountType.equals(SAVINGS_ACCOUNT)||accountType.equals(CHECKING_ACCOUNT)){
            return true;
        }
        return false;
    }

    public boolean isValidCurrency(String currency){
        if(currency.equals(INR)||currency.equals(USD)||currency.equals(CNY)){
            return true;
        }
        return false;
    }

    public <T extends Currency> String openAccount(String accountType, double initDeposit, String baseCurrency) throws SQLException {
        if (initDeposit < 0) {
            return NEGATIVE_AMOUNT;
        }
        if(!isValidAccountType(accountType)){
            return INVALID_ACCOUNT_TYPE;
        }
        if(!isValidCurrency(baseCurrency)) {
            return INVALID_CURRENCY_TYPE;
        }
        if(initDeposit>MAX_DEPOSIT){
            return MAX_DEPOSIT_EXCEEDED;
        }
        String response;
        if (baseCurrency.equals(USD)) {
            Account<USD> newAccount = loggedInCustomer.openAccount(accountType, new USD(initDeposit));
            response = ChargeFee.chargeFee(feeLevel1, loggedInCustomer, newAccount, initDeposit, bank);
        } else if (baseCurrency.equals(INR)) {
            Account<INR> newAccount = loggedInCustomer.openAccount(accountType, new INR(initDeposit));
            response = ChargeFee.chargeFee(feeLevel1, loggedInCustomer, newAccount, initDeposit, bank);
        } else {
            Account<CNY> newAccount = loggedInCustomer.openAccount(accountType, new CNY(initDeposit));
            response = ChargeFee.chargeFee(feeLevel1, loggedInCustomer, newAccount, initDeposit, bank);
        }

        return response;
    }

    public String closeAccount(Account<? extends Currency> account) throws SQLException {
        return loggedInCustomer.closeAccount(account);
    }

    public List<Transaction> viewTransactions(){
        return loggedInCustomer.getTransactionHistory();
    }

    public <T extends Currency> String deposit(Account<? extends Currency> account, double amount) throws SQLException {
        if (amount < 0) {
            return NEGATIVE_AMOUNT;
        }
        if (account.getAccountType().equals(SECURITY_ACCOUNT)) {
            return "Invalid: You cannot deposit into Security account";
        }
        if (account.getAccountCurrency().equals(USD)) {
            return loggedInCustomer.deposit(account, new USD(amount));
        } else if (account.getAccountCurrency().equals(INR)) {
            return loggedInCustomer.deposit(account, new INR(amount));
        } else {
            return loggedInCustomer.deposit(account, new CNY(amount));
        }
    }

    public String withdraw(Account<? extends Currency> account, double amount) throws SQLException {
        if (amount < 0) {
            return NEGATIVE_AMOUNT;
        }
        if (amount > account.getDeposit().getAmount()) {
            return INVALID_WITHDRAW;
        }
        double fee = ChargeFee.getFee(feeLevel1, amount);
        ChargeFee.chargeFee(feeLevel2, loggedInCustomer, account, amount, bank);
        return loggedInCustomer.withdraw(account,amount);
    }

    public String Transaction(Account<? extends Currency> sendingAccount, UUID receivingAccountId, double amount) throws SQLException {
        if (amount < 0) {
            return NEGATIVE_AMOUNT;
        }
        if (amount > sendingAccount.getDeposit().getAmount()){
            return EXCEED_DEPOSIT;
        }
        Customer receiver = getCustomerFromAccount(receivingAccountId);

        Account<? extends Currency> receivingAccount = loggedInCustomer.getAccount(receivingAccountId);
        if(receivingAccount == null) {
            return NO_CUSTOMER_FOUND;
        }
        if(loggedInCustomer.equals(receiver)) {
            if (sendingAccount.getAccountType().equals(SAVINGS_ACCOUNT) && receivingAccount.getAccountType().equals(SECURITY_ACCOUNT)) {
                return savingToSecurityTransfer((SavingAccount<? extends Currency>) sendingAccount, amount);
            }
            else {
                return innerTransfer(sendingAccount, receivingAccount, amount);
            }
        } else {
            return transfer(receiver,sendingAccount,receivingAccount,amount);
        }
    }

    public String innerTransfer(Account<? extends Currency> sender, Account<? extends Currency> receiver, double amount) throws SQLException {
        return loggedInCustomer.innerTransfer(sender, receiver,amount);
    }

    public String transfer(Customer receiver, Account<? extends Currency> senderAccount, Account<? extends Currency> receiverAccount, double amount) throws SQLException {
        if (amount > senderAccount.getDeposit().getAmount()) {
            return INVALID_WITHDRAW;
        }
        double fee = ChargeFee.getFee(feeLevel1, amount);
        ChargeFee.chargeFee(feeLevel2, loggedInCustomer, senderAccount, amount, bank);
        return loggedInCustomer.transfer(receiver,senderAccount,receiverAccount,amount);
    }

    public List<Transaction> showDailyReport(){
        return loggedInManager.getDailyReport();
    }
    public String buyStock(Stock stock, int quantity) throws FancyBankException, SQLException {
        if(quantity<0){
            return NEGATIVE_AMOUNT;
        }
        return stockMarket.buyStock(stock, quantity,loggedInCustomer) ? SUCCESS : FAILED;
    }

    public String sellStock(Stock stock, int quantity) throws FancyBankException, SQLException {
        if(quantity<0){
            return NEGATIVE_AMOUNT;
        }
        return stockMarket.sellStock(stock, quantity,loggedInCustomer) ?SUCCESS :FAILED;
    }

    public String  setStockPrice(Stock stock, double price) throws FancyBankException, SQLException {
        if(price<0){
            return NEGATIVE_AMOUNT;
        }
        return stockMarket.updateStockPrice(stock,price);
    }

    public String addStock(String name, double initPrice, int quantity) throws FancyBankException {
        if(initPrice<0){
            return NEGATIVE_AMOUNT;
        }
        if(quantity<=0){
            return NEGATIVE_AMOUNT;
        }
        if(loggedInManager.getBankEmployeeType().equals(BankEmployeeType.MANAGER)){
            loggedInManager.addStock(name, initPrice, quantity);
            //bank.getStockMarket().addStock(new Stock(name, new Price(initPrice,initPrice), quantity));
            System.out.println(quantity + " new stock " + name + " has been added with price at " + initPrice);
        }
        return SUCCESS;
    }

    public String removeStock(Stock stock, int quantity) throws FancyBankException {
        if(quantity<=0){
            return NEGATIVE_AMOUNT;
        }
        if(loggedInManager.getBankEmployeeType().equals(BankEmployeeType.MANAGER)){
            loggedInManager.removeStock(stock,quantity);
            return SUCCESS;
        }
        return PERMISSION_DENIED;
    }

    // null customer check
    public Customer viewCustomer(String customerUserName) throws SQLException {
        return loggedInManager.viewCustomer(customerUserName);
    }

    public <T extends Currency> String takeLoan(Collateral collateral, double amount, Account<? extends Currency> account, String dueDate) throws SQLException {
        if (amount < 0) {
            return NEGATIVE_AMOUNT;
        }
        if(!validateDate(dueDate)){
           return  INCORRECT_DATE_FORMAT;
        }
        if(account.getAccountCurrency().equals(INR)) {
            INR inr = new INR(amount);
            if (bank.getTotalReservedMoney().getTotalReservedMoney() < inr.convertToUSD().getAmount()) {
                return BANK_NO_MONEY;
            } else {
                return loggedInCustomer.takeLoan(new INR(amount),dueDate,collateral,account);
            }
        } else if(account.getAccountCurrency().equals(USD)){
            if (bank.getTotalReservedMoney().getTotalReservedMoney() < amount) {
                return BANK_NO_MONEY;
            } else {
                return loggedInCustomer.takeLoan(new USD(amount),dueDate,collateral,account);
            }
        } else {
            CNY cny = new CNY(amount);
            if (bank.getTotalReservedMoney().getTotalReservedMoney() < cny.convertToUSD().getAmount()) {
                return BANK_NO_MONEY;
            } else {
                return loggedInCustomer.takeLoan(new CNY(amount),dueDate,collateral,account);
            }
        }
    }

    private boolean validateDate(String date){
        try{
            LocalDate.parse(date, formatter);
            return true;
        }
        catch (DateTimeParseException e){
            return false;
        }
    }

    public String payLoan(Loan loan, Account<? extends Currency> account) throws SQLException {
        return loggedInCustomer.payLoan(loan, account);
    }

    public String incrementDate(int dayToIncrement) throws SQLException {
        if(dayToIncrement<0){
            return NEGATIVE_AMOUNT;
        }
        if(loggedInManager.getBankEmployeeType().equals(BankEmployeeType.MANAGER)){
            loggedInManager.increaseDate(dayToIncrement);
            for (Customer c : Objects.requireNonNull(CustomerDatabase.getAllCustomers())){
                for(Account<? extends Currency> s : c.getSavingAccounts()){
                    if(s instanceof SavingAccount){
                        ((SavingAccount<? extends Currency>) s).incrementInterestMoney(dayToIncrement);
                        String respond = paySavingInterests((SavingAccount<? extends Currency>) s, c);
                        ((SavingAccount<? extends Currency>) s).setTotalInterestMoney(0);
                        if (bank.isBankrupt()) {
                            return "Bank has no money to pay saving interests. Bank has bankrupted.";
                        }
                    }
                }
            }
        }
        return dayToIncrement + " has passed";
    }

    public String paySavingInterests(SavingAccount<? extends Currency> savingAccount, Customer customer) throws SQLException {
        double money = CurrencyExchange.convert(savingAccount.getAccountCurrency(), "USD", savingAccount.getTotalInterestMoney());
        if (bank.getTotalReservedMoney().getTotalReservedMoney() < money) {
            bank.setBankrupt(true);
            return "Bank has no money to pay saving interests. Bank has bankrupted.";
        }
        bank.getTotalReservedMoney().removeTotalReservedMoney(money);
        double receivedMoney = savingAccount.addBalance(new USD(money));
        customer.writeTransaction(money, receivedMoney, "USD", savingAccount.getAccountCurrency(),
                "Saving Interest", customer.getId()+"/"+savingAccount.getAccountId());
        return "Success";
    }

    public List<Account<? extends Currency>> getCustomerAccount() {
        return loggedInCustomer.getAccounts();
    }
    public List<Account<? extends Currency>> getNonSecurityAccounts() {return loggedInCustomer.getNonSecurityAccounts();}
    public List<Account<?extends Currency>> getSavingAccounts(){
        List<Account<?extends Currency>> savingAccounts = new ArrayList<>();
        for (Account a : loggedInCustomer.getAccounts()){
            if(a.getAccountType().equals(SAVINGS_ACCOUNT)) {
                savingAccounts.add(a);
            }
        }
        return savingAccounts;
    }

    public List<Loan<? extends Currency>> getLoansByManager(Customer customer){
        return customer.getLoans();
    }

    public List<Loan<? extends Currency>> getLoansByCustomer(){
        return loggedInCustomer.getLoans();
    }
    public List<Customer> getCustomerHasLoan(){
        List<Customer> customerHasLoan = new ArrayList<>();
        for(Customer c : bank.getCustomerList()){
            if (!c.getLoans().isEmpty()) {
                customerHasLoan.add(c);
            }
        }
        return customerHasLoan;
    }

    public  String openSecurityAccount(SavingAccount<? extends Currency> savingAccount, double initDeposit) throws SQLException {
        if (loggedInCustomer.hasSecurityAccount()!=null){
            return "Can only have one security account";
        }
        System.out.println("saving amount before open security:" + savingAccount.getDeposit().getAmount() + " stockthreshold:"+savingAccount.getStockThreshold());
        if(savingAccount.getDeposit().getAmount()>savingAccount.getStockThreshold()){
            if(initDeposit >= savingAccount.getTransferToSecurityThreshold()) {
                if(savingAccount.getDeposit().getAmount() - initDeposit>= savingAccount.getMinimumThreshold()){
                    loggedInCustomer.openAccount(SECURITY_ACCOUNT, new USD(0));
                    return savingToSecurityTransfer(savingAccount,initDeposit);
                } else {
                    return "saving account must maintain a deposit over USD 2500";
                }
            } else {
                return "saving to security transfer must over USD 1000";
            }
        } else {
            return "Saving account must have deposit over USD 5000 to open a security account";
        }
    }

    public String savingToSecurityTransfer(SavingAccount<? extends Currency> savingAccount, double amount) throws SQLException {
        if(amount >= savingAccount.getTransferToSecurityThreshold()){
            if(savingAccount.getDeposit().getAmount() - amount >= savingAccount.getMinimumThreshold()){
                double fee = ChargeFee.getFee(feeLevel1, amount);
                innerTransfer(savingAccount,loggedInCustomer.hasSecurityAccount(),amount-fee);
                ChargeFee.chargeFee(feeLevel1, loggedInCustomer, savingAccount, amount, bank);
                return SUCCESS;
            } else {
                return "saving account must maintain a deposit over USD 2500";
            }
        } else {
            return "saving to security transfer must over USD 1000";
        }
    }

    public List<Stock> getCustomerStockList(){
        SecurityAccount securityAccount = loggedInCustomer.hasSecurityAccount();
        if(securityAccount!=null){
            return securityAccount.getStocks();
        }
        return new ArrayList<>();
    }

    public Customer getCustomerFromAccount(UUID accountNumber){
        for(Customer c:CustomerDatabase.getAllCustomers()){
            if(c.getAccount(accountNumber)!=null){
                return c;
            }
        }
        return null;
    }

    public List<Stock> getStockProfit() {
        SecurityAccount securityAccount = loggedInCustomer.hasSecurityAccount();
        if (securityAccount != null) {
            securityAccount.getUnRealizedProfit();
            return securityAccount.getStocks();
        }
        return new ArrayList<>();
    }

    public List<Stock> getAllStocks() throws SQLException, FancyBankException {
        return StocksDatabase.getAllStocks();
    }

    public double getTotalRealizedProfit(){
        SecurityAccount securityAccount = loggedInCustomer.hasSecurityAccount();
        if(securityAccount != null) {
            return securityAccount.getRealizedProfit();
        } else {
            return 0.0;
        }
    }

    public List<Transaction> getTransactionsForAccount(UUID accountId){
        return loggedInCustomer.getTransactions(accountId);
    }

    public List<Customer> getAllCustomers(){
        return CustomerDatabase.getAllCustomers();
    }

    public double getToTalBankMoney(){
        return BankReservesDatabase.getBankMoney();
    }

}