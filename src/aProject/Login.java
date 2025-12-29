package aProject;

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

/**
 * The Login class stores data associated with the user's
 * authentication credentials (username, password, and user type level).
 * 
 * @author Group #2
 * @version 1.0
 */


public class Login extends JButton implements ActionListener{

	private JPanel loginPanel;
	private JTextField txtUsername;  // Textfield for user to enter username.
	private JTextField txtPassword;  // Textfield for user to enter password.
	private String userType;		 // Variable to hold user type associated with verified username/password.

	/**
	 * Constructor method to create the frame.
	 */
	public Login() {
		super("Login");

		loginPanel = new JPanel();
		GridBagLayout gbl = new GridBagLayout();
		loginPanel.setLayout(gbl);
		loginPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		loginPanel.add(new JLabel("Username: "), new GBConstraints(0,0).anchor(Anchor.CENTER));
		txtUsername = new JTextField();
		loginPanel.add(txtUsername, new GBConstraints(1,0).fill(Fill.HORIZONTAL));
		
		loginPanel.add(new JLabel("Password: "), new GBConstraints(0,1).anchor(Anchor.CENTER));
		txtPassword = new JTextField();
		txtPassword.addActionListener(this);
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
	 * The getUserType method returns the UserLevelType.
	 * @return The user's UserLevelType value.
	 */
	public UserLevelType getUserType() {
		if(userType.equals(UserLevelType.ADMIN.toString())){
			return UserLevelType.ADMIN;
		}
		
		return UserLevelType.USER;
	} // end of getUserType method

	/**
	 * The actionPerformed method to calls the action event
	 * when a button is clicked.
	 * @param event ActionEvent. The action event called by selecting the Submit button.
	 * @Override
	 */
	public void actionPerformed(ActionEvent event) {

		if(event.getActionCommand().equals("Submit") || event.getSource() == txtPassword){
			String u = txtUsername.getText();
			String p = txtPassword.getText();
			
			// Simple hardcoded authentication
			if("test".equals(u) && "test".equals(p)) {
				userType = UserLevelType.ADMIN.toString();
				System.out.println("Login successful - User type: " + getUserType());
				this.fireActionPerformed(new ActionEvent("",ActionEvent.ACTION_PERFORMED,"ADMIN"));
			} else {
				JOptionPane.showMessageDialog(null, "Invalid username or password. Use 'test' for both.");
				txtUsername.setText("");
				txtPassword.setText("");
			}
			
			this.doClick();
		} // end of if

		// Reset textfields to blank.
		if(event.getActionCommand().equals("Clear")){
			txtUsername.setText("");
			txtPassword.setText("");
		} // end of if

	} // end of actionPerformed

} // end of class Login
