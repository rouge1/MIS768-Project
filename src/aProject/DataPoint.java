package aProject;

import java.util.Date;

/**
 * The DataPoint class extends the Point class and
 * stores data about an accident (case)
 * represented by a data point.
 * 
 * @author Group #2
 * @version 1.0
 */


public class DataPoint extends Point{
	private int caseNumber;			// Variable to store accident's case number.
	private CaseType theCaseType;	// Variable to store the accident's case type.
	private Date theDate;			// Variable to store the date of the accident.

	/**
	 * This constructor initializes the data point's case number, case type, and case date.
	 * It also initializes the data point's latitude and longitude to 0 using the
	 * Point (parent class) class's default constructor.
	 * @param caseNumber int. The data point's case number.
	 * @param type CaseType. The data point's case type.
	 * @param theDate Date. The data point's case date.
	 * @param latitude double. The data point's longitude.
	 * @param longitude double. The data point's latitude.
	 */
	public DataPoint(int caseNumber, CaseType type, Date theDate, double latitude, double longitude) {
		super(latitude,longitude,0); 
		this.caseNumber = caseNumber;
		this.theCaseType = type;
		this.theDate = theDate;
	} // end of constructor

	/**
	 * This constructor initializes the data point's case number, case type, and case date.
	 * It also initializes the data point's latitude, longitude, and elevation using the
	 * Point (parent class) class's default constructor.
	 * @param caseNumber int. The data point's case number.
	 * @param type CaseType. The data point's case type.
	 * @param theDate Date. The data point's case date.
	 * @param latitude double. The data point's latitude.
	 * @param longitude double. The data point's longitude.
	 * @param elevation double. The data point's elevation.
	 */
	public DataPoint(int caseNumber, CaseType type, Date theDate, double latitude, double longitude, double elevation) {
		super(latitude,longitude,elevation);
		this.caseNumber = caseNumber;
		this.theCaseType = type;
		this.theDate = theDate;
	} // end of constructor
	
	/**
	 * The getCaseNumber method returns the data point's
	 * case number.
	 * @return The data point's case number value.
	 */
	public int getCaseNumber() {
		return caseNumber;
	} // end of getCaseNumber method
	
	/**
	 * The setCaseNumber method sets the data point's
	 * case number.
	 * @param caseNumber int. The data point's case number.
	 */
	public void setCaseNumber(int caseNumber) {
		this.caseNumber = caseNumber;
	} // end of setCaseNumber method
	
	/**
	 * The getCaseType method returns the data point's
	 * case type.
	 * @return The data point's case type value.
	 */
	public CaseType getCaseType() {
		return theCaseType;
	} // end of getCaseType method
	
	/**
	 * The setCaseType method sets the data point's
	 * case type.
	 * @param caseType CaseType. The data point's case type.
	 */
	public void setCaseType(CaseType caseType) {
		this.theCaseType = caseType;
	} // end of setCaseType method
	
	/**
	 * The getTheDate method returns the data point's 
	 * case date (accident date).
	 * @return The data point's case date value.
	 */
	public Date getTheDate() {
		return theDate;
	} // end of getTheDate method
	
	/**
	 * The setTheDate method sets the data point's case date (accident date).
	 * @param theDate Date. The data point's case date.
	 */
	public void setTheDate(Date theDate) {
		this.theDate = theDate;
	} // end of setTheDate method
	
	/**
	 * The toString method sets the data point's attributes to a
	 * String representation and overrides the superclass's (Point class) method.
	 */
	public String toString() {
		String temp = String.format("%10s %10s %10s %23s", caseNumber, theCaseType, theDate, this.getPosition().toString());
		return temp;
	} // end of toString method
		
} // end of class DataPoint
