package com.cpm.dailyEntry;

import java.util.ArrayList;
import java.util.Calendar;

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
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;

import com.cpm.Constants.CommonString;
import com.cpm.database.pillsbury_Database;
import com.cpm.delegates.CoverageBean;
import com.cpm.delegates.salesBean;
import com.cpm.pillsbury.R;

public class Competition extends Activity implements OnClickListener {

	private ArrayList<salesBean> CompetitionData = new ArrayList<salesBean>();
	private pillsbury_Database database;
	private SharedPreferences preferences;
	private String store_id, username, intime, visit_date;
	boolean check = false;
	ListView list;

	String storetype_id, region_id;
	private Button savebtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.competionlist);
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		visit_date = preferences.getString(CommonString.KEY_DATE, null);
		store_id = preferences.getString(CommonString.KEY_STORE_ID, null);
		savebtn = (Button) findViewById(R.id.button1);
		list = (ListView) findViewById(R.id.listView1);
		database = new pillsbury_Database(this);
		database.open();
		intime = preferences.getString(CommonString.KEY_STORE_IN_TIME, "");
		intime = getCurrentTime();
		CompetitionData = database.getCompetionDataInserted((int) checkMid());

		if (CompetitionData.size() > 0) {

			check = true;

		} else {

			CompetitionData = database.getCompetionData();

		}

		savebtn.setOnClickListener(this);

		list.setAdapter(new MyAdapter());

		list.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {

			}

			@Override
			public void onScrollStateChanged(AbsListView arg0, int arg1) {
				list.invalidateViews();
			}

		});
		// setListAdapter(new MyAdapter());

	}

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return CompetitionData.size();
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
		public View getView(final int position, View convertView,
				ViewGroup parent) {
		
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();

				LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.competitionlistinflat,
						null);

				holder.Idealfor = (TextView) convertView
						.findViewById(R.id.txt_idealFor);
				holder.Models = (TextView) convertView
						.findViewById(R.id.txt_type);
				holder.price = (EditText) convertView
						.findViewById(R.id.et_price);
				holder.lastmonthsale = (EditText) convertView
						.findViewById(R.id.et_lstmonthsale);
				holder.salesOutlook = (EditText)convertView
						.findViewById(R.id.et_salesOutlook);
				holder.tillDate = (EditText)convertView
						.findViewById(R.id.et_tillDatesell);
				

				convertView.setTag(holder);

			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.price.setOnFocusChangeListener(new OnFocusChangeListener() {
				public void onFocusChange(View v, boolean hasFocus) {

					if (!hasFocus) {
						final int position = v.getId();
						final EditText Caption = (EditText) v;
						String value1 = Caption.getText().toString();

						if (value1.equals("")) {

							CompetitionData.get(position).setPrice("");

						} else {

							CompetitionData.get(position).setPrice(value1);

						}

					}
				}
			});

			holder.lastmonthsale.setOnFocusChangeListener(new OnFocusChangeListener() {
				public void onFocusChange(View v, boolean hasFocus) {

					if (!hasFocus) {
						final int position = v.getId();
						final EditText Caption = (EditText) v;
						String value1 = Caption.getText().toString();

						if (value1.equals("")) {

							CompetitionData.get(position).setLastmonth_sale("");

						} else {

							CompetitionData.get(position).setLastmonth_sale(value1);

						}

					}
				}
			});
			
			holder.tillDate.setOnFocusChangeListener(new OnFocusChangeListener() {
				public void onFocusChange(View v, boolean hasFocus) {

					if (!hasFocus) {
						final int position = v.getId();
						final EditText Caption = (EditText) v;
						String value1 = Caption.getText().toString();

						if (value1.equals("")) {

							CompetitionData.get(position).setTilldate_sale("");

						} else {

							CompetitionData.get(position).setTilldate_sale(value1);

						}

					}
				}
			});
			holder.salesOutlook.setOnFocusChangeListener(new OnFocusChangeListener() {
				public void onFocusChange(View v, boolean hasFocus) {

					if (!hasFocus) {
						final int position = v.getId();
						final EditText Caption = (EditText) v;
						String value1 = Caption.getText().toString();

						if (value1.equals("")) {

							CompetitionData.get(position).setSales_Outlook("");

						} else {

							CompetitionData.get(position).setSales_Outlook(value1);

						}

					}
				}
			});

			holder.Idealfor.setText(CompetitionData.get(position).getType());
			// holder.Models.setText(list.get(position).getBrand());
			holder.Models.setText(CompetitionData.get(position).getProduct());
			holder.price.setText(CompetitionData.get(position).getPrice());
			holder.lastmonthsale.setText(CompetitionData.get(position).getLastmonth_sale());
			holder.tillDate.setText(CompetitionData.get(position).getTilldate_sale());
			holder.salesOutlook.setText(CompetitionData.get(position).getSales_Outlook());

			holder.Idealfor.setId(position);
			holder.Models.setId(position);
			holder.price.setId(position);
			holder.lastmonthsale.setId(position);
			holder.tillDate.setId(position);
			holder.salesOutlook.setId(position);

			return convertView;
		}

		private class ViewHolder {

			TextView Idealfor, Models;
			ImageView imgtick;
			EditText price, lastmonthsale,tillDate,salesOutlook;

		}

	}

	public long checkMid() {

		long mid = 0;

		mid = database.CheckMid(visit_date, store_id);

		return mid;
	}

	public long getMid() {

		long mid = 0;

		mid = database.CheckMid(visit_date, store_id);

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
			cdata.setImage("");
			mid = database.InsertCoverageData(cdata);

		}

		return mid;
	}

	public String getCurrentTime() {

		Calendar m_cal = Calendar.getInstance();

		String intime = m_cal.get(Calendar.HOUR_OF_DAY) + ":"
				+ m_cal.get(Calendar.MINUTE) + ":" + m_cal.get(Calendar.SECOND);

		return intime;

	}

	public void onClick(View v) {
		
		if (v.getId() == R.id.button1) {
			list.clearFocus();

			if (validate()) {

				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setMessage("Are you sure you want to save")
						.setCancelable(false)
						.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {

										database.open();
										//if (check) {
											/*
											 * database.UpdateSkuData( getMid(),
											 * store_id, salesData);
											 */

										//} else {
											database.InsertCompetionDetailsInserted(
													getMid(),

													store_id, CompetitionData);
											

										//}
										Intent DailyEntryMenu = new Intent(
												Competition.this,
												DailyEntryMenu.class);
										startActivity(DailyEntryMenu);
									}
								})
						.setNegativeButton("No",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});
				AlertDialog alert = builder.create();

				alert.show();
			}

			else {

				Toast.makeText(getBaseContext(),
						"Please enter value for price and sales",
						Toast.LENGTH_LONG).show();

			}
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();

		Intent intent = new Intent(this, DailyEntryMenu.class);
		startActivity(intent);
		Competition.this.finish();

	}

	boolean validate() {
		boolean flag = true;
		for (int i = 0; i < CompetitionData.size(); i++) {
			if (CompetitionData.get(i).getPrice().equalsIgnoreCase("")
					|| CompetitionData.get(i).getLastmonth_sale().equalsIgnoreCase(""))

			{

				flag = false;
				
				break;
			}

			
		}
		return flag;

	}

}
