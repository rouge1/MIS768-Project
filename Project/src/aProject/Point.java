package aProject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.markers.BasicMarker;
import gov.nasa.worldwind.render.markers.BasicMarkerAttributes;
import gov.nasa.worldwind.render.markers.BasicMarkerShape;

public class Point extends BasicMarker{

	//TODO Someday you may want to implement Renderable (see ScalebarHint for example)
	
	/**
	 *  No arg-constructor
	 */
	public Point() {
		super(Position.fromDegrees(0,0,0), new BasicMarkerAttributes(Material.WHITE, BasicMarkerShape.SPHERE, 1d));
	}//end No arg-Constructor

	/**
	 * @param latitude
	 * @param longitude
	 * @param elevation
	 */
	public Point(double latitude, double longitude, double elevation) {
		super(Position.fromDegrees(latitude,longitude,elevation), new BasicMarkerAttributes(Material.WHITE, BasicMarkerShape.SPHERE, 1d));
	}// 3 arg constructor


	/**
	 * @param latitude
	 * @param longitude
	 * @param elevation
	 * @param bma - BasicMarkerAttributes 
	 */
	public Point(double latitude, double longitude, double elevation, BasicMarkerAttributes bma) {
		super(Position.fromDegrees(latitude,longitude,elevation), bma);
	}// 4 arg constructor

	/**
	 * @return the Latitude
	 */
	public double getLatitude(){
		double latitude = 0;
		
		String position = this.getPosition().toString();
		Pattern latitudePattern = Pattern.compile("\\((.*?)°");
		Matcher matcher = latitudePattern.matcher(position);
		if (matcher.find()){
			latitude = Double.parseDouble(matcher.group(1));
		}//end if
		
		return latitude;
	}//end getLatitude

	/**
	 * @return the Longitude
	 */
	public double getLongitude(){
		double longitude = 0;
		
		String position = this.getPosition().toString();
		Pattern longitudePattern = Pattern.compile(",(.*?)°");
		Matcher matcher = longitudePattern.matcher(position);
		if (matcher.find()){
			longitude = Double.parseDouble(matcher.group(1));
		}//end if
		
		return longitude;
	}//end getLongitude
	
	/**
	 * @return the elevation
	 */
	public double getElevation(){
		double elevation = 0;
		
		String position = this.getPosition().toString();

		Pattern elevationPattern = Pattern.compile(",.*?,(.*?)\\)$");
		Matcher matcher = elevationPattern.matcher(position);
		if (matcher.find()){
			elevation = Double.parseDouble(matcher.group(1));
		}//end if
		
		return elevation;
	}//end getElevation
	
	/**
	 * @param newLatitude - the new latitude
	 */
	public void setLatitude(double newLatitude){
		
		this.setPosition(Position.fromDegrees(newLatitude,this.getLongitude(),this.getElevation()));

	}//end setLatitude

	/**
	 * @param newLongitude - the new longitude
	 */
	public void setLongitude(double newLongitude){
 		
		this.setPosition(Position.fromDegrees(this.getLatitude(),newLongitude,this.getElevation()));
		
	}//end setLongitude
	
	/**
	 * @param newElevation - the new elevation
	 */
	public void setElevation(double newElevation){
		
		this.setPosition(Position.fromDegrees(this.getLatitude(),this.getLongitude(),newElevation));
		
	}//end setElevation
	
}//end class Point
