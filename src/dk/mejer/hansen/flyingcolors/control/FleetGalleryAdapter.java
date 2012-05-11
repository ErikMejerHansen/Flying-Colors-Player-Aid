package dk.mejer.hansen.flyingcolors.control;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import dk.mejer.hansen.flyingcolors.activities.model.Ship;
import dk.mejer.hansen.flyingcolors.view.LargeShipCounterView;

public class FleetGalleryAdapter extends CursorAdapter {

	public FleetGalleryAdapter(Context context, Cursor c) {
		super(context, c, false);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return new LargeShipCounterView(context, new Ship(cursor));
	}

}
