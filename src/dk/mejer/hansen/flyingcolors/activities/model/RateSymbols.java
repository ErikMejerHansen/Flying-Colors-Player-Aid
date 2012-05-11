package dk.mejer.hansen.flyingcolors.activities.model;

public enum RateSymbols {
	Round, Square, Hexagonal, None;
	
	
	public static RateSymbols fromDbString(String dbString) {
		if(dbString.equalsIgnoreCase("circle")) {
			return Round;
		} else if(dbString.equalsIgnoreCase("square")) {
			return Square;
		} else if(dbString.equalsIgnoreCase("hex")) {
			return Hexagonal;
		} else if(dbString.equalsIgnoreCase("-")) {
			return None;
		} else {
			throw new RuntimeException("No RateShape matching:'" + dbString + "'");
		}
	}
}
