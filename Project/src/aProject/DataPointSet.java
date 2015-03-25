package aProject;

import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.layers.MarkerLayer;
import gov.nasa.worldwind.render.markers.BasicMarker;
import gov.nasa.worldwind.render.markers.BasicMarkerAttributes;
import gov.nasa.worldwind.render.markers.Marker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class DataPointSet {

	private ArrayList<DataPoint> dataSet;
	private UserLevelType userLevel;

	/**
	 * @param dataSet
	 * @param userLevel
	 */
	public DataPointSet() {
		this.dataSet = new ArrayList<DataPoint>();
		this.userLevel = UserLevelType.USER;
	}//no-arg constructor

	/**
	 * @param dataSet
	 * @param userLevel
	 */
	public DataPointSet(UserLevelType userLevel) {
		this.dataSet = new ArrayList<DataPoint>();
		this.userLevel = userLevel;
	}//constructor

	/**
	 * @return the userLevel
	 */
	public UserLevelType getUserLevel() {
		return userLevel;
	}//getUserLevel

	/**
	 * @param userLevel the userLevel to set
	 */
	public void setUserLevel(UserLevelType userLevel) {
		this.userLevel = userLevel;
	}//setUserLevel


	/**
	 * @param dp the DataPoint to add to the ArrayList
	 */
	public void addData(DataPoint dp){
		dataSet.add(dp);
	}//end addData

	/**
	 * @param thefile the file name of the csv data
	 *        to parse into datapoints and create an 
	 *        arrayList from. 
	 *        
	 *        The assumption is the data layout is as follows:
	 *        ID, caseNumber, caseType, MM/dd/yyyy, longitude, latitude, (need elevation)
	 *        
	 */
	public void parseDataFrom(File theFile){
		int caseID;         
		int caseNum;        
		CaseType type;        
		Date thedateinput = null;  
		double longitude;   
		double latitude;    

		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		try{
			//Get scanner instance
			Scanner scanner = new Scanner(theFile);

			//Set the delimiter used in file
			scanner.useDelimiter(",");

			//Get all tokens and store them in some data structure
			//I am just printing them
			while (scanner.hasNext()){
				try{
					caseID = scanner.nextInt();
					caseNum = scanner.nextInt();
					type = CaseType.setType(scanner.next());
					try {	 
						thedateinput = (Date)formatter.parse(scanner.next());			 
					} catch (ParseException e) {
						JOptionPane.showMessageDialog(null, "Error parsing date. " + e.toString());
					}
					longitude = scanner.nextDouble();
					latitude = scanner.nextDouble();

					dataSet.add(new DataPoint(caseID, caseNum, type, thedateinput, latitude, longitude));
				}catch(Exception e){
					JOptionPane.showMessageDialog(null, "Error parsing CSV file. " + e.toString());
					break;
				}
			}//end while

			//Do not forget to close the scanner  
			scanner.close();

			System.out.println(this.toString());   //debugging here. remove me later

		}catch(FileNotFoundException fnfe){
			JOptionPane.showMessageDialog(null, fnfe.toString());
		}//end try/catch

	}//end parseData

	protected MarkerLayer createPlotData()
	{
		ArrayList<Marker> markers = new ArrayList<Marker>();
		Marker a_marker= null;
		
		for(DataPoint a : dataSet){
			a_marker = new BasicMarker(Position.fromDegrees(a.getLatitude(), a.getLongitude()), new BasicMarkerAttributes());
			markers.add(a_marker);
		} //end for

		MarkerLayer layer = new MarkerLayer(markers);
		
		layer.setOverrideMarkerElevation(true);
		layer.setElevation(0);
		layer.setEnablePickSizeReturn(true);

		return layer;

	}//end createPlotData

	/**
	 * @Override toString()
	 */
	public String toString(){
		StringBuffer temp = new StringBuffer();
		for(DataPoint a : dataSet){
			temp.append(a.toString() + "\n");  
		} //end for
		return temp.toString();
	}//end toString()

}//end class DataPointSet
