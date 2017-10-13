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
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cpm.Constants.CommonString;
import com.cpm.database.pillsbury_Database;
import com.cpm.delegates.CoverageBean;
import com.cpm.delegates.salesBean;
import com.cpm.delegates.updateTamBean;
import com.cpm.pillsbury.R;

public class updateTam extends Activity implements OnClickListener {

	private ArrayList<salesBean> salesData = new ArrayList<salesBean>();
	private ArrayList<updateTamBean> updateTamData = new ArrayList<updateTamBean>();
	private pillsbury_Database database;
	private SharedPreferences preferences;
	private String store_id, username, intime, visit_date;
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
		setContentView(R.layout.updatetam_list);

		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		visit_date = preferences.getString(CommonString.KEY_DATE, null);
		store_id = preferences.getString(CommonString.KEY_STORE_ID, null);
		savebtn = (Button) findViewById(R.id.button1);

		list2 = (ListView) findViewById(R.id.listView1);
		database = new pillsbury_Database(this);
		database.open();
		intime = getCurrentTime();

		updateTamData = database.getTamDataInserted(store_id);
		
		if (updateTamData.size() > 0) {

			check = true;

		} else {

			updateTamData = database.getTamData(store_id);
			
			
			

		}
		
		

	/*	if (salesData.size() > 0) {

			check = true;

		} else {

			salesData = database.getSalesData();

		}*/

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
				list2.invalidateViews();
			}

		});

	}

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return updateTamData.size();
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
				convertView = inflater
						.inflate(R.layout.updatetam_listrow, null);

				holder.Brand = (TextView) convertView
						.findViewById(R.id.txt_Brand);
				holder.currentTam = (TextView) convertView
						.findViewById(R.id.txt_currentTam);
				holder.updatedTam = (EditText) convertView
						.findViewById(R.id.et_updatedTam);
				

				convertView.setTag(holder);

			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.updatedTam
			.setOnFocusChangeListener(new OnFocusChangeListener() {
				public void onFocusChange(View v, boolean hasFocus) {

					if (!hasFocus) {
						final int position = v.getId();
						final EditText Caption = (EditText) v;
						String value1 = Caption.getText().toString();

						if (value1.equals("")) {

							updateTamData.get(position).setUpdateTam("");

						}
						else 
						{

							updateTamData.get(position).setUpdateTam(value1);

						}

					}
				}
			});

			
				
				holder.Brand.setText(updateTamData.get(position).getBrandName());
				holder.currentTam.setText(updateTamData.get(position).getCurrentTam());
				holder.updatedTam.setText(updateTamData.get(position).getUpdateTam());
							
			//holder.Models.setText(salesData.get(position).getProduct());
			//holder.Type.setText(salesData.get(position).getType());
			//holder.unitsold.setText(salesData.get(position).getUnitSold());

			holder.Brand.setId(position);
			holder.currentTam.setId(position);
			holder.updatedTam.setId(position);
			

			return convertView;
		}

		private class ViewHolder {

			TextView Brand,currentTam;
			EditText updatedTam;

		}

	}

	public long checkMid() {

		long mid = 0;

		mid = database.CheckMid(visit_date, store_id);

		return mid;
	}

	public void onClick(View v) {
		
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
											database.InsertupdatedTamData(
													

													store_id, updateTamData);
										
										//}

										Intent DailyEntryMenu = new Intent(
												updateTam.this,
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
		super.onBackPressed();
		Intent intent = new Intent(this, StorelistActivity.class);
		startActivity(intent);
		updateTam.this.finish();

	}

	boolean validate() {
		boolean flag = true;
		for (int i = 0; i < salesData.size(); i++) {
			if (salesData.get(i).getSales_Outlook().equalsIgnoreCase(""))

			{
					//changed flag to true, make it false for proper functioning.
				flag = false;
				break;
			}

		}
		return flag;

	}

}
