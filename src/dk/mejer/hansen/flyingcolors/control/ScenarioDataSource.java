package dk.mejer.hansen.flyingcolors.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import dk.mejer.hansen.flyingcolors.activities.model.Nationalities;
import dk.mejer.hansen.flyingcolors.activities.model.Scenario;

public class ScenarioDataSource {

	private DatabaseHelper dbHelper;
	private final Context context;
	private ShipDataSource shipDataSource;
	private SQLiteDatabase database;

	public ScenarioDataSource(Context context) {
		this.context = context;
		dbHelper = new DatabaseHelper(context);
		shipDataSource = new ShipDataSource(context);
	}

	protected void loadScenariosIntoDatabase() {
		 if (dataAlreadyLoaded()) {
			 return;
		 }

		database = dbHelper.getWritableDatabase();
		try {
			InputStreamReader reader = new InputStreamReader(context.getAssets().open("scenarios.csv"));
			BufferedReader bufferedReader = new BufferedReader(reader);
			String scenarioLine;
			while ((scenarioLine = bufferedReader.readLine()) != null) {
				long id = database.insert("scenarios", null, getScenarioValues(scenarioLine));
				insertScenarioShipRows(scenarioLine, id);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			database.close();
		}
	}

	private boolean dataAlreadyLoaded() {
		// TODO: Check the other tables
		SQLiteDatabase database = dbHelper.getReadableDatabase();
		Cursor cursor = database.rawQuery("select COUNT(*) from scenarios", null);
		cursor.moveToFirst();

		if (cursor.getInt(0) > 0) {
			cursor.close();
			database.close();
			return true;
		} else {
			cursor.close();
			database.close();
			return false;
		}
	}

	private ContentValues getScenarioValues(String scenarioLine) {

		StringTokenizer tokenizer = new StringTokenizer(scenarioLine, ";");
		ContentValues scenarioValues = new ContentValues();
		scenarioValues.put("name", tokenizer.nextToken());
		scenarioValues.put("year", tokenizer.nextToken());
		scenarioValues.put("side1", tokenizer.nextToken());
		scenarioValues.put("side2", tokenizer.nextToken());
		scenarioValues.put("british_audacity", tokenizer.nextToken());
		scenarioValues.put("french_audacity", tokenizer.nextToken());
		scenarioValues.put("dutch_audacity", tokenizer.nextToken());
		scenarioValues.put("danish_audacity", tokenizer.nextToken());
		scenarioValues.put("spanish_audacity", tokenizer.nextToken());

		// skip scenario ship tokens
		tokenizer.nextToken();
		tokenizer.nextToken();
		tokenizer.nextToken();
		tokenizer.nextToken();
		tokenizer.nextToken();

		scenarioValues.put("scenario_hint", tokenizer.nextToken());

		return scenarioValues;
	}

	private void insertScenarioShipRows(String scenarioLine, long scenarioId) {
		StringTokenizer tokenizer = new StringTokenizer(scenarioLine, ";");
		// Fast forward to ship tokens
		for (int i = 0; i < 9; i++) {
			tokenizer.nextToken();
		}

		insertScenarioShipRowsForNation(tokenizer.nextToken(), scenarioId, Nationalities.British);
		insertScenarioShipRowsForNation(tokenizer.nextToken(), scenarioId, Nationalities.French);
		insertScenarioShipRowsForNation(tokenizer.nextToken(), scenarioId, Nationalities.Dutch);
		insertScenarioShipRowsForNation(tokenizer.nextToken(), scenarioId, Nationalities.Danish);
		insertScenarioShipRowsForNation(tokenizer.nextToken(), scenarioId, Nationalities.Spanish);
	}

	private void insertScenarioShipRowsForNation(String shipsToken, long scenarioId, Nationalities nationality) {
		if(shipsToken.equals("\"-\"")) { //No ships for that nation in this scenario
			return;
		}
		shipsToken = shipsToken.substring(1, shipsToken.length() - 1); //Strip leading and trailing "-mark
		StringTokenizer tokenizer = new StringTokenizer(shipsToken, ",");
		while (tokenizer.hasMoreTokens()) {
			insertScenarioShip(tokenizer.nextToken(), nationality, scenarioId);
		}

	}

	private void insertScenarioShip(String shipName, Nationalities nationality, long scenarioId) {
		ContentValues scenarioShipValues = new ContentValues(2);
		scenarioShipValues.put("ship_fk", shipDataSource.getShipId(nationality, shipName));
		scenarioShipValues.put("scenario_fk", scenarioId);
		database.insert("scenario_ships", null, scenarioShipValues);
	}

	public static Cursor getScenarioNames(Context context) {
		SQLiteDatabase database = new DatabaseHelper(context).getReadableDatabase();
		Cursor cursor = database.query("scenarios", new String[] { "_id", "name" }, null, null, null, null, null);
		return cursor;
	}

	public Scenario getScenarioFromName(String scenarioName, Context context) {
		DatabaseHelper dbHelper = new DatabaseHelper(context);
		SQLiteDatabase database = dbHelper.getReadableDatabase();
		Scenario scenario = new Scenario(scenarioName);
		setScenarioData(scenario, database, scenarioName);
		database.close();
		return scenario;
	}


	private void setScenarioData(Scenario scenario, SQLiteDatabase database, String scenarioName) {
		Cursor cursor = database.query("scenarios", new String[]{"scenarios.year", 
																"scenarios.scenario_hint", 
																"scenarios.british_audacity", 
																"scenarios.french_audacity", 
																"scenarios.dutch_audacity", 
																"scenarios.danish_audacity", 
																"scenarios.spanish_audacity"}, "scenarios.name = ?", new String[]{scenarioName}, null, null, null);
		cursor.moveToFirst();
		scenario.setYear(cursor.getInt(0));
		scenario.setScenarioHint(cursor.getString(1));
		
		addNationToScenario(Nationalities.British, scenario, cursor.getString(2));
		addNationToScenario(Nationalities.French, scenario, cursor.getString(3));
		addNationToScenario(Nationalities.Dutch, scenario, cursor.getString(4));
		addNationToScenario(Nationalities.Danish, scenario, cursor.getString(5));
		addNationToScenario(Nationalities.Spanish, scenario, cursor.getString(6));
		
		cursor.close();
	}

	private void addNationToScenario(Nationalities nation, Scenario scenario, String audacity) {
		if(audacity.equals("-")) {
			return;
		} else {
			scenario.addNationality(nation, Integer.parseInt(audacity));
		}
		
	}

	public Cursor getScenarioFleet(Context context, String scenarioName) {
		DatabaseHelper helper = new DatabaseHelper(context);
		SQLiteDatabase database = helper.getReadableDatabase();
		return database.query("scenarios INNER JOIN scenario_ships ON scenarios._id = scenario_ships.scenario_fk INNER JOIN ships ON scenario_ships.ship_fk = ships._id INNER JOIN nationalities ON ships.nationality = nationalities._id", 
				new String[]{"ships._id", "ships.name", "ships.historical_rate", "ships.rate", "ships.rate_color", "ships.rate_symbol", "ships.damage_capacity", "ships.marines", "ships.rate_damaged", "ships.damage_capacity_damaged", "ships.marines_damaged", "ships.VP", "nationalities.name AS nationality", "ships.damaged_rate_color", "ships.damaged_rate_symbol"}, "scenarios.name = ?", new String[]{scenarioName}, null, null, "ships.nationality, ships.name");
	}
	
}





