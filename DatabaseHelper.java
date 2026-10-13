import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseHelper {

    // XAMPP defaults
    static String url  = "jdbc:mysql://localhost:3306/pos_db";
    static String user = "root";
    static String pass = "";

    // Single shared connection for the whole session
    static Connection conn = null;

    // =====================================================
    // CONNECT
    // =====================================================
    static void connect() {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, pass);
            System.out.println("[Database] Connected to MySQL.");
        } catch (ClassNotFoundException e) {
            System.out.println("[Database] Driver not found. Add mysql-connector-j to classpath.");
        } catch (SQLException e) {
            System.out.println("[Database] Connection failed: " + e.getMessage());
            System.out.println("[Database] Make sure XAMPP MySQL is running and 'pos_db' exists.");
        }
    }

    // =====================================================
    // DISCONNECT
    // =====================================================
    static void disconnect() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("[Database] Connection closed.");
            }
        } catch (SQLException e) {
            System.out.println("[Database] Error closing connection: " + e.getMessage());
        }
    }

    // =====================================================
    // SEED PRODUCTS (only the first time)
    // =====================================================
    static void seedProductsIfEmpty(String[] ids, String[] names,
                                    String[] cats, double[] prices) {
        if (conn == null) return;

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM products");
            rs.next();
            int count = rs.getInt(1);
            rs.close();
            st.close();

            // ---- If products already exist, skip
            if (count > 0) {
                System.out.println("[Database] Products table already has " + count + " items.");
                return;
            }

            // ---- Insert each product (Week 6 - for loop)
            Statement insert = conn.createStatement();
            for (int i = 0; i < ids.length; i++) {
                String sql = "INSERT INTO products(id, name, category, price) VALUES('"
                           + ids[i] + "', '"
                           + names[i].replace("'", "''") + "', '"
                           + cats[i] + "', "
                           + prices[i] + ")";
                insert.executeUpdate(sql);
            }
            insert.close();
            System.out.println("[Database] Seeded " + ids.length + " products.");

        } catch (SQLException e) {
            System.out.println("[Database] Seed error: " + e.getMessage());
        }
    }

    // =====================================================
    // SAVE A TRANSACTION (called at checkout)
    // =====================================================
    static void saveTransaction(int receiptNo, String cashier, String lane,
                                double subtotal, double discount, double finalTotal,
                                String paymentMethod, double amountReceived, double change,
                                ArrayList<String> ids,
                                ArrayList<String> names,
                                ArrayList<Integer> qtys,
                                ArrayList<Double> prices) {
        if (conn == null) {
            System.out.println("[Database] No connection — sale not saved.");
            return;
        }

        try {
            Statement st = conn.createStatement();

            // ---- Insert the main transaction row
            String insertTx = "INSERT INTO transactions "
                + "(receipt_no, cashier, lane, subtotal, discount, final_total, "
                + "payment_method, amount_received, change_amount) VALUES ("
                + receiptNo + ", '"
                + cashier.replace("'", "''") + "', '"
                + lane + "', "
                + subtotal + ", "
                + discount + ", "
                + finalTotal + ", '"
                + paymentMethod + "', "
                + amountReceived + ", "
                + change + ")";
            st.executeUpdate(insertTx);

            // ---- Insert each line item (Week 6 - for loop)
            for (int i = 0; i < ids.size(); i++) {
                double lineSub = prices.get(i) * qtys.get(i);
                String insertItem = "INSERT INTO transaction_items "
                    + "(receipt_no, product_id, product_name, quantity, unit_price, subtotal) "
                    + "VALUES ("
                    + receiptNo + ", '"
                    + ids.get(i) + "', '"
                    + names.get(i).replace("'", "''") + "', "
                    + qtys.get(i) + ", "
                    + prices.get(i) + ", "
                    + lineSub + ")";
                st.executeUpdate(insertItem);
            }

            st.close();
            System.out.println("[Database] Sale saved (receipt #" + receiptNo + ").");

        } catch (SQLException e) {
            System.out.println("[Database] Save error: " + e.getMessage());
        }
    }

    // =====================================================
    // PRINT TRANSACTION HISTORY (reads from MySQL)
    // =====================================================
    static void printTransactionHistory() {
        if (conn == null) {
            System.out.println("No database connection.");
            return;
        }

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(
                "SELECT receipt_no, cashier, lane, final_total, payment_method "
                + "FROM transactions ORDER BY receipt_no DESC");

            boolean any = false;
            // Week 6 - while loop
            while (rs.next()) {
                any = true;
                System.out.println(
                    "Receipt #" + rs.getInt("receipt_no")
                    + " | Cashier: " + rs.getString("cashier")
                    + " | " + rs.getString("lane")
                    + " | PHP " + String.format("%.2f", rs.getDouble("final_total"))
                    + " | " + rs.getString("payment_method"));
            }
            if (!any) {
                System.out.println("No transactions yet.");
            }

            rs.close();
            st.close();

        } catch (SQLException e) {
            System.out.println("[Database] Read error: " + e.getMessage());
        }
    }
}