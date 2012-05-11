package dk.mejer.hansen.flyingcolors.activities.model;

public enum Period {
	Early, Late;

	public static Period fromYear(int year) {
		if(year < 1789) {
			return Early;
		} else {
			return Late;
		}
	}
}
