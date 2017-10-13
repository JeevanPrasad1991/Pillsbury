package com.cpm.dailyEntry;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;

import com.cpm.pillsbury.R;

public class Training extends FragmentActivity {
	private FragmentTabHost mTabHost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.training);

		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

		mTabHost.addTab(
				mTabHost.newTabSpec("Sales").setIndicator("Add Sales Person"),
				AddSalesPerson.class, null);
		mTabHost.addTab(mTabHost.newTabSpec("Training")
				.setIndicator("Training"), TrainingFragmnt.class, null);

		// mTabHost.addTab(
		// mTabHost.newTabSpec("Sales").setIndicator("Add Sales Person",
		// getResources().getDrawable(0)), AddSalesPerson.class,
		// null);

	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent i = new Intent(getBaseContext(),DailyEntryMenu.class);
		startActivity(i);
		this.finish();
	}
}
