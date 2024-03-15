//Point of Entry for the Application
//designated as the main method
//designs the frame of the application
// 02/22/2024
// Professor Kumar
// Advanced Java Programming
// COP3805C

package SpotlessStockXUI;
import javax.swing.*;

import logger.LoggerStockX;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;


public class app {
	//Variables
	private JFrame frame;
	LoggerStockX logger = new LoggerStockX();
	//Main method to run the application
	public static void main(String[] args) {

		EventQueue.invokeLater(() -> {
			//Try to create the frame
			try {
				//Create the frame
				app window = new app();
				window.frame.setUndecorated(true);
				window.frame.setVisible(true);

			} catch (Exception e) { //Catch any exceptions
				
				
				 
				 LoggerStockX.logger.info("Exception:" + e.getMessage());
			}
		});
	}

	//Constructor to create the application
	/**
	 *
	 * @wbp.parser.entryPoint
	 */
	public app() {
		initialize();
	}

	//Method to initialize the contents of the frame
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(34, 31, 32));
		frame.setBounds(100, 100, 1029, 650);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		panelDeco mainPanel = new panelDeco();
		mainPanel.setRoundTopRight(20);
		mainPanel.setRoundBottomRight(20);
		mainPanel.setRoundTopLeft(20);
		mainPanel.setRoundBottomLeft(20);
		mainPanel.setBounds(10, 11, 200, 589);
		mainPanel.setBackground(new Color(255, 255, 204));
		frame.getContentPane().add(mainPanel);

		panelDeco headerPanel = new panelDeco();
		headerPanel.setRoundTopRight(20);
		headerPanel.setRoundBottomRight(20);
		headerPanel.setRoundTopLeft(20);
		headerPanel.setRoundBottomLeft(20);
		headerPanel.setBounds(220, 11, 783, 120);
		headerPanel.setBackground(new Color(255, 255, 204));
		frame.getContentPane().add(headerPanel);

		//Vetical Menu Bar - Generates when app is run
		JToolBar menuBar = new JToolBar(JToolBar.VERTICAL);
		menuBar.setFloatable(false);
		menuBar.setOpaque(false); // Make the menuBar transparent

		// Insert buttons
		JButton homeButton = createMenuButton("Home");
		JButton chemicalsButton = createMenuButton("Chemicals");
		JButton sitesButton = createMenuButton("Sites");
		JButton generateBOLButton = createMenuButton("Generate BOL");

		//adding buttons to menuBar
		menuBar.add(homeButton);
		menuBar.add(chemicalsButton);
		menuBar.add(sitesButton);
		menuBar.add(generateBOLButton);
		menuBar.setBounds(0, 0, 120, mainPanel.getHeight());
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(menuBar, BorderLayout.WEST); // Align menuBar to the left side


		//in order to have rounded JPanels 
		frame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				int arcWidth = 20;
				int arcHeight = 20;
				frame.setShape(new RoundRectangle2D.Double(0, 0, frame.getWidth(), frame.getHeight(), arcWidth, arcHeight));
			}
		});
	}

	//Method to create a button and its visuals
	private JButton createMenuButton(String text) {
		JButton button = new JButton(text);
		button.setForeground(Color.BLACK);
		button.setFont(new Font("Arial", Font.BOLD, 18));


		// Our hover effect for the buttons
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				button.setForeground(Color.GRAY);
				button.setFont(new Font("Arial", Font.BOLD, 19));
			}

			//When mouse exits the button, it will return to its original state
			@Override
			public void mouseExited(MouseEvent e) {
				button.setForeground(Color.BLACK);
				button.setFont(new Font("Arial", Font.BOLD, 18));
			}
		});

		// Make the boarder of the button invisible
		button.setBorderPainted(false);
		button.setFocusPainted(false);
		button.setContentAreaFilled(false);
		return button;
	}
}
