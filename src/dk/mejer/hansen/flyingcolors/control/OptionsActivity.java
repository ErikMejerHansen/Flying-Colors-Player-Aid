package dk.mejer.hansen.flyingcolors.control;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import dk.mejer.hansen.flyingcolors.R;

public class OptionsActivity extends Activity {

	private CheckBox keepScreenOnCheckBox;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.options);
		keepScreenOnCheckBox = (CheckBox) findViewById(R.id.options_keep_screen_on_checkbox);
		keepScreenOnCheckBox.setChecked(Options.shouldKeepScreenOn(getApplicationContext()));
	}

	public void onKeepScreenOnToggled(View v) {
		Options.setShouldKeepScreenOn(keepScreenOnCheckBox.isChecked(), getApplicationContext());
	}
}
