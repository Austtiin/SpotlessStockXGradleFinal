
// Purpose: Class to display all delivery sites in the database
package mainApp;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;

import logger.LoggerStockX;
import mainApp.DatabaseConnector;

public class ViewSites {
	private DatabaseConnector dbConn;
    private Scanner scanner;
	public ViewSites(DatabaseConnector dbConn) {
		this.dbConn = dbConn;
        this.scanner = new Scanner(System.in);
	}

	 public void DisplaySites() {
	        try (Connection connection = dbConn.connectToDatabase()) {
	            if (connection != null) {
	            	// Recreate the SQL query to retrieve all delivery sites
	                String query = "SELECT * FROM Sites";

	                // execute the query
	                try (PreparedStatement statement = connection.prepareStatement(query);
	                     ResultSet resultSet = statement.executeQuery()) {
	                    System.out.println("==== Delivery Sites ====");
	                    while (resultSet.next()) {
	                        displaySiteInfo(resultSet);
	                        
	                    }
	                    logger.LoggerStockX.logger.log(Level.INFO, "Delivery site information displayed");
	                }
	            } else {
	            	// if the connection is null, print an error message
	                System.out.println("Error: Database connection is null.");
	            }
	           
	        } catch (SQLException e) {
	        	// log any SQL exceptions
	            LoggerStockX.logger.log(Level.SEVERE, "Error retrieving delivery sites", e);
	        }
	    }

	    private void displaySiteInfo(ResultSet resultSet) {
	        try {
	        	// Display the site information
	            System.out.println("Site ID: " + resultSet.getInt("idSites") +
	            		", Site Name: " + resultSet.getString("SiteName") +
	                    ", Address: " + resultSet.getString("SiteAddress") +
	                    ", Contact Name: " + resultSet.getString("SiteContactName") +
	                    ", Phone Number: " + resultSet.getString("SitePhoneNum"));
	        } catch (SQLException e) {
	        	//pulate the table with the delivery site log any SQL exceptions
	            LoggerStockX.logger.log(Level.SEVERE, "Error displaying delivery site info", e);
	        }
	    }

		public void AddSite() {
			
			// Prompt the user to enter the site information
			System.out.println("Enter the site name: ");
			String siteName = scanner.nextLine();
			System.out.println("Enter the site address: ");
			String siteAddress = scanner.nextLine();
			System.out.println("Enter the site contact name: ");
			String siteContactName = scanner.nextLine();
			System.out.println("Enter the site phone number: ");
			String sitePhoneNum = scanner.nextLine();

			// Insert the site information into the database
			try (Connection connection = dbConn.connectToDatabase()) {
				if (connection != null) {
					String query = "INSERT INTO Sites (SiteName, SiteAddress, SiteContactName, SitePhoneNum) VALUES (?, ?, ?, ?)";
					try (PreparedStatement statement = connection.prepareStatement(query)) {
						statement.setString(1, siteName);
						statement.setString(2, siteAddress);
						statement.setString(3, siteContactName);
						statement.setString(4, sitePhoneNum);
						statement.executeUpdate();
						System.out.println("Site added successfully");
						logger.LoggerStockX.logger.log(Level.INFO, "Delivery site added successfully");
					}
				} else {
					System.out.println("Error: Database connection is null.");
				}
			} catch (SQLException e) {
				LoggerStockX.logger.log(Level.SEVERE, "Error adding delivery site", e);
			}
		}

		public void UpdateSite() {
			//Display the current delivery sites
			    System.out.println("Current Delivery Sites:");
			    DisplaySites();

			   //have user enter the site ID and the new site information
			    System.out.println("Enter the ID of the site you want to update:");
			    int siteId = Integer.parseInt(scanner.nextLine());

			    //user enters the new site information
			    System.out.println("Enter the new site address: ");
			    String siteAddress = scanner.nextLine();
			    System.out.println("Enter the new site contact name: ");
			    String siteContactName = scanner.nextLine();
			    System.out.println("Enter the new site phone number: ");
			    String sitePhoneNum = scanner.nextLine();

			    //update the site information in the database
			    try (Connection connection = dbConn.connectToDatabase()) {
			        if (connection != null) {
			            String query = "UPDATE Sites SET SiteAddress = ?, SiteContactName = ?, SitePhoneNum = ? WHERE idSites = ?";
			            try (PreparedStatement statement = connection.prepareStatement(query)) {
			                statement.setString(1, siteAddress);
			                statement.setString(2, siteContactName);
			                statement.setString(3, sitePhoneNum);
			                statement.setInt(4, siteId);
			                int rowsUpdated = statement.executeUpdate();
			                if (rowsUpdated > 0) {
			                	//log the update
			                    System.out.println("Site updated successfully");
			                    LoggerStockX.logger.log(Level.INFO, "Delivery site updated successfully");
			                } else {
			                    System.out.println("No site found with the ID: " + siteId);
			                }
			            }
			        } else {
			            System.out.println("Error: Database connection is null.");
			        }
			    } catch (SQLException e) {
			        LoggerStockX.logger.log(Level.SEVERE, "Error updating delivery site", e);
			    }
			}

		public void deleteSite() {
		    // Display all sites first
		    System.out.println("Current Delivery Sites:");
		    DisplaySites();

		    // Prompt the user to choose a site to delete
		    System.out.println("Enter the ID of the site you want to delete:");
		    int siteId = Integer.parseInt(scanner.nextLine());

		    // Delete the selected site from the database
		    try (Connection connection = dbConn.connectToDatabase()) {
		        if (connection != null) {
		            String query = "DELETE FROM Sites WHERE idSites = ?";
		            try (PreparedStatement statement = connection.prepareStatement(query)) {
		                statement.setInt(1, siteId);
		                int rowsDeleted = statement.executeUpdate();
		                if (rowsDeleted > 0) {
		                    System.out.println("Site deleted successfully");
		                    LoggerStockX.logger.log(Level.INFO, "Delivery site deleted successfully");
		                } else {
		                    System.out.println("No site found with the ID: " + siteId);
		                }
		            }
		        } else {
		            System.out.println("Error: Database connection is null.");
		        }
		    } catch (SQLException e) {
		        LoggerStockX.logger.log(Level.SEVERE, "Error deleting delivery site", e);
		    }
		}

		

		public boolean siteExists(String siteId) {
			// Check if the site exists in the database
			
			try (Connection connection = dbConn.connectToDatabase()) {
				if (connection != null) {
					// Recreate the SQL query to retrieve the site
					String query = "SELECT * FROM Sites WHERE idSites = ?";
					try (PreparedStatement statement = connection.prepareStatement(query)) {
						// Execute the query
				
						statement.setInt(1, Integer.parseInt(siteId));
						try (ResultSet resultSet = statement.executeQuery()) {
							if (resultSet.next()) {
								return true;
							}
						}
					}
				} else {
					System.out.println("Error: Database connection is null.");
				}
			} catch (SQLException e) {
				LoggerStockX.logger.log(Level.SEVERE, "Error checking if site exists", e);
			}
			
			// Return false if the site does not exist
			return false;
		}

		public Customer getCustomerDetails(String siteId) {
			Customer customer = new Customer();
			// Get the customer details for the selected site
            try (Connection connection = dbConn.connectToDatabase()) {
                if (connection != null) {
                    // Recreate the SQL query to retrieve the customer details
                    String query = "SELECT * FROM Sites WHERE idSites = ?";
                    try (PreparedStatement statement = connection.prepareStatement(query)) {
                        // Execute the query
                        statement.setInt(1, Integer.parseInt(siteId));
                        try (ResultSet resultSet = statement.executeQuery()) {
                            if (resultSet.next()) {
                                customer.setName(resultSet.getString("SiteName"));
                                customer.setAddress(resultSet.getString("SiteAddress"));
                                customer.setPhoneNumber(resultSet.getString("SitePhoneNum"));
                            }
                        }
                    }
                } else {
                    System.out.println("Error: Database connection is null.");
                }
            } catch (SQLException e) {
                LoggerStockX.logger.log(Level.SEVERE, "Error retrieving customer details", e);
            }
            // Return the customer details
            return customer;
        }

		public Customer setCustomerDetails(String siteId) {
			// Set the customer details for the selected site
			Customer customer = new Customer();
			try (Connection connection = dbConn.connectToDatabase()) {
				if (connection != null) {
					// Recreate the SQL query to retrieve the customer details
					String query = "SELECT * FROM Sites WHERE idSites = ?";
					try (PreparedStatement statement = connection.prepareStatement(query)) {
						// Execute the query
						statement.setInt(1, Integer.parseInt(siteId));
						try (ResultSet resultSet = statement.executeQuery()) {
							if (resultSet.next()) {
								customer.setName(resultSet.getString("SiteName"));
								customer.setAddress(resultSet.getString("SiteAddress"));
								customer.setPhoneNumber(resultSet.getString("SitePhoneNum"));
							}
						}
					}
				} else {
					System.out.println("Error: Database connection is null.");
				}
			} catch (SQLException e) {
				LoggerStockX.logger.log(Level.SEVERE, "Error retrieving customer details", e);
			}
			return null;
		}
		}
		
	
