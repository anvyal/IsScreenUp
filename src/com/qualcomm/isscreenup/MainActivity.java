package com.qualcomm.isscreenup;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.KeyguardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Below is the code to make the app seek for su root permissions

		/*
		 * try { String progArray="su"; java.lang.Process p =
		 * Runtime.getRuntime().exec(progArray); }
		 * 
		 * catch (IOException e) { e.printStackTrace(); }
		 */
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		boolean isScreenOn = pm.isScreenOn();

		KeyguardManager km = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
		final KeyguardManager.KeyguardLock kl = km
				.newKeyguardLock("MyKeyguardLock");
		kl.disableKeyguard();

		WakeLock wakeLock;
		// long timeout = 100000L;
		int timeout = 1800000;

		/*
		 * View header = (View) getLayoutInflater().inflate( R.id.container,
		 * null);
		 */
		// FrameLayout layout = (FrameLayout) findViewById(R.id.container);
		// TextView headerValue = (TextView) findViewById(R.id.message);
		/*
		 * Typeface tf = Typeface.createFromAsset(this.getAssets(),
		 * "times.ttf"); // TextView tv = (TextView)
		 * findViewById(R.id.CustomFontText); if (tf != null) {
		 * headerValue.setTypeface(tf); }
		 */

/*		Bundle bundle = new Bundle();
		bundle.putString("message", "Alo Elena!");*/

		String message=""; 

		if (isScreenOn) {
			// R.string.hello_world =
			// "SCREEN TURNED ON, SETTING SCREEN_OFF_TIMEOUT to to 30 Minutes";
			Log.e("Anvesh",
					"SCREEN TURNED ON, SETTING SCREEN_OFF_TIMEOUT to 30 Minutes");

			/*
			 * if (headerValue != null) { headerValue.setText("Is it Working?");
			 * //.setText(
			 * "Screen was Turned ON\n\nSCREEN_OFF_TIMEOUT is set to 30 Minutes\n\n\nMinimize this Application and continue with your Automation Tests"
			 * ); }
			 */
			
			message="Screen was Turned ON\n\nSCREEN_OFF_TIMEOUT is set to 30 Minutes\n\n\nMinimize this Application and continue with your Automation Tests";
			
			android.provider.Settings.System.putInt(getContentResolver(),
					Settings.System.SCREEN_OFF_TIMEOUT, timeout);
		} else {
			Log.e("Anvesh",
					"SCREEN TURNED OFF, TURNING SCREEN ON and SETTING SCREEN_OFF_TIMEOUT to 30 Minutes");

			// headerValue
			// .setText("Screen was Turned OFF\n\nDon't Worry !! Turned ON screen for you !! \n\nSCREEN_OFF_TIMEOUT is set to 30 Minutes\n\n\nMinimize this Application and continue with your Automation Tests");

			message="Screen was Turned OFF\n\nDon't Worry !! Turned ON screen for you !! \n\nSCREEN_OFF_TIMEOUT is set to 30 Minutes\n\n\nMinimize this Application and continue with your Automation Tests";
			
			wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
					| PowerManager.ACQUIRE_CAUSES_WAKEUP
					| PowerManager.ON_AFTER_RELEASE, "MyWakeLock");
			wakeLock.acquire();
			android.provider.Settings.System.putInt(getContentResolver(),
					Settings.System.SCREEN_OFF_TIMEOUT, timeout);
			wakeLock.release();

			/*
			 * getWindow()
			 * .addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
			 */

			/*
			 * Settings.System.putString(cr, Settings.System.SCREEN_OFF_TIMEOUT,
			 * "-1");
			 */
			/*
			 * android.provider.Settings.System.putInt(getContentResolver(),
			 * Settings.System.SCREEN_OFF_TIMEOUT, "-1");
			 */

		}

		
		if (savedInstanceState == null) {
			Bundle bundle = new Bundle();
			bundle.putString("message", message);
			PlaceholderFragment fragInfo = new PlaceholderFragment();
			fragInfo.setArguments(bundle);
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.add(R.id.container, fragInfo);
			ft.commit();
		}
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);

			/*
			 * listView.addHeaderView(header); listView.setAdapter(adapter);
			 */

			TextView headerValue = (TextView) rootView
					.findViewById(R.id.message);

			String myValue = this.getArguments().getString("message");
			if (myValue != null)
				headerValue.setText(myValue);

			return rootView;
		}
	}

}