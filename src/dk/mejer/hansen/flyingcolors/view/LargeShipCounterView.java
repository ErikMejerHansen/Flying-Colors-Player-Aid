package dk.mejer.hansen.flyingcolors.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import dk.mejer.hansen.flyingcolors.activities.model.RateColors;
import dk.mejer.hansen.flyingcolors.activities.model.RateSymbols;
import dk.mejer.hansen.flyingcolors.activities.model.Ship;

public class LargeShipCounterView extends ImageView {
	private Bitmap back;
	private Ship ship = null;

	public LargeShipCounterView(Context context, Ship ship) {
		super(context);
		this.ship = ship;
		this.setContentDescription(ship.getName());
	}

	public Ship getShip() {
		return ship;
	}
	
	public void flip() {
		ship.flip();
		invalidate();
	}

	private void loadBackgroundImage() {
		Options options = new BitmapFactory.Options();
		options.inScaled = true;
		if (ship.isDamaged()) {
			back = BitmapFactory.decodeResource(getResources(), ship.getNationality().dammagedCounterDrawableId());
		} else {
			back = BitmapFactory.decodeResource(getResources(), ship.getNationality().counterDrawableId());
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		loadBackgroundImage();
		canvas.drawBitmap(back, new Matrix(), null);
		drawHistoricalInfo(canvas);
		drawRate(canvas);
		drawDamageCapacity(canvas);
		drawMarines(canvas);
		if (ship.isDamaged()) {
			drawVpValue(canvas);
		}
	}

	private void drawVpValue(Canvas canvas) {
		canvas.save();
		
		Paint fontPaint = getTextPaint(Color.YELLOW, 9);
		canvas.rotate(-90, 0, 0);
		canvas.drawText(ship.getVPValue() + " vp", convertDpToPixel(-40), convertDpToPixel(44), fontPaint); // historical
		
		canvas.restore();
	}

	private void drawHistoricalInfo(Canvas canvas) {
		canvas.save();

		Paint fontPaint = getTextPaint(Color.WHITE, 9);
		canvas.rotate(-90, 0, 0);
		canvas.drawText(ship.getName(), convertDpToPixel(-82), convertDpToPixel(12), fontPaint); // ship
		canvas.drawText("(" + ship.getGunnery_and_actual_rate() + ")", convertDpToPixel(-80), convertDpToPixel(44), fontPaint); // historical

		canvas.restore();
	}

	private void drawMarines(Canvas canvas) {
		Paint marinesPaint = getTextPaint(Color.WHITE, 10);
		canvas.drawText("M", convertDpToPixel(5), convertDpToPixel(95), marinesPaint); // marines

		marinesPaint = getTextPaint(Color.WHITE, 13);
		canvas.drawText(ship.getCurrentMarines() + "", convertDpToPixel(14), convertDpToPixel(95), marinesPaint); // marines
	}

	private void drawDamageCapacity(Canvas canvas) {
		Paint fontPaint = getTextPaint(Color.WHITE, 15);
		canvas.drawText(ship.getCurrentDamageCapacity() + "", convertDpToPixel(32), convertDpToPixel(18), fontPaint); // damage capacity
	}

	private void drawRate(Canvas canvas) {
		Paint fontPaint;
		if (ship.getCurrentRateColor() == RateColors.Black) {
			fontPaint = getTextPaint(Color.WHITE, 14);
		} else {
			fontPaint = getTextPaint(Color.BLACK, 14);
		}

		drawRateSymbol(canvas, ship.getCurrentRateColor(), ship.getCurrentRateSymbol());

		canvas.drawText(getContext().getResources().getString(ship.getCurrentRate().getStringResourceId()), convertDpToPixel(8), convertDpToPixel(18), fontPaint); // Rate
	}

	private void drawRateSymbol(Canvas canvas, RateColors rateColors, RateSymbols rateShape) {
		switch (rateShape) {
		case Hexagonal:
			drawHexRateShape(canvas, rateColors);
			break;
		case Round:
			drawRoundRateShape(canvas, rateColors);
			break;
		case Square:
			break;
		case None:
			break;
		}
	}

	private void drawHexRateShape(Canvas canvas, RateColors rateColors) {
		Path hexPath = new Path();
		Paint fillPaint = new Paint();
		fillPaint.setColor(rateColors.getColor());
		
		Paint strokePaint = new Paint();
		strokePaint.setColor(Color.BLACK);
		strokePaint.setStyle(Style.STROKE);
		strokePaint.setAntiAlias(true);
		
		float scale = 0.7f;
		hexPath.lineTo(convertDpToPixel(scale * 11.1f), convertDpToPixel(scale * 6.4f));
		hexPath.lineTo(convertDpToPixel(scale * 11.1f), convertDpToPixel(scale * 19.2f));
		hexPath.lineTo(convertDpToPixel(scale * 0), convertDpToPixel(scale * 25.8f));
		hexPath.lineTo(convertDpToPixel(scale * -11.1f), convertDpToPixel(scale * 19.2f));
		hexPath.lineTo(convertDpToPixel(scale * -11.1f), convertDpToPixel(scale * 6.4f));
		hexPath.lineTo(convertDpToPixel(scale * 0), convertDpToPixel(scale * 0));
		canvas.save();
		canvas.translate(convertDpToPixel(11.5f), convertDpToPixel(4));
		canvas.drawPath(hexPath, fillPaint);
		canvas.drawPath(hexPath, strokePaint);
		canvas.restore();
	}

	private void drawRoundRateShape(Canvas canvas, RateColors rateColor) {
		ShapeDrawable shape = new ShapeDrawable(new OvalShape());
		shape.getPaint().setColor(rateColor.getColor());
		int left = (int) convertDpToPixel(4);
		int top = (int) convertDpToPixel(4);
		int diameter = (int) convertDpToPixel(17);
		shape.setBounds(left, top, left + diameter, top + diameter);
		shape.draw(canvas);
		shape.getPaint().setStyle(Style.STROKE);
		shape.getPaint().setColor(Color.BLACK);
		shape.draw(canvas);
	}

	private Paint getTextPaint(int color, int size) {
		Paint blackFontPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		blackFontPaint.setColor(color);
		blackFontPaint.setTextSize(convertDpToPixel(size));
		blackFontPaint.setFakeBoldText(true);
		return blackFontPaint;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension((int)convertDpToPixel(55), (int)convertDpToPixel(100));
	}

	public float convertDpToPixel(float d) {
		Resources resources = getContext().getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		float px = d * (metrics.densityDpi / 160f);
		return px;
	}
}
