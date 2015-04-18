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
import java.sql.*;

public class Login extends JFrame {
	
	// Create named constants for the URL, user name, and password
	final String DB_URL = "jdbc:mysql://localhost:3306/aProjectDB";
    final String USER_NAME = "root";
    final String PASSWORD = "";

	private JPanel contentPane;
	private JTextField txtUsername;
	private JTextField txtPassword;
	private String userType;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Login() {
		setTitle("User Authentication");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(31, 83, 88, 14);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(31, 132, 69, 14);
		contentPane.add(lblPassword);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(126, 80, 279, 20);
		contentPane.add(txtUsername);
		txtUsername.setColumns(10);
		
		txtPassword = new JTextField();
		txtPassword.setBounds(126, 129, 279, 20);
		contentPane.add(txtPassword);
		txtPassword.setColumns(10);
		
		JLabel lblPleaseEnterYour = new JLabel("Please enter your user authentication information.");
		lblPleaseEnterYour.setBounds(31, 26, 374, 14);
		contentPane.add(lblPleaseEnterYour);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String u = txtUsername.getText();
				String p = txtPassword.getText();
				boolean flag = false;
			    
				try {
					
					// Connect to the database
					Connection conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
					
					// Create a Statement object
					Statement stmt = conn.createStatement();
					
					String sqlStatement = "SELECT userName, password, userType FROM login";
					ResultSet results = stmt.executeQuery(sqlStatement);
					
					while (results.next()) {
						String user = results.getString("userName");
						String pw = results.getString("password");
						String t = results.getString("userType");
						
							if((u.equals(user)) && (p.equals(pw))) {
								flag = true;
								userType = t;
								JOptionPane.showMessageDialog(null, "Username and Password accepted.");	
								System.out.println(getUserType());
								setVisible(false);
							}
							
					}
					
				} catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "ERROR: " + ex.getMessage());
				}
			}
		});
		btnSubmit.setBounds(232, 205, 89, 23);
		contentPane.add(btnSubmit);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				txtUsername.setText("");
				txtPassword.setText("");
			
			}
		});
		btnClear.setBounds(331, 205, 89, 23);
		contentPane.add(btnClear);
	}

	/**
	 * @return the userType
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * @param userType the userType to set
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
}
