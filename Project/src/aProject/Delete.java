package aProject;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;



import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.*;

public class Delete extends JFrame {

	private JPanel contentPane;
	private JTextField txtNum;
	private JTextField txtType;
	private JTextField txtDate;
	private JTextField txtLat;
	private JTextField txtLong;
	private JTextField txtElev;
	
	// Create the Connection object
	Connection conn;
    // Create the ResultSet object
    ResultSet result;
    private JLabel lblRowTotal;
    private JLabel lblRow;
    private JLabel lblCaseID;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Delete frame = new Delete();
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
	public Delete() {
		setTitle("Delete a Record");
		addWindowListener(new ThisWindowListener());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 452, 333);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCaseIdentificationNumber = new JLabel("Case Identification Number:");
		lblCaseIdentificationNumber.setBounds(10, 74, 164, 14);
		contentPane.add(lblCaseIdentificationNumber);
		
		JLabel lblCaseNumber = new JLabel("Case Number:");
		lblCaseNumber.setBounds(10, 99, 141, 14);
		contentPane.add(lblCaseNumber);
		
		JLabel lblCaseType = new JLabel("Case Type:");
		lblCaseType.setBounds(10, 124, 141, 14);
		contentPane.add(lblCaseType);
		
		JLabel lblDate = new JLabel("Case Date (YYYY-MM-DD):");
		lblDate.setBounds(10, 152, 185, 14);
		contentPane.add(lblDate);
		
		JLabel lblLatitude = new JLabel("Latitude:");
		lblLatitude.setBounds(10, 177, 141, 14);
		contentPane.add(lblLatitude);
		
		JLabel lblLongitude = new JLabel("Longitude:");
		lblLongitude.setBounds(10, 202, 141, 14);
		contentPane.add(lblLongitude);
		
		txtNum = new JTextField();
		txtNum.setBounds(206, 96, 220, 20);
		contentPane.add(txtNum);
		txtNum.setColumns(10);
		
		txtType = new JTextField();
		txtType.setBounds(206, 121, 220, 20);
		contentPane.add(txtType);
		txtType.setColumns(10);
		
		txtDate = new JTextField();
		txtDate.setBounds(205, 149, 151, 20);
		contentPane.add(txtDate);
		txtDate.setColumns(10);
		
		txtLat = new JTextField();
		txtLat.setBounds(206, 174, 86, 20);
		contentPane.add(txtLat);
		txtLat.setColumns(10);
		
		txtLong = new JTextField();
		txtLong.setBounds(206, 199, 86, 20);
		contentPane.add(txtLong);
		txtLong.setColumns(10);
		
		JLabel lblElevation = new JLabel("Elevation:");
		lblElevation.setBounds(10, 227, 141, 14);
		contentPane.add(lblElevation);
		
		txtElev = new JTextField();
		txtElev.setBounds(206, 224, 86, 20);
		contentPane.add(txtElev);
		txtElev.setColumns(10);
		
		JButton btnFirst = new JButton("<<");
		btnFirst.addActionListener(new BtnFirstActionListener());
		btnFirst.setBounds(10, 11, 89, 23);
		contentPane.add(btnFirst);
		
		JButton btnPrevious = new JButton("<");
		btnPrevious.addActionListener(new BtnPreviousActionListener());
		btnPrevious.setBounds(109, 11, 89, 23);
		contentPane.add(btnPrevious);
		
		JButton btnNext = new JButton(">");
		btnNext.addActionListener(new BtnNextActionListener());
		btnNext.setBounds(242, 11, 89, 23);
		contentPane.add(btnNext);
		
		JButton btnLast = new JButton(">>");
		btnLast.addActionListener(new BtnLastActionListener());
		btnLast.setBounds(337, 11, 89, 23);
		contentPane.add(btnLast);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new BtnDeleteActionListener());
		btnDelete.setBounds(242, 261, 89, 23);
		contentPane.add(btnDelete);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new BtnCancelActionListener());
		btnCancel.setBounds(337, 261, 89, 23);
		contentPane.add(btnCancel);
		
		lblRow = new JLabel("");
		lblRow.setBounds(10, 49, 198, 14);
		contentPane.add(lblRow);
		
		lblRowTotal = new JLabel("");
		lblRowTotal.setBounds(10, 255, 210, 14);
		contentPane.add(lblRowTotal);
		
		lblCaseID = new JLabel("");
		lblCaseID.setBounds(206, 74, 46, 14);
		contentPane.add(lblCaseID);
	}

	private class BtnCancelActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			DatabaseCommands dc = new DatabaseCommands();
			
			dc.setVisible(true);
			setVisible(false);
			dispose();
		}
	}
	
	private class ThisWindowListener extends WindowAdapter {
		@Override
		public void windowActivated(WindowEvent e) {
			
			try {
				
				getDBConnection();
				
				// Display the number of rows
				result.last();		    		// Move to the last row
				int numRows = result.getRow();  // Get the current row number
				result.first();		    		// Move back to the first row
				lblRowTotal.setText(numRows + " row(s) in the caseLocations table.");

				//Update the labels to show the content of the current row
				showRowContent(result);
			}
			catch (Exception ex){
				JOptionPane.showMessageDialog(null,"ERROR: " + ex.getMessage());
			}
		}

		@Override
		public void windowClosing(WindowEvent e) {
			try {
				conn.close();
			} 
			catch (Exception ex) {
				JOptionPane.showMessageDialog(null,"ERROR: " + ex.getMessage());			
			}
		}
	}
	private class BtnFirstActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try{
				//Move to the first row
				result.first();
				
				//Update the labels to show the content of the current row
				showRowContent(result);
				
			} catch (Exception ex){
				JOptionPane.showMessageDialog(null,"ERROR: " + ex.getMessage());
			}
		}
	}
	private class BtnPreviousActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try{
				//Move to the previous row
				result.previous();
				
				//Update the labels to show the content of the current row
				showRowContent(result);
				
			} catch (Exception ex){
				JOptionPane.showMessageDialog(null,"ERROR: " + ex.getMessage());;
			}
		}
	}
	private class BtnNextActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				//Move to the next row
				result.next();
				
				//Update the labels to show the content of the current row.
				showRowContent(result);
			}
			catch (Exception ex) {
				JOptionPane.showMessageDialog(null,"ERROR: " + ex.getMessage());
			}
		}
	}
	private class BtnLastActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				//Move to the last row
				result.last();
				
				//Update the labels to show the content of the current row.
				showRowContent(result);
			}
			catch (Exception ex) {
				JOptionPane.showMessageDialog(null,"ERROR: " + ex.getMessage());
			}
		}
	}
	private class BtnDeleteActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			String i = lblCaseID.getText();
			String n = txtNum.getText();
			String t = txtType.getText();
			String d = txtDate.getText();
			String la = txtLat.getText();
			String lo = txtLong.getText();
			String el = txtElev.getText();
			
			
			
			try {
				getDBConnection();
				
				 String sql = "DELETE FROM caseLocations WHERE caseID = ?";
				 
				 PreparedStatement stmt2 = conn.prepareStatement(sql);
				 
				 stmt2.setString(1,i);
				
				 // Create a Statement object.
		         //Statement stmt2 = conn.createStatement();
				 
				 // Send the DELETE statement to the DBMS.
			     int rows = stmt2.executeUpdate();

			     // Display the results.
			     JOptionPane.showMessageDialog(null, rows + " row(s) deleted.");
			}
			 catch(Exception ex) {
				 JOptionPane.showMessageDialog(null,"ERROR: " + ex.getMessage());
			}
		}
	}
	
	
	/**
	 * This method is used to establish the DB connection
	 * 
	 */
	private void getDBConnection(){
		// Create named constants for the URL, user name and password
	    final String DB_URL = "jdbc:mysql://localhost:3306/aProjectDB";
	    final String USER_NAME = "root";
	    final String PASSWORD = "";
	    
	    try {
	    	// Create a connection to the database.
		    conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
		       
		    // Create a Statement object.
		    Statement stmt = conn.createStatement();
		         
		    // Create a string with a SELECT statement.
		    String sql = "SELECT caseID, caseNumber, caseType, caseDate, caseLat, caseLong, caseElev FROM caseLocations";
		         
		    // Send the statement to the DBMS.
		    result = stmt.executeQuery(sql);
		 }
		 catch(Exception ex) {
			 JOptionPane.showMessageDialog(null,"ERROR: " + ex.getMessage());
		 }		
	}	
	/**
	 * This function displays the content of the current row in a result set.
	 * Three labels are used to display the content of the ProdNum, Description, and price columns
	 * Another label is used to display the row number
	 * @param result A ResultSet
	 */
	private void showRowContent(ResultSet result) {
		try {
			//Display the content of the current row
			lblCaseID.setText(result.getString("caseID"));
			txtNum.setText(result.getString("caseNumber"));
			txtType.setText(result.getString("caseType"));
			txtDate.setText(result.getString("caseDate"));
			txtLat.setText(result.getString("caseLat"));
			txtLong.setText(result.getString("caseLong"));
			txtElev.setText(result.getString("caseElev"));
		
			//Display the current row number
			lblRow.setText("Current Row: "+result.getRow());
			
		} catch(Exception ex){
			JOptionPane.showMessageDialog(null,"ERROR: " + ex.getMessage());
		}
	}
}