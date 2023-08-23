package Database;

import java.sql.SQLException;
/*
Interface which specifies what functions all the databases should have
 */
public interface database{
    void createNewTables() throws SQLException;
    void inserts();
}
