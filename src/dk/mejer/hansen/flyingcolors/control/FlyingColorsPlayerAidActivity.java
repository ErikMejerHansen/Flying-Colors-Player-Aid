package dk.mejer.hansen.flyingcolors.control;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import dk.mejer.hansen.flyingcolors.R;

public class FlyingColorsPlayerAidActivity extends Activity {
	private Dialog splashDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		showSplashScreen();
		setContentView(R.layout.main_menu);
		new LoadDatabaseAsyncTask().execute(null);
	}

	public void onCreateNewGame(View v) {
		startActivity(new Intent(getApplicationContext(), SetupNewGameActivity.class));
	}
	
	public void onOptions(View v) {
		startActivity(new Intent(getApplicationContext(), OptionsActivity.class));
	}
	
	protected void showSplashScreen() {
	    splashDialog = new Dialog(this, R.style.SplashScreen);
	    splashDialog.setContentView(R.layout.splash_screen);
	    splashDialog.setCancelable(false);
	    splashDialog.show();
	}
	
	protected void removeSplashScreen() {
		splashDialog.dismiss();
	}
	
	
	private class LoadDatabaseAsyncTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			new ShipDataSource(getApplicationContext()).loadShipsIntoDatabase();
			new ScenarioDataSource(getApplicationContext()).loadScenariosIntoDatabase();
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			removeSplashScreen();
		}
		
	}
}

