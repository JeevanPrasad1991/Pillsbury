package com.cpm.dailyEntry;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cpm.Constants.CommonString;
import com.cpm.database.pillsbury_Database;
import com.cpm.delegates.CoverageBean;
import com.cpm.delegates.ReasonModel;
import com.cpm.delegates.StoreBean;
import com.cpm.pillsbury.R;

public class NonWorkingReason extends Activity implements OnItemSelectedListener, OnClickListener {
    ArrayList<ReasonModel> reasondata = new ArrayList<ReasonModel>();
    private Spinner reasonspinner;
    private pillsbury_Database database;
    String reasonname, reasonid, image, entry, reason_reamrk, intime;
    Button camera, save;
    private ArrayAdapter<CharSequence> reason_adapter;
    protected String _path, str;
    protected String _pathforcheck = "";
    private ArrayList<StoreBean> storedata = new ArrayList<StoreBean>();
    private String image1 = "";
    private ArrayList<CoverageBean> cdata = new ArrayList<CoverageBean>();
    protected boolean _taken;
    protected static final String PHOTO_TAKEN = "photo_taken";
    private SharedPreferences preferences;
    String _UserId, visit_date, store_id;
    protected boolean status = true;
    EditText text;
    AlertDialog alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nonworking);
        reasonspinner = (Spinner) findViewById(R.id.spinner2);
        camera = (Button) findViewById(R.id.picbtn1);
        save = (Button) findViewById(R.id.save);
        text = (EditText) findViewById(R.id.text);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        _UserId = preferences.getString(CommonString.KEY_USERNAME, "");
        visit_date = preferences.getString(CommonString.KEY_VISIT_DATE, null);
        store_id = preferences.getString(CommonString.KEY_STORE_ID, "");
        database = new pillsbury_Database(this);
        database.open();
        str = CommonString.FILE_PATH;
        reasondata = database.getReasonData();
        cdata = database.getCoverageData(visit_date, null);
        storedata = database.getStoreData(visit_date);
        intime = getCurrentTime();
        camera.setOnClickListener(this);
        save.setOnClickListener(this);
        reason_adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        reason_adapter.add("Select Reason");

        for (int i = 0; i < reasondata.size(); i++) {
            reason_adapter.add(reasondata.get(i).getReason());
        }

        reasonspinner.setAdapter(reason_adapter);
        reason_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        reasonspinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        Intent i = new Intent(this, StorelistActivity.class);
        startActivity(i);
        NonWorkingReason.this.finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
                               long arg3) {

        switch (arg0.getId()) {
            case R.id.spinner2:
                if (position != 0) {
                    reasonname = reasondata.get(position - 1).getReason();
                    reasonid = reasondata.get(position - 1).getReasonid();
                    image = reasondata.get(position - 1).getImage();
                    entry = reasondata.get(position - 1).getEntry();
                    reason_reamrk = reasondata.get(position - 1).getREASON_REMARK();
                    if (image != null) {
                        if (image.equalsIgnoreCase("true")) {
                            camera.setVisibility(View.VISIBLE);
                        } else {
                            camera.setVisibility(View.INVISIBLE);
                        }
                    }

                    if (reason_reamrk != null) {
                        if (reason_reamrk.equalsIgnoreCase("true")) {
                            text.setVisibility(View.VISIBLE);
                        } else {
                            text.setVisibility(View.INVISIBLE);
                        }
                    }
                } else {
                    reasonname = "";
                    reasonid = "";
                    image = "";
                    entry = "";
                    reason_reamrk = "";
                }
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

    protected void startCameraActivity() {

        try {
            Log.i("MakeMachine", "startCameraActivity()");
            File file = new File(_path);
            Uri outputFileUri = Uri.fromFile(file);

            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

            startActivityForResult(intent, 0);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("MakeMachine", "resultCode: " + resultCode);
        switch (resultCode) {
            case 0:
                Log.i("MakeMachine", "User cancelled");
                break;

            case -1:

                if (_pathforcheck != null && !_pathforcheck.equals("")) {
                    if (new File(str + _pathforcheck).exists()) {
                        camera.setBackgroundResource(R.drawable.camera_list_tick);
                        image1 = _pathforcheck;

                    }
                }

                break;
        }

    }

    public boolean imageAllowed() {
        boolean result = true;

        if (image.equalsIgnoreCase("true")) {

            if (image1.equalsIgnoreCase("")) {

                result = false;

            }
        }

        return result;

    }

    public boolean textAllowed() {
        boolean result = true;

        if (reasonid != null && !reasonid.equalsIgnoreCase("")) {
            if (text.getText().toString().equalsIgnoreCase("")) {
                result = false;
            }
        }

        return result;

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v.getId() == R.id.picbtn1) {
            _pathforcheck = store_id + "NonWorking" + _UserId + ".jpg";

            _path = Environment.getExternalStorageDirectory()
                    + "/WesternDigital_Images/" + _pathforcheck;

            startCameraActivity();
        }
        if (v.getId() == R.id.save) {

            if (validatedata()) {
                if (textAllowed()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(NonWorkingReason.this);
                    builder.setMessage("Do you want to save the data ")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialog,
                                        int id) {
                                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                                    CoverageBean cdata = new CoverageBean();
                                    cdata.setStoreId(store_id);
                                    cdata.setVisitDate(visit_date);
                                    cdata.setUserId(_UserId);
                                    cdata.setInTime(intime);
                                    cdata.setOutTime(getCurrentTime());
                                    cdata.setReason(reasonname);
                                    cdata.setReasonid(reasonid);
                                    cdata.setLatitude("0.0");
                                    cdata.setRights(database.getStoreRights(store_id));
                                    cdata.setLongitude("0.0");
                                    cdata.setImage(image1);
                                    cdata.setOutlook_Remark("0");
                                    cdata.setRemark(text
                                            .getText()
                                            .toString()
                                            .replaceAll(
                                                    "[&^<>{}'$]",
                                                    " "));
                                    cdata.setStatus(CommonString.STORE_STATUS_LEAVE);
                                    database.InsertCoverageData(cdata);
                                    database.updateStoreStatusOnLeave(store_id, visit_date, CommonString.STORE_STATUS_LEAVE);
                                    database.updateStoreStatusOnCheckout(store_id, visit_date, CommonString.KEY_L);
                                    if (entry == null) {
                                    } else {
                                        for (int i = 0; i < storedata.size(); i++) {
                                            if (database.CheckMid(visit_date, storedata.get(i).getStoreid()) > 0 || storedata
                                                    .get(i).getStatus().equalsIgnoreCase(CommonString.KEY_U)) {
                                            } else {
                                                cdata = new CoverageBean();
                                                cdata.setStoreId(storedata.get(i).getStoreid());
                                                cdata.setVisitDate(visit_date);
                                                cdata.setUserId(_UserId);
                                                cdata.setInTime(intime);
                                                cdata.setOutTime(getCurrentTime());
                                                cdata.setReason(reasonname);
                                                cdata.setReasonid(reasonid);
                                                cdata.setLatitude("0.0");
                                                cdata.setRights(database.getStoreRights(store_id));
                                                cdata.setLongitude("0.0");
                                                cdata.setOutlook_Remark("0");
                                                cdata.setImage(image1);
                                                cdata.setRemark(text
                                                        .getText()
                                                        .toString()
                                                        .replaceAll(
                                                                "[&^<>{}'$]", " "));
                                                cdata.setStatus(CommonString.STORE_STATUS_LEAVE);
                                                database.InsertCoverageData(cdata);
                                                database.updateStoreStatusOnLeave(storedata.get(i).getStoreid(), visit_date, CommonString.STORE_STATUS_LEAVE);
                                                database.updateStoreStatusOnCheckout(store_id, visit_date, CommonString.KEY_L);
                                            }
                                        }

                                    }
                                    SharedPreferences.Editor editor = preferences
                                            .edit();
                                    editor.putString(
                                            CommonString.KEY_STOREVISITED,
                                            "");
                                    editor.putString(
                                            CommonString.KEY_STOREVISITED_STATUS,
                                            "");
                                    editor.putString(
                                            CommonString.KEY_STORE_IN_TIME,
                                            "");
                                    editor.putString(
                                            CommonString.KEY_LATITUDE,
                                            "");
                                    editor.putString(
                                            CommonString.KEY_LONGITUDE,
                                            "");
                                    editor.commit();

                                    Intent intent = new Intent(
                                            getApplicationContext(),
                                            StorelistActivity.class);
                                    startActivity(intent);
                                    NonWorkingReason.this.finish();
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

                } else {
                    Toast.makeText(getApplicationContext(), "Please fill Remark !", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Please Select a Reason", Toast.LENGTH_SHORT).show();
            }

        }
    }


    public boolean validatedata() {
        boolean result = false;
        if (reasonid != null && !reasonid.equalsIgnoreCase("")) {
            result = true;
        }

        return result;

    }

    public String getCurrentTime() {
        Calendar m_cal = Calendar.getInstance();
        String intime = m_cal.get(Calendar.HOUR_OF_DAY) + ":" + m_cal.get(Calendar.MINUTE) + ":" + m_cal.get(Calendar.SECOND);
        return intime;

    }
}
