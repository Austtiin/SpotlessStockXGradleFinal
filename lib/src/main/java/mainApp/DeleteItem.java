// Purpose: Delete an item from the inventory by searching for it by name and then confirming the deletion.


package mainApp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;

import logger.LoggerStockX;

//
public class DeleteItem {
    private DatabaseConnector dbConn;
    private Scanner scanner;
//    private String chemicalName;
    public DeleteItem(DatabaseConnector dbConn) {
        this.dbConn = dbConn;
        this.scanner = new Scanner(System.in);
        
    }

    // Purpose: Search for an item by name and then confirm the deletion.
    public void itemDelete(String chemicalName) {
        try (Connection connection = dbConn.connectToDatabase()) {
            if (connection != null) {
            	// SQL query to search for an item by name
                String query = "SELECT * FROM CurrentInventory WHERE ChemicalName LIKE ?";

                // Prepare and execute the query
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setString(1, "%" + chemicalName + "%");

                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (resultSet.next()) {
                            do {
                                displayItemDetails(resultSet);

                                deleteConfirmedItem(resultSet.getString("ChemicalName"));
                            } while (resultSet.next());
                        } else {
                            System.out.println("No matching items found");
                        }
                    }
                }
                
                //scanner.close(); - ISSUE HERE OR IN MENU RESIDUAL INT.
                //ISSUE RESLOVED - SCANNER INSTANCE WAS CLOSED IN THE FINALLY BLOCK.
                // IMPACT TO APPLICATION ISOLATES NEED TO JUST LEAVE IT OPEN FOR THE TIME BEING.
                
            } else {
                System.out.println("Error: Database connection is null.");
            }
        } catch (SQLException e) {
            LoggerStockX.logger.log(Level.SEVERE, "Error deleting item from inventory", e);
        } finally {
            
        }
    }

    // Purpose: Display the details of the item found.
    private void displayItemDetails(ResultSet resultSet) throws SQLException {
    	// Display the details of the item found
        System.out.println("Potential match found:");
        System.out.println("ChemicalID: " + resultSet.getInt("InventoryId") +
                ", Chemical Name: " + resultSet.getString("ChemicalName") +
                ", Container Size: " + resultSet.getString("ContainerSize") +
                ", Quantity: " + resultSet.getString("CurrentQuantity"));
    }

    // Purpose: Delete the item from the inventory.
    private void deleteConfirmedItem(String chemicalName) {
        try (Connection connection = dbConn.connectToDatabase()) {
            if (connection != null) {
            	// SQL query to delete the item from the inventory
                String deleteQuery = "DELETE FROM CurrentInventory WHERE ChemicalName = ?";

                try (PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
                    deleteStatement.setString(1, chemicalName);

                    // Execute the delete query
                    int rowsAffected = deleteStatement.executeUpdate();

                    // Check if the item was deleted successfully
                    if (rowsAffected > 0) {
                        System.out.println("Item deleted successfully");
                    } else {
                        System.out.println("Error: Item not found or deletion unsuccessful");
                    }
                }
            } else {
                System.out.println("Error: Database connection is null.");
            }
            // Catch and log any errors
        } catch (SQLException e) {
            LoggerStockX.logger.log(Level.SEVERE, "Error deleting item from inventory", e);
        } finally {
			// Close the scanner
			//scanner.close();
        }
    }
}
