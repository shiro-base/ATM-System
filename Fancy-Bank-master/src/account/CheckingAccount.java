/*
This class is a checking account. It inherits the general Account class.
 */

package account;

import currency.Currency;
import static Utils.Constants.*;

public class CheckingAccount<T extends Currency> extends Account<T>{
    public CheckingAccount(T deposit) {
        super(deposit, CHECKING_ACCOUNT);
    }

    public CheckingAccount(T deposit,String curr,String id){
        super(deposit,curr,CHECKING_ACCOUNT,id);
    }
}
