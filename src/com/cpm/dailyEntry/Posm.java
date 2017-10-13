package com.cpm.dailyEntry;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;




import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cpm.Constants.CommonString;
import com.cpm.database.pillsbury_Database;
import com.cpm.delegates.CoverageBean;
import com.cpm.delegates.Posmbean;
import com.cpm.pillsbury.R;

public class Posm extends Activity implements OnClickListener {

	public String store_name;
	public ListView lv;
	private static final int CAMERA_PIC_REQUEST = 1;
	protected static String _pathforcheck = "";
	public static String presence[];
	public static String remarks[];
	public static int pos[], camera[], image1[], image2[], image3[];

	protected String _path;
	private static File file;
	private static String str, path;
	String img1 = "";
	private static Uri uri;
	public static final int Info_SELECT = 1;
	static boolean trigerscroll = true;
	public static final int POPUP_SELECT = 2;
	static int mposition = -1;
	AlertDialog alert;
	Boolean update = false;
	Button save;

	private ArrayList<Posmbean> PosmData = new ArrayList<Posmbean>();
	private pillsbury_Database database;
	private SharedPreferences preferences;
	private String store_id, username, intime, brand_id, visit_date;
	boolean check = false;

	String storetype_id, region_id;
	EditText comment;
	private Button savebtn;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.posmlist);

		lv = (ListView) findViewById(R.id.listView1);
		save = (Button) findViewById(R.id.save);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		intime = getCurrentTime();

		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		visit_date = preferences.getString(CommonString.KEY_DATE, null);
		store_name = preferences.getString(CommonString.KEY_STORE_NAME, "");
		username = preferences.getString(CommonString.KEY_USERNAME, "");
		store_id = preferences.getString(CommonString.KEY_STORE_ID, "");
		comment = (EditText) findViewById(R.id.comments);

		str = Environment.getExternalStorageDirectory()
				+ "/westernDigital_Images/";

		savebtn = (Button) findViewById(R.id.button1);

		database = new pillsbury_Database(this);
		database.open();

		PosmData = database.getPosmDataInserted((int) checkMid());

		if (PosmData.size() > 0) {

			setCmmntTxtBox();
			check = true;

		} else 
		{
			PosmData = database.getPosmData();
		}

		savebtn.setOnClickListener(this);

	    lv.setAdapter(new MyAdapter());
		setListViewHeightBasedOnChildren(lv);
	}

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return PosmData.size();
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
				convertView = inflater.inflate(R.layout.posmlistadapter, null);

				holder.Idealfor = (TextView) convertView
						.findViewById(R.id.txt_idealFor);
				holder.NoOFUnits = (EditText) convertView
						.findViewById(R.id.noOfUnits);

				holder.Imageposm = (ImageView) convertView
						.findViewById(R.id.imageCam);

				convertView.setTag(holder);

			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.Imageposm.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					mposition = position;
					_pathforcheck = store_id + "_" + getCurrentDate()
							+ PosmData.get(position).getPOSM() + ".jpg";

					_path = str + _pathforcheck;

					startCameraActivity();
				}

			});

			holder.NoOFUnits
					.setOnFocusChangeListener(new OnFocusChangeListener() {
						public void onFocusChange(View v, boolean hasFocus) {

							if (!hasFocus) {
								final int position = v.getId();
								final EditText Caption = (EditText) v;
								String value1 = Caption.getText().toString();
								// Toast.makeText(mcontext, "got it",
								// 2000).show();
								if (value1.equals("")) {
									PosmData.get(position).setNoof_Units("");

								} else {

									PosmData.get(position)
											.setNoof_Units(value1);

								}

							}
						}
					});
			if (!PosmData.get(position).getImage().equalsIgnoreCase("")) {
				holder.Imageposm
						.setOnLongClickListener(new View.OnLongClickListener() {

							@Override
							public boolean onLongClick(View v) {
								// TODO Auto-generated method stub

								Intent intent = new Intent(Posm.this,
										ShowImage.class);
								_pathforcheck = PosmData.get(position)
										.getImage();
								intent.putExtra("Image", _pathforcheck);
								startActivity(intent);

								return false;
							}
						});

			}

			if (!img1.equalsIgnoreCase("")) {
				if (position == mposition) {
					// PosmData.get(position).setCamera("YES");
					PosmData.get(position).setImage(img1);
					img1 = "";

				}
			}
			
			if (PosmData.get(position).getImage().equalsIgnoreCase("")) {
				holder.Imageposm
						.setBackgroundResource(R.drawable.camera_list);
			} else {
				holder.Imageposm
						.setBackgroundResource(R.drawable.camera_list_tick);
			}

			// holder.Idealfor.setText("poster");
			holder.Idealfor.setText(PosmData.get(position).getPOSM());

			holder.NoOFUnits.setText(PosmData.get(position).getNoof_Units());
			// holder.Comments.setText(PosmData.get(position).getcomments());//

			holder.NoOFUnits.setId(position);
			holder.Imageposm.setId(position);
			return convertView;
		}

		private class ViewHolder {

			TextView Idealfor, Type, Models;
			EditText NoOFUnits;
			ImageView Imageposm;

		}

	}

	public long checkMid() {

		long mid = 0;

		mid = database.CheckMid(visit_date, store_id);

		return mid;
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
			cdata.setImage("");
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

	protected void startCameraActivity() {

		try {
			Log.i("MakeMachine", "startCameraActivity()");
			File file = new File(_path);
			Uri outputFileUri = Uri.fromFile(file);

			Intent intent = new Intent(
					android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

			startActivityForResult(intent, 0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getCurrentDate() {

		Calendar m_cal = Calendar.getInstance();

		int month = m_cal.get(Calendar.MONTH);

		String currmonth = Integer.toString(month + 1);

		if (month <= 9) {

			currmonth = "0" + currmonth;
		} else {

			currmonth = currmonth;

		}

		String currentdate = currmonth + "_" + m_cal.get(Calendar.DAY_OF_MONTH)
				+ "_" + m_cal.get(Calendar.YEAR);

		return currentdate;

	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.i("MakeMachine", "resultCode: " + resultCode);
		switch (resultCode) {
		case 0:
			Log.i("MakeMachine", "User cancelled");
			break;

		case -1:

			if (_pathforcheck != null && !_pathforcheck.equals("")) {
				if (new File(str + _pathforcheck).exists()) {

					img1 = _pathforcheck;
					lv.invalidateViews();
					_pathforcheck = "";
					break;

				}
			}

			break;
		}
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.button1) {
			lv.clearFocus();

			if (validate()) {
				if (validateall()) {
					AlertDialog.Builder builder = new AlertDialog.Builder(this);
					builder.setMessage("Are you sure you want to save")
							.setCancelable(false)
							.setPositiveButton("Yes",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {

											database.open();
											//if (check) {
												/*
												 * database.UpdateSkuData(
												 * getMid(), store_id,
												 * salesData);
												 */

											//} else {

												setCmmnttoCOllection();
												database.InsertPosmDetailsInserted(
														getMid(),

														store_id, PosmData);
											

											//}
											Intent DailyEntryMenu = new Intent(
													Posm.this,
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
				} else

				{
					Toast.makeText(getBaseContext(),
							"Please enter atleast one value", Toast.LENGTH_LONG)
							.show();
				}
			} else {
				Toast.makeText(getBaseContext(),
						"Invalid information",
						Toast.LENGTH_LONG).show();
			}
		}

	}

	void setCmmnttoCOllection() {

		for (int i = 0; i < PosmData.size(); i++) {
			PosmData.get(i).setComments(comment.getText().toString());

		}

	}

	void setCmmntTxtBox() {

		comment.setText(PosmData.get(0).getComments().toString());

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

		Intent intent = new Intent(this, DailyEntryMenu.class);
		startActivity(intent);
		Posm.this.finish();

	}

	boolean validate() {
		boolean flag = true;
		for (int i = 0; i < PosmData.size(); i++) {
			if (!PosmData.get(i).getNoof_Units().equalsIgnoreCase("")
					|| !PosmData.get(i).getImage().equalsIgnoreCase(""))

			{

				if (PosmData.get(i).getNoof_Units().equalsIgnoreCase("")
						|| PosmData.get(i).getImage().equalsIgnoreCase("")) {
					flag = false;
					break;
				}

			}

		}
		return flag;

	}

	boolean validateall() {
		boolean flag = false;

		for (int i = 0; i < PosmData.size(); i++) {

			if (!PosmData.get(i).getNoof_Units().equalsIgnoreCase("")) {
				flag = true;
				break;
			}
		}
		return flag;
	}
	
	public static void updateListViewHeight(ListView myListView) {
		ListAdapter myListAdapter = myListView.getAdapter();
		if (myListAdapter == null) {
			return;
		}
		// get listview height
		int totalHeight = 0;
		int adapterCount = myListAdapter.getCount();
		if (adapterCount >5) {
			trigerscroll = true;
			adapterCount = 13;
		}

		for (int size = 0; size < adapterCount; size++) {
			View listItem = myListAdapter.getView(size, null, myListView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		// Change Height of ListView
		ViewGroup.LayoutParams params = myListView.getLayoutParams();
		params.height = totalHeight
				+ (myListView.getDividerHeight() * (adapterCount - 1));
		myListView.setLayoutParams(params);
	}
	
	
	

	
	
	public static void setListViewHeightBasedOnChildren(ListView listView) {
	    ListAdapter listAdapter = listView.getAdapter();
	    if (listAdapter == null)
	        return;

	    int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(), MeasureSpec.UNSPECIFIED);
	    int totalHeight = 0;
	    View view = null;
	    for (int i = 0; i < listAdapter.getCount(); i++) {
	        view = listAdapter.getView(i, view, listView);
	        if (i == 0)
	            view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, LayoutParams.WRAP_CONTENT));

	        view.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
	        totalHeight += view.getMeasuredHeight();
	    }
	    ViewGroup.LayoutParams params = listView.getLayoutParams();
	    params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
	    listView.setLayoutParams(params);
	    listView.requestLayout();
	}
	
	
}
