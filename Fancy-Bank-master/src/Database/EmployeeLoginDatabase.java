package Database;

import Main.Customer;
import Main.Login;
import bank.BankManager;

import java.sql.*;

/*
Database to store login information of employees
 */
public class EmployeeLoginDatabase extends ExecuteQuery implements database{
    private static final String tableName = "EmployeeLogin";
    private static Connection conn=null;

    public EmployeeLoginDatabase() throws SQLException {
        conn=connect();
        createNewTable();

    }
    private void createNewTable() throws SQLException {


        // SQL statement for creating a new table
        String sql ="CREATE TABLE IF NOT EXISTS EmployeeLogin (\n"
                + " username text PRIMARY KEY,\n"
                + " password INTEGER NOT NULL,\n"
                + " security INTEGER NOT NULL \n"
                + ");";
        executeUpdate(sql);
        insert();
    }

    private void insert(){
        if(!isExists("admin1")) {
            insert("admin1", "admin", "-1406346886");
        }
    }

    public static boolean verifyBankManager(String username,String password){       //verify the username and password of bank manager
        String sql = "SELECT password from " + tableName + " where username = \"" + username +"\";";
        try{
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while(rs!=null && rs.next()){
                if(rs.getInt("password")==password.hashCode()){
                    return true;
                }
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static boolean isExists(String username) {
        try {
            String sql = "SELECT * from " + tableName + " where username = \"" + username + "\";";
            ResultSet rs = executeQuery(sql);
            if (rs != null && rs.next()) {
                return true;
                 }

        } catch (SQLException e) {
            System.out.println("Couldn't run query. Please check back later.");
        }
        return false;
    }

    protected void insert(String username,String password,String security) {
        String sql = "INSERT INTO "+ tableName+"(username, password,security) VALUES(?,?,?)";

        try{

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setInt(2, password.hashCode());
            pstmt.setInt(3, security.hashCode());
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
