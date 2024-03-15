package mainApp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;

import logger.LoggerStockX;



public class ItemDetails {
	private static DatabaseConnector dbConn;
//    private String chemicalName;
    
    public ItemDetails(DatabaseConnector dbConn) {
    	
    	dbConn = new DatabaseConnector();
    }
        
        public static void DetailsItems(String chemicalName) {
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
                                    displayDetails(resultSet);
                                } while (resultSet.next());
                            } else {
                                System.out.println("No matching items found");
                            }
                        }
                    }
                } else {
                    System.out.println("Error: Database connection is null.");
                }
            } catch (SQLException e) {
                LoggerStockX.logger.log(Level.SEVERE, "Error deleting item from inventory", e);
            } finally {
                
            }
        }

		private static void displayDetails(ResultSet resultSet) {
			try {
                System.out.println("InventoryID: " + resultSet.getInt("InventoryID") +
                        ", Chemical Name: " + resultSet.getString("ChemicalName") +
                        ", Container Size: " + resultSet.getString("ContainerSize") +
                        ", Quantity: " + resultSet.getString("CurrentQuantity") +
                        ", Last Update: " + resultSet.getString("LastUpdate"));
            } catch (SQLException e) {
            	LoggerStockX.logger.log(Level.SEVERE, "Error Displaying Results", e);
                e.printStackTrace();
            }
        }
		
		static void UpdateInfo(int newQuantity, int newContainerSize, String itemName) {
		String NQUANT = Integer.toString(newQuantity);
		String NCONT = Integer.toString(newContainerSize);
		
			try (Connection connection = dbConn.connectToDatabase()) {
				if (connection != null) {
					// SQL query to update the item details
					String query = "UPDATE CurrentInventory SET ContainerSize = ?, CurrentQuantity = ? WHERE ChemicalName = ?";
					
					
					// Prepare and execute the query
					try (PreparedStatement statement = connection.prepareStatement(query)) {
						statement.setString(1, NQUANT);
						statement.setString(2, NCONT);
						statement.setString(3, itemName);

						int rowsAffected = statement.executeUpdate();

						if (rowsAffected > 0) {
							System.out.println("Item details updated successfully");
						} else {
							System.out.println("No matching items found");
						}
					}
				} else {
					System.out.println("Error: Database connection is null.");
				}
			} catch (SQLException e) {
				LoggerStockX.logger.log(Level.SEVERE, "Error updating item details", e);
			}

		}
			
		}



