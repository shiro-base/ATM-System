Readme


# CS611-<Final Project>
## <My-Fancy-Bank>
---------------------------------------------------------------------------


# Team Member
Sakshi Sharma (phsakshi@bu.edu)
Ye Tian (ty2806@bu.edu)
Tianxin Zhou (txzhou@bu.edu)
Smriti Suresh (smritis@bu.edu)


---------------------------------------------------------------------------
# How to compile & run
JDK version 1.8
Dependencies - sqlite-jdbc-3.20.1
1. Navigate to the directory "Fancy-Bank" after unzipping the files
2. find . -name "*.java" > sources.txt && javac -d bin @sources.txt
3. javac -cp ".:bin/sqlite-jdbc-3.20.1..jar:bin/forms-1.2.1.jar" -d prod/src/ @sources.txt
4. cd prod/src/
5. java -cp .:../../bin/sqlite-jdbc-3.40.0.0.jar:../../bin/forms-1.2.1.jar Main


From IntelliJ
1. Navigate to the directory "Fancy-Bank" after unzipping the files
2. Add sqlite-jdbc-3.20.1.jar file as a dependency. Go to File -> Project Structure -> Modules 
-> click on the plus sign (+) -> add JAR and select the sqlite-jdbc-3.20.1.jar file
3. Build and run the project from Fancy-Bank/src/gui/LoginPage.




---------------------------------------------------------------------------


# Files
Database package:
We have used sqlite (sqlite-jdbc-3.20.1) to maintain our database system.
Sqlite being lightweight, requiring no installation was easy to set up and made our system persistent.
Database relevant files which will be accessed by our ATM. Where all ATM data is stored.


BankReservesDatabase.java - stores the bank held money
CreateDatabase.java - to initialize a database
CustomerDatabase.java - Customer information
CustomerLoginDatabase.java - Customer login information
EmployeeLoginDatabase.java - Employee login information
ExecuteQuery.java - execute query
FancyBank.db 
StocksDatabase.java -  stock information 
TransactionDatabase.java - Transaction information
Database.java - database interface


Exceptions package:
Exceptions to be thrown when an error occurred.


FancyBankException.java - throw FancyBankException


Main package:
Our bank is service-oriented. In the Main package, we provide functions to enable customers of the bank perform actions including login, transact, etc…
        
Customer.java - actions that can be performed by a customer
DateSystem.java - DateSystem used to keep track of interest
Login.java - Login information of a user
Main.java - Main
Person.java - superclass of abstract employee and Customer
Transaction.java - information we need of a transaction


StockMarket package:
In our bank, we also allow qualified customers to transact stocks. This package includes a StockMarket class which holds open stocks and allows customers to transact stocks. 
        
Buyable.java - interface for Stock
Price.java - record current price and bought price of a stock
Sellable.java - interface for Stock
Stock.java - Stock to be transacted between customer and stockmarket
StockMarket.java - where customer buy and sell stocks
StockMarketInterface.java - interface for stockmarket


Utils package:
We define some constant  such as error messages for convenience
        
Constant.java - error messages and constant 


account package:
Every customer will be able to open and close several types of accounts. 
        
Account.java - superclass of customer’s accounts
SavingAccount.java - subclass of Account
CheckingAccount.java - subclass of Account
SecurityAccount.java -subclass of Account




bank package:  
Bank side of the whole program. ATM as an interface between the frontend and the backend, storing functions of actions that could be performed by a customer or a manager who logged in. BankReserved stores the available funds in the bank. BankManager will be able to perform a different set of actions compared to customers.


ATM.java - where we access databases to communicate with the GUI
ATMAbstract.java - abstract superclass of ATM
Bank.java - concrete Bank where ATM performs actions on
BankAbstract.java - abstract bank class
BankEmployee.java - subclass of BankEmployeeAbstract
BankEmployeeAbstract.java - abstract class representing BankEmployees in the bank
BankEmployeeType.java - Enum for different types of BankEmployees
BankReserved.java - total money held by the bank
Chargefee.java - fees that will be charged when performing specific actions
StockManagement.java - interface for BankManger


currency package:
Our bank allows up to 3 different currencies. Each currency can be converted to another.
        
CNY.java - A type of currency
Convertable.java - interface for currencies to convert to each other
Currency.java - superclass for the three currencies
CurrencyExchange.java - currency exchanger
INR.java - a type of currency
USD.java - a type of currency


gui package:
Pages that will be displayed to users at the frontend, enabling users to interact with the online ATM and take actions. GUI will invoke functions in ATM.java to complete the action.


gui package
        AccountInfo.java - for customers to view their account info
        AddStock.java - page for manager to add stock
        BankReservePage.java - page for manager to view the bank reserves
        BuyStockPage.java - page for customer to buy stocks
        CheckStockPage.java - for customer to check stock
        …


loan package:
Customers can take a loan from the bank if they have a collateral. 
        
Collateral.java - Collateral when a customer wants to take loans
Loan.java - loan information


---------------------------------------------------------------------------
Notes:
JDK version 1.8
---------------------------------------------------------------------------
Example input & output:


When running the program, a login page will pop out and allow the user to login/signup. Choose to login as a customer or manager and when login succeeds, it will go to a new window which allows the customer/manager to take corresponding actions. Follow the instructions and navigate back or proceed with respective buttons. Finally, you can log out with the logout button.