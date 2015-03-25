package aProject;

public enum CaseType {
	BICYCLE,PEDESTRIAN;

	static public CaseType setType(String s){
		CaseType temp = null;
		
		try{
			temp = CaseType.valueOf(s);
		}catch(IllegalArgumentException iae){
			System.out.println("Error: " + iae.toString());
		}//end try/catch
		
		return temp;
	}//end setType
	
}//end enum CaseType
