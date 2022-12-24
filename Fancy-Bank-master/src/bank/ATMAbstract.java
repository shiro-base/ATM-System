/*
    an abstracrt classs, have basic abstract functions shared by all ATMs
 */
package bank;

import Main.Transaction;

import java.sql.SQLException;
import java.util.List;

public abstract class ATMAbstract {

    public abstract String login(String t,String u,String p) throws SQLException;

    public abstract boolean logout();

    public abstract boolean forgotPassword(String u,String s);

    public abstract List<Transaction> showDailyReport();

}
