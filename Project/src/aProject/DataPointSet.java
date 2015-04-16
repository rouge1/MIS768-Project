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
	public void addDataPoint(DataPoint dp){
		dataSet.add(dp);
	}//end addData

	/**
	 * @param index return the DataPoint at index
	 */
	public DataPoint getDataPoint(String index){
		return dataSet.get(Integer.parseInt(index));
	}//end getDataPoint

	/**
	 * @param index return the DataPoint at index
	 */
	public DataPoint getDataPoint(int index){
		return dataSet.get(index);
	}//end getDataPoint

	/**
	 *@Override
	 */
	public void clear(){
		dataSet.clear();
	}

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
	 * @Override toString()
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
	 * @Override toString()
	 */
	protected MarkerLayer createPlotData(CaseType filterType)
	{
		ArrayList<Marker> markers = new ArrayList<Marker>();
		Marker a_marker= null;
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
		
		System.out.println("There are " + i + " " + filterType);
		
		MarkerLayer layer = new MarkerLayer(markers);

		layer.setOverrideMarkerElevation(true);
		layer.setKeepSeparated(false);                //turns off render batching 
		layer.setElevation(0);
		layer.setEnablePickSizeReturn(true);
	
		return layer;

	}//end createPlotData(CaseType filterType)

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
