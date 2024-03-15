//Purpose: SalesTransaction class to store the sales transactions

//Getting ready for DB Connection

package mainApp;

import java.util.Date;

public class SalesTransaction {
    private String customerName;
    private String itemName; 
    private int quantity; 
    private double totalPrice; 
    private Date transactionDate; 

    public SalesTransaction(String customerName, String itemName, int quantity, double totalPrice) {
        this.customerName = customerName;
        this.itemName = itemName; 
        this.quantity = quantity; 
        this.totalPrice = totalPrice; 
        this.transactionDate = new Date();
    }

   
    public String getCustomerName() {
        return customerName;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }
}