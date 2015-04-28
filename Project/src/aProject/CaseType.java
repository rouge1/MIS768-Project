package aProject;

/**
 * This enum lists all of the possible (accident) case types.
 * The case type is set for each accident (data point).
 * @author Group #2
 * @version 1.0
 */

public enum CaseType {
	BICYCLE,PEDESTRIAN;
	
	/**
	 * The setType method sets the case type.
	 * @param s String. The accident's case type.
	 * @return The accident's (data point's) case type.
	 */
	
	static public CaseType setType(String s){
		
		CaseType temp = null;
		
		try{
			temp = CaseType.valueOf(s);
		} 
		catch(IllegalArgumentException iae){
			System.out.println("Error: " + iae.toString());
		} // end of try/catch
		
		return temp;
	} // end of setType method
	
} // end of enum CaseType
