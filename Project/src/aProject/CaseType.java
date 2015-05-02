package aProject;

/**
 * This enum lists all of the possible (accident) case types.
 * The case type is set for each accident (data point).
 * 
 * @author Group #2
 * @version 1.0
 */

public enum CaseType {
	BICYCLE,PEDESTRIAN;


	/**
	 * This method sets the CaseType value.
	 * @param s String. The accident's case type.
	 * @return The accident's (data point's) CaseType value.
	 */
	static public CaseType setType(String input){

		CaseType temp = null;

		try{
			temp = CaseType.valueOf(input);
		}catch(IllegalArgumentException iae){
			System.out.println("Error: " + iae.toString());
		} // end of try/catch

		return temp;
	} // end of setType method

} // end of enum CaseType
