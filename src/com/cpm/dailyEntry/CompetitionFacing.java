package com.cpm.dailyEntry;

import java.util.ArrayList;
import java.util.Calendar;

import com.cpm.Constants.CommonString;

import com.cpm.database.pillsbury_Database;
import com.cpm.delegates.CoverageBean;
import com.cpm.delegates.competitionbeans;
import com.cpm.pillsbury.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CompetitionFacing extends Activity {
	
	ListView lv;
	Button save_btn;
	SharedPreferences preferences;
	String visit_date, loginDate, username, store_id, intime;
	pillsbury_Database db;
	ArrayList<competitionbeans> categorylist ;Boolean update = false;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.competetion_facing);
		
		lv = (ListView)findViewById(R.id.list);
		save_btn = (Button)findViewById(R.id.save);
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		visit_date = preferences.getString(CommonString.KEY_VISIT_DATE, null);
		loginDate = preferences.getString(CommonString.KEY_DATE, null);
	
//		preferences.getString(CommonString.KEY_STORE_NAME, "");
		username = preferences.getString(CommonString.KEY_USERNAME, "");
		store_id = preferences.getString(CommonString.KEY_STORE_ID, "");
		intime = preferences.getString(CommonString.KEY_STORE_IN_TIME, "");
		System.out.println("The store_id is:" + store_id);
		categorylist = new ArrayList<competitionbeans>();
	
		db = new pillsbury_Database(CompetitionFacing.this);
		db.open();
		
		
		categorylist = db.getCategoryData();
		
		if (categorylist.size()>0) {
			lv.setAdapter(new MyAdaptor(this));
		}
		
		categorylist = db.getInsertedCompetitionData(store_id);
		
		if (categorylist.size() == 0) {
			
			categorylist = db.getCategoryData();
			for (int i2 = 0; i2 < categorylist.size(); i2++) {

				

			}
		}

		else {

			update = true;
			save_btn.setText("Update");

			for (int i2 = 0; i2 < categorylist.size(); i2++) {

				if (categorylist.get(i2).getQuantity().equalsIgnoreCase("")) {
					
					categorylist.get(i2).setQuantity("");
				
					
				} else {
					categorylist.get(i2).setQuantity(categorylist.get(i2).getQuantity());
					
				}

			}

		}
		
		save_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if (check_condition()) {
					
					Toast.makeText(CompetitionFacing.this, "Please fill the data", Toast.LENGTH_LONG).show();
					
				} else {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							CompetitionFacing.this);
					builder.setMessage("Do you want to save the data ")
							.setCancelable(false)
							.setPositiveButton("OK",
									new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface dialog,
												int id) {

											if (update) {

												db.deleteCompetitionData(store_id);
												
												db.InsertCompetitionData(getMid(),
														store_id, categorylist);

												Intent i = new Intent(
														getApplicationContext(),
														DailyEntryMenu.class);
												startActivity(i);
												CompetitionFacing.this.finish();

											} else {
												

												db.InsertCompetitionData(getMid(),
														store_id, categorylist);

												Intent i = new Intent(
														getApplicationContext(),
														DailyEntryMenu.class);
												startActivity(i);
												CompetitionFacing.this.finish();
											}

										}
									})
							.setNegativeButton("Cancel",
									new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface dialog,
												int id) {
											dialog.cancel();
										}
									});
					AlertDialog alert = builder.create();
					alert.show();

				}
				
				
			}
		});
		
		
	}
	
	public long getMid() {

		long mid = 0;

		mid = db.CheckMid(visit_date, store_id);

		if (mid == 0) {

			CoverageBean cdata = new CoverageBean();
			cdata.setStoreId(store_id);
			cdata.setVisitDate(visit_date);
			cdata.setUserId(username);
			cdata.setInTime(intime);
			cdata.setOutTime(getCurrentTime());
			cdata.setReason("");
			cdata.setReasonid("0");
			cdata.setLatitude("0.0");
			cdata.setLongitude("0.0");
			cdata.setOutlook_Remark("0");
			cdata.setStatus("");
			cdata.setRights(db.getStoreRights(store_id));
			cdata.setImage("");
			mid = db.InsertCoverageData(cdata);
		}

		return mid;
	}
	
	@Override
	public void onBackPressed() {
		
		super.onBackPressed();
		AlertDialog.Builder builder = new AlertDialog.Builder( this);
        builder.setMessage("Are you sure you want to quit ?") .setCancelable(false).setPositiveButton("YES",
        new DialogInterface.OnClickListener() {
          public void onClick(  DialogInterface dialog, int id) {
        	
        	  Intent in = new Intent(CompetitionFacing.this, DailyEntryMenu.class);
      		startActivity(in);
      		CompetitionFacing.this.finish();

                }
                 }).setNegativeButton( "NO",
                 new DialogInterface.OnClickListener() {public void onClick( DialogInterface dialog, int id) {
                                          dialog.cancel();
                                              }
                                                });
                AlertDialog alert = builder.create();

                   alert.show();
		
	}
	
	public String getCurrentTime() {

		Calendar m_cal = Calendar.getInstance();

		String intime = m_cal.get(Calendar.HOUR_OF_DAY) + ":"
				+ m_cal.get(Calendar.MINUTE) + ":" + m_cal.get(Calendar.SECOND);
	
		return intime;
	}
	
	class ViewHolder {

		
		EditText quantity;TextView category;
	}
	
	public boolean check_condition() {
		boolean result = true;

		for (int i = 0; i < categorylist.size(); i++) {

			if (categorylist.get(i).getQuantity().equalsIgnoreCase("")) {

				result = true;

			} else {
				result = false;
				break;
			}
		}

		return result;

	}
	
	private class MyAdaptor extends BaseAdapter{
		
		private LayoutInflater mInflater;
		Context mcontext;
		
		
		public MyAdaptor(Context context) {
			mInflater = LayoutInflater.from(context);
			mcontext = context;
		}
		
		@Override
		public int getCount() {
			
			return categorylist.size();
		}

		@Override
		public Object getItem(int position) {
			
			return position;
		}

		@Override
		public long getItemId(int position) {
			
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			final ViewHolder holder;
			
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.competitor_listview, null);
				holder = new ViewHolder();

				holder.category = (TextView) convertView
						.findViewById(R.id.category);

				holder.quantity = (EditText) convertView
						.findViewById(R.id.quantity);

				convertView.setTag(holder);

			} else {

				holder = (ViewHolder) convertView.getTag();

			}
			
			
			holder.quantity
			.setOnFocusChangeListener(new OnFocusChangeListener() {
				public void onFocusChange(View v, boolean hasFocus) {

					if (!hasFocus) {
						final int position = v.getId();
						final EditText Caption = (EditText) v;
						String value1 = Caption.getText().toString();
					
						if (value1.equals("")) {

							categorylist.get(position).setQuantity("");

						} else {

							categorylist.get(position).setQuantity(value1);

						}

					}
				}
			});
			
			holder.category.setText(categorylist.get(position).getCategory());
			holder.quantity.setText(categorylist.get(position).getQuantity());
			holder.quantity.setId(position);
			
			return convertView;
		}
		
	}
}
