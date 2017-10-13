package com.cpm.upload;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
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
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import com.cpm.delegates.Posmbean;
import com.cpm.delegates.PromotionTrackingBean;
import com.cpm.delegates.StoreBean;
import com.cpm.delegates.competitionbeans;
import com.cpm.message.AlertMessage;
import com.cpm.pillsbury.MainMenuActivity;
import com.cpm.pillsbury.R;
import com.cpm.xmlGetterSetter.MappingPromotionpostgetterSetter;

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
    private ArrayList<MappingPromotionpostgetterSetter> promotionData = new ArrayList<MappingPromotionpostgetterSetter>();
    private ArrayList<AssetTrackerBean> AssetData = new ArrayList<AssetTrackerBean>();
    private ArrayList<Posmbean> posmData = new ArrayList<Posmbean>();
    private ArrayList<competitionbeans> comData = new ArrayList<competitionbeans>();
    private ArrayList<MidDayRecievedStockBean> listedStockData = new ArrayList<MidDayRecievedStockBean>();
    static int counter = 1;
    String version;
    boolean upload_status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
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

        coverageBeanlist = database.getCoverageData(null, null);
        if (coverageBeanlist != null) {
            visit_date = coverageBeanlist.get(0).getVisitDate();
        } else {
            Toast.makeText(getApplicationContext(), "coveragebeanlist is null", Toast.LENGTH_SHORT);
        }

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

                if (upload_status == false) {
                    coverageBeanlist = database.getCoverageData(visit_date, null);

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
                    storestatus = database.getStoreStatus(coverageBeanlist.get(i).getStoreId());
                    if (upload_status) {
                        storestatus.setCheckout_status("C");
                    }
                    runOnUiThread(new Runnable() {

                        public void run() {
                            k = k + factor;
                            pb.setProgress(10);
                            percentage.setText(10 + "%");
                            message.setText("Uploading Data");
                        }
                    });

                    if ((storestatus.getCheckout_status().equalsIgnoreCase(CommonString.KEY_L)
                            || storestatus.getCheckout_status().equalsIgnoreCase(CommonString.KEY_C))) {
                        // if (true) {

                        if (!coverageBeanlist.get(i).getStatus().equalsIgnoreCase(CommonString.KEY_U)) {
                            // if (true) {
                            JSONObject coverageDat = new JSONObject();
                            coverageDat.put("STORE_CD", coverageBeanlist.get(i).getStoreId());
                            coverageDat.put("VISITDATE", coverageBeanlist.get(i).getVisitDate());
                            coverageDat.put("INTIME", coverageBeanlist.get(i).getInTime());
                            coverageDat.put("OUTTIME", coverageBeanlist.get(i).getOutTime());
                            coverageDat.put("LATITUDE", coverageBeanlist.get(i).getLatitude());
                            coverageDat.put("LONGITUDE", coverageBeanlist.get(i).getLongitude());
                            coverageDat.put("REASON_CD", coverageBeanlist.get(i).getReasonid());
                            coverageDat.put("REASON_REMARK", coverageBeanlist.get(i).getRemark());
                            coverageDat.put("IMAGE_URL", coverageBeanlist.get(i).getImage());
                            coverageDat.put("STATUS", coverageBeanlist.get(i).getStatus());
                            coverageDat.put("RIGHTS", coverageBeanlist.get(i).getRights());
                            coverageDat.put("USER_ID", username);
                            String finalcoverage = makeJson(coverageDat.toString());
                            HttpPost httppost = new HttpPost(CommonString.URL + CommonString.METHOD_UPLOAD_DR_STORE_COVERAGE);
                            httppost.setHeader("Accept", "application/json");
                            httppost.setHeader("Content-type", "application/json");
                            StringEntity se = new StringEntity(finalcoverage);
                            httppost.setEntity(se);
                            HttpResponse response = httpclient.execute(httppost);
                            inputStream = response.getEntity().getContent();
                            if (inputStream != null) {
                                result = convertInputStreamToString(inputStream);
                                result = result.replace("\"", "");
                            }
                            if (result.toString().equalsIgnoreCase(CommonString.KEY_NO_DATA)) {
                                return CommonString.METHOD_UPLOAD_DR_STORE_COVERAGE;
                            }

                            if (result.toString().equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                                return CommonString.METHOD_UPLOAD_DR_STORE_COVERAGE;
                            }

                            datacheck = result.toString();
                            datacheck = datacheck.replace("\"", "");
                            words = datacheck.split("\\;");
                            validity = (words[0]);
                            if (validity.equalsIgnoreCase(CommonString.KEY_SUCCESS)) {
                                database.updateCoverageStatus(coverageBeanlist.get(i).getMID(), CommonString.KEY_D);
                                database.updateStoreStatusOnLeave(coverageBeanlist.get(i).getStoreId(),
                                        coverageBeanlist.get(i).getVisitDate(), CommonString.KEY_D);
                            } else {
                                if (result.toString().equalsIgnoreCase(CommonString.KEY_FALSE)) {
                                    return CommonString.METHOD_UPLOAD_DR_STORE_COVERAGE;
                                }
                                if (result.toString().equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                                    return CommonString.METHOD_UPLOAD_DR_STORE_COVERAGE;
                                }

                            }

                            mid = Integer.parseInt((words[1]));
                            if (!(coverageBeanlist.get(i).getReasonid().equalsIgnoreCase("") || coverageBeanlist
                                    .get(i).getReasonid().equalsIgnoreCase("0"))) {

                                System.out.println("");
                            } else {

                                JSONArray collectiontoSend = new JSONArray();
                                String final_xml = "";
                                String onXML = "";
                                String PriceSinagge = null, singagecorrected, pogadhe, pogcorre, shelf;




                                //opening data upload
                                openingData = database.getOpeningDataUpload(coverageBeanlist.get(i).getStoreId());

                                if (openingData.size() > 0) {
                                    runOnUiThread(new Runnable() {

                                        public void run() {
                                            pb.setProgress(20);
                                            percentage.setText(20 + "%");
                                            message.setText("Uploading Data");
                                        }
                                    });

                                    for (int j = 0; j < openingData.size(); j++) {

                                        if (openingData.get(j).getPrice_sign().equalsIgnoreCase("YES")) {
                                            PriceSinagge = "True";
                                        }
                                        if (openingData.get(j).getPrice_sign().equalsIgnoreCase("NA")) {
                                            PriceSinagge = "NA";
                                        }

                                        if (openingData.get(j).getPrice_sign().equalsIgnoreCase("NO")) {
                                            PriceSinagge = "false";
                                        }

                                        if (openingData.get(j).getShelf().equalsIgnoreCase("YES")) {
                                            shelf = "1";
                                        } else {
                                            shelf = "0";
                                        }

                                        if (openingData.get(j).getSign_corrected().equalsIgnoreCase("YES")) {
                                            singagecorrected = "true";
                                        } else {
                                            singagecorrected = "false";
                                        }


                                        if (openingData.get(j).getPog_adherence().equalsIgnoreCase("YES")) {
                                            pogadhe = "true";
                                        } else {
                                            pogadhe = "false";
                                        }


                                        if (openingData.get(j).getPog_corrected().equalsIgnoreCase("YES")) {
                                            pogcorre = "true";
                                        } else {
                                            pogcorre = "false";
                                        }


                                        if (openingData.get(j).getShelf().equalsIgnoreCase("")) {
                                            openingData.get(j).setShelf("0");
                                        }


                                        if (openingData.get(j).getFacing().equalsIgnoreCase("")) {
                                            openingData.get(j).setFacing("0");
                                        }

                                        if (openingData.get(j).getOpeningStock().equalsIgnoreCase("")) {
                                            openingData.get(j).setOpeningStock("0");
                                        }


                                        if (openingData.get(j).getPricing().equalsIgnoreCase("")) {
                                            openingData.get(j).setPricing("0");
                                        }

                                        if (openingData.get(j).getExpriring_stock().equalsIgnoreCase("")) {
                                            openingData.get(j).setExpriring_stock("0");
                                        }

									/*String frombottomshelf="";

									if(openingData.get(j).getShelf_fm_bottom().equalsIgnoreCase("YES"))
									{
										frombottomshelf="1";
									}

									else{
										frombottomshelf="0";
									}*/


                                        onXML = "[USER_DATA][MID]"
                                                + mid
                                                + "[/MID][CREATED_BY]"
                                                + username
                                                + "[/CREATED_BY][CURRENT_SHELF]"
                                                + shelf
                                                + "[/CURRENT_SHELF]"
                                                + "[FACING]"
                                                + openingData.get(j).getFacing()
                                                + "[/FACING][OPENING_STOCK]"
                                                + openingData.get(j).getOpeningStock()
                                                + "[/OPENING_STOCK][PRICING]"
                                                + openingData.get(j).getPricing()
                                                + "[/PRICING][EXPRING_STOCK]"
                                                + openingData.get(j).getExpriring_stock() + "[/EXPRING_STOCK] [SHELF_FROM_BOTTOM]"
                                                + openingData.get(j).getShelf_fm_bottom() + "[/SHELF_FROM_BOTTOM] [SKU_CD]"
                                                + openingData.get(j).getSKU_CD() + "[/SKU_CD][EXPIRING_DATE] "
                                                + openingData.get(j).getExpiring_date()
                                                + "[/EXPIRING_DATE][/USER_DATA]";

                                        final_xml = final_xml + onXML;
                                    }


                                    final_xml = "\"" + "[DATA]" + final_xml + "[/DATA]" + "\"";

                                    JSONObject openingData = new JSONObject();
                                    openingData.put("MID", mid);
                                    openingData.put("KEYS", "Opening Stock_new");
                                    openingData.put("USERNAME", username);
                                    openingData.put("XMLDATA", final_xml);


                                    String finalOpeningStock = makeJson(openingData.toString());

                                    httppost = new HttpPost(CommonString.URL + CommonString.METHOD_UPLOAD_XML);
                                    httppost.setHeader("Accept", "application/json");
                                    httppost.setHeader("Content-type", "application/json");

                                    se = new StringEntity(finalOpeningStock);

                                    httppost.setEntity(se);

                                    response = httpclient.execute(httppost);

                                    inputStream = response.getEntity().getContent();

                                    if (inputStream != null) {
                                        result = convertInputStreamToString(inputStream);
                                        result = result.replace("\"", "");
                                    }
                                    if (!result.toString().equalsIgnoreCase(CommonString.KEY_SUCCESS)) {
                                        return CommonString.METHOD_UPLOAD_XML;
                                    }

                                    if (result.toString().equalsIgnoreCase(CommonString.KEY_NO_DATA)) {
                                        return CommonString.METHOD_UPLOAD_XML;
                                    }

                                    if (result.toString().equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                                        return CommonString.METHOD_UPLOAD_XML;
                                    }

                                    // My changes below for multiple row insertion
//									collectiontoSend.put(finalOpeningStock);


                                }

                                //Call Data upload
//								String test = collectiontoSend.toString();
//								test = makeJson(test);
                                String call_xml = "";


                                callTrackerData = database.getCallTrackerData(coverageBeanlist.get(i).getStoreId());

                                if (callTrackerData.size() > 0) {/*

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

									onXML = "[USER_DATA][MID]"
											+ mid
											+ "[/MID][CREATED_BY]"
											+ username
											+ "[/CREATED_BY][TOTAL_CALLS]"
											+ callTrackerData.get(j).getTotalCalls()
											+ "[/TOTAL_CALLS]"
											+ "[PRODUCTIVE_CALLS]"
											+ callTrackerData.get(j).getProductiveCalls()
											+ "[/PRODUCTIVE_CALLS][POS_SALE]"
											+ callTrackerData.get(j).getPosSale()
											+ "[/POS_SALE]"
											+ "[/USER_DATA]";


										call_xml = call_xml + onXML;

								}


									call_xml = "\""+"[DATA]" + call_xml +"[/DATA]"+"\"";

									JSONObject callData = new JSONObject();
									callData.put("MID", mid);
									callData.put("KEYS","CALL_DATA");
									callData.put("XMLDATA", call_xml);
									callData.put("USERNAME", username);

									String finalCalls = makeJson(callData.toString());

									httppost = new HttpPost(
											CommonString.URL
													+ CommonString.METHOD_UPLOAD_XML);
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
										return CommonString.METHOD_UPLOAD_XML;
									}
									if (result.toString().equalsIgnoreCase(
											CommonString.KEY_FAILURE)) {
										return CommonString.METHOD_UPLOAD_XML;
									}

								*/
                                }

                                //call data upload 12 to 3

                                database.getCallDataInstant(coverageBeanlist.get(i).getStoreId());
                                for (int j = 0; j < callTrackerData.size(); j++) {
                                }


                                //Promotion data upload
                                String promotion_xml = "";
                                promotionData = database.getPromotionDefaltDataUpload(coverageBeanlist.get(i).getStoreId());

                                if (promotionData.size() > 0) {
                                    runOnUiThread(new Runnable() {

                                        public void run() {
                                            pb.setProgress(30);
                                            percentage.setText(30 + "%");
                                            message.setText("Uploading Data");
                                        }
                                    });
                                    for (int j = 0; j < promotionData.size(); j++) {
                                        String promo_avail;
                                        if (promotionData.get(j).getPromotion_avail().equalsIgnoreCase("YES")) {
                                            promo_avail = "1";
                                        } else {
                                            promo_avail = "0";
                                        }

                                        onXML = "[USER_DATA][MID]"
                                                + mid
                                                + "[/MID][CREATED_BY]"
                                                + username
                                                + "[/CREATED_BY][SKU_CD]"
                                                + promotionData.get(j).getSKU_CD().get(0)
                                                + "[/SKU_CD]"
                                                + "[PROMOTION_CD]"
                                                + promotionData.get(j).getID().get(0)
                                                + "[/PROMOTION_CD][PROMOTION_AVAIL]"
                                                + promo_avail + "[/PROMOTION_AVAIL] [REMARK]"
                                                + promotionData.get(j).getRemark()
                                                + "[/REMARK][REASON_ID]"
                                                + promotionData.get(j).getReasonid()
                                                + "[/REASON_ID][IMAGE]"
                                                + promotionData.get(j).getImage()
                                                + "[/IMAGE][/USER_DATA]";

                                        promotion_xml = promotion_xml + onXML;
                                    }

                                    promotion_xml = "\"" + "[DATA]" + promotion_xml + "[/DATA]" + "\"";
                                    JSONObject promoData = new JSONObject();
                                    promoData.put("MID", mid);
                                    promoData.put("KEYS", "PROMOTION_DATA_WITHOUT_SKU");
                                    promoData.put("XMLDATA", promotion_xml);
                                    promoData.put("USERNAME", username);
                                    String finalCompe = makeJson(promoData.toString());
                                    httppost = new HttpPost(CommonString.URL + CommonString.METHOD_UPLOAD_XML);
                                    httppost.setHeader("Accept", "application/json");
                                    httppost.setHeader("Content-type", "application/json");
                                    se = new StringEntity(finalCompe);
                                    httppost.setEntity(se);
                                    response = httpclient.execute(httppost);
                                    inputStream = response.getEntity().getContent();
                                    if (inputStream != null) {
                                        result = convertInputStreamToString(inputStream);
                                        result = result.replace("\"", "");
                                    }

                                    if (!result.toString().equalsIgnoreCase(CommonString.KEY_SUCCESS)) {
                                        return CommonString.METHOD_UPLOAD_XML;
                                    }
                                    if (result.toString().equalsIgnoreCase(CommonString.KEY_NO_DATA)) {
                                        return CommonString.METHOD_UPLOAD_XML;
                                    }

                                    if (result.toString().equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                                        return CommonString.METHOD_UPLOAD_XML;
                                    }

                                }


                                //Asset Data upload
                                String asset_avail, actual_count;
                                String asset_xml = "";
                                AssetData = database.getAssetDataUpload(coverageBeanlist.get(i).getStoreId());
                                if (AssetData.size() > 0) {
                                    runOnUiThread(new Runnable() {

                                        public void run() {
                                            pb.setProgress(40);
                                            percentage.setText(40 + "%");
                                            message.setText("Uploading Data");
                                        }
                                    });
                                    for (int j = 0; j < AssetData.size(); j++) {
                                        if (AssetData.get(j).getAsset_avail().equalsIgnoreCase("YES")) {
                                            asset_avail = "true";
                                        } else {
                                            asset_avail = "false";
                                        }

                                        if (AssetData.get(j).getActualCount() == null) {
                                            actual_count = "0";
                                        } else {
                                            actual_count = AssetData.get(j).getActualCount();
                                        }

                                        onXML = "[USER_DATA][MID]"
                                                + mid
                                                + "[/MID][CREATED_BY]"
                                                + username
                                                + "[/CREATED_BY][BRAND_CD]"
                                                + AssetData.get(j).getBrand_cd()
                                                + "[/BRAND_CD]"
                                                + "[ASSET_CD]"
                                                + AssetData.get(j).getAsset_cd()
                                                + "[/ASSET_CD][PRESENT]"
                                                + asset_avail
                                                + "[/PRESENT] [REMARK]"
                                                + AssetData.get(j).getRemark()
                                                + "[/REMARK] [ASSET_COUNT]"
                                                + actual_count
                                                + "[/ASSET_COUNT][REASON_ID]"
                                                + AssetData.get(j).getReasonid()
                                                + "[/REASON_ID][IMAGE]" + AssetData.get(j).getImage()
                                                + "[/IMAGE][/USER_DATA]";


                                        asset_xml = asset_xml + onXML;

                                    }

                                    asset_xml = "\"" + "[DATA]" + asset_xml + "[/DATA]" + "\"";

                                    JSONObject assetData = new JSONObject();
                                    assetData.put("MID", mid);
                                    assetData.put("KEYS", "ASSET_DATA_NEW1");
                                    assetData.put("XMLDATA", asset_xml);
                                    assetData.put("USERNAME", username);

                                    String finalPosm = makeJson(assetData.toString());
                                    httppost = new HttpPost(CommonString.URL + CommonString.METHOD_UPLOAD_XML);
                                    httppost.setHeader("Accept", "application/json");
                                    httppost.setHeader("Content-type", "application/json");
                                    se = new StringEntity(finalPosm);
                                    httppost.setEntity(se);
                                    response = httpclient.execute(httppost);
                                    inputStream = response.getEntity().getContent();
                                    if (inputStream != null) {
                                        result = convertInputStreamToString(inputStream);
                                        result = result.replace("\"", "");
                                    }
                                    if (!result.toString().equalsIgnoreCase(CommonString.KEY_SUCCESS)) {
                                        return CommonString.METHOD_UPLOAD_XML;
                                    }

                                    if (result.toString().equalsIgnoreCase(CommonString.KEY_NO_DATA)) {
                                        return CommonString.METHOD_UPLOAD_XML;
                                    }
                                    if (result.toString().equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                                        return CommonString.METHOD_UPLOAD_XML;
                                    }

                                }


                                // ** Upload Closing
                                String closing_xml = "";
                                closingData = database.getClosingStockUpload(coverageBeanlist.get(i).getStoreId());
                                if (closingData.size() > 0) {
                                    runOnUiThread(new Runnable() {

                                        public void run() {
                                            pb.setProgress(50);
                                            percentage.setText(50 + "%");
                                            message.setText("Uploading Data");
                                        }
                                    });
                                    for (int j = 0; j < closingData.size(); j++) {
                                        if (closingData.get(j).getStock().equalsIgnoreCase("")) {
                                            closingData.get(j).setStock("0");
                                        }

                                        onXML = "[USER_DATA][MID]"
                                                + mid
                                                + "[/MID][CREATED_BY]"
                                                + username
                                                + "[/CREATED_BY][SKU_CD]"
                                                + closingData.get(j).getSKU_CD()
                                                + "[/SKU_CD]"
                                                + "[CLOSING_STOCK]"
                                                + closingData.get(j).getStock()
                                                + "[/CLOSING_STOCK][/USER_DATA]";

                                        closing_xml = closing_xml + onXML;
                                    }

                                    closing_xml = "\"" + "[DATA]" + closing_xml + "[/DATA]" + "\"";

                                    JSONObject ClosingData = new JSONObject();

                                    ClosingData.put("MID", mid);
                                    ClosingData.put("KEYS", "CLOSING_STOCK_DATA");
                                    ClosingData.put("XMLDATA", closing_xml);
                                    ClosingData.put("USERNAME", username);

                                    String finalSalesPer = makeJson(ClosingData.toString());
                                    httppost = new HttpPost(CommonString.URL + CommonString.METHOD_UPLOAD_XML);
                                    httppost.setHeader("Accept", "application/json");
                                    httppost.setHeader("Content-type", "application/json");

                                    se = new StringEntity(finalSalesPer);
                                    httppost.setEntity(se);

                                    response = httpclient.execute(httppost);

                                    inputStream = response.getEntity().getContent();

                                    if (inputStream != null) {
                                        result = convertInputStreamToString(inputStream);
                                        result = result.replace("\"", "");
                                    }
                                    if (!result.toString().equalsIgnoreCase(CommonString.KEY_SUCCESS)) {
                                        return CommonString.METHOD_UPLOAD_XML;
                                    }
                                    if (result.toString().equalsIgnoreCase(CommonString.KEY_NO_DATA)) {
                                        return CommonString.METHOD_UPLOAD_XML;
                                    }
                                    else if (result.toString().equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                                        return CommonString.METHOD_UPLOAD_XML;
                                    }
                                    else if (result.toString().equalsIgnoreCase(CommonString.KEY_FALSE)) {
                                        return CommonString.METHOD_UPLOAD_XML;
                                    }

                                }

                                // ** Uploading lunch time data
                                String lunch_xml = "";
                                lunchData = database.getLunchData(coverageBeanlist.get(i).getStoreId());

                                if (lunchData.size() > 0) {
                                    runOnUiThread(new Runnable() {

                                        public void run() {
                                            pb.setProgress(60);
                                            percentage.setText(60 + "%");
                                            message.setText("Uploading Data");
                                        }
                                    });
                                    for (int j = 0; j < lunchData.size(); j++) {

                                        onXML = "[USER_DATA][MID]"
                                                + mid
                                                + "[/MID][CREATED_BY]"
                                                + username
                                                + "[/CREATED_BY][FROMTIME]"
                                                + lunchData.get(j).getFromTime()
                                                + "[/FROMTIME]"
                                                + "[TOTIME]"
                                                + lunchData.get(j).getToTime()
                                                + "[/TOTIME][/USER_DATA]";


                                        lunch_xml = lunch_xml + onXML;
                                    }

                                    lunch_xml = "\"" + "[DATA]" + lunch_xml + "[/DATA]" + "\"";


                                    JSONObject obj = new JSONObject();

                                    obj.put("MID", mid);
                                    obj.put("KEYS", "LUNCH_DATA");
                                    obj.put("XMLDATA", lunch_xml);
                                    obj.put("USERNAME", username);
                                    String finalSalesPer1 = makeJson(obj.toString());

                                    httppost = new HttpPost(CommonString.URL + CommonString.METHOD_UPLOAD_XML);
                                    httppost.setHeader("Accept", "application/json");
                                    httppost.setHeader("Content-type", "application/json");
                                    se = new StringEntity(finalSalesPer1);
                                    httppost.setEntity(se);
                                    response = httpclient.execute(httppost);

                                    inputStream = response.getEntity().getContent();

                                    if (inputStream != null) {
                                        result = convertInputStreamToString(inputStream);
                                        result = result.replace("\"", "");
                                    }
                                    if (!result.toString().equalsIgnoreCase(CommonString.KEY_SUCCESS)) {
                                        return CommonString.METHOD_UPLOAD_XML;
                                    }

                                    if (result.toString().equalsIgnoreCase(CommonString.KEY_NO_DATA)) {
                                        return CommonString.METHOD_UPLOAD_XML;
                                    } else if (result.toString().equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                                        return CommonString.METHOD_UPLOAD_XML;
                                    } else if (result.toString().equalsIgnoreCase(CommonString.KEY_FALSE)) {
                                        return CommonString.METHOD_UPLOAD_XML;
                                    }
                                }




                                // Stock upload
                                String stock_xml = "";
                                StockData = database.getStockUpload(coverageBeanlist.get(i).getStoreId());

                                if (StockData.size() > 0) {
                                    runOnUiThread(new Runnable() {

                                        public void run() {
                                            pb.setProgress(70);
                                            percentage.setText(70 + "%");
                                            message.setText("Uploading Data");
                                        }
                                    });
                                    for (int j = 0; j < StockData.size(); j++) {
                                        if (StockData.get(j).getStock().equalsIgnoreCase("")) {
                                            StockData.get(j).setStock("0");

                                        }

                                        onXML = "[USER_DATA][MID]"
                                                + mid
                                                + "[/MID][CREATED_BY]"
                                                + username
                                                + "[/CREATED_BY][SKU_CD]"
                                                + StockData.get(j).getSKU_CD()
                                                + "[/SKU_CD]"
                                                + "[STOCK_RECEIVED]"
                                                + StockData.get(j).getStock()
                                                + "[/STOCK_RECEIVED][/USER_DATA]";

                                        stock_xml = stock_xml + onXML;

                                    }


                                    stock_xml = "\"" + "[DATA]" + stock_xml + "[/DATA]" + "\"";
                                    JSONObject stockData = new JSONObject();
                                    stockData.put("MID", mid);
                                    stockData.put("KEYS", "STOCK_RECIEVED_DATA");
                                    stockData.put("XMLDATA", stock_xml);
                                    stockData.put("USERNAME", username);
                                    String finalTrainupload = makeJson(stockData.toString());

                                    httppost = new HttpPost(CommonString.URL + CommonString.METHOD_UPLOAD_XML);
                                    httppost.setHeader("Accept", "application/json");
                                    httppost.setHeader("Content-type", "application/json");

                                    se = new StringEntity(finalTrainupload);

                                    httppost.setEntity(se);

                                    response = httpclient.execute(httppost);

                                    inputStream = response.getEntity().getContent();

                                    if (inputStream != null) {
                                        result = convertInputStreamToString(inputStream);
                                        result = result.replace("\"", "");
                                    }
                                    if (!result.toString().equalsIgnoreCase(CommonString.KEY_SUCCESS)) {
                                        return CommonString.METHOD_UPLOAD_XML;
                                    }

                                    if (result.toString().equalsIgnoreCase(CommonString.KEY_NO_DATA)) {
                                        return CommonString.METHOD_UPLOAD_XML;
                                    }

                                    if (result.toString().equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                                        return CommonString.METHOD_UPLOAD_XML;
                                    }
                                }


                                //Competition SOS upload
                                String com_xml = "";

                                comData = database.getInsertedCompetitionData(coverageBeanlist.get(i).getStoreId());
                                if (comData.size() > 0) {
                                    runOnUiThread(new Runnable() {

                                        public void run() {
                                            pb.setProgress(80);
                                            percentage.setText(80 + "%");
                                            message.setText("Uploading Data");
                                        }
                                    });


                                    for (int j = 0; j < comData.size(); j++) {
                                        if (comData.get(j).getQuantity().equalsIgnoreCase("")) {
                                            comData.get(j).setQuantity("0");

                                        }

                                        onXML = "[USER_DATA][MID]"
                                                + mid
                                                + "[/MID][CREATED_BY]"
                                                + username
                                                + "[/CREATED_BY][CATEGORY_CD]"
                                                + comData.get(j).getCategory_id()
                                                + "[/CATEGORY_CD]"
                                                + "[QUANTITY]"
                                                + comData.get(j).getQuantity()
                                                + "[/QUANTITY][/USER_DATA]";


                                        com_xml = com_xml + onXML;

                                    }

                                    com_xml = "\"" + "[DATA]" + com_xml + "[/DATA]" + "\"";
                                    JSONObject comData = new JSONObject();
                                    comData.put("MID", mid);
                                    comData.put("KEYS", "COMPETITION_SOS");
                                    comData.put("XMLDATA", com_xml);
                                    comData.put("USERNAME", username);

                                    String finalTrainupload = makeJson(comData.toString());

                                    httppost = new HttpPost(CommonString.URL + CommonString.METHOD_UPLOAD_XML);
                                    httppost.setHeader("Accept", "application/json");
                                    httppost.setHeader("Content-type", "application/json");
                                    se = new StringEntity(finalTrainupload);

                                    httppost.setEntity(se);

                                    response = httpclient.execute(httppost);

                                    inputStream = response.getEntity().getContent();

                                    if (inputStream != null) {
                                        result = convertInputStreamToString(inputStream);
                                        result = result.replace("\"", "");
                                    }
                                    if (!result.toString().equalsIgnoreCase(CommonString.KEY_SUCCESS)) {
                                        return CommonString.METHOD_UPLOAD_XML;
                                    }
                                    if (result.toString().equalsIgnoreCase(
                                            CommonString.KEY_NO_DATA)) {
                                        return CommonString.METHOD_UPLOAD_XML;
                                    }

                                    if (result.toString().equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                                        return CommonString.METHOD_UPLOAD_XML;
                                    }
                                }


                                runOnUiThread(new Runnable() {

                                    public void run() {
                                        pb.setProgress(90);
                                        percentage.setText(90 + "%");
                                        message.setText("finalizing  Data");
                                    }
                                });


                                //ADDITIONAL VISIBILITY upload
                                String posm_xml = "";

                                posmData = database.getProductEntryDetail(coverageBeanlist.get(i).getStoreId());
                                if (posmData.size() > 0) {
                                    runOnUiThread(new Runnable() {

                                        public void run() {
                                            pb.setProgress(95);
                                            percentage.setText(95 + "%");
                                            message.setText("Uploading Data");
                                        }
                                    });


                                    for (int j = 0; j < posmData.size(); j++) {
											/*if (comData.get(j).getQuantity().equalsIgnoreCase("")) {
												comData.get(j).setQuantity("0");

											}
											*/
                                        onXML = "[USER_DATA][MID]"
                                                + mid
                                                + "[/MID][CREATED_BY]"
                                                + username
                                                + "[/CREATED_BY][CATEGORY_CD]"
                                                + posmData.get(j).getCategory_id()
                                                + "[/CATEGORY_CD]"
                                                + "[POSM_CD]"
                                                + posmData.get(j).getPOSM_CD()
                                                + "[/POSM_CD]" +
                                                "[IMAGE]" + posmData.get(j).getImage()
                                                + " [/IMAGE][/USER_DATA]";


                                        posm_xml = posm_xml + onXML;

                                    }

                                    posm_xml = "\"" + "[DATA]" + posm_xml + "[/DATA]" + "\"";

                                    JSONObject comData = new JSONObject();
                                    comData.put("MID", mid);
                                    comData.put("KEYS", "ADDITIONAL_VISIBILITY");
                                    comData.put("XMLDATA", posm_xml);
                                    comData.put("USERNAME", username);

                                    String finalTrainupload = makeJson(comData
                                            .toString());

                                    httppost = new HttpPost(CommonString.URL + CommonString.METHOD_UPLOAD_XML);
                                    httppost.setHeader("Accept", "application/json");
                                    httppost.setHeader("Content-type", "application/json");
                                    se = new StringEntity(finalTrainupload);
                                    httppost.setEntity(se);
                                    response = httpclient.execute(httppost);

                                    inputStream = response.getEntity()
                                            .getContent();

                                    if (inputStream != null) {
                                        result = convertInputStreamToString(inputStream);
                                        result = result.replace("\"", "");
                                    }
                                    if (!result.toString().equalsIgnoreCase(CommonString.KEY_SUCCESS)) {
                                        return CommonString.METHOD_UPLOAD_XML;
                                    }

                                    if (!result.toString().equalsIgnoreCase(CommonString.KEY_SUCCESS)) {
                                        return CommonString.METHOD_UPLOAD_XML;
                                    }

                                    if (result.toString().equalsIgnoreCase(CommonString.KEY_NO_DATA)) {
                                        return CommonString.METHOD_UPLOAD_XML;
                                    }

                                    if (result.toString().equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                                        return CommonString.METHOD_UPLOAD_XML;
                                    }
                                }


                                // Stock listed data
                                String upload_xml="";
                                listedStockData = database.getStockListedData(coverageBeanlist.get(i).getStoreId());
                                if (listedStockData.size() > 0) {
                                    runOnUiThread(new Runnable() {

                                        public void run() {
                                            pb.setProgress(96);
                                            percentage.setText(96 + "%");
                                            message.setText("Uploading Data");
                                        }
                                    });
                                    for (int j = 0; j < listedStockData.size(); j++) {
                                        onXML = "[USER_DATA][MID]"
                                                + mid
                                                + "[/MID][CREATED_BY]"
                                                + username
                                                + "[/CREATED_BY]"
                                                + "[SKU_CD]"
                                                + listedStockData.get(j).getSKU_CD()
                                                + "[/SKU_CD]"
                                                + "[LISTED_TOGGLE]"
                                                + listedStockData.get(j).getIsListed()
                                                + "[/LISTED_TOGGLE]"
                                                + "[BRAND_CD]"
                                                + listedStockData.get(j).getBRAND_CD()
                                                + "[/BRAND_CD][/USER_DATA]";

                                        upload_xml = upload_xml + onXML;

                                    }


                                    upload_xml = "\"" + "[DATA]" + upload_xml + "[/DATA]" + "\"";
                                    JSONObject stockData = new JSONObject();
                                    stockData.put("MID", mid);
                                    stockData.put("KEYS", "LISTED_SKU_DATA");
                                    stockData.put("XMLDATA", upload_xml);
                                    stockData.put("USERNAME", username);
                                    String finalTrainupload = makeJson(stockData.toString().trim());
                                    httppost = new HttpPost(CommonString.URL + CommonString.METHOD_UPLOAD_XML);
                                    httppost.setHeader("Accept", "application/json");
                                    httppost.setHeader("Content-type", "application/json");

                                    se = new StringEntity(finalTrainupload);
                                    httppost.setEntity(se);

                                    response = httpclient.execute(httppost);

                                    inputStream = response.getEntity().getContent();

                                    if (inputStream != null) {
                                        result = convertInputStreamToString(inputStream);
                                        result = result.replace("\"", "");
                                    }
                                    if (!result.toString().equalsIgnoreCase(CommonString.KEY_SUCCESS)) {
                                        return CommonString.METHOD_UPLOAD_XML;
                                    }

                                    if (result.toString().equalsIgnoreCase(CommonString.KEY_NO_DATA)) {
                                        return CommonString.METHOD_UPLOAD_XML;
                                    }

                                    if (result.toString().equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                                        return CommonString.METHOD_UPLOAD_XML;
                                    }
                                }



                                if (new File(Environment.getExternalStorageDirectory() + "/Pillsbury_Images/"
                                        + coverageBeanlist.get(i).getImage()).exists()) {

                                    result = UploadPOSMImage(coverageBeanlist.get(i).getImage(), "DealerboardImage");

                                    if (result.equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                                        return CommonString.METHOD_Get_DR_POSM_IMAGES + "," + errormsg;
                                    }

                                    runOnUiThread(new Runnable() {

                                        public void run() {
                                            message.setText("Coverage Image Uploaded");
                                        }
                                    });
                                }


                                //asset images data
                                AssetData = database.getAssetDataUpload(coverageBeanlist.get(i).getStoreId());
                                for (int j = 0; j < AssetData.size(); j++) {
                                    if (AssetData.get(j).getImage() != null && !AssetData.get(j).getImage().equals("")) {

                                        if (new File(Environment.getExternalStorageDirectory() + "/Pillsbury_Images/" + AssetData.get(j).getImage()).exists()) {

                                            result = UploadPOSMImage(AssetData.get(j).getImage(), "AssetImage");

                                            if (result.equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                                                return CommonString.METHOD_Get_DR_POSM_IMAGES + "," + errormsg;
                                            }

                                            if (!result.equalsIgnoreCase(CommonString.KEY_SUCCESS)) {
                                                return CommonString.METHOD_Get_DR_POSM_IMAGES + "," + errormsg;
                                            }

                                            runOnUiThread(new Runnable() {

                                                public void run() {
                                                    message.setText("Asset Image Uploaded");
                                                }
                                            });
                                        }
                                    }
                                }


                                //promotion image upload
                                promotionData = database.getPromotionDefaltDataUpload(coverageBeanlist.get(i).getStoreId());

                                for (int j = 0; j < promotionData.size(); j++) {

                                    if (promotionData.get(j).getImage() != null && !promotionData.get(j).getImage().equals("")) {

                                        if (new File(Environment.getExternalStorageDirectory() + "/Pillsbury_Images/" + promotionData.get(j).getImage()).exists()) {

                                            result = UploadPOSMImage(promotionData.get(j).getImage(), "PromotionImage");

                                            if (!result.toString().equalsIgnoreCase(CommonString.KEY_SUCCESS)) {
                                                return CommonString.METHOD_UPLOAD_XML;
                                            }
                                            if (result.equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                                                return CommonString.METHOD_Get_DR_POSM_IMAGES + "," + errormsg;
                                            }
                                            runOnUiThread(new Runnable() {

                                                public void run() {
                                                    message.setText("Promotion Image Uploaded");
                                                }
                                            });
                                        }
                                    }
                                }


                                posmData = database.getProductEntryDetail(coverageBeanlist.get(i).getStoreId());


                                for (int j = 0; j < posmData.size(); j++) {
                                    if (posmData.get(j).getImage() != null && !posmData.get(j).getImage().equals("")) {
                                        if (new File(Environment.getExternalStorageDirectory() + "/Pillsbury_Images/" + posmData.get(j).getImage()).exists()) {
                                            result = UploadPOSMImage(posmData.get(j).getImage(), "AdditionalVisibility");
                                            if (!result.toString().equalsIgnoreCase(CommonString.KEY_SUCCESS)) {
                                                return CommonString.METHOD_UPLOAD_XML;
                                            }
                                            if (result.equalsIgnoreCase(CommonString.KEY_FAILURE)) {

                                                return CommonString.METHOD_Get_DR_POSM_IMAGES + "," + errormsg;
                                            }

                                            runOnUiThread(new Runnable() {

                                                public void run() {
                                                    message.setText("Additional Visibility Image Uploaded");
                                                }
                                            });
                                        }


                                    }
                                }

                                runOnUiThread(new Runnable() {

                                    public void run() {
                                        pb.setProgress(100);
                                        percentage.setText(100 + "%");
                                        message.setText("finalizing  Data");
                                    }
                                });

                            }

                        }

                    }



                    database.open();
                    database.updateCoverageStatus(coverageBeanlist.get(i).getMID(), CommonString.KEY_U);
                    database.updateStoreStatusOnLeave(coverageBeanlist.get(i).getStoreId(), coverageBeanlist.get(i).getVisitDate(), CommonString.KEY_U);
                    // SET COVERAGE STATUS
                    JSONObject StatusXml = new JSONObject();
                    StatusXml.put("STORE_CD", coverageBeanlist.get(i).getStoreId());
                    StatusXml.put("USER_ID", username);
                    StatusXml.put("VISITDATE", coverageBeanlist.get(i).getVisitDate());
                    StatusXml.put("STATUS", CommonString.KEY_U);
                    String finalData;
                    finalData = StatusXml.toString();
                    finalData = makeJson(finalData);
                    HttpPost httppost = new HttpPost(CommonString.URL + CommonString.MEHTOD_UPLOAD_COVERAGE_STATUS);
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

                    if (!result.toString().equalsIgnoreCase(CommonString.KEY_SUCCESS)) {
                        return CommonString.METHOD_UPLOAD_XML;
                    }

                    if (result.toString().equalsIgnoreCase(CommonString.KEY_NO_DATA)) {
                        return CommonString.MEHTOD_UPLOAD_COVERAGE_STATUS;
                    }

                    if (result.toString().equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                        return CommonString.MEHTOD_UPLOAD_COVERAGE_STATUS;
                    }
                }

                return CommonString.KEY_SUCCESS;
            } catch (MalformedURLException e) {

                final AlertMessage message = new AlertMessage(UploadDataActivity.this,
                        AlertMessage.MESSAGE_EXCEPTION, "download", e);
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        message.showMessage();
                    }
                });
            } catch (IOException e) {
                final AlertMessage message = new AlertMessage(UploadDataActivity.this, AlertMessage.MESSAGE_SOCKETEXCEPTION, "socket", e);
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        message.showMessage();
                    }
                });
            } catch (Exception e) {
                final AlertMessage message = new AlertMessage(UploadDataActivity.this,
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
            return "";

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dialog.dismiss();
            if (result.equalsIgnoreCase(CommonString.KEY_SUCCESS)) {
                AlertMessage message = new AlertMessage(UploadDataActivity.this, AlertMessage.MESSAGE_UPLOAD_DATA, "success", null);
                database.deleteAllTables();
                database.close();
                message.showMessage();
            } else if (!result.equals("")) {
                AlertMessage message = new AlertMessage(UploadDataActivity.this, CommonString.ERROR + result, "success", null);
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


    public JSONArray makeJsonArray(JSONArray json) {
        for (int i = 0; i < json.length(); i++) {
        }
        return json;
    }

    public String UploadPOSMImage(String path, String folder) throws Exception {

        errormsg = "";
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;


        BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/Pillsbury_Images/" + path, o);

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
        Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/Pillsbury_Images/" + path, o2);

        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bao);
        byte[] ba = bao.toByteArray();
        String ba1 = Base64.encodeBytes(ba);

        SoapObject request = new SoapObject(CommonString.NAMESPACE, CommonString.METHOD_Get_DR_POSM_IMAGES);

        request.addProperty("img", ba1);
        request.addProperty("name", path);
        request.addProperty("FolderName", folder);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(CommonString.URL_IMAGE);

        androidHttpTransport.call(CommonString.SOAP_ACTION_Get_DR_POSM_IMAGES, envelope);
        Object result = (Object) envelope.getResponse();

        if (result.toString().equalsIgnoreCase(CommonString.KEY_SUCCESS)) {

            new File(Environment.getExternalStorageDirectory() + "/Pillsbury_Images/" + path).delete();

        } else {

            return CommonString.KEY_FAILURE;


        }

        return result.toString();
    }


}
