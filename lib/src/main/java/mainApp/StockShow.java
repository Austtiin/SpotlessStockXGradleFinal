// Purpose: This class is used to show the stock of the chemicals in the inventory.

package mainApp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import logger.LoggerStockX;

public class StockShow {
    private static DatabaseConnector dbConn;

    public StockShow(DatabaseConnector dbConn) {
        StockShow.dbConn = dbConn;
    }

    public void showStock() {
        try (final Connection connection = dbConn.connectToDatabase()) {
            if (connection != null) {
            	// SQL query to show the stock of the chemicals in the inventory
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM CurrentInventory");
                ResultSet resultSet = statement.executeQuery();

                
                // Display the stock of the chemicals in the inventory
                while (resultSet.next()) {
                	
                    System.out.println("ChemicalID: " + resultSet.getInt("InventoryID") +
                            ", Chemical Name: " + resultSet.getString("ChemicalName") +
                            ", Container Size: " + resultSet.getString("ContainerSize") +
                            ", Quantity: " + resultSet.getString("CurrentQuantity"));
                }
            } else {
                System.out.println("Error Showing Current Inventory");
            }
        } catch (SQLException e) {
            LoggerStockX.logger.log(Level.SEVERE, "Error in Showing The Stock", e);
        }
    }

    // Purpose: Show the stock of the chemicals in the inventory based on the gallon size.
    public void perGallonShow(int gallon) {
        try (final Connection connection = dbConn.connectToDatabase()) {
            if (connection != null) {
            	// SQL query to show the stock of the chemicals in the inventory based on the gallon size
                String query = "SELECT * FROM CurrentInventory WHERE ContainerSize = " + gallon;
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    try (ResultSet resultSet = statement.executeQuery()) {
                        while (resultSet.next()) {
                        	// Display the stock of the chemicals in the inventory based on the gallon size
                            System.out.println("ChemicalID: " + resultSet.getInt("InventoryID") +
                                    ", Chemical Name: " + resultSet.getString("ChemicalName") +
                                    ", Container Size: " + resultSet.getString("ContainerSize") +
                                    ", Quantity: " + resultSet.getString("CurrentQuantity"));
                        }
                    }
                }
            } else {
                System.out.println("Error: Database connection is null.");
            }
        } catch (SQLException e) {
            LoggerStockX.logger.log(Level.SEVERE, "ERROR SHOWING CHEMICALS", e);
        }
    }
}
