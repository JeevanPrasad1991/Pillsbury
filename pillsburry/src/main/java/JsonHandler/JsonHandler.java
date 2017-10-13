//jsonhandler
package JsonHandler;

import java.io.StringReader;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;


import com.cpm.xmlGetterSetter.AssetPostgetterSetter;
import com.cpm.xmlGetterSetter.AvailabilitygetterSetter;
import com.cpm.xmlGetterSetter.ConsolidatePerGetterSetter;
import com.cpm.xmlGetterSetter.Designationgettersetter;
import com.cpm.xmlGetterSetter.FailureGetterSetter;
import com.cpm.xmlGetterSetter.GeoTaggettersetter;
import com.cpm.xmlGetterSetter.JCPGetterSetter;
import com.cpm.xmlGetterSetter.Joblevelsgettersetter;
import com.cpm.xmlGetterSetter.LoginGetterSetter;
import com.cpm.xmlGetterSetter.MappingAssetPostgetterSetter;
import com.cpm.xmlGetterSetter.MappingPromotionpostgetterSetter;
import com.cpm.xmlGetterSetter.NonWorkingGetterSetter;
import com.cpm.xmlGetterSetter.PerformanceGetterSetter;
import com.cpm.xmlGetterSetter.PerformanceMSetterGetter;
import com.cpm.xmlGetterSetter.SKUPostgetterSetter;
import com.cpm.xmlGetterSetter.SalesBeanGetterSetter;
import com.cpm.xmlGetterSetter.TableBean;
import com.cpm.xmlGetterSetter.TamgetterSetter;
import com.cpm.xmlGetterSetter.downloadtraingtopicsgettersetter;
import com.cpm.xmlGetterSetter.planogramSetterGetter;
import com.cpm.xmlGetterSetter.posmgettersetter;
import com.cpm.xmlGetterSetter.storekeycontactdetailsgettersetter;
import com.cpm.xmlHandler.XMLHandlers;

public class JsonHandler {

	private static final String TAG_DATE = "CURRENTDATE";
	private static final String TAG_RESULT = "RESULT";
	private static final String TAG_APP_VERSION = "APP_VERSION";
	private static final String TAG_APP_PATH = "APP_PATH";
	static String query;

	static JSONArray contacts = null;
	static int eventType;

	public static LoginGetterSetter loginXMLHandler(String jsonString) {
		LoginGetterSetter lgs = new LoginGetterSetter();


//		"[{\"RIGHTNAME\":\"Merchandiser\",\"APP_VERSION\":\"1\",\"APP_PATH\":\"http:\/\/parinaam.in\/hpretail\/DownloadFile\/HPApplicationActivity_Parinaam.apk\",\"Success\":\"SUCCESS\",\"CURRENTDATE\":\"08\/20\/2014\"}]"
		jsonString = jsonString.replace("\\", "");
		jsonString = jsonString.replace("\"[", "[");
		jsonString = jsonString.replace("]\"", "]");
		try {
			if (jsonString != null) {

				contacts = new JSONArray(jsonString);

				// Getting JSON Array node
				// contacts = jsonObj.getJSONArray(TAG_DATA);

				// looping through All Contacts
				for (int i = 0; i < contacts.length(); i++) {
					JSONObject c = contacts.getJSONObject(i);

					lgs.setVERSION(c.getString(TAG_APP_VERSION));
					lgs.setPATH(c.getString(TAG_APP_PATH));
					lgs.setDATE(c.getString(TAG_DATE));

				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return lgs;

	}


	public static downloadtraingtopicsgettersetter trainTopic(String jsonString) {
		downloadtraingtopicsgettersetter training = new downloadtraingtopicsgettersetter();

		jsonString = jsonString.replace("\\", "");
		jsonString = jsonString.replace("\"[", "[");
		jsonString = jsonString.replace("]\"", "]");

		try {
			if (jsonString != null) {

				contacts = new JSONArray(jsonString);

				// Getting JSON Array node
				// contacts = jsonObj.getJSONArray(TAG_DATA);

				// looping through All Contacts
				for (int i = 0; i < contacts.length(); i++) {
					JSONObject c = contacts.getJSONObject(i);

					training.settopic_id(c.getString("TOPIC_CD"));
					training.settopic(c.getString("TOPIC"));

				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return training;
	}




	public static TamgetterSetter tamData(String jsonString) {
		TamgetterSetter tamData = new TamgetterSetter();

		jsonString = jsonString.replace("\\", "");
		jsonString = jsonString.replace("\"[", "[");
		jsonString = jsonString.replace("]\"", "]");

		try {
			if (jsonString != null) {

				contacts = new JSONArray(jsonString);

				// Getting JSON Array node
				// contacts = jsonObj.getJSONArray(TAG_DATA);

				// looping through All Contacts
				for (int i = 0; i < contacts.length(); i++) {
					JSONObject c = contacts.getJSONObject(i);

					tamData.setStore_cd(c.getString("STORE_CD"));
					tamData.setBrand_cd(c.getString("BRAND_CD"));
					tamData.setTam(c.getString("TAM"));
					tamData.setBrandName(c.getString("BRAND"));

				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return tamData;
	}



	public static PerformanceGetterSetter Performance(String jsonString) {
		PerformanceGetterSetter PerformanceData = new PerformanceGetterSetter();

		jsonString = jsonString.replace("\\", "");
		jsonString = jsonString.replace("\"[", "[");
		jsonString = jsonString.replace("]\"", "]");

		try {
			if (jsonString != null) {

				//"[{\"STORE_CD\":\"3\",\"STORENAME\":\"Lynx Technologies\",\"CITY\":\"Chennai\",\"
				//+ ""TAM\":\"5000.00\",\"SALE\":\"3150.00\",\"SHARE\":\"63\",\"TERRITORY\":\"Blue\"},



				contacts = new JSONArray(jsonString);

				// Getting JSON Array node
				// contacts = jsonObj.getJSONArray(TAG_DATA);

				// looping through All Contacts
				for (int i = 0; i < contacts.length(); i++) {
					JSONObject c = contacts.getJSONObject(i);

					PerformanceData.setSTORE_CD(c.getString("STORE_CD"));
					PerformanceData.setSTORENAME(c.getString("STORENAME"));
					PerformanceData.setCITY(c.getString("CITY"));
					PerformanceData.setTAM(c.getString("TAM"));
					PerformanceData.setSALE(c.getString("SALE"));
					PerformanceData.setSHARE(c.getString("SHARE"));
					PerformanceData.setTERRITORY(c.getString("TERRITORY"));

				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return PerformanceData;
	}




	public static ConsolidatePerGetterSetter ConsolidatePerformance(String jsonString) {
		ConsolidatePerGetterSetter ConsolidatePerformanceData = new ConsolidatePerGetterSetter();

		jsonString = jsonString.replace("\\", "");
		jsonString = jsonString.replace("\"[", "[");
		jsonString = jsonString.replace("]\"", "]");

		try {
			if (jsonString != null) {





				contacts = new JSONArray(jsonString);

				// Getting JSON Array node
				// contacts = jsonObj.getJSONArray(TAG_DATA);

				// looping through All Contacts
				for (int i = 0; i < contacts.length(); i++) {
					JSONObject c = contacts.getJSONObject(i);

					//	"[{\"TAM\":\"65000.00\",\"SALE\":\"5400.00\",\"SHARE\":\"8\",\"TERRITORY\":\"Red\"}]"

					ConsolidatePerformanceData.setTAM(c.getString("TAM"));
					ConsolidatePerformanceData.setSALE(c.getString("SALE"));
					ConsolidatePerformanceData.setSHARE(c.getString("SHARE"));
					ConsolidatePerformanceData.setTERRITORY(c.getString("TERRITORY"));


				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return ConsolidatePerformanceData;
	}





	public static GeoTaggettersetter geotag(String jsonString) {
		GeoTaggettersetter training = new GeoTaggettersetter();

		jsonString = jsonString.replace("\\", "");
		jsonString = jsonString.replace("\"[", "[");
		jsonString = jsonString.replace("]\"", "]");

		try {
			if (jsonString != null) {

				contacts = new JSONArray(jsonString);

				// Getting JSON Array node
				// contacts = jsonObj.getJSONArray(TAG_DATA);

				// looping through All Contacts
				for (int i = 0; i < contacts.length(); i++) {
					JSONObject c = contacts.getJSONObject(i);

					training.setStore_cd(c.getString("STORE_CD"));
					training.setStore_name(c.getString("STORE_NAME"));
					training.setAddres(c.getString("ADDRESS"));
					training.setStoretype(c.getString("CITY_CD"));
					training.setCity(c.getString("STORETYPE_CD"));
					training.setLatitude(c.getString("LONGITUDE"));
					training.setLongitude(c.getString("LATTITUDE"));
					training.setStoretype_status(c.getString("GEO_TAG_STATUS"));

				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return training;
	}

	public static storekeycontactdetailsgettersetter storecontactDetails(
			String jsonString) {
		storekeycontactdetailsgettersetter storeContact = new storekeycontactdetailsgettersetter();

		jsonString = jsonString.replace("\\", "");
		jsonString = jsonString.replace("\"[", "[");
		jsonString = jsonString.replace("]\"", "]");

		try {
			if (jsonString != null) {

				contacts = new JSONArray(jsonString);

				// Getting JSON Array node
				// contacts = jsonObj.getJSONArray(TAG_DATA);

				// looping through All Contacts
				for (int i = 0; i < contacts.length(); i++) {
					JSONObject c = contacts.getJSONObject(i);

					storeContact.setContact_cd(c.getString("CONTACT_CD"));
					storeContact.setStore_cd(c.getString("STORE_CD"));
					storeContact.setTitle(c.getString("TITLE"));
					storeContact.setName(c.getString("NAME"));
					storeContact.setDesignation_cd(c
							.getString("DESIGNATION_CD"));
					storeContact.setJoblevel_id(c.getString("JOBLEVEL_ID"));
					storeContact.setBusiness_email(c
							.getString("BUSINESS_EMAIL"));
					storeContact.setMobile(c.getString("MOBILE"));
					storeContact.setBusiness_phone(c
							.getString("BUSINESS_PHONE"));

				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return storeContact;
	}

	public static posmgettersetter posmDetails(String jsonString) {
		posmgettersetter posm = new posmgettersetter();

		jsonString = jsonString.replace("\\", "");
		jsonString = jsonString.replace("\"[", "[");
		jsonString = jsonString.replace("]\"", "]");

		try {
			if (jsonString != null) {

				contacts = new JSONArray(jsonString);

				// Getting JSON Array node
				// contacts = jsonObj.getJSONArray(TAG_DATA);

				// looping through All Contacts
				for (int i = 0; i < contacts.length(); i++) {
					JSONObject c = contacts.getJSONObject(i);

					posm.setposm_id(c.getString("POSM_CD"));
					posm.setposm(c.getString("POSM"));

				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return posm;
	}

	public static Joblevelsgettersetter JobLevel(String jsonString) {
		Joblevelsgettersetter joblevel = new Joblevelsgettersetter();

		jsonString = jsonString.replace("\\", "");
		jsonString = jsonString.replace("\"[", "[");
		jsonString = jsonString.replace("]\"", "]");

		try {
			if (jsonString != null) {

				contacts = new JSONArray(jsonString);

				// Getting JSON Array node
				// contacts = jsonObj.getJSONArray(TAG_DATA);

				// looping through All Contacts
				for (int i = 0; i < contacts.length(); i++) {
					JSONObject c = contacts.getJSONObject(i);

					joblevel.setjoblevel_cd(c.getString("JOBLEVEL_ID"));
					joblevel.setjoblevel(c.getString("JOB_LEVEL"));

				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return joblevel;
	}

	public static Designationgettersetter DesignationDetails(String jsonString) {
		Designationgettersetter desig = new Designationgettersetter();

		jsonString = jsonString.replace("\\", "");
		jsonString = jsonString.replace("\"[", "[");
		jsonString = jsonString.replace("]\"", "]");

		try {
			if (jsonString != null) {

				contacts = new JSONArray(jsonString);

				// Getting JSON Array node
				// contacts = jsonObj.getJSONArray(TAG_DATA);

				// looping through All Contacts
				for (int i = 0; i < contacts.length(); i++) {
					JSONObject c = contacts.getJSONObject(i);

					desig.setdesignation_cd(c.getString("DESIGNATION_CD"));
					desig.setdesignation(c.getString("DESIGNATION"));

				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return desig;
	}


	public static AvailabilitygetterSetter availabilityDetails(String jsonString){
		AvailabilitygetterSetter avail = new AvailabilitygetterSetter();

		jsonString = jsonString.replace("\\", "");
		jsonString = jsonString.replace("\"[", "[");
		jsonString = jsonString.replace("]\"", "]");

		try {
			if (jsonString != null) {

				contacts = new JSONArray(jsonString);

				// looping through All Contacts
				for (int i = 0; i < contacts.length(); i++) {
					JSONObject c = contacts.getJSONObject(i);
					avail.setSTORE_CD(c.getString("STORE_CD"));
					avail.setSKU_CD(c.getString("SKU_CD"));

					avail.setMBQ(c.getString("MBQ"));
					avail.setIDEAL_SHELF(c.getString("IDEAL_SHELF"));
					avail.setCATEGORY_SEQUENCE(c.getString("CATEGORY_SEQUENCE"));
					avail.setSKU_SEQUENCE(c.getString("SKU_SEQUENCE"));
					avail.setBRAND_SEQUENCE(c.getString("BRAND_SEQUENCE"));
					avail.setFACING_ALLOW(c.getString("FACING_ALLOW"));
					avail.setPRICING_ALLOW(c.getString("PRICING_ALLOW"));
					avail.setSTOCK_ALLOW(c.getString("STOCK_ALLOW"));
					avail.setFOCUS(c.getString("FOCUS"));
					avail.setALLOW_EXPRING_STOCK(c.getString("ALLOW_EXPRING_STOCK"));
					avail.setALLOW_POG(c.getString("ALLOW_POG"));
					avail.setALLOW_SIGNAGE(c.getString("ALLOW_SIGNAGE"));
					avail.setALLOW_SHELF(c.getString("ALLOW_SHELF"));

				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return avail;
	}


	public static PerformanceMSetterGetter performanceDetails(String jsonString){
		PerformanceMSetterGetter avail = new PerformanceMSetterGetter();

		jsonString = jsonString.replace("\\", "");
		jsonString = jsonString.replace("\"[", "[");
		jsonString = jsonString.replace("]\"", "]");

		try {
			if (jsonString != null) {

				contacts = new JSONArray(jsonString);

				// looping through All Contacts
				for (int i = 0; i < contacts.length(); i++) {
					JSONObject c = contacts.getJSONObject(i);
					avail.setMonthly_target(c.getString("MONTHLY_TARGET_UNITS"));
					avail.setDaily_target(c.getString("DAILY_TARGET_UNITS"));
					avail.setAchieved(c.getString("ACHIEVED"));
					avail.setPending(c.getString("PENDING"));
				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return avail;
	}


	public static AssetPostgetterSetter AssetPostHandler(String jsonString){
		AssetPostgetterSetter asset = new AssetPostgetterSetter();

		jsonString = jsonString.replace("\\", "");
		jsonString = jsonString.replace("\"[", "[");
		jsonString = jsonString.replace("]\"", "]");

		try {
			if (jsonString != null) {

				contacts = new JSONArray(jsonString);;

				// looping through All Contacts
				for (int i = 0; i < contacts.length(); i++) {
					JSONObject c = contacts.getJSONObject(i);

					asset.setASSET_CD(c.getString("ASSET_CD"));
					asset.setASSET(c.getString("ASSET"));
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return asset;

	}

	public static SKUPostgetterSetter skupostHandler(String jsonString){
		SKUPostgetterSetter skupost = new SKUPostgetterSetter();

		jsonString = jsonString.replace("\\", "");
		jsonString = jsonString.replace("\"[", "[");
		jsonString = jsonString.replace("]\"", "]");

		try {
			if (jsonString != null) {

				contacts = new JSONArray(jsonString);
				// looping through All Contacts
				for (int i = 0; i < contacts.length(); i++) {
					JSONObject c = contacts.getJSONObject(i);

					skupost.setSKU_CD(c.getString("SKU_CD"));
					skupost.setSKU(c.getString("SKU"));
					skupost.setBRAND_CD(c.getString("BRAND_CD"));
					skupost.setBRAND(c.getString("BRAND"));
					skupost.setCATEGORY_CD(c.getString("CATEGORY_CD"));
					skupost.setISCOMPET(c.getString("ISCOMPETITOR"));

				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return skupost;
	}


	public static MappingAssetPostgetterSetter mapAssetPost(String jsonString){
		MappingAssetPostgetterSetter mapAsset = new MappingAssetPostgetterSetter();

		jsonString = jsonString.replace("\\", "");
		jsonString = jsonString.replace("\"[", "[");
		jsonString = jsonString.replace("]\"", "]");

		try {
			if (jsonString != null) {

				contacts = new JSONArray(jsonString);
				// looping through All Contacts
				for (int i = 0; i < contacts.length(); i++) {
					JSONObject c = contacts.getJSONObject(i);
					mapAsset.setSTORE_CD(c.getString("STORE_CD"));
//					mapAsset.setSKU(c.getString("SKU"));
					mapAsset.setASSET_CD(c.getString("ASSET_CD"));
					mapAsset.setASSET(c.getString("ASSET"));
					mapAsset.setBRAND_CD(c.getString("BRAND_ID"));
					mapAsset.setBRAND(c.getString("BRAND"));
					mapAsset.setCount(c.getString("ASSET_COUNT"));
				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return mapAsset;
	}

	public static MappingPromotionpostgetterSetter mapPromotionpost(String jsonString){
		MappingPromotionpostgetterSetter promotionpost = new MappingPromotionpostgetterSetter();

		jsonString = jsonString.replace("\\", "");
		jsonString = jsonString.replace("\"[", "[");
		jsonString = jsonString.replace("]\"", "]");

		try {
			if (jsonString != null) {
				contacts = new JSONArray(jsonString);
				// looping through All Contacts
				for (int i = 0; i < contacts.length(); i++) {
					JSONObject c = contacts.getJSONObject(i);

					promotionpost.setSTORE_CD(c.getString("STORE_CD"));
					promotionpost.setSKU_CD(c.getString("SKU_CD"));
					promotionpost.setSKU(c.getString("SKU"));
					promotionpost.setPROMOTION(c.getString("PROMOTION"));
					promotionpost.setDescription(c.getString("DESCRIPTION"));
					promotionpost.setCATEGORY_SEQUENCE(c.getString("CATEGORY_SEQUENCE"));
					promotionpost.setBRAND_SEQUENCE(c.getString("BRAND_SEQUENCE"));
					promotionpost.setBRAND(c.getString("BRAND"));
					promotionpost.setBRAND_CD(c.getString("BRAND_CD"));

				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return promotionpost;
	}


	public static planogramSetterGetter planogramDetails(String jsonString) {
		planogramSetterGetter pgs = new planogramSetterGetter();

		jsonString = jsonString.replace("\\", "");
		jsonString = jsonString.replace("\"[", "[");
		jsonString = jsonString.replace("]\"", "]");

		try {
			if (jsonString != null) {

				// Getting JSON Array node
				contacts = new JSONArray(jsonString);

				// looping through All Contacts
				for (int i = 0; i < contacts.length(); i++) {
					JSONObject c = contacts.getJSONObject(i);

					pgs.setSTORE_CD(c.getString("STORE_CD"));
					pgs.setBRAND_CD(c.getString("BRAND_CD"));
					pgs.setImg_URL(c.getString("IMG_URL"));

				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return pgs;
	}

	public static NonWorkingGetterSetter NonWorkingXMLHandler(String jsonString) {
		NonWorkingGetterSetter nonWorking = new NonWorkingGetterSetter();

		jsonString = jsonString.replace("\\", "");
		jsonString = jsonString.replace("\"[", "[");
		jsonString = jsonString.replace("]\"", "]");

		try {
			if (jsonString != null) {

				contacts = new JSONArray(jsonString);

				// Getting JSON Array node
				// contacts = jsonObj.getJSONArray(TAG_DATA);

				// looping through All Contacts
				for (int i = 0; i < contacts.length(); i++) {
					JSONObject c = contacts.getJSONObject(i);

					nonWorking.setReason_id(c.getString("REASON_CD"));
					nonWorking.setReason(c.getString("REASON"));
				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return nonWorking;
	}

	public static SalesBeanGetterSetter salesXMLHandler(String jsonString) {

		SalesBeanGetterSetter salesDetails = new SalesBeanGetterSetter();

		jsonString = jsonString.replace("\\", "");
		jsonString = jsonString.replace("\"[", "[");
		jsonString = jsonString.replace("]\"", "]");

		try {
			if (jsonString != null) {

				contacts = new JSONArray(jsonString);

				// Getting JSON Array node
				// contacts = jsonObj.getJSONArray(TAG_DATA);

				// looping through All Contacts
				for (int i = 0; i < contacts.length(); i++) {
					JSONObject c = contacts.getJSONObject(i);

					salesDetails.setBrandId(c.getString("BRAND_CD"));
					salesDetails.setBrand(c.getString("BRAND"));
					salesDetails.setProduct(c.getString("PRODUCT"));
					salesDetails.setProduct_cd(c.getString("PRODUCT_CD"));
					salesDetails.setType(c.getString("TYPE"));
					salesDetails.setIscompetitor(c.getString("ISCOMPETITOR"));

				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return salesDetails;
	}

	public static JCPGetterSetter JCPXMLHandler(String jsonString) {
		JCPGetterSetter jcpGetterSetter = new JCPGetterSetter();

		try {

			jsonString = jsonString.replace("\\", "");
			jsonString = jsonString.replace("\"[", "[");
			jsonString = jsonString.replace("]\"", "]");

			// jsonString=jsonString.replace("]", newChar)
			if (jsonString != null) {

				contacts = new JSONArray(jsonString);

				// Getting JSON Array node
				// contacts = jsonObj.getJSONArray(TAG_DATA);


				// looping through All Contacts
				for (int i = 0; i < contacts.length(); i++) {
					JSONObject c = contacts.getJSONObject(i);

					jcpGetterSetter.setStoreid(c.getString("STORE_CD"));
					jcpGetterSetter.setStorename(c.getString("STORE_NAME"));
					jcpGetterSetter.setStoreaddres(c.getString("ADDRESS"));
					jcpGetterSetter.setStorelatitude(c.getString("LATITUDE"));
					jcpGetterSetter.setStorelongitude(c.getString("LONGITUDE"));
					jcpGetterSetter.setVisitdate(c.getString("VISIT_DATE"));
					jcpGetterSetter.setCATEGORY_ID(c.getString("CATEGORY_CD"));
					jcpGetterSetter.setStatus(c.getString("STATUS"));
					jcpGetterSetter.setCheckout_status(c.getString("CHECKOUT_STATUS"));
					jcpGetterSetter.setRIGHTS(c.getString("RIGHTS"));


				/*
					jcpGetterSetter.setStoreid("369");
					jcpGetterSetter.setStorename("teststore2 ABRL Hyper");
					jcpGetterSetter.setStoreaddres("delhi");
					jcpGetterSetter.setStorelatitude("0");
					jcpGetterSetter.setStorelongitude("0");
					jcpGetterSetter.setVisitdate("09/13/2014");
					jcpGetterSetter.setCATEGORY_ID("1");
					jcpGetterSetter.setStatus("");
					jcpGetterSetter.setCheckout_status("N");
					jcpGetterSetter.setRIGHTS("Promoter");
				*/

//					jcpGetterSetter.setCATEGORY(c.getString("CATEGORY"));
//					jcpGetterSetter.setTAM(c
//							.getString("TAM"));

				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return jcpGetterSetter;

	}






	public static SKUPostgetterSetter CategoryXMLHandler(String jsonString) {
		SKUPostgetterSetter jcpGetterSetter = new SKUPostgetterSetter();

		try {

			jsonString = jsonString.replace("\\", "");
			jsonString = jsonString.replace("\"[", "[");
			jsonString = jsonString.replace("]\"", "]");

			// jsonString=jsonString.replace("]", newChar)
			if (jsonString != null) {

				contacts = new JSONArray(jsonString);

				XmlPullParserFactory factory = XmlPullParserFactory
						.newInstance();
				factory.setNamespaceAware(true);
				XmlPullParser xpp = factory.newPullParser();

				// looping through All Contacts
				for (int i = 0; i < contacts.length(); i++) {
					JSONObject c = contacts.getJSONObject(i);
					String query=c.getString("META_DATA");
					String data=c.getString("XMLDATA");

//					TableBean.setJcp_table(query);

					xpp.setInput(new StringReader(data));
					xpp.next();
					eventType = xpp.getEventType();
					jcpGetterSetter = XMLHandlers.catXMLHandler(xpp, eventType);




				}
			}


		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return jcpGetterSetter;

	}



	public static SKUPostgetterSetter NonAssetReasonXMLHandler(String jsonString) {
		SKUPostgetterSetter jcpGetterSetter = new SKUPostgetterSetter();

		try {

			jsonString = jsonString.replace("\\", "");
			jsonString = jsonString.replace("\"[", "[");
			jsonString = jsonString.replace("]\"", "]");

			// jsonString=jsonString.replace("]", newChar)
			if (jsonString != null) {

				contacts = new JSONArray(jsonString);

				XmlPullParserFactory factory = XmlPullParserFactory
						.newInstance();
				factory.setNamespaceAware(true);
				XmlPullParser xpp = factory.newPullParser();

				// looping through All Contacts
				for (int i = 0; i < contacts.length(); i++) {
					JSONObject c = contacts.getJSONObject(i);
					String query=c.getString("META_DATA");
					String data=c.getString("XMLDATA");

//					TableBean.setJcp_table(query);

					xpp.setInput(new StringReader(data));
					xpp.next();
					eventType = xpp.getEventType();
					jcpGetterSetter = XMLHandlers.NonAssetReasonXMLHandler(xpp, eventType);




				}
			}


		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return jcpGetterSetter;

	}


	/*public static MappingPromotionpostgetterSetter mapPromotionpost(String jsonString){
		MappingPromotionpostgetterSetter promotionpost = new MappingPromotionpostgetterSetter();

		jsonString = jsonString.replace("\\", "");
		jsonString = jsonString.replace("\"[", "[");
		jsonString = jsonString.replace("]\"", "]");

		try {
			if (jsonString != null) {
				contacts = new JSONArray(jsonString);
				// looping through All Contacts
				for (int i = 0; i < contacts.length(); i++) {
					JSONObject c = contacts.getJSONObject(i);

					promotionpost.setSTORE_CD(c.getString("STORE_CD"));
					promotionpost.setSKU_CD(c.getString("SKU_CD"));
					promotionpost.setSKU(c.getString("SKU"));
					promotionpost.setPROMOTION(c.getString("PROMOTION"));
					promotionpost.setDescription(c.getString("DESCRIPTION"));
					promotionpost.setCATEGORY_SEQUENCE(c.getString("CATEGORY_SEQUENCE"));
					promotionpost.setBRAND_SEQUENCE(c.getString("BRAND_SEQUENCE"));
					promotionpost.setBRAND(c.getString("BRAND"));
					promotionpost.setBRAND_CD(c.getString("BRAND_CD"));

				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return promotionpost;
	}
	*/


	public static MappingPromotionpostgetterSetter NonPromotionalXMLHandlerData(String jsonString){
		MappingPromotionpostgetterSetter promotionpost = new MappingPromotionpostgetterSetter();
		try {
			jsonString = jsonString.replace("\\", "");
			jsonString = jsonString.replace("\"[", "[");
			jsonString = jsonString.replace("]\"", "]");
			if (jsonString != null) {
				contacts = new JSONArray(jsonString);
				XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
				factory.setNamespaceAware(true);
				XmlPullParser xpp = factory.newPullParser();
				// looping through All Contacts
				for (int i = 0; i < contacts.length(); i++) {
					JSONObject c = contacts.getJSONObject(i);
					query = c.getString("META_DATA");
					String data = c.getString("XMLDATA");
					if (!data.contains("NODATA")) {
						xpp.setInput(new StringReader(data));
						xpp.next();
						eventType = xpp.getEventType();
						promotionpost = XMLHandlers.NonPromotionalXMLHandlerData(xpp, eventType);

					}
				}
				TableBean.setMapping_PromotionPost_data(query);
			}

		}catch (Exception e){
			e.printStackTrace();
		}
		return promotionpost;
	}

	public static SKUPostgetterSetter NonPromotionReasonXMLHandler(String jsonString) {
		SKUPostgetterSetter jcpGetterSetter = new SKUPostgetterSetter();

		try {

			jsonString = jsonString.replace("\\", "");
			jsonString = jsonString.replace("\"[", "[");
			jsonString = jsonString.replace("]\"", "]");

			// jsonString=jsonString.replace("]", newChar)
			if (jsonString != null) {

				contacts = new JSONArray(jsonString);

				XmlPullParserFactory factory = XmlPullParserFactory
						.newInstance();
				factory.setNamespaceAware(true);
				XmlPullParser xpp = factory.newPullParser();

				// looping through All Contacts
				for (int i = 0; i < contacts.length(); i++) {
					JSONObject c = contacts.getJSONObject(i);
					String query=c.getString("META_DATA");
					String data=c.getString("XMLDATA");

//					TableBean.setJcp_table(query);

					xpp.setInput(new StringReader(data));
					xpp.next();
					eventType = xpp.getEventType();
					jcpGetterSetter = XMLHandlers.NonPromotionReasonXMLHandler(xpp, eventType);




				}
			}


		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return jcpGetterSetter;

	}



	public static AssetPostgetterSetter POSMXMLHandler(String jsonString) {
		AssetPostgetterSetter jcpGetterSetter = new AssetPostgetterSetter();

		try {

			jsonString = jsonString.replace("\\", "");
			jsonString = jsonString.replace("\"[", "[");
			jsonString = jsonString.replace("]\"", "]");

			// jsonString=jsonString.replace("]", newChar)
			if (jsonString != null) {

				contacts = new JSONArray(jsonString);

				XmlPullParserFactory factory = XmlPullParserFactory
						.newInstance();
				factory.setNamespaceAware(true);
				XmlPullParser xpp = factory.newPullParser();

				// looping through All Contacts
				for (int i = 0; i < contacts.length(); i++) {
					JSONObject c = contacts.getJSONObject(i);
					String query=c.getString("META_DATA");
					String data=c.getString("XMLDATA");

//					TableBean.setJcp_table(query);

					xpp.setInput(new StringReader(data));
					xpp.next();
					eventType = xpp.getEventType();
					jcpGetterSetter = XMLHandlers.POSMXMLHandler(xpp, eventType);




				}
			}


		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return jcpGetterSetter;

	}




	public static FailureGetterSetter FailureXMLHandler(String jsonString) {
		FailureGetterSetter lgs = new FailureGetterSetter();

		try {
			if (jsonString != null) {

				JSONObject jsonObj = new JSONObject(jsonString);

				// Getting JSON Array node
				contacts = jsonObj.getJSONArray(jsonString);

				// looping through All Contacts
				for (int i = 0; i < contacts.length(); i++) {
					JSONObject c = contacts.getJSONObject(i);

					if (c.getString("STATUS").equals("STATUS")) {
						lgs.setStatus(c.getString("STATUS"));
					}
					if (c.getString("ERRORMSG").equals("ERRORMSG")) {
						lgs.setErrorMsg(c.getString("ERRORMSG"));
					}

				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return lgs;

	}




}
