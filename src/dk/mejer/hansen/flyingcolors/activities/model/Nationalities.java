package dk.mejer.hansen.flyingcolors.activities.model;

import dk.mejer.hansen.flyingcolors.R;

public enum Nationalities {
	British(R.string.british, R.drawable.british_large_counter, R.drawable.british_large_counter_damaged), French(R.string.french, R.drawable.french_large_counter, R.drawable.french_large_counter_damaged), Danish(R.string.danish, R.drawable.danish_large_counter, R.drawable.danish_large_counter_damaged), Spanish(R.string.spanish, R.drawable.spanish_large_counter, R.drawable.spanish_large_counter_damaged), Dutch(R.string.dutch, R.drawable.dutch_large_counter, R.drawable.dutch_large_counter_damaged);

	private final int stringResourceId;
	private final int counterDrawableId;
	private final int dammagedCounterDrawableId;

	private Nationalities(int stringResourceId, int counterDrawableId, int dammagedCounterDrawableId) {
		this.stringResourceId = stringResourceId;
		this.counterDrawableId = counterDrawableId;
		this.dammagedCounterDrawableId = dammagedCounterDrawableId;
	}

	public int getStringResourceId() {
		return stringResourceId;
	}

	public int counterDrawableId() {
		return counterDrawableId;
	}

	public int dammagedCounterDrawableId() {
		return dammagedCounterDrawableId;
	}

	public static Nationalities fromDbString(String dbName) {
		for (Nationalities nation : Nationalities.values()) {
			if (nation.name().equalsIgnoreCase(dbName)) {
				return nation;
			}
		}
		throw new RuntimeException("Cound not find Natinality matching: '" + dbName + "'");
	}
}
