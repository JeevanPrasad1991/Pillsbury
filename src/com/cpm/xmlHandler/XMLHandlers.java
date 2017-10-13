package com.cpm.xmlHandler;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.cpm.xmlGetterSetter.AssetPostgetterSetter;
import com.cpm.xmlGetterSetter.FailureGetterSetter;
import com.cpm.xmlGetterSetter.JCPGetterSetter;
import com.cpm.xmlGetterSetter.LoginGetterSetter;
import com.cpm.xmlGetterSetter.NonWorkingGetterSetter;
import com.cpm.xmlGetterSetter.SKUPostgetterSetter;

public class XMLHandlers {

	// LOGIN XML HANDLER
	public static LoginGetterSetter loginXMLHandler(XmlPullParser xpp,
			int eventType) {
		LoginGetterSetter lgs = new LoginGetterSetter();

		try {
			while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
				if (xpp.getEventType() == XmlPullParser.START_TAG) {
					if (xpp.getName().equals("RESULT")) {
						lgs.setResult(xpp.nextText());
					}
					if (xpp.getName().equals("APP_VERSION")) {
						lgs.setVERSION(xpp.nextText());
					}
					if (xpp.getName().equals("APP_PATH")) {
						lgs.setPATH(xpp.nextText());
					}
					if (xpp.getName().equals("DATE")) {
						lgs.setDATE(xpp.nextText());
					}
				}
				xpp.next();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lgs;
	}
	
	
	public static SKUPostgetterSetter catXMLHandler(XmlPullParser xpp, int eventType) {
		SKUPostgetterSetter jcpGetterSetter = new SKUPostgetterSetter();

		try {
			while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
				if (xpp.getEventType() == XmlPullParser.START_TAG) {
					if (xpp.getName().equals("CATEGORY_CD")) {
						jcpGetterSetter.setCATEGORY_CD(xpp.nextText());
					}
					if (xpp.getName().equals("CATEGORY")) {
						jcpGetterSetter.setCATEGORY(xpp.nextText());
					}
					
					

				}
				xpp.next();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return jcpGetterSetter;
	}
	
	
	
	public static SKUPostgetterSetter NonAssetReasonXMLHandler(XmlPullParser xpp, int eventType) {
		SKUPostgetterSetter jcpGetterSetter = new SKUPostgetterSetter();

		try {
			while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
				if (xpp.getEventType() == XmlPullParser.START_TAG) {
					if (xpp.getName().equals("AREASON_ID")) {
						jcpGetterSetter.setAREASON_ID(xpp.nextText());
					}
					if (xpp.getName().equals("REASON")) {
						jcpGetterSetter.setAREASON(xpp.nextText());
					}
					
					

				}
				xpp.next();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return jcpGetterSetter;
	}
	
	
	public static SKUPostgetterSetter NonPromotionReasonXMLHandler(XmlPullParser xpp, int eventType) {
		SKUPostgetterSetter jcpGetterSetter = new SKUPostgetterSetter();

		try {
			while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
				if (xpp.getEventType() == XmlPullParser.START_TAG) {
					if (xpp.getName().equals("PREASON_ID")) {
						jcpGetterSetter.setPREASON_ID(xpp.nextText());
					}
					if (xpp.getName().equals("REASON")) {
						jcpGetterSetter.setPREASON(xpp.nextText());
					}
					
					

				}
				xpp.next();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return jcpGetterSetter;
	}
	
	
	public static AssetPostgetterSetter POSMXMLHandler(XmlPullParser xpp, int eventType) {
		AssetPostgetterSetter jcpGetterSetter = new AssetPostgetterSetter();

		try {
			while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
				if (xpp.getEventType() == XmlPullParser.START_TAG) {
					if (xpp.getName().equals("POSM_CD")) {
						jcpGetterSetter.setPOSM_CD(xpp.nextText());
					}
					if (xpp.getName().equals("POSM")) {
						jcpGetterSetter.setPOSM(xpp.nextText());
					}
	
				}
				xpp.next();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return jcpGetterSetter;
	}

	// FAILURE XML HANDLER
	public static FailureGetterSetter failureXMLHandler(XmlPullParser xpp,
			int eventType) {
		FailureGetterSetter failureGetterSetter = new FailureGetterSetter();

		try {
			while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
				if (xpp.getEventType() == XmlPullParser.START_TAG) {
					if (xpp.getName().equals("STATUS")) {
						failureGetterSetter.setStatus(xpp.nextText());
					}
					if (xpp.getName().equals("ERRORMSG")) {
						failureGetterSetter.setErrorMsg(xpp.nextText());
					}

				}
				xpp.next();
			}
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return failureGetterSetter;
	}

	
	public static NonWorkingGetterSetter NonWorkingXMLHandler(
			XmlPullParser xpp, int eventType) {
		NonWorkingGetterSetter jcpGetterSetter = new NonWorkingGetterSetter();

		try {
			while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
				if (xpp.getEventType() == XmlPullParser.START_TAG) {
					if (xpp.getName().equals("REASON_ID")) {
						jcpGetterSetter.setReason_id(xpp.nextText());
					}
					if (xpp.getName().equals("REASON")) {
						jcpGetterSetter.setReason(xpp.nextText());
					}
					if (xpp.getName().equals("ENTRY_ALLOW")) {
						jcpGetterSetter.setEntry(xpp.nextText());
					}
					if (xpp.getName().equals("IMAGE_ALLOW")) {
						jcpGetterSetter.setImage(xpp.nextText());
					}
					

				}
				xpp.next();
			}
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jcpGetterSetter;
	}

	
	
	// JCP XML HANDLER
	public static JCPGetterSetter JCPXMLHandler(XmlPullParser xpp, int eventType) {
		JCPGetterSetter jcpGetterSetter = new JCPGetterSetter();

		try {
			while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
				if (xpp.getEventType() == XmlPullParser.START_TAG) {
					if (xpp.getName().equals("STORE_ID")) {
						jcpGetterSetter.setStoreid(xpp.nextText());
					}
					if (xpp.getName().equals("STORE_NAME")) {
						jcpGetterSetter.setStorename(xpp.nextText());
					}
					if (xpp.getName().equals("ADDRESS")) {
						jcpGetterSetter.setStoreaddres(xpp.nextText());
					}
					if (xpp.getName().equals("LATITUDE")) {
						jcpGetterSetter.setStorelatitude(xpp.nextText());
					}
					if (xpp.getName().equals("LOGITUDE")) {
						jcpGetterSetter.setStorelongitude(xpp.nextText());
					}
					if (xpp.getName().equals("KEY_ID")) {
						jcpGetterSetter.setKEY_ID(xpp.nextText());
					}
					if (xpp.getName().equals("CHECKOUT_STATUS")) {
						jcpGetterSetter.setCheckout_status(xpp.nextText());
					}
					if (xpp.getName().equals("STATUS")) {
						jcpGetterSetter.setStatus(xpp.nextText());
					}

				}
				xpp.next();
			}
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jcpGetterSetter;
	}

}
