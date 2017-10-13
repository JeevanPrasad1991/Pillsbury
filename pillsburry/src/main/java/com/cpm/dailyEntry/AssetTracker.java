package com.cpm.dailyEntry;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import com.cpm.Constants.CommonString;

import com.cpm.database.pillsbury_Database;
import com.cpm.delegates.AssetTrackerBean;
import com.cpm.delegates.CoverageBean;
import com.cpm.delegates.OpeningStockBean;
import com.cpm.pillsbury.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.AbsListView.OnScrollListener;

public class AssetTracker extends Activity implements OnClickListener {

    private SharedPreferences preferences;
    private String store_id, username, intime, visit_date;
    private pillsbury_Database database;
    String reason_id, reason;
    Boolean update = false;
    ExpandableListView lv;
    Button save_btn;
    String str1;
    static int mposition = -1;
    protected static String _pathforcheck = "";
    Helper help = new Helper();
    private ArrayAdapter<CharSequence> reason_adapter;
    protected String _path;
    List<AssetTrackerBean> listDataHeader;
    HashMap<AssetTrackerBean, List<AssetTrackerBean>> listDataChild;
    List<AssetTrackerBean> save_listDataHeader;
    private HashMap<AssetTrackerBean, List<AssetTrackerBean>> _listDataChild;
    private static String str;
    static int grp_position = -1;
    static int child_position = -1;
    String image1 = "", img1 = "";

    ExpandableListAdapter listAdapter;
    private static ArrayList<AssetTrackerBean> compliance_list = new ArrayList<AssetTrackerBean>();
    private static ArrayList<AssetTrackerBean> sub_compliance_list = new ArrayList<AssetTrackerBean>();
    private static ArrayList<AssetTrackerBean> reasonList = new ArrayList<AssetTrackerBean>();
    boolean validation_flag = true;
    List<Integer> validate_header = new ArrayList<>();
    String Error_Message;
    RelativeLayout relativeLayout;
    LinearLayout nodata_layout;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asset_tracker);
        lv = (ExpandableListView) findViewById(R.id.lvExp);
        save_btn = (Button) findViewById(R.id.save_btn);
        relativeLayout = (RelativeLayout) findViewById(R.id.asset_rl);
        nodata_layout = (LinearLayout) findViewById(R.id.no_data_lay);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        visit_date = preferences.getString(CommonString.KEY_VISIT_DATE, null);
        preferences.getString(CommonString.KEY_STORE_NAME, "");
        intime = preferences.getString(CommonString.KEY_STORE_IN_TIME, "");
        username = preferences.getString(CommonString.KEY_USERNAME, "");
        store_id = preferences.getString(CommonString.KEY_STORE_ID, "");
        System.out.println("The store_id is:" + store_id);
        str = Environment.getExternalStorageDirectory() + "/Pillsbury_Images/";
        database = new pillsbury_Database(this);
        database.open();
        prepareListData();


        if (listDataHeader.size() > 0) {
            nodata_layout.setVisibility(View.INVISIBLE);
            relativeLayout.setVisibility(View.VISIBLE);
        } else {
            nodata_layout.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.INVISIBLE);
        }

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        lv.setAdapter(listAdapter);

        save_btn.setOnClickListener(this);

        lv.setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }

            @Override
            public void onScrollStateChanged(AbsListView arg0, int scrollState) {
                //lv.invalidateViews();
                if (SCROLL_STATE_TOUCH_SCROLL == scrollState) {
                    View currentFocus = getCurrentFocus();
                    if (currentFocus != null) {
                        currentFocus.clearFocus();
                    }
                }
            }

        });

        reasonList = database.getAssetReasonData();

    }


    private void prepareListData() {

        listDataHeader = new ArrayList<AssetTrackerBean>();
        listDataChild = new HashMap<AssetTrackerBean, List<AssetTrackerBean>>();
        compliance_list = database.getInsertedAssetComplianceData(store_id);
        if (compliance_list.size() == 0) {
            compliance_list = database.getAssetTrackData(store_id);
            for (int i2 = 0; i2 < compliance_list.size(); i2++) {
                AssetTrackerBean chk = new AssetTrackerBean();
                chk.setBrand(compliance_list.get(i2).getBrand());
                chk.setBrand_cd(compliance_list.get(i2).getBrand_cd());
                listDataHeader.add(chk);
                sub_compliance_list = database.getAssetChildListData(compliance_list.get(i2).getBrand_cd(), store_id);
                for (int i = 0; i < sub_compliance_list.size(); i++) {
                    sub_compliance_list.get(i).setRemark("");
                    sub_compliance_list.get(i).setAsset_avail("NO");
                    sub_compliance_list.get(i).setImage("");
                    sub_compliance_list.get(i).setReasonid("0");
                    sub_compliance_list.get(i).setReason("");
                }
                listDataChild.put(chk, sub_compliance_list);
            }

        } else {

            //in update i have show the Asset Count

            update = true;
            save_btn.setText("Update");

            for (int i2 = 0; i2 < compliance_list.size(); i2++) {
                AssetTrackerBean chk = new AssetTrackerBean();
                chk.setBrand(compliance_list.get(i2).getBrand());
                chk.setBrand_cd(compliance_list.get(i2).getBrand_cd());
                listDataHeader.add(chk);
                sub_compliance_list = database.getInsertedAssetSubList(compliance_list.get(i2).getCommonId());
                listDataChild.put(chk, sub_compliance_list);
            }
        }
    }


    public String getCurrentDate() {

        Calendar m_cal = Calendar.getInstance();

        int month = m_cal.get(Calendar.MONTH);

        String currmonth = Integer.toString(month + 1);

        if (month <= 9) {

            currmonth = "0" + currmonth;
        } else {

            currmonth = currmonth;

        }

        String currentdate = currmonth + "_" + m_cal.get(Calendar.DAY_OF_MONTH)
                + "_" + m_cal.get(Calendar.YEAR);

        return currentdate;

    }


    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        //super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to quit ?").setCancelable(false).setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent in = new Intent(AssetTracker.this, DailyEntryMenu.class);
                        startActivity(in);
                        AssetTracker.this.finish();

                    }
                }).setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();

        alert.show();
    }

    private class ExpandableListAdapter extends BaseExpandableListAdapter {
        private Context _context;

        public ExpandableListAdapter(Context context,
                                     List<AssetTrackerBean> listDataHeader,
                                     HashMap<AssetTrackerBean, List<AssetTrackerBean>> listChildData) {
            this._context = context;
            save_listDataHeader = listDataHeader;
            _listDataChild = listChildData;
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return _listDataChild.get(save_listDataHeader.get(groupPosition)).get(childPosition);
        }


        @Override
        public View getChildView(final int groupPosition,
                                 final int childPosition, boolean isLastChild, View convertView,
                                 ViewGroup parent) {

            final AssetTrackerBean childText = (AssetTrackerBean) getChild(groupPosition, childPosition);

            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.asset_trackerlistview, null);
            }


            TextView sku_name = (TextView) convertView.findViewById(R.id.sku_name);
            TextView asset_name = (TextView) convertView.findViewById(R.id.asset_name);
            TextView asset_count = (TextView) convertView.findViewById(R.id.count);
            EditText remark = (EditText) convertView.findViewById(R.id.remark_text);
            EditText actualCount = (EditText) convertView.findViewById(R.id.actualCount);
            LinearLayout rea = (LinearLayout) convertView.findViewById(R.id.reasonLayout);
            LinearLayout cameraLayt = (LinearLayout) convertView.findViewById(R.id.cameraLayout);
            final LinearLayout remarkl = (LinearLayout) convertView.findViewById(R.id.remarkLayout);
            Spinner reasonSpinner = (Spinner) convertView.findViewById(R.id.reason);
            ImageView camera = (ImageView) convertView.findViewById(R.id.camera);
            final ToggleButton asset_avail = (ToggleButton) convertView.findViewById(R.id.asset_avail);
            camera.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    grp_position = groupPosition;
                    child_position = childPosition;
                    _pathforcheck = store_id + "_" + getCurrentDate().replace("/", "")
                            + _listDataChild
                            .get(save_listDataHeader.get(groupPosition))
                            .get(childPosition).getAsset_cd() + "_" + getCurrentTime().replace(":", "") + ".jpg";

                    _path = str + _pathforcheck;
                    startCameraActivity();

                }
            });


            reason_adapter = new ArrayAdapter<CharSequence>(AssetTracker.this,
                    android.R.layout.simple_spinner_item);

            reason_adapter.add("Select Reason");

            for (int i = 0; i < reasonList.size(); i++) {
                reason_adapter.add(reasonList.get(i).getReason());
            }

            reasonSpinner.setAdapter(reason_adapter);
            reason_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            reasonSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position != 0) {

                        reason = parent.getItemAtPosition(position).toString();
                        reason_id = reasonList.get(position - 1).getReasonid();
                        _listDataChild.get(save_listDataHeader.get(groupPosition)).get(childPosition).setReason(reason);
                        _listDataChild.get(save_listDataHeader.get(groupPosition)).get(childPosition).setReasonid(reason_id);
                        if (_listDataChild.get(save_listDataHeader.get(groupPosition)).get(childPosition).getReason().equalsIgnoreCase("others")) {
                            remarkl.setVisibility(View.VISIBLE);
                        } else {
                            remarkl.setVisibility(View.GONE);
                            //lv.invalidateViews();
                            lv.clearFocus();
                        }


                    } else {

                        _listDataChild.get(save_listDataHeader.get(groupPosition)).get(childPosition).setReasonid("");

                        _listDataChild.get(save_listDataHeader.get(groupPosition)).get(childPosition).setReason("");


                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {


                }
            });


            remark.setText(childText.getRemark());

            remark.setOnFocusChangeListener(new OnFocusChangeListener() {
                public void onFocusChange(View v, boolean hasFocus) {

                    if (!hasFocus) {

                        final EditText Caption = (EditText) v;
                        String value1 = Caption.getText().toString().replaceAll("[&+!^?*#:<>{}'%$]", "");

                        if (value1.equals("")) {

                            _listDataChild
                                    .get(save_listDataHeader.get(groupPosition)).get(childPosition).setRemark("");

                        } else {

                            _listDataChild
                                    .get(save_listDataHeader.get(groupPosition)).get(childPosition).setRemark(value1);
                        }

                    }
                }
            });

            actualCount.setText(childText.getActualCount());

            actualCount.setOnFocusChangeListener(new OnFocusChangeListener() {

                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        final EditText caption = (EditText) v;
                        String value1 = caption.getText().toString().replaceAll("[&+!^?*#:<>{}'%$]", "");
                        ;
                        if (value1.equals("")) {
                            _listDataChild.get(save_listDataHeader.get(groupPosition)).get(childPosition).setActualCount("");

                        } else {
                            _listDataChild.get(save_listDataHeader.get(groupPosition)).get(childPosition).setActualCount(value1);
                        }
                    }

                }
            });

            asset_avail.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    String val = asset_avail.getText().toString();
                    _listDataChild.get(save_listDataHeader.get(groupPosition)).get(childPosition).setAsset_avail(val);
                    lv.invalidateViews();
                }
            });


            if (!img1.equalsIgnoreCase("")) {
                if (childPosition == child_position && groupPosition == grp_position) {
                    _listDataChild
                            .get(listDataHeader.get(groupPosition)).get(childPosition).setImage(img1);
                    img1 = "";
                }
            }
            if (childText.getAsset_avail().equalsIgnoreCase("No")) {
                asset_avail.setChecked(false);
                rea.setVisibility(View.VISIBLE);
                cameraLayt.setVisibility(View.GONE);
                _listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).setImage("");
            } else {
                asset_avail.setChecked(true);
                rea.setVisibility(View.GONE);
                cameraLayt.setVisibility(View.VISIBLE);
                _listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).setReasonid("0");
                _listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).setReason("");
                if (_listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).getImage() != null && !_listDataChild
                        .get(listDataHeader.get(groupPosition))
                        .get(childPosition).getImage().equals("")) {
                    camera.setBackgroundResource(R.drawable.camera_list_tick);
                } else {
                    camera.setBackgroundResource(R.drawable.camera_list);
                }

            }


            str1 = _listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).getReasonid();


            for (int i = 0; i < reasonList.size(); i++) {
                if (reasonList.get(i).getReasonid().equals(str1)) {

                    reasonSpinner.setSelection(i + 1);
//						reasonSpinner.setBackgroundColor(getResources().getColor(R.color.green));
                    break;

                } else if (str1 == null) {
                    reasonSpinner.setSelection(0);
                    break;
                }


            }


            if (childText.getReasonid().equalsIgnoreCase("4")) {
                remarkl.setVisibility(View.VISIBLE);

            } else {
                remarkl.setVisibility(View.GONE);
                _listDataChild
                        .get(listDataHeader.get(groupPosition))
                        .get(childPosition).setRemark("");
            }


//			brand_name.setText(childText.getBRAND());
//			sku_name.setText(childText.getSku_name());

            asset_name.setText(childText.getAsset_name());
            asset_count.setText(childText.getCount());


            asset_avail.setText(_listDataChild.get(save_listDataHeader.get(groupPosition))
                    .get(childPosition).getAsset_avail());


            if (!validation_flag) {
                if (actualCount.getText().toString().equalsIgnoreCase("")) {
                    actualCount.setBackgroundColor(Color.parseColor("#FF0000"));
                } else {
                    actualCount.setBackgroundColor(Color.parseColor("#00BFFF"));
                }
                if (remark.getText().toString().equalsIgnoreCase("")) {
                    remark.setBackgroundColor(Color.parseColor("#FF0000"));
                } else {
                    remark.setBackgroundColor(Color.parseColor("#00BFFF"));
                }
            }

            return convertView;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        @Override
        public int getGroupCount() {
            return save_listDataHeader.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return _listDataChild.get(save_listDataHeader.get(groupPosition)).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return save_listDataHeader.get(groupPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            final AssetTrackerBean headerTitle = (AssetTrackerBean) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.brand, null);

            }

            TextView brand_name = (TextView) convertView.findViewById(R.id.brand_name);
            LinearLayout layout = (LinearLayout) convertView.findViewById(R.id.first);
            if (!validation_flag) {
                if (validate_header.contains(groupPosition)) {
                    layout.setBackgroundColor(Color.RED);
                } else {
                    layout.setBackgroundColor(Color.parseColor("#CCCCCC"));
                }
            }

            brand_name.setText(headerTitle.getBrand());
            isExpanded = true;
            return convertView;


        }
    }


    public String getCurrentTime() {

        Calendar m_cal = Calendar.getInstance();
        String intime = m_cal.get(Calendar.HOUR_OF_DAY) + ":" + m_cal.get(Calendar.MINUTE) + ":" + m_cal.get(Calendar.SECOND);

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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.save_btn) {
            lv.clearFocus();
            if (validateData(listDataChild, listDataHeader)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure you want to save")
                        .setCancelable(false)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(
                                            DialogInterface dialog, int id) {
                                        database.open();
                                        database.deleteAssetData(store_id);
                                        database.InsertAssetData(getMid(), store_id, listDataChild, listDataHeader);
                                        Toast.makeText(getApplicationContext(), "Data has been saved", Toast.LENGTH_SHORT).show();
                                        Intent DailyEntryMenu = new Intent(AssetTracker.this, DailyEntryMenu.class);
                                        startActivity(DailyEntryMenu);

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
            } else {

                Toast.makeText(getApplicationContext(), "Please select a reason or Fill the Remarks or click an image or fill actual quantity ", Toast.LENGTH_SHORT).show();
                lv.invalidateViews();
            }


        }

    }

    boolean validateData(HashMap<AssetTrackerBean, List<AssetTrackerBean>> listDataChild2,
                         List<AssetTrackerBean> listDataHeader2) {
        boolean flag = true;
        validate_header.clear();

        for (int i = 0; i < listDataHeader2.size(); i++) {
            for (int j = 0; j < listDataChild2.get(listDataHeader.get(i)).size(); j++) {

                String assetAvail = listDataChild.get(listDataHeader.get(i)).get(j).getAsset_avail();
                String reason_id = listDataChild.get(listDataHeader.get(i)).get(j).getReasonid();
                String remarks = listDataChild.get(listDataHeader.get(i)).get(j).getRemark();
                String image = listDataChild.get(listDataHeader.get(i)).get(j).getImage();
                String actual = listDataChild.get(listDataHeader.get(i)).get(j).getActualCount();


                if (assetAvail.equalsIgnoreCase("NO")) {
                    if (reason_id.equalsIgnoreCase("0") || reason_id.equalsIgnoreCase("")) {
                        flag = false;
                        validation_flag = false;
                        Error_Message = "Please select reason";
                        validate_header.add(i);

                        //break;
                    } else if (actual.isEmpty() || actual.equalsIgnoreCase("")) {
                        flag = false;
                        validation_flag = false;
                        Error_Message = "Please fill data in actual";
                        break;
                    }
                    /*else if (){
                        flag = false;
						validation_flag = false;
						break;
					}*/
                    //if (actual){}
                    /*if (reason_id.equalsIgnoreCase("0")) {
						flag = false;
						break;
						
					}*/
                    else if (reason_id.equalsIgnoreCase("4")) {

                        if (remarks.equalsIgnoreCase("")) {
                            flag = false;
                            validation_flag = false;
                            Error_Message = "Please fill data in remark";
                            //break;
                        } else if (actual.isEmpty() || actual.equalsIgnoreCase("")) {
                            flag = false;
                            validation_flag = false;
                            Error_Message = "Please fill data in actual";
                            break;

                        }

                    } else {

                        flag = true;
                    }
                } else if (assetAvail.equalsIgnoreCase("YES")) {
                    if (image.equalsIgnoreCase("")) {
                        flag = false;

                        //break;

                    } else if (actual.isEmpty() || actual.equalsIgnoreCase("")) {
                        flag = false;
                        validation_flag = false;
                        Error_Message = "Please fill data in actual";
                    }
					/*else if (remarks.equalsIgnoreCase("")){
						flag = false;
						validation_flag = false;
						Error_Message="Please fill data in remark";
						break;
					}*/
                    else {
                        flag = true;
                    }
                }


            }

            if (flag == false) {
                validation_flag = false;
                break;
            }

        }
        //lv.invalidateViews();
        return flag;

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

                        //cam.setBackgroundResource(R.drawable.camera_list_tick);
                        image1 = _pathforcheck;
                        img1 = _pathforcheck;
                        lv.invalidateViews();
                        _pathforcheck = "";
                        //Toast.makeText(getApplicationContext(), ""+image1, Toast.LENGTH_LONG).show();

                    }
                }

                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
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


}
