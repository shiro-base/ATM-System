package Database;

import Main.Customer;
import Main.Login;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/*
Class to operate on the customer database
 */

public class CustomerDatabase extends ExecuteQuery implements database{
    private static final String tableName = "customers";
    private static Connection conn=null;

    public CustomerDatabase() throws SQLException {
        conn = connect();
        createNewTable();
    }
    private void createNewTable() throws SQLException {


        // SQL statement for creating a new table
        String sql ="CREATE TABLE IF NOT EXISTS customers    (\n"   //create the table
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " name text NOT NULL,\n"
                + " username text NOT NULL,\n"
                + " address text NOT NULL,\n"
                + " email text NOT NULL,\n"
                + " transactionHistory text, \n"
                + " accounts text, \n"
                + " loans text \n"
                + ");";
        executeUpdate(sql);
    }



    public static Customer getCustomer(String username) {       //get customer based on username
        try {
            String sql = "SELECT * from " + tableName + " where username = \"" + username + "\";";
            ResultSet rs = executeQuery(sql);
            if (rs != null && rs.next()) {
                return new Customer(rs.getString("name"), new Login(rs.getString("username"),"",""),
                        rs.getInt("id"), rs.getString("address"), rs.getString("email"),
                        rs.getString("transactionHistory"), rs.getString("loans"), rs.getString("accounts"));
            }

        } catch (SQLException e) {
            System.out.println("Couldn't run query. Please check back later.");
        }
        return null;
    }

    public static boolean registerCustomer(Login login, Customer c){    //register a customer
        //get security from customer
        if(getCustomer(login.getUserName())==null){
            CustomerLoginDatabase.insert(login.getUserName(), login.getPinHashCode().hashCode(),-1);
            insert(c.getName(),c.getLogin().getUserName(),c.getAddress(),c.getEmail(),getListAsString(c.getTransactionHistory()),getListAsString(c.getLoans()),getListAsString(c.getAccounts()));
            return true;

        }
        return false;
    }

    private static String getListAsString(List li){
        StringBuilder str = new StringBuilder();
        li.forEach(t->{
            str.append(t.toString()).append(",");
        });
        if(!str.toString().equals("")) {
            return str.substring(0, str.lastIndexOf(","));
        }
        return str.toString();
    }

    public static void updateTxnHistory(String username,String txnHistory) throws SQLException {        //update the transaction history of a customer
        String sql = "UPDATE customers SET transactionHistory= \"" + txnHistory+
                "\" where username= \"" + username+"\";";
        executeUpdate(sql);

    }

    public static String getLoans(String username){     //get the loans of the customer
        try {
            String sql = "SELECT loans from " + tableName + " where username = \"" + username + "\";";
            ResultSet rs = executeQuery(sql);
            if (rs != null && rs.next()) {
                return rs.getString("loans");
            }
        }
        catch (SQLException e){
            System.out.println("Couldn't run query. Please check back later.");
        }
        return null;
    }

    public static String getTransactionHistory(String username){
        try {
            String sql = "SELECT transactionHistory from " + tableName + " where username = \"" + username + "\";";
            ResultSet rs = executeQuery(sql);
            if (rs != null && rs.next()) {
                return rs.getString("transactionHistory");
            }
        }
        catch (SQLException e){
            System.out.println("Couldn't run query. Please check back later.");
        }
        return null;
    }

    public static void updateAccount(String username, String account) throws SQLException {
        String sql = "UPDATE customers SET accounts= \"" + account+
                "\" where username= \"" + username+"\";";
        executeUpdate(sql);
    }

    public static List<Customer> getAllCustomers() {        //get all the accounts of the customer
        try {
            String sql = "SELECT * from customers;";
            ResultSet rs = executeQuery(sql);
            List<Customer> customers = new ArrayList<>();
            while (rs != null && rs.next()) {
                Customer c = new Customer(rs.getString("name"), new Login(rs.getString("username"), ""),
                        rs.getInt("id"), rs.getString("address"), rs.getString("email"),
                        rs.getString("transactionHistory"), rs.getString("loans"), rs.getString("accounts"));
                customers.add(c);
            }
            return customers;
        }
        catch (SQLException e){
            System.out.println("Unable to run query");
        }
        return new ArrayList<>();
    }

    public static String getCustomerAccounts(String username) {
        try {
            String accSql = "SELECT accounts from customers where username=\"" + username + "\";";
            ResultSet rs = executeQuery(accSql);
            if (rs != null && rs.next()) {
                return rs.getString("accounts");
            }
        }
        catch (SQLException e){
            System.out.println("error while running query");
        }
        return  null;
    }

    public static void addAccount(String username, String account) throws SQLException {
        String sql = "UPDATE customers SET accounts= \"" + account +
                    "\" where username= \"" + username + "\";";
            executeUpdate(sql);
        Customer s = getCustomer(username);
        System.out.println(s);
    }

    public static void updateLoans(String username, String loan) throws SQLException {
        String sql = "UPDATE customers SET loans= \"" + loan +
                    "\" where username= \"" + username + "\";";
            executeUpdate(sql);
    }

    public void insert(Customer c){
        //add address and email
        insert(c.getName(),c.getLogin().getUserName(),"","",getListAsString(c.getTransactionHistory()),getListAsString(c.getLoans()),getListAsString(c.getAccounts()));
    }



    protected static void insert(String name, String username, String address, String email, String txnHistory, String loans,String accounts) {
        if (getCustomer(username) == null) {
            String sql = "INSERT INTO " + tableName + "(name,username,address,email,transactionHistory,loans,accounts) VALUES(?,?,?,?,?,?,?)";

            try {
                if(conn==null){
                    conn=connect();
                }
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, name);
                pstmt.setString(2, username);
                pstmt.setString(3, address);
                pstmt.setString(4, email);
                pstmt.setString(5, txnHistory);
                pstmt.setString(6, loans);
                pstmt.setString(7, accounts);
                pstmt.executeUpdate();
                //System.out.println("records inserted");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    public void createNewTables(){
    }

    public void inserts(){
    }
}