package com.cpm.upload;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cpm.Constants.CommonString;
import com.cpm.database.pillsbury_Database;
import com.cpm.delegates.AssetTrackerBean;
import com.cpm.delegates.CallsBean;
import com.cpm.delegates.ClosingStockBean;
import com.cpm.delegates.CoverageBean;
import com.cpm.delegates.GeotaggingBeans;
import com.cpm.delegates.LunchBean;
import com.cpm.delegates.MidDayRecievedStockBean;
import com.cpm.delegates.OpeningStockBean;
import com.cpm.delegates.PromotionTrackingBean;
import com.cpm.delegates.StoreBean;
import com.cpm.message.AlertMessage;
import com.cpm.pillsbury.MainMenuActivity;
import com.cpm.pillsbury.R;

public class UploadDataActivity extends Activity {

	private Dialog dialog;
	private ProgressBar pb;
	private TextView percentage, message;
	private String visit_date, username;
	JSONObject jo1 = new JSONObject();
	private SharedPreferences preferences;
	private pillsbury_Database database;
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
	StoreBean storestatus = new StoreBean();


	private ArrayList<CoverageBean> coverageBeanlist = new ArrayList<CoverageBean>();
	ArrayList<GeotaggingBeans> geotaglist = new ArrayList<GeotaggingBeans>();
	private ArrayList<OpeningStockBean> openingData = new ArrayList<OpeningStockBean>();
	private ArrayList<ClosingStockBean> closingData = new ArrayList<ClosingStockBean>();
	private ArrayList<LunchBean> lunchData = new ArrayList<LunchBean>();
	private ArrayList<MidDayRecievedStockBean> StockData = new ArrayList<MidDayRecievedStockBean>();
	private ArrayList<CallsBean> callTrackerData = new ArrayList<CallsBean>();
	private ArrayList<PromotionTrackingBean> promotionData = new ArrayList<PromotionTrackingBean>();
	private ArrayList<AssetTrackerBean> AssetData = new ArrayList<AssetTrackerBean>();

	static int counter = 1;
	String version;
	boolean upload_status;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainpage);

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		visit_date = preferences.getString(CommonString.KEY_VISIT_DATE, null);
		username = preferences.getString(CommonString.KEY_USERNAME, null);

		try {
			version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			
			e.printStackTrace();
		}

		Intent i = getIntent();
		upload_status = i.getBooleanExtra("UploadAll", false);
		database = new pillsbury_Database(this);
		database.open();

		new UploadTask(this).execute();

	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onStop() {
		super.onStop();
		// database.close();
	}

	@Override
	public void onBackPressed() {
		Intent i = new Intent(this, MainMenuActivity.class);
		startActivity(i);
		UploadDataActivity.this.finish();
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
			dialog.show();
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
					

					if ((storestatus.getCheckout_status().equalsIgnoreCase(

					CommonString.KEY_L) || storestatus.getCheckout_status()
							.equalsIgnoreCase(

							CommonString.KEY_C))) {

						// if (true) {

						if (!coverageBeanlist.get(i).getStatus()
								.equalsIgnoreCase(CommonString.KEY_D)) {
							// if (true) {
							JSONObject coverageDat = new JSONObject();
							coverageDat.put("STORE_CD", coverageBeanlist.get(i)
									.getStoreId());
							coverageDat.put("VISITDATE",
									coverageBeanlist.get(i).getVisitDate());
							coverageDat.put("INTIME",coverageBeanlist.get(i)
									.getInTime());
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
									.equalsIgnoreCase(CommonString.KEY_SUCCESS)) {
								database.updateCoverageStatus(coverageBeanlist
										.get(i).getMID(), CommonString.KEY_D);

								database.updateStoreStatusOnLeave(
										coverageBeanlist.get(i).getStoreId(),
										coverageBeanlist.get(i).getVisitDate(),
										CommonString.KEY_D);
							} else {
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

								JSONArray collectiontoSend = new JSONArray();
								
								//opening data upload
								openingData = database.getOpeningDataUpload(coverageBeanlist.get(i).getStoreId());
								for (int j = 0; j < openingData.size(); j++) {

									String PriceSinagge = null,singagecorrected,pogadhe,pogcorre, shelf;

									if(openingData.get(j).getPrice_sign().equalsIgnoreCase("YES"))
									{
										PriceSinagge="True";
									}
									if (openingData.get(j).getPrice_sign().equalsIgnoreCase("NA")) {
									PriceSinagge= "NA";
									}
									
									if(openingData.get(j).getPrice_sign().equalsIgnoreCase("NO")){
										PriceSinagge="false";
									}

									if(openingData.get(j).getShelf().equalsIgnoreCase("YES"))
									{
										shelf="1";
									}
									
									else{
										shelf="0";
									}
									
									if(openingData.get(j).getSign_corrected().equalsIgnoreCase("YES"))
									{
										singagecorrected="true";
									}
									
									else{
										singagecorrected="false";
									}
									
									
									if(openingData.get(j).getPog_adherence().equalsIgnoreCase("YES"))
									{
										pogadhe="true";
									}
									
									else{
										pogadhe="false";
									}
									
									
									if(openingData.get(j).getPog_corrected().equalsIgnoreCase("YES"))
									{
										pogcorre="true";
									}
									
									else{
										pogcorre="false";
									}
									
									
									if(openingData.get(j).getShelf().equalsIgnoreCase(""))
									{
										openingData.get(j).setShelf("0");
									}
									
									
									if(openingData.get(j).getFacing().equalsIgnoreCase(""))
									{
										openingData.get(j).setFacing("0");
									}
									
									if(openingData.get(j).getOpeningStock().equalsIgnoreCase(""))
									{
										openingData.get(j).setOpeningStock("0");
									}
								
									
									if(openingData.get(j).getPricing().equalsIgnoreCase(""))
									{
										openingData.get(j).setPricing("0");
									}
									
									if(openingData.get(j).getExpriring_stock().equalsIgnoreCase(""))
									{
										openingData.get(j).setExpriring_stock("0");
									}
									
									String frombottomshelf="";
									
									if(openingData.get(j).getShelf_fm_bottom().equalsIgnoreCase("YES"))
									{
										frombottomshelf="1";
									}
									
									else{
										frombottomshelf="0";
									}
										
									JSONObject openingObj = new JSONObject();
									openingObj.put("MID", mid);
//									openingObj.put("CURRENT_SHELF", shelf);
									openingObj.put("FACING", openingData
											.get(j).getFacing());
									
									openingObj.put("OPENING_STOCK", openingData
											.get(j).getOpeningStock());
									openingObj.put("PRICING", openingData
											.get(j).getPricing());
									openingObj.put("PRICE_SINAGE_NEW",PriceSinagge);
									
									openingObj.put("SIGNAGE_CORRECTED",singagecorrected);
									
									openingObj.put("EXPRING_STOCK", openingData
											.get(j).getExpriring_stock());
									
									openingObj.put("SHELF_FROM_BOTTOM", frombottomshelf);
									
									openingObj.put("POG_CORRECTED", pogcorre);
									
									openingObj.put("POG_ADHERENCE", pogadhe);
									
									openingObj.put("SKU_CD", openingData
											.get(j).getSKU_CD());
									openingObj.put("USER_ID", username);

									String finalOpeningStock = makeJson(openingObj
											.toString());

									httppost = new HttpPost(
											CommonString.URL
													+ CommonString.METHOD_UPLOAD_OPENING_DATA);
									httppost.setHeader("Accept",
											"application/json");
									httppost.setHeader("Content-type",
											"application/json");

									se = new StringEntity(finalOpeningStock);

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
										return CommonString.METHOD_UPLOAD_OPENING_DATA;
									}

									if (result.toString().equalsIgnoreCase(
											CommonString.KEY_FAILURE)) {
										return CommonString.METHOD_UPLOAD_OPENING_DATA;
									}
									
								// My changes below for multiple row insertion
									collectiontoSend.put(finalOpeningStock);
									
								}
								
								
								//Call Data upload
								String test = collectiontoSend.toString();
								test = makeJson(test);

							    callTrackerData = database.getCallTrackerData(coverageBeanlist.get(i).getStoreId());

								for (int j = 0; j < callTrackerData.size(); j++) {
									
									if (callTrackerData.get(j).getTotalCalls().equalsIgnoreCase("")) {
										callTrackerData.get(j).setTotalCalls("0");
										
									}
									
									if (callTrackerData.get(j).getProductiveCalls().equalsIgnoreCase("")) {
										callTrackerData.get(j).setProductiveCalls("0");	
									}

									if (callTrackerData.get(j).getPosSale().equalsIgnoreCase("")) {
										callTrackerData.get(j).setPosSale("0");
										
									}

									JSONObject callData = new JSONObject();
									callData.put("MID", mid);
									callData.put("TOTAL_CALLS",callTrackerData.get(j).getTotalCalls());
									callData.put("PRODUCTIVE_CALLS", callTrackerData.get(j).getProductiveCalls());
									callData.put("POS_SALE", callTrackerData.get(j).getPosSale());
									callData.put("USER_ID", username);

									String finalCalls = makeJson(callData.toString());

									httppost = new HttpPost(
											CommonString.URL
													+ CommonString.METHOD_UPLOAD_CALLS_DATA);
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
										return CommonString.METHOD_UPLOAD_CALLS_DATA;
									}
									if (result.toString().equalsIgnoreCase(
											CommonString.KEY_FAILURE)) {
										return CommonString.METHOD_UPLOAD_CALLS_DATA;
									}

								}
								
								//call data upload 12 to 3
								
								database.getCallDataInstant(coverageBeanlist.get(i).getStoreId());
								for (int j = 0; j < callTrackerData.size(); j++) {}
								
								
								//promotion data upload
								promotionData = database.getPromotionDataUpload(coverageBeanlist.get(i).getStoreId());
								String promotion_avail;

								for (int j = 0; j < promotionData.size(); j++) {
									
									if(promotionData.get(j).getPromotion_avail().equalsIgnoreCase("YES"))
									{
										promotion_avail="true";
									}
									
									else{
										promotion_avail="false";
									}
									
								
									JSONObject promoData = new JSONObject();
									promoData.put("MID", mid);
									
									promoData.put("SKU_CD",promotionData.get(j).getSku_cd());
									
									promoData.put("PROMOTION", promotionData.get(j).getPromotion_name());
									
									promoData.put("PRESENT", promotion_avail);
									promoData.put("REMARK", promotionData.get(j).getRemark());
								
									
									promoData.put("USER_ID", username);

									String finalCompe = makeJson(promoData
											.toString());

									httppost = new HttpPost(
											CommonString.URL
													+ CommonString.METHOD_UPLOAD_PROMOTION_DATA);
									httppost.setHeader("Accept",
											"application/json");
									httppost.setHeader("Content-type",
											"application/json");

									se = new StringEntity(finalCompe);

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
										return CommonString.METHOD_UPLOAD_PROMOTION_DATA;
									}

									if (result.toString().equalsIgnoreCase(
											CommonString.KEY_FAILURE)) {
										return CommonString.METHOD_UPLOAD_PROMOTION_DATA;
									}

								}
								
								//Asset Data upload
								String asset_avail;
								AssetData = database.getAssetDataUpload(coverageBeanlist
										.get(i).getStoreId());

								
								for (int j = 0; j < AssetData.size(); j++) {
									
									if(AssetData.get(j).getAsset_avail().equalsIgnoreCase("YES")){
										asset_avail = "true";
										
									}else{
										asset_avail ="false";
									}

									JSONObject assetData = new JSONObject();
									assetData.put("MID", mid);
									assetData.put("SKU_CD", "0");
									assetData.put("ASSET_CD", AssetData
											.get(j).getAsset_cd());
									assetData.put("PRESENT",asset_avail);
									assetData.put("ASSET_COUNT", AssetData.get(j).getActualCount());
									assetData.put("REMARK", AssetData
											.get(j).getRemark());
									
									assetData.put("USER_ID", username);

									String finalPosm = makeJson(assetData
											.toString());

									httppost = new HttpPost(
											CommonString.URL
													+ CommonString.METHOD_UPLOAD_ASSET_DATA);
									httppost.setHeader("Accept",
											"application/json");
									httppost.setHeader("Content-type",
											"application/json");

									se = new StringEntity(finalPosm);

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
										return CommonString.METHOD_UPLOAD_ASSET_DATA;
									}

									if (result.toString().equalsIgnoreCase(
											CommonString.KEY_FAILURE)) {
										return CommonString.METHOD_UPLOAD_ASSET_DATA;
									}

								}

								
								// ** Upload Closing
								closingData = database.getClosingStockUpload(coverageBeanlist.get(i).getStoreId());
								for (int j = 0; j < closingData.size(); j++) {
									
									if (closingData.get(j).getStock().equalsIgnoreCase("")) {
										closingData.get(j).setStock("0");
									}

									JSONObject ClosingData = new JSONObject();

									ClosingData.put("MID",
											mid);
									ClosingData
											.put("SKU_CD", closingData
													.get(j).getSKU_CD());
									ClosingData.put("CLOSING_STOCK",
											closingData.get(j).getStock());

									ClosingData.put("USER_ID", username);

									String finalSalesPer = makeJson(ClosingData
											.toString());

									httppost = new HttpPost(
											CommonString.URL
													+ CommonString.METHOD_UPLOAD_CLOSING_DATA);
									httppost.setHeader("Accept",
											"application/json");
									httppost.setHeader("Content-type",
											"application/json");

									se = new StringEntity(finalSalesPer);

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
										return CommonString.METHOD_UPLOAD_CLOSING_DATA;
									}

									else if (result.toString()
											.equalsIgnoreCase(
													CommonString.KEY_FAILURE)) {
										return CommonString.METHOD_UPLOAD_CLOSING_DATA;
									} else if (result.toString()
											.equalsIgnoreCase(
													CommonString.KEY_FALSE)) {
										return CommonString.METHOD_UPLOAD_CLOSING_DATA;
									}
								}
								
								
								// ** Uploading lunch time data
								lunchData = database.getLunchData(coverageBeanlist.get(i).getStoreId());
		
								for (int j = 0; j < lunchData.size(); j++) {
									
									JSONObject obj = new JSONObject();

									obj.put("MID",mid);
									obj.put("FROMTIME", lunchData.get(j).getFromTime());	
									obj.put("TOTIME", lunchData.get(j).getToTime());
									obj.put("USER_ID", username);
									String finalSalesPer = makeJson(obj
											.toString());

									httppost = new HttpPost(
											CommonString.URL
													+ CommonString.METHOD_LUNCH_TIME_DATA);
									httppost.setHeader("Accept",
											"application/json");
									httppost.setHeader("Content-type",
											"application/json");

									se = new StringEntity(finalSalesPer);

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
										return CommonString.METHOD_LUNCH_TIME_DATA;
									}

									else if (result.toString()
											.equalsIgnoreCase(
													CommonString.KEY_FAILURE)) {
										return CommonString.METHOD_LUNCH_TIME_DATA;
									} else if (result.toString()
											.equalsIgnoreCase(
													CommonString.KEY_FALSE)) {
										return CommonString.METHOD_LUNCH_TIME_DATA;
									}
									

								}

								// Stock upload
								
								StockData = database.getStockUpload(coverageBeanlist.get(i).getStoreId());
								runOnUiThread(new Runnable() {

									public void run() {
										pb.setProgress(40);
										percentage.setText(40 + "%");
										message.setText("Uploading Data");
									}
								});
								
								
								
								for (int j = 0; j < StockData.size(); j++) {
									if (StockData.get(j).getStock().equalsIgnoreCase("")) {
										StockData.get(j).setStock("0");
										
									}

									JSONObject stockData = new JSONObject();
									stockData.put("MID", mid);
									stockData.put("SKU_CD", StockData
											.get(j).getSKU_CD());
									stockData.put("STOCK_RECEIVED", StockData
											.get(j).getStock());
									stockData.put("USER_ID", username);

									String finalTrainupload = makeJson(stockData
											.toString());

									httppost = new HttpPost(
											CommonString.URL
													+ CommonString.METHOD_UPLOAD_STOCK_DATA);
									httppost.setHeader("Accept",
											"application/json");
									httppost.setHeader("Content-type",
											"application/json");

									se = new StringEntity(finalTrainupload);

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
										return CommonString.METHOD_UPLOAD_STOCK_DATA;
									}

									if (result.toString().equalsIgnoreCase(
											CommonString.KEY_FAILURE)) {
										return CommonString.METHOD_UPLOAD_STOCK_DATA;
									}
									
									
									runOnUiThread(new Runnable() {

										public void run() {
											pb.setProgress(90);
											percentage.setText(90 + "%");
											message.setText("finalizing  Data");
										}
									});

								}

							}

						}

					}

					database.open();

					database.updateCoverageStatus(coverageBeanlist.get(i)
							.getMID(), CommonString.KEY_D);
					database.updateStoreStatusOnLeave(coverageBeanlist.get(i)
							.getStoreId(), coverageBeanlist.get(i)
							.getVisitDate(), CommonString.KEY_D);

					// SET COVERAGE STATUS
					JSONObject StatusXml = new JSONObject();

					StatusXml.put("STORE_CD", coverageBeanlist.get(i).getStoreId());
					StatusXml.put("USER_ID", username);
					StatusXml.put("VISITDATE", coverageBeanlist.get(i).getVisitDate());
					StatusXml.put("STATUS", CommonString.KEY_D);

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
						result = result.replace("\"", "");
					}

					if (result.toString().equalsIgnoreCase(
							CommonString.KEY_NO_DATA)) {
						return CommonString.MEHTOD_UPLOAD_COVERAGE_STATUS;
					}

					if (result.toString().equalsIgnoreCase(
							CommonString.KEY_FAILURE)) {
						return CommonString.MEHTOD_UPLOAD_COVERAGE_STATUS;
					}
				}


			}

			catch (MalformedURLException e) {

				final AlertMessage message = new AlertMessage(
						UploadDataActivity.this,
						AlertMessage.MESSAGE_EXCEPTION, "download", e);
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						message.showMessage();
					}
				}); 
			} catch (IOException e) {
				final AlertMessage message = new AlertMessage(
						UploadDataActivity.this,
						AlertMessage.MESSAGE_SOCKETEXCEPTION, "socket", e);

				runOnUiThread(new Runnable() {

					@Override
					public void run() {
					message.showMessage();
					}
				});
			}

			catch (Exception e) {
				final AlertMessage message = new AlertMessage(
						UploadDataActivity.this,
						AlertMessage.MESSAGE_EXCEPTION, "download", e);

				e.getMessage();
				e.printStackTrace();
				e.getCause();
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						message.showMessage();
					}
				});
			}
			return CommonString.KEY_SUCCESS;

		}

		@Override
		protected void onPostExecute(String result) {
			
			super.onPostExecute(result);

			dialog.dismiss();
			if (result.equals(CommonString.KEY_SUCCESS)) {
					AlertMessage message = new AlertMessage(
							UploadDataActivity.this,
							AlertMessage.MESSAGE_UPLOAD_DATA, "success", null);
					
					database.deleteAllTables();
					database.close();
					message.showMessage();
				
			} else if (!result.equals("")) {
				AlertMessage message = new AlertMessage(
						UploadDataActivity.this, CommonString.ERROR + result, "success", null);
				message.showMessage();
			}

		}
	}

	private static String convertInputStreamToString(InputStream inputStream) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while ((line = bufferedReader.readLine()) != null)
			result += line;

		inputStream.close();
		return result;

	}

	String makeJson(String json) {
		json = json.replace("\\", "");
		json = json.replace("\"[", "[");
		json = json.replace("]\"", "]");

		return json;
	}
	
	
	
  public JSONArray makeJsonArray(JSONArray json)
	{
		for(int i=0;i<json.length();i++){	
		}
		return json;
	}

}
