package aProject;

/**
<<<<<<< HEAD
 * This class is enum for CaseType 
=======
 * This enum lists all of the possible (accident) case types.
 * The case type is set for each accident (data point).
 * @author Group #2
 * @version 1.0
>>>>>>> refs/remotes/origin/master
 */

public enum CaseType {
	BICYCLE,PEDESTRIAN;
<<<<<<< HEAD

	/**
	 * This method sets the caseType based on 
	 *  
	 * @param String to set the CaseType to
	 * @return the CaseType equal to the String version of the caseType
	 */
	static public CaseType setType(String input){
=======
	
	/**
	 * The setType method sets the case type.
	 * @param s String. The accident's case type.
	 * @return The accident's (data point's) case type.
	 */
	
	static public CaseType setType(String s){
		
>>>>>>> refs/remotes/origin/master
		CaseType temp = null;
		
		try{
<<<<<<< HEAD
			temp = CaseType.valueOf(input);
		}catch(IllegalArgumentException iae){
=======
			temp = CaseType.valueOf(s);
		} 
		catch(IllegalArgumentException iae){
>>>>>>> refs/remotes/origin/master
			System.out.println("Error: " + iae.toString());
		} // end of try/catch
		
		return temp;
	} // end of setType method
	
} // end of enum CaseType
