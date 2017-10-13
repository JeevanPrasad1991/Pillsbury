package com.cpm.dailyEntry;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.cpm.Constants.CommonString;
import com.cpm.database.pillsbury_Database;
import com.cpm.delegates.Consolidatebean;
import com.cpm.delegates.PerformanceBean;
import com.cpm.pillsbury.R;

public class StoreVisitedActivity extends Activity implements LocationListener {

	pillsbury_Database database;
	private int _mid;
	String store_id, visit_date, store_intime = "",tamData, cateotryData;
	SharedPreferences preferences;
	private ArrayList<PerformanceBean> PerformanceData = new ArrayList<PerformanceBean>();
	private LocationManager locmanager = null;
	public static String currLatitude = "0.0";
	public static String currLongitude = "0.0";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.store_visited);

		RadioButton yes = (RadioButton) findViewById(R.id.yes);
		RadioButton no = (RadioButton) findViewById(R.id.no);
		Button updatTam =(Button)findViewById(R.id.updateTam);
		TextView name = (TextView) findViewById(R.id.store_name);
		TextView tam = (TextView) findViewById(R.id.tam);
		TextView cat = (TextView) findViewById(R.id.store_cat);
		TextView tamdata = (TextView) findViewById(R.id.tamdata);
		TextView saless = (TextView) findViewById(R.id.saless);
		TextView sharee = (TextView) findViewById(R.id.sharee);
		TextView territory = (TextView) findViewById(R.id.terriority);
		
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		
		database = new pillsbury_Database(this);
		database.open();
		store_id = preferences.getString(CommonString.KEY_STORE_ID, "");
	//	PerformanceData = database.getConsolidatePerformance();
		
		PerformanceData = database.getPerformanceStore(store_id);
		if(PerformanceData.size()>0)
		
		{
			
			tamdata.setText(PerformanceData.get(0).getTAM());
			saless.setText(PerformanceData.get(0).getSALE());
			sharee.setText(PerformanceData.get(0).getSHARE());
			territory.setText(PerformanceData.get(0).getTERRITORY());
			
			   if(PerformanceData.get(0).getTERRITORY().equalsIgnoreCase("red"))
			    {
				   territory.setBackgroundColor(Color.RED);
			    	
			    }
			    if(PerformanceData.get(0).getTERRITORY().equalsIgnoreCase("green"))
			    {
			    	territory.setBackgroundColor(Color.GREEN);
			    	
			    }
			    if(PerformanceData.get(0).getTERRITORY().equalsIgnoreCase("blue"))
			    {
			    	territory.setBackgroundColor(Color.BLUE);
			    	
			    }
			
		}
		
		
		String store_name = preferences.getString(CommonString.KEY_STORE_NAME, "");
		visit_date = preferences.getString(CommonString.KEY_VISIT_DATE, "");
		store_intime = preferences
				.getString(CommonString.KEY_STORE_IN_TIME, "");
		tamData = preferences.getString(CommonString.KEY_STORE_TAM, "");
		cateotryData = preferences.getString(CommonString.KEY_STORE_CATEGORY, "");
		updatTam.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
				Intent updateTam = new Intent(StoreVisitedActivity.this, updateTam.class);
				startActivity(updateTam);
				StoreVisitedActivity.this.finish();
				
				
				
			}
		});
		
		name.setText(store_name);
		cat.setText(cateotryData);
		tam.setText(tamData);

		// for location
		locmanager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		if (getMid() != 0) {
			yes.setChecked(true);
		}

	}

	public String getCurrentTime() {

		Calendar m_cal = Calendar.getInstance();
		int hour = m_cal.get(Calendar.HOUR_OF_DAY);
		int min = m_cal.get(Calendar.MINUTE);

		String intime = "";

		if (hour == 0) {
			intime = "" + 12 + ":" + min + " AM";
		} else if (hour == 12) {
			intime = "" + 12 + ":" + min + " PM";
		} else {

			if (hour > 12) {
				hour = hour - 12;
				intime = "" + hour + ":" + min + " PM";
			} else {
				intime = "" + hour + ":" + min + " AM";
			}
		}
		return intime;
	}

	public void onButtonClick(View v) {
		switch (v.getId()) {
		case R.id.yes:

			if (store_intime.equalsIgnoreCase("")) {

				SharedPreferences.Editor editor = preferences.edit();
				editor.putString(CommonString.KEY_STORE_IN_TIME,
						getCurrentTime());
				editor.putString(CommonString.KEY_STOREVISITED, store_id);
				editor.putString(CommonString.KEY_STOREVISITED_STATUS, "yes");
				editor.putString(CommonString.KEY_LATITUDE, currLatitude);
				editor.putString(CommonString.KEY_LONGITUDE, currLongitude);
				editor.commit();

			}
			
			Intent intent;
			
			long mid = 0;

			mid = database.CheckMid(visit_date, store_id);

			if (mid == 0) {

				intent = new Intent(getBaseContext(),
						StoreimageActivity.class);
			}
			else{
				intent = new Intent(getBaseContext(),
						DailyEntryMenu.class);
			}

			startActivity(intent);
			StoreVisitedActivity.this.finish();

			break;
			
			
			
		case R.id.updateTam:
			
			

		case R.id.no:

			if (getMid() != 0) {

				AlertDialog.Builder builder = new AlertDialog.Builder(
						StoreVisitedActivity.this);
				builder.setMessage("Your all data will be deleted.")
						.setCancelable(false)
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {

										SharedPreferences.Editor editor = preferences
												.edit();
										editor.putString(
												CommonString.KEY_STORE_IN_TIME,
												"");
										editor.putString(
												CommonString.KEY_STOREVISITED,
												"");
										editor.putString(
												CommonString.KEY_STOREVISITED_STATUS,
												"");
										editor.putString(
												CommonString.KEY_LATITUDE, "");
										editor.putString(
												CommonString.KEY_LONGITUDE, "");

										editor.commit();
										UpdateData();

									}
								})
						.setNegativeButton("Back",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
										Intent i = new Intent(
												StoreVisitedActivity.this,
												StorelistActivity.class);
										startActivity(i);
										StoreVisitedActivity.this.finish();
									}
								});
				AlertDialog alert = builder.create();

				alert.show();

			} else {

				SharedPreferences.Editor editor = preferences.edit();
				editor.putString(CommonString.KEY_STORE_IN_TIME, "");
				editor.putString(CommonString.KEY_STOREVISITED, "");
				editor.putString(CommonString.KEY_STOREVISITED_STATUS, "");
				editor.putString(CommonString.KEY_LATITUDE, "");
				editor.putString(CommonString.KEY_LONGITUDE, "");

				editor.commit();

				database.updateStoreStatusOnCheckout(store_id, visit_date,
						CommonString.KEY_N);
				Intent in = new Intent(this, NonWorkingReason.class);
				startActivity(in);
				StoreVisitedActivity.this.finish();

			}

			break;

		}
	}

	public long getMid() {

		int mid = 0;

		mid = database.CheckMid(visit_date, store_id);
		_mid = mid;
		return mid;
	}

	public void UpdateData() {

		database.open();
		database.deleteStoreMidData(_mid, store_id);

		database.updateStoreStatusOnLeave(store_id, visit_date,
				CommonString.KEY_N);

		database.updateStoreStatusOnCheckout(store_id, visit_date,
				CommonString.KEY_N);

		Intent intent = new Intent(StoreVisitedActivity.this,
				NonWorkingReason.class);

		startActivity(intent);

		StoreVisitedActivity.this.finish();

	}

	@Override
	public void onBackPressed() {
		
		Intent i = new Intent(this, StorelistActivity.class);
		startActivity(i);
		StoreVisitedActivity.this.finish();
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		currLatitude = Double.toString(location.getLatitude());
		currLongitude = Double.toString(location.getLongitude());
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		locmanager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
				this);

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		locmanager.removeUpdates(this);
	}

}
