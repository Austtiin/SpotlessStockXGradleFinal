package SpotlessStockXUI;

import javax.swing.*;

import logger.LoggerStockX;
import mainApp.SpotlessStockXIS;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SpotlessStockXApp {
    private JFrame frame;
    private JTextArea infoTextArea;
    private int selectedMenuOption;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                SpotlessStockXApp window = new SpotlessStockXApp();
                window.initialize(); 
                window.frame.setUndecorated(true);
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public SpotlessStockXApp() {
    }

    /**
     * @wbp.parser.entryPoint
     */
    private void initialize() {
        frame = new JFrame();
        frame.getContentPane().setBackground(new Color(34, 31, 32));
        frame.setBounds(100, 100, 1029, 650);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(255, 255, 204));
        mainPanel.setBounds(10, 11, 200, 589);
        frame.getContentPane().add(mainPanel);

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(255, 255, 204));
        headerPanel.setBounds(220, 11, 783, 120);
        frame.getContentPane().add(headerPanel);

        JToolBar menuBar = new JToolBar(JToolBar.VERTICAL);
        menuBar.setFloatable(false);
        menuBar.setOpaque(false);

        JButton addItemButton = createMenuButton("Add Item");
        JButton checkStockButton = createMenuButton("Check Stock");
        JButton updateStockButton = createMenuButton("Update Stock Information");
        JButton deleteItemButton = createMenuButton("Delete Stock Item");
        JButton viewSitesButton = createMenuButton("View Delivery Sites");
        JButton exportBOLButton = createMenuButton("Export BOL Report");
        JButton searchInventoryButton = createMenuButton("Search Inventory");

        // Add action listeners for menu buttons
        addItemButton.addActionListener(e -> showPanel(1));
        checkStockButton.addActionListener(e -> showPanel(2));
        updateStockButton.addActionListener(e -> showPanel(3));
        deleteItemButton.addActionListener(e -> showPanel(4));
        viewSitesButton.addActionListener(e -> showPanel(5));
        exportBOLButton.addActionListener(e -> showPanel(6));
        searchInventoryButton.addActionListener(e -> showPanel(7));

        menuBar.add(addItemButton);
        menuBar.add(checkStockButton);
        menuBar.add(updateStockButton);
        menuBar.add(deleteItemButton);
        menuBar.add(viewSitesButton);
        menuBar.add(exportBOLButton);
        menuBar.add(searchInventoryButton);
        menuBar.setBounds(0, 0, 120, mainPanel.getHeight());
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(menuBar, BorderLayout.WEST);

        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(new Color(255, 255, 204));
        infoPanel.setBounds(220, 142, 783, 458);
        infoPanel.setLayout(new BorderLayout()); 
        frame.getContentPane().add(infoPanel);

        infoTextArea = new JTextArea();
        infoTextArea.setEditable(false); 
        JScrollPane scrollPane = new JScrollPane(infoTextArea);
        infoPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.BOLD, 18));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setForeground(Color.GRAY);
                button.setFont(new Font("Arial", Font.BOLD, 19));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setForeground(Color.BLACK);
                button.setFont(new Font("Arial", Font.BOLD, 18));
            }
        });

        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        return button;
    }

    private void showPanel(int option) {
        selectedMenuOption = option;
        String panelInformation = fetchPanelInformation(option);
        infoTextArea.setText(panelInformation);
    }

    private String fetchPanelInformation(int option) {
        StringBuilder information = new StringBuilder();
        
        switch (option) {
            case 1:
            	//TODO: Add logger
                System.out.println("Checking Current Inventory...");
                LoggerStockX.logger.info("Checking Current Inventory...");
                // Call the method from SpotlessStockXIS class to fetch current inventory information
                SpotlessStockXIS stockXIS = new SpotlessStockXIS();
                information.append(stockXIS.fetchCurrentInventoryInformation());
                break;
                
            case 2:
                // Logic for checking stock
                information.append("Check Stock Panel Information\n");
                break;
            case 3:
                // Logic for updating stock information
                information.append("Update Stock Information Panel Information\n");
                break;
            case 4:
                // Logic for deleting a stock item
                information.append("Delete Stock Item Panel Information\n");
                break;
            case 5:
                // Logic for viewing delivery sites
                information.append("View Delivery Sites Panel Information\n");
                break;
            case 6:
                // Logic for exporting BOL report
                information.append("Export BOL Report Panel Information\n");
                break;
            case 7:
                // Logic for searching inventory
                information.append("Search Inventory Panel Information\n");
                break;
            default:
                information.append("Invalid choice. Please select an option.\n");
                break;
        }
        
        return information.toString();
    }
}