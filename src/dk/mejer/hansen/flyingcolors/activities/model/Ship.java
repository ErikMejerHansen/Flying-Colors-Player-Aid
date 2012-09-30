package dk.mejer.hansen.flyingcolors.activities.model;

import android.database.Cursor;

public class Ship {

	private String name, gunnery_and_actual_rate;
	private Rates rate, rateWhenDammaged;
	private RateColors rateColor, rateColorWhenDamaged;
	private RateSymbols rateSymbol, rateSymbolWhenDamaged;
	private int marines, marinesWhenDamaged;
	private int vpValue;
	private int damageCapacity, damageCapacityWhenDamaged;
	private Nationalities nationality;
	private boolean onDamagedSide = false;

	public Ship(Cursor cursor) {
		name = cursor.getString(1);
		gunnery_and_actual_rate = cursor.getString(2);
		rate = Rates.fromDbString(cursor.getString(3));
		rateColor = RateColors.fromDbString(cursor.getString(4));
		rateSymbol = RateSymbols.fromDbString(cursor.getString(5));
		damageCapacity = cursor.getInt(6);
		marines = cursor.getInt(7);
		rateWhenDammaged = Rates.fromDbString(cursor.getString(8));
		damageCapacityWhenDamaged = cursor.getInt(9);
		marinesWhenDamaged = cursor.getInt(10);
		vpValue = cursor.getInt(11);
		nationality = Nationalities.valueOf(cursor.getString(12));
		rateColorWhenDamaged = RateColors.fromDbString(cursor.getString(13));
		rateSymbolWhenDamaged = RateSymbols.fromDbString(cursor.getString(14));
	}

	public int getCurrentDamageCapacity() {
		if (onDamagedSide) {
			return damageCapacityWhenDamaged;
		} else {
			return damageCapacity;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGunnery_and_actual_rate() {
		return gunnery_and_actual_rate;
	}

	public Rates getCurrentRate() {
		if (onDamagedSide) {
			return rateWhenDammaged;
		} else {
			return rate;
		}
	}

	public int getCurrentMarines() {
		if (onDamagedSide) {
			return marinesWhenDamaged;
		} else {
			return marines;
		}
	}

	public Nationalities getNationality() {
		return nationality;
	}

	public RateColors getCurrentRateColor() {
		if (onDamagedSide) {
			return rateColorWhenDamaged;
		} else {
			return rateColor;
		}
	}

	public RateSymbols getCurrentRateSymbol() {
		if (onDamagedSide) {
			return rateSymbolWhenDamaged;
		} else {
			return rateSymbol;
		}
	}

	@Override
	public String toString() {
		return "Ship [name=" + name + ", gunnery_and_actual_rate=" + gunnery_and_actual_rate + ", rate=" + rate + ", rateWhenDammaged=" + rateWhenDammaged + ", rateColor=" + rateColor + ", rateSymbol=" + rateSymbol + ", marines=" + marines + ", marinesWhenDamaged=" + marinesWhenDamaged + ", vpValue=" + vpValue + ", damageCapacity=" + damageCapacity + ", damageCapacityWhenDamaged=" + damageCapacityWhenDamaged + ", nationality=" + nationality + "]";
	}

	public void flip() {
		onDamagedSide = !onDamagedSide;
	}

	public boolean isDamaged() {
		return onDamagedSide;
	}

	public int getVPValue() {
		return vpValue;
	}
}
