package com.cpm.download;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import JsonHandler.JsonHandler;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cpm.Constants.CommonString;
import com.cpm.database.pillsbury_Database;
import com.cpm.message.AlertMessage;
import com.cpm.pillsbury.R;
import com.cpm.xmlGetterSetter.AssetPostgetterSetter;
import com.cpm.xmlGetterSetter.AvailabilitygetterSetter;
import com.cpm.xmlGetterSetter.JCPGetterSetter;
import com.cpm.xmlGetterSetter.MappingAssetPostgetterSetter;
import com.cpm.xmlGetterSetter.MappingPromotionpostgetterSetter;
import com.cpm.xmlGetterSetter.NonWorkingGetterSetter;
import com.cpm.xmlGetterSetter.PerformanceMSetterGetter;
import com.cpm.xmlGetterSetter.SKUPostgetterSetter;
import com.cpm.xmlGetterSetter.planogramSetterGetter;

public class CompleteDownloadActivity extends Activity {

    private NonWorkingGetterSetter nonworking = null;
    private JCPGetterSetter jcpdata = null;
    private AvailabilitygetterSetter availability = null;
    private AssetPostgetterSetter assetPost = null;
    private AssetPostgetterSetter posmData = null;
    private SKUPostgetterSetter skup = null;
    private SKUPostgetterSetter categoryData = null;

    private SKUPostgetterSetter nonAssetReasonData = null;

    private SKUPostgetterSetter nonPromotionReasonData = null;
    private MappingAssetPostgetterSetter mappingAsset = null;
    private MappingPromotionpostgetterSetter mappingPromotion = null;
    private PerformanceMSetterGetter performanceData = null;
    private planogramSetterGetter planogramData = null;
    private Dialog dialog;
    private ProgressBar pb;
    private TextView percentage, message;

    private String username, date;
    private Data data;
    private pillsbury_Database db;
    private SharedPreferences.Editor editor = null;

    private SharedPreferences preferences = null;
    static int counter = 1;
    int eventType;
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        username = preferences.getString(CommonString.KEY_USERNAME, "");
        date = preferences.getString(CommonString.KEY_DATE, "");


        db = new pillsbury_Database(CompleteDownloadActivity.this);
        db.open();

//		db.dropTablesBeforeCreate();
        new BackgroundTask(this).execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // db.open();

    }

    @Override
    protected void onStop() {
        super.onStop();
//		db.close();
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
            dialog.setTitle("Download Files");
            dialog.setCancelable(false);
            dialog.show();
            pb = (ProgressBar) dialog.findViewById(R.id.progressBar1);
            percentage = (TextView) dialog.findViewById(R.id.percentage);
            message = (TextView) dialog.findViewById(R.id.message);

        }

        @Override
        protected String doInBackground(Void... params) {
            try {

                data = new Data();
                // JCP

                data.value = 10;
                data.name = "JCP Data Downloading";
                publishProgress(data);

                HttpParams myParams = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(myParams, 10000);
                HttpConnectionParams.setSoTimeout(myParams, 10000);
                HttpClient httpclient = new DefaultHttpClient();
                InputStream inputStream = null;
                JSONObject jo1 = new JSONObject();
                jo1.put("USER_ID", username);
                String jo = jo1.toString();
                HttpPost httppost = new HttpPost(CommonString.URL + CommonString.METHOD_NAME_JCP);
                httppost.setHeader("Accept", "application/json");
                httppost.setHeader("Content-type", "application/json");
                StringEntity se = new StringEntity(jo);

                httppost.setEntity(se);

                HttpResponse response = httpclient.execute(httppost);

                inputStream = response.getEntity().getContent();

                if (inputStream != null) {
                    result = convertInputStreamToString(inputStream);
                }

                //result = removequotes(result);

                if (result.toString().replace("\"", "")
                        .equalsIgnoreCase(CommonString.KEY_NO_DATA)) {
                    return CommonString.METHOD_NAME_JCP + ","
                            + AlertMessage.MESSAGE_JCP_FALSE;
                }

                // for failure

                if (result.toString().replace("\"", "").equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                    return CommonString.METHOD_NAME_JCP;
                }

                jcpdata = JsonHandler.JCPXMLHandler(result);


                data.value = 20;
                data.name = "Product Data Downloading";
                publishProgress(data);

                httppost = new HttpPost(CommonString.URL + CommonString.METHOD_NON_WORKING_MASTER);
                httppost.setHeader("Accept", "application/json");
                httppost.setHeader("Content-type", "application/json");


                response = httpclient.execute(httppost);

                inputStream = response.getEntity().getContent();

                if (inputStream != null) {
                    result = convertInputStreamToString(inputStream);
                }

                if (result.toString().replace("\"", "").equalsIgnoreCase(CommonString.KEY_NO_DATA)) {
                    return CommonString.METHOD_NON_WORKING_MASTER;
                }

                // for failure

                if (result.toString().replace("\"", "").equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                    return CommonString.METHOD_NON_WORKING_MASTER;
                }
                nonworking = JsonHandler.NonWorkingXMLHandler(result);

                //  Method downloading availability
                data.value = 30;
                data.name = "Designation Data Downloading";
                publishProgress(data);


                httppost = new HttpPost(CommonString.URL + CommonString.METHOD_DOWNLOAD_AVAILABILITY);
                httppost.setHeader("Accept", "application/json");
                httppost.setHeader("Content-type", "application/json");

                se = new StringEntity(jo);
                httppost.setEntity(se);
                response = httpclient.execute(httppost);

                inputStream = response.getEntity().getContent();

                if (inputStream != null) {
                    result = convertInputStreamToString(inputStream);
                }

                if (result.toString().replace("\"", "")
                        .equalsIgnoreCase(CommonString.KEY_NO_DATA)) {
                    return CommonString.METHOD_DOWNLOAD_AVAILABILITY;
                }


                if (result.toString().replace("\"", "").equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                    return CommonString.METHOD_DOWNLOAD_AVAILABILITY;
                } else {
                    availability = JsonHandler.availabilityDetails(result);

                }

                //  Method downloading asset POST
                data.value = 40;
                data.name = "Asset Post Data Downloading";
                publishProgress(data);
                httppost = new HttpPost(CommonString.URL
                        + CommonString.METHOD_DOWNLOAD_ASSETPOST);
                httppost.setHeader("Accept", "application/json");
                httppost.setHeader("Content-type", "application/json");

                response = httpclient.execute(httppost);

                inputStream = response.getEntity().getContent();

                if (inputStream != null) {
                    result = convertInputStreamToString(inputStream);
                }

                if (result.toString().replace("\"", "")
                        .equalsIgnoreCase(CommonString.KEY_NO_DATA)) {
//								return CommonString.METHOD_DOWNLOAD_ASSETPOST;
                }

                if (result.toString().replace("\"", "").equalsIgnoreCase(CommonString.KEY_FAILURE)) {
//								return CommonString.METHOD_DOWNLOAD_ASSETPOST;
                } else {
                    assetPost = JsonHandler.AssetPostHandler(result);

                }


                //  Method downloading SKU POST
                data.value = 45;
                data.name = "sku Post Data Downloading";
                publishProgress(data);


                httppost = new HttpPost(CommonString.URL + CommonString.METHOD_DOWNLOAD_SKUPOST);
                httppost.setHeader("Accept", "application/json");
                httppost.setHeader("Content-type", "application/json");

                response = httpclient.execute(httppost);

                inputStream = response.getEntity().getContent();

                if (inputStream != null) {
                    result = convertInputStreamToString(inputStream);
                }

                if (result.toString().replace("\"", "")
                        .equalsIgnoreCase(CommonString.KEY_NO_DATA)) {
                    return CommonString.METHOD_DOWNLOAD_SKUPOST;
                }


                if (result.toString().replace("\"", "")
                        .equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                    return CommonString.METHOD_DOWNLOAD_SKUPOST;
                } else {
                    skup = JsonHandler.skupostHandler(result);

                }


                //METHOD TO DOWNLOAD MAPPING aSSET POST
                data.value = 50;
                data.name = "mapping asset post Data Downloading";
                publishProgress(data);

                httppost = new HttpPost(CommonString.URL + CommonString.METHOD_MAPPING_ASSETPOST);
                httppost.setHeader("Accept", "application/json");
                httppost.setHeader("Content-type", "application/json");

                se = new StringEntity(jo);
                httppost.setEntity(se);
                response = httpclient.execute(httppost);

                inputStream = response.getEntity().getContent();
                if (inputStream != null) {
                    result = convertInputStreamToString(inputStream);
                }


                if (result.toString().replace("\"", "").equalsIgnoreCase(CommonString.KEY_NO_DATA)) {
//								return CommonString.METHOD_MAPPING_ASSETPOST;
                }
                if (result.toString().replace("\"", "").equalsIgnoreCase(CommonString.KEY_FAILURE)) {
//								return CommonString.METHOD_MAPPING_ASSETPOST;
                }
                if (result.toString().replace("\"", "")
                        .equalsIgnoreCase(CommonString.KEY_NO_DATA)) {
//								return CommonString.METHOD_MAPPING_ASSETPOST;
                } else {

                    mappingAsset = JsonHandler.mapAssetPost(result);


                }


                //METHOD TO DOWNLOAD MAPPING promotion POST
                /*data.value = 80;
                data.name = "mapping promotion post Data Downloading";
                publishProgress(data);

              //  httppost = new HttpPost(CommonString.URL + CommonString.Download_MAPPING_PROMOTIONPOST);
                httppost=new HttpPost(CommonString.URL+CommonString.PROMOTION_MAPING);
                httppost.setHeader("Accept", "application/json");
                httppost.setHeader("Content-type", "application/json");

                se = new StringEntity(jo);
                httppost.setEntity(se);
                response = httpclient.execute(httppost);

                inputStream = response.getEntity().getContent();

                if (inputStream != null) {
                    result = convertInputStreamToString(inputStream);
                }

                if (result.toString().replace("\"", "").equalsIgnoreCase(CommonString.KEY_NO_DATA)) {
//								return CommonString.Download_MAPPING_PROMOTIONPOST;
                }

                if (result.toString().replace("\"", "").equalsIgnoreCase(CommonString.KEY_FAILURE)) {
//								return CommonString.Download_MAPPING_PROMOTIONPOST;
                }

                if (result.toString().replace("\"", "").equalsIgnoreCase(CommonString.KEY_NO_DATA)) {
//								return CommonString.Download_MAPPING_PROMOTIONPOST;
                } else {
                    mappingPromotion = JsonHandler.mapPromotionpost(result);
                    db.InsertMappingPromotionPostData(mappingPromotion);
                }
*/ /*mappingPromotion = JsonHandler.mapPromotionpost(result);
                db.InsertMappingPromotionPostData(mappingPromotion);
*/


                //performance data download
                data.value = 55;
                data.name = "Performance Data Downloading";
                publishProgress(data);
                jo1.put("USER_ID", username);

                jo = jo1.toString();
                httppost = new HttpPost(CommonString.URL + CommonString.Download_PERFORMANCE);
                httppost.setHeader("Accept", "application/json");
                httppost.setHeader("Content-type", "application/json");

                se = new StringEntity(jo);
                httppost.setEntity(se);
                response = httpclient.execute(httppost);

                inputStream = response.getEntity().getContent();

                if (inputStream != null) {
                    result = convertInputStreamToString(inputStream);
                }

                if (result.toString().replace("\"", "")
                        .equalsIgnoreCase(CommonString.KEY_NO_DATA)) {
                    return CommonString.Download_PERFORMANCE;
                }

                if (result.toString().replace("\"", "")
                        .equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                    return CommonString.Download_PERFORMANCE;
                } else {

                    performanceData = JsonHandler.performanceDetails(result);

                }


                data.value = 60;
                data.name = "Planogram Data Downloading";
                publishProgress(data);
                jo1.put("USER_ID", username);

                jo = jo1.toString();
                httppost = new HttpPost(CommonString.URL + CommonString.METHOD_DOWNLOAD_PLANOGRAM);
                httppost.setHeader("Accept", "application/json");
                httppost.setHeader("Content-type", "application/json");

                se = new StringEntity(jo);
                httppost.setEntity(se);
                response = httpclient.execute(httppost);

                inputStream = response.getEntity().getContent();

                if (inputStream != null) {
                    result = convertInputStreamToString(inputStream);
                }

                if (result.toString().replace("\"", "").equalsIgnoreCase(CommonString.KEY_NO_DATA)) {
                    return CommonString.METHOD_DOWNLOAD_PLANOGRAM;
                }

                if (result.toString().replace("\"", "")
                        .equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                    return CommonString.METHOD_DOWNLOAD_PLANOGRAM;
                } else {

                    planogramData = JsonHandler.planogramDetails(result);

                }

                data.value = 65;
                data.name = "Performance Data Downloading";
                publishProgress(data);
                jo1.put("USER_ID", username);

                jo = jo1.toString();
                httppost = new HttpPost(CommonString.URL + CommonString.Download_PERFORMANCE);
                httppost.setHeader("Accept", "application/json");
                httppost.setHeader("Content-type", "application/json");

                se = new StringEntity(jo);
                httppost.setEntity(se);
                response = httpclient.execute(httppost);

                inputStream = response.getEntity().getContent();

                if (inputStream != null) {
                    result = convertInputStreamToString(inputStream);
                }

                if (result.toString().replace("\"", "")
                        .equalsIgnoreCase(CommonString.KEY_NO_DATA)) {
                    return CommonString.Download_PERFORMANCE;
                }

                if (result.toString().replace("\"", "")
                        .equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                    return CommonString.Download_PERFORMANCE;
                } else {
                    performanceData = JsonHandler.performanceDetails(result);
                }

                // Category Download
                data.value = 70;
                data.name = "Category Data Downloading";
                publishProgress(data);
                jo1.put("UserName", username);
                jo1.put("Type", "CATEGORY_MASTER");

                jo = jo1.toString();
                httppost = new HttpPost(CommonString.URL + CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD);
                httppost.setHeader("Accept", "application/json");
                httppost.setHeader("Content-type", "application/json");

                se = new StringEntity(jo);
                httppost.setEntity(se);
                response = httpclient.execute(httppost);

                inputStream = response.getEntity().getContent();

                if (inputStream != null) {
                    result = convertInputStreamToString(inputStream);
                }

                if (result.toString().replace("\"", "")
                        .equalsIgnoreCase(CommonString.KEY_NO_DATA)) {
                    return CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD;
                }

                if (result.toString().replace("\"", "")
                        .equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                    return CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD;
                } else {
                    categoryData = JsonHandler.CategoryXMLHandler(result);


                }


                data.value = 75;
                data.name = "Non Asset Reason Data Downloading";
                publishProgress(data);
                jo1.put("UserName", username);
                jo1.put("Type", "NON_ASSET_REASON");

                jo = jo1.toString();
                httppost = new HttpPost(CommonString.URL + CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD);
                httppost.setHeader("Accept", "application/json");
                httppost.setHeader("Content-type", "application/json");

                se = new StringEntity(jo);
                httppost.setEntity(se);
                response = httpclient.execute(httppost);

                inputStream = response.getEntity().getContent();

                if (inputStream != null) {
                    result = convertInputStreamToString(inputStream);
                }

                if (result.toString().replace("\"", "")
                        .equalsIgnoreCase(CommonString.KEY_NO_DATA)) {
                    return CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD;
                }

                if (result.toString().replace("\"", "")
                        .equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                    return CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD;
                } else {
                    nonAssetReasonData = JsonHandler.NonAssetReasonXMLHandler(result);


//								
                }


                data.value = 80;
                data.name = "Non Promotion Reason Data Downloading";
                publishProgress(data);
                jo1.put("UserName", username);
                jo1.put("Type", "NON_PROMOTION_REASON");

                jo = jo1.toString();
                httppost = new HttpPost(CommonString.URL + CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD);
                httppost.setHeader("Accept", "application/json");
                httppost.setHeader("Content-type", "application/json");

                se = new StringEntity(jo);
                httppost.setEntity(se);
                response = httpclient.execute(httppost);

                inputStream = response.getEntity().getContent();

                if (inputStream != null) {
                    result = convertInputStreamToString(inputStream);
                }

                if (result.toString().replace("\"", "")
                        .equalsIgnoreCase(CommonString.KEY_NO_DATA)) {
                    return CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD;
                }

                if (result.toString().replace("\"", "")
                        .equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                    return CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD;
                } else {
                    nonPromotionReasonData = JsonHandler.NonPromotionReasonXMLHandler(result);
                }


                data.value = 85;
                data.name = "POSM Data Downloading";
                publishProgress(data);
                jo1.put("UserName", username);
                jo1.put("Type", "POSM_MASTER");

                jo = jo1.toString();
                httppost = new HttpPost(CommonString.URL + CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD);
                httppost.setHeader("Accept", "application/json");
                httppost.setHeader("Content-type", "application/json");

                se = new StringEntity(jo);
                httppost.setEntity(se);
                response = httpclient.execute(httppost);

                inputStream = response.getEntity().getContent();

                if (inputStream != null) {
                    result = convertInputStreamToString(inputStream);
                }

                if (result.toString().replace("\"", "")
                        .equalsIgnoreCase(CommonString.KEY_NO_DATA)) {
                    return CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD;
                }

                if (result.toString().replace("\"", "")
                        .equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                    return CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD;
                } else {
                    posmData = JsonHandler.POSMXMLHandler(result);

                }

                // PROMOTION_MAPPING
                data.value = 90;
                data.name = "Promotion Mapping Data Downloading";
                publishProgress(data);
                jo1.put("UserName", username);
                jo1.put("Type", "PROMOTION_MAPPING");

                jo = jo1.toString();
                httppost = new HttpPost(CommonString.URL + CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD);
                httppost.setHeader("Accept", "application/json");
                httppost.setHeader("Content-type", "application/json");
                se = new StringEntity(jo);
                httppost.setEntity(se);
                response = httpclient.execute(httppost);

                inputStream = response.getEntity().getContent();

                if (inputStream != null) {
                    result = convertInputStreamToString(inputStream);
                }

                if (result.toString().replace("\"", "")
                        .equalsIgnoreCase(CommonString.KEY_NO_DATA)) {
                    return CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD;
                }

                if (result.toString().replace("\"", "")
                        .equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                    return CommonString.METHOD_NAME_UNIVERSAL_DOWNLOAD;
                } else {

                    mappingPromotion = JsonHandler.NonPromotionalXMLHandlerData(result);

                   }


                editor = preferences.edit();
                editor.commit();
                db.open();

                db.InsertStoreData(jcpdata, jcpdata.getVisitdate().get(0));
                db.InsertAvailabilityData(availability);
                db.InsertNonWorkingData(nonworking);
                db.InsertAssetPostData(assetPost);
                db.InsertSKUPostData(skup);
                db.InsertMappinAssetPostData(mappingAsset);
                db.InsertPerformanceData(performanceData);
                db.InsertPlanogramData(planogramData);
                db.InsertPerformanceData(performanceData);
                db.InsertCategoryData(categoryData);
                db.InsertNonAssetReasonData(nonAssetReasonData);
                db.InsertNonPromotionReasonData(nonPromotionReasonData);
                db.InsertPOSMData(posmData);

                if (mappingPromotion.getSKU_CD().size() > 0) {
                    db.InsertMappingPromotionPostData(mappingPromotion);
                } else {
                    db.deletepromotionMapping();
                }


                data.value = 100;
                data.name = "Finishing";
                publishProgress(data);

                return CommonString.KEY_SUCCESS;

            } catch (MalformedURLException e) {
                e.printStackTrace();

                final AlertMessage message = new AlertMessage(CompleteDownloadActivity.this, AlertMessage.MESSAGE_EXCEPTION, "download", e);
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        message.showMessage();
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
                final AlertMessage message = new AlertMessage(
                        CompleteDownloadActivity.this,
                        AlertMessage.MESSAGE_SOCKETEXCEPTION, "socket", e);
                counter++;
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {


                        if (counter < 3) {
                            new BackgroundTask(CompleteDownloadActivity.this)
                                    .execute();
                        } else {
                            message.showMessage();
                            counter = 1;
                        }

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                final AlertMessage message = new AlertMessage(
                        CompleteDownloadActivity.this,
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
                AlertMessage message = new AlertMessage(CompleteDownloadActivity.this, AlertMessage.MESSAGE_DOWNLOAD, "success", null);
                message.showMessage();
            } else if (result.equals(CommonString.METHOD_NAME_JCP)) {
                AlertMessage message = new AlertMessage(CompleteDownloadActivity.this,
                        AlertMessage.MESSAGE_JCP_FALSE, "success", null);
                message.showMessage();
            } else if (!result.equals("")) {
                AlertMessage message = new AlertMessage(
                        CompleteDownloadActivity.this, result, "success", null);
                message.showMessage();
            }

        }

    }

    class Data {
        int value;
        String name;
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
