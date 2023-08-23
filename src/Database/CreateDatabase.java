package Database;

import Exceptions.FancyBankException;

import java.sql.SQLException;

/*
Main class to initialize the database
 */
public class CreateDatabase {

    public static void initializeDatabase() throws SQLException, FancyBankException {
        ExecuteQuery.createDatabase();
    }
}
