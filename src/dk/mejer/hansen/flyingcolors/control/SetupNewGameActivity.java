package dk.mejer.hansen.flyingcolors.control;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import dk.mejer.hansen.flyingcolors.R;
import dk.mejer.hansen.flyingcolors.activities.model.Scenario;

public class SetupNewGameActivity extends Activity {
	private Spinner scenarios;
	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setup_scenario);
		setScenarios();
	}

	private void setScenarios() {
		scenarios = (Spinner) findViewById(R.id.setup_scenario_scenario_selector);
		CursorAdapter scenarioAdpater = new SimpleCursorAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, ScenarioDataSource.getScenarioNames(this), new String[]{"name"}, new int[]{android.R.id.text1} );
		scenarios.setAdapter(scenarioAdpater);
	}

	public void startGame(View v) {
		showProgressDialog();
		Log.d("FLYINGCOLORS", ((TextView)scenarios.getSelectedView()).getText().toString());
		new ScenarioDataFetcher().execute(((TextView)scenarios.getSelectedView()).getText().toString());
	}
	
	private void showProgressDialog() {
		dialog = ProgressDialog.show(this, "Loading scenario", "Loading scenario ships information");
	}
	
	private void dismissPogressDialog() {
		dialog.dismiss();
	}
	
	
	class ScenarioDataFetcher extends AsyncTask<String, Void, Scenario> {

		@Override
		protected Scenario doInBackground(String... scenarioName) {
			ScenarioDataSource scenarioDataSource = new ScenarioDataSource(getApplicationContext());
			return scenarioDataSource.getScenarioFromName(scenarioName[0], getApplicationContext());
		}
		@Override
		protected void onPostExecute(Scenario scenario) {
			super.onPostExecute(scenario);
			startScenario(scenario);
		}
		
	}

	public void startScenario(Scenario scenario) {
		Intent caluclateBaseFirepowerIntent = new Intent(getApplicationContext(), CalculateBaseFirepowerActivity.class);
		caluclateBaseFirepowerIntent.putExtra("scenario", scenario);
		dismissPogressDialog();
		
		
		startActivity(caluclateBaseFirepowerIntent);
		
	}
}
