package Database;

import java.sql.*;
/*
Database to store bank details
 */
public class BankReservesDatabase extends ExecuteQuery implements database{

    private static final String tableName = "bankReserves";
    private static Connection conn=null;

    public BankReservesDatabase() throws SQLException {
        conn = connect();
        createNewTable();
    }
    protected void createNewTable() throws SQLException {


        // SQL statement for creating a new table
        String sql ="CREATE TABLE IF NOT EXISTS bankReserves    (\n"    //create a new table for bank reserves
                + " date text NOT NULL,\n"
                + " totalMoney REAL NOT NULL \n"
                + ");";
        executeUpdate(sql);
        insert();
    }

    private void insert(){
        insert("2022-12-13",	10000);     //insert initial values
    }

    public void insert(String date, double amount) {
        String sql = "INSERT INTO "+ tableName+"(date,totalMoney) VALUES(?,?)";

        try{
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, date);
            pstmt.setDouble(2, amount);
            pstmt.executeUpdate();
            //System.out.println("records inserted");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static String getDate(){
        try {
            String sql = "SELECT date from " + tableName + ";";
            ResultSet rs = executeQuery(sql);
            if (rs != null && rs.next()) {
                return rs.getString("date");
            }
        }
        catch (SQLException e){
            System.out.println("couldn't fetch date");
        }
        return null;
    }

    public static double getBankMoney(){
        try {
            String sql = "SELECT totalMoney from " + tableName + ";";   //get the money bank has
            ResultSet rs = executeQuery(sql);
            if (rs != null && rs.next()) {
                return rs.getDouble("totalMoney");
            }
        }
        catch (SQLException e){
            System.out.println("couldn't fetch total money");
        }
        return 500000;
    }

    public static void changeDate(String date) throws SQLException {
        String sql = "UPDATE " + tableName + " SET date=\"" + date +"\";";
        executeUpdate(sql);
    }

    public static void changeBankReserve(Double money) throws SQLException {
        String sql = "UPDATE " + tableName + " SET totalMoney=" + money +";";
        executeUpdate(sql);
    }

    public void createNewTables(){
    }

    public void inserts(){
    }
}
