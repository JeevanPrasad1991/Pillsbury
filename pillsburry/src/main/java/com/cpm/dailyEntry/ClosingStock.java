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
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;

public class ClosingStock extends Activity implements OnClickListener{
	
	private String store_id, username, intime, visit_date;
	private SharedPreferences preferences;
	ExpandableListView lv;
	Button save_btn;
	private pillsbury_Database database;
	Helper help = new Helper();
	Boolean update = false;
	private HashMap<ClosingStockBean, List<ClosingStockBean>> _listDataChild;
	List<ClosingStockBean> listDataHeader;
	HashMap<ClosingStockBean, List<ClosingStockBean>> listDataChild;
	List<ClosingStockBean> save_listDataHeader;
	ExpandableListAdapter listAdapter;
	private static ArrayList<ClosingStockBean> compliance_list = new ArrayList<ClosingStockBean>();
	private static ArrayList<ClosingStockBean> sub_compliance_list = new ArrayList<ClosingStockBean>();
	ArrayList<OpeningStockBean> list = new ArrayList<OpeningStockBean>();
	ArrayList<MidDayRecievedStockBean> midStock = new ArrayList<MidDayRecievedStockBean>();
	int open_stock, midd_stock, close_stock;
	boolean validation_flag = true;
	boolean expire_flag = false;
	List<Integer>validate_header=new ArrayList<>();
	boolean isDialogOpen = true;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.closingstock);
		lv = (ExpandableListView)findViewById(R.id.lvExp);
		save_btn = (Button)findViewById(R.id.save_btn);
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		visit_date = preferences.getString(CommonString.KEY_VISIT_DATE, null);
		preferences.getString(CommonString.KEY_STORE_NAME, "");
		username = preferences.getString(CommonString.KEY_USERNAME, "");
		store_id = preferences.getString(CommonString.KEY_STORE_ID, "");
		
		System.out.println("The store_id is:"+store_id);
		intime = preferences.getString(CommonString.KEY_STORE_IN_TIME, "");
		database = new pillsbury_Database(this);
		database.open();
		
				
		prepareListData();
		
		listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
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
		//super.onBackPressed();
		AlertDialog.Builder builder = new AlertDialog.Builder( this);
        builder.setMessage("Are you sure you want to quit ?") .setCancelable(false).setPositiveButton("YES",
        new DialogInterface.OnClickListener() {
          public void onClick(  DialogInterface dialog, int id) {
        	  Intent in = new Intent(ClosingStock.this, DailyEntryMenu.class);
      		startActivity(in);
      		ClosingStock.this.finish();

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

		listDataHeader = new ArrayList<ClosingStockBean>();
		listDataChild = new HashMap<ClosingStockBean, List<ClosingStockBean>>();
		
		compliance_list = database.getInsertedClosingStockComplianceData(store_id);
		
		if(compliance_list.size() == 0){
		
		compliance_list = database.getClosingStock(store_id);

			for (int i2 = 0; i2 < compliance_list.size(); i2++) {

				ClosingStockBean chk = new ClosingStockBean();
				chk.setBRAND(compliance_list.get(i2).getBRAND());
				chk.setBRAND_CD(compliance_list.get(i2).getBRAND_CD());

				listDataHeader.add(chk);

				sub_compliance_list = database.getSubskulistClosingData(compliance_list.get(i2).getBRAND_CD(),store_id);

				for (int i = 0; i < sub_compliance_list.size(); i++) {

					sub_compliance_list.get(i).setStock("");
				}
				listDataChild.put(chk, sub_compliance_list);
				listDataChild.put(chk, addData(sub_compliance_list));
			}
	}
		else{
			update = true;
			save_btn.setText("Update");

			for (int i2 = 0; i2 < compliance_list.size(); i2++) {

				ClosingStockBean chk = new ClosingStockBean();
				chk.setBRAND(compliance_list.get(i2).getBRAND());
				chk.setBRAND_CD(compliance_list.get(i2).getBRAND_CD());

				listDataHeader.add(chk);

				sub_compliance_list = database
						.getInsertedClosingStockSubList(compliance_list.get(i2).getCommonId());


				listDataChild.put(chk, sub_compliance_list);
				listDataChild.put(chk, addData(sub_compliance_list));

		}
	}
	}
	
	
	public ArrayList<ClosingStockBean> addData(
			ArrayList<ClosingStockBean> subList) {

		for (int i = 0; i < subList.size(); i++) {

			list = database
					.getOpeningDataForCheck(subList.get(i).getSKU_CD(), store_id);
			
			midStock = database.getMidDayStockForCheck(subList.get(i).getSKU_CD());
			
			if (list.size()>0) {
				String stock = list.get(0).getOpeningStock();
				String listed = list.get(0).getListedFlag();
				subList.get(i).setOpening_stock(stock);
				subList.get(i).setListed(listed);
			}else{
				subList.get(i).setOpening_stock("0");
				subList.get(i).setListed("");
			}
			
			if (midStock.size()>0) {
				String Stock = midStock.get(0).getStock();
				subList.get(i).setMid_stock(Stock);
			} else {
				subList.get(i).setMid_stock("0");
			}
			}
		return subList;

	}
	
	private class ExpandableListAdapter extends BaseExpandableListAdapter {
		
		private Context _context;
		
		public ExpandableListAdapter(Context context,
				List<ClosingStockBean> listDataHeader,
				HashMap<ClosingStockBean, List<ClosingStockBean>> listChildData) {
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
			
			final ClosingStockBean childText = (ClosingStockBean) getChild(groupPosition, childPosition);

			if (convertView == null) {
				LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = infalInflater.inflate(R.layout.closingstock_listview, null);
			}
			 TextView sku_name = (TextView)convertView.findViewById(R.id.sku_name);
			 EditText sku_stock = (EditText)convertView.findViewById(R.id.sku_stock_recieved);
			LinearLayout l  = (LinearLayout)convertView.findViewById(R.id.second);
			TextView total = (TextView)convertView.findViewById(R.id.openingStock);
			TextView mid = (TextView)convertView.findViewById(R.id.MidStock);
			 sku_stock.setText(childText.getStock());
			sku_stock.setOnFocusChangeListener(new OnFocusChangeListener() {
				public void onFocusChange(View v, boolean hasFocus) {
					if (!hasFocus) {
						final EditText Caption = (EditText) v;
						String value1 = Caption.getText().toString().replaceFirst("^0+(?!$)", "");
						if (!childText.getMid_stock().equals("") || (!childText.getOpening_stock().equalsIgnoreCase(""))) {
							if (!value1.equals("")) {
								if (Integer.parseInt(value1) <= Integer.parseInt(childText.getOpening_stock() + Integer.parseInt(childText.getMid_stock()))) {
									_listDataChild.get(save_listDataHeader.get(groupPosition)).get(childPosition).setStock(value1);
								} else {
									if (isDialogOpen) {
										isDialogOpen = !isDialogOpen;
										//Toast.makeText(getApplicationContext(),"Facing value can not be greater than openning stock value",Toast.LENGTH_LONG).show();
										AlertDialog.Builder builder = new AlertDialog.Builder(ClosingStock.this);
										builder.setMessage("Total value can not be greater than openning stock value and mid day stock")
												.setCancelable(false)
												.setPositiveButton("OK", new DialogInterface.OnClickListener() {
													public void onClick(DialogInterface dialog, int id) {
														dialog.dismiss();
														isDialogOpen = !isDialogOpen;
													}
												});
										AlertDialog alert = builder.create();
										alert.show();
									}
								}
							} else {

								_listDataChild.get(save_listDataHeader.get(groupPosition)).get(childPosition).setStock("");
							}
						}else {

						}
					}
				}
			});
	     	final 	String openingStock = childText.getOpening_stock();
			final	String midStock = childText.getMid_stock();
			final	String listed = childText.getListed();
			
			if (openingStock.equalsIgnoreCase("") && midStock.equalsIgnoreCase("")) {
//				sku_stock.setEnabled(false);
				total.setText("0");
				mid.setText("0");
				
			} else {
				
//				sku_stock.setEnabled(true);
			}
			
			
			if (listed.equalsIgnoreCase("false")) {
				l.setEnabled(false);
				sku_stock.setEnabled(false);
				total.setText("0");
				mid.setText("0");
				sku_stock.setText("0");
				
				
			}else{
				l.setEnabled(true);
				sku_stock.setEnabled(true);
			}
			

			sku_name.setText(childText.getSKU());
			total.setText(childText.getOpening_stock());
			mid.setText(childText.getMid_stock());


			if(!validation_flag) {
				if (sku_stock.getText().toString().equalsIgnoreCase("")) {
					sku_stock.setBackgroundColor(Color.parseColor("#FF0000"));
				} else {
					sku_stock.setBackgroundColor(Color.parseColor("#00BFFF"));
				}

			}

			if(expire_flag) {
				int openstock=0;
				openstock= Integer.parseInt(openingStock);
				//openstock= Integer.parseInt(childText.getOpening_stock());

				int mid_stock=0;
				mid_stock= Integer.parseInt(midStock);
				//mid_stock= Integer.parseInt(childText.getMid_stock());
				int skustock=0;
				skustock= Integer.parseInt(sku_stock.getText().toString());

				if (skustock<(mid_stock+openstock) || skustock<=(mid_stock+openstock)) {
					sku_stock.setBackgroundColor(Color.parseColor("#00BFFF"));

				} else {
					sku_stock.setBackgroundColor(Color.parseColor("#FF0000"));
				}

			}

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
			final ClosingStockBean headerTitle = (ClosingStockBean) getGroup(groupPosition);
			if (convertView == null) {
				LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = infalInflater.inflate(R.layout.brand, null);
			}

			TextView brand_name = (TextView) convertView.findViewById(R.id.brand_name);
			brand_name.setText(headerTitle.getBRAND());
			LinearLayout layout= (LinearLayout) convertView.findViewById(R.id.first);
			if (!validation_flag){
				if (validate_header.contains(groupPosition)){
					layout.setBackgroundColor(Color.RED);
				}
				else {
					layout.setBackgroundColor(Color.parseColor("#CCCCCC"));
				}
			}
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
	
	boolean validateMidAndOpening(HashMap<ClosingStockBean, List<ClosingStockBean>> listDataChild2,
			List<ClosingStockBean> listDataHeader2) {
		boolean flag = true;

		for (int i = 0; i < listDataHeader2.size(); i++) {
			for (int j = 0; j < listDataChild2.get(listDataHeader.get(i)).size(); j++) {
				
				String listedFlag = listDataChild.get(listDataHeader.get(i)).get(j).getListed();
				String openingStock_value = listDataChild.get(listDataHeader.get(i)).get(j).getOpening_stock();
				String mid_stock = listDataChild.get(listDataHeader.get(i)).get(j).getMid_stock();
				if(listedFlag.equalsIgnoreCase("1") ||listedFlag.equalsIgnoreCase("")){
					if (openingStock_value.equalsIgnoreCase("") || mid_stock.equalsIgnoreCase("")) {
						validation_flag=false;
						flag = false;

						break;
					}

				}
				else{
					flag = true;
				}
				
				
				
			}
			
			if (flag == false) {
				validation_flag =false;
				break;
			}
			
			}
	       return flag;
	}
	
	boolean validate(HashMap<ClosingStockBean, List<ClosingStockBean>> listDataChild2, List<ClosingStockBean> listDataHeader2) {
		boolean flag = true;
		validate_header.clear();
		for (int i = 0; i < listDataHeader2.size(); i++) {
			for (int j = 0; j < listDataChild2.get(listDataHeader.get(i)).size(); j++) {
				
				String openingStock_value = listDataChild.get(listDataHeader.get(i)).get(j).getOpening_stock();
				String mid_stock = listDataChild.get(listDataHeader.get(i)).get(j).getMid_stock();
				String closing_stock = listDataChild.get(listDataHeader.get(i)).get(j).getStock();	
				String listed = listDataChild.get(listDataHeader.get(i)).get(j).getListed();
				
				if (listed.equalsIgnoreCase("1") ||listed.equalsIgnoreCase("")) {
					open_stock = Integer.parseInt(openingStock_value);
					midd_stock = Integer.parseInt(mid_stock);
					
					
					if (closing_stock.isEmpty() || closing_stock.equalsIgnoreCase("")) {
						flag = false;
						validation_flag=false;
						validate_header.add(i);
						break;
					}
					
					if (!closing_stock.equalsIgnoreCase("") && !closing_stock.equalsIgnoreCase("?")) {
						close_stock = Integer.parseInt(closing_stock);
						
						
					} else {
						close_stock = 0;
						listDataChild.get(listDataHeader.get(i)).get(j).setStock("?");
					}	
				} else {
					
					flag = true;

				}
				
					
				
			}
			
			if (flag == false) {
				validation_flag=false;
				break;
			}
			
			}
	       return flag;
	}
	
	
	boolean validateData(
			HashMap<ClosingStockBean, List<ClosingStockBean>> listDataChild2,
			List<ClosingStockBean> listDataHeader2) {
		boolean flag = true;
		for (int i = 0; i < listDataHeader2.size(); i++) {
			for (int j = 0; j < listDataChild2.get(listDataHeader.get(i)).size(); j++) {
				
				String openingStock_value = listDataChild.get(listDataHeader.get(i)).get(j).getOpening_stock();
				String mid_stock = listDataChild.get(listDataHeader.get(i)).get(j).getMid_stock();
				String closing_stock = listDataChild.get(listDataHeader.get(i)).get(j).getStock();	
				String listed = listDataChild.get(listDataHeader.get(i)).get(j).getListed();
				
				if (listed.equalsIgnoreCase("1") ||listed.equalsIgnoreCase("")) {
					open_stock = Integer.parseInt(openingStock_value);
					midd_stock = Integer.parseInt(mid_stock);
					close_stock = Integer.parseInt(closing_stock);
					
					if ((close_stock<(open_stock+midd_stock)) || (close_stock<=(open_stock+midd_stock))) {
						flag = true;
						expire_flag=true;

						
						
					} else {
						flag = false;
						break;
						

					}	
				} 
				
				
				else {
						flag = true;
				}
				
				
				
			
				
			}
			
			if (flag == false) {
				expire_flag=true;
				break;
			}
			
		
			
			}
	       return flag;
	}
	
		
	


	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.save_btn) {
			lv.clearFocus();
			if (validateMidAndOpening(listDataChild, listDataHeader)) {
				if(validate(listDataChild, listDataHeader))
				{
					if (validateData(listDataChild, listDataHeader)) {
						AlertDialog.Builder builder = new AlertDialog.Builder(this);
						builder.setMessage("Are you sure you want to save")
								.setCancelable(false)
								.setPositiveButton("Yes",
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialog, int id) {
												

												database.open();
//												database.InsertClosingStock(getMid(),store_id, closingStockData);
												database.deleteClosingStockData(store_id);
												database.InsertClosingStocklistData(getMid(), store_id, listDataChild, listDataHeader);
												
												Toast.makeText(getApplicationContext(), " Data has been saved",Toast.LENGTH_SHORT).show();
												
												Intent DailyEntryMenu = new Intent(
														ClosingStock.this,
														DailyEntryMenu.class);
												startActivity(DailyEntryMenu);
												
												//if (check) {
													/*
													 * database.UpdateSkuData(
													 * getMid(), store_id,
													 * salesData);
													 */

												//} else {

//													setCmmnttoCOllection();
//													database.InsertPosmDetailsInserted(
//															getMid(),store_id, PosmData);
												

												//}
//												
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
						Toast.makeText(getApplicationContext(), "Your value must be less than or equal to Opening stock+mid day stock ", Toast.LENGTH_LONG).show();
						lv.invalidateViews();
					}
						
				}else{
					Toast.makeText(getApplicationContext(), "You must enter the closing stock value", Toast.LENGTH_LONG).show();
					lv.invalidateViews();
				}
			} else {
				Toast.makeText(getApplicationContext(), "First Enter the Opening and Mid day stocks", Toast.LENGTH_LONG).show();
				lv.invalidateViews();
			}
			
			
		}
		
	}

}
