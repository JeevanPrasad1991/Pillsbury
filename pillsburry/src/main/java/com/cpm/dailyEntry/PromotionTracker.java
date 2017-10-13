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
import com.cpm.delegates.PromotionTrackingBean;
import com.cpm.pillsbury.R;
import com.cpm.xmlGetterSetter.MappingPromotionpostgetterSetter;

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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
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
import android.widget.AdapterView.OnItemSelectedListener;

public class PromotionTracker extends Activity implements OnClickListener {

    private SharedPreferences preferences;
    private String store_id, username, intime, visit_date;
    private pillsbury_Database database;
    protected static String _pathforcheck = "";
    Boolean update = false;
    String reason_id, reason;
    String str1;
    ExpandableListView lv;
    Button save_btn;
    boolean result;
    protected String _path;
    private ArrayAdapter<CharSequence> reason_adapter;
    List<MappingPromotionpostgetterSetter> listDataHeader;
    HashMap<MappingPromotionpostgetterSetter, List<MappingPromotionpostgetterSetter>> listDataChild;
    List<MappingPromotionpostgetterSetter> save_listDataHeader;
    private HashMap<MappingPromotionpostgetterSetter, List<MappingPromotionpostgetterSetter>> _listDataChild;
    private String str;
    static int grp_position = -1;
    static int child_position = -1;
    String image1 = "", img1 = "";
    ExpandableListAdapter listAdapter;
    private ArrayList<MappingPromotionpostgetterSetter> compliance_list = new ArrayList<>();
    private ArrayList<MappingPromotionpostgetterSetter> sub_compliance_list = new ArrayList<>();
    private ArrayList<PromotionTrackingBean> reasonList = new ArrayList<>();
    boolean validation_flag = true;
    List<Integer> validate_header = new ArrayList<>();
    RelativeLayout relativeLayout;
    LinearLayout nodata_layout;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promotion_tracker);
        // view1 = getLayoutInflater().inflate(R.layout.nodata_layout, null);

        lv = (ExpandableListView) findViewById(R.id.lvExp);
        save_btn = (Button) findViewById(R.id.save_btn);
        // nodata_alert = (TextView) findViewById(R.id.alert);
        relativeLayout = (RelativeLayout) findViewById(R.id.layout_rl);
        nodata_layout = (LinearLayout) findViewById(R.id.no_data_lay);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        visit_date = preferences.getString(CommonString.KEY_VISIT_DATE, null);
        preferences.getString(CommonString.KEY_STORE_NAME, "");
        username = preferences.getString(CommonString.KEY_USERNAME, "");
        store_id = preferences.getString(CommonString.KEY_STORE_ID, "");
        intime = preferences.getString(CommonString.KEY_STORE_IN_TIME, "");
        System.out.println("The store_id is:" + store_id);
        str = Environment.getExternalStorageDirectory() + "/Pillsbury_Images/";
        database = new pillsbury_Database(this);
        database.open();
        result = prepareListData();
        if (listDataHeader.size() > 0) {
            listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
            // setting list adapter
            lv.setAdapter(listAdapter);
            nodata_layout.setVisibility(View.INVISIBLE);
            relativeLayout.setVisibility(View.VISIBLE);
        } else {
            nodata_layout.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.INVISIBLE);
        }

        if (result == false) {

            lv.expandGroup(0);
        } else {
        }
        save_btn.setOnClickListener(this);
        lv.setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
            @Override
            public void onScrollStateChanged(AbsListView arg0, int arg1) {
                lv.clearFocus();
                if (SCROLL_STATE_TOUCH_SCROLL == arg1) {
                    View currentFocus = getCurrentFocus();
                    if (currentFocus != null) {
                        currentFocus.clearFocus();
                    }
                }
                //lv.invalidateViews();
            }
        });
        reasonList = database.getPromotionReasonData();
    }

    private boolean prepareListData() {
        boolean result = false;
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<MappingPromotionpostgetterSetter, List<MappingPromotionpostgetterSetter>>();
        compliance_list = database.getInsertedPromotionData(store_id);
        Log.d("list", compliance_list.toString());
        if (compliance_list.size() == 0) {
            compliance_list = database.getPromotionDefaultData(store_id);
            if (compliance_list.size() == 0) {
                result = true;
            }
            for (int i2 = 0; i2 < compliance_list.size(); i2++) {
                MappingPromotionpostgetterSetter chk = new MappingPromotionpostgetterSetter();
                chk.setSKU((compliance_list.get(i2).getSKU()).get(0));
                chk.setSKU_CD(compliance_list.get(i2).getSKU_CD().get(0));
                listDataHeader.add(chk);
                sub_compliance_list = database.getPromotionChildListDefaltData(store_id, compliance_list.get(i2).getSKU_CD().get(0));
                //sub_compliance_list = database.getPromotionChildListData(compliance_list.get(i2).getBrand_cd(), store_id);
                for (int i = 0; i < sub_compliance_list.size(); i++) {
                    sub_compliance_list.get(i).setRemark("");
                    sub_compliance_list.get(i).setPromotion_avail("NO");
                    sub_compliance_list.get(i).setImage("");
                    sub_compliance_list.get(i).setReasonid("0");
                    sub_compliance_list.get(i).setReason("");

                }

                listDataChild.put(chk, sub_compliance_list);
            }
        } else {

            update = true;
            save_btn.setText("Update");
            for (int i2 = 0; i2 < compliance_list.size(); i2++) {
                MappingPromotionpostgetterSetter chk = new MappingPromotionpostgetterSetter();
                chk.setSKU_CD(compliance_list.get(i2).getSKU_CD().get(0));
                chk.setSKU(compliance_list.get(i2).getSKU().get(0));
                listDataHeader.add(chk);
                sub_compliance_list = database.getInsertedPromotionDefaltSubList(compliance_list.get(i2).getCommonId());
                listDataChild.put(chk, sub_compliance_list);


                //sub_compliance_list = database.getInsertedPromotionDefaltSubList(compliance_list.get(i2).getSTORE_CD().get(0));

            }
        }


        return result;
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

        String currentdate = currmonth + "_" + m_cal.get(Calendar.DAY_OF_MONTH) + "_" + m_cal.get(Calendar.YEAR);

        return currentdate;

    }


    private class ExpandableListAdapter extends BaseExpandableListAdapter {
        private Context _context;
        public ExpandableListAdapter(Context context, List<MappingPromotionpostgetterSetter> listDataHeader, HashMap<MappingPromotionpostgetterSetter, List<MappingPromotionpostgetterSetter>> listChildData) {
            this._context = context;
            save_listDataHeader = listDataHeader;
            _listDataChild = listChildData;
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return _listDataChild.get(save_listDataHeader.get(groupPosition)).get(childPosition);
        }

        @Override
        public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            final MappingPromotionpostgetterSetter childText = (MappingPromotionpostgetterSetter) getChild(groupPosition, childPosition);

            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.promotion_trackinglistview, null);
            }
            TextView sku_name = (TextView) convertView.findViewById(R.id.sku_name);
            TextView promotion_name = (TextView) convertView.findViewById(R.id.promotion_name);
            final EditText remark = (EditText) convertView.findViewById(R.id.remark_text);
            TextView description = (TextView) convertView.findViewById(R.id.description);
            final ToggleButton promotion_avail = (ToggleButton) convertView.findViewById(R.id.promotion_avail);
            LinearLayout cameraLayt = (LinearLayout) convertView.findViewById(R.id.cameraLayout);
            final LinearLayout remarkl = (LinearLayout) convertView.findViewById(R.id.remarkLayout);
            final Spinner reasonSpinner = (Spinner) convertView.findViewById(R.id.reason);
            ImageView camera = (ImageView) convertView.findViewById(R.id.camera);
            LinearLayout rea = (LinearLayout) convertView.findViewById(R.id.reasonLayout);
            reason_adapter = new ArrayAdapter<CharSequence>(PromotionTracker.this, android.R.layout.simple_spinner_item);
            reason_adapter.add("Select Reason");
            for (int i = 0; i < reasonList.size(); i++) {
                reason_adapter.add(reasonList.get(i).getReason());
            }

            reasonSpinner.setAdapter(reason_adapter);
            reason_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            reasonSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    if (position != 0) {
                        reason = parent.getItemAtPosition(position).toString();
                        reason_id = reasonList.get(position - 1).getReasonid();
                        _listDataChild.get(save_listDataHeader.get(groupPosition)).get(childPosition).setReason(reason);
                        _listDataChild.get(save_listDataHeader.get(groupPosition)).get(childPosition).setReasonid(reason_id);
                        if (_listDataChild.get(save_listDataHeader.get(groupPosition)).get(childPosition).getReason().equalsIgnoreCase("others")) {
                            remarkl.setVisibility(View.VISIBLE);
                        } else {
                            remarkl.setVisibility(View.GONE);
                        }
                        // reasonSpinner.clearFocus();
                        lv.clearFocus();


                    } else {
                        _listDataChild.get(save_listDataHeader.get(groupPosition)).get(childPosition).setReasonid("0");

                        _listDataChild.get(save_listDataHeader.get(groupPosition)).get(childPosition).setReason("");
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {


                }

            });

            camera.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    grp_position = groupPosition;
                    child_position = childPosition;
                    _pathforcheck = store_id + "_" + getCurrentDate().replace("/", "") +
                            _listDataChild.get(save_listDataHeader.get(groupPosition)).get(childPosition).getSKU_CD().get(0) + getCurrentTime().replace(":", "") + ".jpg";
                    _path = str + _pathforcheck;
                    startCameraActivity();

                }
            });


            remark.setOnFocusChangeListener(new OnFocusChangeListener() {
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        final EditText Caption = (EditText) v;
                        String value1 = Caption.getText().toString().replaceAll("[&+!^?*#:<>{}'%$]", "");
                        if (value1.equals("")) {

                            _listDataChild.get(save_listDataHeader.get(groupPosition)).get(childPosition).setRemark("");
                            // childText.

                        } else {

                            _listDataChild.get(save_listDataHeader.get(groupPosition)).get(childPosition).setRemark(value1);

                        }

                    }
                }
            });

            remark.setText(childText.getRemark());
            promotion_avail.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    String val = promotion_avail.getText().toString();
                    _listDataChild.get(save_listDataHeader.get(groupPosition)).get(childPosition).setPromotion_avail(val);
                    lv.clearFocus();
                    lv.invalidateViews();

                }
            });

            if (!img1.equalsIgnoreCase("")) {
                if (childPosition == child_position && groupPosition == grp_position) {
                    _listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).setImage(img1);
                    img1 = "";


                }
            }

            if (childText.getPromotion_avail().equalsIgnoreCase("NO")) {
                promotion_avail.setChecked(false);
                rea.setVisibility(View.VISIBLE);
                cameraLayt.setVisibility(View.GONE);
                _listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).setImage("");


            } else {
                promotion_avail.setChecked(true);
                rea.setVisibility(View.GONE);
                cameraLayt.setVisibility(View.VISIBLE);
                _listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).setReasonid("0");
                _listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).setReason("");
                if (_listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).getImage() != null &&
                        !_listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).getImage().equals("")) {
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
                _listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).setRemark("");
            }
            //	brand_name.setText(childText.getBRAND());
            //description.setText(childText.getDescription());
            //sku_name.setText(childText.getSku_name());
            promotion_name.setText(childText.getPROMOTION().toString());
            promotion_avail.setText(_listDataChild.get(save_listDataHeader.get(groupPosition)).get(childPosition).getPromotion_avail());


            if (!validation_flag) {
                if (remark.getText().toString().equalsIgnoreCase("")) {
                    remark.setBackgroundColor(Color.parseColor("#FF0000"));
                } else {
                    remark.setBackgroundColor(Color.parseColor("#00BFFF"));
                }

            }

            // remark.setId(childPosition);

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
            final MappingPromotionpostgetterSetter headerTitle = (MappingPromotionpostgetterSetter) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.brand, null);
            }
            TextView brand_name = (TextView) convertView.findViewById(R.id.brand_name);
            brand_name.setText(headerTitle.getSKU().get(0));

            LinearLayout layout = (LinearLayout) convertView.findViewById(R.id.first);
            if (!validation_flag) {
                if (validate_header.contains(groupPosition)) {
                    layout.setBackgroundColor(Color.RED);
                } else {
                    layout.setBackgroundColor(Color.parseColor("#CCCCCC"));
                }
            }
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
            lv.invalidateViews();
            lv.clearFocus();
            if (validateData(listDataChild, listDataHeader)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure you want to save")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog, int id) {
                                database.open();
                                database.deletePromotionData(store_id);
                                database.InsertPromotionData(getMid(), store_id, listDataChild, listDataHeader);
                                Toast.makeText(getApplicationContext(), " Data has been saved", Toast.LENGTH_SHORT).show();
                                Intent DailyEntryMenu = new Intent(PromotionTracker.this, DailyEntryMenu.class);
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
                Toast.makeText(getApplicationContext(), "Please Select a Reason Or Fill the Remarks or click an image ", Toast.LENGTH_SHORT).show();
                // lv.invalidateViews();
                lv.clearFocus();
            }


        }

    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to quit ?").setCancelable(false).setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent in = new Intent(PromotionTracker.this, DailyEntryMenu.class);
                        startActivity(in);
                        PromotionTracker.this.finish();
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

            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

            startActivityForResult(intent, 0);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    boolean validateData(HashMap<MappingPromotionpostgetterSetter, List<MappingPromotionpostgetterSetter>> listDataChild2,
                         List<MappingPromotionpostgetterSetter> listDataHeader2) {
        boolean flag = true;
        validate_header.clear();
        for (int i = 0; i < listDataHeader2.size(); i++) {
            for (int j = 0; j < listDataChild2.get(listDataHeader.get(i)).size(); j++) {
                String promotionAvail = listDataChild.get(listDataHeader.get(i)).get(j).getPromotion_avail();
                String reason_id = listDataChild.get(listDataHeader.get(i)).get(j).getReasonid();
                String remarks = listDataChild.get(listDataHeader.get(i)).get(j).getRemark();
                String image = listDataChild.get(listDataHeader.get(i)).get(j).getImage();
                // String actual = listDataChild.get(listDataHeader.get(i)).get(j).;
                if (promotionAvail.equalsIgnoreCase("NO")) {
                    if (reason_id.equalsIgnoreCase("0")) {
                        flag = false;
                        validate_header.add(i);
                        break;
                    } else if (reason_id.equalsIgnoreCase("4")) {
                        if (remarks.equalsIgnoreCase("")) {
                            flag = false;
                            validation_flag = false;
                            break;
                        }
                    } else {
                        flag = true;
                    }
                } else if (promotionAvail.equalsIgnoreCase("YES")) {
                    if (image.equalsIgnoreCase("")) {
                        flag = false;
                        break;

                    } else {
                        flag = true;
                    }
                }
            }
            if (flag == false) {
                validation_flag = false;
                break;
            }

        }
        return flag;
    }
}
