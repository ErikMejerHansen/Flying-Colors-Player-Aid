package dk.mejer.hansen.flyingcolors.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.RelativeLayout;
import android.widget.TextView;
import dk.mejer.hansen.flyingcolors.R;

public class DividerTextView extends RelativeLayout {

	public DividerTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		inflate(context, R.layout.section_divider, this);
		
		LayoutParams layout = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		layout.setMargins(0, (int)convertDpToPixel(5), 0, (int)convertDpToPixel(5));
		setDividerText(context, attrs);
	}

	private void setDividerText(Context context, AttributeSet attrs) {
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SectionDivider);

		int resourceId = a.getResourceId(R.styleable.SectionDivider_divider_text, R.string.unset_divider_text);
		TextView dividerText = (TextView) findViewById(R.id.section_divider_text);
		dividerText.setText(resourceId);

		a.recycle();
	}
	

	public float convertDpToPixel(float dp) {
		Resources resources = getContext().getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		float px = dp * (metrics.densityDpi / 160f);
		return px;
	}
}
