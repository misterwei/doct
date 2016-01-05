package doct.document;

public class DocumentUtils {
	
	public static String getEndName(String startName){
		return "end"+startName;
	}
	
	public static String getEndName(DeclaredCommand command){
		if(command.isHasEnd()){
			return "end"+command.getStartName();
		}else{
			return null;
		}
	}
}
