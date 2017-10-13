package com.cpm.upload;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import JsonHandler.JsonHandler;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cpm.Constants.CommonString;
import com.cpm.database.pillsbury_Database;
import com.cpm.delegates.CoverageBean;
import com.cpm.delegates.GeotaggingBeans;
import com.cpm.delegates.PepsicoEntry;
import com.cpm.delegates.Posmbean;
import com.cpm.delegates.StoreBean;
import com.cpm.message.AlertMessage;
import com.cpm.pillsbury.MainMenuActivity;
import com.cpm.pillsbury.R;
import com.cpm.xmlGetterSetter.FailureGetterSetter;
import com.cpm.xmlHandler.XMLHandlers;

public class UploadImageActivity extends Activity {

	private Dialog dialog;
	private ProgressBar pb;
	private TextView percentage, message;
	private String visit_date;
	private SharedPreferences preferences;
	private pillsbury_Database database;
	private int factor, k;
	private FailureGetterSetter failureGetterSetter = null;

	String result, username;
	String datacheck = "";
	String[] words;
	String validity, storename;
	String mid = "";
	int eventType;
	String errormsg = "";
	static int counter = 1;
	private ArrayList<CoverageBean> coverageBeanlist = new ArrayList<CoverageBean>();
	private ArrayList<Posmbean> PosmData = new ArrayList<Posmbean>();
	ArrayList<GeotaggingBeans> geotaglist = new ArrayList<GeotaggingBeans>();
	private ArrayList<PepsicoEntry> PepsicoData = new ArrayList<PepsicoEntry>();

	boolean upload_status;

	StoreBean storestatus = new StoreBean();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.upload_option);

		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		visit_date = preferences.getString(CommonString.KEY_DATE, null);

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		username = preferences.getString(CommonString.KEY_USERNAME, null);

		database = new pillsbury_Database(this);
		database.open();

		Intent i = getIntent();
		upload_status = i.getBooleanExtra("UploadAll", false);

		new UploadTask(this).execute();
	}

	@Override
	public void onBackPressed() {
		
		Intent i = new Intent(this, MainMenuActivity.class);
		startActivity(i);
		UploadImageActivity.this.finish();
	}

	@Override
	protected void onStop() {
		
		super.onStop();
		
	}

	private class UploadTask extends AsyncTask<Void, Void, String> {
		private Context context;

		UploadTask(Context context) {
			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			dialog = new Dialog(context);
			dialog.setContentView(R.layout.custom);
			dialog.setTitle("Uploading Image");
			dialog.setCancelable(false);
			dialog.show();
			pb = (ProgressBar) dialog.findViewById(R.id.progressBar1);
			percentage = (TextView) dialog.findViewById(R.id.percentage);
			message = (TextView) dialog.findViewById(R.id.message);
		}

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub

			try {

				if (upload_status == false)

				{

					coverageBeanlist = database.getCoverageData(visit_date,
							null);

				}

				else

				{
					coverageBeanlist = database.getCoverageData(null, null);

				}

				HttpParams myParams = new BasicHttpParams();
				HttpConnectionParams.setConnectionTimeout(myParams, 10000);
				HttpConnectionParams.setSoTimeout(myParams, 10000);
				HttpClient httpclient = new DefaultHttpClient();
				InputStream inputStream = null;

				if (coverageBeanlist.size() > 0) {

					if (coverageBeanlist.size() == 1) {
						factor = 50;
					} else {

						factor = 100 / (coverageBeanlist.size());
					}
				}

				for (int i = 0; i < coverageBeanlist.size(); i++) {

					if (coverageBeanlist.get(i).getStatus()
							.equalsIgnoreCase("D")) {

						// doing testing with status "N" here

						database.open();
						storestatus = database.getStoreStatus(coverageBeanlist
								.get(i).getStoreId());

						storename = database.getStoreName(coverageBeanlist.get(
								i).getStoreId());

						runOnUiThread(new Runnable() {

							public void run() {
								// TODO Auto-generated method stub
								k = k + factor;
								pb.setProgress(k);
								percentage.setText(k + "%");
								message.setText(storename + " Images");
							}
						});

						if (!(coverageBeanlist.get(i).getReasonid()
								.equalsIgnoreCase("") || coverageBeanlist
								.get(i).getReasonid().equalsIgnoreCase("0"))) {

							if (coverageBeanlist.get(i).getImage() != null
									&& !coverageBeanlist.get(i).getImage()
											.equals("")) {

								if (new File("/mnt/sdcard/westernDigital_Images/"
												+ coverageBeanlist.get(i)
														.getImage()).exists()) {
									result = UploadSODImage(coverageBeanlist
											.get(i).getImage());

									if (result.toString().equalsIgnoreCase(
											CommonString.KEY_FALSE)) {

										return CommonString.METHOD_UPLOAD_IMAGE;
									} else if (result
											.equalsIgnoreCase(CommonString.KEY_FAILURE)) {

										return CommonString.METHOD_UPLOAD_IMAGE
												+ "," + errormsg;
									}

									runOnUiThread(new Runnable() {

										public void run() {
											// TODO Auto-generated method
											// stub

											message.setText("Reason Image Upload");
										}
									});
								}
							}
						} else {
							// sod images Data

							database.open();
							PosmData = database
									.getPosmDataInserted(coverageBeanlist
											.get(i).getMID());
							for (int k = 0; k < PosmData.size(); k++) {

								if (PosmData.size() > 0) {
									// *** Uploading images

									if (PosmData.get(k).getImage() != null
											&& !PosmData.get(k).getImage().equals("")) {

										if (new File("/mnt/sdcard/westernDigital_Images/" + PosmData.get(k).getImage()).exists()) {

											result = UploadSODImage(PosmData
													.get(k).getImage());

											if (result
													.toString()
													.equalsIgnoreCase(
															CommonString.KEY_FALSE)) {

												return CommonString.METHOD_UPLOAD_IMAGE;
											} else if (result
													.equalsIgnoreCase(CommonString.KEY_FAILURE)) {

												return CommonString.METHOD_UPLOAD_IMAGE
														+ "," + errormsg;
											}

											runOnUiThread(new Runnable() {

												public void run() {
													// TODO Auto-generated
													// method
													// stub

													message.setText("SOD Image2 Upload");
												}
											});
										}
									}

								}

							}
						}

						database.open();
						database.deleteStoreMidData(coverageBeanlist.get(i)
								.getMID(), coverageBeanlist.get(i).getStoreId());
						database.updateStoreStatus(coverageBeanlist.get(i)
								.getStoreId(), coverageBeanlist.get(i)
								.getVisitDate());

						// SET COVERAGE STATUS

						JSONObject StatusXml = new JSONObject();

						StatusXml.put("STORE_ID", coverageBeanlist.get(i)
								.getStoreId());
						StatusXml.put("USER_ID", username);
						StatusXml.put("VISIT_DATE", coverageBeanlist.get(i)
								.getVisitDate());
						StatusXml.put("STATUS", CommonString.KEY_U);

						String finalData;

						finalData = StatusXml.toString();
						finalData = makeJson(finalData);

						HttpPost httppost = new HttpPost(CommonString.URL
								+ CommonString.MEHTOD_UPLOAD_COVERAGE_STATUS);
						httppost.setHeader("Accept", "application/json");
						httppost.setHeader("Content-type", "application/json");

						StringEntity se = new StringEntity(finalData);

						httppost.setEntity(se);

						HttpResponse response = httpclient.execute(httppost);

						inputStream = response.getEntity().getContent();

						if (inputStream != null) {
							result = convertInputStreamToString(inputStream);
						}

						if (result.toString().equalsIgnoreCase(
								CommonString.KEY_NO_DATA)) {
							return CommonString.MEHTOD_UPLOAD_COVERAGE_STATUS;
						}

						if (result.toString().equalsIgnoreCase(
								CommonString.KEY_FAILURE)) {
							return CommonString.MEHTOD_UPLOAD_COVERAGE_STATUS;
						}
						// for failure

					}
				}

				// **

				// sod images Data

				database.open();

				geotaglist = database.getGeotaggingData(CommonString.KEY_D);

				for (int i = 0; i < geotaglist.size(); i++) {

					runOnUiThread(new Runnable() {

						public void run() {
							// TODO Auto-generated method stub
							k = k + factor;
							pb.setProgress(k);
							percentage.setText(k + "%");
							message.setText("Uploading Geotag Images...");
						}
					});

					if (geotaglist.get(i).getUrl1() != null
							&& !geotaglist.get(i).getUrl1()
									.equalsIgnoreCase("")) {

						if (new File("/mnt/sdcard/westernDigital_Images/"
								+ geotaglist.get(i).getUrl1()).exists()) {
							result = UploadSODImage(geotaglist.get(i).getUrl1());

							if (result.toString().equalsIgnoreCase(
									CommonString.KEY_FALSE)) {

								return CommonString.METHOD_UPLOAD_IMAGE;

							} else if (result
									.equalsIgnoreCase(CommonString.KEY_FAILURE)) {

								return CommonString.METHOD_UPLOAD_IMAGE;
							}

							runOnUiThread(new Runnable() {

								public void run() {
									// TODO Auto-generated method stub

									message.setText("Image1 Upload");
								}
							});
						}
					}
					if (geotaglist.get(i).getUrl2() != null
							&& !geotaglist.get(i).getUrl2()
									.equalsIgnoreCase("")) {

						if (new File("/mnt/sdcard/westernDigital_Images/"
								+ geotaglist.get(i).getUrl2()).exists()) {

							result = UploadSODImage(geotaglist.get(i).getUrl2());

							if (result.toString().equalsIgnoreCase(
									CommonString.KEY_FALSE)) {

								return CommonString.METHOD_UPLOAD_IMAGE;
							} else if (result
									.equalsIgnoreCase(CommonString.KEY_FAILURE)) {

								return CommonString.METHOD_UPLOAD_IMAGE + ","
										+ errormsg;
							}

							runOnUiThread(new Runnable() {

								public void run() {
									// TODO Auto-generated method stub

									message.setText("Image2 Upload");
								}
							});
						}
					}
					if (geotaglist.get(i).getUrl3() != null && !geotaglist.get(i).getUrl3().equalsIgnoreCase("")) {

						if (new File("/mnt/sdcard/westernDigital_Images/"
								+ geotaglist.get(i).getUrl3()).exists()) {

							result = UploadSODImage(geotaglist.get(i).getUrl3());

							if (result.toString().equalsIgnoreCase(
									CommonString.KEY_FALSE)) {

								return CommonString.METHOD_UPLOAD_IMAGE;
							} else if (result.equalsIgnoreCase(CommonString.KEY_FAILURE)) {

								return CommonString.METHOD_UPLOAD_IMAGE + "," + errormsg;
							}

							runOnUiThread(new Runnable() {

								public void run() {
									// TODO Auto-generated method stub

									message.setText("Image3 Upload");
								}
							});
						}
					}
					database.open();
					database.updateGeoTagDataInMain(geotaglist.get(i).getStoreid());
					database.deleteGeoTagData(geotaglist.get(i).getStoreid());
				}

				return CommonString.KEY_SUCCESS;
			} catch (MalformedURLException e) {

				final AlertMessage message = new AlertMessage(
						UploadImageActivity.this,
						AlertMessage.MESSAGE_EXCEPTION, "download", e);
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						message.showMessage();
					}
				});

			} catch (IOException e) {
				final AlertMessage message = new AlertMessage(
						UploadImageActivity.this,
						AlertMessage.MESSAGE_SOCKETEXCEPTION,
						"socket", e);

				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						message.showMessage();

					}
				});
			}

			catch (Exception e) {
				final AlertMessage message = new AlertMessage(UploadImageActivity.this,
						AlertMessage.MESSAGE_EXCEPTION, "download", e);
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						message.showMessage();
					}
				});
			}

			return "";
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			dialog.dismiss();

			if (upload_status == true) {

				database.open();
				database.deleteAllTables();

			}

			if (result.equals(CommonString.KEY_SUCCESS)) {
				AlertMessage message = new AlertMessage(
						UploadImageActivity.this, AlertMessage.MESSAGE_UPLOAD_IMAGE, "success", null);
				message.showMessage();
			} else if (!result.equals("")) {
				AlertMessage message = new AlertMessage(
						UploadImageActivity.this, CommonString.ERROR + result, "success", null);
				message.showMessage();
			}
		}

		String makeJson(String json) {
			json = json.replace("\\", "");
			json = json.replace("\"[", "[");
			json = json.replace("]\"", "]");

			return json;
		}

		private String convertInputStreamToString(InputStream inputStream) throws IOException {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String line = "";
			String result = "";
			while ((line = bufferedReader.readLine()) != null)
				result += line;

			inputStream.close();
			return result;

		}

		public String UploadSODImage(String path) throws Exception {

			errormsg = "";
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeFile("/mnt/sdcard/westernDigital_Images/"
					+ path, o);

			// The new size we want to scale to
			final int REQUIRED_SIZE = 1024;

			// Find the correct scale value. It should be the power of 2.
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;

			while (true) {
				if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale *= 2;
			}

			// Decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			Bitmap bitmap = BitmapFactory.decodeFile(
					"/mnt/sdcard/westernDigital_Images/" + path, o2);

			ByteArrayOutputStream bao = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bao);
			byte[] ba = bao.toByteArray();
			// below we have string value of img

			String ba1 = Base64.encodeBytes(ba);
			JSONObject jsonObject = new JSONObject();

			try {

				jsonObject.accumulate("img", ba1);

				jsonObject.accumulate("name", path);

				HttpParams myParams = new BasicHttpParams();
				HttpConnectionParams.setConnectionTimeout(myParams, 10000);
				HttpConnectionParams.setSoTimeout(myParams, 10000);
				HttpClient httpclient = new DefaultHttpClient();
				String json = jsonObject.toString();

				InputStream inputStream = null;
				HttpPost httppost = new HttpPost(CommonString.URL
						+ CommonString.METHOD_UPLOAD_IMAGE);
				httppost.setHeader("Accept", "application/json");
				httppost.setHeader("Content-type", "application/json");

				// posting data to server with parameter

				// ****************************************************

				StringEntity se = new StringEntity(json);

				httppost.setEntity(se);

				HttpResponse response = httpclient.execute(httppost);

				inputStream = response.getEntity().getContent();

				if (inputStream != null) {
					result = convertInputStreamToString(inputStream);
					result = result.replace("\"", "");
				}
				if (result.equalsIgnoreCase(CommonString.KEY_FAILURE)) {

					return CommonString.METHOD_UPLOAD_IMAGE;
				}
				if (result.equalsIgnoreCase(CommonString.KEY_SUCCESS)) {

					new File("/mnt/sdcard/westernDigital_Images/" + path).delete();
				}
				

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}
	}
}
