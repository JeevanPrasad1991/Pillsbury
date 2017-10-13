package com.cpm.dailyEntry;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.cpm.Constants.CommonString;
import com.cpm.database.pillsbury_Database;
import com.cpm.delegates.MidDayRecievedStockBean;
import com.cpm.pillsbury.R;

import java.util.ArrayList;

/**
 * Created by jeevanp on 03-02-2017.
 */

public class ClosingScockListed extends Activity {
    private String store_id, username, intime, visit_date;
    private SharedPreferences preferences;
    ListView lv;
    Button save_btn;
    private pillsbury_Database database;
    Helper help = new Helper();
    Boolean update = false;
    private ArrayList<MidDayRecievedStockBean> compliance_list = new ArrayList<MidDayRecievedStockBean>();
    String value = "0";
    boolean update_flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_closingstock_listed);
        lv = (ListView) findViewById(R.id.lvstock);
        save_btn = (Button) findViewById(R.id.save_btn1);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        visit_date = preferences.getString(CommonString.KEY_DATE, null);
        preferences.getString(CommonString.KEY_STORE_NAME, "");
        username = preferences.getString(CommonString.KEY_USERNAME, "");
        store_id = preferences.getString(CommonString.KEY_STORE_ID, "");
        intime = preferences.getString(CommonString.KEY_STORE_IN_TIME, "");
        database = new pillsbury_Database(this);
        database.open();

        compliance_list = database.getStockListedData(store_id);
        if (compliance_list.size() > 0) {
            for (int i = 0; i < compliance_list.size(); i++) {
                if (!compliance_list.get(i).getSKU().equalsIgnoreCase("")) {
                    update_flag = true;
                    save_btn.setText("Update");
                }
            }
        } else {
            compliance_list = database.getMidDayStockRecievedDataWithSku(store_id);
        }
        if (compliance_list.size() > 0) {
            lv.setAdapter(new MyAdapter());
        }
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }

            @Override
            public void onScrollStateChanged(AbsListView arg0, int arg1) {
                if (SCROLL_STATE_TOUCH_SCROLL == arg1) {
                    View currentFocus = getCurrentFocus();
                    if (currentFocus != null) {
                        currentFocus.clearFocus();
                    }
                }
            }
        });
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ClosingScockListed.this).setMessage("Do you want to save data");
                builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (compliance_list.size() > 0) {
                            database.open();
                            database.insertStockLidtedData(store_id, compliance_list);
                            Toast.makeText(ClosingScockListed.this, "Data save successfully", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(ClosingScockListed.this, DailyEntryMenu.class));
                            finish();
                        }
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
    }

    public class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return compliance_list.size();
        }
        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            MyAdapter.ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.closingstocl_item, null);
                holder.sku_name = (TextView) convertView.findViewById(R.id.textsku);
                holder.toggle = (ToggleButton) convertView.findViewById(R.id.stock_toggle);
                convertView.setTag(holder);
            } else {
                holder = (MyAdapter.ViewHolder) convertView.getTag();
            }
            holder.toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        value = "1";
                        compliance_list.get(position).setIsListed(value);
                    } else {
                        compliance_list.get(position).setIsListed("0");
                    }
                }
            });

            if (compliance_list.get(position).getIsListed().equalsIgnoreCase("0")) {
                holder.toggle.setChecked(false);
                holder.toggle.setText("No");
            } else {
                holder.toggle.setChecked(true);
                holder.toggle.setText("Yes");
            }
            holder.sku_name.setText(compliance_list.get(position).getSKU());
            holder.sku_name.setId(position);
            return convertView;
        }

        private class ViewHolder {
            TextView sku_name;
            ToggleButton toggle;
        }
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to quit ?").setCancelable(false).setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent in = new Intent(ClosingScockListed.this, DailyEntryMenu.class);
                        startActivity(in);
                        ClosingScockListed.this.finish();
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

}

