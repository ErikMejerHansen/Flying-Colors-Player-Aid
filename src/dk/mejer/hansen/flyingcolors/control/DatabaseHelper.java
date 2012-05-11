package dk.mejer.hansen.flyingcolors.control;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "ships.db";
	private static final int DATABASE_VERSION = 1;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		
		database.execSQL("create table ships " +
				"(_id integer primary key autoincrement, " +
				"name text not null," +
				"rate text not null," +
				"historical_rate text not null, " +
				"rate_color text not null," +
				"rate_symbol text not null," +
				"damage_capacity integer not null," +
				"marines integer not null," +
				"rate_damaged text not null," +
				"damage_capacity_damaged integer not null," +
				"marines_damaged integer not null," +
				"VP integer not null," +
				"nationality integer not null," +
				"damaged_rate_color text not null," +
				"damaged_rate_symbol text not null" +
				");");
		
		
		database.execSQL("create table scenarios " +
				"(_id integer primary key, " +
				"name text not null," +
				"year integer not null," +
				"side1 text not null," +
				"side2 text not null," +
				"british_audacity integer," +
				"french_audacity integer," +
				"dutch_audacity integer," +
				"danish_audacity integer," +
				"spanish_audacity integer," +
				"scenario_hint text);");
		
		database.execSQL("create table nationalities " +
				"(_id integer primary key, " +
				"name text not null" +
				");");
		
		
		database.execSQL("create table scenario_ships " +
				"(_id integer primary key, " +
				"ship_fk integer," +
				"scenario_fk," +
				"foreign key(ship_fk) references ships(_id)," +
				"foreign key(scenario_fk) references scenarios(_id)" +
				");");
		
		database.execSQL("create table scenario_nationalities " +
				"(_id integer primary key, " +
				"nationalities_fk integer," +
				"scenario_fk," +
				"foreign key(nationalities_fk) references nationalities(_id)," +
				"foreign key(scenario_fk) references scenarios(_id)" +
				");");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		throw new RuntimeException("Gah! not implemented");
	}

}
