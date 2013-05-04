package com.srikanthgr.calendarweekview;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

import com.srikanthgr.calendarweekview.fragments.DayFragment;
import com.srikanthgr.calendarweekview.listeners.TabsListener;

/**
 * @author Srikanth G.R
 *
 */
public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayUseLogoEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		ActionBar.Tab monthTab = actionBar.newTab().setText("DAY");

		DayFragment dayFragment = new DayFragment();

		monthTab.setTabListener(new TabsListener(dayFragment));
		actionBar.addTab(monthTab, 0, true);

	}
}
