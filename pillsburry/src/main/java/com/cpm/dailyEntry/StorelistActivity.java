package com.cpm.dailyEntry;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cpm.Constants.CommonString;
import com.cpm.database.pillsbury_Database;
import com.cpm.delegates.AssetTrackerBean;
import com.cpm.delegates.CallsBean;
import com.cpm.delegates.ClosingStockBean;
import com.cpm.delegates.CoverageBean;
import com.cpm.delegates.MidDayRecievedStockBean;
import com.cpm.delegates.OpeningStockBean;
import com.cpm.delegates.Posmbean;
import com.cpm.delegates.PromotionTrackingBean;
import com.cpm.delegates.SalesPersonBean;
import com.cpm.delegates.StoreBean;
import com.cpm.delegates.TrainingBean;
import com.cpm.delegates.competitionbeans;
import com.cpm.delegates.salesBean;
import com.cpm.message.AlertMessage;
import com.cpm.pillsbury.MainMenuActivity;
import com.cpm.pillsbury.R;

public class StorelistActivity extends ListActivity {

    private Intent intent;
    private Cursor cursor;
    private pillsbury_Database database;
    private SharedPreferences preferences;
    private String date;
    private ArrayList<StoreBean> list = new ArrayList<StoreBean>();
    private SharedPreferences.Editor editor = null;
    boolean leave;
    StoreBean storestatus = new StoreBean();
    private ArrayList<OpeningStockBean> openingData = new ArrayList<OpeningStockBean>();
    private ArrayList<ClosingStockBean> closingData = new ArrayList<ClosingStockBean>();
    private ArrayList<MidDayRecievedStockBean> StockData = new ArrayList<MidDayRecievedStockBean>();
    private ArrayList<CallsBean> callTrackerData = new ArrayList<CallsBean>();
    private ArrayList<PromotionTrackingBean> promotionData = new ArrayList<PromotionTrackingBean>();
    private ArrayList<PromotionTrackingBean> promotion_list = new ArrayList<PromotionTrackingBean>();

    private ArrayList<AssetTrackerBean> AssetData = new ArrayList<AssetTrackerBean>();
    private ArrayList<AssetTrackerBean> compliance_list = new ArrayList<AssetTrackerBean>();
    ArrayList<competitionbeans> competitionlist = new ArrayList<competitionbeans>();
    private ArrayList<MidDayRecievedStockBean> listedSkuList = new ArrayList<MidDayRecievedStockBean>();
    private ArrayList<MidDayRecievedStockBean> listedskuMasterList = new ArrayList<MidDayRecievedStockBean>();
    ArrayList<competitionbeans> competitionMappingList = new ArrayList<competitionbeans>();
    String store_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storename);

        database = new pillsbury_Database(this);
        database.open();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
//change
        //date = preferences.getString(CommonString.KEY_VISIT_DATE, null);
        date = preferences.getString(CommonString.KEY_DATE, null);
        String visit_storeid = preferences.getString(CommonString.KEY_STOREVISITED, "");
        store_id = preferences.getString(CommonString.KEY_STORE_ID, "");
        if (visit_storeid != null && !visit_storeid.equals("")) {
            storestatus = database.getStoreStatus(visit_storeid);
            if (storestatus.getCheckout_status().equalsIgnoreCase(CommonString.KEY_VALID) || storestatus.getCheckout_status().equalsIgnoreCase(CommonString.KEY_C)) {
            } else {
                database.updateStoreStatusOnCheckout(visit_storeid, date, CommonString.KEY_INVALID);
            }
        }
        filldata();
        list = database.getStoreData(date);
        setListAdapter(new MyAdapter());
    }

    private void filldata() {
        ArrayList<CoverageBean> coverageBeanlist = new ArrayList<CoverageBean>();
        coverageBeanlist = database.getCoverageData(date, null);
        for (int i = 0; i < coverageBeanlist.size(); i++) {
            boolean opening = false, closing = false, stock = false, promotion = false, asset = false, competition_sos = false, listedsku = false;
            storestatus = database.getStoreStatus(coverageBeanlist.get(i).getStoreId());
            String RightsTypes = storestatus.getRights();
            if ((storestatus.getCheckout_status().equalsIgnoreCase(CommonString.KEY_L) || storestatus.getCheckout_status().equalsIgnoreCase(CommonString.KEY_C))) {
            } else {
                openingData = database.getInsertedComplianceData(coverageBeanlist.get(i).getStoreId());
                StockData = database.getInsertedStockRecievedComplianceData(coverageBeanlist.get(i).getStoreId());
                closingData = database.getInsertedClosingStockComplianceData(coverageBeanlist.get(i).getStoreId());
                callTrackerData = database.getCallTrackerData(coverageBeanlist.get(i).getStoreId());
                promotionData = database.getInsertedPromotionComplianceData(coverageBeanlist.get(i).getStoreId());
                AssetData = database.getInsertedAssetComplianceData(coverageBeanlist.get(i).getStoreId());
                listedSkuList = database.getStockListedData(coverageBeanlist.get(i).getStoreId());
                competitionlist = database.getInsertedCompetitionData(coverageBeanlist.get(i).getStoreId());
                if (openingData.size() > 0) {
                    opening = true;
                }
                if (StockData.size() > 0) {
                    stock = true;
                }

                if (closingData.size() > 0) {
                    closing = true;
                }

                promotion_list = database.getPromotionTrackData(coverageBeanlist.get(i).getStoreId());
                if (promotion_list.size() > 0) {
                    if (promotionData.size() > 0) {
                        promotion = true;
                    }
                } else {
                    promotion = true;
                }

                compliance_list = database.getAssetTrackData(coverageBeanlist.get(i).getStoreId());
                if (compliance_list.size() > 0) {
                    if (AssetData.size() > 0) {
                        asset = true;
                    }

                } else {
                    asset = true;
                }

                competitionMappingList = database.getCategoryData();
                if (competitionMappingList.size() > 0) {
                    if (competitionlist.size() > 0) {
                        competition_sos = true;
                    }
                } else {
                    competition_sos = true;
                }

                listedskuMasterList = database.getMidDayStockRecievedDataWithSku(coverageBeanlist.get(i).getStoreId());
                if (listedskuMasterList.size() > 0) {
                    if (listedSkuList.size() > 0) {
                        listedsku = true;
                    }
                } else {
                    listedsku = true;
                }

                if (RightsTypes.equalsIgnoreCase("Merchandiser")) {
                    if (opening  && promotion && asset && competition_sos && listedsku) {
                        database.updateStoreStatusOnCheckout(coverageBeanlist.get(i).getStoreId(), date, CommonString.KEY_VALID);
                        database.updateStatusCheckout(coverageBeanlist.get(i).getStoreId(), date, CommonString.KEY_VALID);
                    }
                }
                if (!RightsTypes.equalsIgnoreCase("Merchandiser")) {
                    if (opening && closing && stock && promotion && asset && competition_sos && listedsku) {
                        database.updateStoreStatusOnCheckout(coverageBeanlist.get(i).getStoreId(), date, CommonString.KEY_VALID);
                        database.updateStatusCheckout(coverageBeanlist.get(i).getStoreId(), date, CommonString.KEY_VALID);
                    }
                }
            }
        }
    }

    public boolean CheckNetAvailability() {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            // we are connected to a network
            connected = true;
        } else {
            Toast.makeText(getApplicationContext(), "Please check internet connection", Toast.LENGTH_SHORT).show();
        }
        return connected;
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return list.size();
        }

        @Override
        public Object getItem(int position) {

            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            // TODO Auto-generated method stub
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.storeviewlist, null);
                holder.storename = (TextView) convertView.findViewById(R.id.storelistviewxml_storename);
                holder.storeaddress = (TextView) convertView.findViewById(R.id.storelistviewxml_storeaddress);
                holder.checkout = (Button) convertView.findViewById(R.id.chkout);
                holder.imgtick = (ImageView) convertView.findViewById(R.id.storelistviewxml_storeico);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.checkout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(StorelistActivity.this);
                    builder.setMessage("Are you sure you want to Checkout").setCancelable(false).setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialog, int id) {
                                    if (CheckNetAvailability()) {
                                        Intent i = new Intent(StorelistActivity.this, CheckOutStoreActivity.class);
                                        i.putExtra(CommonString.KEY_STORE_ID, list.get(position).getStoreid());
                                        startActivity(i);
                                    } else {
                                        Toast.makeText(StorelistActivity.this, "Check internet connection!", Toast.LENGTH_SHORT).show();

                                    }

                                }
                            })
                            .setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(
                                                DialogInterface dialog, int id) {

                                            dialog.cancel();
                                        }
                                    });
                    AlertDialog alert = builder.create();
                    alert.show();

                }
            });

            if ((list.get(position).getCheckout_status().equals(CommonString.KEY_C))) {
                holder.checkout.setBackgroundResource(R.drawable.tick_c);
                holder.checkout.setEnabled(false);
                holder.checkout.setVisibility(View.VISIBLE);
                holder.imgtick.setBackgroundResource(R.drawable.tickgreenv);
                holder.imgtick.setVisibility(View.VISIBLE);

            } else if ((list.get(position).getCheckout_status().equals(CommonString.KEY_VALID))) {
                holder.checkout.setBackgroundResource(R.drawable.checkout);
                holder.checkout.setVisibility(View.VISIBLE);
                holder.checkout.setEnabled(true);

            } else if ((list.get(position).getCheckout_status().equals(CommonString.KEY_INVALID))) {
                holder.checkout.setEnabled(false);
                holder.checkout.setBackgroundResource(R.drawable.checkin_ico);
                holder.checkout.setVisibility(View.VISIBLE);
            } else {
                holder.checkout.setEnabled(false);
                holder.checkout.setVisibility(View.INVISIBLE);

            }

            if (list.get(position).getStatus().equals(CommonString.KEY_U)) {
                if ((list.get(position).getCheckout_status().equalsIgnoreCase(CommonString.KEY_L))) {
                    holder.checkout.setVisibility(View.INVISIBLE);
                    holder.imgtick.setBackgroundResource(R.drawable.leave_tick);

                } else {
                    holder.checkout.setVisibility(View.INVISIBLE);
                    holder.imgtick.setBackgroundResource(R.drawable.tick_u);
                }
            } else if ((list.get(position).getStatus().equals(CommonString.KEY_D))) {
                holder.imgtick.setVisibility(View.VISIBLE);
                holder.imgtick.setBackgroundResource(R.drawable.tick_d);

            } else if ((list.get(position).getStatus().equals(CommonString.KEY_D))) {
                holder.imgtick.setVisibility(View.VISIBLE);
                holder.imgtick.setBackgroundResource(R.drawable.tick_p);
            } else if ((list.get(position).getStatus().equals(CommonString.STORE_STATUS_LEAVE))) {
                holder.imgtick.setVisibility(View.VISIBLE);
                holder.imgtick.setBackgroundResource(R.drawable.tickl);
            } else if (validateCoverage(list.get(position).getStoreid())) {
                holder.imgtick.setVisibility(View.VISIBLE);
                if (leave)
                    holder.imgtick.setBackgroundResource(R.drawable.tickl);
               /* else
                    holder.imgtick.setBackgroundResource(R.drawable.tickgreenv);*/
            } else {
                holder.imgtick.setVisibility(View.VISIBLE);
                holder.imgtick.setBackgroundResource(R.drawable.storeico);

            }
            holder.storename.setText(list.get(position).getStorename());
            holder.storeaddress.setText(list.get(position).getStoreaddress());
            return convertView;
        }

        private class ViewHolder {
            TextView storename, storeaddress;
            ImageView imgtick;
            Button checkout;
        }

        public boolean validateCoverage(String storeid) {
            boolean result = false;
            if (database.CheckMid(date, storeid) > 0) {
                result = true;
                if (database.CheckMidWithStatus(date, storeid).equalsIgnoreCase(CommonString.STORE_STATUS_LEAVE)) {
                    leave = true;
                    database.updateStoreStatusOnLeave(storeid, date, CommonString.STORE_STATUS_LEAVE);
                } else {
                    leave = false;

                }

            }

            return result;

        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        list = database.getStoreData(date);
        StoreBean sb = list.get(position);
        if ((sb.getStatus().equals(CommonString.KEY_U))) {
            if (((sb.getCheckout_status().equalsIgnoreCase(CommonString.KEY_L)))) {
                showToast(AlertMessage.MESSAGE_LEAVE_UPLOAD);
            } else {
                showToast(AlertMessage.MESSAGE_UPLOAD);
            }
        } else if (((sb.getCheckout_status().equals(CommonString.KEY_C)))) {
            showToast(AlertMessage.MESSAGE_CHECKOUT_UPLOAD);

        } else if ((sb.getStatus().equals(CommonString.KEY_D))) {

            showToast(AlertMessage.MESSAGE_DATA_UPLOAD);

        } else if (((sb.getStatus().equals(CommonString.KEY_D)))) {

            showToast(AlertMessage.MESSAGE_PARTIAL_UPLOAD);
        } else if (((sb.getStatus().equals(CommonString.STORE_STATUS_LEAVE)))) {

            showToast(AlertMessage.MESSAGE_LEAVE);
        } else {
            // PUT IN PREFERENCES

            if (preferences.getString(CommonString.KEY_STOREVISITED_STATUS, "no").equalsIgnoreCase("yes")) {

                if (preferences.getString(CommonString.KEY_STOREVISITED, "").equalsIgnoreCase(sb.getStoreid())) {
                    editor = preferences.edit();
                    editor.putString(CommonString.KEY_STORE_ID, sb.getStoreid());
                    editor.putString(CommonString.KEY_STORE_NAME, sb.getStorename());
                    editor.commit();
                    intent = new Intent(getBaseContext(), StoreVisitedActivity.class);
                    startActivity(intent);
                    this.finish();

                } else {
                    Toast.makeText(StorelistActivity.this, "You Can't Checkin,First Checkout the Previous Partner", Toast.LENGTH_SHORT).show();
                }

            } else {

                // PUT IN PREFERENCES
                editor = preferences.edit();
                editor.putString(CommonString.KEY_STORE_ID, sb.getStoreid());
                editor.putString(CommonString.KEY_STORE_NAME, sb.getStorename());
                editor.putString(CommonString.KEY_VISIT_DATE, date);
                editor.putString(CommonString.KEY_STORE_CATEGORY, sb.getCategory());
                editor.putString(CommonString.KEY_STORE_TAM, sb.getTam());
                editor.commit();
                intent = new Intent(getBaseContext(), StoreVisitedActivity.class);
                startActivity(intent);
                this.finish();
            }

        }

    }

    public void onBackClick(View v) {

        Intent i = new Intent(this, MainMenuActivity.class);
        startActivity(i);
        StorelistActivity.this.finish();
    }

    @Override
    public void onBackPressed() {

        Intent i = new Intent(this, MainMenuActivity.class);
        startActivity(i);
        StorelistActivity.this.finish();
    }

    private void showToast(String message) {
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
    }
}
