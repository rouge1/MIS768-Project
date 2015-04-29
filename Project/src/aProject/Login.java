package aProject;

/**
 * The Login class stores data and methods: 
 * 1) connecting to --> MySQL aProjectDB database --> LOGIN table;
 * 2) authenticating user-entered username and password credentials with an existing record in the LOGIN table;
 * 3) retrieving userType from the LOGIN table to determine user level type.
 * @author Group #2
 * @version 1.0
 */

import gbl.Anchor;
import gbl.Fill;
import gbl.GBConstraints;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Event;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;

public class Login extends JButton implements ActionListener{

	// Create named constants for the MySQL URL, username, and password.
	final String DB_URL = "jdbc:mysql://localhost:3306/aProjectDB";
	final String USER_NAME = "root";
	final String PASSWORD = "";

	private JPanel loginPanel;
	private JTextField txtUsername;  // Textfield for user to enter username.
	private JTextField txtPassword;  // Textfield for user to enter password.
	private String userType;		 // Variable to hold user type associated with verified username/password; retrieved from database.

	/**
	 * Create the frame.
	 */
	public Login() {
		super("Login");

		// Create a new CreateDB object.
		CreateDB db = new CreateDB();
		
		// Execute the DB() method.
		db.DB();

		loginPanel = new JPanel();
		GridBagLayout gbl = new GridBagLayout();
		loginPanel.setLayout(gbl);
		loginPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		loginPanel.add(new JLabel("Username: "), new GBConstraints(0,0).anchor(Anchor.CENTER));
		txtUsername = new JTextField();
		loginPanel.add(txtUsername, new GBConstraints(1,0).fill(Fill.HORIZONTAL));
		
		loginPanel.add(new JLabel("Password: "), new GBConstraints(0,1).anchor(Anchor.CENTER));
		txtPassword = new JTextField();
		loginPanel.add(txtPassword, new GBConstraints(1,1).fill(Fill.HORIZONTAL));
		
		JTextField text = new JTextField("Please enter your user authentication information.");
		text.setHorizontalAlignment(JTextField.CENTER);
		text.setFont(new Font("Arial", Font.BOLD, 24));
		text.setEditable(false);
		loginPanel.add(text, new GBConstraints(0,2).fill(Fill.HORIZONTAL).spanX(2));

		JPanel temp = new JPanel();
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(this);
		temp.add(btnClear);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(this);
		temp.add(btnSubmit);
		
		loginPanel.add(temp, new GBConstraints(0,3).fill(Fill.HORIZONTAL).spanX(2));

	} // end of constructor

	/**
	 * The getPanel method returns the login panel.
	 * @return The login panel.
	 */
	public JPanel getPanel(){
		return loginPanel;
	} // end of getPanel method

	/**
	 * The getUserType method returns the user
	 * level type.
	 * @return The user's user level type.
	 */
	public UserLevelType getUserType() {
		if(userType.equals(UserLevelType.ADMIN.toString())){
			return UserLevelType.ADMIN;
		}
		
		return UserLevelType.USER;
	} // end of getUserType method

	/**
	 * The actionPerformed method
	 * @param userType the userType to set
	 * @Override
	 */
	public void actionPerformed(ActionEvent event) {

		if(event.getActionCommand().equals("Submit")){
			String u = txtUsername.getText();
			String p = txtPassword.getText();
			boolean flag = false;

		try {
				// Connect to the database
				Connection conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);

				// Create a Statement object
				Statement stmt = conn.createStatement();
				
				// Extract all records from the LOGIN table.
				String sqlStatement = "SELECT userName, password, userType FROM login";
				
				// Store all records from LOGIN table into ResultSet variable.
				ResultSet results = stmt.executeQuery(sqlStatement);

				// Loop through records in results to authenticate user.
				while (results.next()) {
					String user = results.getString("userName");
					String pw = results.getString("password");
					String t = results.getString("userType");
					
					// Compare user-entered data with existing records from LOGIN table.
					// If user is authenticated, retrieve corresponding userType value.
					if((u.equals(user)) && (p.equals(pw))) {
						flag = true;
						userType = t;
						System.out.println("the usertype: " + getUserType());
					} // end if	
				} // end of while loop

				// If user is authenticated, determine which action performed method to fire by userType.
				if(flag = true){
					if(userType.equals(UserLevelType.ADMIN.toString())){
						this.fireActionPerformed(new ActionEvent("",ActionEvent.ACTION_PERFORMED,"ADMIN"));
					}else{
						this.fireActionPerformed(new ActionEvent("",ActionEvent.ACTION_PERFORMED,"USER"));
					} // end of if
				}
				// If user is not authenticated, display error message and clear textfields.
				else if(flag == false){
					JOptionPane.showMessageDialog(null, "Username and Password not found.");
					txtUsername.setText("");
					txtPassword.setText("");
				} // end of if

			}catch(Exception ex) {
				JOptionPane.showMessageDialog(null, "Please check your user credentials.");
			} // end of try/catch

			this.doClick();
		} // end of if

		// Reset textfields to blank.
		if(event.getActionCommand().equals("Clear")){
			txtUsername.setText("");
			txtPassword.setText("");
		} // end of if

	} // end of actionPerformed

} // end of class Login
