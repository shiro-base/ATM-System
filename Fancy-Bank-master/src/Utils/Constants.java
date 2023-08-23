package Utils;

import java.time.format.DateTimeFormatter;

public class Constants {

    public static final String EMAIL_REGEX ="^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";


    //ERRORS
    public static final String INVALID_EMAIL_ADDRESS = "invalid email address";
    public static final String CUSTOMER_REGISTERED = "Customer registered";
    public static final String USERNAME_TAKEN = "username already exists";
    public static final String SUCCESSFULL_LOGIN = "Successfully logged in";

    public static final String UNSUCCESSFULL_LOGIN = "Some errors occurred, please check your username and password";

    public static final String INVALID_LOGIN = "Invalid login type";
    public static final String INVALID_WITHDRAW = "Invalid Operation: not enough fund to withdraw.";
    public static final String BANK_BRANCH = "local";

    public static final String  CUSTOMER = "Customer";
    public static final String  MANAGER = "Manager";

    public static final String USD = "USD";
    public static final String INR = "INR";
    public static final String CNY = "CNY";

    public static final String feeLevel1 = "tier1";
    public static final String feeLevel2 = "tier2";

    public static final String BANK_NO_MONEY = "Bank doesn't have enough fund";

    public static final String SUCCESS = "Success";
    public static final String FAILED = "Failed";
    public static final String SAVINGS_ACCOUNT = "Savings";
    public static final String SECURITY_ACCOUNT = "Security";
    public static final String CHECKING_ACCOUNT = "Checking";
    public static final String INVALID_ACCOUNT_TYPE = "Invalid account type";
    public static final String INVALID_CURRENCY_TYPE = "Invalid Currency type";


    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static final double MAX_DEPOSIT = 500000;

    public static final String MAX_DEPOSIT_EXCEEDED = "You can't deposit more than " + MAX_DEPOSIT + " at a time. Please choose a lower amount.";

    public static final String NEGATIVE_AMOUNT = "You can't enter a negative amount. Please enter a positive amount.";

    public static final String EXCEED_DEPOSIT = "You don't have enough money";
    public static final String NO_CUSTOMER_FOUND = "No such customer found";
    public static final String PERMISSION_DENIED = "Permission denied.";
    public static final String INCORRECT_DATE_FORMAT = "Please enter date in correct format";
}
