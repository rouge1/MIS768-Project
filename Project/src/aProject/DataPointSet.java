package aProject;

import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.layers.MarkerLayer;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.markers.BasicMarker;
import gov.nasa.worldwind.render.markers.BasicMarkerAttributes;
import gov.nasa.worldwind.render.markers.BasicMarkerShape;
import gov.nasa.worldwind.render.markers.Marker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * This class represents a collection of DataPoints and the associated userlevel of the user.  
 *       
 * @author Group #2
 * @version 1.0
 **/

public class DataPointSet {

	private ArrayList<DataPoint> dataSet;		//a panel holds loginScreen and the main app
	private UserLevelType userLevel;			//layout controller for panel holding loginScreen/main App

	/***
	 * Method is the constructor for this class
	 */
	public DataPointSet() {
		this.dataSet = new ArrayList<DataPoint>();
		this.userLevel = UserLevelType.USER;
	}//no-arg constructor

	/**
	 * Method is the constructor with UserLevelType
	 * @param userLevel the userlevel to set
	 */
	public DataPointSet(UserLevelType userLevel){
		this.dataSet = new ArrayList<DataPoint>();
		this.userLevel = userLevel;
	}//1-arg constructor

	/**
	 * Method is the constructor with UserLevelType and DataPointSet
	 * @param userLevel the userlevel to set
	 * @param dataToCopy a arraylist of dataPoints
	 */
	public DataPointSet(UserLevelType userLevel, DataPointSet dataToCopy){
		this.userLevel = userLevel;
		this.dataSet = new ArrayList<DataPoint>();
		for(DataPoint aDataPoint : dataToCopy.getArrayList()){
			this.dataSet.add(aDataPoint);
		}//end for		
	}//2-arg constructor

	/**
	 * This method sets the UserLevelType 
	 * @param userLevel the userLevel to set
	 */
	public void setUserLevel(UserLevelType userLevel) {
		this.userLevel = userLevel;
	}//setUserLevel

	/**
	 * This method gets the UserLevelType 
	 * @return the userLevel
	 */
	public UserLevelType getUserLevel() {
		return userLevel;
	}//getUserLevel

	/**
	 * This method gets the DataPoint by index number (String input) 
	 * @param index number in the array list
	 * @return a DataPoint
	 */
	public DataPoint getDataPoint(String index){
		return dataSet.get(Integer.parseInt(index));
	}//end getDataPoint

	/**
	 * This method gets the DataPoint by index number (int input) 
	 * @param index number in the array list
	 * @return a DataPoint
	 */
	public DataPoint getDataPoint(int index){
		return dataSet.get(index);
	}//end getDataPoint

	/**
	 * This method returns the DataPoint if the caseNumber matches
	 * @param caseNumber of an element in the array list
	 * @return the dataPoint. returns null if there is no match
	 */
	public DataPoint getDataPointByCaseNumber(int caseNumber){
		DataPoint temp = null;

		for(DataPoint aPoint : dataSet){
			if(aPoint.getCaseNumber() == caseNumber){
				temp =  aPoint;
			}
		}//end for

		return temp;
	}//end getDataPointByCaseNumber(int caseNumber)

	/**
	 * This method returns the DataPoint if the caseNumber matches
	 * @param caseNumber of an element in the array list
	 * @return the dataPoint. returns null if there is no match
	 */
	public DataPoint getDataPointByCaseNumber(String caseNumber){
		DataPoint temp = null;

		try{
			int parsedCaseNumber = Integer.parseInt(caseNumber);
			for(DataPoint aPoint : dataSet){
				if(aPoint.getCaseNumber() == parsedCaseNumber){
					temp = aPoint;
				}
			}//end for
		}catch(NumberFormatException nfe){
			System.out.println(nfe.getMessage());
		}

		return temp;
	}//end getDataPointByCaseNumber(String index)

	/**
	 * This method gets the array list of data
	 * @return the ArrayList of DataPoints
	 */
	public ArrayList<DataPoint> getArrayList(){
		return dataSet;
	}//end getDataPointSet

	/**
	 * This method adds a DataPoint to the array list in this class
	 * @param dp the DataPoint to add to the ArrayList
	 */
	public void addDataPoint(DataPoint dp){
		dataSet.add(dp);
	}//end addData

	/**
	 * This equals assumes that checkThis is a subset of the dataset
	 * if checkThis and the dataset is equal then returns true 
	 * if filtering has occured and checkThis is smaller then returns false. 
	 * @param checkThis the DataPointSet to check if equal to the dataset of this class
	 * @return boolean true or false
	 * 
	 */
	public boolean equals(DataPointSet checkThis){
		if(this.getUserLevel() == checkThis.getUserLevel()){
			for(DataPoint aPoint : dataSet){
				if(!checkThis.getArrayList().contains(aPoint)){
					return false;
				} 
			}
		}
		return true;
	}//end equals

	/**
	 * This method returns the size of the array list
	 * @return the the size of the array list of this class
	 */
	public int size(){
		return dataSet.size();
	}//end size()

	/**
	 * This method clears the array list 
	 */
	public void clear(){
		dataSet.clear();
	}

	/**
	 * This method returns a copy of the class
	 * @return a copy of this Dataset
	 */
	public DataPointSet copy(){
		DataPointSet copySet =  new DataPointSet(this.userLevel);

		for(DataPoint aDataPoint : dataSet){
			copySet.addDataPoint(aDataPoint);
		}//end for

		return copySet;
	}//end copy()

	/**
	 * This method removes DataPoint by casenumber (String input)
	 * @param caseNumberToFind the case # of datapoint to remove.  
	 */
	public void removeDataPointByCaseNumber(int caseNumberToFind){
		for(DataPoint aPoint : dataSet){
			if(aPoint.getCaseNumber() == caseNumberToFind){
				dataSet.remove(aPoint);
			}
		}//end for
	}//end removeByCaseNumber

	/**
	 * This method removes DataPoint by casenumber (int input)
	 * @param text of caseNumberToFind the case # of datapoint to remove. 
	 *        
	 *        This method removes a DataPoint based on the case number.
	 */
	public void removeDataPointByCaseNumber(String text){

		int caseNumberToFind = Integer.parseInt(text);

		for(int i = 0; i < dataSet.size(); i++){
			if(dataSet.get(i).getCaseNumber() == caseNumberToFind){
				dataSet.remove(i);
			}//end if
		}//for 

		//		for(DataPoint aPoint : dataSet){
		//			if(aPoint.getCaseNumber() == caseNumberToFind){
		//				aPoint.
		//				dataSet.remove(aPoint);
		//			}
		//		}//end for
	}//end removeByCaseNumber

	/**
	 * This method parses csv data into datapoints and creates an array list<p>
	 * The assumption is the data layout is as follows:<p>&nbsp &nbsp &nbsp &nbsp &nbsp
	 *        ID, caseNumber, caseType, MM/dd/yyyy, longitude, latitude, elevation
	 * @param theFile is the file name of the csv data to parse into datapoints and create an arrayList from.     
	 */
	public void parseDataFrom(File theFile){       
		int caseNum;        
		CaseType type;        
		Date thedateinput = null;  
		double longitude;   
		double latitude; 
		double elevation;   

		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		try{
			//Get scanner instance
			Scanner outerScanner = new Scanner(theFile);
			Scanner innerScanner = null;


			//Get all tokens and store them in some data structure
			//I am just printing them
			while (outerScanner.hasNext()){
				try{
					innerScanner = new Scanner(outerScanner.nextLine());
					//Set the delimiter used in file
					innerScanner.useDelimiter(",");
					caseNum = innerScanner.nextInt();
					type = CaseType.setType(innerScanner.next());
					try {	 
						thedateinput = (Date)formatter.parse(innerScanner.next());			 
					} catch (ParseException e) {
						JOptionPane.showMessageDialog(null, "Error parsing date. " + e.toString());
					}
					longitude = innerScanner.nextDouble();
					latitude = innerScanner.nextDouble();
					elevation = innerScanner.nextDouble();

					dataSet.add(new DataPoint(caseNum, type, thedateinput, latitude, longitude, elevation));
				}catch(Exception e){
					JOptionPane.showMessageDialog(null, "Error parsing CSV file. " + e.toString());
					break;
				}
			}//end while

			//Do not forget to close the scanner  
			outerScanner.close();
			innerScanner.close();

			System.out.println(this.toString());   //debugging here. remove me later

		}catch(FileNotFoundException fnfe){
			JOptionPane.showMessageDialog(null, fnfe.toString());
		}//end try/catch

	}//end parseData

	/**
	 * This method returns DataPointSet filtered by date 
	 * @param beginDate the start date of the window of data we are looking for
	 * @param endDate the end date of the window of data we are looking for
	 * @return DataPointSet the set of data inside of the window of dates
	 */
	public DataPointSet filterByDate(Date beginDate, Date endDate){
		DataPointSet filteredByDate =  new DataPointSet(this.userLevel);

		for(DataPoint aDataPoint : this.getArrayList()){

			if(aDataPoint.getTheDate().after(beginDate) && aDataPoint.getTheDate().before(endDate)){
				filteredByDate.addDataPoint(aDataPoint);
			}

			if(aDataPoint.getTheDate().equals(beginDate) && aDataPoint.getTheDate().equals(beginDate)){
				filteredByDate.addDataPoint(aDataPoint);
			}

		}//end for

		return filteredByDate;
	}//end filterByDate
							 
	/**
	 * This method returns MarkerLayer to put on the globe based on the data 
	 * @return A MarkerLayer to display on the World Wind Globe
	 */
	protected MarkerLayer createPlotData()
	{
		ArrayList<Marker> markers = new ArrayList<Marker>();
		BasicMarkerAttributes redSphere = new BasicMarkerAttributes(Material.RED, BasicMarkerShape.SPHERE, 1d);
		BasicMarkerAttributes greenSphere = new BasicMarkerAttributes(Material.GREEN, BasicMarkerShape.SPHERE, 1d);

		for(DataPoint a : dataSet){
			if(a.getCaseType().equals(CaseType.PEDESTRIAN)){
				a.setAttributes(redSphere);
			}else if(a.getCaseType().equals(CaseType.BICYCLE)){
				a.setAttributes(greenSphere);
			}
			markers.add(a);
		} //end for

		MarkerLayer layer = new MarkerLayer(markers);

		layer.setOverrideMarkerElevation(true);
		layer.setKeepSeparated(false);                //turns off render batching 
		layer.setElevation(0);
		layer.setEnablePickSizeReturn(true);

		return layer;

	}//end createPlotData

	/**
	 * This method returns MarkerLayer to put on the globe based on the data and caseType 
	 * @param filterType is the caseType to filter the data
	 * @return A MarkerLayer to display on the World Wind Globe
	 */
	protected MarkerLayer createPlotData(CaseType filterType)
	{
		ArrayList<Marker> markers = new ArrayList<Marker>();
	 	BasicMarkerAttributes redSphere = new BasicMarkerAttributes(Material.RED, BasicMarkerShape.SPHERE, 1d);
		BasicMarkerAttributes greenSphere = new BasicMarkerAttributes(Material.GREEN, BasicMarkerShape.SPHERE, 1d);

		int i = 0;


		for(DataPoint a : dataSet){
			switch (filterType){
			case PEDESTRIAN :
				if(a.getCaseType().equals(filterType)){
					//a_marker = new BasicMarker(Position.fromDegrees(0, 0), redSphere);
					a.setAttributes(redSphere);
					markers.add(a);
					i++;
				}
				break;
			case BICYCLE:
				if(a.getCaseType().equals(filterType)){
					a.setAttributes(greenSphere);
					markers.add(a);
					i++;
				}
				break;
			default:
				break;
			}//end case statement

		} //end for

		System.out.println("There are " + i + " " + filterType);   //DEBUG

		MarkerLayer layer = new MarkerLayer(markers);

		layer.setOverrideMarkerElevation(true);
		layer.setKeepSeparated(false);                //turns off render batching 
		layer.setElevation(0);
		layer.setEnablePickSizeReturn(true);

		return layer;

	}//end createPlotData(CaseType filterType)

	/**
	 * This returns a String representation of the data in the class
	 */
	public String toString(){
		StringBuffer temp = new StringBuffer();
		for(DataPoint a : dataSet){
			temp.append(a.toString() + "\n");  
		} //end for
		return temp.toString();
	}//end toString()

}//end class DataPointSet
