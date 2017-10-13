package com.cpm.dailyEntry;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cpm.Constants.CommonString;
import com.cpm.database.pillsbury_Database;
import com.cpm.delegates.CoverageBean;
import com.cpm.pillsbury.R;

public class Remarks extends Activity implements OnClickListener {
	EditText text;
	private Button savebtn,remarks;
	pillsbury_Database db;
	private SharedPreferences preferences;
	private String store_id, username, store_name, intime, brand_id,
			visit_date;

	public pillsbury_Database database;
	private ArrayList<CoverageBean> coverageBeanlist = new ArrayList<CoverageBean>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.remarks);

		text = (EditText) findViewById(R.id.remark);
		db = new pillsbury_Database(getApplicationContext());
		db.open();
		savebtn = (Button) findViewById(R.id.save);
//		remarks = (Button) findViewById(R.id.remarks);
		savebtn.setOnClickListener(this);

		intime = getCurrentTime();

		preferences = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		visit_date = preferences.getString(CommonString.KEY_DATE, null);
		store_name = preferences.getString(CommonString.KEY_STORE_NAME, "");
		username = preferences.getString(CommonString.KEY_USERNAME, "");
		store_id = preferences.getString(CommonString.KEY_STORE_ID, "");
		

		database = new pillsbury_Database(this);
		database.open();
		
		coverageBeanlist = database.getCoverageData(visit_date,
				store_id);
		
		if(coverageBeanlist.size()>0){
			
		if(coverageBeanlist.get(0).getOutlook_Remark().length()>1)
		{
			text.setText(coverageBeanlist.get(0).getOutlook_Remark().toString());
		}
		
		}
	}

	public long checkMid() {

		long mid = 0;
		mid = db.CheckMid(visit_date, store_id);
		return mid;
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
			cdata.setStatus("");
			cdata.setImage("");
			cdata.setOutlook_Remark(text.getText().toString());
			mid = db.InsertCoverageData(cdata);

		}
		
		else
		{
			
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
			cdata.setOutlook_Remark(text.getText().toString());
			mid = db.UpdateCoverageData(cdata, visit_date, store_id);
			
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
	public void onClick(View v) {
		if (v.getId() == R.id.save) {

			if (validatedata()) {

				AlertDialog.Builder builder = new AlertDialog.Builder(
						Remarks.this);
				builder.setMessage("Do you want to save the data ")
						.setCancelable(false)
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										
										getMid();
										

										Intent intent = new Intent(
												getApplicationContext(),
												StorelistActivity.class);
										startActivity(intent);
										Remarks.this.finish();
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

			} else {
				Toast.makeText(getApplicationContext(),
						"Please Enter Reason Remark", Toast.LENGTH_SHORT)
						.show();
			}
		}

	}

	public boolean validatedata() {
		boolean result = false;
		if (!text.getText().toString().equalsIgnoreCase("")) {
			result = true;
		}
		return result;

	}

	@Override
	public void onBackPressed() {
		Intent i = new Intent(this, StorelistActivity.class);
		startActivity(i);
		Remarks.this.finish();
	}
}
