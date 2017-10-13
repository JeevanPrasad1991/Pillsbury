package com.cpm.pillsbury;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import JsonHandler.JsonHandler;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cpm.Constants.CommonString;
import com.cpm.autoupdate.AutoupdateActivity;
import com.cpm.database.pillsbury_Database;
import com.cpm.delegates.CoverageBean;
import com.cpm.message.AlertMessage;
import com.cpm.web.ShowUpdateActivity;
import com.cpm.pillsbury.R;
import com.cpm.xmlGetterSetter.FailureGetterSetter;
import com.cpm.xmlGetterSetter.LoginGetterSetter;
import com.cpm.xmlHandler.XMLHandlers;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import static android.content.ContentValues.TAG;


public class LoginActivity extends Activity implements OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    String resultt;
    private EditText mUsername, mPassword;
    private Button mLogin;
    private RelativeLayout login_remember;
    private ImageView login_remembericon;

    private String username, password, p_username, p_password, MimeNum;
    private double latitude = 0.0, longitude = 0.0;
    private int versionCode;
    private boolean isChecked;
    //username and password -- testuser and cpm123
    private LocationManager locmanager = null;
    private SharedPreferences preferences = null;
    private SharedPreferences.Editor editor = null;
    private Intent intent = null;
    pillsbury_Database database;

    static int counter = 1;
    TextView login_version;
    String app_ver;
    int eventType;
    // Location updates intervals in sec
    private static int UPDATE_INTERVAL = 500; // 5 sec
    private static int FATEST_INTERVAL = 100; // 1 sec
    private static int DISPLACEMENT = 5; // 10 meters
    private LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;


    LoginGetterSetter lgs = null;
    private ArrayList<CoverageBean> coverageBeanlist = new ArrayList<CoverageBean>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loging);
        ContentResolver.setMasterSyncAutomatically(false);

        // login_version = (TextView) findViewById(R.id.login_versiontext);
        mUsername = (EditText) findViewById(R.id.login_usertextbox);
        mPassword = (EditText) findViewById(R.id.login_locktextbox);
        // login_remember = (RelativeLayout)
        // findViewById(R.id.login_rememberme);
        // login_remembericon = (ImageView)
        // findViewById(R.id.login_remembermeicon);

        mLogin = (Button) findViewById(R.id.login_loginbtn);
       /* mUsername.setText("test121");
       // mUsername.setText("mallikarjun.m");
        mPassword.setText("cpm123");*/
     /*  mUsername.setText("pooja");
        mPassword.setText("cpm123");*/

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        p_username = preferences.getString(CommonString.KEY_USERNAME, null);
        p_password = preferences.getString(CommonString.KEY_PASSWORD, null);
        TelephonyManager tManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        MimeNum = tManager.getDeviceId();
        // isChecked = preferences.getBoolean(CommonString.KEY_REMEMBER, false);

        try {
            app_ver = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;


            // login_version.setText("Parinaam Version " + app_ver);
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        database = new pillsbury_Database(this);
        database.open();

        if (!isChecked) {
            // login_remembericon.setImageResource(R.drawable.deactive_radio_box);
        } else {
            mUsername.setText(p_username);
            mPassword.setText(p_password);
        }

        mLogin.setOnClickListener(this);
        // login_remember.setOnClickListener(this);

        // for location
        locmanager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean enabled = locmanager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        // Check if enabled and if not send user to the GSP settings
        // Better solution would be to display a dialog and suggesting to
        // go to the settings
        if (!enabled) {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                    LoginActivity.this);

            // Setting Dialog Title
            alertDialog.setTitle("GPS IS DISABLED...");

            // Setting Dialog Message
            alertDialog.setMessage("Click ok to enable GPS.");

            // Setting Positive "Yes" Button
            alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });

            // Setting Negative "NO" Button
            alertDialog.setNegativeButton("NO",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to invoke NO event

                            dialog.cancel();
                        }
                    });

            // Showing Alert Message
            alertDialog.show();

        }

        // Create a Folder for Images
        File file = new File(Environment.getExternalStorageDirectory(), "Pillsbury_Images");
        if (!file.isDirectory()) {
            file.mkdir();
        }
        // First we need to check availability of play services
        if (checkPlayServices()) {

            // Building the GoogleApi client
            buildGoogleApiClient();

            createLocationRequest();
        }

        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        checkPlayServices();

        //locmanager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

        locmanager.removeUpdates(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        username = mUsername.getText().toString().trim();
        password = mPassword.getText().toString().trim();

        switch (v.getId()) {
            case R.id.login_loginbtn:
                if (username.length() == 0) {
                    showToast("Please enter username");
                } else if (password.length() == 0) {
                    showToast("Please enter password");
                } else {

                    p_username = preferences.getString(CommonString.KEY_USERNAME, null);
                    p_password = preferences.getString(CommonString.KEY_PASSWORD, null);
                    // If no preferences are stored
                    if (p_username == null && p_password == null) {

                        if (CheckNetAvailability()) {
                            new AuthenticateTask().execute();

                        } else {
                            showToast("No Network and first login");
                        }
                    }
                    // If preferences are stored
                    else {
                        if (username.equals(p_username)) {
                            if (CheckNetAvailability()) {
                                new AuthenticateTask().execute();
                            } else if (password.equals(p_password)) {
                                intent = new Intent(this, MainMenuActivity.class);
                                startActivity(intent);
                                this.finish();
                                showToast("No Network and offline login");
                            } else {
                                showToast("Incorrect Password");
                            }
                        } else {
                            showToast("Incorrect Username");
                        }
                    }
                }
                break;

        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (mLastLocation != null) {
                latitude = mLastLocation.getLatitude();
                longitude = mLastLocation.getLongitude();
            }

        }

        //  startLocationUpdates();


    }

    protected void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

//            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());

    }

    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    private class AuthenticateTask extends AsyncTask<Void, Void, String> {
        private ProgressDialog dialog = null;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            dialog = new ProgressDialog(LoginActivity.this);
            dialog.setTitle("Login");
            dialog.setMessage("Authenticating....");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                versionCode = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;

                HttpParams myParams = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(myParams, 10000);
                HttpConnectionParams.setSoTimeout(myParams, 10000);
                HttpClient httpclient = new DefaultHttpClient();
                InputStream inputStream = null;

                JSONObject jo1 = new JSONObject();
                jo1.put("USER_ID", username);
                jo1.put("PASSWORD", password);
                jo1.put("IN_TIME", getCurrentTime());
                jo1.put("LATITUDE", latitude);
                jo1.put("LONGITUDE", longitude);
                jo1.put("APP_VERSION", app_ver);
                jo1.put("ATT_MODE", "online");
//		        jo1.put("DEVICE_ID" ,MimeNum);


                String jo = jo1.toString();

                HttpPost httppost = new HttpPost(CommonString.URL + CommonString.METHOD_LOGIN);
                httppost.setHeader("Accept", "application/json");
                httppost.setHeader("Content-type", "application/json");
                StringEntity se = new StringEntity(jo);
                httppost.setEntity(se);
                HttpResponse response = httpclient.execute(httppost);
                inputStream = response.getEntity().getContent();
                if (inputStream != null) {
                    resultt = convertInputStreamToString(inputStream);
                }

                if (resultt.toString().replace("\"", "").equalsIgnoreCase(CommonString.KEY_FAILURE)) {
                    final AlertMessage message = new AlertMessage(LoginActivity.this, AlertMessage.MESSAGE_FAILURE, "login", null);
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            message.showMessage();
                        }
                    });

                } else if (resultt.toString().replace("\"", "").equalsIgnoreCase(CommonString.KEY_FALSE)) {

                    final AlertMessage message = new AlertMessage(LoginActivity.this, AlertMessage.MESSAGE_FALSE, "login", null);
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            message.showMessage();
                        }
                    });

                } else if (resultt.toString().replace("\"", "").equalsIgnoreCase(CommonString.KEY_CHANGED)) {

                    final AlertMessage message = new AlertMessage(LoginActivity.this, AlertMessage.MESSAGE_CHANGED, "login", null);
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            message.showMessage();
                        }
                    });

                } else {


                    if (resultt.toString().replace("\"", "").equalsIgnoreCase(CommonString.KEY_FAILURE))


                    {
                        final AlertMessage message = new AlertMessage(LoginActivity.this, CommonString.METHOD_LOGIN, CommonString.KEY_FAILURE

                                , null);
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                message.showMessage();
                            }
                        });
                    } else {

                        // For String source

                        lgs = JsonHandler.loginXMLHandler(resultt);

                        // PUT IN PREFERENCES

                        editor.putString(CommonString.KEY_USERNAME, username);
                        editor.putString(CommonString.KEY_PASSWORD, password);
                        editor.putString(CommonString.KEY_VERSION, lgs.getVERSION());
                        editor.putString(CommonString.KEY_PATH, lgs.getPATH());
                        editor.putString(CommonString.KEY_DATE, lgs.getDATE());
                       /* //default date

                       editor.putString(CommonString.KEY_DATE, "12/14/2016");*/
                        editor.commit();

                        return CommonString.KEY_SUCCESS;

                    }
                }

                return "";

            } catch (MalformedURLException e) {

                final AlertMessage message = new AlertMessage(LoginActivity.this, AlertMessage.MESSAGE_EXCEPTION, "acra_login", e);
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        message.showMessage();
                    }
                });

            } catch (IOException e) {
                final AlertMessage message = new AlertMessage(LoginActivity.this, AlertMessage.MESSAGE_SOCKETEXCEPTION, "socket_login", e);

                counter++;
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        if (counter < 3) {
                            new AuthenticateTask().execute();
                        } else {
                            message.showMessage();
                            counter = 1;
                        }
                    }
                });
            } catch (Exception e) {
                final AlertMessage message = new AlertMessage(LoginActivity.this, AlertMessage.MESSAGE_EXCEPTION, "acra_login", e);
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

            if (result.equals(CommonString.KEY_SUCCESS)) {

                database.open();
                if (preferences.getString(CommonString.KEY_VERSION, "").equals(Integer.toString(versionCode))) {
                    intent = new Intent(getBaseContext(), ShowUpdateActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    intent = new Intent(getBaseContext(), AutoupdateActivity.class);
                    intent.putExtra(CommonString.KEY_PATH, preferences.getString(CommonString.KEY_PATH, ""));
                    startActivity(intent);
                    finish();
                }

            }

            dialog.dismiss();
        }

    }


    public boolean CheckNetAvailability() {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            // we are connected to a network
            connected = true;
        }
        return connected;
    }

    private void showToast(String message) {
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
    }

    // for location
    @Override
    public void onLocationChanged(Location location) {
        // TODO Auto-generated method stub
/*        latitude = location.getLatitude();
        longitude = location.getLongitude();*/
    }

    @Override
    public void onProviderDisabled(String arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
        // TODO Auto-generated method stub

    }

    public void onButtonClick(View v) {

		/*
         * Uri uri = Uri.fromFile(new
		 * File(Environment.getExternalStorageDirectory(), "PEPSICO_DATABASE"));
		 * Intent sendIntent = new Intent(Intent.ACTION_SEND);
		 * sendIntent.setType("image/jpeg");
		 * sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[]
		 * {"khushboo.goyal@in.cpm-int.com"});
		 * sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Parinaam Pepsico Backup");
		 * sendIntent.putExtra(Intent.EXTRA_STREAM, uri);
		 * sendIntent.putExtra(Intent.EXTRA_TEXT,
		 * "Please find Attach my Db File Regards Khushi");
		 * startActivity(Intent.createChooser(sendIntent, "Email:"));
		 */

        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);

    }

    public String getCurrentTime() {

        Calendar m_cal = Calendar.getInstance();

        String intime = m_cal.get(Calendar.HOUR_OF_DAY) + ":"
                + m_cal.get(Calendar.MINUTE) + ":" + m_cal.get(Calendar.SECOND);

        return intime;

    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub

        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);

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

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(), "This device is not supported.", Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
    }

    /**
     * Creating location request object
     */
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FATEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    protected void stopLocationUpdates() {
        // LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, (com.google.android.gms.location.LocationListener) this);
    }

}
