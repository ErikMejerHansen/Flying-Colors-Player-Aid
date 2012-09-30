package dk.mejer.hansen.flyingcolors.activities.model;

import java.util.ArrayList;
import java.util.List;

public class BaseFirePowerCalculator {
	private int range = 1; // minimum rage is one
	private List<RateModifiers> rateRowModifers = new ArrayList<RateModifiers>();
	private Ship ship;
	private Scenario scenario;
	private int damageTaken;
	private int modifiedRow;

	public int calculateBaseFirepower() {
		int row = determineRateRow();
		modifiedRow = applyRateRowModifiers(row);
		modifiedRow += getDamageEffects();
		int unmodifiedBaseFirepower = lookupFirePower(modifiedRow, range);
		
		int modifiedFirepower = applyFirepowerModifiers(unmodifiedBaseFirepower);

		return modifiedFirepower;
	}

	private int getDamageEffects() {
		int damageEffect = -(damageTaken / 6);
		return damageEffect;
	}

	private int applyFirepowerModifiers(int unmodifiedBaseFirepower) {
		int modifiedBaseFirepower = unmodifiedBaseFirepower + getShadedAreaModifer();
		modifiedBaseFirepower += scenario.getAudacity(ship.getNationality());
		
		modifiedBaseFirepower += getPoinBlankModifier();
		modifiedBaseFirepower += getCarronadeBonus();
		modifiedBaseFirepower += addAudacityBonus();
		return modifiedBaseFirepower;
	}

	private int getPoinBlankModifier() {
		if(range == 1) {
			return 2;
		} else {
			return 0;
		}
	}

	private int getCarronadeBonus() {
		int bonus = 0;
		bonus += getCarronadeRangeBonus();
		bonus += getRateSymbolCarronadeBonus();
		
		return bonus;
	}



	private int getRateSymbolCarronadeBonus() {
		if(range <= 3) {
			if(ship.getCurrentRateSymbol() == RateSymbols.Hexagonal) {
				return 1;
			} else if(ship.getCurrentRateSymbol() == RateSymbols.Square) {
				return 2;
			}
		}
		return 0;
	}

	private int getCarronadeRangeBonus() {
		Period period = scenario.getPeriod();
		int rangeBonus = 0;
		if(range <= 3) {
			if(period == Period.Early && ship.getNationality() == Nationalities.British) {
				rangeBonus += 1;
			}
			if(period == Period.Late) {
				rangeBonus += 1;
			}
		}
		
		if(range <= 1) {
			if(period == Period.Early && ship.getNationality() == Nationalities.British) {
				rangeBonus += 1;
			}
			
			if(period == Period.Late) {
				rangeBonus += 1;
			}
		}
		
		return rangeBonus;
	}
	
	private int getShadedAreaModifer() {
		if(modifiedRow < 0) {
			return 0;
		}
		boolean[][] tableShading = { { true, false, false, false, false, false, false, false, false, false }, { true, false, false, false, false, false, false, false, false, false }, { true, true, false, false, false, false, false, false, false, false }, { true, true, true, false, false, false, false, false, false, false }, { true, true, true, false, false, false, false, false, false, false }, { true, true, true, false, false, false, false, false, false, false }, { true, true, true, true, false, false, false, false, false, false }, { true, true, true, true, true, false, false, false, false, false }, { true, true, true, true, true, false, false, false, false, false } };

		if (tableShading[modifiedRow][range - 1]) {// Table is base 1 indexed
			if (ship.getCurrentRateColor() == RateColors.Black) {
				return -1;
			} else if (ship.getCurrentRateColor() == RateColors.White) {
				return +1;
			} else {
				return 0;
			}
		}
		return 0;

	}

	private int addAudacityBonus() {
		return scenario.getAudacity(ship.getNationality());
	}

	private int applyRateRowModifiers(int row) {
		return row - rateRowModifers.size(); // Each modifier causes row to
												// decrease one
	}

	private int lookupFirePower(int row, int range) {
		if (row < 0) {
			return -1;
		}

		int[][] firepowerDeterminationTable = { {  2,  1,  1,  0,  0, -1, -1, -1, -1, -1 }, 
												{  3,  2,  2,  1,  0, -1, -1, -1, -1, -1 }, 
												{  4,  3,  3,  2,  1,  1,  0, -1, -1, -1 }, 
												{  7,  5,  4,  3,  2,  2,  1,  0, -1, -1 }, 
												{ 10,  7,  6,  5,  4,  3,  2,  1,  0, -1 }, 
												{ 12,  9,  8,  6,  5,  4,  2,  2,  1,  0 }, 
												{ 15, 12, 10,  7,  6,  5,  3,  2,  2,  1 }, 
												{ 17, 14, 12,  9,  8,  7,  5,  3,  2,  2 }, 
												{ 19, 16, 14, 11, 10,  8,  6,  4,  3,  2 } 
												};
		
		if(ship.getCurrentRateColor() == RateColors.Red && range > 5) {
			return -1;
		}
		return firepowerDeterminationTable[row][range - 1]; // Table is base 1
															// indexed
	}

	private int determineRateRow() {
		return ship.getCurrentRate().ordinal();
	}

	public void setShip(Ship ship) {
		this.ship = ship;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public void setIsAtFullSails(boolean atFullSails) {
		updateRateRowModifers(RateModifiers.Full_sails, atFullSails);
	}

	public void setIsTacking(boolean isTacking) {
		updateRateRowModifers(RateModifiers.Tacking, isTacking);
	}

	public void setIsPartialBroadside(boolean isPartialBroadSide) {
		updateRateRowModifers(RateModifiers.Partial_broadside, isPartialBroadSide);
	}

	public void setIsRotatingAtAnchor(boolean isRotatingAtAchor) {
		updateRateRowModifers(RateModifiers.Rotating_at_anchor, isRotatingAtAchor);
	}

	public void setIsDismasted(boolean isDismasted) {
		updateRateRowModifers(RateModifiers.Dismasted, isDismasted);
	}

	public void setIsOnFire(boolean isOnFire) {
		updateRateRowModifers(RateModifiers.On_fire, isOnFire);
	}

	public void setScenario(Scenario scenario) {
		this.scenario = scenario;
	}

	private void updateRateRowModifers(RateModifiers modifier, boolean shouldBeApplied) {
		if (shouldBeApplied) {
			rateRowModifers.add(modifier);
		} else {
			rateRowModifers.remove(modifier);
		}
	}

	public void setDamageTaken(int damageTaken) {
		this.damageTaken = damageTaken;
	}
}
