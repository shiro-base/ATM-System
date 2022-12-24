package Database;

import Exceptions.FancyBankException;

import java.sql.*;

/*
given an sql query - create a connection to database and execute query
 */
public class ExecuteQuery {

    private static String databaseName = "FancyBank.db";
    private static String connectionUrl = "jdbc:sqlite:src/Database/" + databaseName;
    private static Connection conn=null;

    protected static Connection connect() {
        if(conn==null){
            try {
                conn = DriverManager.getConnection(connectionUrl);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return conn;
    }




    protected static ResultSet executeQuery(String sql){        //run DML queries
        try{
            PreparedStatement pstmt = conn.prepareStatement(sql);
            return pstmt.executeQuery();
            //System.out.println("records inserted");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    protected static void executeUpdate(String sql) throws SQLException {   //to run DDL queries
        Statement statement = conn.createStatement();
        statement.executeUpdate(sql);
    }



    public static void createDatabase() throws SQLException, FancyBankException {

        ExecuteQuery app = new ExecuteQuery();
        app.createNewDatabase();
        new CustomerLoginDatabase();
        new EmployeeLoginDatabase();
        new CustomerDatabase();
        new StocksDatabase(true);
        new TransactionDatabase();
        new BankReservesDatabase();
    }

    private void createNewDatabase() {      //create the database


        try {
            Connection conn = DriverManager.getConnection(connectionUrl);
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void selectAll(String tableName){
        String sql = "SELECT * FROM " + tableName;

        try {
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            // loop through the result set
            while (rs.next()) {
//                System.out.println(rs.getString("username") +  "\t" +
//                        rs.getString("password"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}  