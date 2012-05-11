package dk.mejer.hansen.flyingcolors.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import dk.mejer.hansen.flyingcolors.activities.model.Nationalities;

public class ShipDataSource {

	private DatabaseHelper dbHelper;
	private final Context context;

	public ShipDataSource(Context context) {
		this.context = context;
		dbHelper = new DatabaseHelper(context);
	}

	protected void loadShipsIntoDatabase() {
		if (dataAlreadyLoaded()) {
			return;
		}

		SQLiteDatabase database = dbHelper.getWritableDatabase();
		loadNations(database);
		try {
			InputStreamReader reader = new InputStreamReader(context.getAssets().open("ships.csv"));
			BufferedReader bufferedReader = new BufferedReader(reader);
			String shipLine;
			bufferedReader.readLine(); // skip header line
			while ((shipLine = bufferedReader.readLine()) != null) {
				database.insert("ships", null, getShipValues(shipLine));
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			database.close();
		}
	}

	private void loadNations(SQLiteDatabase database) {
		for (Nationalities nation : Nationalities.values()) {
			ContentValues nationValues = new ContentValues();
			nationValues.put("name", nation.name());
			database.insert("nationalities", null, nationValues);
		}
	}

	private boolean dataAlreadyLoaded() {
		// TODO: Check the other tables
		SQLiteDatabase database = dbHelper.getReadableDatabase();
		Cursor cursor = database.rawQuery("select COUNT(*) from ships", null);
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

	private ContentValues getShipValues(String shipLine) {
		StringTokenizer tokenizer = new StringTokenizer(shipLine, ",");
		ContentValues shipValues = new ContentValues();
		shipValues.put("name", tokenizer.nextToken());
		shipValues.put("historical_rate", tokenizer.nextToken());
		shipValues.put("rate", tokenizer.nextToken());
		shipValues.put("rate_color", tokenizer.nextToken());
		shipValues.put("rate_symbol", tokenizer.nextToken());
		shipValues.put("damage_capacity", tokenizer.nextToken());
		shipValues.put("marines", tokenizer.nextToken());
		shipValues.put("rate_damaged", tokenizer.nextToken());
		shipValues.put("damage_capacity_damaged", tokenizer.nextToken());
		shipValues.put("marines_damaged", tokenizer.nextToken());
		shipValues.put("VP", tokenizer.nextToken());
		shipValues.put("nationality", getNationDBId(Nationalities.fromDbString(tokenizer.nextToken())));
		shipValues.put("damaged_rate_color", tokenizer.nextToken());
		shipValues.put("damaged_rate_symbol", tokenizer.nextToken());

		return shipValues;
	}

	private int getNationDBId(Nationalities nation) {
		Cursor cursor = null;
		try {
		SQLiteDatabase database = dbHelper.getReadableDatabase();
		cursor = database.query("nationalities", new String[] { "_id" }, "name = ?", new String[] { nation.name() }, null, null, null);
		cursor.moveToFirst();
		return cursor.getInt(0);
		} finally {
			if(cursor != null) {
				cursor.close();
			}
		}
	}

	public int getShipId(Nationalities nationality, String shipName) {
		Cursor cursor = null;
		SQLiteDatabase database = null;
		try {
			database = dbHelper.getReadableDatabase();
			long nationalityId = getNationDBId(nationality);
			 cursor = database.query("ships", new String[] { "_id" }, "name = ? AND nationality = ?", new String[] { shipName, nationalityId + "" }, null, null, null);
			if(cursor.moveToFirst()) {
				return cursor.getInt(0); 
			} else {
				Log.w("FlyingColors", "Could not find: '" + shipName + "'(" + nationality.name() + ")");
				return -1;
			}
		} finally {
			if(cursor != null) {
				cursor.close();
				database.close();
			}
		}
	}
}
