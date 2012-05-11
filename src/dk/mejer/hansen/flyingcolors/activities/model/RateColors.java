package dk.mejer.hansen.flyingcolors.activities.model;

import android.graphics.Color;

public enum RateColors {
	Yellow(Color.YELLOW), White(Color.WHITE), Black(Color.BLACK), Red(Color.RED), None(Color.TRANSPARENT);
	
	
	private final int color;
	private RateColors(int color) {
		this.color = color;
	}
	public static RateColors fromDbString(String dbString) {
		if(dbString.equalsIgnoreCase("yellow")) {
			return Yellow;
		} else if(dbString.equalsIgnoreCase("white")) {
			return White;
		} else if(dbString.equalsIgnoreCase("black")) {
			return Black;
		} else if(dbString.equalsIgnoreCase("Red")) {
			return Red;
		} else if(dbString.equalsIgnoreCase("-")) {
			return None;
		} else {
			throw new RuntimeException("No RateColor matching:'" + dbString + "'");
		}
	}
	public int getColor() {
		return color;
	}
}
