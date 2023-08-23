package Database;

import Main.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
/*
Database to maintain a list of all transactions that the bank encounters
 */
public class TransactionDatabase extends ExecuteQuery implements database{
    private static final String tableName = "Transactions";
    private static Connection conn=null;

    public TransactionDatabase() throws SQLException {
        conn=connect();
        createNewTable();
    }
    private void createNewTable() throws SQLException {


        // SQL statement for creating a new table
        String sql ="CREATE TABLE IF NOT EXISTS Transactions (\n"
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " date text NOT NULL,\n"
                + " timestamp text NOT NULL,\n"
                + " sendingAmount REAL NOT NULL, \n"
                + " receivingAmount REAL NOT NULL, \n"
                + " receivingCurrency text NOT NULL, \n"
                + " sendingCurrency text NOT NULL, \n"
                + " receiver text NOT NULL, \n"
                + " sender text NOT NULL \n"
                + ");";
        Statement statement = conn.createStatement();
        statement.executeUpdate(sql);
        //insert();
    }

    public static void insert(Transaction t) {
        String sql = "INSERT INTO "+ tableName+"( date,timestamp,sendingAmount,receivingAmount,sendingCurrency," +
                "receivingCurrency,receiver,sender) VALUES(?,?,?,?,?,?,?,?)";

        try{
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, t.getDate());
            pstmt.setString(2, t.getTimeStamp());
            pstmt.setDouble(3, t.getSendingAmount());
            pstmt.setDouble(4, t.getReceivingAmount());
            pstmt.setString(5, t.getSendingCurrency());
            pstmt.setString(6, t.getReceivingCurrency());
            pstmt.setString(7, t.getTo());
            pstmt.setString(8, t.getFrom());
            pstmt.executeUpdate();
            //System.out.println("records inserted");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void createNewTables(){
    }

    public void inserts(){
    }
}
