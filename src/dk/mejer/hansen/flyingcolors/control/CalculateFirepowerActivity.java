package dk.mejer.hansen.flyingcolors.control;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CheckBox;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import dk.mejer.hansen.flyingcolors.R;
import dk.mejer.hansen.flyingcolors.activities.model.RateModifiers;
import dk.mejer.hansen.flyingcolors.activities.model.Scenario;
import dk.mejer.hansen.flyingcolors.activities.model.Ship;
import dk.mejer.hansen.flyingcolors.view.LargeShipCounterView;

public class CalculateFirepowerActivity extends Activity {

	protected FirePowerCalculationHandler baseFirePowerHandler;
	private Scenario scenario;
	private LinearLayout galleryContainer;
	protected View currentlyHighlightedView;
	private TextView rangeSeekbarValue;
	private SeekBar rangeSeekbar;
	private TextView damageSeekbarValue;
	private SeekBar damageSeekbar;
	protected Ship currentSelectedShip;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calculate_firepower);
		findViewById(R.id.base_firepower_scroll).setKeepScreenOn(true);
		galleryContainer = (LinearLayout) findViewById(R.id.calculate_base_firepower_ship_gallery_container);
	
		scenario = (Scenario) getIntent().getExtras().getSerializable("scenario");
		
		setTitle(getResources().getString(R.string.scenario) + scenario.getName());
		addFleetGallery();

		startBaseFirepowerCalucatorLoop();


		rangeSeekbarValue = (TextView) findViewById(R.id.caluclate_base_firepower_range_seekbar_value);
		rangeSeekbar = (SeekBar) findViewById(R.id.calculate_base_firepower_range_seekbar);


		rangeSeekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			public void onStopTrackingTouch(SeekBar seekBar) {}

			public void onStartTrackingTouch(SeekBar seekBar) {}

			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				int range = progress + 1; // seekbar value range is [0;9], range is [1;10]
				rangeSeekbarValue.setText(range + "");
				sendRangeChangedMessage(range);

			}
		});
		
		damageSeekbarValue = (TextView) findViewById(R.id.caluclate_base_firepower_damage_taken_seekbar_value);
		damageSeekbar = (SeekBar) findViewById(R.id.calculate_base_firepower_damage_taken_seekbar);
		
		
		damageSeekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			public void onStopTrackingTouch(SeekBar seekBar) {}

			public void onStartTrackingTouch(SeekBar seekBar) {}

			public void onProgressChanged(SeekBar seekBar, int damageTaken, boolean fromUser) {
				damageSeekbarValue.setText(damageTaken + "");
				sendDamageChangedMessage(damageTaken);
			}
		});
		
	}


	protected void sendDamageChangedMessage(int damageTaken) {
		Message msg = baseFirePowerHandler.obtainMessage(R.id.damaged_taken_changed, damageTaken, 0);
		baseFirePowerHandler.sendMessage(msg);
	}


	protected void sendRangeChangedMessage(int range) {
		Message msg = baseFirePowerHandler.obtainMessage(R.id.range_changed, range, 0);
		baseFirePowerHandler.sendMessage(msg);
	}


	private void addFleetGallery() {
		final Gallery fleet = new Gallery(this);
		fleet.setSpacing((int) convertDpToPixel(5));
		ScenarioDataSource scenarioDataSource = new ScenarioDataSource(this);
		final FleetGalleryAdapter adapter = new FleetGalleryAdapter(this, scenarioDataSource.getScenarioFleet(this, scenario.getName())); 
		
		fleet.setAdapter(adapter);
		galleryContainer.addView(fleet);
		fleet.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parentView, View view, int position, long id) {
				if(fleet.getSelectedItemPosition() == position) {
					LargeShipCounterView shipView = (LargeShipCounterView)view; 
					shipView.flip();
					updateDamageSeekbar(shipView.getShip());
					sendShipUpdatedMessage();
				}
			}
		});
		
		fleet.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parentView, View view, int position, long id) {
				currentSelectedShip = ((LargeShipCounterView)view).getShip();
				sendShipUpdatedMessage();
				updateDamageSeekbar(((LargeShipCounterView)view).getShip());
			}

			public void onNothingSelected(AdapterView<?> parentView) {
			}
		});

	}




	private void updateDamageSeekbar(Ship ship) {
		SeekBar damageTaken = (SeekBar) findViewById(R.id.calculate_base_firepower_damage_taken_seekbar);
		damageTaken.setMax(ship.getCurrentDamageCapacity());
		damageTaken.setProgress(0);
	}

	private void startBaseFirepowerCalucatorLoop() {
		Thread caluclatorThread = new Thread() {
			public void run() {
				Looper.prepare();
				baseFirePowerHandler = new FirePowerCalculationHandler(CalculateFirepowerActivity.this);
				Looper.loop();
			}
		};
		caluclatorThread.start();
	}

	void setBaseFirepower(final int firepower) {
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
	
	protected void sendShipUpdatedMessage() {
		Message msg = baseFirePowerHandler.obtainMessage(R.id.ship_changed, currentSelectedShip);
		baseFirePowerHandler.sendMessage(msg);
	}
	
	
	private float convertDpToPixel(float dp) {
		Resources resources = getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		float px = dp * (metrics.densityDpi / 160f);
		return px;
	}

	public Scenario getScenario() {
		return scenario;
	}

	public Ship getShip() {
		return currentSelectedShip;
	}

}