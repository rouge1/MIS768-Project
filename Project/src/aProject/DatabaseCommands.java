package aProject;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.*;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JRadioButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.ButtonGroup;

public class DatabaseCommands extends JFrame {

	private JPanel contentPane;
	private JTextField txtCaseNumber;
	private JTextField txtCaseDate;
	private JTextField txtLatitude;
	private JTextField txtLongitude;
	private JTextField txtElevation;
	private JPanel panelMain;
	private JPanel panelCreate;
	private JRadioButton rdbtnBicycle;
	private JRadioButton rdbtnPedestrian;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	// Create the Connection object
	Connection conn;
	// Create the ResultSet object
	ResultSet result;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DatabaseCommands frame = new DatabaseCommands();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DatabaseCommands() {
		setTitle("Database Commands");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 418, 375);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		
		panelMain = new JPanel();
		contentPane.add(panelMain, "name_3120562883609");
		panelMain.setLayout(new GridLayout(3, 0, 0, 0));
		
		JButton btnCreate = new JButton("Create a New Record");
		btnCreate.addActionListener(new BtnCreateActionListener());
		panelMain.add(btnCreate);
		
		JButton btnUpdate = new JButton("Update an Existing Record");
		btnUpdate.addActionListener(new BtnUpdateActionListener());
		panelMain.add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete an Existing Record");
		btnDelete.addActionListener(new BtnDeleteActionListener());
		panelMain.add(btnDelete);
		
		panelCreate = new JPanel();
		contentPane.add(panelCreate, "name_3151627360026");
		panelCreate.setLayout(null);
		
		JLabel lblPleaseEnterThe = new JLabel("Please enter the following:");
		lblPleaseEnterThe.setBounds(10, 11, 262, 14);
		panelCreate.add(lblPleaseEnterThe);
		
		JLabel lblCaseNumber = new JLabel("Case Number:");
		lblCaseNumber.setBounds(35, 36, 91, 14);
		panelCreate.add(lblCaseNumber);
		
		JLabel lblCaseType = new JLabel("Case Type:");
		lblCaseType.setBounds(35, 71, 91, 14);
		panelCreate.add(lblCaseType);
		
		JLabel lblCaseDate = new JLabel("Case Date:");
		lblCaseDate.setBounds(35, 96, 91, 14);
		panelCreate.add(lblCaseDate);
		
		JLabel lblLatitude = new JLabel("Latitude:");
		lblLatitude.setBounds(35, 135, 91, 14);
		panelCreate.add(lblLatitude);
		
		JLabel lblLongitude = new JLabel("Longitude:");
		lblLongitude.setBounds(35, 169, 91, 14);
		panelCreate.add(lblLongitude);
		
		JLabel lblElevation = new JLabel("Elevation:");
		lblElevation.setBounds(35, 204, 82, 14);
		panelCreate.add(lblElevation);
		
		txtCaseNumber = new JTextField();
		txtCaseNumber.setBounds(136, 33, 197, 20);
		panelCreate.add(txtCaseNumber);
		txtCaseNumber.setColumns(10);
		
		txtCaseDate = new JTextField();
		txtCaseDate.setBounds(136, 93, 197, 20);
		panelCreate.add(txtCaseDate);
		txtCaseDate.setColumns(10);
		
		txtLatitude = new JTextField();
		txtLatitude.setBounds(136, 132, 86, 20);
		panelCreate.add(txtLatitude);
		txtLatitude.setColumns(10);
		
		txtLongitude = new JTextField();
		txtLongitude.setBounds(136, 166, 86, 20);
		panelCreate.add(txtLongitude);
		txtLongitude.setColumns(10);
		
		txtElevation = new JTextField();
		txtElevation.setBounds(136, 201, 86, 20);
		panelCreate.add(txtElevation);
		txtElevation.setColumns(10);
		
		rdbtnBicycle = new JRadioButton("Bicycle");
		buttonGroup.add(rdbtnBicycle);
		rdbtnBicycle.setBounds(134, 67, 109, 23);
		panelCreate.add(rdbtnBicycle);
		
		rdbtnPedestrian = new JRadioButton("Pedestrian");
		buttonGroup.add(rdbtnPedestrian);
		rdbtnPedestrian.setBounds(245, 67, 109, 23);
		panelCreate.add(rdbtnPedestrian);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new BtnSubmitActionListener());
		btnSubmit.setBounds(35, 264, 89, 23);
		panelCreate.add(btnSubmit);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new BtnClearActionListener());
		btnClear.setBounds(154, 264, 89, 23);
		panelCreate.add(btnClear);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new BtnBackActionListener());
		btnBack.setBounds(273, 264, 89, 23);
		panelCreate.add(btnBack);
		
		JLabel lblYyyymmdd = new JLabel("YYYY-MM-DD");
		lblYyyymmdd.setBounds(35, 109, 93, 14);
		panelCreate.add(lblYyyymmdd);
	}
	private class BtnCreateActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			
			panelCreate.setVisible(true);
			panelMain.setVisible(false);
		}
	}
	private class BtnUpdateActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			
			UpdateWindow update = new UpdateWindow();
			update.setVisible(true);
			panelMain.setVisible(false);
			dispose();
		}
	}
	private class BtnDeleteActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			Delete delete = new Delete();
			delete.setVisible(true);
			panelMain.setVisible(false);
			dispose();
		}
	}
	private class BtnBackActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			panelMain.setVisible(true);
			panelCreate.setVisible(false);
		}
	}
	private class BtnClearActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			   txtCaseNumber.setText("");
			   txtCaseDate.setText("");
			   txtLatitude.setText("");
			   txtLongitude.setText("");
			   txtElevation.setText("");
			   buttonGroup.clearSelection();
		}
	}
	private class BtnSubmitActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			String caseNumber; // To hold the case number
			String caseType = ""; // To hold the case type
			String caseDate = ""; // To hold the case date
			String caseLat; // To hold the case latitude value
			String caseLong; // To hold the case longitude value
			String caseElev; // To hold the case elevation value
			
			// Create named constants for the URL, user name and password
			final String DB_URL = "jdbc:mysql://localhost:3306/aProjectDB";
		    final String USER_NAME = "root";
		    final String PASSWORD = "";
		    		    
		    // Get the text entered by the user into the text field
		    caseNumber = txtCaseNumber.getText();
		    if (rdbtnBicycle.isSelected()) {
		    	caseType = "BICYCLE";
		    }
		    if (rdbtnPedestrian.isSelected()) {
		    	caseType = "PEDESTRIAN";
		    }
		    caseDate = txtCaseDate.getText();
		    caseLat = txtLatitude.getText();
		    caseLong = txtLongitude.getText();
		    caseElev = txtElevation.getText();
		    
		  // Tests fields for values
		   if (caseType.equalsIgnoreCase("")) {
			   JOptionPane.showMessageDialog(null, "A case type must be selected.");
		   } else if (caseDate.equalsIgnoreCase("")) {
			   JOptionPane.showMessageDialog(null, "A case date must be entered.");
		   } else if (caseLat.equalsIgnoreCase("")) {
			   JOptionPane.showMessageDialog(null, "Please enter a latitude value.");
		   } else if (caseLong.equalsIgnoreCase("")) {
			   JOptionPane.showMessageDialog(null, "Please enter a longitude value.");
		   } else if (caseElev.equalsIgnoreCase("")) {
			   JOptionPane.showMessageDialog(null, "Please enter an elevation value.");
		   } else {
			   try {
				   // Connect to the database
				   conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
				   
				   // Create a Statement object
				   Statement stmt = conn.createStatement();
				   
				   // INSERT SQL statement
				   String sqlStatement = " INSERT INTO caseLocations " +
						   "(caseNumber, caseType, caseDate, caseLong, caseLat, caseElev) " +
						   "VALUES ('" + caseNumber + "', '" + caseType + "', '" +
						   caseDate + "', '" + caseLong + "', '" + caseLat + "', '" +
						   caseElev + "')";
				   
				   int rows = stmt.executeUpdate(sqlStatement);
				   
				   // Display the results
				   JOptionPane.showMessageDialog(null, "A new record has been added.");
				   
				   // Close the database connection
				   conn.close();
				   
				   txtCaseNumber.setText("");
				   txtCaseDate.setText("");
				   txtLatitude.setText("");
				   txtLongitude.setText("");
				   txtElevation.setText("");
				   buttonGroup.clearSelection();
				   
			   } catch (Exception ex) {
			   // Displays ERROR message
			   
			   JOptionPane.showMessageDialog(null,"ERROR: " + ex.getMessage());
			   }
		   }
		}
	}
}