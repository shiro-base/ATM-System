package Database;

import Exceptions.FancyBankException;
import Main.Customer;
import StockMarket.Price;
import StockMarket.Stock;
import account.SecurityAccount;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
Database to represents all stocks
 */
public class StocksDatabase extends ExecuteQuery implements database{
    private static final String tableName = "stocks";
    private static Connection conn=null;

    public StocksDatabase(){
        conn=connect();
    }
    public StocksDatabase(boolean initialize) throws FancyBankException, SQLException {
        this();
        if(initialize){
        createNewTable();}

    }
    private void createNewTable() throws FancyBankException, SQLException {


        // SQL statement for creating a new table
        String sql ="CREATE TABLE IF NOT EXISTS stocks (\n"
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " name text NOT NULL,\n"
                + " price text NOT NULL, \n"
                + " quantity INTEGER NOT NULL, \n"
                + " priceHistory text \n"
                + ");";
        Statement statement = conn.createStatement();
        statement.executeUpdate(sql);
        insert();
    }

    private void insert() throws FancyBankException, SQLException {
        if(getAllStocks().size()==0) {
            insert(new Stock("Apple", new Price(40.5, 100), 1));
            insert(new Stock("Samsung", new Price(4050.9, 11230), 15));
            insert(new Stock("H.P", new Price(100.5, 133), 124));
            insert(new Stock("Rolex", new Price(129.5, 1244), 456));
            insert(new Stock("BCJ", new Price(3724.0, 234), 754));
            insert(new Stock("Packard", new Price(12983, 1245), 123));
            insert(new Stock("Bridge", new Price(1283, 28947), 35));
            insert(new Stock("Route", new Price(9123, 3442), 215));
            insert(new Stock("Pepsi", new Price(21643, 92034), 234));
            insert(new Stock("Sprite", new Price(1239, 32), 2144));
            insert(new Stock("Dell", new Price(130234, 2444), 7248));
        }

    }

    public static void insert(Stock s){ //insert a stock
        insert(s.getName(),s.getPrice().toString(),s.getQuantity(),s.getPriceHistory().toString());
    }

    public static void modifyStock(Stock oldStock,Stock newStock) throws SQLException { //modify a stock
        String sql = "UPDATE stocks SET name=\"" + newStock.getName() + "\" ,price=\""+ newStock.getPrice().toString()
                + "\" ,quantity= " + newStock.getQuantity() + " ,priceHistory=\"" + newStock.getPriceHistory().toString()
                + "\" where id=" + oldStock.getId()+";";
        executeUpdate(sql);
    }

    public static void modifyStockQuantity(int oldStock,int qty) throws SQLException { //modify a stock
        String sql = "UPDATE stocks SET quantity= " + qty + " where id=" + oldStock +";";
        executeUpdate(sql);
    }

    public static void updateStockPrice(Stock old,Stock newS) throws SQLException { //update a stock
        modifyStock(old,newS);
        updateCustomerStocks(old,newS.getPrice().getCurrentPrice());
    }

    private static void updateCustomerStocks(Stock old, double news){   //update stocks a customer has
        List<Customer> allCustomers = CustomerDatabase.getAllCustomers();
        allCustomers.forEach(customer -> {
            SecurityAccount securityAccount = customer.hasSecurityAccount();
            if(securityAccount!=null){
                try {
                    securityAccount.modifyPrice(old,news);
                } catch (FancyBankException e) {
                    System.out.println("Couldn't change stock price for customer");
                }
            }
            try {
                CustomerDatabase.updateAccount(customer.getLogin().getUserName(),getListAsString(customer.getAccounts()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
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

    private static void insert(String name,String price,int qty,String priceHistory) {  //insert new stocks
        String sql = "INSERT INTO "+ tableName+"(name,price,quantity,priceHistory) VALUES(?,?,?,?)";

        try{
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, price);
            pstmt.setInt(3, qty);
            pstmt.setString(4,priceHistory);
            pstmt.executeUpdate();
            //System.out.println("records inserted");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<Stock> getAllStocks() throws SQLException, FancyBankException {  //get all stocks in the market
        String sql = "SELECT * from " + tableName + ";";
        ResultSet rs = executeQuery(sql);
        List<Stock> stocks = new ArrayList<>();
        while(rs!=null && rs.next()){
            Stock stock = new Stock(rs.getInt("id"),rs.getString("name")
                    ,new Price(rs.getString("price")),rs.getInt("quantity"),
                    rs.getString("priceHistory"));
            stocks.add(stock);
        }
        return stocks;
    }
    public void createNewTables(){
    }

    public void inserts(){
    }

}
