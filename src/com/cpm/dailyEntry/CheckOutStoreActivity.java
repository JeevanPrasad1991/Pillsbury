package com.cpm.dailyEntry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.Calendar;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cpm.Constants.CommonString;
import com.cpm.database.pillsbury_Database;
import com.cpm.message.AlertMessage;
import com.cpm.pillsbury.R;

public class CheckOutStoreActivity extends Activity implements LocationListener {

	private Dialog dialog;
	private ProgressBar pb;
	private TextView percentage, message;
	private String username, visit_date, store_id;
	private Data data;
	int eventType;
	private pillsbury_Database db;
	private SharedPreferences preferences = null;
	static int counter = 1;
	public static String currLatitude = "0.0";
	public static String currLongitude = "0.0";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.storename);

		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		username = preferences.getString(CommonString.KEY_USERNAME, "");
		visit_date = preferences.getString(CommonString.KEY_VISIT_DATE, null);
		store_id = preferences.getString(CommonString.KEY_STORE_ID, "");
		preferences
				.getString(CommonString.KEY_STORE_IN_TIME, "");
		currLatitude = preferences.getString(CommonString.KEY_LATITUDE, "0.0");
		currLongitude = preferences
				.getString(CommonString.KEY_LONGITUDE, "0.0");

		db = new pillsbury_Database(this);
		db.open();

		new BackgroundTask(this).execute();
	}

	@Override
	protected void onResume() {
		
		super.onResume();
		db.open();

	}

	@Override
	protected void onStop() {
		
		super.onStop();
		db.close();
	}

	private class BackgroundTask extends AsyncTask<Void, Data, String> {
		private Context context;

		BackgroundTask(Context context) {
			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			
			super.onPreExecute();

			dialog = new Dialog(context);
			dialog.setContentView(R.layout.custom);
			dialog.setTitle("Uploading Checkout Data");
			dialog.setCancelable(false);
			dialog.show();
			pb = (ProgressBar) dialog.findViewById(R.id.progressBar1);
			percentage = (TextView) dialog.findViewById(R.id.percentage);
			message = (TextView) dialog.findViewById(R.id.message);

		}

		@Override
		protected String doInBackground(Void... params) {
			

			try {

				String result = "";
				data = new Data();

				data.value = 20;
				data.name = "Checked out Data Uploading";
				publishProgress(data);

				HttpParams myParams = new BasicHttpParams();
				HttpConnectionParams.setConnectionTimeout(myParams, 10000);
				HttpConnectionParams.setSoTimeout(myParams, 10000);
				HttpClient httpclient = new DefaultHttpClient();
				InputStream inputStream = null;

				JSONObject checkout = new JSONObject();

				checkout.put("USER_ID", username);
				checkout.put("LATITUDE", currLatitude);
				checkout.put("LOGITUDE", currLongitude);
				checkout.put("CHECKOUT_DATE", visit_date);
				checkout.put("CHECK_OUTTIME", getCurrentTime());
//				checkout.put("CHECK_INTIME", store_intime);
				checkout.put("CREATED_BY", username);
				checkout.put("STORE_ID", store_id);

				String finalcheckout = checkout.toString();
				finalcheckout = makeJson(finalcheckout);

				HttpPost httppost = new HttpPost(CommonString.URL
						+ CommonString.METHOD_Checkout_StatusNew);
				httppost.setHeader("Accept", "application/json");
				httppost.setHeader("Content-type", "application/json");

				StringEntity se = new StringEntity(finalcheckout);

				httppost.setEntity(se);

				HttpResponse response = httpclient.execute(httppost);

				inputStream = response.getEntity().getContent();

				if (inputStream != null) {

					result = convertInputStreamToString(inputStream);
					result = result.replace("\"", "");
				}

				if (result.toString()
						.equalsIgnoreCase(CommonString.KEY_NO_DATA)) {
					return CommonString.METHOD_Checkout_StatusNew;
				}
				
				if (result.toString()
						.equalsIgnoreCase(CommonString.KEY_FAILURE)) {
					return CommonString.METHOD_Checkout_StatusNew;
				}
				if (result.toString()
						.equalsIgnoreCase(CommonString.KEY_FALSE)) {
					return CommonString.METHOD_Checkout_StatusNew;
				}

				// for failure
				data.value = 100;
				data.name = "Checkout Done";
				publishProgress(data);

				if (result.toString()
						.equalsIgnoreCase(CommonString.KEY_SUCCESS)) {

					SharedPreferences.Editor editor = preferences.edit();
					editor.putString(CommonString.KEY_STOREVISITED, "");
					editor.putString(CommonString.KEY_STOREVISITED_STATUS, "");
					editor.putString(CommonString.KEY_STORE_IN_TIME, "");
					editor.putString(CommonString.KEY_LATITUDE, "");
					editor.putString(CommonString.KEY_LONGITUDE, "");
					editor.commit();

					db.updateStoreStatusOnCheckout(store_id, visit_date,
							CommonString.KEY_C);
					db.update_CoverageTable(getCurrentTime(),store_id);

				} else {
					if (result.toString().equalsIgnoreCase(
							CommonString.KEY_FALSE)) {
						return CommonString.METHOD_Checkout_StatusNew;
					}

			// for failure

				}
				return CommonString.KEY_SUCCESS;

			} catch (MalformedURLException e) {

				final AlertMessage message = new AlertMessage(
						CheckOutStoreActivity.this,
						AlertMessage.MESSAGE_EXCEPTION, "download", e);
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						message.showMessage();
					}
				});

			} catch (IOException e) {
				final AlertMessage message = new AlertMessage(
						CheckOutStoreActivity.this,
						AlertMessage.MESSAGE_SOCKETEXCEPTION,
						"socket", e);
				// counter++;
				runOnUiThread(new Runnable() {

					@Override
					public void run() {

						message.showMessage();
						/*
						 * if (counter < 10) { new
						 * BackgroundTask(CheckOutUploadActivity
						 * .this).execute(); } else { message.showMessage();
						 * counter =1; }
						 */
					}
				});
			} catch (Exception e) {
				final AlertMessage message = new AlertMessage(
						CheckOutStoreActivity.this,
						AlertMessage.MESSAGE_EXCEPTION, "download", e);
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						message.showMessage();
					}
				});
			}

			return "";
		}

		@Override
		protected void onProgressUpdate(Data... values) {
			pb.setProgress(values[0].value);
			percentage.setText(values[0].value + "%");
			message.setText(values[0].name);

		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			dialog.dismiss();

			if (result.equals(CommonString.KEY_SUCCESS)) {

				AlertMessage message = new AlertMessage(
						CheckOutStoreActivity.this,
						"Successfully Checked out", "checkout", null);
				message.showMessage();
			} else if (!result.equals("")) {
				AlertMessage message = new AlertMessage(
						CheckOutStoreActivity.this, CommonString.ERROR + result, "success", null);
				message.showMessage();
			}

		}

	}

	class Data {
		int value;
		String name;
	}

	public String getCurrentTime() {

		Calendar m_cal = Calendar.getInstance();
		int hour = m_cal.get(Calendar.HOUR_OF_DAY);
		int min = m_cal.get(Calendar.MINUTE);

		String intime = "";

		if (hour == 0) {
			intime = "" + 12 + ":" + min + " AM";
		} else if (hour == 12) {
			intime = "" + 12 + ":" + min + " PM";
		} else {
			if (hour > 12) {
				hour = hour - 12;
				intime = "" + hour + ":" + min + " PM";
			} else {
				intime = "" + hour + ":" + min + " AM";
			}
		}
		return intime;

	}

	@Override
	public void onLocationChanged(Location location) {
		location.getLatitude();
		location.getLongitude();
	}

	@Override
	public void onProviderDisabled(String arg0) {
		

	}

	@Override
	public void onProviderEnabled(String arg0) {
		

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		

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
}
