//purpose: This file contains the BOLReport class which is responsible for exporting a Bill of Lading (BOL) report for a given site.
package mainApp;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class BOLReport {
    private ViewSites viewSites;
    private Scanner scanner;
    private static int counter = 1;

    public BOLReport() {
        this.viewSites = new ViewSites(new DatabaseConnector());
        this.scanner = new Scanner(System.in);
    }

    public void export() {
        viewSites.DisplaySites();

        System.out.println("Enter the site ID for which you want to generate the BOL report: ");
        String siteId = scanner.next();

        if (viewSites.siteExists(siteId)) {
            String textFileName = "bol_report_" + counter + "_" + siteId + ".txt";
            counter++;

            try {
                FileWriter writer = new FileWriter(textFileName);

                Customer customer = viewSites.getCustomerDetails(siteId);

                writer.write("***** BOL REPORT *****\n\n");
                writer.write("Customer Name: " + customer.getName() + "\n");
                writer.write("Address: " + customer.getAddress() + "\n");
                writer.write("Phone Number: " + customer.getPhoneNumber() + "\n");

                writer.close();

                System.out.println("BOL report text file generated successfully for site ID: " + siteId);
            } catch (IOException e) {
                System.out.println("Error: Failed to generate BOL report.");
                e.printStackTrace();
            }
        } else {
            System.out.println("Error: Site ID not found.");
        }
    }
}
