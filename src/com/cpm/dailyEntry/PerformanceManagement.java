package com.cpm.dailyEntry;

import java.util.ArrayList;

import com.cpm.database.pillsbury_Database;
import com.cpm.delegates.PerformanceDataBean;
import com.cpm.pillsbury.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class PerformanceManagement extends Activity{
	
	pillsbury_Database database;
	
	TextView monthly, daily, achieved, pending;
	ArrayList<PerformanceDataBean> data = new ArrayList<PerformanceDataBean>();
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.performance_management);
		monthly = (TextView)findViewById(R.id.monthly_target);
		daily = (TextView)findViewById(R.id.daily_target);
		achieved = (TextView)findViewById(R.id.achieved);
		pending = (TextView)findViewById(R.id.pendingAchivement);
		
		
		database = new pillsbury_Database(getApplicationContext());
		database.open();
		
		data = 	database.getPerformanceData();
		
		if (data.size()>0) {
			
			monthly.setText(data.get(0).getMonthly_target());
			daily.setText(data.get(0).getDaily_target());
			achieved.setText(data.get(0).getAchieved());
			pending.setText(data.get(0).getPending());

		}
	
	}

}
