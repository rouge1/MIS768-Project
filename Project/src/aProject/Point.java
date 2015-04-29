package aProject;

/**
 * The Point class stores geographical marker data 
 * (latitude, longitude, and elevation) about an accident (point)
 * for Group #2's term group programming project.
 * @author Group #2
 * @version 1.0
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.markers.BasicMarker;
import gov.nasa.worldwind.render.markers.BasicMarkerAttributes;
import gov.nasa.worldwind.render.markers.BasicMarkerShape;

public class Point extends BasicMarker{

	//TODO Someday Daniel may want to implement Renderable (see ScalebarHint for example)
	
	/**
	 *  No-args constructor
	 */
	public Point() {
		super(Position.fromDegrees(0,0,0), new BasicMarkerAttributes(Material.WHITE, BasicMarkerShape.SPHERE, 1d));
	} // end of No-arg Constructor

	/**
	 * This Constructor sets three of the point's arguments: latitude, longitude, and elevation.
	 * @param latitude double. The point's latitude.
	 * @param longitude double. The point's longitude.
	 * @param elevation double. The point's elevation.
	 */
	public Point(double latitude, double longitude, double elevation) {
		super(Position.fromDegrees(latitude,longitude,elevation), new BasicMarkerAttributes(Material.WHITE, BasicMarkerShape.SPHERE, 1d));
	} // end of three-args constructor


	/**
	 * This Constructor sets four of the point's arguments: latitude, longitude,
	 * elevation, and basic marker attributes (which will display on the interactive
	 * globe).
	 * @param latitude double. The point's latitude.
	 * @param longitude double. The point's longitude.
	 * @param elevation double. The point's elevation.
	 * @param bma BasicMarkerAttributes. The point's marker attributes. 
	 */
	public Point(double latitude, double longitude, double elevation, BasicMarkerAttributes bma) {
		super(Position.fromDegrees(latitude,longitude,elevation), bma);
	} // end of four-args constructor

	/**
	 * The getLatitude method returns the point's 
	 * latitude.
	 * @return The point's latitude value.
	 */
	public double getLatitude(){
		double latitude = 0;
		
		String position = this.getPosition().toString();
		Pattern latitudePattern = Pattern.compile("\\((.*?)°");
		Matcher matcher = latitudePattern.matcher(position);
		
		if (matcher.find()){
			latitude = Double.parseDouble(matcher.group(1));
		} // end of if
		
		return latitude;
	} // end of getLatitude method

	/**
	 * The getLongitude method returns the point's
	 * longitude.
	 * @return The point's longitude value.
	 */
	public double getLongitude(){
		double longitude = 0;
		
		String position = this.getPosition().toString();
		Pattern longitudePattern = Pattern.compile(",(.*?)°");
		Matcher matcher = longitudePattern.matcher(position);
		
		if (matcher.find()){
			longitude = Double.parseDouble(matcher.group(1));
		} // end of if
		
		return longitude;
	} // end of getLongitude method
	
	/**
	 * The getElevation method returns the point's
	 * elevation.
	 * @return The point's elevation value.
	 */
	public double getElevation(){
		double elevation = 0;
		
		String position = this.getPosition().toString();

		Pattern elevationPattern = Pattern.compile(",.*?,(.*?)\\)$");
		Matcher matcher = elevationPattern.matcher(position);
		
		if (matcher.find()){
			elevation = Double.parseDouble(matcher.group(1));
		} // end of if
		
		return elevation;
	} // end of getElevation method
	
	/**
	 * The setLatitude method stores a value in 
	 * the latitude field.
	 * @param newLatitude double. The new latitude value of the point.
	 */
	public void setLatitude(double newLatitude){
		
		this.setPosition(Position.fromDegrees(newLatitude,this.getLongitude(),this.getElevation()));

	} // end of setLatitude method

	/**
	 * The setLongitude method stores a value
	 * in the longitude field.
	 * @param newLongitude double. The new longitude value of the point.
	 */
	public void setLongitude(double newLongitude){
 		
		this.setPosition(Position.fromDegrees(this.getLatitude(),newLongitude,this.getElevation()));
		
	} // end of setLongitude method
	
	/**
	 * The setElevation method stores a value
	 * in the elevation field.
	 * @param newElevation double. The new elevation value of the point.
	 */
	public void setElevation(double newElevation){
		
		this.setPosition(Position.fromDegrees(this.getLatitude(),this.getLongitude(),newElevation));
		
	} // end of setElevation method
	
} // end of class Point
