package com.cpm.dailyEntry;




import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import com.cpm.Constants.CommonString;





import com.cpm.database.pillsbury_Database;
import com.cpm.delegates.Posmbean;




import com.cpm.delegates.competitionbeans;
import com.cpm.pillsbury.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class AdditionalVisibility extends Activity {
	
	ListView listview;
	ImageView cam;
	Spinner brand, display;
	String cate_name, posm_name, cate_id, posm_id;
	Button add;
	pillsbury_Database db;
	protected static String _pathforcheck = "";
	private SharedPreferences preferences;
	private String store_id, username, intime, visit_date;
	String img1 = "";
	String _path;
	String image1="";
	MyAdaptor  adapterData;
	
	private static String str;
	public ArrayList<competitionbeans> brand_list = new ArrayList<competitionbeans>();
	public ArrayList<Posmbean> display_list = new ArrayList<Posmbean>();
	ArrayList<Posmbean> list = new ArrayList<Posmbean>();
	private ArrayAdapter<CharSequence> brandAdaptor, displayAdaptor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addtional_before_display);
		listview = (ListView)findViewById(R.id.lv);
		cam = (ImageView)findViewById(R.id.camera);
		brand = (Spinner)findViewById(R.id.brand_name);
		display = (Spinner)findViewById(R.id.display_name);
		add = (Button)findViewById(R.id.add_btn);
		
		db = new pillsbury_Database(AdditionalVisibility.this);
		db.open();
		
		
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		visit_date = preferences.getString(CommonString.KEY_VISIT_DATE, null);
		preferences.getString(CommonString.KEY_STORE_NAME, "");
		intime = preferences.getString(CommonString.KEY_STORE_IN_TIME, "");
		username = preferences.getString(CommonString.KEY_USERNAME, "");
		store_id = preferences.getString(CommonString.KEY_STORE_ID, "");
		
		str = Environment.getExternalStorageDirectory()+"/Pillsbury_Images/";
		brand_list = db.getCategoryData();
		display_list = db.getDisplayList();
		
		brandAdaptor = new ArrayAdapter<CharSequence>(AdditionalVisibility.this, android.R.layout.simple_spinner_item);
		brandAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		
		displayAdaptor = new ArrayAdapter<CharSequence>(AdditionalVisibility.this, android.R.layout.simple_spinner_item);
		displayAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		
		brandAdaptor.add("Select Brand");
		for(int i=0 ; i<brand_list.size();i++){
		brandAdaptor.add(brand_list.get(i).getCategory());
	}
	
		displayAdaptor.add("Select Display");
		for(int i=0 ; i<display_list.size();i++){
			displayAdaptor.add(display_list.get(i).getPOSM());
		}
		
		brand.setAdapter(brandAdaptor);
		display.setAdapter(displayAdaptor);
		
		brand.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				
				if (position != 0) {
					cate_name = brand_list.get(position - 1).getCategory();
					cate_id = brand_list.get(position- 1).getCategory_id();
					
				} else {
					
					cate_name = "";
					cate_id = "";
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
				
			}
		});
		
		display.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				
				if (position != 0) {
					posm_name = display_list.get(position - 1).getPOSM();
					posm_id = display_list.get(position- 1).getPOSM_CD();
//					image_url = display_list.get(position-1).getImage_url();
					
				} else {
					
					posm_name = "";
					posm_id = "";
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
				
			}
		});
		
		
		
		add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
//			String quan = 	quantity.getText().toString();
			
			
				if (!image1.equalsIgnoreCase("")) {

					
					if (validatedata()) {
						Posmbean ab = new Posmbean();
						
						
						ab.setCategory(cate_name);
						ab.setCategory_id(cate_id);
						ab.setPOSM(posm_name);
						ab.setPOSM_CD(posm_id);
						ab.setImage(image1);
						
		
						db.InsertAdditionalInfo(ab, store_id);
//						quantity.setText("1");
						
						brand.setSelection(0);
						display.setSelection(0);
						image1 = "";
						cam.setBackgroundResource(R.drawable.camera_list);
						
						
						list = db.getProductEntryDetail(store_id);
				        adapterData = new MyAdaptor(AdditionalVisibility.this,list);
				        
				    	listview.setAdapter(adapterData);
				    	listview.invalidateViews();
					} else {
						Toast.makeText(getBaseContext(), "Please Select the Category,POSM", Toast.LENGTH_LONG).show();
					}

				} else {
					
					Toast.makeText(getBaseContext(), "Please Take a photo", Toast.LENGTH_LONG).show();

				}
			 
			
			
				
			}
		});
		
		
	}
	
	public boolean validatedata() {
		boolean result = false;
		if (cate_name != null && !cate_id.equalsIgnoreCase("") && posm_name != null && !posm_id.equalsIgnoreCase("")) {
			result = true;
		}

		return result;

	}
	
 public void onButtonClick(View v){
         
         
         if (v.getId() == R.id.camera) {

        	 if (cate_name != null && !cate_id.equalsIgnoreCase("") && posm_name != null && !posm_id.equalsIgnoreCase("")) {
        		 _pathforcheck = store_id+""+ getCurrentDate().replace("/", "") + cate_id + posm_id + getCurrentTime().replace(":", "") +".jpg";
        		 
                 _path = str + _pathforcheck;
                 startCameraActivity();
     		}
        	 
        	 else{
        		 Toast.makeText(AdditionalVisibility.this, "Please select Category and Posm first", Toast.LENGTH_LONG).show();
        	 }

         
         
         }          
         
}
 
 
 protected void startCameraActivity() {

     try {
                     Log.i("MakeMachine", "startCameraActivity()");
                     File file = new File(_path);
                     Uri outputFileUri = Uri.fromFile(file);

                     Intent intent = new Intent( android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                     intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

                     startActivityForResult(intent, 0);
     } catch (Exception e) { 
                     e.printStackTrace();
     }
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

		               cam.setBackgroundResource(R.drawable.camera_list_tick);
		               image1 = _pathforcheck;
		               img1 = _pathforcheck;
		               listview.invalidateViews();
		               _pathforcheck = "";
		               //Toast.makeText(getApplicationContext(), ""+image1, Toast.LENGTH_LONG).show();

		            }
		         }

		         break;
		   }

		super.onActivityResult(requestCode, resultCode, data);
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
	
 
 public String getCurrentTime() {

		Calendar m_cal = Calendar.getInstance();
		String intime = m_cal.get(Calendar.HOUR_OF_DAY) + ":"
				+ m_cal.get(Calendar.MINUTE) + ":" + m_cal.get(Calendar.SECOND);

		return intime;
	}
 
	public class MyAdaptor extends BaseAdapter{
		
		private LayoutInflater mInflater;
		private Context mcontext;
		private ArrayList<Posmbean> list;

	public MyAdaptor(Activity activity, ArrayList<Posmbean> list1) {
		
		mInflater = LayoutInflater.from(getBaseContext());
		mcontext = activity;
		list = list1;
		}

	@Override
	public int getCount() {
		
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		
		return position;
	}

	@Override
	public long getItemId(int position) {
		
		return position;
	}
	
	class ViewHolder {
		TextView brand, qty_bought, display;
		Button save,delete;

	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		final ViewHolder holder;
		
		if (convertView == null) {

			convertView = mInflater
					.inflate(R.layout.addtional_list, null);
			holder = new ViewHolder();
		
			holder.brand = (TextView) convertView.findViewById(R.id.brand_name);

			holder.display = (TextView) convertView.findViewById(R.id.display_name);
		
			
			

			holder.delete = (Button) convertView.findViewById(R.id.delete_btn);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						AdditionalVisibility.this);
		 
					// set title
					alertDialogBuilder.setTitle("Do You Want To Delete?");
		 
					// set dialog message
					alertDialogBuilder
						.setMessage("Click Yes To Delete!")
						.setCancelable(false)
						.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								// if this button is clicked, close
								// current activity
								
								db.deleteProductEntry(list.get(position).getKey_id());
								
								adapterData.notifyDataSetChanged();
								
								list = db.getProductEntryDetail(store_id);
								listview.setAdapter(new MyAdaptor(AdditionalVisibility.this, list));
								listview.invalidateViews();
								
							}
						  })
						.setNegativeButton("No",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								// if this button is clicked, just close
								// the dialog box and do nothing
								dialog.cancel();
							}
						});
		 
						// create alert dialog
						AlertDialog alertDialog = alertDialogBuilder.create();
		 
						// show it
						alertDialog.show();
			
			}			
		});
		

		
		holder.brand.setText(list.get(position).getCategory()
					.toString());
		holder.display.setText(list.get(position).getPOSM().toString());
		
		
		
	
		
		
		return convertView;
	}
	}

}
