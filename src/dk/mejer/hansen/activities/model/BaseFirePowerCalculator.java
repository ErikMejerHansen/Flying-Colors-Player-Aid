package dk.mejer.hansen.activities.model;

public class BaseFirePowerCalculator {
	private Nationalities nationality;
	private Rates rate;
	private int range = 1; //minimum rage is one

	public int calculateBaseFirepower() {
		int row = determineRateRow();
		int unmodifiedBaseFirepower = lookupFirePower(row, range);
		
		//determinePointBlankModifier
		//determineCumulativeCarronadeBonus
		
		return unmodifiedBaseFirepower;
	}

	
	private int lookupFirePower(int row, int range) {
		int[][] firepowerDeterminationTable = {
				{ 2,  1,  1,  0,  0, -1, -1, -1, -1, -1},
				{ 3,  2,  2,  1,  0, -1, -1, -1, -1, -1},
				{ 4,  3,  3,  2,  1,  1,  0, -1, -1, -1},
				{ 7,  5,  4,  3,  2,  2,  1,  0, -1, -1},
				{10,  7,  6,  5,  4,  3,  2,  1,  0, -1},
				{12,  9,  8,  6,  5,  4,  2,  2,  1,  0},
				{15, 12, 10,  7,  6,  5,  3,  2,  2,  1}, 
				{17, 14, 12,  9,  8,  7,  5,  3,  2,  2},
				{19, 16, 14, 11, 10,  8,  6,  4,  3,  2}
		};

		return firepowerDeterminationTable[row][range - 1]; //Table is base 1 indexed
	}

	private int determineRateRow() {
		return rate.ordinal();
	}

	public void setNationality(Nationalities nationality) {
		this.nationality = nationality;
		
	}

	public void setRate(Rates rate) {
		this.rate = rate;
		
	}
	
	public void setRange(int range) {
		this.range = range;
		
	}

	
}
