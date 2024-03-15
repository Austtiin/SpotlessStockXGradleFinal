//purpose: This file contains the BOLReport class which is responsible for exporting a Bill of Lading (BOL) report for a given site.

package mainApp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class BOLReport {
    private ViewSites viewSites;
    private Scanner scanner;
    private static int counter = 1;

    public BOLReport() {
        this.viewSites = new ViewSites(new DatabaseConnector());
        this.scanner = new Scanner(System.in);
    }

    public void export() {
        try {
            // Prompt user to select a site
            viewSites.DisplaySites();

            // Load Excel template workbook
            FileInputStream inputStream = new FileInputStream(new File("AppResources/BOL Template.xlsx"));
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            // Get site ID from user input
            System.out.println("Enter the site ID for which you want to generate the BOL report: ");
            String siteId = scanner.next();

            
            if (viewSites.siteExists(siteId)) {
            	//Counter for generating unique file names
                String fileName = "bol_report_" + counter + "_" + siteId + ".xlsx";
                counter++;

                // Create instance of a Customer object and retrieve its details
                Customer customer = viewSites.getCustomerDetails(siteId);

                // Populate Excel template with customer details
                Row row = sheet.getRow(11);
                row.getCell(1).setCellValue(customer.getName());
                row.getCell(1).setCellValue(customer.getAddress());
                row.getCell(1).setCellValue(customer.getPhoneNumber());

                //Save the updated workbook
                FileOutputStream outputStream = new FileOutputStream(new File(fileName));
                workbook.write(outputStream);
                outputStream.close();

                System.out.println("BOL report generated successfully for site ID: " + siteId);
            } else {
                System.out.println("Error: Site ID not found.");
            }
        } catch (IOException e) {
            System.out.println("Error exporting BOL report: " + e.getMessage());
        }
    }
}
