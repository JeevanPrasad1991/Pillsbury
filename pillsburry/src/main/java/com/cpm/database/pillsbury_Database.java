package com.cpm.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.cpm.Constants.CommonString;
import com.cpm.delegates.AssetTrackerBean;

import com.cpm.delegates.CallsBean;

import com.cpm.delegates.ClosingStockBean;
import com.cpm.delegates.Consolidatebean;
import com.cpm.delegates.CoverageBean;
import com.cpm.delegates.DesignationBean;
import com.cpm.delegates.GeotaggingBeans;
import com.cpm.delegates.JobLevelBean;
import com.cpm.delegates.LunchBean;
import com.cpm.delegates.MidDayRecievedStockBean;
import com.cpm.delegates.OpeningStockBean;
import com.cpm.delegates.PerformanceBean;
import com.cpm.delegates.PerformanceDataBean;
import com.cpm.delegates.Posmbean;
import com.cpm.delegates.PromotionTrackingBean;
import com.cpm.delegates.ReasonModel;
import com.cpm.delegates.SalesPersonBean;


import com.cpm.delegates.StoreBean;
import com.cpm.delegates.Storenamebean;
import com.cpm.delegates.TrainingBean;
import com.cpm.delegates.competitionbeans;
import com.cpm.delegates.salesBean;
import com.cpm.delegates.updateTamBean;
import com.cpm.xmlGetterSetter.AssetPostgetterSetter;
import com.cpm.xmlGetterSetter.AvailabilitygetterSetter;
import com.cpm.xmlGetterSetter.ConsolidatePerGetterSetter;
import com.cpm.xmlGetterSetter.Designationgettersetter;
import com.cpm.xmlGetterSetter.GeoTaggettersetter;
import com.cpm.xmlGetterSetter.JCPGetterSetter;
import com.cpm.xmlGetterSetter.Joblevelsgettersetter;
import com.cpm.xmlGetterSetter.MappingAssetPostgetterSetter;
import com.cpm.xmlGetterSetter.MappingPromotionpostgetterSetter;
import com.cpm.xmlGetterSetter.NonWorkingGetterSetter;
import com.cpm.xmlGetterSetter.PerformanceMSetterGetter;
import com.cpm.xmlGetterSetter.SKUPostgetterSetter;
import com.cpm.xmlGetterSetter.SalesBeanGetterSetter;
import com.cpm.xmlGetterSetter.TableBean;
import com.cpm.xmlGetterSetter.TamgetterSetter;
import com.cpm.xmlGetterSetter.downloadtraingtopicsgettersetter;
import com.cpm.xmlGetterSetter.planogramSetterGetter;
import com.cpm.xmlGetterSetter.posmgettersetter;
import com.cpm.xmlGetterSetter.storekeycontactdetailsgettersetter;

public class pillsbury_Database extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "PILLSBURY_DATABASE_1";
    public static final int DATABASE_VERSION = 5;
    private SQLiteDatabase db;
    public pillsbury_Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void open() {
        try {
            db = this.getWritableDatabase();
        } catch (Exception e) {
        }
    }

    public void close() {
        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TableBean.getMapping_PromotionPost_data());
        db.execSQL(CommonString.CREATE_TABLE_COVERAGE_DATA);
        db.execSQL(CommonString.CREATE_TABLE_STORE_MASTER);
        db.execSQL(CommonString.CREATE_TABLE_NON_WORKING);
        db.execSQL(CommonString.CREATE_TABLE_AVAILABILITY_DATA);
        db.execSQL(CommonString.CREATE_TABLE_ASSETPOST);
        db.execSQL(CommonString.CREATE_TABLE_SKU_POST);
        db.execSQL(CommonString.CREATE_TABLE_MAPPINGASSET_POST);
        db.execSQL(CommonString.CREATE_TABLE_OPENINGSTOCK_DATA);
        db.execSQL(CommonString.CREATE_TABLE_MIDDAYSTOCK_RECIEVED);
        db.execSQL(CommonString.CREATE_TABLE_CLOSINGSTOCK);
        db.execSQL(CommonString.CREATE_TABLE_CALLS_TRACKER);
        db.execSQL(CommonString.CREATE_TABLE_insert_CHECKLIST_DATA);
        db.execSQL(CommonString.CREATE_TABLE_insertHeader_STOCKRECIEVED_DATA);
        db.execSQL(CommonString.CREATE_TABLE_insertHeader_CLOSINGSTOCK_DATA);
        db.execSQL(CommonString.CREATE_TABLE_PROMOTION_DATA);
        db.execSQL(CommonString.CREATE_TABLE_insertHEADER_PROMOTION_DATA);
        db.execSQL(CommonString.CREATE_TABLE_INSERT_LUNCH_TIME);
        db.execSQL(CommonString.CREATE_TABLE_ASSET_DATA);
        db.execSQL(CommonString.CREATE_TABLE_insertHEADER_ASSET_DATA);
        db.execSQL(CommonString.CREATE_TABLE_PERFORMANCE);
        db.execSQL(CommonString.CREATE_TABLE_CALLS_TRACKER2);
        db.execSQL(CommonString.CREATE_TABLE_PLANOGRAM);
        db.execSQL(CommonString.CREATE_TABLE_CATEGORY_MASTER);
        db.execSQL(CommonString.CREATE_TABLE_COMPETITION_FACING);
        db.execSQL(CommonString.CREATE_TABLE_NON_ASSET_REASON_MASTER);
        db.execSQL(CommonString.CREATE_TABLE_NON_PROMOTION_REASON_MASTER);
        db.execSQL(CommonString.CREATE_TABLE_POSM_MASTER);
        db.execSQL(CommonString.CREATE_TABLE_ADDITIONAL_DETAILS);
        db.execSQL(CommonString.CREATE_TABLE_CLOSINGSTOCK_LISTEDDATA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_TAM_DETAIL);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_CONSOLIDATE);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_COVERAGE_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_TAM_INSERTED);

        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_STORE_DETAIL);
        db.execSQL("DROP TABLE IF EXISTS "
                + CommonString.TABLE_NON_WORKING_REASON);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_SALES);

        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_POSM);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_JOB_LEVELS);
        db.execSQL("DROP TABLE IF EXISTS "
                + CommonString.TABLE_STOREKEY_CONTACT_DETAILS);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_TRAINING_TOPICS);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_DESIGNATION);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_SALES_INSERTED);
        db.execSQL("DROP TABLE IF EXISTS "
                + CommonString.TABLE_COMPETITION_INSERTED);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_POSM_INSERTED);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_GEOTAG_DETAIL);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_INSERT_GEO_TAG);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_ADD_SALESPERSON);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_ADD_TRAINING);

        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_ASSETPOST_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_SKUPOST_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_AVAILABILITY_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_MAPPING_ASSETPOST);
        //db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_MAPPING_PROMOTIONPOST);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_OENGINGSTOCK_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_MIDDAYSTOCK_RECIEVED_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_CLOSING_STOCK_DATA);

        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_CALLS_TRACKER_DATA);


        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_INSERT_OPENINGHEADER_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_INSERT_STOCKRECIEVED_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_INSERT_CLOSINGSTOCK_DATA);

        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_INSERT_HEADER_PROMOTION_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_INSERT_PROMOTION_DATA);

        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_INSERT_HEADER_ASSET_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_INSERT_ASSET_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_LUNCH_TIME);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_PERFORMANCE_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_CALLS_TRACKER_DATA_12To3);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_PLANOGRAM);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_CATEGORY_MASTER);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_INSERT_COMPETITION_DATA);

        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_NON_ASSET_REASON_MASTER);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_NON_PROMOTION_REASON_MASTER);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_NON_PROMOTION_REASON_MASTER);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_INSERT_ADDTIONAL_DETAILS);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_INSERT_ADDTIONAL_DETAILS);


        //db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_MAPPING_PROMOTIONPOST);

        onCreate(db);
    }


    public void dropTablesBeforeCreate() {
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_TAM_DETAIL);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_CONSOLIDATE);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_COVERAGE_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_TAM_INSERTED);

        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_STORE_DETAIL);
        db.execSQL("DROP TABLE IF EXISTS "
                + CommonString.TABLE_NON_WORKING_REASON);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_SALES);

        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_POSM);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_JOB_LEVELS);
        db.execSQL("DROP TABLE IF EXISTS "
                + CommonString.TABLE_STOREKEY_CONTACT_DETAILS);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_TRAINING_TOPICS);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_DESIGNATION);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_SALES_INSERTED);
        db.execSQL("DROP TABLE IF EXISTS "
                + CommonString.TABLE_COMPETITION_INSERTED);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_POSM_INSERTED);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_GEOTAG_DETAIL);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_INSERT_GEO_TAG);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_ADD_SALESPERSON);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_ADD_TRAINING);

        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_ASSETPOST_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_SKUPOST_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_AVAILABILITY_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_MAPPING_ASSETPOST);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_MAPPING_PROMOTIONPOST);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_OENGINGSTOCK_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_MIDDAYSTOCK_RECIEVED_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_CLOSING_STOCK_DATA);

        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_CALLS_TRACKER_DATA);


        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_INSERT_OPENINGHEADER_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_INSERT_STOCKRECIEVED_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_INSERT_CLOSINGSTOCK_DATA);

        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_INSERT_HEADER_PROMOTION_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_INSERT_PROMOTION_DATA);

        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_INSERT_HEADER_ASSET_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_INSERT_ASSET_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_LUNCH_TIME);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_PERFORMANCE_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_CALLS_TRACKER_DATA_12To3);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_PLANOGRAM);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_CATEGORY_MASTER);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_INSERT_COMPETITION_DATA);

        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_NON_ASSET_REASON_MASTER);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_NON_PROMOTION_REASON_MASTER);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_POSM_MASTER);

        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_INSERT_ADDTIONAL_DETAILS);
        db.execSQL("DROP TABLE IF EXISTS " + CommonString.TABLE_CLOSING_STOCK_LISTED_DATA);


        onCreate(db);
    }

    public void deleteAllTables() {
        //	db.delete(CommonString.TABLE_PERFORMANCE, null, null);
        //	db.delete(CommonString.TABLE_CONSOLIDATE, null, null);
        //db.delete(CommonString.TABLE_TAM_DETAIL, null, null);
        //db.delete(CommonString.TABLE_TAM_INSERTED, null, null);
        //db.delete(CommonString.TABLE_STORE_DETAIL, null, null);
        db.delete(CommonString.TABLE_COVERAGE_DATA, null, null);
        //db.delete(CommonString.TABLE_NON_WORKING_REASON, null, null);
        db.delete(CommonString.TABLE_CALLS_TRACKER_DATA, null, null);
        db.delete(CommonString.TABLE_INSERT_OPENINGHEADER_DATA, null, null);
        db.delete(CommonString.TABLE_INSERT_STOCKRECIEVED_DATA, null, null);
        db.delete(CommonString.TABLE_INSERT_CLOSINGSTOCK_DATA, null, null);
        db.delete(CommonString.TABLE_INSERT_HEADER_PROMOTION_DATA, null, null);
        db.delete(CommonString.TABLE_INSERT_PROMOTION_DATA, null, null);
        db.delete(CommonString.TABLE_INSERT_HEADER_ASSET_DATA, null, null);
        db.delete(CommonString.TABLE_INSERT_ASSET_DATA, null, null);
        db.delete(CommonString.TABLE_CALLS_TRACKER_DATA, null, null);
        db.delete(CommonString.TABLE_CALLS_TRACKER_DATA_12To3, null, null);
        db.delete(CommonString.TABLE_OENGINGSTOCK_DATA, null, null);
        db.delete(CommonString.TABLE_MIDDAYSTOCK_RECIEVED_DATA, null, null);
        db.delete(CommonString.TABLE_CLOSING_STOCK_DATA, null, null);
        db.delete(CommonString.TABLE_CALLS_TRACKER_DATA, null, null);
        db.delete(CommonString.TABLE_INSERT_PROMOTION_DATA, null, null);
        db.delete(CommonString.TABLE_LUNCH_TIME, null, null);
        db.delete(CommonString.TABLE_PERFORMANCE_DATA, null, null);
        db.delete(CommonString.TABLE_CALLS_TRACKER_DATA_12To3, null, null);
        db.delete(CommonString.TABLE_PLANOGRAM, null, null);
        db.delete(CommonString.TABLE_CATEGORY_MASTER, null, null);
        db.delete(CommonString.TABLE_INSERT_COMPETITION_DATA, null, null);
        db.delete(CommonString.TABLE_INSERT_ADDTIONAL_DETAILS, null, null);
        db.delete(CommonString.TABLE_INSERT_HEADER_PROMOTION_DATA, null, null);
        // TABLE_INSERT_HEADER_PROMOTION_DATA
        db.delete(CommonString.TABLE_INSERT_PROMOTION_DATA, null, null);
        db.delete(CommonString.TABLE_CLOSING_STOCK_LISTED_DATA, null, null);


    }

    public void AddTraining(Long mid, String storeid, String salespersonid, String salesperson, String trainingid, String training) {
        String Mid = String.valueOf(mid);
        ContentValues values = new ContentValues();

        try {

            values.put(CommonString.Key_Mid, Mid);
            values.put(CommonString.KEY_CONTACT_CD, salespersonid);
            values.put(CommonString.KEY_NAME, salesperson);
            values.put(CommonString.KEY_TRAINING_ID, trainingid);
            values.put(CommonString.KEY_TRAINING, training);
            db.insert(CommonString.TABLE_ADD_TRAINING, null, values);

        } catch (Exception ex) {
            Log.d("Database Exception while Insert Store Data ",
                    ex.toString());
        }

    }

    public ArrayList<SalesPersonBean> getSalesPersonData(long mid) {

        String Mid = String.valueOf(mid);

        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");
        ArrayList<SalesPersonBean> list = new ArrayList<SalesPersonBean>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT  * from "
                    + CommonString.TABLE_ADD_SALESPERSON + " where "
                    + CommonString.Key_Mid + " = '" + Mid + "' ", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {

                    SalesPersonBean salesp = new SalesPersonBean();

                    salesp.setId(dbcursor.getInt(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_ID)));
                    salesp.setTitle(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_TITLE)));
                    salesp.setName(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_NAME)));
                    salesp.setDesignation(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_DESIGNATION_CD)));

                    salesp.setJoblevel(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.key_JOBLEVEL_ID)));
                    salesp.setEmail(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_EMAIL)));

                    salesp.setMobile(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_MOBILE)));
                    salesp.setPhone(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_PHONE)));

                    list.add(salesp);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return list;

    }

    public ArrayList<TrainingBean> getAddedTrainingData(long mid) {

        String Mid = String.valueOf(mid);

        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");
        ArrayList<TrainingBean> list = new ArrayList<TrainingBean>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT  * from "
                    + CommonString.TABLE_ADD_TRAINING + " where "
                    + CommonString.Key_Mid + " = '" + Mid + "' ", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    TrainingBean des = new TrainingBean();
                    des.setContact_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_CONTACT_CD)));
                    des.setSalesperson(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_NAME)));
                    des.setTraining_id(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_TRAINING_ID)));
                    des.setTraining(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_TRAINING)));

                    list.add(des);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return list;

    }

    public ArrayList<DesignationBean> getDesignation() {

        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");
        ArrayList<DesignationBean> list = new ArrayList<DesignationBean>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT  * from "
                    + CommonString.TABLE_DESIGNATION, null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    DesignationBean des = new DesignationBean();
                    des.setDesignation_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.Key_DESIGNATION_CD)));

                    des.setDesignation(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.Key_DESIGNATION)));

                    list.add(des);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return list;

    }

    public long AddSalesPerson(Long mid, String storeid, String title,
                               String name, String designation, String joblevel, String email,
                               String mobile, String phone) {
        long contact_id = 0;

        String Mid = String.valueOf(mid);

        ContentValues values = new ContentValues();

        try {

            values.put(CommonString.Key_Mid, Mid);
            values.put(CommonString.KEY_TITLE, title);
            values.put(CommonString.KEY_NAME, name);
            values.put(CommonString.KEY_DESIGNATION_CD, designation);
            values.put(CommonString.key_JOBLEVEL_ID, joblevel);
            values.put(CommonString.KEY_EMAIL, email);
            values.put(CommonString.KEY_MOBILE, mobile);
            values.put(CommonString.KEY_PHONE, phone);

            contact_id = db.insert(CommonString.TABLE_ADD_SALESPERSON, null,
                    values);

        } catch (Exception ex) {
            Log.d("Database Exception while Insert Store Data ",
                    ex.toString());
        }

        return contact_id;

    }

    public void AddSalesPersonInStoreContact(String storeid, String title,
                                             String name, String designation, String joblevel, String email,
                                             String mobile, String phone, String contactid) {

        ContentValues values = new ContentValues();

        try {

            values.put(CommonString.key_STORE_CD, storeid);
            values.put(CommonString.KEY_TITLE, title);
            values.put(CommonString.key_NAME, name);
            values.put(CommonString.KEY_DESIGNATION_CD, designation);
            values.put(CommonString.key_JOBLEVEL_ID, joblevel);
            values.put(CommonString.KEY_BUSINESS_EMAIL, email);
            values.put(CommonString.key_MOBILE, mobile);
            values.put(CommonString.KEY_BUSINESS_PHONE, phone);
            values.put(CommonString.KEY_CONTACT_CD, contactid);
            values.put(CommonString.KEY_FLAG, "new");

            db.insert(CommonString.TABLE_STOREKEY_CONTACT_DETAILS, null, values);

        } catch (Exception ex) {
            Log.d("Database Exception while Insert Store Data ",
                    ex.toString());
        }

    }

    public ArrayList<JobLevelBean> getJobLevel() {

        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");
        ArrayList<JobLevelBean> list = new ArrayList<JobLevelBean>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT  * from "
                    + CommonString.TABLE_JOB_LEVELS, null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    JobLevelBean des = new JobLevelBean();
                    des.setJOB_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.key_JOBLEVEL_ID)));

                    des.setJOB(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.Key_JOB_LEVEL)));

                    list.add(des);

                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return list;

    }

    public ArrayList<SalesPersonBean> getSalesperson(String storeid) {

        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");
        ArrayList<SalesPersonBean> list = new ArrayList<SalesPersonBean>();
        Cursor dbcursor = null;

        try {
            dbcursor = db
                    .rawQuery(
                            "SELECT CONTACT_CD,NAME,DESIGNATION from STOREKEY_CONTACT_DETAILS ST INNER JOIN DESIGNATION_DETAILS DS ON DS.DESIGNATION_CD = ST.DESIGNATION_CD WHERE STORE_CD ='"
                                    + storeid + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    SalesPersonBean des = new SalesPersonBean();
                    des.setContact_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_CONTACT_CD)));

                    des.setName(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.key_NAME)));
                    des.setDesignation(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.Key_DESIGNATION)));

                    list.add(des);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return list;

    }

    public ArrayList<TrainingBean> getAddedTrainingData() {

        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");
        ArrayList<TrainingBean> list = new ArrayList<TrainingBean>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT  * from "
                    + CommonString.TABLE_ADD_TRAINING, null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    TrainingBean des = new TrainingBean();
                    des.setSalesperson(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_NAME)));
                    des.setTraining_id(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_TRAINING_ID)));
                    des.setTraining(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_TRAINING)));

                    list.add(des);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "--------");
        return list;

    }

    public void InsertOpeningStocklistData(long mid, String storeid,
                                           HashMap<OpeningStockBean, List<OpeningStockBean>> data, List<OpeningStockBean> save_listDataHeader) {

        ContentValues values = new ContentValues();
        ContentValues values1 = new ContentValues();

        try {

            db.beginTransaction();
            for (int i = 0; i < save_listDataHeader.size(); i++) {

                values.put(CommonString.MID, mid);
                values.put(CommonString.key_STORE_CD, storeid);

                values.put(CommonString.KEY_BRAND_CD, save_listDataHeader.get(i)
                        .getBRAND_CD());
                values.put(CommonString.Key_BRAND, save_listDataHeader
                        .get(i).getBRAND());

                long l = db.insert(CommonString.TABLE_INSERT_OPENINGHEADER_DATA,
                        null, values);

                for (int j = 0; j < data.get(save_listDataHeader.get(i)).size(); j++) {

                    values1.put(CommonString.KEY_COMMONID, l);
                    values1.put(CommonString.key_STORE_CD, storeid);
                    values1.put(CommonString.KEY_SHELF,
                            data.get(save_listDataHeader.get(i)).get(j)
                                    .getShelf());

                    values1.put(CommonString.KEY_FACING,
                            data.get(save_listDataHeader.get(i)).get(j)
                                    .getFacing());
                    values1.put(CommonString.KEY_OPENINGSTOCK,
                            data.get(save_listDataHeader.get(i)).get(j)
                                    .getOpeningStock());
                    values1.put(CommonString.KEY_PRICING,
                            data.get(save_listDataHeader.get(i)).get(j)
                                    .getPricing());
                    values1.put(CommonString.KEY_PRICE_SIGNOGE,
                            data.get(save_listDataHeader.get(i)).get(j)
                                    .getPrice_sign());
                    values1.put(CommonString.KEY_SIGNOGE_CORRECTED,
                            data.get(save_listDataHeader.get(i)).get(j)
                                    .getSign_corrected());

                    values1.put(CommonString.KEY_EXPIRING_STOCK,
                            data.get(save_listDataHeader.get(i)).get(j)
                                    .getExpriring_stock());

                    values1.put(CommonString.KEY_POG_ADHERENCE,
                            data.get(save_listDataHeader.get(i)).get(j)
                                    .getPog_adherence());

                    values1.put(CommonString.KEY_POG_CORRECTED,
                            data.get(save_listDataHeader.get(i)).get(j)
                                    .getPog_corrected());

                    values1.put(CommonString.KEY_SHELF_FM_BOTTOM,
                            data.get(save_listDataHeader.get(i)).get(j)
                                    .getShelf_fm_bottom());

                    values1.put(CommonString.KEY_SKU,
                            data.get(save_listDataHeader.get(i)).get(j)
                                    .getSKU());

                    values1.put(CommonString.KEY_SKU_CD,
                            data.get(save_listDataHeader.get(i)).get(j)
                                    .getSKU_CD());

                    values1.put(CommonString.KEY_EXPIRING_DATE, data.get(save_listDataHeader.get(i)).get(j).getExpiring_date());

                    values1.put(CommonString.KEY_LISTED_CHECKBOX, "1");

					/*values1.put(CommonString.KEY_LISTED_CHECKBOX,
                            data.get(save_listDataHeader.get(i)).get(j)
									.getListedFlag());*/

                    db.insert(CommonString.TABLE_OENGINGSTOCK_DATA, null, values1);

                }
            }
            db.setTransactionSuccessful();
            db.endTransaction();
        } catch (Exception ex) {
            Log.d("Database Exception while Insert Posm Master Data ",
                    ex.toString());
        }

    }


    public void InsertPromotionData(long mid, String storeid, HashMap<MappingPromotionpostgetterSetter,
            List<MappingPromotionpostgetterSetter>> data,
                                    List<MappingPromotionpostgetterSetter> save_listDataHeader) {

        ContentValues values = new ContentValues();
        ContentValues values1 = new ContentValues();

        try {

            db.beginTransaction();

            for (int i = 0; i < save_listDataHeader.size(); i++) {

                values.put(CommonString.MID, mid);
                values.put(CommonString.key_STORE_CD, storeid);
                values.put(CommonString.KEY_SKU, save_listDataHeader.get(i).getSKU().get(0));
                values.put(CommonString.KEY_SKU_CD, save_listDataHeader.get(i).getSKU_CD().get(0));
                long l = db.insert(CommonString.TABLE_INSERT_HEADER_PROMOTION_DATA, null, values);


                for (int j = 0; j < data.get(save_listDataHeader.get(i)).size(); j++) {
                    values1.put(CommonString.KEY_COMMONID, l);
                    values1.put(CommonString.key_STORE_CD, storeid);
                   /* values1.put(CommonString.KEY_SKU, save_listDataHeader.get(i).getSKU().get(0));
                    values1.put(CommonString.KEY_SKU_CD, save_listDataHeader.get(i).getSKU_CD().get(0));*/

                    //values.put(CommonString.KEY_ID1, data.getID().get(i));
                    values1.put(CommonString.KEY_SKU, save_listDataHeader.get(i).getSKU().get(0));
                    values1.put(CommonString.KEY_SKU_CD, save_listDataHeader.get(i).getSKU_CD().get(0));

                    values1.put(CommonString.KEY_PROMOTION_CD, data.get(save_listDataHeader.get(i)).get(j).getID().get(0));
                    values1.put(CommonString.KEY_PROMOTION, data.get(save_listDataHeader.get(i)).get(j).getPROMOTION().get(0));
                    values1.put(CommonString.KEY_PROMOTION_AVAIL, data.get(save_listDataHeader.get(i)).get(j).getPromotion_avail());
                    values1.put(CommonString.KEY_REMARKS, data.get(save_listDataHeader.get(i)).get(j).getRemark());
                    values1.put(CommonString.KEY_REASON_ID, data.get(save_listDataHeader.get(i)).get(j).getReasonid());
                    values1.put(CommonString.KEY_REASON, data.get(save_listDataHeader.get(i)).get(j).getReason());
                    values1.put(CommonString.KEY_IMAGE, data.get(save_listDataHeader.get(i)).get(j).getImage());
                    db.insert(CommonString.TABLE_INSERT_PROMOTION_DATA, null, values1);
                }
            }
            db.setTransactionSuccessful();
            db.endTransaction();
        } catch (Exception ex) {
            Log.d("Database Exception while Inserting Promotion Data ",
                    ex.toString());
        }

    }


    public void deleteOpeningStockData(String storeid) {

        try {

            db.delete(CommonString.TABLE_OENGINGSTOCK_DATA,
                    CommonString.KEY_STORE_CD + "='" + storeid + "'", null);

            db.delete(CommonString.TABLE_INSERT_OPENINGHEADER_DATA,
                    CommonString.KEY_STORE_CD + "='" + storeid + "'", null);

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());

        }
    }


    public void deleteStockRecievedData(String storeid) {

        try {

            db.delete(CommonString.TABLE_INSERT_STOCKRECIEVED_DATA,
                    CommonString.KEY_STORE_CD + "='" + storeid + "'", null);

            db.delete(CommonString.TABLE_MIDDAYSTOCK_RECIEVED_DATA,
                    CommonString.KEY_STORE_CD + "='" + storeid + "'", null);

        } catch (Exception e) {
            Log.d("Exception when deleting Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());

        }
    }


    public void deletePromotionData(String storeid) {

        try {
            db.delete(CommonString.TABLE_INSERT_HEADER_PROMOTION_DATA, CommonString.KEY_STORE_CD + "='" + storeid + "'", null);
            db.delete(CommonString.TABLE_INSERT_PROMOTION_DATA, CommonString.KEY_STORE_CD + "='" + storeid + "'", null);

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());

        }
    }


    public void deleteAssetData(String storeid) {

        try {

            db.delete(CommonString.TABLE_INSERT_HEADER_ASSET_DATA, CommonString.KEY_STORE_CD + "='" + storeid + "'", null);

            db.delete(CommonString.TABLE_INSERT_ASSET_DATA, CommonString.KEY_STORE_CD + "='" + storeid + "'", null);

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());

        }
    }


    public void deleteCompetitionData(String storeid) {

        try {

            db.delete(CommonString.TABLE_INSERT_COMPETITION_DATA,
                    CommonString.KEY_STORE_CD + "='" + storeid + "'", null);

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());

        }
    }


    public void deleteClosingStockData(String storeid) {

        try {

            db.delete(CommonString.TABLE_INSERT_CLOSINGSTOCK_DATA,
                    CommonString.KEY_STORE_CD + "='" + storeid + "'", null);

            db.delete(CommonString.TABLE_CLOSING_STOCK_DATA,
                    CommonString.KEY_STORE_CD + "='" + storeid + "'", null);

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());

        }
    }


    public void InsertAssetData(long mid, String storeid,
                                HashMap<AssetTrackerBean, List<AssetTrackerBean>> data, List<AssetTrackerBean> save_listDataHeader) {

        ContentValues values = new ContentValues();
        ContentValues values1 = new ContentValues();

        try {
            db.beginTransaction();
            for (int i = 0; i < save_listDataHeader.size(); i++) {
                values.put(CommonString.MID, mid);
                values.put(CommonString.key_STORE_CD, storeid);
                values.put(CommonString.KEY_BRAND_CD, save_listDataHeader.get(i).getBrand_cd());
                values.put(CommonString.Key_BRAND, save_listDataHeader.get(i).getBrand());
                long l = db.insert(CommonString.TABLE_INSERT_HEADER_ASSET_DATA, null, values);
                for (int j = 0; j < data.get(save_listDataHeader.get(i)).size(); j++) {
                    values1.put(CommonString.KEY_COMMONID, l);
                    values1.put(CommonString.key_STORE_CD, storeid);
                    values1.put(CommonString.KEY_ASSET_NAME, data.get(save_listDataHeader.get(i)).get(j).getAsset_name());
                    values1.put(CommonString.KEY_ASSET_CD, data.get(save_listDataHeader.get(i)).get(j).getAsset_cd());
                    values1.put(CommonString.key_count, data.get(save_listDataHeader.get(i)).get(j).getCount());
                    values1.put(CommonString.key_actualCount, data.get(save_listDataHeader.get(i)).get(j).getActualCount());
                    values1.put(CommonString.KEY_ASSET_AVAIL, data.get(save_listDataHeader.get(i)).get(j).getAsset_avail());
                    values1.put(CommonString.KEY_REASON_ID, data.get(save_listDataHeader.get(i)).get(j).getReasonid());
                    values1.put(CommonString.KEY_REASON, data.get(save_listDataHeader.get(i)).get(j).getReason());

                    values1.put(CommonString.KEY_IMAGE, data.get(save_listDataHeader.get(i)).get(j).getImage());

                    values1.put(CommonString.KEY_REMARKS, data.get(save_listDataHeader.get(i)).get(j).getRemark());
                    db.insert(CommonString.TABLE_INSERT_ASSET_DATA, null, values1);

                }
            }
            db.setTransactionSuccessful();
            db.endTransaction();
        } catch (Exception ex) {
            Log.d("Database Exception while Inserting Asset Data ",
                    ex.toString());
        }

    }


    public void InsertStockRecievedlistData(long mid, String storeid,
                                            HashMap<MidDayRecievedStockBean, List<MidDayRecievedStockBean>> data,
                                            List<MidDayRecievedStockBean> save_listDataHeader) {

        ContentValues values = new ContentValues();
        ContentValues values1 = new ContentValues();

        try {

            db.beginTransaction();

            for (int i = 0; i < save_listDataHeader.size(); i++) {
                values.put(CommonString.MID, mid);
                values.put(CommonString.key_STORE_CD, storeid);

                values.put(CommonString.KEY_BRAND_CD, save_listDataHeader.get(i)
                        .getBRAND_CD());
                values.put(CommonString.Key_BRAND, save_listDataHeader
                        .get(i).getBRAND());

                long l = db.insert(CommonString.TABLE_INSERT_STOCKRECIEVED_DATA, null, values);

                for (int j = 0; j < data.get(save_listDataHeader.get(i)).size(); j++) {

                    values1.put(CommonString.KEY_COMMONID, l);
                    values1.put(CommonString.key_STORE_CD, storeid);
                    values1.put(CommonString.KEY_SKU, data.get(save_listDataHeader.get(i)).get(j).getSKU());
                    values1.put(CommonString.KEY_SKU_CD, data.get(save_listDataHeader.get(i)).get(j).getSKU_CD());
                    values1.put(CommonString.KEY_LISTED_CHECKBOX, "1");
                    // values1.put(CommonString.KEY_LISTED_CHECKBOX, data.get(save_listDataHeader.get(i)).get(j).getListedFlag());

                    String stock = data.get(save_listDataHeader.get(i)).get(j).getStock();

                    if (stock != null && stock.equalsIgnoreCase("")) {
                        stock = "0";
                    }


                    values1.put(CommonString.KEY_STOCK, stock);
                    db.insert(CommonString.TABLE_MIDDAYSTOCK_RECIEVED_DATA, null, values1);

                }
            }
            db.setTransactionSuccessful();
            db.endTransaction();
        } catch (Exception ex) {
            Log.d("Database Exception while Insert Posm Master Data ",
                    ex.toString());
        }

    }


    public void insertStockLidtedData(String storeid, List<MidDayRecievedStockBean> save_listDataHeader) {
        db.delete(CommonString.TABLE_CLOSING_STOCK_LISTED_DATA, CommonString.KEY_STORE_CD + "='" + storeid + "'", null);
        ContentValues values1 = new ContentValues();
        try {

            db.beginTransaction();
            for (int j = 0; j < save_listDataHeader.size(); j++){

                values1.put(CommonString.key_STORE_CD, storeid);
                values1.put(CommonString.KEY_SKU, save_listDataHeader.get(j).getSKU());
                values1.put(CommonString.KEY_SKU_CD, save_listDataHeader.get(j).getSKU_CD());
                values1.put(CommonString.KEY_BRAND_CD, save_listDataHeader.get(j).getBRAND_CD());
                values1.put(CommonString.KEY_LISTED_TOGGLE, save_listDataHeader.get(j).isListed);

                db.insert(CommonString.TABLE_CLOSING_STOCK_LISTED_DATA, null, values1);
            }

            db.setTransactionSuccessful();
            db.endTransaction();
        } catch (Exception ex) {
            Log.d("Database Exception while Insert Posm Master Data ",
                    ex.toString());
        }


    }


    public void InsertClosingStocklistData(long mid, String storeid,
                                           HashMap<ClosingStockBean, List<ClosingStockBean>> data, List<ClosingStockBean> save_listDataHeader) {

        ContentValues values = new ContentValues();
        ContentValues values1 = new ContentValues();

        try {

            db.beginTransaction();
            for (int i = 0; i < save_listDataHeader.size(); i++) {

                values.put(CommonString.MID, mid);
                values.put(CommonString.key_STORE_CD, storeid);

                values.put(CommonString.KEY_BRAND_CD, save_listDataHeader.get(i)
                        .getBRAND_CD());
                values.put(CommonString.Key_BRAND, save_listDataHeader
                        .get(i).getBRAND());

                long l = db.insert(CommonString.TABLE_INSERT_CLOSINGSTOCK_DATA,
                        null, values);

                for (int j = 0; j < data.get(save_listDataHeader.get(i)).size(); j++) {

                    values1.put(CommonString.KEY_COMMONID, l);
                    values1.put(CommonString.key_STORE_CD, storeid);
                    values1.put(CommonString.KEY_SKU,
                            data.get(save_listDataHeader.get(i)).get(j)
                                    .getSKU());

                    values1.put(CommonString.KEY_SKU_CD,
                            data.get(save_listDataHeader.get(i)).get(j)
                                    .getSKU_CD());
                    values1.put(CommonString.KEY_STOCK, data.get(save_listDataHeader.get(i)).get(j).getStock());
                    values1.put(CommonString.KEY_LISTED_CHECKBOX, "1");
                    // values1.put(CommonString.KEY_LISTED_CHECKBOX, data.get(save_listDataHeader.get(i)).get(j).getListed());


                    db.insert(CommonString.TABLE_CLOSING_STOCK_DATA, null, values1);

                }
            }
            db.setTransactionSuccessful();
            db.endTransaction();
        } catch (Exception ex) {
            Log.d("Database Exception while Insert Posm Master Data ",
                    ex.toString());
        }

    }



    public ArrayList<TrainingBean> getTraining() {

        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");
        ArrayList<TrainingBean> list = new ArrayList<TrainingBean>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT  * from "
                    + CommonString.TABLE_TRAINING_TOPICS, null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    TrainingBean des = new TrainingBean();
                    des.setTraining_id(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_TOPIC_CD)));

                    des.setTraining(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.key_TOPIC)));
                    list.add(des);

                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return list;

    }

    public void InsertStoreKeyContact(storekeycontactdetailsgettersetter data) {

        db.delete(CommonString.TABLE_STOREKEY_CONTACT_DETAILS,
                CommonString.KEY_FLAG + " = 'old'", null);
        ContentValues values = new ContentValues();

        try {

            for (int i = 0; i < data.getJoblevel_id().size(); i++) {

                values.put(CommonString.KEY_CONTACT_CD, data.getContact_cd()
                        .get(i));
                values.put(CommonString.key_STORE_CD, data.getStore_cd().get(i));
                values.put(CommonString.KEY_TITLE, data.getTitle().get(i));
                values.put(CommonString.key_NAME, data.getName().get(i));
                values.put(CommonString.KEY_DESIGNATION_CD, data
                        .getDesignation_cd().get(i));
                values.put(CommonString.key_JOBLEVEL_ID, data.getJoblevel_id()
                        .get(i));
                values.put(CommonString.KEY_BUSINESS_EMAIL, data
                        .getBusiness_email().get(i));
                values.put(CommonString.key_MOBILE, data.getMobile().get(i));
                values.put(CommonString.KEY_BUSINESS_PHONE, data
                        .getBusiness_phone().get(i));
                values.put(CommonString.KEY_FLAG, "old");
                db.insert(CommonString.TABLE_STOREKEY_CONTACT_DETAILS, null,
                        values);

            }

        } catch (Exception ex) {
            Log.d("Database Exception while Insert Store Data ",
                    ex.toString());
        }

    }

    public void InsertJobLevel(Joblevelsgettersetter data) {

        db.delete(CommonString.TABLE_JOB_LEVELS, null, null);
        ContentValues values = new ContentValues();

        try {

            for (int i = 0; i < data.getjoblevel_cd().size(); i++) {

                values.put(CommonString.key_JOBLEVEL_ID, data.getjoblevel_cd()
                        .get(i));
                values.put(CommonString.Key_JOB_LEVEL, data.getJoblevel()
                        .get(i));

                db.insert(CommonString.TABLE_JOB_LEVELS, null, values);

            }

        } catch (Exception ex) {
            Log.d("Database Exception while Insert Store Data ",
                    ex.toString());
        }

    }

    public void InsertDesignation(Designationgettersetter data) {

        db.delete(CommonString.TABLE_DESIGNATION, null, null);
        ContentValues values = new ContentValues();

        try {

            for (int i = 0; i < data.getdesignation_cd().size(); i++) {

                values.put(CommonString.Key_DESIGNATION_CD, data
                        .getdesignation_cd().get(i));
                values.put(CommonString.Key_DESIGNATION, data.getDesignation()
                        .get(i));

                db.insert(CommonString.TABLE_DESIGNATION, null, values);

            }

        } catch (Exception ex) {
            Log.d("Database Exception while Insert Store Data ",
                    ex.toString());
        }

    }

    public void InsertPosmDetails(posmgettersetter data) {

        db.delete(CommonString.TABLE_POSM, null, null);
        ContentValues values = new ContentValues();

        try {

            for (int i = 0; i < data.getposm_cd().size(); i++) {

                values.put(CommonString.Key_POSM_CD, data.getposm_cd().get(i));
                values.put(CommonString.Key_POSM, data.getposm().get(i));

                db.insert(CommonString.TABLE_POSM, null, values);

            }

        } catch (Exception ex) {
            Log.d("Database Exception while Insert Store Data ",
                    ex.toString());
        }

    }

    public void InsertSalesDetails(SalesBeanGetterSetter data) {

        db.delete(CommonString.TABLE_SALES, null, null);
        ContentValues values = new ContentValues();

        try {

            for (int i = 0; i < data.getProduct_cd().size(); i++) {

                values.put(CommonString.Key_BRAND_ID, data.getBrandId().get(i));
                values.put(CommonString.Key_BRAND, data.getBrand().get(i));
                values.put(CommonString.Key_PRODUCT_CD, data.getProduct_cd()
                        .get(i));
                values.put(CommonString.Key_PRODUCT_NAME, data.getProduct()
                        .get(i));
                values.put(CommonString.Key_ISCOMPETITIOR, data
                        .getIscompetitor().get(i));
                values.put(CommonString.Key_PRODUCT_TYPE, data.getType().get(i));

                db.insert(CommonString.TABLE_SALES, null, values);

            }

        } catch (Exception ex) {
            Log.d("Database Exception while Insert Store Data ",
                    ex.toString());
        }

    }

    public void InsertSalesDetailsInserted(Long mid, String storeid,
                                           ArrayList<salesBean> data) {

        db.delete(CommonString.TABLE_SALES_INSERTED, CommonString.KEY_MID
                + "='" + mid + "'", null);

        String Mid = String.valueOf(mid);

        ContentValues values = new ContentValues();

        try {

            for (int i = 0; i < data.size(); i++) {

                values.put(CommonString.Key_BRAND, data.get(i).getBrand());

                values.put(CommonString.Key_BRAND_ID, data.get(i).getBrandID());

                values.put(CommonString.Key_PRODUCT_CD, "");
                values.put(CommonString.Key_PRODUCT_NAME, "");
                values.put(CommonString.Key_ISCOMPETITIOR, "");
                values.put(CommonString.Key_PRODUCT_TYPE, "");
                values.put(CommonString.Key_Mid, Mid);
                values.put(CommonString.Key_SALES_OUTLOOK, data.get(i)
                        .getSales_Outlook());
                values.put(CommonString.key_LSTMONTH_SALE, data.get(i)
                        .getLastmonth_sale());
                values.put(CommonString.Key_TILLDATE_SALE, data.get(i)
                        .getTilldate_sale());

                db.insert(CommonString.TABLE_SALES_INSERTED, null, values);

            }

        } catch (Exception ex) {
            Log.d("Database Exception while Insert Store Data ",
                    ex.toString());
        }

    }

    public void InsertupdatedTamData(String storeid,
                                     ArrayList<updateTamBean> data) {

        db.delete(CommonString.TABLE_TAM_INSERTED, CommonString.key_STORE_CD
                + "='" + storeid + "'", null);

        // String Mid = String.valueOf(mid);

        ContentValues values = new ContentValues();

        try {

            String tam = "";
            for (int i = 0; i < data.size(); i++) {

                if (data.get(i).getUpdateTam().equals("")) {
                    tam = data.get(i).getCurrentTam();
                } else {
                    tam = data.get(i).getUpdateTam();
                }

                values.put(CommonString.KEY_BRAND_NAME, data.get(i)
                        .getBrandName());
                values.put(CommonString.Key_BRAND_ID, data.get(i).getBrandid());
                values.put(CommonString.key_STORE_CD, storeid);
                values.put(CommonString.KEY_STORE_TAM, tam);

                db.insert(CommonString.TABLE_TAM_INSERTED, null, values);

            }

        } catch (Exception ex) {
            Log.d("Database Exception while Insert Store Data ",
                    ex.toString());
        }

    }

    public void InsertPosmDetailsInserted(Long mid, String storeid,
                                          ArrayList<Posmbean> data) {

        db.delete(CommonString.TABLE_POSM_INSERTED, CommonString.KEY_MID + "='"
                + mid + "'", null);

        String Mid = String.valueOf(mid);
        ContentValues values = new ContentValues();
        try {

            for (int i = 0; i < data.size(); i++) {

                values.put(CommonString.Key_POSM_CD, data.get(i).getPOSM_CD());
                values.put(CommonString.Key_POSM, data.get(i).getPOSM());
                values.put(CommonString.Key_Noof_Units, data.get(i)
                        .getNoof_Units());
                values.put(CommonString.KEY_IMAGE, data.get(i).getImage());
                values.put(CommonString.Key_Mid, Mid);
                values.put(CommonString.Key_Comments, data.get(i).getComments());
                db.insert(CommonString.TABLE_POSM_INSERTED, null, values);
            }

        } catch (Exception ex) {
            Log.d("Database Exception while Insert Store Data ",
                    ex.toString());
        }

    }


    public void InsertMidDayStockRecievedData(Long mid, String storeid, ArrayList<MidDayRecievedStockBean> data) {
//		db.delete(CommonString.TABLE_MIDDAYSTOCK_RECIEVED_DATA, CommonString.KEY_MID + "='"
//				+ mid + "'", null);

        ContentValues values = new ContentValues();
        try {

            for (int i = 0; i < data.size(); i++) {

                values.put(CommonString.KEY_BRAND_CD, data.get(i).getBRAND_CD());
                values.put(CommonString.Key_BRAND, data.get(i).getBRAND());
                values.put(CommonString.KEY_SKU_CD, data.get(i).getSKU_CD());
                values.put(CommonString.KEY_STOCK, data.get(i).getStock());
//				values.put(CommonString.Key_Mid, Mid);
//				values.put(CommonString.Key_Comments, data.get(i).getComments());
                db.insert(CommonString.TABLE_MIDDAYSTOCK_RECIEVED_DATA, null, values);
            }

        } catch (Exception ex) {
            Log.d("Database Exception while Insert Store Data ",
                    ex.toString());
        }
    }


    public void InsertCheecklistData(long mid, String storeid,
                                     HashMap<MidDayRecievedStockBean, List<MidDayRecievedStockBean>> data,
                                     List<MidDayRecievedStockBean> save_listDataHeader) {

        ContentValues values = new ContentValues();
        try {

            db.beginTransaction();

            for (int i = 0; i < save_listDataHeader.size(); i++) {

                values.put(CommonString.MID, mid);
                values.put(CommonString.KEY_STORE_ID, storeid);

                values.put(CommonString.Key_BRAND, save_listDataHeader.get(i)
                        .getBRAND());
                values.put(CommonString.KEY_BRAND_CD, save_listDataHeader
                        .get(i).getBRAND_CD());

//				long l = db.insert(CommonString.TABLE_INSERT_CHECKLIST_DATA,
//						null, values);
//
//				for (int j = 0; j < data.get(save_listDataHeader.get(i)).size(); j++) {
//
//					values1.put(CommonString.COMMONID, l);
//
//					values1.put(CommonString.KEY_AREA,
//							data.get(save_listDataHeader.get(i)).get(j)
//									.getArea());
//					values1.put(CommonString.KEY_STORE_ID, storeid);
//					values1.put(CommonString.Key_AREA_ID,
//							data.get(save_listDataHeader.get(i)).get(j)
//									.getAreaid());
//					values1.put(CommonString.Key_CHECKLIST,
//							data.get(save_listDataHeader.get(i)).get(j)
//									.getChklist());
//					values1.put(CommonString.Key_CHECKLIST_ID,
//							data.get(save_listDataHeader.get(i)).get(j)
//									.getChklistid());
//					values1.put(CommonString.KEY_IMAGE,
//							data.get(save_listDataHeader.get(i)).get(j)
//									.getImage());
//					values1.put(CommonString.VALUE,
//							data.get(save_listDataHeader.get(i)).get(j)
//									.getValue().replaceAll("[&^<>{}'$]", " "));
//					values1.put(CommonString.PRESENCE,
//							data.get(save_listDataHeader.get(i)).get(j)
//									.getValidity());
//
//					db.insert(CommonString.TABLE_INSERT_SUBCHECKLIST_DATA,
//							null, values1);
//
//				}
            }
            db.setTransactionSuccessful();
            db.endTransaction();
        } catch (Exception ex) {
            Log.d("Database Exception while Insert Posm Master Data ",
                    ex.toString());
        }

    }

    // store_id, mid
    public void InsertCallsTrackerData(Long mid, String storeid, String totalCals, String productiveCalls, String posSale) {

        ContentValues values = new ContentValues();
        try {
            values.put(CommonString.KEY_STORE_CD, storeid);
            values.put(CommonString.Key_Mid, mid);
            values.put(CommonString.KEY_TOTAL_CALLS3to6, totalCals);
            values.put(CommonString.KEY_PRODUCTIVE_CALLS3to6, productiveCalls);
            values.put(CommonString.KEY_POS_SALE, posSale);

            db.insert(CommonString.TABLE_CALLS_TRACKER_DATA, null, values);

        } catch (Exception ex) {
            Log.d("Database Exception while Insert Call Tracker Data ",
                    ex.toString());
        }
    }


    public void insertLunchtimeData(long mid, String username, String fromTime,
                                    String toTime, String store_cd) {


        db.delete(CommonString.TABLE_LUNCH_TIME, CommonString.key_STORE_CD + "='"
                + store_cd + "'", null);
        ContentValues values = new ContentValues();
        try {
            values.put(CommonString.KEY_USER_ID, username);
            values.put(CommonString.Key_Mid, mid);
            values.put(CommonString.KEY_FROM_TIME, fromTime);
            values.put(CommonString.KEY_TO_TIME, toTime);
            values.put(CommonString.key_STORE_CD, store_cd);

            db.insert(CommonString.TABLE_LUNCH_TIME, null, values);

        } catch (Exception ex) {
            Log.d("Database Exception while Insert Call Tracker Data ",
                    ex.toString());
        }
    }

    public void InsertCallsBetween12To3Data(Long mid, String totalCalls, String productiveCals, String punchTime, String storeid) {

        ContentValues cv = new ContentValues();
        try {

            cv.put(CommonString.key_STORE_CD, storeid);
            cv.put(CommonString.KEY_TOTAL_CALLS12to3, totalCalls);
            cv.put(CommonString.KEY_PRODUCTIVE_CALLS12to3, productiveCals);
            cv.put(CommonString.KEY_PUNCH_TIME, punchTime);
            cv.put(CommonString.Key_Mid, mid);

            db.insert(CommonString.TABLE_CALLS_TRACKER_DATA_12To3, null, cv);

        } catch (Exception e) {
            Log.d("Database Exception while insert calls between 12 to 3 tracker data", e.toString());
        }

    }

    public void InsertClosingStock(Long mid, String storeid, ArrayList<MidDayRecievedStockBean> data) {

        ContentValues values = new ContentValues();
        try {

            for (int i = 0; i < data.size(); i++) {

                values.put(CommonString.KEY_BRAND_CD, data.get(i).getBRAND_CD());
                values.put(CommonString.Key_BRAND, data.get(i).getBRAND());
                values.put(CommonString.KEY_SKU_CD, data.get(i).getSKU_CD());
                values.put(CommonString.KEY_STOCK, data.get(i).getStock());
//				values.put(CommonString.Key_Mid, Mid);
//				values.put(CommonString.Key_Comments, data.get(i).getComments());
                db.insert(CommonString.TABLE_CLOSING_STOCK_DATA, null, values);
            }

        } catch (Exception ex) {
            Log.d("Database Exception while Insert Store Data ",
                    ex.toString());
        }
    }


    public void InsertOpeningStockData(Long mid, String storeid, ArrayList<OpeningStockBean> data) {
        db.delete(CommonString.TABLE_OENGINGSTOCK_DATA, CommonString.KEY_MID + "='"
                + mid + "'", null);
        ContentValues values = new ContentValues();

        try {
            for (int i = 0; i < data.size(); i++) {
                values.put(CommonString.KEY_SKU, data.get(i).getSKU());
                values.put(CommonString.KEY_SKU_CD, data.get(i).getSKU_CD());
                values.put(CommonString.Key_BRAND, data.get(i).getBRAND());
                values.put(CommonString.KEY_BRAND_CD, data.get(i).getBRAND_CD());
                values.put(CommonString.KEY_SHELF, data.get(i).getShelf());
                values.put(CommonString.KEY_FACING, data.get(i).getShelf());
                values.put(CommonString.KEY_OPENINGSTOCK, data.get(i).getShelf());
                values.put(CommonString.KEY_PRICING, data.get(i).getShelf());
                values.put(CommonString.KEY_EXPIRING_STOCK, data.get(i).getShelf());

                db.insert(CommonString.TABLE_OENGINGSTOCK_DATA, null, values);

            }

        } catch (Exception ex) {
            Log.d("Database Exception while Insert Opening Stock Data ",
                    ex.toString());
        }
//		String Mid = String.valueOf(mid);
//		ContentValues values = new ContentValues();

    }

    public void InsertCompetionDetailsInserted(Long mid, String storeid,
                                               ArrayList<salesBean> data) {

        db.delete(CommonString.TABLE_COMPETITION_INSERTED, CommonString.KEY_MID
                + "='" + mid + "'", null);
        String Mid = String.valueOf(mid);

        ContentValues values = new ContentValues();

        try {

            for (int i = 0; i < data.size(); i++) {

                values.put(CommonString.Key_BRAND, data.get(i).getBrand());
                values.put(CommonString.Key_PRODUCT_CD, data.get(i)
                        .getProduct_cd());
                values.put(CommonString.Key_PRODUCT_NAME, data.get(i)
                        .getProduct());
                values.put(CommonString.Key_ISCOMPETITIOR, data.get(i)
                        .getIscompetitor());
                values.put(CommonString.Key_PRODUCT_TYPE, data.get(i).getType());
                values.put(CommonString.Key_Mid, Mid);

                values.put(CommonString.Key_PRICE, data.get(i).getPrice());
                values.put(CommonString.key_LSTMONTH_SALE, data.get(i)
                        .getLastmonth_sale());
                values.put(CommonString.Key_TILLDATE_SALE, data.get(i)
                        .getTilldate_sale());
                values.put(CommonString.Key_SALES_OUTLOOK, data.get(i)
                        .getSales_Outlook());

                db.insert(CommonString.TABLE_COMPETITION_INSERTED, null, values);

            }

        } catch (Exception ex) {
            Log.d("Database Exception while Insert Store Data ",
                    ex.toString());
        }

    }

    // jcp
    public void InsertStoreData(JCPGetterSetter data, String date) {
        db.delete(CommonString.TABLE_STORE_DETAIL, null, null);
        ContentValues values = new ContentValues();
        try {
            for (int i = 0; i < data.getStoreid().size(); i++) {
                values.put(CommonString.KEY_STORE_ID, data.getStoreid().get(i));
                values.put(CommonString.KEY_STORE_NAME, data.getStorename().get(i));
                values.put(CommonString.KEY_ADDRESS, data.getStoreaddres().get(i));
                values.put(CommonString.KEY_VISIT_DATE, data.getVisitdate().get(i));
                values.put(CommonString.KEY_STATUS, data.getStatus().get(i));
                values.put(CommonString.KEY_LATITUDE, data.getStorelatitude().get(i));
                values.put(CommonString.KEY_LONGITUDE, data.getStorelongitude().get(i));
                values.put(CommonString.KEY_CATEGORY_CODE, data.getCATEGORY_ID().get(i));
                values.put(CommonString.KEY_CHECKOUT_STATUS, data.getCheckout_status().get(i));
                values.put(CommonString.KEY_RIGHTS, data.getRIGHTS().get(i));
                db.insert(CommonString.TABLE_STORE_DETAIL, null, values);

            }

        } catch (Exception ex) {
            Log.d("Database Exception while Insert Store Data ",
                    ex.toString());
        }

    }

    public void InsertTamData(TamgetterSetter data) {

        db.delete(CommonString.TABLE_TAM_DETAIL, null, null);
        ContentValues values = new ContentValues();

        try {

            for (int i = 0; i < data.getStore_cd().size(); i++) {


                values.put(CommonString.key_STORE_CD, data.getStore_cd().get(i));
                values.put(CommonString.KEY_STORE_TAM, data.getTam().get(i));
                values.put(CommonString.Key_BRAND_ID, data.getBrand_cd().get(i));
                values.put(CommonString.KEY_BRAND_NAME, data.getBrandName().get(i));

                db.insert(CommonString.TABLE_TAM_DETAIL, null, values);

            }

        } catch (Exception ex) {
            Log.d("Database Exception while Insert Store Data ",
                    ex.toString());
        }

    }


    public void InsertPerformanceData(PerformanceMSetterGetter data) {

        db.delete(CommonString.TABLE_PERFORMANCE_DATA, null, null);
        ContentValues values = new ContentValues();

        try {

            for (int i = 0; i < data.getMonthly_target().size(); i++) {

                values.put(CommonString.KEY_MONTHLY_TARGET, data.getMonthly_target().get(i));
                values.put(CommonString.KEY_DAILY_TARGET, data.getDaily_target().get(i));
                values.put(CommonString.KEY_ACHIEVED, data.getAchieved().get(i));
                values.put(CommonString.KEY_PENDING, data.getPending().get(i));

                db.insert(CommonString.TABLE_PERFORMANCE_DATA, null, values);

            }

        } catch (Exception ex) {
            Log.d("Database Exception while Insert Store Data ",
                    ex.toString());
        }

    }

    public void InsertPlanogramData(planogramSetterGetter data) {

        db.delete(CommonString.TABLE_PLANOGRAM, null, null);
        ContentValues values = new ContentValues();

        try {

            for (int i = 0; i < data.getBRAND_CD().size(); i++) {

                values.put(CommonString.key_STORE_CD, data.getSTORE_CD().get(i));
                values.put(CommonString.Key_BRAND, data.getBRAND_CD().get(i));
                values.put(CommonString.Key_Image, data.getImg_URL().get(i));

                db.insert(CommonString.TABLE_PLANOGRAM, null, values);

            }

        } catch (Exception ex) {
            Log.d("Database Exception while Insert Planogram Data ",
                    ex.toString());
        }

    }


    public void InsertCategoryData(SKUPostgetterSetter categoryData) {


        db.delete(CommonString.TABLE_CATEGORY_MASTER, null, null);
        ContentValues values = new ContentValues();

        try {

            for (int i = 0; i < categoryData.getCATEGORY_CD().size(); i++) {

                values.put(CommonString.KEY_CATEGORY_CD, categoryData.getCATEGORY_CD().get(i));
                values.put(CommonString.KEY_CATEGORY, categoryData.getCATEGORY().get(i));

                db.insert(CommonString.TABLE_CATEGORY_MASTER, null, values);

            }

        } catch (Exception ex) {
            Log.d("Database Exception while Insert Planogram Data ",
                    ex.toString());
        }

    }


    public void InsertNonAssetReasonData(SKUPostgetterSetter nonAssetReasondata) {


        db.delete(CommonString.TABLE_NON_ASSET_REASON_MASTER, null, null);
        ContentValues values = new ContentValues();

        try {

            for (int i = 0; i < nonAssetReasondata.getAREASON_ID().size(); i++) {

                values.put(CommonString.KEY_REASON_ID, nonAssetReasondata.getAREASON_ID().get(i));
                values.put(CommonString.KEY_REASON, nonAssetReasondata.getAREASON().get(i));

                db.insert(CommonString.TABLE_NON_ASSET_REASON_MASTER, null, values);

            }

        } catch (Exception ex) {
            Log.d("Database Exception while Insert Planogram Data ",
                    ex.toString());
        }

    }


    public void InsertNonPromotionReasonData(SKUPostgetterSetter nonPromotionReasondata) {


        db.delete(CommonString.TABLE_NON_PROMOTION_REASON_MASTER, null, null);
        ContentValues values = new ContentValues();

        try {

            for (int i = 0; i < nonPromotionReasondata.getPREASON_ID().size(); i++) {

                values.put(CommonString.KEY_REASON_ID, nonPromotionReasondata.getPREASON_ID().get(i));
                values.put(CommonString.KEY_REASON, nonPromotionReasondata.getPREASON().get(i));

                db.insert(CommonString.TABLE_NON_PROMOTION_REASON_MASTER, null, values);

            }

        } catch (Exception ex) {
            Log.d("Database Exception while Insert Planogram Data ",
                    ex.toString());
        }

    }


    public void InsertPOSMData(AssetPostgetterSetter data) {


        db.delete(CommonString.TABLE_POSM_MASTER, null, null);
        ContentValues values = new ContentValues();

        try {

            for (int i = 0; i < data.getPOSM_CD().size(); i++) {

                values.put(CommonString.KEY_POSM_ID, data.getPOSM_CD().get(i));
                values.put(CommonString.KEY_POSM, data.getPOSM().get(i));

                db.insert(CommonString.TABLE_POSM_MASTER, null, values);

            }

        } catch (Exception ex) {
            Log.d("Database Exception while Insert Planogram Data ",
                    ex.toString());
        }

    }


    public void InsertConsolidatePerformanceData(ConsolidatePerGetterSetter data) {

        db.delete(CommonString.TABLE_CONSOLIDATE, null, null);
        ContentValues values = new ContentValues();

        try {

            for (int i = 0; i < data.getSALE().size(); i++) {


//				"[{\"TAM\":\"65000.00\",\"SALE\":\"5400.00\",\"SHARE\":\"8\",\"TERRITORY\":\"Red\"}]"


                values.put(CommonString.KEY_TAM, data.getTAM().get(i));
                values.put(CommonString.KEY_SALE, data.getSALE().get(i));
                values.put(CommonString.KEY_SHARE, data.getSHARE().get(i));
                values.put(CommonString.KEY_TERRITORY, data.getTERRITORY()
                        .get(i));

                db.insert(CommonString.TABLE_CONSOLIDATE, null, values);

            }

        } catch (Exception ex) {
            Log.d("Database Exception while Insert Store Data ",
                    ex.toString());
        }

    }


    public void deleteStoreMidData(int mid, String store_id) {

        try {
            db.delete(CommonString.TABLE_INSERT_HEADER_PROMOTION_DATA,
                    CommonString.KEY_STORE_CD + "='" + store_id + "'", null);

            db.delete(CommonString.TABLE_INSERT_PROMOTION_DATA,
                    CommonString.KEY_STORE_CD + "='" + store_id + "'", null);
            db.delete(CommonString.TABLE_COVERAGE_DATA, CommonString.KEY_ID
                    + " = '" + mid + "'", null);

            db.delete(CommonString.TABLE_COVERAGE_DATA, CommonString.KEY_ID
                    + " = '" + mid + "'", null);

            db.delete(CommonString.TABLE_CALLS_TRACKER_DATA, CommonString.MID
                    + " = '" + mid + "'", null);

            db.delete(CommonString.TABLE_INSERT_STOCKRECIEVED_DATA, CommonString.MID
                    + " = '" + mid + "'", null);
            db.delete(CommonString.TABLE_INSERT_CLOSINGSTOCK_DATA, CommonString.MID
                    + " = '" + mid + "'", null);


        } catch (Exception e) {

            System.out.println(e.toString());

        }

    }

    public StoreBean getStoreStatus(String id) {

        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");

        StoreBean sb = new StoreBean();

        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT  * from " + CommonString.TABLE_STORE_DETAIL + "  WHERE STORE_ID = '" + id + "'", null);
            if (dbcursor != null) {
                int numrows = dbcursor.getCount();
                dbcursor.moveToFirst();
                for (int i = 0; i < numrows; i++) {
                    sb.setStoreid(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_STORE_ID)));
                    sb.setStorename((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_STORE_NAME))));
                    sb.setStoreaddress(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_ADDRESS)));
                    sb.setVisitdate((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_VISIT_DATE))));
                    sb.setLatitude((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_LATITUDE))));
                    sb.setLongitude(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_LONGITUDE)));
                    sb.setStatus(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_STATUS)));sb.setCheckout_status(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_CHECKOUT_STATUS)));
                    sb.setRights(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_RIGHTS)));


                    dbcursor.moveToNext();

                }

                dbcursor.close();

            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return sb;

    }


    public String getStoreRights(String id) {

        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");
        String Rights = null;
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT  * from " + CommonString.TABLE_STORE_DETAIL + "  WHERE STORE_ID = '" + id + "'", null);

            if (dbcursor != null) {
                int numrows = dbcursor.getCount();


                dbcursor.moveToFirst();
                for (int i = 0; i < numrows; i++) {
                    Rights = (dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_RIGHTS)));
                    dbcursor.moveToNext();

                }

                dbcursor.close();

            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!", e.toString());
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return Rights;

    }


    public String getStoreName(String storeid) {
        String storename = "";
        Cursor cursor = db.rawQuery("select " + CommonString.KEY_STORE_NAME
                + " from " + CommonString.TABLE_STORE_DETAIL + " where "
                + CommonString.KEY_STORE_ID + "='" + storeid + "'", null);

        if (cursor != null) {
            cursor.moveToFirst();
            storename = cursor.getString(cursor
                    .getColumnIndexOrThrow(CommonString.KEY_STORE_NAME));
        }

        cursor.close();
        return storename;
    }

    public void updateCoverageStatus(int mid, String status) {
        try {
            ContentValues values = new ContentValues();
            values.put(CommonString.KEY_STATUS, status);
            db.update(CommonString.TABLE_COVERAGE_DATA, values, CommonString.KEY_ID + "=" + mid, null);
        } catch (Exception e) {

        }
    }

    public void updateContactId(int mid, String contactid, String id) {

        try {
            ContentValues values = new ContentValues();
            values.put(CommonString.KEY_CONTACT_CD, contactid);

            db.update(CommonString.TABLE_ADD_TRAINING, values,
                    CommonString.KEY_ID + "='" + mid + "' AND "
                            + CommonString.KEY_CONTACT_CD + "='" + id
                            + "(new)'", null);

        } catch (Exception e) {
            e.toString();

        }
    }

    public void updateContactIdStoreContact(int mid, String contactid, String id) {

        try {
            ContentValues values = new ContentValues();
            values.put(CommonString.KEY_CONTACT_CD, contactid);
            values.put(CommonString.KEY_FLAG, "old");

            db.update(CommonString.TABLE_STOREKEY_CONTACT_DETAILS, values,
                    CommonString.KEY_CONTACT_CD + "='" + id + "(new)'", null);
        } catch (Exception e) {
            e.toString();

        }
    }

    public void updateStoreStatus1(String storeid, String status) {

        try {
            ContentValues values = new ContentValues();
            values.put(CommonString.KEY_STATUS, status);

            db.update(CommonString.TABLE_STORE_DETAIL, values,
                    CommonString.KEY_STORE_ID + "='" + storeid + "'", null);
        } catch (Exception e) {

        }
    }

    // delete data
    public void updateStoreStatus(String storeid, String visitdate) {

        try {
            ContentValues values = new ContentValues();
            values.put(CommonString.KEY_STATUS, "U");

            db.update(CommonString.TABLE_STORE_DETAIL, values,
                    CommonString.KEY_STORE_ID + "='" + storeid + "' AND "
                            + CommonString.KEY_VISIT_DATE + "='" + visitdate
                            + "'", null);
        } catch (Exception e) {

        }
    }

    public ArrayList<Storenamebean> getGeoTagStore() {

        ArrayList<Storenamebean> list = new ArrayList<Storenamebean>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT  * from "
                    + CommonString.TABLE_GEOTAG_DETAIL, null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    Storenamebean pb = new Storenamebean();
                    pb.setStoreid(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_STORE_ID)));
                    pb.setStorename(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_STORE_NAME)));

                    pb.setStatus(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_STATUS)));

                    pb.setCityid(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_CITY)));
                    pb.setStoretypeid(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_STORE_TYPE)));

                    pb.setAddress(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_ADDRESS)));

                    pb.setLatitude(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_LATITUDE)));
                    pb.setLongitude(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_LONGITUDE)));

                    list.add(pb);
                    dbcursor.moveToNext();

                }
                dbcursor.close();

            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());

        }
        return list;
    }

    public ArrayList<updateTamBean> getTamData(String storeid) {

        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");
        ArrayList<updateTamBean> list = new ArrayList<updateTamBean>();
        Cursor dbcursor = null;

        try {

			/*
             * dbcursor = db.rawQuery("SELECT * from " +
			 * CommonString.TABLE_SALES + " where " +
			 * CommonString.Key_ISCOMPETITIOR + " = 'False' AND "+
			 * CommonString.key_STORE_CD + " = "+ storeid, null);
			 */
            dbcursor = db.rawQuery("SELECT * from "
                    + CommonString.TABLE_TAM_DETAIL + " where "
                    + CommonString.key_STORE_CD + " = " + storeid, null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    updateTamBean sb = new updateTamBean();

                    sb.setBrandid(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.Key_BRAND_ID)));
                    sb.setBrandName(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_BRAND_NAME)));
                    sb.setStoreid(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.key_STORE_CD)));
                    sb.setUpdateTam("");

                    sb.setCurrentTam(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_STORE_TAM)));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return list;

    }

    public void InsertGeoTagStoreData(GeoTaggettersetter data) {

        db.delete(CommonString.TABLE_GEOTAG_DETAIL, null, null);
        ContentValues values = new ContentValues();

        try {

            for (int i = 0; i < data.getStore_cd().size(); i++) {

                values.put(CommonString.KEY_STORE_ID, data.getStore_cd().get(i));
                values.put(CommonString.KEY_STORE_NAME, data.getStore_name()
                        .get(i));
                values.put(CommonString.KEY_ADDRESS, data.getAddres().get(i));
                values.put(CommonString.KEY_CITY, data.getCity().get(i));
                values.put(CommonString.KEY_STATUS, data.getStoretype_status()
                        .get(i));
                values.put(CommonString.KEY_LATITUDE, data.getLatitude().get(i));
                values.put(CommonString.KEY_LONGITUDE,
                        data.getLongitude().get(i));
                values.put(CommonString.KEY_STORE_TYPE, data.getStoretype()
                        .get(i));

                db.insert(CommonString.TABLE_GEOTAG_DETAIL, null, values);

            }

        } catch (Exception ex) {
            Log.d("Database Exception while Insert Store Data ",
                    ex.toString());
        }

    }

    public void InsertStoregeotagging(String storeid, double lat,
                                      double longitude, String path, String path_inside,
                                      String path_inside1, String status) {

        db.delete(CommonString.TABLE_INSERT_GEO_TAG, CommonString.Key_Store_id
                + " ='" + storeid + "'", null);

        ContentValues values = new ContentValues();

        try {

            values.put(CommonString.KEY_STORE_ID, storeid);
            values.put(CommonString.KEY_LATITUDE, Double.toString(lat));
            values.put(CommonString.KEY_LONGITUDE, Double.toString(longitude));
            values.put(CommonString.KEY_IMAGE_PATH1, path);
            values.put(CommonString.KEY_IMAGE_PATH2, path_inside);
            values.put(CommonString.KEY_IMAGE_PATH3, path_inside1);
            values.put(CommonString.KEY_STATUS, status);

            db.insert(CommonString.TABLE_INSERT_GEO_TAG, null, values);

        } catch (Exception ex) {
            Log.d("Database Exception while Insert Closes Data ",
                    ex.toString());
        }

    }

    public ArrayList<GeotaggingBeans> getGeotaggingStatusData() {

        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");

        ArrayList<GeotaggingBeans> geodata = new ArrayList<GeotaggingBeans>();

        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT  * from "
                    + CommonString.TABLE_INSERT_GEO_TAG, null);

            if (dbcursor != null) {
                int numrows = dbcursor.getCount();

                dbcursor.moveToFirst();
                for (int i = 1; i <= numrows; ++i) {

                    GeotaggingBeans data = new GeotaggingBeans();
                    data.setStoreid(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_STORE_ID)));
                    data.setLatitude(Double.parseDouble(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_LATITUDE))));
                    data.setLongitude(Double.parseDouble(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_LONGITUDE))));
                    data.setUrl1(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE_PATH1)));
                    data.setUrl2(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE_PATH2)));
                    data.setUrl3(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE_PATH3)));
                    data.setStatus(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_STATUS)));
                    geodata.add(data);
                    dbcursor.moveToNext();

                }

                dbcursor.close();

            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return geodata;

    }

    public void updateGeoTagStatus(String id, String status, double lat,
                                   double longtitude) {

        ContentValues values = new ContentValues();

        try {

            values.put(CommonString.KEY_STATUS, status);
            values.put(CommonString.KEY_LATITUDE, Double.toString(lat));
            values.put(CommonString.KEY_LONGITUDE, Double.toString(longtitude));

            db.update(CommonString.TABLE_GEOTAG_DETAIL, values,
                    CommonString.KEY_STORE_ID + "='" + id + "'", null);

        } catch (Exception ex) {
            Log.d("Database Exception while Insert Earned Toggle Data ",
                    ex.toString());
        }

    }

    public void updateGeoTagInsertedStatus(String id, String img1, String img2,
                                           String img3, String status) {

        ContentValues values = new ContentValues();

        try {

            values.put(CommonString.KEY_STATUS, status);
            values.put(CommonString.KEY_IMAGE_PATH1, img1);
            values.put(CommonString.KEY_IMAGE_PATH2, img2);
            values.put(CommonString.KEY_IMAGE_PATH3, img3);

            db.update(CommonString.TABLE_INSERT_GEO_TAG, values,
                    CommonString.KEY_STORE_ID + "='" + id + "'", null);

        } catch (Exception ex) {
            Log.d("Database Exception while Insert Earned Toggle Data ",
                    ex.toString());
        }

    }

    public ArrayList<StoreBean> getStoreData(String date) {
        Log.d("FetchingStoredata--------------->Start<------------", "------------------");
        ArrayList<StoreBean> list = new ArrayList<StoreBean>();
        Cursor dbcursor = null;
        try {
            dbcursor = db.rawQuery("SELECT  * from " + CommonString.TABLE_STORE_DETAIL + " where " + CommonString.KEY_VISIT_DATE + "='" + date + "'", null);
            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    StoreBean sb = new StoreBean();
                    sb.setStoreid(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_STORE_ID)));
                    sb.setStorename((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_STORE_NAME))));
                    sb.setStoreaddress(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_ADDRESS)));
                    sb.setLatitude((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_LATITUDE))));
                    sb.setLongitude(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_LONGITUDE)));
                    sb.setStatus(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_STATUS)));
                    sb.setCheckout_status(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_CHECKOUT_STATUS)));
                    sb.setCategory(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_CATEGORY_CODE)));
                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return list;

    }

    public ArrayList<salesBean> getSalesData() {

        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");
        ArrayList<salesBean> list = new ArrayList<salesBean>();
        Cursor dbcursor = null;
        try {

			/*
             * dbcursor = db.rawQuery("SELECT * from " +
			 * CommonString.TABLE_SALES + " where " +
			 * CommonString.Key_ISCOMPETITIOR + " = 'False' AND "+
			 * CommonString.key_STORE_CD + " = "+ storeid, null);
			 */
            dbcursor = db.rawQuery("SELECT DISTINCT BRAND,BRAND_ID FROM "
                    + CommonString.TABLE_SALES + " where "
                    + CommonString.Key_ISCOMPETITIOR + " = 'False'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {

                    // need to make changes below as m using distinct one column

                    salesBean sb = new salesBean();

                    sb.setBrand(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.Key_BRAND)));

                    sb.setBrandID(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.Key_BRAND_ID)));

					/*
                     * sb.setProduct(dbcursor.getString(dbcursor
					 * .getColumnIndexOrThrow(CommonString.Key_PRODUCT_NAME)));
					 */

					/*
					 * sb.setProduct_cd(dbcursor.getString(dbcursor
					 * .getColumnIndexOrThrow(CommonString.Key_PRODUCT_CD)));
					 */

					/*
					 * sb.setType((dbcursor.getString(dbcursor
					 * .getColumnIndexOrThrow(CommonString.Key_PRODUCT_TYPE))));
					 */
					/*
					 * sb.setIscompetitor(dbcursor.getString(dbcursor
					 * .getColumnIndexOrThrow(CommonString.Key_ISCOMPETITIOR)));
					 */

                    sb.setLastmonth_sale("");
                    sb.setTilldate_sale("");
                    sb.setSales_Outlook("");
                    list.add(sb);
                    dbcursor.moveToNext();

                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return list;

    }

    public ArrayList<Posmbean> getPosmData() {

        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");
        ArrayList<Posmbean> list = new ArrayList<Posmbean>();
        Cursor dbcursor = null;

        try {

			/*
			 * dbcursor = db.rawQuery("SELECT * from " +
			 * CommonString.TABLE_SALES + " where " +
			 * CommonString.Key_ISCOMPETITIOR + " = 'False' AND "+
			 * CommonString.key_STORE_CD + " = "+ storeid, null);
			 */
            dbcursor = db.rawQuery("SELECT  * from " + CommonString.TABLE_POSM,
                    null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    Posmbean Posmb = new Posmbean();
                    Posmb.setPOSM_CD(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.Key_POSM_CD)));

                    Posmb.setPOSM(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.Key_POSM)));

                    Posmb.setNoof_Units("");
                    Posmb.setImage("");
                    Posmb.setComments("");

                    list.add(Posmb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return list;

    }

    public ArrayList<PerformanceBean> getPerformanceStore(String storeid) {

        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");
        ArrayList<PerformanceBean> list = new ArrayList<PerformanceBean>();
        Cursor dbcursor = null;

        try {

			/*
			 * dbcursor = db.rawQuery("SELECT * from " +
			 * CommonString.TABLE_SALES + " where " +
			 * CommonString.Key_ISCOMPETITIOR + " = 'False' AND "+
			 * CommonString.key_STORE_CD + " = "+ storeid, null);
			 */
			/*dbcursor = db.rawQuery("SELECT  * from " + CommonString.TABLE_PERFORMANCE,
					null);dd*/

            if (storeid == null) {

                dbcursor = db.rawQuery("SELECT * from "
                        + CommonString.TABLE_PERFORMANCE, null);
            } else

            {
                dbcursor = db.rawQuery("SELECT * from "
                        + CommonString.TABLE_PERFORMANCE + " where "
                        + CommonString.key_STORE_CD + " = " + storeid, null);
            }

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    PerformanceBean PermormanceStore = new PerformanceBean();


                    PermormanceStore.setCITY(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_CITY)));

                    PermormanceStore.setSALE(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_SALE)));

                    PermormanceStore.setSHARE(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_SHARE)));

                    PermormanceStore.setSTORE_CD(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.key_STORE_CD)));

                    PermormanceStore.setSTORENAME(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_STORE_NAME)));

                    PermormanceStore.setTAM(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_TAM)));

                    PermormanceStore.setTERRITORY(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_TERRITORY)));


                    list.add(PermormanceStore);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return list;

    }


    public ArrayList<Consolidatebean> getConsolidatePerformance() {

        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");
        ArrayList<Consolidatebean> list = new ArrayList<Consolidatebean>();
        Cursor dbcursor = null;

        try {

			/*
			 * dbcursor = db.rawQuery("SELECT * from " +
			 * CommonString.TABLE_SALES + " where " +
			 * CommonString.Key_ISCOMPETITIOR + " = 'False' AND "+
			 * CommonString.key_STORE_CD + " = "+ storeid, null);
			 */
			/*dbcursor = db.rawQuery("SELECT  * from " + CommonString.TABLE_PERFORMANCE,
					null);dd*/

            dbcursor = db.rawQuery("SELECT * from "
                    + CommonString.TABLE_CONSOLIDATE, null);


            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    Consolidatebean PermormanceStore = new Consolidatebean();


                    PermormanceStore.setTAM(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_TAM)));

                    PermormanceStore.setTERRIORITY(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_TERRITORY)));
                    PermormanceStore.setSALE(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_SALE)));

                    PermormanceStore.setSHARE(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_SHARE)));


                    list.add(PermormanceStore);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {

            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return list;

    }


    public ArrayList<salesBean> getSalesDataInserted(int mid) {

        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");
        ArrayList<salesBean> list = new ArrayList<salesBean>();
        Cursor dbcursor = null;

        try {

			/*
			 * dbcursor = db.rawQuery("SELECT * from " +
			 * CommonString.TABLE_SALES + " where " +
			 * CommonString.Key_ISCOMPETITIOR + " = 'False' AND "+
			 * CommonString.key_STORE_CD + " = "+ storeid, null);
			 */
            dbcursor = db.rawQuery("SELECT * from "
                    + CommonString.TABLE_SALES_INSERTED + " where "
                    + CommonString.Key_Mid + " = " + mid, null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    salesBean sb = new salesBean();

                    sb.setBrand(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.Key_BRAND)));
                    sb.setBrandID(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.Key_BRAND_ID)));

					/*
					 * sb.setProduct(dbcursor.getString(dbcursor
					 * .getColumnIndexOrThrow(CommonString.Key_PRODUCT_NAME)));
					 *
					 * sb.setProduct_cd(dbcursor.getString(dbcursor
					 * .getColumnIndexOrThrow(CommonString.Key_PRODUCT_CD)));
					 *
					 * sb.setType((dbcursor.getString(dbcursor
					 * .getColumnIndexOrThrow(CommonString.Key_PRODUCT_TYPE))));
					 * sb.setIscompetitor(dbcursor.getString(dbcursor
					 * .getColumnIndexOrThrow(CommonString.Key_ISCOMPETITIOR)));
					 */

                    sb.setLastmonth_sale(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.key_LSTMONTH_SALE)));
                    sb.setTilldate_sale(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.Key_TILLDATE_SALE)));
                    sb.setSales_Outlook(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.Key_SALES_OUTLOOK)));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return list;

    }

    public ArrayList<updateTamBean> getTamDataInserted(String storeid) {

        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");
        ArrayList<updateTamBean> list = new ArrayList<updateTamBean>();
        Cursor dbcursor = null;

        try {

			/*
			 * dbcursor = db.rawQuery("SELECT * from " +
			 * CommonString.TABLE_SALES + " where " +
			 * CommonString.Key_ISCOMPETITIOR + " = 'False' AND "+
			 * CommonString.key_STORE_CD + " = "+ storeid, null);
			 *
			 *
			 */

            if (storeid == null) {
                dbcursor = db.rawQuery("SELECT * from "
                        + CommonString.TABLE_TAM_INSERTED, null);
            } else {
                dbcursor = db.rawQuery("SELECT * from "
                        + CommonString.TABLE_TAM_INSERTED + " where "
                        + CommonString.key_STORE_CD + " = " + storeid, null);
            }

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    updateTamBean sb = new updateTamBean();

                    sb.setBrandid(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.Key_BRAND_ID)));
                    sb.setBrandName(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_BRAND_NAME)));
                    sb.setStoreid(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.key_STORE_CD)));
                    sb.setUpdateTam("");

                    sb.setCurrentTam(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_STORE_TAM)));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return list;

    }

    public ArrayList<Posmbean> getPosmDataInserted(int mid) {

        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");
        ArrayList<Posmbean> list = new ArrayList<Posmbean>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT * from "
                    + CommonString.TABLE_POSM_INSERTED + " where "
                    + CommonString.Key_Mid + " = " + mid, null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    Posmbean posm = new Posmbean();
                    posm.setPOSM_CD(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.Key_POSM_CD)));

                    posm.setPOSM(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.Key_POSM)));

                    posm.setNoof_Units(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.Key_Noof_Units)));

                    posm.setImage((dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.Key_Image))));
                    posm.setComments((dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.Key_Comments))));

                    list.add(posm);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return list;

    }


    public ArrayList<OpeningStockBean> getOpeningStockData(String Store_id) {
        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");

        ArrayList<OpeningStockBean> list = new ArrayList<OpeningStockBean>();
        Cursor dbcursor = null;

        try {

            dbcursor = db.rawQuery("SELECT DISTINCT ST.BRAND, ST.BRAND_CD from  Skupost_data ST INNER JOIN Availability_MappingPOST_DATA DS ON" +
                    " DS.SKU_CD = ST.SKU_CD WHERE STORE_CD =" + Store_id + " ORDER BY DS.BRAND_SEQUENCE", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    OpeningStockBean openingBean = new OpeningStockBean();
                    openingBean.setBRAND(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Key_BRAND)));
                    openingBean.setBRAND_CD(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_BRAND_CD)));
                    list.add(openingBean);

                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }


        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return list;

    }

    public ArrayList<PromotionTrackingBean> getPromotionTrackData(String Store_id) {
        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");

        ArrayList<PromotionTrackingBean> list = new ArrayList<PromotionTrackingBean>();
        Cursor dbcursor = null;

        try {

            dbcursor = db.rawQuery("SELECT DISTINCT BRAND , BRAND_CD from Mapping_PromotionPost_data " + " WHERE STORE_CD =" + Store_id + " ORDER BY SKU_SEQUENCE ASC", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {

                    PromotionTrackingBean openingBean = new PromotionTrackingBean();
                    openingBean.setBrand(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Key_BRAND)));
                    openingBean.setBrand_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_BRAND_CD)));
                    list.add(openingBean);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }


        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return list;

    }

    public ArrayList<MappingPromotionpostgetterSetter> getPromotionDefaultData(String Store_id) {
        Log.d("FetchingSto>Start<",
                "-");

        ArrayList<MappingPromotionpostgetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;

        try {

            dbcursor = db.rawQuery("SELECT DISTINCT SK.SKU_CD AS SKU_CD, SK.SKU AS SKU, BRAND_CD " +
                    "from PROMOTION_MAPPING PM INNER JOIN Skupost_data SK ON PM.SKU_CD = SK.SKU_CD" + " WHERE STORE_CD =" + Store_id + " ORDER BY SKU_SEQUENCE ASC", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {

                    MappingPromotionpostgetterSetter openingBean = new MappingPromotionpostgetterSetter();
                    openingBean.setSKU_CD(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_SKU_CD)));
                    openingBean.setSKU(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_SKU)));
                    list.add(openingBean);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }


        } catch (Exception e) {
            Log.d("Excefetching Records", e.toString());
            return list;
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return list;

    }


    public ArrayList<AssetTrackerBean> getAssetTrackData(String Store_id) {
        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");

        ArrayList<AssetTrackerBean> list = new ArrayList<AssetTrackerBean>();
        Cursor dbcursor = null;

        try {

            dbcursor = db.rawQuery("SELECT DISTINCT BRAND , BRAND_CD from Mapping_AssetPost_data " + " WHERE STORE_CD =" + Store_id + " GROUP BY BRAND", null);
            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    AssetTrackerBean openingBean = new AssetTrackerBean();
                    openingBean.setBrand(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Key_BRAND)));
                    openingBean.setBrand_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_BRAND_CD)));
                    list.add(openingBean);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }


        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingStoredat------->Stop<------",
                "---------------");
        return list;

    }

    public ArrayList<PromotionTrackingBean> getPromotionChildListData(String brand_id, String store_id) {
        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");
        ArrayList<PromotionTrackingBean> list = new ArrayList<PromotionTrackingBean>();
        Cursor dbcursor = null;

        try {

            dbcursor = db.rawQuery("SELECT SKU, SKU_CD, PROMOTION,DESCRIPTION from Mapping_PromotionPost_data " +
                    " WHERE BRAND_CD =" + brand_id + " AND STORE_CD =" + store_id, null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    PromotionTrackingBean openingBean = new PromotionTrackingBean();
                    openingBean.setSku_name(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_SKU)));
                    openingBean.setSku_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_SKU_CD)));
                    openingBean.setPromotion_name(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_PROMOTION)));
                    openingBean.setDescription(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_DESCRIPTION)));
                    list.add(openingBean);

                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingStoredat--------->Stop<----",
                "----------");
        return list;

    }


    public ArrayList<MappingPromotionpostgetterSetter> getPromotionChildListDefaltData(String STORE_CD, String SKU_CD) {
        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");
        ArrayList<MappingPromotionpostgetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;

        try {

            dbcursor = db.rawQuery("SELECT * from PROMOTION_MAPPING " + " WHERE STORE_CD ='" + STORE_CD + "' AND SKU_CD ='" + SKU_CD + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MappingPromotionpostgetterSetter openingBean = new MappingPromotionpostgetterSetter();
                    openingBean.setID(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_ID1)));
                    openingBean.setSKU_CD(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_SKU_CD)));
                    openingBean.setPROMOTION(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_PROMOTION)));
                    // openingBean.setSKU_SEQUENCE(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_SKU_SEQUENCE)));
                    list.add(openingBean);

                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingStoredat--------->Stop<----",
                "----------");
        return list;

    }


    public ArrayList<AssetTrackerBean> getAssetChildListData(String brand_id, String store_id) {
        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");

        ArrayList<AssetTrackerBean> list = new ArrayList<AssetTrackerBean>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT ASSET, ASSET_CD, count from Mapping_AssetPost_data " +
                    " WHERE BRAND_CD =" + brand_id + " AND STORE_CD =" + store_id, null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    AssetTrackerBean openingBean = new AssetTrackerBean();
//					openingBean.setSku_name(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_SKU)));
//					openingBean.setSku_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_SKU_CD)));
                    openingBean.setAsset_name(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_ASSET)));
                    openingBean.setAsset_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_ASSET_CD)));
                    openingBean.setCount(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.key_count)));

                    list.add(openingBean);

                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }


        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return list;

    }

    public ArrayList<MidDayRecievedStockBean> getMidDayStockRecieved(String Store_id) {
        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");

        ArrayList<MidDayRecievedStockBean> list = new ArrayList<MidDayRecievedStockBean>();
        Cursor dbcursor = null;
        String False = "'False'";

        try {
            dbcursor = db.rawQuery("SELECT DISTINCT ST.BRAND, ST.BRAND_CD from  Skupost_data ST INNER JOIN Availability_MappingPOST_DATA DS ON" +
                    " DS.SKU_CD = ST.SKU_CD WHERE STORE_CD =" + Store_id + " AND ST.ISCOMPETETIOR =" + False + "" +
                    "AND DS.SKU_CD IN (SELECT SKU_CD FROM Availability_MappingPOST_DATA  WHERE STOCK_ALLOW='True') ORDER BY DS.BRAND_SEQUENCE ASC", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MidDayRecievedStockBean StockRecievedBean = new MidDayRecievedStockBean();
                    StockRecievedBean.setBRAND(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Key_BRAND)));
//					StockRecievedBean.setSKU(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_SKU)));
//					StockRecievedBean.setSKU_CD(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_SKU_CD)));
                    StockRecievedBean.setBRAND_CD(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_BRAND_CD)));
                    list.add(StockRecievedBean);

                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }


        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return list;

    }


    public ArrayList<MidDayRecievedStockBean> getMidDayStockRecievedDataWithSku(String Store_id) {
        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");

        ArrayList<MidDayRecievedStockBean> list = new ArrayList<MidDayRecievedStockBean>();
        Cursor dbcursor = null;
        String False = "'False'";
        try {
            dbcursor = db.rawQuery(" SELECT DISTINCT ST.BRAND, ST.BRAND_CD, ST.SKU, ST.SKU_CD from  Skupost_data ST INNER JOIN Availability_MappingPOST_DATA DS ON " +
                    " DS.SKU_CD = ST.SKU_CD WHERE STORE_CD = " + Store_id + " AND ST.ISCOMPETETIOR = " + False + "" +
                    " AND DS.SKU_CD IN (SELECT SKU_CD FROM Availability_MappingPOST_DATA  WHERE STOCK_ALLOW = 'True') ORDER BY DS.BRAND_SEQUENCE ASC ", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MidDayRecievedStockBean StockRecievedBean = new MidDayRecievedStockBean();
                    StockRecievedBean.setBRAND(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Key_BRAND)));
                    StockRecievedBean.setSKU(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_SKU)));
                    StockRecievedBean.setSKU_CD(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_SKU_CD)));
                    StockRecievedBean.setBRAND_CD(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_BRAND_CD)));
                    StockRecievedBean.setIsListed("0");
                    list.add(StockRecievedBean);

                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }


        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return list;

    }

    public ArrayList<MidDayRecievedStockBean> getSubskulistData(String brand_id, String store_cd) {
        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");

        ArrayList<MidDayRecievedStockBean> list = new ArrayList<MidDayRecievedStockBean>();
        Cursor dbcursor = null;
        String True = "'True'";

        try {

            dbcursor = db.rawQuery("SELECT ST.SKU, ST.SKU_CD from  Skupost_data ST INNER JOIN Availability_MappingPOST_DATA" +
                    " DS ON DS.SKU_CD = ST.SKU_CD WHERE BRAND_CD =" + brand_id + " AND DS.STORE_CD=" + store_cd + " AND DS.STOCK_ALLOW =" + True +
                    " ORDER BY DS.SKU_SEQUENCE", null);
            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MidDayRecievedStockBean StockRecievedBean = new MidDayRecievedStockBean();
//					StockRecievedBean.setBRAND(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Key_BRAND)));
                    StockRecievedBean.setSKU(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_SKU)));
                    StockRecievedBean.setSKU_CD(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_SKU_CD)));
//					StockRecievedBean.setBRAND_CD(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_BRAND_CD)));
                    list.add(StockRecievedBean);

                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }


        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return list;


    }


    @SuppressLint("LongLogTag")
    public ArrayList<ClosingStockBean> getSubskulistClosingData(String brand_id, String store_cd) {
        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");

        ArrayList<ClosingStockBean> list = new ArrayList<ClosingStockBean>();
        Cursor dbcursor = null;
        String True = "'True'";

        try {


            dbcursor = db.rawQuery("SELECT ST.SKU, ST.SKU_CD from  Skupost_data ST INNER JOIN Availability_MappingPOST_DATA" +
                    " DS ON DS.SKU_CD = ST.SKU_CD WHERE BRAND_CD =" + brand_id + " AND DS.STORE_CD=" + store_cd + " AND DS.STOCK_ALLOW =" + True +
                    " ORDER BY DS.SKU_SEQUENCE", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    ClosingStockBean StockRecievedBean = new ClosingStockBean();
//					StockRecievedBean.setBRAND(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Key_BRAND)));
                    StockRecievedBean.setSKU(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_SKU)));
                    StockRecievedBean.setSKU_CD(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_SKU_CD)));
//					StockRecievedBean.setBRAND_CD(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_BRAND_CD)));
                    list.add(StockRecievedBean);

                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }


        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return list;


    }

    public ArrayList<OpeningStockBean> getOpeningSkuData(String brand_id, String store_cd) {
        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");

        ArrayList<OpeningStockBean> list = new ArrayList<OpeningStockBean>();
        Cursor dbcursor = null;

        try {

            dbcursor = db.rawQuery("SELECT ST.SKU, ST.SKU_CD from  Skupost_data ST INNER JOIN Availability_MappingPOST_DATA" +
                    " DS ON DS.SKU_CD = ST.SKU_CD WHERE BRAND_CD =" + brand_id + " AND DS.STORE_CD=" + store_cd + " ORDER BY DS.SKU_SEQUENCE ", null);
            //  Store_id + " ORDER BY DS.BRAND_SEQUENCE",


            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    OpeningStockBean StockRecievedBean = new OpeningStockBean();
//					StockRecievedBean.setBRAND(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Key_BRAND)));
                    StockRecievedBean.setSKU(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_SKU)));
                    StockRecievedBean.setSKU_CD(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_SKU_CD)));
//					StockRecievedBean.setBRAND_CD(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_BRAND_CD)));
                    list.add(StockRecievedBean);

                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }


        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return list;


    }


    public ArrayList<ClosingStockBean> getClosingStock(String Store_id) {
        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");

        ArrayList<ClosingStockBean> list = new ArrayList<ClosingStockBean>();
        Cursor dbcursor = null;
        String False = "'False'";


        try {

            dbcursor = db.rawQuery("SELECT DISTINCT ST.BRAND, ST.BRAND_CD from  Skupost_data ST INNER JOIN Availability_MappingPOST_DATA DS ON" +
                    " DS.SKU_CD = ST.SKU_CD WHERE STORE_CD =" + Store_id + " AND ST.ISCOMPETETIOR =" + False + "" +
                    "AND DS.SKU_CD IN (SELECT SKU_CD FROM Availability_MappingPOST_DATA  WHERE STOCK_ALLOW='True') ORDER BY DS.BRAND_SEQUENCE ASC", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    ClosingStockBean StockRecievedBean = new ClosingStockBean();
                    StockRecievedBean.setBRAND(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Key_BRAND)));
                    StockRecievedBean.setBRAND_CD(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_BRAND_CD)));
                    list.add(StockRecievedBean);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }


        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return list;

    }


    // getInsertedData
    public ArrayList<OpeningStockBean> getInsertedComplianceData(String storeid) {

        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");
        ArrayList<OpeningStockBean> list = new ArrayList<OpeningStockBean>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT  * from "
                    + CommonString.TABLE_INSERT_OPENINGHEADER_DATA + " where "
                    + CommonString.key_STORE_CD + " = '" + storeid + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    OpeningStockBean sb = new OpeningStockBean();

                    sb.setCommonId(dbcursor.getInt(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_ID)));
                    sb.setMid(dbcursor.getInt(dbcursor
                            .getColumnIndexOrThrow(CommonString.MID)));

                    sb.setStore(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.key_STORE_CD)));
                    sb.setBRAND(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.Key_BRAND)));

                    sb.setBRAND_CD(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_BRAND_CD)));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return list;

    }


    // getInsertedData
    public ArrayList<PromotionTrackingBean> getInsertedPromotionComplianceData(String storeid) {

        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");
        ArrayList<PromotionTrackingBean> list = new ArrayList<PromotionTrackingBean>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT  * from " + CommonString.TABLE_INSERT_HEADER_PROMOTION_DATA + " where "
                    + CommonString.key_STORE_CD + " = '" + storeid + "'", null);


            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    PromotionTrackingBean sb = new PromotionTrackingBean();
                    sb.setCommonId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_ID)));
                    sb.setMid(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.MID)));
                    sb.setStore_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.key_STORE_CD)));
                    sb.setBrand(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Key_BRAND)));
                    sb.setBrand_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_BRAND_CD)));
                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return list;

    }

    // getInsertedData
    public ArrayList<MappingPromotionpostgetterSetter> getInsertedPromotionData(String storeid) {

        Log.d("FetchingSdata-->Start<",
                "-");
        ArrayList<MappingPromotionpostgetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT  * from " + CommonString.TABLE_INSERT_HEADER_PROMOTION_DATA + " where " + CommonString.key_STORE_CD + " = '" + storeid + "'", null);


            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MappingPromotionpostgetterSetter sb = new MappingPromotionpostgetterSetter();
                    sb.setCommonId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_ID)));
                    // sb.setMid(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.MID)));
                    sb.setSTORE_CD(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.key_STORE_CD)));
                    sb.setSKU(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_SKU)));
                    sb.setSKU_CD(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_SKU_CD)));



                   /* sb.setPROMOTION(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_PROMOTION)));
                    sb.setPromotion_avail(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_PROMOTION_AVAIL)));
                    sb.setReasonid(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_REASON_ID)));
                    sb.setReason(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_REASON)));
                    sb.setImage(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE)));
                    sb.setRemark(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_REMARKS)));*/




                    /*values1.put(CommonString.KEY_SKU_CD, save_listDataHeader.get(j).getSKU_CD().get(0));

                    values1.put(CommonString.KEY_PROMOTION, data.get(save_listDataHeader.get(i)).get(j).getPROMOTION().get(0));
                    values1.put(CommonString.KEY_PROMOTION_AVAIL, data.get(save_listDataHeader.get(i)).get(j).getPromotion_avail());

                    values1.put(CommonString.KEY_REMARKS, data.get(save_listDataHeader.get(i)).get(j).getRemark());

                  *//*  values1.put(CommonString.KEY_DESCRIPTION,
                            data.get(save_listDataHeader.get(i)).get(j)
                                    .getDescription());*//*


                    values1.put(CommonString.KEY_REASON_ID, data.get(save_listDataHeader.get(i)).get(j).getReasonid());

                    values1.put(CommonString.KEY_REASON, data.get(save_listDataHeader.get(i)).get(j).getReason());


                    values1.put(CommonString.KEY_IMAGE, data.get(save_listDataHeader.get(i)).get(j).getImage());*/


                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return list;

    }

   /* public ArrayList<PromotionTrackingBean> getInsertedPromotionSubList(int commomid) {

        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");
        ArrayList<PromotionTrackingBean> list = new ArrayList<PromotionTrackingBean>();
        Cursor dbcursor = null;

        try {

            dbcursor = db.rawQuery("SELECT  * from "
                    + CommonString.TABLE_INSERT_PROMOTION_DATA + " where "
                    + CommonString.KEY_COMMONID + " = '" + commomid + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    PromotionTrackingBean sb = new PromotionTrackingBean();

                    sb.setStore_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.key_STORE_CD)));

                    sb.setSku_name(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_SKU)));

                    sb.setSku_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_SKU_CD)));

                    sb.setPromotion_avail(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_PROMOTION_AVAIL)));

                    sb.setPromotion_name(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_PROMOTION_NAME)));

                    sb.setRemark(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_REMARKS)));

                    sb.setDescription(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_DESCRIPTION)));

                    sb.setCommonId(dbcursor.getInt(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_COMMONID)));

                    sb.setReasonid(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_REASON_ID)));

                    sb.setReason(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_REASON)));

                    sb.setImage(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_IMAGE)));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }*/


    public ArrayList<MappingPromotionpostgetterSetter> getInsertedPromotionDefaltSubList(int commomid) {

        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");
        ArrayList<MappingPromotionpostgetterSetter> list = new ArrayList<>();
        Cursor dbcursor = null;

        try {

            dbcursor = db.rawQuery("SELECT  * from "
                    + CommonString.TABLE_INSERT_PROMOTION_DATA + " where " + CommonString.KEY_COMMONID + " = '" + commomid + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MappingPromotionpostgetterSetter sb = new MappingPromotionpostgetterSetter();

                    sb.setSTORE_CD(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.key_STORE_CD)));

                    // sb.setS(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_SKU)));
                    sb.setSKU_CD(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_SKU_CD)));
                    sb.setPromotion_avail(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_PROMOTION_AVAIL)));
                    sb.setPROMOTION(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_PROMOTION)));
                    sb.setRemark(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_REMARKS)));
                    sb.setID(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_PROMOTION_CD)));
                    sb.setCommonId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_COMMONID)));
                    sb.setReasonid(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_REASON_ID)));
                    sb.setReason(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_REASON)));
                    sb.setImage(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE)));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }


        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return list;

    }


    public ArrayList<AssetTrackerBean> getInsertedAssetComplianceData(String storeid) {
        Log.d("FetchingStoredata--------------->Start<------------", "------------------");
        ArrayList<AssetTrackerBean> list = new ArrayList<AssetTrackerBean>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT  * from " + CommonString.TABLE_INSERT_HEADER_ASSET_DATA + " where " + CommonString.key_STORE_CD + " = '" + storeid + "'", null);
            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    AssetTrackerBean sb = new AssetTrackerBean();
                    sb.setCommonId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_ID)));
                    sb.setMid(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.MID)));
                    sb.setStore_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.key_STORE_CD)));
                    sb.setBrand(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Key_BRAND)));
                    sb.setBrand_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_BRAND_CD)));
                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return list;

    }


    public ArrayList<competitionbeans> getInsertedCompetitionData(String storeid) {

        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");
        ArrayList<competitionbeans> list = new ArrayList<competitionbeans>();
        Cursor dbcursor = null;

        try {

            dbcursor = db.rawQuery("SELECT  * from "
                    + CommonString.TABLE_INSERT_COMPETITION_DATA + " where "
                    + CommonString.key_STORE_CD + " = '" + storeid + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    competitionbeans sb = new competitionbeans();

                    sb.setStore_id(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.key_STORE_CD)));


                    sb.setCategory_id(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_CATEGORY_CD)));

                    sb.setCategory(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_CATEGORY)));

                    sb.setQuantity(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_QUANTITY)));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return list;

    }


    public ArrayList<AssetTrackerBean> getInsertedAssetSubList(int commomid) {

        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");
        ArrayList<AssetTrackerBean> list = new ArrayList<AssetTrackerBean>();
        Cursor dbcursor = null;

        try {

            dbcursor = db.rawQuery("SELECT  * from "
                    + CommonString.TABLE_INSERT_ASSET_DATA + " where "
                    + CommonString.KEY_COMMONID + " = '" + commomid + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    AssetTrackerBean sb = new AssetTrackerBean();

                    sb.setStore_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.key_STORE_CD)));

//								sb.setSku_name(dbcursor.getString(dbcursor
//										.getColumnIndexOrThrow(CommonString.KEY_SKU)));
//
//								sb.setSku_cd(dbcursor.getString(dbcursor
//										.getColumnIndexOrThrow(CommonString.KEY_SKU_CD)));
                    sb.setAsset_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_ASSET_CD)));
                    sb.setAsset_avail(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_ASSET_AVAIL)));

                    sb.setAsset_name(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_ASSET_NAME)));

                    sb.setCount(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.key_count)));

                    sb.setActualCount(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.key_actualCount)));

                    sb.setRemark(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_REMARKS)));

                    sb.setCommonId(dbcursor.getInt(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_COMMONID)));

                    sb.setReasonid(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_REASON_ID)));

                    sb.setReason(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_REASON)));

                    sb.setImage(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_IMAGE)));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return list;

    }


    public ArrayList<OpeningStockBean> getInsertedSubList(int commomid) {

        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");
        ArrayList<OpeningStockBean> list = new ArrayList<OpeningStockBean>();
        Cursor dbcursor = null;

        try {

            dbcursor = db.rawQuery("SELECT  * from "
                    + CommonString.TABLE_OENGINGSTOCK_DATA + " where "
                    + CommonString.KEY_COMMONID + " = '" + commomid + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    OpeningStockBean sb = new OpeningStockBean();

                    sb.setSKU(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_SKU)));

                    sb.setSKU_CD(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_SKU_CD)));

                    sb.setStore(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.key_STORE_CD)));

                    sb.setShelf(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_SHELF)));

                    sb.setFacing(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_FACING)));
                    sb.setOpeningStock(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_OPENINGSTOCK)));

                    sb.setPricing(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_PRICING)));

                    sb.setPrice_sign(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_PRICE_SIGNOGE)));

                    sb.setSign_corrected(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_SIGNOGE_CORRECTED)));

                    sb.setExpriring_stock(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_EXPIRING_STOCK)));

                    sb.setPog_adherence(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_POG_ADHERENCE)));

                    sb.setPog_corrected(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_POG_CORRECTED)));

                    sb.setShelf_fm_bottom(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_SHELF_FM_BOTTOM)));

                    sb.setCommonId(dbcursor.getInt(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_COMMONID)));


                    sb.setExpiring_date(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_EXPIRING_DATE)));


                    sb.setListedFlag(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_LISTED_CHECKBOX)));


                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return list;

    }

    // getInsertedStockRecievedData
    public ArrayList<MidDayRecievedStockBean> getInsertedStockRecievedComplianceData(String storeid) {

        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");
        ArrayList<MidDayRecievedStockBean> list = new ArrayList<MidDayRecievedStockBean>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT  * from "
                    + CommonString.TABLE_INSERT_STOCKRECIEVED_DATA + " where "
                    + CommonString.key_STORE_CD + " = '" + storeid + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MidDayRecievedStockBean sb = new MidDayRecievedStockBean();
                    sb.setCommonId(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_ID)));
                    sb.setMid(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.MID)));
                    sb.setStore(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.key_STORE_CD)));
                    sb.setBRAND(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.Key_BRAND)));
                    sb.setBRAND_CD(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_BRAND_CD)));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return list;

    }


    public ArrayList<MidDayRecievedStockBean> getInsertedStockRecievedSubList(int commomid) {

        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");
        ArrayList<MidDayRecievedStockBean> list = new ArrayList<MidDayRecievedStockBean>();
        Cursor dbcursor = null;

        try {

            dbcursor = db.rawQuery("SELECT  * from "
                    + CommonString.TABLE_MIDDAYSTOCK_RECIEVED_DATA + " where "
                    + CommonString.KEY_COMMONID + " = '" + commomid + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MidDayRecievedStockBean sb = new MidDayRecievedStockBean();

                    sb.setStore(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.key_STORE_CD)));

                    sb.setSKU(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_SKU)));

                    sb.setSKU_CD(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_SKU_CD)));
                    sb.setStock(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_STOCK)));

                    sb.setCommonId(dbcursor.getInt(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_COMMONID)));


                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return list;

    }


    // getInsertedStockRecievedData
    public ArrayList<ClosingStockBean> getInsertedClosingStockComplianceData(String storeid) {

        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");
        ArrayList<ClosingStockBean> list = new ArrayList<ClosingStockBean>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT  * from "
                    + CommonString.TABLE_INSERT_CLOSINGSTOCK_DATA + " where "
                    + CommonString.key_STORE_CD + " = '" + storeid + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    ClosingStockBean sb = new ClosingStockBean();

                    sb.setCommonId(dbcursor.getInt(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_ID)));
                    sb.setMid(dbcursor.getInt(dbcursor
                            .getColumnIndexOrThrow(CommonString.MID)));

                    sb.setStore(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.key_STORE_CD)));
                    sb.setBRAND(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.Key_BRAND)));

                    sb.setBRAND_CD(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_BRAND_CD)));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return list;

    }

    public ArrayList<ClosingStockBean> getInsertedClosingStockSubList(int commomid) {

        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");
        ArrayList<ClosingStockBean> list = new ArrayList<ClosingStockBean>();
        Cursor dbcursor = null;

        try {

            dbcursor = db.rawQuery("SELECT  * from "
                    + CommonString.TABLE_CLOSING_STOCK_DATA + " where "
                    + CommonString.KEY_COMMONID + " = '" + commomid + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    ClosingStockBean sb = new ClosingStockBean();

                    sb.setStore(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.key_STORE_CD)));

                    sb.setSKU(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_SKU)));

                    sb.setSKU_CD(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_SKU_CD)));
                    sb.setStock(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_STOCK)));

                    sb.setCommonId(dbcursor.getInt(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_COMMONID)));


                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return list;

    }

    public ArrayList<CallsBean> getCallTrackerData(String store_id) {
        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");

        ArrayList<CallsBean> list = new ArrayList<CallsBean>();
        Cursor dbCursor = null;
        try {
            dbCursor = db.rawQuery("SELECT * FROM CallsTracker_data WHERE STORE_CD = " + store_id, null);

            if (dbCursor != null) {
                dbCursor.moveToFirst();
                while (!dbCursor.isAfterLast()) {
                    CallsBean bean = new CallsBean();
                    bean.setTotalCalls(dbCursor.getString(dbCursor.getColumnIndexOrThrow(CommonString.KEY_TOTAL_CALLS3to6)));

                    bean.setProductiveCalls(dbCursor.getString(dbCursor.getColumnIndexOrThrow(CommonString.KEY_PRODUCTIVE_CALLS3to6)));

                    bean.setPosSale(dbCursor.getString(dbCursor.getColumnIndexOrThrow(CommonString.KEY_POS_SALE)));
                    bean.setMid(dbCursor.getInt(dbCursor.getColumnIndexOrThrow(CommonString.Key_Mid)));
                    bean.setStore_id(dbCursor.getString(dbCursor.getColumnIndexOrThrow(CommonString.KEY_STORE_CD)));
                    list.add(bean);
                    dbCursor.moveToNext();

                }

                dbCursor.close();
                return list;

            }

        } catch (Exception e) {

            Log.e("........", e.toString());

        }
        return list;

    }

    public ArrayList<CallsBean> getCallDataInstant(String store_id) {
        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");

        ArrayList<CallsBean> list = new ArrayList<CallsBean>();
        Cursor dbCursor = null;
        try {
            dbCursor = db.rawQuery("SELECT * FROM CallsTracker_data_12to3 WHERE STORE_CD = " + store_id, null);

            if (dbCursor != null) {
                dbCursor.moveToFirst();
                while (!dbCursor.isAfterLast()) {
                    CallsBean bean = new CallsBean();
                    bean.setTotalCalls(dbCursor.getString(dbCursor
                            .getColumnIndexOrThrow(CommonString.KEY_TOTAL_CALLS12to3)));

                    bean.setProductiveCalls(dbCursor.getString(dbCursor
                            .getColumnIndexOrThrow(CommonString.KEY_PRODUCTIVE_CALLS12to3)));

                    bean.setMid(dbCursor.getInt(dbCursor
                            .getColumnIndexOrThrow(CommonString.Key_Mid)));
                    bean.setStore_id(dbCursor.getString(dbCursor
                            .getColumnIndexOrThrow(CommonString.KEY_STORE_CD)));

                    list.add(bean);

                    dbCursor.moveToNext();

                }

                dbCursor.close();
                return list;

            }

        } catch (Exception e) {

            Log.e("........", e.toString());

        }
        return list;


    }

    public ArrayList<salesBean> getCompetionDataInserted(int mid) {

        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");
        ArrayList<salesBean> list = new ArrayList<salesBean>();
        Cursor dbcursor = null;

        try {

            dbcursor = db.rawQuery("SELECT * from "
                    + CommonString.TABLE_COMPETITION_INSERTED + " where "
                    + CommonString.Key_Mid + " = " + mid, null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    salesBean sb = new salesBean();
                    sb.setBrand(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.Key_BRAND)));

                    sb.setProduct(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.Key_PRODUCT_NAME)));

                    sb.setProduct_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.Key_PRODUCT_CD)));

                    sb.setType((dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.Key_PRODUCT_TYPE))));
                    sb.setIscompetitor(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.Key_ISCOMPETITIOR)));
                    sb.setPrice(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.Key_PRICE)));
                    sb.setLastmonth_sale(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.key_LSTMONTH_SALE)));
                    sb.setTilldate_sale(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.Key_TILLDATE_SALE)));
                    sb.setSales_Outlook(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.Key_SALES_OUTLOOK)));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return list;

    }


    public ArrayList<salesBean> getCompetionData() {

        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");
        ArrayList<salesBean> list = new ArrayList<salesBean>();
        Cursor dbcursor = null;

        try {

            dbcursor = db.rawQuery(
                    "SELECT * from " + CommonString.TABLE_SALES + " where "
                            + CommonString.Key_ISCOMPETITIOR + " = 'True' ",
                    null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    salesBean sb = new salesBean();
                    sb.setBrand(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.Key_BRAND)));

                    sb.setProduct(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.Key_PRODUCT_NAME)));

                    sb.setProduct_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.Key_PRODUCT_CD)));

                    sb.setType((dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.Key_PRODUCT_TYPE))));
                    sb.setIscompetitor(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.Key_ISCOMPETITIOR)));
                    sb.setPrice("");
                    sb.setLastmonth_sale("");
                    sb.setTilldate_sale("");
                    sb.setSales_Outlook("");

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return list;

    }

    public void updateGeoTagDataInMain(String storeid) {

        try {

            ContentValues values = new ContentValues();
            values.put(CommonString.KEY_STATUS, CommonString.KEY_U);

            int l = db.update(CommonString.TABLE_GEOTAG_DETAIL, values,
                    CommonString.KEY_STORE_ID + "=?", new String[]{storeid});
            System.out.println("update : " + l);
        } catch (Exception e) {

        }
    }

    public void deleteGeoTagData(String storeid) {

        try {
            db.delete(CommonString.TABLE_INSERT_GEO_TAG,
                    CommonString.KEY_STORE_ID + "='" + storeid + "'", null);
        } catch (Exception e) {

        }
    }

    public long InsertCoverageData(CoverageBean data) {
        ContentValues values = new ContentValues();
        try {
            values.put(CommonString.KEY_STORE_ID, data.getStoreId());
            values.put(CommonString.KEY_USER_ID, data.getUserId());
            values.put(CommonString.KEY_IN_TIME, data.getInTime());
            values.put(CommonString.KEY_OUT_TIME, data.getOutTime());
            values.put(CommonString.KEY_VISIT_DATE, data.getVisitDate());
            values.put(CommonString.KEY_LATITUDE, data.getLatitude());
            values.put(CommonString.KEY_LONGITUDE, data.getLongitude());
            values.put(CommonString.KEY_REASON_ID, data.getReasonid());
            values.put(CommonString.KEY_REASON, data.getReason());
            values.put(CommonString.KEY_REMARKS, data.getRemark());
            values.put(CommonString.KEY_STATUS, data.getStatus());
            values.put(CommonString.KEY_RIGHTS, data.getRights());
            values.put(CommonString.KEY_IMAGE, data.getImage());
            values.put(CommonString.KEY_OUTLOOK_REMARK, data.getOutlook_Remark());
            return db.insert(CommonString.TABLE_COVERAGE_DATA, null, values);

        } catch (Exception ex) {
            Log.d("Database Exception while Insert Closes Data ",
                    ex.toString());
        }
        return 0;
    }

    public long UpdateCoverageData(CoverageBean data, String visitDate, String Storeid) {

        ContentValues values = new ContentValues();

        try {

            values.put(CommonString.KEY_STORE_ID, data.getStoreId());
            values.put(CommonString.KEY_USER_ID, data.getUserId());
            values.put(CommonString.KEY_IN_TIME, data.getInTime());
            values.put(CommonString.KEY_OUT_TIME, data.getOutTime());
            values.put(CommonString.KEY_VISIT_DATE, data.getVisitDate());
            values.put(CommonString.KEY_LATITUDE, data.getLatitude());
            values.put(CommonString.KEY_LONGITUDE, data.getLongitude());
            values.put(CommonString.KEY_REASON_ID, data.getReasonid());
            values.put(CommonString.KEY_REASON, data.getReason());
            values.put(CommonString.KEY_REMARKS, data.getRemark());
            values.put(CommonString.KEY_STATUS, data.getStatus());
            values.put(CommonString.KEY_IMAGE, data.getImage());
            values.put(CommonString.KEY_OUTLOOK_REMARK,
                    data.getOutlook_Remark());

            db.update(CommonString.TABLE_COVERAGE_DATA, values,
                    CommonString.KEY_STORE_ID + "='" + Storeid + "' AND "
                            + CommonString.KEY_VISIT_DATE + "='" + visitDate
                            + "'", null);

        } catch (Exception ex) {
            Log.d("Database Exception while Insert Closes Data ",
                    ex.toString());
        }
        return 0;
    }

    public void updateGeoTagData(String storeid) {

        try {

            ContentValues values = new ContentValues();
            values.put(CommonString.KEY_STATUS, CommonString.KEY_D);

            int l = db.update(CommonString.TABLE_INSERT_GEO_TAG, values,
                    CommonString.KEY_STORE_ID + "=?", new String[]{storeid});
            System.out.println("update : " + l);
        } catch (Exception e) {
            Log.d("Database Exception while Insert Posm Master Data ",
                    e.toString());
        }
    }

    public ArrayList<GeotaggingBeans> getGeotaggingData(String status) {

        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");

        ArrayList<GeotaggingBeans> geodata = new ArrayList<GeotaggingBeans>();

        Cursor dbcursor = null;
        try {
            dbcursor = db.rawQuery("SELECT  * from " + CommonString.TABLE_INSERT_GEO_TAG + "  WHERE STATUS = '"
                    + status + "'", null);

            if (dbcursor != null) {
                int numrows = dbcursor.getCount();

                dbcursor.moveToFirst();
                for (int i = 1; i <= numrows; ++i) {

                    GeotaggingBeans data = new GeotaggingBeans();
                    data.setStoreid(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_STORE_ID)));
                    data.setLatitude(Double.parseDouble(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_LATITUDE))));
                    data.setLongitude(Double.parseDouble(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_LONGITUDE))));
                    data.setUrl1(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE_PATH1)));
                    data.setUrl2(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE_PATH2)));
                    data.setUrl3(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE_PATH3)));
                    geodata.add(data);
                    dbcursor.moveToNext();

                }

                dbcursor.close();

            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return geodata;

    }

    public boolean update_CoverageTable(String time, String id) {
        ContentValues values = new ContentValues();
        values.put(CommonString.KEY_OUT_TIME, time);
        return db.update(CommonString.TABLE_COVERAGE_DATA, values, CommonString.KEY_STORE_ID + " =" + id, null) > 0;
    }

    public ArrayList<ReasonModel> getReasonData() {

        ArrayList<ReasonModel> list = new ArrayList<ReasonModel>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT  * from "
                    + CommonString.TABLE_NON_WORKING_REASON, null);

            if (dbcursor != null) {

                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    ReasonModel sb = new ReasonModel();

                    sb.setReason(((dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_REASON)))));

                    sb.setReasonid((((dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_REASON_ID))))));

                    sb.setImage(((dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_IMAGE)))));

                    sb.setEntry(((dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_ENTRY)))));

                    sb.setREASON_REMARK((dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_RESON_REMARK))));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception ex) {
            Log.d("Database Exception while Insert Posm Master Data ",
                    ex.toString());
        }
        return list;

    }

    public void InsertNonWorkingData(NonWorkingGetterSetter data) {

        db.delete(CommonString.TABLE_NON_WORKING_REASON, null, null);
        ContentValues values = new ContentValues();

        try {

            for (int i = 0; i < data.getReason_id().size(); i++) {
                values.put(CommonString.KEY_REASON, data.getReason().get(i));
                values.put(CommonString.KEY_REASON_ID, data.getReason_id().get(i));
//				values.put(CommonString.KEY_IMAGE, data.getImage().get(i));
//				values.put(CommonString.KEY_ENTRY, data.getEntry().get(i));
//				values.put(CommonString.KEY_RESON_REMARK, data
//						.getReason_remark().get(i));

                db.insert(CommonString.TABLE_NON_WORKING_REASON, null, values);

            }

        } catch (Exception ex) {
            Log.d("Database Exception while Insert Posm Master Data ",
                    ex.toString());
        }

    }

    public void InsertAvailabilityData(AvailabilitygetterSetter data) {

        db.delete(CommonString.TABLE_AVAILABILITY_DATA, null, null);
        ContentValues values = new ContentValues();

        try {
            for (int i = 0; i < data.getSTORE_CD().size(); i++) {

                values.put(CommonString.key_STORE_CD, data.getSTORE_CD().get(i));
                values.put(CommonString.KEY_SKU_CD,
                        data.getSKU_CD().get(i));

                values.put(CommonString.KEY_SKU_SEQUENCE,
                        data.getSKU_SEQUENCE().get(i));

                values.put(CommonString.KEY_MBQ, data.getMBQ().get(i));
                values.put(CommonString.KEY_IDEAL_SHELF, data
                        .getIDEAL_SHELF().get(i));
                values.put(CommonString.KEY_CATEGORY_SEQUENCE, data.getCATEGORY_SEQUENCE().get(i));

                values.put(CommonString.KEY_BRAND_SEQUENCE, data.getBRAND_SEQUENCE().get(i));
                values.put(CommonString.KEY_FACING_ALLOW, data.getFACING_ALLOW().get(i));
                values.put(CommonString.KEY_PRICING_ALLOW, data.getPRICING_ALLOW().get(i));
                values.put(CommonString.KEY_STOCK_ALLOW, data.getSTOCK_ALLOW().get(i));
                values.put(CommonString.KEY_FOCUS, data.getFOCUS().get(i));

                values.put(CommonString.KEY_ALLOW_EXPRING_STOCK, data.getALLOW_EXPRING_STOCK().get(i));
                values.put(CommonString.KEY_ALLOW_POG, data.getALLOW_POG().get(i));
                values.put(CommonString.KEY_ALLOW_SHELF, data.getALLOW_SHELF().get(i));
                values.put(CommonString.KEY_ALLOW_SIGNAGE, data.getALLOW_SIGNAGE().get(i));

                db.insert(CommonString.TABLE_AVAILABILITY_DATA, null, values);
            }

        } catch (Exception ex) {
            Log.d("Database Exception while Insert Availablity post mapping ",
                    ex.toString());
        }

    }

    public void InsertAssetPostData(AssetPostgetterSetter data) {
        db.delete(CommonString.TABLE_ASSETPOST_DATA, null, null);
        ContentValues values = new ContentValues();
        try {
            for (int i = 0; i < data.getASSET_CD().size(); i++) {
                values.put(CommonString.KEY_ASSET_CD, data.getASSET_CD().get(i));
                values.put(CommonString.KEY_ASSET, data.getASSET().get(i));
                db.insert(CommonString.TABLE_ASSETPOST_DATA, null, values);
            }


        } catch (Exception ex) {
            Log.d("Database Exception while Insert Asset post data ",
                    ex.toString());
        }
    }


    public void InsertSKUPostData(SKUPostgetterSetter data) {
        db.delete(CommonString.TABLE_SKUPOST_DATA, null, null);
        ContentValues values = new ContentValues();

        try {
            for (int i = 0; i < data.getSKU_CD().size(); i++) {

                values.put(CommonString.KEY_SKU_CD, data.getSKU_CD().get(i));
                values.put(CommonString.KEY_SKU, data.getSKU().get(i));
                values.put(CommonString.KEY_BRAND_CD, data.getBRAND_CD().get(i));
                values.put(CommonString.Key_BRAND, data.getBRAND().get(i));
                values.put(CommonString.KEY_CATEGORY_CD, data.getCATEGORY_CD().get(i));
                values.put(CommonString.KEY_ISCOMPETETIOR, data.getISCOMPET().get(i));

                db.insert(CommonString.TABLE_SKUPOST_DATA, null, values);
            }

        } catch (Exception ex) {
            Log.d("Database Exception while Insert sku post data ",
                    ex.toString());
        }
    }


    public void InsertMappinAssetPostData(MappingAssetPostgetterSetter data) {
        db.delete(CommonString.TABLE_MAPPING_ASSETPOST, null, null);
        ContentValues values = new ContentValues();
        try {
            for (int i = 0; i < data.getASSET_CD().size(); i++) {
                values.put(CommonString.KEY_STORE_CD, data.getSTORE_CD().get(i));
//				values.put(CommonString.KEY_SKU, data.getSKU().get(i));
                values.put(CommonString.KEY_ASSET_CD, data.getASSET_CD().get(i));
                values.put(CommonString.KEY_ASSET, data.getASSET().get(i));
                values.put(CommonString.Key_BRAND, data.getBRAND().get(i));
                values.put(CommonString.KEY_BRAND_CD, data.getBRAND_CD().get(i));
                values.put(CommonString.key_count, data.getCount().get(i));
                db.insert(CommonString.TABLE_MAPPING_ASSETPOST, null, values);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("Database Exception while Insert sku post data ", ex.toString());
        }

    }


    public boolean isFeedbackCheckListData(String store_cd) {
        boolean filled = false;
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("Select * from INSERT_PROMOTION_DATA " + "where STORE_CD='" + store_cd + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                int count = dbcursor.getInt(0);
                dbcursor.close();

                if (count > 0) {
                    filled = true;
                } else {
                    filled = false;
                }
            }
        } catch (Exception e) {
            Log.d("Exception isempty", e.toString());
            return filled;
        }
        return filled;
    }

    public boolean isFeedbackCheckAdditionalDataListData(String store_cd) {
        boolean filled = false;
        Cursor dbcursor = null;

        try {
            // dbcursor = db.rawQuery("SELECT  * from " + CommonString.TABLE_INSERT_ADDTIONAL_DETAILS + " where " + CommonString.KEY_STORE_CD + "='" + store_cd + "'",null);
            dbcursor = db.rawQuery("Select * from ADDTIONAL_INFO " + "where STORE_ID='" + store_cd + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                int count = dbcursor.getInt(0);
                dbcursor.close();

                if (count > 0) {
                    filled = true;
                } else {
                    filled = false;
                }
            }
        } catch (Exception e) {
            Log.d("Exception isempty", e.toString());
            return filled;
        }
        return filled;
    }








	/*public void InsertMappingPromotionPostData(MappingPromotionpostgetterSetter data){
		db.delete(CommonString.TABLE_MAPPING_PROMOTIONPOST, null, null);
		ContentValues values = new ContentValues();
		try {
			for(int i=0; i<data.getSKU_CD().size();i++){

				values.put(CommonString.KEY_STORE_CD, data.getSTORE_CD().get(i));
				values.put(CommonString.KEY_SKU_CD,
						data.getSKU_CD().get(i));
				values.put(CommonString.KEY_PROMOTION, data.getPROMOTION().get(i));
				values.put(CommonString.KEY_CATEGORY_SEQUENCE, data.getCATEGORY_SEQUENCE().get(i));
				values.put(CommonString.KEY_BRAND_SEQUENCE, data.getBRAND_SEQUENCE().get(i));
				values.put(CommonString.KEY_SKU, data.getSKU().get(i));
				values.put(CommonString.Key_BRAND, data.getBRAND().get(i));
				values.put(CommonString.KEY_BRAND_CD, data.getBRAND_CD().get(i));
				values.put(CommonString.KEY_DESCRIPTION, data.getDescription().get(i));


				db.insert(CommonString.TABLE_MAPPING_PROMOTIONPOST, null, values);

			}
		} catch (Exception ex) {
			Log.d("Database Exception while Insert sku post data ",
					ex.getMessage());
		}
	}
*/


    @SuppressLint("LongLogTag")
    public void InsertMappingPromotionPostData(MappingPromotionpostgetterSetter data) {
        db.delete(CommonString.TABLE_PROMOTION_MAPPING, null, null);
        ContentValues values = new ContentValues();
        try {
            for (int i = 0; i < data.getSKU_CD().size(); i++) {

                values.put(CommonString.KEY_ID1, data.getID().get(i));
                values.put(CommonString.KEY_STORE_CD, data.getSTORE_CD().get(i));
                values.put(CommonString.KEY_SKU_CD, data.getSKU_CD().get(i));
                values.put(CommonString.KEY_PROMOTION, data.getPROMOTION().get(i));
                values.put(CommonString.KEY_SKU_SEQUENCE, data.getSKU_SEQUENCE().get(i));

				/*values.put(CommonString.KEY_BRAND_SEQUENCE, data.getBRAND_SEQUENCE().get(i));
				values.put(CommonString.KEY_SKU, data.getSKU().get(i));
				values.put(CommonString.Key_BRAND, data.getBRAND().get(i));
				values.put(CommonString.KEY_BRAND_CD, data.getBRAND_CD().get(i));
				values.put(CommonString.KEY_DESCRIPTION, data.getDescription().get(i));
				*/

                db.insert(CommonString.TABLE_PROMOTION_MAPPING, null, values);

            }
        } catch (Exception ex) {
            Log.d("Database Exception while Insert sku post data ",
                    ex.toString());
        }
    }

    public void deletepromotionMapping() {
        //  db.delete(CommonString.TABLE_MAPPING_PROMOTIONPOST, null, null);TABLE_PROMOTION_MAPPING
        db.delete(CommonString.TABLE_PROMOTION_MAPPING, null, null);
    }


    public int getCoverageData1() {
        Cursor dbcursor = null;
        int count = 0;
        try {

            dbcursor = db.rawQuery("SELECT  * from "
                    + CommonString.TABLE_COVERAGE_DATA + " where "
                    + CommonString.KEY_STATUS + " = 'N' ", null);

            count = dbcursor.getCount();

        } catch (Exception e) {
            // TODO: handle exception
            Log.d("Exception when fetching Coverage Data!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return count;
        }

        return count;

    }

    // getCoverageData
    public ArrayList<CoverageBean> getCoverageData(String visitdate, String store_id) {

        ArrayList<CoverageBean> list = new ArrayList<CoverageBean>();
        Cursor dbcursor = null;
        try {

            if (visitdate == null) {

                dbcursor = db.rawQuery("SELECT  * from " + CommonString.TABLE_COVERAGE_DATA, null);

            } else if (store_id == null) {

                dbcursor = db.rawQuery("SELECT  * from " + CommonString.TABLE_COVERAGE_DATA + " where " + CommonString.KEY_VISIT_DATE + "='" + visitdate + "'", null);
            } else {

                dbcursor = db.rawQuery("SELECT  * from "
                        + CommonString.TABLE_COVERAGE_DATA + " where "
                        + CommonString.KEY_VISIT_DATE + "='" + visitdate
                        + "' AND " + CommonString.KEY_STORE_ID + "='"
                        + store_id + "'", null);
            }

            if (dbcursor != null) {

                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    CoverageBean sb = new CoverageBean();
                    sb.setMID((dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_ID))));
                    sb.setStoreId(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_STORE_ID)));
                    sb.setUserId((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_USER_ID))));
                    sb.setInTime(((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IN_TIME)))));
                    sb.setOutTime(((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_OUT_TIME)))));
                    sb.setVisitDate((((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_VISIT_DATE))))));
                    sb.setLatitude(((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_LATITUDE)))));
                    sb.setLongitude(((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_LONGITUDE)))));
                    sb.setReasonid(((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_REASON_ID)))));
                    sb.setReason(((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_REASON)))));
                    sb.setStatus((((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_STATUS))))));

                    sb.setRemark(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_REMARKS)));

                    sb.setRights(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_RIGHTS)));


                    sb.setImage((((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE))))));
                    sb.setOutlook_Remark((((dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_OUTLOOK_REMARK))))));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            e.printStackTrace();
			/*Log.d("Exception when fetching Coverage Data!!!!!!!!!!!!!!!!!!!!!",
					e.getMessage());
*/
        }

        return list;

    }

    public void deleteCoverageData(String date) {

        try {
            db.delete(CommonString.TABLE_COVERAGE_DATA, CommonString.KEY_DATE
                    + "=" + date, null);

        } catch (Exception e) {

        }
    }


    public void deleteCallTracker(String store_id) {

        try {
            db.delete(CommonString.TABLE_CALLS_TRACKER_DATA_12To3, CommonString.key_STORE_CD
                    + "=" + store_id, null);
        } catch (Exception e) {
            Log.e("", "");
        }

    }

    public void updateStoreStatusOnCheckout(String storeid, String visitdate, String status) {
        try {
            ContentValues values = new ContentValues();
            values.put(CommonString.KEY_CHECKOUT_STATUS, status);
            db.update(CommonString.TABLE_STORE_DETAIL, values,
                    CommonString.KEY_STORE_ID + "='" + storeid + "' AND " +
                            CommonString.KEY_VISIT_DATE + "='" + visitdate + "'", null);
        } catch (Exception e) {

        }
    }
    public void updateStatusCheckout_(String storeid, String visitdate, String status) {
        try {
            ContentValues values = new ContentValues();
            values.put(CommonString.KEY_STATUS, status);
            db.update(CommonString.TABLE_COVERAGE_DATA, values, CommonString.KEY_STORE_ID + "='" + storeid + "' AND " + CommonString.KEY_VISIT_DATE + "='" + visitdate + "'", null);
        } catch (Exception e) {

        }
    }

    public void updateStatusCheckout(String storeid, String visitdate, String status) {
        try {
            ContentValues values = new ContentValues();
            values.put(CommonString.KEY_STATUS, status);
            db.update(CommonString.TABLE_COVERAGE_DATA, values, CommonString.KEY_STORE_ID + "='" + storeid + "' AND " + CommonString.KEY_VISIT_DATE + "='" + visitdate + "'", null);
        } catch (Exception e) {

        }
    }

    public void updateStoreStatusOnLeave(String storeid, String visitdate, String status) {
        try {
            ContentValues values = new ContentValues();
            values.put(CommonString.KEY_STATUS, status);
            db.update(CommonString.TABLE_STORE_DETAIL, values, CommonString.KEY_STORE_ID + "='" + storeid + "' AND " + CommonString.KEY_VISIT_DATE + "='" + visitdate + "'", null);
        } catch (Exception e) {

        }
    }

    public int CheckMid(String currdate, String storeid) {

        Cursor dbcursor = null;
        int mid = 0;
        try {
            dbcursor = db.rawQuery("SELECT  * from "
                    + CommonString.TABLE_COVERAGE_DATA + "  WHERE "
                    + CommonString.KEY_VISIT_DATE + " = '" + currdate
                    + "' AND " + CommonString.KEY_STORE_ID + " ='" + storeid
                    + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();

                mid = dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_ID));

                dbcursor.close();

            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
        }

        return mid;
    }

    public String CheckMidWithStatus(String currdate, String storeid) {
        Cursor dbcursor = null;
        String status = "";
        try {
            dbcursor = db.rawQuery("SELECT  * from "
                    + CommonString.TABLE_COVERAGE_DATA + "  WHERE "
                    + CommonString.KEY_VISIT_DATE + " = '" + currdate
                    + "' AND " + CommonString.KEY_STORE_ID + " ='" + storeid
                    + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();

                status = dbcursor.getString(dbcursor
                        .getColumnIndexOrThrow(CommonString.KEY_STATUS));

                dbcursor.close();

            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
        }

        return status;
    }

    public ArrayList<OpeningStockBean> getOpeningDataUpload(String storeId) {
        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");
        ArrayList<OpeningStockBean> list = new ArrayList<OpeningStockBean>();
        Cursor dbcursor = null;

        try {

            dbcursor = db
                    .rawQuery(
                            "SELECT SD.SKU, SD.SKU_CD, SD.SHELF, SD.FACING, SD.OPENING_STOCK, SD.PRICING, SD.PRICE_SIGNOGE, SD.SIGNOGE_CORRECTED, SD.EXPIRING_STOCK" +
                                    ", SD.POG_ADHERENCE, SD.POG_CORRECTED, SD.SHELF_FM_BOTTOM , SD.EXPIRING_DATE ,CD.BRAND_CD FROM openingHeader_data CD INNER JOIN Opening_Stock_data " +
                                    "SD ON CD._id=SD.COMMONID WHERE CD.STORE_CD= '" + storeId + "' AND SD.LISTED_CHECKBOX = '1' ", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    OpeningStockBean sb = new OpeningStockBean();

                    sb.setShelf(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_SHELF)));
                    sb.setFacing(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_FACING)));

                    sb.setExpiring_date(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_EXPIRING_DATE)));

                    sb.setOpeningStock(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_OPENINGSTOCK)));

                    sb.setPricing(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_PRICING)));

                    sb.setPrice_sign(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_PRICE_SIGNOGE)));

                    sb.setSign_corrected(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_SIGNOGE_CORRECTED)));

                    sb.setExpriring_stock(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_EXPIRING_STOCK)));

                    sb.setPog_adherence(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_POG_ADHERENCE)));

                    sb.setPog_corrected(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_POG_CORRECTED)));

                    sb.setShelf_fm_bottom(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_SHELF_FM_BOTTOM)));

                    sb.setSKU(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_SKU)));

                    sb.setSKU_CD(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_SKU_CD)));


                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return list;
    }


    public ArrayList<OpeningStockBean> getOpeningDataForCheck(String sku_cd, String store_cd) {
        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");
        ArrayList<OpeningStockBean> list = new ArrayList<OpeningStockBean>();
        Cursor dbcursor = null;

        try {

            dbcursor = db
                    .rawQuery(
                            "SELECT SD.OPENING_STOCK, SD.LISTED_CHECKBOX FROM openingHeader_data CD INNER JOIN " +
                                    "Opening_Stock_data SD ON CD._id=SD.COMMONID WHERE SD.SKU_CD= '"
                                    + sku_cd + "' AND SD.STORE_CD = '" + store_cd + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    OpeningStockBean sb = new OpeningStockBean();
                    sb.setOpeningStock(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_OPENINGSTOCK)));
                    sb.setListedFlag(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_LISTED_CHECKBOX)));
                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return list;
    }


    public ArrayList<MidDayRecievedStockBean> getMidDayStockForCheck(String sku_cd) {

        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");
        ArrayList<MidDayRecievedStockBean> list = new ArrayList<MidDayRecievedStockBean>();
        Cursor dbcursor = null;

        try {

            dbcursor = db
                    .rawQuery(
                            "SELECT SD.STOCK " +
                                    "FROM StockRecievedHeader_data CD INNER JOIN MidDayStock_Recieved_Data SD ON CD._id=SD.COMMONID WHERE SD.SKU_CD= '"
                                    + sku_cd + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MidDayRecievedStockBean sb = new MidDayRecievedStockBean();

                    sb.setStock(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_STOCK)));


                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return list;

    }


    public ArrayList<PromotionTrackingBean> getPromotionDataUpload(
            String storeId) {

        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");
        ArrayList<PromotionTrackingBean> list = new ArrayList<PromotionTrackingBean>();
        Cursor dbcursor = null;

        try {

            dbcursor = db
                    .rawQuery(
                            "SELECT SD.SKU,SD.SKU_CD,SD.PROMOTION_NAME,SD.PROMOTION_AVAIL,SD.REMARKS,CD.BRAND_CD, SD.REASON_ID , SD.IMAGE " +
                                    "FROM INSERT_HEADER_PROMOTION_DATA CD INNER JOIN INSERT_PROMOTION_DATA SD ON CD._id=SD.COMMONID WHERE CD.STORE_CD= '"
                                    + storeId + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    PromotionTrackingBean sb = new PromotionTrackingBean();

                    sb.setSku_name(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_SKU)));

                    sb.setSku_cd(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_SKU_CD)));

                    sb.setPromotion_name(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_PROMOTION_NAME)));

                    sb.setPromotion_avail(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_PROMOTION_AVAIL)));

                    sb.setRemark(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_REMARKS)));


                    sb.setReasonid(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_REASON_ID)));


                    sb.setImage(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE)));


                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return list;
    }


    public ArrayList<MappingPromotionpostgetterSetter> getPromotionDefaltDataUpload(String storeId) {

        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");
        ArrayList<MappingPromotionpostgetterSetter> list = new ArrayList<MappingPromotionpostgetterSetter>();
        Cursor dbcursor = null;

        try {
            dbcursor = db.rawQuery("SELECT * FROM INSERT_PROMOTION_DATA WHERE STORE_CD= '" + storeId + "'", null);

            // dbcursor = db.rawQuery("SELECT SD.PROMOTION,SD.PROMOTION_AVAIL,SD.REMARKS,SD.REASON_ID,SD.IMAGE,SD.PROMOTION_CD,HP.SKU,HP.SKU_CD FROM INSERT_PROMOTION_DATA SD INNER JOIN INSERT_HEADER_PROMOTION_DATA  HP ON SD.COMMONID=HP._id WHERE STORE_CD= '" + storeId + "'", null);


                      /*  dbcursor = db.rawQuery("SELECT SD.SKU,SD.SKU_CD,SD.PROMOTION_NAME,SD.PROMOTION_AVAIL,SD.REMARKS,CD.BRAND_CD, SD.REASON_ID , SD.IMAGE " +
                                    "FROM INSERT_HEADER_PROMOTION_DATA CD INNER JOIN INSERT_PROMOTION_DATA SD ON CD._id=SD.COMMONID WHERE CD.STORE_CD= '" + storeId + "'", null);*/

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MappingPromotionpostgetterSetter sb = new MappingPromotionpostgetterSetter();
                    sb.setSKU(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_SKU)));
                    sb.setSKU_CD(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_SKU_CD)));
                    sb.setPROMOTION(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_PROMOTION)));
                    sb.setPromotion_avail(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_PROMOTION_AVAIL)));
                    sb.setRemark(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_REMARKS)));
                    sb.setReasonid(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_REASON_ID)));
                    sb.setImage(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE)));
                    sb.setID(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_PROMOTION_CD)));
                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return list;
    }


    public ArrayList<AssetTrackerBean> getAssetDataUpload(String storeId) {
        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");
        ArrayList<AssetTrackerBean> list = new ArrayList<AssetTrackerBean>();
        Cursor dbcursor = null;

        try {

            dbcursor = db
                    .rawQuery("SELECT SD.ASSET, SD.ASSET_CD, SD.PROMOTION_AVAIL, SD.REMARKS, SD.actualCount, SD.REASON_ID , SD.IMAGE, CD.BRAND_CD " + "FROM INSERT_HEADER_ASSET_DATA CD INNER JOIN INSERT_ASSET_DATA SD ON CD._id=SD.COMMONID WHERE CD.STORE_CD= '" + storeId + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    AssetTrackerBean sb = new AssetTrackerBean();
                    sb.setAsset_name(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_ASSET_NAME)));
                    sb.setBrand_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_BRAND_CD)));
                    sb.setAsset_cd(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_ASSET_CD)));
                    sb.setActualCount(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.key_actualCount)));
                    sb.setAsset_avail(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_ASSET_AVAIL)));
                    sb.setRemark(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_REMARKS)));
                    sb.setReasonid(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_REASON_ID)));
                    sb.setImage(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_IMAGE)));
                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records !!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }
        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return list;
    }

    public ArrayList<ClosingStockBean> getClosingStockUpload(String storeId) {
        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");
        ArrayList<ClosingStockBean> list = new ArrayList<ClosingStockBean>();
        Cursor dbcursor = null;

        try {

            dbcursor = db
                    .rawQuery(
                            "SELECT SD.SKU,SD.SKU_CD,SD.STOCK,CD.BRAND_CD " +
                                    "FROM ClosingStockHeader_data CD INNER JOIN ClosingStock_data SD ON CD._id=SD.COMMONID " +
                                    "WHERE CD.STORE_CD= '"
                                    + storeId + "' AND SD.LISTED_CHECKBOX = '1'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    ClosingStockBean sb = new ClosingStockBean();

                    sb.setSKU(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_SKU)));

                    sb.setSKU_CD(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_SKU_CD)));

                    sb.setStock(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_STOCK)));


                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return list;
    }


    public ArrayList<LunchBean> getLunchData(String storeId) {
        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");
        ArrayList<LunchBean> list = new ArrayList<LunchBean>();
        Cursor dbcursor = null;
        try {

            dbcursor = db.rawQuery("SELECT * FROM LUNCH_TIME WHERE STORE_CD = " + storeId, null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    LunchBean sb = new LunchBean();

                    sb.setFromTime(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_FROM_TIME)));

                    sb.setToTime(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_TO_TIME)));

                    sb.setUserid(dbcursor.getString(dbcursor.getColumnIndexOrThrow(CommonString.KEY_USER_ID)));

                    sb.setMid(dbcursor.getInt(dbcursor.getColumnIndexOrThrow(CommonString.KEY_MID)));


                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return list;
    }


    public ArrayList<ClosingStockBean> getLunchTimeData(String storeId) {
        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");
        ArrayList<ClosingStockBean> list = new ArrayList<ClosingStockBean>();
        Cursor dbcursor = null;

        try {

            dbcursor = db
                    .rawQuery(
                            "SELECT SD.SKU,SD.SKU_CD,SD.STOCK,CD.BRAND_CD " +
                                    "FROM ClosingStockHeader_data CD INNER JOIN ClosingStock_data SD ON CD._id=SD.COMMONID WHERE CD.STORE_CD= '"
                                    + storeId + "'", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    ClosingStockBean sb = new ClosingStockBean();

                    sb.setSKU(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_SKU)));

                    sb.setSKU_CD(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_SKU_CD)));

                    sb.setStock(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_STOCK)));


                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return list;
    }

    public ArrayList<MidDayRecievedStockBean> getStockUpload(String storeId) {
        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");
        ArrayList<MidDayRecievedStockBean> list = new ArrayList<MidDayRecievedStockBean>();
        Cursor dbcursor = null;

        try {

            dbcursor = db
                    .rawQuery(
                            "SELECT SD.SKU,SD.SKU_CD,SD.STOCK,CD.BRAND_CD " +
                                    "FROM StockRecievedHeader_data CD INNER JOIN MidDayStock_Recieved_Data SD ON CD._id=SD.COMMONID " +
                                    "WHERE CD.STORE_CD= '" + storeId + "' AND LISTED_CHECKBOX = '1' ", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    MidDayRecievedStockBean sb = new MidDayRecievedStockBean();

                    sb.setSKU(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_SKU)));

                    sb.setSKU_CD(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_SKU_CD)));

                    sb.setStock(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_STOCK)));


                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return list;
    }


    public ArrayList<PerformanceDataBean> getPerformanceData() {
        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");
        ArrayList<PerformanceDataBean> list = new ArrayList<PerformanceDataBean>();
        Cursor dbcursor = null;

        try {

            dbcursor = db
                    .rawQuery(
                            "SELECT * FROM PERFORMANCE", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    PerformanceDataBean sb = new PerformanceDataBean();

                    sb.setMonthly_target(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_MONTHLY_TARGET)));

                    sb.setDaily_target(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_DAILY_TARGET)));

                    sb.setAchieved(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_ACHIEVED)));

                    sb.setPending(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow(CommonString.KEY_PENDING)));

                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return list;
    }

    public ArrayList<OpeningStockBean> getFocusStatus(String sku_cd, String store_cd) {
        Log.d("Fetching the focus status!!!!!!!!", "...");
        ArrayList<OpeningStockBean> result = new ArrayList<OpeningStockBean>();
        Cursor cursor = null;
        try {
            //get column sku_cd by rishika on19 september
            cursor = db.rawQuery("SELECT SKU_CD,FOCUS,STOCK_ALLOW,PRICING_ALLOW,FACING_ALLOW, ALLOW_EXPRING_STOCK,ALLOW_POG,ALLOW_SHELF,ALLOW_SIGNAGE FROM Availability_MappingPOST_DATA WHERE SKU_CD =" + sku_cd + " AND STORE_CD =" + store_cd, null);

            if (cursor != null) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    OpeningStockBean sb = new OpeningStockBean();

                    sb.setSKU_CD(cursor.getString(cursor
                            .getColumnIndexOrThrow(CommonString.KEY_SKU_CD)));  //by Rishika on 19th sep

                    sb.setFocus(cursor.getString(cursor
                            .getColumnIndexOrThrow(CommonString.KEY_FOCUS)));

                    sb.setStock_allow(cursor.getString(cursor
                            .getColumnIndexOrThrow(CommonString.KEY_STOCK_ALLOW)));

                    sb.setPricing_allow(cursor.getString(cursor
                            .getColumnIndexOrThrow(CommonString.KEY_PRICING_ALLOW)));

                    sb.setFACING_ALLOW(cursor.getString(cursor
                            .getColumnIndexOrThrow(CommonString.KEY_FACING_ALLOW)));

                    sb.setAllow_expiring_stock(cursor.getString(cursor
                            .getColumnIndexOrThrow(CommonString.KEY_ALLOW_EXPRING_STOCK)));

                    sb.setAllow_pog(cursor.getString(cursor.getColumnIndexOrThrow(CommonString.KEY_ALLOW_POG)));

                    sb.setAllow_shelf(cursor.getString(cursor.getColumnIndexOrThrow(CommonString.KEY_ALLOW_SHELF)));

                    sb.setAllow_signage(cursor.getString(cursor.getColumnIndexOrThrow(CommonString.KEY_ALLOW_SIGNAGE)));

                    result.add(sb);
                    cursor.moveToNext();
                }
                cursor.close();
                return result;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!",
                    e.toString());
        }

        return result;
    }

    public ArrayList<OpeningStockBean> getPlanogramData(String brand_CD,
                                                        String store_id) {

        Log.d("Fetching the focus status!!!!!!!!", "...");
        ArrayList<OpeningStockBean> result = new ArrayList<OpeningStockBean>();
        Cursor cursor = null;
        try {

            cursor = db.rawQuery("SELECT IMAGE FROM PLANOGRAM_DETAILS WHERE BRAND =" + brand_CD + " AND STORE_CD =" + store_id, null);

            if (cursor != null) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    OpeningStockBean sb = new OpeningStockBean();

                    sb.setImage_url(cursor.getString(cursor
                            .getColumnIndexOrThrow(CommonString.Key_Image)));

                    result.add(sb);
                    cursor.moveToNext();
                }
                cursor.close();
                return result;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!",
                    e.toString());
        }

        return result;

    }


    public ArrayList<AssetTrackerBean> getAssetReasonData() {


        Log.d("Fetching the focus status!!!!!!!!", "...");
        ArrayList<AssetTrackerBean> result = new ArrayList<AssetTrackerBean>();
        Cursor cursor = null;
        try {

            cursor = db.rawQuery("SELECT * FROM " + CommonString.TABLE_NON_ASSET_REASON_MASTER, null);

            if (cursor != null) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    AssetTrackerBean sb = new AssetTrackerBean();

                    sb.setReasonid(cursor.getString(cursor
                            .getColumnIndexOrThrow(CommonString.KEY_REASON_ID)));

                    sb.setReason(cursor.getString(cursor
                            .getColumnIndexOrThrow(CommonString.KEY_REASON)));


                    result.add(sb);
                    cursor.moveToNext();
                }
                cursor.close();
                return result;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!",
                    e.toString());
        }

        return result;
    }


    public ArrayList<PromotionTrackingBean> getPromotionReasonData() {


        Log.d("Fetching the focus status!!!!!!!!", "...");
        ArrayList<PromotionTrackingBean> result = new ArrayList<PromotionTrackingBean>();
        Cursor cursor = null;
        try {

            cursor = db.rawQuery("SELECT * FROM " + CommonString.TABLE_NON_PROMOTION_REASON_MASTER, null);

            if (cursor != null) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    PromotionTrackingBean sb = new PromotionTrackingBean();

                    sb.setReasonid(cursor.getString(cursor
                            .getColumnIndexOrThrow(CommonString.KEY_REASON_ID)));

                    sb.setReason(cursor.getString(cursor
                            .getColumnIndexOrThrow(CommonString.KEY_REASON)));


                    result.add(sb);
                    cursor.moveToNext();
                }
                cursor.close();
                return result;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!",
                    e.toString());
        }

        return result;
    }


    public ArrayList<competitionbeans> getCategoryData() {


        Log.d("Fetching the focus status!!!!!!!!", "...");
        ArrayList<competitionbeans> result = new ArrayList<competitionbeans>();
        Cursor cursor = null;
        try {

            cursor = db.rawQuery("SELECT * FROM " + CommonString.TABLE_CATEGORY_MASTER, null);
            // cursor = db.rawQuery("SELECT * FROM " + CommonString.TABLE_INSERT_COMPETITION_DATA, null);
            if (cursor != null) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    competitionbeans sb = new competitionbeans();
                    sb.setCategory_id(cursor.getString(cursor.getColumnIndexOrThrow(CommonString.KEY_CATEGORY_CD)));
                    sb.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(CommonString.KEY_CATEGORY)));
                    sb.setQuantity("");
                    result.add(sb);
                    cursor.moveToNext();
                }
                cursor.close();
                return result;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!",
                    e.toString());
        }

        return result;


    }

    public void InsertCompetitionData(long mid, String store_id,
                                      ArrayList<competitionbeans> categorylist) {

        ContentValues values = new ContentValues();

        String quantity = "";
        String image = "";
        try {

            for (int i = 0; i < categorylist.size(); i++) {
                values.put(CommonString.MID, mid);
                values.put(CommonString.KEY_STORE_CD, store_id);

                values.put(CommonString.KEY_CATEGORY_CD, categorylist.get(i).getCategory_id());
                values.put(CommonString.KEY_CATEGORY, categorylist.get(i).getCategory());

                values.put(CommonString.KEY_QUANTITY, categorylist.get(i).getQuantity());

                db.insert(CommonString.TABLE_INSERT_COMPETITION_DATA, null, values);
            }
        } catch (Exception ex) {
            Log.d("Database Exception while Insert Posm Master Data ",
                    ex.toString());
        }

    }


    public ArrayList<Posmbean> getDisplayList() {

        Log.d("FetchingStoredata--------------->Start<------------",
                "------------------");
        ArrayList<Posmbean> list = new ArrayList<Posmbean>();
        Cursor dbcursor = null;

        try {


            dbcursor = db.rawQuery("SELECT * FROM " + CommonString.TABLE_POSM_MASTER,
                    null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    Posmbean sb = new Posmbean();

                    sb.setPOSM_CD(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("POSM_ID")));

                    sb.setPOSM((dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("POSM_NAME"))));


                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception when fetching Records!!!!!!!!!!!!!!!!!!!!!",
                    e.toString());
            return list;
        }

        Log.d("FetchingStoredat---------------------->Stop<-----------",
                "-------------------");
        return list;

    }


    public void InsertAdditionalInfo(Posmbean data, String store_id) {
        ContentValues values = new ContentValues();

        try {

            values.put(CommonString.KEY_STORE_ID, store_id);
            values.put(CommonString.KEY_CATEGORY_ID, data.getCategory_id());
            values.put(CommonString.KEY_CATEGORY, data.getCategory());
            values.put(CommonString.Key_POSM, data.getPOSM());
            values.put(CommonString.KEY_POSM_ID, data.getPOSM_CD());


            values.put(CommonString.KEY_IMAGE, data.getImage());


            db.insert(CommonString.TABLE_INSERT_ADDTIONAL_DETAILS, null, values);


        } catch (Exception ex) {
            Log.d("Database Exception while Insert Fabricator master data ", ex.toString());
        }

    }

    public void deleteProductEntry(String id) {
        try {
            db.delete(CommonString.TABLE_INSERT_ADDTIONAL_DETAILS, CommonString.KEY_ID + "='" + id + "'", null);
        } catch (Exception e) {
            System.out.println("" + e);
        }

    }


    public ArrayList<Posmbean> getProductEntryDetail(String store_id) {
        Cursor cursordata = null;
        ArrayList<Posmbean> productData = new ArrayList<Posmbean>();
        try {
            cursordata = db.rawQuery("SELECT  * from " + CommonString.TABLE_INSERT_ADDTIONAL_DETAILS + " where " + CommonString.KEY_STORE_ID + "='" + store_id + "'", null);
            if (cursordata != null) {
                cursordata.moveToFirst();
                while (!cursordata.isAfterLast()) {
                    Posmbean sb = new Posmbean();
                    sb.setKey_id(cursordata.getString(cursordata.getColumnIndexOrThrow(CommonString.KEY_ID)));
                    sb.setCategory_id(cursordata.getString(cursordata.getColumnIndexOrThrow(CommonString.KEY_CATEGORY_ID)));
                    sb.setCategory(cursordata.getString(cursordata.getColumnIndexOrThrow(CommonString.KEY_CATEGORY)));
                    sb.setPOSM(cursordata.getString(cursordata.getColumnIndexOrThrow(CommonString.Key_POSM)));
                    sb.setPOSM_CD(cursordata.getString(cursordata.getColumnIndexOrThrow(CommonString.KEY_POSM_ID)));
                    sb.setImage(cursordata.getString(cursordata.getColumnIndexOrThrow(CommonString.KEY_IMAGE)));
                    productData.add(sb);
                    cursordata.moveToNext();
                }
                cursordata.close();

            }


        } catch (Exception ex) {
            Log.d("Database Exception while Insert Closes Data ",
                    ex.toString());
        }
        return productData;

    }


    public ArrayList<MidDayRecievedStockBean> getStockListedData(String store_id) {
        Cursor cursordata = null;
        ArrayList<MidDayRecievedStockBean> productData = new ArrayList<MidDayRecievedStockBean>();
        try {
            cursordata = db.rawQuery("SELECT  * from " + CommonString.TABLE_CLOSING_STOCK_LISTED_DATA + " where " + CommonString.KEY_STORE_CD + "='" + store_id + "'", null);

            if (cursordata != null) {
                cursordata.moveToFirst();
                while (!cursordata.isAfterLast()) {
                    MidDayRecievedStockBean sb = new MidDayRecievedStockBean();
                    sb.setIsListed(cursordata.getString(cursordata.getColumnIndexOrThrow(CommonString.KEY_LISTED_TOGGLE)));
                    sb.setSKU(cursordata.getString(cursordata.getColumnIndexOrThrow(CommonString.KEY_SKU)));
                    sb.setSKU_CD(cursordata.getString(cursordata.getColumnIndexOrThrow(CommonString.KEY_SKU_CD)));
                    sb.setBRAND_CD(cursordata.getString(cursordata.getColumnIndexOrThrow(CommonString.KEY_BRAND_CD)));
                    productData.add(sb);
                    cursordata.moveToNext();
                }
                cursordata.close();

            }

        } catch (Exception ex) {
            Log.d("Database Exception while Insert Closes Data ",
                    ex.toString());
        }
        return productData;

    }


}
