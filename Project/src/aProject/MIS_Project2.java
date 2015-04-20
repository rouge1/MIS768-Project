package aProject;

import gbl.Anchor;
import gbl.Fill;
import gbl.GBConstraints;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.event.SelectEvent;
import gov.nasa.worldwind.event.SelectListener;
import gov.nasa.worldwind.layers.MarkerLayer;
import gov.nasa.worldwind.layers.ViewControlsLayer;
import gov.nasa.worldwind.layers.ViewControlsSelectListener;
import gov.nasa.worldwind.pick.PickedObject;
import gov.nasa.worldwindx.examples.ApplicationTemplate;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.swing.*;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;


public class MIS_Project2 extends JFrame implements ActionListener {

	final String DB_URL = "jdbc:mysql://localhost:3306/aProjectDB";
	final String USERNAME = "root";
	final String PASSWORD = "";

	private JPanel cards;                                    //a panel that uses CardLayout
	private CardLayout cardLayoutController; 
	private Dimension canvasSize = new Dimension(1000, 800); //preferred size

	private JTextField caseNumber; 
	private JTextField caseType;  
	private JTextField caseDate; 
	private JTextField caseLongitude;   
	private JTextField caseLatitude;   
	private JTextField caseElevation; 
	private JButton updateButton;
	private JButton deleteButton;

	private JButton applyDateFilterButton;
	private JButton resetDateFilterButton;
	private JButton uploadToDatabase;
	private JButton downloadFromDatabase;
	private JTextField databaseStatus;
	private Login loginScreen;

	private JDatePickerImpl endDatePicker;
	private JDatePickerImpl beginDatePicker;
	private JCheckBox pedestrians;
	private JCheckBox cyclists;

	private DataPointSet crashData;
	private DataPointSet filteredCrashData;

	ApplicationTemplate.AppPanel worldWindGlobe;
	private MarkerLayer cyclistsLayer = null;     //
	private MarkerLayer pedestriansLayer = null;  //

	private Date beginDate;                       //Use selection
	private Date endDate;                         //Use selection
	private Date todaysDate;                      //Does not change after startup
	private Date defaultBeginDate;                //Does not change after startup


	public MIS_Project2() {
		super("Pedestrian and Bicyclist Safety Tool");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setSize(canvasSize);
		this.setLocationRelativeTo(null);

		this.createUIforFrame();

		worldWindGlobe.getWwd().addSelectListener(new appSelectListener());

	}//end constructor() MIS_Project2

	/**
	 * @param panels the Panel with cardLayout. 
	 * 
	 * This method returns the name of the visible panel in a cardLayout Panel. 
	 * 
	 */
	protected void createUIforFrame(){

		//Create the "cards".
		//Create the panel that contains the "cards".
		cardLayoutController = new CardLayout();
		cards = new JPanel(cardLayoutController);

		//Make panel with login screen

		Login loginPanel = new Login();
		loginPanel.getPanel().setName("Login");
		loginPanel.addActionListener(this);
		cards.add(loginPanel.getPanel(), "Login");

		//Make panel with globe 
		worldWindGlobe = new ApplicationTemplate.AppPanel(canvasSize, true);
		worldWindGlobe.setName("World");
		cards.add(worldWindGlobe, "World");

		ViewControlsLayer viewControlsLayer = new ViewControlsLayer();
		ApplicationTemplate.insertBeforeCompass(worldWindGlobe.getWwd(), viewControlsLayer);
		worldWindGlobe.getWwd().addSelectListener(new ViewControlsSelectListener(worldWindGlobe.getWwd(), viewControlsLayer));

		//center of layout has login and globe
		this.add(cards, BorderLayout.CENTER);
		this.setVisible(true);

	}//end createUI

	/**
	 * This method builds the Details Panel
	 * 
	 * @returns the details panel
	 */
	protected void createMenuBar(){			
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic('F');

		JMenuItem openItem = new JMenuItem("Open");
		fileMenu.setMnemonic('O');
		fileMenu.add(openItem);
		openItem.addActionListener(this);

		fileMenu.addSeparator();

		JMenuItem exitItem = new JMenuItem("Exit");
		fileMenu.setMnemonic('E');
		fileMenu.add(exitItem);
		exitItem.addActionListener(this);

		JMenuBar bar = new JMenuBar();
		this.setJMenuBar(bar);
		bar.add(fileMenu); 

	}//end createMenuBar

	/**
	 * This method builds the filter Panel
	 * 
	 * @returns the details panel
	 */
	protected JPanel createfilterPanel(){

		try {
			SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd"); 
			defaultBeginDate = dateFormatter.parse("2000-01-01");                
			beginDate = defaultBeginDate;
			todaysDate = dateFormatter.parse(LocalDate.now().toString());
			endDate = todaysDate; 
		} catch (ParseException e) {
			e.printStackTrace();
		}

		JPanel filter = new JPanel();
		GridBagLayout gbl = new GridBagLayout();
		filter.setLayout(gbl);

		//LocalDate now = LocalDate.now();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");

		filter.add(new JLabel("Filter by Date"), new GBConstraints(0,0).anchor(Anchor.CENTER).spanX(2));

		filter.add(new JLabel("Start date: "), new GBConstraints(0,1).anchor(Anchor.WEST));  

		UtilDateModel beginModel = new UtilDateModel();
		beginModel.setValue(beginDate);
		beginModel.setSelected(true);
		JDatePanelImpl bDatePanel = new JDatePanelImpl(beginModel, p);
		beginDatePicker = new JDatePickerImpl(bDatePanel, new DateLabelFormatter());
		beginDatePicker.addActionListener(this);

		filter.add(beginDatePicker, new GBConstraints(1,1));                        

		filter.add(new JLabel("End date: "), new GBConstraints(0,2).anchor(Anchor.WEST));

		UtilDateModel endModel = new UtilDateModel();
		endModel.setValue(endDate);
		endModel.setSelected(true);
		JDatePanelImpl eDatePanel = new JDatePanelImpl(endModel, p);
		endDatePicker = new JDatePickerImpl(eDatePanel, new DateLabelFormatter());
		endDatePicker.addActionListener(this);

		filter.add(endDatePicker, new GBConstraints(1,2));

		JPanel buttonPanel = new JPanel();
		resetDateFilterButton = new JButton("Reset");
		resetDateFilterButton.addActionListener(this);
		resetDateFilterButton.setEnabled(false);
		buttonPanel.add(resetDateFilterButton);

		applyDateFilterButton = new JButton("Apply");
		applyDateFilterButton.addActionListener(this);
		applyDateFilterButton.setEnabled(false);
		buttonPanel.add(applyDateFilterButton);

		filter.add(buttonPanel, new GBConstraints(1,3).fill(Fill.HORIZONTAL));

		filter.add(new JSeparator(JSeparator.HORIZONTAL), new GBConstraints(0,4).fill(Fill.HORIZONTAL).spanX(2)); //Separator

		filter.add(new JLabel("Filter by Type"), new GBConstraints(0,5).anchor(Anchor.CENTER).spanX(2));

		pedestrians = new JCheckBox("Pedestrians",true);
		pedestrians.addActionListener(this);
		cyclists = new JCheckBox("Cyclists",true);
		cyclists.addActionListener(this);			 

		filter.add(pedestrians, new GBConstraints(0,6).anchor(Anchor.WEST));
		filter.add(cyclists, new GBConstraints(0,7).anchor(Anchor.WEST));

		filter.add(new JPanel(), new GBConstraints(10,10).fill(Fill.BOTH));  //this pushes all components to the top left corner 
		return filter;

	}//end createfilterPanel

	/**
	 * This method builds the Details Panel
	 * 
	 * @returns the details panel
	 */
	protected JPanel createDetailsPanel(){			

		caseNumber = new JTextField(10);
		caseType = new JTextField(10);
		caseDate = new JTextField(10);
		caseLongitude = new JTextField(10);
		caseLatitude = new JTextField(10);
		caseElevation = new JTextField(10);

		caseNumber.setEditable(false);
		caseType.setEditable(false);
		caseDate.setEditable(false);
		caseLongitude.setEditable(false);
		caseLatitude.setEditable(false);
		caseElevation.setEditable(false);

		JPanel details = new JPanel();
		GridBagLayout gbl = new GridBagLayout();
		details.setLayout(gbl);

		details.add(new JLabel("Details"), new GBConstraints(0,0).anchor(Anchor.CENTER).spanX(2));

		details.add(new JLabel("Case Number: "), new GBConstraints(0,1).anchor(Anchor.WEST));
		details.add(caseNumber ,new GBConstraints(1,1).anchor(Anchor.WEST));

		details.add(new JLabel("Case Type: "),new GBConstraints(0,2).anchor(Anchor.WEST));
		details.add(caseType ,new GBConstraints(1,2).anchor(Anchor.WEST));

		details.add(new JLabel("Case Date: "),new GBConstraints(0,3).anchor(Anchor.WEST));
		details.add(caseDate ,new GBConstraints(1,3).anchor(Anchor.WEST));

		details.add(new JLabel("Longitude: "),new GBConstraints(0,4).anchor(Anchor.WEST));
		details.add(caseLongitude ,new GBConstraints(1,4).anchor(Anchor.WEST));

		details.add(new JLabel("Latitude: "),new GBConstraints(0,5).anchor(Anchor.WEST));
		details.add(caseLatitude ,new GBConstraints(1,5).anchor(Anchor.WEST));

		details.add(new JLabel("Elevation: "),new GBConstraints(0,6).anchor(Anchor.WEST));
		details.add(caseElevation ,new GBConstraints(1,6).anchor(Anchor.WEST));

		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(this);
		deleteButton.setEnabled(false);
		details.add(deleteButton,new GBConstraints(0,7).fill(Fill.HORIZONTAL));

		updateButton = new JButton("Update");
		updateButton.addActionListener(this);
		updateButton.setEnabled(false);
		details.add(updateButton,new GBConstraints(1,7).fill(Fill.HORIZONTAL));

		details.add(new JSeparator(JSeparator.HORIZONTAL), new GBConstraints(0,8).fill(Fill.HORIZONTAL).spanX(2).insets(5, 2, 5, 2));

		details.add(new JLabel("Database functions:"),new GBConstraints(0,9).fill(Fill.HORIZONTAL).spanX(2));

		uploadToDatabase = new JButton("Upload To Databse");
		uploadToDatabase.setActionCommand("Up");
		uploadToDatabase.addActionListener(this);
		uploadToDatabase.setEnabled(false);
		details.add(uploadToDatabase,new GBConstraints(0,10).fill(Fill.HORIZONTAL).spanX(2));

		downloadFromDatabase = new JButton("Download From Database");
		downloadFromDatabase.setActionCommand("Down");
		downloadFromDatabase.addActionListener(this);
		downloadFromDatabase.setEnabled(false);                                                         //when admin see file open
		details.add(downloadFromDatabase,new GBConstraints(0,11).fill(Fill.HORIZONTAL).spanX(2).insets(5, 2, 5, 2));

		databaseStatus = new JTextField("Status:");
		databaseStatus.setEditable(false);
		databaseStatus.setHorizontalAlignment(JTextField.LEFT);
		details.add(databaseStatus ,new GBConstraints(0,12).anchor(Anchor.WEST).fill(Fill.HORIZONTAL).spanX(2));

		details.add(new JPanel(), new GBConstraints(13,13).fill(Fill.BOTH));  //this pushes all components to the top left corner 
		return details; 

	}//end createDetailsPanel

	/**
	 * @param panels the Panel with cardLayout. 
	 * 
	 * This method returns the name of the visible panel in a cardLayout Panel. 
	 * 
	 */
	protected String nameOfVisiblePanel(JPanel panels){
		JPanel card = null;
		for (Component comp : panels.getComponents()) {
			if (comp.isVisible() == true) {
				card = (JPanel) comp;
			}//end if
		}//end for
		return card.getName();
	}//end nameOfVisiblePanel()


	/**
	 * This method restores data opened originally.
	 * It includes data that has been updated on the details panel
	 */
	private void restoreData(){
		if(filteredCrashData != null && crashData != null){
			if(!crashData.equals(filteredCrashData)){
				filteredCrashData = crashData.copy();

				if(pedestriansLayer != null && cyclistsLayer != null){
					worldWindGlobe.getWwd().getModel().getLayers().remove(pedestriansLayer);    //remove the layer
					worldWindGlobe.getWwd().getModel().getLayers().remove(cyclistsLayer);       //remove the layer
				}
				cyclistsLayer = filteredCrashData.createPlotData(CaseType.BICYCLE);
				pedestriansLayer = filteredCrashData.createPlotData(CaseType.PEDESTRIAN);

				ApplicationTemplate.insertBeforeCompass(worldWindGlobe.getWwd(), cyclistsLayer);
				ApplicationTemplate.insertBeforeCompass(worldWindGlobe.getWwd(), pedestriansLayer);
			}//end if(!crashData.equals(filteredCrashData))
		}//end if(filteredCrashData != null)
	}//end restoreData

	/**
	 * This method responds to users inputs
	 */
	private void createFilteredData(){
		filteredCrashData = crashData.filterByDate(beginDate, endDate);

		System.out.println("Size of data is now: " + filteredCrashData.size());
		System.out.println("Size of data was: " + crashData.size());

		System.out.println(filteredCrashData.toString());
		//filter here
		if(pedestriansLayer != null && cyclistsLayer != null){
			worldWindGlobe.getWwd().getModel().getLayers().remove(pedestriansLayer);    //remove the layer
			worldWindGlobe.getWwd().getModel().getLayers().remove(cyclistsLayer);       //remove the layer
		}
		cyclistsLayer = filteredCrashData.createPlotData(CaseType.BICYCLE);
		pedestriansLayer = filteredCrashData.createPlotData(CaseType.PEDESTRIAN);

		ApplicationTemplate.insertBeforeCompass(worldWindGlobe.getWwd(), cyclistsLayer);
		ApplicationTemplate.insertBeforeCompass(worldWindGlobe.getWwd(), pedestriansLayer);

	}//end createFilteredData

	/**
	 * This method responds to users inputs
	 */	
	private void resetFilters(){
		pedestriansLayer.setEnabled(true);
		cyclistsLayer.setEnabled(true);

		pedestrians.setSelected(true);
		cyclists.setSelected(true);
	}

	/**
	 * This method responds to users inputs
	 */	
	private void resetDatePickers(){

		beginDate = defaultBeginDate;
		Calendar cal = Calendar.getInstance();
		cal.setTime(beginDate);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);

		beginDatePicker.getModel().setDate(year,month,day);

		endDate = todaysDate;
		cal.setTime(endDate);
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH);
		day = cal.get(Calendar.DAY_OF_MONTH);
		endDatePicker.getModel().setDate(year,month,day);



	}//end resetDatePickers

	/**
	 * This method responds to users inputs
	 */
	private void disableDetailsPanel(){
		updateButton.setEnabled(false);
		deleteButton.setEnabled(false);

		caseNumber.setEditable(false);
		caseNumber.setText("");
		caseType.setEditable(false);
		caseType.setText("");
		caseDate.setEditable(false);
		caseDate.setText("");
		caseLongitude.setEditable(false);
		caseLongitude.setText("");
		caseLatitude.setEditable(false);
		caseLatitude.setText("");
		caseElevation.setEditable(false);
		caseElevation.setText("");

		databaseStatus.setText("Status: ");

	}//end DisableDetailsPanel


	/**
	 * This method responds to users inputs
	 */
	private void handleDeleteButton(){
		//updates both the original data and filtered data
		//if filtered data exists.
		if(filteredCrashData.getUserLevel() == UserLevelType.ADMIN){
			//remove old data point
			filteredCrashData.removeDataPointByCaseNumber(caseNumber.getText());     //filtered data is a subset of crashData
			crashData.removeDataPointByCaseNumber(caseNumber.getText());   

			//refresh globe
			if(pedestriansLayer != null && cyclistsLayer != null){
				worldWindGlobe.getWwd().getModel().getLayers().remove(pedestriansLayer);         //remove the layer
				worldWindGlobe.getWwd().getModel().getLayers().remove(cyclistsLayer);            //remove the layer
			}
			cyclistsLayer = filteredCrashData.createPlotData(CaseType.BICYCLE);                  //create layer
			pedestriansLayer = filteredCrashData.createPlotData(CaseType.PEDESTRIAN);            //create layer
			ApplicationTemplate.insertBeforeCompass(worldWindGlobe.getWwd(), cyclistsLayer);     //plot layer
			ApplicationTemplate.insertBeforeCompass(worldWindGlobe.getWwd(), pedestriansLayer);  //plot layer

			if(pedestrians.isSelected()){
				pedestriansLayer.setEnabled(true);
			}else{
				pedestriansLayer.setEnabled(false);
			}
			
			if(cyclists.isSelected()){
				cyclistsLayer.setEnabled(true);
			}else{
				cyclistsLayer.setEnabled(false);
			}
			
			caseNumber.setText("");
			caseType.setEditable(false);
			caseType.setText("");
			caseDate.setEditable(false);
			caseDate.setText("");
			caseLongitude.setEditable(false);
			caseLongitude.setText("");
			caseLatitude.setEditable(false);
			caseLatitude.setText("");
			caseElevation.setEditable(false);
			caseElevation.setText("");

		}//end if(filteredCrashData.getUserLevel() == UserLevelType.ADMIN

	}//end handleDeleteButton()


	/**
	 * This method responds to users inputs
	 */
	private void handleUpdateButton(){
		//updates both the original data and filtered data
		//if filtered data exists.
		if(filteredCrashData.getUserLevel() == UserLevelType.ADMIN){

			//Prepare data to insert
			int caseNum = Integer.parseInt(caseNumber.getText());
			CaseType theType = CaseType.setType(caseType.getText());

			Date theDate = filteredCrashData.getDataPointByCaseNumber(caseNum).getTheDate(); 

			double latitude = Double.parseDouble(caseLatitude.getText());
			double longitude = Double.parseDouble(caseLongitude.getText());
			double elevation = Double.parseDouble(caseElevation.getText());

			//remove old data point
			filteredCrashData.removeDataPointByCaseNumber(caseNumber.getText());     //filtered data is a subset of crashData
			crashData.removeDataPointByCaseNumber(caseNumber.getText());             //

			//create new data point
			DataPoint temp = new DataPoint(caseNum, theType, theDate, latitude, longitude, elevation);
			filteredCrashData.addDataPoint(temp);                                   //filtered data is a subset of crashData
			crashData.addDataPoint(temp);                                           //

			//refresh globe
			if(pedestriansLayer != null && cyclistsLayer != null){
				worldWindGlobe.getWwd().getModel().getLayers().remove(pedestriansLayer);         //remove the layer
				worldWindGlobe.getWwd().getModel().getLayers().remove(cyclistsLayer);            //remove the layer
			}
			cyclistsLayer = filteredCrashData.createPlotData(CaseType.BICYCLE);                  //create layer
			pedestriansLayer = filteredCrashData.createPlotData(CaseType.PEDESTRIAN);            //create layer
			ApplicationTemplate.insertBeforeCompass(worldWindGlobe.getWwd(), cyclistsLayer);     //plot layer
			ApplicationTemplate.insertBeforeCompass(worldWindGlobe.getWwd(), pedestriansLayer);  //plot layer
			
			if(pedestrians.isSelected()){
				pedestriansLayer.setEnabled(true);
			}else{
				pedestriansLayer.setEnabled(false);
			}
			
			if(cyclists.isSelected()){
				cyclistsLayer.setEnabled(true);
			}else{
				cyclistsLayer.setEnabled(false);
			}
			
		}//end if user level == ADMIN
	}//end handleUpdateButton()

	/**
	 * This method responds to users inputs
	 */
	private void openFileAndParseData(){
		Boolean old = UIManager.getBoolean("FileChooser.readOnly");  
		UIManager.put("FileChooser.readOnly", Boolean.TRUE);  
		JFileChooser fc = new JFileChooser(".");  
		UIManager.put("FileChooser.readOnly", old);  
		FileNameExtensionFilter filter = new FileNameExtensionFilter("*.CSV", "csv");
		fc.setFileFilter(filter);

		int returnVal = fc.showOpenDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			//This is where a real application would open the file.
			System.out.println("Opening: " + file.getName() + ".");

			if(!resetDateFilterButton.isEnabled() || !applyDateFilterButton.isEnabled()){
				resetDateFilterButton.setEnabled(true);
				applyDateFilterButton.setEnabled(true);
			}

			if(cyclistsLayer != null && pedestriansLayer != null){                          //if there is an existing dataLayer, delete it first.
				crashData.clear();                                                          //clear all the data
				filteredCrashData.clear();
				worldWindGlobe.getWwd().getModel().getLayers().remove(pedestriansLayer);    //remove the layer
				worldWindGlobe.getWwd().getModel().getLayers().remove(cyclistsLayer);       //remove the layer
				cyclists.setSelected(true);
				pedestrians.setSelected(true);
				resetDatePickers();
				disableDetailsPanel();
			}//end if

			//				EventQueue.invokeLater(new Runnable() {
			//					public void run() {
			//						try {
			//						} catch (Exception e) {
			//							e.printStackTrace();
			//						}
			//					}
			//				});
			//TODO
			crashData.parseDataFrom(file);       //needs to be threaded, see above
			filteredCrashData = crashData.copy();

			if(filteredCrashData.getUserLevel().equals(UserLevelType.ADMIN)){
				uploadToDatabase.setEnabled(true);
			}

			cyclistsLayer = filteredCrashData.createPlotData(CaseType.BICYCLE);
			pedestriansLayer = filteredCrashData.createPlotData(CaseType.PEDESTRIAN);

			ApplicationTemplate.insertBeforeCompass(worldWindGlobe.getWwd(), cyclistsLayer);
			ApplicationTemplate.insertBeforeCompass(worldWindGlobe.getWwd(), pedestriansLayer);
		} else {
			System.out.println("Open command cancelled by user.");
		}//end if/else JFileChooser.APPROVE_OPTION

	}//end OpenFileAndParseData

	/**
	 * This method responds to users inputs
	 */
	private void uploadToDatabase(){

		try {
			// Create a connection to the database.
			Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

			String sql = "DELETE FROM caseLocations";
			PreparedStatement stmt1 = conn.prepareStatement(sql);

			// Send the DELETE statement to the DBMS.
			int rows1 = stmt1.executeUpdate();                         //delete all 

			// Display the results.
			//JOptionPane.showMessageDialog(null, rows1 + " row(s) deleted.");

			Statement stmt2 = conn.createStatement();

			// INSERT SQL statement
			String sqlStatement;

			SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd"); 

			int rows2 = 0;	
			for(DataPoint aPoint : crashData.getArrayList()){
				sqlStatement = " INSERT INTO caseLocations " +
						"(caseNumber, caseType, caseDate, caseLong, caseLat, caseElev) " +
						"VALUES ('" + aPoint.getCaseNumber() + "', '" + aPoint.getCaseType().toString() + "', '" +
						dateFormatter.format(aPoint.getTheDate()) + "', '" + aPoint.getLongitude() + 
						"', '" + aPoint.getLatitude() + "', '" + aPoint.getElevation() + "')";

				rows2 += stmt2.executeUpdate(sqlStatement);
			}//end for

			databaseStatus.setText("Status: " + rows2 + " row(s) added.");

			conn.close();

		}catch (Exception ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}//end try/catch

	}//end uploadToDatabase

	/**
	 * This method responds to users inputs
	 */
	private void downloadFromDatabase(){

		try {
			// Create a connection to the database.
			Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

			Statement stmt = conn.createStatement();

			String sql = "SELECT caseNumber, caseType, caseDate, caseLat, caseLong, caseElev FROM caseLocations";

			ResultSet result = stmt.executeQuery(sql);

			crashData.clear();                                                //clear all the data
			filteredCrashData.clear();                                        //clear all the data

			SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd"); 

			while(result.next()){

				int caseNum = Integer.parseInt(result.getString("caseNumber"));
				CaseType theType = CaseType.setType(result.getString("caseType"));
				Date theDate = dateFormatter.parse(result.getString("caseDate"));
				double latitude = Double.parseDouble(result.getString("caseLat"));
				double longitude = Double.parseDouble(result.getString("caseLong"));
				double elevation = Double.parseDouble(result.getString("caseElev"));

				crashData.addDataPoint(new DataPoint(caseNum, theType, theDate, latitude, longitude, elevation));
			}//end while

			conn.close();                                                                        //close the connection

			filteredCrashData = crashData.copy();

			//refresh globe
			if(pedestriansLayer != null && cyclistsLayer != null){
				worldWindGlobe.getWwd().getModel().getLayers().remove(pedestriansLayer);         //remove the layer
				worldWindGlobe.getWwd().getModel().getLayers().remove(cyclistsLayer);            //remove the layer
			}
			cyclistsLayer = filteredCrashData.createPlotData(CaseType.BICYCLE);                  //create layer
			pedestriansLayer = filteredCrashData.createPlotData(CaseType.PEDESTRIAN);            //create layer
			ApplicationTemplate.insertBeforeCompass(worldWindGlobe.getWwd(), cyclistsLayer);     //plot layer
			ApplicationTemplate.insertBeforeCompass(worldWindGlobe.getWwd(), pedestriansLayer);  //plot layer

			if(pedestrians.isSelected()){
				pedestriansLayer.setEnabled(true);
			}else{
				pedestriansLayer.setEnabled(false);
			}
			
			if(cyclists.isSelected()){
				cyclistsLayer.setEnabled(true);
			}else{
				cyclistsLayer.setEnabled(false);
			}
	
			databaseStatus.setText("Status: " + filteredCrashData.size() + " row(s) downloaded.");

		}catch (Exception ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}//end try/catch

	}//end downloadFromDatabase



	/**
	 * This method responds to users inputs
	 * 
	 * @Override actionPerformed (ActionEvent event)
	 */
	public void actionPerformed(ActionEvent event) {

		System.out.println("command: " + event.getActionCommand());

		if(event.getActionCommand().equals("Open")){

			openFileAndParseData();

		}else if(event.getActionCommand().equals("Exit")){
			//shutdown() save stuff
			System.exit(0);

		}else if(event.getActionCommand().equals("Date selected")){
			Date newEndDate = (Date) endDatePicker.getModel().getValue();
			Date newBeginDate = (Date) beginDatePicker.getModel().getValue();

			if(newBeginDate != null && newEndDate != null){

				if(beginDate.equals(endDate)){
					System.out.println("Same Date!!");
				}//Debug string

				SimpleDateFormat simpleDate = new SimpleDateFormat("MM/dd/yyyy");
				if(newBeginDate.after(newEndDate)){
					String errorMessage = "\nThe beginning search date of: " + simpleDate.format(newBeginDate) + 
							" is\nafter the ending search date of: " + simpleDate.format(newEndDate) + "\n";
					JOptionPane.showMessageDialog(null, errorMessage, "Date selection error", JOptionPane.ERROR_MESSAGE );

					this.resetDatePickers();

					System.out.println("Can't do this! " + simpleDate.format(newBeginDate) + " to " + simpleDate.format(newEndDate));
				}else{

					beginDate = newBeginDate;
					endDate = newEndDate;

					System.out.println(simpleDate.format(newBeginDate) + " to " + simpleDate.format(newEndDate));
				}//end inner if
			}//end if(endDate != null
		}//if either datepicker changed

		if(event.getActionCommand().equals("Up")){
			uploadToDatabase();
		}else if(event.getActionCommand().equals("Down")){
			downloadFromDatabase();
		}//end if

		if(event.getActionCommand().equals("Pedestrians")){
			if(filteredCrashData != null && pedestriansLayer != null){
				if(pedestrians.isSelected()){
					pedestriansLayer.setEnabled(true);
				}else{
					pedestriansLayer.setEnabled(false);
				}//end if/else
			}//if(filteredCrashData != null
		}//end if(event.getActionCommand().equals("Pedestrians"))

		if(event.getActionCommand().equals("Cyclists")){
			if(filteredCrashData != null && cyclistsLayer != null){
				if(cyclists.isSelected()){
					cyclistsLayer.setEnabled(true);
				}else{
					cyclistsLayer.setEnabled(false);
				}//end if/else
			}//if(filteredCrashData != null
		}//end if(event.getActionCommand().equals("Cyclists"))

		if(event.getActionCommand().equals("Apply")){
			if(filteredCrashData.size() > 0){
				this.createFilteredData();		
			}//end if(filteredCrashData.size() > 0)
		}else if((event.getActionCommand().equals("Reset"))){
			if(filteredCrashData.size() > 0){
				this.resetDatePickers();
				this.restoreData();
				this.resetFilters();
			}//end if(filteredCrashData.size() > 0)
		}//end if Apply or Reset

		if(event.getActionCommand().equals("ADMIN")){
			this.createMenuBar();
			this.getContentPane().add(createfilterPanel(),BorderLayout.EAST);
			this.getContentPane().add(createDetailsPanel(), BorderLayout.WEST);

			cardLayoutController.show(cards, "World");

			crashData = new DataPointSet(UserLevelType.ADMIN);
			filteredCrashData = crashData.copy();

			downloadFromDatabase.setEnabled(true);

		}else if(event.getActionCommand().equals("USER")){
			this.createMenuBar();
			this.getContentPane().add(createfilterPanel(),BorderLayout.EAST);
			this.getContentPane().add(createDetailsPanel(), BorderLayout.WEST);

			cardLayoutController.show(cards, "World");

			crashData = new DataPointSet(UserLevelType.USER);
			filteredCrashData = crashData.copy();
		}//end if ADMIN or USER


		if(event.getActionCommand().equals("Update")){
			//updates both the original data and filtered data
			//if filtered data exists.
			this.handleUpdateButton();
		}else if(event.getActionCommand().equals("Delete")){
			//updates both the original data and filtered data
			//if filtered data exists.
			this.handleDeleteButton();		
		}//end if update/delete

	}//end actionPerformed

	public static void main(String[] args) {

		//Schedule a job for the event dispatch thread:
		//creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try{
					MIS_Project2 frame = new MIS_Project2();
					frame.setVisible(true);
				}catch(Exception e){
					e.printStackTrace();
				}//end try/catch
			}//end run()
		});//end invokeLater

	}//end main

	/**
	 *  Private inner class appSelectListener
	 */
	private class appSelectListener implements SelectListener{
		DataPoint mark = null;

		private String datePattern = "MM/dd/yyyy"; 
		private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

		@Override
		public void selected(SelectEvent event) {
			if (event.getTopObject() != null && event.isLeftClick()){

				if (event.getTopPickedObject().getParentLayer() instanceof MarkerLayer){
					PickedObject po = event.getTopPickedObject();
					//noinspection RedundantCast

					mark = (DataPoint) po.getObject();

					caseNumber.setText(Integer.toString(mark.getCaseNumber()));
					caseType.setText(mark.getCaseType().toString());  
					caseDate.setText(dateFormatter.format(mark.getTheDate()));
					caseLongitude.setText(Double.toString(mark.getLongitude()));
					caseLatitude.setText(Double.toString(mark.getLatitude())); 
					caseElevation.setText(Double.toString(mark.getElevation()));

					if(!updateButton.isEnabled() && filteredCrashData.getUserLevel() == UserLevelType.ADMIN){
						updateButton.setEnabled(true);
						deleteButton.setEnabled(true);
					}

					if(!caseLongitude.isEditable()){
						caseLongitude.setEditable(true);
						caseLatitude.setEditable(true);
						caseElevation.setEditable(true);
					}

					System.out.println("\nObject type: " + mark.toString());
					System.out.printf("Track position %s, %s, size = %f\n",
							po.getValue(AVKey.PICKED_OBJECT_ID).toString(),
							po.getPosition(), (Double) po.getValue(AVKey.PICKED_OBJECT_SIZE));

				}//end if(event.getTopPickedObject()
			}//end if LeftClick
		}//end selected

	}//end inner class appSelectListener 

	/**
	 * Private Inner class to format the date for the datepicker
	 * 
	 * @returns a DataLabelFormatter for the datepicker. see method above
	 */
	public class DateLabelFormatter extends AbstractFormatter {

		private String datePattern = "yyyy-MM-dd";
		private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

		@Override
		public Object stringToValue(String text) throws ParseException {
			return dateFormatter.parseObject(text);
		}//end stringToValue

		@Override
		public String valueToString(Object value) throws ParseException {
			if (value != null) {
				Calendar cal = (Calendar) value;
				return dateFormatter.format(cal.getTime());
			}

			return "";
		}//end stringToValue

	}//end inner class DateLabelFormatter

}//end class MIS_Project2 

