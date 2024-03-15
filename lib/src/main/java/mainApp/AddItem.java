// Purpose: AddItem class for adding items to the inventory.
package mainApp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;

import logger.LoggerStockX;

public class AddItem {
    private DatabaseConnector dbConn;

    // Constructor
    public AddItem(DatabaseConnector dbConn) {
        this.dbConn = dbConn;
    }

    // Method to display the current inventory
    public void ShowCurrent() {
    	// Try with resources to automatically close the connection
        try (final Connection connection = dbConn.connectToDatabase()) {
            if (connection != null) {
            	// SQL query to select all items from the CurrentInventory table
                String query = "SELECT * FROM CurrentInventory";
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    try (ResultSet resultSet = statement.executeQuery()) {
                        while (resultSet.next()) {
                            System.out.println("InventoryID: " + resultSet.getInt("InventoryID") +
                                    ", Chemical Name: " + resultSet.getString("ChemicalName") +
                                    ", Container Size: " + resultSet.getString("ContainerSize") +
                                    ", Quantity: " + resultSet.getString("CurrentQuantity") +
                                    ", Last Update: " + resultSet.getString("LastUpdate"));
                        }
                    }
                }
            } else {
                System.out.println("Error: Database connection is null.");
            }
        } catch (SQLException e) {
            LoggerStockX.logger.log(Level.SEVERE, "ERROR ADDING TO INVENTORY", e);
        }
    }

    // Method to add an item to the inventory
    public void itemAdd(String chemicalName, String containerSize, String currentInventory) {
        try (final Connection connection = dbConn.connectToDatabase()) {
            if (connection != null) {
            	
                String query = "INSERT INTO CurrentInventory (ChemicalName, ContainerSize, CurrentQuantity) VALUES (?, ?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                	// Set the parameters for the SQL query
                    statement.setString(1, chemicalName);
                    statement.setString(2, containerSize);
                    statement.setString(3, currentInventory);

                    int rowsAffected = statement.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("Item added successfully");
                    } else {
                        System.out.println("Error adding item to inventory");
                    }
                }
            } else {
                System.out.println("Error: Database connection is null.");
            }
        } catch (SQLException e) {
            LoggerStockX.logger.log(Level.SEVERE, "ERROR ADDING TO INVENTORY", e);
        }
    }
}
