package dk.mejer.hansen.flyingcolors.control;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
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
	
	
	public void onTwitter(View v) {
		lunchBrowserIntent("http://twitter.com/#!/erikmejerhansen");
	}
	

	
	public void onGooglePlus(View v) {
		lunchBrowserIntent("https://plus.google.com/u/0/106990074643821352530/about");
	}
	
	public void onMail(View v) {
		Intent i = new Intent(android.content.Intent.ACTION_SEND);
		i.setType("plain/text");
		i.putExtra(android.content.Intent.EXTRA_SUBJECT, "Feedback for Flying Colors Player Aid");
		i.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"erik@mejer-hansen.dk"});
		startActivity(Intent.createChooser(i, getResources().getString(R.string.send_mail)));
	}
	
	public void onSource(View v) {
		lunchBrowserIntent("https://github.com/ErikMejerHansen/Flying-Colors-Player-Aid");
	}
	
	public void onGMTLogo(View v) {
		lunchBrowserIntent("http://www.gmtgames.com/");
	}
	
	private void lunchBrowserIntent(String url) {
		Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		startActivity(i);
	}

 }
