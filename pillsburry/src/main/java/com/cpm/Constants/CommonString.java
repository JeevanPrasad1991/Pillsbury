package com.cpm.Constants;

import android.os.Environment;

public class CommonString {

	// preference keys
	public static final String KEY_UPLOADXML_DATA = "UPLOADXMLDATA";
	public static final String KEY_USERNAME = "username";

	public static final String KEY_PASSWORD = "password";
	public static final String KEY_REMEMBER = "remember";
	public static final String KEY_RIGHT_NAME = "right_name";
	public static final String METHOD_NAME_UNIVERSAL_DOWNLOAD = "DownloadUniversal";

	public static final String KEY_PATH = "path";
	public static final String KEY_VERSION = "version";
	public static final String KEY_STORE_IN_TIME = "Store_in_time";
	public static final String KEY_STOREVISITED = "STORE_VISITED";
	public static final String KEY_STOREVISITED_STATUS = "STORE_VISITED_STATUS";
	public static final String METHOD_Get_DR_POSM_IMAGES = "GetImageWithFolderNameImages";
	public static final String SOAP_ACTION_Get_DR_POSM_IMAGES = "http://tempuri.org/"+ METHOD_Get_DR_POSM_IMAGES;
	public static final String FILE_PATH = Environment.getExternalStorageDirectory() + "/Pillsbury_Images/";
	// public static final String KEY_APPVERSION = "1.1";

	public static final String KEY_DATE = "date";
	public static final String MID = "MID";
	
	//public static final String KEY_P = "P";
	public static final String KEY_C = "C";
	public static final String KEY_D = "D";
	public static final String KEY_U = "U";
	public static final String KEY_N = "N";
	public static final String KEY_L = "Leave";
	public static final String KEY_INVALID = "INVALID";
	public static final String STORE_STATUS_LEAVE = "L";
	public static final String KEY_VALID = "Valid";

	public static final String KEY_pepsi = "pepsi";
	public static final String KEY_other = "other";

	// /creating table for Store list activity
	public static final String Key_Store_id = "store_id";
	public static final String Key_Store_Ranking = "store_ranking";
	public static final String Key_Execution_img1 = "img_path1";

	public static final String Key_Mid = "Mid";
	public static final String Key_Comments = "Comments";
	public static final String METHOD_UPLOAD_STOCK_DATA = "UPLOAD_DR_STOCK_RECIEVEDPOST";

	public static final String TABLE_PEPSICO_ENTRY = "PEPSICO_STORE_ENTRY";

	public static final String KEY_ID = "_id";
	
	public static final String KEY_REASON_ID = "REASON_ID";
	public static final String KEY_REASON = "REASON";

	// webservice constants
	
	//"[{\"STORE_CD\":\"3\",\"STORENAME\":\"Lynx Technologies\",\"CITY\":\"Chennai\",\"
	//+ ""TAM\":\"5000.00\",\"SALE\":\"3150.00\",\"SHARE\":\"63\",\"TERRITORY\":\"Blue\"},
	
	
	public static final String KEY_SALE= "SALE";
	public static final String KEY_TERRITORY = "TERRITORY";
	public static final String KEY_SHARE = "SHARE";
	public static final String KEY_TAM = "TAM";
	public static final String KEY_SUCCESS = "Success";
	public static final String KEY_FAILURE = "Failure";
	public static final String KEY_FALSE = "False";
	public static final String KEY_BRAND_NAME = "BRAND_NAME";
	public static final String KEY_CHANGED = "Changed";
	public static final String KEY_STORE_TAM = "TAM";
	public static final String KEY_CHECKOUT_STATUS = "CHECKOUT_STATUS";
	public static final String KEY_SERVICE_STATUS = "SERVICE_STATUS";
	public static final String KEY_UPLOADCHECKOUT_STATUS = "UPLOADCHECKOUT_STATUS";
	public static final String KEY_NO_DATA = "No Data";
	public static final String NAMESPACE = "http://tempuri.org/";

	public static final String KEY_PROMO = "PROMO";
	public static final String KEY_PROMO_TYPE = "PROMO_TYPE";
	public static final String KEY_PROMO_ID = "PROMO_ID";
	public static final String KEY_PROMO_TYPE_ID = "PROMO_TYPE_ID";

	public static final String KEY_ENTRY = "ENTRY";
	public static final String KEY_IMAGE = "IMAGE";
	public static final String KEY_OUTLOOK_REMARK= "OUTLOOK_REMARK";

	public static final String KEY_NETWORK_STATUS = "NETWORK_STATUS";
	public static final String KEY_CURRENT_TIME = "CURRENT_TIME";

	//public static final String URL2 = "http://10.200.20.133/WdRestWCF/WDRestWCFService.svc/";
//	public static final String URL = "http://10.200.20.226/pillsburytesting/WCEService/PilsburyRestService.svc/";
//	public static final String URL = "http://10.200.20.226/pillsburytesting/WCEService/PilsburyRestService.svc/";
	
	public static final String URL = "http://gm.parinaam.in/WCEService/PilsburyRestService.svc/";
	
	public static final String URL_IMAGE = "http://gm.parinaam.in/WCEService/Pillsbury.asmx";

	 
	public static final String METHOD_UPLOAD_IMAGE = "AddImage";

	public static final String URL1 = "http://10.200.20.133/PepsicoService/PepsicoWebService.asmx";

	public static final String METHOD_LOGIN = "LoginDetailPOST";

	public static final String METHOD_NAME_JCP = "DowloadJcpPOST";
	public static final String PROMOTION_MAPING="PROMOTION_MAPPING";
	public static final String METHOD_NAME_POSM = "DownloadPosmMasters";
	public static final String METHOD_NAME_DOWNLOAD_DESGN = "DownloadDesignations";
	public static final String METHOD_NAME_DOWNLOAD_JOBLEVELS = "DownloadJobLevels";
	public static final String METHOD_NAME_DOWNLOAD_STOREKEY_CONTACTDETAILS = "StoreKeyContactDetails";
	public static final String METHOD_NAME_DOWNLOAD_DOWNLOAD_TRANING_TOPICS = "DownloadTrainingTopics";

	public static final String METHOD_GEO_TAG = "STORES_BY_MAPPING";
	public static final String TABLE_INSERT_GEO_TAG = "INSERT_GEO_TAG_DATA";

	// Upload Coverage
	public static final String METHOD_UPLOAD_DR_STORE_COVERAGE = "UPLOAD_DR_STORE_COVERAGEPOST";
	public static final String METHOD_UPLOAD_CLOSING_DATA = "UPLOAD_DR_STOCK_CLOSINGPOST";
	public static final String METHOD_LUNCH_TIME_DATA = "UploadLunchtimePOST";

//	public static final String METHOD_UPLOAD_OPENING_DATA = "UPLOAD_Dr_StockPOST";
	public static final String METHOD_UPLOAD_OPENING_DATA = "UPLOAD_Dr_Stock_NewPOST";
	
	public static final String METHOD_UPLOAD_XML = "UploadXmlData";
	
	
	public static final String METHOD_UPLOAD_CALLS_DATA = "UPLOAD_DR_CALLSPOST";
	public static final String METHOD_UPLOAD_CALLS_DATA_INSTANT = "UPloadCallTrackerPOST";
	public static final String METHOD_UPLOAD_ASSET_DATA = "UPLOAD_DR_ASSET_EXECUTION_NEWPOST";

	public static final String METHOD_UPLOAD_PROMOTION_DATA = "UPLOAD_DR_PROMOTIONPOST";

	public static final String MEHTOD_UPLOAD_COVERAGE_STATUS = "UploadCoverageStatusUT";
	public static final String MEHTOD_UPLOAD_TAM = "UploadTAMNew";

	public static final String METHOD_UPLOAD_GEOTAG = "UploadStoreGeoTagPOST";

	public static final String METHOD_DOWNLOAD_PRODUCT_MASTER = "DownloadProductMasters";

	public static final String METHOD_NON_WORKING_MASTER = "DownloadNonWorkingPOST";

	public static final String METHOD_SET_COVERAGE_STATUS = "COVERAGESTATUS_UPDATE_SUP";

	public static final String METHOD_CHECKOUT = "Checkout_Status";
	
	public static final String ERROR = " PROBLEM OCCURED IN " ; 

	// database

	// functions name below

	public static final String METHOD_POSM = "DownloadPosmMasters";
	public static final String METHOD_DESIGNATION = "DownloadDesignations";
	public static final String METHOD_JOB_LEVELS = "DownloadJobLevels";
	public static final String METHOD_StoreKeyContact_Details = "StoreKeyContactDetails";
	public static final String METHOD_DOWNLOAD_AVAILABILITY = "Download_Availability_Mapping_NewPOST";
	
	public static final String METHOD_DOWNLOAD_ASSETPOST = "Download_AssetPOST";

	public static final String METHOD_DOWNLOAD_SKUPOST = "DownloadSKU_NewPOST";
	
	public static final String METHOD_MAPPING_ASSETPOST = "Download_MAPPING_ASSET_NEWPOST";
	
	public static final String METHOD_DOWNLOAD_PLANOGRAM = "DownloadPlanogramPOST";
	
//	public static final String Download_MAPPING_PROMOTIONPOST  = "Download_MAPPING_PROMOTIONPOST";
	
	public static final String Download_MAPPING_PROMOTIONPOST  = "Download_MAPPING_PROMOTION_NEWPOST";
	
	public static final String Download_PERFORMANCE  = "DownloadMonthlyPerformancePOST";
	
	

	// sales tableA

	public static final String TABLE_SALES = "SALES_DETAILS";
	public static final String Key_BRAND = "BRAND";
	public static final String Key_BRAND_ID = "BRAND_ID";
	public static final String Key_PRODUCT_CD = "PRODUCT_CD";
	public static final String Key_PRODUCT_NAME = "PRODUCT_NAME";
	public static final String Key_PRODUCT_TYPE = "PRODUCT_TYPE";
	public static final String Key_ISCOMPETITIOR = "IS_COMPETITOR";
	public static final String Key_Unitsold = "UNIT_SOLD";
	public static final String Key_PRICE = "PRICE";
	public static final String Key_SALES = "SALES";
	public static final String KEY_ISCOMPETETIOR = "ISCOMPETETIOR";
	public static final String KEY_ID1= "ID";
	public static final String KEY_PROMOTION_CD= "PROMOTION_CD";
	
	
	
	
	public static final String key_LSTMONTH_SALE = "LASTMONTH_SALE";
	public static final String Key_TILLDATE_SALE = "TILLDATE_SALE";
	public static final String Key_SALES_OUTLOOK = "SALES_OUTLOOK";
	public static final String Key_UpdateTam = "UPDATE_TAM_VALUE";
	public static final String Key_Current_Tam = "CURRENT_TAM_VALUE";

	public static final String METHOD_geo_tag = "DownLoadGeoTagStatusUserWisePOST";
	public static final String METHOD_DOWNLOAD_TAM = "DowloadTAMNew";
	public static final String METHOD_DOWNLOAD_PERFORM_STOREWISE = "MerPerformanceStorewise";
	public static final String METHOD_CONSOLIDATE_PERFORMANCE = "MerPerformanceConsolidate";
	
	
	public static final String KEY_ASSET_CD = "ASSET_CD";
	public static final String KEY_ASSET = "ASSET";
	
	public static final String key_count = "count";

	// table structure sales

	public static final String CREATE_TABLE_SALES_DETAILS = "CREATE TABLE "
			+ TABLE_SALES + " (" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + Key_BRAND + " VARCHAR,"
			+ Key_PRODUCT_CD + " VARCHAR," + Key_PRODUCT_NAME + " VARCHAR,"
			+ Key_BRAND_ID +" VARCHAR,"
			+ Key_PRODUCT_TYPE + " VARCHAR," + Key_ISCOMPETITIOR + " VARCHAR)";

	public static final String TABLE_SALES_INSERTED = "SALES_DETAILS_INSERTED";
	public static final String TABLE_TAM_INSERTED ="UPDATE_TAM";
	
	public static final String key_STORE_CD = "STORE_CD";

	public static final String KEY_MONTHLY_TARGET = "MONTHLY_TARGET";
	public static final String KEY_DAILY_TARGET = "DAILY_TARGET";
	public static final String KEY_ACHIEVED = "ACHIEVED";
	public static final String KEY_PENDING = "PENDING";
	
	
	public static final String TABLE_PERFORMANCE_DATA = "PERFORMANCE";
	
	
	public static final String CREATE_TABLE_PERFORMANCE = "CREATE TABLE "

			+ TABLE_PERFORMANCE_DATA
			+ " ("
			+ KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ,"
			+ KEY_MONTHLY_TARGET
			+ " VARCHAR,"
			+ KEY_DAILY_TARGET
			+ " VARCHAR,"
			+ KEY_PENDING 
			+ " VARCHAR," 			
			+ KEY_ACHIEVED
			+ " VARCHAR)";
	
	
	public static final String CREATE_TABLE_UPDATE_TAM_INSERTED = "CREATE TABLE "

			+ TABLE_TAM_INSERTED
			+ " ("
			+ KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ,"
			+ KEY_BRAND_NAME
			+ " VARCHAR,"
			+ Key_BRAND_ID
			+ " VARCHAR,"
			+ key_STORE_CD 
			+ " VARCHAR," 			
			+ KEY_STORE_TAM
			+ " VARCHAR)";


	public static final String CREATE_TABLE_SALES_DETAILS_INSERTED = "CREATE TABLE "
			+ TABLE_SALES_INSERTED
			+ " ("
			+ KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ,"
			+ Key_BRAND
			+ " VARCHAR,"
			+ Key_BRAND_ID
			+ " VARCHAR,"
			+ Key_PRODUCT_CD
			+ " VARCHAR,"
			+ Key_PRODUCT_NAME
			+ " VARCHAR,"
			+ Key_Mid
			+ " VARCHAR,"
			+ Key_SALES_OUTLOOK
			+ " VARCHAR,"
			+ key_LSTMONTH_SALE
			+ " VARCHAR,"
			+ Key_TILLDATE_SALE
			+ " VARCHAR,"
			+ Key_PRODUCT_TYPE + " VARCHAR," + Key_ISCOMPETITIOR + " VARCHAR)";

	// posm table

	public static final String TABLE_POSM = "POSM_DETAILS";
	public static final String Key_POSM_CD = "POSM_CD";
	public static final String Key_POSM = "POSM";
	public static final String Key_Noof_Units = "NOOF_UNITS";
	public static final String Key_Image = "IMAGE";
	public static final String KEY_CATEGORY = "CATEGORY";
	

	public static final String TABLE_POSM_INSERTED = "POSM_DETAILS_INSERTED";
	public static final String TABLE_PLANOGRAM = "PLANOGRAM_DETAILS";
	public static final String TABLE_CATEGORY_MASTER= "CATEGORY_MASTER";
	public static final String TABLE_NON_ASSET_REASON_MASTER= "NON_ASSET_REASON_MASTER";
	public static final String TABLE_NON_PROMOTION_REASON_MASTER= "NON_PROMOTION_REASON_MASTER";
	public static final String KEY_CATEGORY_CD = "CATEGORY_CD";

	public static final String CREATE_TABLE_POSM_DETAILS_INSERTED = "CREATE TABLE "
			+ TABLE_POSM_INSERTED
			+ " ("
			+ KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ,"
			+ Key_POSM_CD
			+ " VARCHAR,"
			+ Key_POSM
			+ " VARCHAR,"
			+ Key_Noof_Units
			+ " VARCHAR,"
			+ Key_Comments
			+ " VARCHAR,"
			+ Key_Mid
			+ " VARCHAR,"
			+ Key_Image + " VARCHAR)";

	
	public static final String CREATE_TABLE_CATEGORY_MASTER = "CREATE TABLE "
			+ TABLE_CATEGORY_MASTER
			+ " ("
			+ KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ,"
			+ KEY_CATEGORY_CD
			+ " VARCHAR,"
			+ KEY_CATEGORY
			+ " VARCHAR)"
			;
	
	
	
	public static final String CREATE_TABLE_NON_ASSET_REASON_MASTER = "CREATE TABLE "
			+ TABLE_NON_ASSET_REASON_MASTER
			+ " ("
			+ KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ,"
			+ KEY_REASON_ID
			+ " VARCHAR,"
			+ KEY_REASON
			+ " VARCHAR)"
			;


	public static final String CREATE_TABLE_NON_PROMOTION_REASON_MASTER = "CREATE TABLE "
			+ TABLE_NON_PROMOTION_REASON_MASTER
			+ " ("
			+ KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ,"
			+ KEY_REASON_ID
			+ " VARCHAR,"
			+ KEY_REASON
			+ " VARCHAR)"
			;
	
	
	
	
	
	public static final String CREATE_TABLE_PLANOGRAM = "CREATE TABLE "
			+ TABLE_PLANOGRAM
			+ " ("
			+ KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ,"
			+ key_STORE_CD
			+ " VARCHAR,"
			+ Key_BRAND
			+ " VARCHAR,"
			+ Key_Image + " VARCHAR)";
	
	public static final String TABLE_COMPETITION_INSERTED = "COMPETITON_DETAILS_INSERTED";

	public static final String CREATE_TABLE_COMPETITION_DETAILS_INSERTED = "CREATE TABLE "
			+ TABLE_COMPETITION_INSERTED
			+ " ("
			+ KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ,"
			+ Key_BRAND
			+ " VARCHAR,"
			+ Key_PRODUCT_CD
			+ " VARCHAR,"
			+ Key_PRODUCT_NAME
			+ " VARCHAR,"
			+ Key_Mid
			+ " VARCHAR,"
			+ Key_PRICE
			+ " VARCHAR,"
			+ key_LSTMONTH_SALE
			+ " VARCHAR,"
			+ Key_TILLDATE_SALE
			+ " VARCHAR,"
			+ Key_SALES_OUTLOOK
			+ " VARCHAR,"
			+ Key_PRODUCT_TYPE
			+ " VARCHAR,"
			+ Key_ISCOMPETITIOR
			+ " VARCHAR)";

	// TABLE STRUCTURE POSM

	public static final String CREATE_TABLE_POSM_DETAILS = "CREATE TABLE "
			+ TABLE_POSM + " (" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + Key_POSM_CD
			+ " VARCHAR," + Key_POSM + " VARCHAR)";

	// DESIGNATION TABLE

	public static final String TABLE_DESIGNATION = "DESIGNATION_DETAILS";
	public static final String Key_DESIGNATION_CD = "DESIGNATION_CD";
	public static final String Key_DESIGNATION = "DESIGNATION";

	// TABLE STRUCTURE DESIGNATION

	public static final String CREATE_TABLE_DESIGNATION_DETAILS = "CREATE TABLE "
			+ TABLE_DESIGNATION
			+ " ("
			+ KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ,"
			+ Key_DESIGNATION_CD
			+ " VARCHAR," + Key_DESIGNATION + " VARCHAR)";

	// JOB LEVELS TABLE

	public static final String TABLE_JOB_LEVELS = "JOB_LEVEL_DETAILS";
	public static final String Key_JOBLEVEL_ID = "JOBLEVEL_ID";
	public static final String Key_JOB_LEVEL = "JOB_LEVEL";

	// TABLE STRUCTURE JOB LEVELS

	public static final String CREATE_TABLE_JOBLEVLES_DETAILS = "CREATE TABLE "
			+ TABLE_JOB_LEVELS + " (" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + Key_JOBLEVEL_ID
			+ " VARCHAR," + Key_JOB_LEVEL + " VARCHAR)";

	// STORE KEY CONTACT TABLE

	public static final String TABLE_STOREKEY_CONTACT_DETAILS = "STOREKEY_CONTACT_DETAILS";
	public static final String KEY_CONTACT_CD = "CONTACT_CD";
	//public static final String key_STORE_CD = "STORE_CD";
	public static final String KEY_TITLE = "TITLE";
	public static final String key_NAME = "NAME";
	public static final String KEY_DESIGNATION_CD = "DESIGNATION_CD";
	public static final String key_JOBLEVEL_ID = "JOBLEVEL_ID";
	public static final String KEY_BUSINESS_EMAIL = "BUSINESS_EMAIL";
	public static final String key_MOBILE = "MOBILE";
	public static final String KEY_BUSINESS_PHONE = "BUSINESS_PHONE";
	public static final String KEY_FLAG = "FLAG";
	
	
	// TABLE STRUCTURE STORE KEY CONTACT
	public static final String CREATE_TABLE_STOREKEY_CONTACT = "CREATE TABLE "
			+ TABLE_STOREKEY_CONTACT_DETAILS + " (" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + KEY_CONTACT_CD
			+ " VARCHAR," + key_STORE_CD + " VARCHAR," + KEY_TITLE
			+ " VARCHAR," + key_NAME + " VARCHAR," + KEY_DESIGNATION_CD
			+ " VARCHAR," + key_JOBLEVEL_ID + " VARCHAR," + KEY_BUSINESS_EMAIL
			+ " VARCHAR," + key_MOBILE + " VARCHAR," + KEY_FLAG + " VARCHAR,"
			+ KEY_BUSINESS_PHONE + " VARCHAR)";

	// TRAINING TOPICS TABLE

	public static final String TABLE_TRAINING_TOPICS = "TRAINING_DETAILS";
	public static final String KEY_TOPIC_CD = "TOPIC_CD";
	public static final String key_TOPIC = "TOPIC";

	// TABLE STRUCTURE TRAINING TOPICS
	public static final String CREATE_TABLE_TRAINING_DETAILS = "CREATE TABLE "
			+ TABLE_TRAINING_TOPICS + " (" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + KEY_TOPIC_CD
			+ " VARCHAR," + key_TOPIC + " VARCHAR)";

	
	
//	BRAND, SKU, BRAND_CD, SKU_CD, shelf, facing, openingStock, pricing, price_sign, sign_corrected,
//	expriring_stock, pog_adherence, pog_corrected;
	
	
	//table Opening Stock Focus
	
	public static final String KEY_SHELF = "SHELF";
	public static final String KEY_FACING = "FACING";
	public static final String KEY_PRICING = "PRICING";
	public static final String KEY_PRICE_SIGNOGE = "PRICE_SIGNOGE";
	public static final String KEY_SIGNOGE_CORRECTED = "SIGNOGE_CORRECTED";
	public static final String KEY_EXPIRING_STOCK = "EXPIRING_STOCK";
	public static final String KEY_SHELF_FM_BOTTOM = "SHELF_FM_BOTTOM";
	public static final String KEY_POG_ADHERENCE = "POG_ADHERENCE";
	public static final String KEY_POG_CORRECTED = "POG_CORRECTED";
	public static final String KEY_COMMONID = "COMMONID";
	
			
	
	
	public static final String KEY_STORE_ID = "STORE_ID";
	public static final String KEY_STORE_NAME = "STORE_NAME";
	public static final String KEY_RIGHTS = "RIGHTS";
	
	public static final String KEY_STORE_CATEGORY = "CATEGORY";
	
	public static final String KEY_ADDRESS = "ADDRESS";
	public static final String KEY_USER_ID = "USER_ID";
	public static final String KEY_IN_TIME = "IN_TIME";
	public static final String KEY_OUT_TIME = "OUT_TIME";
	public static final String KEY_VISIT_DATE = "VISIT_DATE";
	public static final String KEY_LATITUDE = "LATITUDE";
	public static final String KEY_LONGITUDE = "LONGITUDE";
	
	public static final String KEY_RESON_REMARK = "REASON_REMARK";

	public static final String KEY_STATUS = "STATUS";
	public static final String TABLE_GEOTAG_DETAIL = "GEOTAG_MASTER";

	public static final String KEY_CITY = "CITY";
	public static final String KEY_STORE_TYPE = "STORE_TYPE";

	// FOCUS SKU

	public static final String KEY_VERTICAL_ID = "VERTICAL_ID";
	public static final String KEY_VERTICAL_NAME = "VERTICAL_NAME";
	public static final String KEY_BRAND_ID = "BRAND_ID";
	public static final String KEY_CATEGORY_ID = "CATEGORY_ID";

	public static final String KEY_COMPETITOR_ID = "COMPETITOR_BRANDID";
	public static final String KEY_CATEGORY_NAME = "CATEGORY_NAME";
	public static final String KEY_CATEGORY_CODE = "CATEGORY_CODE";
	public static final String KEY_CATEGORY_SEQ = "CATEGORY_SEQUENCE";

	public static final String KEY_SKU_ID = "SKU_ID";
	public static final String KEY_SKU_NAME = "SKU_NAME";
	public static final String KEY_COMPANY_ID = "COMPANY_ID";
	public static final String KEY_COMAPANY_NAME = "COMPANY_NAME";
	public static final String KEY_MID = "MID";
	public static final String KEY_VALUE = "VALUE";
	public static final String KEY_IMAGE_PATH1 = "IMAGE_PATH1";
	public static final String KEY_IMAGE_PATH2 = "IMAGE_PATH2";
	public static final String KEY_IMAGE_PATH3 = "IMAGE_PATH3";
	public static final String KEY_FOCUS = "FOCUS";
	public static final String KEY_BR_SEQUENCE = "BRSEQUENCE";
	public static final String KEY_SKU_SEQUENCE = "SKU_SEQUENCE";
	public static final String KEY_COMPETITOR = "COMPETITOR";
	public static final String KEY_NON_KEYCOMPETITOR = "NONKEYCOMPETITOR";
	public static final String KEY_AVAILABLE = "AVAILABLE";
	public static final String KEY_REMARKS = "REMARKS";
	// Training table

	public static final String KEY_EMAIL = "EMAIL";
	public static final String KEY_MOBILE = "MOBILE";
	public static final String KEY_LEVEL_ID = "LEVEL_ID";
	public static final String KEY_TRAINING_LEVEL = "TRAINING_LEVEL";
	public static final String KEY_TRAINING_ID = "TRAINING_ID";
	public static final String KEY_TRAINING = "TRAINING";
	public static final String KEY_POSM_ID = "POSM_ID";
	public static final String KEY_POSM = "POSM_NAME";
	
	public static final String KEY_EXPIRING_DATE = "EXPIRING_DATE";
	public static final String KEY_LISTED_CHECKBOX = "LISTED_CHECKBOX";
	public static final String KEY_LISTED_TOGGLE= "LISTED_TOGGLE";
	
	
	public static final String KEY_STORE_CD = "STORE_CD";
	public static final String KEY_DESCRIPTION = "DESCRIPTION";
	public static final String KEY_SKU_CD = "SKU_CD";
	public static final String KEY_SKU = "SKU";
	public static final String KEY_PROMOTION= "PROMOTION";
	public static final String KEY_ASSET_NAME= "ASSET";
	public static final String KEY_MBQ = "MBQ";
	public static final String KEY_IDEAL_SHELF = "IDEAL_SHELF";
	public static final String KEY_OPENINGSTOCK = "OPENING_STOCK";
	public static final String KEY_CATEGORY_SEQUENCE = "CATEGORY_SEQUENCE";
	public static final String KEY_BRAND_SEQUENCE = "BRAND_SEQUENCE";
	public static final String KEY_FACING_ALLOW = "FACING_ALLOW";
	public static final String KEY_PRICING_ALLOW = "PRICING_ALLOW";
	public static final String KEY_STOCK_ALLOW = "STOCK_ALLOW";
	public static final String KEY_STOCK = "STOCK";
	
	public static final String KEY_ALLOW_SHELF = "ALLOW_SHELF";
	public static final String KEY_ALLOW_SIGNAGE = "ALLOW_SIGNAGE";
	public static final String KEY_ALLOW_POG = "ALLOW_POG";
	public static final String KEY_ALLOW_EXPRING_STOCK = "ALLOW_EXPRING_STOCK";
	
//	public static final String KEY_SKU_SEQUENCE = "SKU_SEQUENCE";
	
	
	//Calls Tracker tAble
	
	public static final String KEY_TOTAL_CALLS12to3 = "TOTAL_CALLS_12to3";
	public static final String KEY_PRODUCTIVE_CALLS12to3 = "PRODUCTIVE_CALLS_12to3";
	public static final String KEY_PUNCH_TIME = "PUNCH_TIME";
	
	public static final String KEY_TOTAL_CALLS3to6 = "TOTAL_CALLS_3to6";
	public static final String KEY_PRODUCTIVE_CALLS3to6 = "PRODUCTIVE_CALLS_3to6";
	public static final String KEY_POS_SALE = "POS_SALE";
	
	public static final String key_actualCount = "actualCount";
	
	
	public static final String TABLE_COVERAGE_DATA = "COVERAGE_DATA";
	public static final String TABLE_STORE_DETAIL = "STORE_MASTER";
	
	public static final String KEY_BRAND_CD = "BRAND_CD";
	
	
	public static final String TABLE_TAM_DETAIL = "TAM_DOWNLOAD";

	
	public static final String TABLE_PERFORMANCE = "PERFORMANCE";
	
	public static final String TABLE_CONSOLIDATE = "CONSOLIDATE_PERFORMANCE";
	
	public static final String TABLE_POSM_MASTER = "POSM_MASTER";
	public static final String TABLE_INSERT_ADDTIONAL_DETAILS = "ADDTIONAL_INFO";

	public static final String TABLE_NON_WORKING_REASON = "NON_WORKING_DATA";
	
	public static final String TABLE_AVAILABILITY_DATA = "Availability_MappingPOST_DATA";
	
	public static final String TABLE_ASSETPOST_DATA ="Assetpost_data";
	
	public static final String TABLE_SKUPOST_DATA ="Skupost_data";
	public static final String TABLE_MAPPING_ASSETPOST ="Mapping_AssetPost_data";
	public static final String TABLE_MAPPING_PROMOTIONPOST ="Mapping_PromotionPost_data";
	public static final String TABLE_OENGINGSTOCK_DATA = "Opening_Stock_data";
	public static final String TABLE_MIDDAYSTOCK_RECIEVED_DATA = "MidDayStock_Recieved_Data";
	public static final String TABLE_CLOSING_STOCK_DATA = "ClosingStock_Data";
	public static final String TABLE_CALLS_TRACKER_DATA = "CallsTracker_data";
	public static final String TABLE_CALLS_TRACKER_DATA_12To3 = "CallsTracker_data_12to3";
	public static final String TABLE_INSERT_OPENINGHEADER_DATA = "openingHeader_data";
	public static final String TABLE_INSERT_STOCKRECIEVED_DATA = "StockRecievedHeader_data";
	public static final String TABLE_INSERT_CLOSINGSTOCK_DATA = "ClosingStockHeader_data";
	public static final String TABLE_INSERT_PROMOTION_DATA  = "INSERT_PROMOTION_DATA";
	public static final String TABLE_INSERT_HEADER_PROMOTION_DATA  = "INSERT_HEADER_PROMOTION_DATA";
	public static final String TABLE_PROMOTION_MAPPING = "PROMOTION_MAPPING";
	public static final String TABLE_INSERT_ASSET_DATA  = "INSERT_ASSET_DATA";
	public static final String TABLE_INSERT_HEADER_ASSET_DATA  = "INSERT_HEADER_ASSET_DATA";
	public static final String TABLE_INSERT_COMPETITION_DATA = "COMPETITION_DATA";
	public static final String TABLE_CLOSING_STOCK_LISTED_DATA = "ClOSINGSTOCK_LISTEDDATA";
	
	
	//PROMOTION KEYS
	public static final String KEY_PROMOTION_NAME = "PROMOTION_NAME";
	public static final String KEY_PROMOTION_AVAIL = "PROMOTION_AVAIL";
	
	
	//ASSET KEYS
	
//	public static final String KEY_ASSET_NAME = "PROMOTION_NAME";
	public static final String KEY_ASSET_AVAIL = "PROMOTION_AVAIL";
	
	//Lunch Table
	public static final String TABLE_LUNCH_TIME = "LUNCH_TIME";
	public static final String KEY_FROM_TIME = "FROM_TIME";
	public static final String KEY_TO_TIME = "TO_TIME";
	
	
	public static final String CREATE_TABLE_ADDITIONAL_DETAILS = "CREATE TABLE "
			+ TABLE_INSERT_ADDTIONAL_DETAILS + " (" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_STORE_ID + " VARCHAR,"
			+ KEY_CATEGORY_ID + " VARCHAR," + KEY_CATEGORY + " VARCHAR,"
			+ KEY_POSM_ID +" VARCHAR,"
			+ Key_POSM + " VARCHAR," + KEY_IMAGE + " VARCHAR)";
	
	
	public static final String CREATE_TABLE_INSERT_LUNCH_TIME = "CREATE TABLE "
			+ TABLE_LUNCH_TIME
			+ " ("
			+ KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ,"
			+ MID
			+ " INTEGER,"
			+ KEY_FROM_TIME
			+ " VARCHAR,"
			+ KEY_TO_TIME
			+ " VARCHAR,"
			+ KEY_USER_ID + " VARCHAR,"
			+ key_STORE_CD+ " VARCHAR)";
	
	public static final String CREATE_TABLE_insert_CHECKLIST_DATA = "CREATE TABLE "
			+ TABLE_INSERT_OPENINGHEADER_DATA
			+ " ("
			+ KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ,"
			+ MID
			+ " INTEGER,"
			+ key_STORE_CD
			+ " VARCHAR,"
			+ KEY_BRAND_CD
			+ " VARCHAR,"
			+ Key_BRAND + " VARCHAR)";
	
	
	public static final String CREATE_TABLE_insertHEADER_PROMOTION_DATA = "CREATE TABLE " +
			TABLE_INSERT_HEADER_PROMOTION_DATA + " ("
			+ KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ,"
			+ MID
			+ " INTEGER,"
			+ key_STORE_CD
			+ " VARCHAR,"
			+ KEY_SKU
			+ " VARCHAR,"
			+ KEY_SKU_CD + " VARCHAR)";
	
	
	public static final String CREATE_TABLE_insertHEADER_ASSET_DATA = "CREATE TABLE "
			+ TABLE_INSERT_HEADER_ASSET_DATA
			+ " ("
			+ KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ,"
			+ MID
			+ " INTEGER,"
			+ key_STORE_CD
			+ " VARCHAR,"
			+ KEY_BRAND_CD
			+ " VARCHAR,"
			+ Key_BRAND + " VARCHAR)";
	
	public static final String KEY_QUANTITY= "QUANTITY";
	public static final String CREATE_TABLE_COMPETITION_FACING= "CREATE TABLE "
			+ TABLE_INSERT_COMPETITION_DATA
			+ " ("
			+ KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ,"
			+ MID
			+ " INTEGER,"
			+ key_STORE_CD
			+ " VARCHAR,"
			+ KEY_CATEGORY_CD
			+ " VARCHAR,"
			+ KEY_CATEGORY 
			+ " VARCHAR,"
			+ KEY_QUANTITY
			+ " VARCHAR)";
	
	
	public static final String CREATE_TABLE_insertHeader_STOCKRECIEVED_DATA = "CREATE TABLE "
			+ TABLE_INSERT_STOCKRECIEVED_DATA
			+ " ("
			+ KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ,"
			+ MID
			+ " INTEGER,"
			+ key_STORE_CD
			+ " VARCHAR,"
			+ KEY_BRAND_CD
			+ " VARCHAR,"
			+ Key_BRAND + " VARCHAR)";
	
	
	public static final String CREATE_TABLE_insertHeader_CLOSINGSTOCK_DATA = "CREATE TABLE "
			+ TABLE_INSERT_CLOSINGSTOCK_DATA
			+ " ("
			+ KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ,"
			+ MID
			+ " INTEGER,"
			+ key_STORE_CD
			+ " VARCHAR,"
			+ KEY_BRAND_CD
			+ " VARCHAR,"
			+ Key_BRAND + " VARCHAR)";
	
	public static final String CREATE_TABLE_MIDDAYSTOCK_RECIEVED= "CREATE TABLE "
			+ TABLE_MIDDAYSTOCK_RECIEVED_DATA + " (" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + KEY_COMMONID
			+ " INTEGER," + KEY_SKU_CD + " VARCHAR," + KEY_SKU
			+ " VARCHAR," + KEY_STOCK + " VARCHAR, " + key_STORE_CD
			+ " INTEGER," + KEY_LISTED_CHECKBOX + " VARCHAR)";
	
	public static final String CREATE_TABLE_PROMOTION_DATA = "CREATE TABLE "
			+ TABLE_INSERT_PROMOTION_DATA + " (" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + KEY_COMMONID
			+ " INTEGER," + KEY_PROMOTION + " VARCHAR," + KEY_SKU
			+ " VARCHAR," + KEY_PROMOTION_AVAIL + " VARCHAR, " + KEY_REMARKS
			+ " VARCHAR," + KEY_SKU_CD + " INTEGER," + KEY_REASON_ID + " INTEGER," + KEY_REASON + " VARCHAR,"
			+ KEY_IMAGE + " VARCHAR, " + KEY_STORE_CD + " INTEGER,"+ KEY_PROMOTION_CD + " INTEGER)";

	/*,"+ KEY_DESCRIPTION + " VARCHAR,"
			+ KEY_REASON_ID + " VARCHAR,"
			+ KEY_REASON + " VARCHAR,"
			+ KEY_IMAGE + " VARCHAR
	*/
	
	public static final String CREATE_TABLE_ASSET_DATA = "CREATE TABLE "
			+ TABLE_INSERT_ASSET_DATA + " (" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + KEY_COMMONID
			+ " INTEGER," + KEY_BRAND_CD + " INTEGER, " + Key_BRAND
			+ " VARCHAR," + KEY_ASSET_NAME + " VARCHAR," +KEY_ASSET_AVAIL+ " VARCHAR,"
			+ KEY_REMARKS + " VARCHAR,"
			+ key_STORE_CD + " INTEGER, " + KEY_ASSET_CD + " VARCHAR,"
			+ key_count + " VARCHAR," + key_actualCount + " VARCHAR,"
			+ KEY_REASON_ID + " VARCHAR," + KEY_REASON + " VARCHAR,"
			+ KEY_IMAGE + " VARCHAR)"; 
	
	
	public static final String CREATE_TABLE_CLOSINGSTOCK= "CREATE TABLE "
			+ TABLE_CLOSING_STOCK_DATA + " (" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + KEY_COMMONID
			+ " INTEGER," + KEY_SKU + " VARCHAR," + KEY_SKU_CD
			+ " INTEGER," + KEY_STOCK + " VARCHAR,"+ KEY_STORE_CD
			+ " INTEGER,"
			+ KEY_LISTED_CHECKBOX + " VARCHAR)";




	public static final String CREATE_TABLE_CLOSINGSTOCK_LISTEDDATA= "CREATE TABLE "
			+ TABLE_CLOSING_STOCK_LISTED_DATA + " (" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + KEY_BRAND_CD
			+ " VARCHAR," + KEY_SKU + " VARCHAR," + KEY_SKU_CD
			+ " VARCHAR," + KEY_STORE_CD
			+ " VARCHAR,"
			+ KEY_LISTED_TOGGLE + " VARCHAR)";



	public static final String CREATE_TABLE_CALLS_TRACKER= "CREATE TABLE "
			+ TABLE_CALLS_TRACKER_DATA + " (" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + KEY_TOTAL_CALLS3to6
			+ " VARCHAR," + KEY_PRODUCTIVE_CALLS3to6 + " VARCHAR," 
			+ KEY_POS_SALE+ " VARCHAR," + Key_Mid + " INTEGER," + KEY_STORE_CD + " VARCHAR)" ; 
	
	
	public static final String CREATE_TABLE_CALLS_TRACKER2= "CREATE TABLE "
			+ TABLE_CALLS_TRACKER_DATA_12To3 + " (" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + KEY_TOTAL_CALLS12to3
			+ " VARCHAR," + KEY_PRODUCTIVE_CALLS12to3 + " VARCHAR," 
			+ KEY_PUNCH_TIME+ " VARCHAR," + Key_Mid + " INTEGER," + KEY_STORE_CD + " VARCHAR)" ; 
	
	
	
	public static final String CREATE_TABLE_OPENINGSTOCK_DATA = "CREATE TABLE "
			+ TABLE_OENGINGSTOCK_DATA + " (" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + KEY_STORE_CD
			+ " VARCHAR," + KEY_SHELF + " VARCHAR," + KEY_FACING
			+ " VARCHAR," + KEY_OPENINGSTOCK + " VARCHAR," + KEY_PRICING + " VARCHAR," + KEY_PRICE_SIGNOGE
			+ " VARCHAR," + KEY_SIGNOGE_CORRECTED + " VARCHAR," + KEY_EXPIRING_STOCK + " VARCHAR," + KEY_POG_ADHERENCE
			+ " VARCHAR," + KEY_POG_CORRECTED + " VARCHAR," + KEY_SHELF_FM_BOTTOM + " VARCHAR," + KEY_COMMONID
			+ " INTEGER," + KEY_SKU + " VARCHAR," + KEY_SKU_CD + " VARCHAR,"
			+ KEY_EXPIRING_DATE + " VARCHAR," + KEY_LISTED_CHECKBOX + " VARCHAR)";
	


	/*public static final String CREATE_TABLE_PROMOTIONPOST = "CREATE TABLE "
			+ TABLE_MAPPING_PROMOTIONPOST + " (" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + KEY_STORE_CD
			+ " VARCHAR," + KEY_SKU_CD + " VARCHAR," + KEY_PROMOTION
			+ " VARCHAR,"	
			+ KEY_CATEGORY_SEQUENCE + " VARCHAR,"
			+ KEY_BRAND_SEQUENCE + " VARCHAR,"
			+ Key_BRAND + " VARCHAR,"
			+ KEY_BRAND_CD + " VARCHAR,"
			+ KEY_SKU
			+ " VARCHAR,"
			+ KEY_DESCRIPTION
			+ " VARCHAR)";*/


	public static final String CREATE_TABLE_PROMOTIONPOST = "CREATE TABLE "
			+ TABLE_MAPPING_PROMOTIONPOST + " (" + KEY_ID1
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + KEY_STORE_CD
			+ " VARCHAR," + KEY_STORE_CD + " VARCHAR," + KEY_PROMOTION
			+ " VARCHAR,"
			+ KEY_SKU_SEQUENCE + " VARCHAR)";
	
	
	public static final String CREATE_TABLE_MAPPINGASSET_POST= "CREATE TABLE "
			+ TABLE_MAPPING_ASSETPOST + " (" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + KEY_STORE_CD
			+ " VARCHAR," + KEY_ASSET_CD + " VARCHAR," + KEY_ASSET
			+ " VARCHAR," + Key_BRAND + " VARCHAR," + KEY_BRAND_CD + " VARCHAR,"
			+   key_count + " VARCHAR)"; 
	
	
	
	
	public static final String CREATE_TABLE_NON_WORKING = "CREATE TABLE "
			+ TABLE_NON_WORKING_REASON + " (" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + KEY_REASON_ID
			+ " VARCHAR," + KEY_REASON + " VARCHAR," + KEY_RESON_REMARK
			+ " VARCHAR," + KEY_ENTRY + " VARCHAR," + KEY_IMAGE + " VARCHAR)";
	

	
	public static final String CREATE_TABLE_ASSETPOST = "CREATE TABLE "
			+ TABLE_ASSETPOST_DATA + " (" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + KEY_ASSET_CD
			+ " VARCHAR," + KEY_ASSET + " VARCHAR)";
	

	public static final String CREATE_TABLE_AVAILABILITY_DATA = "CREATE TABLE "
			+ TABLE_AVAILABILITY_DATA + " (" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + KEY_STORE_CD
			+ " VARCHAR," + KEY_SKU_CD + " VARCHAR,"  + KEY_MBQ + " VARCHAR," + KEY_IDEAL_SHELF
			+ " VARCHAR," + KEY_CATEGORY_SEQUENCE + " VARCHAR," + KEY_BRAND_SEQUENCE
			+ " INTEGER," + KEY_FACING_ALLOW + " VARCHAR," + KEY_PRICING_ALLOW
			+ " VARCHAR," +  KEY_STOCK_ALLOW + " VARCHAR," + KEY_FOCUS
			+ " VARCHAR," + KEY_SKU_SEQUENCE+ " INTEGER," + KEY_ALLOW_SHELF + " VARCHAR," + KEY_ALLOW_SIGNAGE + " VARCHAR,"
			+ KEY_ALLOW_POG + " VARCHAR," + KEY_ALLOW_EXPRING_STOCK + " VARCHAR)";
			

//	public static final String CREATE_TABLE_GEO_TAG_DATA = "CREATE TABLE "
//			+ TABLE_GEOTAG_DETAIL + " (" + KEY_ID
//			+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + KEY_STORE_ID
//			+ " VARCHAR," + KEY_STORE_NAME + " VARCHAR," + KEY_ADDRESS
//			+ " VARCHAR," + KEY_CITY + " VARCHAR," + KEY_STATUS + " VARCHAR,"
//			+ KEY_STORE_TYPE + " VARCHAR," + KEY_LATITUDE + " VARCHAR,"
//			+ KEY_LONGITUDE + " VARCHAR)";

	
	public static final String CREATE_TABLE_STORE_MASTER = "CREATE TABLE "
			+ TABLE_STORE_DETAIL + " (" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + KEY_STORE_ID
			+ " VARCHAR," + KEY_STORE_NAME + " VARCHAR," + KEY_ADDRESS
			+ " VARCHAR," + KEY_CHECKOUT_STATUS + " VARCHAR," + KEY_STATUS
			+ " VARCHAR," + KEY_VISIT_DATE + " VARCHAR," + KEY_CATEGORY_CODE
			+ " VARCHAR," + KEY_RIGHTS + " VARCHAR,"
			+ KEY_LATITUDE + " VARCHAR,"+ KEY_LONGITUDE
			+ " VARCHAR)";
	
	
	public static final String CREATE_TABLE_SKU_POST= "CREATE TABLE "
			+ TABLE_SKUPOST_DATA + " (" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + KEY_SKU_CD
			+ " VARCHAR," + KEY_SKU + " VARCHAR," + KEY_BRAND_CD
			+ " VARCHAR," + Key_BRAND + " VARCHAR,"+ KEY_CATEGORY_CD + " VARCHAR,"
			+ KEY_ISCOMPETETIOR+ " VARCHAR)"; 
	
	/*
	
	public static final String CREATE_TABLE_TAM_MASTER = "CREATE TABLE "
			+ TABLE_TAM_DETAIL + " (" + KEY_ID		
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ," 
			+ key_STORE_CD
			+ " VARCHAR," 
			+ KEY_STORE_TAM 
			+ " VARCHAR," 
			+ KEY_BRAND_ID
			+ " VARCHAR,"
			+ KEY_BRAND_NAME
			+ " VARCHAR)";
	
	
	/*	
	.key_STORE_CD, data.getSTORE_CD().get(i));
	values.put(CommonString.KEY_STORE_NAME, data.getSTORENAME().get(i));
	values.put(CommonString.KEY_CITY, data.getCITY().get(i));
	values.put(CommonString.KEY_TAM, data.getTAM().get(i));
	values.put(CommonString.KEY_SALE, data.getSALE().get(i));
	values.put(CommonString.KEY_SHARE, data.getSHARE().get(i));
	values.put(CommonString.KEY_TERRITORY, d*/
	
	
	public static final String CREATE_TABLE_PERFORMANCE_MASTER = "CREATE TABLE "
			+ TABLE_PERFORMANCE + " (" + KEY_ID		
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ," 
			+ key_STORE_CD
			+ " VARCHAR," 
			+ KEY_STORE_NAME 
			+ " VARCHAR," 
			+ KEY_CITY
			+ " VARCHAR,"
			+ KEY_TAM
			+" VARCHAR,"
			+ KEY_SALE 
			+ " VARCHAR," 
			+ KEY_SHARE
			+ " VARCHAR,"
			+ KEY_TERRITORY
			+ " VARCHAR)";
	
	public static final String CREATE_TABLE_CONSOLIDATE_PERFORMANCE = "CREATE TABLE "
			+ TABLE_CONSOLIDATE + " (" + KEY_ID		
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ," 
			+ KEY_TAM
			+" VARCHAR,"
			+ KEY_SALE 
			+ " VARCHAR," 
			+ KEY_SHARE
			+ " VARCHAR,"
			+ KEY_TERRITORY
			+ " VARCHAR)";

	public static final String CREATE_TABLE_COVERAGE_DATA = "CREATE TABLE "
			+ TABLE_COVERAGE_DATA + " (" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + KEY_STORE_ID
			+ " INTEGER,USER_ID VARCHAR, " + KEY_IN_TIME + " VARCHAR,"
			+ KEY_OUT_TIME + " VARCHAR," + KEY_VISIT_DATE + " VARCHAR,"
			+ KEY_LATITUDE + " VARCHAR," + KEY_LONGITUDE + " VARCHAR,"
			+ KEY_REASON_ID + " INTEGER," + KEY_REASON + " VARCHAR,"
			+ KEY_OUTLOOK_REMARK +" VARCHAR,"
			+ KEY_RIGHTS +" VARCHAR,"
			+ KEY_REMARKS + " VARCHAR," + KEY_IMAGE + " VARCHAR," + KEY_STATUS
			+ " VARCHAR)";

	public static final String CREATE_TABLE_POSM_MASTER = "CREATE TABLE "
			+ TABLE_POSM_MASTER + " (" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + KEY_POSM + " VARCHAR,"
			+ KEY_POSM_ID + " VARCHAR)";

	public static final String METHOD_Checkout_StatusNew = "UPLOAD_CHECKOUTPOST";

	// Geo tag

	public static final String CREATE_TABLE_INSERT_GEOTAG = "CREATE TABLE "
			+ TABLE_INSERT_GEO_TAG + " (" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + KEY_STORE_ID
			+ " VARCHAR," + KEY_LATITUDE + " VARCHAR," + KEY_LONGITUDE
			+ " VARCHAR," + KEY_STATUS + " VARCHAR," + KEY_IMAGE_PATH1
			+ " VARCHAR," + KEY_IMAGE_PATH2 + " VARCHAR," + KEY_IMAGE_PATH3
			+ " VARCHAR)";

	public static final String KEY_NAME = "NAME";

	public static final String KEY_PHONE = "PHONE_NO";
	public static final String TABLE_ADD_SALESPERSON = "ADD_SALESPERSON";

	public static final String CREATE_TABLE_ADD_SALESPERSON = "CREATE TABLE "
			+ TABLE_ADD_SALESPERSON + " (" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + KEY_TITLE + " VARCHAR,"
			+ KEY_NAME + " VARCHAR," + Key_DESIGNATION_CD + " VARCHAR,"
			+ Key_JOBLEVEL_ID + " VARCHAR," + KEY_EMAIL + " VARCHAR,"
			+ KEY_MOBILE + " VARCHAR," + KEY_TOPIC_CD + " VARCHAR," + key_TOPIC
			+ " VARCHAR," + KEY_FLAG + " VARCHAR," + KEY_PHONE + " VARCHAR,"
			+ Key_Mid + " VARCHAR,UNIQUE(NAME,EMAIL,MOBILE) ON CONFLICT FAIL)";

	public static final String TABLE_ADD_TRAINING = "TRAINING";

	public static final String CREATE_TABLE_ADD_TRAINING = "CREATE TABLE "
			+ TABLE_ADD_TRAINING + " (" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT ," + KEY_CONTACT_CD
			+ " VARCHAR," + KEY_NAME + " VARCHAR," + KEY_TRAINING_ID
			+ " VARCHAR," + KEY_TRAINING + " VARCHAR," + Key_Mid
			+ " VARCHAR,  UNIQUE(CONTACT_CD,TRAINING_ID) ON CONFLICT FAIL)";

}
