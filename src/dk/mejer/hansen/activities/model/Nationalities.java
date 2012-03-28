package dk.mejer.hansen.activities.model;

import dk.mejer.hansen.R;

public enum Nationalities  {
	British(R.string.british), French(R.string.french), Danish(R.string.danish), Spanish(R.string.spanish);
	
	private final int stringResourceId;

	private Nationalities(int stringResourceId) {
		this.stringResourceId = stringResourceId;
	}

	public int getStringResourceId() {
		return stringResourceId;
	}
}
