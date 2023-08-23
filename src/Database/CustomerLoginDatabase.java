package Database;


import java.sql.*;

/*
Class to operate on customer login
 */
public class CustomerLoginDatabase extends ExecuteQuery implements database{

    private static final String tableName = "LoginInfo";
    private static Connection conn=null;

    public CustomerLoginDatabase() throws SQLException {
        conn=connect();
        createNewTable();
    }
    private  void createNewTable() throws SQLException {


        // SQL statement for creating a new table
        String sql ="CREATE TABLE IF NOT EXISTS LoginInfo (\n"
                + " username text PRIMARY KEY,\n"
                + " password INTEGER NOT NULL,\n"
                + " sec INTEGER NOT NULL \n"
                + ");";
        Statement statement = conn.createStatement();
        statement.executeUpdate(sql);

        insert();
    }

    public static boolean verifyPassword(String username, String password){ //verifies the password of a user
        String sql = "SELECT password from " + tableName + " where username =\"" + username +"\";";
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

    public static boolean forgotPassword(String username, String sec){  //forgot password - check security answer
        String sql = "SELECT sec from " + tableName + " where username = \"" + username +"\";";
        try{
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while(rs!=null && rs.next()){
                if(rs.getInt("sec")==sec.hashCode()){
                    return true;
                }
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static void setNewPassword(String username, String password) throws SQLException { //set new password
        String sql = "UPDATE " + tableName + " SET password=" + password.hashCode() +
                "WHERE username=\"" + username + "\";";
        Statement statement = conn.createStatement();
        statement.executeUpdate(sql);
    }



    private void insert(){
        if(!isExists("smritis")) {      //insert sample values - no need as it is hash values - we dont know the actual password but okay
            insert("smritis", -1823196413, -1119580796);
            insert("rahul01", -994672976, -508618265);
            insert("dylan52", -7193837, 1095664492);
            insert("julie57", -1627876682, 389284766);
            insert("todd91", -471340378, 1097468315);
            insert("ben99", 1038340067, 280098357);
        }
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

    public static void insert(String username,int password,int sec) {
        String sql = "INSERT INTO "+ tableName+"(username, password,sec) VALUES(?,?,?)";

        try{
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setInt(2, password);
            pstmt.setInt(3, sec);
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
