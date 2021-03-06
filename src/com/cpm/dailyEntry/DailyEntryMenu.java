package com.cpm.dailyEntry;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.cpm.Constants.CommonString;
import com.cpm.database.pillsbury_Database;
import com.cpm.delegates.AssetTrackerBean;
import com.cpm.delegates.CallsBean;
import com.cpm.delegates.ClosingStockBean;
import com.cpm.delegates.CoverageBean;
import com.cpm.delegates.LunchBean;
import com.cpm.delegates.MidDayRecievedStockBean;
import com.cpm.delegates.OpeningStockBean;
import com.cpm.delegates.Posmbean;
import com.cpm.delegates.PromotionTrackingBean;
import com.cpm.delegates.SalesPersonBean;
import com.cpm.delegates.TrainingBean;
import com.cpm.delegates.competitionbeans;
import com.cpm.delegates.salesBean;
import com.cpm.pillsbury.R;

public class DailyEntryMenu extends Activity {

	Button openingStock, stockRecieved, closingStock, calls,promotions,assets, performanceManagement, lunch_time,
	competetion_facing, additional;
	private pillsbury_Database database;

	private Bundle extras = null;
	private String store_id, store_address, store_name, visit_date, username,
			store_latitude, store_longitude;
	private Intent intent;
	private TextView text;

	private SharedPreferences preferences = null;
	private SharedPreferences.Editor editor = null;

	private ArrayList<CoverageBean> coverageBeanlist = new ArrayList<CoverageBean>();

	
	
	private ArrayList<OpeningStockBean> openingData = new ArrayList<OpeningStockBean>();
	private ArrayList<ClosingStockBean> closingData = new ArrayList<ClosingStockBean>();
	private ArrayList<MidDayRecievedStockBean> StockData = new ArrayList<MidDayRecievedStockBean>();
	private ArrayList<CallsBean> callTrackerData = new ArrayList<CallsBean>();
	private ArrayList<PromotionTrackingBean> promotionData = new ArrayList<PromotionTrackingBean>();
	private ArrayList<AssetTrackerBean> AssetData = new ArrayList<AssetTrackerBean>();
	private ArrayList<LunchBean> lunchData = new ArrayList<LunchBean>();
	private ArrayList<competitionbeans> competitionfacing = new ArrayList<competitionbeans>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dailyactivitymenu);
		openingStock = (Button)findViewById(R.id.btn_openingStock);
		stockRecieved = (Button)findViewById(R.id.btn_stockRecieved);
		closingStock = (Button)findViewById(R.id.btn_closingStock);
//		calls = (Button)findViewById(R.id.btn_calls);
		promotions = (Button)findViewById(R.id.btn_promotions);
		assets = (Button)findViewById(R.id.btn_assets);
		competetion_facing = (Button)findViewById(R.id.competition_facing);
		additional = (Button)findViewById(R.id.additional);
		performanceManagement = (Button)findViewById(R.id.promotionManagement_btn);
		
//		Sales = (Button) findViewById(R.id.btn_sales);
//		competition = (Button) findViewById(R.id.btn_competition);
//		posm = (Button) findViewById(R.id.posm);
//		training = (Button) findViewById(R.id.btn_training);
//		remarks = (Button) findViewById(R.id.remarks);

		preferences = PreferenceManager.getDefaultSharedPreferences(this);

		store_name = preferences.getString(CommonString.KEY_STORE_NAME, "");
		store_id = preferences.getString(CommonString.KEY_STORE_ID, "");
		visit_date = preferences.getString(CommonString.KEY_VISIT_DATE, "");
		username = preferences.getString(CommonString.KEY_USERNAME, "");

		database = new pillsbury_Database(this);
		database.open();
		
		

		filldata();

		openingStock.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent sales = new Intent(DailyEntryMenu.this, OpeningStockList.class);
				startActivity(sales);

			}
		});
		
		stockRecieved.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent sales = new Intent(DailyEntryMenu.this, StockRecieved.class);
				startActivity(sales);

			}
		});
		closingStock.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent sales = new Intent(DailyEntryMenu.this,
						ClosingStock.class);
				startActivity(sales);

			}
		});

/*		calls.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent sales = new Intent(DailyEntryMenu.this, CallsTracker.class);
				startActivity(sales);

			}
		});*/
		promotions.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent training = new Intent(DailyEntryMenu.this,
						PromotionTracker.class);
				startActivity(training);

			}
		});
		
		
		assets.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent training = new Intent(DailyEntryMenu.this,
						AssetTracker.class);
				startActivity(training);

			}
		});
		
		performanceManagement.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent in = new Intent(DailyEntryMenu.this, PerformanceManagement.class);
				startActivity(in);
				
			}
		});
		
	
		
		competetion_facing.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent in = new Intent(DailyEntryMenu.this, CompetitionFacing.class);
				startActivity(in);
				
			}
		});
		
		
		additional.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent in = new Intent(DailyEntryMenu.this, AdditionalVisibility.class);
				startActivity(in);
				
			}
		});

	}

	@Override
	public void onBackPressed() {
		
		super.onBackPressed();
		Intent i = new Intent(DailyEntryMenu.this, StorelistActivity.class);
		startActivity(i);
		DailyEntryMenu.this.finish();

	}
	
	private void filldata(){

		ArrayList<CoverageBean> coverageBeanlist = new ArrayList<CoverageBean>();
		coverageBeanlist = database.getCoverageData(visit_date, store_id);

		for (int i = 0; i < coverageBeanlist.size(); i++) {

				openingData = database.getInsertedComplianceData(coverageBeanlist.get(i).getStoreId());
				
				StockData = database.getInsertedStockRecievedComplianceData(coverageBeanlist.get(i).getStoreId());
				
				closingData = database.getInsertedClosingStockComplianceData(coverageBeanlist.get(i).getStoreId());
				
				callTrackerData = database.getCallTrackerData(coverageBeanlist.get(i).getStoreId());
				
				promotionData= database.getInsertedPromotionComplianceData(coverageBeanlist.get(i).getStoreId());
				
				AssetData = database.getInsertedAssetComplianceData(coverageBeanlist.get(i).getStoreId());
				
				lunchData = database.getLunchData(coverageBeanlist.get(i).getStoreId());
				
				competitionfacing = database.getInsertedCompetitionData(coverageBeanlist.get(i).getStoreId());
				
				if(openingData.size()>0){
					((Button) findViewById(R.id.btn_openingStock))
					.setBackgroundResource(R.drawable.opening_stock_tick);
					
				}
				
				if (StockData.size() >0) {
					((Button) findViewById(R.id.btn_stockRecieved))
					.setBackgroundResource(R.drawable.stock_recieved_tick);
					
				}
				
				if(openingData.size()>0 && StockData.size()>0){
					closingStock.setEnabled(true);
					((Button) findViewById(R.id.btn_closingStock))
					.setBackgroundResource(R.drawable.closing_stock);
					
				}else{
					closingStock.setEnabled(false);
					((Button) findViewById(R.id.btn_closingStock))
					.setBackgroundResource(R.drawable.closing_stock_disabled);
				}
				
				if (closingData.size()>0) {
				
					((Button) findViewById(R.id.btn_closingStock))
					.setBackgroundResource(R.drawable.closing_stock_tick);
					openingStock.setEnabled(false);
					stockRecieved.setEnabled(false);
				}
				
				/*if (callTrackerData.size()>0) {
					((Button) findViewById(R.id.btn_calls))
					.setBackgroundResource(R.drawable.calls_tick);
				}*/
				
				if (promotionData.size()>0) {
					((Button) findViewById(R.id.btn_promotions))
					.setBackgroundResource(R.drawable.promotions_tick);
				}
				
				if (AssetData.size()>0) {
					((Button) findViewById(R.id.btn_assets))
					.setBackgroundResource(R.drawable.assets_tick);
				}
			
				
				if (competitionfacing.size()>0) {
					((Button) findViewById(R.id.competition_facing)).setBackgroundResource(R.drawable.competition_sos_tick);
					
					
					
				}

			
			}
		
		if(database.getStoreRights(store_id).equalsIgnoreCase("merchandiser"))
		{
			Button btnClosingstock = 	(Button) findViewById(R.id.btn_closingStock);
//			Button btnCalls = 	(Button) findViewById(R.id.btn_calls);
			Button btnstockRecrived = 	(Button) findViewById(R.id.btn_stockRecieved);
		
			Button performance = (Button)findViewById(R.id.promotionManagement_btn);
			
			btnClosingstock.setEnabled(false);
//			btnCalls.setEnabled(false);
			btnstockRecrived.setEnabled(false);
			
			performance.setEnabled(false);
			btnClosingstock.setBackgroundResource(R.drawable.closing_stock_disabled);
//			btnCalls.setBackgroundResource(R.drawable.calls_disabled);
			btnstockRecrived.setBackgroundResource(R.drawable.stock_recieved_disabled);
		
			performance.setBackgroundResource(R.drawable.performance_management_disabled);

			
		}
		
		
		
			
		}

	}


//	private void filldata() {
//		boolean sales = false, competion = false, posm = false, training = false;
//
//		coverageBeanlist = database.getCoverageData(visit_date, store_id);
//		SaleDataRaw = database.getSalesData();
//		PosmDataRaw = database.getPosmData();
//		CompetionDataRaw = database.getCompetionData();
//		TrainingDataRaw = database.getTraining();
//		contactdatarow = database.getSalesperson(store_id);
//
//		// salesPersonData TrainingData
//
//		for (int i = 0; i < coverageBeanlist.size(); i++) {
//			
//			
//			
//			
//			
//			
//			if((coverageBeanlist.get(0).getOutlook_Remark().length()>1)){
//				
//				((Button) findViewById(R.id.remarks))
//				.setBackgroundResource(R.drawable.remarktick);
//				
//			}
//			
//			
//			
//			
//
//			salesPersonData = database.getSalesPersonData(coverageBeanlist.get(
//					i).getMID());
//			TrainingData = database.getAddedTrainingData(coverageBeanlist
//					.get(i).getMID());
//
//			if ((salesPersonData.size() > 0) || (TrainingData.size() > 0)) {
//
//				((Button) findViewById(R.id.btn_training))
//						.setBackgroundResource(R.drawable.training_tick_ico);
//				training = true;
//
//			}
//
//			/*if (TrainingDataRaw.size() > 0 && contactdatarow.size() > 0) {
//
//			}
//			
//			else {
//				training = true;
//			}*/
//
//			SaleData = database.getSalesDataInserted(coverageBeanlist.get(i)
//					.getMID());
//
//			if (SaleDataRaw.size() > 0) {
//
//				if (SaleData.size() > 0) {
//					((Button) findViewById(R.id.btn_sales))
//							.setBackgroundResource(R.drawable.saless_tick_ico);
//					sales = true;
//
//				}
//			} else {
//				sales = true;
//
//			}
//
//			CompetionData = database.getCompetionDataInserted(coverageBeanlist
//					.get(i).getMID());
//
//			if (CompetionDataRaw.size() > 0) {
//				if (CompetionData.size() > 0 ) {
//
//					((Button) findViewById(R.id.btn_competition))
//							.setBackgroundResource(R.drawable.competition_tick_ico);
//					competion = true;
//				}
//			} else {
//				competion = true;
//			}
//
//			PosmData = database.getPosmDataInserted(coverageBeanlist.get(i)
//					.getMID());
//
//			if (PosmDataRaw.size() > 0) {
//
//				if (PosmData.size() > 0) {
//
//					((Button) findViewById(R.id.posm))
//							.setBackgroundResource(R.drawable.posm_tick_ico);
//
//					posm = true;
//				}
//			} else {
//				posm = true;
//			}
//
//			if (sales && competion && posm ) {
//				database.updateStoreStatusOnCheckout(coverageBeanlist.get(i)
//						.getStoreId(), visit_date, CommonString.KEY_VALID);
//			}
//
//		}
//	}
//	


