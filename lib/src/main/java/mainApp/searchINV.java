// Description: This class is used to search the inventory for a specific item.
// Purpose: Search the inventory for a specific item.
// Austin Stephens
// 02/17/2024
// Professor Kumar
// Advanced Java Programming
// COP3805C

package mainApp;
import java.util.HashMap;
import java.util.Map;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class searchINV {
    private Map<String, Integer> inventory;
    
    
    protected searchINV() {
		this.inventory = new HashMap<>(); //Testing inventory for now until DB setup
		
		
		inventory.put("GOLDEN KNIGHT", 20);
        inventory.put("SURFACE ARMOUR", 6);
        inventory.put("HELLCAT", 30);
        inventory.put("BALSAM FROST BURST", 42);
        
	}
    
    
    protected void search(String searchQuery) {
        // Implement logic to search the inventory.
    	// Then Display
		// INP = new Scanner(System.in);
    	    boolean found = false; // Set found to false initially / default
 
    	    for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
    	        String itemName = entry.getKey().toLowerCase(); // Convert to lower-case
    	        if (itemName.contains(searchQuery.toLowerCase())) { // Find the item if it contains our specified sequence
    	            System.out.println("Stock of " + entry.getKey() + ": " + entry.getValue() + " units"); 
    	            // Display the stock based on what key is utilized from GetKey Method
    	            
    	            found = true; // Set to true if found
    	        }
    	    }

    	    if (!found) {  // If the item is not found
    	        System.out.println("Item not found in the inventory."); // Display that the item is not found
    	    }
    	//INP.close();}
    }


	


	protected boolean removeInventory(String item) {
		// Implement logic to remove an item from the inventory.
		// Then Display
		//Testing Only
		
		if (inventory.remove(item) == null) {
			return true;
		} else {
			return false;
		}
	}


	public boolean addInventory(String item, int quantity) {
		// Implement logic to add an item to the inventory.
		// Then Display
		// Testing Only
		
		if (inventory.put(item.toUpperCase(), quantity) == null) {
			return true;
		} else {
			return false;
		}
	}
	
	//Getting ready for DBDB implementation
	private Connection connectToDatabase() {
	    Connection connection = null;
	    String url = "";
	    String username = "user";
	    String password = "pass";

	    try {
	        connection = DriverManager.getConnection(url, username, password);
	        System.out.println("Connected");
	    } catch (SQLException e) {
	        System.out.println("Failed Connection to Database.");
	        e.printStackTrace();
	    }

	    return connection;
	}
	
	//Getting ready for DBDB implementation
	protected void search1(String searchQuery) {
	    Connection DBConnection = connectToDatabase();

	    if (DBConnection != null) {
	        String sql = "SELECT itemName, quantity FROM inventory WHERE itemName LIKE ?";
	        
	        try (PreparedStatement statement = DBConnection.prepareStatement(sql)) {
	            statement.setString(1, "%" + searchQuery + "%");
	            ResultSet resultSet = statement.executeQuery();

	            while (resultSet.next()) {
	                System.out.println("Stock of " + resultSet.getString("itemName") +
	                                   ": " + resultSet.getInt("quantity") + " units");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	//Getting ready for DBDB implementation
	
	
	private void closeDatabaseConnection(Connection connection) {
	    try {
	        if (connection != null) {
	            connection.close();
	            System.out.println("Database connection closed.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
}