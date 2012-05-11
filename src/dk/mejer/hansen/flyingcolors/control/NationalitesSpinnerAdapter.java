package dk.mejer.hansen.flyingcolors.control;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import dk.mejer.hansen.flyingcolors.activities.model.Nationalities;

public class NationalitesSpinnerAdapter extends BaseAdapter {

	private final Context context;

	NationalitesSpinnerAdapter(Context context) {
		this.context = context;
	}

	public int getCount() {
		return Nationalities.values().length;
	}

	public Nationalities getItem(int position) {
		return Nationalities.values()[position];
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
