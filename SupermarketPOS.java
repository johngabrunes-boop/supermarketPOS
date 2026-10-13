import java.util.ArrayList;
import java.util.Scanner;

public class SupermarketPOS {

    // =====================================================
    // PRODUCT DATA (Week 10 - Arrays)
    // =====================================================
    static String[] productIDs = {
        "1","2","3","4","5","6","7","8","9","10",
        "11","12","13","14","15","16","17","18","19","20",
        "21","22","23","24","25","26","27","28","29","30",
        "31","32","33","34","35","36","37","38","39","40",
        "41","42","43","44","45","46","47","48","49","50"
    };

    static String[] productNames = {
        "555 Sardines in Tomato Sauce 155g",
        "Century Tuna Flakes in Oil 155g",
        "Argentina Corned Beef 150g",
        "Ligo Sardines Red 155g",
        "Del Monte Corned Beef 150g",
        "Mega Sardinas 155g",
        "Argentina Meat Loaf 150g",
        "Coca-Cola 1.5L",
        "Pepsi 1.5L",
        "Royal Tru-Orange 1.5L",
        "C2 Green Tea Apple 500ml",
        "Zesto Orange Juice 250ml",
        "Nestle Chuckie 250ml",
        "Wilkins Distilled Water 500ml",
        "Piattos Cheese 85g",
        "Chippy Barbecue 110g",
        "Nova Multigrain 78g",
        "Boy Bawang Cornick 100g",
        "Chiz Curls 85g",
        "Pillows Chocolate 38g",
        "Rebisco Crackers 10s",
        "Skyflakes Crackers 10s",
        "Lucky Me Pancit Canton Original 60g",
        "Lucky Me Beef Mami 55g",
        "Payless Xtra Big Canton 80g",
        "Nescafe 3-in-1 Original 30g",
        "Kopiko Black 3-in-1 30g",
        "Great Taste White 30g",
        "Sinandomeng Rice 1kg",
        "Well-Milled Rice 5kg",
        "Brown Sugar 1kg",
        "White Sugar 1kg",
        "Silver Swan Soy Sauce 385ml",
        "Datu Puti Vinegar 385ml",
        "Marca Pina Patis 500ml",
        "Mantika Cooking Oil 1L",
        "Palm Cooking Oil 1L",
        "Alaska Evaporada 370ml",
        "Carnation Condensada 300ml",
        "Bear Brand Powdered Milk 320g",
        "Eden Cheese 160g",
        "Gardenia Classic White Bread",
        "Pandesal (10 pcs)",
        "Eggs Medium (1 dozen)",
        "CDO Chicken Hotdog 500g",
        "Safeguard Soap 130g",
        "Head & Shoulders Shampoo 170ml",
        "Colgate Toothpaste 145g",
        "Rexona Deodorant 50ml",
        "Joy Dishwashing Liquid 495ml"
    };

    static String[] productCategories = {
        "Canned Goods","Canned Goods","Canned Goods","Canned Goods",
        "Canned Goods","Canned Goods","Canned Goods",
        "Beverages","Beverages","Beverages","Beverages","Beverages",
        "Beverages","Beverages",
        "Snacks","Snacks","Snacks","Snacks","Snacks","Snacks","Snacks","Snacks",
        "Instant Noodles","Instant Noodles","Instant Noodles",
        "Coffee","Coffee","Coffee",
        "Rice","Rice","Sugar","Sugar",
        "Condiments","Condiments","Condiments",
        "Cooking Oil","Cooking Oil",
        "Dairy","Dairy","Dairy","Dairy",
        "Bakery","Bakery","Eggs","Frozen & Meat",
        "Personal Care","Personal Care","Personal Care",
        "Personal Care","Household"
    };

    static double[] productPrices = {
        25.00, 32.00, 42.00, 26.50, 55.00, 22.00, 38.00,
        75.00, 72.00, 75.00, 25.00, 15.00, 22.00, 18.00,
        32.00, 28.00, 30.00, 22.00, 28.00, 42.00, 18.00, 22.00,
        18.00, 12.00, 15.00, 10.00, 8.00, 10.00,
        55.00, 260.00, 80.00, 75.00, 32.00, 28.00, 35.00,
        95.00, 90.00, 42.00, 55.00, 185.00, 92.00,
        65.00, 25.00, 95.00, 145.00,
        48.00, 175.00, 95.00, 110.00, 95.00
    };

    // =====================================================
    // CART DATA (Week 11 - ArrayList)
    // =====================================================
    static ArrayList<String>  cartIDs        = new ArrayList<>();
    static ArrayList<String>  cartNames      = new ArrayList<>();
    static ArrayList<Double>  cartPrices     = new ArrayList<>();
    static ArrayList<Integer> cartQuantities = new ArrayList<>();

    // =====================================================
    // STATE
    // =====================================================
    static String cashierName  = "";
    static String selectedLane = "Lane 1";
    static int    receiptNo    = 1001;
    static Scanner scanner     = new Scanner(System.in);

    // =====================================================
    // MAIN
    // =====================================================
    public static void main(String[] args) {
        printBanner();

        // ---- Get cashier name (Week 9 - String trim)
        System.out.print("Enter cashier name: ");
        cashierName = scanner.nextLine().trim();
        if (cashierName.isEmpty()) {
            System.out.println("Cashier name cannot be empty. Exiting.");
            return;
        }

        // ---- Get lane (Week 9 - parseInt)
        System.out.print("Enter lane (1-5) [default 1]: ");
        String laneInput = scanner.nextLine().trim();
        if (!laneInput.isEmpty()) {
            try {
                int laneNum = Integer.parseInt(laneInput);
                selectedLane = "Lane " + laneNum;
            } catch (NumberFormatException e) {
                selectedLane = "Lane 1";
            }
        }

        System.out.println();
        System.out.println("Welcome, " + cashierName + "!");
        System.out.println("Assigned to " + selectedLane + ".");

        // ---- Main menu (Week 6 - while loop)
        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Choose an option: ");

            // ---- Week 4 - switch
            switch (choice) {
                case 1: viewProducts();     break;
                case 2: addProduct();       break;
                case 3: viewCart();         break;
                case 4: removeItem();       break;
                case 5: clearCart();        break;
                case 6: checkout();         break;
                case 7: viewHistory();      break;
                case 8:
                    running = false;
                    System.out.println();
                    System.out.println("Thank you! Goodbye, " + cashierName + "!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }

        scanner.close();
    }

    // =====================================================
    // HELPERS
    // =====================================================
    static void printBanner() {
        System.out.println("========================================");
        System.out.println("        SUPERMARKET POS SYSTEM          ");
        System.out.println("========================================");
    }

    static void printMenu() {
        System.out.println();
        System.out.println("============ MAIN MENU ============");
        System.out.println("1. View Products");
        System.out.println("2. Add Product to Cart");
        System.out.println("3. View Cart");
        System.out.println("4. Remove Product from Cart");
        System.out.println("5. Clear Cart");
        System.out.println("6. Checkout");
        System.out.println("7. Transaction History");
        System.out.println("8. Exit");
        System.out.println("===================================");
    }

    // Week 9 - parseInt
    static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    // Week 9 - parseDouble
    static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    // Week 9 - substring
    static String truncate(String s, int max) {
        if (s.length() > max) {
            return s.substring(0, max - 3) + "...";
        }
        return s;
    }

    // =====================================================
    // 1. VIEW PRODUCTS (Week 10 - Arrays)
    // =====================================================
    static void viewProducts() {
        System.out.println();
        System.out.println("============================= PRODUCT LIST =============================");
        System.out.printf("%-5s %-40s %-18s %10s%n", "ID", "Name", "Category", "Price");
        System.out.println("------------------------------------------------------------------------");

        // Week 6 - for loop
        for (int i = 0; i < productIDs.length; i++) {
            System.out.printf("%-5s %-40s %-18s %10.2f%n",
                productIDs[i], productNames[i], productCategories[i], productPrices[i]);
        }
        System.out.println("========================================================================");
    }

    // =====================================================
    // 2. ADD PRODUCT TO CART
    // =====================================================
    static void addProduct() {
        viewProducts();

        System.out.print("\nEnter Product ID to add (0 to cancel): ");
        String inputId = scanner.nextLine().trim();
        if (inputId.equals("0")) {
            return;
        }

        // ---- Find product in array (Week 6 - for loop)
        int foundIndex = -1;
        for (int i = 0; i < productIDs.length; i++) {
            if (productIDs[i].equals(inputId)) {
                foundIndex = i;
                break;
            }
        }

        // Week 4 - if / else
        if (foundIndex == -1) {
            System.out.println("Product ID '" + inputId + "' not found.");
            return;
        }

        int qty = readInt("Enter quantity: ");
        if (qty <= 0) {
            System.out.println("Quantity must be a positive number.");
            return;
        }

        String pid    = productIDs[foundIndex];
        String pname  = productNames[foundIndex];
        double pprice = productPrices[foundIndex];

        // ---- Merge duplicate (Week 11 - ArrayList set)
        boolean alreadyInCart = false;
        for (int i = 0; i < cartIDs.size(); i++) {
            if (cartIDs.get(i).equals(pid)) {
                cartQuantities.set(i, cartQuantities.get(i) + qty);
                alreadyInCart = true;
                break;
            }
        }

        // Week 4 - if
        if (!alreadyInCart) {
            cartIDs.add(pid);
            cartNames.add(pname);
            cartPrices.add(pprice);
            cartQuantities.add(qty);
        }

        System.out.println("Added " + qty + " x " + pname + " to cart.");
    }

    // =====================================================
    // 3. VIEW CART
    // =====================================================
    static void viewCart() {
        if (cartIDs.isEmpty()) {
            System.out.println("\nCart is empty.");
            return;
        }

        System.out.println();
        System.out.println("=================== SHOPPING CART ===================");
        System.out.printf("%-5s %-30s %5s %10s %12s%n", "No", "Name", "Qty", "Unit", "Subtotal");
        System.out.println("-----------------------------------------------------");

        for (int i = 0; i < cartIDs.size(); i++) {
            double sub = cartPrices.get(i) * cartQuantities.get(i);
            System.out.printf("%-5d %-30s %5d %10.2f %12.2f%n",
                (i + 1),
                truncate(cartNames.get(i), 30),
                cartQuantities.get(i),
                cartPrices.get(i),
                sub);
        }

        System.out.println("-----------------------------------------------------");
        System.out.printf("Total items: %d%n", getTotalQuantity());
        System.out.printf("Subtotal   : PHP %.2f%n", getSubtotal());
        System.out.println("=====================================================");
    }

    // =====================================================
    // 4. REMOVE ITEM
    // =====================================================
    static void removeItem() {
        if (cartIDs.isEmpty()) {
            System.out.println("\nCart is empty.");
            return;
        }
        viewCart();

        int index = readInt("\nEnter row number to remove (1-" + cartIDs.size() + ", 0 to cancel): ");
        if (index == 0) {
            return;
        }
        if (index < 1 || index > cartIDs.size()) {
            System.out.println("Invalid row number.");
            return;
        }

        int i = index - 1;
        System.out.println("Removed: " + cartNames.get(i));
        cartIDs.remove(i);
        cartNames.remove(i);
        cartPrices.remove(i);
        cartQuantities.remove(i);
    }

    // =====================================================
    // 5. CLEAR CART
    // =====================================================
    static void clearCart() {
        if (cartIDs.isEmpty()) {
            System.out.println("\nCart is already empty.");
            return;
        }

        System.out.print("Are you sure you want to clear the cart? (y/n): ");
        String ans = scanner.nextLine().trim().toLowerCase();

        if (ans.equals("y")) {
            cartIDs.clear();
            cartNames.clear();
            cartPrices.clear();
            cartQuantities.clear();
            System.out.println("Cart cleared.");
        } else {
            System.out.println("Cancelled.");
        }
    }

    // =====================================================
    // 6. CHECKOUT (Week 4 switch + Week 5 Nested if)
    // =====================================================
    static void checkout() {
        if (cartIDs.isEmpty()) {
            System.out.println("\nCart is empty. Add products before checkout.");
            return;
        }

        viewCart();

        // ---- Discount menu
        System.out.println();
        System.out.println("Discount options:");
        System.out.println("1. No Discount");
        System.out.println("2. Senior Citizen (20%)");
        System.out.println("3. PWD (20%)");
        System.out.println("4. Student (10%)");
        int dChoice = readInt("Choose discount: ");

        String discountLabel = "No Discount";
        double discountRate  = 0;
        boolean eligible     = false;

        switch (dChoice) {
            case 2: discountLabel = "Senior Citizen"; discountRate = 0.20; break;
            case 3: discountLabel = "PWD";            discountRate = 0.20; break;
            case 4: discountLabel = "Student";        discountRate = 0.10; break;
            default: discountLabel = "No Discount";   discountRate = 0;
        }

        // Week 5 - Nested if
        if (discountRate > 0) {
            System.out.print("Confirm customer is eligible? (y/n): ");
            eligible = scanner.nextLine().trim().equalsIgnoreCase("y");
            if (!eligible) {
                System.out.println("Discount not applied.");
                discountLabel = "No Discount";
                discountRate  = 0;
            }
        }

        double subtotal   = getSubtotal();
        double discount   = eligible ? subtotal * discountRate : 0;
        double finalTotal = subtotal - discount;

        System.out.printf("%nSubtotal       : PHP %.2f%n", subtotal);
        System.out.printf("Discount       : PHP %.2f (%s)%n", discount, discountLabel);
        System.out.printf("FINAL TOTAL    : PHP %.2f%n", finalTotal);

        // ---- Payment menu
        System.out.println();
        System.out.println("Payment methods:");
        System.out.println("1. Cash");
        System.out.println("2. GCash");
        System.out.println("3. Maya");
        System.out.println("4. Debit/Credit Card");
        int pChoice = readInt("Choose payment: ");

        String paymentMethod  = "Cash";
        double amountReceived = 0;
        double change         = 0;

        switch (pChoice) {
            case 2:
                paymentMethod  = "GCash";
                amountReceived = finalTotal;
                change         = 0;
                System.out.println("GCash payment confirmed (simulated).");
                break;
            case 3:
                paymentMethod  = "Maya";
                amountReceived = finalTotal;
                change         = 0;
                System.out.println("Maya payment confirmed (simulated).");
                break;
            case 4:
                paymentMethod  = "Card";
                amountReceived = finalTotal;
                change         = 0;
                System.out.println("Card payment confirmed (simulated).");
                break;
            default:
                paymentMethod = "Cash";
                // Week 6 - while loop
                while (true) {
                    amountReceived = readDouble("Enter amount received: PHP ");
                    if (amountReceived < finalTotal) {
                        System.out.printf("Insufficient. Need at least PHP %.2f%n", finalTotal);
                    } else {
                        change = amountReceived - finalTotal;
                        break;
                    }
                }
        }

        // ---- Print receipt
        printReceipt(subtotal, discount, finalTotal, paymentMethod, amountReceived, change);

        receiptNo++;

        // ---- Clear cart
        cartIDs.clear();
        cartNames.clear();
        cartPrices.clear();
        cartQuantities.clear();

        System.out.println();
        System.out.println("Ready for the next customer.");
    }

    // =====================================================
    // PRINT RECEIPT
    // =====================================================
    static void printReceipt(double subtotal, double discount, double finalTotal,
                             String paymentMethod, double amountReceived, double change) {
        System.out.println();
        System.out.println("========================================");
        System.out.println("        SUPERMARKET POS SYSTEM          ");
        System.out.println("========================================");
        System.out.println("Receipt No.: " + receiptNo);
        System.out.println("Cashier    : " + cashierName);
        System.out.println("Lane       : " + selectedLane);
        System.out.println("----------------------------------------");
        System.out.printf("%-6s %-18s %5s %10s%n", "ID", "Name", "Qty", "Subtotal");
        System.out.println("----------------------------------------");

        for (int i = 0; i < cartIDs.size(); i++) {
            double sub = cartPrices.get(i) * cartQuantities.get(i);
            System.out.printf("%-6s %-18s %5d %10.2f%n",
                cartIDs.get(i),
                truncate(cartNames.get(i), 18),
                cartQuantities.get(i),
                sub);
        }

        System.out.println("----------------------------------------");
        System.out.printf("Total Quantity : %d%n", getTotalQuantity());
        System.out.printf("Subtotal       : PHP %.2f%n", subtotal);
        System.out.printf("Discount       : PHP %.2f%n", discount);
        System.out.printf("FINAL TOTAL    : PHP %.2f%n", finalTotal);
        System.out.println("----------------------------------------");
        System.out.printf("Payment Method : %s%n", paymentMethod);

        if (paymentMethod.equals("Cash")) {
            System.out.printf("Amount Received: PHP %.2f%n", amountReceived);
            System.out.printf("Change         : PHP %.2f%n", change);
        } else {
            System.out.println("Payment        : Confirmed (Simulated)");
        }

        System.out.println("----------------------------------------");
        System.out.println("     Thank you for shopping with us!    ");
        System.out.println("========================================");
    }

    // =====================================================
    // 7. TRANSACTION HISTORY
    // =====================================================
    static void viewHistory() {
        System.out.println();
        System.out.println("============ TRANSACTION HISTORY ============");
        System.out.println("No transactions recorded in this session.");
        System.out.println("==============================================");
    }

    // =====================================================
    // TOTALS
    // =====================================================
    static double getSubtotal() {
        double sum = 0;
        for (int i = 0; i < cartPrices.size(); i++) {
            sum += cartPrices.get(i) * cartQuantities.get(i);
        }
        return sum;
    }

    static int getTotalQuantity() {
        int total = 0;
        for (int i = 0; i < cartQuantities.size(); i++) {
            total += cartQuantities.get(i);
        }
        return total;
    }
}