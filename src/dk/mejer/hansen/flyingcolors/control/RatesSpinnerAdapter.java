package dk.mejer.hansen.flyingcolors.control;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import dk.mejer.hansen.flyingcolors.activities.model.Rates;

public class RatesSpinnerAdapter extends BaseAdapter {

	private final Context context;

	RatesSpinnerAdapter(Context context) {
		this.context = context;
	}

	public int getCount() {
		return Rates.values().length;
	}

	public Rates getItem(int position) {
		return Rates.values()[position];
	}

	public long getItemId(int position) {
		return getItem(position).ordinal();
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		TextView view = new TextView(context);
		view.setText(context.getText(getItem(position).getStringResourceId()));
		return view;
	}
}
