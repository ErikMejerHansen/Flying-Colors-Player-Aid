package dk.mejer.hansen.flyingcolors.control;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Options {
	static String PREFERENCES_NAME = "dk.mejer.hansen.FlyingColors.prefs";
	static String PREFERENCE_NAME_KEEP_SCREEN_ALIVE = "dk.mejer.hansen.FlyingColors.keepScreenAlive"; 
	
	public static boolean shouldKeepScreenOn(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
		return prefs.getBoolean(PREFERENCE_NAME_KEEP_SCREEN_ALIVE, true);
	}
	
	public static void setShouldKeepScreenOn(boolean keepScreenOn, Context context) {
		SharedPreferences prefs = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putBoolean(PREFERENCE_NAME_KEEP_SCREEN_ALIVE, keepScreenOn).commit();
	}
}