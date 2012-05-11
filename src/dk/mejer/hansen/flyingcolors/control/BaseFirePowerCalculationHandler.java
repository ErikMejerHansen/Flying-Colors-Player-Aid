package dk.mejer.hansen.flyingcolors.control;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import dk.mejer.hansen.flyingcolors.R;
import dk.mejer.hansen.flyingcolors.activities.model.BaseFirePowerCalculator;
import dk.mejer.hansen.flyingcolors.activities.model.RateModifiers;

class BaseFirePowerCalculationHandler extends Handler {

	private BaseFirePowerCalculator calcuclator;
	private final CalculateBaseFirepowerActivity calculateBaseFirepowerActivity;

	BaseFirePowerCalculationHandler(CalculateBaseFirepowerActivity calculateBaseFirepowerActivity) {
		this.calculateBaseFirepowerActivity = calculateBaseFirepowerActivity;
		this.calcuclator = new BaseFirePowerCalculator();
		this.calcuclator = new BaseFirePowerCalculator();
	}
	
	@Override
	public void handleMessage(Message msg) {
		Runnable calculateBaseFirepower = new Runnable() {
			public void run() {
				calcuclator.setScenario(calculateBaseFirepowerActivity.getScenario());
				calcuclator.setShip(calculateBaseFirepowerActivity.getShip());
				
				int result = calcuclator.calculateBaseFirepower();
				
				Message msg = obtainMessage(R.id.base_firepower_calculated, result, -1);
				sendMessage(msg);
			}
		};
		
		switch (msg.what) {
			case R.id.damaged_taken_changed:
				calcuclator.setDamageTaken(msg.arg1);
				post(calculateBaseFirepower);
			break;
			
			case R.id.range_changed:
				Log.d("FLYINGCOLORS", "Range changed to:" + msg.arg1);
				calcuclator.setRange(msg.arg1);
				post(calculateBaseFirepower);
				break;
			
			case R.id.base_firepower_calculated:
				calculateBaseFirepowerActivity.setBaseFirepower(msg.arg1);
				break;
				
			case R.id.rate_modifier_changed:
				setRateModifer(RateModifiers.values()[msg.arg1], msg.arg2);
				post(calculateBaseFirepower);
				break;
			case R.id.ship_changed:
				post(calculateBaseFirepower);
				break;
			default:
				break;
		}
	}

	private void setRateModifer(RateModifiers rateModifier, int applied) {
		boolean isApplied;
		if(applied == 1) {
			isApplied = true;
		} else {
			isApplied = false;
		}
		
		switch (rateModifier) {
			case Dismasted:
				calcuclator.setIsDismasted(isApplied);
				break;
			case Full_sails:
				calcuclator.setIsAtFullSails(isApplied);
				break;
			case On_fire:
				calcuclator.setIsOnFire(isApplied);
				break;
			case Partial_broadside:
				calcuclator.setIsPartialBroadside(isApplied);
				break;
			case Rotating_at_anchor:
				calcuclator.setIsRotatingAtAnchor(isApplied);
				break;
			case Tacking:
				calcuclator.setIsTacking(isApplied);
				break;
			default:
				break;
			}
	}
	
}
