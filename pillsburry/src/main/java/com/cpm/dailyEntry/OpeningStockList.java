package com.cpm.dailyEntry;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.AbsListView.OnScrollListener;

import com.cpm.Constants.CommonString;
import com.cpm.database.pillsbury_Database;

import com.cpm.delegates.CoverageBean;
import com.cpm.delegates.MidDayRecievedStockBean;
import com.cpm.delegates.OpeningStockBean;
import com.cpm.pillsbury.R;

public class OpeningStockList extends Activity implements OnClickListener {

    private SharedPreferences preferences;
    private String store_id, username, intime, visit_date, loginDate,
            focus, stock_allow, facing_allow, pricing_allow,
            allow_shelf, allow_signage, allow_pog, allow_expiring, image_url;
    private pillsbury_Database database;
    Boolean update = false;
    ArrayList<OpeningStockBean> list = new ArrayList<OpeningStockBean>();
    static int row_pos = 10000;
    List<Integer> checkValidHeaderArray = new ArrayList<Integer>();
    List<Integer> checkValidChildArray = new ArrayList<Integer>();

    List<Integer> checkHeaderArray = new ArrayList<Integer>();
    ExpandableListView lv;
    Button save_btn;
    Helper help = new Helper();

    List<OpeningStockBean> listDataHeader;
    HashMap<OpeningStockBean, List<OpeningStockBean>> listDataChild;
    List<OpeningStockBean> save_listDataHeader;
    private HashMap<OpeningStockBean, List<OpeningStockBean>> _listDataChild;
    int facing_value, stock_value, expring_stock, mYear, mMonth, mDay;
    ExpandableListAdapter listAdapter;
    public static ArrayList<OpeningStockBean> compliance_list = new ArrayList<OpeningStockBean>();
    public static ArrayList<MidDayRecievedStockBean> compliance_listMidDay = new ArrayList<MidDayRecievedStockBean>();
    public static ArrayList<OpeningStockBean> sub_compliance_list = new ArrayList<OpeningStockBean>();
    public static ArrayList<OpeningStockBean> planogram_list = new ArrayList<OpeningStockBean>();
    boolean validation_flag = true;
    boolean date_flag = true;
    List<Integer> validate_header = new ArrayList<>();
    boolean isDialogOpen = true;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opening_stock);
        lv = (ExpandableListView) findViewById(R.id.lvExp);
        save_btn = (Button) findViewById(R.id.save_btn);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        visit_date = preferences.getString(CommonString.KEY_VISIT_DATE, null);
        loginDate = preferences.getString(CommonString.KEY_DATE, null);
//		preferences.getString(CommonString.KEY_STORE_NAME, "");
        username = preferences.getString(CommonString.KEY_USERNAME, "");
        store_id = preferences.getString(CommonString.KEY_STORE_ID, "");
        intime = preferences.getString(CommonString.KEY_STORE_IN_TIME, "");
        System.out.println("The store_id is:" + store_id);


        database = new pillsbury_Database(this);
        database.open();
        prepareListData();
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        lv.setAdapter(listAdapter);

        save_btn.setOnClickListener(this);

        lv.setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

            }

            @Override
            public void onScrollStateChanged(AbsListView arg0, int arg1) {
                InputMethodManager inputManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (getCurrentFocus() != null) {
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    getCurrentFocus().clearFocus();
                    lv.invalidateViews();
                }
                //lv.clearFocus();
            }

        });

    }

    @Override
    public void onBackPressed() {

//		super.onBackPressed();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to quit ?").setCancelable(false).setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent in = new Intent(OpeningStockList.this, DailyEntryMenu.class);
                        startActivity(in);
                        OpeningStockList.this.finish();

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

    private void prepareListData() {

        listDataHeader = new ArrayList<OpeningStockBean>();
        listDataChild = new HashMap<OpeningStockBean, List<OpeningStockBean>>();
        compliance_list = database.getInsertedComplianceData(store_id);
        compliance_listMidDay = database.getInsertedStockRecievedComplianceData(store_id);
        if (compliance_list.size() == 0) {

            compliance_list = database.getOpeningStockData(store_id);
            for (int i2 = 0; i2 < compliance_list.size(); i2++) {
                OpeningStockBean chk = new OpeningStockBean();
                chk.setBRAND(compliance_list.get(i2).getBRAND());
                chk.setBRAND_CD(compliance_list.get(i2).getBRAND_CD());
                // chk.setSKU(compliance_list.get(i2).getSKU());

                planogram_list = database.getPlanogramData(compliance_list.get(i2).getBRAND_CD(), store_id);

                for (int i = 0; i < planogram_list.size(); i++) {
                    image_url = planogram_list.get(i).getImage_url();

                    chk.setImage_url(planogram_list.get(i).getImage_url());
                }

                listDataHeader.add(chk);


                sub_compliance_list = database.getOpeningSkuData(compliance_list.get(i2).getBRAND_CD(), store_id);

                for (int i = 0; i < sub_compliance_list.size(); i++) {
                    sub_compliance_list.get(i).setShelf("NO");
                    sub_compliance_list.get(i).setFacing("");
                    sub_compliance_list.get(i).setOpeningStock("");
                    sub_compliance_list.get(i).setPricing("");
                    sub_compliance_list.get(i).setExpriring_stock("");
                    sub_compliance_list.get(i).setExpiring_date("");
                    sub_compliance_list.get(i).setShelf_fm_bottom("");
                    sub_compliance_list.get(i).setPrice_sign("NO");
                    sub_compliance_list.get(i).setSign_corrected("NO");
                    sub_compliance_list.get(i).setPog_adherence("NO");
                    sub_compliance_list.get(i).setPog_corrected("NO");
                    sub_compliance_list.get(i).setShelf_fm_bottom("");
                    sub_compliance_list.get(i).setListedFlag("true");

                }
                listDataChild.put(chk, sub_compliance_list);
                listDataChild.put(chk, addData(sub_compliance_list));

            }
        } else {
            update = true;
            save_btn.setText("Update");
            for (int i2 = 0; i2 < compliance_list.size(); i2++) {

                OpeningStockBean chk = new OpeningStockBean();
                chk.setBRAND(compliance_list.get(i2).getBRAND());
                chk.setBRAND_CD(compliance_list.get(i2).getBRAND_CD());

                planogram_list = database.getPlanogramData(compliance_list.get(i2).getBRAND_CD(), store_id);

                for (int i = 0; i < planogram_list.size(); i++) {
                    image_url = planogram_list.get(0).getImage_url();

                    chk.setImage_url(planogram_list.get(i).getImage_url());
                }


                listDataHeader.add(chk);
                sub_compliance_list = database.getInsertedSubList(compliance_list.get(i2).getCommonId());
                listDataChild.put(chk, sub_compliance_list);
                listDataChild.put(chk, addData(sub_compliance_list));

            }

        }
    }


    public ArrayList<OpeningStockBean> addData(ArrayList<OpeningStockBean> subList) {

        for (int i = 0; i < subList.size(); i++) {

            list = database.getFocusStatus(subList.get(i).getSKU_CD(), store_id);

            focus = list.get(0).getFocus();
            facing_allow = list.get(0).getFACING_ALLOW();
            pricing_allow = list.get(0).getPricing_allow();
            stock_allow = list.get(0).getStock_allow();

            allow_expiring = list.get(0).getAllow_expiring_stock();
            allow_pog = list.get(0).getAllow_pog();
            allow_shelf = list.get(0).getAllow_shelf();
            allow_signage = list.get(0).getAllow_signage();


            subList.get(i).setFocus(focus);
            subList.get(i).setFACING_ALLOW(facing_allow);
            subList.get(i).setPricing_allow(pricing_allow);
            subList.get(i).setStock_allow(stock_allow);

            subList.get(i).setAllow_expiring_stock(allow_expiring);
            subList.get(i).setAllow_pog(allow_pog);
            subList.get(i).setAllow_shelf(allow_shelf);
            subList.get(i).setAllow_signage(allow_signage);


        }
        return subList;

    }


    private class ExpandableListAdapter extends BaseExpandableListAdapter {
        private Context _context;

        public ExpandableListAdapter(Context context,
                                     List<OpeningStockBean> listDataHeader, HashMap<OpeningStockBean, List<OpeningStockBean>> listChildData) {
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
            final OpeningStockBean childText = (OpeningStockBean) getChild(groupPosition, childPosition);
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.openingstockviewlist, null);
            }

            TextView sku_name = (TextView) convertView.findViewById(R.id.sku_name);
            final TextView expiry_date = (TextView) convertView.findViewById(R.id.expiring_date_picker);

            final EditText price_text = (EditText) convertView.findViewById(R.id.pricing_edit);
            final EditText facing_text = (EditText) convertView.findViewById(R.id.facing_text);
            EditText openingStock_text = (EditText) convertView.findViewById(R.id.opening_edit);
            final EditText expiringStock_text = (EditText) convertView.findViewById(R.id.expiring_stock_text);
            EditText shelf_fm_text = (EditText) convertView.findViewById(R.id.shelf_fm_bottom_text);
            final CheckBox listed = (CheckBox) convertView.findViewById(R.id.listed);
            final CheckBox checkBox_listed = (CheckBox) convertView.findViewById(R.id.ckeckbox_listed);
            Button expirydate_btn = (Button) convertView.findViewById(R.id.expiring_date_btn);
            LinearLayout expLayout = (LinearLayout) convertView.findViewById(R.id.expiringdate_layout);
            LinearLayout mainLayout = (LinearLayout) convertView.findViewById(R.id.mainLayout);
            LinearLayout l1 = (LinearLayout) convertView.findViewById(R.id.stock_layout);
            LinearLayout l2 = (LinearLayout) convertView.findViewById(R.id.facing_layout);
            LinearLayout l3 = (LinearLayout) convertView.findViewById(R.id.expiring_layout);
            LinearLayout l4 = (LinearLayout) convertView.findViewById(R.id.pricing_layout);
            LinearLayout self_text_l = (LinearLayout) convertView.findViewById(R.id.twelve);
            final ToggleButton shelf_text = (ToggleButton) convertView.findViewById(R.id.shelf_edit);
            price_text.setText(childText.getPricing());
            // facing_text.setText(childText.getFacing());
            // openingStock_text.setText(childText.getOpeningStock());
            //expiringStock_text.setText(childText.getExpriring_stock());
            shelf_fm_text.setText(childText.getShelf_fm_bottom());
            expirydate_btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);
                    // Launch Date Picker Dialog
                    DatePickerDialog dpd = new DatePickerDialog(OpeningStockList.this,
                            new DatePickerDialog.OnDateSetListener() {


                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    String Date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
//				                  text.setDate(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                    _listDataChild.get(save_listDataHeader.get(groupPosition)).get(childPosition).setExpiring_date(Date);

                                }


                            }, mYear, mMonth, mDay);
                    dpd.show();
                }
            });


            expiry_date.setOnFocusChangeListener(new OnFocusChangeListener() {
                public void onFocusChange(View v, boolean hasFocus) {

                    if (!hasFocus) {
                        final int position1 = v.getId();
                        final TextView Caption = (TextView) v;
                        String value1 = Caption.getText().toString().replaceFirst("^0+(?!$)", "");
                        if (value1.equals("")) {
                            _listDataChild.get(save_listDataHeader.get(groupPosition)).get(position1).setExpiring_date("");
                        } else {
                            _listDataChild.get(save_listDataHeader.get(groupPosition)).get(position1).setExpiring_date(value1);
                        }

                    }
                }
            });

            expiry_date.setText(_listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).getExpiring_date());


            openingStock_text.setOnFocusChangeListener(new OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        final EditText Caption = (EditText) v;
                        String value1 = Caption.getText().toString().replaceFirst("^0+(?!$)", "");
                        if (!value1.equals("")) {
                            String stock = value1;
                            _listDataChild.get(save_listDataHeader.get(groupPosition)).get(childPosition).setOpeningStock(stock);
                            if (value1.equals("0")) {
                                _listDataChild.get(save_listDataHeader.get(groupPosition)).get(childPosition).setFacing("0");
                                _listDataChild.get(save_listDataHeader.get(groupPosition)).get(childPosition).setExpriring_stock("0");
                                facing_text.setEnabled(false);
                                expiringStock_text.setEnabled(false);
                            } else {
                                _listDataChild.get(save_listDataHeader.get(groupPosition)).get(childPosition).setFacing(childText.getFacing());
                                facing_text.setEnabled(true);
                                price_text.setEnabled(true);
                                expiringStock_text.setEnabled(true);
                                expiry_date.setEnabled(true);
                                price_text.setEnabled(true);
                            }

                        } else {
                            _listDataChild.get(save_listDataHeader.get(groupPosition)).get(childPosition).setOpeningStock(value1);
                            facing_text.setEnabled(true);
                            price_text.setEnabled(true);
                            expiringStock_text.setEnabled(true);
                            expiry_date.setEnabled(true);
                            price_text.setEnabled(true);
                        }

                    }

                }
            });

            openingStock_text.setText(_listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).getOpeningStock());
            facing_text.setOnFocusChangeListener(new OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        final EditText Caption = (EditText) v;
                        String value1 = Caption.getText().toString().replaceFirst("^0+(?!$)", "");
                        if (!childText.getOpeningStock().equals("")) {
                            if (!value1.equals("")) {
                                if (childText.getStock_allow().equalsIgnoreCase("False") && childText.getAllow_expiring_stock().equalsIgnoreCase("false")) {
                                    _listDataChild.get(save_listDataHeader.get(groupPosition)).get(childPosition).setFacing(value1);
                                } else if (Integer.parseInt(value1) <= Integer.parseInt(childText.getOpeningStock())) {
                                    _listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).setFacing(value1);

                                } else {
                                    if (isDialogOpen) {
                                        isDialogOpen = !isDialogOpen;
                                        if (!childText.getStock_allow().equalsIgnoreCase("False") && !childText.getAllow_expiring_stock().equalsIgnoreCase("false")) {
                                            //Toast.makeText(getApplicationContext(),"Facing value can not be greater than openning stock value",Toast.LENGTH_LONG).show();
                                            AlertDialog.Builder builder = new AlertDialog.Builder(OpeningStockList.this);
                                            builder.setMessage("Facing value can not be greater than openning stock value")
                                                    .setCancelable(false)
                                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            dialog.dismiss();
                                                            isDialogOpen = !isDialogOpen;
                                                        }
                                                    });


                                            AlertDialog alert = builder.create();
                                            alert.show();
                                        }
                                    }

                                }

                                // _listDataChild.get(save_listDataHeader.get(groupPosition)).get(childPosition).setFacing("");

                            } else {

                                _listDataChild.get(save_listDataHeader.get(groupPosition)).get(childPosition).setFacing("");

                            }
                        } else {
                            if (isDialogOpen) {
                                isDialogOpen = !isDialogOpen;
                                AlertDialog.Builder builder = new AlertDialog.Builder(OpeningStockList.this);
                                builder.setMessage("First fill the openning stock value")
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.dismiss();
                                                isDialogOpen = !isDialogOpen;
                                                //lv.clearFocus();
                                                // lv.invalidateViews();
                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();
                            }
                        }
                    }

                }
            });
            facing_text.setText(_listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).getFacing());


            expiringStock_text.setOnFocusChangeListener(new OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        final EditText Caption = (EditText) v;
                        String value1 = Caption.getText().toString().replaceFirst("^0+(?!$)", "");

                        if (!childText.getOpeningStock().equals("")) {
                            if (!value1.equals("")) {
                                if (Integer.parseInt(value1) <= Integer.parseInt(childText.getOpeningStock())) {
                                    _listDataChild.get(save_listDataHeader.get(groupPosition)).get(childPosition).setExpriring_stock(value1);

                                    // _listDataChild.get(save_listDataHeader.get(groupPosition)).get(childPosition).setExpriring_stock("");
                                } else {
                                    if (isDialogOpen) {
                                        isDialogOpen = !isDialogOpen;
                                        AlertDialog.Builder builder = new AlertDialog.Builder(OpeningStockList.this);
                                        builder.setMessage("Expire stock value can not be greater than openning stock value")
                                                .setCancelable(false)
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.dismiss();
                                                        isDialogOpen = !isDialogOpen;
                                                    }
                                                });
                                        AlertDialog alert = builder.create();
                                        alert.show();
                                    }
                                }

                            } else {
                                _listDataChild.get(save_listDataHeader.get(groupPosition)).get(childPosition).setExpriring_stock("");

                            }

                        } else {
                            if (isDialogOpen) {
                                isDialogOpen = !isDialogOpen;
                                AlertDialog.Builder builder = new AlertDialog.Builder(OpeningStockList.this);
                                builder.setMessage("First fill the openning stock value")
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.dismiss();
                                                isDialogOpen = !isDialogOpen;
                                                //lv.clearFocus();
                                                // lv.invalidateViews();
                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();
                            }
                        }


                    }
                }
            });

            expiringStock_text.setText(_listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).getExpriring_stock());

            shelf_fm_text.setOnFocusChangeListener(new OnFocusChangeListener() {

                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        final EditText Caption = (EditText) v;
                        String value1 = Caption.getText().toString().replaceFirst("^0+(?!$)", "");

                        if (value1.equals("")) {

                            _listDataChild.get(save_listDataHeader.get(groupPosition)).get(childPosition).setShelf_fm_bottom("");

                        } else {
                            _listDataChild.get(save_listDataHeader.get(groupPosition)).get(childPosition).setShelf_fm_bottom(value1);
                        }

                    }

                }
            });
            price_text.setOnFocusChangeListener(new OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        final EditText Caption = (EditText) v;
                        String value1 = Caption.getText().toString().replaceFirst("^0+(?!$)", "");
                        if (value1.equals("")) {
                            _listDataChild.get(save_listDataHeader.get(groupPosition)).get(childPosition).setPricing("");

                        } else {
                            _listDataChild.get(save_listDataHeader.get(groupPosition)).get(childPosition).setPricing(value1);

                        }

                    }

                }
            });

            shelf_text.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    String val = shelf_text.getText().toString().replaceFirst("^&0+(?!$)", "");
                    _listDataChild.get(save_listDataHeader.get(groupPosition)).get(childPosition).setShelf(val);
                    lv.invalidateViews();
                }
            });

            if (childText.getShelf().equalsIgnoreCase("NO")) {
                shelf_text.setChecked(false);
            } else {
                shelf_text.setChecked(true);
            }

            if (childText.getListedFlag().equalsIgnoreCase("false")) {
                listed.setChecked(false);
            } else {
                listed.setChecked(true);
            }

            sku_name.setText(childText.getSKU());

            String focusStatus = childText.getFocus();
            String stock_allow = childText.getStock_allow();

            String allow_shelfs = childText.getAllow_shelf();

            String price_allow = childText.getPricing_allow();
            String face_allow = childText.getFACING_ALLOW();
            String allow_expire = childText.getAllow_expiring_stock();
            String allow_signag = childText.getAllow_signage();
            String allow_pog = childText.getAllow_pog();

            if (focusStatus.equalsIgnoreCase("YES")) {
                convertView.setBackgroundResource(R.color.light);
            } else {
                convertView.setBackgroundResource(R.color.white);
            }

            String listedorNot = _listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).getListedFlag();

            if (listedorNot.equalsIgnoreCase("false")) {
                mainLayout.setVisibility(View.GONE);
                listDataChild.get(save_listDataHeader.get(groupPosition)).get(childPosition).setOpeningStock("");
                listDataChild.get(save_listDataHeader.get(groupPosition)).get(childPosition).setExpiring_date("");
                listDataChild.get(save_listDataHeader.get(groupPosition)).get(childPosition).setFacing("");
                listDataChild.get(save_listDataHeader.get(groupPosition)).get(childPosition).setExpriring_stock("");
                listDataChild.get(save_listDataHeader.get(groupPosition)).get(childPosition).setShelf("");
                listDataChild.get(save_listDataHeader.get(groupPosition)).get(childPosition).setShelf_fm_bottom("");
                listDataChild.get(save_listDataHeader.get(groupPosition)).get(childPosition).setPricing("");
                self_text_l.setVisibility(View.GONE);


            } else {

                mainLayout.setVisibility(View.VISIBLE);
                if (stock_allow.equalsIgnoreCase("False")) {
                    openingStock_text.setEnabled(false);
                    l1.setVisibility(View.GONE);
                    listDataChild.get(save_listDataHeader.get(groupPosition)).get(childPosition).setOpeningStock("0");
                    openingStock_text.setBackgroundColor(Color.parseColor("#F2F2F2"));


                } else {
                    l1.setVisibility(View.VISIBLE);
                    openingStock_text.setEnabled(true);
                    openingStock_text.setBackgroundColor(Color.parseColor("#00BFFF"));

                }
                if (price_allow.equalsIgnoreCase("False")) {
                    price_text.setEnabled(false);
                    l4.setVisibility(View.GONE);
                    listDataChild.get(save_listDataHeader.get(groupPosition)).get(childPosition).setPricing("0");
                    price_text.setBackgroundColor(Color.parseColor("#F2F2F2"));
                } else {
                    l4.setVisibility(View.VISIBLE);
                    price_text.setEnabled(true);
                    price_text.setBackgroundColor(Color.parseColor("#00BFFF"));
//					price_text.setBackgroundColor(Color.parseColor("#00BFFF"));
                }

                if (face_allow.equalsIgnoreCase("False")) {
                    facing_text.setEnabled(false);
                    l2.setVisibility(View.GONE);
                    listDataChild.get(save_listDataHeader.get(groupPosition))
                            .get(childPosition).setFacing("0");
                    facing_text.setBackgroundColor(Color.parseColor("#F2F2F2"));
                } else {
                    l2.setVisibility(View.VISIBLE);
                    facing_text.setEnabled(true);
                    facing_text.setBackgroundColor(Color.parseColor("#00BFFF"));
                }

                if (allow_expire.equalsIgnoreCase("False")) {
                    expiringStock_text.setEnabled(false);
                    expirydate_btn.setEnabled(false);
                    expiry_date.setEnabled(false);
                    expLayout.setVisibility(View.GONE);
                    l3.setVisibility(View.GONE);
                    listDataChild.get(save_listDataHeader.get(groupPosition)).get(childPosition).setExpriring_stock("0");
                    expiringStock_text.setBackgroundColor(Color.parseColor("#F2F2F2"));
                } else {
                    if (!listDataChild.get(save_listDataHeader.get(groupPosition)).get(childPosition).getExpriring_stock().equalsIgnoreCase("")) {

                        int expiring_stock = Integer.parseInt(listDataChild.get(save_listDataHeader.get(groupPosition))
                                .get(childPosition).getExpriring_stock());

                        if (expiring_stock > 0) {
                            expLayout.setVisibility(View.VISIBLE);
                            expiry_date.setEnabled(true);
                            expirydate_btn.setEnabled(true);
                        } else {
                            expLayout.setVisibility(View.GONE);
                            expiry_date.setEnabled(false);
                            expirydate_btn.setEnabled(false);
                        }
                    }

                    l3.setVisibility(View.VISIBLE);
                    expiringStock_text.setEnabled(true);
                    expiringStock_text.setBackgroundColor(Color.parseColor("#00BFFF"));
                }

                if (allow_shelfs.equalsIgnoreCase("False")) {
                    shelf_text.setEnabled(false);
                    self_text_l.setVisibility(View.GONE);
                    listDataChild.get(save_listDataHeader.get(groupPosition))
                            .get(childPosition).setShelf_fm_bottom("0");
                } else {
                    self_text_l.setVisibility(View.VISIBLE);
                    shelf_text.setEnabled(true);
                }
            }


            if (!validation_flag) {
                if (expiry_date.getText().toString().equalsIgnoreCase("")) {
                    expiry_date.setBackgroundColor(Color.parseColor("#FF0000"));
                } else {
                    expiry_date.setBackgroundColor(Color.parseColor("#00BFFF"));
                }
                if (openingStock_text.getText().toString().equalsIgnoreCase("")) {
                    openingStock_text.setBackgroundColor(Color.parseColor("#FF0000"));
                } else {
                    openingStock_text.setBackgroundColor(Color.parseColor("#00BFFF"));
                }

                if (facing_text.getText().toString().equalsIgnoreCase("")) {
                    facing_text.setBackgroundColor(Color.parseColor("#FF0000"));
                } else {
                    facing_text.setBackgroundColor(Color.parseColor("#00BFFF"));
                }


                if (price_text.getText().toString().equalsIgnoreCase("")) {
                    price_text.setBackgroundColor(Color.parseColor("#FF0000"));
                } else {
                    price_text.setBackgroundColor(Color.parseColor("#00BFFF"));
                }

                if (expiringStock_text.getText().toString().equalsIgnoreCase("")) {
                    expiringStock_text.setBackgroundColor(Color.parseColor("#FF0000"));
                } else {
                    expiringStock_text.setBackgroundColor(Color.parseColor("#00BFFF"));
                }

                if (shelf_fm_text.getText().toString().equalsIgnoreCase("")) {
                    shelf_fm_text.setBackgroundColor(Color.parseColor("#FF0000"));
                } else {
                    shelf_fm_text.setBackgroundColor(Color.parseColor("#00BFFF"));
                }


            }
            if (!date_flag) {
                int facing_s = 0;
                facing_s = Integer.parseInt(facing_text.getText().toString());
                int openning = 0;
                openning = Integer.parseInt(openingStock_text.getText().toString());
                int expiring_s = 0;
                expiring_s = Integer.parseInt(expiringStock_text.getText().toString());
                if (facing_s > openning) {
                    openingStock_text.setBackgroundColor(Color.parseColor("#FF0000"));
                    facing_text.setBackgroundColor(Color.parseColor("#FF0000"));
                } else {
                    facing_text.setBackgroundColor(Color.parseColor("#00BFFF"));
                    openingStock_text.setBackgroundColor(Color.parseColor("#00BFFF"));
                }

                if (expiring_s > openning) {
                    openingStock_text.setBackgroundColor(Color.parseColor("#FF0000"));
                    expiringStock_text.setBackgroundColor(Color.parseColor("#FF0000"));
                } else {
                    openingStock_text.setBackgroundColor(Color.parseColor("#00BFFF"));
                    expiringStock_text.setBackgroundColor(Color.parseColor("#00BFFF"));
                }
            }


            shelf_text.setText(_listDataChild.get(save_listDataHeader.get(groupPosition)).get(childPosition).getShelf());

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
            return _listDataChild.get(save_listDataHeader.get(groupPosition))
                    .size();
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
        public View getGroupView(final int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            final OpeningStockBean headerTitle = (OpeningStockBean) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.openingbrand, null);
            }

            Button img_btn = (Button) convertView.findViewById(R.id.imageView);
            img_btn.setTag(groupPosition);
            img_btn.setFocusable(false);


            img_btn.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    String imageURL = headerTitle.getImage_url();
                    new LoadImage().execute(imageURL);

                }
            });


            TextView brand_name = (TextView) convertView.findViewById(R.id.brand_name);
            brand_name.setTypeface(null, Typeface.BOLD);

            brand_name.setText(headerTitle.getBRAND());
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.save_btn) {
            lv.clearFocus();
            if (validateData(listDataChild, listDataHeader)) {
                if (validate(listDataChild, listDataHeader)) {
                    //if (validatePricing(listDataChild, listDataHeader)) {

                    if (validateExpiringDate(_listDataChild, listDataHeader)) {

                        //if (validateShelf1(_listDataChild, listDataHeader)) {
                        // if (validateShelf(_listDataChild, listDataHeader)) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage("Are you sure you want to save")
                                .setCancelable(false)
                                .setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,
                                                                int id) {

                                                database.open();

                                                database.deleteOpeningStockData(store_id);
                                                database.InsertOpeningStocklistData(getMid(), store_id, listDataChild, listDataHeader);

                                                Toast.makeText(getApplicationContext(),
                                                        "Data has been saved", Toast.LENGTH_SHORT).show();

                                                Intent DailyEntryMenu = new Intent(OpeningStockList.this, DailyEntryMenu.class);
                                                startActivity(DailyEntryMenu);
                                            }
                                        })
                                .setNegativeButton("No",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,
                                                                int id) {
                                                dialog.cancel();
                                            }
                                        });
                        AlertDialog alert = builder.create();

                        alert.show();
                               /* } else {
                                    Toast.makeText(getApplicationContext(), "shelf no should not be zero", Toast.LENGTH_SHORT).show();
                                    lv.invalidateViews();
                                }*/

                           /* } else {
                                Toast.makeText(getApplicationContext(), "shelf no be zero", Toast.LENGTH_SHORT).show();
                                lv.invalidateViews();
                            }*/
                    } else {
                        Toast.makeText(getApplicationContext(), "Please fill expiring Date", Toast.LENGTH_SHORT).show();
                        lv.invalidateViews();
                    }


                  /*  } else {
                        Toast.makeText(getApplicationContext(), "Pricing Cannot be Zero", Toast.LENGTH_SHORT).show();
                        lv.invalidateViews();
                    }*/

                } else {
                    Toast.makeText(getApplicationContext(), "Opening stock should be greater than facings and Expiry stock", Toast.LENGTH_SHORT).show();
                    lv.invalidateViews();
                }


            } else {
                Toast.makeText(getApplicationContext(), "Please fill all the data", Toast.LENGTH_SHORT).show();
                lv.invalidateViews();
            }

        }


    }

    public String getCurrentTime() {

        Calendar m_cal = Calendar.getInstance();

        String intime = m_cal.get(Calendar.HOUR_OF_DAY) + ":"
                + m_cal.get(Calendar.MINUTE) + ":" + m_cal.get(Calendar.SECOND);

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

    public void InsertOpeningStocklistData(long mid, String storeid,
                                           HashMap<OpeningStockBean, List<OpeningStockBean>> data,
                                           List<OpeningStockBean> save_listDataHeader) {

        for (int i = 0; i < save_listDataHeader.size(); i++) {

            for (int j = 0; j < data.get(save_listDataHeader.get(i)).size(); j++) {

            }

        }
    }

    boolean validate(HashMap<OpeningStockBean, List<OpeningStockBean>> listDataChild2,
                     List<OpeningStockBean> listDataHeader2) {
        boolean flag = true;
        for (int i = 0; i < listDataHeader2.size(); i++) {
            for (int j = 0; j < listDataChild2.get(listDataHeader.get(i)).size(); j++) {
                String listed = listDataChild.get(listDataHeader.get(i)).get(j).getListedFlag();
                String Stock_allow = listDataChild.get(listDataHeader.get(i)).get(j).getStock_allow();

                if (listed.equalsIgnoreCase("true")) {
                    if (Stock_allow.equalsIgnoreCase("False")) {
                        flag = true;

                    } else {

                        String facinf = listDataChild.get(listDataHeader.get(i)).get(j).getFacing();
                        String stocking = listDataChild.get(listDataHeader.get(i)).get(j).getOpeningStock();
                        String expiring = listDataChild.get(listDataHeader.get(i)).get(j).getExpriring_stock();

                        if (!facinf.equalsIgnoreCase("")) {
                            facing_value = Integer.parseInt(facinf);
                        } else {
                            facing_value = 0;
                        }

                        if (!stocking.equalsIgnoreCase("")) {
                            stock_value = Integer.parseInt(stocking);
                        } else {
                            stock_value = 0;
                        }

                        if (!expiring.equalsIgnoreCase("")) {
                            expring_stock = Integer.parseInt(expiring);
                        } else {
                            expring_stock = 0;
                        }

                        if (facing_value > stock_value && listDataChild.get(listDataHeader.get(i)).get(j).getStock_allow().equalsIgnoreCase("true")) {

                            date_flag = false;
                            return false;
                        }

                        if (expring_stock > stock_value && listDataChild.get(listDataHeader.get(i)).get(j).getAllow_expiring_stock().equalsIgnoreCase("true")) {
                            date_flag = false;
                            return false;

                        }

                    }

//				flag=true;
                }
            }
        }
        return flag;

    }


    boolean validateData(
            HashMap<OpeningStockBean, List<OpeningStockBean>> listDataChild2,
            List<OpeningStockBean> listDataHeader2) {
        boolean flag = true;
        validate_header.clear();
        for (int i = 0; i < listDataHeader2.size(); i++) {
            for (int j = 0; j < listDataChild2.get(listDataHeader.get(i))
                    .size(); j++) {
                String listed = listDataChild.get(listDataHeader.get(i)).get(j).getListedFlag();
                String facing = listDataChild.get(listDataHeader.get(i)).get(j).getFacing();
                String stock = listDataChild.get(listDataHeader.get(i)).get(j).getOpeningStock();
                String pricing = listDataChild.get(listDataHeader.get(i)).get(j).getPricing();
                String exp_stock = listDataChild.get(listDataHeader.get(i)).get(j).getExpriring_stock();
                String shelf_no = listDataChild.get(listDataHeader.get(i)).get(j).getShelf_fm_bottom();
                String is_shelf_allowed = listDataChild.get(listDataHeader.get(i)).get(j).getAllow_shelf();

                if (listed.equalsIgnoreCase("true")) {
                    if (facing.equalsIgnoreCase("") || stock.equalsIgnoreCase("") || pricing.equalsIgnoreCase("") ||
                            exp_stock.equalsIgnoreCase("") || shelf_no.equalsIgnoreCase("")) {
                        flag = false;
                        validation_flag = false;
                        validate_header.add(i);
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


    boolean validateExpiringDate(
            HashMap<OpeningStockBean, List<OpeningStockBean>> listDataChild2,
            List<OpeningStockBean> listDataHeader2) {
        boolean flag = true;
        for (int i = 0; i < listDataHeader2.size(); i++) {
            for (int j = 0; j < listDataChild2.get(listDataHeader.get(i))
                    .size(); j++) {
                String listed = listDataChild.get(listDataHeader.get(i)).get(j).getListedFlag();

                if (listed.equalsIgnoreCase("true")) {
                    String allow_exp = listDataChild.get(listDataHeader.get(i)).get(j).getAllow_expiring_stock();
                    String expiring_stock = listDataChild.get(listDataHeader.get(i)).get(j).getExpriring_stock();
                    if (allow_exp.equalsIgnoreCase("true")) {
                        if (!expiring_stock.equalsIgnoreCase("0") && !expiring_stock.equalsIgnoreCase("")) {
                            String exp_date = listDataChild.get(listDataHeader.get(i)).get(j).getExpiring_date();
                            if (exp_date.equalsIgnoreCase("")) {
                                validation_flag = false;
                                flag = false;
                                break;
                            } else {
                                flag = true;
                            }
                        } else {

                            flag = true;

                        }


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

    boolean validatePricing(
            HashMap<OpeningStockBean, List<OpeningStockBean>> listDataChild2,
            List<OpeningStockBean> listDataHeader2) {
        boolean flag = true;
        for (int i = 0; i < listDataHeader2.size(); i++) {
            for (int j = 0; j < listDataChild2.get(listDataHeader.get(i))
                    .size(); j++) {

                String stock = listDataChild.get(listDataHeader.get(i)).get(j).getOpeningStock();
                String pricing = listDataChild.get(listDataHeader.get(i)).get(j).getPricing();
                String listed = listDataChild.get(listDataHeader.get(i)).get(j).getListedFlag();

                if (listed.equalsIgnoreCase("true")) {
                    int price = Integer.parseInt(pricing);
                    stock_value = Integer.parseInt(stock);
                    String Stock_allows = listDataChild.get(listDataHeader.get(i)).get(j).getStock_allow();
                    String pricing_allows = listDataChild.get(listDataHeader.get(i)).get(j).getPricing_allow();

                    if (pricing_allows.equalsIgnoreCase("false")) {
                        flag = true;

                    } else {
                        if ((stock_value != 0) && (price == 0)) {
                            flag = false;
                            break;
                        } else {
                            flag = true;
                        }

                    }


                }

                if (flag == false) {
                    break;
                }
            }


        }
        return flag;

    }


    boolean validateShelf(
            HashMap<OpeningStockBean, List<OpeningStockBean>> listDataChild2,
            List<OpeningStockBean> listDataHeader2) {
        boolean flag = true;
        for (int i = 0; i < listDataHeader2.size(); i++) {
            for (int j = 0; j < listDataChild2.get(listDataHeader.get(i))
                    .size(); j++) {

                String stock = listDataChild.get(listDataHeader.get(i)).get(j).getOpeningStock();
                String facing = listDataChild.get(listDataHeader.get(i)).get(j).getFacing();
                String shelf_no = listDataChild.get(listDataHeader.get(i)).get(j).getShelf_fm_bottom();
                String listed = listDataChild.get(listDataHeader.get(i)).get(j).getListedFlag();

                if (listed.equalsIgnoreCase("true")) {
                    int facing_value = Integer.parseInt(facing);
                    stock_value = Integer.parseInt(stock);
                    int shelf = Integer.parseInt(shelf_no);


                    if (facing_value > 0 && stock_value > 0) {

                        if (shelf > 0) {
                            flag = true;
                        } else {
                            flag = false;
                            break;
                        }


                    } else if (facing_value == 0 && stock_value == 0) {
                        if (shelf > 0) {
                            flag = false;
                            break;
                        } else {
                            flag = true;
//						break;

                        }
                    }


                }


            }

            if (flag == false) {
                break;
            }


        }
        return flag;

    }


    boolean validateShelf1(
            HashMap<OpeningStockBean, List<OpeningStockBean>> listDataChild2,
            List<OpeningStockBean> listDataHeader2) {
        boolean flag = true;
        for (int i = 0; i < listDataHeader2.size(); i++) {
            for (int j = 0; j < listDataChild2.get(listDataHeader.get(i))
                    .size(); j++) {

                String stock = listDataChild.get(listDataHeader.get(i)).get(j).getOpeningStock();
                String facing = listDataChild.get(listDataHeader.get(i)).get(j).getFacing();
                String shelf_no = listDataChild.get(listDataHeader.get(i)).get(j).getShelf_fm_bottom();
                String listed = listDataChild.get(listDataHeader.get(i)).get(j).getListedFlag();
                String shelf_flag = listDataChild.get(listDataHeader.get(i)).get(j).getAllow_shelf();

                if (listed.equalsIgnoreCase("true")) {
                    int facing_value = Integer.parseInt(facing);
                    stock_value = Integer.parseInt(stock);
                    int shelf = Integer.parseInt(shelf_no);


                    if (shelf_flag.equalsIgnoreCase("True")) {

                        if (facing_value == 0 && stock_value == 0) {
                            if (shelf > 0) {
                                flag = false;
                                break;
                            } else {
                                flag = true;
//								break;

                            }
                        }
                    }


                }


            }

            if (flag == false) {
                break;
            }


        }
        return flag;

    }

    private class LoadImage extends AsyncTask<String, String, URL> {
        ProgressDialog pDialog;
        Bitmap bitmap;
        URL url;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(OpeningStockList.this);
            pDialog.setMessage("Loading Image ....");
            pDialog.show();
        }

        @Override
        protected URL doInBackground(String... args) {
            try {
                url = new URL(args[0]);
//				bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return url;
        }

        @Override
        protected void onPostExecute(URL url) {
            if (url != null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(OpeningStockList.this);
                LayoutInflater inflater = (LayoutInflater) OpeningStockList.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.thumbnail, null);
//				ImageView mYimage = (ImageView) layout.findViewById(R.id.thumbnail_IMAGEVIEW);
                WebView image = (WebView) layout.findViewById(R.id.image);
                image.getSettings().setBuiltInZoomControls(true);

                image.loadUrl(url.toString());
//				mYimage.setImageBitmap(image);
                pDialog.dismiss();
                builder.setView(layout);
                builder.create();
                builder.show();
            } else {
                pDialog.dismiss();
                Toast.makeText(OpeningStockList.this, "Image Does Not exist or Network Error", Toast.LENGTH_SHORT).show();
            }

        }


    }

}
