/*
    abstract Bank class, hold functions that should be shared by all banks
 */
package bank;

import java.sql.SQLException;

public abstract class BankAbstract {

    public abstract BankReserved getTotalReservedMoney() throws SQLException;

    public abstract String getBankName();

    public abstract boolean isBankrupt();
}
