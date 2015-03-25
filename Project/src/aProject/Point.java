package aProject;

public class Point {
	private double latitude;
	private double longitude;
	private double elevation;

	/**
	 *  No arg-constructor
	 */
	public Point() {
		this.latitude = 0;
		this.longitude = 0;
		this.elevation = 0;
	}

	/**
	 * @param latitude
	 * @param longitude
	 * @param elevation
	 */
	public Point(double latitude, double longitude, double elevation) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.elevation = elevation;
	}

	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}//end getLatitude
	
	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}//end setLatitude
	
	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}//end getLongitude
	
	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}//end setLongitude
	
	/**
	 * @return the elevation
	 */
	public double getElevation() {
		return elevation;
	}//end getElevation
	
	/**
	 * @param elevation the elevation to set
	 */
	public void setElevation(double elevation) {
		this.elevation = elevation;
	}//end setElevation
	
}//end class Point
