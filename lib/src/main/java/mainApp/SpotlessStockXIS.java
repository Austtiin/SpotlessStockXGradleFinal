// Purpose: Main application class for the SpotlessStockXIS application.
// Description: This class is the main application class for the SpotlessStockXIS application. 
//It contains the main menu and the logic for each menu option.

package mainApp;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;

import logger.LoggerStockX;

//import database.DatabaseConnector;


public class SpotlessStockXIS {
    private DatabaseConnector databaseConnector;
    private StockShow stockShow;
    private AddItem addItem;
    private Scanner scanner;
    private DeleteItem deleteItem;
    private ItemDetails itemDetails;
    private ViewSites viewSites;
    private BOLReport exportBOL;
    private List<SalesTransaction> salesTransactions;

    // Constructor to initialize the database connector and other objects
    public SpotlessStockXIS() {
        this.databaseConnector = new DatabaseConnector();
        this.stockShow = new StockShow(databaseConnector);
        this.addItem = new AddItem(databaseConnector);
        this.scanner = new Scanner(System.in); // Use a single scanner
        this.deleteItem = new DeleteItem(databaseConnector);
        this.salesTransactions = new ArrayList<>();
        this.viewSites = new ViewSites(databaseConnector);
        this.itemDetails = new ItemDetails(databaseConnector);
        this.exportBOL = new BOLReport();
    }

    // Main method to start the application
    public void run() {
        try {
            LoggerStockX.logger.info("SpotlessStockXIS Application Started.");
            // Display the main menu
            System.out.println("Welcome to SpotlessStockX - Your go-to chemical inventory system!");
            inventoryManager();
        } catch (Exception e) {
            LoggerStockX.logger.log(Level.SEVERE, "Error in SpotlessStockXIS application", e);
        } finally {
            // Close the scanner in the finally block
            if (scanner != null) {
                scanner.close();
            }
        }
    }
    
    // Method to display the main menu and handle user input
    private void inventoryManager() {
        try {
            while (true) {
            	// Display the main menu
                LoggerStockX.logger.info("SpotlessStockXIS Menu Accessed.");
                System.out.println("==== Main Menu ====");
                System.out.println("Select an option:");
                System.out.println("1. Add Item");
                System.out.println("2. Check Stock");
                System.out.println("3. Update Stock Information");
                System.out.println("4. Delete Stock Item");
                System.out.println("5. View Delivery Sites");
                System.out.println("6. Export BOL Report");
                System.out.println("7. Search Inventory");

                if (scanner.hasNextInt()) {
                	// Read the user's choice
                    int choice = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character after reading the integer

                    if (choice >= 1 && choice <= 7) {
                        switch (choice) {
                            case 1:
                                itemAdd();
                                break;
                            case 2:
                                stockCheck();
                                break;
                            case 3:
                                stockUpdate();
                                break;
                            case 4:
                                stockDelete();
                                break;
                            case 5:
                                sitesView();
                                break;
                            case 6:
                                exportBOL();
                                break;
                            case 7:
                                searchInventory();
                                break;
                            default:
                                System.out.println("Invalid choice. Please enter a number between 1 and 7.");
                                break;
                        }
                    } else {
                        System.out.println("Invalid choice. Please enter a number between 1 and 7.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a valid integer.");
                    // Consume the invalid input
                }
            }
           
            
            
        } catch (IllegalStateException | NoSuchElementException e) {
            LoggerStockX.logger.log(Level.SEVERE, "Error reading input: " + e.getMessage(), e);
        }
    }




    // Method to add a new item to the inventory
    private void itemAdd() {
        LoggerStockX.logger.info("==== Add Stock Item ====");
        try {
            boolean exit = false;

            while (!exit) {
                System.out.println("==== Adding Item menu ====");
                System.out.println("Select an option:");
                System.out.println("1. Show Current Inventory");
                System.out.println("2. Add Item to Inventory");
                System.out.println("3. Exit");

                if (scanner.hasNextInt()) {
                    int choice = scanner.nextInt();
                    scanner.nextLine();

                    // Based on the user's choice, perform the corresponding action
                    switch (choice) {
                        case 1:
                            System.out.println("Checking Current Inventory...");
                            LoggerStockX.logger.info("Checking Current Inventory...");
                            addItem.ShowCurrent();
                            break;
                        case 2:
                            System.out.println("Please Type the Chemical Name:");
                            String chemicalName = scanner.nextLine();
                            System.out.println("Please Type the Container Size: (5, 15, 30, 55)");
                            String containerSize = scanner.nextLine();

                            if (containerSize.equals("5") || containerSize.equals("15")
                                    || containerSize.equals("30") || containerSize.equals("55")) {
                                System.out.println("Please Type the Current Inventory:");
                                String currentInventory = scanner.nextLine();
                                addItem.itemAdd(chemicalName, containerSize, currentInventory);
                            } else {
                                System.out.println("Invalid Container Size. Please try again.");
                                break;
                            }

                            break;
                        case 3:
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                            break;
                    }

                    // Exit the loop if the user chooses option 3
                    if (choice == 3) {
                        exit = true;
                    }
                    
                } else {
                    // Consume the invalid input
                    System.out.println("Invalid input. Please enter a valid integer.");
                    scanner.nextLine();
                }
            }
        } catch (IllegalStateException | NoSuchElementException e) {
            LoggerStockX.logger.log(Level.SEVERE, "Error reading input: " + e.getMessage(), e);
        }
    }

  
    // Main method to start the application
    public static void main(String[] args) {
        SpotlessStockXIS stockXIS = new SpotlessStockXIS();
        stockXIS.run();
    }
    
    
    // Method to check the stock levels
    private void stockCheck() {
        try {
            boolean exit = false;

            // Loop until the user chooses to exit
            while (!exit) {
                System.out.println("==== Check Stock levels ====");
                System.out.println("Select an option:");
                System.out.println("1. Show All Stock Levels");
                System.out.println("2. Show 5 Gallon Stock Levels");
                System.out.println("3. Show 15 Gallon Stock Levels");
                System.out.println("4. Show 30 Gallon Stock Levels");
                System.out.println("5. Show 55 Gallon Stock Levels");
                System.out.println("6. Exit");

                
                // Read the user's choice
                if (scanner.hasNextInt()) {
                    int choice = scanner.nextInt();
                    scanner.nextLine();

                    // Based on the user's choice, perform the corresponding action
                    switch (choice) {
                        case 1:
                            System.out.println("Checking Stock Levels...");
                            LoggerStockX.logger.info("Checking Stock Levels...");
                            stockShow.showStock();
                            break;
                        case 2:
                            System.out.println("Checking 5 Gallon Stock Levels...");
                            LoggerStockX.logger.info("Checking 5 Gallon Stock Levels...");
                            //
                            stockShow.perGallonShow(5);
                            break;
                        case 3:
                        	// Check the stock levels for 15 gallon containers
                            System.out.println("Checking 15 Gallon Stock Levels...");
                            LoggerStockX.logger.info("Checking 15 Gallon Stock Levels...");
                            
                            stockShow.perGallonShow(15);
                            break;
                        case 4:
                        	// Check the stock levels for 30 gallon containers
                            System.out.println("Checking 30 Gallon Stock Levels...");
                            LoggerStockX.logger.info("Checking 30 Gallon Stock Levels...");
                            stockShow.perGallonShow(30);
                            break;
                        case 5:
                            System.out.println("Checking 55 Gallon Stock Levels...");
                            LoggerStockX.logger.info("Checking 55 Gallon Stock Levels...");
                            stockShow.perGallonShow(55);
                            break;
                        case 6:
                        	// Exit the loop if the user chooses option 6
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                            break;
                    }
                    
                    // Exit the loop if the user chooses option 6
                    if (choice == 6) {
                        exit = true;
                    }
                    
                } else {
                    // Consume the invalid input
                    System.out.println("Invalid input. Please enter a valid integer.");
                    scanner.nextLine();
                }
            }
            //scanner.close();
        } catch (IllegalStateException | NoSuchElementException e) {
            LoggerStockX.logger.log(Level.SEVERE, "Error reading input: " + e.getMessage(), e);
        }
    }

    // Method to update the stock levels
    private void stockUpdate() {
        LoggerStockX.logger.info("==== Update Stock ====");
        System.out.println("Enter the item name to update:");
        String itemName = scanner.nextLine().trim();

        // Get item details from inventory
        ItemDetails.DetailsItems(itemName);

        
        if (itemDetails != null) {
            try {
                System.out.println("Enter the new quantity:");
                int newQuantity = Integer.parseInt(scanner.nextLine().trim());

                System.out.println("Enter the new Container Size:");
                int newContainerSize = Integer.parseInt(scanner.nextLine().trim());

                // Update item information in inventory
                ItemDetails.UpdateInfo(newQuantity, newContainerSize, itemName);
              
                System.out.println("Stock updated successfully!");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer for quantity and container size.");
            }
        } else {
            System.out.println("Item not found. Please check the item name.");
        }
    }

    // Method to delete an item from the stock
    public void stockDelete() {
        LoggerStockX.logger.info("==== Delete Stock ====");
        System.out.println("Enter the item name to delete:");

        try {
            String chemicalName = scanner.nextLine().trim();

            
            //ItemDetails itemDetails = fetchItemDetails(itemName);
            if (!chemicalName.isEmpty()) {
                DeleteItem deleteItem = new DeleteItem(databaseConnector);
                deleteItem.itemDelete(chemicalName);

                
            } else {
                System.out.println("Item name cannot be empty, Try again.");
            }
            //scanner.close();
        } catch (IllegalStateException | NoSuchElementException e) {
            LoggerStockX.logger.log(Level.SEVERE, "Error reading input: " + e.getMessage(), e);
        }
    }

        
   
    // Method to view the delivery sites
    private void sitesView() {
        LoggerStockX.logger.info("==== View Delivery Sites ====");
        System.out.println("Select an option:");
        System.out.println("1. Display All Delivery Sites");
        System.out.println("2. Add New Delivery Site");
        System.out.println("3. Update Delivery Site Information");
        System.out.println("4. Delete Delivery Site");
        System.out.println("5. Exit");
        
        
        
        // Read the user's choice
		if (scanner.hasNextInt()) {
			int choice = scanner.nextInt();
			scanner.nextLine();

			// Based on the user's choice, perform the corresponding action
			switch (choice) {
			case 1:
				LoggerStockX.logger.info("Displaying Delivery Sites...");
		        logger.LoggerStockX.logger.info("Displaying Delivery Sites...");
				viewSites.DisplaySites();
				break;
			case 2:
				viewSites.AddSite();
				break;
			case 3:
				viewSites.UpdateSite();
				break;
			case 4:
				viewSites.deleteSite();
				break;
			case 5:
				break;
			default:
				System.out.println("Invalid choice. Please try again.");
				break;
			}
		} else {
			// Consume the invalid input
			System.out.println("Invalid input. Please enter a valid integer.");
			scanner.nextLine();
		}
        
        LoggerStockX.logger.info("Displaying Delivery Sites...");
        logger.LoggerStockX.logger.info("Displaying Delivery Sites...");
        
       
        
    }

    // Method to export the Bill of Lading (BOL) report
    private void exportBOL() {
        LoggerStockX.logger.info("==== Export BOL Report ====");
        System.out.println("Exporting BOL Report...");
        exportBOL.export();
        
        //TODO: Implement Export BOL Report
    }

    private void searchInventory() {
        LoggerStockX.logger.info("==== Search Inventory ====");
        // TODO: Implement Search Inventory- Waiting for DB

        System.out.println("Enter the keyword to search:");
        String keyword = scanner.nextLine().trim();

        //List<Item> searchResults = searchInventory(keyword);
      
    }

    private void viewSalesTransactions() {
        System.out.println("==== View Sales Transactions ====");
        // TODO: Implement View Sales Transactions

        System.out.println("This feature is under development. Check back later!");
        // Example: List<SalesTransaction> salesTransactions = fetchSalesTransactions();
        
        //Display our Sales Transactions
    }

   
}
