package com.cpm.dailyEntry;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import com.cpm.Constants.CommonString;

import com.cpm.database.pillsbury_Database;



import com.cpm.delegates.ClosingStockBean;
import com.cpm.delegates.CoverageBean;
import com.cpm.delegates.MidDayRecievedStockBean;
import com.cpm.delegates.OpeningStockBean;
import com.cpm.pillsbury.R;
import com.cpm.xmlGetterSetter.SKUPostgetterSetter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;

public class StockRecieved extends Activity implements OnClickListener{
	
	private String store_id, username, intime, visit_date;
	private SharedPreferences preferences;
	ExpandableListView lv;
	Button save_btn;
	private pillsbury_Database database;
	Helper help = new Helper();
	Boolean update = false;
	private HashMap<MidDayRecievedStockBean, List<MidDayRecievedStockBean>> _listDataChild;
	List<MidDayRecievedStockBean> listDataHeader;
	HashMap<MidDayRecievedStockBean, List<MidDayRecievedStockBean>> listDataChild;
	List<MidDayRecievedStockBean> save_listDataHeader;
	ArrayList<OpeningStockBean> list = new ArrayList<OpeningStockBean>();
	ExpandableListAdapter listAdapter;
	private static ArrayList<MidDayRecievedStockBean> compliance_list = new ArrayList<MidDayRecievedStockBean>();
	private static ArrayList<MidDayRecievedStockBean> sub_compliance_list = new ArrayList<MidDayRecievedStockBean>();
	
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stock_recieved);
		lv = (ExpandableListView)findViewById(R.id.lvExp);
		save_btn = (Button)findViewById(R.id.save_btn);
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		visit_date = preferences.getString(CommonString.KEY_DATE, null);
		preferences.getString(CommonString.KEY_STORE_NAME, "");
		username = preferences.getString(CommonString.KEY_USERNAME, "");
		store_id = preferences.getString(CommonString.KEY_STORE_ID, "");
		
		System.out.println("The store_id is:"+store_id);
		intime = preferences.getString(CommonString.KEY_STORE_IN_TIME, "");
		database = new pillsbury_Database(this);
		database.open();
		
		
		prepareListData();
		
		listAdapter = new ExpandableListAdapter(this, listDataHeader,
				listDataChild);

		// setting list adapter
		lv.setAdapter(listAdapter);
		
		
		lv.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {

			}

			@Override
			public void onScrollStateChanged(AbsListView arg0, int arg1) {
//				lv.invalidateViews();
				
				   if (SCROLL_STATE_TOUCH_SCROLL == arg1) {
		                View currentFocus = getCurrentFocus();
		                if (currentFocus != null) {
		                    currentFocus.clearFocus();
		                }
			}

				
				
			}

		});
		
	
		
		save_btn.setOnClickListener(this);
	
	
		
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
//		super.onBackPressed();
		AlertDialog.Builder builder = new AlertDialog.Builder( this);
        builder.setMessage("Are you sure you want to quit ?") .setCancelable(false).setPositiveButton("YES",
        new DialogInterface.OnClickListener() {
          public void onClick(  DialogInterface dialog, int id) {
        	
        	  Intent in = new Intent(StockRecieved.this, DailyEntryMenu.class);
      		startActivity(in);
      		StockRecieved.this.finish();

                }
                 }).setNegativeButton( "NO",
                 new DialogInterface.OnClickListener() {public void onClick( DialogInterface dialog, int id) {
                                          dialog.cancel();
                                              }
                                                });
                AlertDialog alert = builder.create();

                   alert.show();
	}
	
	
	private void prepareListData() {

		listDataHeader = new ArrayList<MidDayRecievedStockBean>();
		listDataChild = new HashMap<MidDayRecievedStockBean, List<MidDayRecievedStockBean>>();
		
		compliance_list = database.getInsertedStockRecievedComplianceData(store_id);
		
		if(compliance_list.size() == 0){
			
		
		
		compliance_list = database.getMidDayStockRecieved(store_id);

//			compliance_list = database.getServiceData(storetype_id);
			for (int i2 = 0; i2 < compliance_list.size(); i2++) {

				MidDayRecievedStockBean chk = new MidDayRecievedStockBean();
				chk.setBRAND(compliance_list.get(i2).getBRAND());
				chk.setBRAND_CD(compliance_list.get(i2).getBRAND_CD());

				listDataHeader.add(chk);

				sub_compliance_list = database.getSubskulistData(
						compliance_list.get(i2).getBRAND_CD(),store_id);

				for (int i = 0; i < sub_compliance_list.size(); i++) {

					sub_compliance_list.get(i).setStock("");
//					sub_compliance_list.get(i).setListedFlag("true");
				}
				listDataChild.put(chk, sub_compliance_list);
				listDataChild.put(chk, addData(sub_compliance_list));

			}
			
		}
		
		else{
			update = true;
			save_btn.setText("Update");

			for (int i2 = 0; i2 < compliance_list.size(); i2++) {

				MidDayRecievedStockBean chk = new MidDayRecievedStockBean();
				chk.setBRAND(compliance_list.get(i2).getBRAND());
				chk.setBRAND_CD(compliance_list.get(i2).getBRAND_CD());

				listDataHeader.add(chk);

				sub_compliance_list = database
						.getInsertedStockRecievedSubList(compliance_list.get(i2).getCommonId());


				listDataChild.put(chk, sub_compliance_list);
				listDataChild.put(chk, addData(sub_compliance_list));

		}
	}
	}
	
	public ArrayList<MidDayRecievedStockBean> addData(
			ArrayList<MidDayRecievedStockBean> subList) {

		for (int i = 0; i < subList.size(); i++) {

			list = database
					.getOpeningDataForCheck(subList.get(i).getSKU_CD(), store_id);
			
//			midStock = database.getMidDayStockForCheck(subList.get(i).getSKU_CD());
			
			if (list.size()>0) {
				String stock = list.get(0).getOpeningStock();
				String listed = list.get(0).getListedFlag();
				subList.get(i).setOpening_stock(stock);
				subList.get(i).setListedFlag(listed);
			}else{
				subList.get(i).setOpening_stock("");
				subList.get(i).setListedFlag("");
			}
			
			
			}
		return subList;

	}
	
	private class ExpandableListAdapter extends BaseExpandableListAdapter {

		private Context _context;

		public ExpandableListAdapter(Context context,
				List<MidDayRecievedStockBean> listDataHeader,
				HashMap<MidDayRecievedStockBean, List<MidDayRecievedStockBean>> listChildData) {
			this._context = context;
			save_listDataHeader = listDataHeader;
			_listDataChild = listChildData;
		}
		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return _listDataChild.get(save_listDataHeader.get(groupPosition))
					.get(childPosition);
		}



		@Override
		public View getChildView (final int groupPosition,
				final int childPosition, boolean isLastChild, View convertView,
				ViewGroup parent) {
			
			final MidDayRecievedStockBean childText = (MidDayRecievedStockBean) getChild(
					groupPosition, childPosition);

			if (convertView == null) {
				LayoutInflater infalInflater = (LayoutInflater) this._context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = infalInflater.inflate(
						R.layout.stockrecieved_listview, null);
			}
			

			 TextView sku_name = (TextView)convertView.findViewById(R.id.sku_name);
			 EditText sku_stock = (EditText)convertView.findViewById(R.id.sku_stock_recieved);
			 LinearLayout l = (LinearLayout)convertView.findViewById(R.id.secondq);
			 
			
			
		
//			Toast.makeText(getApplicationContext(), "the Size of the list is is:"+list.size(), 1).show();
			
			sku_stock.setOnFocusChangeListener(new OnFocusChangeListener() {
				public void onFocusChange(View v, boolean hasFocus) {

					if (!hasFocus) {

						final EditText Caption = (EditText) v;
						String value1 = Caption.getText().toString();

						if (value1.equals("")) {

							_listDataChild
									.get(save_listDataHeader.get(groupPosition))
									.get(childPosition).setStock("");

						} else {

							_listDataChild
									.get(save_listDataHeader.get(groupPosition))
									.get(childPosition).setStock(value1);

						}

					}
				}
			});
			
			String listed = childText.getListedFlag();
			
			if (listed.equalsIgnoreCase("false")) {
				l.setEnabled(false);
				l.setBackgroundColor(Color.GRAY);
				_listDataChild
				.get(save_listDataHeader.get(groupPosition))
				.get(childPosition).setStock("0");
				sku_stock.setText("0");
				sku_stock.setEnabled(false);

				
				
			}else{
				l.setEnabled(true);
				l.setBackgroundColor(Color.WHITE);
				sku_stock.setEnabled(true);
				sku_stock.setText("");
				
			}
			
//			brand_name.setText(childText.getBRAND());
			sku_stock.setText(childText.getStock());
			sku_name.setText(childText.getSKU());
			
			return convertView;
		}
		

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}


		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}
		@Override
		public int getGroupCount() {
			return save_listDataHeader.size();
		}
		@Override
		public int getChildrenCount(int groupPosition) {
			return _listDataChild.get(save_listDataHeader.get(groupPosition))
					.size();
		}
		@Override
		public Object getGroup(int groupPosition) {
			return save_listDataHeader.get(groupPosition);
		}
		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}
		@Override
		public boolean hasStableIds() {
			return false;
		}
		@Override
		public View getGroupView(final int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			final MidDayRecievedStockBean headerTitle = (MidDayRecievedStockBean) getGroup(groupPosition);
			if (convertView == null) {
				LayoutInflater infalInflater = (LayoutInflater) this._context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = infalInflater.inflate(R.layout.brand,
						null);
			}

			TextView brand_name = (TextView) convertView
					.findViewById(R.id.brand_name);

			brand_name.setText(headerTitle.getBRAND());

			return convertView;
			
		}
	}
	
	
	public String getCurrentTime() {

		Calendar m_cal = Calendar.getInstance();

		String intime = m_cal.get(Calendar.HOUR_OF_DAY) + ":"
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
	

	boolean validate(
			HashMap<MidDayRecievedStockBean, List<MidDayRecievedStockBean>> listDataChild2,
			List<MidDayRecievedStockBean> listDataHeader2) {
		boolean flag = true;
		for (int i = 0; i < listDataHeader2.size(); i++) {
			for (int j = 0; j < listDataChild2.get(listDataHeader2.get(i))
					.size(); j++) {
				String Stock_allow = listDataChild2.get(listDataHeader2.get(i)).get(j).getStock();
				String isListed = listDataChild2.get(listDataHeader2.get(i)).get(j).getListedFlag();
				
				if(isListed.equalsIgnoreCase("true"))
				if (Stock_allow.equalsIgnoreCase("")) {
					flag = false;
					break;
					
				} else{ 	
					flag = true;
				
			}
			}
			
			if (flag == false) {
				break;
				
			}
		}
		return flag;
		
	}

	@Override
	public void onClick(View v) {	
		if (v.getId() == R.id.save_btn) {
			lv.clearFocus();
			if (validate(listDataChild, listDataHeader)) {
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setMessage("Are you sure you want to save")
						.setCancelable(false)
						.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {
									public void onClick(
											DialogInterface dialog, int id) {
										

										database.open();
										database.deleteStockRecievedData(store_id);
										database.InsertStockRecievedlistData(getMid(), store_id, listDataChild, 
												listDataHeader);
										
										Toast.makeText(getApplicationContext(), " Data has been saved", 1).show();
										
										Intent DailyEntryMenu = new Intent(
												StockRecieved.this,
												DailyEntryMenu.class);
										startActivity(DailyEntryMenu);
										
									}
								})
						.setNegativeButton("No",
								new DialogInterface.OnClickListener() {
									public void onClick(
											DialogInterface dialog, int id) {
										dialog.cancel();
									}
								});
				AlertDialog alert = builder.create();

				alert.show();
			} else {
				Toast.makeText(getApplicationContext(), "Please fill all Data", Toast.LENGTH_LONG).show();
			}
	
		}
	}
		}
		
		
	

