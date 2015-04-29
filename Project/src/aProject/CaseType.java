package aProject;

/**
 * This class is enum for CaseType 
 */

public enum CaseType {
	BICYCLE,PEDESTRIAN;

	/**
	 * This method sets the caseType based on 
	 *  
	 * @param String to set the CaseType to
	 * @return the CaseType equal to the String version of the caseType
	 */
	static public CaseType setType(String input){
		CaseType temp = null;
		
		try{
			temp = CaseType.valueOf(input);
		}catch(IllegalArgumentException iae){
			System.out.println("Error: " + iae.toString());
		}//end try/catch
		
		return temp;
	}//end setType
	
}//end enum CaseType
