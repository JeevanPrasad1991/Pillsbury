package com.cpm.dailyEntry;

import java.util.ArrayList;
import java.util.Calendar;

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
import com.cpm.delegates.PerformanceBean;
import com.cpm.delegates.salesBean;
import com.cpm.pillsbury.R;

public class PerformanceStore extends Activity implements OnClickListener {
	

	private ArrayList<PerformanceBean> PerformanceData = new ArrayList<PerformanceBean>();
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
		super.onCreate(savedInstanceState);
		setContentView(R.layout.performancestorelist);

		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		visit_date = preferences.getString(CommonString.KEY_DATE, null);
		store_id = preferences.getString(CommonString.KEY_STORE_ID, null);
		savebtn = (Button) findViewById(R.id.button1);

		list2 = (ListView) findViewById(R.id.listView1);
		database = new pillsbury_Database(this);
		database.open();
		intime = getCurrentTime();

		PerformanceData = database.getPerformanceStore(null);

		
		
		list2.setAdapter(new MyAdapter());
		// setListAdapter(new MyAdapter());
	

	}

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return PerformanceData.size();
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
						.inflate(R.layout.performancestore_adapter, null);

				holder.storename = (TextView) convertView
						.findViewById(R.id.txt_storename);
				holder.city = (TextView) convertView
						.findViewById(R.id.txt_city);
				holder.sales = (TextView) convertView
						.findViewById(R.id.txt_sale);
				holder.share = (TextView) convertView
						.findViewById(R.id.txt_share);
				holder.tam = (TextView) convertView
						.findViewById(R.id.txt_tam);
				holder.terriority = (TextView) convertView
						.findViewById(R.id.txt_terrority);

				convertView.setTag(holder);

			} else {
				holder = (ViewHolder) convertView.getTag();
			}

		
			
		
			
				
				holder.storename.setText(PerformanceData.get(position).getSTORENAME());
				holder.city.setText(PerformanceData.get(position).getCITY());
				holder.sales.setText(PerformanceData.get(position).getSALE());
				holder.share.setText(PerformanceData.get(position).getSHARE());
				holder.tam.setText(PerformanceData.get(position).getTAM());
				holder.terriority.setText(PerformanceData.get(position).getTERRITORY());
			
			
			
				   if(PerformanceData.get(position).getTERRITORY().equalsIgnoreCase("red"))
				    {
				    	holder.terriority.setBackgroundColor(Color.RED);
				    	
				    }
				    if(PerformanceData.get(position).getTERRITORY().equalsIgnoreCase("green"))
				    {
				    	holder.terriority.setBackgroundColor(Color.GREEN);
				    	
				    }
				    if(PerformanceData.get(position).getTERRITORY().equalsIgnoreCase("blue"))
				    {
				    	holder.terriority.setBackgroundColor(Color.BLUE);
				    	
				    }
			
			
			
			//holder.Models.setText(salesData.get(position).getProduct());
			//holder.Type.setText(salesData.get(position).getType());
			//holder.unitsold.setText(salesData.get(position).getUnitSold());

			/*holder.Idealfor.setId(position);
			holder.lastmonthsale.setId(position);
			holder.tilldateSale.setId(position);
			holder.salesOutlook.setId(position);
*/
			return convertView;
		}

		private class ViewHolder {

			TextView storename,city,share,sales,tam,terriority;
		//	EditText lastmonthsale, tilldateSale,salesOutlook;
			ImageView imgtick;


		}

	}

	public long checkMid() {

		long mid = 0;

		mid = database.CheckMid(visit_date, store_id);

		return mid;
	}

	public void onClick(View v) {}

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

		Intent intent = new Intent(this, reports.class);
		startActivity(intent);
		PerformanceStore.this.finish();

	}

	

}
