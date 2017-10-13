
package com.cpm.dailyEntry;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.cpm.Constants.CommonString;

import com.cpm.database.pillsbury_Database;
import com.cpm.delegates.CoverageBean;
import com.cpm.pillsbury.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class StoreimageActivity extends Activity implements OnClickListener {


    ImageView img_cam,img_clicked;
    Button btn_save;

    String _pathforcheck,_path,str;

    String store_cd,visit_date,username,intime,date;

    private SharedPreferences preferences;

    AlertDialog alert;

    String img_str;

    private pillsbury_Database database;

    // private LocationManager locationManager;

    String lat,lon;

    ArrayList<CoverageBean> coverage_list;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contentstoreimage);
        
        img_cam = (ImageView) findViewById(R.id.img_selfie);
        img_clicked = (ImageView) findViewById(R.id.img_cam_selfie);

        btn_save = (Button) findViewById(R.id.btn_save_selfie);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        store_cd = preferences.getString(CommonString.KEY_STORE_ID, null);

        visit_date = preferences.getString(CommonString.KEY_DATE, null);
        date = preferences.getString(CommonString.KEY_DATE, null);
        username = preferences.getString(CommonString.KEY_USERNAME, null);

        intime = preferences
                .getString(CommonString.KEY_STORE_IN_TIME, "");

        str = CommonString.FILE_PATH;

        database = new pillsbury_Database(this);
        database.open();

        //coverage_list =  database.getCoverageSpecificData(store_cd);

        img_clicked.setOnClickListener(this);
        btn_save.setOnClickListener(this);


    }

  

    @Override
    public void onBackPressed() {
		/*Intent i = new Intent(this, DailyEntryScreen.class);
		startActivity(i);*/

       /* if (img_str !=null) {
            deleteImages();
        }
        else{
            finish();

            //overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
        }
*/

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id){

            case R.id.img_cam_selfie:

                _pathforcheck = store_cd + "Store"
                        + "Image" + visit_date.replace("/","") + getCurrentTime().replace(":","")+".jpg";

                _path = CommonString.FILE_PATH + _pathforcheck;

                intime = getCurrentTime();

                startCameraActivity();

                break;

            case R.id.btn_save_selfie:

                if (img_str !=null) {


                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            StoreimageActivity.this);
                    builder.setMessage("Do you want to save the data ")
                            .setCancelable(false)
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(
                                                DialogInterface dialog,
                                                int id) {

                                            alert.getButton(
                                                    AlertDialog.BUTTON_POSITIVE)
                                                    .setEnabled(false);

                              
                                            CoverageBean cdata = new CoverageBean();
                                			cdata.setStoreId(store_cd);
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
                                			cdata.setRights(database.getStoreRights(store_cd));
                                			cdata.setImage(img_str);
                                			cdata.setRemark("");
                                			database.InsertCoverageData(cdata);
                                            

                                			Intent in=new Intent(StoreimageActivity.this,DailyEntryMenu.class);

                                            startActivity(in);

                                            finish();


                                        }
                                    })
                            .setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(
                                                DialogInterface dialog,
                                                int id) {
                                            dialog.cancel();
                                        }
                                    });

                    alert = builder.create();
                    alert.show();

                }
                else {
                    Toast.makeText(getApplicationContext(),
                            "Please click the image", Toast.LENGTH_SHORT).show();

                }

                break;

        }

    }

    protected void startCameraActivity() {

        try {
            Log.i("MakeMachine", "startCameraActivity()");
            File file = new File(_path);
            Uri outputFileUri = Uri.fromFile(file);

            Intent intent = new Intent(
                    android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
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
                _pathforcheck = "";
                break;

            case -1:

                if (_pathforcheck != null && !_pathforcheck.equals("")) {
                    if (new File(str + _pathforcheck).exists()) {

                        //img_cam.setBackgroundResource(R.drawable.camera_list_tick);

                        // Decode the filepath with BitmapFactory followed by the position
                        Bitmap bmp = BitmapFactory.decodeFile(str + _pathforcheck);

                        // Set the decoded bitmap into ImageView
                        img_cam.setImageBitmap(bmp);

                        img_clicked.setVisibility(View.GONE);
                        img_cam.setVisibility(View.VISIBLE);

                        //Set Clicked image to Imageview

                        //_pathforcheck = "";

                        img_str = _pathforcheck;

                        //Toast.makeText(getApplicationContext(), ""+image1, Toast.LENGTH_LONG).show();

                    }
                }

                break;
        }


        super.onActivityResult(requestCode, resultCode, data);
    }


    public String getCurrentTime() {

        Calendar m_cal = Calendar.getInstance();

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String cdate = formatter.format(m_cal.getTime());

       /* String intime = m_cal.get(Calendar.HOUR_OF_DAY) + ":"
                + m_cal.get(Calendar.MINUTE) + ":" + m_cal.get(Calendar.SECOND);*/

        return cdate;

    }

  


    public void  deleteImages(){

        AlertDialog.Builder builder = new AlertDialog.Builder(
                StoreimageActivity.this);
        builder.setMessage("All saved data of this store will be lost")
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog,
                                    int id) {

                                alert.getButton(
                                        AlertDialog.BUTTON_POSITIVE)
                                        .setEnabled(false);

                                if (img_str !=null) {


                                    File file1= new File(CommonString.FILE_PATH+ img_str);
                                    if(file1.exists())
                                    {
                                        file1.delete();
                                    }
                                }

                                finish();

                                                           }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog,
                                    int id) {
                                dialog.cancel();
                            }
                        });

        alert = builder.create();
        alert.show();



    }

    }
