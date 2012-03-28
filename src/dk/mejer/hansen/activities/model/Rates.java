package dk.mejer.hansen.activities.model;

import dk.mejer.hansen.R;

public enum Rates {
	T(R.string.rate_t),
	G(R.string.rate_g), 
	Sixth(R.string.rate_6), 
	Fifth(R.string.rate_5), 	
	Fourth(R.string.rate_4), 
	Third(R.string.rate_3), 
	Second(R.string.rate_2), 
	First(R.string.rate_1), 
	Overgunned_first(R.string.rate_1_overgunned);
	
	private final int stringResourceId;

	private Rates(int stringResourceId) {
		this.stringResourceId = stringResourceId;
	}

	public int getStringResourceId() {
		return stringResourceId;
	}
	
}
