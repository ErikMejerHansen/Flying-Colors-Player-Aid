package dk.mejer.hansen.control;

import android.app.Activity;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import dk.mejer.hansen.R;
import dk.mejer.hansen.activities.model.Nationalities;
import dk.mejer.hansen.activities.model.RateModifiers;
import dk.mejer.hansen.activities.model.Rates;

public class FlyingColorsPlayerAidActivity extends Activity {

	private Spinner rateSpinner;
	private Spinner nationalitySpinner;
	protected BaseFirePowerCalculationHandler baseFirePowerHandler;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		createBaseFirepowerCalucatorLoop();

		populateNationalitiesSpinner();
		populateRates();

		setSpinerListeners();
		setRangeEditTextListeners();

	}

	private void setRangeEditTextListeners() {
		EditText rangeEditText = (EditText) findViewById(R.id.range_edit_text);

		rangeEditText.setOnFocusChangeListener(new OnFocusChangeListener() {
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					// clear text on when user touches editText. Saves having to
					// delete existing value.
					((TextView) v).setText("");
				} else {
					EditText rangeEditText = (EditText) v;
					if (rangeEditText.getText().length() > 0) {
						Message msg = baseFirePowerHandler.obtainMessage(R.id.range_changed, Integer.parseInt(rangeEditText.getText().toString()), 0);
						baseFirePowerHandler.sendMessage(msg);
					}
				}
			}
		});

		rangeEditText.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				// clear text on when user touches editText. Saves having to
				// delete existing value. Also takes care of cases where clicks
				// the view without focus having having changed between touches.
				((TextView) v).setText("");
				return false;
			}
		});

		rangeEditText.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			public void afterTextChanged(Editable s) {
				if (s.length() > 0) {
					EditText rangeEditText = (EditText) findViewById(R.id.range_edit_text);
					Message msg = baseFirePowerHandler.obtainMessage(R.id.range_changed, Integer.parseInt(rangeEditText.getText().toString()), 0);
					baseFirePowerHandler.sendMessage(msg);
				}
			}
		});
	}

	private void createBaseFirepowerCalucatorLoop() {
		Thread caluclatorThread = new Thread() {
			public void run() {
				Looper.prepare();
				baseFirePowerHandler = new BaseFirePowerCalculationHandler(FlyingColorsPlayerAidActivity.this);
				Looper.loop();
			}
		};
		caluclatorThread.start();
	}

	private void setSpinerListeners() {
		nationalitySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parentView, View selectedView, int position, long id) {
				Message message = baseFirePowerHandler.obtainMessage(R.id.nationality_changed, Nationalities.values()[position]);
				baseFirePowerHandler.sendMessage(message);
			}

			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		rateSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parentView, View selectedView, int position, long id) {
				Message message = baseFirePowerHandler.obtainMessage(R.id.rate_changed, Rates.values()[position]);
				baseFirePowerHandler.sendMessage(message);
			}

			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}

	private void populateRates() {
		rateSpinner = (Spinner) findViewById(R.id.rate_spinner);
		rateSpinner.setAdapter(new RatesSpinnerAdapter(this));
	}

	private void populateNationalitiesSpinner() {
		nationalitySpinner = (Spinner) findViewById(R.id.nationality_spinner);
		nationalitySpinner.setAdapter(new NationalitesSpinnerAdapter(this));
	}

	public void setBaseFirepower(final int firepower) {
		runOnUiThread(new Runnable() {
			public void run() {
				TextView baseFirepower = (TextView) findViewById(R.id.caluclated_base_firepower);
				if (firepower == -1) {
					baseFirepower.setText("-");
				} else {
					baseFirepower.setText(firepower + "");
				}
			}
		});
	}

	public void togglePartialBroadside(View v) {
		sendRateModifierChangedMessage(RateModifiers.Partial_broadside, ((CheckBox) v).isChecked());
	}

	public void toggleRotatingAtAnchor(View v) {
		sendRateModifierChangedMessage(RateModifiers.Rotating_at_anchor, ((CheckBox) v).isChecked());
	}

	public void toggleTacking(View v) {
		sendRateModifierChangedMessage(RateModifiers.Tacking, ((CheckBox) v).isChecked());
	}

	public void toggleAtFullSails(View v) {
		sendRateModifierChangedMessage(RateModifiers.Full_sails, ((CheckBox) v).isChecked());
	}

	public void toggleIsDismasted(View v) {
		sendRateModifierChangedMessage(RateModifiers.Dismasted, ((CheckBox) v).isChecked());
	}

	public void toggleIsOnFire(View v) {
		sendRateModifierChangedMessage(RateModifiers.On_fire, ((CheckBox) v).isChecked());
	}

	public void sendRateModifierChangedMessage(RateModifiers modifer, boolean inEffect) {
		int inEffectInt;
		if (inEffect) {
			inEffectInt = 1;
		} else {
			inEffectInt = 0;
		}
		Message msg = baseFirePowerHandler.obtainMessage(R.id.rate_modifier_changed, modifer.ordinal(), inEffectInt);
		baseFirePowerHandler.sendMessage(msg);
	}

}