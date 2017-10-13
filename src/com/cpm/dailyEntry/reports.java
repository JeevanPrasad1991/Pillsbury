package com.cpm.dailyEntry;

import com.cpm.pillsbury.MainMenuActivity;
import com.cpm.pillsbury.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class reports extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reports);
		
		Button storewisereport = (Button)findViewById(R.id.storeWise);
		Button consolidatereport = (Button)findViewById(R.id.consolidate);
		
		
		storewisereport.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(reports.this, PerformanceStore.class);
				startActivity(intent);
				reports.this.finish();
			}
		});
		
		consolidatereport.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(reports.this, ConsolidatePerformance.class);
				startActivity(intent);
				reports.this.finish();
			}
		});
		
	}
	
@Override
public void onBackPressed() {
	// TODO Auto-generated method stub
	super.onBackPressed();
	
	
	Intent intent = new Intent(this, MainMenuActivity.class);
	startActivity(intent);
	reports.this.finish();
}
}
