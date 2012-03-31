package dk.mejer.hansen.control;

import dk.mejer.hansen.R;
import dk.mejer.hansen.activities.model.BaseFirePowerCalculator;
import dk.mejer.hansen.activities.model.Nationalities;
import dk.mejer.hansen.activities.model.RateModifiers;
import dk.mejer.hansen.activities.model.Rates;
import android.os.Handler;
import android.os.Message;

class BaseFirePowerCalculationHandler extends Handler {

	private BaseFirePowerCalculator caluclator;
	private final FlyingColorsPlayerAidActivity activity;

	BaseFirePowerCalculationHandler(FlyingColorsPlayerAidActivity activity) {
		this.activity = activity;
		this.caluclator = new BaseFirePowerCalculator();
		this.caluclator = new BaseFirePowerCalculator();
	}
	
	@Override
	public void handleMessage(Message msg) {
		Runnable calculateBaseFirepower = new Runnable() {
			public void run() {
				int result = caluclator.calculateBaseFirepower();
				Message msg = obtainMessage(R.id.base_firepower_calculated, result, -1);
				sendMessage(msg);
			}
		};
		
		switch (msg.what) {
			case R.id.nationality_changed:
				caluclator.setNationality((Nationalities) msg.obj);
				post(calculateBaseFirepower);
				break;
	
			case R.id.rate_changed:
				caluclator.setRate((Rates) msg.obj);
				post(calculateBaseFirepower);
				break;
	
			case R.id.range_changed:
				caluclator.setRange(msg.arg1);
				post(calculateBaseFirepower);
				break;
	
			case R.id.base_firepower_calculated:
				activity.setBaseFirepower(msg.arg1);
				break;
	
			case R.id.rate_modifier_changed:
				setRateModifer(RateModifiers.values()[msg.arg1], msg.arg2);
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
			caluclator.setIsDismasted(isApplied);
			break;
		case Full_sails:
			caluclator.setIsAtFullSails(isApplied);
			break;
		case On_fire:
			caluclator.setIsOnFire(isApplied);
			break;
		case Partial_broadside:
			caluclator.setIsPartialBroadside(isApplied);
			break;
		case Rotating_at_anchor:
			caluclator.setIsRotatingAtAnchor(isApplied);
			break;
		case Tacking:
			caluclator.setIsTacking(isApplied);
			break;
		default:
			break;
		}
	}
	
}
