// Purpose: Main class to start and run the application.

package mainApp;

public class Main {
    public static void main(String[] args) { // Start and run instance
        SpotlessStockXIS IS = new SpotlessStockXIS();
        DatabaseConnector dbConnector = new DatabaseConnector();
        IS.run();
    }
}

