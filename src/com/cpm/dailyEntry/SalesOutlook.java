package com.cpm.dailyEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.HashSet;
import java.util.Set;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
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

public class SalesOutlook extends Activity implements OnClickListener {

	private ArrayList<salesBean> salesData = new ArrayList<salesBean>();
	private pillsbury_Database database;
	private SharedPreferences preferences;
	private String store_id, username, intime, brand_id, visit_date;
	boolean check = false;
	ListView list2;
	String idealForRepetition="";
	boolean chckidealfor=false;

	String storetype_id, region_id;
	private Button savebtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listsalesoutlook);

		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		visit_date = preferences.getString(CommonString.KEY_DATE, null);
		store_id = preferences.getString(CommonString.KEY_STORE_ID, null);
		savebtn = (Button) findViewById(R.id.button1);

		list2 = (ListView) findViewById(R.id.listView1);
		database = new pillsbury_Database(this);
		database.open();
		intime = getCurrentTime();

		salesData = database.getSalesDataInserted((int) checkMid());

		if (salesData.size() > 0) {

			check = true;

		} else {

			salesData = database.getSalesData();

		}

		savebtn.setOnClickListener(this);
		list2.setAdapter(new MyAdapter());
		// setListAdapter(new MyAdapter());
		list2.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {

			}

			@Override
			public void onScrollStateChanged(AbsListView arg0, int arg1) {
				// TODO Auto-generated method stub
				list2.invalidateViews();
			}

		});

	}

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return salesData.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();

				LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater
						.inflate(R.layout.saleoutlooklist_inflater, null);

				holder.Idealfor = (TextView) convertView
						.findViewById(R.id.txt_idealFor);
				holder.lastmonthsale = (EditText) convertView
						.findViewById(R.id.lastmonthSale);
				holder.tilldateSale = (EditText) convertView
						.findViewById(R.id.tillDateSale);
				holder.salesOutlook = (EditText) convertView
						.findViewById(R.id.salesOutlook);

				convertView.setTag(holder);

			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.lastmonthsale
					.setOnFocusChangeListener(new OnFocusChangeListener() {
						public void onFocusChange(View v, boolean hasFocus) {

							if (!hasFocus) {
								final int position = v.getId();
								final EditText Caption = (EditText) v;
								String value1 = Caption.getText().toString();

								if (value1.equals("")) {

									salesData.get(position).setLastmonth_sale("");

								} else {

									salesData.get(position).setLastmonth_sale(value1);

								}

							}
						}
					});
			
			holder.tilldateSale
			.setOnFocusChangeListener(new OnFocusChangeListener() {
				public void onFocusChange(View v, boolean hasFocus) {

					if (!hasFocus) {
						final int position = v.getId();
						final EditText Caption = (EditText) v;
						String value1 = Caption.getText().toString();

						if (value1.equals("")) {

							salesData.get(position).setTilldate_sale("");

						} else {

							salesData.get(position).setTilldate_sale(value1);

						}

					}
				}
			});
			holder.salesOutlook
			.setOnFocusChangeListener(new OnFocusChangeListener() {
				public void onFocusChange(View v, boolean hasFocus) {

					if (!hasFocus) {
						final int position = v.getId();
						final EditText Caption = (EditText) v;
						String value1 = Caption.getText().toString();

						if (value1.equals("")) {

							salesData.get(position).setSales_Outlook("");

						}
						else 
						{

							salesData.get(position).setSales_Outlook(value1);

						}

					}
				}
			});

			
				
				holder.Idealfor.setText(salesData.get(position).getBrand());
				holder.lastmonthsale.setText(salesData.get(position).getLastmonth_sale());
				holder.salesOutlook.setText(salesData.get(position).getSales_Outlook());
				holder.tilldateSale.setText(salesData.get(position).getTilldate_sale());
			
			
			
		
			
			
			
			//holder.Models.setText(salesData.get(position).getProduct());
			//holder.Type.setText(salesData.get(position).getType());
			//holder.unitsold.setText(salesData.get(position).getUnitSold());

			holder.Idealfor.setId(position);
			holder.lastmonthsale.setId(position);
			holder.tilldateSale.setId(position);
			holder.salesOutlook.setId(position);

			return convertView;
		}

		private class ViewHolder {

			TextView Idealfor;
			EditText lastmonthsale, tilldateSale,salesOutlook;
			ImageView imgtick;


		}

	}

	public long checkMid() {

		long mid = 0;

		mid = database.CheckMid(visit_date, store_id);

		return mid;
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.button1) {
			list2.clearFocus();

			if (validate()) {
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setMessage("Are you sure you want to save")
						.setCancelable(false)
						.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {

										database.open();

									//	if (check) {
											/*
											 * database.UpdateSkuData( getMid(),
											 * store_id, salesData);
											 */

										//} else {
											database.InsertSalesDetailsInserted(
													getMid(),

													store_id, salesData);
										
										//}

										Intent DailyEntryMenu = new Intent(
												SalesOutlook.this,
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
						"Please enter value for all Items", Toast.LENGTH_LONG)
						.show();
			}

		}
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
			cdata.setStatus("");
			cdata.setImage("");
			cdata.setOutlook_Remark("0");
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

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

		Intent intent = new Intent(this, DailyEntryMenu.class);
		startActivity(intent);
		SalesOutlook.this.finish();

	}

	boolean validate() {
		boolean flag = true;
		for (int i = 0; i < salesData.size(); i++) {
			if (salesData.get(i).getSales_Outlook().equalsIgnoreCase(""))

			{
					//		changed flag to true, make it false for proper functioning.
				flag = false;
				break;
			}

		}
		return flag;

	}

}
