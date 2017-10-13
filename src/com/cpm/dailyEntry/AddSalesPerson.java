package com.cpm.dailyEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Pattern;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.cpm.Constants.CommonString;
import com.cpm.database.pillsbury_Database;
import com.cpm.delegates.CoverageBean;
import com.cpm.delegates.DesignationBean;
import com.cpm.delegates.JobLevelBean;
import com.cpm.pillsbury.R;

public class AddSalesPerson extends Fragment implements OnItemSelectedListener, OnClickListener  {

	public final Pattern EMAIL_ADDRESS_PATTERN = Pattern
			.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@"
					+ "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\."
					+ "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+");

	String[] title_array = { "Select Title", "Mr.", "Ms." };
	ArrayAdapter<String> title_adapter;
	Spinner title_spinner;
	Spinner designation_spinner;
	ArrayAdapter<CharSequence> designation_adapter;
	ArrayList<DesignationBean> designationdata = new ArrayList<DesignationBean>();

	Spinner joblevel_spinner;
	ArrayAdapter<CharSequence> joblevel_adapter;
	ArrayList<JobLevelBean> jobleveldata = new ArrayList<JobLevelBean>();

	pillsbury_Database db;
	String designation, designationid, joblevel, joblevelid, title;
	EditText edit_name, edit_email, edit_mobile, edit_phone;
	Button save;

	private SharedPreferences preferences;
	private String store_id, username, intime, brand_id, visit_date;
	Long contactid;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// Inflate the layout for this fragment
		View V = inflater.inflate(R.layout.addperson, container, false);

		return V;

	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		db = new pillsbury_Database(getActivity());
		db.open();

		edit_name = (EditText) getActivity().findViewById(R.id.name_edit);
		edit_email = (EditText) getActivity().findViewById(R.id.email_edit);
		edit_mobile = (EditText) getActivity().findViewById(R.id.mobile_edit);
		edit_phone = (EditText) getActivity().findViewById(R.id.phone_edit);
		save = (Button) getActivity().findViewById(R.id.save);
		save.setOnClickListener(this);

		intime = getCurrentTime();

		preferences = PreferenceManager
				.getDefaultSharedPreferences(getActivity());
		visit_date = preferences.getString(CommonString.KEY_DATE, null);
		// store_name = preferences.getString(CommonString.KEY_STORE_NAME, "");
		username = preferences.getString(CommonString.KEY_USERNAME, "");
		store_id = preferences.getString(CommonString.KEY_STORE_ID, "");

		// title
		title_spinner = (Spinner) getActivity().findViewById(R.id.spinner1);

		title_adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item);
		for (int i = 0; i < title_array.length; i++) {
			title_adapter.add(title_array[i]);

		}
		title_spinner.setAdapter(title_adapter);
		title_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		title_spinner.setOnItemSelectedListener(this);

		// designation

		designation_spinner = (Spinner) getActivity().findViewById(
				R.id.spinner2);

		designation_adapter = new ArrayAdapter<CharSequence>(getActivity(),
				android.R.layout.simple_spinner_item);

		designationdata = db.getDesignation();
		designation_adapter.add("Select designation");
		for (int i = 0; i < designationdata.size(); i++) {

			designation_adapter.add(designationdata.get(i).getDesignation());

		}
		designation_spinner.setAdapter(designation_adapter);
		designation_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		designation_spinner.setOnItemSelectedListener(this);

		// job level

		joblevel_spinner = (Spinner) getActivity().findViewById(R.id.spinner3);

		joblevel_adapter = new ArrayAdapter<CharSequence>(getActivity(),
				android.R.layout.simple_spinner_item);

		jobleveldata = db.getJobLevel();
		joblevel_adapter.add("Select JobLevel");
		for (int i = 0; i < jobleveldata.size(); i++) {

			joblevel_adapter.add(jobleveldata.get(i).getJOB());

		}
		joblevel_spinner.setAdapter(joblevel_adapter);
		joblevel_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		joblevel_spinner.setOnItemSelectedListener(this);

	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.spinner1:
			if (position != 0) {
				title = title_array[position];

			} else {
				title = "";
			}

			break;

		case R.id.spinner2:
			if (position != 0) {
				designation = designationdata.get(position - 1)
						.getDesignation_cd();
				designationid = designationdata.get(position - 1)
						.getDesignation();
			} else {
				designationid = "";
				designation = "";
			}

			break;

		case R.id.spinner3:
			if (position != 0) {
				joblevelid = jobleveldata.get(position - 1).getJOB_cd();
				joblevel = jobleveldata.get(position - 1).getJOB();
			} else {
				joblevel = "";
				joblevelid = "";
			}

			break;
		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

	boolean validate() {
		boolean flag = true;

		if (title.equalsIgnoreCase("") || designationid.equals("")
				|| joblevelid.equals("")
				|| edit_name.getText().toString().equals("")
				|| edit_email.getText().toString().equals("")
				|| edit_mobile.getText().toString().equals("")
				|| edit_phone.getText().toString().equals("")) {
			flag = false;
		}

		return flag;

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
			cdata.setOutlook_Remark("");
			mid = db.InsertCoverageData(cdata);

		}

		return mid;
	}

	public String getCurrentTime() {

		Calendar m_cal = Calendar.getInstance();

		String intime = m_cal.get(Calendar.HOUR_OF_DAY) + ":"
				+ m_cal.get(Calendar.MINUTE) + ":" + m_cal.get(Calendar.SECOND);

		return intime;

	}

	private boolean checkEmail(String email) {

		return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
	}
	boolean validatephoneno()
	{
		boolean flag = true;
		
		if(edit_mobile.getText().length() < 10) {
    		
   		 return false;
		}
		return flag;
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		if (v.getId() == R.id.save) {

			if (validate()) {
				
                if(checkEmail(edit_email.getText().toString().trim()))
                {

                	if(validatephoneno())
                	{

				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				builder.setMessage("Are you sure you want to save")
						.setCancelable(false)
						.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {

										db.open();
										// local
										contactid = db.AddSalesPerson(getMid(),

										store_id, title, edit_name.getText()
												.toString(), designation,
												joblevelid, edit_email
														.getText().toString(),
												edit_mobile.getText()
														.toString(), edit_phone
														.getText().toString());

										if (contactid != -1) {

											String contact_id = String
													.valueOf(contactid)
													+ "(new)";
											// downloaded
											db.AddSalesPersonInStoreContact(
													store_id, title, edit_name
															.getText()
															.toString(),
													designation, joblevelid,
													edit_email.getText()
															.toString(),
													edit_mobile.getText()
															.toString(),
													edit_phone.getText()
															.toString(),
													contact_id);

										}
										Intent i = new Intent(getActivity()
												.getBaseContext(),
												DailyEntryMenu.class);
										startActivity(i);

									}
								})
						.setNegativeButton("No",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});
				AlertDialog alert = builder.create();

				alert.show();
				
                	}
                	else{
                		Context context = getActivity().getApplicationContext();
        				Toast.makeText(getActivity(), "Please Enter valid phone number",
        						Toast.LENGTH_LONG).show();
                	}
				
			} else {

				Context context = getActivity().getApplicationContext();
				Toast.makeText(getActivity(), "Please Enter Valid Email",
						Toast.LENGTH_LONG).show();

			}
                	
                
			}
			else{
			
				Context context = getActivity().getApplicationContext();
				Toast.makeText(getActivity(), "Please Select All Fields",
						Toast.LENGTH_LONG).show();
				
			}
		}

	}

}
