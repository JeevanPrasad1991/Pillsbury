package com.cpm.dailyEntry;

import java.util.ArrayList;
import java.util.Calendar;

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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cpm.Constants.CommonString;
import com.cpm.database.pillsbury_Database;
import com.cpm.delegates.CoverageBean;
import com.cpm.delegates.SalesPersonBean;
import com.cpm.delegates.TrainingBean;
import com.cpm.pillsbury.R;

public class TrainingFragmnt extends Fragment implements
		OnItemSelectedListener, OnClickListener {

	Spinner salesperson_spinner;
	ArrayAdapter<CharSequence> salesperson_adapter;
	ArrayList<SalesPersonBean> salespersondata = new ArrayList<SalesPersonBean>();

	Spinner topic_spinner;
	ArrayAdapter<CharSequence> topic_adapter;
	ArrayList<TrainingBean> topicdata = new ArrayList<TrainingBean>();
	ArrayList<TrainingBean> getAddedData = new ArrayList<TrainingBean>();

	pillsbury_Database db;
	String salesperson, salesperson_id, topic, topicid;

	Button save;

	private SharedPreferences preferences;
	private String store_id, username, intime, brand_id, visit_date;
	ListView l1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// Inflate the layout for this fragment
		View V = inflater.inflate(R.layout.trainingfragmrnt, container, false);

		return V;
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		db = new pillsbury_Database(getActivity());
		db.open();

		save = (Button) getActivity().findViewById(R.id.save);

		l1 = (ListView) getActivity().findViewById(R.id.list);

		save.setOnClickListener(this);

		intime = getCurrentTime();

		preferences = PreferenceManager
				.getDefaultSharedPreferences(getActivity());
		visit_date = preferences.getString(CommonString.KEY_DATE, null);
		// store_name = preferences.getString(CommonString.KEY_STORE_NAME, "");
		username = preferences.getString(CommonString.KEY_USERNAME, "");
		store_id = preferences.getString(CommonString.KEY_STORE_ID, "");

		// designation

		salesperson_spinner = (Spinner) getActivity().findViewById(
				R.id.spinner1);

		salesperson_adapter = new ArrayAdapter<CharSequence>(getActivity(),
				android.R.layout.simple_spinner_item);

		salespersondata = db.getSalesperson(store_id);
		salesperson_adapter.add("Select Salesperson");
		for (int i = 0; i < salespersondata.size(); i++) {

			salesperson_adapter.add(salespersondata.get(i).getName() + "/"
					+ salespersondata.get(i).getDesignation());

		}
		salesperson_spinner.setAdapter(salesperson_adapter);
		salesperson_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		salesperson_spinner.setOnItemSelectedListener(this);

		// job level

		topic_spinner = (Spinner) getActivity().findViewById(R.id.spinner2);

		topic_adapter = new ArrayAdapter<CharSequence>(getActivity(),
				android.R.layout.simple_spinner_item);

		topicdata = db.getTraining();
		topic_adapter.add("Select Training");
		for (int i = 0; i < topicdata.size(); i++) {

			topic_adapter.add(topicdata.get(i).getTraining());

		}
		topic_spinner.setAdapter(topic_adapter);
		topic_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		topic_spinner.setOnItemSelectedListener(this);

		getAddedData = db.getAddedTrainingData(checkMid());

		l1.setAdapter(new MyAdapter());

	}
	
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		if (v.getId() == R.id.save) {

			if (!(topicid == "" || salesperson_id == "")) {

				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				builder.setMessage("Are you sure you want to save")
						.setCancelable(false)
						.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {

										db.open();

										db.AddTraining(getMid(), store_id,
												salesperson_id, salesperson,
												topicid, topic);

										getAddedData = db
												.getAddedTrainingData(checkMid());

										topic_spinner.setSelection(0);
										salesperson_spinner.setSelection(0);

										l1.setAdapter(new MyAdapter());

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
			} else {
				Toast.makeText(getActivity().getApplicationContext(),
						"Please Select SalesPerson & Training",
						Toast.LENGTH_LONG).show();
			}

		}
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.spinner1:
			if (position != 0) {
				salesperson = salespersondata.get(position - 1).getName();
				salesperson_id = salespersondata.get(position - 1)
						.getContact_cd();
			} else {
				salesperson = "";
				salesperson_id = "";
			}

			break;

		case R.id.spinner2:
			if (position != 0) {
				topicid = topicdata.get(position - 1).getTraining_id();
				topic = topicdata.get(position - 1).getTraining();
			} else {
				topicid = "";
				topic = "";
			}

			break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

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
			cdata.setOutlook_Remark("");
			cdata.setImage("");
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

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return getAddedData.size();
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

				LayoutInflater inflater = (LayoutInflater) getActivity()
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater
						.inflate(R.layout.listview_training, null);

				holder.salesperson = (TextView) convertView
						.findViewById(R.id.salesperson_edit);
				holder.training = (TextView) convertView
						.findViewById(R.id.training_edit);
				holder.contactid = (TextView) convertView
						.findViewById(R.id.contact_edit);

				convertView.setTag(holder);

			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.contactid
					.setText(getAddedData.get(position).getContact_cd());
			holder.salesperson.setText(getAddedData.get(position)
					.getSalesperson());
			holder.training.setText(getAddedData.get(position).getTraining());

			holder.salesperson.setId(position);
			holder.training.setId(position);

			return convertView;
		}

		private class ViewHolder {

			TextView salesperson, training, contactid;

		}
	}
	
	

}
