package com.cpm.dailyEntry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.cpm.Constants.CommonString;
import com.cpm.database.pillsbury_Database;
import com.cpm.delegates.CallsBean;
import com.cpm.delegates.CoverageBean;
import com.cpm.delegates.GeotaggingBeans;
import com.cpm.delegates.StoreBean;
import com.cpm.message.AlertMessage;
import com.cpm.pillsbury.R;

public class CallsTracker extends Activity implements OnClickListener{
	
	static final int TIME_DIALOG_ID1 = 999;
	
	private Dialog dialog;
	private ProgressBar pb;
	private TextView percentage, message;
	
	JSONObject jo1 = new JSONObject();
	
	String jo = "";
	private int factor, k;
	String datacheck = "";
	String[] words;
	String validity, storename;
	int mid;
	String sod = "";
	String total_sku = "";
	String sku = "";
	String sos_data = "";
	String category_data = "";
	String result;
	String errormsg = "";
	int count;
	int eventType;

	static final int TIME_DIALOG_ID2 = 998;
	
	
	
	Button save, upload;
	EditText totalCalls, productiveCalls, posSale, totalcals12to3 , productivecals12to3, totalcalss3to6, productivecalss3to6;
	pillsbury_Database database;
	public String totalCall_String12to3, producCalls_String12to3,totalCall_String3to6, producCalls_String3to6, posSales_string;
	private SharedPreferences preferences;
	private String store_id, username, intime, visit_date;
	
	private ArrayList<CoverageBean> coverageBeanlist = new ArrayList<CoverageBean>();
	ArrayList<GeotaggingBeans> geotaglist = new ArrayList<GeotaggingBeans>();
	private ArrayList<CallsBean> callDataInstant = new ArrayList<CallsBean>();
	static int counter = 1;
	String version;
	StoreBean storestatus = new StoreBean();
	boolean upload_status;

	
	public void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calls);
//		totalCalls = (EditText)findViewById(R.id.total_callss);
//		productiveCalls = (EditText)findViewById(R.id.productive_callss);
		totalcals12to3 = (EditText)findViewById(R.id.total_calls123);
		totalcalss3to6 = (EditText)findViewById(R.id.total_calls36);
		
		productivecals12to3 = (EditText)findViewById(R.id.productive_calls123);
		productivecalss3to6 = (EditText)findViewById(R.id.productive_calls36);
		posSale = (EditText)findViewById(R.id.pos_Sale);
		save = (Button)findViewById(R.id.save_btn);
		upload = (Button)findViewById(R.id.upload_btn);
		
		database = new pillsbury_Database(getApplicationContext());
		database.open();
		ArrayList<CallsBean>calList = new ArrayList<CallsBean>();
		Boolean update = false;
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		visit_date = preferences.getString(CommonString.KEY_VISIT_DATE, null);
		preferences.getString(CommonString.KEY_STORE_NAME, "");
		username = preferences.getString(CommonString.KEY_USERNAME, "");
		store_id = preferences.getString(CommonString.KEY_STORE_ID, "");
		intime = preferences.getString(CommonString.KEY_STORE_IN_TIME, "");
		
		System.out.println("The store id is:"+store_id);
		
//		staticDate();
		//database.deleteAllTables();
		
		callDataInstant = database.getCallDataInstant(store_id);
		if(callDataInstant.size()>0)
		{
			upload.setEnabled(false);
		}
		
		save.setOnClickListener(this);
		upload.setOnClickListener(this);
		
		
		database = new pillsbury_Database(this);
		database.open();
		
		calList = database.getCallTrackerData(store_id);
		
		if(calList.size()>0){
			update = true;
			save.setText("update");
			totalcalss3to6.setText(calList.get(0).getTotalCalls());
			productivecalss3to6.setText(calList.get(0).getProductiveCalls());
			posSale.setText(calList.get(0).getPosSale());
		}
//		else{
//			totalCalls.setTe
//		}
	}
	
	@Override
	protected void onResume() {
		
		super.onResume();
		if(callDataInstant.size()>0)
		{
			upload.setEnabled(false);
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
	
	boolean validate() {
		boolean flag = true;
		
		totalCall_String3to6   =   totalcalss3to6.getText().toString();
		producCalls_String3to6 = productivecalss3to6.getText().toString();
		
		int total, productive;
		
		if (!totalCall_String3to6.equalsIgnoreCase("")) {
			total = Integer.parseInt(totalCall_String3to6);
		} else {
			total =0;
		}
		
		if (!producCalls_String3to6.equalsIgnoreCase("")) {
			productive = Integer.parseInt(producCalls_String3to6);
		} else {
			productive=0;
		}
		if (total < productive) {
			return false;
		}
		return flag;
		
	}

	boolean validateSave(){
		boolean flag=true;
		
		totalCall_String12to3 = totalcals12to3.getText().toString();
		producCalls_String12to3 = productivecals12to3.getText().toString();
		
		int total, productive;
		
		
		if (!totalCall_String12to3.equalsIgnoreCase("")) {
			total = Integer.parseInt(totalCall_String12to3);
		} else {
			total =0;
		}
		
		if (!producCalls_String12to3.equalsIgnoreCase("")) {
			productive = Integer.parseInt(producCalls_String12to3);			
		} else {
			productive =0;

		}
		
		if (total<productive) {
			return false;
		}
		
		return flag;
		
	}

	@Override
	public void onClick(View v) {
		
		if (v.getId() == R.id.save_btn) {
			
			if (validate()) {

					AlertDialog.Builder builder = new AlertDialog.Builder(this);
					builder.setMessage("Are you sure you want to save")
							.setCancelable(false)
							.setPositiveButton("Yes",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {																					
											posSales_string = posSale.getText().toString();
											
											database.open();
											database.deleteCallTracker(store_id);
											database.InsertCallsTrackerData(getMid(),store_id, totalCall_String3to6, producCalls_String3to6, posSales_string);
											Toast.makeText(getApplicationContext(), "Data has been saved!", Toast.LENGTH_LONG).show();
											
											Intent in = new Intent(CallsTracker.this, DailyEntryMenu.class);
											startActivity(in);
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
			}
			
			else{
				Toast.makeText(getApplicationContext(), "Productive calls must be less than total Calls", Toast.LENGTH_LONG);
			}
		}
		
		if (v.getId() == R.id.upload_btn) {
			
			if (validateSave()) {
				
			
			
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Are you sure you want to upload the data")
					.setCancelable(false)
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(
										DialogInterface dialog, int id) {
									
									totalCall_String12to3 = totalcals12to3.getText().toString();
									producCalls_String12to3 = productivecals12to3.getText().toString();
									
									database.open();
									database.deleteCallTracker(store_id);
									database.InsertCallsBetween12To3Data(getMid(), totalCall_String12to3, producCalls_String12to3,getCurrentTime(),store_id);
									dialog.cancel();
						         	new UploadTask(getApplicationContext()).execute();
									
									Toast.makeText(getApplicationContext(), "Data has been uploaded Successfully", 1).show();
									upload.setEnabled(false);
									//Intent in = new Intent(CallsTracker.this, DailyEntryMenu.class);
									//startActivity(in);
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
			}
			 else {
				 Toast.makeText(getApplicationContext(), "Productive calls must be less than Total calls", Toast.LENGTH_LONG).show();
				  }
			
		}
		
		
	}

	
	private class UploadTask extends AsyncTask<Void, Void, String> {
		private Context context;

		UploadTask(Context context) {
			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			dialog = new Dialog(context);
			dialog.setContentView(R.layout.custom);
			dialog.setTitle("Uploading Data");
			dialog.setCancelable(false);
			//dialog.show();
			pb = (ProgressBar) dialog.findViewById(R.id.progressBar1);
			percentage = (TextView) dialog.findViewById(R.id.percentage);
			message = (TextView) dialog.findViewById(R.id.message);
		}

		@Override
		protected String doInBackground(Void... params) {

			try {

				HttpParams myParams = new BasicHttpParams();
				HttpConnectionParams.setConnectionTimeout(myParams, 10000);
				HttpConnectionParams.setSoTimeout(myParams, 10000);
				HttpClient httpclient = new DefaultHttpClient();
				InputStream inputStream = null;

				if (upload_status == false)

				{
					coverageBeanlist = database.getCoverageData(visit_date,
							null);

				} else {
					coverageBeanlist = database.getCoverageData(null, null);

				}
				if (coverageBeanlist.size() > 0) {

					if (coverageBeanlist.size() == 1) {
						factor = 50;
					} else {

						factor = 100 / (coverageBeanlist.size());
					}
				}

				for (int i = 0; i < coverageBeanlist.size(); i++) {

					storestatus = database.getStoreStatus(coverageBeanlist.get(
							i).getStoreId());

					if (upload_status) {
						storestatus.setCheckout_status("C");
					}
					
					
					
					runOnUiThread(new Runnable() {

						public void run() {
							k = k + factor;
							pb.setProgress(k);
							percentage.setText(k + "%");
							message.setText("Uploading Data");
						}
					});
					
					storestatus.setCheckout_status("invalid");

					if ((storestatus.getCheckout_status().equalsIgnoreCase(

					CommonString.KEY_L) || storestatus.getCheckout_status()
							.equalsIgnoreCase(

							"invalid"))) {

						// if (true) {

						if (!coverageBeanlist.get(i).getStatus()
								.equalsIgnoreCase(CommonString.KEY_D)) {
							// if (true) {
							JSONObject coverageDat = new JSONObject();
							coverageDat.put("STORE_CD", coverageBeanlist.get(i)
									.getStoreId());
//							coverageDat.put("USERNAME", username);
							coverageDat.put("VISITDATE",
									coverageBeanlist.get(i).getVisitDate());
							coverageDat.put("INTIME",coverageBeanlist.get(i)
									.getOutTime());
							coverageDat.put("OUTTIME", coverageBeanlist.get(i)
									.getOutTime());
							coverageDat.put("LATITUDE", coverageBeanlist.get(i)
									.getLatitude());
							coverageDat.put("LONGITUDE", coverageBeanlist
									.get(i).getLongitude());
							coverageDat.put("REASON_CD", coverageBeanlist
									.get(i).getReasonid());
							coverageDat.put("REASON_REMARK", coverageBeanlist
									.get(i).getRemark());
							coverageDat.put("IMAGE_URL", coverageBeanlist
									.get(i).getImage());
//							coverageDat.put("CREATED_BY", username);
							coverageDat.put("STATUS", coverageBeanlist
									.get(i).getStatus());
							coverageDat.put("RIGHTS", coverageBeanlist
								.get(i).getRights());
							
							
							coverageDat.put("USER_ID", username);

							String finalcoverage = makeJson(coverageDat
									.toString());

							HttpPost httppost = new HttpPost(
									CommonString.URL
											+ CommonString.METHOD_UPLOAD_DR_STORE_COVERAGE);
							httppost.setHeader("Accept", "application/json");
							httppost.setHeader("Content-type",
									"application/json");

							StringEntity se = new StringEntity(finalcoverage);

							httppost.setEntity(se);

							HttpResponse response = httpclient
									.execute(httppost);

							inputStream = response.getEntity().getContent();

							if (inputStream != null) {
								result = convertInputStreamToString(inputStream);
								result = result.replace("\"", "");
							}

							if (result.toString().equalsIgnoreCase(
									CommonString.KEY_NO_DATA)) {
								return CommonString.METHOD_UPLOAD_DR_STORE_COVERAGE;
							}

							if (result.toString().equalsIgnoreCase(
									CommonString.KEY_FAILURE)) {
								return CommonString.METHOD_UPLOAD_DR_STORE_COVERAGE;
							}

							datacheck = result.toString();
							datacheck = datacheck.replace("\"", "");
							words = datacheck.split("\\;");
							validity = (words[0]);

							if (validity
									.equalsIgnoreCase(CommonString.KEY_SUCCESS)) {/*
							//	database.updateCoverageStatus(coverageBeanlist
									//	.get(i).getMID(), CommonString.KEY_D);

								database.updateStoreStatusOnLeave(
										coverageBeanlist.get(i).getStoreId(),
										coverageBeanlist.get(i).getVisitDate(),
										CommonString.KEY_D);
						*/	} else {
								if (result.toString().equalsIgnoreCase(
										CommonString.KEY_FALSE)) {
									return CommonString.METHOD_UPLOAD_DR_STORE_COVERAGE;
								}
								if (result.toString().equalsIgnoreCase(
										CommonString.KEY_FAILURE)) {
									return CommonString.METHOD_UPLOAD_DR_STORE_COVERAGE;
								}

							}

							mid = Integer.parseInt((words[1]));

							if (!(coverageBeanlist.get(i).getReasonid()
									.equalsIgnoreCase("") || coverageBeanlist
									.get(i).getReasonid().equalsIgnoreCase("0"))) {

								System.out.println("");
							} else {

								
								
			
								
									
							
								//call data upload 12 to 3
								
								callDataInstant = database.getCallDataInstant(coverageBeanlist.get(i).getStoreId());
								
								for (int j = 0; j < callDataInstant.size(); j++) {
									
									if (callDataInstant.get(j).getTotalCalls().equalsIgnoreCase("")) {
										callDataInstant.get(j).setTotalCalls("0");
										
									}
									
									
									if (callDataInstant.get(j).getProductiveCalls().equalsIgnoreCase("")) {
										callDataInstant.get(j).setProductiveCalls("0");
										
									}
									

									JSONObject callData = new JSONObject();
									callData.put("MID", mid);
									callData.put("TOTAL_CALLS",
											callDataInstant.get(j).getTotalCalls());
									
									callData.put("PRODUCTIVE_CALLS", callDataInstant
											.get(j).getProductiveCalls());
									
									callData.put("USER_ID", username);

									String finalCalls = makeJson(callData
											.toString());

									httppost = new HttpPost(
											CommonString.URL
													+ CommonString.METHOD_UPLOAD_CALLS_DATA_INSTANT);
									httppost.setHeader("Accept",
											"application/json");
									httppost.setHeader("Content-type",
											"application/json");

									se = new StringEntity(finalCalls);

									httppost.setEntity(se);

									response = httpclient.execute(httppost);

									inputStream = response.getEntity()
											.getContent();

									if (inputStream != null) {
										result = convertInputStreamToString(inputStream);
										result = result.replace("\"", "");
									}

									if (result.toString().equalsIgnoreCase(
											CommonString.KEY_NO_DATA)) {
										return CommonString.METHOD_UPLOAD_CALLS_DATA_INSTANT;
									}
									if (result.toString().equalsIgnoreCase(
											CommonString.KEY_FAILURE)) {
										return CommonString.METHOD_UPLOAD_CALLS_DATA_INSTANT;
									}

								}
								
								

							}

						}

					}

				

				

					
				
					
					
				}


			}

			catch (MalformedURLException e) {

				final AlertMessage message = new AlertMessage(
						CallsTracker.this,
						AlertMessage.MESSAGE_EXCEPTION, "download", e);
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						message.showMessage();
					}
				});

			} catch (IOException e) {
				final AlertMessage message = new AlertMessage(
						CallsTracker.this,
						AlertMessage.MESSAGE_SOCKETEXCEPTION, "socket", e);

				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub

						message.showMessage();

					}
				});
			}

			catch (Exception e) {
				final AlertMessage message = new AlertMessage(
						CallsTracker.this,
						AlertMessage.MESSAGE_EXCEPTION, "download", e);

				e.getMessage();
				e.printStackTrace();
				e.getCause();
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						message.showMessage();
					}
				});
			}
			return CommonString.KEY_SUCCESS;

		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			dialog.dismiss();

			if (result.equals(CommonString.KEY_SUCCESS)) {

			/*	if (upload_status == true) {

					Intent intent = new Intent(getBaseContext(),
							UploadImageActivity.class);
					intent.putExtra("UploadAll", true);
					startActivity(intent);
				}

				else {*/
					//AlertMessage message = new AlertMessage(
						//	CallsTracker.this,
							//AlertMessage.MESSAGE_UPLOAD_DATA, "success", null);
					
				//	database.deleteAllTables();
				//	database.close();
					//message.showMessage();
				
			} else if (!result.equals("")) {
			//	AlertMessage message = new AlertMessage(
					//	CallsTracker.this, CommonString.ERROR + result, "success", null);
				//message.showMessage();
			}

		}
	}	
	

	String makeJson(String json) {
		json = json.replace("\\", "");
		json = json.replace("\"[", "[");
		json = json.replace("]\"", "]");

		return json;
	}
	
	private static String convertInputStreamToString(InputStream inputStream)
			throws IOException {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while ((line = bufferedReader.readLine()) != null)
			result += line;

		inputStream.close();
		return result;

	}
	
	
	private void  staticDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
         sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

         String inputString = "16:00:00.000";

         Date date = null;
               try {
                     try {
						date = sdf.parse(inputString);
					} catch (java.text.ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                       System.out.println("in milliseconds: " + date.getTime());
//                       Toast.makeText(getApplicationContext(), "time in mills first"+date.getTime(), 100).show();
                       staticDate1(date.getTime());
               } catch (ParseException e) {
                     e.printStackTrace();
               }             
 }

private void staticDate1(long l) {
       
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
         sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

         String inputString = "22:00:00.000";

         Date date = null;
               try {
                     try {
						date = sdf.parse(inputString);
					} catch (java.text.ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                       System.out.println("in milliseconds: " + date.getTime());
//                      Toast.makeText(getApplicationContext(), "time in mills last"+date.getTime(), 100).show();
                       checkTime(l,date.getTime());
               } catch (ParseException e) {
                     
                     e.printStackTrace();
               }             
 }

private void checkTime(long l, long m) {

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
         sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

         String inputString = getCurrentTime();

         Date date = null;
               try {
                     try {
						date = sdf.parse(inputString+".000");
					} catch (java.text.ParseException e) {
						
						e.printStackTrace();
					}
                       System.out.println("in milliseconds: " + date.getTime());
                            if(date.getTime()>=l&&date.getTime()<=m){
//                                   save.setEnabled(true);
                            }
//                       Toast.makeText(getApplicationContext(), "current time in mills"+date.getTime(), 100).show();
               } catch (ParseException e) {
                    
                     e.printStackTrace();
               }
       
 }

	
	
}
