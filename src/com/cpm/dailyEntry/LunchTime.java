package com.cpm.dailyEntry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONObject;

import com.cpm.Constants.CommonString;
import com.cpm.database.pillsbury_Database;
import com.cpm.delegates.CoverageBean;
import com.cpm.delegates.GeotaggingBeans;
import com.cpm.delegates.LunchBean;
import com.cpm.delegates.StoreBean;
import com.cpm.pillsbury.R;
import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class LunchTime extends Activity implements OnClickListener{
	
	private	TextView timePicker1, timePicker2;
	private Button save,btnChangeTime,btnSetTime;
	String fromTime, toTime, username,visit_date,store_id, intime;
	private int hour, minute, fromHour, fromMinute, tohour, toMinute;
	pillsbury_Database database;
	SharedPreferences preferences;
	static final int TIME_DIALOG_ID1 = 999;
	
	JSONObject jo1 = new JSONObject();
	
	String jo = "";
	String datacheck = "";
	String[] words;
	String validity, storename;
	int mid;
	String sod = "";
	String total_sku = "";
	String sku = "";
	String sos_data = "";
	String category_data = "";
	String result;
	String errormsg = "";
	int count;
	int eventType;
	StoreBean storestatus = new StoreBean();
	static final int TIME_DIALOG_ID2 = 998;
	
	
	ArrayList<GeotaggingBeans> geotaglist = new ArrayList<GeotaggingBeans>();
	static int counter = 1;
	String version;
	boolean upload_status;
	
	ArrayList<LunchBean> lunchList = new ArrayList<LunchBean>();
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lunch_time);
		timePicker1 = (TextView)findViewById(R.id.timePicker1);
		timePicker2 = (TextView)findViewById(R.id.timePicker2);
		
		save = (Button)findViewById(R.id.save_btn);
		preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		username = preferences.getString(CommonString.KEY_USERNAME, "");
		visit_date = preferences.getString(CommonString.KEY_DATE, null);
		store_id = preferences.getString(CommonString.KEY_STORE_ID, "");
		database = new pillsbury_Database(getApplicationContext());
		database.open();
		intime = preferences.getString(CommonString.KEY_STORE_IN_TIME, "");
		setCurrentTimeOnView();
		addListenerOnButton();

		lunchList = database.getLunchData(store_id);
		
		if (lunchList.size()>0) {
			String fromTime = lunchList.get(0).getFromTime();
			String toTime = lunchList.get(0).getToTime();
			
			timePicker1.setText(fromTime);
			timePicker2.setText(toTime);
			
			save.setText("update");
		}
		
		
		save.setOnClickListener(this);
	}

	
	// display current time
	public void setCurrentTimeOnView() {
	//	tvDisplayTime = (TextView) findViewById(R.id.tvTime);
		timePicker1 = (TextView) findViewById(R.id.timePicker1);

		final Calendar c = Calendar.getInstance();
		hour = c.get(Calendar.HOUR_OF_DAY);
		minute = c.get(Calendar.MINUTE);

		// set current time into textview
	

		// set current time into timepicker
		timePicker1.setText(new StringBuilder().append(pad(hour)).append(":")
				.append(pad(minute)));
		

	}
	
	public void addListenerOnButton() {

		btnChangeTime = (Button) findViewById(R.id.btnChangeTime);
		btnSetTime = (Button) findViewById(R.id.btnSetTime);
		btnChangeTime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				showDialog(TIME_DIALOG_ID1);

			}

		});
		
		

		btnSetTime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				showDialog(TIME_DIALOG_ID2);

			}

		});
	}
	
	public String getCurrentTime() {

		Calendar m_cal = Calendar.getInstance();

		 intime = m_cal.get(Calendar.HOUR_OF_DAY) + ":"
				+ m_cal.get(Calendar.MINUTE) + ":" + m_cal.get(Calendar.SECOND);
		
		

		return intime;
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
			cdata.setRights(database.getStoreRights(store_id));
			cdata.setImage("");
			mid = database.InsertCoverageData(cdata);
		}

		return mid;
	}
	
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case TIME_DIALOG_ID1:
			// set time picker as current time
			return new TimePickerDialog(this, timePickerListener, hour, minute,
					false);
		case TIME_DIALOG_ID2:
			// set time picker as current time
			return new TimePickerDialog(this, timePickerListener1, hour, minute,
					false);

		}
		return null;
	}

	private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int selectedHour,
				int selectedMinute) {
			fromHour = selectedHour;
			fromMinute = selectedMinute;

			// set current time into textview
			timePicker2.setText(new StringBuilder().append(pad(fromHour)).append(":")
					.append(pad(fromMinute)));
			

		}
	};
	
	private TimePickerDialog.OnTimeSetListener timePickerListener1 = new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int selectedHour,
				int selectedMinute) {
			tohour = selectedHour;
			toMinute = selectedMinute;

			// set current time into text view
			timePicker1.setText(new StringBuilder().append(pad(tohour)).append(":")
					.append(pad(toMinute)));
			
		}
	};


	private static String pad(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}
	@Override
	public void onClick(View v) {
		if (v.getId()== R.id.save_btn) {
			
			Date datetime1 = null;
			Date datetime2 = null;
			StringBuilder ftime;
			StringBuilder fromTime =new StringBuilder().append(pad(tohour)).append(":").append(pad(toMinute));;
			if(fromTime.toString().equalsIgnoreCase("00:00")){
				ftime = new StringBuilder().append(pad(hour)).append(":").append(pad(minute));
			}else{
				ftime = new StringBuilder().append(pad(tohour)).append(":").append(pad(toMinute));
			}
			StringBuilder tTime = new StringBuilder().append(pad(fromHour)).append(":").append(pad(fromMinute));
			
			SimpleDateFormat  time1 = new SimpleDateFormat("HH:mm"); 
			SimpleDateFormat  time2 = new SimpleDateFormat("HH:mm"); 
			try {
				datetime1 =	time1.parse(ftime.toString());
				datetime2 =	time2.parse(tTime.toString());
				int endH = datetime2.getHours();
				int endM = datetime2.getMinutes();
				long totalendTime =(endH*60*60*1000)+(endM*60*1000);
				
				int startH = datetime1.getHours();
				int startM = datetime1.getMinutes();
				long totastartTime =(startH*60*60*1000)+(startM*60*1000);
				
				if(totalendTime>totastartTime){
					database.open();
					database.insertLunchtimeData(getMid(), username, ftime.toString(), tTime.toString(), store_id);
					Toast.makeText(getApplicationContext(), "Data has been Saved Successfully", Toast.LENGTH_LONG).show();
					
					Intent in = new Intent(LunchTime.this, DailyEntryMenu.class);
					startActivity(in);
					
				}else{
					Toast.makeText(getApplicationContext(), "Invalid Entry!! ", Toast.LENGTH_LONG).show();
				}
				
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
