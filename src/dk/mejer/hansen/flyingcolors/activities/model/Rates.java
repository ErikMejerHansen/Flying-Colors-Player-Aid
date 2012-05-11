package dk.mejer.hansen.flyingcolors.activities.model;

import dk.mejer.hansen.flyingcolors.R;

public enum Rates {
	T(R.string.rate_t), G(R.string.rate_g), Sixth(R.string.rate_6), Fifth(R.string.rate_5), Fourth(R.string.rate_4), Third(R.string.rate_3), Second(R.string.rate_2), First(R.string.rate_1), Overgunned_first(R.string.rate_1_overgunned);

	private final int stringResourceId;

	private Rates(int stringResourceId) {
		this.stringResourceId = stringResourceId;
	}

	public int getStringResourceId() {
		return stringResourceId;
	}

	public static Rates fromDbString(String dbString) {
		if (dbString.equalsIgnoreCase("T")) {
			return T;
		} else if (dbString.equalsIgnoreCase("G")) {
			return G;
		} else if (dbString.equalsIgnoreCase("6")) {
			return Sixth;
		} else if (dbString.equalsIgnoreCase("5")) {
			return Fifth;
		} else if (dbString.equalsIgnoreCase("4")) {
			return Rates.Fourth;
		} else if (dbString.equalsIgnoreCase("3")) {
			return Third;
		} else if (dbString.equalsIgnoreCase("2")) {
			return Second;
		} else if (dbString.equalsIgnoreCase("1")) {
			return Rates.First;
		} else if (dbString.equalsIgnoreCase("(1)")) {
			return Overgunned_first;
		} else {
			throw new RuntimeException("No Rate matching:'" + dbString + "'");
		}
	}
}
