
package aProject;

import java.util.Date;

public class DataPoint extends Point{
	private int caseID;
	private int caseNumber;
	private CaseType theCaseType;
	private Date theDate;

	/**
	 * @param caseNumber
	 * @param type
	 * @param theDate
	 */
	public DataPoint(int caseID, int caseNumber, CaseType type, Date theDate, double latitude, double longitude) {
		super(latitude,longitude,0);
		this.caseID = caseID;
		this.caseNumber = caseNumber;
		this.theCaseType = type;
		this.theDate = theDate;
	}//end constructor

	/**
	 * @return the caseNumber
	 */
	public int getCaseNumber() {
		return caseNumber;
	}
	
	/**
	 * @param caseNumber the caseNumber to set
	 */
	public void setCaseNumber(int caseNumber) {
		this.caseNumber = caseNumber;
	}
	
	/**
	 * @return the caseType
	 */
	public CaseType getCaseType() {
		return theCaseType;
	}
	
	/**
	 * @param caseType the caseType to set
	 */
	public void setCaseType(CaseType caseType) {
		this.theCaseType = caseType;
	}
	
	/**
	 * @return the theDate
	 */
	public Date getTheDate() {
		return theDate;
	}
	
	/**
	 * @param theDate the theDate to set
	 */
	public void setTheDate(Date theDate) {
		this.theDate = theDate;
	} 
	
	/**
	 * @Override theDate the theDate to set
	 */
	public String toString() {
		String temp = String.format("%2s %10s %10s %10s %5.2f %5.2f",caseID, caseNumber, theCaseType, theDate, this.getLatitude(), this.getLongitude());
		return temp;
	} 
	
	
}//end class DataPoint
